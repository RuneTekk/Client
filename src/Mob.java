// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

public class Mob extends Entity {

    public void updateMobPosition(int xOffset, int yOffset, boolean clearQueue) {
        if(animid_request != -1 && AnimSequence.animationsequences[animid_request].anInt364 == 1)
            animid_request = -1;
        if(!clearQueue)
        {
            int k = xOffset - xList[0];
            int l = yOffset - yList[0];
            if(k >= -8 && k <= 8 && l >= -8 && l <= 8)
            {
                if(stack_position_mob < 9)
                    stack_position_mob++;
                for(int i1 = stack_position_mob; i1 > 0; i1--)
                {
                    xList[i1] = xList[i1 - 1];
                    yList[i1] = yList[i1 - 1];
                    running_stack[i1] = running_stack[i1 - 1];
                }

                xList[0] = xOffset;
                yList[0] = yOffset;
                running_stack[0] = false;
                return;
            }
        }
        stack_position_mob = 0;
        anInt1542 = 0;
        anInt1503 = 0;
        xList[0] = xOffset;
        yList[0] = yOffset;
        fineX = xList[0] * 128 + halfOffsets * 64;
        fineY = yList[0] * 128 + halfOffsets * 64;
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

    public void handleMobMovment(boolean isRunning, int updateType)
    {
        int deltaX = xList[0];
        int deltaY = yList[0];
        if(updateType == 0)
        {
            deltaX--;
            deltaY++;
        }
        if(updateType == 1)
            deltaY++;
        if(updateType == 2)
        {
            deltaX++;
            deltaY++;
        }
        if(updateType == 3)
            deltaX--;
        if(updateType == 4)
            deltaX++;
        if(updateType == 5)
        {
            deltaX--;
            deltaY--;
        }
        if(updateType == 6)
            deltaY--;
        if(updateType == 7)
        {
            deltaX++;
            deltaY--;
        }
        if(animid_request != -1 && AnimSequence.animationsequences[animid_request].anInt364 == 1)
            animid_request = -1;
        if(stack_position_mob < 9)
            stack_position_mob++;
        for(int l = stack_position_mob; l > 0; l--)
        {
            xList[l] = xList[l - 1];
            yList[l] = yList[l - 1];
            running_stack[l] = running_stack[l - 1];
        }
        xList[0] = deltaX;
        yList[0] = deltaY;
        running_stack[0] = isRunning;
    }

    public boolean hasDefinition() {
        return false;
    }

    public Mob()
    {
        xList = new int[10];
        yList = new int[10];
        anInt1502 = -1;
        rotation = 32;
        runAnimation = -1;
        anInt1507 = 200;
        aBoolean1508 = false;
        anInt1509 = -35698;
        standAnimation = -1;
        standTurnAnimation = -1;
        hitamt_stack = new int[4];
        hittype_stack = new int[4];
        hitdelay_stack = new int[4];
        anInt1517 = -1;
        anInt1520 = -1;
        animid_request = -1;
        anInt1532 = -1000;
        anInt1535 = 100;
        anInt1536 = -895;
        halfOffsets = 1;
        aBoolean1541 = false;
        running_stack = new boolean[10];
        walkAnimation = -1;
        turnAnimation180 = -1;
        turnCwAnimation90 = -1;
        turnCcwAnimation90 = -1;
    }

    public int xList[];
    public int yList[];
    public int anInt1502;
    public int anInt1503;
    public int rotation;
    public int runAnimation;
    public String chat_txt;
    public int anInt1507;
    public boolean aBoolean1508;
    public int anInt1509;
    public int anInt1510;
    public int standAnimation;
    public int standTurnAnimation;
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
    public int halfOffsets;
    public boolean aBoolean1541;
    public int anInt1542;
    public int forcewlk_startx;
    public int forcewlk_endx;
    public int forcewlk_starty;
    public int forcewlk_endy;
    public int forcewlk_sp1;
    public int forcewlk_sp2;
    public int forcewlk_dir;
    public int fineX;
    public int fineY;
    public int anInt1552;
    public boolean running_stack[];
    public int walkAnimation;
    public int turnAnimation180;
    public int turnCwAnimation90;
    public int turnCcwAnimation90;
}
