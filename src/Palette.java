// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

import java.io.PrintStream;
import sign.Signlink;

public final class Palette {

    public Palette(int xsize, int ysize, int zsize, int[][][] heights) {
        aClass28Array444 = new GeneralEntity[5000];
        anIntArray486 = new int[10000];
        anIntArray487 = new int[10000];
        sizez = zsize;
        sizex = xsize;
        sizey = ysize;
        nodes = new PalletNode[zsize][xsize][ysize];
        tileupdatestamps = new int[zsize][xsize + 1][ysize + 1];
        heightmap = heights;
        reset();
    }

    public static void dystroy(int junk) {
        aClass28Array462 = null;
        anIntArray473 = null;
        aClass47ArrayArray474 = null;
        aClass19_477 = null;
        aBooleanArrayArrayArrayArray491 = null;
        aBooleanArrayArray492 = null;
    }

    public void reset() {
        for(int j = 0; j < sizez; j++) {
            for(int k = 0; k < sizex; k++) {
                for(int i1 = 0; i1 < sizey; i1++)
                    nodes[j][k][i1] = null;
            }
        }
        for(int l = 0; l < anInt472; l++) {
            for(int j1 = 0; j1 < anIntArray473[l]; j1++)
                aClass47ArrayArray474[l][j1] = null;
            anIntArray473[l] = 0;
        }
        for(int k1 = 0; k1 < anInt443; k1++)
            aClass28Array444[k1] = null;
        anInt443 = 0;
        for(int l1 = 0; l1 < aClass28Array462.length; l1++)
            aClass28Array462[l1] = null;
    }

    public void buildPlane(int z) {
        currentz = z;
        for(int k = 0; k < sizex; k++) {
            for(int l = 0; l < sizey; l++)
                if(nodes[z][k][l] == null)
                    nodes[z][k][l] = new PalletNode(z, k, l);
        }
    }

    public void method276(int i, int j, int junk) {
        PalletNode class30_sub3 = nodes[0][j][i];
        for(int l = 0; l < 3; l++) {
            PalletNode class30_sub3_1 = nodes[l][j][i] = nodes[l + 1][j][i];
            if(class30_sub3_1 != null) {
                class30_sub3_1.anInt1307--;
                for(int j1 = 0; j1 < class30_sub3_1.anInt1317; j1++) {
                    GeneralEntity class28 = class30_sub3_1.aClass28Array1318[j1];
                    if((class28.anInt529 >> 29 & 3) == 2 && class28.anInt523 == j && class28.anInt525 == i)
                        class28.anInt517--;
                }
            }
        }
        if(nodes[0][j][i] == null)
            nodes[0][j][i] = new PalletNode(0, j, i);
        nodes[0][j][i].aClass30_Sub3_1329 = class30_sub3;
        nodes[3][j][i] = null;
    }

    public static void method277(int i, int j, int k, int l, int i1, int j1, int junk, int l1, int i2) {
        PalleteNode$ class47 = new PalleteNode$();
        class47.anInt787 = j / 128;
        class47.anInt788 = l / 128;
        class47.anInt789 = l1 / 128;
        class47.anInt790 = i1 / 128;
        class47.anInt791 = i2;
        class47.anInt792 = j;
        class47.anInt793 = l;
        class47.anInt794 = l1;
        class47.anInt795 = i1;
        class47.anInt796 = j1;
        class47.anInt797 = k;
        aClass47ArrayArray474[i][anIntArray473[i]++] = class47;
    }

    public void method278(int i, int j, int k, int l) {
        PalletNode class30_sub3 = nodes[i][j][k];
        if(class30_sub3 == null) {
            return;
        } else {
            nodes[i][j][k].anInt1321 = l;
            return;
        }
    }

    public void method279(int i, int j, int k, int type, int i1, int textureid, int k1, int l1, int i2, int j2, int k2, int l2, int i3, int j3,  int k3, int l3, int i4, int j4, int k4, int l4) {
        if(type == 0) {
            TileGraphics class43 = new TileGraphics(k2, l2, i3, j3, -1, k4, false);
            for(int i5 = i; i5 >= 0; i5--)
                if(nodes[i5][j][k] == null)
                    nodes[i5][j][k] = new PalletNode(i5, j, k);
            nodes[i][j][k].shading = class43;
            return;
        }
        if(type == 1) {
            TileGraphics class43_1 = new TileGraphics(k3, l3, i4, j4, textureid, l4, k1 == l1 && k1 == i2 && k1 == j2);
            for(int j5 = i; j5 >= 0; j5--)
                if(nodes[j5][j][k] == null)
                    nodes[j5][j][k] = new PalletNode(j5, j, k);
            nodes[i][j][k].shading = class43_1;
            return;
        }
        PalleteNodeTexture class40 = new PalleteNodeTexture(k, k3, j3, i2, textureid, i4, i1, k2, k4, i3, j2, l1, k1, type, j4, l3, l2, 3, j, l4);
        for(int k5 = i; k5 >= 0; k5--)
            if(nodes[k5][j][k] == null)
                nodes[k5][j][k] = new PalletNode(k5, j, k);
        nodes[i][j][k].node_texture = class40;
    }

    public void method280(int i, int j, int k, int l, Entity class30_sub2_sub4, byte byte0, int i1, int j1) {
        if(class30_sub2_sub4 == null)
            return;
        FloorDecoration class49 = new FloorDecoration();
        class49.aActor_814 = class30_sub2_sub4;
        class49.anInt812 = j1 * 128 + 64;
        class49.anInt813 = k * 128 + 64;
        if(l <= 0)
            aBoolean435 = !aBoolean435;
        class49.anInt811 = j;
        class49.anInt815 = i1;
        class49.aByte816 = byte0;
        if(nodes[i][j1][k] == null)
            nodes[i][j1][k] = new PalletNode(i, j1, k);
        nodes[i][j1][k].aClass49_1315 = class49;
    }

    public void method281(byte byte0, int i, int j, Entity class30_sub2_sub4, int k, Entity class30_sub2_sub4_1, Entity class30_sub2_sub4_2, int l, int i1) {
        ItemTile class3 = new ItemTile();
        class3.aActor_48 = class30_sub2_sub4_2;
        class3.anInt46 = i * 128 + 64;
        class3.anInt47 = i1 * 128 + 64;
        if(byte0 != 7)
            return;
        class3.anInt45 = k;
        class3.anInt51 = j;
        class3.aActor_49 = class30_sub2_sub4;
        class3.aActor_50 = class30_sub2_sub4_1;
        int j1 = 0;
        PalletNode class30_sub3 = nodes[l][i][i1];
        if(class30_sub3 != null) {
            for(int k1 = 0; k1 < class30_sub3.anInt1317; k1++)
                if(class30_sub3.aClass28Array1318[k1].aActor_521 instanceof Model) {
                    int l1 = ((Model)class30_sub3.aClass28Array1318[k1].aActor_521).anInt1654;
                    if(l1 > j1)
                        j1 = l1;
                }
        }
        class3.anInt52 = j1;
        if(nodes[l][i][i1] == null)
            nodes[l][i][i1] = new PalletNode(l, i, i1);
        nodes[l][i][i1].aClass3_1316 = class3;
    }

    public void putWall(int i, Entity class30_sub2_sub4, boolean flag, int j, int k, byte byte0, int l, Entity class30_sub2_sub4_1, int i1, int j1, int k1) {
        if(!flag)
            aBoolean434 = !aBoolean434;
        if(class30_sub2_sub4 == null && class30_sub2_sub4_1 == null)
            return;
        Wall class10 = new Wall();
        class10.anInt280 = j;
        class10.aByte281 = byte0;
        class10.anInt274 = l * 128 + 64;
        class10.anInt275 = k * 128 + 64;
        class10.anInt273 = i1;
        class10.aActor_278 = class30_sub2_sub4;
        class10.aActor_279 = class30_sub2_sub4_1;
        class10.anInt276 = i;
        class10.anInt277 = j1;
        for(int l1 = k1; l1 >= 0; l1--)
            if(nodes[l1][l][k] == null)
                nodes[l1][l][k] = new PalletNode(l1, l, k);
        nodes[k1][l][k].aClass10_1313 = class10;
    }

    public void putWallDecoration(int objhash, int spawny, int objrotation, int junk, int spwnz, int xoffset, int k1, Entity entity, int spawnx, byte byte0, int yoffset, int j2) {
        if(entity == null)
            return;
        WallDecoration class26 = new WallDecoration();
        class26.wd_objhash = objhash;
        class26.aByte506 = byte0;
        class26.wd_xlocation = spawnx * 128 + 64 + xoffset;
        class26.wd_ylocation = spawny * 128 + 64 + yoffset;
        class26.anInt499 = k1;
        class26.wd_entity = entity;
        class26.anInt502 = j2;
        class26.wd_rotation = objrotation;
        for(int k2 = spwnz; k2 >= 0; k2--)
            if(nodes[k2][spawnx][spawny] == null)
                nodes[k2][spawnx][spawny] = new PalletNode(k2, spawnx, spawny);
        nodes[spwnz][spawnx][spawny].aClass26_1314 = class26;
    }

    public boolean method284(int i, byte byte0, int j, int k, Entity class30_sub2_sub4, int l, int i1, int j1, byte byte1, int k1, int l1) {
        if(byte1 != 110)
            anInt431 = 250;
        if(class30_sub2_sub4 == null) {
            return true;
        } else {
            int i2 = l1 * 128 + 64 * l;
            int j2 = k1 * 128 + 64 * k;
            return method287(i1, l1, k1, l, k, i2, j2, j, class30_sub2_sub4, j1, false, i, byte0);
        }
    }

    public boolean method285(int i, int j, byte byte0, int k, int l, int i1, int j1, int k1, Entity class30_sub2_sub4, boolean flag) {
        if(class30_sub2_sub4 == null)
            return true;
        int l1 = k1 - j1;
        int i2 = i1 - j1;
        int j2 = k1 + j1;
        int k2 = i1 + j1;
        if(flag) {
            if(j > 640 && j < 1408)
                k2 += 128;
            if(j > 1152 && j < 1920)
                j2 += 128;
            if(j > 1664 || j < 384)
                i2 -= 128;
            if(j > 128 && j < 896)
                l1 -= 128;
        }
        l1 /= 128;
        if(byte0 == 6)
            byte0 = 0;
        else
            throw new NullPointerException();
        i2 /= 128;
        j2 /= 128;
        k2 /= 128;
        return method287(i, l1, i2, (j2 - l1) + 1, (k2 - i2) + 1, k1, i1, k, class30_sub2_sub4, j, true, l, (byte)0);
    }

    public boolean method286(int i, int j, int k, Entity class30_sub2_sub4, int l, int i1, int j1, int k1, int l1, int i2, int j2, int k2, byte byte0) {
        if(byte0 != 35) {
            for(int l2 = 1; l2 > 0; l2++);
        }
        if(class30_sub2_sub4 == null)
            return true;
        else
            return method287(j, l1, k2, (i2 - l1) + 1, (i1 - k2) + 1, j1, k, k1, class30_sub2_sub4, l, true, j2, (byte)0);
    }

