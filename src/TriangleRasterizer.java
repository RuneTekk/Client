// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

public class TriangleRasterizer extends BasicRasterizer {

    public static void destroy(int junk) {
        GRADIENTTABLE = null;
        GRADIENTTABLE = null;
        SINE_TABLE = null;
        COSINE_TABLE = null;
        heightoffsets = null;
        textures = null;
        aBooleanArray1475 = null;
        calculatedavr_colors = null;
        texturestack = null;
        texturecache = null;
        unpackcounters = null;
        shading$ = null;
        r3d_colorindexes = null;
    }

    public static void setDimensions() {
        heightoffsets = new int[BasicRasterizer.bufferHeight];
        for(int j = 0; j < BasicRasterizer.bufferHeight; j++)
            heightoffsets[j] = BasicRasterizer.bufferWidth * j;
        centerWidth = BasicRasterizer.bufferWidth / 2;
        centerHeight = BasicRasterizer.bufferHeight / 2;
    }

    public static void setDimensions(int junk, int width, int height) {
	heightoffsets = new int[height];
        for(int h = 0; h < height; h++)
            heightoffsets[h] = width * h;
        centerWidth = width / 2;
        centerHeight = height / 2;
    }

    public static void resetCaches(int junk) {
        texturestack = null;
        for(int j = 0; j < 50; j++)
            texturecache[j] = null;
    }

    public static void initialize(int amounttextures, boolean junk)
    {
        if(texturestack == null) {
            stackpos = amounttextures;
            if(lowmemory)
                texturestack = new int[stackpos][16384];
            else
                texturestack = new int[stackpos][0x10000];
            for(int k = 0; k < 50; k++)
                texturecache[k] = null;
        }
    }

    public static void unpackTextures(ArchivePackage archive)
    {
        anInt1473 = 0;
        for(int j = 0; j < 50; j++)
            try {
                textures[j] = new IndexedColorSprite(archive, String.valueOf(j), 0);
                if(lowmemory && textures[j].spriteWidth == 128)
                    textures[j].compressUnpack();
                else
                    textures[j].unpack();
                anInt1473++;
            } catch(Exception _ex) { 
	}
    }

    public static int getAverageTextureColor(int i, int junk)
    {
        if(calculatedavr_colors[i] != 0)
            return calculatedavr_colors[i];
        int red = 0;
        int green = 0;
        int blue = 0;
        int div = r3d_colorindexes[i].length;
        for(int k1 = 0; k1 < div; k1++) {
            red += r3d_colorindexes[i][k1] >> 16 & 0xff;
            green += r3d_colorindexes[i][k1] >> 8 & 0xff;
            blue += r3d_colorindexes[i][k1] & 0xff;
        }
        int l1 = (red / div << 16) + (green / div << 8) + blue / div;
        l1 = brightenColor(l1, 1.3999999999999999D);
        if(l1 == 0)
            l1 = 1;
        calculatedavr_colors[i] = l1;
        return l1;
    }

    public static void pushTexture(int i)
    {
        if(texturecache[i] == null)
            return;
        texturestack[stackpos++] = texturecache[i];
        texturecache[i] = null;
    }

    public static int[] unpackTexture(int i)
    {
        unpackcounters[i] = unpackcounter++;
        if(texturecache[i] != null)
            return texturecache[i];
        int ai[];
        if(stackpos > 0)
        {
            ai = texturestack[--stackpos];
            texturestack[stackpos] = null;
        } else
        {
            int j = 0;
            int k = -1;
            for(int l = 0; l < anInt1473; l++)
                if(texturecache[l] != null && (unpackcounters[l] < j || k == -1))
                {
                    j = unpackcounters[l];
                    k = l;
                }

            ai = texturecache[k];
            texturecache[k] = null;
        }
        texturecache[i] = ai;
        IndexedColorSprite class30_sub2_sub1_sub2 = textures[i];
        int ai1[] = r3d_colorindexes[i];
        if(lowmemory)
        {
            aBooleanArray1475[i] = false;
            for(int i1 = 0; i1 < 4096; i1++) 
            {
                int i2 = ai[i1] = ai1[class30_sub2_sub1_sub2.buffer[i1]] & 0xf8f8ff;
                if(i2 == 0)
                    aBooleanArray1475[i] = true;
                ai[4096 + i1] = i2 - (i2 >>> 3) & 0xf8f8ff;
                ai[8192 + i1] = i2 - (i2 >>> 2) & 0xf8f8ff;
                ai[12288 + i1] = i2 - (i2 >>> 2) - (i2 >>> 3) & 0xf8f8ff;
            }

        } else
        {
            if(class30_sub2_sub1_sub2.indexWidth == 64)
            {
                for(int j1 = 0; j1 < 128; j1++)
                {
                    for(int j2 = 0; j2 < 128; j2++)
                        ai[j2 + (j1 << 7)] = ai1[class30_sub2_sub1_sub2.buffer[(j2 >> 1) + ((j1 >> 1) << 6)]];

                }

            } else
            {
                for(int k1 = 0; k1 < 16384; k1++)
                    ai[k1] = ai1[class30_sub2_sub1_sub2.buffer[k1]];

            }
            aBooleanArray1475[i] = false;
            for(int l1 = 0; l1 < 16384; l1++)
            {
                /* 11111000/11111000/11111111 */
                ai[l1] &= 0xf8f8ff;
                int k2 = ai[l1];
                if(k2 == 0)
                    aBooleanArray1475[i] = true;
                ai[16384 + l1] = k2 - (k2 >>> 3) & 0xf8f8ff;
                ai[32768 + l1] = k2 - (k2 >>> 2) & 0xf8f8ff;
                ai[49152 + l1] = k2 - (k2 >>> 2) - (k2 >>> 3) & 0xf8f8ff;
            }

        }
        return ai;
    }

    public static void method372(double d, byte byte0) {
        d += Math.random() * 0.029999999999999999D - 0.014999999999999999D;
        int j = 0;
        for(int k = 0; k < 512; k++)
        {
            double d1 = (double)(k / 8) / 64D + 0.0078125D;
            double d2 = (double)(k & 7) / 8D + 0.0625D;
            for(int k1 = 0; k1 < 128; k1++)
            {
                double d3 = (double)k1 / 128D;
                double d4 = d3;
                double d5 = d3;
                double d6 = d3;
                if(d2 != 0.0D)
                {
                    double d7;
                    if(d3 < 0.5D)
                        d7 = d3 * (1.0D + d2);
                    else
                        d7 = (d3 + d2) - d3 * d2;
                    double d8 = 2D * d3 - d7;
                    double d9 = d1 + 0.33333333333333331D;
                    if(d9 > 1.0D)
                        d9--;
                    double d10 = d1;
                    double d11 = d1 - 0.33333333333333331D;
                    if(d11 < 0.0D)
                        d11++;
                    if(6D * d9 < 1.0D)
                        d4 = d8 + (d7 - d8) * 6D * d9;
                    else
                    if(2D * d9 < 1.0D)
                        d4 = d7;
                    else
                    if(3D * d9 < 2D)
                        d4 = d8 + (d7 - d8) * (0.66666666666666663D - d9) * 6D;
                    else
                        d4 = d8;
                    if(6D * d10 < 1.0D)
                        d5 = d8 + (d7 - d8) * 6D * d10;
                    else
                    if(2D * d10 < 1.0D)
                        d5 = d7;
                    else
                    if(3D * d10 < 2D)
                        d5 = d8 + (d7 - d8) * (0.66666666666666663D - d10) * 6D;
                    else
                        d5 = d8;
                    if(6D * d11 < 1.0D)
                        d6 = d8 + (d7 - d8) * 6D * d11;
                    else
                    if(2D * d11 < 1.0D)
                        d6 = d7;
                    else
                    if(3D * d11 < 2D)
                        d6 = d8 + (d7 - d8) * (0.66666666666666663D - d11) * 6D;
                    else
                        d6 = d8;
                }
                int l1 = (int)(d4 * 256D);
                int i2 = (int)(d5 * 256D);
                int j2 = (int)(d6 * 256D);
                int k2 = (l1 << 16) + (i2 << 8) + j2;
                k2 = brightenColor(k2, d);
                if(k2 == 0)
                    k2 = 1;
                shading$[j++] = k2;
            }

        }

        for(int l = 0; l < 50; l++)
            if(textures[l] != null)
            {
                int ai[] = textures[l].colorRef;
                r3d_colorindexes[l] = new int[ai.length];
                for(int j1 = 0; j1 < ai.length; j1++)
                {
                    r3d_colorindexes[l][j1] = brightenColor(ai[j1], d);
                    if((r3d_colorindexes[l][j1] & 0xf8f8ff) == 0 && j1 != 0)
                        r3d_colorindexes[l][j1] = 1;
                }

            }

        for(int i1 = 0; i1 < 50; i1++)
            pushTexture(i1);

    }

    public static int brightenColor(int color, double intensity)
    {
        double d1 = (double)(color >> 16) / 256D;
        double d2 = (double)(color >> 8 & 0xff) / 256D;
        double d3 = (double)(color & 0xff) / 256D;
        d1 = Math.pow(d1, intensity);
        d2 = Math.pow(d2, intensity);
        d3 = Math.pow(d3, intensity);
        int j = (int)(d1 * 256D);
        int k = (int)(d2 * 256D);
        int l = (int)(d3 * 256D);
        return (j << 16) + (k << 8) + l;
    }

