// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

import sign.Signlink;

public class StillGraphic extends Entity
{

    public StillGraphic(int i, int j, int k, int l, int i1, int j1, int k1, int l1) {
        aBoolean1565 = true;
        anInt1566 = 9;
        destroy_gfx = false;
        aClass23_1568 = SpotAnim.aClass23Array403[i1];
        anInt1560 = i;
        anInt1561 = l1;
        anInt1562 = k1;
        anInt1563 = j1;
        runcycle = j + l;
        if(k != 6) {
            throw new NullPointerException();
        } else
        {
            destroy_gfx = false;
            return;
        }
    }

    public Model getModel()
    {
        Model class30_sub2_sub4_sub6 = aClass23_1568.method266();
        if(class30_sub2_sub4_sub6 == null)
            return null;
        int frameid = aClass23_1568.aClass20_407.anIntArray353[anInt1569];
        Model model = new Model(class30_sub2_sub4_sub6,true, AnimFrame.method532(frameid, false), false);
        if(!destroy_gfx)
        {
            model.setVertexTriangleGroups();
            model.applyAnimationFrame(frameid);
            model.trianglegroups = null;
            model.vertexgroups = null;
        }
        if(aClass23_1568.anInt410 != 128 || aClass23_1568.anInt411 != 128)
            model.scaleModel(aClass23_1568.anInt410, aClass23_1568.anInt411, aClass23_1568.anInt410);
        if(aClass23_1568.rotate != 0)
        {
            if(aClass23_1568.rotate == 90)
                model.rotate(360);
            if(aClass23_1568.rotate == 180)
            {
                model.rotate(360);
                model.rotate(360);
            }
            if(aClass23_1568.rotate == 270)
            {
                model.rotate(360);
                model.rotate(360);
                model.rotate(360);
            }
        }
        model.setLightingVectors(64 + aClass23_1568.anInt413, 850 + aClass23_1568.anInt414, -30, -50, -30, true);
        return model;
    }

    public void method454(int i, boolean junk)
    {
        for(anInt1570 += i; anInt1570 > aClass23_1568.aClass20_407.method258(anInt1569, (byte)-39);)
        {
            anInt1570 -= aClass23_1568.aClass20_407.method258(anInt1569, (byte)-39) + 1;
            anInt1569++;
            if(anInt1569 >= aClass23_1568.aClass20_407.anInt352 && (anInt1569 < 0 || anInt1569 >= aClass23_1568.aClass20_407.anInt352))
            {
                anInt1569 = 0;
                destroy_gfx = true;
            }
        }

    }

    public int anInt1560;
    public int anInt1561;
    public int anInt1562;
    public int anInt1563;
    public int runcycle;
    public boolean aBoolean1565;
    public int anInt1566;
    public boolean destroy_gfx;
    public SpotAnim aClass23_1568;
    public int anInt1569;
    public int anInt1570;
}
