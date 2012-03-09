// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

import java.awt.*;
import java.awt.image.PixelGrabber;

public class DirectColorSprite extends BasicRasterizer {

    public DirectColorSprite(int width, int height) {
        buffer = new int[width * height];
        indexWidth = spriteWidth = width;
        indexHeight = spriteHeight = height;
        offsetX = offsetY = 0;
    }

    public DirectColorSprite(Component component, byte[] src) {
        try {
            Image image = Toolkit.getDefaultToolkit().createImage(src);
            MediaTracker mediatracker = new MediaTracker(component);
            mediatracker.addImage(image, 0);
            mediatracker.waitForAll();
            indexWidth = image.getWidth(component);
            indexHeight = image.getHeight(component);
            spriteWidth = indexWidth;
            spriteHeight = indexHeight;
            offsetX = 0;
            offsetY = 0;
            buffer = new int[indexWidth * indexHeight];
            PixelGrabber pixelgrabber = new PixelGrabber(image, 0, 0, indexWidth, indexHeight, buffer, 0, indexWidth);
            pixelgrabber.grabPixels();
            return;
        } catch(Exception ex) {
            System.out.println("Error converting jpg");
        }
    }

    public DirectColorSprite(ArchivePackage pack, String archiveName, int childId) {
        ByteBuffer mainBuffer = new ByteBuffer(pack.getArchive(archiveName + ".dat", null));
        ByteBuffer indexBuffer = new ByteBuffer(pack.getArchive("index.dat", null));
        indexBuffer.offset = mainBuffer.getUword();
        spriteWidth = indexBuffer.getUword();
        spriteHeight = indexBuffer.getUword();
        int amountColors = indexBuffer.getUbyte();
        int colorIndex[] = new int[amountColors];
        for(int i = 0; i < amountColors - 1; i++) {
            colorIndex[i + 1] = (indexBuffer.getTri() & 0xFFFFFF);
            if(colorIndex[i + 1] == 0)
                colorIndex[i + 1] = 1;
        }
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
        buffer = new int[bufferSize];
        if(indexType == 0)
        {
            for(int i = 0; i < bufferSize; i++)
                buffer[i] = colorIndex[mainBuffer.getUbyte()];
            return;
        }
        if(indexType == 1) {
            for(int x = 0; x < indexWidth; x++) {
                for(int y = 0; y < indexHeight; y++) {
                    buffer[x + (y * indexWidth)] = colorIndex[mainBuffer.getUbyte()];
                }
            }
        }
    }

    public void initialize() {
        BasicRasterizer.initialize(indexHeight, indexWidth, buffer);
    }

    public void intensify(int rAmount, int gAmount, int bAmount) {
        for(int i = 0; i < buffer.length; i++) {
            int pixelRgb = buffer[i];
            if(pixelRgb != 0) {
                int rChannel = pixelRgb >> 16 & 0xff;
                rChannel += rAmount;
                if(rChannel < 1)
                    rChannel = 1;
                else
                if(rChannel > 255)
                    rChannel = 255;
                int gChannel = pixelRgb >> 8 & 0xff;
                gChannel += gAmount;
                if(gChannel < 1)
                    gChannel = 1;
                else
                if(gChannel > 255)
                    gChannel = 255;
                int bChannel = pixelRgb & 0xff;
                bChannel += bAmount;
                if(bChannel < 1)
                    bChannel = 1;
                else
                if(bChannel > 255)
                    bChannel = 255;
                buffer[i] = (rChannel << 16) + (gChannel << 8) + bChannel;
            }
        }
    }

    public void unpack() {
        int pBuffer[] = new int[spriteWidth * spriteHeight];
        for(int y = 0; y < indexHeight; y++) {
            for(int x = 0; x < indexWidth; x++)
                pBuffer[(y + offsetY) * spriteWidth + (x + offsetX)] = pBuffer[y * indexWidth + x];
        }
        buffer = pBuffer;
        indexWidth = spriteWidth;
        indexHeight = spriteHeight;
        offsetX = 0;
        offsetY = 0;
    }