    public static void drawShadedTriangle(int x0, int x1, int x2,int y0, int y1, int y2, int s0, int s1, int s2) {
        int prate0 = 0;
        int srate0 = 0;
        if(y1 != y0)
        {
            prate0 = (x0 - x1 << 16) / (y1 - y0);
            srate0 = (s0 - s1 << 15) / (y1 - y0);
        }
        int prate1 = 0;
        int srate1 = 0;
        if(y2 != y1)
        {
            prate1 = (x2 - x0 << 16) / (y2 - y1);
            srate1 = (s2 - s0 << 15) / (y2 - y1);
        }
        int prate2 = 0;
        int srate2 = 0;
        if(y2 != y0)
        {
            prate2 = (x1 - x2 << 16) / (y0 - y2);
            srate2 = (s1 - s2 << 15) / (y0 - y2);
        }
        if(y0 <= y1 && y0 <= y2)
        {
            if(y0 >= BasicRasterizer.height)
                return;
            if(y1 > BasicRasterizer.height)
                y1 = BasicRasterizer.height;
            if(y2 > BasicRasterizer.height)
                y2 = BasicRasterizer.height;
            if(y1 < y2)
            {
                x2 = x1 <<= 16;
                s2 = s1 <<= 15;
                if(y0 < 0)
                {
                    x2 -= prate2 * y0;
                    x1 -= prate0 * y0;
                    s2 -= srate2 * y0;
                    s1 -= srate0 * y0;
                    y0 = 0;
                }
                x0 <<= 16;
                s0 <<= 15;
                if(y1 < 0)
                {
                    x0 -= prate1 * y1;
                    s0 -= srate1 * y1;
                    y1 = 0;
                }
                if(y0 != y1 && prate2 < prate0 || y0 == y1 && prate2 > prate1)
                {
                    y2 -= y1;
                    y1 -= y0;
                    for(y0 = heightoffsets[y0]; --y1 >= 0; y0 += BasicRasterizer.bufferWidth)
                    {
                        drawShadedLine(BasicRasterizer.pixelBuffer, x2 >> 16, x1 >> 16, y0, 0, 0, s2 >> 7, s1 >> 7);
                        x2 += prate2;
                        x1 += prate0;
                        s2 += srate2;
                        s1 += srate0;
                    }

                    while(--y2 >= 0)
                    {
                        drawShadedLine(BasicRasterizer.pixelBuffer, x2 >> 16, x0 >> 16, y0, 0, 0, s2 >> 7, s0 >> 7);
                        x2 += prate2;
                        x0 += prate1;
                        s2 += srate2;
                        s0 += srate1;
                        y0 += BasicRasterizer.bufferWidth;
                    }
                    return;
                }
                y2 -= y1;
                y1 -= y0;
                for(y0 = heightoffsets[y0]; --y1 >= 0; y0 += BasicRasterizer.bufferWidth)
                {
                    drawShadedLine(BasicRasterizer.pixelBuffer, x1 >> 16, x2 >> 16, y0, 0, 0, s1 >> 7, s2 >> 7);
                    x2 += prate2;
                    x1 += prate0;
                    s2 += srate2;
                    s1 += srate0;
                }

                while(--y2 >= 0)
                {
                    drawShadedLine(BasicRasterizer.pixelBuffer, x0 >> 16, x2 >> 16, y0, 0, 0, s0 >> 7, s2 >> 7);
                    x2 += prate2;
                    x0 += prate1;
                    s2 += srate2;
                    s0 += srate1;
                    y0 += BasicRasterizer.bufferWidth;
                }
                return;
            }
            x0 = x1 <<= 16;
            s0 = s1 <<= 15;
            if(y0 < 0)
            {
                x0 -= prate2 * y0;
                x1 -= prate0 * y0;
                s0 -= srate2 * y0;
                s1 -= srate0 * y0;
                y0 = 0;
            }
            x2 <<= 16;
            s2 <<= 15;
            if(y2 < 0)
            {
                x2 -= prate1 * y2;
                s2 -= srate1 * y2;
                y2 = 0;
            }
            if(y0 != y2 && prate2 < prate0 || y0 == y2 && prate1 > prate0)
            {
                y1 -= y2;
                y2 -= y0;
                for(y0 = heightoffsets[y0]; --y2 >= 0; y0 += BasicRasterizer.bufferWidth)
                {
                    drawShadedLine(BasicRasterizer.pixelBuffer, x0 >> 16, x1 >> 16, y0, 0, 0, s0 >> 7, s1 >> 7);
                    x0 += prate2;
                    x1 += prate0;
                    s0 += srate2;
                    s1 += srate0;
                }

                while(--y1 >= 0)
                {
                    drawShadedLine(BasicRasterizer.pixelBuffer, x2 >> 16, x1 >> 16, y0, 0, 0, s2 >> 7, s1 >> 7);
                    x2 += prate1;
                    x1 += prate0;
                    s2 += srate1;
                    s1 += srate0;
                    y0 += BasicRasterizer.bufferWidth;
                }
                return;
            }
            y1 -= y2;
            y2 -= y0;
            for(y0 = heightoffsets[y0]; --y2 >= 0; y0 += BasicRasterizer.bufferWidth)
            {
                drawShadedLine(BasicRasterizer.pixelBuffer, x1 >> 16, x0 >> 16, y0, 0, 0, s1 >> 7, s0 >> 7);
                x0 += prate2;
                x1 += prate0;
                s0 += srate2;
                s1 += srate0;
            }

            while(--y1 >= 0)
            {
                drawShadedLine(BasicRasterizer.pixelBuffer, x1 >> 16, x2 >> 16, y0, 0, 0, s1 >> 7, s2 >> 7);
                x2 += prate1;
                x1 += prate0;
                s2 += srate1;
                s1 += srate0;
                y0 += BasicRasterizer.bufferWidth;
            }
            return;
        }
        if(y1 <= y2)
        {
            if(y1 >= BasicRasterizer.height)
                return;
            if(y2 > BasicRasterizer.height)
                y2 = BasicRasterizer.height;
            if(y0 > BasicRasterizer.height)
                y0 = BasicRasterizer.height;
            if(y2 < y0)
            {
                x1 = x0 <<= 16;
                s1 = s0 <<= 15;
                if(y1 < 0)
                {
                    x1 -= prate0 * y1;
                    x0 -= prate1 * y1;
                    s1 -= srate0 * y1;
                    s0 -= srate1 * y1;
                    y1 = 0;
                }
                x2 <<= 16;
                s2 <<= 15;
                if(y2 < 0)
                {
                    x2 -= prate2 * y2;
                    s2 -= srate2 * y2;
                    y2 = 0;
                }
                if(y1 != y2 && prate0 < prate1 || y1 == y2 && prate0 > prate2)
                {
                    y0 -= y2;
                    y2 -= y1;
                    for(y1 = heightoffsets[y1]; --y2 >= 0; y1 += BasicRasterizer.bufferWidth)
                    {
                        drawShadedLine(BasicRasterizer.pixelBuffer, x1 >> 16, x0 >> 16, y1, 0, 0, s1 >> 7, s0 >> 7);
                        x1 += prate0;
                        x0 += prate1;
                        s1 += srate0;
                        s0 += srate1;
                    }

                    while(--y0 >= 0)
                    {
                        drawShadedLine(BasicRasterizer.pixelBuffer, x1 >> 16, x2 >> 16, y1, 0, 0, s1 >> 7, s2 >> 7);
                        x1 += prate0;
                        x2 += prate2;
                        s1 += srate0;
                        s2 += srate2;
                        y1 += BasicRasterizer.bufferWidth;
                    }
                    return;
                }
                y0 -= y2;
                y2 -= y1;
                for(y1 = heightoffsets[y1]; --y2 >= 0; y1 += BasicRasterizer.bufferWidth)
                {
                    drawShadedLine(BasicRasterizer.pixelBuffer, x0 >> 16, x1 >> 16, y1, 0, 0, s0 >> 7, s1 >> 7);
                    x1 += prate0;
                    x0 += prate1;
                    s1 += srate0;
                    s0 += srate1;
                }

                while(--y0 >= 0)
                {
                    drawShadedLine(BasicRasterizer.pixelBuffer, x2 >> 16, x1 >> 16, y1, 0, 0, s2 >> 7, s1 >> 7);
                    x1 += prate0;
                    x2 += prate2;
                    s1 += srate0;
                    s2 += srate2;
                    y1 += BasicRasterizer.bufferWidth;
                }
                return;
            }
            x2 = x0 <<= 16;
            s2 = s0 <<= 15;
            if(y1 < 0)
            {
                x2 -= prate0 * y1;
                x0 -= prate1 * y1;
                s2 -= srate0 * y1;
                s0 -= srate1 * y1;
                y1 = 0;
            }
            x1 <<= 16;
            s1 <<= 15;
            if(y0 < 0)
            {
                x1 -= prate2 * y0;
                s1 -= srate2 * y0;
                y0 = 0;
            }
            if(prate0 < prate1)
            {
                y2 -= y0;
                y0 -= y1;
                for(y1 = heightoffsets[y1]; --y0 >= 0; y1 += BasicRasterizer.bufferWidth)
                {
                    drawShadedLine(BasicRasterizer.pixelBuffer, x2 >> 16, x0 >> 16, y1, 0, 0, s2 >> 7, s0 >> 7);
                    x2 += prate0;
                    x0 += prate1;
                    s2 += srate0;
                    s0 += srate1;
                }

                while(--y2 >= 0)
                {
                    drawShadedLine(BasicRasterizer.pixelBuffer, x1 >> 16, x0 >> 16, y1, 0, 0, s1 >> 7, s0 >> 7);
                    x1 += prate2;
                    x0 += prate1;
                    s1 += srate2;
                    s0 += srate1;
                    y1 += BasicRasterizer.bufferWidth;
                }
                return;
            }
            y2 -= y0;
            y0 -= y1;
            for(y1 = heightoffsets[y1]; --y0 >= 0; y1 += BasicRasterizer.bufferWidth)
            {
                drawShadedLine(BasicRasterizer.pixelBuffer, x0 >> 16, x2 >> 16, y1, 0, 0, s0 >> 7, s2 >> 7);
                x2 += prate0;
                x0 += prate1;
                s2 += srate0;
                s0 += srate1;
            }

            while(--y2 >= 0)
            {
                drawShadedLine(BasicRasterizer.pixelBuffer, x0 >> 16, x1 >> 16, y1, 0, 0, s0 >> 7, s1 >> 7);
                x1 += prate2;
                x0 += prate1;
                s1 += srate2;
                s0 += srate1;
                y1 += BasicRasterizer.bufferWidth;
            }
            return;
        }
        if(y2 >= BasicRasterizer.height)
            return;
        if(y0 > BasicRasterizer.height)
            y0 = BasicRasterizer.height;
        if(y1 > BasicRasterizer.height)
            y1 = BasicRasterizer.height;
        if(y0 < y1)
        {
            x0 = x2 <<= 16;
            s0 = s2 <<= 15;
            if(y2 < 0)
            {
                x0 -= prate1 * y2;
                x2 -= prate2 * y2;
                s0 -= srate1 * y2;
                s2 -= srate2 * y2;
                y2 = 0;
            }
            x1 <<= 16;
            s1 <<= 15;
            if(y0 < 0)
            {
                x1 -= prate0 * y0;
                s1 -= srate0 * y0;
                y0 = 0;
            }
            if(prate1 < prate2)
            {
                y1 -= y0;
                y0 -= y2;
                for(y2 = heightoffsets[y2]; --y0 >= 0; y2 += BasicRasterizer.bufferWidth)
                {
                    drawShadedLine(BasicRasterizer.pixelBuffer, x0 >> 16, x2 >> 16, y2, 0, 0, s0 >> 7, s2 >> 7);
                    x0 += prate1;
                    x2 += prate2;
                    s0 += srate1;
                    s2 += srate2;
                }

                while(--y1 >= 0)
                {
                    drawShadedLine(BasicRasterizer.pixelBuffer, x0 >> 16, x1 >> 16, y2, 0, 0, s0 >> 7, s1 >> 7);
                    x0 += prate1;
                    x1 += prate0;
                    s0 += srate1;
                    s1 += srate0;
                    y2 += BasicRasterizer.bufferWidth;
                }
                return;
            }
            y1 -= y0;
            y0 -= y2;
            for(y2 = heightoffsets[y2]; --y0 >= 0; y2 += BasicRasterizer.bufferWidth)
            {
                drawShadedLine(BasicRasterizer.pixelBuffer, x2 >> 16, x0 >> 16, y2, 0, 0, s2 >> 7, s0 >> 7);
                x0 += prate1;
                x2 += prate2;
                s0 += srate1;
                s2 += srate2;
            }

            while(--y1 >= 0)
            {
                drawShadedLine(BasicRasterizer.pixelBuffer, x1 >> 16, x0 >> 16, y2, 0, 0, s1 >> 7, s0 >> 7);
                x0 += prate1;
                x1 += prate0;
                s0 += srate1;
                s1 += srate0;
                y2 += BasicRasterizer.bufferWidth;
            }
            return;
        }
        x1 = x2 <<= 16;
        s1 = s2 <<= 15;
        if(y2 < 0)
        {
            x1 -= prate1 * y2;
            x2 -= prate2 * y2;
            s1 -= srate1 * y2;
            s2 -= srate2 * y2;
            y2 = 0;
        }
        x0 <<= 16;
        s0 <<= 15;
        if(y1 < 0)
        {
            x0 -= prate0 * y1;
            s0 -= srate0 * y1;
            y1 = 0;
        }
        if(prate1 < prate2)
        {
            y0 -= y1;
            y1 -= y2;
            for(y2 = heightoffsets[y2]; --y1 >= 0; y2 += BasicRasterizer.bufferWidth)
            {
                drawShadedLine(BasicRasterizer.pixelBuffer, x1 >> 16, x2 >> 16, y2, 0, 0, s1 >> 7, s2 >> 7);
                x1 += prate1;
                x2 += prate2;
                s1 += srate1;
                s2 += srate2;
            }

            while(--y0 >= 0)
            {
                drawShadedLine(BasicRasterizer.pixelBuffer, x0 >> 16, x2 >> 16, y2, 0, 0, s0 >> 7, s2 >> 7);
                x0 += prate0;
                x2 += prate2;
                s0 += srate0;
                s2 += srate2;
                y2 += BasicRasterizer.bufferWidth;
            }
            return;
        }
        y0 -= y1;
        y1 -= y2;
        for(y2 = heightoffsets[y2]; --y1 >= 0; y2 += BasicRasterizer.bufferWidth)
        {
            drawShadedLine(BasicRasterizer.pixelBuffer, x2 >> 16, x1 >> 16, y2, 0, 0, s2 >> 7, s1 >> 7);
            x1 += prate1;
            x2 += prate2;
            s1 += srate1;
            s2 += srate2;
        }

        while(--y0 >= 0)
        {
            drawShadedLine(BasicRasterizer.pixelBuffer, x2 >> 16, x0 >> 16, y2, 0, 0, s2 >> 7, s0 >> 7);
            x0 += prate0;
            x2 += prate2;
            s0 += srate0;
            s2 += srate2;
            y2 += BasicRasterizer.bufferWidth;
        }
    }

