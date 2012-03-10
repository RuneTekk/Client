// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

public final class PlaneFlags {

    public PlaneFlags(int width, int height) {
        offsetX = 0;
        offsetY = 0;
        this.bufferWidth = width;
        this.bufferHeight = height;
        flagBuffer = new int[bufferWidth][bufferHeight];
        resetFlagBuffer();
    }

    public void resetFlagBuffer() {
        for(int x = 0; x < bufferWidth; x++) {
            for(int y = 0; y < bufferHeight; y++)
                if(x == 0 || y == 0 || x == bufferWidth - 1 || y == bufferHeight - 1)
                    flagBuffer[x][y] = 0xffffff;
                else
                    flagBuffer[x][y] = 0x1000000;
        }
    }

    public void method211(int y, int rotation, int x, int type, boolean flag)
    {
        x -= offsetX;
        y -= offsetY;
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
        l -= offsetX;
        i1 -= offsetY;
        if(rotation == 1 || rotation == 3)
        {
            int l1 = j;
            j = k;
            k = l1;
        }
        for(int i2 = l; i2 < l + j; i2++)
            if(i2 >= 0 && i2 < bufferWidth) {
                for(int j2 = i1; j2 < i1 + k; j2++)
                    if(j2 >= 0 && j2 < bufferHeight)
                        maskOR(i2, j2, k1);
            }
    }

    public void method213(int i, int k)
    {
        k -= offsetX;
        i -= offsetY;
        flagBuffer[k][i] |= 0x200000;
    }

    public void maskOR(int x, int y, int mask) {
        flagBuffer[x][y] |= mask;
    }

    public void method215(int i, int j, boolean flag, boolean flag1, int k, int l)
    {
        k -= offsetX;
        l -= offsetY;
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
        k -= offsetX;
        l -= offsetY;
        if(i == 1 || i == 3)
        {
            int k1 = j;
            j = i1;
            i1 = k1;
        }
        for(int l1 = k; l1 < k + j; l1++)
            if(l1 >= 0 && l1 < bufferWidth)
            {
                for(int i2 = l; i2 < l + i1; i2++)
                    if(i2 >= 0 && i2 < bufferHeight)
                        maskAND(j1, l1, i2, 933);

            }

    }

    public void maskAND(int i, int j, int k, int l)
    {
        flagBuffer[j][k] &= 0xffffff - i;
    }

    public void method218(int junk, int j, int k)
    {
        k -= offsetX;
        j -= offsetY;
        flagBuffer[k][j] &= 0xdfffff;
    }

    public boolean method219(int i, int j, int k, int junk, int i1, int j1, int k1)
    {
        if(j == i && k == k1)
            return true;
        j -= offsetX;
        k -= offsetY;
        i -= offsetX;
        k1 -= offsetY;
        if(j1 == 0)
            if(i1 == 0)
            {
                if(j == i - 1 && k == k1)
                    return true;
                if(j == i && k == k1 + 1 && (flagBuffer[j][k] & 0x1280120) == 0)
                    return true;
                if(j == i && k == k1 - 1 && (flagBuffer[j][k] & 0x1280102) == 0)
                    return true;
            } else
            if(i1 == 1)
            {
                if(j == i && k == k1 + 1)
                    return true;
                if(j == i - 1 && k == k1 && (flagBuffer[j][k] & 0x1280108) == 0)
                    return true;
                if(j == i + 1 && k == k1 && (flagBuffer[j][k] & 0x1280180) == 0)
                    return true;
            } else
            if(i1 == 2)
            {
                if(j == i + 1 && k == k1)
                    return true;
                if(j == i && k == k1 + 1 && (flagBuffer[j][k] & 0x1280120) == 0)
                    return true;
                if(j == i && k == k1 - 1 && (flagBuffer[j][k] & 0x1280102) == 0)
                    return true;
            } else
            if(i1 == 3)
            {
                if(j == i && k == k1 - 1)
                    return true;
                if(j == i - 1 && k == k1 && (flagBuffer[j][k] & 0x1280108) == 0)
                    return true;
                if(j == i + 1 && k == k1 && (flagBuffer[j][k] & 0x1280180) == 0)
                    return true;
            }
        if(j1 == 2)
            if(i1 == 0)
            {
                if(j == i - 1 && k == k1)
                    return true;
                if(j == i && k == k1 + 1)
                    return true;
                if(j == i + 1 && k == k1 && (flagBuffer[j][k] & 0x1280180) == 0)
                    return true;
                if(j == i && k == k1 - 1 && (flagBuffer[j][k] & 0x1280102) == 0)
                    return true;
            } else
            if(i1 == 1)
            {
                if(j == i - 1 && k == k1 && (flagBuffer[j][k] & 0x1280108) == 0)
                    return true;
                if(j == i && k == k1 + 1)
                    return true;
                if(j == i + 1 && k == k1)
                    return true;
                if(j == i && k == k1 - 1 && (flagBuffer[j][k] & 0x1280102) == 0)
                    return true;
            } else
            if(i1 == 2)
            {
                if(j == i - 1 && k == k1 && (flagBuffer[j][k] & 0x1280108) == 0)
                    return true;
                if(j == i && k == k1 + 1 && (flagBuffer[j][k] & 0x1280120) == 0)
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
                if(j == i && k == k1 + 1 && (flagBuffer[j][k] & 0x1280120) == 0)
                    return true;
                if(j == i + 1 && k == k1 && (flagBuffer[j][k] & 0x1280180) == 0)
                    return true;
                if(j == i && k == k1 - 1)
                    return true;
            }
        if(j1 == 9)
        {
            if(j == i && k == k1 + 1 && (flagBuffer[j][k] & 0x20) == 0)
                return true;
            if(j == i && k == k1 - 1 && (flagBuffer[j][k] & 2) == 0)
                return true;
            if(j == i - 1 && k == k1 && (flagBuffer[j][k] & 8) == 0)
                return true;
            if(j == i + 1 && k == k1 && (flagBuffer[j][k] & 0x80) == 0)
                return true;
        }
        return false;
    }

