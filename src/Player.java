// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

public class Player extends Mob {

    @Override
    public Model getModel() {
        if(!updated)
            return null;
        Model class30_sub2_sub4_sub6 = method452(0);
        if(class30_sub2_sub4_sub6 == null)
            return null;
        super.anInt1507 = ((Entity) (class30_sub2_sub4_sub6)).miny;
        class30_sub2_sub4_sub6.aBoolean1659 = true;
        if(aBoolean1699)
            return class30_sub2_sub4_sub6;
        if(super.anInt1520 != -1 && super.anInt1521 != -1)
        {
            SpotAnim class23 = SpotAnim.aClass23Array403[super.anInt1520];
            Model class30_sub2_sub4_sub6_2 = class23.method266();
            if(class30_sub2_sub4_sub6_2 != null)
            {
                Model class30_sub2_sub4_sub6_3 = new Model(class30_sub2_sub4_sub6_2,true, AnimFrame.method532(super.anInt1521, false), false);
                class30_sub2_sub4_sub6_3.moveVertices(0, -super.anInt1524, 16384, 0);
                class30_sub2_sub4_sub6_3.setVertexTriangleGroups();
                class30_sub2_sub4_sub6_3.applyAnimationFrame(class23.aClass20_407.anIntArray353[super.anInt1521]);
                class30_sub2_sub4_sub6_3.trianglegroups = null;
                class30_sub2_sub4_sub6_3.vertexgroups = null;
                if(class23.anInt410 != 128 || class23.anInt411 != 128)
                    class30_sub2_sub4_sub6_3.scaleModel(class23.anInt410, class23.anInt411, class23.anInt410);
                class30_sub2_sub4_sub6_3.setLightingVectors(64 + class23.anInt413, 850 + class23.anInt414, -30, -50, -30, true);
                Model aclass30_sub2_sub4_sub6_1[] = {
                    class30_sub2_sub4_sub6, class30_sub2_sub4_sub6_3
                };
                class30_sub2_sub4_sub6 = new Model(2, -819, true, aclass30_sub2_sub4_sub6_1);
            }
        }
        if(aActor_Sub6_1714 != null)
        {
            if(Main.loopCycle >= anInt1708)
                aActor_Sub6_1714 = null;
            if(Main.loopCycle >= anInt1707 && Main.loopCycle < anInt1708)
            {
                Model class30_sub2_sub4_sub6_1 = aActor_Sub6_1714;
                class30_sub2_sub4_sub6_1.moveVertices(anInt1711 - super.fineposx, anInt1712 - tileheight$, 16384, anInt1713 - super.fineposy);
                if(super.anInt1510 == 512)
                {
                    class30_sub2_sub4_sub6_1.rotate(360);
                    class30_sub2_sub4_sub6_1.rotate(360);
                    class30_sub2_sub4_sub6_1.rotate(360);
                } else
                if(super.anInt1510 == 1024)
                {
                    class30_sub2_sub4_sub6_1.rotate(360);
                    class30_sub2_sub4_sub6_1.rotate(360);
                } else
                if(super.anInt1510 == 1536)
                    class30_sub2_sub4_sub6_1.rotate(360);
                Model aclass30_sub2_sub4_sub6[] = {
                    class30_sub2_sub4_sub6, class30_sub2_sub4_sub6_1
                };
                class30_sub2_sub4_sub6 = new Model(2, -819, true, aclass30_sub2_sub4_sub6);
                if(super.anInt1510 == 512)
                    class30_sub2_sub4_sub6_1.rotate(360);
                else
                if(super.anInt1510 == 1024)
                {
                    class30_sub2_sub4_sub6_1.rotate(360);
                    class30_sub2_sub4_sub6_1.rotate(360);
                } else
                if(super.anInt1510 == 1536)
                {
                    class30_sub2_sub4_sub6_1.rotate(360);
                    class30_sub2_sub4_sub6_1.rotate(360);
                    class30_sub2_sub4_sub6_1.rotate(360);
                }
                class30_sub2_sub4_sub6_1.moveVertices(super.fineposx - anInt1711, tileheight$ - anInt1712, 16384, super.fineposy - anInt1713);
            }
        }
        class30_sub2_sub4_sub6.aBoolean1659 = true;
        return class30_sub2_sub4_sub6;
    }

