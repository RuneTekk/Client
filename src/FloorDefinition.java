// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

public class FloorDefinition {

    public static void unpackDefinitions(ArchivePackage pack) {
        ByteBuffer buffer = new ByteBuffer(pack.getArchive("flo.dat", null));
        amountDefinitions = buffer.getUword();
        if(definitions == null)
            definitions = new FloorDefinition[amountDefinitions];
        for(int j = 0; j < amountDefinitions; j++) {
            if(definitions[j] == null)
                definitions[j] = new FloorDefinition();
            definitions[j].initialize(buffer);
        }

    }

    public void initialize(ByteBuffer buffer0)
    {
        do
        {
            int opcode = buffer0.getUbyte();
            if(opcode == 0)
                return;
            if(opcode == 1)
            {
                anInt390 = buffer0.getTri();
                method262(anInt390, 9);
            } else
            if(opcode == 2)
                textureId = buffer0.getUbyte();
            else
            if(opcode == 3)
                aBoolean392 = true;
            else
            if(opcode == 5)
                aBoolean393 = false;
            else
            if(opcode == 6)
                name = buffer0.getString();
            else
            if(opcode == 7)
            {
                int j = cArc;
                int k = iArc;
                int l = mChannel;
                int i1 = anInt397;
                int j1 = buffer0.getTri();
                method262(j1, 9);
                cArc = j;
                iArc = k;
                mChannel = l;
                anInt397 = i1;
                anInt398 = i1;
            } else
            {
                System.out.println("Error unrecognised config code: " + opcode);
            }
        } while(true);
    }

    public void method262(int i, int j)
    {
        double rChannel = (double)(i >> 16 & 0xff) / 256D;
        double gChannel = (double)(i >> 8 & 0xff) / 256D;
        double bChannel = (double)(i & 0xff) / 256D;
        double lIntensity = rChannel;
        if(gChannel < lIntensity)
            lIntensity = gChannel;
        if(bChannel < lIntensity)
            lIntensity = bChannel;
        double hIntesnity = rChannel;
        if(gChannel > hIntesnity)
            hIntesnity = gChannel;
        if(bChannel > hIntesnity)
            hIntesnity = bChannel;
        double cRate = 0.0D;
        double iRate = 0.0D;
        double mIntesnity = (lIntensity + hIntesnity) / 2D;
        if(lIntensity != hIntesnity)
        {
            if(mIntesnity < 0.5D)
                iRate = (hIntesnity - lIntensity) / (hIntesnity + lIntensity);
            if(mIntesnity >= 0.5D)
                iRate = (hIntesnity - lIntensity) / (2D - hIntesnity - lIntensity);
            if(rChannel == hIntesnity)
                cRate = (gChannel - bChannel) / (hIntesnity - lIntensity);
            else
            if(gChannel == hIntesnity)
                cRate = 2D + (bChannel - rChannel) / (hIntesnity - lIntensity);
            else
            if(bChannel == hIntesnity)
                cRate = 4D + (rChannel - gChannel) / (hIntesnity - lIntensity);
        }
        cRate /= 6D;
        cArc = (int)(cRate * 256D);
        iArc = (int)(iRate * 256D);
        mChannel = (int) (mIntesnity * 256D);
        if(iArc < 0)
            iArc = 0;
        else
        if(iArc > 255)
            iArc = 255;
        if(mChannel < 0)
            mChannel = 0;
        else
        if(mChannel > 255)
            mChannel = 255;
        if(mIntesnity > 0.5D)
            anInt398 = (int)((1.0D - mIntesnity) * iRate * 512D);
        else
            anInt398 = (int)(mIntesnity * iRate * 512D);
        if(anInt398 < 1)
            anInt398 = 1;
        anInt397 = (int)(cRate * (double)anInt398);
        int k = (cArc + (int)(Math.random() * 16D)) - 8;
        if(k < 0)
            k = 0;
        else
        if(k > 255)
            k = 255;
        int l = (iArc + (int)(Math.random() * 48D)) - 24;
        if(j < 9 || j > 9)
            return;
        if(l < 0)
            l = 0;
        else
        if(l > 255)
            l = 255;
        int s = (mChannel + (int)(Math.random() * 48D)) - 24;
        if(s < 0)
            s = 0;
        else
        if(s > 255)
            s = 255;
        anInt399 = method263(k, l, s);
    }

    public int method263(int i, int j, int r)
    {
        if(r > 179)
            j /= 2;
        if(r > 192)
            j /= 2;
        if(r > 217)
            j /= 2;
        if(r > 243)
            j /= 2;
        int l = (i / 4 << 10) + (j / 32 << 7) + r / 2;
        return l;
    }

    public FloorDefinition()
    {
        aBoolean385 = true;
        textureId = -1;
        aBoolean392 = false;
        aBoolean393 = true;
    }

    public boolean aBoolean385;
    public static int anInt386;
    public static int amountDefinitions;
    public static FloorDefinition definitions[];
    public String name;
    public int anInt390;
    public int textureId;
    public boolean aBoolean392;
    public boolean aBoolean393;
    public int cArc;
    public int iArc;
    public int mChannel;
    public int anInt397;
    public int anInt398;
    public int anInt399;
}
