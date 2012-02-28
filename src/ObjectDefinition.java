// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

import sign.Signlink;

public class ObjectDefinition {

    public static ObjectDefinition getObjectDefinition(int i) {
        for(int j = 0; j < 20; j++)
            if(definition_stack[j].id == i)
                return definition_stack[j];
        stack_offset = (stack_offset + 1) % 20;
        ObjectDefinition class46 = definition_stack[stack_offset];
        aClass30_Sub2_Sub2_753.offset = offsets[i];
        class46.id = i;
        class46.setDefaults();
        class46.method582(true, aClass30_Sub2_Sub2_753);
        return class46;
    }

    public void setDefaults() {
        anIntArray773 = null;
        anIntArray776 = null;
        aString739 = null;
        aByteArray777 = null;
        anIntArray784 = null;
        anIntArray747 = null;
        anInt744 = 1;
        objsize = 1;
        aBoolean767 = true;
        aBoolean757 = true;
        aBoolean778 = false;
        aBoolean762 = false;
        aBoolean769 = false;
        aBoolean764 = false;
        anInt781 = -1;
        anInt775 = 16;
        aByte737 = 0;
        aByte742 = 0;
        aStringArray786 = null;
        mapsprite = -1;
        anInt758 = -1;
        aBoolean751 = false;
        aBoolean779 = true;
        anInt748 = 128;
        anInt772 = 128;
        anInt740 = 128;
        anInt768 = 0;
        anInt738 = 0;
        anInt745 = 0;
        anInt783 = 0;
        aBoolean736 = false;
        aBoolean766 = false;
        anInt760 = -1;
        anInt774 = -1;
        anInt749 = -1;
        anIntArray759 = null;
    }

    public void method574(OndemandHandler class42_sub1, int junk) {
        if(anIntArray773 == null)
            return;
        for(int j = 0; j < anIntArray773.length; j++)
            class42_sub1.request(0, anIntArray773[j] & 0xffff);
    }

    public static void dystory(int i)
    {
        aClass12_785 = null;
        object_modelstorage = null;
        offsets = null;
        definition_stack = null;
        aClass30_Sub2_Sub2_753 = null;
    }

    public static void initialize(ArchivePackage class44)
    {
        aClass30_Sub2_Sub2_753 = new ByteBuffer(class44.getArchive("loc.dat", null));
        ByteBuffer buffer0 = new ByteBuffer(class44.getArchive("loc.idx", null));
        anInt756 = buffer0.getUword();
        offsets = new int[anInt756];
        int i = 2;
        for(int j = 0; j < anInt756; j++)
        {
            offsets[j] = i;
            i += buffer0.getUword();
        }
        definition_stack = new ObjectDefinition[20];
        for(int k = 0; k < 20; k++)
            definition_stack[k] = new ObjectDefinition();
    }

    public boolean method577(int i, boolean flag)
    {
        if(!flag)
            throw new NullPointerException();
        if(anIntArray776 == null)
        {
            if(anIntArray773 == null)
                return true;
            if(i != 10)
                return true;
            boolean flag1 = true;
            for(int k = 0; k < anIntArray773.length; k++)
                flag1 &= Model.isLoaded(anIntArray773[k] & 0xffff);

            return flag1;
        }
        for(int j = 0; j < anIntArray776.length; j++)
            if(anIntArray776[j] == i)
                return Model.isLoaded(anIntArray773[j] & 0xffff);

        return true;
    }

    public Model method578(int type, int rotation, int k, int l, int i1, int j1, int anim_something)
    {
        Model class30_sub2_sub4_sub6 = method581(0, type, anim_something, rotation);
        if(class30_sub2_sub4_sub6 == null)
            return null;
        if(aBoolean762 || aBoolean769)
            class30_sub2_sub4_sub6 = new Model(aBoolean762, -819, aBoolean769, class30_sub2_sub4_sub6);
        if(aBoolean762)
        {
            int avr = (k + l + i1 + j1) / 4;
            for(int i2 = 0; i2 < class30_sub2_sub4_sub6.numverticies; i2++)
            {
                int posx = class30_sub2_sub4_sub6.verticiesx[i2];
                int posz = class30_sub2_sub4_sub6.verticiesz[i2];
                int d1 = k + ((l - k) * (posx + 64)) / 128;
                int d2 = j1 + ((i1 - j1) * (posx + 64)) / 128;
                int d3 = d1 + ((d2 - d1) * (posz + 64)) / 128;
                class30_sub2_sub4_sub6.verticiesy[i2] += d3 - avr;
            }

            class30_sub2_sub4_sub6.method467(false);
        }
        return class30_sub2_sub4_sub6;
    }

