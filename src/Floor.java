// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

import java.io.PrintStream;
import sign.Signlink;

public class Floor {

    public static void method260(int i, ArchivePackage class44) {
        if(i != 0)
            anInt386 = 115;
        ByteBuffer buffer0 = new ByteBuffer(class44.getArchive("flo.dat", null));
        anInt387 = buffer0.getUword();
        if(floors == null)
            floors = new Floor[anInt387];
        for(int j = 0; j < anInt387; j++) {
            if(floors[j] == null)
                floors[j] = new Floor();
            floors[j].method261(true, buffer0);
        }

    }

    public void method261(boolean flag, ByteBuffer buffer0)
    {
        if(!flag)
            aBoolean385 = !aBoolean385;
        do
        {
            int i = buffer0.getUbyte();
            if(i == 0)
                return;
            if(i == 1)
            {
                anInt390 = buffer0.getTri();
                method262(anInt390, 9);
            } else
            if(i == 2)
                floor_texture = buffer0.getUbyte();
            else
            if(i == 3)
                aBoolean392 = true;
            else
            if(i == 5)
                aBoolean393 = false;
            else
            if(i == 6)
                name = buffer0.getString();
            else
            if(i == 7)
            {
                int j = anInt394;
                int k = anInt395;
                int l = avrintensitycolor;
                int i1 = anInt397;
                int j1 = buffer0.getTri();
                method262(j1, 9);
                anInt394 = j;
                anInt395 = k;
                avrintensitycolor = l;
                anInt397 = i1;
                anInt398 = i1;
            } else
            {
                System.out.println("Error unrecognised config code: " + i);
            }
        } while(true);
    }

    public void method262(int i, int j)
    {
        double redintensity = (double)(i >> 16 & 0xff) / 256D;
        double greenintensity = (double)(i >> 8 & 0xff) / 256D;
        double blueintensity = (double)(i & 0xff) / 256D;
		/* Get the channel that is the least intense */
        double leastintense = redintensity;
        if(greenintensity < leastintense)
            leastintense = greenintensity;
        if(blueintensity < leastintense)
            leastintense = blueintensity;
		/* Get the channel that is the most intense */
        double mostintense = redintensity;
        if(greenintensity > mostintense)
            mostintense = greenintensity;
        if(blueintensity > mostintense)
            mostintense = blueintensity;
        double d5 = 0.0D;
        double d6 = 0.0D;
		/* Calculate the average intensity */
        double avrintensity = (leastintense + mostintense) / 2D;
        if(leastintense != mostintense)
        {
            if(avrintensity < 0.5D)
                d6 = (mostintense - leastintense) / (mostintense + leastintense);
            if(avrintensity >= 0.5D)
                d6 = (mostintense - leastintense) / (2D - mostintense - leastintense);
            if(redintensity == mostintense)
                d5 = (greenintensity - blueintensity) / (mostintense - leastintense);
            else
            if(greenintensity == mostintense)
                d5 = 2D + (blueintensity - redintensity) / (mostintense - leastintense);
            else
            if(blueintensity == mostintense)
                d5 = 4D + (redintensity - greenintensity) / (mostintense - leastintense);
        }
        d5 /= 6D;
        anInt394 = (int)(d5 * 256D);
        anInt395 = (int)(d6 * 256D);
        avrintensitycolor = (int)(avrintensity * 256D);
        if(anInt395 < 0)
            anInt395 = 0;
        else
        if(anInt395 > 255)
            anInt395 = 255;
        if(avrintensitycolor < 0)
            avrintensitycolor = 0;
        else
        if(avrintensitycolor > 255)
            avrintensitycolor = 255;
        if(avrintensity > 0.5D)
            anInt398 = (int)((1.0D - avrintensity) * d6 * 512D);
        else
            anInt398 = (int)(avrintensity * d6 * 512D);
        if(anInt398 < 1)
            anInt398 = 1;
        anInt397 = (int)(d5 * (double)anInt398);
        int k = (anInt394 + (int)(Math.random() * 16D)) - 8;
        if(k < 0)
            k = 0;
        else
        if(k > 255)
            k = 255;
        int l = (anInt395 + (int)(Math.random() * 48D)) - 24;
        if(j < 9 || j > 9)
            return;
        if(l < 0)
            l = 0;
        else
        if(l > 255)
            l = 255;
        int i1 = (avrintensitycolor + (int)(Math.random() * 48D)) - 24;
        if(i1 < 0)
            i1 = 0;
        else
        if(i1 > 255)
            i1 = 255;
        anInt399 = method263(k, l, i1);
    }

    public int method263(int i, int j, int k)
    {
        if(k > 179)
            j /= 2;
        if(k > 192)
            j /= 2;
        if(k > 217)
            j /= 2;
        if(k > 243)
            j /= 2;
        int l = (i / 4 << 10) + (j / 32 << 7) + k / 2;
        return l;
    }

    public Floor()
    {
        aBoolean385 = true;
        floor_texture = -1;
        aBoolean392 = false;
        aBoolean393 = true;
    }

    public boolean aBoolean385;
    public static int anInt386;
    public static int anInt387;
    public static Floor floors[];
    public String name;
    public int anInt390;
    public int floor_texture;
    public boolean aBoolean392;
    public boolean aBoolean393;
    public int anInt394;
    public int anInt395;
    public int avrintensitycolor;
    public int anInt397;
    public int anInt398;
    public int anInt399;
}