    public static void drawShadedLine(int out[], int x, int xmax, int offset, int loops, int color, int shade, int shademax)
    {
        if(aBoolean1464)
        {
            int increment;
            if(clip)
            {
                if(xmax - x > 3)
                    increment = (shademax - shade) / (xmax - x);
                else
                    increment = 0;
                if(xmax > BasicRasterizer.widthLength)
                    xmax = BasicRasterizer.widthLength;
                if(x < 0)
                {
                    shade -= x * increment;
                    x = 0;
                }
                if(x >= xmax)
                    return;
                offset += x;
                loops = xmax - x >> 2;
                increment <<= 2;
            } else
            {
                if(x >= xmax)
                    return;
                offset += x;
                loops = xmax - x >> 2;
                if(loops > 0)
                    increment = (shademax - shade) * GRADIENTTABLE[loops] >> 15;
                else
                    increment = 0;
            }
            if(alpha$ == 0)
            {
                while(--loops >= 0)
                {
                    color = shading$[shade >> 8];
                    shade += increment;
                    out[offset++] = color;
                    out[offset++] = color;
                    out[offset++] = color;
                    out[offset++] = color;
                }
                loops = xmax - x & 3;
                if(loops > 0)
                {
                    color = shading$[shade >> 8];
                    do
                        out[offset++] = color;
                    while(--loops > 0);
                    return;
                }
            } else
            {
                int outalpha = alpha$;
                int alpha = 256 - alpha$;
                while(--loops >= 0)
                {
                    color = shading$[shade >> 8];
                    shade += increment;
                    color = ((color & 0xff00ff) * alpha >> 8 & 0xff00ff) + ((color & 0xff00) * alpha >> 8 & 0xff00);
                    out[offset++] = color + ((out[offset] & 0xff00ff) * outalpha >> 8 & 0xff00ff) + ((out[offset] & 0xff00) * outalpha >> 8 & 0xff00);
                    out[offset++] = color + ((out[offset] & 0xff00ff) * outalpha >> 8 & 0xff00ff) + ((out[offset] & 0xff00) * outalpha >> 8 & 0xff00);
                    out[offset++] = color + ((out[offset] & 0xff00ff) * outalpha >> 8 & 0xff00ff) + ((out[offset] & 0xff00) * outalpha >> 8 & 0xff00);
                    out[offset++] = color + ((out[offset] & 0xff00ff) * outalpha >> 8 & 0xff00ff) + ((out[offset] & 0xff00) * outalpha >> 8 & 0xff00);
                }
                loops = xmax - x & 3;
                if(loops > 0)
                {
                    color = shading$[shade >> 8];
                    color = ((color & 0xff00ff) * alpha >> 8 & 0xff00ff) + ((color & 0xff00) * alpha >> 8 & 0xff00);
                    do
                        out[offset++] = color + ((out[offset] & 0xff00ff) * outalpha >> 8 & 0xff00ff) + ((out[offset] & 0xff00) * outalpha >> 8 & 0xff00);
                    while(--loops > 0);
                }
            }
            return;
        }
        if(x >= xmax)
            return;
        int increment = (shademax - shade) / (xmax - x);
        if(clip)
        {
            if(xmax > BasicRasterizer.widthLength)
                xmax = BasicRasterizer.widthLength;
            if(x < 0)
            {
                shade -= x * increment;
                x = 0;
            }
            if(x >= xmax)
                return;
        }
        offset += x;
        loops = xmax - x;
        if(alpha$ == 0)
        {
            do
            {
                out[offset++] = shading$[shade >> 8];
                shade += increment;
            } while(--loops > 0);
            return;
        }
        int outalpha = alpha$;
        int alpha = 256 - alpha$;
        do
        {
            color = shading$[shade >> 8];
            shade += increment;
            color = ((color & 0xff00ff) * alpha >> 8 & 0xff00ff) + ((color & 0xff00) * alpha >> 8 & 0xff00);
            out[offset++] = color + ((out[offset] & 0xff00ff) * outalpha >> 8 & 0xff00ff) + ((out[offset] & 0xff00) * outalpha >> 8 & 0xff00);
        } while(--loops > 0);
    }

