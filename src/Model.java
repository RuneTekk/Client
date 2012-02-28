// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

import sign.Signlink;

public class Model extends Entity {

    public static void dystroy(int junk) {
        rawmodels = null;
        cliptriangle = null;
        aBooleanArray1664 = null;
        xvertex$ = null;
        yvertex$ = null;
        zvertex$ = null;
        xtexed$ = null;
        ytexed$ = null;
        ztexed$ = null;
        tridepthoffsets = null;
        trideptharray = null;
        tripricount = null;
        pritriangles = null;
        anIntArray1675 = null;
        anIntArray1676 = null;
        pridistances = null;
        sinetable = null;
        cosinetable = null;
        shadingtable = null;
        othercolortable = null;
    }

    public static void initialize(int i, NumericLoader class42)
    {
        rawmodels = new ModelData[i];
        numericloader = class42;
    }

    public static void unpackRawModel(byte abyte0[], int i, int j)
    {
        if(abyte0 == null)
        {
            ModelData class21 = rawmodels[j] = new ModelData();
            class21.vs = 0;
            class21.ts = 0;
            class21.tts = 0;
            return;
        }
        ByteBuffer buffer0 = new ByteBuffer(abyte0);
        buffer0.offset = abyte0.length - 18;
        ModelData class21_1 = rawmodels[j] = new ModelData();
        class21_1.payload = abyte0;
        class21_1.vs = buffer0.getUword();
        class21_1.ts = buffer0.getUword();
        class21_1.tts = buffer0.getUbyte();
        int hastopcodes = buffer0.getUbyte();
        int haspriorities = buffer0.getUbyte();
        int hasalpha = buffer0.getUbyte();
        int hastgroups = buffer0.getUbyte();
        int hasvgroups = buffer0.getUbyte();
        int l1 = buffer0.getUword();
        int i2 = buffer0.getUword();
        int j2 = buffer0.getUword();
        int k2 = buffer0.getUword();
        int l2 = 0;
        class21_1.vopcodeoffset = l2;
        l2 += class21_1.vs;
        class21_1.tposoffset = l2;
        l2 += class21_1.ts;
        class21_1.tprioffset = l2;
        if(haspriorities == 255)
            l2 += class21_1.ts;
        else
            class21_1.tprioffset = -haspriorities - 1;
        class21_1.tgroupoffset = l2;
        if(hastgroups == 1)
            l2 += class21_1.ts;
        else
            class21_1.tgroupoffset = -1;
        class21_1.topcodeoffset = l2;
        if(hastopcodes == 1)
            l2 += class21_1.ts;
        else
            class21_1.topcodeoffset = -1;
        class21_1.vgroupoffset = l2;
        if(hasvgroups == 1)
            l2 += class21_1.vs;
        else
            class21_1.vgroupoffset = -1;
        class21_1.talphaoffset = l2;
        if(hasalpha == 1)
            l2 += class21_1.ts;
        else
            class21_1.talphaoffset = -1;
        class21_1.tposopcodes = l2;
        l2 += k2;
        class21_1.tcoloroffset = l2;
        l2 += class21_1.ts * 2;
        class21_1.anInt384 = l2;
        l2 += class21_1.tts * 6;
        class21_1.vxoffset = l2;
        l2 += l1;
        class21_1.vyoffset = l2;
        l2 += i2;
        class21_1.vzoffset = l2;
        l2 += j2;
    }

    public static void remove(int i, int j)
    {
        rawmodels[j] = null;
    }

    public static Model getModel(int junk, int j)
    {
        if(rawmodels == null)
            return null;
        ModelData class21 = rawmodels[j];
        if(class21 == null) {
            numericloader.load(j);
            return null;
        } else {
            return new Model(j, -870);
        }
    }

    public static boolean isLoaded(int i) {
        if(rawmodels == null)
            return false;
        ModelData class21 = rawmodels[i];
        if(class21 == null) {
            numericloader.load(i);
            return false;
        } else {
            return true;
        }
    }

    public Model(boolean flag)
    {
        anInt1614 = 9;
        aBoolean1615 = false;
        anInt1616 = 360;
        anInt1617 = 1;
        aBoolean1618 = true;
        aBoolean1659 = false;
        if(!flag)
            aBoolean1618 = !aBoolean1618;
    }

    public Model(int i, int j)
    {
        anInt1614 = 9;
        aBoolean1615 = false;
        anInt1616 = 360;
        anInt1617 = 1;
        aBoolean1618 = true;
        aBoolean1659 = false;
        anInt1620++;
        ModelData rawmodel = rawmodels[i];
        numverticies = rawmodel.vs;
        numtriangles = rawmodel.ts;
        numtexedtriangles = rawmodel.tts;
        verticiesx = new int[numverticies];
        verticiesy = new int[numverticies];
        verticiesz = new int[numverticies];
        trivertex0 = new int[numtriangles];
        trivertex1 = new int[numtriangles];
        trivertex2 = new int[numtriangles];
        texedtrianglesx = new int[numtexedtriangles];
        texedtrianglesy = new int[numtexedtriangles];
        texedtrianglesz = new int[numtexedtriangles];
        if(rawmodel.vgroupoffset >= 0)
            vgroups = new int[numverticies];
        if(rawmodel.topcodeoffset >= 0)
            triangleopcodes = new int[numtriangles];
        if(rawmodel.tprioffset >= 0)
            tripriorities = new int[numtriangles];
        else
            anInt1641 = -rawmodel.tprioffset - 1;
        if(rawmodel.talphaoffset >= 0)
            trianglealphas = new int[numtriangles];
        if(rawmodel.tgroupoffset >= 0)
            tgroups = new int[numtriangles];
        trianglecolors = new int[numtriangles];
        ByteBuffer buffer0 = new ByteBuffer(rawmodel.payload);
        buffer0.offset = rawmodel.vopcodeoffset;
        ByteBuffer buffer1 = new ByteBuffer(rawmodel.payload);
        buffer1.offset = rawmodel.vxoffset;
        ByteBuffer buffer2 = new ByteBuffer(rawmodel.payload);
        buffer2.offset = rawmodel.vyoffset;
        ByteBuffer buffer3 = new ByteBuffer(rawmodel.payload);
        buffer3.offset = rawmodel.vzoffset;
        ByteBuffer buffer4 = new ByteBuffer(rawmodel.payload);
        buffer4.offset = rawmodel.vgroupoffset;
        int curx = 0;
        int cury = 0;
        int curz = 0;
        for(int j1 = 0; j1 < numverticies; j1++)
        {
            int opcode = buffer0.getUbyte();
            int translatex = 0;
            if((opcode & 1) != 0)
                translatex = buffer1.getSmartA();
            int translatey = 0;
            if((opcode & 2) != 0)
                translatey = buffer2.getSmartA();
            int translatez = 0;
            if((opcode & 4) != 0)
                translatez = buffer3.getSmartA();
            verticiesx[j1] = curx + translatex;
            verticiesy[j1] = cury + translatey;
            verticiesz[j1] = curz + translatez;
            curx = verticiesx[j1];
            cury = verticiesy[j1];
            curz = verticiesz[j1];
            if(vgroups != null)
                vgroups[j1] = buffer4.getUbyte();
        }

        buffer0.offset = rawmodel.tcoloroffset;
        buffer1.offset = rawmodel.topcodeoffset;
        buffer2.offset = rawmodel.tprioffset;
        buffer3.offset = rawmodel.talphaoffset;
        buffer4.offset = rawmodel.tgroupoffset;
        for(int l1 = 0; l1 < numtriangles; l1++)
        {
            trianglecolors[l1] = buffer0.getUword();
            if(triangleopcodes != null)
                triangleopcodes[l1] = buffer1.getUbyte();
            if(tripriorities != null)
                tripriorities[l1] = buffer2.getUbyte();
            if(trianglealphas != null)
                trianglealphas[l1] = buffer3.getUbyte();
            if(tgroups != null)
                tgroups[l1] = buffer4.getUbyte();
        }

        buffer0.offset = rawmodel.tposopcodes;
        buffer1.offset = rawmodel.tposoffset;
        int tcurx = 0;
        int tcury = 0;
        int tcurz = 0;
        int op = 0;
        for(int l3 = 0; l3 < numtriangles; l3++)
        {
            int opcode = buffer1.getUbyte();
            if(opcode == 1)
            {
                tcurx = buffer0.getSmartA() + op;
                op = tcurx;
                tcury = buffer0.getSmartA() + op;
                op = tcury;
                tcurz = buffer0.getSmartA() + op;
                op = tcurz;
                trivertex0[l3] = tcurx;
                trivertex1[l3] = tcury;
                trivertex2[l3] = tcurz;
            }
            if(opcode == 2)
            {
                tcurx = tcurx;
                tcury = tcurz;
                tcurz = buffer0.getSmartA() + op;
                op = tcurz;
                trivertex0[l3] = tcurx;
                trivertex1[l3] = tcury;
                trivertex2[l3] = tcurz;
            }
            if(opcode == 3)
            {
                tcurx = tcurz;
                tcury = tcury;
                tcurz = buffer0.getSmartA() + op;
                op = tcurz;
                trivertex0[l3] = tcurx;
                trivertex1[l3] = tcury;
                trivertex2[l3] = tcurz;
            }
            if(opcode == 4)
            {
                int k4 = tcurx;
                tcurx = tcury;
                tcury = k4;
                tcurz = buffer0.getSmartA() + op;
                op = tcurz;
                trivertex0[l3] = tcurx;
                trivertex1[l3] = tcury;
                trivertex2[l3] = tcurz;
            }
        }

        buffer0.offset = rawmodel.anInt384;
        for(int j4 = 0; j4 < numtexedtriangles; j4++)
        {
            texedtrianglesx[j4] = buffer0.getUword();
            texedtrianglesy[j4] = buffer0.getUword();
            texedtrianglesz[j4] = buffer0.getUword();
        }

    }

