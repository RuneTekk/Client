// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

public final class CollisionMap {

    public CollisionMap(int sizex, int sizey) {
        offsetx = 0;
        offsety = 0;
        this.sizex = sizex;
        this.sizey = sizey;
        index = new int[this.sizex][this.sizey];
        initialize();
    }

    public void initialize() {
        for(int x = 0; x < sizex; x++) {
            for(int y = 0; y < sizey; y++)
                if(x == 0 || y == 0 || x == sizex - 1 || y == sizey - 1)
                    index[x][y] = 0xffffff;
                else
                    index[x][y] = 0x1000000;
        }
    }

    public void method211(int y, int rotation, int x, int type, boolean flag)
    {
        x -= offsetx;
        y -= offsety;
        if(type == 0)
        {
            if(rotation == 0)
            {
                maskOR(x, y, 128);
                maskOR(x - 1, y, 8);
            }
            if(rotation == 1)
            {
                maskOR(x, y, 2);
                maskOR(x, y + 1, 32);
            }
            if(rotation == 2)
            {
                maskOR(x, y, 8);
                maskOR(x + 1, y, 128);
            }
            if(rotation == 3)
            {
                maskOR(x, y, 32);
                maskOR(x, y - 1, 2);
            }
        }
        if(type == 1 || type == 3)
        {
            if(rotation == 0)
            {
                maskOR(x, y, 1);
                maskOR(x - 1, y + 1, 16);
            }
            if(rotation == 1)
            {
                maskOR(x, y, 4);
                maskOR(x + 1, y + 1, 64);
            }
            if(rotation == 2)
            {
                maskOR(x, y, 16);
                maskOR(x + 1, y - 1, 1);
            }
            if(rotation == 3)
            {
                maskOR(x, y, 64);
                maskOR(x - 1, y - 1, 4);
            }
        }
        if(type == 2)
        {
            if(rotation == 0)
            {
                maskOR(x, y, 130);
                maskOR(x - 1, y, 8);
                maskOR(x, y + 1, 32);
            }
            if(rotation == 1)
            {
                maskOR(x, y, 10);
                maskOR(x, y + 1, 32);
                maskOR(x + 1, y, 128);
            }
            if(rotation == 2)
            {
                maskOR(x, y, 40);
                maskOR(x + 1, y, 128);
                maskOR(x, y - 1, 2);
            }
            if(rotation == 3)
            {
                maskOR(x, y, 160);
                maskOR(x, y - 1, 2);
                maskOR(x - 1, y, 8);
            }
        }
        if(flag)
        {
            if(type == 0)
            {
                if(rotation == 0)
                {
                    maskOR(x, y, 0x10000);
                    maskOR(x - 1, y, 4096);
                }
                if(rotation == 1)
                {
                    maskOR(x, y, 1024);
                    maskOR(x, y + 1, 16384);
                }
                if(rotation == 2)
                {
                    maskOR(x, y, 4096);
                    maskOR(x + 1, y, 0x10000);
                }
                if(rotation == 3)
                {
                    maskOR(x, y, 16384);
                    maskOR(x, y - 1, 1024);
                }
            }
            if(type == 1 || type == 3)
            {
                if(rotation == 0)
                {
                    maskOR(x, y, 512);
                    maskOR(x - 1, y + 1, 8192);
                }
                if(rotation == 1)
                {
                    maskOR(x, y, 2048);
                    maskOR(x + 1, y + 1, 32768);
                }
                if(rotation == 2)
                {
                    maskOR(x, y, 8192);
                    maskOR(x + 1, y - 1, 512);
                }
                if(rotation == 3)
                {
                    maskOR(x, y, 32768);
                    maskOR(x - 1, y - 1, 2048);
                }
            }
            if(type == 2)
            {
                if(rotation == 0)
                {
                    maskOR(x, y, 0x10400);
                    maskOR(x - 1, y, 4096);
                    maskOR(x, y + 1, 16384);
                }
                if(rotation == 1)
                {
                    maskOR(x, y, 5120);
                    maskOR(x, y + 1, 16384);
                    maskOR(x + 1, y, 0x10000);
                }
                if(rotation == 2)
                {
                    maskOR(x, y, 20480);
                    maskOR(x + 1, y, 0x10000);
                    maskOR(x, y - 1, 1024);
                }
                if(rotation == 3)
                {
                    maskOR(x, y, 0x14000);
                    maskOR(x, y - 1, 1024);
                    maskOR(x - 1, y, 4096);
                }
            }
        }
    }

