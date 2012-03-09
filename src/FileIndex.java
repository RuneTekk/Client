// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

import java.io.*;

public final class FileIndex {

    public FileIndex(RandomAccessFile mainFile, RandomAccessFile indexFile, int id, int mSize) {
        maxSize = 65000;
        indexId = id;
        mainRandomAccessFile = mainFile;
        indexRandomAccessFile = indexFile;
        maxSize = mSize;
    }

    public synchronized byte[] get(int id) {
        try {
            seek(indexRandomAccessFile, id * 6);
            {
                int read;
                for(int j = 0; j < 6; j += read) {
                    read = indexRandomAccessFile.read(chunkBuffer, j, 6 - j);
                    if(read == -1)
                        return null;
                }
            }
            int size = ((chunkBuffer[0] & 0xff) << 16) + ((chunkBuffer[1] & 0xff) << 8) + (chunkBuffer[2] & 0xff);
            int block = ((chunkBuffer[3] & 0xff) << 16) + ((chunkBuffer[4] & 0xff) << 8) + (chunkBuffer[5] & 0xff);
            if(size < 0)
                return null;
            if(block <= 0 || (long)block > mainRandomAccessFile.length() / 520L)
                return null;
            byte src[] = new byte[size];
            int archiveOffset = 0;
            for(int chunk = 0; archiveOffset < size; chunk++) {
                if(block == 0)
                    return null;
                seek(mainRandomAccessFile, block * 520);
                int off = 0;
                int blockSize = size - archiveOffset;
                if(blockSize > 512)
                    blockSize = 512;
                int read;
                for(; off < blockSize + 8; off += read)  {
                    read = mainRandomAccessFile.read(chunkBuffer, off, (blockSize + 8) - off);
                    if(read == -1)
                        return null;
                }
                int expectedArchive = ((chunkBuffer[0] & 0xff) << 8) + (chunkBuffer[1] & 0xff);
                int expectedChunk = ((chunkBuffer[2] & 0xff) << 8) + (chunkBuffer[3] & 0xff);
                int nextBlock = ((chunkBuffer[4] & 0xff) << 16) + ((chunkBuffer[5] & 0xff) << 8) + (chunkBuffer[6] & 0xff);
                int expectedIndex = chunkBuffer[7] & 0xff;
                if(expectedArchive != id || expectedChunk != chunk || expectedIndex != indexId)
                    return null;
                if(nextBlock < 0 || (long)nextBlock > mainRandomAccessFile.length() / 520L)
                    return null;
                for(int i = 0; i < blockSize; i++)
                    src[archiveOffset++] = chunkBuffer[i + 8];
                block = nextBlock;
            }
            return src;
        } catch(IOException ex) {
            return null;
        }
    }

    public synchronized boolean put(byte src[], int id, int len) {
        boolean successful = put(true, id, len, src);
        if(!successful)
            successful = put(false, id, len, src);
        return successful;
    }

    public synchronized boolean put(boolean exists, int id, int len, byte abyte0[]) {
        try {
            int firstBlock;
            if(exists) {
                seek(indexRandomAccessFile, id * 6);
                int read;
                for(int off = 0; off < 6; off += read) {
                    read = indexRandomAccessFile.read(chunkBuffer, off, 6 - off);
                    if(read == -1)
                        return false;
                }
                firstBlock = ((chunkBuffer[3] & 0xff) << 16) + ((chunkBuffer[4] & 0xff) << 8) + (chunkBuffer[5] & 0xff);
                if(firstBlock <= 0 || (long)firstBlock > mainRandomAccessFile.length() / 520L)
                    return false;
            } else {
                firstBlock = (int)((mainRandomAccessFile.length() + 519L) / 520L);
                if(firstBlock == 0)
                    firstBlock = 1;
            }
            chunkBuffer[0] = (byte)(len >> 16);
            chunkBuffer[1] = (byte)(len >> 8);
            chunkBuffer[2] = (byte)len;
            chunkBuffer[3] = (byte)(firstBlock >> 16);
            chunkBuffer[4] = (byte)(firstBlock >> 8);
            chunkBuffer[5] = (byte) firstBlock;
            seek(indexRandomAccessFile, id * 6);
            indexRandomAccessFile.write(chunkBuffer, 0, 6);
            int archiveOffset = 0;
            for(int chunk = 0; archiveOffset < len; chunk++) {
                int nextBlock = 0;
                if(exists) {
                    seek(mainRandomAccessFile, firstBlock * 520);
                    int off;
                    int read;
                    for(off = 0; off < 8; off += read) {
                        read = mainRandomAccessFile.read(chunkBuffer, off, 8 - off);
                        if(read == -1)
                            break;
                    }
                    if(off == 8) {
                        int expectedArchive = ((chunkBuffer[0] & 0xff) << 8) + (chunkBuffer[1] & 0xff);
                        int expectedChunk = ((chunkBuffer[2] & 0xff) << 8) + (chunkBuffer[3] & 0xff);
                        nextBlock = ((chunkBuffer[4] & 0xff) << 16) + ((chunkBuffer[5] & 0xff) << 8) + (chunkBuffer[6] & 0xff);
                        int expectedIndex = chunkBuffer[7] & 0xff;
                        if(expectedArchive != id || expectedChunk != chunk || expectedIndex != indexId)
                            return false;
                        if(nextBlock < 0 || (long)nextBlock > mainRandomAccessFile.length() / 520L)
                            return false;
                    }
                }
                if(nextBlock == 0) {
                    exists = false;
                    nextBlock = (int)((mainRandomAccessFile.length() + 519L) / 520L);
                    if(nextBlock == 0)
                        nextBlock++;
                    if(nextBlock == firstBlock)
                        nextBlock++;
                }
                if(len - archiveOffset <= 512)
                    nextBlock = 0;
                chunkBuffer[0] = (byte)(id >> 8);
                chunkBuffer[1] = (byte)id;
                chunkBuffer[2] = (byte)(chunk >> 8);
                chunkBuffer[3] = (byte)chunk;
                chunkBuffer[4] = (byte)(nextBlock >> 16);
                chunkBuffer[5] = (byte)(nextBlock >> 8);
                chunkBuffer[6] = (byte)nextBlock;
                chunkBuffer[7] = (byte)indexId;
                seek(mainRandomAccessFile, firstBlock * 520);
                mainRandomAccessFile.write(chunkBuffer, 0, 8);
                int blockSize = len - archiveOffset;
                if(blockSize > 512)
                    blockSize = 512;
                mainRandomAccessFile.write(abyte0, archiveOffset, blockSize);
                archiveOffset += blockSize;
                firstBlock = nextBlock;
            }
            return true;
        } catch(IOException ex) {
            return false;
        }
    }

    public synchronized void seek(RandomAccessFile randomaccessfile, int pos) throws IOException {
        if(pos < 0 || pos > 0x3c00000) {
            System.out.println("Badseek - pos:" + pos + " len:" + randomaccessfile.length());
            pos = 0x3c00000;
            try {
                Thread.sleep(1000L);
            } catch(Exception ex) { 
            }
        }
        randomaccessfile.seek(pos);
    }

    public static byte chunkBuffer[] = new byte[520];
    public RandomAccessFile mainRandomAccessFile;
    public RandomAccessFile indexRandomAccessFile;
    public int indexId;
    public int maxSize;

}