    public void draw(int x, int y)
    {
        x += offsetX;
        y += offsetY;
        int outoffset = x + y * BasicRasterizer.bufferWidth;
        int srcoffset = 0;
        int h = indexHeight;
        int w = indexWidth;
        int srcinc = BasicRasterizer.bufferWidth - w;
        int outinc = 0;
        if(y < BasicRasterizer.heightOffset)
        {
            int j2 = BasicRasterizer.heightOffset - y;
            h -= j2;
            y = BasicRasterizer.heightOffset;
            srcoffset += j2 * w;
            outoffset += j2 * BasicRasterizer.bufferWidth;
        }
        if(y + h > BasicRasterizer.height)
            h -= (y + h) - BasicRasterizer.height;
        if(x < BasicRasterizer.widthOffset)
        {
            int k2 = BasicRasterizer.widthOffset - x;
            w -= k2;
            x = BasicRasterizer.widthOffset;
            srcoffset += k2;
            outoffset += k2;
            outinc += k2;
            srcinc += k2;
        }
        if(x + w > BasicRasterizer.width)
        {
            int l2 = (x + w) - BasicRasterizer.width;
            w -= l2;
            outinc += l2;
            srcinc += l2;
        }
        if(w <= 0 || h <= 0)
        {
            return;
        } else
        {
            draw(BasicRasterizer.pixelBuffer, buffer, outoffset, srcoffset, srcinc, outinc, w, h);
            return;
        }
    }

    public void draw(int[] out, int[] src, int outoffset, int srcoffset, int outheight, int srcheight, int width, int height) {
        int widthoffset0 = -(width >> 2);
        width = -(width & 3);
        for(int i = -height; i < 0; i++) {
            for(int j = widthoffset0; j < 0; j++) {
                out[outoffset++] = src[srcoffset++];
                out[outoffset++] = src[srcoffset++];
                out[outoffset++] = src[srcoffset++];
                out[outoffset++] = src[srcoffset++];
            }
            for(int k = width; k < 0; k++)
                out[outoffset++] = src[srcoffset++];
            outoffset += outheight;
            srcoffset += srcheight;
        }
    }

