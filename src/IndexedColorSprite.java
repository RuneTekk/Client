// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

import sign.Signlink;

public class IndexedColorSprite extends BasicRasterizer {

    public IndexedColorSprite(ArchivePackage class44, String s, int i) {
        aBoolean1447 = false;
        anInt1448 = 360;
        aByte1449 = 3;
        ByteBuffer main = new ByteBuffer(class44.getArchive(s + ".dat", null));
        ByteBuffer index = new ByteBuffer(class44.getArchive("index.dat", null));
        index.offset = main.getUword();
        width_idk1 = index.getUword();
        height_idk1 = index.getUword();
        int j = index.getUbyte();
        icolors = new int[j];
        for(int k = 0; k < j - 1; k++)
            icolors[k + 1] = (index.getTri() & 0xFFFF00);
        for(int l = 0; l < i; l++)
        {
            index.offset += 2;
            main.offset += index.getUword() * index.getUword();
            index.offset++;
        }
        anInt1454 = index.getUbyte();
        anInt1455 = index.getUbyte();
        indexwidth_ = index.getUword();
        indexheight_ = index.getUword();
        int i1 = index.getUbyte();
        int j1 = indexwidth_ * indexheight_;
        colorindex = new byte[j1];
        if(i1 == 0) {
            for(int k1 = 0; k1 < j1; k1++)
                colorindex[k1] = main.getByte();
            return;
        }
        if(i1 == 1)
        {
            for(int x = 0; x < indexwidth_; x++) {
                for(int y = 0; y < indexheight_; y++)
					/* index width = 5, index height = 10: 0*/
                    colorindex[x + (y * indexwidth_)] = main.getByte();

            }
        }
    }

    public void shrink(boolean flag)
    {
        width_idk1 /= 2;
        height_idk1 /= 2;
        byte abyte0[] = new byte[width_idk1 * height_idk1];
        int i = 0;
        for(int y = 0; y < indexheight_; y++)
        {
            for(int x = 0; x < indexwidth_; x++)
                abyte0[(x + anInt1454 >> 1) + (y + anInt1455 >> 1) * width_idk1] = colorindex[i++];

        }

        colorindex = abyte0;
        indexwidth_ = width_idk1;
        indexheight_ = height_idk1;
        anInt1454 = 0;
        if(flag)
        {
            return;
        } else
        {
            anInt1455 = 0;
            return;
        }
    }

    public void method357(boolean flag)
    {
        if(indexwidth_ == width_idk1 && indexheight_ == height_idk1)
            return;
        byte abyte0[] = new byte[width_idk1 * height_idk1];
        if(flag)
            return;
        int i = 0;
        for(int j = 0; j < indexheight_; j++)
        {
            for(int k = 0; k < indexwidth_; k++)
                abyte0[k + anInt1454 + (j + anInt1455) * width_idk1] = colorindex[i++];

        }

        colorindex = abyte0;
        indexwidth_ = width_idk1;
        indexheight_ = height_idk1;
        anInt1454 = 0;
        anInt1455 = 0;
    }

    public void method358(int i)
    {
        if(i != 0)
            return;
        byte abyte0[] = new byte[indexwidth_ * indexheight_];
        int j = 0;
        for(int k = 0; k < indexheight_; k++)
        {
            for(int l = indexwidth_ - 1; l >= 0; l--)
                abyte0[j++] = colorindex[l + k * indexwidth_];

        }

        colorindex = abyte0;
        anInt1454 = width_idk1 - indexwidth_ - anInt1454;
    }

    public void method359(boolean flag)
    {
        byte abyte0[] = new byte[indexwidth_ * indexheight_];
        int i = 0;
        for(int j = indexheight_ - 1; j >= 0; j--)
        {
            for(int k = 0; k < indexwidth_; k++)
                abyte0[i++] = colorindex[k + j * indexwidth_];

        }

        colorindex = abyte0;
        if(!flag)
            anInt1446 = -48;
        anInt1455 = height_idk1 - indexheight_ - anInt1455;
    }

    public void intensifyColorIndex(int i, int j, int k, int l)
    {
        for(int i1 = 0; i1 < icolors.length; i1++)
        {
            int j1 = icolors[i1] >> 16 & 0xff;
            j1 += i;
            if(j1 < 0)
                j1 = 0;
            else
            if(j1 > 255)
                j1 = 255;
            int k1 = icolors[i1] >> 8 & 0xff;
            k1 += j;
            if(k1 < 0)
                k1 = 0;
            else
            if(k1 > 255)
                k1 = 255;
            int l1 = icolors[i1] & 0xff;
            l1 += k;
            if(l1 < 0)
                l1 = 0;
            else
            if(l1 > 255)
                l1 = 255;
            icolors[i1] = (j1 << 16) + (k1 << 8) + l1;
        }

        if(l != 0)
            anInt1446 = 69;
    }

    public void renderImage(int i, int j, int k)
    {
        i += anInt1454;
        k += anInt1455;
        int l = i + k * BasicRasterizer.bufferWidth;
        int i1 = 0;
        if(j != 16083)
            return;
        int j1 = indexheight_;
        int k1 = indexwidth_;
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
            render(j1, (byte)9, BasicRasterizer.pixelBuffer, colorindex, l1, l, k1, i1, icolors, i2);
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
    public byte colorindex[];
    public int icolors[];
    public int indexwidth_;
    public int indexheight_;
    public int anInt1454;
    public int anInt1455;
    public int width_idk1;
    public int height_idk1;
}