    public static void drawSimpleTriangle(int x0, int x1, int x2, int y0, int y1, int y2, int color)
    {
        int rate0 = 0;
        if(y0 != y1)
            rate0 = (x0 - x1 << 16) / (y0 - y1);
        int rate1 = 0;
        if(y2 != y0)
            rate1 = (x2 - x0 << 16) / (y2 - y0);
        int rate3 = 0;
        if(y2 != y1)
            rate3 = (x1 - x2 << 16) / (y1 - y2);
        if(y1 <= y0 && y1 <= y2)
        {
            if(y1 >= BasicRasterizer.height)
                return;
            if(y0 > BasicRasterizer.height)
                y0 = BasicRasterizer.height;
            if(y2 > BasicRasterizer.height)
                y2 = BasicRasterizer.height;
            if(y0 < y2)
            {
                x2 = x1 <<= 16;
                if(y1 < 0)
                {
                    x2 -= rate3 * y1;
                    x1 -= rate0 * y1;
                    y1 = 0;
                }
                x0 <<= 16;
                if(y0 < 0)
                {
                    x0 -= rate1 * y0;
                    y0 = 0;
                }
                if(y1 != y0 && rate3 < rate0 || y1 == y0 && rate3 > rate1)
                {
                    y2 -= y0;
                    y0 -= y1;
                    for(y1 = heightoffsets[y1]; --y0 >= 0; y1 += BasicRasterizer.bufferWidth)
                    {
                        drawBasicLine(BasicRasterizer.pixelBuffer, y1, x2 >> 16, x1 >> 16, 0, color);
                        x2 += rate3;
                        x1 += rate0;
                    }

                    while(--y2 >= 0)
                    {
                        drawBasicLine(BasicRasterizer.pixelBuffer, y1, x2 >> 16, x0 >> 16, 0, color);
                        x2 += rate3;
                        x0 += rate1;
                        y1 += BasicRasterizer.bufferWidth;
                    }
                    return;
                }
                y2 -= y0;
                y0 -= y1;
                for(y1 = heightoffsets[y1]; --y0 >= 0; y1 += BasicRasterizer.bufferWidth)
                {
                    drawBasicLine(BasicRasterizer.pixelBuffer, y1, x1 >> 16, x2 >> 16, 0, color);
                    x2 += rate3;
                    x1 += rate0;
                }

                while(--y2 >= 0)
                {
                    drawBasicLine(BasicRasterizer.pixelBuffer, y1, x0 >> 16, x2 >> 16, 0, color);
                    x2 += rate3;
                    x0 += rate1;
                    y1 += BasicRasterizer.bufferWidth;
                }
                return;
            }
            x0 = x1 <<= 16;
            if(y1 < 0)
            {
                x0 -= rate3 * y1;
                x1 -= rate0 * y1;
                y1 = 0;
            }
            x2 <<= 16;
            if(y2 < 0)
            {
                x2 -= rate1 * y2;
                y2 = 0;
            }
            if(y1 != y2 && rate3 < rate0 || y1 == y2 && rate1 > rate0)
            {
                y0 -= y2;
                y2 -= y1;
                for(y1 = heightoffsets[y1]; --y2 >= 0; y1 += BasicRasterizer.bufferWidth)
                {
                    drawBasicLine(BasicRasterizer.pixelBuffer, y1, x0 >> 16, x1 >> 16, 0, color);
                    x0 += rate3;
                    x1 += rate0;
                }

                while(--y0 >= 0)
                {
                    drawBasicLine(BasicRasterizer.pixelBuffer, y1, x2 >> 16, x1 >> 16, 0, color);
                    x2 += rate1;
                    x1 += rate0;
                    y1 += BasicRasterizer.bufferWidth;
                }
                return;
            }
            y0 -= y2;
            y2 -= y1;
            for(y1 = heightoffsets[y1]; --y2 >= 0; y1 += BasicRasterizer.bufferWidth)
            {
                drawBasicLine(BasicRasterizer.pixelBuffer, y1, x1 >> 16, x0 >> 16, 0, color);
                x0 += rate3;
                x1 += rate0;
            }

            while(--y0 >= 0)
            {
                drawBasicLine(BasicRasterizer.pixelBuffer, y1, x1 >> 16, x2 >> 16, 0, color);
                x2 += rate1;
                x1 += rate0;
                y1 += BasicRasterizer.bufferWidth;
            }
            return;
        }
        if(y0 <= y2)
        {
            if(y0 >= BasicRasterizer.height)
                return;
            if(y2 > BasicRasterizer.height)
                y2 = BasicRasterizer.height;
            if(y1 > BasicRasterizer.height)
                y1 = BasicRasterizer.height;
            if(y2 < y1)
            {
                x1 = x0 <<= 16;
                if(y0 < 0)
                {
                    x1 -= rate0 * y0;
                    x0 -= rate1 * y0;
                    y0 = 0;
                }
                x2 <<= 16;
                if(y2 < 0)
                {
                    x2 -= rate3 * y2;
                    y2 = 0;
                }
                if(y0 != y2 && rate0 < rate1 || y0 == y2 && rate0 > rate3)
                {
                    y1 -= y2;
                    y2 -= y0;
                    for(y0 = heightoffsets[y0]; --y2 >= 0; y0 += BasicRasterizer.bufferWidth)
                    {
                        drawBasicLine(BasicRasterizer.pixelBuffer, y0, x1 >> 16, x0 >> 16, 0, color);
                        x1 += rate0;
                        x0 += rate1;
                    }

                    while(--y1 >= 0)
                    {
                        drawBasicLine(BasicRasterizer.pixelBuffer, y0, x1 >> 16, x2 >> 16, 0, color);
                        x1 += rate0;
                        x2 += rate3;
                        y0 += BasicRasterizer.bufferWidth;
                    }
                    return;
                }
                y1 -= y2;
                y2 -= y0;
                for(y0 = heightoffsets[y0]; --y2 >= 0; y0 += BasicRasterizer.bufferWidth)
                {
                    drawBasicLine(BasicRasterizer.pixelBuffer, y0, x0 >> 16, x1 >> 16, 0, color);
                    x1 += rate0;
                    x0 += rate1;
                }

                while(--y1 >= 0)
                {
                    drawBasicLine(BasicRasterizer.pixelBuffer, y0, x2 >> 16, x1 >> 16, 0, color);
                    x1 += rate0;
                    x2 += rate3;
                    y0 += BasicRasterizer.bufferWidth;
                }
                return;
            }
            x2 = x0 <<= 16;
            if(y0 < 0)
            {
                x2 -= rate0 * y0;
                x0 -= rate1 * y0;
                y0 = 0;
            }
            x1 <<= 16;
            if(y1 < 0)
            {
                x1 -= rate3 * y1;
                y1 = 0;
            }
            if(rate0 < rate1)
            {
                y2 -= y1;
                y1 -= y0;
                for(y0 = heightoffsets[y0]; --y1 >= 0; y0 += BasicRasterizer.bufferWidth)
                {
                    drawBasicLine(BasicRasterizer.pixelBuffer, y0, x2 >> 16, x0 >> 16, 0, color);
                    x2 += rate0;
                    x0 += rate1;
                }

                while(--y2 >= 0)
                {
                    drawBasicLine(BasicRasterizer.pixelBuffer, y0, x1 >> 16, x0 >> 16, 0, color);
                    x1 += rate3;
                    x0 += rate1;
                    y0 += BasicRasterizer.bufferWidth;
                }
                return;
            }
            y2 -= y1;
            y1 -= y0;
            for(y0 = heightoffsets[y0]; --y1 >= 0; y0 += BasicRasterizer.bufferWidth)
            {
                drawBasicLine(BasicRasterizer.pixelBuffer, y0, x0 >> 16, x2 >> 16, 0, color);
                x2 += rate0;
                x0 += rate1;
            }

            while(--y2 >= 0)
            {
                drawBasicLine(BasicRasterizer.pixelBuffer, y0, x0 >> 16, x1 >> 16, 0, color);
                x1 += rate3;
                x0 += rate1;
                y0 += BasicRasterizer.bufferWidth;
            }
            return;
        }
        if(y2 >= BasicRasterizer.height)
            return;
        if(y1 > BasicRasterizer.height)
            y1 = BasicRasterizer.height;
        if(y0 > BasicRasterizer.height)
            y0 = BasicRasterizer.height;
        if(y1 < y0)
        {
            x0 = x2 <<= 16;
            if(y2 < 0)
            {
                x0 -= rate1 * y2;
                x2 -= rate3 * y2;
                y2 = 0;
            }
            x1 <<= 16;
            if(y1 < 0)
            {
                x1 -= rate0 * y1;
                y1 = 0;
            }
            if(rate1 < rate3)
            {
                y0 -= y1;
                y1 -= y2;
                for(y2 = heightoffsets[y2]; --y1 >= 0; y2 += BasicRasterizer.bufferWidth)
                {
                    drawBasicLine(BasicRasterizer.pixelBuffer, y2, x0 >> 16, x2 >> 16, 0, color);
                    x0 += rate1;
                    x2 += rate3;
                }

                while(--y0 >= 0)
                {
                    drawBasicLine(BasicRasterizer.pixelBuffer, y2, x0 >> 16, x1 >> 16, 0, color);
                    x0 += rate1;
                    x1 += rate0;
                    y2 += BasicRasterizer.bufferWidth;
                }
                return;
            }
            y0 -= y1;
            y1 -= y2;
            for(y2 = heightoffsets[y2]; --y1 >= 0; y2 += BasicRasterizer.bufferWidth)
            {
                drawBasicLine(BasicRasterizer.pixelBuffer, y2, x2 >> 16, x0 >> 16, 0, color);
                x0 += rate1;
                x2 += rate3;
            }

            while(--y0 >= 0)
            {
                drawBasicLine(BasicRasterizer.pixelBuffer, y2, x1 >> 16, x0 >> 16, 0, color);
                x0 += rate1;
                x1 += rate0;
                y2 += BasicRasterizer.bufferWidth;
            }
            return;
        }
        x1 = x2 <<= 16;
        if(y2 < 0)
        {
            x1 -= rate1 * y2;
            x2 -= rate3 * y2;
            y2 = 0;
        }
        x0 <<= 16;
        if(y0 < 0)
        {
            x0 -= rate0 * y0;
            y0 = 0;
        }
        if(rate1 < rate3)
        {
            y1 -= y0;
            y0 -= y2;
            for(y2 = heightoffsets[y2]; --y0 >= 0; y2 += BasicRasterizer.bufferWidth)
            {
                drawBasicLine(BasicRasterizer.pixelBuffer, y2, x1 >> 16, x2 >> 16, 0, color);
                x1 += rate1;
                x2 += rate3;
            }

            while(--y1 >= 0)
            {
                drawBasicLine(BasicRasterizer.pixelBuffer, y2, x0 >> 16, x2 >> 16, 0, color);
                x0 += rate0;
                x2 += rate3;
                y2 += BasicRasterizer.bufferWidth;
            }
            return;
        }
        y1 -= y0;
        y0 -= y2;
        for(y2 = heightoffsets[y2]; --y0 >= 0; y2 += BasicRasterizer.bufferWidth)
        {
            drawBasicLine(BasicRasterizer.pixelBuffer, y2, x2 >> 16, x1 >> 16, 0, color);
            x1 += rate1;
            x2 += rate3;
        }

        while(--y1 >= 0)
        {
            drawBasicLine(BasicRasterizer.pixelBuffer, y2, x2 >> 16, x0 >> 16, 0, color);
            x0 += rate0;
            x2 += rate3;
            y2 += BasicRasterizer.bufferWidth;
        }
    }

