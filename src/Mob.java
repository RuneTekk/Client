// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

import sign.Signlink;

public class Mob extends Entity {

    public void updateMobPosition(int i, int j, boolean flag, boolean flag1) {
        if(animid_request != -1 && AnimSequence.animationsequences[animid_request].anInt364 == 1)
            animid_request = -1;
        if(!flag)
        {
            int k = i - palettex_stack[0];
            int l = j - palettey_stack[0];
            if(k >= -8 && k <= 8 && l >= -8 && l <= 8)
            {
                if(stack_position_mob < 9)
                    stack_position_mob++;
                for(int i1 = stack_position_mob; i1 > 0; i1--)
                {
                    palettex_stack[i1] = palettex_stack[i1 - 1];
                    palettey_stack[i1] = palettey_stack[i1 - 1];
                    running_stack[i1] = running_stack[i1 - 1];
                }

                palettex_stack[0] = i;
                palettey_stack[0] = j;
                running_stack[0] = false;
                return;
            }
        }
        stack_position_mob = 0;
        anInt1542 = 0;
        anInt1503 = 0;
        palettex_stack[0] = i;
        palettey_stack[0] = j;
        fineposx = palettex_stack[0] * 128 + halftile_offsets * 64;
        if(flag1)
            anInt1536 = 42;
        fineposy = palettey_stack[0] * 128 + halftile_offsets * 64;
    }

    public void method446(boolean flag)
    {
        stack_position_mob = 0;
        anInt1542 = 0;
    }

    public void pushHit(int junk, int type, int amount, int delay)
    {
        for(int i1 = 0; i1 < 4; i1++)
            if(hitdelay_stack[i1] <= delay) {
                hitamt_stack[i1] = amount;
                hittype_stack[i1] = type;
                hitdelay_stack[i1] = delay + 70;
                return;
            }
    }

    public void handleMobMovment(boolean flag, byte byte0, int i)
    {
        int j = palettex_stack[0];
        int k = palettey_stack[0];
        if(i == 0)
        {
            j--;
            k++;
        }
        if(i == 1)
            k++;
        if(i == 2)
        {
            j++;
            k++;
        }
        if(i == 3)
            j--;
        if(i == 4)
            j++;
        if(i == 5)
        {
            j--;
            k--;
        }
        if(i == 6)
            k--;
        if(i == 7)
        {
            j++;
            k--;
        }
        if(animid_request != -1 && AnimSequence.animationsequences[animid_request].anInt364 == 1)
            animid_request = -1;
        if(stack_position_mob < 9)
            stack_position_mob++;
        for(int l = stack_position_mob; l > 0; l--)
        {
            palettex_stack[l] = palettex_stack[l - 1];
            palettey_stack[l] = palettey_stack[l - 1];
            running_stack[l] = running_stack[l - 1];
        }

        if(byte0 != 20)
        {
            return;
        } else
        {
            palettex_stack[0] = j;
            palettey_stack[0] = k;
            running_stack[0] = flag;
            return;
        }
    }

    public boolean hasDefinition(boolean flag) {
        return false;
    }

    public Mob()
    {
        palettex_stack = new int[10];
        palettey_stack = new int[10];
        anInt1502 = -1;
        rotation = 32;
        run_anim = -1;
        anInt1507 = 200;
        aBoolean1508 = false;
        anInt1509 = -35698;
        stand_anim = -1;
        standturn_anim = -1;
        hitamt_stack = new int[4];
        hittype_stack = new int[4];
        hitdelay_stack = new int[4];
        anInt1517 = -1;
        anInt1520 = -1;
        animid_request = -1;
        anInt1532 = -1000;
        anInt1535 = 100;
        anInt1536 = -895;
        halftile_offsets = 1;
        aBoolean1541 = false;
        running_stack = new boolean[10];
        walk_anim = -1;
        turn180_anim = -1;
        turn90cw_anim = -1;
        turn90ccw_anim = -1;
    }

    public int palettex_stack[];
    public int palettey_stack[];
    public int anInt1502;
    public int anInt1503;
    public int rotation;
    public int run_anim;
    public String chat_txt;
    public int anInt1507;
    public boolean aBoolean1508;
    public int anInt1509;
    public int anInt1510;
    public int stand_anim;
    public int standturn_anim;
    public int anInt1513;
    public int hitamt_stack[];
    public int hittype_stack[];
    public int hitdelay_stack[];
    public int anInt1517;
    public int anInt1518;
    public int anInt1519;
    public int anInt1520;
    public int anInt1521;
    public int anInt1522;
    public int anInt1523;
    public int anInt1524;
    public int stack_position_mob;
    public int animid_request;
    public int anInt1527;
    public int anInt1528;
    public int animdelay_request;
    public int anInt1530;
    public int anInt1531;
    public int anInt1532;
    public int anInt1533;
    public int anInt1534;
    public int anInt1535;
    public int anInt1536;
    public int anInt1537;
    public int anInt1538;
    public int anInt1539;
    public int halftile_offsets;
    public boolean aBoolean1541;
    public int anInt1542;
    public int forcewlk_startx;
    public int forcewlk_endx;
    public int forcewlk_starty;
    public int forcewlk_endy;
    public int forcewlk_sp1;
    public int forcewlk_sp2;
    public int forcewlk_dir;
    public int fineposx;
    public int fineposy;
    public int anInt1552;
    public boolean running_stack[];
    public int walk_anim;
    public int turn180_anim;
    public int turn90cw_anim;
    public int turn90ccw_anim;
}
