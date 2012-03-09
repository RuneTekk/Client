// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

import sign.Signlink;

public class GameObject extends Entity {

    public Model getModel() {
        int j = -1;
        if(aClass20_1607 != null)
        {
            int k = Main.loopCycle - anInt1608;
            if(k > 100 && aClass20_1607.anInt356 > 0)
                k = 100;
            while(k > aClass20_1607.method258(anInt1599, (byte)-39)) 
            {
                k -= aClass20_1607.method258(anInt1599, (byte)-39);
                anInt1599++;
                if(anInt1599 < aClass20_1607.anInt352)
                    continue;
                anInt1599 -= aClass20_1607.anInt356;
                if(anInt1599 >= 0 && anInt1599 < aClass20_1607.anInt352)
                    continue;
                aClass20_1607 = null;
                break;
            }
            anInt1608 = Main.loopCycle - k;
            if(aClass20_1607 != null)
                j = aClass20_1607.anIntArray353[anInt1599];
        }
        ObjectDefinition class46;
        if(anIntArray1600 != null)
            class46 = method457(true);
        else
            class46 = ObjectDefinition.getObjectDefinition(rso_objid);
        if(class46 == null)
        {
            return null;
        } else
        {
            Model class30_sub2_sub4_sub6 = class46.method578(type, rotation, anInt1603, anInt1604, anInt1605, anInt1606, j);
            return class30_sub2_sub4_sub6;
        }
    }

    public ObjectDefinition method457(boolean flag)
    {
        int i = -1;
        if(!flag)
        {
            for(int j = 1; j > 0; j++);
        }
        if(anInt1601 != -1)
        {
            VarbitFile class37 = VarbitFile.varbitArray[anInt1601];
            int k = class37.config_num;
            int l = class37.anInt649;
            int i1 = class37.anInt650;
            int j1 = Main.BIT_MASKS[i1 - l];
            i = main.configstates[k] >> l & j1;
        } else
        if(anInt1602 != -1)
            i = main.configstates[anInt1602];
        if(i < 0 || i >= anIntArray1600.length || anIntArray1600[i] == -1)
            return null;
        else
            return ObjectDefinition.getObjectDefinition(anIntArray1600[i]);
    }

    public GameObject(int i, int r, int t, int l, byte junk, int i1, int j1, int k1, int animid, boolean flag) {
        rso_objid = i;
        type = t;
        rotation = r;
        anInt1603 = j1;
        anInt1604 = l;
        anInt1605 = i1;
        anInt1606 = k1;
        if(animid != -1)
        {
            aClass20_1607 = AnimSequence.animationsequences[animid];
            anInt1599 = 0;
            anInt1608 = Main.loopCycle;
            if(flag && aClass20_1607.anInt356 != -1)
            {
                anInt1599 = (int)(Math.random() * (double)aClass20_1607.anInt352);
                anInt1608 -= (int)(Math.random() * (double)aClass20_1607.method258(anInt1599, (byte)-39));
            }
        }
        ObjectDefinition class46 = ObjectDefinition.getObjectDefinition(rso_objid);
        anInt1601 = class46.anInt774;
        anInt1602 = class46.anInt749;
        anIntArray1600 = class46.anIntArray759;
    }

    public byte aByte1598;
    public int anInt1599;
    public int anIntArray1600[];
    public int anInt1601;
    public int anInt1602;
    public int anInt1603;
    public int anInt1604;
    public int anInt1605;
    public int anInt1606;
    public AnimSequence aClass20_1607;
    public int anInt1608;
    public static Main main;
    public int rso_objid;
    public int type;
    public int rotation;
    public int anInt1613;
}
