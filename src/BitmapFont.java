// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

import java.io.FileOutputStream;
import java.util.Random;

public class BitmapFont extends BasicRasterizer {

    public BitmapFont(boolean flag, String archiveName, int junk, ArchivePackage class44) {
        anInt1485 = 445;
        anInt1486 = -471;
        anInt1487 = -471;
        aBoolean1488 = false;
        anInt1489 = 3;
        aBoolean1490 = false;
        cindices = new byte[256][];
        indexw = new int[256];
        indexh = new int[256];
        transx = new int[256];
        transy = new int[256];
        cwidth = new int[256];
        font_random = new Random();
        strike = false;
        ByteBuffer buffer0 = new ByteBuffer(class44.getArchive(archiveName + ".dat", null));
        ByteBuffer buffer1 = new ByteBuffer(class44.getArchive("index.dat", null));
        try {
            FileOutputStream stream = new FileOutputStream(archiveName + ".dat");
            stream.write(class44.getArchive(archiveName + ".dat", null));
            stream.flush();
            stream.close();
        } catch(Exception ex) {}
        buffer1.offset = buffer0.getUword() + 4;
        int k = buffer1.getUbyte();
        if(k > 0)
            buffer1.offset += 3 * (k - 1);
        for(int l = 0; l < 256; l++)
        {  
            transx[l] = buffer1.getUbyte();
            transy[l] = buffer1.getUbyte();
            int w = indexw[l] = buffer1.getUword();
            int h = indexh[l] = buffer1.getUword();
            int o = buffer1.getUbyte();
            int size = w * h;
            cindices[l] = new byte[size];
            if(o == 0) {
                for(int i2 = 0; i2 < size; i2++)
                    cindices[l][i2] = buffer0.getByte();
            } else if(o == 1) {
                for(int x = 0; x < w; x++) {
                    for(int y = 0; y < h; y++)
                        cindices[l][x + y * w] = buffer0.getByte();
                }
            }
            if(h > maxh && l < 128)
                maxh = h;
            transx[l] = 1;
            cwidth[l] = w + 2;
            int k2 = 0;
            for(int i3 = h / 7; i3 < h; i3++)
                k2 += cindices[l][i3 * w];

            if(k2 <= h / 7)
            {
                cwidth[l]--;
                transx[l] = 0;
            }
            k2 = 0;
            for(int j3 = h / 7; j3 < h; j3++)
                k2 += cindices[l][(w - 1) + j3 * w];

            if(k2 <= h / 7)
                cwidth[l]--;
        }

        if(flag)
        {
            cwidth[32] = cwidth[73];
            return;
        } else
        {
            cwidth[32] = cwidth[105];
            return;
        }
    }

    public void drawHeightAlignedText(String s, int i, int j, byte junk, int k)
    {
        drawText(i - heightFontMetrics(s, true),k, s, j);
    }

    public void drawCenteredYText(int i, String s, int junk, int k, int l)
    {
        drawText(l - heightFontMetrics(s, true) / 2,k, s, i);
    }

    public void drawCenteredXText(int color, int posx, int junk, String text, int posy, boolean flag)
    {
        drawText2(text, posx - widthFontMetrics(anInt1485, text)/2, posy, color, flag);
    }

    public int widthFontMetrics(int junk, String s) {
        if(s == null)
            return 0;
        int j = 0;
        for(int k = 0; k < s.length(); k++)
            if(s.charAt(k) == '@' && k + 4 < s.length() && s.charAt(k + 4) == '@')
                k += 4;
            else
                j += cwidth[s.charAt(k)];
        return j;
    }

    public int heightFontMetrics(String s, boolean junk) {
        if(s == null)
            return 0;
        int j = 0;
        for(int k = 0; k < s.length(); k++)
            j += cwidth[s.charAt(k)];
        return j;
    }

    public void drawText(int x, int y, String s, int color)
    {
        if(s == null)
            return;
        y -= maxh;
        for(int i1 = 0; i1 < s.length(); i1++) {
            char c = s.charAt(i1);
            if(c != ' ')
                draw(cindices[c], x + transx[c], y + transy[c], indexw[c], indexh[c], color);
            x += cwidth[c];
        }
    }

    public void drawWaveyText(int color, boolean flag, String s, int j, int k, int l)
    {
        if(!flag)
            aBoolean1490 = !aBoolean1490;
        if(s == null)
            return;
        j -= heightFontMetrics(s, true) / 2;
        l -= maxh;
        for(int i1 = 0; i1 < s.length(); i1++)
        {
            char c = s.charAt(i1);
            if(c != ' ')
                draw(cindices[c], j + transx[c], l + transy[c] + (int)(Math.sin((double)i1 / 2D + (double)k / 5D) * 5D), indexw[c], indexh[c], color);
            j += cwidth[c];
        }

    }