    public Model(Model models[], int amountmodels)
    {
        anInt1614 = 9;
        aBoolean1615 = false;
        anInt1616 = 360;
        anInt1617 = 1;
        aBoolean1618 = true;
        aBoolean1659 = false;
        anInt1620++;
        boolean flag = false;
        boolean flag1 = false;
        boolean flag2 = false;
        boolean flag3 = false;
        numverticies = 0;
        numtriangles = 0;
        numtexedtriangles = 0;
        anInt1641 = -1;
        for(int k = 0; k < amountmodels; k++)
        {
            Model class30_sub2_sub4_sub6 = models[k];
            if(class30_sub2_sub4_sub6 != null)
            {
                numverticies += class30_sub2_sub4_sub6.numverticies;
                numtriangles += class30_sub2_sub4_sub6.numtriangles;
                numtexedtriangles += class30_sub2_sub4_sub6.numtexedtriangles;
                flag |= class30_sub2_sub4_sub6.triangleopcodes != null;
                if(class30_sub2_sub4_sub6.tripriorities != null)
                {
                    flag1 = true;
                } else
                {
                    if(anInt1641 == -1)
                        anInt1641 = class30_sub2_sub4_sub6.anInt1641;
                    if(anInt1641 != class30_sub2_sub4_sub6.anInt1641)
                        flag1 = true;
                }
                flag2 |= class30_sub2_sub4_sub6.trianglealphas != null;
                flag3 |= class30_sub2_sub4_sub6.tgroups != null;
            }
        }

        verticiesx = new int[numverticies];
        verticiesy = new int[numverticies];
        verticiesz = new int[numverticies];
        vgroups = new int[numverticies];
        trivertex0 = new int[numtriangles];
        trivertex1 = new int[numtriangles];
        trivertex2 = new int[numtriangles];
        texedtrianglesx = new int[numtexedtriangles];
        texedtrianglesy = new int[numtexedtriangles];
        texedtrianglesz = new int[numtexedtriangles];
        if(flag)
            triangleopcodes = new int[numtriangles];
        if(flag1)
            tripriorities = new int[numtriangles];
        if(flag2)
            trianglealphas = new int[numtriangles];
        if(flag3)
            tgroups = new int[numtriangles];
        trianglecolors = new int[numtriangles];
        numverticies = 0;
        numtriangles = 0;
        int l = 0;
        for(int i1 = 0; i1 < amountmodels; i1++)
        {
            Model class30_sub2_sub4_sub6_1 = models[i1];
            if(class30_sub2_sub4_sub6_1 != null)
            {
                for(int j1 = 0; j1 < class30_sub2_sub4_sub6_1.numtriangles; j1++)
                {
                    if(flag)
                        if(class30_sub2_sub4_sub6_1.triangleopcodes == null)
                        {
                            triangleopcodes[numtriangles] = 0;
                        } else
                        {
                            int k1 = class30_sub2_sub4_sub6_1.triangleopcodes[j1];
                            if((k1 & 2) == 2)
                                k1 += l << 2;
                            triangleopcodes[numtriangles] = k1;
                        }
                    if(flag1)
                        if(class30_sub2_sub4_sub6_1.tripriorities == null)
                            tripriorities[numtriangles] = class30_sub2_sub4_sub6_1.anInt1641;
                        else
                            tripriorities[numtriangles] = class30_sub2_sub4_sub6_1.tripriorities[j1];
                    if(flag2)
                        if(class30_sub2_sub4_sub6_1.trianglealphas == null)
                            trianglealphas[numtriangles] = 0;
                        else
                            trianglealphas[numtriangles] = class30_sub2_sub4_sub6_1.trianglealphas[j1];
                    if(flag3 && class30_sub2_sub4_sub6_1.tgroups != null)
                        tgroups[numtriangles] = class30_sub2_sub4_sub6_1.tgroups[j1];
                    trianglecolors[numtriangles] = class30_sub2_sub4_sub6_1.trianglecolors[j1];
                    trivertex0[numtriangles] = method465(class30_sub2_sub4_sub6_1, class30_sub2_sub4_sub6_1.trivertex0[j1]);
                    trivertex1[numtriangles] = method465(class30_sub2_sub4_sub6_1, class30_sub2_sub4_sub6_1.trivertex1[j1]);
                    trivertex2[numtriangles] = method465(class30_sub2_sub4_sub6_1, class30_sub2_sub4_sub6_1.trivertex2[j1]);
                    numtriangles++;
                }

                for(int l1 = 0; l1 < class30_sub2_sub4_sub6_1.numtexedtriangles; l1++)
                {
                    texedtrianglesx[numtexedtriangles] = method465(class30_sub2_sub4_sub6_1, class30_sub2_sub4_sub6_1.texedtrianglesx[l1]);
                    texedtrianglesy[numtexedtriangles] = method465(class30_sub2_sub4_sub6_1, class30_sub2_sub4_sub6_1.texedtrianglesy[l1]);
                    texedtrianglesz[numtexedtriangles] = method465(class30_sub2_sub4_sub6_1, class30_sub2_sub4_sub6_1.texedtrianglesz[l1]);
                    numtexedtriangles++;
                }

                l += class30_sub2_sub4_sub6_1.numtexedtriangles;
            }
        }

    }

    public Model(int i, int j, boolean flag, Model models[])
    {
        anInt1614 = 9;
        aBoolean1615 = false;
        anInt1616 = 360;
        anInt1617 = 1;
        aBoolean1618 = true;
        aBoolean1659 = false;
        anInt1620++;
        boolean flag1 = false;
        boolean flag2 = false;
        boolean flag3 = false;
        boolean flag4 = false;
        numverticies = 0;
        numtriangles = 0;
        numtexedtriangles = 0;
        anInt1641 = -1;
        for(int k = 0; k < i; k++)
        {
            Model class30_sub2_sub4_sub6 = models[k];
            if(class30_sub2_sub4_sub6 != null)
            {
                numverticies += class30_sub2_sub4_sub6.numverticies;
                numtriangles += class30_sub2_sub4_sub6.numtriangles;
                numtexedtriangles += class30_sub2_sub4_sub6.numtexedtriangles;
                flag1 |= class30_sub2_sub4_sub6.triangleopcodes != null;
                if(class30_sub2_sub4_sub6.tripriorities != null)
                {
                    flag2 = true;
                } else
                {
                    if(anInt1641 == -1)
                        anInt1641 = class30_sub2_sub4_sub6.anInt1641;
                    if(anInt1641 != class30_sub2_sub4_sub6.anInt1641)
                        flag2 = true;
                }
                flag3 |= class30_sub2_sub4_sub6.trianglealphas != null;
                flag4 |= class30_sub2_sub4_sub6.trianglecolors != null;
            }
        }

        verticiesx = new int[numverticies];
        verticiesy = new int[numverticies];
        verticiesz = new int[numverticies];
        trivertex0 = new int[numtriangles];
        trivertex1 = new int[numtriangles];
        trivertex2 = new int[numtriangles];
        vertexcolors0 = new int[numtriangles];
        vertexcolors1 = new int[numtriangles];
        vertexcolors2 = new int[numtriangles];
        texedtrianglesx = new int[numtexedtriangles];
        texedtrianglesy = new int[numtexedtriangles];
        texedtrianglesz = new int[numtexedtriangles];
        if(j >= 0)
        {
            for(int l = 1; l > 0; l++);
        }
        if(flag1)
            triangleopcodes = new int[numtriangles];
        if(flag2)
            tripriorities = new int[numtriangles];
        if(flag3)
            trianglealphas = new int[numtriangles];
        if(flag4)
            trianglecolors = new int[numtriangles];
        numverticies = 0;
        numtriangles = 0;
        numtexedtriangles = 0;
        int i1 = 0;
        for(int j1 = 0; j1 < i; j1++)
        {
            Model class30_sub2_sub4_sub6_1 = models[j1];
            if(class30_sub2_sub4_sub6_1 != null)
            {
                int k1 = numverticies;
                for(int l1 = 0; l1 < class30_sub2_sub4_sub6_1.numverticies; l1++)
                {
                    verticiesx[numverticies] = class30_sub2_sub4_sub6_1.verticiesx[l1];
                    verticiesy[numverticies] = class30_sub2_sub4_sub6_1.verticiesy[l1];
                    verticiesz[numverticies] = class30_sub2_sub4_sub6_1.verticiesz[l1];
                    numverticies++;
                }

                for(int i2 = 0; i2 < class30_sub2_sub4_sub6_1.numtriangles; i2++)
                {
                    trivertex0[numtriangles] = class30_sub2_sub4_sub6_1.trivertex0[i2] + k1;
                    trivertex1[numtriangles] = class30_sub2_sub4_sub6_1.trivertex1[i2] + k1;
                    trivertex2[numtriangles] = class30_sub2_sub4_sub6_1.trivertex2[i2] + k1;
                    vertexcolors0[numtriangles] = class30_sub2_sub4_sub6_1.vertexcolors0[i2];
                    vertexcolors1[numtriangles] = class30_sub2_sub4_sub6_1.vertexcolors1[i2];
                    vertexcolors2[numtriangles] = class30_sub2_sub4_sub6_1.vertexcolors2[i2];
                    if(flag1)
                        if(class30_sub2_sub4_sub6_1.triangleopcodes == null)
                        {
                            triangleopcodes[numtriangles] = 0;
                        } else
                        {
                            int j2 = class30_sub2_sub4_sub6_1.triangleopcodes[i2];
                            if((j2 & 2) == 2)
                                j2 += i1 << 2;
                            triangleopcodes[numtriangles] = j2;
                        }
                    if(flag2)
                        if(class30_sub2_sub4_sub6_1.tripriorities == null)
                            tripriorities[numtriangles] = class30_sub2_sub4_sub6_1.anInt1641;
                        else
                            tripriorities[numtriangles] = class30_sub2_sub4_sub6_1.tripriorities[i2];
                    if(flag3)
                        if(class30_sub2_sub4_sub6_1.trianglealphas == null)
                            trianglealphas[numtriangles] = 0;
                        else
                            trianglealphas[numtriangles] = class30_sub2_sub4_sub6_1.trianglealphas[i2];
                    if(flag4 && class30_sub2_sub4_sub6_1.trianglecolors != null)
                        trianglecolors[numtriangles] = class30_sub2_sub4_sub6_1.trianglecolors[i2];
                    numtriangles++;
                }

                for(int k2 = 0; k2 < class30_sub2_sub4_sub6_1.numtexedtriangles; k2++)
                {
                    texedtrianglesx[numtexedtriangles] = class30_sub2_sub4_sub6_1.texedtrianglesx[k2] + k1;
                    texedtrianglesy[numtexedtriangles] = class30_sub2_sub4_sub6_1.texedtrianglesy[k2] + k1;
                    texedtrianglesz[numtexedtriangles] = class30_sub2_sub4_sub6_1.texedtrianglesz[k2] + k1;
                    numtexedtriangles++;
                }

                i1 += class30_sub2_sub4_sub6_1.numtexedtriangles;
            }
        }

        method466(false);
    }