    public static void drawBasicLine(int output[], int offset, int x, int xmax, int loops, int color)
    {
        if(clip)
        {
            if(xmax > BasicRasterizer.widthLength)
                xmax = BasicRasterizer.widthLength;
            if(x < 0)
                x = 0;
        }
        if(x >= xmax)
            return;
        offset += x;
        loops = xmax - x >> 2;
        if(alpha$ == 0)
        {
            while(--loops >= 0)
            {
                output[offset++] = color;
                output[offset++] = color;
                output[offset++] = color;
                output[offset++] = color;
            }
            for(loops = xmax - x & 3; --loops >= 0;)
                output[offset++] = color;
            return;
        }
        int outalpha = alpha$;
        int alpha = 256 - alpha$;
        color = ((color & 0xff00ff) * alpha >> 8 & 0xff00ff) + ((color & 0xff00) * alpha >> 8 & 0xff00);
        while(--loops >= 0)
        {
            output[offset++] = color + ((output[offset] & 0xff00ff) * outalpha >> 8 & 0xff00ff) + ((output[offset] & 0xff00) * outalpha >> 8 & 0xff00);
            output[offset++] = color + ((output[offset] & 0xff00ff) * outalpha >> 8 & 0xff00ff) + ((output[offset] & 0xff00) * outalpha >> 8 & 0xff00);
            output[offset++] = color + ((output[offset] & 0xff00ff) * outalpha >> 8 & 0xff00ff) + ((output[offset] & 0xff00) * outalpha >> 8 & 0xff00);
            output[offset++] = color + ((output[offset] & 0xff00ff) * outalpha >> 8 & 0xff00ff) + ((output[offset] & 0xff00) * outalpha >> 8 & 0xff00);
        }
        for(loops = xmax - x & 3; --loops >= 0;)
            output[offset++] = color + ((output[offset] & 0xff00ff) * outalpha >> 8 & 0xff00ff) + ((output[offset] & 0xff00) * outalpha >> 8 & 0xff00);
    }