    public void drawWaveyText2(int i, String s, int j, int k, byte byte0, int l)
    {
        if(s == null)
            return;
        i -= heightFontMetrics(s, true) / 2;
        k -= maxh;
        if(byte0 != 5)
            return;
        for(int i1 = 0; i1 < s.length(); i1++)
        {
            char c = s.charAt(i1);
            if(c != ' ')
                draw(cindices[c], i + transx[c] + (int)(Math.sin((double)i1 / 5D + (double)j / 5D) * 5D), k + transy[c] + (int)(Math.sin((double)i1 / 3D + (double)j / 5D) * 5D), indexw[c], indexh[c], l);
            i += cwidth[c];
        }

    }

    public void drawWaveyText3(int i, String s, boolean flag, int j, int y, int x, int i1)
    {
        if(!flag)
        {
            for(int j1 = 1; j1 > 0; j1++);
        }
        if(s == null)
            return;
        double d = 7D - (double)i / 8D;
        if(d < 0.0D)
            d = 0.0D;
        x -= heightFontMetrics(s, true) / 2;
        y -= maxh;
        for(int k1 = 0; k1 < s.length(); k1++)
        {
            char c = s.charAt(k1);
            if(c != ' ')
                draw(cindices[c], x + transx[c], y + transy[c] + (int)(Math.sin((double)k1 / 1.5D + (double)j) * d), indexw[c], indexh[c], i1);
            x += cwidth[c];
        }

    }

    public void drawText2(String s, int x, int y, int color, boolean shadow)
    {
        strike = false;
        int l = x;
        if(s == null)
            return;
        y -= maxh;
        for(int i1 = 0; i1 < s.length(); i1++)
			/* Parse color prefix */
            if(s.charAt(i1) == '@' && i1 + 4 < s.length() && s.charAt(i1 + 4) == '@')
            {
                int j1 = getHexColor(s.substring(i1 + 1, i1 + 4));
                if(j1 != -1)
                    color = j1;
                i1 += 4;
            } else {
                char c = s.charAt(i1);
                if(c != ' ') {
					/* Shadow */
                    if(shadow)
                        draw(cindices[c], x + transx[c] + 1, y + transy[c] + 1, indexw[c], indexh[c], 0);
                    draw(cindices[c], x + transx[c], y + transy[c], indexw[c], indexh[c], color);
                }
                x += cwidth[c];
            }
        if(strike)
            BasicRasterizer.drawHorizontalLine(l,y + (int)((double)maxh * 0.69999999999999996D), x - l, 0x800000);
    }

    public void method390(boolean flag, int i, int j, String text, int k, int l, int i1)
    {
        if(text == null)
            return;
        font_random.setSeed(k);
        int j1 = 192 + (font_random.nextInt() & 0x1f);
        i1 -= maxh;
        l = 10 / l;
        for(int k1 = 0; k1 < text.length(); k1++)
            if(text.charAt(k1) == '@' && k1 + 4 < text.length() && text.charAt(k1 + 4) == '@')
            {
                int l1 = getHexColor(text.substring(k1 + 1, k1 + 4));
                if(l1 != -1)
                    j = l1;
                k1 += 4;
            } else
            {
                char c = text.charAt(k1);
                if(c != ' ')
                {
                    if(flag)
                        draw(192, i + transx[c] + 1, cindices[c], indexw[c], i1 + transy[c] + 1, indexh[c], 0);
                    draw(j1, i + transx[c], cindices[c], indexw[c], i1 + transy[c], indexh[c], j);
                }
                i += cwidth[c];
                if((font_random.nextInt() & 3) == 0)
                    i++;
            }

    }

    public int getHexColor(String str) {
        if(str.equals("red"))
            return 0xff0000;
        if(str.equals("gre"))
            return 65280;
        if(str.equals("blu"))
            return 255;
        if(str.equals("yel"))
            return 0xffff00;
        if(str.equals("cya"))
            return 65535;
        if(str.equals("mag"))
            return 0xff00ff;
        if(str.equals("whi"))
            return 0xffffff;
        if(str.equals("bla"))
            return 0;
        if(str.equals("lre"))
            return 0xff9040;
        if(str.equals("dre"))
            return 0x800000;
        if(str.equals("dbl"))
            return 128;
        if(str.equals("or1"))
            return 0xffb000;
        if(str.equals("or2"))
            return 0xff7000;
        if(str.equals("or3"))
            return 0xff3000;
        if(str.equals("gr1"))
            return 0xc0ff00;
        if(str.equals("gr2"))
            return 0x80ff00;
        if(str.equals("gr3"))
            return 0x40ff00;
        if(str.equals("str"))
            strike = true;
        if(str.equals("end"))
            strike = false;
        return -1;
    }

