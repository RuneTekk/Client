// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

import sign.Signlink;

public class FrameOperation {

    public FrameOperation(int i, ByteBuffer buffer0) {
        anInt340 = -588;
        anInt341 = buffer0.getUbyte();
        animopcodes = new int[anInt341];
        animgroups = new int[anInt341][];
        if(i != 0)
            anInt340 = 203;
        for(int j = 0; j < anInt341; j++)
            animopcodes[j] = buffer0.getUbyte();
        for(int k = 0; k < anInt341; k++) {
            int l = buffer0.getUbyte();
            animgroups[k] = new int[l];
            for(int i1 = 0; i1 < l; i1++)
                animgroups[k][i1] = buffer0.getUbyte();
        }
    }

    public int anInt340;
    public int anInt341;
    public int animopcodes[];
    public int animgroups[][];
}