    public boolean method287(int i, int j, int k, int l, int i1, int j1, int k1, int l1, Entity class30_sub2_sub4, int i2, boolean flag, int j2, byte byte0) {
        for(int k2 = j; k2 < j + l; k2++) {
            for(int l2 = k; l2 < k + i1; l2++) {
                if(k2 < 0 || l2 < 0 || k2 >= sizex || l2 >= sizey)
                    return false;
                PalletNode class30_sub3 = nodes[i][k2][l2];
                if(class30_sub3 != null && class30_sub3.anInt1317 >= 5)
                    return false;
            }
        }
        GeneralEntity class28 = new GeneralEntity();
        class28.anInt529 = j2;
        class28.aByte530 = byte0;
        class28.anInt517 = i;
        class28.anInt519 = j1;
        class28.anInt520 = k1;
        class28.anInt518 = l1;
        class28.aActor_521 = class30_sub2_sub4;
        class28.anInt522 = i2;
        class28.anInt523 = j;
        class28.anInt525 = k;
        class28.anInt524 = (j + l) - 1;
        class28.anInt526 = (k + i1) - 1;
        for(int i3 = j; i3 < j + l; i3++) {
            for(int j3 = k; j3 < k + i1; j3++) {
                int k3 = 0;
                if(i3 > j)
                    k3++;
                if(i3 < (j + l) - 1)
                    k3 += 4;
                if(j3 > k)
                    k3 += 8;
                if(j3 < (k + i1) - 1)
                    k3 += 2;
                for(int l3 = i; l3 >= 0; l3--)
                    if(nodes[l3][i3][j3] == null)
                        nodes[l3][i3][j3] = new PalletNode(l3, i3, j3);
                PalletNode class30_sub3_1 = nodes[i][i3][j3];
                class30_sub3_1.aClass28Array1318[class30_sub3_1.anInt1317] = class28;
                class30_sub3_1.anIntArray1319[class30_sub3_1.anInt1317] = k3;
                class30_sub3_1.anInt1320 |= k3;
                class30_sub3_1.anInt1317++;
            }

        }
        if(flag)
            aClass28Array444[anInt443++] = class28;
        return true;
    }

    public void method288(byte byte0) {
        if(byte0 != 104)
            aBoolean435 = !aBoolean435;
        for(int i = 0; i < anInt443; i++) {
            GeneralEntity class28 = aClass28Array444[i];
            method289(-997, class28);
            aClass28Array444[i] = null;
        }
        anInt443 = 0;
    }

    public void method289(int i, GeneralEntity class28) {
        if(i >= 0)
            return;
        for(int j = class28.anInt523; j <= class28.anInt524; j++) {
            for(int k = class28.anInt525; k <= class28.anInt526; k++) {
                PalletNode class30_sub3 = nodes[class28.anInt517][j][k];
                if(class30_sub3 != null) {
                    for(int l = 0; l < class30_sub3.anInt1317; l++) {
                        if(class30_sub3.aClass28Array1318[l] != class28)
                            continue;
                        class30_sub3.anInt1317--;
                        for(int i1 = l; i1 < class30_sub3.anInt1317; i1++) {
                            class30_sub3.aClass28Array1318[i1] = class30_sub3.aClass28Array1318[i1 + 1];
                            class30_sub3.anIntArray1319[i1] = class30_sub3.anIntArray1319[i1 + 1];
                        }
                        class30_sub3.aClass28Array1318[class30_sub3.anInt1317] = null;
                        break;
                    }

                    class30_sub3.anInt1320 = 0;
                    for(int j1 = 0; j1 < class30_sub3.anInt1317; j1++)
                        class30_sub3.anInt1320 |= class30_sub3.anIntArray1319[j1];
                }
            }
        }
    }

    public void method290(int i, int j, int k, int l, int i1) {
        PalletNode class30_sub3 = nodes[i1][l][i];
        if(j <= 0)
            aBoolean429 = !aBoolean429;
        if(class30_sub3 == null)
            return;
        WallDecoration class26 = class30_sub3.aClass26_1314;
        if(class26 == null) {
            return;
        } else {
            int j1 = l * 128 + 64;
            int k1 = i * 128 + 64;
            class26.wd_xlocation = j1 + ((class26.wd_xlocation - j1) * k) / 16;
            class26.wd_ylocation = k1 + ((class26.wd_ylocation - k1) * k) / 16;
            return;
        }
    }

    public void method291(int i, int j, int k, byte byte0) {
        PalletNode class30_sub3 = nodes[j][i][k];
        if(byte0 != -119)
            aBoolean434 = !aBoolean434;
        if(class30_sub3 == null) {
            return;
        } else {
            class30_sub3.aClass10_1313 = null;
            return;
        }
    }

    public void method292(int x,int y, int z) {
        PalletNode node = nodes[z][x][x];
        if(node == null) {
            return;
        } else {
            node.aClass26_1314 = null;
            return;
        }
    }

    public void method293(int x, int y, int z) {
        PalletNode node = nodes[z][x][y];
        if(node == null)
            return;
        for(int j1 = 0; j1 < node.anInt1317; j1++) {
            GeneralEntity class28 = node.aClass28Array1318[j1];
            if((class28.anInt529 >> 29 & 3) == 2 && class28.anInt523 == x && class28.anInt525 == y)
            {
                method289(-997, class28);
                return;
            }
        }
    }

    public void method294(byte byte0, int i, int j, int k)
    {
        PalletNode class30_sub3 = nodes[i][k][j];
        if(class30_sub3 == null)
            return;
        class30_sub3.aClass49_1315 = null;
        if(byte0 == 9)
            byte0 = 0;
    }

    public void method295(int i, int j, int k)
    {
        PalletNode class30_sub3 = nodes[i][j][k];
        if(class30_sub3 == null)
        {
            return;
        } else
        {
            class30_sub3.aClass3_1316 = null;
            return;
        }
    }

    public Wall getWall(int i, int j, int k, boolean junk) {
        PalletNode class30_sub3 = nodes[i][j][k];
        if(class30_sub3 == null)
            return null;
        else
            return class30_sub3.aClass10_1313;
    }

    public WallDecoration getWallDecoration(int i, int junk, int k, int l) {
        PalletNode class30_sub3 = nodes[l][i][k];
        if(class30_sub3 == null)
            return null;
        else
            return class30_sub3.aClass26_1314;
    }

    public GeneralEntity method298(int i, int j, byte junk, int k)
    {
        PalletNode class30_sub3 = nodes[k][i][j];
        if(class30_sub3 == null)
            return null;
        for(int l = 0; l < class30_sub3.anInt1317; l++) {
            GeneralEntity class28 = class30_sub3.aClass28Array1318[l];
            if((class28.anInt529 >> 29 & 3) == 2 && class28.anInt523 == i && class28.anInt525 == j)
                return class28;
        }
        return null;
    }

    public FloorDecoration getFloorDecoration(int i, int j, int k, int junk)
    {
        PalletNode class30_sub3 = nodes[k][j][i];
        if(class30_sub3 == null || class30_sub3.aClass49_1315 == null)
            return null;
        else
            return class30_sub3.aClass49_1315;
    }

    public int method300(int i, int j, int k)
    {
        PalletNode class30_sub3 = nodes[i][j][k];
        if(class30_sub3 == null || class30_sub3.aClass10_1313 == null)
            return 0;
        else
            return class30_sub3.aClass10_1313.anInt280;
    }

    public int method301(int i, int j, int k, int l)
    {
        PalletNode class30_sub3 = nodes[i][j][l];
        if(k != 0)
            return anInt430;
        if(class30_sub3 == null || class30_sub3.aClass26_1314 == null)
            return 0;
        else
            return class30_sub3.aClass26_1314.wd_objhash;
    }

    public int method302(int i, int j, int k)
    {
        PalletNode class30_sub3 = nodes[i][j][k];
        if(class30_sub3 == null)
            return 0;
        for(int l = 0; l < class30_sub3.anInt1317; l++)
        {
            GeneralEntity class28 = class30_sub3.aClass28Array1318[l];
            if((class28.anInt529 >> 29 & 3) == 2 && class28.anInt523 == j && class28.anInt525 == k)
                return class28.anInt529;
        }

        return 0;
    }

    public int method303(int i, int j, int k)
    {
        PalletNode class30_sub3 = nodes[i][j][k];
        if(class30_sub3 == null || class30_sub3.aClass49_1315 == null)
            return 0;
        else
            return class30_sub3.aClass49_1315.anInt815;
    }

    public int method304(int i, int j, int k, int l)
    {
        PalletNode class30_sub3 = nodes[i][j][k];
        if(class30_sub3 == null)
            return -1;
        if(class30_sub3.aClass10_1313 != null && class30_sub3.aClass10_1313.anInt280 == l)
            return class30_sub3.aClass10_1313.aByte281 & 0xff;
        if(class30_sub3.aClass26_1314 != null && class30_sub3.aClass26_1314.wd_objhash == l)
            return class30_sub3.aClass26_1314.aByte506 & 0xff;
        if(class30_sub3.aClass49_1315 != null && class30_sub3.aClass49_1315.anInt815 == l)
            return class30_sub3.aClass49_1315.aByte816 & 0xff;
        for(int i1 = 0; i1 < class30_sub3.anInt1317; i1++)
            if(class30_sub3.aClass28Array1318[i1].anInt529 == l)
                return class30_sub3.aClass28Array1318[i1].aByte530 & 0xff;

        return -1;
    }

    public void method305(int i, byte byte0, int j, int k, int l, int i1)
    {
        int j1 = (int)Math.sqrt(k * k + i * i + i1 * i1);
        int k1 = l * j1 >> 8;
        if(byte0 != 3)
            aBoolean434 = !aBoolean434;
        for(int l1 = 0; l1 < sizez; l1++)
        {
            for(int i2 = 0; i2 < sizex; i2++)
            {
                for(int j2 = 0; j2 < sizey; j2++)
                {
                    PalletNode class30_sub3 = nodes[l1][i2][j2];
                    if(class30_sub3 != null)
                    {
                        Wall class10 = class30_sub3.aClass10_1313;
                        if(class10 != null && class10.aActor_278 != null && class10.aActor_278.surfaces != null)
                        {
                            method307(l1, 1, 1, i2, (byte)115, j2, (Model)class10.aActor_278);
                            if(class10.aActor_279 != null && class10.aActor_279.surfaces != null)
                            {
                                method307(l1, 1, 1, i2, (byte)115, j2, (Model)class10.aActor_279);
                                method308((Model)class10.aActor_278, (Model)class10.aActor_279, 0, 0, 0, false);
                                ((Model)class10.aActor_279).applyShading(k, i, i1, k1, j);
                            }
                            ((Model)class10.aActor_278).applyShading(k, i, i1, k1, j);
                        }
                        for(int k2 = 0; k2 < class30_sub3.anInt1317; k2++)
                        {
                            GeneralEntity class28 = class30_sub3.aClass28Array1318[k2];
                            if(class28 != null && class28.aActor_521 != null && class28.aActor_521.surfaces != null)
                            {
                                method307(l1, (class28.anInt524 - class28.anInt523) + 1, (class28.anInt526 - class28.anInt525) + 1, i2, (byte)115, j2, (Model)class28.aActor_521);
                                ((Model)class28.aActor_521).applyShading(k, i, i1, k1, j);
                            }
                        }

                        FloorDecoration class49 = class30_sub3.aClass49_1315;
                        if(class49 != null && class49.aActor_814.surfaces != null)
                        {
                            method306(i2, l1, (Model)class49.aActor_814, (byte)37, j2);
                            ((Model)class49.aActor_814).applyShading(k, i, i1, k1, j);
                        }
                    }
                }

            }

        }

    }

    public void method306(int i, int j, Model class30_sub2_sub4_sub6, byte byte0, int k)
    {
        if(byte0 != 37)
        {
            for(int l = 1; l > 0; l++);
        }
        if(i < sizex)
        {
            PalletNode class30_sub3 = nodes[j][i + 1][k];
            if(class30_sub3 != null && class30_sub3.aClass49_1315 != null && class30_sub3.aClass49_1315.aActor_814.surfaces != null)
                method308(class30_sub2_sub4_sub6, (Model)class30_sub3.aClass49_1315.aActor_814, 128, 0, 0, true);
        }
        if(k < sizex)
        {
            PalletNode class30_sub3_1 = nodes[j][i][k + 1];
            if(class30_sub3_1 != null && class30_sub3_1.aClass49_1315 != null && class30_sub3_1.aClass49_1315.aActor_814.surfaces != null)
                method308(class30_sub2_sub4_sub6, (Model)class30_sub3_1.aClass49_1315.aActor_814, 0, 0, 128, true);
        }
        if(i < sizex && k < sizey)
        {
            PalletNode class30_sub3_2 = nodes[j][i + 1][k + 1];
            if(class30_sub3_2 != null && class30_sub3_2.aClass49_1315 != null && class30_sub3_2.aClass49_1315.aActor_814.surfaces != null)
                method308(class30_sub2_sub4_sub6, (Model)class30_sub3_2.aClass49_1315.aActor_814, 128, 0, 128, true);
        }
        if(i < sizex && k > 0)
        {
            PalletNode class30_sub3_3 = nodes[j][i + 1][k - 1];
            if(class30_sub3_3 != null && class30_sub3_3.aClass49_1315 != null && class30_sub3_3.aClass49_1315.aActor_814.surfaces != null)
                method308(class30_sub2_sub4_sub6, (Model)class30_sub3_3.aClass49_1315.aActor_814, 128, 0, -128, true);
        }
    }