    public Model(Model model,boolean sharetricolors, boolean sharetrialphas, boolean shareverticies)
    {
        anInt1614 = 9;
        aBoolean1615 = false;
        anInt1616 = 360;
        anInt1617 = 1;
        aBoolean1618 = true;
        aBoolean1659 = false;
        anInt1620++;
        numverticies = model.numverticies;
        numtriangles = model.numtriangles;
        numtexedtriangles = model.numtexedtriangles;
        if(shareverticies)
        {
            verticiesx = model.verticiesx;
            verticiesy = model.verticiesy;
            verticiesz = model.verticiesz;
        } else
        {
            verticiesx = new int[numverticies];
            verticiesy = new int[numverticies];
            verticiesz = new int[numverticies];
            for(int j = 0; j < numverticies; j++)
            {
                verticiesx[j] = model.verticiesx[j];
                verticiesy[j] = model.verticiesy[j];
                verticiesz[j] = model.verticiesz[j];
            }
        }
        if(sharetricolors)
        {
            trianglecolors = model.trianglecolors;
        } else
        {
            trianglecolors = new int[numtriangles];
            for(int k = 0; k < numtriangles; k++)
                trianglecolors[k] = model.trianglecolors[k];

        }
        if(sharetrialphas)
        {
            trianglealphas = model.trianglealphas;
        } else
        {
            trianglealphas = new int[numtriangles];
            if(model.trianglealphas == null)
            {
                for(int l = 0; l < numtriangles; l++)
                    trianglealphas[l] = 0;

            } else
            {
                for(int i1 = 0; i1 < numtriangles; i1++)
                    trianglealphas[i1] = model.trianglealphas[i1];

            }
        }
        vgroups = model.vgroups;
        tgroups = model.tgroups;
        triangleopcodes = model.triangleopcodes;
        trivertex0 = model.trivertex0;
        trivertex1 = model.trivertex1;
        trivertex2 = model.trivertex2;
        tripriorities = model.tripriorities;
        anInt1641 = model.anInt1641;
        texedtrianglesx = model.texedtrianglesx;
        texedtrianglesy = model.texedtrianglesy;
        texedtrianglesz = model.texedtrianglesz;
    }

    public Model(boolean flag, int i, boolean flag1, Model class30_sub2_sub4_sub6)
    {
        anInt1614 = 9;
        aBoolean1615 = false;
        anInt1616 = 360;
        anInt1617 = 1;
        aBoolean1618 = true;
        aBoolean1659 = false;
        anInt1620++;
        numverticies = class30_sub2_sub4_sub6.numverticies;
        numtriangles = class30_sub2_sub4_sub6.numtriangles;
        numtexedtriangles = class30_sub2_sub4_sub6.numtexedtriangles;
        if(flag)
        {
            verticiesy = new int[numverticies];
            for(int j = 0; j < numverticies; j++)
                verticiesy[j] = class30_sub2_sub4_sub6.verticiesy[j];

        } else
        {
            verticiesy = class30_sub2_sub4_sub6.verticiesy;
        }
        if(flag1)
        {
            vertexcolors0 = new int[numtriangles];
            vertexcolors1 = new int[numtriangles];
            vertexcolors2 = new int[numtriangles];
            for(int k = 0; k < numtriangles; k++)
            {
                vertexcolors0[k] = class30_sub2_sub4_sub6.vertexcolors0[k];
                vertexcolors1[k] = class30_sub2_sub4_sub6.vertexcolors1[k];
                vertexcolors2[k] = class30_sub2_sub4_sub6.vertexcolors2[k];
            }

            triangleopcodes = new int[numtriangles];
            if(class30_sub2_sub4_sub6.triangleopcodes == null)
            {
                for(int l = 0; l < numtriangles; l++)
                    triangleopcodes[l] = 0;

            } else
            {
                for(int i1 = 0; i1 < numtriangles; i1++)
                    triangleopcodes[i1] = class30_sub2_sub4_sub6.triangleopcodes[i1];

            }
            super.surfaces = new GouraudVertex[numverticies];
            for(int j1 = 0; j1 < numverticies; j1++)
            {
                GouraudVertex class33 = super.surfaces[j1] = new GouraudVertex();
                GouraudVertex class33_1 = ((Entity) (class30_sub2_sub4_sub6)).surfaces[j1];
                class33.vectorx = class33_1.vectorx;
                class33.vectory = class33_1.vectory;
                class33.vectorz = class33_1.vectorz;
                class33.values = class33_1.values;
            }

            aClass33Array1660 = class30_sub2_sub4_sub6.aClass33Array1660;
        } else
        {
            vertexcolors0 = class30_sub2_sub4_sub6.vertexcolors0;
            vertexcolors1 = class30_sub2_sub4_sub6.vertexcolors1;
            vertexcolors2 = class30_sub2_sub4_sub6.vertexcolors2;
            triangleopcodes = class30_sub2_sub4_sub6.triangleopcodes;
        }
        verticiesx = class30_sub2_sub4_sub6.verticiesx;
        verticiesz = class30_sub2_sub4_sub6.verticiesz;
        trianglecolors = class30_sub2_sub4_sub6.trianglecolors;
        trianglealphas = class30_sub2_sub4_sub6.trianglealphas;
        tripriorities = class30_sub2_sub4_sub6.tripriorities;
        anInt1641 = class30_sub2_sub4_sub6.anInt1641;
        trivertex0 = class30_sub2_sub4_sub6.trivertex0;
        trivertex1 = class30_sub2_sub4_sub6.trivertex1;
        trivertex2 = class30_sub2_sub4_sub6.trivertex2;
        texedtrianglesx = class30_sub2_sub4_sub6.texedtrianglesx;
        texedtrianglesy = class30_sub2_sub4_sub6.texedtrianglesy;
        texedtrianglesz = class30_sub2_sub4_sub6.texedtrianglesz;
        super.miny = ((Entity) (class30_sub2_sub4_sub6)).miny;
        for(maxy = class30_sub2_sub4_sub6.maxy; i >= 0;)
            throw new NullPointerException();

        maxdistxz = class30_sub2_sub4_sub6.maxdistxz;
        maxdistxzminy = class30_sub2_sub4_sub6.maxdistxzminy;
        maxdistxzmaxy = class30_sub2_sub4_sub6.maxdistxzmaxy;
        minx_ = class30_sub2_sub4_sub6.minx_;
        anInt1648 = class30_sub2_sub4_sub6.anInt1648;
        anInt1649 = class30_sub2_sub4_sub6.anInt1649;
        maxx_ = class30_sub2_sub4_sub6.maxx_;
    }

    public void method464(int i, Model class30_sub2_sub4_sub6, boolean flag)
    {
        numverticies = class30_sub2_sub4_sub6.numverticies;
        if(i != 7)
        {
            for(int j = 1; j > 0; j++);
        }
        numtriangles = class30_sub2_sub4_sub6.numtriangles;
        numtexedtriangles = class30_sub2_sub4_sub6.numtexedtriangles;
        if(anIntArray1622.length < numverticies)
        {
            anIntArray1622 = new int[numverticies + 100];
            anIntArray1623 = new int[numverticies + 100];
            anIntArray1624 = new int[numverticies + 100];
        }
        verticiesx = anIntArray1622;
        verticiesy = anIntArray1623;
        verticiesz = anIntArray1624;
        for(int k = 0; k < numverticies; k++)
        {
            verticiesx[k] = class30_sub2_sub4_sub6.verticiesx[k];
            verticiesy[k] = class30_sub2_sub4_sub6.verticiesy[k];
            verticiesz[k] = class30_sub2_sub4_sub6.verticiesz[k];
        }

        if(flag)
        {
            trianglealphas = class30_sub2_sub4_sub6.trianglealphas;
        } else
        {
            if(anIntArray1625.length < numtriangles)
                anIntArray1625 = new int[numtriangles + 100];
            trianglealphas = anIntArray1625;
            if(class30_sub2_sub4_sub6.trianglealphas == null)
            {
                for(int l = 0; l < numtriangles; l++)
                    trianglealphas[l] = 0;

            } else
            {
                for(int i1 = 0; i1 < numtriangles; i1++)
                    trianglealphas[i1] = class30_sub2_sub4_sub6.trianglealphas[i1];

            }
        }
        triangleopcodes = class30_sub2_sub4_sub6.triangleopcodes;
        trianglecolors = class30_sub2_sub4_sub6.trianglecolors;
        tripriorities = class30_sub2_sub4_sub6.tripriorities;
        anInt1641 = class30_sub2_sub4_sub6.anInt1641;
        trianglegroups = class30_sub2_sub4_sub6.trianglegroups;
        vertexgroups = class30_sub2_sub4_sub6.vertexgroups;
        trivertex0 = class30_sub2_sub4_sub6.trivertex0;
        trivertex1 = class30_sub2_sub4_sub6.trivertex1;
        trivertex2 = class30_sub2_sub4_sub6.trivertex2;
        vertexcolors0 = class30_sub2_sub4_sub6.vertexcolors0;
        vertexcolors1 = class30_sub2_sub4_sub6.vertexcolors1;
        vertexcolors2 = class30_sub2_sub4_sub6.vertexcolors2;
        texedtrianglesx = class30_sub2_sub4_sub6.texedtrianglesx;
        texedtrianglesy = class30_sub2_sub4_sub6.texedtrianglesy;
        texedtrianglesz = class30_sub2_sub4_sub6.texedtrianglesz;
    }

