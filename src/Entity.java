// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

import sign.Signlink;

public class Entity extends SubNode {

    public void drawModel(int i, int j, int k, int l, int i1, int j1, int k1, int l1, int i2) {
        Model class30_sub2_sub4_sub6 = getModel();
        if(class30_sub2_sub4_sub6 != null) {
            miny = ((Entity) (class30_sub2_sub4_sub6)).miny;
            class30_sub2_sub4_sub6.drawModel(i, j, k, l, i1, j1, k1, l1, i2);
        }
    }

    public Model getModel() {
        return null;
    }

    public Entity() {
        miny = 1000;
    }

    public GouraudVertex surfaces[];
    public int miny;
}