    public void method307(int i, int j, int k, int l, byte byte0, int i1, Model class30_sub2_sub4_sub6)
    {
        boolean flag = true;
        if(byte0 != 115)
            anInt431 = 350;
        int j1 = l;
        int k1 = l + j;
        int l1 = i1 - 1;
        int i2 = i1 + k;
        for(int j2 = i; j2 <= i + 1; j2++)
            if(j2 != sizez)
            {
                for(int k2 = j1; k2 <= k1; k2++)
                    if(k2 >= 0 && k2 < sizex)
                    {
                        for(int l2 = l1; l2 <= i2; l2++)
                            if(l2 >= 0 && l2 < sizey && (!flag || k2 >= k1 || l2 >= i2 || l2 < i1 && k2 != l))
                            {
                                PalletNode class30_sub3 = nodes[j2][k2][l2];
                                if(class30_sub3 != null)
                                {
                                    int i3 = (heightmap[j2][k2][l2] + heightmap[j2][k2 + 1][l2] + heightmap[j2][k2][l2 + 1] + heightmap[j2][k2 + 1][l2 + 1]) / 4 - (heightmap[i][l][i1] + heightmap[i][l + 1][i1] + heightmap[i][l][i1 + 1] + heightmap[i][l + 1][i1 + 1]) / 4;
                                    Wall class10 = class30_sub3.aClass10_1313;
                                    if(class10 != null && class10.aActor_278 != null && class10.aActor_278.surfaces != null)
                                        method308(class30_sub2_sub4_sub6, (Model)class10.aActor_278, (k2 - l) * 128 + (1 - j) * 64, i3, (l2 - i1) * 128 + (1 - k) * 64, flag);
                                    if(class10 != null && class10.aActor_279 != null && class10.aActor_279.surfaces != null)
                                        method308(class30_sub2_sub4_sub6, (Model)class10.aActor_279, (k2 - l) * 128 + (1 - j) * 64, i3, (l2 - i1) * 128 + (1 - k) * 64, flag);
                                    for(int j3 = 0; j3 < class30_sub3.anInt1317; j3++)
                                    {
                                        GeneralEntity class28 = class30_sub3.aClass28Array1318[j3];
                                        if(class28 != null && class28.aActor_521 != null && class28.aActor_521.surfaces != null)
                                        {
                                            int k3 = (class28.anInt524 - class28.anInt523) + 1;
                                            int l3 = (class28.anInt526 - class28.anInt525) + 1;
                                            method308(class30_sub2_sub4_sub6, (Model)class28.aActor_521, (class28.anInt523 - l) * 128 + (k3 - j) * 64, i3, (class28.anInt525 - i1) * 128 + (l3 - k) * 64, flag);
                                        }
                                    }

                                }
                            }

                    }

                j1--;
                flag = false;
            }

    }

    public void method308(Model class30_sub2_sub4_sub6, Model class30_sub2_sub4_sub6_1, int i, int j, int k, boolean flag)
    {
        anInt488++;
        int l = 0;
        int ai[] = class30_sub2_sub4_sub6_1.verticiesx;
        int i1 = class30_sub2_sub4_sub6_1.numverticies;
        for(int j1 = 0; j1 < class30_sub2_sub4_sub6.numverticies; j1++)
        {
            GouraudVertex surface0 = ((Entity) (class30_sub2_sub4_sub6)).surfaces[j1];
            GouraudVertex surface1 = class30_sub2_sub4_sub6.aClass33Array1660[j1];
            if(surface1.values != 0)
            {
                int i2 = class30_sub2_sub4_sub6.verticiesy[j1] - j;
                if(i2 <= class30_sub2_sub4_sub6_1.maxy)
                {
                    int j2 = class30_sub2_sub4_sub6.verticiesx[j1] - i;
                    if(j2 >= class30_sub2_sub4_sub6_1.minx_ && j2 <= class30_sub2_sub4_sub6_1.maxx_)
                    {
                        int k2 = class30_sub2_sub4_sub6.verticiesz[j1] - k;
                        if(k2 >= class30_sub2_sub4_sub6_1.anInt1649 && k2 <= class30_sub2_sub4_sub6_1.anInt1648)
                        {
                            for(int l2 = 0; l2 < i1; l2++)
                            {
                                GouraudVertex class33_2 = ((Entity) (class30_sub2_sub4_sub6_1)).surfaces[l2];
                                GouraudVertex class33_3 = class30_sub2_sub4_sub6_1.aClass33Array1660[l2];
                                if(j2 == ai[l2] && k2 == class30_sub2_sub4_sub6_1.verticiesz[l2] && i2 == class30_sub2_sub4_sub6_1.verticiesy[l2] && class33_3.values != 0)
                                {
                                    surface0.vectorx += class33_3.vectorx;
                                    surface0.vectory += class33_3.vectory;
                                    surface0.vectorz += class33_3.vectorz;
                                    surface0.values += class33_3.values;
                                    class33_2.vectorx += surface1.vectorx;
                                    class33_2.vectory += surface1.vectory;
                                    class33_2.vectorz += surface1.vectorz;
                                    class33_2.values += surface1.values;
                                    l++;
                                    anIntArray486[j1] = anInt488;
                                    anIntArray487[l2] = anInt488;
                                }
                            }

                        }
                    }
                }
            }
        }

        if(l < 3 || !flag)
            return;
        for(int k1 = 0; k1 < class30_sub2_sub4_sub6.numtriangles; k1++)
            if(anIntArray486[class30_sub2_sub4_sub6.trivertex0[k1]] == anInt488 && anIntArray486[class30_sub2_sub4_sub6.trivertex1[k1]] == anInt488 && anIntArray486[class30_sub2_sub4_sub6.trivertex2[k1]] == anInt488)
                class30_sub2_sub4_sub6.triangleopcodes[k1] = -1;

        for(int l1 = 0; l1 < class30_sub2_sub4_sub6_1.numtriangles; l1++)
            if(anIntArray487[class30_sub2_sub4_sub6_1.trivertex0[l1]] == anInt488 && anIntArray487[class30_sub2_sub4_sub6_1.trivertex1[l1]] == anInt488 && anIntArray487[class30_sub2_sub4_sub6_1.trivertex2[l1]] == anInt488)
                class30_sub2_sub4_sub6_1.triangleopcodes[l1] = -1;

    }

    public void method309(int ai[], int i, int j, int z, int x, int y)
    {
        PalletNode class30_sub3 = nodes[z][x][y];
        if(class30_sub3 == null)
            return;
        TileGraphics class43 = class30_sub3.shading;
        if(class43 != null)
        {
            int j1 = class43.anInt722;
            if(j1 == 0)
                return;
            for(int k1 = 0; k1 < 4; k1++)
            {
                ai[i] = j1;
                ai[i + 1] = j1;
                ai[i + 2] = j1;
                ai[i + 3] = j1;
                i += j;
            }

            return;
        }
        PalleteNodeTexture class40 = class30_sub3.node_texture;
        if(class40 == null)
            return;
        int l1 = class40.anInt684;
        int i2 = class40.anInt685;
        int j2 = class40.anInt686;
        int k2 = class40.anInt687;
        int ai1[] = anIntArrayArray489[l1];
        int ai2[] = anIntArrayArray490[i2];
        int l2 = 0;
        if(j2 != 0)
        {
            for(int i3 = 0; i3 < 4; i3++)
            {
                ai[i] = ai1[ai2[l2++]] != 0 ? k2 : j2;
                ai[i + 1] = ai1[ai2[l2++]] != 0 ? k2 : j2;
                ai[i + 2] = ai1[ai2[l2++]] != 0 ? k2 : j2;
                ai[i + 3] = ai1[ai2[l2++]] != 0 ? k2 : j2;
                i += j;
            }

            return;
        }
        for(int j3 = 0; j3 < 4; j3++)
        {
            if(ai1[ai2[l2++]] != 0)
                ai[i] = k2;
            if(ai1[ai2[l2++]] != 0)
                ai[i + 1] = k2;
            if(ai1[ai2[l2++]] != 0)
                ai[i + 2] = k2;
            if(ai1[ai2[l2++]] != 0)
                ai[i + 3] = k2;
            i += j;
        }

    }

    public static void method310(int i, int j, int k, int l, int ai[], boolean flag)
    {
        anInt495 = 0;
        anInt496 = 0;
        anInt497 = k;
        anInt498 = l;
        anInt493 = k / 2;
        anInt494 = l / 2;
        boolean aflag[][][][] = new boolean[9][32][53][53];
        if(flag)
            anInt433 = 168;
		/* 22 degrees -> 72 degrees */
        for(int i1 = 128; i1 <= 384; i1 += 32)
        {
			/* 0 degrees -> 360 degrees */
            for(int j1 = 0; j1 < 2048; j1 += 64)
            {
                sinepitch = Model.sinetable[i1];
                cosinepitch = Model.cosinetable[i1];
				/* 11 degree incrememnts (0 - 360) */
                sineyaw = Model.sinetable[j1];
                cosineyaw = Model.cosinetable[j1];
                int l1 = (i1 - 128) / 32;
                int j2 = j1 / 64;
                for(int l2 = -26; l2 <= 26; l2++)
                {
                    for(int j3 = -26; j3 <= 26; j3++)
                    {
                        int k3 = l2 * 128;
                        int i4 = j3 * 128;
                        boolean flag2 = false;
                        for(int k4 = -i; k4 <= j; k4 += 128)
                        {
                            if(!method311((byte)9, ai[l1] + k4, i4, k3))
                                continue;
                            flag2 = true;
                            break;
                        }

                        aflag[l1][j2][l2 + 25 + 1][j3 + 25 + 1] = flag2;
                    }

                }

            }

        }

        for(int k1 = 0; k1 < 8; k1++)
        {
            for(int i2 = 0; i2 < 32; i2++)
            {
                for(int k2 = -25; k2 < 25; k2++)
                {
                    for(int i3 = -25; i3 < 25; i3++)
                    {
                        boolean flag1 = false;
label0:
                        for(int l3 = -1; l3 <= 1; l3++)
                        {
                            for(int j4 = -1; j4 <= 1; j4++)
                            {
                                if(aflag[k1][i2][k2 + l3 + 25 + 1][i3 + j4 + 25 + 1])
                                    flag1 = true;
                                else
                                if(aflag[k1][(i2 + 1) % 31][k2 + l3 + 25 + 1][i3 + j4 + 25 + 1])
                                    flag1 = true;
                                else
                                if(aflag[k1 + 1][i2][k2 + l3 + 25 + 1][i3 + j4 + 25 + 1])
                                {
                                    flag1 = true;
                                } else
                                {
                                    if(!aflag[k1 + 1][(i2 + 1) % 31][k2 + l3 + 25 + 1][i3 + j4 + 25 + 1])
                                        continue;
                                    flag1 = true;
                                }
                                break label0;
                            }

                        }

                        aBooleanArrayArrayArrayArray491[k1][i2][k2 + 25][i3 + 25] = flag1;
                    }

                }

            }

        }

    }