    public int method465(Model class30_sub2_sub4_sub6, int i)
    {
        int j = -1;
        int k = class30_sub2_sub4_sub6.verticiesx[i];
        int l = class30_sub2_sub4_sub6.verticiesy[i];
        int i1 = class30_sub2_sub4_sub6.verticiesz[i];
        for(int j1 = 0; j1 < numverticies; j1++)
        {
            if(k != verticiesx[j1] || l != verticiesy[j1] || i1 != verticiesz[j1])
                continue;
            j = j1;
            break;
        }

        if(j == -1)
        {
            verticiesx[numverticies] = k;
            verticiesy[numverticies] = l;
            verticiesz[numverticies] = i1;
            if(class30_sub2_sub4_sub6.vgroups != null)
                vgroups[numverticies] = class30_sub2_sub4_sub6.vgroups[i];
            j = numverticies++;
        }
        return j;
    }

    public void method466(boolean flag)
    {
        super.miny = 0;
        maxdistxz = 0;
        maxy = 0;
        for(int i = 0; i < numverticies; i++)
        {
            int j = verticiesx[i];
            int k = verticiesy[i];
            int l = verticiesz[i];
            if(-k > super.miny)
                super.miny = -k;
            if(k > maxy)
                maxy = k;
            int i1 = j * j + l * l;
            if(i1 > maxdistxz)
                maxdistxz = i1;
        }

        if(flag)
            anInt1619 = 455;
        maxdistxz = (int)(Math.sqrt(maxdistxz) + 0.98999999999999999D);
        maxdistxzminy = (int)(Math.sqrt(maxdistxz * maxdistxz + super.miny * super.miny) + 0.98999999999999999D);
        maxdistxzmaxy = maxdistxzminy + (int)(Math.sqrt(maxdistxz * maxdistxz + maxy * maxy) + 0.98999999999999999D);
    }

    public void method467(boolean flag)
    {
        super.miny = 0;
        maxy = 0;
        if(flag)
            aBoolean1615 = !aBoolean1615;
        for(int i = 0; i < numverticies; i++)
        {
            int j = verticiesy[i];
            if(-j > super.miny)
                super.miny = -j;
            if(j > maxy)
                maxy = j;
        }

        maxdistxzminy = (int)(Math.sqrt(maxdistxz * maxdistxz + super.miny * super.miny) + 0.98999999999999999D);
        maxdistxzmaxy = maxdistxzminy + (int)(Math.sqrt(maxdistxz * maxdistxz + maxy * maxy) + 0.98999999999999999D);
    }

    public void method468(int i)
    {
        super.miny = 0;
        maxdistxz = 0;
        maxy = 0;
        minx_ = 0xf423f;    //999999
        maxx_ = 0xfff0bdc1; //4293967297
        anInt1648 = 0xfffe7961; //4294867297
        anInt1649 = 0x1869f;    //99999
        for(int j = 0; j < numverticies; j++)
        {
            int x = verticiesx[j];
            int y = verticiesy[j];
            int z = verticiesz[j];
            if(x < minx_)
                minx_ = x;
            if(x > maxx_)
                maxx_ = x;
            if(z < anInt1649)
                anInt1649 = z;
            if(z > anInt1648)
                anInt1648 = z;
            if(-y > super.miny)
                super.miny = -y;
            if(y > maxy)
                maxy = y;
            int j1 = x * x + z * z;
            if(j1 > maxdistxz)
                maxdistxz = j1;
        }

        maxdistxz = (int)Math.sqrt(maxdistxz);
        maxdistxzminy = (int)Math.sqrt(maxdistxz * maxdistxz + super.miny * super.miny);
        if(i != 21073)
        {
            return;
        } else
        {
            maxdistxzmaxy = maxdistxzminy + (int)Math.sqrt(maxdistxz * maxdistxz + maxy * maxy);
            return;
        }
    }

    public void setVertexTriangleGroups()
    {
        if(vgroups != null)
        {
            int groups[] = new int[256];
            int maxgroup = 0;
            for(int l = 0; l < numverticies; l++)
            {
                int j1 = vgroups[l];
                groups[j1]++;
                if(j1 > maxgroup)
                    maxgroup = j1;
            }

            vertexgroups = new int[maxgroup + 1][];
            for(int k1 = 0; k1 <= maxgroup; k1++)
            {
                vertexgroups[k1] = new int[groups[k1]];
                groups[k1] = 0;
            }

            for(int j2 = 0; j2 < numverticies; j2++)
            {
                int l2 = vgroups[j2];
                vertexgroups[l2][groups[l2]++] = j2;
            }

            vgroups = null;
        }
        if(tgroups != null)
        {
            int groups[] = new int[256];
            int maxgroup = 0;
            for(int triangle = 0; triangle < numtriangles; triangle++)
            {
                int group = tgroups[triangle];
                groups[group]++;
                if(group > maxgroup)
                    maxgroup = group;
            }

            trianglegroups = new int[maxgroup + 1][];
            for(int i = 0; i <= maxgroup; i++)
            {
                trianglegroups[i] = new int[groups[i]];
                groups[i] = 0;
            }

            for(int triangle = 0; triangle < numtriangles; triangle++)
            {
                int group = tgroups[triangle];
                trianglegroups[group][groups[group]++] = triangle;
            }

            tgroups = null;
        }
    }

    public void applyAnimationFrame(int id)
    {
        if(vertexgroups == null)
            return;
        if(id == -1)
            return;
        AnimFrame frame = AnimFrame.getAnimationFrame(id);
        if(frame == null)
            return;
        FrameOperation frameop = frame.aClass18_637;
        referencex = 0;
        referencey = 0;
        referencez = 0;
        for(int k = 0; k < frame.amountops; k++)
        {
            int position = frame.operations[k];
            applyFrameOperation(frameop.animopcodes[position], frameop.animgroups[position], frame.xvars[k], frame.yvars[k], frame.zvars[k]);
        }
    }

    public void method471(int junk, int ai[], int j, int k)
    {
        if(k == -1)
            return;
        if(ai == null || j == -1)
        {
            applyAnimationFrame(k);
            return;
        }
        AnimFrame class36 = AnimFrame.getAnimationFrame(k);
        if(class36 == null)
            return;
        AnimFrame class36_1 = AnimFrame.getAnimationFrame(j);
        if(class36_1 == null)
        {
            applyAnimationFrame(k);
            return;
        }
        FrameOperation class18 = class36.aClass18_637;
        referencex = 0;
        referencey = 0;
        referencez = 0;
        int l = 0;
        int i1 = ai[l++];
        for(int j1 = 0; j1 < class36.amountops; j1++)
        {
            int k1;
            for(k1 = class36.operations[j1]; k1 > i1; i1 = ai[l++]);
            if(k1 != i1 || class18.animopcodes[k1] == 0)
                applyFrameOperation(class18.animopcodes[k1], class18.animgroups[k1], class36.xvars[j1], class36.yvars[j1], class36.zvars[j1]);
        }

        referencex = 0;
        referencey = 0;
        referencez = 0;
        l = 0;
        i1 = ai[l++];
        for(int l1 = 0; l1 < class36_1.amountops; l1++)
        {
            int i2;
            for(i2 = class36_1.operations[l1]; i2 > i1; i1 = ai[l++]);
            if(i2 == i1 || class18.animopcodes[i2] == 0)
                applyFrameOperation(class18.animopcodes[i2], class18.animgroups[i2], class36_1.xvars[l1], class36_1.yvars[l1], class36_1.zvars[l1]);
        }

    }