    public void method212(boolean flag, int junk, int j, int k, int l, int i1, int rotation)
    {
        int k1 = 256;
        if(flag)
            k1 |= 0x20000;
        l -= offsetx;
        i1 -= offsety;
        if(rotation == 1 || rotation == 3)
        {
            int l1 = j;
            j = k;
            k = l1;
        }
        for(int i2 = l; i2 < l + j; i2++)
            if(i2 >= 0 && i2 < sizex) {
                for(int j2 = i1; j2 < i1 + k; j2++)
                    if(j2 >= 0 && j2 < sizey)
                        maskOR(i2, j2, k1);
            }
    }

    public void method213(int i, int k)
    {
        k -= offsetx;
        i -= offsety;
        /* 22nd bit */
        index[k][i] |= 0x200000;
    }

    public void maskOR(int x, int y, int mask) {
        index[x][y] |= mask;
    }

    public void method215(int i, int j, boolean flag, boolean flag1, int k, int l)
    {
        k -= offsetx;
        l -= offsety;
        if(!flag1)
            return;
        if(j == 0)
        {
            if(i == 0)
            {
                maskAND(128, k, l, 933);
                maskAND(8, k - 1, l, 933);
            }
            if(i == 1)
            {
                maskAND(2, k, l, 933);
                maskAND(32, k, l + 1, 933);
            }
            if(i == 2)
            {
                maskAND(8, k, l, 933);
                maskAND(128, k + 1, l, 933);
            }
            if(i == 3)
            {
                maskAND(32, k, l, 933);
                maskAND(2, k, l - 1, 933);
            }
        }
        if(j == 1 || j == 3)
        {
            if(i == 0)
            {
                maskAND(1, k, l, 933);
                maskAND(16, k - 1, l + 1, 933);
            }
            if(i == 1)
            {
                maskAND(4, k, l, 933);
                maskAND(64, k + 1, l + 1, 933);
            }
            if(i == 2)
            {
                maskAND(16, k, l, 933);
                maskAND(1, k + 1, l - 1, 933);
            }
            if(i == 3)
            {
                maskAND(64, k, l, 933);
                maskAND(4, k - 1, l - 1, 933);
            }
        }
        if(j == 2)
        {
            if(i == 0)
            {
                maskAND(130, k, l, 933);
                maskAND(8, k - 1, l, 933);
                maskAND(32, k, l + 1, 933);
            }
            if(i == 1)
            {
                maskAND(10, k, l, 933);
                maskAND(32, k, l + 1, 933);
                maskAND(128, k + 1, l, 933);
            }
            if(i == 2)
            {
                maskAND(40, k, l, 933);
                maskAND(128, k + 1, l, 933);
                maskAND(2, k, l - 1, 933);
            }
            if(i == 3)
            {
                maskAND(160, k, l, 933);
                maskAND(2, k, l - 1, 933);
                maskAND(8, k - 1, l, 933);
            }
        }
        if(flag)
        {
            if(j == 0)
            {
                if(i == 0)
                {
                    maskAND(0x10000, k, l, 933);
                    maskAND(4096, k - 1, l, 933);
                }
                if(i == 1)
                {
                    maskAND(1024, k, l, 933);
                    maskAND(16384, k, l + 1, 933);
                }
                if(i == 2)
                {
                    maskAND(4096, k, l, 933);
                    maskAND(0x10000, k + 1, l, 933);
                }
                if(i == 3)
                {
                    maskAND(16384, k, l, 933);
                    maskAND(1024, k, l - 1, 933);
                }
            }
            if(j == 1 || j == 3)
            {
                if(i == 0)
                {
                    maskAND(512, k, l, 933);
                    maskAND(8192, k - 1, l + 1, 933);
                }
                if(i == 1)
                {
                    maskAND(2048, k, l, 933);
                    maskAND(32768, k + 1, l + 1, 933);
                }
                if(i == 2)
                {
                    maskAND(8192, k, l, 933);
                    maskAND(512, k + 1, l - 1, 933);
                }
                if(i == 3)
                {
                    maskAND(32768, k, l, 933);
                    maskAND(2048, k - 1, l - 1, 933);
                }
            }
            if(j == 2)
            {
                if(i == 0)
                {
                    maskAND(0x10400, k, l, 933);
                    maskAND(4096, k - 1, l, 933);
                    maskAND(16384, k, l + 1, 933);
                }
                if(i == 1)
                {
                    maskAND(5120, k, l, 933);
                    maskAND(16384, k, l + 1, 933);
                    maskAND(0x10000, k + 1, l, 933);
                }
                if(i == 2)
                {
                    maskAND(20480, k, l, 933);
                    maskAND(0x10000, k + 1, l, 933);
                    maskAND(1024, k, l - 1, 933);
                }
                if(i == 3)
                {
                    maskAND(0x14000, k, l, 933);
                    maskAND(1024, k, l - 1, 933);
                    maskAND(4096, k - 1, l, 933);
                }
            }
        }
    }