    public static boolean method311(byte byte0, int i, int j, int k)
    {
        int l = j * sineyaw + k * cosineyaw >> 16;
        int i1 = j * cosineyaw - k * sineyaw >> 16;
        if(byte0 != 9)
            anInt431 = -346;
        int j1 = i * sinepitch + i1 * cosinepitch >> 16;
        int k1 = i * cosinepitch - i1 * sinepitch >> 16;
        if(j1 < 50 || j1 > 3500)
            return false;
        int l1 = anInt493 + (l << 9) / j1;
        int i2 = anInt494 + (k1 << 9) / j1;
        return l1 >= anInt495 && l1 <= anInt497 && i2 >= anInt496 && i2 <= anInt498;
    }

    public void method312(boolean flag, int i, int j)
    {
        aBoolean467 = true;
        anInt468 = j;
        anInt469 = i;
        anInt470 = -1;
        anInt471 = -1;
        if(flag)
        {
            for(int k = 1; k > 0; k++);
        }
    }

    public void setDimensions(int finex, int finey, int yaw, int l, int i1, int pitch, boolean flag)
    {
        if(finex < 0)
            finex = 0;
        else
        if(finex >= sizex * 128)
            finex = sizex * 128 - 1;
        if(finey < 0)
            finey = 0;
        else
        if(finey >= sizey * 128)
            finey = sizey * 128 - 1;
        palettecycle++;
        sinepitch = Model.sinetable[pitch];
        cosinepitch = Model.cosinetable[pitch];
        if(flag)
            return;
        sineyaw = Model.sinetable[yaw];
        cosineyaw = Model.cosinetable[yaw];
        aBooleanArrayArray492 = aBooleanArrayArrayArrayArray491[(pitch - 128) / 32][yaw / 64];
        finesizex = finex;
        cameraz = l;
        finesizey = finey;
        currentx = finex / 128;
        currenty = finey / 128;
        currentz$ = i1;
        minclipx = currentx - 25;
        if(minclipx < 0)
            minclipx = 0;
        minclipy = currenty - 25;
        if(minclipy < 0)
            minclipy = 0;
        maxclipx = currentx + 25;
        if(maxclipx > sizex)
            maxclipx = sizex;
        maxclipy = currenty + 25;
        if(maxclipy > sizey)
            maxclipy = sizey;
        method319(0);
        anInt446 = 0;
        for(int k1 = currentz; k1 < sizez; k1++) {
            PalletNode aclass30_sub3[][] = nodes[k1];
            for(int i2 = minclipx; i2 < maxclipx; i2++) {
                for(int k2 = minclipy; k2 < maxclipy; k2++) {
                    PalletNode class30_sub3 = aclass30_sub3[i2][k2];
                    if(class30_sub3 != null)
                        if(class30_sub3.anInt1321 > i1 || !aBooleanArrayArray492[(i2 - currentx) + 25][(k2 - currenty) + 25] && heightmap[k1][i2][k2] - l < 2000) {
                            class30_sub3.aBoolean1322 = false;
                            class30_sub3.aBoolean1323 = false;
                            class30_sub3.anInt1325 = 0;
                        } else
                        {
                            class30_sub3.aBoolean1322 = true;
                            class30_sub3.aBoolean1323 = true;
                            if(class30_sub3.anInt1317 > 0)
                                class30_sub3.aBoolean1324 = true;
                            else
                                class30_sub3.aBoolean1324 = false;
                            anInt446++;
                        }
                }

            }

        }

        for(int l1 = currentz; l1 < sizez; l1++)
        {
            PalletNode aclass30_sub3_1[][] = nodes[l1];
            for(int l2 = -25; l2 <= 0; l2++)
            {
                int i3 = currentx + l2;
                int k3 = currentx - l2;
                if(i3 >= minclipx || k3 < maxclipx)
                {
                    for(int i4 = -25; i4 <= 0; i4++)
                    {
                        int k4 = currenty + i4;
                        int i5 = currenty - i4;
                        if(i3 >= minclipx)
                        {
                            if(k4 >= minclipy)
                            {
                                PalletNode class30_sub3_1 = aclass30_sub3_1[i3][k4];
                                if(class30_sub3_1 != null && class30_sub3_1.aBoolean1322)
                                    method314(class30_sub3_1, true);
                            }
                            if(i5 < maxclipy)
                            {
                                PalletNode class30_sub3_2 = aclass30_sub3_1[i3][i5];
                                if(class30_sub3_2 != null && class30_sub3_2.aBoolean1322)
                                    method314(class30_sub3_2, true);
                            }
                        }
                        if(k3 < maxclipx)
                        {
                            if(k4 >= minclipy)
                            {
                                PalletNode class30_sub3_3 = aclass30_sub3_1[k3][k4];
                                if(class30_sub3_3 != null && class30_sub3_3.aBoolean1322)
                                    method314(class30_sub3_3, true);
                            }
                            if(i5 < maxclipy)
                            {
                                PalletNode class30_sub3_4 = aclass30_sub3_1[k3][i5];
                                if(class30_sub3_4 != null && class30_sub3_4.aBoolean1322)
                                    method314(class30_sub3_4, true);
                            }
                        }
                        if(anInt446 == 0)
                        {
                            aBoolean467 = false;
                            return;
                        }
                    }

                }
            }

        }

        for(int j2 = currentz; j2 < sizez; j2++)
        {
            PalletNode aclass30_sub3_2[][] = nodes[j2];
            for(int j3 = -25; j3 <= 0; j3++)
            {
                int l3 = currentx + j3;
                int j4 = currentx - j3;
                if(l3 >= minclipx || j4 < maxclipx)
                {
                    for(int l4 = -25; l4 <= 0; l4++)
                    {
                        int j5 = currenty + l4;
                        int k5 = currenty - l4;
                        if(l3 >= minclipx)
                        {
                            if(j5 >= minclipy)
                            {
                                PalletNode class30_sub3_5 = aclass30_sub3_2[l3][j5];
                                if(class30_sub3_5 != null && class30_sub3_5.aBoolean1322)
                                    method314(class30_sub3_5, false);
                            }
                            if(k5 < maxclipy)
                            {
                                PalletNode class30_sub3_6 = aclass30_sub3_2[l3][k5];
                                if(class30_sub3_6 != null && class30_sub3_6.aBoolean1322)
                                    method314(class30_sub3_6, false);
                            }
                        }
                        if(j4 < maxclipx)
                        {
                            if(j5 >= minclipy)
                            {
                                PalletNode class30_sub3_7 = aclass30_sub3_2[j4][j5];
                                if(class30_sub3_7 != null && class30_sub3_7.aBoolean1322)
                                    method314(class30_sub3_7, false);
                            }
                            if(k5 < maxclipy)
                            {
                                PalletNode class30_sub3_8 = aclass30_sub3_2[j4][k5];
                                if(class30_sub3_8 != null && class30_sub3_8.aBoolean1322)
                                    method314(class30_sub3_8, false);
                            }
                        }
                        if(anInt446 == 0)
                        {
                            aBoolean467 = false;
                            return;
                        }
                    }

                }
            }

        }

        aBoolean467 = false;
    }