    public void applyFrameOperation(int opcode, int groups[], int xvar, int yvar, int zvar)
    {
        int amountgroups = groups.length;
        if(opcode == 0) {
            int amountverticies = 0;
            referencex = 0;
            referencey = 0;
            referencez = 0;
            for(int i = 0; i < amountgroups; i++) {
                int group = groups[i];
                if(group < vertexgroups.length) {
                    int verticies[] = vertexgroups[group];
                    for(int j = 0; j < verticies.length; j++) {
                        int vertex = verticies[j];
                        referencex += verticiesx[vertex];
                        referencey += verticiesy[vertex];
                        referencez += verticiesz[vertex];
                        amountverticies++;
                    }
                }
            }
            if(amountverticies > 0)
            {
                referencex = referencex / amountverticies + xvar;
                referencey = referencey / amountverticies + yvar;
                referencez = referencez / amountverticies + zvar;
                return;
            } else
            {
                referencex = xvar;
                referencey = yvar;
                referencez = zvar;
                return;
            }
        }
        if(opcode == 1) {
            for(int i = 0; i < amountgroups; i++) {
                int group = groups[i];
                if(group < vertexgroups.length) {
                    int verticies[] = vertexgroups[group];
                    for(int j = 0; j < verticies.length; j++) {
                        int vertex = verticies[j];
                        verticiesx[vertex] += xvar;
                        verticiesy[vertex] += yvar;
                        verticiesz[vertex] += zvar;
                    }

                }
            }
            return;
        }
        if(opcode == 2)
        {
            for(int i = 0; i < amountgroups; i++) {
                int group = groups[i];
                if(group < vertexgroups.length) {
                    int verticies[] = vertexgroups[group];
                    for(int j = 0; j < verticies.length; j++) {
                        int vertex = verticies[j];
                        verticiesx[vertex] -= referencex;
                        verticiesy[vertex] -= referencey;
                        verticiesz[vertex] -= referencez;
                        int xaxisrotation = (xvar & 0xff) * 8;
                        int yaxisrotation = (yvar & 0xff) * 8;
                        int zaxisrotation = (zvar & 0xff) * 8;
                        /* Z Axis Rotation */
                        if(zaxisrotation != 0)
                        {
                            int sine = sinetable[zaxisrotation];
                            int cosine = cosinetable[zaxisrotation];
                            int calcx = verticiesy[vertex] * sine + verticiesx[vertex] * cosine >> 16;
                            verticiesy[vertex] = verticiesy[vertex] * cosine - verticiesx[vertex] * sine >> 16;
                            verticiesx[vertex] = calcx;
                        }
                        /* X Axis rotation */
                        if(xaxisrotation != 0)
                        {
                            int sine = sinetable[xaxisrotation];
                            int cosine = cosinetable[xaxisrotation];
                            int calcy = verticiesy[vertex] * cosine - verticiesz[vertex] * sine >> 16;
                            verticiesz[vertex] = verticiesy[vertex] * sine + verticiesz[vertex] * cosine >> 16;
                            verticiesy[vertex] = calcy;
                        }
                        /* Y Rotation */
                        if(yaxisrotation != 0)
                        {
                            int sine = sinetable[yaxisrotation];
                            int cosine = cosinetable[yaxisrotation];
                            int calcx = verticiesz[vertex] * sine + verticiesx[vertex] * cosine >> 16;
                            verticiesz[vertex] = verticiesz[vertex] * cosine - verticiesx[vertex] * sine >> 16;
                            verticiesx[vertex] = calcx;
                        }
                        verticiesx[vertex] += referencex;
                        verticiesy[vertex] += referencey;
                        verticiesz[vertex] += referencez;
                    }
                }
            }
            return;
        }
        if(opcode == 3)
        {
            for(int i2 = 0; i2 < amountgroups; i2++)
            {
                int group = groups[i2];
                if(group < vertexgroups.length)
                {
                    int verticies[] = vertexgroups[group];
                    for(int k4 = 0; k4 < verticies.length; k4++)
                    {
                        int vertex = verticies[k4];
                        verticiesx[vertex] -= referencex;
                        verticiesy[vertex] -= referencey;
                        verticiesz[vertex] -= referencez;
                        verticiesx[vertex] = (verticiesx[vertex] * xvar) / 128;
                        verticiesy[vertex] = (verticiesy[vertex] * yvar) / 128;
                        verticiesz[vertex] = (verticiesz[vertex] * zvar) / 128;
                        verticiesx[vertex] += referencex;
                        verticiesy[vertex] += referencey;
                        verticiesz[vertex] += referencez;
                    }
                }
            }
            return;
        }
        if(opcode == 5 && trianglegroups != null && trianglealphas != null)
        {
            for(int j2 = 0; j2 < amountgroups; j2++)
            {
                int group = groups[j2];
                if(group < trianglegroups.length)
                {
                    int triangles[] = trianglegroups[group];
                    for(int l4 = 0; l4 < triangles.length; l4++)
                    {
                        int triangle = triangles[l4];
                        trianglealphas[triangle] += xvar * 8;
                        if(trianglealphas[triangle] < 0)
                            trianglealphas[triangle] = 0;
                        if(trianglealphas[triangle] > 255)
                            trianglealphas[triangle] = 255;
                    }
                }
            }
        }
    }

    public void rotate(int junk)
    {
        for(int j = 0; j < numverticies; j++)
        {
            int k = verticiesx[j];
            verticiesx[j] = verticiesz[j];
            verticiesz[j] = -k;
        }
    }

    public void xAxisRotation(int angle)
    {
        int sine = sinetable[angle];
        int cosine = cosinetable[angle];
        for(int vertex = 0; vertex < numverticies; vertex++) {
            int y = verticiesy[vertex] * cosine - verticiesz[vertex] * sine >> 16;
            verticiesz[vertex] = verticiesy[vertex] * sine + verticiesz[vertex] * cosine >> 16;
            verticiesy[vertex] = y;
        }
    }

    public void moveVertices(int amtx, int amty, int junk, int amtz) {
        for(int i1 = 0; i1 < numverticies; i1++) {
            verticiesx[i1] += amtx;
            verticiesy[i1] += amty;
            verticiesz[i1] += amtz;
        }
    }

    public void setTriangleColors(int i, int j) {
        for(int k = 0; k < numtriangles; k++)
            if(trianglecolors[k] == i)
                trianglecolors[k] = j;
    }

    public void rotateTri()
    {
        for(int vertex = 0; vertex < numverticies; vertex++)
            verticiesz[vertex] = -verticiesz[vertex];

        for(int triangle = 0; triangle < numtriangles; triangle++)
        {
            int v0 = trivertex0[triangle];
            trivertex0[triangle] = trivertex2[triangle];
            trivertex2[triangle] = v0;
        }
    }

    public void scaleModel(int scalex, int scaley, int scalez)
    {
        for(int i1 = 0; i1 < numverticies; i1++)
        {
            verticiesx[i1] = (verticiesx[i1] * scalex) / 128;
            verticiesy[i1] = (verticiesy[i1] * scaley) / 128;
            verticiesz[i1] = (verticiesz[i1] * scalez) / 128;
        }
    }

    public void setLightingVectors(int constant, int j, int xmulti, int ymulti, int zmulti, boolean flag)
    {
		/* 3D distance formula */
        int j1 = (int)Math.sqrt(xmulti * xmulti + ymulti * ymulti + zmulti * zmulti);
        int valuemulti = j * j1 >> 8;
        if(vertexcolors0 == null)
        {
            vertexcolors0 = new int[numtriangles];
            vertexcolors1 = new int[numtriangles];
            vertexcolors2 = new int[numtriangles];
        }
        if(super.surfaces == null)
        {
            super.surfaces = new GouraudVertex[numverticies];
            for(int l1 = 0; l1 < numverticies; l1++)
                super.surfaces[l1] = new GouraudVertex();

        }
        for(int i2 = 0; i2 < numtriangles; i2++) // a^2 + b^2 = c^2, sqr(a^2 + b^2) = c,
        {
            int j2 = trivertex0[i2];
            int l2 = trivertex1[i2];
            int i3 = trivertex2[i2];
            int dx = verticiesx[l2] - verticiesx[j2]; /* Comparison Vertex: (5, 5, 5), Vertex 0: (0, 0, 0) [Dx = Dy = Dz = -5], Vertex 1: (5, 5, 0) [Dx2 = Dy2 = 0, Dz2 = -5] */
            int dy = verticiesy[l2] - verticiesy[j2]; /* (deltaY * deltaZ2) - (deltaY2 * deltaZ) Unit: YZ*/
            int dz = verticiesz[l2] - verticiesz[j2];
            int dx2 = verticiesx[i3] - verticiesx[j2];
            int dy2 = verticiesy[i3] - verticiesy[j2];
            int dz2 = verticiesz[i3] - verticiesz[j2];
            int l4 = dy * dz2 - dy2 * dz;
            int i5 = dz * dx2 - dz2 * dx;
            int j5;
			/* 72 dots inch */
            for(j5 = dx * dy2 - dx2 * dy; l4 > 8192 || i5 > 8192 || j5 > 8192 || l4 < -8192 || i5 < -8192 || j5 < -8192; j5 >>= 1)
            {
                l4 >>= 1;
                i5 >>= 1;
            }

            int k5 = (int) Math.sqrt(l4 * l4 + i5 * i5 + j5 * j5);
            if(k5 <= 0)
                k5 = 1;
            l4 = (l4 * 256) / k5;
            i5 = (i5 * 256) / k5;
            j5 = (j5 * 256) / k5;
            if(triangleopcodes == null || (triangleopcodes[i2] & 1) == 0)
            {
                GouraudVertex class33_2 = super.surfaces[j2];
                class33_2.vectorx += l4;
                class33_2.vectory += i5;
                class33_2.vectorz += j5;
                class33_2.values++;
                class33_2 = super.surfaces[l2];
                class33_2.vectorx += l4;
                class33_2.vectory += i5;
                class33_2.vectorz += j5;
                class33_2.values++;
                class33_2 = super.surfaces[i3];
                class33_2.vectorx += l4;
                class33_2.vectory += i5;
                class33_2.vectorz += j5;
                class33_2.values++;
            } else
            {
                int l5 = constant + (xmulti * l4 + ymulti * i5 + zmulti * j5) / (valuemulti + valuemulti / 2);
                vertexcolors0[i2] = method481(trianglecolors[i2], l5, triangleopcodes[i2]);
            }
        }

        if(flag)
        {
            applyShading(xmulti, ymulti, zmulti, valuemulti, constant);
        } else
        {
            aClass33Array1660 = new GouraudVertex[numverticies];
            for(int k2 = 0; k2 < numverticies; k2++)
            {
                GouraudVertex class33 = super.surfaces[k2];
                GouraudVertex class33_1 = aClass33Array1660[k2] = new GouraudVertex();
                class33_1.vectorx = class33.vectorx;
                class33_1.vectory = class33.vectory;
                class33_1.vectorz = class33.vectorz;
                class33_1.values = class33.values;
            }

        }
        if(flag)
        {
            method466(false);
            return;
        } else
        {
            method468(21073);
            return;
        }
    }

