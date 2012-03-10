// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

import java.io.PrintStream;
import sign.Signlink;

public class IdentityKit {

    public static void method535(int junk, ArchivePackage class44) {
        ByteBuffer buffer = new ByteBuffer(class44.getArchive("idk.dat", null));
        anInt655 = buffer.getUword();
        if(identityKits == null)
            identityKits = new IdentityKit[anInt655];
        for(int j = 0; j < anInt655; j++) {
            if(identityKits[j] == null)
                identityKits[j] = new IdentityKit();
            identityKits[j].initialize(buffer);
        }
    }

    public void initialize(ByteBuffer buffer)
    {
        do
        {
            int op = buffer.getUbyte();
            if(op == 0)
                return;
            if(op == 1)
                anInt657 = buffer.getUbyte();
            else
            if(op == 2)
            {
                int amountModels = buffer.getUbyte();
                models = new int[amountModels];
                for(int k = 0; k < amountModels; k++)
                    models[k] = buffer.getUword();

            } else
            if(op == 3)
                aBoolean662 = true;
            else
            if(op >= 40 && op < 50)
                triangleIds[op - 40] = buffer.getUword();
            else
            if(op >= 50 && op < 60)
                triangleColors[op - 50] = buffer.getUword();
            else
            if(op >= 60 && op < 70)
                anIntArray661[op - 60] = buffer.getUword();
            else
                System.out.println("Error unrecognised config code: " + op);
        } while(true);
    }

    public boolean method537(byte byte0)
    {
        if(models == null)
            return true;
        boolean flag = true;
        if(byte0 == 2)
        {
            byte0 = 0;
        } else
        {
            for(int i = 1; i > 0; i++);
        }
        for(int j = 0; j < models.length; j++)
            if(!Model.isLoaded(models[j]))
                flag = false;

        return flag;
    }

    public Model method538(boolean flag)
    {
        if(flag)
            throw new NullPointerException();
        if(models == null)
            return null;
        Model modelArray[] = new Model[models.length];
        for(int i = 0; i < models.length; i++)
            modelArray[i] = Model.getModel(models[i]);

        Model model;
        if(modelArray.length == 1)
            model = modelArray[0];
        else
            model = new Model(modelArray, modelArray.length);
        for(int j = 0; j < 6; j++)
        {
            if(triangleIds[j] == 0)
                break;
            model.setTriangleColors(triangleIds[j], triangleColors[j]);
        }

        return model;
    }

    public boolean hasModels(boolean flag)
    {
        if(flag)
            throw new NullPointerException();
        boolean flag1 = true;
        for(int i = 0; i < 5; i++)
            if(anIntArray661[i] != -1 && !Model.isLoaded(anIntArray661[i]))
                flag1 = false;

        return flag1;
    }

    public Model getModel()
    {
        Model modelArray[] = new Model[5];
        int off = 0;
        for(int i = 0; i < 5; i++)
            if(anIntArray661[i] != -1)
                modelArray[off++] = Model.getModel(anIntArray661[i]);

        Model model = new Model(modelArray, off);
        for(int i = 0; i < 6; i++)
        {
            if(triangleIds[i] == 0)
                break;
            model.setTriangleColors(triangleIds[i], triangleColors[i]);
        }

        return model;
    }

    public IdentityKit()
    {
        anInt654 = 9;
        anInt657 = -1;
        triangleIds = new int[6];
        triangleColors = new int[6];
        aBoolean662 = false;
    }

    public int anInt654;
    public static int anInt655;
    public static IdentityKit identityKits[];
    public int anInt657;
    public int models[];
    public int triangleIds[];
    public int triangleColors[];
    public int anIntArray661[] = {
        -1, -1, -1, -1, -1
    };
    public boolean aBoolean662;
}