    public void method314(PalletNode class30_sub3, boolean flag)
    {
        aClass19_477.addLast(class30_sub3);
        do
        {
            PalletNode node;
            do
            {
                node = (PalletNode) aClass19_477.removeFirst();
                if(node == null)
                    return;
            } while(!node.aBoolean1323);
            int x = node.anInt1308;
            int y = node.anInt1309;
            int k = node.anInt1307;
            int z = node.anInt1310;
            PalletNode aclass30_sub3[][] = nodes[k];
            if(node.aBoolean1322)
            {
                if(flag)
                {
                    if(k > 0)
                    {
                        PalletNode class30_sub3_2 = nodes[k - 1][x][y];
                        if(class30_sub3_2 != null && class30_sub3_2.aBoolean1323)
                            continue;
                    }
                    if(x <= currentx && x > minclipx)
                    {
                        PalletNode class30_sub3_3 = aclass30_sub3[x - 1][y];
                        if(class30_sub3_3 != null && class30_sub3_3.aBoolean1323 && (class30_sub3_3.aBoolean1322 || (node.anInt1320 & 1) == 0))
                            continue;
                    }
                    if(x >= currentx && x < maxclipx - 1)
                    {
                        PalletNode class30_sub3_4 = aclass30_sub3[x + 1][y];
                        if(class30_sub3_4 != null && class30_sub3_4.aBoolean1323 && (class30_sub3_4.aBoolean1322 || (node.anInt1320 & 4) == 0))
                            continue;
                    }
                    if(y <= currenty && y > minclipy)
                    {
                        PalletNode class30_sub3_5 = aclass30_sub3[x][y - 1];
                        if(class30_sub3_5 != null && class30_sub3_5.aBoolean1323 && (class30_sub3_5.aBoolean1322 || (node.anInt1320 & 8) == 0))
                            continue;
                    }
                    if(y >= currenty && y < maxclipy - 1)
                    {
                        PalletNode class30_sub3_6 = aclass30_sub3[x][y + 1];
                        if(class30_sub3_6 != null && class30_sub3_6.aBoolean1323 && (class30_sub3_6.aBoolean1322 || (node.anInt1320 & 2) == 0))
                            continue;
                    }
                } else
                {
                    flag = true;
                }
                node.aBoolean1322 = false;
                if(node.aClass30_Sub3_1329 != null)
                {
                    PalletNode class30_sub3_7 = node.aClass30_Sub3_1329;
                    if(class30_sub3_7.shading != null)
                    {
                        if(!isTileDrawn(x, y, 0))
                            drawTile(class30_sub3_7.shading, x, y, 0, sinepitch, cosinepitch, sineyaw, cosineyaw);
                    } else
                    if(class30_sub3_7.node_texture != null && !isTileDrawn(x, y, 0))
                        method316(x, (byte)99, sinepitch, sineyaw, class30_sub3_7.node_texture, cosinepitch, y, cosineyaw);
                    Wall class10 = class30_sub3_7.aClass10_1313;
                    if(class10 != null)
                        class10.aActor_278.drawModel(0, sinepitch, cosinepitch, sineyaw, cosineyaw, class10.anInt274 - finesizex, class10.anInt273 - cameraz, class10.anInt275 - finesizey, class10.anInt280);
                    for(int i2 = 0; i2 < class30_sub3_7.anInt1317; i2++)
                    {
                        GeneralEntity class28 = class30_sub3_7.aClass28Array1318[i2];
                        if(class28 != null)
                            class28.aActor_521.drawModel(class28.anInt522, sinepitch, cosinepitch, sineyaw, cosineyaw, class28.anInt519 - finesizex, class28.anInt518 - cameraz, class28.anInt520 - finesizey, class28.anInt529);
                    }

                }
                boolean flag1 = false;
                if(node.shading != null)
                {
                    if(!isTileDrawn(x, y, z))
                    {
                        flag1 = true;
                        drawTile(node.shading, x, y, z, sinepitch, cosinepitch, sineyaw, cosineyaw);
                    }
                } else
                if(node.node_texture != null && !isTileDrawn(x, y, z))
                {
                    flag1 = true;
                    method316(x, (byte)99, sinepitch, sineyaw, node.node_texture, cosinepitch, y, cosineyaw);
                }
                int j1 = 0;
                int j2 = 0;
                Wall class10_3 = node.aClass10_1313;
                WallDecoration class26_1 = node.aClass26_1314;
                if(class10_3 != null || class26_1 != null)
                {
                    if(currentx == x)
                        j1++;
                    else
                    if(currentx < x)
                        j1 += 2;
                    if(currenty == y)
                        j1 += 3;
                    else
                    if(currenty > y)
                        j1 += 6;
                    j2 = anIntArray478[j1];
                    node.anInt1328 = anIntArray480[j1];
                }
                if(class10_3 != null)
                {
                    if((class10_3.anInt276 & anIntArray479[j1]) != 0)
                    {
                        if(class10_3.anInt276 == 16)
                        {
                            node.anInt1325 = 3;
                            node.anInt1326 = anIntArray481[j1];
                            node.anInt1327 = 3 - node.anInt1326;
                        } else
                        if(class10_3.anInt276 == 32)
                        {
                            node.anInt1325 = 6;
                            node.anInt1326 = anIntArray482[j1];
                            node.anInt1327 = 6 - node.anInt1326;
                        } else
                        if(class10_3.anInt276 == 64)
                        {
                            node.anInt1325 = 12;
                            node.anInt1326 = anIntArray483[j1];
                            node.anInt1327 = 12 - node.anInt1326;
                        } else
                        {
                            node.anInt1325 = 9;
                            node.anInt1326 = anIntArray484[j1];
                            node.anInt1327 = 9 - node.anInt1326;
                        }
                    } else
                    {
                        node.anInt1325 = 0;
                    }
                    if((class10_3.anInt276 & j2) != 0 && !method321(z, x, y, class10_3.anInt276))
                        class10_3.aActor_278.drawModel(0, sinepitch, cosinepitch, sineyaw, cosineyaw, class10_3.anInt274 - finesizex, class10_3.anInt273 - cameraz, class10_3.anInt275 - finesizey, class10_3.anInt280);
                    if((class10_3.anInt277 & j2) != 0 && !method321(z, x, y, class10_3.anInt277))
                        class10_3.aActor_279.drawModel(0, sinepitch, cosinepitch, sineyaw, cosineyaw, class10_3.anInt274 - finesizex, class10_3.anInt273 - cameraz, class10_3.anInt275 - finesizey, class10_3.anInt280);
                }
                if(class26_1 != null && !method322(z, x, y, class26_1.wd_entity.miny))
                    if((class26_1.anInt502 & j2) != 0)
                        class26_1.wd_entity.drawModel(class26_1.wd_rotation, sinepitch, cosinepitch, sineyaw, cosineyaw, class26_1.wd_xlocation - finesizex, class26_1.anInt499 - cameraz, class26_1.wd_ylocation - finesizey, class26_1.wd_objhash);
                    else
                    if((class26_1.anInt502 & 0x300) != 0)
                    {
                        int j4 = class26_1.wd_xlocation - finesizex;
                        int l5 = class26_1.anInt499 - cameraz;
                        int k6 = class26_1.wd_ylocation - finesizey;
                        int i8 = class26_1.wd_rotation;
                        int k9;
                        if(i8 == 1 || i8 == 2)
                            k9 = -j4;
                        else
                            k9 = j4;
                        int k10;
                        if(i8 == 2 || i8 == 3)
                            k10 = -k6;
                        else
                            k10 = k6;
                        if((class26_1.anInt502 & 0x100) != 0 && k10 < k9)
                        {
                            int i11 = j4 + anIntArray463[i8];
                            int k11 = k6 + anIntArray464[i8];
                            class26_1.wd_entity.drawModel(i8 * 512 + 256, sinepitch, cosinepitch, sineyaw, cosineyaw, i11, l5, k11, class26_1.wd_objhash);
                        }
                        if((class26_1.anInt502 & 0x200) != 0 && k10 > k9)
                        {
                            int j11 = j4 + anIntArray465[i8];
                            int l11 = k6 + anIntArray466[i8];
                            class26_1.wd_entity.drawModel(i8 * 512 + 1280 & 0x7ff, sinepitch, cosinepitch, sineyaw, cosineyaw, j11, l5, l11, class26_1.wd_objhash);
                        }
                    }
                if(flag1)
                {
                    FloorDecoration class49 = node.aClass49_1315;
                    if(class49 != null)
                        class49.aActor_814.drawModel(0, sinepitch, cosinepitch, sineyaw, cosineyaw, class49.anInt812 - finesizex, class49.anInt811 - cameraz, class49.anInt813 - finesizey, class49.anInt815);
                    ItemTile class3_1 = node.aClass3_1316;
                    if(class3_1 != null && class3_1.anInt52 == 0)
                    {
                        if(class3_1.aActor_49 != null)
                            class3_1.aActor_49.drawModel(0, sinepitch, cosinepitch, sineyaw, cosineyaw, class3_1.anInt46 - finesizex, class3_1.anInt45 - cameraz, class3_1.anInt47 - finesizey, class3_1.anInt51);
                        if(class3_1.aActor_50 != null)
                            class3_1.aActor_50.drawModel(0, sinepitch, cosinepitch, sineyaw, cosineyaw, class3_1.anInt46 - finesizex, class3_1.anInt45 - cameraz, class3_1.anInt47 - finesizey, class3_1.anInt51);
                        if(class3_1.aActor_48 != null)
                            class3_1.aActor_48.drawModel(0, sinepitch, cosinepitch, sineyaw, cosineyaw, class3_1.anInt46 - finesizex, class3_1.anInt45 - cameraz, class3_1.anInt47 - finesizey, class3_1.anInt51);
                    }
                }
                int k4 = node.anInt1320;
                if(k4 != 0)
                {
                    if(x < currentx && (k4 & 4) != 0)
                    {
                        PalletNode class30_sub3_17 = aclass30_sub3[x + 1][y];
                        if(class30_sub3_17 != null && class30_sub3_17.aBoolean1323)
                            aClass19_477.addLast(class30_sub3_17);
                    }
                    if(y < currenty && (k4 & 2) != 0)
                    {
                        PalletNode class30_sub3_18 = aclass30_sub3[x][y + 1];
                        if(class30_sub3_18 != null && class30_sub3_18.aBoolean1323)
                            aClass19_477.addLast(class30_sub3_18);
                    }
                    if(x > currentx && (k4 & 1) != 0)
                    {
                        PalletNode class30_sub3_19 = aclass30_sub3[x - 1][y];
                        if(class30_sub3_19 != null && class30_sub3_19.aBoolean1323)
                            aClass19_477.addLast(class30_sub3_19);
                    }
                    if(y > currenty && (k4 & 8) != 0)
                    {
                        PalletNode class30_sub3_20 = aclass30_sub3[x][y - 1];
                        if(class30_sub3_20 != null && class30_sub3_20.aBoolean1323)
                            aClass19_477.addLast(class30_sub3_20);
                    }
                }
            }
            if(node.anInt1325 != 0)
            {
                boolean flag2 = true;
                for(int k1 = 0; k1 < node.anInt1317; k1++)
                {
                    if(node.aClass28Array1318[k1].anInt528 == palettecycle || (node.anIntArray1319[k1] & node.anInt1325) != node.anInt1326)
                        continue;
                    flag2 = false;
                    break;
                }

                if(flag2)
                {
                    Wall class10_1 = node.aClass10_1313;
                    if(!method321(z, x, y, class10_1.anInt276))
                        class10_1.aActor_278.drawModel(0, sinepitch, cosinepitch, sineyaw, cosineyaw, class10_1.anInt274 - finesizex, class10_1.anInt273 - cameraz, class10_1.anInt275 - finesizey, class10_1.anInt280);
                    node.anInt1325 = 0;
                }
            }
            if(node.aBoolean1324)
                try
                {
                    int i1 = node.anInt1317;
                    node.aBoolean1324 = false;
                    int l1 = 0;
label0:
                    for(int k2 = 0; k2 < i1; k2++)
                    {
                        GeneralEntity class28_1 = node.aClass28Array1318[k2];
                        if(class28_1.anInt528 == palettecycle)
                            continue;
                        for(int k3 = class28_1.anInt523; k3 <= class28_1.anInt524; k3++)
                        {
                            for(int l4 = class28_1.anInt525; l4 <= class28_1.anInt526; l4++)
                            {
                                PalletNode class30_sub3_21 = aclass30_sub3[k3][l4];
                                if(class30_sub3_21.aBoolean1322)
                                {
                                    node.aBoolean1324 = true;
                                } else
                                {
                                    if(class30_sub3_21.anInt1325 == 0)
                                        continue;
                                    int l6 = 0;
                                    if(k3 > class28_1.anInt523)
                                        l6++;
                                    if(k3 < class28_1.anInt524)
                                        l6 += 4;
                                    if(l4 > class28_1.anInt525)
                                        l6 += 8;
                                    if(l4 < class28_1.anInt526)
                                        l6 += 2;
                                    if((l6 & class30_sub3_21.anInt1325) != node.anInt1327)
                                        continue;
                                    node.aBoolean1324 = true;
                                }
                                continue label0;
                            }

                        }

                        aClass28Array462[l1++] = class28_1;
                        int i5 = currentx - class28_1.anInt523;
                        int i6 = class28_1.anInt524 - currentx;
                        if(i6 > i5)
                            i5 = i6;
                        int i7 = currenty - class28_1.anInt525;
                        int j8 = class28_1.anInt526 - currenty;
                        if(j8 > i7)
                            class28_1.anInt527 = i5 + j8;
                        else
                            class28_1.anInt527 = i5 + i7;
                    }

                    while(l1 > 0) 
                    {
                        int i3 = -50;
                        int l3 = -1;
                        for(int j5 = 0; j5 < l1; j5++)
                        {
                            GeneralEntity class28_2 = aClass28Array462[j5];
                            if(class28_2.anInt528 != palettecycle)
                                if(class28_2.anInt527 > i3)
                                {
                                    i3 = class28_2.anInt527;
                                    l3 = j5;
                                } else
                                if(class28_2.anInt527 == i3)
                                {
                                    int j7 = class28_2.anInt519 - finesizex;
                                    int k8 = class28_2.anInt520 - finesizey;
                                    int l9 = aClass28Array462[l3].anInt519 - finesizex;
                                    int l10 = aClass28Array462[l3].anInt520 - finesizey;
                                    if(j7 * j7 + k8 * k8 > l9 * l9 + l10 * l10)
                                        l3 = j5;
                                }
                        }

                        if(l3 == -1)
                            break;
                        GeneralEntity class28_3 = aClass28Array462[l3];
                        class28_3.anInt528 = palettecycle;
                        if(!method323(z, class28_3.anInt523, class28_3.anInt524, class28_3.anInt525, class28_3.anInt526, class28_3.aActor_521.miny))
                            class28_3.aActor_521.drawModel(class28_3.anInt522, sinepitch, cosinepitch, sineyaw, cosineyaw, class28_3.anInt519 - finesizex, class28_3.anInt518 - cameraz, class28_3.anInt520 - finesizey, class28_3.anInt529);
                        for(int k7 = class28_3.anInt523; k7 <= class28_3.anInt524; k7++)
                        {
                            for(int l8 = class28_3.anInt525; l8 <= class28_3.anInt526; l8++)
                            {
                                PalletNode class30_sub3_22 = aclass30_sub3[k7][l8];
                                if(class30_sub3_22.anInt1325 != 0)
                                    aClass19_477.addLast(class30_sub3_22);
                                else
                                if((k7 != x || l8 != y) && class30_sub3_22.aBoolean1323)
                                    aClass19_477.addLast(class30_sub3_22);
                            }

                        }

                    }
                    if(node.aBoolean1324)
                        continue;
                }
                catch(Exception _ex)
                {
                    node.aBoolean1324 = false;
                }
            if(!node.aBoolean1323 || node.anInt1325 != 0)
                continue;
            if(x <= currentx && x > minclipx)
            {
                PalletNode class30_sub3_8 = aclass30_sub3[x - 1][y];
                if(class30_sub3_8 != null && class30_sub3_8.aBoolean1323)
                    continue;
            }
            if(x >= currentx && x < maxclipx - 1)
            {
                PalletNode class30_sub3_9 = aclass30_sub3[x + 1][y];
                if(class30_sub3_9 != null && class30_sub3_9.aBoolean1323)
                    continue;
            }
            if(y <= currenty && y > minclipy)
            {
                PalletNode class30_sub3_10 = aclass30_sub3[x][y - 1];
                if(class30_sub3_10 != null && class30_sub3_10.aBoolean1323)
                    continue;
            }
            if(y >= currenty && y < maxclipy - 1)
            {
                PalletNode class30_sub3_11 = aclass30_sub3[x][y + 1];
                if(class30_sub3_11 != null && class30_sub3_11.aBoolean1323)
                    continue;
            }
            node.aBoolean1323 = false;
            anInt446--;
            ItemTile class3 = node.aClass3_1316;
            if(class3 != null && class3.anInt52 != 0)
            {
                if(class3.aActor_49 != null)
                    class3.aActor_49.drawModel(0, sinepitch, cosinepitch, sineyaw, cosineyaw, class3.anInt46 - finesizex, class3.anInt45 - cameraz - class3.anInt52, class3.anInt47 - finesizey, class3.anInt51);
                if(class3.aActor_50 != null)
                    class3.aActor_50.drawModel(0, sinepitch, cosinepitch, sineyaw, cosineyaw, class3.anInt46 - finesizex, class3.anInt45 - cameraz - class3.anInt52, class3.anInt47 - finesizey, class3.anInt51);
                if(class3.aActor_48 != null)
                    class3.aActor_48.drawModel(0, sinepitch, cosinepitch, sineyaw, cosineyaw, class3.anInt46 - finesizex, class3.anInt45 - cameraz - class3.anInt52, class3.anInt47 - finesizey, class3.anInt51);
            }
            if(node.anInt1328 != 0)
            {
                WallDecoration class26 = node.aClass26_1314;
                if(class26 != null && !method322(z, x, y, class26.wd_entity.miny))
                    if((class26.anInt502 & node.anInt1328) != 0)
                        class26.wd_entity.drawModel(class26.wd_rotation, sinepitch, cosinepitch, sineyaw, cosineyaw, class26.wd_xlocation - finesizex, class26.anInt499 - cameraz, class26.wd_ylocation - finesizey, class26.wd_objhash);
                    else
                    if((class26.anInt502 & 0x300) != 0)
                    {
                        int l2 = class26.wd_xlocation - finesizex;
                        int j3 = class26.anInt499 - cameraz;
                        int i4 = class26.wd_ylocation - finesizey;
                        int k5 = class26.wd_rotation;
                        int j6;
                        if(k5 == 1 || k5 == 2)
                            j6 = -l2;
                        else
                            j6 = l2;
                        int l7;
                        if(k5 == 2 || k5 == 3)
                            l7 = -i4;
                        else
                            l7 = i4;
                        if((class26.anInt502 & 0x100) != 0 && l7 >= j6)
                        {
                            int i9 = l2 + anIntArray463[k5];
                            int i10 = i4 + anIntArray464[k5];
                            class26.wd_entity.drawModel(k5 * 512 + 256, sinepitch, cosinepitch, sineyaw, cosineyaw, i9, j3, i10, class26.wd_objhash);
                        }
                        if((class26.anInt502 & 0x200) != 0 && l7 <= j6)
                        {
                            int j9 = l2 + anIntArray465[k5];
                            int j10 = i4 + anIntArray466[k5];
                            class26.wd_entity.drawModel(k5 * 512 + 1280 & 0x7ff, sinepitch, cosinepitch, sineyaw, cosineyaw, j9, j3, j10, class26.wd_objhash);
                        }
                    }
                Wall class10_2 = node.aClass10_1313;
                if(class10_2 != null)
                {
                    if((class10_2.anInt277 & node.anInt1328) != 0 && !method321(z, x, y, class10_2.anInt277))
                        class10_2.aActor_279.drawModel(0, sinepitch, cosinepitch, sineyaw, cosineyaw, class10_2.anInt274 - finesizex, class10_2.anInt273 - cameraz, class10_2.anInt275 - finesizey, class10_2.anInt280);
                    if((class10_2.anInt276 & node.anInt1328) != 0 && !method321(z, x, y, class10_2.anInt276))
                        class10_2.aActor_278.drawModel(0, sinepitch, cosinepitch, sineyaw, cosineyaw, class10_2.anInt274 - finesizex, class10_2.anInt273 - cameraz, class10_2.anInt275 - finesizey, class10_2.anInt280);
                }
            }
            if(k < sizez - 1)
            {
                PalletNode class30_sub3_12 = nodes[k + 1][x][y];
                if(class30_sub3_12 != null && class30_sub3_12.aBoolean1323)
                    aClass19_477.addLast(class30_sub3_12);
            }
            if(x < currentx)
            {
                PalletNode class30_sub3_13 = aclass30_sub3[x + 1][y];
                if(class30_sub3_13 != null && class30_sub3_13.aBoolean1323)
                    aClass19_477.addLast(class30_sub3_13);
            }
            if(y < currenty)
            {
                PalletNode class30_sub3_14 = aclass30_sub3[x][y + 1];
                if(class30_sub3_14 != null && class30_sub3_14.aBoolean1323)
                    aClass19_477.addLast(class30_sub3_14);
            }
            if(x > currentx)
            {
                PalletNode class30_sub3_15 = aclass30_sub3[x - 1][y];
                if(class30_sub3_15 != null && class30_sub3_15.aBoolean1323)
                    aClass19_477.addLast(class30_sub3_15);
            }
            if(y > currenty)
            {
                PalletNode class30_sub3_16 = aclass30_sub3[x][y - 1];
                if(class30_sub3_16 != null && class30_sub3_16.aBoolean1323)
                    aClass19_477.addLast(class30_sub3_16);
            }
        } while(true);
    }