    public void applyShading(int xmulti, int ymulti, int zmulti, int valuesmulti, int constant)
    {
        for(int j1 = 0; j1 < numtriangles; j1++)
        {
            int v0 = trivertex0[j1];
            int v1 = trivertex1[j1];
            int v2 = trivertex2[j1];
            if(triangleopcodes == null)
            {
                int color = trianglecolors[j1];
                GouraudVertex surface = super.surfaces[v0];
                int k2 = constant + (xmulti * surface.vectorx + ymulti * surface.vectory + zmulti * surface.vectorz) / (valuesmulti * surface.values);
                vertexcolors0[j1] = method481(color, k2, 0);
                surface = super.surfaces[v1];
                k2 = constant + (xmulti * surface.vectorx + ymulti * surface.vectory + zmulti * surface.vectorz) / (valuesmulti * surface.values);
                vertexcolors1[j1] = method481(color, k2, 0);
                surface = super.surfaces[v2];
                k2 = constant + (xmulti * surface.vectorx + ymulti * surface.vectory + zmulti * surface.vectorz) / (valuesmulti * surface.values);
                vertexcolors2[j1] = method481(color, k2, 0);
            } else if((triangleopcodes[j1] & 1) == 0)
            {
                int color = trianglecolors[j1];
                int opcode = triangleopcodes[j1];
                GouraudVertex class33_1 = super.surfaces[v0];
                int l2 = constant + (xmulti * class33_1.vectorx + ymulti * class33_1.vectory + zmulti * class33_1.vectorz) / (valuesmulti * class33_1.values);
                vertexcolors0[j1] = method481(color, l2, opcode);
                class33_1 = super.surfaces[v1];
                l2 = constant + (xmulti * class33_1.vectorx + ymulti * class33_1.vectory + zmulti * class33_1.vectorz) / (valuesmulti * class33_1.values);
                vertexcolors1[j1] = method481(color, l2, opcode);
                class33_1 = super.surfaces[v2];
                l2 = constant + (xmulti * class33_1.vectorx + ymulti * class33_1.vectory + zmulti * class33_1.vectorz) / (valuesmulti * class33_1.values);
                vertexcolors2[j1] = method481(color, l2, opcode);
            }
        }

        super.surfaces = null;
        aClass33Array1660 = null;
        vgroups = null;
        tgroups = null;
        if(triangleopcodes != null)
        {
            for(int l1 = 0; l1 < numtriangles; l1++)
                if((triangleopcodes[l1] & 2) == 2)
                    return;

        }
        trianglecolors = null;
    }

    public static int method481(int color, int j, int opcode)
    {
        if((opcode & 2) == 2)
        {
            if(j < 0)
                j = 0;
            else
            if(j > 127)
                j = 127;
            j = 127 - j;
            return j;
        }
        j = j * (color & 0x7f) >> 7;
        if(j < 2)
            j = 2;
        else
        if(j > 126)
            j = 126;
        return (color & 0xff80) + j;
    }

    public void drawModel(int xrotation, int yrotation, int zrotation, int xrotation2, int originx, int originy, int originz)
    {
        int midwidth = TriangleRasterizer.midwidth;
        int midheight = TriangleRasterizer.midheight;
        int sine0 = sinetable[xrotation];
        int cosine0 = cosinetable[xrotation];
        int sine1 = sinetable[yrotation];
        int cosine1 = cosinetable[yrotation];
        int sine2 = sinetable[zrotation];
        int cosine2 = cosinetable[zrotation];
        int sine3 = sinetable[xrotation2];
        int cosine3 = cosinetable[xrotation2];
        int j4 = originy * sine3 + originz * cosine3 >> 16;
        for(int i = 0; i < numverticies; i++) {
            int x = verticiesx[i];
            int y = verticiesy[i];
            int z = verticiesz[i];
            if(zrotation != 0)
            {
                int k5 = y * sine2 + x * cosine2 >> 16;
                y = y * cosine2 - x * sine2 >> 16;
                x = k5;
            }
            if(xrotation != 0)
            {
                int l5 = y * cosine0 - z * sine0 >> 16;
                z = y * sine0 + z * cosine0 >> 16;
                y = l5;
            }
            if(yrotation != 0)
            {
                int i6 = z * sine1 + x * cosine1 >> 16;
                z = z * cosine1 - x * sine1 >> 16;
                x = i6;
            }
            x += originx;
            y += originy;
            z += originz;
            int j6 = y * cosine3 - z * sine3 >> 16;
            z = y * sine3 + z * cosine3 >> 16;
            y = j6;
            zvertex$[i] = z - j4;
            xvertex$[i] = midwidth + (x << 9) / z;
            yvertex$[i] = midheight + (y << 9) / z;
            if(numtexedtriangles > 0)
            {
                xtexed$[i] = x;
                ytexed$[i] = y;
                ztexed$[i] = z;
            }
        }

        try {
            drawTriangles(false, false, 0);
            return;
        }
        catch(Exception _ex)
        {
            return;
        }
    }

    @Override
    public void drawModel(int angle, int j, int k, int l, int i1, int originx, int originy, int originz, int i2) {
        int j2 = originz * i1 - originx * l >> 16;
        int k2 = originy * j + j2 * k >> 16;
        int l2 = maxdistxz * k >> 16;
        int i3 = k2 + l2;
        if(i3 <= 50 || k2 >= 3500)
            return;
        int j3 = originz * l + originx * i1 >> 16;
        int k3 = j3 - maxdistxz << 9;
        if(k3 / i3 >= BasicRasterizer.widthCenter)
            return;
        int l3 = j3 + maxdistxz << 9;
        if(l3 / i3 <= -BasicRasterizer.widthCenter)
            return;
        int i4 = originy * k - j2 * j >> 16;
        int j4 = maxdistxz * j >> 16;
        int k4 = i4 + j4 << 9;
        if(k4 / i3 <= -BasicRasterizer.heightCenter)
            return;
        int l4 = j4 + (super.miny * k >> 16);
        int i5 = i4 - l4 << 9;
        if(i5 / i3 >= BasicRasterizer.heightCenter)
            return;
        int j5 = l2 + (super.miny * j >> 16);
        boolean flag = false;
        if(k2 - j5 <= 50)
            flag = true;
        boolean flag1 = false;
        if(i2 > 0 && aBoolean1684)
        {
            int k5 = k2 - l2;
            if(k5 <= 50)
                k5 = 50;
            if(j3 > 0)
            {
                k3 /= i3;
                l3 /= k5;
            } else
            {
                l3 /= i3;
                k3 /= k5;
            }
            if(i4 > 0)
            {
                i5 /= i3;
                k4 /= k5;
            } else
            {
                k4 /= i3;
                i5 /= k5;
            }
            int i6 = anInt1685 - TriangleRasterizer.midwidth;
            int k6 = anInt1686 - TriangleRasterizer.midheight;
            if(i6 > k3 && i6 < l3 && k6 > i5 && k6 < k4)
                if(aBoolean1659)
                    anIntArray1688[anInt1687++] = i2;
                else
                    flag1 = true;
        }
        int l5 = TriangleRasterizer.midwidth;
        int j6 = TriangleRasterizer.midheight;
        int l6 = 0;
        int i7 = 0;
        if(angle != 0)
        {
            l6 = sinetable[angle];
            i7 = cosinetable[angle];
        }
        for(int j7 = 0; j7 < numverticies; j7++)
        {
            int posx = verticiesx[j7];
            int posy = verticiesy[j7];
            int posz = verticiesz[j7];
            if(angle != 0)
            {
                int j8 = posz * l6 + posx * i7 >> 16;
                posz = posz * i7 - posx * l6 >> 16;
                posx = j8;
            }
            posx += originx;
            posy += originy;
            posz += originz;
            int k8 = posz * l + posx * i1 >> 16;
            posz = posz * i1 - posx * l >> 16;
            posx = k8;
            k8 = posy * k - posz * j >> 16;
            posz = posy * j + posz * k >> 16;
            posy = k8;
            zvertex$[j7] = posz - k2;
            if(posz >= 50)
            {
                xvertex$[j7] = l5 + (posx << 9) / posz;
                yvertex$[j7] = j6 + (posy << 9) / posz;
            } else
            {
                xvertex$[j7] = -5000;
                flag = true;
            }
            if(flag || numtexedtriangles > 0)
            {
                xtexed$[j7] = posx;
                ytexed$[j7] = posy;
                ztexed$[j7] = posz;
            }
        }

        try
        {
            drawTriangles(flag, flag1, i2);
            return;
        }
        catch(Exception _ex)
        {
            return;
        }
    }

