// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

import java.io.PrintStream;
import sign.Signlink;

public class SpotAnim {

    public static void method264(int i, ArchivePackage class44) {
        ByteBuffer buffer0 = new ByteBuffer(class44.getArchive("spotanim.dat", null));
        if(i != 0)
            aBoolean401 = !aBoolean401;
        anInt402 = buffer0.getUword();
        if(aClass23Array403 == null)
            aClass23Array403 = new SpotAnim[anInt402];
        for(int j = 0; j < anInt402; j++) {
            if(aClass23Array403[j] == null)
                aClass23Array403[j] = new SpotAnim();
            aClass23Array403[j].id = j;
            aClass23Array403[j].method265(true, buffer0);
        }

    }

    public void method265(boolean junk, ByteBuffer buffer0)
    {
        do
        {
            int i = buffer0.getUbyte();
            if(i == 0)
                return;
            if(i == 1)
                model_id = buffer0.getUword();
            else
            if(i == 2)
            {
                anim_id = buffer0.getUword();
                if(AnimSequence.animationsequences != null)
                    aClass20_407 = AnimSequence.animationsequences[anim_id];
            } else
            if(i == 4)
                anInt410 = buffer0.getUword();
            else
            if(i == 5)
                anInt411 = buffer0.getUword();
            else
            if(i == 6)
                rotate = buffer0.getUword();
            else
            if(i == 7)
                anInt413 = buffer0.getUbyte();
            else
            if(i == 8)
                anInt414 = buffer0.getUbyte();
            else
            if(i >= 40 && i < 50)
                anIntArray408[i - 40] = buffer0.getUword();
            else
            if(i >= 50 && i < 60)
                anIntArray409[i - 50] = buffer0.getUword();
            else
                System.out.println("Error unrecognised spotanim config code: " + i);
        } while(true);
    }

    public Model method266()
    {
        Model class30_sub2_sub4_sub6 = (Model) aClass12_415.get(id);
        if(class30_sub2_sub4_sub6 != null)
            return class30_sub2_sub4_sub6;
        class30_sub2_sub4_sub6 = Model.getModel((int) 'j', model_id);
        if(class30_sub2_sub4_sub6 == null)
            return null;
        for(int i = 0; i < 6; i++)
            if(anIntArray408[0] != 0)
                class30_sub2_sub4_sub6.setTriangleColors(anIntArray408[i], anIntArray409[i]);
        aClass12_415.put(class30_sub2_sub4_sub6, id);
        return class30_sub2_sub4_sub6;
    }

    public SpotAnim()
    {
        anInt400 = 9;
        anim_id = -1;
        anIntArray408 = new int[6];
        anIntArray409 = new int[6];
        anInt410 = 128;
        anInt411 = 128;
    }

    public int anInt400;
    public static boolean aBoolean401 = true;
    public static int anInt402;
    public static SpotAnim aClass23Array403[];
    public int id;
    public int model_id;
    public int anim_id;
    public AnimSequence aClass20_407;
    public int anIntArray408[];
    public int anIntArray409[];
    public int anInt410;
    public int anInt411;
    public int rotate;
    public int anInt413;
    public int anInt414;
    public static Cache aClass12_415 = new Cache(30);

}