    public void drawTile(TileGraphics tileshading, int x, int y, int z, int sinepitch, int cosinepitch, int sineyaw, int cosineyaw)
    {
        int dx0;
        int dx1 = dx0 = (x << 7) - finesizex;
        int dy0;
        int dy1 = dy0 = (y << 7) - finesizey;
        int dxt0;
        int dxt1 = dxt0 = dx1 + 128;
        int dyt0;
        int dyt1 = dyt0 = dy1 + 128;
        int height0 = heightmap[z][x][y] - cameraz;
        int height1 = heightmap[z][x + 1][y] - cameraz;
        int height2 = heightmap[z][x + 1][y + 1] - cameraz;
        int height3 = heightmap[z][x][y + 1] - cameraz;
        /* Z Axis Rotation */
        int l4 = dy1 * sineyaw + dx1 * cosineyaw >> 16;
        dy1 = dy1 * cosineyaw - dx1 * sineyaw >> 16;
        dx1 = l4;
        /* X Axis Rotation */
        l4 = height0 * cosinepitch - dy1 * sinepitch >> 16;
        dy1 = height0 * sinepitch + dy1 * cosinepitch >> 16;
        height0 = l4;
        if(dy1 < 50)
            return;
        /* Z Axis Rotation */
        l4 = dy0 * sineyaw + dxt1 * cosineyaw >> 16;
        dy0 = dy0 * cosineyaw - dxt1 * sineyaw >> 16;
        dxt1 = l4;
        /* X Axis Rotation */
        l4 = height1 * cosinepitch - dy0 * sinepitch >> 16;
        dy0 = height1 * sinepitch + dy0 * cosinepitch >> 16;
        height1 = l4;
        if(dy0 < 50)
            return;
        /* Z Axis Rotation */
        l4 = dyt1 * sineyaw + dxt0 * cosineyaw >> 16;
        dyt1 = dyt1 * cosineyaw - dxt0 * sineyaw >> 16;
        dxt0 = l4;
        /* X Axis Rotation */
        l4 = height2 * cosinepitch - dyt1 * sinepitch >> 16;
        dyt1 = height2 * sinepitch + dyt1 * cosinepitch >> 16;
        height2 = l4;
        if(dyt1 < 50)
            return;
        /* Z Axis Rotation */
        l4 = dyt0 * sineyaw + dx0 * cosineyaw >> 16;
        dyt0 = dyt0 * cosineyaw - dx0 * sineyaw >> 16;
        dx0 = l4;
        /* X Axis Rotation */
        l4 = height3 * cosinepitch - dyt0 * sinepitch >> 16;
        dyt0 = height3 * sinepitch + dyt0 * cosinepitch >> 16;
        height3 = l4;
        if(dyt0 < 50)
            return;
        int x0 = TriangleRasterizer.midwidth + (dx1 << 9) / dy1;
        int y0 = TriangleRasterizer.midheight + (height0 << 9) / dy1;
		
        int x1 = TriangleRasterizer.midwidth + (dxt1 << 9) / dy0;
        int y1 = TriangleRasterizer.midheight + (height1 << 9) / dy0;
        
        int x2 = TriangleRasterizer.midwidth + (dxt0 << 9) / dyt1;
        int y2 = TriangleRasterizer.midheight + (height2 << 9) / dyt1;
        
        int x3 = TriangleRasterizer.midwidth + (dx0 << 9) / dyt0;
        int y3 = TriangleRasterizer.midheight + (height3 << 9) / dyt0;
        TriangleRasterizer.alpha$ = 0;
        if((x2 - x3) * (y1 - y3) - (y2 - y3) * (x1 - x3) > 0)
        {
            TriangleRasterizer.clip = false;
            if(x2 < 0 || x3 < 0 || x1 < 0 || x2 > BasicRasterizer.widthLength || x3 > BasicRasterizer.widthLength || x1 > BasicRasterizer.widthLength)
                TriangleRasterizer.clip = true;
            if(aBoolean467 && method318(anInt468, anInt469, y2, y3, y1, x2, x3, x1))
            {
                anInt470 = x;
                anInt471 = y;
            }
            if(tileshading.textureid == -1)
            {
                if(tileshading.colorpoint1 != 0xbc614e)
                    TriangleRasterizer.drawShadedTriangle(x3, x2, x1, y2, y3, y1, tileshading.colorpoint0, tileshading.colorpoint1, tileshading.colorpoint2);
            } else
            if(!lowmemory)
            {
                if(tileshading.aBoolean721)
                    TriangleRasterizer.drawTexturedTriangle(y2, y3, y1, x2, x3, x1, tileshading.colorpoint1, tileshading.colorpoint0, tileshading.colorpoint2, dx1, dxt1, dx0, height0, height1, height3, dy1, dy0, dyt0, tileshading.textureid);
                else
                    TriangleRasterizer.drawTexturedTriangle(y2, y3, y1, x2, x3, x1, tileshading.colorpoint1, tileshading.colorpoint0, tileshading.colorpoint2, dxt0, dx0, dxt1, height2, height3, height1, dyt1, dyt0, dy0, tileshading.textureid);
            } else
            {
                int i7 = textureshades[tileshading.textureid];
                TriangleRasterizer.drawShadedTriangle(x3, x2, x1, y2, y3, y1, method317(i7, tileshading.colorpoint0), method317(i7, tileshading.colorpoint1), method317(i7, tileshading.colorpoint2));
            }
        }
        if((x0 - x1) * (y3 - y1) - (y0 - y1) * (x3 - x1) > 0)
        {
            TriangleRasterizer.clip = false;
            if(x0 < 0 || x1 < 0 || x3 < 0 || x0 > BasicRasterizer.widthLength || x1 > BasicRasterizer.widthLength || x3 > BasicRasterizer.widthLength)
                TriangleRasterizer.clip = true;
            if(aBoolean467 && method318(anInt468, anInt469, y0, y1, y3, x0, x1, x3))
            {
                anInt470 = x;
                anInt471 = y;
            }
            if(tileshading.textureid == -1)
            {
                if(tileshading.colorpoint1$ != 0xbc614e)
                {
                    TriangleRasterizer.drawShadedTriangle(x1, x0, x3, y0, y1, y3, tileshading.colorpoint2, tileshading.colorpoint1$, tileshading.colorpoint0);
                    return;
                }
            } else
            {
                if(!lowmemory)
                {
                    TriangleRasterizer.drawTexturedTriangle(y0, y1, y3, x0, x1, x3, tileshading.colorpoint1$, tileshading.colorpoint2, tileshading.colorpoint0, dx1, dxt1, dx0, height0, height1, height3, dy1, dy0, dyt0, tileshading.textureid);
                    return;
                }
                int j7 = textureshades[tileshading.textureid];
                TriangleRasterizer.drawShadedTriangle(x1, x0, x3, y0, y1, y3, method317(j7, tileshading.colorpoint2), method317(j7, tileshading.colorpoint1$), method317(j7, tileshading.colorpoint0));
            }
        }
    }

