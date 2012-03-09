// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

public class IndexedColorSprite extends BasicRasterizer {

    public IndexedColorSprite(ArchivePackage pack, String archiveName, int childId) {
        ByteBuffer mainBuffer = new ByteBuffer(pack.getArchive(archiveName + ".dat", null));
        ByteBuffer indexBuffer = new ByteBuffer(pack.getArchive("index.dat", null));
        indexBuffer.offset = mainBuffer.getUword();
        spriteWidth = indexBuffer.getUword();
        spriteHeight = indexBuffer.getUword();
        int amountColors = indexBuffer.getUbyte();
        colorRef = new int[amountColors];
        for(int i = 0; i < amountColors - 1; i++)
            colorRef[i + 1] = indexBuffer.getTri();
        for(int i = 0; i < childId; i++) {
            indexBuffer.offset += 2;
            mainBuffer.offset += indexBuffer.getUword() * indexBuffer.getUword();
            indexBuffer.offset++;
        }
        offsetX = indexBuffer.getUbyte();
        offsetY = indexBuffer.getUbyte();
        indexWidth = indexBuffer.getUword();
        indexHeight = indexBuffer.getUword();
        int indexType = indexBuffer.getUbyte();
        int bufferSize = indexWidth * indexHeight;
        buffer = new byte[bufferSize];
        if(indexType == 0) {
            for(int k1 = 0; k1 < bufferSize; k1++)
                buffer[k1] = mainBuffer.getByte();
            return;
        }
        if(indexType == 1)
        {
            for(int x = 0; x < indexWidth; x++) {
                for(int y = 0; y < indexHeight; y++)
					/* index width = 5, index height = 10: 0*/
                    buffer[x + (y * indexWidth)] = mainBuffer.getByte();

            }
        }
    }

    public void compressUnpack()
    {
        spriteWidth /= 2;
        spriteHeight /= 2;
        byte byteArray[] = new byte[spriteWidth * spriteHeight];
        int i = 0;
        for(int y = 0; y < indexHeight; y++)
        {
            for(int x = 0; x < indexWidth; x++)
                byteArray[(x + offsetX >> 1) + (y + offsetY >> 1) * spriteWidth] = buffer[i++];
        }
        buffer = byteArray;
        indexWidth = spriteWidth;
        indexHeight = spriteHeight;
        offsetX = 0;      
        offsetY = 0;
    }

    public void unpack()
    {
        if(indexWidth == spriteWidth && indexHeight == spriteHeight)
            return;
        byte pBuffer[] = new byte[spriteWidth * spriteHeight];
        int off = 0;
        for(int y = 0; y < indexHeight; y++)
        {
            for(int x = 0; x < indexWidth; x++)
                pBuffer[x + offsetX + (y + offsetY) * spriteWidth] = buffer[off++];

        }

        buffer = pBuffer;
        indexWidth = spriteWidth;
        indexHeight = spriteHeight;
        offsetX = 0;
        offsetY = 0;
    }

    public void indexReflectX()
    {
        byte pBuffer[] = new byte[indexWidth * indexHeight];
        int off = 0;
        for(int y = 0; y < indexHeight; y++)
        {
            for(int x = indexWidth - 1; x >= 0; x--)
                pBuffer[off++] = buffer[x + y * indexWidth];

        }

        buffer = pBuffer;
        offsetX = spriteWidth - indexWidth - offsetX;
    }

    public void indexReflectY()
    {
        byte abyte0[] = new byte[indexWidth * indexHeight];
        int i = 0;
        for(int j = indexHeight - 1; j >= 0; j--)
        {
            for(int k = 0; k < indexWidth; k++)
                abyte0[i++] = buffer[k + j * indexWidth];

        }

        buffer = abyte0;
        offsetY = spriteHeight - indexHeight - offsetY;
    }