    public void draw(int i, int j, int k)
    {
        i += offsetX;
        k += offsetY;
        if(j != 16083)
            return;
        int l = i + k * BasicRasterizer.bufferWidth;
        int i1 = 0;
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
            draw(BasicRasterizer.pixelBuffer, buffer, 0, i1, l, k1, j1, l1, i2);
            return;
        }
    }

    public void draw(int out[], int src[], int color, int srcoffset, int outoffset, int width, int i1, int outheight, int srcheight) {
        int widthoffset0 = -(width >> 2);
        width = -(width & 3);
        for(int i2 = -i1; i2 < 0; i2++)
        {
            for(int j2 = widthoffset0; j2 < 0; j2++)
            {
                color = src[srcoffset++];
                if(color != 0)
                    out[outoffset++] = color;
                else
                    outoffset++;
                color = src[srcoffset++];
                if(color != 0)
                    out[outoffset++] = color;
                else
                    outoffset++;
                color = src[srcoffset++];
                if(color != 0)
                    out[outoffset++] = color;
                else
                    outoffset++;
                color = src[srcoffset++];
                if(color != 0)
                    out[outoffset++] = color;
                else
                    outoffset++;
            }

            for(int k2 = width; k2 < 0; k2++)
            {
                color = src[srcoffset++];
                if(color != 0)
                    out[outoffset++] = color;
                else
                    outoffset++;
            }

            outoffset += outheight;
            srcoffset += srcheight;
        }

    }

    public void drawBlended(int x, int y, int k, boolean flag)
    {
        x += offsetX;
        y += offsetY;
        int i1 = x + y * BasicRasterizer.bufferWidth;
        int j1 = 0;
        int indexh = indexHeight;
        int indexw = indexWidth;
        int i2 = BasicRasterizer.bufferWidth - indexw;
        int j2 = 0;
        if(y < BasicRasterizer.heightOffset)
        {
            int k2 = BasicRasterizer.heightOffset - y;
            indexh -= k2;
            y = BasicRasterizer.heightOffset;
            j1 += k2 * indexw;
            i1 += k2 * BasicRasterizer.bufferWidth;
        }
        if(y + indexh > BasicRasterizer.height)
            indexh -= (y + indexh) - BasicRasterizer.height;
        if(x < BasicRasterizer.widthOffset)
        {
            int l2 = BasicRasterizer.widthOffset - x;
            indexw -= l2;
            x = BasicRasterizer.widthOffset;
            j1 += l2;
            i1 += l2;
            j2 += l2;
            i2 += l2;
        }
        if(x + indexw > BasicRasterizer.width)
        {
            int i3 = (x + indexw) - BasicRasterizer.width;
            indexw -= i3;
            j2 += i3;
            i2 += i3;
        }
        if(indexw <= 0 || indexh <= 0)
        {
            return;
        } else
        {
            drawBlended(j1, indexw, BasicRasterizer.pixelBuffer, 0, buffer, j2, indexh, i2, k, i1, 8);
            return;
        }
    }

    public void drawBlended(int srcoffset, int width, int out[], int srccolor, int src[], int srcheight, int height, int outheight, int alpha, int outoffset, int i2)
    {
        int outalpha = 256 - alpha;
        for(int k2 = -height; k2 < 0; k2++)
        {
            for(int l2 = -width; l2 < 0; l2++)
            {
                srccolor = src[srcoffset++];
                if(srccolor != 0)
                {
                    int outcolor = out[outoffset];
                    out[outoffset++] = ((srccolor & 0xff00ff) * alpha + (outcolor & 0xff00ff) * outalpha & 0xff00ff00) + ((srccolor & 0xff00) * alpha + (outcolor & 0xff00) * outalpha & 0xff0000) >> 8;
                } else
                {
                    outoffset++;
                }
            }

            outoffset += outheight;
            srcoffset += srcheight;
        }
    }

    public void draw(int i, int j, int ai[], int k, int ai1[], int l, int i1,
            int y, int x, int l1, int i2)
    {
        try
        {
            int j2 = -l1 / 2;
            int k2 = -i / 2;
            int sine = (int)(Math.sin((double)j / 326.11000000000001D) * 65536D);
            int cosine = (int)(Math.cos((double)j / 326.11000000000001D) * 65536D);
            sine = sine * k >> 8;
            cosine = cosine * k >> 8;
            int j3 = (i2 << 16) + (k2 * sine + j2 * cosine);
            int k3 = (i1 << 16) + (k2 * cosine - j2 * sine);
            int offset = x + y * BasicRasterizer.bufferWidth;
            for(y = 0; y < i; y++)
            {
                int i4 = ai1[y];
                int j4 = offset + i4;
                int ox = j3 + cosine * i4;
                int oy = k3 - sine * i4;
                for(x = -ai[y]; x < 0; x++)
                {
                    BasicRasterizer.pixelBuffer[j4++] = buffer[(ox >> 16) + (oy >> 16) * indexWidth];
                    ox += cosine;
                    oy -= sine;
                }
                j3 += sine;
                k3 += cosine;
                offset += BasicRasterizer.bufferWidth;
            }

            return;
        }
        catch(Exception _ex)
        {
            return;
        }
    }

    public void draw(int y, int j, int k, int l, int i1, int j1, int k1, double rotation, int x) {
        try
        {
            int i2 = -k / 2;
            int j2 = -k1 / 2;
            int k2 = (int)(Math.sin(rotation) * 65536D);
            int l2 = (int)(Math.cos(rotation) * 65536D);
            k2 = k2 * j1 >> 8;
            l2 = l2 * j1 >> 8;
            int i3 = (l << 16) + (j2 * k2 + i2 * l2);
            int j3 = (j << 16) + (j2 * l2 - i2 * k2);
            int offset = x + y * BasicRasterizer.bufferWidth;
            for(y = 0; y < k1; y++)
            {
                int o = offset;
                int ox = i3;
                int oy = j3;
                for(x = -k; x < 0; x++)
                {
                    int k4 = buffer[(ox >> 16) + (oy >> 16) * indexWidth];
                    if(k4 != 0)
                        BasicRasterizer.pixelBuffer[o++] = k4;
                    else
                        o++;
                    ox += l2;
                    oy -= k2;
                }

                i3 += k2;
                j3 += l2;
                offset += BasicRasterizer.bufferWidth;
            }

            return;
        }
        catch(Exception _ex)
        {
            return;
        }
    }

    public void draw(IndexedColorSprite class30_sub2_sub1_sub2, boolean flag, int i, int j)
    {
        j += offsetX;
        i += offsetY;
        int k = j + i * BasicRasterizer.bufferWidth;
        int l = 0;
        int i1 = indexHeight;
        int j1 = indexWidth;
        int k1 = BasicRasterizer.bufferWidth - j1;
        int l1 = 0;
        if(i < BasicRasterizer.heightOffset)
        {
            int i2 = BasicRasterizer.heightOffset - i;
            i1 -= i2;
            i = BasicRasterizer.heightOffset;
            l += i2 * j1;
            k += i2 * BasicRasterizer.bufferWidth;
        }
        if(i + i1 > BasicRasterizer.height)
            i1 -= (i + i1) - BasicRasterizer.height;
        if(j < BasicRasterizer.widthOffset)
        {
            int j2 = BasicRasterizer.widthOffset - j;
            j1 -= j2;
            j = BasicRasterizer.widthOffset;
            l += j2;
            k += j2;
            l1 += j2;
            k1 += j2;
        }
        if(j + j1 > BasicRasterizer.width)
        {
            int k2 = (j + j1) - BasicRasterizer.width;
            j1 -= k2;
            l1 += k2;
            k1 += k2;
        }
        if(j1 <= 0 || i1 <= 0)
        {
            return;
        } else
        {
            draw(buffer, j1, class30_sub2_sub1_sub2.buffer, i1, BasicRasterizer.pixelBuffer, 0, k1, k, l1, l);
            return;
        }
    }

    public void draw(int src[], int width, byte index[], int height, int out[], int color, int outheight, int outoffset, int srcheight, int srcoffset)
    {
        int widthoffset0 = -(width >> 2);
        width = -(width & 3);
        for(int j2 = -height; j2 < 0; j2++)
        {
            for(int k2 = widthoffset0; k2 < 0; k2++)
            {
                color = src[srcoffset++];
                if(color != 0 && index[outoffset] == 0)
                    out[outoffset++] = color;
                else
                    outoffset++;
                color = src[srcoffset++];
                if(color != 0 && index[outoffset] == 0)
                    out[outoffset++] = color;
                else
                    outoffset++;
                color = src[srcoffset++];
                if(color != 0 && index[outoffset] == 0)
                    out[outoffset++] = color;
                else
                    outoffset++;
                color = src[srcoffset++];
                if(color != 0 && index[outoffset] == 0)
                    out[outoffset++] = color;
                else
                    outoffset++;
            }

            for(int l2 = width; l2 < 0; l2++)
            {
                color = src[srcoffset++];
                if(color != 0 && index[outoffset] == 0)
                    out[outoffset++] = color;
                else
                    outoffset++;
            }

            outoffset += outheight;
            srcoffset += srcheight;
        }

    }

    public int buffer[];
    public int indexWidth;
    public int indexHeight;
    public int offsetX;
    public int offsetY;
    public int spriteWidth;
    public int spriteHeight;
}
