// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

import java.io.PrintStream;
import sign.Signlink;

public class CharModel {

    public static void method535(int junk, ArchivePackage class44) {
        ByteBuffer buffer = new ByteBuffer(class44.getArchive("idk.dat", null));
        anInt655 = buffer.getUword();
        if(charactermodels == null)
            charactermodels = new CharModel[anInt655];
        for(int j = 0; j < anInt655; j++) {
            if(charactermodels[j] == null)
                charactermodels[j] = new CharModel();
            charactermodels[j].method536(true, buffer);
        }
    }

    public void method536(boolean flag, ByteBuffer buffer0)
    {
        if(!flag)
            throw new NullPointerException();
        do
        {
            int i = buffer0.getUbyte();
            if(i == 0)
                return;
            if(i == 1)
                anInt657 = buffer0.getUbyte();
            else
            if(i == 2)
            {
                int j = buffer0.getUbyte();
                anIntArray658 = new int[j];
                for(int k = 0; k < j; k++)
                    anIntArray658[k] = buffer0.getUword();

            } else
            if(i == 3)
                aBoolean662 = true;
            else
            if(i >= 40 && i < 50)
                anIntArray659[i - 40] = buffer0.getUword();
            else
            if(i >= 50 && i < 60)
                anIntArray660[i - 50] = buffer0.getUword();
            else
            if(i >= 60 && i < 70)
                anIntArray661[i - 60] = buffer0.getUword();
            else
                System.out.println("Error unrecognised config code: " + i);
        } while(true);
    }

    public boolean method537(byte byte0)
    {
        if(anIntArray658 == null)
            return true;
        boolean flag = true;
        if(byte0 == 2)
        {
            byte0 = 0;
        } else
        {
            for(int i = 1; i > 0; i++);
        }
        for(int j = 0; j < anIntArray658.length; j++)
            if(!Model.isLoaded(anIntArray658[j]))
                flag = false;

        return flag;
    }

    public Model method538(boolean flag)
    {
        if(flag)
            throw new NullPointerException();
        if(anIntArray658 == null)
            return null;
        Model aclass30_sub2_sub4_sub6[] = new Model[anIntArray658.length];
        for(int i = 0; i < anIntArray658.length; i++)
            aclass30_sub2_sub4_sub6[i] = Model.getModel(anInt654, anIntArray658[i]);

        Model class30_sub2_sub4_sub6;
        if(aclass30_sub2_sub4_sub6.length == 1)
            class30_sub2_sub4_sub6 = aclass30_sub2_sub4_sub6[0];
        else
            class30_sub2_sub4_sub6 = new Model(aclass30_sub2_sub4_sub6, aclass30_sub2_sub4_sub6.length);
        for(int j = 0; j < 6; j++)
        {
            if(anIntArray659[j] == 0)
                break;
            class30_sub2_sub4_sub6.setTriangleColors(anIntArray659[j], anIntArray660[j]);
        }

        return class30_sub2_sub4_sub6;
    }

    public boolean method539(boolean flag)
    {
        if(flag)
            throw new NullPointerException();
        boolean flag1 = true;
        for(int i = 0; i < 5; i++)
            if(anIntArray661[i] != -1 && !Model.isLoaded(anIntArray661[i]))
                flag1 = false;

        return flag1;
    }

    public Model method540(int i)
    {
        if(i != 0)
            throw new NullPointerException();
        Model aclass30_sub2_sub4_sub6[] = new Model[5];
        int j = 0;
        for(int k = 0; k < 5; k++)
            if(anIntArray661[k] != -1)
                aclass30_sub2_sub4_sub6[j++] = Model.getModel(anInt654, anIntArray661[k]);

        Model class30_sub2_sub4_sub6 = new Model(aclass30_sub2_sub4_sub6, j);
        for(int l = 0; l < 6; l++)
        {
            if(anIntArray659[l] == 0)
                break;
            class30_sub2_sub4_sub6.setTriangleColors(anIntArray659[l], anIntArray660[l]);
        }

        return class30_sub2_sub4_sub6;
    }

    public CharModel()
    {
        anInt654 = 9;
        anInt657 = -1;
        anIntArray659 = new int[6];
        anIntArray660 = new int[6];
        aBoolean662 = false;
    }

    public int anInt654;
    public static int anInt655;
    public static CharModel charactermodels[];
    public int anInt657;
    public int anIntArray658[];
    public int anIntArray659[];
    public int anIntArray660[];
    public int anIntArray661[] = {
        -1, -1, -1, -1, -1
    };
    public boolean aBoolean662;
}