    public void intensify(int i, int j, int k, int l)
    {
        for(int i1 = 0; i1 < colorRef.length; i1++)
        {
            int j1 = colorRef[i1] >> 16 & 0xff;
            j1 += i;
            if(j1 < 0)
                j1 = 0;
            else
            if(j1 > 255)
                j1 = 255;
            int k1 = colorRef[i1] >> 8 & 0xff;
            k1 += j;
            if(k1 < 0)
                k1 = 0;
            else
            if(k1 > 255)
                k1 = 255;
            int l1 = colorRef[i1] & 0xff;
            l1 += k;
            if(l1 < 0)
                l1 = 0;
            else
            if(l1 > 255)
                l1 = 255;
            colorRef[i1] = (j1 << 16) + (k1 << 8) + l1;
        }
    }

    public void renderImage(int i, int j, int k)
    {
        i += offsetX;
        k += offsetY;
        int l = i + k * BasicRasterizer.bufferWidth;
        int i1 = 0;
        if(j != 16083)
            return;
        int j1 = indexHeight;
        int k1 = indexWidth;
        int l1 = BasicRasterizer.bufferWidth - k1;
        int i2 = 0;
        if(k < BasicRasterizer.heightOffset)
        {
            int j2 = BasicRasterizer.heightOffset - k;
            j1 -= j2;
            k = BasicRasterizer.heightOffset;
            i1 += j2 * k1;
            l += j2 * BasicRasterizer.bufferWidth;
        }
        if(k + j1 > BasicRasterizer.height)
            j1 -= (k + j1) - BasicRasterizer.height;
        if(i < BasicRasterizer.widthOffset)
        {
            int k2 = BasicRasterizer.widthOffset - i;
            k1 -= k2;
            i = BasicRasterizer.widthOffset;
            i1 += k2;
            l += k2;
            i2 += k2;
            l1 += k2;
        }
        if(i + k1 > BasicRasterizer.width)
        {
            int l2 = (i + k1) - BasicRasterizer.width;
            k1 -= l2;
            i2 += l2;
            l1 += l2;
        }
        if(k1 <= 0 || j1 <= 0)
        {
            return;
        } else
        {
            render(j1, (byte)9, BasicRasterizer.pixelBuffer, buffer, l1, l, k1, i1, colorRef, i2);
            return;
        }
    }

    public void render(int i, byte byte0, int ai[], byte abyte0[], int j, int k, int l, 
            int i1, int ai1[], int j1)
    {
        if(byte0 != 9)
            aBoolean1447 = !aBoolean1447;
        int k1 = -(l >> 2);
        l = -(l & 3);
        for(int l1 = -i; l1 < 0; l1++)
        {
            for(int i2 = k1; i2 < 0; i2++)
            {
                byte byte1 = abyte0[i1++];
                if(byte1 != 0)
                    ai[k++] = ai1[byte1 & 0xff];
                else
                    k++;
                byte1 = abyte0[i1++];
                if(byte1 != 0)
                    ai[k++] = ai1[byte1 & 0xff];
                else
                    k++;
                byte1 = abyte0[i1++];
                if(byte1 != 0)
                    ai[k++] = ai1[byte1 & 0xff];
                else
                    k++;
                byte1 = abyte0[i1++];
                if(byte1 != 0)
                    ai[k++] = ai1[byte1 & 0xff];
                else
                    k++;
            }

            for(int j2 = l; j2 < 0; j2++)
            {
                byte byte2 = abyte0[i1++];
                if(byte2 != 0)
                    ai[k++] = ai1[byte2 & 0xff];
                else
                    k++;
            }

            k += j;
            i1 += j1;
        }

    }

    public int anInt1446;
    public boolean aBoolean1447;
    public int anInt1448;
    public byte aByte1449;
    public byte buffer[];
    public int colorRef[];
    public int indexWidth;
    public int indexHeight;
    public int offsetX;
    public int offsetY;
    public int spriteWidth;
    public int spriteHeight;
}