    public static void drawTexturedTriangle(int i, int j, int k, int l, int i1, int j1, int k1, int l1,
            int i2, int j2, int k2, int l2, int i3, int j3, int k3, 
            int l3, int i4, int j4, int id)
    {
        int ai[] = unpackTexture(id);
        aBoolean1463 = !aBooleanArray1475[id];
        k2 = j2 - k2;
        j3 = i3 - j3;
        i4 = l3 - i4;
        l2 -= j2;
        k3 -= i3;
        j4 -= l3;
        int l4 = l2 * i3 - k3 * j2 << 14;
        int i5 = k3 * l3 - j4 * i3 << 8;
        int j5 = j4 * j2 - l2 * l3 << 5;
        int k5 = k2 * i3 - j3 * j2 << 14;
        int l5 = j3 * l3 - i4 * i3 << 8;
        int i6 = i4 * j2 - k2 * l3 << 5;
        int j6 = j3 * l2 - k2 * k3 << 14;
        int k6 = i4 * k3 - j3 * j4 << 8;
        int l6 = k2 * j4 - i4 * l2 << 5;
        int i7 = 0;
        int j7 = 0;
        if(j != i)
        {
            i7 = (i1 - l << 16) / (j - i);
            j7 = (l1 - k1 << 16) / (j - i);
        }
        int k7 = 0;
        int l7 = 0;
        if(k != j)
        {
            k7 = (j1 - i1 << 16) / (k - j);
            l7 = (i2 - l1 << 16) / (k - j);
        }
        int i8 = 0;
        int j8 = 0;
        if(k != i)
        {
            i8 = (l - j1 << 16) / (i - k);
            j8 = (k1 - i2 << 16) / (i - k);
        }
        if(i <= j && i <= k)
        {
            if(i >= BasicRasterizer.height)
                return;
            if(j > BasicRasterizer.height)
                j = BasicRasterizer.height;
            if(k > BasicRasterizer.height)
                k = BasicRasterizer.height;
            if(j < k)
            {
                j1 = l <<= 16;
                i2 = k1 <<= 16;
                if(i < 0)
                {
                    j1 -= i8 * i;
                    l -= i7 * i;
                    i2 -= j8 * i;
                    k1 -= j7 * i;
                    i = 0;
                }
                i1 <<= 16;
                l1 <<= 16;
                if(j < 0)
                {
                    i1 -= k7 * j;
                    l1 -= l7 * j;
                    j = 0;
                }
                int k8 = i - centerHeight;
                l4 += j5 * k8;
                k5 += i6 * k8;
                j6 += l6 * k8;
                if(i != j && i8 < i7 || i == j && i8 > k7)
                {
                    k -= j;
                    j -= i;
                    i = heightoffsets[i];
                    while(--j >= 0) 
                    {
                        drawTexturedLine(BasicRasterizer.pixelBuffer, ai, 0, 0, i, j1 >> 16, l >> 16, i2 >> 8, k1 >> 8, l4, k5, j6, i5, l5, k6);
                        j1 += i8;
                        l += i7;
                        i2 += j8;
                        k1 += j7;
                        i += BasicRasterizer.bufferWidth;
                        l4 += j5;
                        k5 += i6;
                        j6 += l6;
                    }
                    while(--k >= 0) 
                    {
                        drawTexturedLine(BasicRasterizer.pixelBuffer, ai, 0, 0, i, j1 >> 16, i1 >> 16, i2 >> 8, l1 >> 8, l4, k5, j6, i5, l5, k6);
                        j1 += i8;
                        i1 += k7;
                        i2 += j8;
                        l1 += l7;
                        i += BasicRasterizer.bufferWidth;
                        l4 += j5;
                        k5 += i6;
                        j6 += l6;
                    }
                    return;
                }
                k -= j;
                j -= i;
                i = heightoffsets[i];
                while(--j >= 0) 
                {
                    drawTexturedLine(BasicRasterizer.pixelBuffer, ai, 0, 0, i, l >> 16, j1 >> 16, k1 >> 8, i2 >> 8, l4, k5, j6, i5, l5, k6);
                    j1 += i8;
                    l += i7;
                    i2 += j8;
                    k1 += j7;
                    i += BasicRasterizer.bufferWidth;
                    l4 += j5;
                    k5 += i6;
                    j6 += l6;
                }
                while(--k >= 0) 
                {
                    drawTexturedLine(BasicRasterizer.pixelBuffer, ai, 0, 0, i, i1 >> 16, j1 >> 16, l1 >> 8, i2 >> 8, l4, k5, j6, i5, l5, k6);
                    j1 += i8;
                    i1 += k7;
                    i2 += j8;
                    l1 += l7;
                    i += BasicRasterizer.bufferWidth;
                    l4 += j5;
                    k5 += i6;
                    j6 += l6;
                }
                return;
            }
            i1 = l <<= 16;
            l1 = k1 <<= 16;
            if(i < 0)
            {
                i1 -= i8 * i;
                l -= i7 * i;
                l1 -= j8 * i;
                k1 -= j7 * i;
                i = 0;
            }
            j1 <<= 16;
            i2 <<= 16;
            if(k < 0)
            {
                j1 -= k7 * k;
                i2 -= l7 * k;
                k = 0;
            }
            int l8 = i - centerHeight;
            l4 += j5 * l8;
            k5 += i6 * l8;
            j6 += l6 * l8;
            if(i != k && i8 < i7 || i == k && k7 > i7)
            {
                j -= k;
                k -= i;
                i = heightoffsets[i];
                while(--k >= 0) 
                {
                    drawTexturedLine(BasicRasterizer.pixelBuffer, ai, 0, 0, i, i1 >> 16, l >> 16, l1 >> 8, k1 >> 8, l4, k5, j6, i5, l5, k6);
                    i1 += i8;
                    l += i7;
                    l1 += j8;
                    k1 += j7;
                    i += BasicRasterizer.bufferWidth;
                    l4 += j5;
                    k5 += i6;
                    j6 += l6;
                }
                while(--j >= 0) 
                {
                    drawTexturedLine(BasicRasterizer.pixelBuffer, ai, 0, 0, i, j1 >> 16, l >> 16, i2 >> 8, k1 >> 8, l4, k5, j6, i5, l5, k6);
                    j1 += k7;
                    l += i7;
                    i2 += l7;
                    k1 += j7;
                    i += BasicRasterizer.bufferWidth;
                    l4 += j5;
                    k5 += i6;
                    j6 += l6;
                }
                return;
            }
            j -= k;
            k -= i;
            i = heightoffsets[i];
            while(--k >= 0) 
            {
                drawTexturedLine(BasicRasterizer.pixelBuffer, ai, 0, 0, i, l >> 16, i1 >> 16, k1 >> 8, l1 >> 8, l4, k5, j6, i5, l5, k6);
                i1 += i8;
                l += i7;
                l1 += j8;
                k1 += j7;
                i += BasicRasterizer.bufferWidth;
                l4 += j5;
                k5 += i6;
                j6 += l6;
            }
            while(--j >= 0) 
            {
                drawTexturedLine(BasicRasterizer.pixelBuffer, ai, 0, 0, i, l >> 16, j1 >> 16, k1 >> 8, i2 >> 8, l4, k5, j6, i5, l5, k6);
                j1 += k7;
                l += i7;
                i2 += l7;
                k1 += j7;
                i += BasicRasterizer.bufferWidth;
                l4 += j5;
                k5 += i6;
                j6 += l6;
            }
            return;
        }
        if(j <= k)
        {
            if(j >= BasicRasterizer.height)
                return;
            if(k > BasicRasterizer.height)
                k = BasicRasterizer.height;
            if(i > BasicRasterizer.height)
                i = BasicRasterizer.height;
            if(k < i)
            {
                l = i1 <<= 16;
                k1 = l1 <<= 16;
                if(j < 0)
                {
                    l -= i7 * j;
                    i1 -= k7 * j;
                    k1 -= j7 * j;
                    l1 -= l7 * j;
                    j = 0;
                }
                j1 <<= 16;
                i2 <<= 16;
                if(k < 0)
                {
                    j1 -= i8 * k;
                    i2 -= j8 * k;
                    k = 0;
                }
                int i9 = j - centerHeight;
                l4 += j5 * i9;
                k5 += i6 * i9;
                j6 += l6 * i9;
                if(j != k && i7 < k7 || j == k && i7 > i8)
                {
                    i -= k;
                    k -= j;
                    j = heightoffsets[j];
                    while(--k >= 0) 
                    {
                        drawTexturedLine(BasicRasterizer.pixelBuffer, ai, 0, 0, j, l >> 16, i1 >> 16, k1 >> 8, l1 >> 8, l4, k5, j6, i5, l5, k6);
                        l += i7;
                        i1 += k7;
                        k1 += j7;
                        l1 += l7;
                        j += BasicRasterizer.bufferWidth;
                        l4 += j5;
                        k5 += i6;
                        j6 += l6;
                    }
                    while(--i >= 0) 
                    {
                        drawTexturedLine(BasicRasterizer.pixelBuffer, ai, 0, 0, j, l >> 16, j1 >> 16, k1 >> 8, i2 >> 8, l4, k5, j6, i5, l5, k6);
                        l += i7;
                        j1 += i8;
                        k1 += j7;
                        i2 += j8;
                        j += BasicRasterizer.bufferWidth;
                        l4 += j5;
                        k5 += i6;
                        j6 += l6;
                    }
                    return;
                }
                i -= k;
                k -= j;
                j = heightoffsets[j];
                while(--k >= 0) 
                {
                    drawTexturedLine(BasicRasterizer.pixelBuffer, ai, 0, 0, j, i1 >> 16, l >> 16, l1 >> 8, k1 >> 8, l4, k5, j6, i5, l5, k6);
                    l += i7;
                    i1 += k7;
                    k1 += j7;
                    l1 += l7;
                    j += BasicRasterizer.bufferWidth;
                    l4 += j5;
                    k5 += i6;
                    j6 += l6;
                }
                while(--i >= 0) 
                {
                    drawTexturedLine(BasicRasterizer.pixelBuffer, ai, 0, 0, j, j1 >> 16, l >> 16, i2 >> 8, k1 >> 8, l4, k5, j6, i5, l5, k6);
                    l += i7;
                    j1 += i8;
                    k1 += j7;
                    i2 += j8;
                    j += BasicRasterizer.bufferWidth;
                    l4 += j5;
                    k5 += i6;
                    j6 += l6;
                }
                return;
            }
            j1 = i1 <<= 16;
            i2 = l1 <<= 16;
            if(j < 0)
            {
                j1 -= i7 * j;
                i1 -= k7 * j;
                i2 -= j7 * j;
                l1 -= l7 * j;
                j = 0;
            }
            l <<= 16;
            k1 <<= 16;
            if(i < 0)
            {
                l -= i8 * i;
                k1 -= j8 * i;
                i = 0;
            }
            int j9 = j - centerHeight;
            l4 += j5 * j9;
            k5 += i6 * j9;
            j6 += l6 * j9;
            if(i7 < k7)
            {
                k -= i;
                i -= j;
                j = heightoffsets[j];
                while(--i >= 0) 
                {
                    drawTexturedLine(BasicRasterizer.pixelBuffer, ai, 0, 0, j, j1 >> 16, i1 >> 16, i2 >> 8, l1 >> 8, l4, k5, j6, i5, l5, k6);
                    j1 += i7;
                    i1 += k7;
                    i2 += j7;
                    l1 += l7;
                    j += BasicRasterizer.bufferWidth;
                    l4 += j5;
                    k5 += i6;
                    j6 += l6;
                }
                while(--k >= 0) 
                {
                    drawTexturedLine(BasicRasterizer.pixelBuffer, ai, 0, 0, j, l >> 16, i1 >> 16, k1 >> 8, l1 >> 8, l4, k5, j6, i5, l5, k6);
                    l += i8;
                    i1 += k7;
                    k1 += j8;
                    l1 += l7;
                    j += BasicRasterizer.bufferWidth;
                    l4 += j5;
                    k5 += i6;
                    j6 += l6;
                }
                return;
            }
            k -= i;
            i -= j;
            j = heightoffsets[j];
            while(--i >= 0) 
            {
                drawTexturedLine(BasicRasterizer.pixelBuffer, ai, 0, 0, j, i1 >> 16, j1 >> 16, l1 >> 8, i2 >> 8, l4, k5, j6, i5, l5, k6);
                j1 += i7;
                i1 += k7;
                i2 += j7;
                l1 += l7;
                j += BasicRasterizer.bufferWidth;
                l4 += j5;
                k5 += i6;
                j6 += l6;
            }
            while(--k >= 0) 
            {
                drawTexturedLine(BasicRasterizer.pixelBuffer, ai, 0, 0, j, i1 >> 16, l >> 16, l1 >> 8, k1 >> 8, l4, k5, j6, i5, l5, k6);
                l += i8;
                i1 += k7;
                k1 += j8;
                l1 += l7;
                j += BasicRasterizer.bufferWidth;
                l4 += j5;
                k5 += i6;
                j6 += l6;
            }
            return;
        }
        if(k >= BasicRasterizer.height)
            return;
        if(i > BasicRasterizer.height)
            i = BasicRasterizer.height;
        if(j > BasicRasterizer.height)
            j = BasicRasterizer.height;
        if(i < j)
        {
            i1 = j1 <<= 16;
            l1 = i2 <<= 16;
            if(k < 0)
            {
                i1 -= k7 * k;
                j1 -= i8 * k;
                l1 -= l7 * k;
                i2 -= j8 * k;
                k = 0;
            }
            l <<= 16;
            k1 <<= 16;
            if(i < 0)
            {
                l -= i7 * i;
                k1 -= j7 * i;
                i = 0;
            }
            int k9 = k - centerHeight;
            l4 += j5 * k9;
            k5 += i6 * k9;
            j6 += l6 * k9;
            if(k7 < i8)
            {
                j -= i;
                i -= k;
                k = heightoffsets[k];
                while(--i >= 0) 
                {
                    drawTexturedLine(BasicRasterizer.pixelBuffer, ai, 0, 0, k, i1 >> 16, j1 >> 16, l1 >> 8, i2 >> 8, l4, k5, j6, i5, l5, k6);
                    i1 += k7;
                    j1 += i8;
                    l1 += l7;
                    i2 += j8;
                    k += BasicRasterizer.bufferWidth;
                    l4 += j5;
                    k5 += i6;
                    j6 += l6;
                }
                while(--j >= 0) 
                {
                    drawTexturedLine(BasicRasterizer.pixelBuffer, ai, 0, 0, k, i1 >> 16, l >> 16, l1 >> 8, k1 >> 8, l4, k5, j6, i5, l5, k6);
                    i1 += k7;
                    l += i7;
                    l1 += l7;
                    k1 += j7;
                    k += BasicRasterizer.bufferWidth;
                    l4 += j5;
                    k5 += i6;
                    j6 += l6;
                }
                return;
            }
            j -= i;
            i -= k;
            k = heightoffsets[k];
            while(--i >= 0) 
            {
                drawTexturedLine(BasicRasterizer.pixelBuffer, ai, 0, 0, k, j1 >> 16, i1 >> 16, i2 >> 8, l1 >> 8, l4, k5, j6, i5, l5, k6);
                i1 += k7;
                j1 += i8;
                l1 += l7;
                i2 += j8;
                k += BasicRasterizer.bufferWidth;
                l4 += j5;
                k5 += i6;
                j6 += l6;
            }
            while(--j >= 0) 
            {
                drawTexturedLine(BasicRasterizer.pixelBuffer, ai, 0, 0, k, l >> 16, i1 >> 16, k1 >> 8, l1 >> 8, l4, k5, j6, i5, l5, k6);
                i1 += k7;
                l += i7;
                l1 += l7;
                k1 += j7;
                k += BasicRasterizer.bufferWidth;
                l4 += j5;
                k5 += i6;
                j6 += l6;
            }
            return;
        }
        l = j1 <<= 16;
        k1 = i2 <<= 16;
        if(k < 0)
        {
            l -= k7 * k;
            j1 -= i8 * k;
            k1 -= l7 * k;
            i2 -= j8 * k;
            k = 0;
        }
        i1 <<= 16;
        l1 <<= 16;
        if(j < 0)
        {
            i1 -= i7 * j;
            l1 -= j7 * j;
            j = 0;
        }
        int l9 = k - centerHeight;
        l4 += j5 * l9;
        k5 += i6 * l9;
        j6 += l6 * l9;
        if(k7 < i8)
        {
            i -= j;
            j -= k;
            k = heightoffsets[k];
            while(--j >= 0) 
            {
                drawTexturedLine(BasicRasterizer.pixelBuffer, ai, 0, 0, k, l >> 16, j1 >> 16, k1 >> 8, i2 >> 8, l4, k5, j6, i5, l5, k6);
                l += k7;
                j1 += i8;
                k1 += l7;
                i2 += j8;
                k += BasicRasterizer.bufferWidth;
                l4 += j5;
                k5 += i6;
                j6 += l6;
            }
            while(--i >= 0) 
            {
                drawTexturedLine(BasicRasterizer.pixelBuffer, ai, 0, 0, k, i1 >> 16, j1 >> 16, l1 >> 8, i2 >> 8, l4, k5, j6, i5, l5, k6);
                i1 += i7;
                j1 += i8;
                l1 += j7;
                i2 += j8;
                k += BasicRasterizer.bufferWidth;
                l4 += j5;
                k5 += i6;
                j6 += l6;
            }
            return;
        }
        i -= j;
        j -= k;
        k = heightoffsets[k];
        while(--j >= 0) 
        {
            drawTexturedLine(BasicRasterizer.pixelBuffer, ai, 0, 0, k, j1 >> 16, l >> 16, i2 >> 8, k1 >> 8, l4, k5, j6, i5, l5, k6);
            l += k7;
            j1 += i8;
            k1 += l7;
            i2 += j8;
            k += BasicRasterizer.bufferWidth;
            l4 += j5;
            k5 += i6;
            j6 += l6;
        }
        while(--i >= 0) 
        {
            drawTexturedLine(BasicRasterizer.pixelBuffer, ai, 0, 0, k, j1 >> 16, i1 >> 16, i2 >> 8, l1 >> 8, l4, k5, j6, i5, l5, k6);
            i1 += i7;
            j1 += i8;
            l1 += j7;
            i2 += j8;
            k += BasicRasterizer.bufferWidth;
            l4 += j5;
            k5 += i6;
            j6 += l6;
        }
    }