    public void parseAppearanceUpdate(int junk, ByteBuffer buffer0) {
        buffer0.offset = 0;
        gender = buffer0.getUbyte();
        active_headicons = buffer0.getUbyte();
        pnpc = null;
        anInt1701 = 0;
        for(int j = 0; j < 12; j++)
        {
            int k = buffer0.getUbyte();
            if(k == 0)
            {
                appearances[j] = 0;
                continue;
            }
            int i1 = buffer0.getUbyte();
            appearances[j] = (k << 8) + i1;
            if(j == 0 && appearances[0] == 65535)
            {
                pnpc = NPCDefinition.getNPCDefinition(buffer0.getUword());
                break;
            }
            if(appearances[j] >= 512 && appearances[j] - 512 < ItemDefinition.anInt203)
            {
                int l1 = ItemDefinition.getItemDefinition(appearances[j] - 512).anInt202;
                if(l1 != 0)
                    anInt1701 = l1;
            }
        }

        for(int l = 0; l < 5; l++)
        {
            int j1 = buffer0.getUbyte();
            if(j1 < 0 || j1 >= Main.anIntArrayArray1003[l].length)
                j1 = 0;
            anIntArray1700[l] = j1;
        }

        super.stand_anim = buffer0.getUword();
        if(super.stand_anim == 65535)
            super.stand_anim = -1;
        super.standturn_anim = buffer0.getUword();
        if(super.standturn_anim == 65535)
            super.standturn_anim = -1;
        super.walk_anim = buffer0.getUword();
        if(super.walk_anim == 65535)
            super.walk_anim = -1;
        super.turn180_anim = buffer0.getUword();
        if(super.turn180_anim == 65535)
            super.turn180_anim = -1;
        super.turn90cw_anim = buffer0.getUword();
        if(super.turn90cw_anim == 65535)
            super.turn90cw_anim = -1;
        super.turn90ccw_anim = buffer0.getUword();
        if(super.turn90ccw_anim == 65535)
            super.turn90ccw_anim = -1;
        super.run_anim = buffer0.getUword();
        if(super.run_anim == 65535)
            super.run_anim = -1;
        name = TextTools.formatUsername(-45804, TextTools.longToString(buffer0.getQword(), (byte)-99));
        combatlevel = buffer0.getUbyte();
        skilltotal = buffer0.getUword();
        updated = true;
        playermodel_index = 0L;
        for(int k1 = 0; k1 < 12; k1++)
        {
            playermodel_index <<= 4;
            if(appearances[k1] >= 256)
                playermodel_index += appearances[k1] - 256;
        }
        if(appearances[0] >= 256)
            playermodel_index += appearances[0] - 256 >> 4;
        if(appearances[1] >= 256)
            playermodel_index += appearances[1] - 256 >> 8;
        for(int i2 = 0; i2 < 5; i2++)
        {
            playermodel_index <<= 3;
            playermodel_index += anIntArray1700[i2];
        }

        playermodel_index <<= 1;
        playermodel_index += gender;
    }