    public void method316(int i, byte byte0, int j, int k, PalleteNodeTexture class40, int l, int i1, 
            int j1)
    {
        int k1 = class40.anIntArray673.length;
        if(byte0 != 99)
            return;
        for(int l1 = 0; l1 < k1; l1++)
        {
            int i2 = class40.anIntArray673[l1] - finesizex;
            int k2 = class40.anIntArray674[l1] - cameraz;
            int i3 = class40.anIntArray675[l1] - finesizey;
            int k3 = i3 * k + i2 * j1 >> 16;
            i3 = i3 * j1 - i2 * k >> 16;
            i2 = k3;
            k3 = k2 * l - i3 * j >> 16;
            i3 = k2 * j + i3 * l >> 16;
            k2 = k3;
            if(i3 < 50)
                return;
            if(class40.textureids != null)
            {
                PalleteNodeTexture.anIntArray690[l1] = i2;
                PalleteNodeTexture.anIntArray691[l1] = k2;
                PalleteNodeTexture.anIntArray692[l1] = i3;
            }
            PalleteNodeTexture.anIntArray688[l1] = TriangleRasterizer.midwidth + (i2 << 9) / i3;
            PalleteNodeTexture.anIntArray689[l1] = TriangleRasterizer.midheight + (k2 << 9) / i3;
        }

        TriangleRasterizer.alpha$ = 0;
        k1 = class40.anIntArray679.length;
        for(int m = 0; m < k1; m++)
        {
            int p0 = class40.anIntArray679[m];
            int p1 = class40.anIntArray680[m];
            int p2 = class40.anIntArray681[m];
            int x0 = PalleteNodeTexture.anIntArray688[p0];
            int x1 = PalleteNodeTexture.anIntArray688[p1];
            int x2 = PalleteNodeTexture.anIntArray688[p2];
            int y0 = PalleteNodeTexture.anIntArray689[p0];
            int y1 = PalleteNodeTexture.anIntArray689[p1];
            int y2 = PalleteNodeTexture.anIntArray689[p2];
            if((x0 - x1) * (y2 - y1) - (y0 - y1) * (x2 - x1) > 0)
            {
                TriangleRasterizer.clip = false;
                if(x0 < 0 || x1 < 0 || x2 < 0 || x0 > BasicRasterizer.widthLength || x1 > BasicRasterizer.widthLength || x2 > BasicRasterizer.widthLength)
                    TriangleRasterizer.clip = true;
                if(aBoolean467 && method318(anInt468, anInt469, y0, y1, y2, x0, x1, x2))
                {
                    anInt470 = i;
                    anInt471 = i1;
                }
                if(class40.textureids == null || class40.textureids[m] == -1)
                {
                    if(class40.anIntArray676[m] != 0xbc614e)
                        TriangleRasterizer.drawShadedTriangle(x1, x0, x2, y0, y1, y2, class40.anIntArray677[m], class40.anIntArray676[m], class40.anIntArray678[m]);
                } else
                if(!lowmemory)
                {
                    if(class40.aBoolean683)
                        TriangleRasterizer.drawTexturedTriangle(y0, y1, y2, x0, x1, x2, class40.anIntArray676[m], class40.anIntArray677[m], class40.anIntArray678[m], PalleteNodeTexture.anIntArray690[0], PalleteNodeTexture.anIntArray690[1], PalleteNodeTexture.anIntArray690[3], PalleteNodeTexture.anIntArray691[0], PalleteNodeTexture.anIntArray691[1], PalleteNodeTexture.anIntArray691[3], PalleteNodeTexture.anIntArray692[0], PalleteNodeTexture.anIntArray692[1], PalleteNodeTexture.anIntArray692[3], class40.textureids[m]);
                    else
                        TriangleRasterizer.drawTexturedTriangle(y0, y1, y2, x0, x1, x2, class40.anIntArray676[m], class40.anIntArray677[m], class40.anIntArray678[m], PalleteNodeTexture.anIntArray690[p0], PalleteNodeTexture.anIntArray690[p1], PalleteNodeTexture.anIntArray690[p2], PalleteNodeTexture.anIntArray691[p0], PalleteNodeTexture.anIntArray691[p1], PalleteNodeTexture.anIntArray691[p2], PalleteNodeTexture.anIntArray692[p0], PalleteNodeTexture.anIntArray692[p1], PalleteNodeTexture.anIntArray692[p2], class40.textureids[m]);
                } else
                {
                    int k5 = textureshades[class40.textureids[m]];
                    TriangleRasterizer.drawShadedTriangle(x1, x0, x2, y0, y1, y2, method317(k5, class40.anIntArray677[m]), method317(k5, class40.anIntArray676[m]), method317(k5, class40.anIntArray678[m]));
                }
            }
        }

    }

    public int method317(int j, int k)
    {
        k = 127 - k;
        k = (k * (j & 0x7f)) / 160;
        if(k < 2)
            k = 2;
        else if(k > 126)
            k = 126;
        return (j & 0xff80) + k;
    }

    public boolean method318(int i, int j, int k, int l, int i1, int j1, int k1, 
            int l1)
    {
        if(j < k && j < l && j < i1)
            return false;
        if(j > k && j > l && j > i1)
            return false;
        if(i < j1 && i < k1 && i < l1)
            return false;
        if(i > j1 && i > k1 && i > l1)
            return false;
        int i2 = (j - k) * (k1 - j1) - (i - j1) * (l - k);
        int j2 = (j - i1) * (j1 - l1) - (i - l1) * (k - i1);
        int k2 = (j - l) * (l1 - k1) - (i - k1) * (i1 - l);
        return i2 * k2 > 0 && k2 * j2 > 0;
    }

    public void method319(int i)
    {
        if(i != 0)
            return;
        int j = anIntArray473[currentz$];
        PalleteNode$ aclass47[] = aClass47ArrayArray474[currentz$];
        anInt475 = 0;
        for(int k = 0; k < j; k++)
        {
            PalleteNode$ class47 = aclass47[k];
            if(class47.anInt791 == 1)
            {
                int l = (class47.anInt787 - currentx) + 25;
                if(l < 0 || l > 50)
                    continue;
                int k1 = (class47.anInt789 - currenty) + 25;
                if(k1 < 0)
                    k1 = 0;
                int j2 = (class47.anInt790 - currenty) + 25;
                if(j2 > 50)
                    j2 = 50;
                boolean flag = false;
                while(k1 <= j2) 
                    if(aBooleanArrayArray492[l][k1++])
                    {
                        flag = true;
                        break;
                    }
                if(!flag)
                    continue;
                int j3 = finesizex - class47.anInt792;
                if(j3 > 32)
                {
                    class47.anInt798 = 1;
                } else
                {
                    if(j3 >= -32)
                        continue;
                    class47.anInt798 = 2;
                    j3 = -j3;
                }
                class47.anInt801 = (class47.anInt794 - finesizey << 8) / j3;
                class47.anInt802 = (class47.anInt795 - finesizey << 8) / j3;
                class47.anInt803 = (class47.anInt796 - cameraz << 8) / j3;
                class47.anInt804 = (class47.anInt797 - cameraz << 8) / j3;
                aClass47Array476[anInt475++] = class47;
                continue;
            }
            if(class47.anInt791 == 2)
            {
                int i1 = (class47.anInt789 - currenty) + 25;
                if(i1 < 0 || i1 > 50)
                    continue;
                int l1 = (class47.anInt787 - currentx) + 25;
                if(l1 < 0)
                    l1 = 0;
                int k2 = (class47.anInt788 - currentx) + 25;
                if(k2 > 50)
                    k2 = 50;
                boolean flag1 = false;
                while(l1 <= k2) 
                    if(aBooleanArrayArray492[l1++][i1])
                    {
                        flag1 = true;
                        break;
                    }
                if(!flag1)
                    continue;
                int k3 = finesizey - class47.anInt794;
                if(k3 > 32)
                {
                    class47.anInt798 = 3;
                } else
                {
                    if(k3 >= -32)
                        continue;
                    class47.anInt798 = 4;
                    k3 = -k3;
                }
                class47.anInt799 = (class47.anInt792 - finesizex << 8) / k3;
                class47.anInt800 = (class47.anInt793 - finesizex << 8) / k3;
                class47.anInt803 = (class47.anInt796 - cameraz << 8) / k3;
                class47.anInt804 = (class47.anInt797 - cameraz << 8) / k3;
                aClass47Array476[anInt475++] = class47;
            } else
            if(class47.anInt791 == 4)
            {
                int j1 = class47.anInt796 - cameraz;
                if(j1 > 128)
                {
                    int i2 = (class47.anInt789 - currenty) + 25;
                    if(i2 < 0)
                        i2 = 0;
                    int l2 = (class47.anInt790 - currenty) + 25;
                    if(l2 > 50)
                        l2 = 50;
                    if(i2 <= l2)
                    {
                        int i3 = (class47.anInt787 - currentx) + 25;
                        if(i3 < 0)
                            i3 = 0;
                        int l3 = (class47.anInt788 - currentx) + 25;
                        if(l3 > 50)
                            l3 = 50;
                        boolean flag2 = false;
label0:
                        for(int i4 = i3; i4 <= l3; i4++)
                        {
                            for(int j4 = i2; j4 <= l2; j4++)
                            {
                                if(!aBooleanArrayArray492[i4][j4])
                                    continue;
                                flag2 = true;
                                break label0;
                            }

                        }

                        if(flag2)
                        {
                            class47.anInt798 = 5;
                            class47.anInt799 = (class47.anInt792 - finesizex << 8) / j1;
                            class47.anInt800 = (class47.anInt793 - finesizex << 8) / j1;
                            class47.anInt801 = (class47.anInt794 - finesizey << 8) / j1;
                            class47.anInt802 = (class47.anInt795 - finesizey << 8) / j1;
                            aClass47Array476[anInt475++] = class47;
                        }
                    }
                }
            }
        }

    }

    public boolean isTileDrawn(int x, int y, int z)
    {
        int l = tileupdatestamps[z][x][y];
        if(l == -palettecycle)
            return false;
        if(l == palettecycle)
            return true;
        int finex = x << 7;
        int finey = y << 7;
        if(method324(finex + 1, heightmap[z][x][y], finey + 1) && method324((finex + 128) - 1, heightmap[z][x + 1][y], finey + 1) && method324((finex + 128) - 1, heightmap[z][x + 1][y + 1], (finey + 128) - 1) && method324(finex + 1, heightmap[z][x][y + 1], (finey + 128) - 1))
        {
            tileupdatestamps[z][x][y] = palettecycle;
            return true;
        } else
        {
            tileupdatestamps[z][x][y] = -palettecycle;
            return false;
        }
    }