    public static void drawTexturedLine(int output[], int src[], int i, int j, int offset, int x, int length, int j1,
            int k1, int l1, int i2, int j2, int k2, int l2, int i3)
    {
        if(x >= length)
            return;
        int j3;
        int loops;
        if(clip)
        {
            j3 = (k1 - j1) / (length - x);
            if(length > BasicRasterizer.widthLength)
                length = BasicRasterizer.widthLength;
            if(x < 0)
            {
                j1 -= x * j3;
                x = 0;
            }
            if(x >= length)
                return;
            loops = length - x >> 3;
            j3 <<= 12;
            j1 <<= 9;
        } else
        {
            if(length - x > 7)
            {
                loops = length - x >> 3;
                j3 = (k1 - j1) * GRADIENTTABLE[loops] >> 6;
            } else
            {
                loops = 0;
                j3 = 0;
            }
            j1 <<= 9;
        }
        offset += x;
        if(lowmemory)
        {
            int i4 = 0;
            int k4 = 0;
            int k6 = x - centerWidth;
            l1 += (k2 >> 3) * k6;
            i2 += (l2 >> 3) * k6;
            j2 += (i3 >> 3) * k6;
            int i5 = j2 >> 12;
            if(i5 != 0)
            {
                i = l1 / i5;
                j = i2 / i5;
                if(i < 0)
                    i = 0;
                else
                if(i > 4032)
                    i = 4032;
            }
            l1 += k2;
            i2 += l2;
            j2 += i3;
            i5 = j2 >> 12;
            if(i5 != 0)
            {
                i4 = l1 / i5;
                k4 = i2 / i5;
                if(i4 < 7)
                    i4 = 7;
                else
                if(i4 > 4032)
                    i4 = 4032;
            }
            int i7 = i4 - i >> 3;
            int k7 = k4 - j >> 3;
            i += (j1 & 0x600000) >> 3;
            int i8 = j1 >> 23;
            if(aBoolean1463)
            {
                while(loops-- > 0) {
                    output[offset++] = src[(j & 0xfc0) + (i >> 6)] >>> i8;
                    i += i7;
                    j += k7;
                    output[offset++] = src[(j & 0xfc0) + (i >> 6)] >>> i8;
                    i += i7;
                    j += k7;
                    output[offset++] = src[(j & 0xfc0) + (i >> 6)] >>> i8;
                    i += i7;
                    j += k7;
                    output[offset++] = src[(j & 0xfc0) + (i >> 6)] >>> i8;
                    i += i7;
                    j += k7;
                    output[offset++] = src[(j & 0xfc0) + (i >> 6)] >>> i8;
                    i += i7;
                    j += k7;
                    output[offset++] = src[(j & 0xfc0) + (i >> 6)] >>> i8;
                    i += i7;
                    j += k7;
                    output[offset++] = src[(j & 0xfc0) + (i >> 6)] >>> i8;
                    i += i7;
                    j += k7;
                    output[offset++] = src[(j & 0xfc0) + (i >> 6)] >>> i8;
                    i = i4;
                    j = k4;
                    l1 += k2;
                    i2 += l2;
                    j2 += i3;
                    int j5 = j2 >> 12;
                    if(j5 != 0)
                    {
                        i4 = l1 / j5;
                        k4 = i2 / j5;
                        if(i4 < 7)
                            i4 = 7;
                        else
                        if(i4 > 4032)
                            i4 = 4032;
                    }
                    i7 = i4 - i >> 3;
                    k7 = k4 - j >> 3;
                    j1 += j3;
                    i += (j1 & 0x600000) >> 3;
                    i8 = j1 >> 23;
                }
                for(loops = length - x & 7; loops-- > 0;)
                {
                    output[offset++] = src[(j & 0xfc0) + (i >> 6)] >>> i8;
                    i += i7;
                    j += k7;
                }

                return;
            }
            while(loops-- > 0)
            {
                int color;
                if((color = src[(j & 0xfc0) + (i >> 6)] >>> i8) != 0)
                    output[offset] = color;
                offset++;
                i += i7;
                j += k7;
                if((color = src[(j & 0xfc0) + (i >> 6)] >>> i8) != 0)
                    output[offset] = color;
                offset++;
                i += i7;
                j += k7;
                if((color = src[(j & 0xfc0) + (i >> 6)] >>> i8) != 0)
                    output[offset] = color;
                offset++;
                i += i7;
                j += k7;
                if((color = src[(j & 0xfc0) + (i >> 6)] >>> i8) != 0)
                    output[offset] = color;
                offset++;
                i += i7;
                j += k7;
                if((color = src[(j & 0xfc0) + (i >> 6)] >>> i8) != 0)
                    output[offset] = color;
                offset++;
                i += i7;
                j += k7;
                if((color = src[(j & 0xfc0) + (i >> 6)] >>> i8) != 0)
                    output[offset] = color;
                offset++;
                i += i7;
                j += k7;
                if((color = src[(j & 0xfc0) + (i >> 6)] >>> i8) != 0)
                    output[offset] = color;
                offset++;
                i += i7;
                j += k7;
                if((color = src[(j & 0xfc0) + (i >> 6)] >>> i8) != 0)
                    output[offset] = color;
                offset++;
                i = i4;
                j = k4;
                l1 += k2;
                i2 += l2;
                j2 += i3;
                int k5 = j2 >> 12;
                if(k5 != 0)
                {
                    i4 = l1 / k5;
                    k4 = i2 / k5;
                    if(i4 < 7)
                        i4 = 7;
                    else
                    if(i4 > 4032)
                        i4 = 4032;
                }
                i7 = i4 - i >> 3;
                k7 = k4 - j >> 3;
                j1 += j3;
                i += (j1 & 0x600000) >> 3;
                i8 = j1 >> 23;
            }
            for(loops = length - x & 7; loops-- > 0;)
            {
                int l8;
                if((l8 = src[(j & 0xfc0) + (i >> 6)] >>> i8) != 0)
                    output[offset] = l8;
                offset++;
                i += i7;
                j += k7;
            }

            return;
        }
        int j4 = 0;
        int l4 = 0;
        int l6 = x - centerWidth;
        l1 += (k2 >> 3) * l6;
        i2 += (l2 >> 3) * l6;
        j2 += (i3 >> 3) * l6;
        int l5 = j2 >> 14;
        if(l5 != 0)
        {
            i = l1 / l5;
            j = i2 / l5;
            if(i < 0)
                i = 0;
            else
            if(i > 16256)
                i = 16256;
        }
        l1 += k2;
        i2 += l2;
        j2 += i3;
        l5 = j2 >> 14;
        if(l5 != 0)
        {
            j4 = l1 / l5;
            l4 = i2 / l5;
            if(j4 < 7)
                j4 = 7;
            else
            if(j4 > 16256)
                j4 = 16256;
        }
        int j7 = j4 - i >> 3;
        int l7 = l4 - j >> 3;
        i += j1 & 0x600000;
        int j8 = j1 >> 23;
        if(aBoolean1463)
        {
            while(loops-- > 0)
            {
                output[offset++] = src[(j & 0x3f80) + (i >> 7)] >>> j8;
                i += j7;
                j += l7;
                output[offset++] = src[(j & 0x3f80) + (i >> 7)] >>> j8;
                i += j7;
                j += l7;
                output[offset++] = src[(j & 0x3f80) + (i >> 7)] >>> j8;
                i += j7;
                j += l7;
                output[offset++] = src[(j & 0x3f80) + (i >> 7)] >>> j8;
                i += j7;
                j += l7;
                output[offset++] = src[(j & 0x3f80) + (i >> 7)] >>> j8;
                i += j7;
                j += l7;
                output[offset++] = src[(j & 0x3f80) + (i >> 7)] >>> j8;
                i += j7;
                j += l7;
                output[offset++] = src[(j & 0x3f80) + (i >> 7)] >>> j8;
                i += j7;
                j += l7;
                output[offset++] = src[(j & 0x3f80) + (i >> 7)] >>> j8;
                i = j4;
                j = l4;
                l1 += k2;
                i2 += l2;
                j2 += i3;
                int i6 = j2 >> 14;
                if(i6 != 0)
                {
                    j4 = l1 / i6;
                    l4 = i2 / i6;
                    if(j4 < 7)
                        j4 = 7;
                    else
                    if(j4 > 16256)
                        j4 = 16256;
                }
                j7 = j4 - i >> 3;
                l7 = l4 - j >> 3;
                j1 += j3;
                i += j1 & 0x600000;
                j8 = j1 >> 23;
            }
            for(loops = length - x & 7; loops-- > 0;)
            {
                output[offset++] = src[(j & 0x3f80) + (i >> 7)] >>> j8;
                i += j7;
                j += l7;
            }

            return;
        }
        while(loops-- > 0)
        {
            int color;
            if((color = src[(j & 0x3f80) + (i >> 7)] >>> j8) != 0)
                output[offset] = color;
            offset++;
            i += j7;
            j += l7;
            if((color = src[(j & 0x3f80) + (i >> 7)] >>> j8) != 0)
                output[offset] = color;
            offset++;
            i += j7;
            j += l7;
            if((color = src[(j & 0x3f80) + (i >> 7)] >>> j8) != 0)
                output[offset] = color;
            offset++;
            i += j7;
            j += l7;
            if((color = src[(j & 0x3f80) + (i >> 7)] >>> j8) != 0)
                output[offset] = color;
            offset++;
            i += j7;
            j += l7;
            if((color = src[(j & 0x3f80) + (i >> 7)] >>> j8) != 0)
                output[offset] = color;
            offset++;
            i += j7;
            j += l7;
            if((color = src[(j & 0x3f80) + (i >> 7)] >>> j8) != 0)
                output[offset] = color;
            offset++;
            i += j7;
            j += l7;
            if((color = src[(j & 0x3f80) + (i >> 7)] >>> j8) != 0)
                output[offset] = color;
            offset++;
            i += j7;
            j += l7;
            if((color = src[(j & 0x3f80) + (i >> 7)] >>> j8) != 0)
                output[offset] = color;
            offset++;
            i = j4;
            j = l4;
            l1 += k2;
            i2 += l2;
            j2 += i3;
            int j6 = j2 >> 14;
            if(j6 != 0)
            {
                j4 = l1 / j6;
                l4 = i2 / j6;
                if(j4 < 7)
                    j4 = 7;
                else
                if(j4 > 16256)
                    j4 = 16256;
            }
            j7 = j4 - i >> 3;
            l7 = l4 - j >> 3;
            j1 += j3;
            i += j1 & 0x600000;
            j8 = j1 >> 23;
        }
        for(int l3 = length - x & 7; l3-- > 0;)
        {
            int j9;
            if((j9 = src[(j & 0x3f80) + (i >> 7)] >>> j8) != 0)
                output[offset] = j9;
            offset++;
            i += j7;
            j += l7;
        }

    }