    public void method216(int i, int j, int k, int l, byte byte0, int i1, boolean flag)
    {
        int j1 = 256;
        if(flag)
            j1 += 0x20000;
        k -= offsetx;
        l -= offsety;
        if(i == 1 || i == 3)
        {
            int k1 = j;
            j = i1;
            i1 = k1;
        }
        for(int l1 = k; l1 < k + j; l1++)
            if(l1 >= 0 && l1 < sizex)
            {
                for(int i2 = l; i2 < l + i1; i2++)
                    if(i2 >= 0 && i2 < sizey)
                        maskAND(j1, l1, i2, 933);

            }

    }

    public void maskAND(int i, int j, int k, int l)
    {
        index[j][k] &= 0xffffff - i;
    }

    public void method218(int junk, int j, int k)
    {
        k -= offsetx;
        j -= offsety;
        index[k][j] &= 0xdfffff;
    }

    public boolean method219(int i, int j, int k, int junk, int i1, int j1, int k1)
    {
        if(j == i && k == k1)
            return true;
        j -= offsetx;
        k -= offsety;
        i -= offsetx;
        k1 -= offsety;
        if(j1 == 0)
            if(i1 == 0)
            {
                if(j == i - 1 && k == k1)
                    return true;
                if(j == i && k == k1 + 1 && (index[j][k] & 0x1280120) == 0)
                    return true;
                if(j == i && k == k1 - 1 && (index[j][k] & 0x1280102) == 0)
                    return true;
            } else
            if(i1 == 1)
            {
                if(j == i && k == k1 + 1)
                    return true;
                if(j == i - 1 && k == k1 && (index[j][k] & 0x1280108) == 0)
                    return true;
                if(j == i + 1 && k == k1 && (index[j][k] & 0x1280180) == 0)
                    return true;
            } else
            if(i1 == 2)
            {
                if(j == i + 1 && k == k1)
                    return true;
                if(j == i && k == k1 + 1 && (index[j][k] & 0x1280120) == 0)
                    return true;
                if(j == i && k == k1 - 1 && (index[j][k] & 0x1280102) == 0)
                    return true;
            } else
            if(i1 == 3)
            {
                if(j == i && k == k1 - 1)
                    return true;
                if(j == i - 1 && k == k1 && (index[j][k] & 0x1280108) == 0)
                    return true;
                if(j == i + 1 && k == k1 && (index[j][k] & 0x1280180) == 0)
                    return true;
            }
        if(j1 == 2)
            if(i1 == 0)
            {
                if(j == i - 1 && k == k1)
                    return true;
                if(j == i && k == k1 + 1)
                    return true;
                if(j == i + 1 && k == k1 && (index[j][k] & 0x1280180) == 0)
                    return true;
                if(j == i && k == k1 - 1 && (index[j][k] & 0x1280102) == 0)
                    return true;
            } else
            if(i1 == 1)
            {
                if(j == i - 1 && k == k1 && (index[j][k] & 0x1280108) == 0)
                    return true;
                if(j == i && k == k1 + 1)
                    return true;
                if(j == i + 1 && k == k1)
                    return true;
                if(j == i && k == k1 - 1 && (index[j][k] & 0x1280102) == 0)
                    return true;
            } else
            if(i1 == 2)
            {
                if(j == i - 1 && k == k1 && (index[j][k] & 0x1280108) == 0)
                    return true;
                if(j == i && k == k1 + 1 && (index[j][k] & 0x1280120) == 0)
                    return true;
                if(j == i + 1 && k == k1)
                    return true;
                if(j == i && k == k1 - 1)
                    return true;
            } else
            if(i1 == 3)
            {
                if(j == i - 1 && k == k1)
                    return true;
                if(j == i && k == k1 + 1 && (index[j][k] & 0x1280120) == 0)
                    return true;
                if(j == i + 1 && k == k1 && (index[j][k] & 0x1280180) == 0)
                    return true;
                if(j == i && k == k1 - 1)
                    return true;
            }
        if(j1 == 9)
        {
            if(j == i && k == k1 + 1 && (index[j][k] & 0x20) == 0)
                return true;
            if(j == i && k == k1 - 1 && (index[j][k] & 2) == 0)
                return true;
            if(j == i - 1 && k == k1 && (index[j][k] & 8) == 0)
                return true;
            if(j == i + 1 && k == k1 && (index[j][k] & 0x80) == 0)
                return true;
        }
        return false;
    }

