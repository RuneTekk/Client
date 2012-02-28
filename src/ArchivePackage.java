// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

public final class ArchivePackage {

    public ArchivePackage(byte src[]) {
        initialize(src);
    }

    public void initialize(byte src[]) {
        ByteBuffer buffer = new ByteBuffer(src);
        int uSize = buffer.getTri();
        int cSize = buffer.getTri();
        if(cSize != uSize) {
            byte bytes[] = new byte[uSize];
            Bzip2Decompressor.decompress(bytes, uSize, src, cSize, 6);
            containerData = bytes;
            buffer = new ByteBuffer(containerData);
            compressed = true;
        } else {
            containerData = src;
            compressed = false;
        }
        entries = buffer.getUword();
        nameHashes = new int[entries];
        uSizes = new int[entries];
        cSizes = new int[entries];
        entryOffsets = new int[entries];
        int offset = buffer.offset + entries * 10;
        for(int l = 0; l < entries; l++) {
            nameHashes[l] = buffer.getDword();
            uSizes[l] = buffer.getTri();
            cSizes[l] = buffer.getTri();
            entryOffsets[l] = offset;
            offset += cSizes[l];
        }
    }

    public byte[] getArchive(String s, byte dest[]) {
        int i = 0;
        s = s.toUpperCase();
        for(int j = 0; j < s.length(); j++)
            i = (i * 61 + s.charAt(j)) - 32;
        for(int k = 0; k < entries; k++)
            if(nameHashes[k] == i) {
                if(dest == null)
                    dest = new byte[uSizes[k]];
                if(!compressed) {
                    Bzip2Decompressor.decompress(dest, uSizes[k], containerData, cSizes[k], entryOffsets[k]);
                } else {
                    for(int pos = 0; pos < uSizes[k]; pos++)
                        dest[pos] = containerData[entryOffsets[k] + pos];
                }
                return dest;
            }
        return null;
    }

    public byte containerData[];
    public int entries;
    public int nameHashes[];
    public int uSizes[];
    public int cSizes[];
    public int entryOffsets[];
    public boolean compressed;
}