    public Model method452(int i)
    {
        if(pnpc != null)
        {
            int j = -1;
            if(super.animid_request >= 0 && super.animdelay_request == 0)
                j = AnimSequence.animationsequences[super.animid_request].anIntArray353[super.anInt1527];
            else
            if(super.anInt1517 >= 0)
                j = AnimSequence.animationsequences[super.anInt1517].anIntArray353[super.anInt1518];
            Model class30_sub2_sub4_sub6 = pnpc.getModel(0, -1, j, null);
            return class30_sub2_sub4_sub6;
        }
        long l = playermodel_index;
        int k = -1;
        int i1 = -1;
        int j1 = -1;
        int k1 = -1;
        if(super.animid_request >= 0 && super.animdelay_request == 0)
        {
            AnimSequence class20 = AnimSequence.animationsequences[super.animid_request];
            k = class20.anIntArray353[super.anInt1527];
            if(super.anInt1517 >= 0 && super.anInt1517 != super.stand_anim)
                i1 = AnimSequence.animationsequences[super.anInt1517].anIntArray353[super.anInt1518];
            if(class20.anInt360 >= 0)
            {
                j1 = class20.anInt360;
                l += j1 - appearances[5] << 40;
            }
            if(class20.anInt361 >= 0)
            {
                k1 = class20.anInt361;
                l += k1 - appearances[3] << 48;
            }
        } else
        if(super.anInt1517 >= 0)
            k = AnimSequence.animationsequences[super.anInt1517].anIntArray353[super.anInt1518];
        Model class30_sub2_sub4_sub6_1 = (Model)aClass12_1704.get(l);
        if(i != 0)
        {
            for(int l1 = 1; l1 > 0; l1++);
        }
        if(class30_sub2_sub4_sub6_1 == null)
        {
            boolean flag = false;
            for(int i2 = 0; i2 < 12; i2++)
            {
                int k2 = appearances[i2];
                if(k1 >= 0 && i2 == 3)
                    k2 = k1;
                if(j1 >= 0 && i2 == 5)
                    k2 = j1;
                if(k2 >= 256 && k2 < 512 && !CharModel.charactermodels[k2 - 256].method537((byte)2))
                    flag = true;
                if(k2 >= 512 && !ItemDefinition.getItemDefinition(k2 - 512).loadPlayerModels_(40903, gender))
                    flag = true;
            }

            if(flag)
            {
                if(aLong1697 != -1L)
                    class30_sub2_sub4_sub6_1 = (Model)aClass12_1704.get(aLong1697);
                if(class30_sub2_sub4_sub6_1 == null)
                    return null;
            }
        }
        if(class30_sub2_sub4_sub6_1 == null)
        {
            Model aclass30_sub2_sub4_sub6[] = new Model[12];
            int j2 = 0;
            for(int l2 = 0; l2 < 12; l2++)
            {
                int i3 = appearances[l2];
                if(k1 >= 0 && l2 == 3)
                    i3 = k1;
                if(j1 >= 0 && l2 == 5)
                    i3 = j1;
                if(i3 >= 256 && i3 < 512)
                {
                    Model class30_sub2_sub4_sub6_3 = CharModel.charactermodels[i3 - 256].method538(false);
                    if(class30_sub2_sub4_sub6_3 != null)
                        aclass30_sub2_sub4_sub6[j2++] = class30_sub2_sub4_sub6_3;
                }
                if(i3 >= 512)
                {
                    Model class30_sub2_sub4_sub6_4 = ItemDefinition.getItemDefinition(i3 - 512).toModel_id(false, gender);
                    if(class30_sub2_sub4_sub6_4 != null)
                        aclass30_sub2_sub4_sub6[j2++] = class30_sub2_sub4_sub6_4;
                }
            }

            class30_sub2_sub4_sub6_1 = new Model(aclass30_sub2_sub4_sub6, j2);
            for(int j3 = 0; j3 < 5; j3++)
                if(anIntArray1700[j3] != 0)
                {
                    class30_sub2_sub4_sub6_1.setTriangleColors(Main.anIntArrayArray1003[j3][0], Main.anIntArrayArray1003[j3][anIntArray1700[j3]]);
                    if(j3 == 1)
                        class30_sub2_sub4_sub6_1.setTriangleColors(Main.anIntArray1204[0], Main.anIntArray1204[anIntArray1700[j3]]);
                }

            class30_sub2_sub4_sub6_1.setVertexTriangleGroups();
            class30_sub2_sub4_sub6_1.setLightingVectors(64, 850, -30, -50, -30, true);
            aClass12_1704.put(class30_sub2_sub4_sub6_1, l);
            aLong1697 = l;
        }
        if(aBoolean1699)
            return class30_sub2_sub4_sub6_1;
        Model class30_sub2_sub4_sub6_2 = Model.aActor_Sub6_1621;
        class30_sub2_sub4_sub6_2.method464(7, class30_sub2_sub4_sub6_1, AnimFrame.method532(k, false) & AnimFrame.method532(i1, false));
        if(k != -1 && i1 != -1)
            class30_sub2_sub4_sub6_2.method471(-20491, AnimSequence.animationsequences[super.animid_request].anIntArray357, i1, k);
        else
        if(k != -1)
            class30_sub2_sub4_sub6_2.applyAnimationFrame(k);
        class30_sub2_sub4_sub6_2.method466(false);
        class30_sub2_sub4_sub6_2.trianglegroups = null;
        class30_sub2_sub4_sub6_2.vertexgroups = null;
        return class30_sub2_sub4_sub6_2;
    }