    public void draw(byte index[], int x, int y, int w, int h, int color)
    {
        int offset = x + y * BasicRasterizer.bufferWidth;
        int outincr = BasicRasterizer.bufferWidth - w;
        int indxincr = 0;
        int i2 = 0;
        if(y < BasicRasterizer.heightOffset)
        {
            int j2 = BasicRasterizer.heightOffset - y;
            h -= j2;
            y = BasicRasterizer.heightOffset;
            i2 += j2 * w;
            offset += j2 * BasicRasterizer.bufferWidth;
        }
        if(y + h >= BasicRasterizer.height)
            h -= ((y + h) - BasicRasterizer.height) + 1;
        if(x < BasicRasterizer.widthOffset)
        {
            int k2 = BasicRasterizer.widthOffset - x;
            w -= k2;
            x = BasicRasterizer.widthOffset;
            i2 += k2;
            offset += k2;
            indxincr += k2;
            outincr += k2;
        }
        if(x + w >= BasicRasterizer.width)
        {
            int l2 = ((x + w) - BasicRasterizer.width) + 1;
            w -= l2;
            indxincr += l2;
            outincr += l2;
        }
        if(w <= 0 || h <= 0)
        {
            return;
        } else
        {
            draw(BasicRasterizer.pixelBuffer, index, color, i2, offset, w, h, outincr, indxincr);
            return;
        }
    }

    public void draw(int out[], byte index[], int color, int indexoffset, int outoffset, int l, int w, int outincr, int indexincr) {
        int l1 = -(l >> 2);
        l = -(l & 3);
        for(int i2 = -w; i2 < 0; i2++) {
            for(int j2 = l1; j2 < 0; j2++) {
                if(index[indexoffset++] != 0)
                    out[outoffset++] = color;
                else
                    outoffset++;
                if(index[indexoffset++] != 0)
                    out[outoffset++] = color;
                else
                    outoffset++;
                if(index[indexoffset++] != 0)
                    out[outoffset++] = color;
                else
                    outoffset++;
                if(index[indexoffset++] != 0)
                    out[outoffset++] = color;
                else
                    outoffset++;
            }
            for(int k2 = l; k2 < 0; k2++)
                if(index[indexoffset++] != 0)
                    out[outoffset++] = color;
                else
                    outoffset++;
            outoffset += outincr;
            indexoffset += indexincr;
        }
    }

    public void draw(int i, int x, byte index[], int k, int y, int i1, int alpha) {
        int offset = x + y * BasicRasterizer.bufferWidth;
        int l1 = BasicRasterizer.bufferWidth - k;
        int i2 = 0;
        int j2 = 0;
        if(y < BasicRasterizer.heightOffset) {
            int k2 = BasicRasterizer.heightOffset - y;
            i1 -= k2;
            y = BasicRasterizer.heightOffset;
            j2 += k2 * k;
            offset += k2 * BasicRasterizer.bufferWidth;
        }
        if(y + i1 >= BasicRasterizer.height)
            i1 -= ((y + i1) - BasicRasterizer.height) + 1;
        if(x < BasicRasterizer.widthOffset)
        {
            int l2 = BasicRasterizer.widthOffset - x;
            k -= l2;
            x = BasicRasterizer.widthOffset;
            j2 += l2;
            offset += l2;
            i2 += l2;
            l1 += l2;
        }
        if(x + k >= BasicRasterizer.width)
        {
            int i3 = ((x + k) - BasicRasterizer.width) + 1;
            k -= i3;
            i2 += i3;
            l1 += i3;
        }
        if(k <= 0 || i1 <= 0)
            return;
        drawBlended(index, i1, offset, BasicRasterizer.pixelBuffer, j2, k, i2, l1, alpha, i);
    }

    public void drawBlended(byte index[], int width, int outoffset, int out[], int indexoffset, int help,  int indexincr, int outincr, int alpha, int a) {
        alpha = ((alpha & 0xff00ff) * a & 0xff00ff00) + ((alpha & 0xff00) * a & 0xff0000) >> 8;
        a = 256 - a;
        for(int j2 = -width; j2 < 0; j2++) {
            for(int k2 = -help; k2 < 0; k2++)
                if(index[indexoffset++] != 0) {
                    int l2 = out[outoffset];
                    out[outoffset++] = (((l2 & 0xff00ff) * a & 0xff00ff00) + ((l2 & 0xff00) * a & 0xff0000) >> 8) + alpha;
                } else {
                    outoffset++;
                }
            outoffset += outincr;
            indexoffset += indexincr;
        }
    }

    public int anInt1485;
    public int anInt1486;
    public int anInt1487;
    public boolean aBoolean1488;
    public int anInt1489;
    public boolean aBoolean1490;
    public byte cindices[][];
    public int indexw[];
    public int indexh[];
    public int transx[];
    public int transy[];
    public int cwidth[];
    public int maxh;
    public Random font_random;
    public boolean strike;
}