    public boolean method579(boolean flag)
    {
        if(anIntArray773 == null)
            return true;
        boolean flag1 = true;
        for(int i = 0; i < anIntArray773.length; i++)
            flag1 &= Model.isLoaded(anIntArray773[i] & 0xffff);

        if(!flag)
            throw new NullPointerException();
        else
            return flag1;
    }

    public ObjectDefinition method580(boolean flag)
    {
        if(!flag)
            throw new NullPointerException();
        int i = -1;
        if(anInt774 != -1)
        {
            VarbitFile class37 = VarbitFile.aClass37Array646[anInt774];
            int j = class37.config_num;
            int k = class37.anInt649;
            int l = class37.anInt650;
            int i1 = Main.BIT_MASKS[l - k];
            i = main.configstates[j] >> k & i1;
        } else
        if(anInt749 != -1)
            i = main.configstates[anInt749];
        if(i < 0 || i >= anIntArray759.length || anIntArray759[i] == -1)
            return null;
        else
            return getObjectDefinition(anIntArray759[i]);
    }

    public Model method581(int i, int j, int k, int l)
    {
        Model class30_sub2_sub4_sub6 = null;
        long l1;
        if(anIntArray776 == null)
        {
            if(j != 10)
                return null;
            l1 = (long) ((id << 6) + l) + ((long)(k + 1) << 32);
            Model class30_sub2_sub4_sub6_1 = (Model) object_modelstorage.get(l1);
            if(class30_sub2_sub4_sub6_1 != null)
                return class30_sub2_sub4_sub6_1;
            if(anIntArray773 == null)
                return null;
            boolean flag1 = aBoolean751 ^ (l > 3);
            int k1 = anIntArray773.length;
            for(int i2 = 0; i2 < k1; i2++)
            {
                int l2 = anIntArray773[i2];
                if(flag1)
                    l2 += 0x10000;
                class30_sub2_sub4_sub6 = (Model)aClass12_785.get(l2);
                if(class30_sub2_sub4_sub6 == null)
                {
                    class30_sub2_sub4_sub6 = Model.getModel(anInt770, l2 & 0xffff);
                    if(class30_sub2_sub4_sub6 == null)
                        return null;
                    if(flag1)
                        class30_sub2_sub4_sub6.rotateTri();
                    aClass12_785.put(class30_sub2_sub4_sub6, l2);
                }
                if(k1 > 1)
                    aActor_Sub6Array741[i2] = class30_sub2_sub4_sub6;
            }

            if(k1 > 1)
                class30_sub2_sub4_sub6 = new Model(aActor_Sub6Array741, k1);
        } else
        {
            int i1 = -1;
            for(int j1 = 0; j1 < anIntArray776.length; j1++)
            {
                if(anIntArray776[j1] != j)
                    continue;
                i1 = j1;
                break;
            }

            if(i1 == -1)
                return null;
            l1 = (long)((id << 6) + (i1 << 3) + l) + ((long)(k + 1) << 32);
            Model class30_sub2_sub4_sub6_2 = (Model)object_modelstorage.get(l1);
            if(class30_sub2_sub4_sub6_2 != null)
                return class30_sub2_sub4_sub6_2;
            int j2 = anIntArray773[i1];
            boolean flag3 = aBoolean751 ^ (l > 3);
            if(flag3)
                j2 += 0x10000;
            class30_sub2_sub4_sub6 = (Model)aClass12_785.get(j2);
            if(class30_sub2_sub4_sub6 == null)
            {
                class30_sub2_sub4_sub6 = Model.getModel(anInt770, j2 & 0xffff);
                if(class30_sub2_sub4_sub6 == null)
                    return null;
                if(flag3)
                    class30_sub2_sub4_sub6.rotateTri();
                aClass12_785.put(class30_sub2_sub4_sub6, j2);
            }
        }
        boolean flag;
        if(anInt748 != 128 || anInt772 != 128 || anInt740 != 128)
            flag = true;
        else
            flag = false;
        boolean flag2;
        if(anInt738 != 0 || anInt745 != 0 || anInt783 != 0)
            flag2 = true;
        else
            flag2 = false;
        Model class30_sub2_sub4_sub6_3 = new Model(class30_sub2_sub4_sub6,anIntArray784 == null, AnimFrame.method532(k, false), l == 0 && k == -1 && !flag && !flag2);
        if(k != -1)
        {
            class30_sub2_sub4_sub6_3.setVertexTriangleGroups();
            class30_sub2_sub4_sub6_3.applyAnimationFrame(k);
            class30_sub2_sub4_sub6_3.trianglegroups = null;
            class30_sub2_sub4_sub6_3.vertexgroups = null;
        }
        while(l-- > 0) 
            class30_sub2_sub4_sub6_3.rotate(360);
        if(anIntArray784 != null)
        {
            for(int k2 = 0; k2 < anIntArray784.length; k2++)
                class30_sub2_sub4_sub6_3.setTriangleColors(anIntArray784[k2], anIntArray747[k2]);

        }
        if(flag)
            class30_sub2_sub4_sub6_3.scaleModel(anInt748, anInt772, anInt740);
        if(flag2)
            class30_sub2_sub4_sub6_3.moveVertices(anInt738, anInt745, 16384, anInt783);
        class30_sub2_sub4_sub6_3.setLightingVectors(64 + aByte737, 768 + aByte742 * 5, -50, -10, -50, !aBoolean769);
        if(anInt760 == 1)
            class30_sub2_sub4_sub6_3.anInt1654 = ((Entity) (class30_sub2_sub4_sub6_3)).miny;
        object_modelstorage.put(class30_sub2_sub4_sub6_3, l1);
        if(i != 0)
            anInt743 = 422;
        return class30_sub2_sub4_sub6_3;
    }

