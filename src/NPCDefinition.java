// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

import sign.Signlink;

public class NpcDefinition {

    public static NpcDefinition getNPCDefinition(int i) {
        for(int j = 0; j < 20; j++)
            if(aClass5Array80[j].id == (long)i)
                return aClass5Array80[j];
        anInt56 = (anInt56 + 1) % 20;
        NpcDefinition class5 = aClass5Array80[anInt56] = new NpcDefinition();
        npcbuffer.offset = anIntArray72[i];
        class5.id = i;
        class5.loadDefinition(true, npcbuffer);
        return class5;
    }

    public Model method160(boolean flag) {
        if(confignpcs != null)
        {
            NpcDefinition class5 = method161(anInt64);
            if(class5 == null)
                return null;
            else
                return class5.method160(true);
        }
        if(headmodels == null)
            return null;
        boolean flag1 = false;
        if(!flag)
            anInt64 = 303;
        for(int i = 0; i < headmodels.length; i++)
            if(!Model.isLoaded(headmodels[i]))
                flag1 = true;

        if(flag1)
            return null;
        Model aclass30_sub2_sub4_sub6[] = new Model[headmodels.length];
        for(int j = 0; j < headmodels.length; j++)
            aclass30_sub2_sub4_sub6[j] = Model.getModel(headmodels[j]);
        Model class30_sub2_sub4_sub6;
        if(aclass30_sub2_sub4_sub6.length == 1)
            class30_sub2_sub4_sub6 = aclass30_sub2_sub4_sub6[0];
        else
            class30_sub2_sub4_sub6 = new Model(aclass30_sub2_sub4_sub6, aclass30_sub2_sub4_sub6.length);
        if(recoloringtris != null)
        {
            for(int k = 0; k < recoloringtris.length; k++)
                class30_sub2_sub4_sub6.setTriangleColors(recoloringtris[k], recoloringcolors[k]);
        }
        return class30_sub2_sub4_sub6;
    }

    public NpcDefinition method161(int i)
    {
        int j = -1;
        if(i != 1834)
            aBoolean81 = !aBoolean81;
        if(varbitid != -1) {
            VarbitFile class37 = VarbitFile.varbitArray[varbitid];
            int k = class37.config_num;
            int l = class37.anInt649;
            int i1 = class37.anInt650;
            int j1 = Main.BIT_MASKS[i1 - l];
            j = main.configstates[k] >> l & j1;
        } else if(configid != -1)
            j = main.configstates[configid];
        if(j < 0 || j >= confignpcs.length || confignpcs[j] == -1)
            return null;
        else
            return getNPCDefinition(confignpcs[j]);
    }

    public static void unpackNpcDefs(ArchivePackage class44) {
        npcbuffer = new ByteBuffer(class44.getArchive("npc.dat", null));
        ByteBuffer buffer0 = new ByteBuffer(class44.getArchive("npc.idx", null));
        anInt62 = buffer0.getUword();
        anIntArray72 = new int[anInt62];
        int i = 2;
        for(int j = 0; j < anInt62; j++) {
            anIntArray72[j] = i;
            i += buffer0.getUword();
        }
        aClass5Array80 = new NpcDefinition[20];
        for(int k = 0; k < 20; k++)
            aClass5Array80[k] = new NpcDefinition();
    }

    public static void dystroy(int junk) {
        aClass12_95 = null;
        anIntArray72 = null;
        aClass5Array80 = null;
        npcbuffer = null;
    }

    public Model getModel(int junk, int j, int k, int ai[])
    {
        if(confignpcs != null)
        {
            NpcDefinition class5 = method161(anInt64);
            if(class5 == null)
                return null;
            else
                return class5.getModel(0, j, k, ai);
        }
        Model class30_sub2_sub4_sub6 = (Model)aClass12_95.get(id);
        if(class30_sub2_sub4_sub6 == null)
        {
            boolean flag = false;
            for(int i1 = 0; i1 < npcdef_models.length; i1++)
                if(!Model.isLoaded(npcdef_models[i1]))
                    flag = true;

            if(flag)
                return null;
            Model aclass30_sub2_sub4_sub6[] = new Model[npcdef_models.length];
            for(int j1 = 0; j1 < npcdef_models.length; j1++)
                aclass30_sub2_sub4_sub6[j1] = Model.getModel(npcdef_models[j1]);

            if(aclass30_sub2_sub4_sub6.length == 1)
                class30_sub2_sub4_sub6 = aclass30_sub2_sub4_sub6[0];
            else
                class30_sub2_sub4_sub6 = new Model(aclass30_sub2_sub4_sub6, aclass30_sub2_sub4_sub6.length);
            if(recoloringtris != null)
            {
                for(int k1 = 0; k1 < recoloringtris.length; k1++)
                    class30_sub2_sub4_sub6.setTriangleColors(recoloringtris[k1], recoloringcolors[k1]);

            }
            class30_sub2_sub4_sub6.setVertexTriangleGroups();
            class30_sub2_sub4_sub6.setLightingVectors(64 + lightvectorx, 850 + lightvectory, -30, -50, -30, true);
            aClass12_95.put(class30_sub2_sub4_sub6, id);
        }
        Model class30_sub2_sub4_sub6_1 = Model.aActor_Sub6_1621;
        class30_sub2_sub4_sub6_1.method464(7, class30_sub2_sub4_sub6, AnimFrame.method532(k, false) & AnimFrame.method532(j, false));
        if(k != -1 && j != -1)
            class30_sub2_sub4_sub6_1.method471(-20491, ai, j, k);
        else
        if(k != -1)
            class30_sub2_sub4_sub6_1.applyAnimationFrame(k);
        if(scalexz != 128 || scaley != 128)
            class30_sub2_sub4_sub6_1.scaleModel(scalexz, scaley, scalexz);
        class30_sub2_sub4_sub6_1.method466(false);
        class30_sub2_sub4_sub6_1.trianglegroups = null;
        class30_sub2_sub4_sub6_1.vertexgroups = null;
        if(npc_halftileoffsets == 1)
            class30_sub2_sub4_sub6_1.aBoolean1659 = true;
        return class30_sub2_sub4_sub6_1;
    }