    public void drawTriangles(boolean flag, boolean flag1, int i)
    {
        for(int j = 0; j < maxdistxzmaxy; j++)
            tridepthoffsets[j] = 0;

        for(int triangle = 0; triangle < numtriangles; triangle++)
            if(triangleopcodes == null || triangleopcodes[triangle] != -1)
            {
                int l = trivertex0[triangle];
                int k1 = trivertex1[triangle];
                int j2 = trivertex2[triangle];
                int i3 = xvertex$[l];
                int l3 = xvertex$[k1];
                int k4 = xvertex$[j2];
                if(flag && (i3 == -5000 || l3 == -5000 || k4 == -5000))
                {
                    aBooleanArray1664[triangle] = true;
                    int depth = (zvertex$[l] + zvertex$[k1] + zvertex$[j2]) / 3 + maxdistxzminy;
                    trideptharray[depth][tridepthoffsets[depth]++] = triangle;
                } else
                {
                    if(flag1 && inBounds(anInt1685, anInt1686, yvertex$[l], yvertex$[k1], yvertex$[j2], i3, l3, k4))
                    {
                        anIntArray1688[anInt1687++] = i;
                        flag1 = false;
                    }
                    if((i3 - l3) * (yvertex$[j2] - yvertex$[k1]) - (yvertex$[l] - yvertex$[k1]) * (k4 - l3) > 0)
                    {
                        aBooleanArray1664[triangle] = false;
                        if(i3 < 0 || l3 < 0 || k4 < 0 || i3 > BasicRasterizer.widthLength || l3 > BasicRasterizer.widthLength || k4 > BasicRasterizer.widthLength)
                            cliptriangle[triangle] = true;
                        else
                            cliptriangle[triangle] = false;
                        int k5 = (zvertex$[l] + zvertex$[k1] + zvertex$[j2]) / 3 + maxdistxzminy;
                        trideptharray[k5][tridepthoffsets[k5]++] = triangle;
                    }
                }
            }

        if(tripriorities == null)
        {
            for(int dist = maxdistxzmaxy - 1; dist >= 0; dist--)
            {
                int amounttriangles = tridepthoffsets[dist];
                if(amounttriangles > 0)
                {
                    int triangleorder[] = trideptharray[dist];
                    for(int j3 = 0; j3 < amounttriangles; j3++)
                        drawTriangle(triangleorder[j3]);

                }
            }

            return;
        }
        for(int j1 = 0; j1 < 12; j1++)
        {
            tripricount[j1] = 0;
            pridistances[j1] = 0;
        }

        for(int dist = maxdistxzmaxy - 1; dist >= 0; dist--)
        {
            int offset = tridepthoffsets[dist];
            if(offset > 0)
            {
                int deptharray[] = trideptharray[dist];
                for(int i4 = 0; i4 < offset; i4++)
                {
                    int tri = deptharray[i4];
                    int pri = tripriorities[tri];
                    int incr = tripricount[pri]++;
                    pritriangles[pri][incr] = tri;
                    if(pri < 10)
                        pridistances[pri] += dist;
                    else
                    if(pri == 10)
                        anIntArray1675[incr] = dist;
                    else
                        anIntArray1676[incr] = dist;
                }

            }
        }

        int l2 = 0;
        if(tripricount[1] > 0 || tripricount[2] > 0)
            l2 = (pridistances[1] + pridistances[2]) / (tripricount[1] + tripricount[2]);
        int k3 = 0;
        if(tripricount[3] > 0 || tripricount[4] > 0)
            k3 = (pridistances[3] + pridistances[4]) / (tripricount[3] + tripricount[4]);
        int j4 = 0;
        if(tripricount[6] > 0 || tripricount[8] > 0)
            j4 = (pridistances[6] + pridistances[8]) / (tripricount[6] + tripricount[8]);
        int counter = 0;
        int k6 = tripricount[10];
        int triangleorder[] = pritriangles[10];
        int ai3[] = anIntArray1675;
        if(counter == k6)
        {
            counter = 0;
            k6 = tripricount[11];
            triangleorder = pritriangles[11];
            ai3 = anIntArray1676;
        }
        int i5;
        if(counter < k6)
            i5 = ai3[counter];
        else
            i5 = -1000;
        for(int l6 = 0; l6 < 10; l6++)
        {
            while(l6 == 0 && i5 > l2) 
            {
                drawTriangle(triangleorder[counter++]);
                if(counter == k6 && triangleorder != pritriangles[11])
                {
                    counter = 0;
                    k6 = tripricount[11];
                    triangleorder = pritriangles[11];
                    ai3 = anIntArray1676;
                }
                if(counter < k6)
                    i5 = ai3[counter];
                else
                    i5 = -1000;
            }
            while(l6 == 3 && i5 > k3) 
            {
                drawTriangle(triangleorder[counter++]);
                if(counter == k6 && triangleorder != pritriangles[11])
                {
                    counter = 0;
                    k6 = tripricount[11];
                    triangleorder = pritriangles[11];
                    ai3 = anIntArray1676;
                }
                if(counter < k6)
                    i5 = ai3[counter];
                else
                    i5 = -1000;
            }
            while(l6 == 5 && i5 > j4) 
            {
                drawTriangle(triangleorder[counter++]);
                if(counter == k6 && triangleorder != pritriangles[11])
                {
                    counter = 0;
                    k6 = tripricount[11];
                    triangleorder = pritriangles[11];
                    ai3 = anIntArray1676;
                }
                if(counter < k6)
                    i5 = ai3[counter];
                else
                    i5 = -1000;
            }
            int i7 = tripricount[l6];
            int ai4[] = pritriangles[l6];
            for(int j7 = 0; j7 < i7; j7++)
                drawTriangle(ai4[j7]);

        }

        while(i5 != -1000) 
        {
            drawTriangle(triangleorder[counter++]);
            if(counter == k6 && triangleorder != pritriangles[11])
            {
                counter = 0;
                triangleorder = pritriangles[11];
                k6 = tripricount[11];
                ai3 = anIntArray1676;
            }
            if(counter < k6)
                i5 = ai3[counter];
            else
                i5 = -1000;
        }
    }

    public void drawTriangle(int id)
    {
        if(aBooleanArray1664[id])
        {
            drawFineTriangle(id);
            return;
        }
        int posx = trivertex0[id];
        int posy = trivertex1[id];
        int posz = trivertex2[id];
        TriangleRasterizer.clip = cliptriangle[id];
        if(trianglealphas == null)
            TriangleRasterizer.alpha$ = 0;
        else
            TriangleRasterizer.alpha$ = trianglealphas[id];
        int opcode;
        if(triangleopcodes == null)
            opcode = 0;
        else
            opcode = triangleopcodes[id] & 3;
        if(opcode == 0)
        {
            TriangleRasterizer.drawShadedTriangle(xvertex$[posy], xvertex$[posx], xvertex$[posz], yvertex$[posx], yvertex$[posy], yvertex$[posz], vertexcolors1[id], vertexcolors0[id], vertexcolors2[id]);
            return;
        }
        if(opcode == 1)
        {
            TriangleRasterizer.drawSimpleTriangle(xvertex$[posy], xvertex$[posx], xvertex$[posz], yvertex$[posy], yvertex$[posx], yvertex$[posz], shadingtable[vertexcolors0[id]]);
            return;
        }
        if(opcode == 2)
        {
            int j1 = triangleopcodes[id] >> 2;
            int x0 = texedtrianglesx[j1];
            int y0 = texedtrianglesy[j1];
            int z0 = texedtrianglesz[j1];
            TriangleRasterizer.drawTexturedTriangle(yvertex$[posx], yvertex$[posy], yvertex$[posz], xvertex$[posx], xvertex$[posy], xvertex$[posz], vertexcolors0[id], vertexcolors1[id], vertexcolors2[id], xtexed$[x0], xtexed$[y0], xtexed$[z0], ytexed$[x0], ytexed$[y0], ytexed$[z0], ztexed$[x0], ztexed$[y0], ztexed$[z0], trianglecolors[id]);
            return;
        }
        if(opcode == 3)
        {
            int k1 = triangleopcodes[id] >> 2;
            int i2 = texedtrianglesx[k1];
            int k2 = texedtrianglesy[k1];
            int i3 = texedtrianglesz[k1];
            TriangleRasterizer.drawTexturedTriangle(yvertex$[posx], yvertex$[posy], yvertex$[posz], xvertex$[posx], xvertex$[posy], xvertex$[posz], vertexcolors0[id], vertexcolors0[id], vertexcolors0[id], xtexed$[i2], xtexed$[k2], xtexed$[i3], ytexed$[i2], ytexed$[k2], ytexed$[i3], ztexed$[i2], ztexed$[k2], ztexed$[i3], trianglecolors[id]);
        }
    }