    public void method582(boolean flag, ByteBuffer buffer0)
    {
        if(!flag)
            anInt750 = 217;
        int i = -1;
label0:
        do
        {
            int j;
            do
            {
                j = buffer0.getUbyte();
                if(j == 0)
                    break label0;
                if(j == 1)
                {
                    int k = buffer0.getUbyte();
                    if(k > 0)
                        if(anIntArray773 == null || lowmemory)
                        {
                            anIntArray776 = new int[k];
                            anIntArray773 = new int[k];
                            for(int k1 = 0; k1 < k; k1++)
                            {
                                anIntArray773[k1] = buffer0.getUword();
                                anIntArray776[k1] = buffer0.getUbyte();
                            }

                        } else
                        {
                            buffer0.offset += k * 3;
                        }
                } else
                if(j == 2)
                    aString739 = buffer0.getString();
                else
                if(j == 3)
                    aByteArray777 = buffer0.getStringBytes();
                else
                if(j == 5)
                {
                    int l = buffer0.getUbyte();
                    if(l > 0)
                        if(anIntArray773 == null || lowmemory)
                        {
                            anIntArray776 = null;
                            anIntArray773 = new int[l];
                            for(int l1 = 0; l1 < l; l1++)
                                anIntArray773[l1] = buffer0.getUword();

                        } else
                        {
                            buffer0.offset += l * 2;
                        }
                } else
                if(j == 14)
                    anInt744 = buffer0.getUbyte();
                else
                if(j == 15)
                    objsize = buffer0.getUbyte();
                else
                if(j == 17)
                    aBoolean767 = false;
                else
                if(j == 18)
                    aBoolean757 = false;
                else
                if(j == 19)
                {
                    i = buffer0.getUbyte();
                    if(i == 1)
                        aBoolean778 = true;
                } else
                if(j == 21)
                    aBoolean762 = true;
                else
                if(j == 22)
                    aBoolean769 = true;
                else
                if(j == 23)
                    aBoolean764 = true;
                else
                if(j == 24)
                {
                    anInt781 = buffer0.getUword();
                    if(anInt781 == 65535)
                        anInt781 = -1;
                } else
                if(j == 28)
                    anInt775 = buffer0.getUbyte();
                else
                if(j == 29)
                    aByte737 = buffer0.getByte();
                else
                if(j == 39)
                    aByte742 = buffer0.getByte();
                else
                if(j >= 30 && j < 39)
                {
                    if(aStringArray786 == null)
                        aStringArray786 = new String[5];
                    aStringArray786[j - 30] = buffer0.getString();
                    if(aStringArray786[j - 30].equalsIgnoreCase("hidden"))
                        aStringArray786[j - 30] = null;
                } else
                if(j == 40)
                {
                    int i1 = buffer0.getUbyte();
                    anIntArray784 = new int[i1];
                    anIntArray747 = new int[i1];
                    for(int i2 = 0; i2 < i1; i2++)
                    {
                        anIntArray784[i2] = buffer0.getUword();
                        anIntArray747[i2] = buffer0.getUword();
                    }

                } else
                if(j == 60)
                    mapsprite = buffer0.getUword();
                else
                if(j == 62)
                    aBoolean751 = true;
                else
                if(j == 64)
                    aBoolean779 = false;
                else
                if(j == 65)
                    anInt748 = buffer0.getUword();
                else
                if(j == 66)
                    anInt772 = buffer0.getUword();
                else
                if(j == 67)
                    anInt740 = buffer0.getUword();
                else
                if(j == 68)
                    anInt758 = buffer0.getUword();
                else
                if(j == 69)
                    anInt768 = buffer0.getUbyte();
                else
                if(j == 70)
                    anInt738 = buffer0.getWord();
                else
                if(j == 71)
                    anInt745 = buffer0.getWord();
                else
                if(j == 72)
                    anInt783 = buffer0.getWord();
                else
                if(j == 73)
                    aBoolean736 = true;
                else
                if(j == 74)
                {
                    aBoolean766 = true;
                } else
                {
                    if(j != 75)
                        continue;
                    anInt760 = buffer0.getUbyte();
                }
                continue label0;
            } while(j != 77);
            anInt774 = buffer0.getUword();
            if(anInt774 == 65535)
                anInt774 = -1;
            anInt749 = buffer0.getUword();
            if(anInt749 == 65535)
                anInt749 = -1;
            int j1 = buffer0.getUbyte();
            anIntArray759 = new int[j1 + 1];
            for(int j2 = 0; j2 <= j1; j2++)
            {
                anIntArray759[j2] = buffer0.getUword();
                if(anIntArray759[j2] == 65535)
                    anIntArray759[j2] = -1;
            }

        } while(true);
        if(i == -1)
        {
            aBoolean778 = false;
            if(anIntArray773 != null && (anIntArray776 == null || anIntArray776[0] == 10))
                aBoolean778 = true;
            if(aStringArray786 != null)
                aBoolean778 = true;
        }
        if(aBoolean766)
        {
            aBoolean767 = false;
            aBoolean757 = false;
        }
        if(anInt760 == -1)
            anInt760 = aBoolean767 ? 1 : 0;
    }

