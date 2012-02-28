// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

import sign.Signlink;

public class AnimFrame {

    public static void initialize(int i) {
        aClass36Array635 = new AnimFrame[i + 1];
        aBooleanArray643 = new boolean[i + 1];
        for(int j = 0; j < i + 1; j++)
            aBooleanArray643[j] = true;
    }

    public static void unpackAnimFrames(byte bytes[]) {
        ByteBuffer buffer0 = new ByteBuffer(bytes);
        buffer0.offset = bytes.length - 8;
        int offset1 = buffer0.getUword();
        int offset2 = buffer0.getUword();
        int offset3 = buffer0.getUword();
        int offset4 = buffer0.getUword();
        int offset = 0;
        ByteBuffer buffer1 = new ByteBuffer(bytes);
        buffer1.offset = offset;
        offset += offset1 + 2;
        ByteBuffer buffer2 = new ByteBuffer(bytes);
        buffer2.offset = offset;
        offset += offset2;
        ByteBuffer buffer3 = new ByteBuffer(bytes);
        buffer3.offset = offset;
        offset += offset3;
        ByteBuffer buffer4 = new ByteBuffer(bytes);
        buffer4.offset = offset;
        offset += offset4;
        ByteBuffer buffer5 = new ByteBuffer(bytes);
        buffer5.offset = offset;
        FrameOperation class18 = new FrameOperation(0, buffer5);
        int animationframes = buffer1.getUword();
        int ai[] = new int[500];
        int ai1[] = new int[500];
        int ai2[] = new int[500];
        int ai3[] = new int[500];
        for(int i = 0; i < animationframes; i++)
        {
            int i2 = buffer1.getUword();
            AnimFrame class36 = aClass36Array635[i2] = new AnimFrame();
            class36.anInt636 = buffer4.getUbyte();
            class36.aClass18_637 = class18;
            int j2 = buffer1.getUbyte();
            int k2 = -1;
            int l2 = 0;
            for(int i3 = 0; i3 < j2; i3++)
            {
                int j3 = buffer2.getUbyte();
                if(j3 > 0)
                {
                    if(class18.animopcodes[i3] != 0)
                    {
                        for(int l3 = i3 - 1; l3 > k2; l3--)
                        {
                            if(class18.animopcodes[l3] != 0)
                                continue;
                            ai[l2] = l3;
                            ai1[l2] = 0;
                            ai2[l2] = 0;
                            ai3[l2] = 0;
                            l2++;
                            break;
                        }

                    }
                    ai[l2] = i3;
                    char c = '\0';
                    if(class18.animopcodes[i3] == 3)
                        c = '\200';
                    if((j3 & 1) != 0)
                        ai1[l2] = buffer3.getSmartA();
                    else
                        ai1[l2] = c;
                    if((j3 & 2) != 0)
                        ai2[l2] = buffer3.getSmartA();
                    else
                        ai2[l2] = c;
                    if((j3 & 4) != 0)
                        ai3[l2] = buffer3.getSmartA();
                    else
                        ai3[l2] = c;
                    k2 = i3;
                    l2++;
                    if(class18.animopcodes[i3] == 5)
                        aBooleanArray643[i2] = false;
                }
            }

            class36.amountops = l2;
            class36.operations = new int[l2];
            class36.xvars = new int[l2];
            class36.yvars = new int[l2];
            class36.zvars = new int[l2];
            for(int k3 = 0; k3 < l2; k3++)
            {
                class36.operations[k3] = ai[k3];
                class36.xvars[k3] = ai1[k3];
                class36.yvars[k3] = ai2[k3];
                class36.zvars[k3] = ai3[k3];
            }

        }

    }

    public static void method530(int i)
    {
        while(i >= 0) 
            anInt634 = 90;
        aClass36Array635 = null;
    }

    public static AnimFrame getAnimationFrame(int j)
    {
        if(aClass36Array635 == null)
            return null;
        else
            return aClass36Array635[j];
    }

    public static boolean method532(int i, boolean junk) {
        return i == -1;
    }

    public AnimFrame()
    {
    }

    public static int anInt634 = -715;
    public static AnimFrame aClass36Array635[];
    public int anInt636;
    public FrameOperation aClass18_637;
    public int amountops;
    public int operations[];
    public int xvars[];
    public int yvars[];
    public int zvars[];
    public static boolean aBooleanArray643[];

}