    public boolean method321(int z, int x, int y, int l)
    {
        if(!isTileDrawn(x, y, z))
            return false;
        int finex = x << 7;
        int finey = y << 7;
        int k1 = heightmap[z][x][y] - 1;
        int l1 = k1 - 120;
        int i2 = k1 - 230;
        int j2 = k1 - 238;
        if(l < 16)
        {
            if(l == 1)
            {
                if(finex > finesizex)
                {
                    if(!method324(finex, k1, finey))
                        return false;
                    if(!method324(finex, k1, finey + 128))
                        return false;
                }
                if(z > 0)
                {
                    if(!method324(finex, l1, finey))
                        return false;
                    if(!method324(finex, l1, finey + 128))
                        return false;
                }
                if(!method324(finex, i2, finey))
                    return false;
                return method324(finex, i2, finey + 128);
            }
            if(l == 2)
            {
                if(finey < finesizey)
                {
                    if(!method324(finex, k1, finey + 128))
                        return false;
                    if(!method324(finex + 128, k1, finey + 128))
                        return false;
                }
                if(z > 0)
                {
                    if(!method324(finex, l1, finey + 128))
                        return false;
                    if(!method324(finex + 128, l1, finey + 128))
                        return false;
                }
                if(!method324(finex, i2, finey + 128))
                    return false;
                return method324(finex + 128, i2, finey + 128);
            }
            if(l == 4)
            {
                if(finex < finesizex)
                {
                    if(!method324(finex + 128, k1, finey))
                        return false;
                    if(!method324(finex + 128, k1, finey + 128))
                        return false;
                }
                if(z > 0)
                {
                    if(!method324(finex + 128, l1, finey))
                        return false;
                    if(!method324(finex + 128, l1, finey + 128))
                        return false;
                }
                if(!method324(finex + 128, i2, finey))
                    return false;
                return method324(finex + 128, i2, finey + 128);
            }
            if(l == 8)
            {
                if(finey > finesizey)
                {
                    if(!method324(finex, k1, finey))
                        return false;
                    if(!method324(finex + 128, k1, finey))
                        return false;
                }
                if(z > 0)
                {
                    if(!method324(finex, l1, finey))
                        return false;
                    if(!method324(finex + 128, l1, finey))
                        return false;
                }
                if(!method324(finex, i2, finey))
                    return false;
                return method324(finex + 128, i2, finey);
            }
        }
        if(!method324(finex + 64, j2, finey + 64))
            return false;
        if(l == 16)
            return method324(finex, i2, finey + 128);
        if(l == 32)
            return method324(finex + 128, i2, finey + 128);
        if(l == 64)
            return method324(finex + 128, i2, finey);
        if(l == 128)
        {
            return method324(finex, i2, finey);
        } else
        {
            System.out.println("Warning unsupported wall type");
            return true;
        }
    }

    public boolean method322(int i, int j, int k, int l)
    {
        if(!isTileDrawn(j, k, i))
            return false;
        int i1 = j << 7;
        int j1 = k << 7;
        return method324(i1 + 1, heightmap[i][j][k] - l, j1 + 1) && method324((i1 + 128) - 1, heightmap[i][j + 1][k] - l, j1 + 1) && method324((i1 + 128) - 1, heightmap[i][j + 1][k + 1] - l, (j1 + 128) - 1) && method324(i1 + 1, heightmap[i][j][k + 1] - l, (j1 + 128) - 1);
    }

    public boolean method323(int i, int j, int k, int l, int i1, int j1)
    {
        if(j == k && l == i1)
        {
            if(!isTileDrawn(j, l, i))
                return false;
            int k1 = j << 7;
            int i2 = l << 7;
            return method324(k1 + 1, heightmap[i][j][l] - j1, i2 + 1) && method324((k1 + 128) - 1, heightmap[i][j + 1][l] - j1, i2 + 1) && method324((k1 + 128) - 1, heightmap[i][j + 1][l + 1] - j1, (i2 + 128) - 1) && method324(k1 + 1, heightmap[i][j][l + 1] - j1, (i2 + 128) - 1);
        }
        for(int l1 = j; l1 <= k; l1++)
        {
            for(int j2 = l; j2 <= i1; j2++)
                if(tileupdatestamps[i][l1][j2] == -palettecycle)
                    return false;

        }

        int k2 = (j << 7) + 1;
        int l2 = (l << 7) + 2;
        int i3 = heightmap[i][j][l] - j1;
        if(!method324(k2, i3, l2))
            return false;
        int j3 = (k << 7) - 1;
        if(!method324(j3, i3, l2))
            return false;
        int k3 = (i1 << 7) - 1;
        if(!method324(k2, i3, k3))
            return false;
        return method324(j3, i3, k3);
    }

    public boolean method324(int i, int j, int k)
    {
        for(int l = 0; l < anInt475; l++)
        {
            PalleteNode$ class47 = aClass47Array476[l];
            if(class47.anInt798 == 1)
            {
                int i1 = class47.anInt792 - i;
                if(i1 > 0)
                {
                    int j2 = class47.anInt794 + (class47.anInt801 * i1 >> 8);
                    int k3 = class47.anInt795 + (class47.anInt802 * i1 >> 8);
                    int l4 = class47.anInt796 + (class47.anInt803 * i1 >> 8);
                    int i6 = class47.anInt797 + (class47.anInt804 * i1 >> 8);
                    if(k >= j2 && k <= k3 && j >= l4 && j <= i6)
                        return true;
                }
            } else
            if(class47.anInt798 == 2)
            {
                int j1 = i - class47.anInt792;
                if(j1 > 0)
                {
                    int k2 = class47.anInt794 + (class47.anInt801 * j1 >> 8);
                    int l3 = class47.anInt795 + (class47.anInt802 * j1 >> 8);
                    int i5 = class47.anInt796 + (class47.anInt803 * j1 >> 8);
                    int j6 = class47.anInt797 + (class47.anInt804 * j1 >> 8);
                    if(k >= k2 && k <= l3 && j >= i5 && j <= j6)
                        return true;
                }
            } else
            if(class47.anInt798 == 3)
            {
                int k1 = class47.anInt794 - k;
                if(k1 > 0)
                {
                    int l2 = class47.anInt792 + (class47.anInt799 * k1 >> 8);
                    int i4 = class47.anInt793 + (class47.anInt800 * k1 >> 8);
                    int j5 = class47.anInt796 + (class47.anInt803 * k1 >> 8);
                    int k6 = class47.anInt797 + (class47.anInt804 * k1 >> 8);
                    if(i >= l2 && i <= i4 && j >= j5 && j <= k6)
                        return true;
                }
            } else
            if(class47.anInt798 == 4)
            {
                int l1 = k - class47.anInt794;
                if(l1 > 0)
                {
                    int i3 = class47.anInt792 + (class47.anInt799 * l1 >> 8);
                    int j4 = class47.anInt793 + (class47.anInt800 * l1 >> 8);
                    int k5 = class47.anInt796 + (class47.anInt803 * l1 >> 8);
                    int l6 = class47.anInt797 + (class47.anInt804 * l1 >> 8);
                    if(i >= i3 && i <= j4 && j >= k5 && j <= l6)
                        return true;
                }
            } else
            if(class47.anInt798 == 5)
            {
                int i2 = j - class47.anInt796;
                if(i2 > 0)
                {
                    int j3 = class47.anInt792 + (class47.anInt799 * i2 >> 8);
                    int k4 = class47.anInt793 + (class47.anInt800 * i2 >> 8);
                    int l5 = class47.anInt794 + (class47.anInt801 * i2 >> 8);
                    int i7 = class47.anInt795 + (class47.anInt802 * i2 >> 8);
                    if(i >= j3 && i <= k4 && k >= l5 && k <= i7)
                        return true;
                }
            }
        }

        return false;
    }

    public boolean aBoolean429;
    public int anInt430;
    public static int anInt431 = 360;
    public static int anInt432 = 1;
    public static int anInt433 = -460;
    public boolean aBoolean434;
    public boolean aBoolean435;
    public static boolean lowmemory = true;
    public int sizez;
    public int sizex;
    public int sizey;
    public int heightmap[][][];
    public PalletNode nodes[][][];
    public int currentz;
    public int anInt443;
    public GeneralEntity aClass28Array444[];
    public int tileupdatestamps[][][];
    public static int anInt446;
    public static int currentz$;
    public static int palettecycle;
    public static int minclipx;
    public static int maxclipx;
    public static int minclipy;
    public static int maxclipy;
    public static int currentx;
    public static int currenty;
    public static int finesizex;
    public static int cameraz;
    public static int finesizey;
    public static int sinepitch;
    public static int cosinepitch;
    public static int sineyaw;
    public static int cosineyaw;
    public static GeneralEntity aClass28Array462[] = new GeneralEntity[100];
    public static final int anIntArray463[] = {
        53, -53, -53, 53
    };
    public static final int anIntArray464[] = {
        -53, -53, 53, 53
    };
    public static final int anIntArray465[] = {
        -45, 45, 45, -45
    };
    public static final int anIntArray466[] = {
        45, 45, -45, -45
    };
    public static boolean aBoolean467;
    public static int anInt468;
    public static int anInt469;
    public static int anInt470 = -1;
    public static int anInt471 = -1;
    public static int anInt472;
    public static int anIntArray473[];
    public static PalleteNode$ aClass47ArrayArray474[][];
    public static int anInt475;
    public static PalleteNode$ aClass47Array476[] = new PalleteNode$[500];
    public static Deque aClass19_477 = new Deque();
    public static final int anIntArray478[] = {
        19, 55, 38, 155, 255, 110, 137, 205, 76
    };
    public static final int anIntArray479[] = {
        160, 192, 80, 96, 0, 144, 80, 48, 160
    };
    public static final int anIntArray480[] = {
        76, 8, 137, 4, 0, 1, 38, 2, 19
    };
    public static final int anIntArray481[] = {
        0, 0, 2, 0, 0, 2, 1, 1, 0
    };
    public static final int anIntArray482[] = {
        2, 0, 0, 2, 0, 0, 0, 4, 4
    };
    public static final int anIntArray483[] = {
        0, 4, 4, 8, 0, 0, 8, 0, 0
    };
    public static final int anIntArray484[] = {
        1, 1, 0, 0, 0, 8, 0, 0, 8
    };

    //10 * 5
    public static final int textureshades[] = {
        41, 39248, 41, 4643, 41, 41, 41, 41, 41, 41, 
        41, 41, 41, 41, 41, 43086, 41, 41, 41, 41, 
        41, 41, 41, 8602, 41, 28992, 41, 41, 41, 41, 
        41, 5056, 41, 41, 41, 7079, 41, 41, 41, 41, 
        41, 41, 41, 41, 41, 41, 3131, 41, 41, 41
    };
    public int anIntArray486[];
    public int anIntArray487[];
    public int anInt488;
    public int anIntArrayArray489[][] = {
        new int[16], {
            1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 
            1, 1, 1, 1, 1, 1
        }, {
            1, 0, 0, 0, 1, 1, 0, 0, 1, 1, 
            1, 0, 1, 1, 1, 1
        }, {
            1, 1, 0, 0, 1, 1, 0, 0, 1, 0, 
            0, 0, 1, 0, 0, 0
        }, {
            0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 
            0, 1, 0, 0, 0, 1
        }, {
            0, 1, 1, 1, 0, 1, 1, 1, 1, 1, 
            1, 1, 1, 1, 1, 1
        }, {
            1, 1, 1, 0, 1, 1, 1, 0, 1, 1, 
            1, 1, 1, 1, 1, 1
        }, {
            1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 
            0, 0, 1, 1, 0, 0
        }, {
            0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 
            0, 0, 1, 1, 0, 0
        }, {
            1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 
            1, 1, 0, 0, 1, 1
        }, 
        {
            1, 1, 1, 1, 1, 1, 0, 0, 1, 0, 
            0, 0, 1, 0, 0, 0
        }, {
            0, 0, 0, 0, 0, 0, 1, 1, 0, 1, 
            1, 1, 0, 1, 1, 1
        }, {
            0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 
            1, 0, 1, 1, 1, 1
        }
    };
    public int anIntArrayArray490[][] = {
        {
            0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 
            10, 11, 12, 13, 14, 15
        }, {
            12, 8, 4, 0, 13, 9, 5, 1, 14, 10, 
            6, 2, 15, 11, 7, 3
        }, {
            15, 14, 13, 12, 11, 10, 9, 8, 7, 6, 
            5, 4, 3, 2, 1, 0
        }, {
            3, 7, 11, 15, 2, 6, 10, 14, 1, 5, 
            9, 13, 0, 4, 8, 12
        }
    };
    public static boolean aBooleanArrayArrayArrayArray491[][][][] = new boolean[8][32][51][51];
    public static boolean aBooleanArrayArray492[][];
    public static int anInt493;
    public static int anInt494;
    public static int anInt495;
    public static int anInt496;
    public static int anInt497;
    public static int anInt498;

    static 
    {
        anInt472 = 4;
        anIntArray473 = new int[anInt472];
        aClass47ArrayArray474 = new PalleteNode$[anInt472][500];
    }
}