    public ObjectDefinition()
    {
        anInt743 = 9;
        id = -1;
        aBoolean763 = true;
        anInt770 = 9;
    }

    public boolean aBoolean736;
    public byte aByte737;
    public int anInt738;
    public String aString739;
    public int anInt740;
    public static Model aActor_Sub6Array741[] = new Model[4];
    public byte aByte742;
    public int anInt743;
    public int anInt744;
    public int anInt745;
    public int mapsprite;
    public int anIntArray747[];
    public int anInt748;
    public int anInt749;
    public int anInt750;
    public boolean aBoolean751;
    public static boolean lowmemory;
    public static ByteBuffer aClass30_Sub2_Sub2_753;
    public int id;
    public static int offsets[];
    public static int anInt756;
    public boolean aBoolean757;
    public int anInt758;
    public int anIntArray759[];
    public int anInt760;
    public int objsize;
    public boolean aBoolean762;
    public boolean aBoolean763;
    public boolean aBoolean764;
    public static Main main;
    public boolean aBoolean766;
    public boolean aBoolean767;
    public int anInt768;
    public boolean aBoolean769;
    public int anInt770;
    public static int stack_offset;
    public int anInt772;
    public int anIntArray773[];
    public int anInt774;
    public int anInt775;
    public int anIntArray776[];
    public byte aByteArray777[];
    public boolean aBoolean778;
    public boolean aBoolean779;
    public static Cache object_modelstorage = new Cache(30);
    public int anInt781;
    public static ObjectDefinition definition_stack[];
    public int anInt783;
    public int anIntArray784[];
    public static Cache aClass12_785 = new Cache(500);
    public String aStringArray786[];

}