    public void drawFineTriangle(int id)
    {
        int midw = TriangleRasterizer.midwidth;
        int midh = TriangleRasterizer.midheight;
        int offset = 0;
        int v0 = trivertex0[id];
        int v1 = trivertex1[id];
        int v2 = trivertex2[id];
        int z0 = ztexed$[v0];
        int z1 = ztexed$[v1];
        int z2 = ztexed$[v2];
        if(z0 >= 50)
        {
            drawxstack[offset] = xvertex$[v0];
            drawstacky[offset] = yvertex$[v0];
            vertexcolors[offset++] = vertexcolors0[id];
        } else
        {
            int x0 = xtexed$[v0];
            int y0 = ytexed$[v0];
            int c0 = vertexcolors0[id];
            if(z2 >= 50)
            {
                int k5 = (50 - z0) * othercolortable[z2 - z0];
                drawxstack[offset] = midw + (x0 + ((xtexed$[v2] - x0) * k5 >> 16) << 9) / 50;
                drawstacky[offset] = midh + (y0 + ((ytexed$[v2] - y0) * k5 >> 16) << 9) / 50;
                vertexcolors[offset++] = c0 + ((vertexcolors2[id] - c0) * k5 >> 16);
            }
            if(z1 >= 50)
            {
                int multi = (50 - z0) * othercolortable[z1 - z0];
                drawxstack[offset] = midw + (x0 + ((xtexed$[v1] - x0) * multi >> 16) << 9) / 50;
                drawstacky[offset] = midh + (y0 + ((ytexed$[v1] - y0) * multi >> 16) << 9) / 50;
                vertexcolors[offset++] = c0 + ((vertexcolors1[id] - c0) * multi >> 16);
            }
        }
        if(z1 >= 50)
        {
            drawxstack[offset] = xvertex$[v1];
            drawstacky[offset] = yvertex$[v1];
            vertexcolors[offset++] = vertexcolors1[id];
        } else
        {
            int x1 = xtexed$[v1];
            int y1 = ytexed$[v1];
            int c1 = vertexcolors1[id];
            if(z0 >= 50)
            {
                int i6 = (50 - z1) * othercolortable[z0 - z1];
                drawxstack[offset] = midw + (x1 + ((xtexed$[v0] - x1) * i6 >> 16) << 9) / 50;
                drawstacky[offset] = midh + (y1 + ((ytexed$[v0] - y1) * i6 >> 16) << 9) / 50;
                vertexcolors[offset++] = c1 + ((vertexcolors0[id] - c1) * i6 >> 16);
            }
            if(z2 >= 50)
            {
                int j6 = (50 - z1) * othercolortable[z2 - z1];
                drawxstack[offset] = midw + (x1 + ((xtexed$[v2] - x1) * j6 >> 16) << 9) / 50;
                drawstacky[offset] = midh + (y1 + ((ytexed$[v2] - y1) * j6 >> 16) << 9) / 50;
                vertexcolors[offset++] = c1 + ((vertexcolors2[id] - c1) * j6 >> 16);
            }
        }
        if(z2 >= 50)
        {
            drawxstack[offset] = xvertex$[v2];
            drawstacky[offset] = yvertex$[v2];
            vertexcolors[offset++] = vertexcolors2[id];
        } else
        {
            int x2 = xtexed$[v2];
            int y2 = ytexed$[v2];
            int c2 = vertexcolors2[id];
            if(z1 >= 50)
            {
                int k6 = (50 - z2) * othercolortable[z1 - z2];
                drawxstack[offset] = midw + (x2 + ((xtexed$[v1] - x2) * k6 >> 16) << 9) / 50;
                drawstacky[offset] = midh + (y2 + ((ytexed$[v1] - y2) * k6 >> 16) << 9) / 50;
                vertexcolors[offset++] = c2 + ((vertexcolors1[id] - c2) * k6 >> 16);
            }
            if(z0 >= 50)
            {
                int l6 = (50 - z2) * othercolortable[z0 - z2];
                drawxstack[offset] = midw + (x2 + ((xtexed$[v0] - x2) * l6 >> 16) << 9) / 50;
                drawstacky[offset] = midh + (y2 + ((ytexed$[v0] - y2) * l6 >> 16) << 9) / 50;
                vertexcolors[offset++] = c2 + ((vertexcolors0[id] - c2) * l6 >> 16);
            }
        }
        int x0 = drawxstack[0];
        int x1 = drawxstack[1];
        int x2 = drawxstack[2];
        int y0 = drawstacky[0];
        int y1 = drawstacky[1];
        int y2 = drawstacky[2];
        if((x0 - x1) * (y2 - y1) - (y0 - y1) * (x2 - x1) > 0) {
            TriangleRasterizer.clip = false;
            if(offset == 3) {
                if(x0 < 0 || x1 < 0 || x2 < 0 || x0 > BasicRasterizer.widthLength || x1 > BasicRasterizer.widthLength || x2 > BasicRasterizer.widthLength)
                    TriangleRasterizer.clip = true;
                int opcode;
                if(triangleopcodes == null)
                    opcode = 0;
                else
                    opcode = triangleopcodes[id] & 3;
                if(opcode == 0)
                    TriangleRasterizer.drawShadedTriangle(x1, x0, x2, y0, y1, y2, vertexcolors[1], vertexcolors[0], vertexcolors[2]);
                else
                if(opcode == 1)
                    TriangleRasterizer.drawSimpleTriangle(x1, x0, x2, y1, y0, y2, shadingtable[vertexcolors0[id]]);
                else if(opcode == 2) {
                    int j8 = triangleopcodes[id] >> 2;
                    int k9 = texedtrianglesx[j8];
                    int k10 = texedtrianglesy[j8];
                    int k11 = texedtrianglesz[j8];
                    TriangleRasterizer.drawTexturedTriangle(y0, y1, y2, x0, x1, x2, vertexcolors[0], vertexcolors[1], vertexcolors[2], xtexed$[k9], xtexed$[k10], xtexed$[k11], ytexed$[k9], ytexed$[k10], ytexed$[k11], ztexed$[k9], ztexed$[k10], ztexed$[k11], trianglecolors[id]);
                } else if(opcode == 3) {
                    int k8 = triangleopcodes[id] >> 2;
                    int l9 = texedtrianglesx[k8];
                    int l10 = texedtrianglesy[k8];
                    int l11 = texedtrianglesz[k8];
                    TriangleRasterizer.drawTexturedTriangle(y0, y1, y2, x0, x1, x2, vertexcolors0[id], vertexcolors0[id], vertexcolors0[id], xtexed$[l9], xtexed$[l10], xtexed$[l11], ytexed$[l9], ytexed$[l10], ytexed$[l11], ztexed$[l9], ztexed$[l10], ztexed$[l11], trianglecolors[id]);
                }
            } if(offset == 4) {
                if(x0 < 0 || x1 < 0 || x2 < 0 || x0 > BasicRasterizer.widthLength || x1 > BasicRasterizer.widthLength || x2 > BasicRasterizer.widthLength || drawxstack[3] < 0 || drawxstack[3] > BasicRasterizer.widthLength)
                    TriangleRasterizer.clip = true;
                int opcode;
                if(triangleopcodes == null)
                    opcode = 0;
                else
                    opcode = triangleopcodes[id] & 3;
                if(opcode == 0) {
                    TriangleRasterizer.drawShadedTriangle(x1, x0, x2, y0, y1, y2, vertexcolors[1], vertexcolors[0], vertexcolors[2]);
                    TriangleRasterizer.drawShadedTriangle(x2, x0, drawxstack[3], y0, y2, drawstacky[3], vertexcolors[2], vertexcolors[0], vertexcolors[3]);
                    return;
                } if(opcode == 1) {
                    int l8 = shadingtable[vertexcolors0[id]];
                    TriangleRasterizer.drawSimpleTriangle(x1, x0, x2, y1, y0, y2, l8);
                    TriangleRasterizer.drawSimpleTriangle(x2, x0, drawxstack[3], y2, y0, drawstacky[3], l8);
                    return;
                } if(opcode == 2)
                {
                    int i9 = triangleopcodes[id] >> 2;
                    int i10 = texedtrianglesx[i9];
                    int i11 = texedtrianglesy[i9];
                    int i12 = texedtrianglesz[i9];
                    TriangleRasterizer.drawTexturedTriangle(y0, y1, y2, x0, x1, x2, vertexcolors[0], vertexcolors[1], vertexcolors[2], xtexed$[i10], xtexed$[i11], xtexed$[i12], ytexed$[i10], ytexed$[i11], ytexed$[i12], ztexed$[i10], ztexed$[i11], ztexed$[i12], trianglecolors[id]);
                    TriangleRasterizer.drawTexturedTriangle(y0, y2, drawstacky[3], x0, x2, drawxstack[3], vertexcolors[0], vertexcolors[2], vertexcolors[3], xtexed$[i10], xtexed$[i11], xtexed$[i12], ytexed$[i10], ytexed$[i11], ytexed$[i12], ztexed$[i10], ztexed$[i11], ztexed$[i12], trianglecolors[id]);
                    return;
                }
                if(opcode == 3)
                {
                    int j9 = triangleopcodes[id] >> 2;
                    int j10 = texedtrianglesx[j9];
                    int j11 = texedtrianglesy[j9];
                    int j12 = texedtrianglesz[j9];
                    TriangleRasterizer.drawTexturedTriangle(y0, y1, y2, x0, x1, x2, vertexcolors0[id], vertexcolors0[id], vertexcolors0[id], xtexed$[j10], xtexed$[j11], xtexed$[j12], ytexed$[j10], ytexed$[j11], ytexed$[j12], ztexed$[j10], ztexed$[j11], ztexed$[j12], trianglecolors[id]);
                    TriangleRasterizer.drawTexturedTriangle(y0, y2, drawstacky[3], x0, x2, drawxstack[3], vertexcolors0[id], vertexcolors0[id], vertexcolors0[id], xtexed$[j10], xtexed$[j11], xtexed$[j12], ytexed$[j10], ytexed$[j11], ytexed$[j12], ztexed$[j10], ztexed$[j11], ztexed$[j12], trianglecolors[id]);
                }
            }
        }
    }

    public boolean inBounds(int i, int j, int k, int l, int i1, int j1, int k1, 
            int l1)
    {
        if(j < k && j < l && j < i1)
            return false;
        if(j > k && j > l && j > i1)
            return false;
        if(i < j1 && i < k1 && i < l1)
            return false;
        return i <= j1 || i <= k1 || i <= l1;
    }

    public int anInt1614;
    public boolean aBoolean1615;
    public int anInt1616;
    public int anInt1617;
    public boolean aBoolean1618;
    public static int anInt1619 = -192;
    public static int anInt1620;
    public static Model aActor_Sub6_1621 = new Model(true);
    public static int anIntArray1622[] = new int[2000];
    public static int anIntArray1623[] = new int[2000];
    public static int anIntArray1624[] = new int[2000];
    public static int anIntArray1625[] = new int[2000];
    public int numverticies;
    public int verticiesx[];
    public int verticiesy[];
    public int verticiesz[];
    public int numtriangles;
    public int trivertex0[];
    public int trivertex1[];
    public int trivertex2[];
    public int vertexcolors0[];
    public int vertexcolors1[];
    public int vertexcolors2[];
    public int triangleopcodes[];
    public int tripriorities[];
    public int trianglealphas[];
    public int trianglecolors[];
    public int anInt1641;
    public int numtexedtriangles;
    public int texedtrianglesx[];
    public int texedtrianglesy[];
    public int texedtrianglesz[];
    public int minx_;
    public int maxx_;
    public int anInt1648;
    public int anInt1649;
    public int maxdistxz;
    public int maxy;
    public int maxdistxzmaxy;
    public int maxdistxzminy;
    public int anInt1654;
    public int vgroups[];
    public int tgroups[];
    public int vertexgroups[][];
    public int trianglegroups[][];
    public boolean aBoolean1659;
    public GouraudVertex aClass33Array1660[];
    public static ModelData rawmodels[];
    public static NumericLoader numericloader;
    public static boolean cliptriangle[] = new boolean[4096];
    public static boolean aBooleanArray1664[] = new boolean[4096];
    public static int xvertex$[] = new int[4096];
    public static int yvertex$[] = new int[4096];
    public static int zvertex$[] = new int[4096];
    public static int xtexed$[] = new int[4096];
    public static int ytexed$[] = new int[4096];
    public static int ztexed$[] = new int[4096];
    public static int tridepthoffsets[] = new int[1500];
    public static int trideptharray[][] = new int[1500][512];
    public static int tripricount[] = new int[12];
    public static int pritriangles[][] = new int[12][2000];
    public static int anIntArray1675[] = new int[2000];
    public static int anIntArray1676[] = new int[2000];
    public static int pridistances[] = new int[12];
    public static int drawxstack[] = new int[10];
    public static int drawstacky[] = new int[10];
    public static int vertexcolors[] = new int[10];
    public static int referencex;
    public static int referencey;
    public static int referencez;
    public static boolean aBoolean1684;
    public static int anInt1685;
    public static int anInt1686;
    public static int anInt1687;
    public static int anIntArray1688[] = new int[1000];
    public static int sinetable[];
    public static int cosinetable[];
    public static int shadingtable[];
    public static int othercolortable[];

    static 
    {
        sinetable = TriangleRasterizer.SINE_TABLE;
        cosinetable = TriangleRasterizer.COSINE_TABLE;
        shadingtable = TriangleRasterizer.shading$;
        othercolortable = TriangleRasterizer.SHADINGTABLE;
    }
}