    public void loadDefinition(boolean junk, ByteBuffer buffer0) {
        do {
            int i = buffer0.getUbyte();
            if(i == 0)
                return;
            if(i == 1)
            {
                int j = buffer0.getUbyte();
                npcdef_models = new int[j];
                for(int j1 = 0; j1 < j; j1++)
                    npcdef_models[j1] = buffer0.getUword();
            } else
            if(i == 2)
                name = buffer0.getString();
            else
            if(i == 3)
                examine = buffer0.getStringBytes();
            else
            if(i == 12)
                npc_halftileoffsets = buffer0.getByte();
            else
            if(i == 13)
                npcdef_standanim = buffer0.getUword();
            else
            if(i == 14)
                npcdef_walkanim = buffer0.getUword();
            else
            if(i == 17)
            {
                npcdef_walkanim = buffer0.getUword();
                npcdef_turn180anim = buffer0.getUword();
                npcdef_turn90cw = buffer0.getUword();
                npcdef_turn90ccw = buffer0.getUword();
            } else
            if(i >= 30 && i < 40)
            {
                if(options == null)
                    options = new String[5];
                options[i - 30] = buffer0.getString();
                if(options[i - 30].equalsIgnoreCase("hidden"))
                    options[i - 30] = null;
            } else
            if(i == 40) {
                int k = buffer0.getUbyte();
                recoloringtris = new int[k];
                recoloringcolors = new int[k];
                for(int k1 = 0; k1 < k; k1++)
                {
                    recoloringtris[k1] = buffer0.getUword();
                    recoloringcolors[k1] = buffer0.getUword();
                }

            } else if(i == 60) {
                int l = buffer0.getUbyte();
                headmodels = new int[l];
                for(int l1 = 0; l1 < l; l1++)
                    headmodels[l1] = buffer0.getUword();

            } else
            if(i == 90)
                anInt96 = buffer0.getUword();
            else
            if(i == 91)
                anInt71 = buffer0.getUword();
            else
            if(i == 92)
                anInt90 = buffer0.getUword();
            else
            if(i == 93)
                displaymapdot = false;
            else
            if(i == 95)
                level = buffer0.getUword();
            else
            if(i == 97)
                scalexz = buffer0.getUword();
            else
            if(i == 98)
                scaley = buffer0.getUword();
            else
            if(i == 99)
                placementpriority = true;
            else
            if(i == 100)
                lightvectorx = buffer0.getByte();
            else
            if(i == 101)
                lightvectory = buffer0.getByte() * 5;
            else
            if(i == 102)
                npcheadicon = buffer0.getUword();
            else
            if(i == 103)
                spawndirection = buffer0.getUword();
            else
            if(i == 106)
            {
                varbitid = buffer0.getUword();
                if(varbitid == 65535)
                    varbitid = -1;
                configid = buffer0.getUword();
                if(configid == 65535)
                    configid = -1;
                int i1 = buffer0.getUbyte();
                confignpcs = new int[i1 + 1];
                for(int i2 = 0; i2 <= i1; i2++)
                {
                    confignpcs[i2] = buffer0.getUword();
                    if(confignpcs[i2] == 65535)
                        confignpcs[i2] = -1;
                }

            } else
            if(i == 107)
                isvisible = false;
        } while(true);
    }

    public NpcDefinition() {
        npcdef_turn90ccw = -1;
        varbitid = -1;
        npcdef_turn180anim = -1;
        configid = -1;
        level = -1;
        anInt63 = 9;
        anInt64 = 1834;
        npcdef_walkanim = -1;
        npc_halftileoffsets = 1;
        anInt69 = 9;
        anInt71 = -1;
        npcheadicon = -1;
        npcdef_standanim = -1;
        id = -1L;
        spawndirection = 32;
        aBoolean81 = false;
        npcdef_turn90cw = -1;
        isvisible = true;
        scaley = 128;
        displaymapdot = true;
        anInt90 = -1;
        scalexz = 128;
        placementpriority = false;
        anInt96 = -1;
    }

    public int npcdef_turn90ccw;
    public static int anInt56;
    public int varbitid;
    public int npcdef_turn180anim;
    public int configid;
    public static ByteBuffer npcbuffer;
    public int level;
    public static int anInt62;
    public int anInt63;
    public int anInt64;
    public String name;
    public String options[];
    public int npcdef_walkanim;
    public byte npc_halftileoffsets;
    public int anInt69;
    public int recoloringcolors[];
    public int anInt71;
    public static int anIntArray72[];
    public int headmodels[];
    public static int anInt74 = 748;
    public int npcheadicon;
    public int recoloringtris[];
    public int npcdef_standanim;
    public long id;
    public int spawndirection;
    public static NpcDefinition aClass5Array80[];
    public boolean aBoolean81;
    public static Main main;
    public int npcdef_turn90cw;
    public boolean isvisible;
    public int lightvectorx;
    public int scaley;
    public boolean displaymapdot;
    public int confignpcs[];
    public byte examine[];
    public int anInt90;
    public int scalexz;
    public int lightvectory;
    public boolean placementpriority;
    public int npcdef_models[];
    public static Cache aClass12_95 = new Cache(30);
    public int anInt96;

}