    public boolean method220(int xO, int yO, int y, int type, int i1, int x, int k1)
    {
        if(x == xO && y == yO)
            return true;
        x -= offsetX;
        y -= offsetY;
        xO -= offsetX;
        yO -= offsetY;
        if(type == 6 || type == 7)
        {
            if(type == 7)
                i1 = i1 + 2 & 3;
            if(i1 == 0)
            {
                if(x == xO + 1 && y == yO && (flagBuffer[x][y] & 0x80) == 0)
                    return true;
                if(x == xO && y == yO - 1 && (flagBuffer[x][y] & 2) == 0)
                    return true;
            } else
            if(i1 == 1)
            {
                if(x == xO - 1 && y == yO && (flagBuffer[x][y] & 8) == 0)
                    return true;
                if(x == xO && y == yO - 1 && (flagBuffer[x][y] & 2) == 0)
                    return true;
            } else
            if(i1 == 2)
            {
                if(x == xO - 1 && y == yO && (flagBuffer[x][y] & 8) == 0)
                    return true;
                if(x == xO && y == yO + 1 && (flagBuffer[x][y] & 0x20) == 0)
                    return true;
            } else
            if(i1 == 3)
            {
                if(x == xO + 1 && y == yO && (flagBuffer[x][y] & 0x80) == 0)
                    return true;
                if(x == xO && y == yO + 1 && (flagBuffer[x][y] & 0x20) == 0)
                    return true;
            }
        }
        if(type == 8)
        {
            if(x == xO && y == yO + 1 && (flagBuffer[x][y] & 0x20) == 0)
                return true;
            if(x == xO && y == yO - 1 && (flagBuffer[x][y] & 2) == 0)
                return true;
            if(x == xO - 1 && y == yO && (flagBuffer[x][y] & 8) == 0)
                return true;
            if(x == xO + 1 && y == yO && (flagBuffer[x][y] & 0x80) == 0)
                return true;
        }
        return false;
    }

    public boolean method221(byte junk, int i, int j, int k, int l, int i1, int j1, int k1) {
        int l1 = (j + j1) - 1;
        int i2 = (i + l) - 1;
        if(k >= j && k <= l1 && k1 >= i && k1 <= i2)
            return true;
        if(k == j - 1 && k1 >= i && k1 <= i2 && (flagBuffer[k - offsetX][k1 - offsetY] & 8) == 0 && (i1 & 8) == 0)
            return true;
        if(k == l1 + 1 && k1 >= i && k1 <= i2 && (flagBuffer[k - offsetX][k1 - offsetY] & 0x80) == 0 && (i1 & 2) == 0)
            return true;
        if(k1 == i - 1 && k >= j && k <= l1 && (flagBuffer[k - offsetX][k1 - offsetY] & 2) == 0 && (i1 & 4) == 0)
            return true;
        return k1 == i2 + 1 && k >= j && k <= l1 && (flagBuffer[k - offsetX][k1 - offsetY] & 0x20) == 0 && (i1 & 1) == 0;
    }

    public int offsetX;
    public int offsetY;
    public int bufferWidth;
    public int bufferHeight;
    public int flagBuffer[][];
}
