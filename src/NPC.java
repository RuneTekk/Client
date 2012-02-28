// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

import sign.Signlink;

public class NPC extends Mob {

    public Model method450(int i)
    {
        if(super.animid_request >= 0 && super.animdelay_request == 0)
        {
            int k = AnimSequence.animationsequences[super.animid_request].anIntArray353[super.anInt1527];
            int i1 = -1;
            if(super.anInt1517 >= 0 && super.anInt1517 != super.stand_anim)
                i1 = AnimSequence.animationsequences[super.anInt1517].anIntArray353[super.anInt1518];
            return definition.getModel(0, i1, k, AnimSequence.animationsequences[super.animid_request].anIntArray357);
        }
        int l = -1;
        if(super.anInt1517 >= 0)
            l = AnimSequence.animationsequences[super.anInt1517].anIntArray353[super.anInt1518];
        return definition.getModel(0, -1, l, null);
    }

    public Model getModel()
    {
        if(definition == null)
            return null;
        Model class30_sub2_sub4_sub6 = method450(0);
        if(class30_sub2_sub4_sub6 == null)
            return null;
        super.anInt1507 = ((Entity) (class30_sub2_sub4_sub6)).miny;
        if(super.anInt1520 != -1 && super.anInt1521 != -1)
        {
            SpotAnim class23 = SpotAnim.aClass23Array403[super.anInt1520];
            Model class30_sub2_sub4_sub6_1 = class23.method266();
            if(class30_sub2_sub4_sub6_1 != null)
            {
                int j = class23.aClass20_407.anIntArray353[super.anInt1521];
                Model class30_sub2_sub4_sub6_2 = new Model(class30_sub2_sub4_sub6_1,true, AnimFrame.method532(j, false), false);
                class30_sub2_sub4_sub6_2.moveVertices(0, -super.anInt1524, 16384, 0);
                class30_sub2_sub4_sub6_2.setVertexTriangleGroups();
                class30_sub2_sub4_sub6_2.applyAnimationFrame(j);
                class30_sub2_sub4_sub6_2.trianglegroups = null;
                class30_sub2_sub4_sub6_2.vertexgroups = null;
                if(class23.anInt410 != 128 || class23.anInt411 != 128)
                    class30_sub2_sub4_sub6_2.scaleModel(class23.anInt410, class23.anInt411, class23.anInt410);
                class30_sub2_sub4_sub6_2.setLightingVectors(64 + class23.anInt413, 850 + class23.anInt414, -30, -50, -30, true);
                Model aclass30_sub2_sub4_sub6[] = {
                    class30_sub2_sub4_sub6, class30_sub2_sub4_sub6_2
                };
                class30_sub2_sub4_sub6 = new Model(2, -819, true, aclass30_sub2_sub4_sub6);
            }
        }
        if(definition.npc_halftileoffsets == 1)
            class30_sub2_sub4_sub6.aBoolean1659 = true;
        return class30_sub2_sub4_sub6;
    }

    public boolean hasDefinition(boolean junk) {
        return definition != null;
    }

    public NPC()
    {
        
    }

    public NPCDefinition definition;
}