    public boolean hasDefinition(boolean flag)
    {
        return updated;
    }

    public Model method453(byte byte0)
    {
        if(byte0 != -41)
            anInt1715 = 132;
        if(!updated)
            return null;
        if(pnpc != null)
            return pnpc.method160(true);
        boolean flag = false;
        for(int i = 0; i < 12; i++)
        {
            int j = appearances[i];
            if(j >= 256 && j < 512 && !CharModel.charactermodels[j - 256].method539(false))
                flag = true;
            if(j >= 512 && !ItemDefinition.getItemDefinition(j - 512).method192(-2836, gender))
                flag = true;
        }

        if(flag)
            return null;
        Model aclass30_sub2_sub4_sub6[] = new Model[12];
        int k = 0;
        for(int l = 0; l < 12; l++)
        {
            int i1 = appearances[l];
            if(i1 >= 256 && i1 < 512)
            {
                Model class30_sub2_sub4_sub6_1 = CharModel.charactermodels[i1 - 256].method540(0);
                if(class30_sub2_sub4_sub6_1 != null)
                    aclass30_sub2_sub4_sub6[k++] = class30_sub2_sub4_sub6_1;
            }
            if(i1 >= 512)
            {
                Model class30_sub2_sub4_sub6_2 = ItemDefinition.getItemDefinition(i1 - 512).method194(-705, gender);
                if(class30_sub2_sub4_sub6_2 != null)
                    aclass30_sub2_sub4_sub6[k++] = class30_sub2_sub4_sub6_2;
            }
        }

        Model class30_sub2_sub4_sub6 = new Model(aclass30_sub2_sub4_sub6, k);
        for(int j1 = 0; j1 < 5; j1++)
            if(anIntArray1700[j1] != 0)
            {
                class30_sub2_sub4_sub6.setTriangleColors(Main.anIntArrayArray1003[j1][0], Main.anIntArrayArray1003[j1][anIntArray1700[j1]]);
                if(j1 == 1)
                    class30_sub2_sub4_sub6.setTriangleColors(Main.anIntArray1204[0], Main.anIntArray1204[anIntArray1700[j1]]);
            }

        return class30_sub2_sub4_sub6;
    }

    public Player() {
        aLong1697 = -1L;
        aBoolean1699 = false;
        anIntArray1700 = new int[5];
        updated = false;
        anInt1715 = 9;
        aBoolean1716 = true;
        appearances = new int[12];
    }

    public long aLong1697;
    public NPCDefinition pnpc;
    public boolean aBoolean1699;
    public int anIntArray1700[];
    public int anInt1701;
    public int gender;
    public String name;
    public static Cache aClass12_1704 = new Cache(260);
    public int combatlevel;
    public int active_headicons;
    public int anInt1707;
    public int anInt1708;
    public int tileheight$;
    public boolean updated;
    public int anInt1711;
    public int anInt1712;
    public int anInt1713;
    public Model aActor_Sub6_1714;
    public int anInt1715;
    public boolean aBoolean1716;
    public int appearances[];
    public long playermodel_index;
    public int anInt1719;
    public int anInt1720;
    public int anInt1721;
    public int anInt1722;
    public int skilltotal;

}