    public boolean method220(int i, int j, int k, int l, int i1, int j1, int k1)
    {
        if(k1 != 0)
            throw new NullPointerException();
        if(j1 == i && k == j)
            return true;
        j1 -= offsetx;
        k -= offsety;
        i -= offsetx;
        j -= offsety;
        if(l == 6 || l == 7)
        {
            if(l == 7)
                i1 = i1 + 2 & 3;
            if(i1 == 0)
            {
                if(j1 == i + 1 && k == j && (index[j1][k] & 0x80) == 0)
                    return true;
                if(j1 == i && k == j - 1 && (index[j1][k] & 2) == 0)
                    return true;
            } else
            if(i1 == 1)
            {
                if(j1 == i - 1 && k == j && (index[j1][k] & 8) == 0)
                    return true;
                if(j1 == i && k == j - 1 && (index[j1][k] & 2) == 0)
                    return true;
            } else
            if(i1 == 2)
            {
                if(j1 == i - 1 && k == j && (index[j1][k] & 8) == 0)
                    return true;
                if(j1 == i && k == j + 1 && (index[j1][k] & 0x20) == 0)
                    return true;
            } else
            if(i1 == 3)
            {
                if(j1 == i + 1 && k == j && (index[j1][k] & 0x80) == 0)
                    return true;
                if(j1 == i && k == j + 1 && (index[j1][k] & 0x20) == 0)
                    return true;
            }
        }
        if(l == 8)
        {
            if(j1 == i && k == j + 1 && (index[j1][k] & 0x20) == 0)
                return true;
            if(j1 == i && k == j - 1 && (index[j1][k] & 2) == 0)
                return true;
            if(j1 == i - 1 && k == j && (index[j1][k] & 8) == 0)
                return true;
            if(j1 == i + 1 && k == j && (index[j1][k] & 0x80) == 0)
                return true;
        }
        return false;
    }

    public boolean method221(byte junk, int i, int j, int k, int l, int i1, int j1, int k1) {
        int l1 = (j + j1) - 1;
        int i2 = (i + l) - 1;
        if(k >= j && k <= l1 && k1 >= i && k1 <= i2)
            return true;
        if(k == j - 1 && k1 >= i && k1 <= i2 && (index[k - offsetx][k1 - offsety] & 8) == 0 && (i1 & 8) == 0)
            return true;
        if(k == l1 + 1 && k1 >= i && k1 <= i2 && (index[k - offsetx][k1 - offsety] & 0x80) == 0 && (i1 & 2) == 0)
            return true;
        if(k1 == i - 1 && k >= j && k <= l1 && (index[k - offsetx][k1 - offsety] & 2) == 0 && (i1 & 4) == 0)
            return true;
        return k1 == i2 + 1 && k >= j && k <= l1 && (index[k - offsetx][k1 - offsety] & 0x20) == 0 && (i1 & 1) == 0;
    }

    public int offsetx;
    public int offsety;
    public int sizex;
    public int sizey;
    public int index[][];
}