    public static int anInt1458 = -436;
    public static int anInt1459 = -477;
    public static boolean aBoolean1460 = true;
    public static boolean lowmemory = true;
    public static boolean clip;
    public static boolean aBoolean1463;
    public static boolean aBoolean1464 = true;
    public static int alpha$;
    public static int centerWidth;
    public static int centerHeight;
    public static int GRADIENTTABLE[];
    public static int SHADINGTABLE[];
    public static int SINE_TABLE[];
    public static int COSINE_TABLE[];
    public static int heightoffsets[];
    public static int anInt1473;
    public static IndexedColorSprite textures[] = new IndexedColorSprite[50];
    public static boolean aBooleanArray1475[] = new boolean[50];
    public static int calculatedavr_colors[] = new int[50];
    public static int stackpos;
    public static int texturestack[][];
    public static int texturecache[][] = new int[50][];
    public static int unpackcounters[] = new int[50];
    public static int unpackcounter;
    public static int shading$[] = new int[0x10000];
    public static int r3d_colorindexes[][] = new int[50][];

    static 
    {
        GRADIENTTABLE = new int[512];
        SHADINGTABLE = new int[2048];
        SINE_TABLE = new int[2048];
        COSINE_TABLE = new int[2048];
        for(int i = 1; i < 512; i++)
            GRADIENTTABLE[i] = 32768 / i;
        for(int j = 1; j < 2048; j++)
            SHADINGTABLE[j] = 0x10000 / j;
        for(int k = 0; k < 2048; k++) {
            SINE_TABLE[k] = (int)(65536D * Math.sin((double)k * 0.0030679614999999999D));
            COSINE_TABLE[k] = (int)(65536D * Math.cos((double)k * 0.0030679614999999999D));
        }
    }
}
