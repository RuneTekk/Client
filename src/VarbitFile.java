// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

import java.io.PrintStream;
import sign.Signlink;

public class VarbitFile {

    public static void unpackArchives(int i, ArchivePackage class44) {
        if(i != 0)
            anInt644 = 91;
        ByteBuffer buffer0 = new ByteBuffer(class44.getArchive("varbit.dat", null));
        anInt645 = buffer0.getUword();
        if(varbitArray == null)
            varbitArray = new VarbitFile[anInt645];
        for(int j = 0; j < anInt645; j++) {
            if(varbitArray[j] == null)
                varbitArray[j] = new VarbitFile();
            varbitArray[j].method534(buffer0, false, j);
            if(varbitArray[j].aBoolean651)
                VarpFile.aClass41Array701[varbitArray[j].config_num].aBoolean713 = true;
        }

        if(buffer0.offset != buffer0.payload.length)
            System.out.println("varbit load mismatch");
    }

    public void method534(ByteBuffer buffer0, boolean flag, int i)
    {
        if(flag)
            return;
        do
        {
            int j = buffer0.getUbyte();
            if(j == 0)
                return;
            if(j == 1)
            {
                config_num = buffer0.getUword();
                anInt649 = buffer0.getUbyte();
                anInt650 = buffer0.getUbyte();
            } else
            if(j == 10)
                aString647 = buffer0.getString();
            else
            if(j == 2)
                aBoolean651 = true;
            else
            if(j == 3)
                anInt652 = buffer0.getDword();
            else
            if(j == 4)
                anInt653 = buffer0.getDword();
            else
                System.out.println("Error unrecognised config code: " + j);
        } while(true);
    }

    public VarbitFile()
    {
        aBoolean651 = false;
        anInt652 = -1;
    }

    public static int anInt644;
    public static int anInt645;
    public static VarbitFile varbitArray[];
    public String aString647;
    public int config_num;
    public int anInt649;
    public int anInt650;
    public boolean aBoolean651;
    public int anInt652;
    public int anInt653;
}
