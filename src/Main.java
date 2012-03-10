// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

import java.applet.AppletContext;
import java.awt.*;
import java.io.*;
import java.math.BigInteger;
import java.net.*;
import java.util.zip.CRC32;
import sign.Signlink;

public class Main extends ApplicationApplet {

    public static String method14(int i, int j) {
        String s = String.valueOf(i);
        for(int k = s.length() - 3; k > 0; k -= 3)
            s = s.substring(0, k) + "," + s.substring(k);
        if(j != 0)
            aBoolean1224 = !aBoolean1224;
        if(s.length() > 8)
            s = "@gre@" + s.substring(0, s.length() - 8) + " million @whi@(" + s + ")";
        else
        if(s.length() > 4)
            s = "@cya@" + s.substring(0, s.length() - 4) + "K @whi@(" + s + ")";
        return " " + s;
    }

    public void stopMidi(int junk)
    {
        Signlink.midiFade = 0;
        Signlink.midiFileName = "stop";
    }

    public void getJaggrabEntryChecksums() {
        if(!JAGGRAB_DISABLED) {
            int delay = 5;
            jaggrabArchiveCrcs[8] = 0;
            int attempts = 0;
            while(jaggrabArchiveCrcs[8] == 0) {
                String s = "Unknown problem";
                drawLoadingBar("Connecting to web server", 20);
                try {
                    DataInputStream datainputstream = writeJaggrabRequest("crc" + (int) (Math.random() * 99999999D) + "-" + 317);
                    ByteBuffer buffer = new ByteBuffer(new byte[40]);
                    datainputstream.readFully(buffer.payload, 0, 40);
                    datainputstream.close();
                    for(int i1 = 0; i1 < 9; i1++)
                        jaggrabArchiveCrcs[i1] = buffer.getDword();
                    int expectedCheck = buffer.getDword();
                    int checksumCheck = 1234;
                    for(int l1 = 0; l1 < 9; l1++)
                        checksumCheck = (checksumCheck << 1) + jaggrabArchiveCrcs[l1];
                    if(expectedCheck != checksumCheck) {
                        s = "checksum problem";
                        jaggrabArchiveCrcs[8] = 0;
                    }
                } catch(EOFException eofex) {
                    s = "EOF problem";
                    jaggrabArchiveCrcs[8] = 0;
                } catch(IOException ioex) {
                    s = "Connection problem";
                    jaggrabArchiveCrcs[8] = 0;
                } catch(Exception ex) {
                    s = "Logic problem";
                    jaggrabArchiveCrcs[8] = 0;
                    if(!Signlink.allowErrorReporting)
                        return;
                }
                if(jaggrabArchiveCrcs[8] == 0) {
                    attempts++;
                    for(int l = delay; l > 0; l--) {
                        if(attempts >= 10) {
                            drawLoadingBar("Game updated - please reload page", 10);
                            l = 10;
                        } else {
                            drawLoadingBar(s + " - Will retry in " + l + " secs.", 10);
                        }
                        try{
                            Thread.sleep(1000L);
                        } catch(Exception ex) { 
                        }
                    }
                    delay *= 2;
                    if(delay > 60)
                        delay = 60;
                    useWebJaggrab = !useWebJaggrab;
                }
            }      
        }
    }

    public boolean method17(int i, int j)
    {
        if(j < 0)
            return false;
        int k = interfaceopcodestack[j];
        if(i != 9)
            packetId = -1;
        if(k >= 2000)
            k -= 2000;
        return k == 337;
    }

    public void renderChat(int i)
    {
        chat_imagefetcher.initialize(0);
        TriangleRasterizer.heightoffsets = anIntArray1180;
        chatback.renderImage(0, 16083, 0);
        if(aBoolean1256)
        {
            b12Font.drawCenteredYText(0, aString1121, 23693, 40, 239);
            b12Font.drawCenteredYText(128, aString1212 + "*", 23693, 60, 239);
        } else
        if(anInt1225 == 1)
        {
            b12Font.drawCenteredYText(0, "Enter amount:", 23693, 40, 239);
            b12Font.drawCenteredYText(128, aString1004 + "*", 23693, 60, 239);
        } else
        if(anInt1225 == 2)
        {
            b12Font.drawCenteredYText(0, "Enter name:", 23693, 40, 239);
            b12Font.drawCenteredYText(128, aString1004 + "*", 23693, 60, 239);
        } else
        if(aString844 != null)
        {
            b12Font.drawCenteredYText(0, aString844, 23693, 40, 239);
            b12Font.drawCenteredYText(128, "Click to continue", 23693, 60, 239);
        } else
        if(anInt1276 != -1)
            drawWidget(Widget.widgets[anInt1276],0, 0, 0);
        else
        if(anInt1042 != -1)
        {
            drawWidget(Widget.widgets[anInt1042],0, 0, 0);
        } else
        {
            BitmapFont class30_sub2_sub1_sub4 = p12Font;
            int j = 0;
            BasicRasterizer.setDimensions(463, 0, 77, 0);
            for(int k = 0; k < 100; k++)
                if(msgbody_stack[k] != null)
                {
                    int l = msgtype_stack[k];
                    int i1 = (70 - j * 14) + anInt1089;
                    String s1 = msgprefix_stack[k];
                    byte byte0 = 0;
                    if(s1 != null && s1.startsWith("@cr1@"))
                    {
                        s1 = s1.substring(5);
                        byte0 = 1;
                    }
                    if(s1 != null && s1.startsWith("@cr2@"))
                    {
                        s1 = s1.substring(5);
                        byte0 = 2;
                    }
                    if(l == 0)
                    {
                        if(i1 > 0 && i1 < 110)
                            class30_sub2_sub1_sub4.drawText(0, msgbody_stack[k], i1, 822, 4);
                        j++;
                    }
                    if((l == 1 || l == 2) && (l == 1 || anInt1287 == 0 || anInt1287 == 1 && onFriendsList(false, s1)))
                    {
                        if(i1 > 0 && i1 < 110)
                        {
                            int j1 = 4;
                            if(byte0 == 1)
                            {
                                mod_icons[0].renderImage(j1, 16083, i1 - 12);
                                j1 += 14;
                            }
                            if(byte0 == 2)
                            {
                                mod_icons[1].renderImage(j1, 16083, i1 - 12);
                                j1 += 14;
                            }
                            class30_sub2_sub1_sub4.drawText(0, s1 + ":", i1, 822, j1);
                            j1 += class30_sub2_sub1_sub4.widthFontMetrics(anInt1116, s1) + 8;
                            class30_sub2_sub1_sub4.drawText(255, msgbody_stack[k], i1, 822, j1);
                        }
                        j++;
                    }
                    if((l == 3 || l == 7) && anInt1195 == 0 && (l == 7 || anInt845 == 0 || anInt845 == 1 && onFriendsList(false, s1)))
                    {
                        if(i1 > 0 && i1 < 110)
                        {
                            int k1 = 4;
                            class30_sub2_sub1_sub4.drawText(0, "From", i1, 822, k1);
                            k1 += class30_sub2_sub1_sub4.widthFontMetrics(anInt1116, "From ");
                            if(byte0 == 1)
                            {
                                mod_icons[0].renderImage(k1, 16083, i1 - 12);
                                k1 += 14;
                            }
                            if(byte0 == 2)
                            {
                                mod_icons[1].renderImage(k1, 16083, i1 - 12);
                                k1 += 14;
                            }
                            class30_sub2_sub1_sub4.drawText(0, s1 + ":", i1, 822, k1);
                            k1 += class30_sub2_sub1_sub4.widthFontMetrics(anInt1116, s1) + 8;
                            class30_sub2_sub1_sub4.drawText(0x800000, msgbody_stack[k], i1, 822, k1);
                        }
                        j++;
                    }
                    if(l == 4 && (anInt1248 == 0 || anInt1248 == 1 && onFriendsList(false, s1)))
                    {
                        if(i1 > 0 && i1 < 110)
                            class30_sub2_sub1_sub4.drawText(0x800080, s1 + " " + msgbody_stack[k], i1, 822, 4);
                        j++;
                    }
                    if(l == 5 && anInt1195 == 0 && anInt845 < 2)
                    {
                        if(i1 > 0 && i1 < 110)
                            class30_sub2_sub1_sub4.drawText(0x800000, msgbody_stack[k], i1, 822, 4);
                        j++;
                    }
                    if(l == 6 && anInt1195 == 0 && anInt845 < 2)
                    {
                        if(i1 > 0 && i1 < 110)
                        {
                            class30_sub2_sub1_sub4.drawText(0, "To " + s1 + ":", i1, 822, 4);
                            class30_sub2_sub1_sub4.drawText(0x800000, msgbody_stack[k], i1, 822, 12 + class30_sub2_sub1_sub4.widthFontMetrics(anInt1116, "To " + s1));
                        }
                        j++;
                    }
                    if(l == 8 && (anInt1248 == 0 || anInt1248 == 1 && onFriendsList(false, s1)))
                    {
                        if(i1 > 0 && i1 < 110)
                            class30_sub2_sub1_sub4.drawText(0x7e3200, s1 + " " + msgbody_stack[k], i1, 822, 4);
                        j++;
                    }
                }

            BasicRasterizer.reset();
            anInt1211 = j * 14 + 7;
            if(anInt1211 < 78)
                anInt1211 = 78;
            drawScrollBar(519, 77, anInt1211 - anInt1089 - 77, 0, 463, anInt1211);
            String s;
            if(localPlayer != null && localPlayer.name != null)
                s = localPlayer.name;
            else
                s = TextTools.formatUsername(-45804, username);
            class30_sub2_sub1_sub4.drawText(0, s + ":", 90, 822, 4);
            class30_sub2_sub1_sub4.drawText(255, aString887 + "*", 90, 822, 6 + class30_sub2_sub1_sub4.widthFontMetrics(anInt1116, s + ": "));
            BasicRasterizer.drawHorizontalLine(0, 77, 479, 0);
        }
        if(aBoolean885 && clickarea == 2)
            method40((byte)9);
        chat_imagefetcher.updateGraphics(357, 23680, super.appletGraphics, 17);
        toplefttext_imagefetcher.initialize(0);
        TriangleRasterizer.heightoffsets = anIntArray1182;
        if(i < 6 || i > 6)
            aBoolean991 = !aBoolean991;
    }

    @Override
    public void init() {
        nodeid = Integer.parseInt(getParameter("nodeid"));
        portOffset = Integer.parseInt(getParameter("portoff"));
        String s = getParameter("lowmem");
        if(s != null && s.equals("1"))
            initializeLowMemory();
        else
            setHighMem(false);
        String s1 = getParameter("free");
        if(s1 != null && s1.equals("1"))
            members = false;
        else
            members = true;
        initializeApplet(503, 765);
    }

    @Override
    public void createThread(Runnable runnable, int i)
    {
        if(i > 10)
            i = 10;
        if(Signlink.applet != null)
        {
            Signlink.createThread(runnable, i);
            return;
        } else
        {
            super.createThread(runnable, i);
            return;
        }
    }

    public Socket createSocket(int port) throws IOException {
        if(Signlink.applet != null)
            return Signlink.createSocket(port);
        else
            return new Socket(InetAddress.getByName(getCodeBase().getHost()), port);
    }

    public void handleClickPackets_(int i)
    {
        if(i != 4)
            packetId = inbuffer.getUbyte();
        if(anInt1086 != 0)
            return;
        int j = super.anInt26;
        if(anInt1136 == 1 && super.pressedX >= 516 && super.pressedY >= 160 && super.pressedX <= 765 && super.pressedY <= 205)
            j = 0;
        if(aBoolean885)
        {
            if(j != 1)
            {
                int k = super.newMouseX;
                int j1 = super.newMouseY;
                if(clickarea == 0)
                {
                    k -= 4;
                    j1 -= 4;
                }
                if(clickarea == 1)
                {
                    k -= 553;
                    j1 -= 205;
                }
                if(clickarea == 2)
                {
                    k -= 17;
                    j1 -= 357;
                }
                if(k < anInt949 - 10 || k > anInt949 + anInt951 + 10 || j1 < anInt950 - 10 || j1 > anInt950 + anInt952 + 10)
                {
                    aBoolean885 = false;
                    if(clickarea == 1)
                        aBoolean1153 = true;
                    if(clickarea == 2)
                        aBoolean1223 = true;
                }
            }
            if(j == 1)
            {
                int l = anInt949;
                int k1 = anInt950;
                int i2 = anInt951;
                int k2 = super.pressedX;
                int l2 = super.pressedY;
                if(clickarea == 0)
                {
                    k2 -= 4;
                    l2 -= 4;
                }
                if(clickarea == 1)
                {
                    k2 -= 553;
                    l2 -= 205;
                }
                if(clickarea == 2)
                {
                    k2 -= 17;
                    l2 -= 357;
                }
                int i3 = -1;
                for(int j3 = 0; j3 < anInt1133; j3++)
                {
                    int k3 = k1 + 31 + (anInt1133 - 1 - j3) * 15;
                    if(k2 > l && k2 < l + i2 && l2 > k3 - 13 && l2 < k3 + 3)
                        i3 = j3;
                }

                if(i3 != -1)
                    handleClick(i3, false);
                aBoolean885 = false;
                if(clickarea == 1)
                    aBoolean1153 = true;
                if(clickarea == 2)
                {
                    aBoolean1223 = true;
                    return;
                }
            }
        } else
        {
            if(j == 1 && anInt1133 > 0)
            {
                int i1 = interfaceopcodestack[anInt1133 - 1];
                if(i1 == 632 || i1 == 78 || i1 == 867 || i1 == 431 || i1 == 53 || i1 == 74 || i1 == 454 || i1 == 539 || i1 == 493 || i1 == 847 || i1 == 447 || i1 == 1125)
                {
                    int l1 = interfacestack_a[anInt1133 - 1];
                    int j2 = interfacestack_b[anInt1133 - 1];
                    Widget class9 = Widget.widgets[j2];
                    if(class9.aBoolean259 || class9.aBoolean235)
                    {
                        aBoolean1242 = false;
                        anInt989 = 0;
                        moveitem_frameid = j2;
                        moveitem_startslot = l1;
                        anInt1086 = 2;
                        anInt1087 = super.pressedX;
                        anInt1088 = super.pressedY;
                        if(Widget.widgets[j2].parentId == anInt857)
                            anInt1086 = 1;
                        if(Widget.widgets[j2].parentId == anInt1276)
                            anInt1086 = 3;
                        return;
                    }
                }
            }
            if(j == 1 && (anInt1253 == 1 || method17(9, anInt1133 - 1)) && anInt1133 > 2)
                j = 2;
            if(j == 1 && anInt1133 > 0)
                handleClick(anInt1133 - 1, false);
            if(j == 2 && anInt1133 > 0)
                method116(true);
        }
    }

    public void initMidi(boolean fade, int junk, byte data[])
    {
        Signlink.midiFade = fade ? 1 : 0;
        Signlink.writeMidiFile(data, data.length);
    }

    public void processLandscapeLoading_(boolean flag)
    {
        try
        {
            anInt985 = -1;
            gfxs_storage.clear();
            aClass19_1013.clear();
            TriangleRasterizer.resetCaches(anInt846);
            clearModelCaches(false);
            pallet.reset();
            System.gc();
            for(int i = 0; i < 4; i++)
                planeFlags[i].resetFlagBuffer();
            for(int z = 0; z < 4; z++)
            {
                for(int x = 0; x < 104; x++)
                {
                    for(int y = 0; y < 104; y++)
                        tileFlags[z][x][y] = 0;
                }
            }

            LandscapeLoader loader = new LandscapeLoader(tileFlags, -60, 104, 104, tileHeightmap);
            int amountRegions = tileSrcs.length;
            gameBuffer.putPacket(0);
            if(!aBoolean1159)
            {
                for(int i = 0; i < amountRegions; i++)
                {
                    int y = (regionHashes[i] >> 8) * 64 - paletteX;
                    int x = (regionHashes[i] & 0xff) * 64 - paletteY;
                    byte src[] = tileSrcs[i];
                    if(src != null)
                        loader.loadRegionTiles(src, x, y, (chunkx_ - 6) * 8, (chunky_ - 6) * 8, planeFlags);
                }

                for(int i = 0; i < amountRegions; i++)
                {
                    int offX = (regionHashes[i] >> 8) * 64 - paletteX;
                    int offY = (regionHashes[i] & 0xff) * 64 - paletteY;
                    byte src[] = tileSrcs[i];
                    if(src == null && chunky_ < 800)
                        loader.adjustHeightmap(offY, 64, 0, 64, offX);
                }

                anInt1097++;
                if(anInt1097 > 160)
                {
                    anInt1097 = 0;
                    gameBuffer.putPacket(238);
                    gameBuffer.put(96);
                }
                gameBuffer.putPacket(0);
                for(int i = 0; i < amountRegions; i++)
                {
                    byte src[] = regionSrcs[i];
                    if(src != null)
                    {
                        int xOff = (regionHashes[i] >> 8) * 64 - paletteX;
                        int yOff = (regionHashes[i] & 0xff) * 64 - paletteY;
                        loader.loadRegionGameObjects(src, pallet, planeFlags, xOff, yOff);
                    }
                }

            }
            if(aBoolean1159)
            {
                for(int j3 = 0; j3 < 4; j3++)
                {
                    for(int k4 = 0; k4 < 13; k4++)
                    {
                        for(int j6 = 0; j6 < 13; j6++)
                        {
                            int l7 = custompalette[j3][k4][j6];
                            if(l7 != -1)
                            {
                                int i9 = l7 >> 24 & 3;
                                int l9 = l7 >> 1 & 3;
                                int j10 = l7 >> 14 & 0x3ff;
                                int l10 = l7 >> 3 & 0x7ff;
                                int j11 = (j10 / 8 << 8) + l10 / 8;
                                for(int l11 = 0; l11 < regionHashes.length; l11++)
                                {
                                    if(regionHashes[l11] != j11 || tileSrcs[l11] == null)
                                        continue;
                                    loader.loadChunkTiles(tileSrcs[l11], (j10 & 7) * 8, (l10 & 7) * 8, i9, k4 * 8, j6 * 8, l9, j3, planeFlags);
                                    break;
                                }

                            }
                        }

                    }

                }

                for(int l4 = 0; l4 < 13; l4++)
                {
                    for(int k6 = 0; k6 < 13; k6++)
                    {
                        int i8 = custompalette[0][l4][k6];
                        if(i8 == -1)
                            loader.adjustHeightmap(k6 * 8, 8, 0, 8, l4 * 8);
                    }

                }

                gameBuffer.putPacket(0);
                for(int h = 0; h < 4; h++)
                {
                    for(int x = 0; x < 13; x++)
                    {
                        for(int y = 0; y < 13; y++)
                        {
                            int i10 = custompalette[h][x][y];
                            if(i10 != -1)
                            {
                                int z = i10 >> 24 & 3;
                                int rotation = i10 >> 1 & 3;
                                int chunkx = i10 >> 14 & 0x3ff;
                                int chunky = i10 >> 3 & 0x7ff;
                                int regionhash = (chunkx / 8 << 8) + chunky / 8;
                                for(int k12 = 0; k12 < regionHashes.length; k12++)
                                {
                                    if(regionHashes[k12] != regionhash || regionSrcs[k12] == null)
                                        continue;
                                    loader.loadChunk(planeFlags, pallet, z, x * 8, (chunky & 7) * 8, true, h, regionSrcs[k12], (chunkx & 7) * 8, rotation, y * 8);
                                    break;
                                }

                            }
                        }

                    }

                }

            }
            gameBuffer.putPacket(0);
            loader.method171(planeFlags, pallet, 2);
            toplefttext_imagefetcher.initialize(0);
            gameBuffer.putPacket(0);
            int k3 = LandscapeLoader.anInt145;
            if(k3 > currentZ)
                k3 = currentZ;
            if(k3 < currentZ - 1)
                k3 = currentZ - 1;
            if(lowMemory)
                pallet.buildPlane(LandscapeLoader.anInt145);
            else
                pallet.buildPlane(0);
            for(int i5 = 0; i5 < 104; i5++)
            {
                for(int i7 = 0; i7 < 104; i7++)
                    method25(i5, i7);

            }

            anInt1051++;
            if(anInt1051 > 98)
            {
                anInt1051 = 0;
                gameBuffer.putPacket(150);
            }
            method63(-919);
        }
        catch(Exception exception) { 
            exception.printStackTrace();
            int a = 5;
        }
        ObjectDefinition.aClass12_785.clear();
        isOnlineGame &= flag;
        if(super.appletFrame != null)
        {
            gameBuffer.putPacket(210);
            gameBuffer.putDword(0x3f008edd);
        }
        if(lowMemory && Signlink.mainCacheRandomAccessFile != null)
        {
            int j = ondemandhandler.getAmountArchives(0);
            for(int i1 = 0; i1 < j; i1++)
            {
                int l1 = ondemandhandler.getModelSettingFlag(i1, -203);
                if((l1 & 0x79) == 0)
                    Model.remove(116, i1);
            }

        }
        System.gc();
        TriangleRasterizer.initialize(20, true);
        ondemandhandler.method566(0);
        int k = (chunkx_ - 6) / 8 - 1;
        int j1 = (chunkx_ + 6) / 8 + 1;
        int i2 = (chunky_ - 6) / 8 - 1;
        int l2 = (chunky_ + 6) / 8 + 1;
        if(isLoadedLandscapes)
        {
            k = 49;
            j1 = 50;
            i2 = 49;
            l2 = 50;
        }
        for(int l3 = k; l3 <= j1; l3++)
        {
            for(int j5 = i2; j5 <= l2; j5++)
                if(l3 == k || l3 == j1 || j5 == i2 || j5 == l2)
                {
                    int j7 = ondemandhandler.getMapArchive(l3, j5, 0);
                    if(j7 != -1)
                        ondemandhandler.request(3, j7);
                    int k8 = ondemandhandler.getMapArchive(l3, j5, 1);
                    if(k8 != -1)
                        ondemandhandler.request(3, k8);
                }

        }

    }

    public void clearModelCaches(boolean junk)
    {
        ObjectDefinition.aClass12_785.clear();
        ObjectDefinition.object_modelstorage.clear();
        NpcDefinition.aClass12_95.clear();
        ItemDefinition.item_modelstorage.clear();
        ItemDefinition.aClass12_158.clear();
        Player.aClass12_1704.clear();
        SpotAnim.aClass12_415.clear();
    }

    public void method24(boolean flag, int i)
    {
        int ai[] = aClass30_Sub2_Sub1_Sub1_1263.buffer;
        int j = ai.length;
        for(int k = 0; k < j; k++)
            ai[k] = 0;

        for(int l = 1; l < 103; l++)
        {
            int i1 = 24628 + (103 - l) * 512 * 4;
            for(int k1 = 1; k1 < 103; k1++)
            {
                if((tileFlags[i][k1][l] & 0x18) == 0)
                    pallet.method309(ai, i1, 512, i, k1, l);
                if(i < 3 && (tileFlags[i + 1][k1][l] & 8) != 0)
                    pallet.method309(ai, i1, 512, i + 1, k1, l);
                i1 += 4;
            }

        }

        int j1 = ((238 + (int)(Math.random() * 20D)) - 10 << 16) + ((238 + (int)(Math.random() * 20D)) - 10 << 8) + ((238 + (int)(Math.random() * 20D)) - 10);
        int l1 = (238 + (int)(Math.random() * 20D)) - 10 << 16;
        aClass30_Sub2_Sub1_Sub1_1263.initialize();
        for(int i2 = 1; i2 < 103; i2++)
        {
            for(int j2 = 1; j2 < 103; j2++)
            {
                if((tileFlags[i][j2][i2] & 0x18) == 0)
                    method50(i2, -960, j1, j2, l1, i);
                if(i < 3 && (tileFlags[i + 1][j2][i2] & 8) != 0)
                    method50(i2, -960, j1, j2, l1, i + 1);
            }

        }

        toplefttext_imagefetcher.initialize(0);
        isOnlineGame &= flag;
        mapfunctionstackpos = 0;
        for(int k2 = 0; k2 < 104; k2++)
        {
            for(int l2 = 0; l2 < 104; l2++)
            {
                int i3 = pallet.method303(currentZ, k2, l2);
                if(i3 != 0)
                {
                    i3 = i3 >> 14 & 0x7fff;
                    int j3 = ObjectDefinition.getObjectDefinition(i3).mapsprite;
                    if(j3 >= 0)
                    {
                        int k3 = k2;
                        int l3 = l2;
                        if(j3 != 22 && j3 != 29 && j3 != 34 && j3 != 36 && j3 != 46 && j3 != 47 && j3 != 48)
                        {
                            byte planeWidth = 104;
                            byte planeHeight = 104;
                            int flags[][] = planeFlags[currentZ].flagBuffer;
                            for(int i4 = 0; i4 < 10; i4++)
                            {
                                int j4 = (int)(Math.random() * 4D);
                                if(j4 == 0 && k3 > 0 && k3 > k2 - 3 && (flags[k3 - 1][l3] & 0x1280108) == 0)
                                    k3--;
                                if(j4 == 1 && k3 < planeWidth - 1 && k3 < k2 + 3 && (flags[k3 + 1][l3] & 0x1280180) == 0)
                                    k3++;
                                if(j4 == 2 && l3 > 0 && l3 > l2 - 3 && (flags[k3][l3 - 1] & 0x1280102) == 0)
                                    l3--;
                                if(j4 == 3 && l3 < planeHeight - 1 && l3 < l2 + 3 && (flags[k3][l3 + 1] & 0x1280120) == 0)
                                    l3++;
                            }

                        }
                        mapfunctionstack[mapfunctionstackpos] = mapfunction[j3];
                        anIntArray1072[mapfunctionstackpos] = k3;
                        anIntArray1073[mapfunctionstackpos] = l3;
                        mapfunctionstackpos++;
                    }
                }
            }

        }

    }

    public void method25(int i, int j)
    {
        Deque class19 = grounditems[currentZ][i][j];
        if(class19 == null)
        {
            pallet.method295(currentZ, i, j);
            return;
        }
        int k = 0xfa0a1f01;
        Object obj = null;
        for(GroundItem class30_sub2_sub4_sub2 = (GroundItem)class19.getFirst(); class30_sub2_sub4_sub2 != null; class30_sub2_sub4_sub2 = (GroundItem)class19.getNextFront())
        {
            ItemDefinition class8 = ItemDefinition.getItemDefinition(class30_sub2_sub4_sub2.itemid);
            int l = class8.anInt155;
            if(class8.aBoolean176)
                l *= class30_sub2_sub4_sub2.amount + 1;
            if(l > k)
            {
                k = l;
                obj = class30_sub2_sub4_sub2;
            }
        }

        class19.addFirst(((Node) (obj)));
        Object obj1 = null;
        Object obj2 = null;
        for(GroundItem class30_sub2_sub4_sub2_1 = (GroundItem)class19.getFirst(); class30_sub2_sub4_sub2_1 != null; class30_sub2_sub4_sub2_1 = (GroundItem)class19.getNextFront())
        {
            if(class30_sub2_sub4_sub2_1.itemid != ((GroundItem) (obj)).itemid && obj1 == null)
                obj1 = class30_sub2_sub4_sub2_1;
            if(class30_sub2_sub4_sub2_1.itemid != ((GroundItem) (obj)).itemid && class30_sub2_sub4_sub2_1.itemid != ((GroundItem) (obj1)).itemid && obj2 == null)
                obj2 = class30_sub2_sub4_sub2_1;
        }

        int i1 = i + (j << 7) + 0x60000000;
        pallet.method281((byte)7, i, i1, ((Entity) (obj1)), calculateTileHeight( i * 128 + 64, j * 128 + 64, currentZ), ((Entity) (obj2)), ((Entity) (obj)), currentZ, j);
    }

    public void processNPCs(boolean flag, int i)
    {
        for(int j = 0; j < anInt836; j++)
        {
            Npc class30_sub2_sub4_sub1_sub1 = npcs[localNpcIds[j]];
            int k = 0x20000000 + (localNpcIds[j] << 14);
            if(class30_sub2_sub4_sub1_sub1 == null || !class30_sub2_sub4_sub1_sub1.hasDefinition() || class30_sub2_sub4_sub1_sub1.definition.placementpriority != flag)
                continue;
            int l = ((Mob) (class30_sub2_sub4_sub1_sub1)).fineX >> 7;
            int i1 = ((Mob) (class30_sub2_sub4_sub1_sub1)).fineY >> 7;
            if(l < 0 || l >= 104 || i1 < 0 || i1 >= 104)
                continue;
            if(((Mob) (class30_sub2_sub4_sub1_sub1)).halfOffsets == 1 && (((Mob) (class30_sub2_sub4_sub1_sub1)).fineX & 0x7f) == 64 && (((Mob) (class30_sub2_sub4_sub1_sub1)).fineY & 0x7f) == 64)
            {
                if(anIntArrayArray929[l][i1] == anInt1265)
                    continue;
                anIntArrayArray929[l][i1] = anInt1265;
            }
            if(!class30_sub2_sub4_sub1_sub1.definition.isvisible)
                k += 0x80000000;
            pallet.method285(currentZ, ((Mob) (class30_sub2_sub4_sub1_sub1)).anInt1552, (byte)6, calculateTileHeight( ((Mob) (class30_sub2_sub4_sub1_sub1)).fineX, ((Mob) (class30_sub2_sub4_sub1_sub1)).fineY, currentZ), k, ((Mob) (class30_sub2_sub4_sub1_sub1)).fineY, (((Mob) (class30_sub2_sub4_sub1_sub1)).halfOffsets - 1) * 64 + 60, ((Mob) (class30_sub2_sub4_sub1_sub1)).fineX, class30_sub2_sub4_sub1_sub1, ((Mob) (class30_sub2_sub4_sub1_sub1)).aBoolean1541);
        }

        if(i == -30815);
    }

    public boolean waveReplay(int i)
    {
        if(i != 11456)
            throw new NullPointerException();
        else
            return Signlink.reloadWaveFile();
    }

    public void displayLoadError(String s)
    {
        try {
            getAppletContext().showDocument(new URL(getCodeBase(), "loaderror_" + s + ".html"));
        } catch(Exception exception) {
            exception.printStackTrace();
        }
        do
            try
            {
                Thread.sleep(1000L);
            }
            catch(Exception _ex) { }
        while(true);
    }

    public void handleInterfaceOptions(int xlimit, int junk, Widget parent, int mousex, int ylimit, int mousey, int yoffset)
    {
        if(parent.type != 0 || parent.childrenIds == null || parent.isActive)
            return;
        if(mousex < xlimit || mousey < ylimit || mousex > xlimit + parent.width || mousey > ylimit + parent.height)
            return;
        int k1 = parent.childrenIds.length;
        for(int l1 = 0; l1 < k1; l1++)
        {
            int i2 = parent.childrenOffX[l1] + xlimit;
            int j2 = (parent.childrenOffY[l1] + ylimit) - yoffset;
            Widget class9_1 = Widget.widgets[parent.childrenIds[l1]];
            i2 += class9_1.offsetX;
            j2 += class9_1.offsetY;
            if((class9_1.childId >= 0 || class9_1.anInt216 != 0) && mousex >= i2 && mousey >= j2 && mousex < i2 + class9_1.width && mousey < j2 + class9_1.height)
                if(class9_1.childId >= 0)
                    currentWidgetId = class9_1.childId;
                else
                    currentWidgetId = class9_1.widgetId;
            if(class9_1.type == 0)
            {
                handleInterfaceOptions(i2, 13037, class9_1, mousex, j2, mousey, class9_1.anInt224);
                if(class9_1.currentHeight > class9_1.height)
                    method65(i2 + class9_1.width, class9_1.height, mousex, mousey, class9_1, j2, true, class9_1.currentHeight, 0);
            } else
            {
                if(class9_1.fieldType == 1 && mousex >= i2 && mousey >= j2 && mousex < i2 + class9_1.width && mousey < j2 + class9_1.height)
                {
                    boolean flag = false;
                    if(class9_1.actionCode != 0)
                        flag = method103(class9_1, false);
                    if(!flag)
                    {
                        interfacestringstack[anInt1133] = class9_1.optionField;
                        interfaceopcodestack[anInt1133] = 315;
                        interfacestack_b[anInt1133] = class9_1.widgetId;
                        anInt1133++;
                    }
                }
                if(class9_1.fieldType == 2 && anInt1136 == 0 && mousex >= i2 && mousey >= j2 && mousex < i2 + class9_1.width && mousey < j2 + class9_1.height)
                {
                    String s = class9_1.aString222;
                    if(s.indexOf(" ") != -1)
                        s = s.substring(0, s.indexOf(" "));
                    interfacestringstack[anInt1133] = s + " @gre@" + class9_1.aString218;
                    interfaceopcodestack[anInt1133] = 626;
                    interfacestack_b[anInt1133] = class9_1.widgetId;
                    anInt1133++;
                }
                if(class9_1.fieldType == 3 && mousex >= i2 && mousey >= j2 && mousex < i2 + class9_1.width && mousey < j2 + class9_1.height)
                {
                    interfacestringstack[anInt1133] = "Close";
                    interfaceopcodestack[anInt1133] = 200;
                    interfacestack_b[anInt1133] = class9_1.widgetId;
                    anInt1133++;
                }
                if(class9_1.fieldType == 4 && mousex >= i2 && mousey >= j2 && mousex < i2 + class9_1.width && mousey < j2 + class9_1.height)
                {
                    interfacestringstack[anInt1133] = class9_1.optionField;
                    interfaceopcodestack[anInt1133] = 169;
                    interfacestack_b[anInt1133] = class9_1.widgetId;
                    anInt1133++;
                }
                if(class9_1.fieldType == 5 && mousex >= i2 && mousey >= j2 && mousex < i2 + class9_1.width && mousey < j2 + class9_1.height)
                {
                    interfacestringstack[anInt1133] = class9_1.optionField;
                    interfaceopcodestack[anInt1133] = 646;
                    interfacestack_b[anInt1133] = class9_1.widgetId;
                    anInt1133++;
                }
                if(class9_1.fieldType == 6 && !aBoolean1149 && mousex >= i2 && mousey >= j2 && mousex < i2 + class9_1.width && mousey < j2 + class9_1.height)
                {
                    interfacestringstack[anInt1133] = class9_1.optionField;
                    interfaceopcodestack[anInt1133] = 679;
                    interfacestack_b[anInt1133] = class9_1.widgetId;
                    anInt1133++;
                }
                if(class9_1.type == 2)
                {
                    int k2 = 0;
                    for(int y = 0; y < class9_1.height; y++)
                    {
                        for(int x = 0; x < class9_1.width; x++)
                        {
                            int j3 = i2 + x * (32 + class9_1.xOff);
                            int k3 = j2 + y * (32 + class9_1.yOff);
                            if(k2 < 20)
                            {
                                j3 += class9_1.anIntArray215[k2];
                                k3 += class9_1.anIntArray247[k2];
                            }
                            if(mousex >= j3 && mousey >= k3 && mousex < j3 + 32 && mousey < k3 + 32)
                            {
                                moveitem_endslot = k2;
                                anInt1067 = class9_1.widgetId;
                                if(class9_1.itemIds[k2] > 0)
                                {
                                    ItemDefinition item = ItemDefinition.getItemDefinition(class9_1.itemIds[k2] - 1);
                                    if(anInt1282 == 1 && class9_1.aBoolean249)
                                    {
                                        if(class9_1.widgetId != anInt1284 || k2 != anInt1283)
                                        {
                                            interfacestringstack[anInt1133] = "Use " + usedItemName + " with @lre@" + item.withItemName;
                                            interfaceopcodestack[anInt1133] = 870;
                                            interfacestack_c[anInt1133] = item.id;
                                            interfacestack_a[anInt1133] = k2;
                                            interfacestack_b[anInt1133] = class9_1.widgetId;
                                            anInt1133++;
                                        }
                                    } else
                                    if(anInt1136 == 1 && class9_1.aBoolean249)
                                    {
                                        if((anInt1138 & 0x10) == 16)
                                        {
                                            interfacestringstack[anInt1133] = aString1139 + " @lre@" + item.withItemName;
                                            interfaceopcodestack[anInt1133] = 543;
                                            interfacestack_c[anInt1133] = item.id;
                                            interfacestack_a[anInt1133] = k2;
                                            interfacestack_b[anInt1133] = class9_1.widgetId;
                                            anInt1133++;
                                        }
                                    } else
                                    {
                                        if(class9_1.aBoolean249)
                                        {
                                            for(int l3 = 4; l3 >= 3; l3--)
                                                if(item.inventory_options != null && item.inventory_options[l3] != null)
                                                {
                                                    interfacestringstack[anInt1133] = item.inventory_options[l3] + " @lre@" + item.withItemName;
                                                    if(l3 == 3)
                                                        interfaceopcodestack[anInt1133] = 493;
                                                    if(l3 == 4)
                                                        interfaceopcodestack[anInt1133] = 847;
                                                    interfacestack_c[anInt1133] = item.id;
                                                    interfacestack_a[anInt1133] = k2;
                                                    interfacestack_b[anInt1133] = class9_1.widgetId;
                                                    anInt1133++;
                                                } else
                                                if(l3 == 4)
                                                {
                                                    interfacestringstack[anInt1133] = "Drop @lre@" + item.withItemName;
                                                    interfaceopcodestack[anInt1133] = 847;
                                                    interfacestack_c[anInt1133] = item.id;
                                                    interfacestack_a[anInt1133] = k2;
                                                    interfacestack_b[anInt1133] = class9_1.widgetId;
                                                    anInt1133++;
                                                }

                                        }
                                        if(class9_1.aBoolean242)
                                        {
                                            interfacestringstack[anInt1133] = "Use @lre@" + item.withItemName;
                                            interfaceopcodestack[anInt1133] = 447;
                                            interfacestack_c[anInt1133] = item.id;
                                            interfacestack_a[anInt1133] = k2;
                                            interfacestack_b[anInt1133] = class9_1.widgetId;
                                            anInt1133++;
                                        }
                                        if(class9_1.aBoolean249 && item.inventory_options != null)
                                        {
                                            for(int i4 = 2; i4 >= 0; i4--)
                                                if(item.inventory_options[i4] != null)
                                                {
                                                    interfacestringstack[anInt1133] = item.inventory_options[i4] + " @lre@" + item.withItemName;
                                                    if(i4 == 0)
                                                        interfaceopcodestack[anInt1133] = 74;
                                                    if(i4 == 1)
                                                        interfaceopcodestack[anInt1133] = 454;
                                                    if(i4 == 2)
                                                        interfaceopcodestack[anInt1133] = 539;
                                                    interfacestack_c[anInt1133] = item.id;
                                                    interfacestack_a[anInt1133] = k2;
                                                    interfacestack_b[anInt1133] = class9_1.widgetId;
                                                    anInt1133++;
                                                }

                                        }
                                        if(class9_1.itemOptions != null)
                                        {
                                            for(int j4 = 4; j4 >= 0; j4--)
                                                if(class9_1.itemOptions[j4] != null)
                                                {
                                                    interfacestringstack[anInt1133] = class9_1.itemOptions[j4] + " @lre@" + item.withItemName;
                                                    if(j4 == 0)
                                                        interfaceopcodestack[anInt1133] = 632;
                                                    if(j4 == 1)
                                                        interfaceopcodestack[anInt1133] = 78;
                                                    if(j4 == 2)
                                                        interfaceopcodestack[anInt1133] = 867;
                                                    if(j4 == 3)
                                                        interfaceopcodestack[anInt1133] = 431;
                                                    if(j4 == 4)
                                                        interfaceopcodestack[anInt1133] = 53;
                                                    interfacestack_c[anInt1133] = item.id;
                                                    interfacestack_a[anInt1133] = k2;
                                                    interfacestack_b[anInt1133] = class9_1.widgetId;
                                                    anInt1133++;
                                                }

                                        }
                                        interfacestringstack[anInt1133] = "Examine @lre@" + item.withItemName;
                                        interfaceopcodestack[anInt1133] = 1125;
                                        interfacestack_c[anInt1133] = item.id;
                                        interfacestack_a[anInt1133] = k2;
                                        interfacestack_b[anInt1133] = class9_1.widgetId;
                                        anInt1133++;
                                    }
                                }
                            }
                            k2++;
                        }

                    }

                }
            }
        }

    }

    public void drawScrollBar(int i, int j, int k, int l, int i1, int j1)
    {
        scrollbar0.renderImage(i1, 16083, l);
        scrollbar1.renderImage(i1, 16083, (l + j) - 16);
        BasicRasterizer.drawQuad(i1, l + 16, 16, j - 32, anInt1002);
        int k1 = ((j - 32) * j) / j1;
        if(k1 < 8)
            k1 = 8;
        int l1 = ((j - 32 - k1) * k) / (j1 - j);
        BasicRasterizer.drawQuad(i1, l + 16 + l1, 16, k1, anInt1063);
        BasicRasterizer.drawVerticalLine(i1,l + 16 + l1, k1, anInt902);
        BasicRasterizer.drawVerticalLine(i1 + 1, l + 16 + l1, k1, anInt902);
        BasicRasterizer.drawHorizontalLine( i1, l + 16 + l1, 16, anInt902);
        BasicRasterizer.drawHorizontalLine( i1, l + 17 + l1, 16, anInt902);
        BasicRasterizer.drawVerticalLine(i1 + 15, l + 16 + l1, k1, anInt927);
        BasicRasterizer.drawVerticalLine(i1 + 14, l + 17 + l1, k1 - 1, anInt927);
        BasicRasterizer.drawHorizontalLine( i1, l + 15 + l1 + k1, 16, anInt927);
        BasicRasterizer.drawHorizontalLine(i1 + 1, l + 14 + l1 + k1, 15, anInt927);
    }

    public void parseNpcUpdate(ByteBuffer buffer, int i, int j)
    {
        eRmQueuePosition = 0;
        amtplayerupdatestack = 0;
        method139(buffer, -45, i);
        processNpcs(i, buffer, (byte)2);
        parseNpcUpdateMasks(i, buffer, true);
        for(int k = 0; k < eRmQueuePosition; k++)
        {
            int l = eRmQueue[k];
            if(((Mob) (npcs[l])).anInt1537 != loopCycle)
            {
                npcs[l].definition = null;
                npcs[l] = null;
            }
        }

        if(buffer.offset != i)
        {
            Signlink.reportError(username + " size mismatch in getnpcpos - pos:" + buffer.offset + " psize:" + i);
            throw new RuntimeException("eek");
        }
        for(int i1 = 0; i1 < anInt836; i1++)
            if(npcs[localNpcIds[i1]] == null)
            {
                Signlink.reportError(username + " null entry in npc list - pos:" + i1 + " size:" + anInt836);
                throw new RuntimeException("eek");
            }

    }

    public void handleToolbar(boolean flag)
    {
        isOnlineGame &= flag;
        if(super.anInt26 == 1)
        {
            if(super.pressedX >= 6 && super.pressedX <= 106 && super.pressedY >= 467 && super.pressedY <= 499)
            {
                anInt1287 = (anInt1287 + 1) % 4;
                updatetoolbar = true;
                aBoolean1223 = true;
                gameBuffer.putPacket(95);
                gameBuffer.put(anInt1287);
                gameBuffer.put(anInt845);
                gameBuffer.put(anInt1248);
            }
            if(super.pressedX >= 135 && super.pressedX <= 235 && super.pressedY >= 467 && super.pressedY <= 499)
            {
                anInt845 = (anInt845 + 1) % 3;
                updatetoolbar = true;
                aBoolean1223 = true;
                gameBuffer.putPacket(95);
                gameBuffer.put(anInt1287);
                gameBuffer.put(anInt845);
                gameBuffer.put(anInt1248);
            }
            if(super.pressedX >= 273 && super.pressedX <= 373 && super.pressedY >= 467 && super.pressedY <= 499)
            {
                anInt1248 = (anInt1248 + 1) % 3;
                updatetoolbar = true;
                aBoolean1223 = true;
                gameBuffer.putPacket(95);
                gameBuffer.put(anInt1287);
                gameBuffer.put(anInt845);
                gameBuffer.put(anInt1248);
            }
            if(super.pressedX >= 412 && super.pressedX <= 512 && super.pressedY >= 467 && super.pressedY <= 499)
                if(anInt857 == -1)
                {
                    method147(537);
                    aString881 = "";
                    aBoolean1158 = false;
                    for(int i = 0; i < Widget.widgets.length; i++)
                    {
                        if(Widget.widgets[i] == null || Widget.widgets[i].actionCode != 600)
                            continue;
                        anInt1178 = anInt857 = Widget.widgets[i].parentId;
                        break;
                    }

                } else
                {
                    pushMessage("Please close the interface you have open before using 'report abuse'", 0, "", aBoolean991);
                }
            anInt940++;
            if(anInt940 > 1386)
            {
                anInt940 = 0;
                gameBuffer.putPacket(165);
                gameBuffer.put(0);
                int j = gameBuffer.offset;
                gameBuffer.put(139);
                gameBuffer.put(150);
                gameBuffer.putWord(32131);
                gameBuffer.put((int)(Math.random() * 256D));
                gameBuffer.putWord(3250);
                gameBuffer.put(177);
                gameBuffer.putWord(24859);
                gameBuffer.put(119);
                if((int)(Math.random() * 2D) == 0)
                    gameBuffer.putWord(47234);
                if((int)(Math.random() * 2D) == 0)
                    gameBuffer.put(21);
                gameBuffer.endVarByte(gameBuffer.offset - j, (byte)0);
            }
        }
    }

    public void parseClientVarps(boolean flag, int i) {
        int j = VarpFile.aClass41Array701[i].anInt709;
        if(j == 0)
            return;
        int k = configstates[i];
        if(flag)
            anInt961 = packetencryption.poll();
		/* Screen brighten */
        if(j == 1)
        {
            if(k == 1)
                TriangleRasterizer.method372(0.90000000000000002D, aByte1200);
            if(k == 2)
                TriangleRasterizer.method372(0.80000000000000004D, aByte1200);
            if(k == 3)
                TriangleRasterizer.method372(0.69999999999999996D, aByte1200);
            if(k == 4)
                TriangleRasterizer.method372(0.59999999999999998D, aByte1200);
            ItemDefinition.aClass12_158.clear();
            paintRequested = true;
        }
        if(j == 3)
        {
            boolean flag1 = aBoolean1151;
            if(k == 0)
            {
                volumeAdjustMidi((byte)0, aBoolean1151, 0);
                aBoolean1151 = true;
            }
            if(k == 1)
            {
                volumeAdjustMidi((byte)0, aBoolean1151, -400);
                aBoolean1151 = true;
            }
            if(k == 2)
            {
                volumeAdjustMidi((byte)0, aBoolean1151, -800);
                aBoolean1151 = true;
            }
            if(k == 3)
            {
                volumeAdjustMidi((byte)0, aBoolean1151, -1200);
                aBoolean1151 = true;
            }
            if(k == 4)
                aBoolean1151 = false;
            if(aBoolean1151 != flag1 && !lowMemory)
            {
                if(aBoolean1151)
                {
                    anInt1227 = anInt956;
                    aBoolean1228 = true;
                    ondemandhandler.requestPriority(2, anInt1227);
                } else
                {
                    stopMidi(860);
                }
                anInt1259 = 0;
            }
        }
        if(j == 4)
        {
            if(k == 0)
            {
                aBoolean848 = true;
                method111((byte)2, 0);
            }
            if(k == 1)
            {
                aBoolean848 = true;
                method111((byte)2, -400);
            }
            if(k == 2)
            {
                aBoolean848 = true;
                method111((byte)2, -800);
            }
            if(k == 3)
            {
                aBoolean848 = true;
                method111((byte)2, -1200);
            }
            if(k == 4)
                aBoolean848 = false;
        }
        if(j == 5)
            anInt1253 = k;
        if(j == 6)
            anInt1249 = k;
        if(j == 8)
        {
            anInt1195 = k;
            aBoolean1223 = true;
        }
        if(j == 9)
            anInt913 = k;
    }

    public void updateMobGraphics(int i)
    {
        anInt974 = 0;
        for(int j = -1; j < playerOffset + anInt836; j++)
        {
            Object obj;
            if(j == -1)
                obj = localPlayer;
            else
            if(j < playerOffset)
                obj = playerArray[addedPlayers[j]];
            else
                obj = npcs[localNpcIds[j - playerOffset]];
            if(obj == null || !((Mob) (obj)).hasDefinition())
                continue;
            if(obj instanceof Npc)
            {
                NpcDefinition class5 = ((Npc)obj).definition;
                if(class5.confignpcs != null)
                    class5 = class5.method161(anInt877);
                if(class5 == null)
                    continue;
            }
            if(j < playerOffset)
            {
                int l = 30;
                Player class30_sub2_sub4_sub1_sub2 = (Player)obj;
		/* Render isActive head icons */
                if(class30_sub2_sub4_sub1_sub2.headIcons != 0)
                {
                    calculateSpriteMobXY(true, ((Mob) (obj)), ((Mob) (obj)).anInt1507 + 15);
                    if(spriteX > -1)
                    {
                        for(int i2 = 0; i2 < 8; i2++)
                            if((class30_sub2_sub4_sub1_sub2.headIcons & 1 << i2) != 0)
                            {
                                headicons[i2].draw(spriteX - 12, 16083, spriteY - l);
                                l -= 25;
                            }
                    }
                }
		/* Render hint icon */
                if(j >= 0 && markertype == 10 && pmarker_id == addedPlayers[j])
                {
                    calculateSpriteMobXY(true, ((Mob) (obj)), ((Mob) (obj)).anInt1507 + 15);
                    if(spriteX > -1)
                        headicons[7].draw(spriteX - 12, 16083, spriteY - l);
                }
            } else
            {
                NpcDefinition class5_1 = ((Npc)obj).definition;
				/* Render Npc head icon */
                if(class5_1.npcheadicon >= 0 && class5_1.npcheadicon < headicons.length)
                {
                    calculateSpriteMobXY(true, ((Mob) (obj)), ((Mob) (obj)).anInt1507 + 15);
                    if(spriteX > -1)
                        headicons[class5_1.npcheadicon].draw(spriteX - 12, 16083, spriteY - 30);
                }
				/* Render hint icon */
                if(markertype == 1 && nmarker_id == localNpcIds[j - playerOffset] && loopCycle % 20 < 10)
                {
                    calculateSpriteMobXY(true, ((Mob) (obj)), ((Mob) (obj)).anInt1507 + 15);
                    if(spriteX > -1)
                        headicons[2].draw(spriteX - 12, 16083, spriteY - 28);
                }
            }
			/* Update chat */
            if(((Mob) (obj)).chat_txt != null && (j >= playerOffset || anInt1287 == 0 || anInt1287 == 3 || anInt1287 == 1 && onFriendsList(false, ((Player)obj).name)))
            {
                calculateSpriteMobXY(true, ((Mob) (obj)), ((Mob) (obj)).anInt1507);
                if(spriteX > -1 && anInt974 < anInt975)
                {
                    anIntArray979[anInt974] = b12Font.heightFontMetrics(((Mob) (obj)).chat_txt, true) / 2;
                    anIntArray978[anInt974] = b12Font.maxh;
                    anIntArray976[anInt974] = spriteX;
                    anIntArray977[anInt974] = spriteY;
                    anIntArray980[anInt974] = ((Mob) (obj)).anInt1513;
                    anIntArray981[anInt974] = ((Mob) (obj)).anInt1531;
                    anIntArray982[anInt974] = ((Mob) (obj)).anInt1535;
                    aStringArray983[anInt974++] = ((Mob) (obj)).chat_txt;
                    if(anInt1249 == 0 && ((Mob) (obj)).anInt1531 >= 1 && ((Mob) (obj)).anInt1531 <= 3)
                    {
                        anIntArray978[anInt974] += 10;
                        anIntArray977[anInt974] += 5;
                    }
                    if(anInt1249 == 0 && ((Mob) (obj)).anInt1531 == 4)
                        anIntArray979[anInt974] = 60;
                    if(anInt1249 == 0 && ((Mob) (obj)).anInt1531 == 5)
                        anIntArray978[anInt974] += 5;
                }
            }
			/* Update HPs? */
            if(((Mob) (obj)).anInt1532 > loopCycle)
            {
                calculateSpriteMobXY(true, ((Mob) (obj)), ((Mob) (obj)).anInt1507 + 15);
                if(spriteX > -1)
                {
                    int i1 = (((Mob) (obj)).anInt1533 * 30) / ((Mob) (obj)).anInt1534;
                    if(i1 > 30)
                        i1 = 30;
                    BasicRasterizer.drawQuad( spriteX - 15, spriteY - 3, i1, 5, 65280);
                    BasicRasterizer.drawQuad((spriteX - 15) + i1, spriteY - 3, 30 - i1, 5, 0xff0000);
                }
            }
			/* Draw hitmarks */
            for(int j1 = 0; j1 < 4; j1++)
                if(((Mob) (obj)).hitdelay_stack[j1] > loopCycle)
                {
                    calculateSpriteMobXY(true, ((Mob) (obj)), ((Mob) (obj)).anInt1507 / 2);
                    if(spriteX > -1)
                    {
                        if(j1 == 1)
                            spriteY -= 20;
                        if(j1 == 2)
                        {
                            spriteX -= 15;
                            spriteY -= 10;
                        }
                        if(j1 == 3)
                        {
                            spriteX += 15;
                            spriteY -= 10;
                        }
                        hitmarks[((Mob) (obj)).hittype_stack[j1]].draw(spriteX - 12, 16083, spriteY - 12);
						/* Shadow */
                        p11Font.drawCenteredYText(0, String.valueOf(((Mob) (obj)).hitamt_stack[j1]), 23693, spriteY + 4, spriteX);
						/* Amount text */
                        p11Font.drawCenteredYText(0xffffff, String.valueOf(((Mob) (obj)).hitamt_stack[j1]), 23693, spriteY + 3, spriteX - 1);
                    }
                }

        }

        if(i != 0)
            loadClient();
        for(int k = 0; k < anInt974; k++)
        {
            int k1 = anIntArray976[k];
            int l1 = anIntArray977[k];
            int j2 = anIntArray979[k];
            int k2 = anIntArray978[k];
            boolean flag = true;
            while(flag) 
            {
                flag = false;
                for(int l2 = 0; l2 < k; l2++)
                    if(l1 + 2 > anIntArray977[l2] - anIntArray978[l2] && l1 - k2 < anIntArray977[l2] + 2 && k1 - j2 < anIntArray976[l2] + anIntArray979[l2] && k1 + j2 > anIntArray976[l2] - anIntArray979[l2] && anIntArray977[l2] - anIntArray978[l2] < l1)
                    {
                        l1 = anIntArray977[l2] - anIntArray978[l2];
                        flag = true;
                    }

            }
            spriteX = anIntArray976[k];
            spriteY = anIntArray977[k] = l1;
            String s = aStringArray983[k];
            if(anInt1249 == 0)
            {
                int i3 = 0xffff00;
                if(anIntArray980[k] < 6)
                    i3 = anIntArray965[anIntArray980[k]];
                if(anIntArray980[k] == 6)
                    i3 = anInt1265 % 20 >= 10 ? 0xffff00 : 0xff0000;
                if(anIntArray980[k] == 7)
                    i3 = anInt1265 % 20 >= 10 ? 65535 : 255;
                if(anIntArray980[k] == 8)
                    i3 = anInt1265 % 20 >= 10 ? 0x80ff80 : 45056;
                if(anIntArray980[k] == 9)
                {
                    int j3 = 150 - anIntArray982[k];
                    if(j3 < 50)
                        i3 = 0xff0000 + 1280 * j3;
                    else
                    if(j3 < 100)
                        i3 = 0xffff00 - 0x50000 * (j3 - 50);
                    else
                    if(j3 < 150)
                        i3 = 65280 + 5 * (j3 - 100);
                }
                if(anIntArray980[k] == 10)
                {
                    int k3 = 150 - anIntArray982[k];
                    if(k3 < 50)
                        i3 = 0xff0000 + 5 * k3;
                    else
                    if(k3 < 100)
                        i3 = 0xff00ff - 0x50000 * (k3 - 50);
                    else
                    if(k3 < 150)
                        i3 = (255 + 0x50000 * (k3 - 100)) - 5 * (k3 - 100);
                }
                if(anIntArray980[k] == 11)
                {
                    int l3 = 150 - anIntArray982[k];
                    if(l3 < 50)
                        i3 = 0xffffff - 0x50005 * l3;
                    else
                    if(l3 < 100)
                        i3 = 65280 + 0x50005 * (l3 - 50);
                    else
                    if(l3 < 150)
                        i3 = 0xffffff - 0x50000 * (l3 - 100);
                }
                if(anIntArray981[k] == 0)
                {
                    b12Font.drawCenteredYText(0, s, 23693, spriteY + 1, spriteX);
                    b12Font.drawCenteredYText(i3, s, 23693, spriteY, spriteX);
                }
                if(anIntArray981[k] == 1)
                {
                    b12Font.drawWaveyText(0, true, s, spriteX, anInt1265, spriteY + 1);
                    b12Font.drawWaveyText(i3, true, s, spriteX, anInt1265, spriteY);
                }
                if(anIntArray981[k] == 2)
                {
                    b12Font.drawWaveyText2(spriteX, s, anInt1265, spriteY + 1, aByte1194, 0);
                    b12Font.drawWaveyText2(spriteX, s, anInt1265, spriteY, aByte1194, i3);
                }
                if(anIntArray981[k] == 3)
                {
                    b12Font.drawWaveyText3(150 - anIntArray982[k], s, true, anInt1265, spriteY + 1, spriteX, 0);
                    b12Font.drawWaveyText3(150 - anIntArray982[k], s, true, anInt1265, spriteY, spriteX, i3);
                }
                if(anIntArray981[k] == 4)
                {
                    int i4 = b12Font.heightFontMetrics(s, true);
                    int k4 = ((150 - anIntArray982[k]) * (i4 + 100)) / 150;
                    BasicRasterizer.setDimensions(spriteX + 50, spriteX - 50, 334, 0);
                    b12Font.drawText(0, s, spriteY + 1, 822, (spriteX + 50) - k4);
                    b12Font.drawText(i3, s, spriteY, 822, (spriteX + 50) - k4);
                    BasicRasterizer.reset();
                }
                if(anIntArray981[k] == 5)
                {
                    int j4 = 150 - anIntArray982[k];
                    int l4 = 0;
                    if(j4 < 25)
                        l4 = j4 - 25;
                    else
                    if(j4 > 125)
                        l4 = j4 - 125;
                    BasicRasterizer.setDimensions( 512, 0, spriteY + 5, spriteY - b12Font.maxh - 1);
                    b12Font.drawCenteredYText(0, s, 23693, spriteY + 1 + l4, spriteX);
                    b12Font.drawCenteredYText(i3, s, 23693, spriteY + l4, spriteX);
                    BasicRasterizer.reset();
                }
            } else
            {
                b12Font.drawCenteredYText(0, s, 23693, spriteY + 1, spriteX);
                b12Font.drawCenteredYText(0xffff00, s, 23693, spriteY, spriteX);
            }
        }

    }

    public void removeFriend(boolean flag, long l)
    {
        try
        {
            if(l == 0L)
                return;
            for(int i = 0; i < amt_friendhashes; i++)
            {
                if(friend_hashes[i] != l)
                    continue;
                amt_friendhashes--;
                aBoolean1153 = true;
                for(int j = i; j < amt_friendhashes; j++)
                {
                    friendusernames[j] = friendusernames[j + 1];
                    anIntArray826[j] = anIntArray826[j + 1];
                    friend_hashes[j] = friend_hashes[j + 1];
                }

                gameBuffer.putPacket(215);
                gameBuffer.putQword(l);
                break;
            }

            if(flag)
                return;
        }
        catch(RuntimeException runtimeexception)
        {
            Signlink.reportError("18622, " + flag + ", " + l + ", " + runtimeexception.toString());
            throw new RuntimeException();
        }
    }

    public void method36(byte byte0)
    {
        aClass15_1163.initialize(0);
        TriangleRasterizer.heightoffsets = anIntArray1181;
        if(byte0 != -81)
            return;
        invback.renderImage(0, 16083, 0);
        if(anInt1189 != -1)
            drawWidget(Widget.widgets[anInt1189],0, 0, 0);
        else
        if(tab_interfaces[current_tab] != -1)
            drawWidget(Widget.widgets[tab_interfaces[current_tab]],0, 0, 0);
        if(aBoolean885 && clickarea == 1)
            method40((byte)9);
        aClass15_1163.updateGraphics(205, 23680, super.appletGraphics, 553);
        toplefttext_imagefetcher.initialize(0);
        TriangleRasterizer.heightoffsets = anIntArray1182;
    }

    public void method37(int i, int j)
    {
        if(i <= 0)
            packetId = -1;
        if(!lowMemory)
        {
            if(TriangleRasterizer.unpackcounters[17] >= j)
            {
                IndexedColorSprite class30_sub2_sub1_sub2 = TriangleRasterizer.textures[17];
                int k = class30_sub2_sub1_sub2.indexWidth * class30_sub2_sub1_sub2.indexHeight - 1;
                int j1 = class30_sub2_sub1_sub2.indexWidth * anInt945 * 2;
                byte abyte0[] = class30_sub2_sub1_sub2.buffer;
                byte abyte3[] = aByteArray912;
                for(int i2 = 0; i2 <= k; i2++)
                    abyte3[i2] = abyte0[i2 - j1 & k];

                class30_sub2_sub1_sub2.buffer = abyte3;
                aByteArray912 = abyte0;
                TriangleRasterizer.pushTexture(17);
                anInt854++;
                if(anInt854 > 1235)
                {
                    anInt854 = 0;
                    gameBuffer.putPacket(226);
                    gameBuffer.put(0);
                    int l2 = gameBuffer.offset;
                    gameBuffer.putWord(58722);
                    gameBuffer.put(240);
                    gameBuffer.putWord((int)(Math.random() * 65536D));
                    gameBuffer.put((int)(Math.random() * 256D));
                    if((int)(Math.random() * 2D) == 0)
                        gameBuffer.putWord(51825);
                    gameBuffer.put((int)(Math.random() * 256D));
                    gameBuffer.putWord((int)(Math.random() * 65536D));
                    gameBuffer.putWord(7130);
                    gameBuffer.putWord((int)(Math.random() * 65536D));
                    gameBuffer.putWord(61657);
                    gameBuffer.endVarByte(gameBuffer.offset - l2, (byte)0);
                }
            }
            if(TriangleRasterizer.unpackcounters[24] >= j)
            {
                IndexedColorSprite class30_sub2_sub1_sub2_1 = TriangleRasterizer.textures[24];
                int l = class30_sub2_sub1_sub2_1.indexWidth * class30_sub2_sub1_sub2_1.indexHeight - 1;
                int k1 = class30_sub2_sub1_sub2_1.indexWidth * anInt945 * 2;
                byte abyte1[] = class30_sub2_sub1_sub2_1.buffer;
                byte abyte4[] = aByteArray912;
                for(int j2 = 0; j2 <= l; j2++)
                    abyte4[j2] = abyte1[j2 - k1 & l];

                class30_sub2_sub1_sub2_1.buffer = abyte4;
                aByteArray912 = abyte1;
                TriangleRasterizer.pushTexture(24);
            }
            if(TriangleRasterizer.unpackcounters[34] >= j)
            {
                IndexedColorSprite class30_sub2_sub1_sub2_2 = TriangleRasterizer.textures[34];
                int i1 = class30_sub2_sub1_sub2_2.indexWidth * class30_sub2_sub1_sub2_2.indexHeight - 1;
                int l1 = class30_sub2_sub1_sub2_2.indexWidth * anInt945 * 2;
                byte abyte2[] = class30_sub2_sub1_sub2_2.buffer;
                byte abyte5[] = aByteArray912;
                for(int k2 = 0; k2 <= i1; k2++)
                    abyte5[k2] = abyte2[k2 - l1 & i1];

                class30_sub2_sub1_sub2_2.buffer = abyte5;
                aByteArray912 = abyte2;
                TriangleRasterizer.pushTexture(34);
            }
        }
    }

    public void resetMobsChatText(byte byte0)
    {
        if(byte0 != -92)
            gameBuffer.put(214);
        for(int i = -1; i < playerOffset; i++)
        {
            int j;
            if(i == -1)
                j = localPlayerIndex;
            else
                j = addedPlayers[i];
            Player class30_sub2_sub4_sub1_sub2 = playerArray[j];
            if(class30_sub2_sub4_sub1_sub2 != null && ((Mob) (class30_sub2_sub4_sub1_sub2)).anInt1535 > 0)
            {
                class30_sub2_sub4_sub1_sub2.anInt1535--;
                if(((Mob) (class30_sub2_sub4_sub1_sub2)).anInt1535 == 0)
                    class30_sub2_sub4_sub1_sub2.chat_txt = null;
            }
        }

        for(int k = 0; k < anInt836; k++)
        {
            int l = localNpcIds[k];
            Npc class30_sub2_sub4_sub1_sub1 = npcs[l];
            if(class30_sub2_sub4_sub1_sub1 != null && ((Mob) (class30_sub2_sub4_sub1_sub1)).anInt1535 > 0)
            {
                class30_sub2_sub4_sub1_sub1.anInt1535--;
                if(((Mob) (class30_sub2_sub4_sub1_sub1)).anInt1535 == 0)
                    class30_sub2_sub4_sub1_sub1.chat_txt = null;
            }
        }

    }

    public void updateCameraPosition(byte junk)
    {
		/* Spinning camera calculations */
        int i = spincam_x * 128 + 64;
        int j = spincam_y * 128 + 64;
        int k = calculateTileHeight(i, j, currentZ) - spincam_z;
		/* Calculate moving right */
        if(camerax < i)
        {
            camerax += spincam_speed + ((i - camerax) * spincam_angle) / 1000;
            if(camerax > i)
                camerax = i;
        }
		/* Calculate moving left */
        if(camerax > i)
        {
            camerax -= spincam_speed + ((camerax - i) * spincam_angle) / 1000;
            if(camerax < i)
                camerax = i;
        }
		/* Calculate moving up */
        if(cameraz < k)
        {
            cameraz += spincam_speed + ((k - cameraz) * spincam_angle) / 1000;
            if(cameraz > k)
                cameraz = k;
        }
		/* Calculate moving down */
        if(cameraz > k)
        {
            cameraz -= spincam_speed + ((cameraz - k) * spincam_angle) / 1000;
            if(cameraz < k)
                cameraz = k;
        }
		/* Calculate moving forwards */
        if(cameray < j)
        {
            cameray += spincam_speed + ((j - cameray) * spincam_angle) / 1000;
            if(cameray > j)
                cameray = j;
        }
		/* Calculate moving backwards */
        if(cameray > j)
        {
            cameray -= spincam_speed + ((cameray - j) * spincam_angle) / 1000;
            if(cameray < j)
                cameray = j;
        }
        i = normalcam_x * 128 + 64;
        j = normalcam_y * 128 + 64;
        k = calculateTileHeight(i, j, currentZ) - normalcam_z;
        int l = i - camerax;
        int i1 = k - cameraz;
        int j1 = j - cameray;
        int k1 = (int)Math.sqrt(l * l + j1 * j1);
        int l1 = (int)(Math.atan2(i1, k1) * 325.94900000000001D) & 0x7ff;
        int i2 = (int)(Math.atan2(l, j1) * -325.94900000000001D) & 0x7ff;
        if(l1 < 128)
            l1 = 128;
        if(l1 > 383)
            l1 = 383;
        if(camerapitch$ < l1)
        {
            camerapitch$ += normalcam_speed + ((l1 - camerapitch$) * normalcam_angle) / 1000;
            if(camerapitch$ > l1)
                camerapitch$ = l1;
        }
        if(camerapitch$ > l1)
        {
            camerapitch$ -= normalcam_speed + ((camerapitch$ - l1) * normalcam_angle) / 1000;
            if(camerapitch$ < l1)
                camerapitch$ = l1;
        }
        int j2 = i2 - camerayaw$;
        if(j2 > 1024)
            j2 -= 2048;
        if(j2 < -1024)
            j2 += 2048;
        if(j2 > 0)
        {
            camerayaw$ += normalcam_speed + (j2 * normalcam_angle) / 1000;
            camerayaw$ &= 0x7ff;
        }
        if(j2 < 0)
        {
            camerayaw$ -= normalcam_speed + (-j2 * normalcam_angle) / 1000;
            camerayaw$ &= 0x7ff;
        }
        int k2 = i2 - camerayaw$;
        if(k2 > 1024)
            k2 -= 2048;
        if(k2 < -1024)
            k2 += 2048;
        if(k2 < 0 && j2 > 0 || k2 > 0 && j2 < 0)
            camerayaw$ = i2;
    }

    public void method40(byte byte0)
    {
        int i = anInt949;
        int j = anInt950;
        int k = anInt951;
        int l = anInt952;
        int i1 = 0x5d5447;
        BasicRasterizer.drawQuad(i, j, k, l, i1);
        if(byte0 == 9)
            byte0 = 0;
        else
            return;
        BasicRasterizer.drawQuad( i + 1, j + 1, k - 2, 16, 0);
        BasicRasterizer.drawQuadrilateralOutline(i + 1, j + 18, k - 2, l - 19, 0);
        b12Font.drawText(i1, "Choose Option", j + 14, 822, i + 3);
        int j1 = super.newMouseX;
        int k1 = super.newMouseY;
        if(clickarea == 0)
        {
            j1 -= 4;
            k1 -= 4;
        }
        if(clickarea == 1)
        {
            j1 -= 553;
            k1 -= 205;
        }
        if(clickarea == 2)
        {
            j1 -= 17;
            k1 -= 357;
        }
        for(int l1 = 0; l1 < anInt1133; l1++)
        {
            int i2 = j + 31 + (anInt1133 - 1 - l1) * 15;
            int j2 = 0xffffff;
            if(j1 > i && j1 < i + k && k1 > i2 - 13 && k1 < i2 + 3)
                j2 = 0xffff00;
            b12Font.drawText2(false, true, i + 3, j2, interfacestringstack[l1], i2);
        }

    }

    public void socialListLogic(byte byte0, long l)
    {
        try
        {
            if(l == 0L)
                return;
            if(amt_friendhashes >= 100 && isMembers != 1)
            {
                pushMessage("Your friendlist is full. Max of 100 for free users, and 200 for members", 0, "", aBoolean991);
                return;
            }
            if(amt_friendhashes >= 200)
            {
                pushMessage("Your friendlist is full. Max of 100 for free users, and 200 for members", 0, "", aBoolean991);
                return;
            }
            String s = TextTools.formatUsername(-45804, TextTools.longToString(l, (byte)-99));
            for(int i = 0; i < amt_friendhashes; i++)
                if(friend_hashes[i] == l)
                {
                    pushMessage(s + " is already on your friend list", 0, "", aBoolean991);
                    return;
                }

            if(byte0 != 68)
                packetId = -1;
            for(int j = 0; j < amt_ignorehashes; j++)
                if(ignore_hashes[j] == l)
                {
                    pushMessage("Please remove " + s + " from your ignore list first", 0, "", aBoolean991);
                    return;
                }

            if(s.equals(localPlayer.name))
            {
                return;
            } else
            {
                friendusernames[amt_friendhashes] = s;
                friend_hashes[amt_friendhashes] = l;
                anIntArray826[amt_friendhashes] = 0;
                amt_friendhashes++;
                aBoolean1153 = true;
                gameBuffer.putPacket(188);
                gameBuffer.putQword(l);
                return;
            }
        }
        catch(RuntimeException runtimeexception)
        {
            Signlink.reportError("15283, " + byte0 + ", " + l + ", " + runtimeexception.toString());
        }
        throw new RuntimeException();
    }

    public int calculateTileHeight(int finex, int finey, int z)
    {
        int x = finex >> 7;
        int y = finey >> 7;
        if(x < 0 || y < 0 || x > 103 || y > 103)
            return 0;
        int height = z;
        if(height < 3 && (tileFlags[1][x][y] & 2) == 2)
            height++;
        int finetilex = finex & 0x7f;
        int finetiley = finey & 0x7f;
        int i2 = tileHeightmap[height][x][y] * (128 - finetilex) + tileHeightmap[height][x + 1][y] * finetilex >> 7;
        int j2 = tileHeightmap[height][x][y + 1] * (128 - finetilex) + tileHeightmap[height][x + 1][y + 1] * finetilex >> 7;
        return i2 * (128 - finetiley) + j2 * finetiley >> 7;
    }

    public static String amountToAmountStr(int junk, int j)
    {
        if(j < 0x186a0)
            return String.valueOf(j);
        if(j < 0x989680)
            return j / 1000 + "K";
        else
            return j / 0xf4240 + "M";
    }

    public void killToMainscreen(boolean flag)
    {
        try
        {
            if(bufferedConnection != null)
                bufferedConnection.close();
        }
        catch(Exception _ex) { }
        bufferedConnection = null;
        if(!flag)
            return;
        isOnlineGame = false;
        titlescreen_tab = 0;
        username = "";
        password = "";
        clearModelCaches(false);
        pallet.reset();
        for(int i = 0; i < 4; i++)
            planeFlags[i].resetFlagBuffer();

        System.gc();
        stopMidi(860);
        anInt956 = -1;
        anInt1227 = -1;
        anInt1259 = 0;
    }

    public void method45(int i)
    {
        if(i != 0)
            packetId = -1;
        aBoolean1031 = true;
        for(int j = 0; j < 7; j++)
        {
            characterModelIds[j] = -1;
            for(int k = 0; k < IdentityKit.anInt655; k++)
            {
                if(IdentityKit.identityKits[k].aBoolean662 || IdentityKit.identityKits[k].anInt657 != j + (aBoolean1047 ? 0 : 7))
                    continue;
                characterModelIds[j] = k;
                break;
            }

        }

    }

    public void processNpcs(int i, ByteBuffer buffer0, byte byte0)
    {
        while(buffer0.bitOffset + 21 < i * 8) 
        {
            int k = buffer0.getBits(14);
            if(k == 16383)
                break;
            if(npcs[k] == null)
                npcs[k] = new Npc();
            Npc class30_sub2_sub4_sub1_sub1 = npcs[k];
            localNpcIds[anInt836++] = k;
            class30_sub2_sub4_sub1_sub1.anInt1537 = loopCycle;
            int l = buffer0.getBits(5);
            if(l > 15)
                l -= 32;
            int i1 = buffer0.getBits(5);
            if(i1 > 15)
                i1 -= 32;
            int j1 = buffer0.getBits(1);
            class30_sub2_sub4_sub1_sub1.definition = NpcDefinition.getNPCDefinition(buffer0.getBits(12));
            int k1 = buffer0.getBits(1);
            if(k1 == 1)
                pFlagUpdateList[amtplayerupdatestack++] = k;
            class30_sub2_sub4_sub1_sub1.halfOffsets = class30_sub2_sub4_sub1_sub1.definition.npc_halftileoffsets;
            class30_sub2_sub4_sub1_sub1.rotation = class30_sub2_sub4_sub1_sub1.definition.spawndirection;
            class30_sub2_sub4_sub1_sub1.walkAnimation = class30_sub2_sub4_sub1_sub1.definition.npcdef_walkanim;
            class30_sub2_sub4_sub1_sub1.turnAnimation180 = class30_sub2_sub4_sub1_sub1.definition.npcdef_turn180anim;
            class30_sub2_sub4_sub1_sub1.turnCwAnimation90 = class30_sub2_sub4_sub1_sub1.definition.npcdef_turn90cw;
            class30_sub2_sub4_sub1_sub1.turnCcwAnimation90 = class30_sub2_sub4_sub1_sub1.definition.npcdef_turn90ccw;
            class30_sub2_sub4_sub1_sub1.standAnimation = class30_sub2_sub4_sub1_sub1.definition.npcdef_standanim;
            class30_sub2_sub4_sub1_sub1.updateMobPosition(((Mob) (localPlayer)).xList[0] + i1, ((Mob) (localPlayer)).yList[0] + l, j1 == 1);
        }
        buffer0.endBitAccess();
    }

    public void handleLoopCycle() {
        if(aBoolean1252 || aBoolean926 || aBoolean1176)
            return;
        loopCycle++;
        if(!isOnlineGame)
            loopCycleTitle(true);
        else
            loopCycleGame(anInt1218);
        loadFinalizedRequest(false);
    }

    public void processPlayers(int i, boolean flag)
    {
        if(((Mob) (localPlayer)).fineX >> 7 == anInt1261 && ((Mob) (localPlayer)).fineY >> 7 == anInt1262)
            anInt1261 = 0;
        int j = playerOffset;
        if(i != 0)
        {
            for(int k = 1; k > 0; k++);
        }
        if(flag)
            j = 1;
        for(int l = 0; l < j; l++)
        {
            Player class30_sub2_sub4_sub1_sub2;
            int i1;
            if(flag)
            {
                class30_sub2_sub4_sub1_sub2 = localPlayer;
                i1 = localPlayerIndex << 14;
            } else
            {
                class30_sub2_sub4_sub1_sub2 = playerArray[addedPlayers[l]];
                i1 = addedPlayers[l] << 14;
            }
            if(class30_sub2_sub4_sub1_sub2 == null || !class30_sub2_sub4_sub1_sub2.hasDefinition())
                continue;
            class30_sub2_sub4_sub1_sub2.aBoolean1699 = false;
            if((lowMemory && playerOffset > 50 || playerOffset > 200) && !flag && ((Mob) (class30_sub2_sub4_sub1_sub2)).anInt1517 == ((Mob) (class30_sub2_sub4_sub1_sub2)).standAnimation)
                class30_sub2_sub4_sub1_sub2.aBoolean1699 = true;
            int j1 = ((Mob) (class30_sub2_sub4_sub1_sub2)).fineX >> 7;
            int k1 = ((Mob) (class30_sub2_sub4_sub1_sub2)).fineY >> 7;
            if(j1 < 0 || j1 >= 104 || k1 < 0 || k1 >= 104)
                continue;
            if(class30_sub2_sub4_sub1_sub2.aActor_Sub6_1714 != null && loopCycle >= class30_sub2_sub4_sub1_sub2.anInt1707 && loopCycle < class30_sub2_sub4_sub1_sub2.anInt1708)
            {
                class30_sub2_sub4_sub1_sub2.aBoolean1699 = false;
                class30_sub2_sub4_sub1_sub2.tileheight$ = calculateTileHeight( ((Mob) (class30_sub2_sub4_sub1_sub2)).fineX, ((Mob) (class30_sub2_sub4_sub1_sub2)).fineY, currentZ);
                pallet.method286(60, currentZ, ((Mob) (class30_sub2_sub4_sub1_sub2)).fineY, class30_sub2_sub4_sub1_sub2, ((Mob) (class30_sub2_sub4_sub1_sub2)).anInt1552, class30_sub2_sub4_sub1_sub2.anInt1722, ((Mob) (class30_sub2_sub4_sub1_sub2)).fineX, class30_sub2_sub4_sub1_sub2.tileheight$, class30_sub2_sub4_sub1_sub2.anInt1719, class30_sub2_sub4_sub1_sub2.anInt1721, i1, class30_sub2_sub4_sub1_sub2.anInt1720, (byte)35);
                continue;
            }
            if((((Mob) (class30_sub2_sub4_sub1_sub2)).fineX & 0x7f) == 64 && (((Mob) (class30_sub2_sub4_sub1_sub2)).fineY & 0x7f) == 64)
            {
                if(anIntArrayArray929[j1][k1] == anInt1265)
                    continue;
                anIntArrayArray929[j1][k1] = anInt1265;
            }
            class30_sub2_sub4_sub1_sub2.tileheight$ = calculateTileHeight( ((Mob) (class30_sub2_sub4_sub1_sub2)).fineX, ((Mob) (class30_sub2_sub4_sub1_sub2)).fineY, currentZ);
            pallet.method285(currentZ, ((Mob) (class30_sub2_sub4_sub1_sub2)).anInt1552, (byte)6, class30_sub2_sub4_sub1_sub2.tileheight$, i1, ((Mob) (class30_sub2_sub4_sub1_sub2)).fineY, 60, ((Mob) (class30_sub2_sub4_sub1_sub2)).fineX, class30_sub2_sub4_sub1_sub2, ((Mob) (class30_sub2_sub4_sub1_sub2)).aBoolean1541);
        }

    }

    public boolean method48(int i, Widget class9)
    {
        if(i <= 0)
            packetId = -1;
        int j = class9.actionCode;
        if(anInt900 == 2)
        {
            if(j == 201)
            {
                aBoolean1223 = true;
                anInt1225 = 0;
                aBoolean1256 = true;
                aString1212 = "";
                anInt1064 = 1;
                aString1121 = "Enter name of friend to add to list";
            }
            if(j == 202)
            {
                aBoolean1223 = true;
                anInt1225 = 0;
                aBoolean1256 = true;
                aString1212 = "";
                anInt1064 = 2;
                aString1121 = "Enter name of friend to delete from list";
            }
        }
        if(j == 205)
        {
            anInt1011 = 250;
            return true;
        }
        if(j == 501)
        {
            aBoolean1223 = true;
            anInt1225 = 0;
            aBoolean1256 = true;
            aString1212 = "";
            anInt1064 = 4;
            aString1121 = "Enter name of player to add to list";
        }
        if(j == 502)
        {
            aBoolean1223 = true;
            anInt1225 = 0;
            aBoolean1256 = true;
            aString1212 = "";
            anInt1064 = 5;
            aString1121 = "Enter name of player to delete from list";
        }
        if(j >= 300 && j <= 313)
        {
            int k = (j - 300) / 2;
            int j1 = j & 1;
            int i2 = characterModelIds[k];
            if(i2 != -1)
            {
                do
                {
                    if(j1 == 0 && --i2 < 0)
                        i2 = IdentityKit.anInt655 - 1;
                    if(j1 == 1 && ++i2 >= IdentityKit.anInt655)
                        i2 = 0;
                } while(IdentityKit.identityKits[i2].aBoolean662 || IdentityKit.identityKits[i2].anInt657 != k + (aBoolean1047 ? 0 : 7));
                characterModelIds[k] = i2;
                aBoolean1031 = true;
            }
        }
        if(j >= 314 && j <= 323)
        {
            int l = (j - 314) / 2;
            int k1 = j & 1;
            int j2 = characterColorIds[l];
            if(k1 == 0 && --j2 < 0)
                j2 = anIntArrayArray1003[l].length - 1;
            if(k1 == 1 && ++j2 >= anIntArrayArray1003[l].length)
                j2 = 0;
            characterColorIds[l] = j2;
            aBoolean1031 = true;
        }
        if(j == 324 && !aBoolean1047)
        {
            aBoolean1047 = true;
            method45(0);
        }
        if(j == 325 && aBoolean1047)
        {
            aBoolean1047 = false;
            method45(0);
        }
        if(j == 326)
        {
            gameBuffer.putPacket(101);
            gameBuffer.put(aBoolean1047 ? 0 : 1);
            for(int i1 = 0; i1 < 7; i1++)
                gameBuffer.put(characterModelIds[i1]);

            for(int l1 = 0; l1 < 5; l1++)
                gameBuffer.put(characterColorIds[l1]);

            return true;
        }
        if(j == 613)
            aBoolean1158 = !aBoolean1158;
        if(j >= 601 && j <= 612)
        {
            method147(537);
            if(aString881.length() > 0)
            {
                gameBuffer.putPacket(218);
                gameBuffer.putQword(TextTools.stringToLong(aString881));
                gameBuffer.put(j - 601);
                gameBuffer.put(aBoolean1158 ? 1 : 0);
            }
        }
        return false;
    }

    public void handlePlayerUpdateFlags(ByteBuffer buffer)
    {
        for(int j = 0; j < amtplayerupdatestack; j++)
        {
            int id = pFlagUpdateList[j];
            Player player = playerArray[id];
            int flag = buffer.getUbyte();
            if((flag & 0x40) != 0)
                flag += buffer.getUbyte() << 8;
            System.out.println("Flag: " + flag);
            System.out.println("PID: "+ id);
            parsePlayerUpdateFlags(flag, id, buffer, player);
        }

    }

    public void method50(int i, int j, int k, int l, int i1, int j1)
    {
        int k1 = pallet.method300(j1, l, i);
        if(j >= 0)
            return;
        if(k1 != 0)
        {
            int l1 = pallet.method304(j1, l, i, k1);
            int k2 = l1 >> 6 & 3;
            int i3 = l1 & 0x1f;
            int k3 = k;
            if(k1 > 0)
                k3 = i1;
            int ai[] = aClass30_Sub2_Sub1_Sub1_1263.buffer;
            int k4 = 24624 + l * 4 + (103 - i) * 512 * 4;
            int i5 = k1 >> 14 & 0x7fff;
            ObjectDefinition class46_2 = ObjectDefinition.getObjectDefinition(i5);
            if(class46_2.anInt758 != -1)
            {
                IndexedColorSprite class30_sub2_sub1_sub2_2 = mapscene[class46_2.anInt758];
                if(class30_sub2_sub1_sub2_2 != null)
                {
                    int i6 = (class46_2.anInt744 * 4 - class30_sub2_sub1_sub2_2.indexWidth) / 2;
                    int j6 = (class46_2.size * 4 - class30_sub2_sub1_sub2_2.indexHeight) / 2;
                    class30_sub2_sub1_sub2_2.renderImage(48 + l * 4 + i6, 16083, 48 + (104 - i - class46_2.size) * 4 + j6);
                }
            } else
            {
                if(i3 == 0 || i3 == 2)
                    if(k2 == 0)
                    {
                        ai[k4] = k3;
                        ai[k4 + 512] = k3;
                        ai[k4 + 1024] = k3;
                        ai[k4 + 1536] = k3;
                    } else
                    if(k2 == 1)
                    {
                        ai[k4] = k3;
                        ai[k4 + 1] = k3;
                        ai[k4 + 2] = k3;
                        ai[k4 + 3] = k3;
                    } else
                    if(k2 == 2)
                    {
                        ai[k4 + 3] = k3;
                        ai[k4 + 3 + 512] = k3;
                        ai[k4 + 3 + 1024] = k3;
                        ai[k4 + 3 + 1536] = k3;
                    } else
                    if(k2 == 3)
                    {
                        ai[k4 + 1536] = k3;
                        ai[k4 + 1536 + 1] = k3;
                        ai[k4 + 1536 + 2] = k3;
                        ai[k4 + 1536 + 3] = k3;
                    }
                if(i3 == 3)
                    if(k2 == 0)
                        ai[k4] = k3;
                    else
                    if(k2 == 1)
                        ai[k4 + 3] = k3;
                    else
                    if(k2 == 2)
                        ai[k4 + 3 + 1536] = k3;
                    else
                    if(k2 == 3)
                        ai[k4 + 1536] = k3;
                if(i3 == 2)
                    if(k2 == 3)
                    {
                        ai[k4] = k3;
                        ai[k4 + 512] = k3;
                        ai[k4 + 1024] = k3;
                        ai[k4 + 1536] = k3;
                    } else
                    if(k2 == 0)
                    {
                        ai[k4] = k3;
                        ai[k4 + 1] = k3;
                        ai[k4 + 2] = k3;
                        ai[k4 + 3] = k3;
                    } else
                    if(k2 == 1)
                    {
                        ai[k4 + 3] = k3;
                        ai[k4 + 3 + 512] = k3;
                        ai[k4 + 3 + 1024] = k3;
                        ai[k4 + 3 + 1536] = k3;
                    } else
                    if(k2 == 2)
                    {
                        ai[k4 + 1536] = k3;
                        ai[k4 + 1536 + 1] = k3;
                        ai[k4 + 1536 + 2] = k3;
                        ai[k4 + 1536 + 3] = k3;
                    }
            }
        }
        k1 = pallet.method302(j1, l, i);
        if(k1 != 0)
        {
            int i2 = pallet.method304(j1, l, i, k1);
            int l2 = i2 >> 6 & 3;
            int j3 = i2 & 0x1f;
            int l3 = k1 >> 14 & 0x7fff;
            ObjectDefinition class46_1 = ObjectDefinition.getObjectDefinition(l3);
            if(class46_1.anInt758 != -1)
            {
                IndexedColorSprite class30_sub2_sub1_sub2_1 = mapscene[class46_1.anInt758];
                if(class30_sub2_sub1_sub2_1 != null)
                {
                    int j5 = (class46_1.anInt744 * 4 - class30_sub2_sub1_sub2_1.indexWidth) / 2;
                    int k5 = (class46_1.size * 4 - class30_sub2_sub1_sub2_1.indexHeight) / 2;
                    class30_sub2_sub1_sub2_1.renderImage(48 + l * 4 + j5, 16083, 48 + (104 - i - class46_1.size) * 4 + k5);
                }
            } else
            if(j3 == 9)
            {
                int l4 = 0xeeeeee;
                if(k1 > 0)
                    l4 = 0xee0000;
                int ai1[] = aClass30_Sub2_Sub1_Sub1_1263.buffer;
                int l5 = 24624 + l * 4 + (103 - i) * 512 * 4;
                if(l2 == 0 || l2 == 2)
                {
                    ai1[l5 + 1536] = l4;
                    ai1[l5 + 1024 + 1] = l4;
                    ai1[l5 + 512 + 2] = l4;
                    ai1[l5 + 3] = l4;
                } else
                {
                    ai1[l5] = l4;
                    ai1[l5 + 512 + 1] = l4;
                    ai1[l5 + 1024 + 2] = l4;
                    ai1[l5 + 1536 + 3] = l4;
                }
            }
        }
        k1 = pallet.method303(j1, l, i);
        if(k1 != 0)
        {
            int j2 = k1 >> 14 & 0x7fff;
            ObjectDefinition class46 = ObjectDefinition.getObjectDefinition(j2);
            if(class46.anInt758 != -1)
            {
                IndexedColorSprite class30_sub2_sub1_sub2 = mapscene[class46.anInt758];
                if(class30_sub2_sub1_sub2 != null)
                {
                    int i4 = (class46.anInt744 * 4 - class30_sub2_sub1_sub2.indexWidth) / 2;
                    int j4 = (class46.size * 4 - class30_sub2_sub1_sub2.indexHeight) / 2;
                    class30_sub2_sub1_sub2.renderImage(48 + l * 4 + i4, 16083, 48 + (104 - i - class46.size) * 4 + j4);
                }
            }
        }
    }

    public void method51(int i)
    {
        aClass30_Sub2_Sub1_Sub2_966 = new IndexedColorSprite(titlescreen_archive, "titlebox", 0);
        if(i <= 0)
            aBoolean1231 = !aBoolean1231;
        aClass30_Sub2_Sub1_Sub2_967 = new IndexedColorSprite(titlescreen_archive, "titlebutton", 0);
        titlescreen_sprites = new IndexedColorSprite[12];
        int j = 0;
        try
        {
            j = Integer.parseInt(getParameter("fl_icon"));
        }
        catch(Exception _ex) { }
        if(j == 0)
        {
            for(int k = 0; k < 12; k++)
                titlescreen_sprites[k] = new IndexedColorSprite(titlescreen_archive, "runes", k);

        } else
        {
            for(int l = 0; l < 12; l++)
                titlescreen_sprites[l] = new IndexedColorSprite(titlescreen_archive, "runes", 12 + (l & 3));

        }
        aClass30_Sub2_Sub1_Sub1_1201 = new DirectColorSprite(128, 265);
        aClass30_Sub2_Sub1_Sub1_1202 = new DirectColorSprite(128, 265);
        for(int i1 = 0; i1 < 33920; i1++)
            aClass30_Sub2_Sub1_Sub1_1201.buffer[i1] = titletopleft_imagefetcher.pixelBuffer[i1];

        for(int j1 = 0; j1 < 33920; j1++)
            aClass30_Sub2_Sub1_Sub1_1202.buffer[j1] = titletopright_imagefetcher.pixelBuffer[j1];

        anIntArray851 = new int[256];
        for(int k1 = 0; k1 < 64; k1++)
            anIntArray851[k1] = k1 * 0x40000;

        for(int l1 = 0; l1 < 64; l1++)
            anIntArray851[l1 + 64] = 0xff0000 + 1024 * l1;

        for(int i2 = 0; i2 < 64; i2++)
            anIntArray851[i2 + 128] = 0xffff00 + 4 * i2;

        for(int j2 = 0; j2 < 64; j2++)
            anIntArray851[j2 + 192] = 0xffffff;

        anIntArray852 = new int[256];
        for(int k2 = 0; k2 < 64; k2++)
            anIntArray852[k2] = k2 * 1024;

        for(int l2 = 0; l2 < 64; l2++)
            anIntArray852[l2 + 64] = 65280 + 4 * l2;

        for(int i3 = 0; i3 < 64; i3++)
            anIntArray852[i3 + 128] = 65535 + 0x40000 * i3;

        for(int j3 = 0; j3 < 64; j3++)
            anIntArray852[j3 + 192] = 0xffffff;

        anIntArray853 = new int[256];
        for(int k3 = 0; k3 < 64; k3++)
            anIntArray853[k3] = k3 * 4;

        for(int l3 = 0; l3 < 64; l3++)
            anIntArray853[l3 + 64] = 255 + 0x40000 * l3;

        for(int i4 = 0; i4 < 64; i4++)
            anIntArray853[i4 + 128] = 0xff00ff + 1024 * i4;

        for(int j4 = 0; j4 < 64; j4++)
            anIntArray853[j4 + 192] = 0xffffff;

        anIntArray850 = new int[256];
        anIntArray1190 = new int[32768];
        anIntArray1191 = new int[32768];
        method106(null, -135);
        anIntArray828 = new int[32768];
        anIntArray829 = new int[32768];
        drawLoadingBar("Connecting to fileserver", 10);
        if(!runflamecycle)
        {
            runclient = true;
            runflamecycle = true;
            createThread(this, 2);
        }
    }

    public static void setHighMem(boolean junk) {
        Palette.lowmemory = false;
        TriangleRasterizer.lowmemory = false;
        lowMemory = false;
        LandscapeLoader.lowmemory = false;
        ObjectDefinition.lowmemory = false;
    }

    public static void main(String args[]) {
        try {
            System.out.println("Refactored by Sini - Visit RuneTekk.com for more RSPS tools!");
            if(args.length != 6) {
                System.out.println("Usage: node-id, port-offset, [lowmem/highmem], [free/members], storeid, disabled jaggrab");
                return;
            }
            nodeid = Integer.parseInt(args[0]);
            portOffset = Integer.parseInt(args[1]);
            if(args[2].equals("lowmem"))
                initializeLowMemory();
            else if(args[2].equals("highmem")) {
                setHighMem(false);
            } else {
                System.out.println("Usage: node-id, port-offset, [lowmem/highmem], [free/members], storeid");
                return;
            }
            if(args[3].equals("free"))
                members = false;
            else if(args[3].equals("members")) {
                members = true;
            } else {
                System.out.println("Usage: node-id, port-offset, [lowmem/highmem], [free/members], storeid");
                return;
            }
            Signlink.storeId = Integer.parseInt(args[4]);
            JAGGRAB_DISABLED = Boolean.parseBoolean(args[5]);
            Signlink.initialize(InetAddress.getLocalHost());
            Main client = new Main();
            client.initializeFrame(503, 765);
            return;
        }
        catch(Exception exception)
        {
            return;
        }
    }

    public void updateLandscape(int i)
    {
        if(i != -48877)
            return;
        if(lowMemory && landscape_stage == 2 && LandscapeLoader.ml_hieght != currentZ)
        {
            toplefttext_imagefetcher.initialize(0);
            p12Font.drawCenteredYText(0, "Loading - please wait.", 23693, 151, 257);
            p12Font.drawCenteredYText(0xffffff, "Loading - please wait.", 23693, 150, 256);
            toplefttext_imagefetcher.updateGraphics(4, 23680, super.appletGraphics, 4);
            landscape_stage = 1;
            timestamp = System.currentTimeMillis();
        }
        if(landscape_stage == 1)
        {
            int j = method54();
            if(j != 0 && System.currentTimeMillis() - timestamp > 0x57e40L)
            {
                Signlink.reportError(username + " glcfb " + ssk + "," + j + "," + lowMemory + "," + fileIndexes[0] + "," + ondemandhandler.amount() + "," + currentZ + "," + chunkx_ + "," + chunky_);
                timestamp = System.currentTimeMillis();
            }
        }
        if(landscape_stage == 2 && currentZ != anInt985)
        {
            anInt985 = currentZ;
            method24(true, currentZ);
        }
    }

    public int method54()
    {
        for(int i = 0; i < tileSrcs.length; i++)
        {
            if(tileSrcs[i] == null && anIntArray1235[i] != -1)
                return -1;
            if(regionSrcs[i] == null && anIntArray1236[i] != -1)
                return -2;
        }

        boolean flag = true;
        for(int j = 0; j < tileSrcs.length; j++)
        {
            byte abyte0[] = regionSrcs[j];
            if(abyte0 != null)
            {
                int k = (regionHashes[j] >> 8) * 64 - paletteX;
                int l = (regionHashes[j] & 0xff) * 64 - paletteY;
                if(aBoolean1159)
                {
                    k = 10;
                    l = 10;
                }
                flag &= LandscapeLoader.method189(k, abyte0, l, 6);
            }
        }

        if(!flag)
            return -3;
        if(aBoolean1080)
        {
            return -4;
        } else
        {
            landscape_stage = 2;
            LandscapeLoader.ml_hieght = currentZ;
            processLandscapeLoading_(true);
            gameBuffer.putPacket(121);
            return 0;
        }
    }

    public void processProjectiles(int i)
    {
        while(i >= 0) 
            loadClient();
        for(Projectile class30_sub2_sub4_sub4 = (Projectile)aClass19_1013.getFirst(); class30_sub2_sub4_sub4 != null; class30_sub2_sub4_sub4 = (Projectile)aClass19_1013.getNextFront())
            if(class30_sub2_sub4_sub4.anInt1597 != currentZ || loopCycle > class30_sub2_sub4_sub4.anInt1572)
                class30_sub2_sub4_sub4.removeDeque();
            else
            if(loopCycle >= class30_sub2_sub4_sub4.anInt1571)
            {
                if(class30_sub2_sub4_sub4.anInt1590 > 0)
                {
                    Npc class30_sub2_sub4_sub1_sub1 = npcs[class30_sub2_sub4_sub4.anInt1590 - 1];
                    if(class30_sub2_sub4_sub1_sub1 != null && ((Mob) (class30_sub2_sub4_sub1_sub1)).fineX >= 0 && ((Mob) (class30_sub2_sub4_sub1_sub1)).fineX < 13312 && ((Mob) (class30_sub2_sub4_sub1_sub1)).fineY >= 0 && ((Mob) (class30_sub2_sub4_sub1_sub1)).fineY < 13312)
                        class30_sub2_sub4_sub4.method455(loopCycle, ((Mob) (class30_sub2_sub4_sub1_sub1)).fineY, calculateTileHeight(((Mob) (class30_sub2_sub4_sub1_sub1)).fineX, ((Mob) (class30_sub2_sub4_sub1_sub1)).fineY, class30_sub2_sub4_sub4.anInt1597) - class30_sub2_sub4_sub4.anInt1583, ((Mob) (class30_sub2_sub4_sub1_sub1)).fineX, (byte)-83);
                }
                if(class30_sub2_sub4_sub4.anInt1590 < 0)
                {
                    int j = -class30_sub2_sub4_sub4.anInt1590 - 1;
                    Player class30_sub2_sub4_sub1_sub2;
                    if(j == localPlayerId)
                        class30_sub2_sub4_sub1_sub2 = localPlayer;
                    else
                        class30_sub2_sub4_sub1_sub2 = playerArray[j];
                    if(class30_sub2_sub4_sub1_sub2 != null && ((Mob) (class30_sub2_sub4_sub1_sub2)).fineX >= 0 && ((Mob) (class30_sub2_sub4_sub1_sub2)).fineX < 13312 && ((Mob) (class30_sub2_sub4_sub1_sub2)).fineY >= 0 && ((Mob) (class30_sub2_sub4_sub1_sub2)).fineY < 13312)
                        class30_sub2_sub4_sub4.method455(loopCycle, ((Mob) (class30_sub2_sub4_sub1_sub2)).fineY, calculateTileHeight(((Mob) (class30_sub2_sub4_sub1_sub2)).fineX, ((Mob) (class30_sub2_sub4_sub1_sub2)).fineY, class30_sub2_sub4_sub4.anInt1597) - class30_sub2_sub4_sub4.anInt1583, ((Mob) (class30_sub2_sub4_sub1_sub2)).fineX, (byte)-83);
                }
                class30_sub2_sub4_sub4.method456(anInt945, anInt1020);
                pallet.method285(currentZ, class30_sub2_sub4_sub4.anInt1595, (byte)6, (int)class30_sub2_sub4_sub4.aDouble1587, -1, (int)class30_sub2_sub4_sub4.aDouble1586, 60, (int)class30_sub2_sub4_sub4.aDouble1585, class30_sub2_sub4_sub4, false);
            }

    }

    public AppletContext getAppletContext()
    {
        if(Signlink.applet != null)
            return Signlink.applet.getAppletContext();
        else
            return super.getAppletContext();
    }

    public void drawTitleBackround(int i)
    {
        byte abyte0[] = titlescreen_archive.getArchive("title.dat", null);
        DirectColorSprite sprite = new DirectColorSprite(this, abyte0);
        titletopleft_imagefetcher.initialize(0);
        sprite.draw(0, 0);
        titletopright_imagefetcher.initialize(0);
        sprite.draw(-637, 0);
        logo_imagefetcher.initialize(0);
        sprite.draw(-128, 0);
        bottomleftmid_imagefetcher.initialize(0);
        sprite.draw(-202, -371);
        aClass15_1109.initialize(0);
        sprite.draw(-202, -171);
        aClass15_1112.initialize(0);
        sprite.draw(0, -265);
        aClass15_1113.initialize(0);
        sprite.draw(-562, -265);
        aClass15_1114.initialize(0);
        sprite.draw(-128, -171);
        aClass15_1115.initialize(0);
        sprite.draw(-562, -171);
		/* Do other half */
        int ai[] = new int[sprite.indexWidth];
        for(int j = 0; j < sprite.indexHeight; j++)
        {
            for(int k = 0; k < sprite.indexWidth; k++)
                ai[k] = sprite.buffer[(sprite.indexWidth - k - 1) + sprite.indexWidth * j];

            for(int l = 0; l < sprite.indexWidth; l++)
                sprite.buffer[l + sprite.indexWidth * j] = ai[l];
        }
        titletopleft_imagefetcher.initialize(0);
        sprite.draw(382, 0);
        titletopright_imagefetcher.initialize(0);
        sprite.draw(-255, 0);
        logo_imagefetcher.initialize(0);
        sprite.draw(254, 0);
        bottomleftmid_imagefetcher.initialize(0);
        sprite.draw(180, -371);
        aClass15_1109.initialize(0);
        sprite.draw(180, -171);
        aClass15_1112.initialize(0);
        sprite.draw(382, -265);
        aClass15_1113.initialize(0);
        sprite.draw(-180, -265);
        aClass15_1114.initialize(0);
        sprite.draw(254, -171);
        aClass15_1115.initialize(0);
        sprite.draw(-180, -171);
        sprite = new DirectColorSprite(titlescreen_archive, "logo", 0);
        logo_imagefetcher.initialize(0);
        sprite.draw(382 - sprite.indexWidth / 2 - 128, 16083, 18);
        sprite = null;
        Object obj = null;
        Object obj1 = null;
        System.gc();
    }

    public void loadFinalizedRequest(boolean flag)
    {
        if(flag)
            anInt883 = -72;
        do
        {
            OndemandRequest class30_sub2_sub3;
            do
            {
                class30_sub2_sub3 = ondemandhandler.getFinishedRequest();
                if(class30_sub2_sub3 == null)
                    return;
                if(class30_sub2_sub3.indexId == 0)
                {
                    Model.unpackRawModel(class30_sub2_sub3.archiveBuffer, -4036, class30_sub2_sub3.archiveId);
                    if((ondemandhandler.getModelSettingFlag(class30_sub2_sub3.archiveId, -203) & 0x62) != 0)
                    {
                        aBoolean1153 = true;
                        if(anInt1276 != -1)
                            aBoolean1223 = true;
                    }
                }
                if(class30_sub2_sub3.indexId == 1 && class30_sub2_sub3.archiveBuffer != null)
                    AnimFrame.unpackAnimFrames(class30_sub2_sub3.archiveBuffer);
                if(class30_sub2_sub3.indexId == 2 && class30_sub2_sub3.archiveId == anInt1227 && class30_sub2_sub3.archiveBuffer != null)
                    initMidi(aBoolean1228, 0, class30_sub2_sub3.archiveBuffer);
                if(class30_sub2_sub3.indexId == 3 && landscape_stage == 1)
                {
                    for(int i = 0; i < tileSrcs.length; i++)
                    {
                        if(anIntArray1235[i] == class30_sub2_sub3.archiveId)
                        {
                            tileSrcs[i] = class30_sub2_sub3.archiveBuffer;
                            if(class30_sub2_sub3.archiveBuffer == null)
                                anIntArray1235[i] = -1;
                            break;
                        }
                        if(anIntArray1236[i] != class30_sub2_sub3.archiveId)
                            continue;
                        regionSrcs[i] = class30_sub2_sub3.archiveBuffer;
                        if(class30_sub2_sub3.archiveBuffer == null)
                            anIntArray1236[i] = -1;
                        break;
                    }

                }
            } while(class30_sub2_sub3.indexId != 93 || !ondemandhandler.method564(class30_sub2_sub3.archiveId));
            LandscapeLoader.loadExtraObjects((byte)-107, new ByteBuffer(class30_sub2_sub3.archiveBuffer), ondemandhandler);
        } while(true);
    }

    public void method58(int i)
    {
        char c = '\u0100';
        for(int j = 10; j < 117; j++)
        {
            int k = (int)(Math.random() * 100D);
            if(k < 50)
                anIntArray828[j + (c - 2 << 7)] = 255; //White
        }

        if(i != 25106)
            loadClient();
        for(int l = 0; l < 100; l++)
        {
            int i1 = (int)(Math.random() * 124D) + 2;
            int k1 = (int)(Math.random() * 128D) + 128;
            int k2 = i1 + (k1 << 7);
            anIntArray828[k2] = 192;
        }

        for(int j1 = 1; j1 < c - 1; j1++)
        {
            for(int l1 = 1; l1 < 127; l1++)
            {
                int l2 = l1 + (j1 << 7);
                anIntArray829[l2] = (anIntArray828[l2 - 1] + anIntArray828[l2 + 1] + anIntArray828[l2 - 128] + anIntArray828[l2 + 128]) / 4;
            }

        }

        anInt1275 += 128;
        if(anInt1275 > anIntArray1190.length)
        {
            anInt1275 -= anIntArray1190.length;
            int i2 = (int)(Math.random() * 12D);
            method106(titlescreen_sprites[i2], -135);
        }
        for(int j2 = 1; j2 < c - 1; j2++)
        {
            for(int i3 = 1; i3 < 127; i3++)
            {
                int k3 = i3 + (j2 << 7);
                int i4 = anIntArray829[k3 + 128] - anIntArray1190[k3 + anInt1275 & anIntArray1190.length - 1] / 5;
                if(i4 < 0)
                    i4 = 0;
                anIntArray828[k3] = i4;
            }

        }

        for(int j3 = 0; j3 < c - 1; j3++)
            anIntArray969[j3] = anIntArray969[j3 + 1];
		/* Side ways */
        anIntArray969[c - 1] = (int)(Math.sin((double)loopCycle / 14D) * 16D + Math.sin((double)loopCycle / 15D) * 14D + Math.sin((double)loopCycle / 16D) * 12D);
        if(anInt1040 > 0)
            anInt1040 -= 4;
        if(anInt1041 > 0)
            anInt1041 -= 4;
        if(anInt1040 == 0 && anInt1041 == 0)
        {
            int l3 = (int)(Math.random() * 2000D);
            if(l3 == 0)
                anInt1040 = 1024;
            if(l3 == 1)
                anInt1041 = 1024;
        }
    }

    public boolean writeWaveFile(byte abyte0[], int i)
    {
        if(abyte0 == null)
            return true;
        else
            return Signlink.writeWaveFile(abyte0, i);
    }

    public void method60(int i, byte byte0)
    {
        Widget class9 = Widget.widgets[i];
        for(int j = 0; j < class9.childrenIds.length; j++)
        {
            if(class9.childrenIds[j] == -1)
                break;
            Widget class9_1 = Widget.widgets[class9.childrenIds[j]];
            if(class9_1.type == 1)
                method60(class9_1.widgetId, (byte)6);
            class9_1.anInt246 = 0;
            class9_1.anInt208 = 0;
        }

        if(byte0 == 6)
            byte0 = 0;
    }

    public void drawMarkerOnLocation(int i)
    {
        if(markertype != 2)
            return;
        calculateSpriteXY((markerloc_x - paletteX << 7) + markeroffset_x, markerheight * 2, anInt875, (markerloc_y - paletteY << 7) + markeroffset_y);
        if(i >= 0)
            aBoolean1224 = !aBoolean1224;
        if(spriteX > -1 && loopCycle % 20 < 10)
            headicons[2].draw(spriteX - 12, 16083, spriteY - 28);
    }

    public void loopCycleGame(int i)
    {
		/* Unknown timer */
        if(anInt1104 > 1)
            anInt1104--;
		/* Unknown timer */
        if(anInt1011 > 0)
            anInt1011--;
		/* Parse the incoming packets */
        for(int j = 0; j < 5; j++)
            if(!parsePackets(true))
                break;
        if(!isOnlineGame)
            return;
		/* Write monitor packet */
        synchronized(monitor.sync)
        {
            if(flagged) {
                if(super.anInt26 != 0 || monitor.position >= 40) {
                    gameBuffer.putPacket(45);
                    gameBuffer.put(0);
                    int j2 = gameBuffer.offset;
                    int j3 = 0;
                    for(int j4 = 0; j4 < monitor.position; j4++) {
                        if(j2 - gameBuffer.offset >= 240)
                            break;
                        j3++;
                        int l4 = monitor.y_queue[j4];
                        if(l4 < 0)
                            l4 = 0;
                        else
                        if(l4 > 502)
                            l4 = 502;
                        int k5 = monitor.x_queue[j4];
                        if(k5 < 0)
                            k5 = 0;
                        else
                        if(k5 > 764)
                            k5 = 764;
                        int i6 = l4 * 765 + k5;
                        if(monitor.y_queue[j4] == -1 && monitor.x_queue[j4] == -1)
                        {
                            k5 = -1;
                            l4 = -1;
                            i6 = 0x7ffff;
                        }
                        if(k5 == anInt1237 && l4 == anInt1238)
                        {
                            if(anInt1022 < 2047)
                                anInt1022++;
                        } else
                        {
                            int j6 = k5 - anInt1237;
                            anInt1237 = k5;
                            int k6 = l4 - anInt1238;
                            anInt1238 = l4;
                            if(anInt1022 < 8 && j6 >= -32 && j6 <= 31 && k6 >= -32 && k6 <= 31)
                            {
                                j6 += 32;
                                k6 += 32;
                                gameBuffer.putWord((anInt1022 << 12) + (j6 << 6) + k6);
                                anInt1022 = 0;
                            } else
                            if(anInt1022 < 8)
                            {
                                gameBuffer.putTri(0x800000 + (anInt1022 << 19) + i6);
                                anInt1022 = 0;
                            } else
                            {
                                gameBuffer.putDword(0xc0000000 + (anInt1022 << 19) + i6);
                                anInt1022 = 0;
                            }
                        }
                    }

                    gameBuffer.endVarByte(gameBuffer.offset - j2, (byte)0);
                    if(j3 >= monitor.position)
                    {
                        monitor.position = 0;
                    } else
                    {
                        monitor.position -= j3;
                        for(int i5 = 0; i5 < monitor.position; i5++)
                        {
                            monitor.x_queue[i5] = monitor.x_queue[i5 + j3];
                            monitor.y_queue[i5] = monitor.y_queue[i5 + j3];
                        }

                    }
                }
            } else
            {
                monitor.position = 0;
            }
        }
		/* Write click packet */
        if(super.anInt26 != 0)
        {
            long timeDif = (super.pressedTimestamp - lastPressedTimestamp) / 50L;
            if(timeDif > 4095L)
                timeDif = 4095L;
            lastPressedTimestamp = super.pressedTimestamp;
            int y = super.pressedY;
            if(y < 0)
                y = 0;
            else
            if(y > 502)
                y = 502;
            int x = super.pressedX;
            if(x < 0)
                x = 0;
            else
            if(x > 764)
                x = 764;
            int coordHash = y * 765 + x;
            int pressedOp = 0;
            if(super.anInt26 == 2)
                pressedOp = 1;
            int tStamp = (int)timeDif;
            gameBuffer.putPacket(241);
            gameBuffer.putDword((tStamp << 20) + (pressedOp << 19) + coordHash);
        }
        if(camerapacket_delay > 0)
            camerapacket_delay--;
		/* Up, down, left, right */
        if(super.activeKeycodes[1] == 1 || super.activeKeycodes[2] == 1 || super.activeKeycodes[3] == 1 || super.activeKeycodes[4] == 1)
            camerapacket_write = true;
		/* Camera change */
        if(camerapacket_write && camerapacket_delay <= 0)
        {
            camerapacket_delay = 20;
            camerapacket_write = false;
            gameBuffer.putPacket(86);
            gameBuffer.putWord(cameraPitch);
            gameBuffer.putWord128(cameraYaw);
        }
		/* Focus change */
        if(super.aBoolean17 && !focusPacketToggle)
        {
            focusPacketToggle = true;
            gameBuffer.putPacket(3);
            gameBuffer.put(1);
        }
		/* Focus change */
        if(!super.aBoolean17 && focusPacketToggle)
        {
            focusPacketToggle = false;
            gameBuffer.putPacket(3);
            gameBuffer.put(0);
        }
        updateLandscape(-48877);
        updateObjects((byte)8);
        updateSounds(false);
        anInt1009++;
        if(anInt1009 > 750)
            connectionLost(-670);
        updatePlayers((byte)-74);
        updateNpcs(-8066);
        resetMobsChatText((byte)-92);
        anInt945++;
        if(anInt917 != 0)
        {
            anInt916 += 20;
            if(anInt916 >= 400)
                anInt917 = 0;
        }
        if(anInt1246 != 0)
        {
            anInt1243++;
            if(anInt1243 >= 15)
            {
                if(anInt1246 == 2)
                    aBoolean1153 = true;
                if(anInt1246 == 3)
                    aBoolean1223 = true;
                anInt1246 = 0;
            }
        }
        if(anInt1086 != 0)
        {
            anInt989++;
            if(super.newMouseX > anInt1087 + 5 || super.newMouseX < anInt1087 - 5 || super.newMouseY > anInt1088 + 5 || super.newMouseY < anInt1088 - 5)
                aBoolean1242 = true;
            if(super.anInt19 == 0)
            {
                if(anInt1086 == 2)
                    aBoolean1153 = true;
                if(anInt1086 == 3)
                    aBoolean1223 = true;
                anInt1086 = 0;
                if(aBoolean1242 && anInt989 >= 5)
                {
                    anInt1067 = -1;
                    method82(0);
					/* Write moved item packet */
                    if(anInt1067 == moveitem_frameid && moveitem_endslot != moveitem_startslot)
                    {
                        Widget class9 = Widget.widgets[moveitem_frameid];
                        int j1 = 0;
                        if(anInt913 == 1 && class9.actionCode == 206)
                            j1 = 1;
                        if(class9.itemIds[moveitem_endslot] <= 0)
                            j1 = 0;
                        if(class9.aBoolean235)
                        {
                            int l2 = moveitem_startslot;
                            int l3 = moveitem_endslot;
                            class9.itemIds[l3] = class9.itemIds[l2];
                            class9.itemAmounts[l3] = class9.itemAmounts[l2];
                            class9.itemIds[l2] = -1;
                            class9.itemAmounts[l2] = 0;
                        } else
                        if(j1 == 1)
                        {
                            int i3 = moveitem_startslot;
                            for(int i4 = moveitem_endslot; i3 != i4;)
                                if(i3 > i4)
                                {
                                    class9.swapItem(i3, i3 - 1);
                                    i3--;
                                } else
                                if(i3 < i4)
                                {
                                    class9.swapItem(i3, i3 + 1);
                                    i3++;
                                }

                        } else
                        {
                            class9.swapItem(moveitem_startslot, moveitem_endslot);
                        }
                        gameBuffer.putPacket(214);
                        gameBuffer.putWordLE128(moveitem_frameid);
                        gameBuffer.putByteA(j1);
                        gameBuffer.putWordLE128(moveitem_startslot);
                        gameBuffer.putWordLE(moveitem_endslot);
                    }
                } else
                if((anInt1253 == 1 || method17(9, anInt1133 - 1)) && anInt1133 > 2)
                    method116(true);
                else
                if(anInt1133 > 0)
                    handleClick(anInt1133 - 1, false);
                anInt1243 = 10;
                super.anInt26 = 0;
            }
        }
        if(Palette.anInt470 != -1)
        {
            int k = Palette.anInt470;
            int k1 = Palette.anInt471;
            boolean flag = sendWalkPacket(0, 0, 0, -11308, 0, ((Mob) (localPlayer)).yList[0], 0, 0, k1, ((Mob) (localPlayer)).xList[0], true, k);
            Palette.anInt470 = -1;
            if(flag)
            {
                anInt914 = super.pressedX;
                anInt915 = super.pressedY;
                anInt917 = 1;
                anInt916 = 0;
            }
        }
        if(super.anInt26 == 1 && aString844 != null)
        {
            aString844 = null;
            aBoolean1223 = true;
            super.anInt26 = 0;
        }
        handleClickPackets_(4);
        handleMinimap(true);
        handleTabs(19);
        handleToolbar(true);
        if(super.anInt19 == 1 || super.anInt26 == 1)
            anInt1213++;
        if(landscape_stage == 2)
            handleKeyboardArrows(3);
        if(landscape_stage == 2 && aBoolean1160)
            updateCameraPosition((byte)5);
        for(int i1 = 0; i1 < 5; i1++)
            cameratransvars2[i1]++;
        method73(732);
		/* Handle idea counter */
        super.idleCounter++;
        if(super.idleCounter > 4500) {
            anInt1011 = 250;
            super.idleCounter -= 500;
            gameBuffer.putPacket(202);
        }
        anInt988++;
        if(i >= 0)
            grounditems = null;
        if(anInt988 > 500)
        {
            anInt988 = 0;
            int l1 = (int)(Math.random() * 8D);
            if((l1 & 1) == 1)
                anInt1278 += anInt1279;
            if((l1 & 2) == 2)
                anInt1131 += anInt1132;
            if((l1 & 4) == 4)
                anInt896 += anInt897;
        }
        if(anInt1278 < -50)
            anInt1279 = 2;
        if(anInt1278 > 50)
            anInt1279 = -2;
        if(anInt1131 < -55)
            anInt1132 = 2;
        if(anInt1131 > 55)
            anInt1132 = -2;
        if(anInt896 < -40)
            anInt897 = 1;
        if(anInt896 > 40)
            anInt897 = -1;
        anInt1254++;
        if(anInt1254 > 500)
        {
            anInt1254 = 0;
            int i2 = (int)(Math.random() * 8D);
            if((i2 & 1) == 1)
                anInt1209 += anInt1210;
            if((i2 & 2) == 2)
                anInt1170 += anInt1171;
        }
        if(anInt1209 < -60)
            anInt1210 = 2;
        if(anInt1209 > 60)
            anInt1210 = -2;
        if(anInt1170 < -20)
            anInt1171 = 1;
        if(anInt1170 > 10)
            anInt1171 = -1;
        anInt1010++;
        if(anInt1010 > 50)
            gameBuffer.putPacket(0);
		/* Write the outgoing bytes */
        try
        {
            if(bufferedConnection != null && gameBuffer.offset > 0)
            {
                bufferedConnection.write(gameBuffer.payload, 0, gameBuffer.offset);
                gameBuffer.offset = 0;
                anInt1010 = 0;
                return;
            }
        }
        catch(IOException _ex)
        {
            connectionLost(-670);
            return;
        }
        catch(Exception exception)
        {
            killToMainscreen(true);
        }
    }

    public void method63(int i)
    {
        SpawnedObject class30_sub1 = (SpawnedObject)aClass19_1179.getFirst();
        while(i >= 0) 
        {
            for(int j = 1; j > 0; j++);
        }
        for(; class30_sub1 != null; class30_sub1 = (SpawnedObject)aClass19_1179.getNextFront())
            if(class30_sub1.anInt1294 == -1)
            {
                class30_sub1.anInt1302 = 0;
                method89(false, class30_sub1);
            } else
            {
                class30_sub1.removeDeque();
            }

    }

    public void method64(int i)
    {
        if(logo_imagefetcher != null)
            return;
        super.appletImageFetcher = null;
        chat_imagefetcher = null;
        aClass15_1164 = null;
        aClass15_1163 = null;
        toplefttext_imagefetcher = null;
        toolbartext_imagefetcher = null;
        aClass15_1124 = null;
        aClass15_1125 = null;
        titletopleft_imagefetcher = new ImageFetcher(128, 265, getDrawableComponent(), 0);
        BasicRasterizer.resetPixelBuffer();
        titletopright_imagefetcher = new ImageFetcher(128, 265, getDrawableComponent(), 0);
        BasicRasterizer.resetPixelBuffer();
        logo_imagefetcher = new ImageFetcher(509, 171, getDrawableComponent(), 0);
        BasicRasterizer.resetPixelBuffer();
        bottomleftmid_imagefetcher = new ImageFetcher(360, 132, getDrawableComponent(), 0);
        BasicRasterizer.resetPixelBuffer();
        aClass15_1109 = new ImageFetcher(360, 200, getDrawableComponent(), 0);
        BasicRasterizer.resetPixelBuffer();
        aClass15_1112 = new ImageFetcher(202, 238, getDrawableComponent(), 0);
        BasicRasterizer.resetPixelBuffer();
        aClass15_1113 = new ImageFetcher(203, 238, getDrawableComponent(), 0);
        BasicRasterizer.resetPixelBuffer();
        aClass15_1114 = new ImageFetcher(74, 94, getDrawableComponent(), 0);
        BasicRasterizer.resetPixelBuffer();
        aClass15_1115 = new ImageFetcher(75, 94, getDrawableComponent(), 0);
        BasicRasterizer.resetPixelBuffer();
        if(titlescreen_archive != null) {
            drawTitleBackround(0);
            method51(215);
        }
        paintRequested = true;
    }

    public void drawLoadingBar(String s, int i)
    {
        anInt1079 = i;
        aString1049 = s;
        method64(0);
        if(titlescreen_archive == null)
        {
            super.drawLoadingBar(s, i);
            return;
        }
        aClass15_1109.initialize(0);
        char c = '\u0168';
        char c1 = '\310';
        byte byte1 = 20;
        p11Font.drawCenteredYText(0xffffff, "RuneScape is loading - please wait...", 23693, c1 / 2 - 26 - byte1, c / 2);
        int j = c1 / 2 - 18 - byte1;
        BasicRasterizer.drawQuadrilateralOutline(c / 2 - 152, j, 304, 34, 0x8c1111);
        BasicRasterizer.drawQuadrilateralOutline(c / 2 - 151, j + 1, 302, 32, 0);
        BasicRasterizer.drawQuad(c / 2 - 150, j + 2, i * 3, 30, 0x8c1111);
        BasicRasterizer.drawQuad( (c / 2 - 150) + i * 3, j + 2, 300 - i * 3, 30, 0);
        b12Font.drawCenteredYText(0xffffff, s, 23693, (c1 / 2 + 5) - byte1, c / 2);
        aClass15_1109.updateGraphics(171, 23680, super.appletGraphics, 202);
        if(paintRequested)
        {
            paintRequested = false;
            if(!runflamecycle)
            {
                titletopleft_imagefetcher.updateGraphics(0, 23680, super.appletGraphics, 0);
                titletopright_imagefetcher.updateGraphics(0, 23680, super.appletGraphics, 637);
            }
            logo_imagefetcher.updateGraphics(0, 23680, super.appletGraphics, 128);
            bottomleftmid_imagefetcher.updateGraphics(371, 23680, super.appletGraphics, 202);
            aClass15_1112.updateGraphics(265, 23680, super.appletGraphics, 0);
            aClass15_1113.updateGraphics(265, 23680, super.appletGraphics, 562);
            aClass15_1114.updateGraphics(171, 23680, super.appletGraphics, 128);
            aClass15_1115.updateGraphics(171, 23680, super.appletGraphics, 562);
        }
    }

    public void method65(int i, int j, int k, int l, Widget class9, int i1, boolean flag, 
            int j1, int k1)
    {
        if(aBoolean972)
            anInt992 = 32;
        else
            anInt992 = 0;
        aBoolean972 = false;
        packetSize += k1;
        if(k >= i && k < i + 16 && l >= i1 && l < i1 + 16)
        {
            class9.anInt224 -= anInt1213 * 4;
            if(flag)
            {
                aBoolean1153 = true;
                return;
            }
        } else
        if(k >= i && k < i + 16 && l >= (i1 + j) - 16 && l < i1 + j)
        {
            class9.anInt224 += anInt1213 * 4;
            if(flag)
            {
                aBoolean1153 = true;
                return;
            }
        } else
        if(k >= i - anInt992 && k < i + 16 + anInt992 && l >= i1 + 16 && l < (i1 + j) - 16 && anInt1213 > 0)
        {
            int l1 = ((j - 32) * j) / j1;
            if(l1 < 8)
                l1 = 8;
            int i2 = l - i1 - 16 - l1 / 2;
            int j2 = j - 32 - l1;
            class9.anInt224 = ((j1 - j) * i2) / j2;
            if(flag)
                aBoolean1153 = true;
            aBoolean972 = true;
        }
    }

    public boolean method66(int i, int j, int k, int l)
    {
        int i1 = i >> 14 & 0x7fff;
        int j1 = pallet.method304(currentZ, k, j, i);
        if(l >= 0)
            throw new NullPointerException();
        if(j1 == -1)
            return false;
        int k1 = j1 & 0x1f;
        int l1 = j1 >> 6 & 3;
        if(k1 == 10 || k1 == 11 || k1 == 22)
        {
            ObjectDefinition class46 = ObjectDefinition.getObjectDefinition(i1);
            int i2;
            int j2;
            if(l1 == 0 || l1 == 2)
            {
                i2 = class46.anInt744;
                j2 = class46.size;
            } else
            {
                i2 = class46.size;
                j2 = class46.anInt744;
            }
            int k2 = class46.anInt768;
            if(l1 != 0)
                k2 = (k2 << l1 & 0xf) + (k2 >> 4 - l1);
            sendWalkPacket(2, 0, j2, -11308, 0, ((Mob) (localPlayer)).yList[0], i2, k2, j, ((Mob) (localPlayer)).xList[0], false, k);
        } else
        {
            sendWalkPacket(2, l1, 0, -11308, k1 + 1, ((Mob) (localPlayer)).yList[0], 0, 0, j, ((Mob) (localPlayer)).xList[0], false, k);
        }
        anInt914 = super.pressedX;
        anInt915 = super.pressedY;
        anInt917 = 2;
        anInt916 = 0;
        return true;
    }

    public ArchivePackage getArchivePackage(int id, String archiveName, String requestAddress, int expectedChecksum, int loadingPercent) {
        byte src[] = null;
        int delay = 5;
        try {
            if(fileIndexes[0] != null)
                src = fileIndexes[0].get(id);
        } catch(Exception ex) {
        }
        if(!JAGGRAB_DISABLED) {
            if(src != null) {
                CRC.reset();
                CRC.update(src);
                int checksum = (int)CRC.getValue();
                if(checksum != expectedChecksum)
                    src = null;
            }
        }
        if(src != null) {
            ArchivePackage pack = new ArchivePackage(src);
            return pack;
        } else if(JAGGRAB_DISABLED) {
            throw new RuntimeException("SEVERE ERROR! Invalid archive package : " + id + "!\n"
                                     + "The cache must be in the wrong place or is corrupted.");
        }
        int attempts = 0;
        while(src == null) {
            String errorMessage = "Unknown error";
            drawLoadingBar("Requesting " + archiveName, loadingPercent);
            try {
                int currentPercent = 0;
                DataInputStream datainputstream = writeJaggrabRequest(requestAddress + expectedChecksum);
                byte header[] = new byte[6];
                datainputstream.readFully(header, 0, 6);
                ByteBuffer buffer0 = new ByteBuffer(header);
                buffer0.offset = 3;
                int len = buffer0.getTri() + 6;
                int archiveOffset = 6;
                src = new byte[len];
                System.arraycopy(header, 0, src, 0, 6);
                while(archiveOffset < len)  {
                    int blockSize = len - archiveOffset;
                    if(blockSize > 1000)
                        blockSize = 1000;
                    int read = datainputstream.read(src, archiveOffset, blockSize);
                    if(read < 0) {
                        errorMessage = "Length error: " + archiveOffset + "/" + len;
                        throw new IOException("EOF");
                    }
                    archiveOffset += read;
                    int percent = (archiveOffset * 100) / len;
                    if(percent != currentPercent)
                        drawLoadingBar("Loading " + archiveName + " - " + percent + "%", loadingPercent);
                    currentPercent = percent;
                }
                datainputstream.close();
                try {
                    if(fileIndexes[0] != null)
                        fileIndexes[0].put(src, id, src.length);
                } catch(Exception ex) {
                    fileIndexes[0] = null;
                }			
		if(src != null) {
                    CRC.reset();
                    CRC.update(src);
                    int checksum = (int)CRC.getValue();
                    if(checksum != expectedChecksum) {
                        src = null;
                        attempts++;
                        errorMessage = "Checksum error: " + checksum;
                    }
                } 				
            } catch(IOException ioexception) {
                if(errorMessage.equals("Unknown error"))
                    errorMessage = "Connection error";
                src = null;
            } catch(NullPointerException _ex) {
                errorMessage = "Null error";
                src = null;
                if(!Signlink.allowErrorReporting)
                    return null;
            } catch(ArrayIndexOutOfBoundsException _ex) {
                errorMessage = "Bounds error";
                src = null;
                if(!Signlink.allowErrorReporting)
                    return null;
            } catch(Exception ex) {
                errorMessage = "Unexpected error";
                src = null;
                if(!Signlink.allowErrorReporting)
                    return null;
            }
            if(src == null) {
                for(int l1 = delay; l1 > 0; l1--) {
                    if(attempts >= 3)
                    {
                        drawLoadingBar("Game updated - please reload page", loadingPercent);
                        l1 = 10;
                    } else {
                        drawLoadingBar(errorMessage + " - Retrying in " + l1, loadingPercent);
                    }
                    try {
                        Thread.sleep(1000L);
                    } catch(Exception ex) { 
                    }
                }
                delay *= 2;
                if(delay > 60)
                    delay = 60;
                useWebJaggrab = !useWebJaggrab;
            }
        }
        ArchivePackage pack = new ArchivePackage(src);
        return pack;
    }

    public void connectionLost(int i)
    {
        if(anInt1011 > 0)
        {
            killToMainscreen(true);
            return;
        }
        toplefttext_imagefetcher.initialize(0);
        p12Font.drawCenteredYText(0, "Connection lost", 23693, 144, 257);
        p12Font.drawCenteredYText(0xffffff, "Connection lost", 23693, 143, 256);
        p12Font.drawCenteredYText(0, "Please wait - attempting to reestablish", 23693, 159, 257);
        p12Font.drawCenteredYText(0xffffff, "Please wait - attempting to reestablish", 23693, 158, 256);
        while(i >= 0) 
            gameBuffer.put(164);
        toplefttext_imagefetcher.updateGraphics(4, 23680, super.appletGraphics, 4);
        anInt1021 = 0;
        anInt1261 = 0;
        BufferedConnection class24 = bufferedConnection;
        isOnlineGame = false;
        loginAttempts = 0;
        handleLogin(username, password, true);
        if(!isOnlineGame)
            killToMainscreen(true);
        try
        {
            class24.close();
            return;
        }
        catch(Exception _ex)
        {
            return;
        }
    }

    public void handleClick(int i, boolean flag)
    {
        if(i < 0)
            return;
        if(anInt1225 != 0)
        {
            anInt1225 = 0;
            aBoolean1223 = true;
        }
        int j = interfacestack_a[i];
        int k = interfacestack_b[i];
        int l = interfaceopcodestack[i];
        int i1 = interfacestack_c[i];
        if(l >= 2000)
            l -= 2000;
		/* Write item on Npc packet */
        if(l == 582)
        {
            Npc class30_sub2_sub4_sub1_sub1 = npcs[i1];
            if(class30_sub2_sub4_sub1_sub1 != null)
            {
                sendWalkPacket(2, 0, 1, -11308, 0, ((Mob) (localPlayer)).yList[0], 1, 0, ((Mob) (class30_sub2_sub4_sub1_sub1)).yList[0], ((Mob) (localPlayer)).xList[0], false, ((Mob) (class30_sub2_sub4_sub1_sub1)).xList[0]);
                anInt914 = super.pressedX;
                anInt915 = super.pressedY;
                anInt917 = 2;
                anInt916 = 0;
                gameBuffer.putPacket(57);
                gameBuffer.putWord128(anInt1285);
                gameBuffer.putWord128(i1);
                gameBuffer.putWordLE(anInt1283);
                gameBuffer.putWord128(anInt1284);
            }
        }
		/* Pickup ground item */
        if(l == 234)
        {
            boolean flag1 = sendWalkPacket(2, 0, 0, -11308, 0, ((Mob) (localPlayer)).yList[0], 0, 0, k, ((Mob) (localPlayer)).xList[0], false, j);
            if(!flag1)
                flag1 = sendWalkPacket(2, 0, 1, -11308, 0, ((Mob) (localPlayer)).yList[0], 1, 0, k, ((Mob) (localPlayer)).xList[0], false, j);
            anInt914 = super.pressedX;
            anInt915 = super.pressedY;
            anInt917 = 2;
            anInt916 = 0;
            gameBuffer.putPacket(236);
            gameBuffer.putWordLE(k + paletteY);
            gameBuffer.putWord(i1);
            gameBuffer.putWordLE(j + paletteX);
        }
		/* Item on object */
        if(l == 62 && method66(i1, k, j, -770))
        {
            gameBuffer.putPacket(192);
            gameBuffer.putWord(anInt1284);
            gameBuffer.putWordLE(i1 >> 14 & 0x7fff);
            gameBuffer.putWordLE128(k + paletteY);
            gameBuffer.putWordLE(anInt1283);
            gameBuffer.putWordLE128(j + paletteX);
            gameBuffer.putWord(anInt1285);
        }
		/* Item on floor */
        if(l == 511)
        {
            boolean flag2 = sendWalkPacket(2, 0, 0, -11308, 0, ((Mob) (localPlayer)).yList[0], 0, 0, k, ((Mob) (localPlayer)).xList[0], false, j);
            if(!flag2)
                flag2 = sendWalkPacket(2, 0, 1, -11308, 0, ((Mob) (localPlayer)).yList[0], 1, 0, k, ((Mob) (localPlayer)).xList[0], false, j);
            anInt914 = super.pressedX;
            anInt915 = super.pressedY;
            anInt917 = 2;
            anInt916 = 0;
            gameBuffer.putPacket(25);
            gameBuffer.putWordLE(anInt1284);
            gameBuffer.putWord128(anInt1285);
            gameBuffer.putWord(i1);
            gameBuffer.putWord128(k + paletteY);
            gameBuffer.putWordLE128(anInt1283);
            gameBuffer.putWord(j + paletteX);
        }
		/* Item action one */
        if(l == 74)
        {
            gameBuffer.putPacket(122);
            gameBuffer.putWordLE128(k);
            gameBuffer.putWord128(j);
            gameBuffer.putWordLE(i1);
            anInt1243 = 0;
            anInt1244 = k;
            anInt1245 = j;
            anInt1246 = 2;
            if(Widget.widgets[k].parentId == anInt857)
                anInt1246 = 1;
            if(Widget.widgets[k].parentId == anInt1276)
                anInt1246 = 3;
        }
		/* Button click */
        if(l == 315)
        {
            Widget class9 = Widget.widgets[k];
            boolean flag8 = true;
            if(class9.actionCode > 0)
                flag8 = method48(505, class9);
            if(flag8)
            {
                gameBuffer.putPacket(185);
                gameBuffer.putWord(k);
            }
        }
        if(l == 561)
        {
            Player class30_sub2_sub4_sub1_sub2 = playerArray[i1];
            if(class30_sub2_sub4_sub1_sub2 != null)
            {
                sendWalkPacket(2, 0, 1, -11308, 0, ((Mob) (localPlayer)).yList[0], 1, 0, ((Mob) (class30_sub2_sub4_sub1_sub2)).yList[0], ((Mob) (localPlayer)).xList[0], false, ((Mob) (class30_sub2_sub4_sub1_sub2)).xList[0]);
                anInt914 = super.pressedX;
                anInt915 = super.pressedY;
                anInt917 = 2;
                anInt916 = 0;
                anInt1188 += i1;
                if(anInt1188 >= 90)
                {
                    gameBuffer.putPacket(136);
                    anInt1188 = 0;
                }
                gameBuffer.putPacket(128);
                gameBuffer.putWord(i1);
            }
        }
        if(l == 20)
        {
            Npc class30_sub2_sub4_sub1_sub1_1 = npcs[i1];
            if(class30_sub2_sub4_sub1_sub1_1 != null)
            {
                sendWalkPacket(2, 0, 1, -11308, 0, ((Mob) (localPlayer)).yList[0], 1, 0, ((Mob) (class30_sub2_sub4_sub1_sub1_1)).yList[0], ((Mob) (localPlayer)).xList[0], false, ((Mob) (class30_sub2_sub4_sub1_sub1_1)).xList[0]);
                anInt914 = super.pressedX;
                anInt915 = super.pressedY;
                anInt917 = 2;
                anInt916 = 0;
                gameBuffer.putPacket(155);
                gameBuffer.putWordLE(i1);
            }
        }
        if(l == 779)
        {
            Player class30_sub2_sub4_sub1_sub2_1 = playerArray[i1];
            if(class30_sub2_sub4_sub1_sub2_1 != null)
            {
                sendWalkPacket(2, 0, 1, -11308, 0, ((Mob) (localPlayer)).yList[0], 1, 0, ((Mob) (class30_sub2_sub4_sub1_sub2_1)).yList[0], ((Mob) (localPlayer)).xList[0], false, ((Mob) (class30_sub2_sub4_sub1_sub2_1)).xList[0]);
                anInt914 = super.pressedX;
                anInt915 = super.pressedY;
                anInt917 = 2;
                anInt916 = 0;
                gameBuffer.putPacket(153);
                gameBuffer.putWordLE(i1);
            }
        }
        if(l == 516)
            if(!aBoolean885)
                pallet.method312(false, super.pressedY - 4, super.pressedX - 4);
            else
                pallet.method312(false, k - 4, j - 4);
        if(l == 1062)
        {
            anInt924 += paletteX;
            if(anInt924 >= 113)
            {
                gameBuffer.putPacket(183);
                gameBuffer.putTri(0xe63271);
                anInt924 = 0;
            }
            method66(i1, k, j, -770);
            gameBuffer.putPacket(228);
            gameBuffer.putWord128(i1 >> 14 & 0x7fff);
            gameBuffer.putWord128(k + paletteY);
            gameBuffer.putWord(j + paletteX);
        }
        if(l == 679 && !aBoolean1149)
        {
            gameBuffer.putPacket(40);
            gameBuffer.putWord(k);
            aBoolean1149 = true;
        }
        if(l == 431)
        {
            gameBuffer.putPacket(129);
            gameBuffer.putWord128(j);
            gameBuffer.putWord(k);
            gameBuffer.putWord128(i1);
            anInt1243 = 0;
            anInt1244 = k;
            anInt1245 = j;
            anInt1246 = 2;
            if(Widget.widgets[k].parentId == anInt857)
                anInt1246 = 1;
            if(Widget.widgets[k].parentId == anInt1276)
                anInt1246 = 3;
        }
        if(l == 337 || l == 42 || l == 792 || l == 322)
        {
            String s = interfacestringstack[i];
            int k1 = s.indexOf("@whi@");
            if(k1 != -1)
            {
                long l3 = TextTools.stringToLong(s.substring(k1 + 5).trim());
                if(l == 337)
                    socialListLogic((byte)68, l3);
                if(l == 42)
                    method113(l3, 4);
                if(l == 792)
                    removeFriend(false, l3);
                if(l == 322)
                    method122(3, l3);
            }
        }
        if(l == 53)
        {
            gameBuffer.putPacket(135);
            gameBuffer.putWordLE(j);
            gameBuffer.putWord128(k);
            gameBuffer.putWordLE(i1);
            anInt1243 = 0;
            anInt1244 = k;
            anInt1245 = j;
            anInt1246 = 2;
            if(Widget.widgets[k].parentId == anInt857)
                anInt1246 = 1;
            if(Widget.widgets[k].parentId == anInt1276)
                anInt1246 = 3;
        }
        if(l == 539)
        {
            gameBuffer.putPacket(16);
            gameBuffer.putWord128(i1);
            gameBuffer.putWordLE128(j);
            gameBuffer.putWordLE128(k);
            anInt1243 = 0;
            anInt1244 = k;
            anInt1245 = j;
            anInt1246 = 2;
            if(Widget.widgets[k].parentId == anInt857)
                anInt1246 = 1;
            if(Widget.widgets[k].parentId == anInt1276)
                anInt1246 = 3;
        }
        if(l == 484 || l == 6)
        {
            String s1 = interfacestringstack[i];
            int l1 = s1.indexOf("@whi@");
            if(l1 != -1)
            {
                s1 = s1.substring(l1 + 5).trim();
                String s7 = TextTools.formatUsername(-45804, TextTools.longToString(TextTools.stringToLong(s1), (byte)-99));
                boolean flag9 = false;
                for(int j3 = 0; j3 < playerOffset; j3++)
                {
                    Player class30_sub2_sub4_sub1_sub2_7 = playerArray[addedPlayers[j3]];
                    if(class30_sub2_sub4_sub1_sub2_7 == null || class30_sub2_sub4_sub1_sub2_7.name == null || !class30_sub2_sub4_sub1_sub2_7.name.equalsIgnoreCase(s7))
                        continue;
                    sendWalkPacket(2, 0, 1, -11308, 0, ((Mob) (localPlayer)).yList[0], 1, 0, ((Mob) (class30_sub2_sub4_sub1_sub2_7)).yList[0], ((Mob) (localPlayer)).xList[0], false, ((Mob) (class30_sub2_sub4_sub1_sub2_7)).xList[0]);
                    if(l == 484)
                    {
                        gameBuffer.putPacket(139);
                        gameBuffer.putWordLE(addedPlayers[j3]);
                    }
                    if(l == 6)
                    {
                        anInt1188 += i1;
                        if(anInt1188 >= 90)
                        {
                            gameBuffer.putPacket(136);
                            anInt1188 = 0;
                        }
                        gameBuffer.putPacket(128);
                        gameBuffer.putWord(addedPlayers[j3]);
                    }
                    flag9 = true;
                    break;
                }

                if(!flag9)
                    pushMessage("Unable to find " + s7, 0, "", aBoolean991);
            }
        }
		/* Item on item */
        if(l == 870)
        {
            gameBuffer.putPacket(53);
            gameBuffer.putWord(j);
            gameBuffer.putWord128(anInt1283);
            gameBuffer.putWordLE128(i1);
            gameBuffer.putWord(anInt1284);
            gameBuffer.putWordLE(anInt1285);
            gameBuffer.putWord(k);
            anInt1243 = 0;
            anInt1244 = k;
            anInt1245 = j;
            anInt1246 = 2;
            if(Widget.widgets[k].parentId == anInt857)
                anInt1246 = 1;
            if(Widget.widgets[k].parentId == anInt1276)
                anInt1246 = 3;
        }
		/* Drop item */
        if(l == 847)
        {
            gameBuffer.putPacket(87);
            gameBuffer.putWord128(i1);
            gameBuffer.putWord(k);
            gameBuffer.putWord128(j);
            anInt1243 = 0;
            anInt1244 = k;
            anInt1245 = j;
            anInt1246 = 2;
            if(Widget.widgets[k].parentId == anInt857)
                anInt1246 = 1;
            if(Widget.widgets[k].parentId == anInt1276)
                anInt1246 = 3;
        }
        if(l == 626)
        {
            Widget class9_1 = Widget.widgets[k];
            anInt1136 = 1;
            anInt1137 = k;
            anInt1138 = class9_1.anInt237;
            anInt1282 = 0;
            aBoolean1153 = true;
            String s4 = class9_1.aString222;
            if(s4.indexOf(" ") != -1)
                s4 = s4.substring(0, s4.indexOf(" "));
            String s8 = class9_1.aString222;
            if(s8.indexOf(" ") != -1)
                s8 = s8.substring(s8.indexOf(" ") + 1);
            aString1139 = s4 + " " + class9_1.aString218 + " " + s8;
            if(anInt1138 == 16)
            {
                aBoolean1153 = true;
                current_tab = 3;
                update_tabs = true;
            }
            return;
        }
        if(l == 78)
        {
            gameBuffer.putPacket(117);
            gameBuffer.putWordLE128(k);
            gameBuffer.putWordLE128(i1);
            gameBuffer.putWordLE(j);
            anInt1243 = 0;
            anInt1244 = k;
            anInt1245 = j;
            anInt1246 = 2;
            if(Widget.widgets[k].parentId == anInt857)
                anInt1246 = 1;
            if(Widget.widgets[k].parentId == anInt1276)
                anInt1246 = 3;
        }
        if(l == 27)
        {
            Player class30_sub2_sub4_sub1_sub2_2 = playerArray[i1];
            if(class30_sub2_sub4_sub1_sub2_2 != null)
            {
                sendWalkPacket(2, 0, 1, -11308, 0, ((Mob) (localPlayer)).yList[0], 1, 0, ((Mob) (class30_sub2_sub4_sub1_sub2_2)).yList[0], ((Mob) (localPlayer)).xList[0], false, ((Mob) (class30_sub2_sub4_sub1_sub2_2)).xList[0]);
                anInt914 = super.pressedX;
                anInt915 = super.pressedY;
                anInt917 = 2;
                anInt916 = 0;
                anInt986 += i1;
                if(anInt986 >= 54)
                {
                    gameBuffer.putPacket(189);
                    gameBuffer.put(234);
                    anInt986 = 0;
                }
                gameBuffer.putPacket(73);
                gameBuffer.putWordLE(i1);
            }
        }
        if(l == 213)
        {
            boolean flag3 = sendWalkPacket(2, 0, 0, -11308, 0, ((Mob) (localPlayer)).yList[0], 0, 0, k, ((Mob) (localPlayer)).xList[0], false, j);
            if(!flag3)
                flag3 = sendWalkPacket(2, 0, 1, -11308, 0, ((Mob) (localPlayer)).yList[0], 1, 0, k, ((Mob) (localPlayer)).xList[0], false, j);
            anInt914 = super.pressedX;
            anInt915 = super.pressedY;
            anInt917 = 2;
            anInt916 = 0;
            gameBuffer.putPacket(79);
            gameBuffer.putWordLE(k + paletteY);
            gameBuffer.putWord(i1);
            gameBuffer.putWord128(j + paletteX);
        }
        if(l == 632)
        {
            gameBuffer.putPacket(145);
            gameBuffer.putWord128(k);
            gameBuffer.putWord128(j);
            gameBuffer.putWord128(i1);
            anInt1243 = 0;
            anInt1244 = k;
            anInt1245 = j;
            anInt1246 = 2;
            if(Widget.widgets[k].parentId == anInt857)
                anInt1246 = 1;
            if(Widget.widgets[k].parentId == anInt1276)
                anInt1246 = 3;
        }
        if(l == 493)
        {
            gameBuffer.putPacket(75);
            gameBuffer.putWordLE128(k);
            gameBuffer.putWordLE(j);
            gameBuffer.putWord128(i1);
            anInt1243 = 0;
            anInt1244 = k;
            anInt1245 = j;
            anInt1246 = 2;
            if(Widget.widgets[k].parentId == anInt857)
                anInt1246 = 1;
            if(Widget.widgets[k].parentId == anInt1276)
                anInt1246 = 3;
        }
        if(l == 652)
        {
            boolean flag4 = sendWalkPacket(2, 0, 0, -11308, 0, ((Mob) (localPlayer)).yList[0], 0, 0, k, ((Mob) (localPlayer)).xList[0], false, j);
            if(!flag4)
                flag4 = sendWalkPacket(2, 0, 1, -11308, 0, ((Mob) (localPlayer)).yList[0], 1, 0, k, ((Mob) (localPlayer)).xList[0], false, j);
            anInt914 = super.pressedX;
            anInt915 = super.pressedY;
            anInt917 = 2;
            anInt916 = 0;
            gameBuffer.putPacket(156);
            gameBuffer.putWord128(j + paletteX);
            gameBuffer.putWordLE(k + paletteY);
            gameBuffer.putWordLE128(i1);
        }
        if(l == 94)
        {
            boolean flag5 = sendWalkPacket(2, 0, 0, -11308, 0, ((Mob) (localPlayer)).yList[0], 0, 0, k, ((Mob) (localPlayer)).xList[0], false, j);
            if(!flag5)
                flag5 = sendWalkPacket(2, 0, 1, -11308, 0, ((Mob) (localPlayer)).yList[0], 1, 0, k, ((Mob) (localPlayer)).xList[0], false, j);
            anInt914 = super.pressedX;
            anInt915 = super.pressedY;
            anInt917 = 2;
            anInt916 = 0;
            gameBuffer.putPacket(181);
            gameBuffer.putWordLE(k + paletteY);
            gameBuffer.putWord(i1);
            gameBuffer.putWordLE(j + paletteX);
            gameBuffer.putWord128(anInt1137);
        }
        if(l == 646)
        {
            gameBuffer.putPacket(185);
            gameBuffer.putWord(k);
            Widget class9_2 = Widget.widgets[k];
            if(class9_2.scriptOpcodes != null && class9_2.scriptOpcodes[0][0] == 5)
            {
                int i2 = class9_2.scriptOpcodes[0][1];
                if(configstates[i2] != class9_2.scriptConditions[0])
                {
                    configstates[i2] = class9_2.scriptConditions[0];
                    parseClientVarps(false, i2);
                    aBoolean1153 = true;
                }
            }
        }
        if(l == 225)
        {
            Npc class30_sub2_sub4_sub1_sub1_2 = npcs[i1];
            if(class30_sub2_sub4_sub1_sub1_2 != null)
            {
                sendWalkPacket(2, 0, 1, -11308, 0, ((Mob) (localPlayer)).yList[0], 1, 0, ((Mob) (class30_sub2_sub4_sub1_sub1_2)).yList[0], ((Mob) (localPlayer)).xList[0], false, ((Mob) (class30_sub2_sub4_sub1_sub1_2)).xList[0]);
                anInt914 = super.pressedX;
                anInt915 = super.pressedY;
                anInt917 = 2;
                anInt916 = 0;
                anInt1226 += i1;
                if(anInt1226 >= 85)
                {
                    gameBuffer.putPacket(230);
                    gameBuffer.put(239);
                    anInt1226 = 0;
                }
                gameBuffer.putPacket(17);
                gameBuffer.putWordLE128(i1);
            }
        }
        if(l == 965)
        {
            Npc class30_sub2_sub4_sub1_sub1_3 = npcs[i1];
            if(class30_sub2_sub4_sub1_sub1_3 != null)
            {
                sendWalkPacket(2, 0, 1, -11308, 0, ((Mob) (localPlayer)).yList[0], 1, 0, ((Mob) (class30_sub2_sub4_sub1_sub1_3)).yList[0], ((Mob) (localPlayer)).xList[0], false, ((Mob) (class30_sub2_sub4_sub1_sub1_3)).xList[0]);
                anInt914 = super.pressedX;
                anInt915 = super.pressedY;
                anInt917 = 2;
                anInt916 = 0;
                anInt1134++;
                if(anInt1134 >= 96)
                {
                    gameBuffer.putPacket(152);
                    gameBuffer.put(88);
                    anInt1134 = 0;
                }
                gameBuffer.putPacket(21);
                gameBuffer.putWord(i1);
            }
        }
        if(l == 413)
        {
            Npc class30_sub2_sub4_sub1_sub1_4 = npcs[i1];
            if(class30_sub2_sub4_sub1_sub1_4 != null)
            {
                sendWalkPacket(2, 0, 1, -11308, 0, ((Mob) (localPlayer)).yList[0], 1, 0, ((Mob) (class30_sub2_sub4_sub1_sub1_4)).yList[0], ((Mob) (localPlayer)).xList[0], false, ((Mob) (class30_sub2_sub4_sub1_sub1_4)).xList[0]);
                anInt914 = super.pressedX;
                anInt915 = super.pressedY;
                anInt917 = 2;
                anInt916 = 0;
                gameBuffer.putPacket(131);
                gameBuffer.putWordLE128(i1);
                gameBuffer.putWord128(anInt1137);
            }
        }
        if(l == 200)
            method147(537);
        if(l == 1025)
        {
            Npc class30_sub2_sub4_sub1_sub1_5 = npcs[i1];
            if(class30_sub2_sub4_sub1_sub1_5 != null)
            {
                NpcDefinition class5 = class30_sub2_sub4_sub1_sub1_5.definition;
                if(class5.confignpcs != null)
                    class5 = class5.method161(anInt877);
                if(class5 != null)
                {
                    String s9;
                    if(class5.examine != null)
                        s9 = new String(class5.examine);
                    else
                        s9 = "It's a " + class5.name + ".";
                    pushMessage(s9, 0, "", aBoolean991);
                }
            }
        }
        if(l == 900)
        {
            method66(i1, k, j, -770);
            gameBuffer.putPacket(252);
            gameBuffer.putWordLE128(i1 >> 14 & 0x7fff);
            gameBuffer.putWordLE(k + paletteY);
            gameBuffer.putWord128(j + paletteX);
        }
        if(l == 412)
        {
            Npc class30_sub2_sub4_sub1_sub1_6 = npcs[i1];
            if(class30_sub2_sub4_sub1_sub1_6 != null)
            {
                sendWalkPacket(2, 0, 1, -11308, 0, ((Mob) (localPlayer)).yList[0], 1, 0, ((Mob) (class30_sub2_sub4_sub1_sub1_6)).yList[0], ((Mob) (localPlayer)).xList[0], false, ((Mob) (class30_sub2_sub4_sub1_sub1_6)).xList[0]);
                anInt914 = super.pressedX;
                anInt915 = super.pressedY;
                anInt917 = 2;
                anInt916 = 0;
                gameBuffer.putPacket(72);
                gameBuffer.putWord128(i1);
            }
        }
        if(l == 365)
        {
            Player class30_sub2_sub4_sub1_sub2_3 = playerArray[i1];
            if(class30_sub2_sub4_sub1_sub2_3 != null)
            {
                sendWalkPacket(2, 0, 1, -11308, 0, ((Mob) (localPlayer)).yList[0], 1, 0, ((Mob) (class30_sub2_sub4_sub1_sub2_3)).yList[0], ((Mob) (localPlayer)).xList[0], false, ((Mob) (class30_sub2_sub4_sub1_sub2_3)).xList[0]);
                anInt914 = super.pressedX;
                anInt915 = super.pressedY;
                anInt917 = 2;
                anInt916 = 0;
                gameBuffer.putPacket(249);
                gameBuffer.putWord128(i1);
                gameBuffer.putWordLE(anInt1137);
            }
        }
        if(l == 729)
        {
            Player class30_sub2_sub4_sub1_sub2_4 = playerArray[i1];
            if(class30_sub2_sub4_sub1_sub2_4 != null)
            {
                sendWalkPacket(2, 0, 1, -11308, 0, ((Mob) (localPlayer)).yList[0], 1, 0, ((Mob) (class30_sub2_sub4_sub1_sub2_4)).yList[0], ((Mob) (localPlayer)).xList[0], false, ((Mob) (class30_sub2_sub4_sub1_sub2_4)).xList[0]);
                anInt914 = super.pressedX;
                anInt915 = super.pressedY;
                anInt917 = 2;
                anInt916 = 0;
                gameBuffer.putPacket(39);
                gameBuffer.putWordLE(i1);
            }
        }
        if(l == 577)
        {
            Player class30_sub2_sub4_sub1_sub2_5 = playerArray[i1];
            if(class30_sub2_sub4_sub1_sub2_5 != null)
            {
                sendWalkPacket(2, 0, 1, -11308, 0, ((Mob) (localPlayer)).yList[0], 1, 0, ((Mob) (class30_sub2_sub4_sub1_sub2_5)).yList[0], ((Mob) (localPlayer)).xList[0], false, ((Mob) (class30_sub2_sub4_sub1_sub2_5)).xList[0]);
                anInt914 = super.pressedX;
                anInt915 = super.pressedY;
                anInt917 = 2;
                anInt916 = 0;
                gameBuffer.putPacket(139);
                gameBuffer.putWordLE(i1);
            }
        }
        if(l == 956 && method66(i1, k, j, -770))
        {
            gameBuffer.putPacket(35);
            gameBuffer.putWordLE(j + paletteX);
            gameBuffer.putWord128(anInt1137);
            gameBuffer.putWord128(k + paletteY);
            gameBuffer.putWordLE(i1 >> 14 & 0x7fff);
        }
        if(l == 567)
        {
            boolean flag6 = sendWalkPacket(2, 0, 0, -11308, 0, ((Mob) (localPlayer)).yList[0], 0, 0, k, ((Mob) (localPlayer)).xList[0], false, j);
            if(!flag6)
                flag6 = sendWalkPacket(2, 0, 1, -11308, 0, ((Mob) (localPlayer)).yList[0], 1, 0, k, ((Mob) (localPlayer)).xList[0], false, j);
            anInt914 = super.pressedX;
            anInt915 = super.pressedY;
            anInt917 = 2;
            anInt916 = 0;
            gameBuffer.putPacket(23);
            gameBuffer.putWordLE(k + paletteY);
            gameBuffer.putWordLE(i1);
            gameBuffer.putWordLE(j + paletteX);
        }
        if(l == 867)
        {
            if((i1 & 3) == 0)
                anInt1175++;
            if(anInt1175 >= 59)
            {
                gameBuffer.putPacket(200);
                gameBuffer.putWord(25501);
                anInt1175 = 0;
            }
            gameBuffer.putPacket(43);
            gameBuffer.putWordLE(k);
            gameBuffer.putWord128(i1);
            gameBuffer.putWord128(j);
            anInt1243 = 0;
            anInt1244 = k;
            anInt1245 = j;
            anInt1246 = 2;
            if(Widget.widgets[k].parentId == anInt857)
                anInt1246 = 1;
            if(Widget.widgets[k].parentId == anInt1276)
                anInt1246 = 3;
        }
        if(l == 543)
        {
            gameBuffer.putPacket(237);
            gameBuffer.putWord(j);
            gameBuffer.putWord128(i1);
            gameBuffer.putWord(k);
            gameBuffer.putWord128(anInt1137);
            anInt1243 = 0;
            anInt1244 = k;
            anInt1245 = j;
            anInt1246 = 2;
            if(Widget.widgets[k].parentId == anInt857)
                anInt1246 = 1;
            if(Widget.widgets[k].parentId == anInt1276)
                anInt1246 = 3;
        }
        if(l == 606)
        {
            String s2 = interfacestringstack[i];
            int j2 = s2.indexOf("@whi@");
            if(j2 != -1)
                if(anInt857 == -1)
                {
                    method147(537);
                    aString881 = s2.substring(j2 + 5).trim();
                    aBoolean1158 = false;
                    for(int i3 = 0; i3 < Widget.widgets.length; i3++)
                    {
                        if(Widget.widgets[i3] == null || Widget.widgets[i3].actionCode != 600)
                            continue;
                        anInt1178 = anInt857 = Widget.widgets[i3].parentId;
                        break;
                    }

                } else
                {
                    pushMessage("Please close the interface you have open before using 'report abuse'", 0, "", aBoolean991);
                }
        }
        if(l == 491)
        {
            Player class30_sub2_sub4_sub1_sub2_6 = playerArray[i1];
            if(class30_sub2_sub4_sub1_sub2_6 != null)
            {
                sendWalkPacket(2, 0, 1, -11308, 0, ((Mob) (localPlayer)).yList[0], 1, 0, ((Mob) (class30_sub2_sub4_sub1_sub2_6)).yList[0], ((Mob) (localPlayer)).xList[0], false, ((Mob) (class30_sub2_sub4_sub1_sub2_6)).xList[0]);
                anInt914 = super.pressedX;
                anInt915 = super.pressedY;
                anInt917 = 2;
                anInt916 = 0;
                gameBuffer.putPacket(14);
                gameBuffer.putWord128(anInt1284);
                gameBuffer.putWord(i1);
                gameBuffer.putWord(anInt1285);
                gameBuffer.putWordLE(anInt1283);
            }
        }
        if(l == 639)
        {
            String s3 = interfacestringstack[i];
            int k2 = s3.indexOf("@whi@");
            if(k2 != -1)
            {
                long l4 = TextTools.stringToLong(s3.substring(k2 + 5).trim());
                int k3 = -1;
                for(int i4 = 0; i4 < amt_friendhashes; i4++)
                {
                    if(friend_hashes[i4] != l4)
                        continue;
                    k3 = i4;
                    break;
                }

                if(k3 != -1 && anIntArray826[k3] > 0)
                {
                    aBoolean1223 = true;
                    anInt1225 = 0;
                    aBoolean1256 = true;
                    aString1212 = "";
                    anInt1064 = 3;
                    aLong953 = friend_hashes[k3];
                    aString1121 = "Enter message to send to " + friendusernames[k3];
                }
            }
        }
        if(l == 454)
        {
            gameBuffer.putPacket(41);
            gameBuffer.putWord(i1);
            gameBuffer.putWord128(j);
            gameBuffer.putWord128(k);
            anInt1243 = 0;
            anInt1244 = k;
            anInt1245 = j;
            anInt1246 = 2;
            if(Widget.widgets[k].parentId == anInt857)
                anInt1246 = 1;
            if(Widget.widgets[k].parentId == anInt1276)
                anInt1246 = 3;
        }
        if(l == 478)
        {
            Npc class30_sub2_sub4_sub1_sub1_7 = npcs[i1];
            if(class30_sub2_sub4_sub1_sub1_7 != null)
            {
                sendWalkPacket(2, 0, 1, -11308, 0, ((Mob) (localPlayer)).yList[0], 1, 0, ((Mob) (class30_sub2_sub4_sub1_sub1_7)).yList[0], ((Mob) (localPlayer)).xList[0], false, ((Mob) (class30_sub2_sub4_sub1_sub1_7)).xList[0]);
                anInt914 = super.pressedX;
                anInt915 = super.pressedY;
                anInt917 = 2;
                anInt916 = 0;
                if((i1 & 3) == 0)
                    anInt1155++;
                if(anInt1155 >= 53)
                {
                    gameBuffer.putPacket(85);
                    gameBuffer.put(66);
                    anInt1155 = 0;
                }
                gameBuffer.putPacket(18);
                gameBuffer.putWordLE(i1);
            }
        }
        if(l == 113)
        {
            method66(i1, k, j, -770);
            gameBuffer.putPacket(70);
            gameBuffer.putWordLE(j + paletteX);
            gameBuffer.putWord(k + paletteY);
            gameBuffer.putWordLE128(i1 >> 14 & 0x7fff);
        }
        if(l == 872)
        {
            method66(i1, k, j, -770);
            gameBuffer.putPacket(234);
            gameBuffer.putWordLE128(j + paletteX);
            gameBuffer.putWord128(i1 >> 14 & 0x7fff);
            gameBuffer.putWordLE128(k + paletteY);
        }
        if(l == 502)
        {
            method66(i1, k, j, -770);
            gameBuffer.putPacket(132);
            gameBuffer.putWordLE128(j + paletteX);
            gameBuffer.putWord(i1 >> 14 & 0x7fff);
            gameBuffer.putWord128(k + paletteY);
        }
        if(l == 1125)
        {
            ItemDefinition class8 = ItemDefinition.getItemDefinition(i1);
            Widget class9_4 = Widget.widgets[k];
            String s5;
            if(class9_4 != null && class9_4.itemAmounts[j] >= 0x186a0)
                s5 = class9_4.itemAmounts[j] + " x " + class8.withItemName;
            else
            if(class8.examine != null)
                s5 = new String(class8.examine);
            else
                s5 = "It's a " + class8.withItemName + ".";
            pushMessage(s5, 0, "", aBoolean991);
        }
        if(l == 169)
        {
            gameBuffer.putPacket(185);
            gameBuffer.putWord(k);
            Widget class9_3 = Widget.widgets[k];
            if(class9_3.scriptOpcodes != null && class9_3.scriptOpcodes[0][0] == 5)
            {
                int l2 = class9_3.scriptOpcodes[0][1];
                configstates[l2] = 1 - configstates[l2];
                parseClientVarps(false, l2);
                aBoolean1153 = true;
            }
        }
        if(l == 447)
        {
            anInt1282 = 1;
            anInt1283 = j;
            anInt1284 = k;
            anInt1285 = i1;
            usedItemName = ItemDefinition.getItemDefinition(i1).withItemName;
            anInt1136 = 0;
            aBoolean1153 = true;
            return;
        }
        if(l == 1226)
        {
            int j1 = i1 >> 14 & 0x7fff;
            ObjectDefinition class46 = ObjectDefinition.getObjectDefinition(j1);
            String s10;
            if(class46.aByteArray777 != null)
                s10 = new String(class46.aByteArray777);
            else
                s10 = "It's a " + class46.aString739 + ".";
            pushMessage(s10, 0, "", aBoolean991);
        }
        if(l == 244)
        {
            boolean flag7 = sendWalkPacket(2, 0, 0, -11308, 0, ((Mob) (localPlayer)).yList[0], 0, 0, k, ((Mob) (localPlayer)).xList[0], false, j);
            if(!flag7)
                flag7 = sendWalkPacket(2, 0, 1, -11308, 0, ((Mob) (localPlayer)).yList[0], 1, 0, k, ((Mob) (localPlayer)).xList[0], false, j);
            anInt914 = super.pressedX;
            anInt915 = super.pressedY;
            anInt917 = 2;
            anInt916 = 0;
            gameBuffer.putPacket(253);
            gameBuffer.putWordLE(j + paletteX);
            gameBuffer.putWordLE128(k + paletteY);
            gameBuffer.putWord128(i1);
        }
        if(l == 1448)
        {
            ItemDefinition class8_1 = ItemDefinition.getItemDefinition(i1);
            String s6;
            if(class8_1.examine != null)
                s6 = new String(class8_1.examine);
            else
                s6 = "It's a " + class8_1.withItemName + ".";
            pushMessage(s6, 0, "", aBoolean991);
        }
        anInt1282 = 0;
        if(flag)
        {
            return;
        } else
        {
            anInt1136 = 0;
            aBoolean1153 = true;
            return;
        }
    }

    public void calculateOntutorialIsland(int junk)
    {
        ontutorial_island = 0;
        int x = (((Mob) (localPlayer)).fineX >> 7) + paletteX;
        int y = (((Mob) (localPlayer)).fineY >> 7) + paletteY;
        if(x >= 3053 && x <= 3156 && y >= 3056 && y <= 3136)
            ontutorial_island = 1;
        if(x >= 3072 && x <= 3118 && y >= 9492 && y <= 9535)
            ontutorial_island = 1;
        if(ontutorial_island == 1 && x >= 3139 && x <= 3199 && y >= 3008 && y <= 3062)
            ontutorial_island = 0;
    }

    public void run()
    {
        if(runclient)
        {
            clientProcess((byte)59);
            return;
        } else
        {
            super.run();
            return;
        }
    }

    public void method71(int i)
    {
        if(anInt1282 == 0 && anInt1136 == 0)
        {
            interfacestringstack[anInt1133] = "Walk here";
            interfaceopcodestack[anInt1133] = 516;
            interfacestack_a[anInt1133] = super.newMouseX;
            interfacestack_b[anInt1133] = super.newMouseY;
            anInt1133++;
        }
        int j = -1;
        for(int k = 0; k < Model.anInt1687; k++)
        {
            int l = Model.anIntArray1688[k];
            int i1 = l & 0x7f;
            int j1 = l >> 7 & 0x7f;
            int type = l >> 29 & 3;
            int l1 = l >> 14 & 0x7fff;
            if(l == j)
                continue;
            j = l;
            if(type == 2 && pallet.method304(currentZ, i1, j1, l) >= 0)
            {
                ObjectDefinition class46 = ObjectDefinition.getObjectDefinition(l1);
                if(class46.anIntArray759 != null)
                    class46 = class46.method580(true);
                if(class46 == null)
                    continue;
                if(anInt1282 == 1)
                {
                    interfacestringstack[anInt1133] = "Use " + usedItemName + " with @cya@" + class46.aString739;
                    interfaceopcodestack[anInt1133] = 62;
                    interfacestack_c[anInt1133] = l;
                    interfacestack_a[anInt1133] = i1;
                    interfacestack_b[anInt1133] = j1;
                    anInt1133++;
                } else
                if(anInt1136 == 1)
                {
                    if((anInt1138 & 4) == 4)
                    {
                        interfacestringstack[anInt1133] = aString1139 + " @cya@" + class46.aString739;
                        interfaceopcodestack[anInt1133] = 956;
                        interfacestack_c[anInt1133] = l;
                        interfacestack_a[anInt1133] = i1;
                        interfacestack_b[anInt1133] = j1;
                        anInt1133++;
                    }
                } else
                {
                    if(class46.aStringArray786 != null)
                    {
                        for(int i2 = 4; i2 >= 0; i2--)
                            if(class46.aStringArray786[i2] != null)
                            {
                                interfacestringstack[anInt1133] = class46.aStringArray786[i2] + " @cya@" + class46.aString739;
                                if(i2 == 0)
                                    interfaceopcodestack[anInt1133] = 502;
                                if(i2 == 1)
                                    interfaceopcodestack[anInt1133] = 900;
                                if(i2 == 2)
                                    interfaceopcodestack[anInt1133] = 113;
                                if(i2 == 3)
                                    interfaceopcodestack[anInt1133] = 872;
                                if(i2 == 4)
                                    interfaceopcodestack[anInt1133] = 1062;
                                interfacestack_c[anInt1133] = l;
                                interfacestack_a[anInt1133] = i1;
                                interfacestack_b[anInt1133] = j1;
                                anInt1133++;
                            }

                    }
                    interfacestringstack[anInt1133] = "Examine @cya@" + class46.aString739;
                    interfaceopcodestack[anInt1133] = 1226;
                    interfacestack_c[anInt1133] = class46.id << 14;
                    interfacestack_a[anInt1133] = i1;
                    interfacestack_b[anInt1133] = j1;
                    anInt1133++;
                }
            }
            if(type == 1)
            {
                Npc class30_sub2_sub4_sub1_sub1 = npcs[l1];
                if(class30_sub2_sub4_sub1_sub1.definition.npc_halftileoffsets == 1 && (((Mob) (class30_sub2_sub4_sub1_sub1)).fineX & 0x7f) == 64 && (((Mob) (class30_sub2_sub4_sub1_sub1)).fineY & 0x7f) == 64)
                {
                    for(int j2 = 0; j2 < anInt836; j2++)
                    {
                        Npc class30_sub2_sub4_sub1_sub1_1 = npcs[localNpcIds[j2]];
                        if(class30_sub2_sub4_sub1_sub1_1 != null && class30_sub2_sub4_sub1_sub1_1 != class30_sub2_sub4_sub1_sub1 && class30_sub2_sub4_sub1_sub1_1.definition.npc_halftileoffsets == 1 && ((Mob) (class30_sub2_sub4_sub1_sub1_1)).fineX == ((Mob) (class30_sub2_sub4_sub1_sub1)).fineX && ((Mob) (class30_sub2_sub4_sub1_sub1_1)).fineY == ((Mob) (class30_sub2_sub4_sub1_sub1)).fineY)
                            method87(class30_sub2_sub4_sub1_sub1_1.definition, localNpcIds[j2], false, j1, i1);
                    }

                    for(int l2 = 0; l2 < playerOffset; l2++)
                    {
                        Player class30_sub2_sub4_sub1_sub2_1 = playerArray[addedPlayers[l2]];
                        if(class30_sub2_sub4_sub1_sub2_1 != null && ((Mob) (class30_sub2_sub4_sub1_sub2_1)).fineX == ((Mob) (class30_sub2_sub4_sub1_sub1)).fineX && ((Mob) (class30_sub2_sub4_sub1_sub2_1)).fineY == ((Mob) (class30_sub2_sub4_sub1_sub1)).fineY)
                            method88(i1, addedPlayers[l2], class30_sub2_sub4_sub1_sub2_1, false, j1);
                    }

                }
                method87(class30_sub2_sub4_sub1_sub1.definition, l1, false, j1, i1);
            }
            if(type == 0)
            {
                Player class30_sub2_sub4_sub1_sub2 = playerArray[l1];
                if((((Mob) (class30_sub2_sub4_sub1_sub2)).fineX & 0x7f) == 64 && (((Mob) (class30_sub2_sub4_sub1_sub2)).fineY & 0x7f) == 64)
                {
                    for(int k2 = 0; k2 < anInt836; k2++)
                    {
                        Npc class30_sub2_sub4_sub1_sub1_2 = npcs[localNpcIds[k2]];
                        if(class30_sub2_sub4_sub1_sub1_2 != null && class30_sub2_sub4_sub1_sub1_2.definition.npc_halftileoffsets == 1 && ((Mob) (class30_sub2_sub4_sub1_sub1_2)).fineX == ((Mob) (class30_sub2_sub4_sub1_sub2)).fineX && ((Mob) (class30_sub2_sub4_sub1_sub1_2)).fineY == ((Mob) (class30_sub2_sub4_sub1_sub2)).fineY)
                            method87(class30_sub2_sub4_sub1_sub1_2.definition, localNpcIds[k2], false, j1, i1);
                    }

                    for(int i3 = 0; i3 < playerOffset; i3++)
                    {
                        Player class30_sub2_sub4_sub1_sub2_2 = playerArray[addedPlayers[i3]];
                        if(class30_sub2_sub4_sub1_sub2_2 != null && class30_sub2_sub4_sub1_sub2_2 != class30_sub2_sub4_sub1_sub2 && ((Mob) (class30_sub2_sub4_sub1_sub2_2)).fineX == ((Mob) (class30_sub2_sub4_sub1_sub2)).fineX && ((Mob) (class30_sub2_sub4_sub1_sub2_2)).fineY == ((Mob) (class30_sub2_sub4_sub1_sub2)).fineY)
                            method88(i1, addedPlayers[i3], class30_sub2_sub4_sub1_sub2_2, false, j1);
                    }

                }
                method88(i1, l1, class30_sub2_sub4_sub1_sub2, false, j1);
            }
            if(type == 3)
            {
                Deque class19 = grounditems[currentZ][i1][j1];
                if(class19 != null)
                {
                    for(GroundItem class30_sub2_sub4_sub2 = (GroundItem)class19.getLast(); class30_sub2_sub4_sub2 != null; class30_sub2_sub4_sub2 = (GroundItem)class19.getNextBack())
                    {
                        ItemDefinition class8 = ItemDefinition.getItemDefinition(class30_sub2_sub4_sub2.itemid);
                        if(anInt1282 == 1)
                        {
                            interfacestringstack[anInt1133] = "Use " + usedItemName + " with @lre@" + class8.withItemName;
                            interfaceopcodestack[anInt1133] = 511;
                            interfacestack_c[anInt1133] = class30_sub2_sub4_sub2.itemid;
                            interfacestack_a[anInt1133] = i1;
                            interfacestack_b[anInt1133] = j1;
                            anInt1133++;
                        } else
                        if(anInt1136 == 1)
                        {
                            if((anInt1138 & 1) == 1)
                            {
                                interfacestringstack[anInt1133] = aString1139 + " @lre@" + class8.withItemName;
                                interfaceopcodestack[anInt1133] = 94;
                                interfacestack_c[anInt1133] = class30_sub2_sub4_sub2.itemid;
                                interfacestack_a[anInt1133] = i1;
                                interfacestack_b[anInt1133] = j1;
                                anInt1133++;
                            }
                        } else
                        {
                            for(int j3 = 4; j3 >= 0; j3--)
                                if(class8.ground_options != null && class8.ground_options[j3] != null)
                                {
                                    interfacestringstack[anInt1133] = class8.ground_options[j3] + " @lre@" + class8.withItemName;
                                    if(j3 == 0)
                                        interfaceopcodestack[anInt1133] = 652;
                                    if(j3 == 1)
                                        interfaceopcodestack[anInt1133] = 567;
                                    if(j3 == 2)
                                        interfaceopcodestack[anInt1133] = 234;
                                    if(j3 == 3)
                                        interfaceopcodestack[anInt1133] = 244;
                                    if(j3 == 4)
                                        interfaceopcodestack[anInt1133] = 213;
                                    interfacestack_c[anInt1133] = class30_sub2_sub4_sub2.itemid;
                                    interfacestack_a[anInt1133] = i1;
                                    interfacestack_b[anInt1133] = j1;
                                    anInt1133++;
                                } else
                                if(j3 == 2)
                                {
                                    interfacestringstack[anInt1133] = "Take @lre@" + class8.withItemName;
                                    interfaceopcodestack[anInt1133] = 234;
                                    interfacestack_c[anInt1133] = class30_sub2_sub4_sub2.itemid;
                                    interfacestack_a[anInt1133] = i1;
                                    interfacestack_b[anInt1133] = j1;
                                    anInt1133++;
                                }

                            interfacestringstack[anInt1133] = "Examine @lre@" + class8.withItemName;
                            interfaceopcodestack[anInt1133] = 1448;
                            interfacestack_c[anInt1133] = class30_sub2_sub4_sub2.itemid;
                            interfacestack_a[anInt1133] = i1;
                            interfacestack_b[anInt1133] = j1;
                            anInt1133++;
                        }
                    }

                }
            }
        }

        if(i != 33660)
            packetId = inbuffer.getUbyte();
    }

    public void destroy(int i)
    {
        Signlink.allowErrorReporting = false;
        try
        {
            if(bufferedConnection != null)
                bufferedConnection.close();
        }
        catch(Exception _ex) { }
        bufferedConnection = null;
        stopMidi(860);
        if(monitor != null)
            monitor.running = false;
        monitor = null;
        ondemandhandler.pause();
        ondemandhandler = null;
        aClass30_Sub2_Sub2_834 = null;
        gameBuffer = null;
        loginBuffer = null;
        inbuffer = null;
        regionHashes = null;
        tileSrcs = null;
        regionSrcs = null;
        anIntArray1235 = null;
        anIntArray1236 = null;
        tileHeightmap = null;
        tileFlags = null;
        pallet = null;
        planeFlags = null;
        anIntArrayArray901 = null;
        distancestrength = null;
        walkingstepsx = null;
        walkingstepsy = null;
        aByteArray912 = null;
        aClass15_1163 = null;
        aClass15_1164 = null;
        toplefttext_imagefetcher = null;
        chat_imagefetcher = null;
        toolbartext_imagefetcher = null;
        aClass15_1124 = null;
        aClass15_1125 = null;
        backleft1_imagefetcher = null;
        backleft2_imagefetcher = null;
        backright1_imagefetcher = null;
        backright2_imagefetcher = null;
        backtop1_imagefetcher = null;
        backvmid1_imagefetcher = null;
        backvmid2_imagefetcher = null;
        backvmid3_imagefetcher = null;
        backhmid2_imagefetcher = null;
        invback = null;
        mapback = null;
        chatback = null;
        backbase1 = null;
        backbase2 = null;
        backmid1 = null;
        sideicons = null;
        redstone1 = null;
        redstone2 = null;
        redstone3 = null;
        redstone1_2 = null;
        redstone2_2 = null;
        redstone1_3 = null;
        redstone2_3 = null;
        redstone3_2 = null;
        redstone1_4 = null;
        redstone2_4 = null;
        compass = null;
        hitmarks = null;
        headicons = null;
        cross_sprites = null;
        grounditem_mapdotsprite = null;
        mapdots1 = null;
        mapdots2 = null;
        mapdots3 = null;
        mapdots4 = null;
        mapscene = null;
        mapfunction = null;
        anIntArrayArray929 = null;
        playerArray = null;
        addedPlayers = null;
        pFlagUpdateList = null;
        appearanceBuffers = null;
        eRmQueue = null;
        npcs = null;
        localNpcIds = null;
        grounditems = null;
        aClass19_1179 = null;
        i = 55 / i;
        aClass19_1013 = null;
        gfxs_storage = null;
        interfacestack_a = null;
        interfacestack_b = null;
        interfaceopcodestack = null;
        interfacestack_c = null;
        interfacestringstack = null;
        configstates = null;
        anIntArray1072 = null;
        anIntArray1073 = null;
        mapfunctionstack = null;
        aClass30_Sub2_Sub1_Sub1_1263 = null;
        friendusernames = null;
        friend_hashes = null;
        anIntArray826 = null;
        titletopleft_imagefetcher = null;
        titletopright_imagefetcher = null;
        logo_imagefetcher = null;
        bottomleftmid_imagefetcher = null;
        aClass15_1109 = null;
        aClass15_1112 = null;
        aClass15_1113 = null;
        aClass15_1114 = null;
        aClass15_1115 = null;
        method118(3);
        ObjectDefinition.dystory(-501);
        NpcDefinition.dystroy(-501);
        ItemDefinition.dystroy(-501);
        FloorDefinition.definitions = null;
        IdentityKit.identityKits = null;
        Widget.widgets = null;
        AnimSequence.animationsequences = null;
        SpotAnim.aClass23Array403 = null;
        SpotAnim.aClass12_415 = null;
        VarpFile.aClass41Array701 = null;
        super.appletImageFetcher = null;
        Player.aClass12_1704 = null;
        TriangleRasterizer.destroy(-501);
        Palette.dystroy(-501);
        Model.dystroy(-501);
        AnimFrame.method530(-501);
        System.gc();
    }

    public void printInfo() {
        System.out.println("============");
        System.out.println("flame-cycle:" + flameCycle);
        if(ondemandhandler != null)
            System.out.println("Ondemandd-cycle:" + ondemandhandler.cycle);
        System.out.println("loop-cycle:" + loopCycle);
        System.out.println("draw-cycle:" + drawCycle);
        System.out.println("ptype:" + packetId);
        System.out.println("psize:" + packetSize);
        if(bufferedConnection != null)
            bufferedConnection.printInfo();
        super.aBoolean9 = true;
    }

    @Override
    public Component getDrawableComponent() {
        if(Signlink.applet != null)
            return Signlink.applet;
        if(super.appletFrame != null)
            return super.appletFrame;
        else
            return this;
    }

    public void method73(int i)
    {
        i = 55 / i;
        do
        {
            int j = removeKeyId();
            if(j == -1)
                break;
            if(anInt857 != -1 && anInt857 == anInt1178)
            {
                if(j == 8 && aString881.length() > 0)
                    aString881 = aString881.substring(0, aString881.length() - 1);
                if((j >= 97 && j <= 122 || j >= 65 && j <= 90 || j >= 48 && j <= 57 || j == 32) && aString881.length() < 12)
                    aString881 += (char)j;
            } else
            if(aBoolean1256)
            {
                if(j >= 32 && j <= 122 && aString1212.length() < 80)
                {
                    aString1212 += (char)j;
                    aBoolean1223 = true;
                }
                if(j == 8 && aString1212.length() > 0)
                {
                    aString1212 = aString1212.substring(0, aString1212.length() - 1);
                    aBoolean1223 = true;
                }
                if(j == 13 || j == 10)
                {
                    aBoolean1256 = false;
                    aBoolean1223 = true;
                    if(anInt1064 == 1)
                    {
                        long l = TextTools.stringToLong(aString1212);
                        socialListLogic((byte)68, l);
                    }
                    if(anInt1064 == 2 && amt_friendhashes > 0)
                    {
                        long l1 = TextTools.stringToLong(aString1212);
                        removeFriend(false, l1);
                    }
                    if(anInt1064 == 3 && aString1212.length() > 0)
                    {
                        gameBuffer.putPacket(126);
                        gameBuffer.put(0);
                        int k = gameBuffer.offset;
                        gameBuffer.putQword(aLong953);
                        ChatUtils.method526(aString1212, aBoolean1277, gameBuffer);
                        gameBuffer.endVarByte(gameBuffer.offset - k, (byte)0);
                        aString1212 = ChatUtils.method527(aString1212, 0);
                        aString1212 = Censor.censor(aString1212, 0);
                        pushMessage(aString1212, 6, TextTools.formatUsername(-45804, TextTools.longToString(aLong953, (byte)-99)), aBoolean991);
                        if(anInt845 == 2)
                        {
                            anInt845 = 1;
                            updatetoolbar = true;
                            gameBuffer.putPacket(95);
                            gameBuffer.put(anInt1287);
                            gameBuffer.put(anInt845);
                            gameBuffer.put(anInt1248);
                        }
                    }
                    if(anInt1064 == 4 && amt_ignorehashes < 100)
                    {
                        long l2 = TextTools.stringToLong(aString1212);
                        method113(l2, 4);
                    }
                    if(anInt1064 == 5 && amt_ignorehashes > 0)
                    {
                        long l3 = TextTools.stringToLong(aString1212);
                        method122(3, l3);
                    }
                }
            } else
            if(anInt1225 == 1)
            {
                if(j >= 48 && j <= 57 && aString1004.length() < 10)
                {
                    aString1004 += (char)j;
                    aBoolean1223 = true;
                }
                if(j == 8 && aString1004.length() > 0)
                {
                    aString1004 = aString1004.substring(0, aString1004.length() - 1);
                    aBoolean1223 = true;
                }
                if(j == 13 || j == 10)
                {
                    if(aString1004.length() > 0)
                    {
                        int i1 = 0;
                        try
                        {
                            i1 = Integer.parseInt(aString1004);
                        }
                        catch(Exception _ex) { }
                        gameBuffer.putPacket(208);
                        gameBuffer.putDword(i1);
                    }
                    anInt1225 = 0;
                    aBoolean1223 = true;
                }
            } else
            if(anInt1225 == 2)
            {
                if(j >= 32 && j <= 122 && aString1004.length() < 12)
                {
                    aString1004 += (char)j;
                    aBoolean1223 = true;
                }
                if(j == 8 && aString1004.length() > 0)
                {
                    aString1004 = aString1004.substring(0, aString1004.length() - 1);
                    aBoolean1223 = true;
                }
                if(j == 13 || j == 10)
                {
                    if(aString1004.length() > 0)
                    {
                        gameBuffer.putPacket(60);
                        gameBuffer.putQword(TextTools.stringToLong(aString1004));
                    }
                    anInt1225 = 0;
                    aBoolean1223 = true;
                }
            } else
            if(anInt1276 == -1)
            {
                if(j >= 32 && j <= 122 && aString887.length() < 80)
                {
                    aString887 += (char)j;
                    aBoolean1223 = true;
                }
                if(j == 8 && aString887.length() > 0)
                {
                    aString887 = aString887.substring(0, aString887.length() - 1);
                    aBoolean1223 = true;
                }
                if((j == 13 || j == 10) && aString887.length() > 0)
                {
                    if(rights == 2)
                    {
                        if(aString887.equals("::clientdrop"))
                            connectionLost(-670);
                        if(aString887.equals("::lag"))
                            printInfo();
                        if(aString887.equals("::prefetchmusic")) {
                            for(int j1 = 0; j1 < ondemandhandler.getAmountArchives(2); j1++)
                                ondemandhandler.setArchivePriority(2, j1, (byte)1);

                        }
                        if(aString887.equals("::fpson"))
                            drawfps = true;
                        if(aString887.equals("::fpsoff"))
                            drawfps = false;
                        if(aString887.equals("::noclip"))
                        {
                            for(int k1 = 0; k1 < 4; k1++)
                            {
                                for(int i2 = 1; i2 < 103; i2++)
                                {
                                    for(int k2 = 1; k2 < 103; k2++)
                                        planeFlags[k1].flagBuffer[i2][k2] = 0;

                                }

                            }

                        }
                    }
                    if(aString887.startsWith("::"))
                    {
                        gameBuffer.putPacket(103);
                        gameBuffer.put(aString887.length() - 1);
                        gameBuffer.putString(aString887.substring(2));
                    } else
                    {
                        String s = aString887.toLowerCase();
                        int j2 = 0;
                        if(s.startsWith("yellow:"))
                        {
                            j2 = 0;
                            aString887 = aString887.substring(7);
                        } else
                        if(s.startsWith("red:"))
                        {
                            j2 = 1;
                            aString887 = aString887.substring(4);
                        } else
                        if(s.startsWith("green:"))
                        {
                            j2 = 2;
                            aString887 = aString887.substring(6);
                        } else
                        if(s.startsWith("cyan:"))
                        {
                            j2 = 3;
                            aString887 = aString887.substring(5);
                        } else
                        if(s.startsWith("purple:"))
                        {
                            j2 = 4;
                            aString887 = aString887.substring(7);
                        } else
                        if(s.startsWith("white:"))
                        {
                            j2 = 5;
                            aString887 = aString887.substring(6);
                        } else
                        if(s.startsWith("flash1:"))
                        {
                            j2 = 6;
                            aString887 = aString887.substring(7);
                        } else
                        if(s.startsWith("flash2:"))
                        {
                            j2 = 7;
                            aString887 = aString887.substring(7);
                        } else
                        if(s.startsWith("flash3:"))
                        {
                            j2 = 8;
                            aString887 = aString887.substring(7);
                        } else
                        if(s.startsWith("glow1:"))
                        {
                            j2 = 9;
                            aString887 = aString887.substring(6);
                        } else
                        if(s.startsWith("glow2:"))
                        {
                            j2 = 10;
                            aString887 = aString887.substring(6);
                        } else
                        if(s.startsWith("glow3:"))
                        {
                            j2 = 11;
                            aString887 = aString887.substring(6);
                        }
                        s = aString887.toLowerCase();
                        int i3 = 0;
                        if(s.startsWith("wave:"))
                        {
                            i3 = 1;
                            aString887 = aString887.substring(5);
                        } else
                        if(s.startsWith("wave2:"))
                        {
                            i3 = 2;
                            aString887 = aString887.substring(6);
                        } else
                        if(s.startsWith("shake:"))
                        {
                            i3 = 3;
                            aString887 = aString887.substring(6);
                        } else
                        if(s.startsWith("scroll:"))
                        {
                            i3 = 4;
                            aString887 = aString887.substring(7);
                        } else
                        if(s.startsWith("slide:"))
                        {
                            i3 = 5;
                            aString887 = aString887.substring(6);
                        }
                        gameBuffer.putPacket(4);
                        gameBuffer.put(0);
                        int j3 = gameBuffer.offset;
                        gameBuffer.putByteB(i3);
                        gameBuffer.putByteB(j2);
                        aClass30_Sub2_Sub2_834.offset = 0;
                        ChatUtils.method526(aString887, aBoolean1277, aClass30_Sub2_Sub2_834);
                        gameBuffer.putReverse128(aClass30_Sub2_Sub2_834.payload, 0, aClass30_Sub2_Sub2_834.offset);
                        gameBuffer.endVarByte(gameBuffer.offset - j3, (byte)0);
                        aString887 = ChatUtils.method527(aString887, 0);
                        aString887 = Censor.censor(aString887, 0);
                        localPlayer.chat_txt = aString887;
                        localPlayer.anInt1513 = j2;
                        localPlayer.anInt1531 = i3;
                        localPlayer.anInt1535 = 150;
                        if(rights == 2)
                            pushMessage(((Mob) (localPlayer)).chat_txt, 2, "@cr2@" + localPlayer.name, aBoolean991);
                        else
                        if(rights == 1)
                            pushMessage(((Mob) (localPlayer)).chat_txt, 2, "@cr1@" + localPlayer.name, aBoolean991);
                        else
                            pushMessage(((Mob) (localPlayer)).chat_txt, 2, localPlayer.name, aBoolean991);
                        if(anInt1287 == 2)
                        {
                            anInt1287 = 3;
                            updatetoolbar = true;
                            gameBuffer.putPacket(95);
                            gameBuffer.put(anInt1287);
                            gameBuffer.put(anInt845);
                            gameBuffer.put(anInt1248);
                        }
                    }
                    aString887 = "";
                    aBoolean1223 = true;
                }
            }
        } while(true);
    }

    public void method74(int i, int j, int k)
    {
        if(k != anInt838)
            anInt838 = packetencryption.poll();
        int l = 0;
        for(int i1 = 0; i1 < 100; i1++)
        {
            if(msgbody_stack[i1] == null)
                continue;
            int j1 = msgtype_stack[i1];
            int k1 = (70 - l * 14) + anInt1089 + 4;
            if(k1 < -20)
                break;
            String s = msgprefix_stack[i1];
            boolean flag = false;
            if(s != null && s.startsWith("@cr1@"))
            {
                s = s.substring(5);
                boolean flag1 = true;
            }
            if(s != null && s.startsWith("@cr2@"))
            {
                s = s.substring(5);
                byte byte0 = 2;
            }
            if(j1 == 0)
                l++;
            if((j1 == 1 || j1 == 2) && (j1 == 1 || anInt1287 == 0 || anInt1287 == 1 && onFriendsList(false, s)))
            {
                if(j > k1 - 14 && j <= k1 && !s.equals(localPlayer.name))
                {
                    if(rights >= 1)
                    {
                        interfacestringstack[anInt1133] = "Report abuse @whi@" + s;
                        interfaceopcodestack[anInt1133] = 606;
                        anInt1133++;
                    }
                    interfacestringstack[anInt1133] = "Add ignore @whi@" + s;
                    interfaceopcodestack[anInt1133] = 42;
                    anInt1133++;
                    interfacestringstack[anInt1133] = "Add friend @whi@" + s;
                    interfaceopcodestack[anInt1133] = 337;
                    anInt1133++;
                }
                l++;
            }
            if((j1 == 3 || j1 == 7) && anInt1195 == 0 && (j1 == 7 || anInt845 == 0 || anInt845 == 1 && onFriendsList(false, s)))
            {
                if(j > k1 - 14 && j <= k1)
                {
                    if(rights >= 1)
                    {
                        interfacestringstack[anInt1133] = "Report abuse @whi@" + s;
                        interfaceopcodestack[anInt1133] = 606;
                        anInt1133++;
                    }
                    interfacestringstack[anInt1133] = "Add ignore @whi@" + s;
                    interfaceopcodestack[anInt1133] = 42;
                    anInt1133++;
                    interfacestringstack[anInt1133] = "Add friend @whi@" + s;
                    interfaceopcodestack[anInt1133] = 337;
                    anInt1133++;
                }
                l++;
            }
            if(j1 == 4 && (anInt1248 == 0 || anInt1248 == 1 && onFriendsList(false, s)))
            {
                if(j > k1 - 14 && j <= k1)
                {
                    interfacestringstack[anInt1133] = "Accept trade @whi@" + s;
                    interfaceopcodestack[anInt1133] = 484;
                    anInt1133++;
                }
                l++;
            }
            if((j1 == 5 || j1 == 6) && anInt1195 == 0 && anInt845 < 2)
                l++;
            if(j1 == 8 && (anInt1248 == 0 || anInt1248 == 1 && onFriendsList(false, s)))
            {
                if(j > k1 - 14 && j <= k1)
                {
                    interfacestringstack[anInt1133] = "Accept challenge @whi@" + s;
                    interfaceopcodestack[anInt1133] = 6;
                    anInt1133++;
                }
                l++;
            }
        }

    }

    public void handleWidgetActionCode(Widget widget)
    {
        int actionCode = widget.actionCode;
        if(actionCode >= 1 && actionCode <= 100 || actionCode >= 701 && actionCode <= 800)
        {
            if(actionCode == 1 && anInt900 == 0)
            {
                widget.inactiveText = "Loading friend list";
                widget.fieldType = 0;
                return;
            }
            if(actionCode == 1 && anInt900 == 1)
            {
                widget.inactiveText = "Connecting to friendserver";
                widget.fieldType = 0;
                return;
            }
            if(actionCode == 2 && anInt900 != 2)
            {
                widget.inactiveText = "Please wait...";
                widget.fieldType = 0;
                return;
            }
            int k = amt_friendhashes;
            if(anInt900 != 2)
                k = 0;
            if(actionCode > 700)
                actionCode -= 601;
            else
                actionCode--;
            if(actionCode >= k)
            {
                widget.inactiveText = "";
                widget.fieldType = 0;
                return;
            } else
            {
                widget.inactiveText = friendusernames[actionCode];
                widget.fieldType = 1;
                return;
            }
        }
        if(actionCode >= 101 && actionCode <= 200 || actionCode >= 801 && actionCode <= 900)
        {
            int l = amt_friendhashes;
            if(anInt900 != 2)
                l = 0;
            if(actionCode > 800)
                actionCode -= 701;
            else
                actionCode -= 101;
            if(actionCode >= l)
            {
                widget.inactiveText = "";
                widget.fieldType = 0;
                return;
            }
            if(anIntArray826[actionCode] == 0)
                widget.inactiveText = "@red@Offline";
            else
            if(anIntArray826[actionCode] == nodeid)
                widget.inactiveText = "@gre@World-" + (anIntArray826[actionCode] - 9);
            else
                widget.inactiveText = "@yel@World-" + (anIntArray826[actionCode] - 9);
            widget.fieldType = 1;
            return;
        }
        if(actionCode == 203)
        {
            int i1 = amt_friendhashes;
            if(anInt900 != 2)
                i1 = 0;
            widget.currentHeight = i1 * 15 + 20;
            if(widget.currentHeight <= widget.height)
                widget.currentHeight = widget.height + 1;
            return;
        }
        if(actionCode >= 401 && actionCode <= 500)
        {
            if((actionCode -= 401) == 0 && anInt900 == 0)
            {
                widget.inactiveText = "Loading ignore list";
                widget.fieldType = 0;
                return;
            }
            if(actionCode == 1 && anInt900 == 0)
            {
                widget.inactiveText = "Please wait...";
                widget.fieldType = 0;
                return;
            }
            int j1 = amt_ignorehashes;
            if(anInt900 == 0)
                j1 = 0;
            if(actionCode >= j1)
            {
                widget.inactiveText = "";
                widget.fieldType = 0;
                return;
            } else
            {
                widget.inactiveText = TextTools.formatUsername(-45804, TextTools.longToString(ignore_hashes[actionCode], (byte)-99));
                widget.fieldType = 1;
                return;
            }
        }
        if(actionCode == 503)
        {
            widget.currentHeight = amt_ignorehashes * 15 + 20;
            if(widget.currentHeight <= widget.height)
                widget.currentHeight = widget.height + 1;
            return;
        }
        if(actionCode == 327)
        {
            widget.rotationAngleX = 150;
            widget.rotationAngleY = (int)(Math.sin((double)loopCycle / 40D) * 256D) & 0x7ff;
            if(aBoolean1031)
            {
                for(int k1 = 0; k1 < 7; k1++)
                {
                    int l1 = characterModelIds[k1];
                    if(l1 >= 0 && !IdentityKit.identityKits[l1].method537((byte)2))
                        return;
                }

                aBoolean1031 = false;
                Model characterModels[] = new Model[7];
                int i2 = 0;
                for(int j2 = 0; j2 < 7; j2++)
                {
                    int k2 = characterModelIds[j2];
                    if(k2 >= 0)
                        characterModels[i2++] = IdentityKit.identityKits[k2].method538(false);
                }

                Model charmodel = new Model(characterModels, i2);
                for(int l2 = 0; l2 < 5; l2++)
                    if(characterColorIds[l2] != 0)
                    {
                        charmodel.setTriangleColors(anIntArrayArray1003[l2][0], anIntArrayArray1003[l2][characterColorIds[l2]]);
                        if(l2 == 1)
                            charmodel.setTriangleColors(anIntArray1204[0], anIntArray1204[characterColorIds[l2]]);
                    }
                charmodel.setVertexTriangleGroups();
                charmodel.applyAnimationFrame(AnimSequence.animationsequences[((Mob) (localPlayer)).standAnimation].anIntArray353[0]);
                charmodel.setLightingVectors(64, 850, -30, -50, -30, true);
                widget.unactiveAnimFetchType = 5;
                widget.unactiveAnimModelId = 0;
                Widget.cacheModel(0, 5, charmodel);
            }
            return;
        }
        if(actionCode == 324)
        {
            if(aClass30_Sub2_Sub1_Sub1_931 == null)
            {
                aClass30_Sub2_Sub1_Sub1_931 = widget.inactiveSprite;
                aClass30_Sub2_Sub1_Sub1_932 = widget.activeSprite;
            }
            if(aBoolean1047)
            {
                widget.inactiveSprite = aClass30_Sub2_Sub1_Sub1_932;
                return;
            } else
            {
                widget.inactiveSprite = aClass30_Sub2_Sub1_Sub1_931;
                return;
            }
        }
        if(actionCode == 325)
        {
            if(aClass30_Sub2_Sub1_Sub1_931 == null)
            {
                aClass30_Sub2_Sub1_Sub1_931 = widget.inactiveSprite;
                aClass30_Sub2_Sub1_Sub1_932 = widget.activeSprite;
            }
            if(aBoolean1047)
            {
                widget.inactiveSprite = aClass30_Sub2_Sub1_Sub1_931;
                return;
            } else
            {
                widget.inactiveSprite = aClass30_Sub2_Sub1_Sub1_932;
                return;
            }
        }
        if(actionCode == 600)
        {
            widget.inactiveText = aString881;
            if(loopCycle % 20 < 10)
            {
                widget.inactiveText += "|";
                return;
            } else
            {
                widget.inactiveText += " ";
                return;
            }
        }
        if(actionCode == 613)
            if(rights >= 1)
            {
                if(aBoolean1158)
                {
                    widget.inactiveTextColor = 0xff0000;
                    widget.inactiveText = "Moderator option: Mute player for 48 hours: <ON>";
                } else
                {
                    widget.inactiveTextColor = 0xffffff;
                    widget.inactiveText = "Moderator option: Mute player for 48 hours: <OFF>";
                }
            } else
            {
                widget.inactiveText = "";
            }
        if(actionCode == 650 || actionCode == 655)
            if(anInt1193 != 0)
            {
                String s;
                if(pastlaginamountdays == 0)
                    s = "earlier today";
                else
                if(pastlaginamountdays == 1)
                    s = "yesterday";
                else
                    s = pastlaginamountdays + " days ago";
                widget.inactiveText = "You last logged in " + s + " from: " + Signlink.lookupAddress;
            } else
            {
                widget.inactiveText = "";
            }
        if(actionCode == 651)
        {
            if(anInt1154 == 0)
            {
                widget.inactiveText = "0 unread messages";
                widget.inactiveTextColor = 0xffff00;
            }
            if(anInt1154 == 1)
            {
                widget.inactiveText = "1 unread message";
                widget.inactiveTextColor = 65280;
            }
            if(anInt1154 > 1)
            {
                widget.inactiveText = anInt1154 + " unread messages";
                widget.inactiveTextColor = 65280;
            }
        }
        if(actionCode == 652)
            if(anInt1167 == 201)
            {
                if(anInt1120 == 1)
                    widget.inactiveText = "@yel@This is a non-members world: @whi@Since you are a member we";
                else
                    widget.inactiveText = "";
            } else
            if(anInt1167 == 200)
            {
                widget.inactiveText = "You have not yet set any password recovery questions.";
            } else
            {
                String s1;
                if(anInt1167 == 0)
                    s1 = "Earlier today";
                else
                if(anInt1167 == 1)
                    s1 = "Yesterday";
                else
                    s1 = anInt1167 + " days ago";
                widget.inactiveText = s1 + " you changed your recovery questions";
            }
        if(actionCode == 653)
            if(anInt1167 == 201)
            {
                if(anInt1120 == 1)
                    widget.inactiveText = "@whi@recommend you use a members world instead. You may use";
                else
                    widget.inactiveText = "";
            } else
            if(anInt1167 == 200)
                widget.inactiveText = "We strongly recommend you do so now to secure your account.";
            else
                widget.inactiveText = "If you do not remember making this change then cancel it immediately";
        if(actionCode == 654)
        {
            if(anInt1167 == 201)
                if(anInt1120 == 1)
                {
                    widget.inactiveText = "@whi@this world but member benefits are unavailable whilst here.";
                    return;
                } else
                {
                    widget.inactiveText = "";
                    return;
                }
            if(anInt1167 == 200)
            {
                widget.inactiveText = "Do this from the 'account management' area on our front webpage";
                return;
            }
            widget.inactiveText = "Do this from the 'account management' area on our front webpage";
        }
    }

    public void method76(byte byte0)
    {
        if(anInt1195 == 0)
            return;
        BitmapFont class30_sub2_sub1_sub4 = p12Font;
        if(byte0 != aByte1274)
            aBoolean1231 = !aBoolean1231;
        int i = 0;
        if(anInt1104 != 0)
            i = 1;
        for(int j = 0; j < 100; j++)
            if(msgbody_stack[j] != null)
            {
                int k = msgtype_stack[j];
                String s = msgprefix_stack[j];
                byte byte1 = 0;
                if(s != null && s.startsWith("@cr1@"))
                {
                    s = s.substring(5);
                    byte1 = 1;
                }
                if(s != null && s.startsWith("@cr2@"))
                {
                    s = s.substring(5);
                    byte1 = 2;
                }
                if((k == 3 || k == 7) && (k == 7 || anInt845 == 0 || anInt845 == 1 && onFriendsList(false, s)))
                {
                    int l = 329 - i * 13;
                    int k1 = 4;
                    class30_sub2_sub1_sub4.drawText(0, "From", l, 822, k1);
                    class30_sub2_sub1_sub4.drawText(65535, "From", l - 1, 822, k1);
                    k1 += class30_sub2_sub1_sub4.widthFontMetrics(anInt1116, "From ");
                    if(byte1 == 1)
                    {
                        mod_icons[0].renderImage(k1, 16083, l - 12);
                        k1 += 14;
                    }
                    if(byte1 == 2)
                    {
                        mod_icons[1].renderImage(k1, 16083, l - 12);
                        k1 += 14;
                    }
                    class30_sub2_sub1_sub4.drawText(0, s + ": " + msgbody_stack[j], l, 822, k1);
                    class30_sub2_sub1_sub4.drawText(65535, s + ": " + msgbody_stack[j], l - 1, 822, k1);
                    if(++i >= 5)
                        return;
                }
                if(k == 5 && anInt845 < 2)
                {
                    int i1 = 329 - i * 13;
                    class30_sub2_sub1_sub4.drawText(0, msgbody_stack[j], i1, 822, 4);
                    class30_sub2_sub1_sub4.drawText(65535, msgbody_stack[j], i1 - 1, 822, 4);
                    if(++i >= 5)
                        return;
                }
                if(k == 6 && anInt845 < 2)
                {
                    int j1 = 329 - i * 13;
                    class30_sub2_sub1_sub4.drawText(0, "To " + s + ": " + msgbody_stack[j], j1, 822, 4);
                    class30_sub2_sub1_sub4.drawText(65535, "To " + s + ": " + msgbody_stack[j], j1 - 1, 822, 4);
                    if(++i >= 5)
                        return;
                }
            }

    }

    public void pushMessage(String s, int i, String s1, boolean flag)
    {
        if(flag)
            return;
        if(i == 0 && anInt1042 != -1)
        {
            aString844 = s;
            super.anInt26 = 0;
        }
        if(anInt1276 == -1)
            aBoolean1223 = true;
        for(int j = 99; j > 0; j--)
        {
            msgtype_stack[j] = msgtype_stack[j - 1];
            msgprefix_stack[j] = msgprefix_stack[j - 1];
            msgbody_stack[j] = msgbody_stack[j - 1];
        }
        msgtype_stack[0] = i;
        msgprefix_stack[0] = s1;
        msgbody_stack[0] = s;
    }

    public void handleTabs(int i)
    {
        i = 72 / i;
        if(super.anInt26 == 1)
        {
            if(super.pressedX >= 539 && super.pressedX <= 573 && super.pressedY >= 169 && super.pressedY < 205 && tab_interfaces[0] != -1)
            {
                aBoolean1153 = true;
                current_tab = 0;
                update_tabs = true;
            }
            if(super.pressedX >= 569 && super.pressedX <= 599 && super.pressedY >= 168 && super.pressedY < 205 && tab_interfaces[1] != -1)
            {
                aBoolean1153 = true;
                current_tab = 1;
                update_tabs = true;
            }
            if(super.pressedX >= 597 && super.pressedX <= 627 && super.pressedY >= 168 && super.pressedY < 205 && tab_interfaces[2] != -1)
            {
                aBoolean1153 = true;
                current_tab = 2;
                update_tabs = true;
            }
            if(super.pressedX >= 625 && super.pressedX <= 669 && super.pressedY >= 168 && super.pressedY < 203 && tab_interfaces[3] != -1)
            {
                aBoolean1153 = true;
                current_tab = 3;
                update_tabs = true;
            }
            if(super.pressedX >= 666 && super.pressedX <= 696 && super.pressedY >= 168 && super.pressedY < 205 && tab_interfaces[4] != -1)
            {
                aBoolean1153 = true;
                current_tab = 4;
                update_tabs = true;
            }
            if(super.pressedX >= 694 && super.pressedX <= 724 && super.pressedY >= 168 && super.pressedY < 205 && tab_interfaces[5] != -1)
            {
                aBoolean1153 = true;
                current_tab = 5;
                update_tabs = true;
            }
            if(super.pressedX >= 722 && super.pressedX <= 756 && super.pressedY >= 169 && super.pressedY < 205 && tab_interfaces[6] != -1)
            {
                aBoolean1153 = true;
                current_tab = 6;
                update_tabs = true;
            }
            if(super.pressedX >= 540 && super.pressedX <= 574 && super.pressedY >= 466 && super.pressedY < 502 && tab_interfaces[7] != -1)
            {
                aBoolean1153 = true;
                current_tab = 7;
                update_tabs = true;
            }
            if(super.pressedX >= 572 && super.pressedX <= 602 && super.pressedY >= 466 && super.pressedY < 503 && tab_interfaces[8] != -1)
            {
                aBoolean1153 = true;
                current_tab = 8;
                update_tabs = true;
            }
            if(super.pressedX >= 599 && super.pressedX <= 629 && super.pressedY >= 466 && super.pressedY < 503 && tab_interfaces[9] != -1)
            {
                aBoolean1153 = true;
                current_tab = 9;
                update_tabs = true;
            }
            if(super.pressedX >= 627 && super.pressedX <= 671 && super.pressedY >= 467 && super.pressedY < 502 && tab_interfaces[10] != -1)
            {
                aBoolean1153 = true;
                current_tab = 10;
                update_tabs = true;
            }
            if(super.pressedX >= 669 && super.pressedX <= 699 && super.pressedY >= 466 && super.pressedY < 503 && tab_interfaces[11] != -1)
            {
                aBoolean1153 = true;
                current_tab = 11;
                update_tabs = true;
            }
            if(super.pressedX >= 696 && super.pressedX <= 726 && super.pressedY >= 466 && super.pressedY < 503 && tab_interfaces[12] != -1)
            {
                aBoolean1153 = true;
                current_tab = 12;
                update_tabs = true;
            }
            if(super.pressedX >= 724 && super.pressedX <= 758 && super.pressedY >= 466 && super.pressedY < 502 && tab_interfaces[13] != -1)
            {
                aBoolean1153 = true;
                current_tab = 13;
                update_tabs = true;
            }
        }
    }

    public void method79(int i)
    {
        if(chat_imagefetcher != null)
            return;
        method118(3);
        super.appletImageFetcher = null;
        logo_imagefetcher = null;
        bottomleftmid_imagefetcher = null;
        aClass15_1109 = null;
        titletopleft_imagefetcher = null;
        titletopright_imagefetcher = null;
        aClass15_1112 = null;
        aClass15_1113 = null;
        aClass15_1114 = null;
        aClass15_1115 = null;
        chat_imagefetcher = new ImageFetcher(479, 96, getDrawableComponent(), 0);
        aClass15_1164 = new ImageFetcher(172, 156, getDrawableComponent(), 0);
        BasicRasterizer.resetPixelBuffer();
        mapback.renderImage(0, 16083, 0);
        aClass15_1163 = new ImageFetcher(190, 261, getDrawableComponent(), 0);
        toplefttext_imagefetcher = new ImageFetcher(512, 334, getDrawableComponent(), 0);
        BasicRasterizer.resetPixelBuffer();
        toolbartext_imagefetcher = new ImageFetcher(496, 50, getDrawableComponent(), 0);
        if(i != 1)
            loadClient();
        aClass15_1124 = new ImageFetcher(269, 37, getDrawableComponent(), 0);
        aClass15_1125 = new ImageFetcher(249, 45, getDrawableComponent(), 0);
        paintRequested = true;
    }

    public String method80(boolean flag)
    {
        isOnlineGame &= flag;
        if(Signlink.applet != null)
            return Signlink.applet.getDocumentBase().getHost().toLowerCase();
        if(super.appletFrame != null)
            return "runescape.com";
        else
            return super.getDocumentBase().getHost().toLowerCase();
    }

    public void method81(DirectColorSprite class30_sub2_sub1_sub1, int i, int j, int k)
    {
        int l = k * k + j * j;
        if(i >= 0)
            loadClient();
        if(l > 4225 && l < 0x15f90)
        {
            int yaw = cameraYaw + anInt1209 & 0x7ff;
            int sine = Model.sinetable[yaw];
            int cosine = Model.cosinetable[yaw];
            sine = (sine * 256) / (anInt1170 + 256);
            cosine = (cosine * 256) / (anInt1170 + 256);
            int l1 = j * sine + k * cosine >> 16;
            int i2 = j * cosine - k * sine >> 16;
            double d = Math.atan2(l1, i2);
            int j2 = (int)(Math.sin(d) * 63D);
            int k2 = (int)(Math.cos(d) * 57D);
            mapedge.draw(83 - k2 - 20, 15, 20, 15, 41960, 256, 20, d, (94 + j2 + 4) - 10);
            return;
        } else
        {
            drawOnMinimap(class30_sub2_sub1_sub1, k, j, false);
            return;
        }
    }

    public void method82(int i)
    {
        if(anInt1086 != 0)
            return;
        interfacestringstack[0] = "Cancel";
        interfaceopcodestack[0] = 1107;
        anInt1133 = 1;
        method129(false);
        currentWidgetId = 0;
        if(super.newMouseX > 4 && super.newMouseY > 4 && super.newMouseX < 516 && super.newMouseY < 338)
            if(anInt857 != -1)
                handleInterfaceOptions(4, 13037, Widget.widgets[anInt857], super.newMouseX, 4, super.newMouseY, 0);
            else
                method71(33660);
        if(currentWidgetId != anInt1026)
            anInt1026 = currentWidgetId;
        currentWidgetId = 0;
        if(super.newMouseX > 553 && super.newMouseY > 205 && super.newMouseX < 743 && super.newMouseY < 466)
            if(anInt1189 != -1)
                handleInterfaceOptions(553, 13037, Widget.widgets[anInt1189], super.newMouseX, 205, super.newMouseY, 0);
            else
            if(tab_interfaces[current_tab] != -1)
                handleInterfaceOptions(553, 13037, Widget.widgets[tab_interfaces[current_tab]], super.newMouseX, 205, super.newMouseY, 0);
        if(currentWidgetId != anInt1048)
        {
            aBoolean1153 = true;
            anInt1048 = currentWidgetId;
        }
        currentWidgetId = 0;
        if(super.newMouseX > 17 && super.newMouseY > 357 && super.newMouseX < 496 && super.newMouseY < 453)
            if(anInt1276 != -1)
                handleInterfaceOptions(17, 13037, Widget.widgets[anInt1276], super.newMouseX, 357, super.newMouseY, 0);
            else
            if(super.newMouseY < 434 && super.newMouseX < 426)
                method74(super.newMouseX - 17, super.newMouseY - 357, 9);
        if(anInt1276 != -1 && currentWidgetId != anInt1039)
        {
            aBoolean1223 = true;
            anInt1039 = currentWidgetId;
        }
        boolean flag = false;
        packetSize += i;
        while(!flag) 
        {
            flag = true;
            for(int j = 0; j < anInt1133 - 1; j++)
                if(interfaceopcodestack[j] < 1000 && interfaceopcodestack[j + 1] > 1000)
                {
                    String s = interfacestringstack[j];
                    interfacestringstack[j] = interfacestringstack[j + 1];
                    interfacestringstack[j + 1] = s;
                    int k = interfaceopcodestack[j];
                    interfaceopcodestack[j] = interfaceopcodestack[j + 1];
                    interfaceopcodestack[j + 1] = k;
                    k = interfacestack_a[j];
                    interfacestack_a[j] = interfacestack_a[j + 1];
                    interfacestack_a[j + 1] = k;
                    k = interfacestack_b[j];
                    interfacestack_b[j] = interfacestack_b[j + 1];
                    interfacestack_b[j + 1] = k;
                    k = interfacestack_c[j];
                    interfacestack_c[j] = interfacestack_c[j + 1];
                    interfacestack_c[j + 1] = k;
                    flag = false;
                }

        }
    }

    public int method83(boolean junk, int i, int j, int k)
    {
		/* Remove bits from byte */
        int l = 256 - k;
        return ((i & 0xff00ff) * l + (j & 0xff00ff) * k & 0xff00ff00) + ((i & 0xff00) * l + (j & 0xff00) * k & 0xff0000) >> 8;
    }

    public void handleLogin(String username, String password, boolean isReconnecting)
    {
        Signlink.errorMessage = username;
        try {
            if(!isReconnecting) {
                loginMessage0 = "";
                loginMessage1 = "Connecting to server...";
                drawTitleScreen(true, false);
            }
            bufferedConnection = new BufferedConnection(this, -978, createSocket(43594 + portOffset));
            long nameHash = TextTools.stringToLong(username);
            int hash = (int) (nameHash >> 16 & 31L);
            gameBuffer.offset = 0;
            gameBuffer.put(14);
            gameBuffer.put(hash);
            bufferedConnection.write(gameBuffer.payload, 0, 2);
            for(int j = 0; j < 8; j++)
                bufferedConnection.read(); 
            int loginState = bufferedConnection.read();
            int responseCode = loginState;
            if(loginState == 0) {
                bufferedConnection.read(inbuffer.payload, 0, 8);
                inbuffer.offset = 0;
                ssk = inbuffer.getQword();
                int seeds[] = new int[4];
                seeds[0] = (int)(Math.random() * 99999999D);
                seeds[1] = (int)(Math.random() * 99999999D);
                seeds[2] = (int)(ssk >> 32);
                seeds[3] = (int) ssk;
                gameBuffer.offset = 0;
                gameBuffer.put(10);
                gameBuffer.putDword(seeds[0]);
                gameBuffer.putDword(seeds[1]);
                gameBuffer.putDword(seeds[2]);
                gameBuffer.putDword(seeds[3]);
                gameBuffer.putDword(Signlink.uid);
                gameBuffer.putString(username);
                gameBuffer.putString(password);
                gameBuffer.applyRsa(publicKey, modulus);
                loginBuffer.offset = 0;
                if(isReconnecting)
                    loginBuffer.put(18);
                else
                    loginBuffer.put(16);
                loginBuffer.put(gameBuffer.offset + 36 + 1 + 1 + 2);
                loginBuffer.put(255);
                loginBuffer.putWord(317);
                loginBuffer.put(lowMemory ? 1 : 0);
                for(int j = 0; j < 9; j++)
                    loginBuffer.putDword(jaggrabArchiveCrcs[j]);
                loginBuffer.put(gameBuffer.payload, gameBuffer.offset, 0);
                gameBuffer.isaacCipher = new IsaacCipher(seeds);
                for(int j = 0; j < 4; j++)
                    seeds[j] += 50;
                packetencryption = new IsaacCipher(seeds);
                bufferedConnection.write(loginBuffer.payload, 0, loginBuffer.offset);
                loginState = bufferedConnection.read();
            }
            if(loginState == 1) {
                try {
                    Thread.sleep(2000L);
                }
                catch(Exception ex) { 
                }
                handleLogin(username, password, isReconnecting);
                return;
            }
            if(loginState == 2)
            {
                rights = bufferedConnection.read();
                flagged = bufferedConnection.read() == 1;
                lastPressedTimestamp = 0L;
                anInt1022 = 0;
                monitor.position = 0;
                super.aBoolean17 = true;
                focusPacketToggle = true;
                isOnlineGame = true;
                gameBuffer.offset = 0;
                inbuffer.offset = 0;
                packetId = -1;
                anInt841 = -1;
                anInt842 = -1;
                anInt843 = -1;
                packetSize = 0;
                anInt1009 = 0;
                anInt1104 = 0;
                anInt1011 = 0;
                markertype = 0;
                anInt1133 = 0;
                aBoolean885 = false;
                super.idleCounter = 0;
                for(int j1 = 0; j1 < 100; j1++)
                    msgbody_stack[j1] = null;
                anInt1282 = 0;
                anInt1136 = 0;
                landscape_stage = 0;
                anInt1062 = 0;
                anInt1278 = (int)(Math.random() * 100D) - 50;
                anInt1131 = (int)(Math.random() * 110D) - 55;
                anInt896 = (int)(Math.random() * 80D) - 40;
                anInt1209 = (int)(Math.random() * 120D) - 60;
                anInt1170 = (int)(Math.random() * 30D) - 20;
                cameraYaw = (int)(Math.random() * 20D) - 10 & 0x7ff;
                anInt1021 = 0;
                anInt985 = -1;
                anInt1261 = 0;
                anInt1262 = 0;
                playerOffset = 0;
                anInt836 = 0;
                for(int i2 = 0; i2 < maxplayers; i2++)
                {
                    playerArray[i2] = null;
                    appearanceBuffers[i2] = null;
                }

                for(int k2 = 0; k2 < 16384; k2++)
                    npcs[k2] = null;

                localPlayer = playerArray[localPlayerIndex] = new Player();
                aClass19_1013.clear();
                gfxs_storage.clear();
                for(int l2 = 0; l2 < 4; l2++)
                {
                    for(int i3 = 0; i3 < 104; i3++)
                    {
                        for(int k3 = 0; k3 < 104; k3++)
                            grounditems[l2][i3][k3] = null;

                    }

                }

                aClass19_1179 = new Deque();
                anInt900 = 0;
                amt_friendhashes = 0;
                anInt1042 = -1;
                anInt1276 = -1;
                anInt857 = -1;
                anInt1189 = -1;
                anInt1018 = -1;
                aBoolean1149 = false;
                current_tab = 3;
                anInt1225 = 0;
                aBoolean885 = false;
                aBoolean1256 = false;
                aString844 = null;
                anInt1055 = 0;
                anInt1054 = -1;
                aBoolean1047 = true;
                method45(0);
                for(int j3 = 0; j3 < 5; j3++)
                    characterColorIds[j3] = 0;

                for(int l3 = 0; l3 < 5; l3++)
                {
                    aStringArray1127[l3] = null;
                    aBooleanArray1128[l3] = false;
                }

                anInt1175 = 0;
                anInt1134 = 0;
                anInt986 = 0;
                stepCounter = 0;
                anInt924 = 0;
                anInt1188 = 0;
                anInt1155 = 0;
                anInt1226 = 0;
                anInt941 = 0;
                anInt1260 = 0;
                method79(1);
                return;
            }
            if(loginState == 3)
            {
                loginMessage0 = "";
                loginMessage1 = "Invalid username or password.";
                return;
            }
            if(loginState == 4)
            {
                loginMessage0 = "Your account has been disabled.";
                loginMessage1 = "Please check your message-centre for details.";
                return;
            }
            if(loginState == 5)
            {
                loginMessage0 = "Your account is already logged in.";
                loginMessage1 = "Try again in 60 secs...";
                return;
            }
            if(loginState == 6)
            {
                loginMessage0 = "RuneScape has been updated!";
                loginMessage1 = "Please reload this page.";
                return;
            }
            if(loginState == 7)
            {
                loginMessage0 = "This world is full.";
                loginMessage1 = "Please use a different world.";
                return;
            }
            if(loginState == 8)
            {
                loginMessage0 = "Unable to connect.";
                loginMessage1 = "Login server offline.";
                return;
            }
            if(loginState == 9)
            {
                loginMessage0 = "Login limit exceeded.";
                loginMessage1 = "Too many connections from your address.";
                return;
            }
            if(loginState == 10)
            {
                loginMessage0 = "Unable to connect.";
                loginMessage1 = "Bad session id.";
                return;
            }
            if(loginState == 11)
            {
                loginMessage0 = "Login server rejected session.";
                loginMessage1 = "Please try again.";
                return;
            }
            if(loginState == 12)
            {
                loginMessage0 = "You need a members account to login to this world.";
                loginMessage1 = "Please subscribe, or use a different world.";
                return;
            }
            if(loginState == 13)
            {
                loginMessage0 = "Could not complete login.";
                loginMessage1 = "Please try using a different world.";
                return;
            }
            if(loginState == 14)
            {
                loginMessage0 = "The server is being updated.";
                loginMessage1 = "Please wait 1 minute and try again.";
                return;
            }
            if(loginState == 15)
            {
                isOnlineGame = true;
                gameBuffer.offset = 0;
                inbuffer.offset = 0;
                packetId = -1;
                anInt841 = -1;
                anInt842 = -1;
                anInt843 = -1;
                packetSize = 0;
                anInt1009 = 0;
                anInt1104 = 0;
                anInt1133 = 0;
                aBoolean885 = false;
                timestamp = System.currentTimeMillis();
                return;
            }
            if(loginState == 16)
            {
                loginMessage0 = "Login attempts exceeded.";
                loginMessage1 = "Please wait 1 minute and try again.";
                return;
            }
            if(loginState == 17)
            {
                loginMessage0 = "You are standing in a members-only area.";
                loginMessage1 = "To play on this world move to a free area first";
                return;
            }
            if(loginState == 20)
            {
                loginMessage0 = "Invalid loginserver requested";
                loginMessage1 = "Please try using a different world.";
                return;
            }
            if(loginState == 21)
            {
                for(int k1 = bufferedConnection.read(); k1 >= 0; k1--)
                {
                    loginMessage0 = "You have only just left another world";
                    loginMessage1 = "Your profile will be transferred in: " + k1 + " seconds";
                    drawTitleScreen(true, false);
                    try
                    {
                        Thread.sleep(1000L);
                    }
                    catch(Exception _ex) { }
                }

                handleLogin(username, password, isReconnecting);
                return;
            }
            if(loginState == -1) {
                if(responseCode == 0) {
                    if(loginAttempts < 2) {
                        try {
                            Thread.sleep(2000L);
                        }
                        catch(Exception ex) { 
                        }
                        loginAttempts++;
                        handleLogin(username, password, isReconnecting);
                        return;
                    } else {
                        loginMessage0 = "No response from loginserver";
                        loginMessage1 = "Please wait 1 minute and try again.";
                        return;
                    }
                } else {
                    loginMessage0 = "No response from server";
                    loginMessage1 = "Please try using a different world.";
                    return;
                }
            } else {
                System.out.println("response: " + loginState);
                loginMessage0 = "Unexpected server response";
                loginMessage1 = "Please try using a different world.";
                return;
            }
        }
        catch(IOException ex) {
            loginMessage0 = "";
        }
        loginMessage1 = "Error connecting to server.";
    }

    public boolean sendWalkPacket(int type, int j, int k, int l, int i1, int j1, int k1,
            int l1, int i2, int j2, boolean flag, int k2)
    {
        byte w = 104;
        byte h = 104;
        for(int l2 = 0; l2 < w; l2++)
        {
            for(int i3 = 0; i3 < h; i3++)
            {
                anIntArrayArray901[l2][i3] = 0;
                distancestrength[l2][i3] = 0x5f5e0ff;
            }

        }

        int stepx = j2;
        int stepy = j1;
        anIntArrayArray901[j2][j1] = 99;
        distancestrength[j2][j1] = 0;
        int steppos = 0;
        int stepoff = 0;
        walkingstepsx[steppos] = j2;
        walkingstepsy[steppos++] = j1;
        boolean flag1 = false;
        int steparrsize = walkingstepsx.length;
        int ai[][] = planeFlags[currentZ].flagBuffer;
        while(stepoff != steppos)
        {
            stepx = walkingstepsx[stepoff];
            stepy = walkingstepsy[stepoff];
            stepoff = (stepoff + 1) % steparrsize;
            if(stepx == k2 && stepy == i2)
            {
                flag1 = true;
                break;
            }
            if(i1 != 0)
            {
                if((i1 < 5 || i1 == 10) && planeFlags[currentZ].method219(k2, stepx, stepy, 0, j, i1 - 1, i2))
                {
                    flag1 = true;
                    break;
                }
                if(i1 < 10 && planeFlags[currentZ].method220(k2, i2, stepy, i1 - 1, j, stepx, 0))
                {
                    flag1 = true;
                    break;
                }
            }
            if(k1 != 0 && k != 0 && planeFlags[currentZ].method221((byte)1, i2, k2, stepx, k, l1, k1, stepy))
            {
                flag1 = true;
                break;
            }
            int l4 = distancestrength[stepx][stepy] + 1;
            if(stepx > 0 && anIntArrayArray901[stepx - 1][stepy] == 0 && (ai[stepx - 1][stepy] & 0x1280108) == 0)
            {
                walkingstepsx[steppos] = stepx - 1;
                walkingstepsy[steppos] = stepy;
                steppos = (steppos + 1) % steparrsize;
                anIntArrayArray901[stepx - 1][stepy] = 2;
                distancestrength[stepx - 1][stepy] = l4;
            }
            if(stepx < w - 1 && anIntArrayArray901[stepx + 1][stepy] == 0 && (ai[stepx + 1][stepy] & 0x1280180) == 0)
            {
                walkingstepsx[steppos] = stepx + 1;
                walkingstepsy[steppos] = stepy;
                steppos = (steppos + 1) % steparrsize;
                anIntArrayArray901[stepx + 1][stepy] = 8;
                distancestrength[stepx + 1][stepy] = l4;
            }
            if(stepy > 0 && anIntArrayArray901[stepx][stepy - 1] == 0 && (ai[stepx][stepy - 1] & 0x1280102) == 0)
            {
                walkingstepsx[steppos] = stepx;
                walkingstepsy[steppos] = stepy - 1;
                steppos = (steppos + 1) % steparrsize;
                anIntArrayArray901[stepx][stepy - 1] = 1;
                distancestrength[stepx][stepy - 1] = l4;
            }
            if(stepy < h - 1 && anIntArrayArray901[stepx][stepy + 1] == 0 && (ai[stepx][stepy + 1] & 0x1280120) == 0)
            {
                walkingstepsx[steppos] = stepx;
                walkingstepsy[steppos] = stepy + 1;
                steppos = (steppos + 1) % steparrsize;
                anIntArrayArray901[stepx][stepy + 1] = 4;
                distancestrength[stepx][stepy + 1] = l4;
            }
            if(stepx > 0 && stepy > 0 && anIntArrayArray901[stepx - 1][stepy - 1] == 0 && (ai[stepx - 1][stepy - 1] & 0x128010e) == 0 && (ai[stepx - 1][stepy] & 0x1280108) == 0 && (ai[stepx][stepy - 1] & 0x1280102) == 0)
            {
                walkingstepsx[steppos] = stepx - 1;
                walkingstepsy[steppos] = stepy - 1;
                steppos = (steppos + 1) % steparrsize;
                anIntArrayArray901[stepx - 1][stepy - 1] = 3;
                distancestrength[stepx - 1][stepy - 1] = l4;
            }
            if(stepx < w - 1 && stepy > 0 && anIntArrayArray901[stepx + 1][stepy - 1] == 0 && (ai[stepx + 1][stepy - 1] & 0x1280183) == 0 && (ai[stepx + 1][stepy] & 0x1280180) == 0 && (ai[stepx][stepy - 1] & 0x1280102) == 0)
            {
                walkingstepsx[steppos] = stepx + 1;
                walkingstepsy[steppos] = stepy - 1;
                steppos = (steppos + 1) % steparrsize;
                anIntArrayArray901[stepx + 1][stepy - 1] = 9;
                distancestrength[stepx + 1][stepy - 1] = l4;
            }
            if(stepx > 0 && stepy < h - 1 && anIntArrayArray901[stepx - 1][stepy + 1] == 0 && (ai[stepx - 1][stepy + 1] & 0x1280138) == 0 && (ai[stepx - 1][stepy] & 0x1280108) == 0 && (ai[stepx][stepy + 1] & 0x1280120) == 0)
            {
                walkingstepsx[steppos] = stepx - 1;
                walkingstepsy[steppos] = stepy + 1;
                steppos = (steppos + 1) % steparrsize;
                anIntArrayArray901[stepx - 1][stepy + 1] = 6;
                distancestrength[stepx - 1][stepy + 1] = l4;
            }
            if(stepx < w - 1 && stepy < h - 1 && anIntArrayArray901[stepx + 1][stepy + 1] == 0 && (ai[stepx + 1][stepy + 1] & 0x12801e0) == 0 && (ai[stepx + 1][stepy] & 0x1280180) == 0 && (ai[stepx][stepy + 1] & 0x1280120) == 0)
            {
                walkingstepsx[steppos] = stepx + 1;
                walkingstepsy[steppos] = stepy + 1;
                steppos = (steppos + 1) % steparrsize;
                anIntArrayArray901[stepx + 1][stepy + 1] = 12;
                distancestrength[stepx + 1][stepy + 1] = l4;
            }
        }
        anInt1264 = 0;
        if(!flag1)
        {
            if(flag)
            {
                int i5 = 100;
                for(int k5 = 1; k5 < 2; k5++)
                {
                    for(int i6 = k2 - k5; i6 <= k2 + k5; i6++)
                    {
                        for(int l6 = i2 - k5; l6 <= i2 + k5; l6++)
                            if(i6 >= 0 && l6 >= 0 && i6 < 104 && l6 < 104 && distancestrength[i6][l6] < i5)
                            {
                                i5 = distancestrength[i6][l6];
                                stepx = i6;
                                stepy = l6;
                                anInt1264 = 1;
                                flag1 = true;
                            }

                    }

                    if(flag1)
                        break;
                }

            }
            if(!flag1)
                return false;
        }
        stepoff = 0;
        walkingstepsx[stepoff] = stepx;
        walkingstepsy[stepoff++] = stepy;
        int l5;
        for(int j5 = l5 = anIntArrayArray901[stepx][stepy]; stepx != j2 || stepy != j1; j5 = anIntArrayArray901[stepx][stepy])
        {
            if(j5 != l5)
            {
                l5 = j5;
                walkingstepsx[stepoff] = stepx;
                walkingstepsy[stepoff++] = stepy;
            }
            if((j5 & 2) != 0)
                stepx++;
            else
            if((j5 & 8) != 0)
                stepx--;
            if((j5 & 1) != 0)
                stepy++;
            else
            if((j5 & 4) != 0)
                stepy--;
        }

        if(stepoff > 0)
        {
            int amtsteps = stepoff;
            if(amtsteps > 25)
                amtsteps = 25;
            stepoff--;
            int firstStepX = walkingstepsx[stepoff];
            int firstStepY = walkingstepsy[stepoff];
            stepCounter += amtsteps;
            if(stepCounter >= 92)
            {
                gameBuffer.putPacket(36);
                gameBuffer.putDword(0);
                stepCounter = 0;
            }
            if(type == 0)
            {
                gameBuffer.putPacket(164);
                gameBuffer.put(amtsteps + amtsteps + 3);
            }
            if(type == 1)
            {
                gameBuffer.putPacket(248);
                gameBuffer.put(amtsteps + amtsteps + 3 + 14);
            }
            if(type == 2)
            {
                gameBuffer.putPacket(98);
                gameBuffer.put(amtsteps + amtsteps + 3);
            }
            gameBuffer.putWordLE128(firstStepX + paletteX);
            anInt1261 = walkingstepsx[0];
            anInt1262 = walkingstepsy[0];
            for(int j7 = 1; j7 < amtsteps; j7++)
            {
                stepoff--;
                gameBuffer.put(walkingstepsx[stepoff] - firstStepX);
                gameBuffer.put(walkingstepsy[stepoff] - firstStepY);
            }

            gameBuffer.putWordLE(firstStepY + paletteY);
            gameBuffer.putByteA(super.activeKeycodes[5] != 1 ? 0 : 1);
            return true;
        }
        return type != 1;
    }

    public void parseNpcUpdateMasks(int i, ByteBuffer buffer0, boolean flag)
    {
        for(int j = 0; j < amtplayerupdatestack; j++)
        {
            int k = pFlagUpdateList[j];
            Npc class30_sub2_sub4_sub1_sub1 = npcs[k];
            int l = buffer0.getUbyte();
			/* Animation */
            if((l & 0x10) != 0)
            {
                int i1 = buffer0.getUwordLE();
                if(i1 == 65535)
                    i1 = -1;
                int i2 = buffer0.getUbyte();
                if(i1 == ((Mob) (class30_sub2_sub4_sub1_sub1)).animid_request && i1 != -1)
                {
                    int l2 = AnimSequence.animationsequences[i1].anInt365;
                    if(l2 == 1)
                    {
                        class30_sub2_sub4_sub1_sub1.anInt1527 = 0;
                        class30_sub2_sub4_sub1_sub1.anInt1528 = 0;
                        class30_sub2_sub4_sub1_sub1.animdelay_request = i2;
                        class30_sub2_sub4_sub1_sub1.anInt1530 = 0;
                    }
                    if(l2 == 2)
                        class30_sub2_sub4_sub1_sub1.anInt1530 = 0;
                } else
                if(i1 == -1 || ((Mob) (class30_sub2_sub4_sub1_sub1)).animid_request == -1 || AnimSequence.animationsequences[i1].anInt359 >= AnimSequence.animationsequences[((Mob) (class30_sub2_sub4_sub1_sub1)).animid_request].anInt359)
                {
                    class30_sub2_sub4_sub1_sub1.animid_request = i1;
                    class30_sub2_sub4_sub1_sub1.anInt1527 = 0;
                    class30_sub2_sub4_sub1_sub1.anInt1528 = 0;
                    class30_sub2_sub4_sub1_sub1.animdelay_request = i2;
                    class30_sub2_sub4_sub1_sub1.anInt1530 = 0;
                    class30_sub2_sub4_sub1_sub1.anInt1542 = ((Mob) (class30_sub2_sub4_sub1_sub1)).stack_position_mob;
                }
            }
            if((l & 8) != 0)
            {
                int j1 = buffer0.getByte128();
                int j2 = buffer0.getUbyteA();
                class30_sub2_sub4_sub1_sub1.pushHit(-35698, j2, j1, loopCycle);
                class30_sub2_sub4_sub1_sub1.anInt1532 = loopCycle + 300;
                class30_sub2_sub4_sub1_sub1.anInt1533 = buffer0.getByte128();
                class30_sub2_sub4_sub1_sub1.anInt1534 = buffer0.getUbyte();
            }
            if((l & 0x80) != 0)
            {
                class30_sub2_sub4_sub1_sub1.anInt1520 = buffer0.getUword();
                int k1 = buffer0.getDword();
                class30_sub2_sub4_sub1_sub1.anInt1524 = k1 >> 16;
                class30_sub2_sub4_sub1_sub1.anInt1523 = loopCycle + (k1 & 0xffff);
                class30_sub2_sub4_sub1_sub1.anInt1521 = 0;
                class30_sub2_sub4_sub1_sub1.anInt1522 = 0;
                if(((Mob) (class30_sub2_sub4_sub1_sub1)).anInt1523 > loopCycle)
                    class30_sub2_sub4_sub1_sub1.anInt1521 = -1;
                if(((Mob) (class30_sub2_sub4_sub1_sub1)).anInt1520 == 65535)
                    class30_sub2_sub4_sub1_sub1.anInt1520 = -1;
            }
            if((l & 0x20) != 0)
            {
                class30_sub2_sub4_sub1_sub1.anInt1502 = buffer0.getUword();
                if(((Mob) (class30_sub2_sub4_sub1_sub1)).anInt1502 == 65535)
                    class30_sub2_sub4_sub1_sub1.anInt1502 = -1;
            }
            if((l & 1) != 0)
            {
                class30_sub2_sub4_sub1_sub1.chat_txt = buffer0.getString();
                class30_sub2_sub4_sub1_sub1.anInt1535 = 100;
            }
            if((l & 0x40) != 0)
            {
                int l1 = buffer0.getUbyteA();
                int k2 = buffer0.getUbyteB();
                class30_sub2_sub4_sub1_sub1.pushHit(-35698, k2, l1, loopCycle);
                class30_sub2_sub4_sub1_sub1.anInt1532 = loopCycle + 300;
                class30_sub2_sub4_sub1_sub1.anInt1533 = buffer0.getUbyteB();
                class30_sub2_sub4_sub1_sub1.anInt1534 = buffer0.getUbyteA();
            }
			/* Switch Npc */
            if((l & 2) != 0)
            {
                class30_sub2_sub4_sub1_sub1.definition = NpcDefinition.getNPCDefinition(buffer0.getUwordLE128());
                class30_sub2_sub4_sub1_sub1.halfOffsets = class30_sub2_sub4_sub1_sub1.definition.npc_halftileoffsets;
                class30_sub2_sub4_sub1_sub1.rotation = class30_sub2_sub4_sub1_sub1.definition.spawndirection;
                class30_sub2_sub4_sub1_sub1.walkAnimation = class30_sub2_sub4_sub1_sub1.definition.npcdef_walkanim;
                class30_sub2_sub4_sub1_sub1.turnAnimation180 = class30_sub2_sub4_sub1_sub1.definition.npcdef_turn180anim;
                class30_sub2_sub4_sub1_sub1.turnCwAnimation90 = class30_sub2_sub4_sub1_sub1.definition.npcdef_turn90cw;
                class30_sub2_sub4_sub1_sub1.turnCcwAnimation90 = class30_sub2_sub4_sub1_sub1.definition.npcdef_turn90ccw;
                class30_sub2_sub4_sub1_sub1.standAnimation = class30_sub2_sub4_sub1_sub1.definition.npcdef_standanim;
            }
            if((l & 4) != 0)
            {
                class30_sub2_sub4_sub1_sub1.anInt1538 = buffer0.getUwordLE();
                class30_sub2_sub4_sub1_sub1.anInt1539 = buffer0.getUwordLE();
            }
        }

        isOnlineGame &= flag;
    }

    public void method87(NpcDefinition class5, int i, boolean flag, int j, int k)
    {
        if(anInt1133 >= 400)
            return;
        if(class5.confignpcs != null)
            class5 = class5.method161(anInt877);
        if(class5 == null)
            return;
        if(!class5.isvisible)
            return;
        String s = class5.name;
        if(flag)
            aBoolean919 = !aBoolean919;
        if(class5.level != 0)
            s = s + getLevelColor(localPlayer.combatLevel, class5.level, true) + " (level-" + class5.level + ")";
        if(anInt1282 == 1)
        {
            interfacestringstack[anInt1133] = "Use " + usedItemName + " with @yel@" + s;
            interfaceopcodestack[anInt1133] = 582;
            interfacestack_c[anInt1133] = i;
            interfacestack_a[anInt1133] = k;
            interfacestack_b[anInt1133] = j;
            anInt1133++;
            return;
        }
        if(anInt1136 == 1)
        {
            if((anInt1138 & 2) == 2)
            {
                interfacestringstack[anInt1133] = aString1139 + " @yel@" + s;
                interfaceopcodestack[anInt1133] = 413;
                interfacestack_c[anInt1133] = i;
                interfacestack_a[anInt1133] = k;
                interfacestack_b[anInt1133] = j;
                anInt1133++;
                return;
            }
        } else
        {
            if(class5.options != null)
            {
                for(int l = 4; l >= 0; l--)
                    if(class5.options[l] != null && !class5.options[l].equalsIgnoreCase("attack"))
                    {
                        interfacestringstack[anInt1133] = class5.options[l] + " @yel@" + s;
                        if(l == 0)
                            interfaceopcodestack[anInt1133] = 20;
                        if(l == 1)
                            interfaceopcodestack[anInt1133] = 412;
                        if(l == 2)
                            interfaceopcodestack[anInt1133] = 225;
                        if(l == 3)
                            interfaceopcodestack[anInt1133] = 965;
                        if(l == 4)
                            interfaceopcodestack[anInt1133] = 478;
                        interfacestack_c[anInt1133] = i;
                        interfacestack_a[anInt1133] = k;
                        interfacestack_b[anInt1133] = j;
                        anInt1133++;
                    }

            }
            if(class5.options != null)
            {
                for(int i1 = 4; i1 >= 0; i1--)
                    if(class5.options[i1] != null && class5.options[i1].equalsIgnoreCase("attack"))
                    {
                        char c = '\0';
                        if(class5.level > localPlayer.combatLevel)
                            c = '\u07D0';
                        interfacestringstack[anInt1133] = class5.options[i1] + " @yel@" + s;
                        if(i1 == 0)
                            interfaceopcodestack[anInt1133] = 20 + c;
                        if(i1 == 1)
                            interfaceopcodestack[anInt1133] = 412 + c;
                        if(i1 == 2)
                            interfaceopcodestack[anInt1133] = 225 + c;
                        if(i1 == 3)
                            interfaceopcodestack[anInt1133] = 965 + c;
                        if(i1 == 4)
                            interfaceopcodestack[anInt1133] = 478 + c;
                        interfacestack_c[anInt1133] = i;
                        interfacestack_a[anInt1133] = k;
                        interfacestack_b[anInt1133] = j;
                        anInt1133++;
                    }

            }
            interfacestringstack[anInt1133] = "Examine @yel@" + s;
            interfaceopcodestack[anInt1133] = 1025;
            interfacestack_c[anInt1133] = i;
            interfacestack_a[anInt1133] = k;
            interfacestack_b[anInt1133] = j;
            anInt1133++;
        }
    }

    public void method88(int i, int j, Player class30_sub2_sub4_sub1_sub2, boolean flag, int k)
    {
        if(class30_sub2_sub4_sub1_sub2 == localPlayer)
            return;
        if(anInt1133 >= 400)
            return;
        if(flag)
            return;
        String s;
        if(class30_sub2_sub4_sub1_sub2.skillTotal == 0)
            s = class30_sub2_sub4_sub1_sub2.name + getLevelColor(localPlayer.combatLevel, class30_sub2_sub4_sub1_sub2.combatLevel, true) + " (level-" + class30_sub2_sub4_sub1_sub2.combatLevel + ")";
        else
            s = class30_sub2_sub4_sub1_sub2.name + " (skill-" + class30_sub2_sub4_sub1_sub2.skillTotal + ")";
        if(anInt1282 == 1)
        {
            interfacestringstack[anInt1133] = "Use " + usedItemName + " with @whi@" + s;
            interfaceopcodestack[anInt1133] = 491;
            interfacestack_c[anInt1133] = j;
            interfacestack_a[anInt1133] = i;
            interfacestack_b[anInt1133] = k;
            anInt1133++;
        } else
        if(anInt1136 == 1)
        {
            if((anInt1138 & 8) == 8)
            {
                interfacestringstack[anInt1133] = aString1139 + " @whi@" + s;
                interfaceopcodestack[anInt1133] = 365;
                interfacestack_c[anInt1133] = j;
                interfacestack_a[anInt1133] = i;
                interfacestack_b[anInt1133] = k;
                anInt1133++;
            }
        } else
        {
            for(int l = 4; l >= 0; l--)
                if(aStringArray1127[l] != null)
                {
                    interfacestringstack[anInt1133] = aStringArray1127[l] + " @whi@" + s;
                    char c = '\0';
                    if(aStringArray1127[l].equalsIgnoreCase("attack"))
                    {
                        if(class30_sub2_sub4_sub1_sub2.combatLevel > localPlayer.combatLevel)
                            c = '\u07D0';
                        if(localPlayer.itemModel != 0 && class30_sub2_sub4_sub1_sub2.itemModel != 0)
                            if(localPlayer.itemModel == class30_sub2_sub4_sub1_sub2.itemModel)
                                c = '\u07D0';
                            else
                                c = '\0';
                    } else
                    if(aBooleanArray1128[l])
                        c = '\u07D0';
                    if(l == 0)
                        interfaceopcodestack[anInt1133] = 561 + c;
                    if(l == 1)
                        interfaceopcodestack[anInt1133] = 779 + c;
                    if(l == 2)
                        interfaceopcodestack[anInt1133] = 27 + c;
                    if(l == 3)
                        interfaceopcodestack[anInt1133] = 577 + c;
                    if(l == 4)
                        interfaceopcodestack[anInt1133] = 729 + c;
                    interfacestack_c[anInt1133] = j;
                    interfacestack_a[anInt1133] = i;
                    interfacestack_b[anInt1133] = k;
                    anInt1133++;
                }

        }
        for(int i1 = 0; i1 < anInt1133; i1++)
            if(interfaceopcodestack[i1] == 516)
            {
                interfacestringstack[i1] = "Walk here @whi@" + s;
                return;
            }

    }

    public void method89(boolean flag, SpawnedObject class30_sub1)
    {
        int i = 0;
        int j = -1;
        int k = 0;
        int l = 0;
        if(class30_sub1.type_num == 0)
            i = pallet.method300(class30_sub1.cheight, class30_sub1.pallete_x, class30_sub1.pallete_y);
        if(class30_sub1.type_num == 1)
            i = pallet.method301(class30_sub1.cheight, class30_sub1.pallete_x, 0, class30_sub1.pallete_y);
        if(class30_sub1.type_num == 2)
            i = pallet.method302(class30_sub1.cheight, class30_sub1.pallete_x, class30_sub1.pallete_y);
        if(class30_sub1.type_num == 3)
            i = pallet.method303(class30_sub1.cheight, class30_sub1.pallete_x, class30_sub1.pallete_y);
        if(i != 0)
        {
            int i1 = pallet.method304(class30_sub1.cheight, class30_sub1.pallete_x, class30_sub1.pallete_y, i);
            j = i >> 14 & 0x7fff;
            k = i1 & 0x1f;
            l = i1 >> 6;
        }
        class30_sub1.anInt1299 = j;
        class30_sub1.anInt1301 = k;
        if(flag)
        {
            for(int j1 = 1; j1 > 0; j1++);
        }
        class30_sub1.anInt1300 = l;
    }

    public void updateSounds(boolean flag)
    {
        if(flag)
            packetId = -1;
        for(int i = 0; i < anInt1062; i++)
            if(anIntArray1250[i] <= 0)
            {
                boolean flag1 = false;
                try
                {
                    if(anIntArray1207[i] == anInt874 && anIntArray1241[i] == anInt1289)
                    {
                        if(!waveReplay(11456))
                            flag1 = true;
                    } else
                    {
                        ByteBuffer buffer = Sound.method241(anIntArray1241[i], anIntArray1207[i], false);
                        if(System.currentTimeMillis() + (long)(buffer.offset / 22) > aLong1172 + (long)(anInt1257 / 22))
                        {
                            anInt1257 = buffer.offset;
                            aLong1172 = System.currentTimeMillis();
                            if(writeWaveFile(buffer.payload, buffer.offset))
                            {
                                anInt874 = anIntArray1207[i];
                                anInt1289 = anIntArray1241[i];
                            } else
                            {
                                flag1 = true;
                            }
                        }
                    }
                }
                catch(Exception exception) { }
                if(!flag1 || anIntArray1250[i] == -5)
                {
                    anInt1062--;
                    for(int j = i; j < anInt1062; j++)
                    {
                        anIntArray1207[j] = anIntArray1207[j + 1];
                        anIntArray1241[j] = anIntArray1241[j + 1];
                        anIntArray1250[j] = anIntArray1250[j + 1];
                    }

                    i--;
                } else
                {
                    anIntArray1250[i] = -5;
                }
            } else
            {
                anIntArray1250[i]--;
            }

        if(anInt1259 > 0)
        {
            anInt1259 -= 20;
            if(anInt1259 < 0)
                anInt1259 = 0;
            if(anInt1259 == 0 && aBoolean1151 && !lowMemory)
            {
                anInt1227 = anInt956;
                aBoolean1228 = true;
                ondemandhandler.requestPriority(2, anInt1227);
            }
        }
    }

    public void loadClient()
    {
        drawLoadingBar("Starting up", 20);
        if(Signlink.sunjava)
            super.mindel = 5;
        if(aBoolean993)
        {
            aBoolean1252 = true;
            return;
        }
        aBoolean993 = true;
        boolean flag = false;
        String s = method80(true);
        if(s.endsWith("jagex.com"))
            flag = true;
        if(s.endsWith("runescape.com"))
            flag = true;
        if(s.endsWith("192.168.1.2"))
            flag = true;
        if(s.endsWith("192.168.1.229"))
            flag = true;
        if(s.endsWith("192.168.1.228"))
            flag = true;
        if(s.endsWith("192.168.1.227"))
            flag = true;
        if(s.endsWith("192.168.1.226"))
            flag = true;
        if(s.endsWith("127.0.0.1"))
            flag = true;
        if(!flag)
        {
            aBoolean1176 = true;
            return;
        }
        if(Signlink.mainCacheRandomAccessFile != null)
        {
            for(int i = 0; i < 5; i++)
                fileIndexes[i] = new FileIndex(Signlink.mainCacheRandomAccessFile, Signlink.cacheRandomAccessFiles[i], i + 1, 0xffffffff);
        }
        try
        {
            getJaggrabEntryChecksums();
            titlescreen_archive = getArchivePackage(1, "title screen", "title", jaggrabArchiveCrcs[1], 25);
            p11Font = new BitmapFont(false, "p11_full", 0, titlescreen_archive);
            p12Font = new BitmapFont(false, "p12_full", 0, titlescreen_archive);
            b12Font = new BitmapFont(false, "b12_full", 0, titlescreen_archive);
            q8Font = new BitmapFont(true, "q8_full", 0, titlescreen_archive);
            drawTitleBackround(0);
            method51(215);
            ArchivePackage class44 = getArchivePackage(2, "config", "config", jaggrabArchiveCrcs[2], 30);
            ArchivePackage class44_1 = getArchivePackage(3, "interface", "interface", jaggrabArchiveCrcs[3], 35);
            ArchivePackage graphics_archive = getArchivePackage(4, "2d graphics", "media", jaggrabArchiveCrcs[4], 40);
            ArchivePackage class44_3 = getArchivePackage(6, "textures", "textures", jaggrabArchiveCrcs[6], 45);
            ArchivePackage class44_4 = getArchivePackage(7, "chat system", "wordenc", jaggrabArchiveCrcs[7], 50);
            ArchivePackage class44_5 = getArchivePackage(8, "sound effects", "sounds", jaggrabArchiveCrcs[8], 55);
            tileFlags = new byte[4][104][104];
            tileHeightmap = new int[4][105][105];
            pallet = new Palette(104, 104, 4, tileHeightmap);
            for(int j = 0; j < 4; j++)
                planeFlags[j] = new PlaneFlags(104, 104);
            aClass30_Sub2_Sub1_Sub1_1263 = new DirectColorSprite(512, 512);
            ArchivePackage class44_6 = getArchivePackage(5, "update list", "versionlist", jaggrabArchiveCrcs[5], 60);
            drawLoadingBar("Connecting to update server", 60);
            ondemandhandler = new OndemandHandler();
            ondemandhandler.parseVersionList(class44_6, this);
            AnimFrame.initialize(ondemandhandler.getAmountAnims());
            Model.initialize(ondemandhandler.getAmountArchives(0), ondemandhandler);
            if(!lowMemory) {
                anInt1227 = 0;
                try {
                    anInt1227 = Integer.parseInt(getParameter("music"));
                }
                catch(Exception _ex) { }
                aBoolean1228 = true;
                ondemandhandler.requestPriority(2, anInt1227);
                while(ondemandhandler.amount() > 0) {
                    loadFinalizedRequest(false);
                    try {
                        Thread.sleep(100L);
                    } catch(Exception _ex) { }
                    if(ondemandhandler.anInt1349 > 3) {
                        displayLoadError("ondemand");
                        return;
                    }
                }
            }
            drawLoadingBar("Requesting animations", 65);
            int k = ondemandhandler.getAmountArchives(1);
            for(int i1 = 0; i1 < k; i1++)
                ondemandhandler.requestPriority(1, i1);
            while(ondemandhandler.amount() > 0) 
            {
                int j1 = k - ondemandhandler.amount();
                if(j1 > 0)
                    drawLoadingBar("Loading animations - " + (j1 * 100) / k + "%", 65);
                loadFinalizedRequest(false);
                try
                {
                    Thread.sleep(100L);
                }
                catch(Exception _ex) { }
                if(ondemandhandler.anInt1349 > 3)
                {
                    displayLoadError("ondemand");
                    return;
                }
            }
            drawLoadingBar("Requesting models", 70);
            k = ondemandhandler.getAmountArchives(0);
            for(int k1 = 0; k1 < k; k1++)
            {
                int l1 = ondemandhandler.getModelSettingFlag(k1, -203);
                if((l1 & 1) != 0)
                    ondemandhandler.requestPriority(0, k1);
            }

            k = ondemandhandler.amount();
            while(ondemandhandler.amount() > 0) 
            {
                int i2 = k - ondemandhandler.amount();
                if(i2 > 0)
                    drawLoadingBar("Loading models - " + (i2 * 100) / k + "%", 70);
                loadFinalizedRequest(false);
                try
                {
                    Thread.sleep(100L);
                }
                catch(Exception _ex) { }
            }
            if(fileIndexes[0] != null)
            {
                drawLoadingBar("Requesting maps", 75);
                ondemandhandler.requestPriority(3, ondemandhandler.getMapArchive(47, 48, 0));
                ondemandhandler.requestPriority(3, ondemandhandler.getMapArchive(47, 48, 1));
                ondemandhandler.requestPriority(3, ondemandhandler.getMapArchive(48, 48, 0));
                ondemandhandler.requestPriority(3, ondemandhandler.getMapArchive(48, 48, 1));
                ondemandhandler.requestPriority(3, ondemandhandler.getMapArchive(49, 48, 0));
                ondemandhandler.requestPriority(3, ondemandhandler.getMapArchive(49, 48, 1));
                ondemandhandler.requestPriority(3, ondemandhandler.getMapArchive(47, 47, 0));
                ondemandhandler.requestPriority(3, ondemandhandler.getMapArchive(47, 47, 1));
                ondemandhandler.requestPriority(3, ondemandhandler.getMapArchive(48, 47, 0));
                ondemandhandler.requestPriority(3, ondemandhandler.getMapArchive(48, 47, 1));
                ondemandhandler.requestPriority(3, ondemandhandler.getMapArchive(48, 148, 0));
                ondemandhandler.requestPriority(3, ondemandhandler.getMapArchive(48, 148, 1));
                k = ondemandhandler.amount();
                while(ondemandhandler.amount() > 0) 
                {
                    int j2 = k - ondemandhandler.amount();
                    if(j2 > 0)
                        drawLoadingBar("Loading maps - " + (j2 * 100) / k + "%", 75);
                    loadFinalizedRequest(false);
                    try
                    {
                        Thread.sleep(100L);
                    }
                    catch(Exception _ex) { }
                }
            }
            k = ondemandhandler.getAmountArchives(0);
            for(int k2 = 0; k2 < k; k2++)
            {
                int l2 = ondemandhandler.getModelSettingFlag(k2, -203);
                byte byte0 = 0;
                if((l2 & 8) != 0)
                    byte0 = 10;
                else
                if((l2 & 0x20) != 0)
                    byte0 = 9;
                else
                if((l2 & 0x10) != 0)
                    byte0 = 8;
                else
                if((l2 & 0x40) != 0)
                    byte0 = 7;
                else
                if((l2 & 0x80) != 0)
                    byte0 = 6;
                else
                if((l2 & 2) != 0)
                    byte0 = 5;
                else
                if((l2 & 4) != 0)
                    byte0 = 4;
                if((l2 & 1) != 0)
                    byte0 = 3;
                if(byte0 != 0)
                    ondemandhandler.setArchivePriority(0, k2, byte0);
            }

            ondemandhandler.method554(members, 0);
            if(!lowMemory)
            {
                int l = ondemandhandler.getAmountArchives(2);
                for(int i3 = 1; i3 < l; i3++)
                    if(ondemandhandler.isExtraMidiFile(i3))
                        ondemandhandler.setArchivePriority(2, i3, (byte)1);

            }
            drawLoadingBar("Unpacking media", 80);
            invback = new IndexedColorSprite(graphics_archive, "invback", 0);
            chatback = new IndexedColorSprite(graphics_archive, "chatback", 0);
            mapback = new IndexedColorSprite(graphics_archive, "mapback", 0);
            backbase1 = new IndexedColorSprite(graphics_archive, "backbase1", 0);
            backbase2 = new IndexedColorSprite(graphics_archive, "backbase2", 0);
            backmid1 = new IndexedColorSprite(graphics_archive, "backhmid1", 0);
            for(int j3 = 0; j3 < 13; j3++)
                sideicons[j3] = new IndexedColorSprite(graphics_archive, "sideicons", j3);
            compass = new DirectColorSprite(graphics_archive, "compass", 0);
            mapedge = new DirectColorSprite(graphics_archive, "mapedge", 0);
            mapedge.unpack();
            try
            {
                for(int k3 = 0; k3 < 100; k3++)
                    mapscene[k3] = new IndexedColorSprite(graphics_archive, "mapscene", k3);

            }
            catch(Exception _ex) { }
            try
            {
                for(int l3 = 0; l3 < 100; l3++)
                    mapfunction[l3] = new DirectColorSprite(graphics_archive, "mapfunction", l3);

            }
            catch(Exception _ex) { }
            try
            {
                for(int i4 = 0; i4 < 5; i4++)
                    hitmarks[i4] = new DirectColorSprite(graphics_archive, "hitmarks", i4);

            }
            catch(Exception _ex) { }
            try
            {
                for(int j4 = 0; j4 < 20; j4++)
                    headicons[j4] = new DirectColorSprite(graphics_archive, "headicons", j4);

            }
            catch(Exception _ex) { }
            mapmarker0 = new DirectColorSprite(graphics_archive, "mapmarker", 0);
            mapmarker1 = new DirectColorSprite(graphics_archive, "mapmarker", 1);
            for(int k4 = 0; k4 < 8; k4++)
                cross_sprites[k4] = new DirectColorSprite(graphics_archive, "cross", k4);
            grounditem_mapdotsprite = new DirectColorSprite(graphics_archive, "mapdots", 0);
            mapdots1 = new DirectColorSprite(graphics_archive, "mapdots", 1);
            mapdots2 = new DirectColorSprite(graphics_archive, "mapdots", 2);
            mapdots3 = new DirectColorSprite(graphics_archive, "mapdots", 3);
            mapdots4 = new DirectColorSprite(graphics_archive, "mapdots", 4);
            scrollbar0 = new IndexedColorSprite(graphics_archive, "scrollbar", 0);
            scrollbar1 = new IndexedColorSprite(graphics_archive, "scrollbar", 1);
            redstone1 = new IndexedColorSprite(graphics_archive, "redstone1", 0);
            redstone2 = new IndexedColorSprite(graphics_archive, "redstone2", 0);
            redstone3 = new IndexedColorSprite(graphics_archive, "redstone3", 0);
            redstone1_2 = new IndexedColorSprite(graphics_archive, "redstone1", 0);
            redstone1_2.indexReflectX();
            redstone2_2 = new IndexedColorSprite(graphics_archive, "redstone2", 0);
            redstone2_2.indexReflectX();
            redstone1_3 = new IndexedColorSprite(graphics_archive, "redstone1", 0);
            redstone1_3.indexReflectY();
            redstone2_3 = new IndexedColorSprite(graphics_archive, "redstone2", 0);
            redstone2_3.indexReflectY();
            redstone3_2 = new IndexedColorSprite(graphics_archive, "redstone3", 0);
            redstone3_2.indexReflectY();
            redstone1_4 = new IndexedColorSprite(graphics_archive, "redstone1", 0);
            redstone1_4.indexReflectX();
            redstone1_4.indexReflectY();
            redstone2_4 = new IndexedColorSprite(graphics_archive, "redstone2", 0);
            redstone2_4.indexReflectX();
            redstone2_4.indexReflectY();
            for(int l4 = 0; l4 < 2; l4++)
                mod_icons[l4] = new IndexedColorSprite(graphics_archive, "mod_icons", l4);
			/* */
            DirectColorSprite class30_sub2_sub1_sub1 = new DirectColorSprite(graphics_archive, "backleft1", 0);
            backleft1_imagefetcher = new ImageFetcher(class30_sub2_sub1_sub1.indexWidth, class30_sub2_sub1_sub1.indexHeight, getDrawableComponent(), 0);
            class30_sub2_sub1_sub1.draw(0, 0);
			/* */
            class30_sub2_sub1_sub1 = new DirectColorSprite(graphics_archive, "backleft2", 0);
            backleft2_imagefetcher = new ImageFetcher(class30_sub2_sub1_sub1.indexWidth, class30_sub2_sub1_sub1.indexHeight, getDrawableComponent(), 0);
            class30_sub2_sub1_sub1.draw(0, 0);
			/* */
            class30_sub2_sub1_sub1 = new DirectColorSprite(graphics_archive, "backright1", 0);
            backright1_imagefetcher = new ImageFetcher(class30_sub2_sub1_sub1.indexWidth, class30_sub2_sub1_sub1.indexHeight, getDrawableComponent(), 0);
            class30_sub2_sub1_sub1.draw(0, 0);
			/* */
            class30_sub2_sub1_sub1 = new DirectColorSprite(graphics_archive, "backright2", 0);
            backright2_imagefetcher = new ImageFetcher(class30_sub2_sub1_sub1.indexWidth, class30_sub2_sub1_sub1.indexHeight, getDrawableComponent(), 0);
            class30_sub2_sub1_sub1.draw(0, 0);
			/* */
            class30_sub2_sub1_sub1 = new DirectColorSprite(graphics_archive, "backtop1", 0);
            backtop1_imagefetcher = new ImageFetcher(class30_sub2_sub1_sub1.indexWidth, class30_sub2_sub1_sub1.indexHeight, getDrawableComponent(), 0);
            class30_sub2_sub1_sub1.draw(0, 0);
			/* */
            class30_sub2_sub1_sub1 = new DirectColorSprite(graphics_archive, "backvmid1", 0);
            backvmid1_imagefetcher = new ImageFetcher(class30_sub2_sub1_sub1.indexWidth, class30_sub2_sub1_sub1.indexHeight, getDrawableComponent(), 0);
            class30_sub2_sub1_sub1.draw(0, 0);
			/* */
            class30_sub2_sub1_sub1 = new DirectColorSprite(graphics_archive, "backvmid2", 0);
            backvmid2_imagefetcher = new ImageFetcher(class30_sub2_sub1_sub1.indexWidth, class30_sub2_sub1_sub1.indexHeight, getDrawableComponent(), 0);
            class30_sub2_sub1_sub1.draw(0, 0);
			/* */
            class30_sub2_sub1_sub1 = new DirectColorSprite(graphics_archive, "backvmid3", 0);
            backvmid3_imagefetcher = new ImageFetcher(class30_sub2_sub1_sub1.indexWidth, class30_sub2_sub1_sub1.indexHeight, getDrawableComponent(), 0);
            class30_sub2_sub1_sub1.draw(0, 0);
			/* */
            class30_sub2_sub1_sub1 = new DirectColorSprite(graphics_archive, "backhmid2", 0);
            backhmid2_imagefetcher = new ImageFetcher(class30_sub2_sub1_sub1.indexWidth, class30_sub2_sub1_sub1.indexHeight, getDrawableComponent(), 0);
            class30_sub2_sub1_sub1.draw(0, 0);
            int i5 = (int)(Math.random() * 21D) - 10;
            int j5 = (int)(Math.random() * 21D) - 10;
            int k5 = (int)(Math.random() * 21D) - 10;
            int l5 = (int)(Math.random() * 41D) - 20;
            for(int i6 = 0; i6 < 100; i6++)
            {
                if(mapfunction[i6] != null)
                    mapfunction[i6].intensify(i5 + l5, j5 + l5, k5 + l5);
                if(mapscene[i6] != null)
                    mapscene[i6].intensify(i5 + l5, j5 + l5, k5 + l5, 0);
            }

            drawLoadingBar("Unpacking textures", 83);
            TriangleRasterizer.unpackTextures(class44_3);
            TriangleRasterizer.method372(0.80000000000000004D, aByte1200);
            TriangleRasterizer.initialize(20, true);
            drawLoadingBar("Unpacking config", 86);
            AnimSequence.unpackAnimSequences(class44);
            ObjectDefinition.initialize(class44);
            FloorDefinition.unpackDefinitions(class44);
            ItemDefinition.amountitems(class44);
            NpcDefinition.unpackNpcDefs(class44);
            IdentityKit.method535(0, class44);
            SpotAnim.method264(0, class44);
            VarpFile.method546(0, class44);
            VarbitFile.unpackArchives(0, class44);
            ItemDefinition.aBoolean182 = members;
            if(!lowMemory)
            {
                drawLoadingBar("Unpacking sounds", 90);
                byte abyte0[] = class44_5.getArchive("sounds.dat", null);
                ByteBuffer buffer0 = new ByteBuffer(abyte0);
                Sound.method240(0, buffer0);
            }
            drawLoadingBar("Unpacking interfaces", 95);
            BitmapFont aclass30_sub2_sub1_sub4[] = {
                p11Font, p12Font, b12Font, q8Font
            };
            Widget.loadWidgets(class44_1, aclass30_sub2_sub1_sub4, graphics_archive);
            drawLoadingBar("Preparing game engine", 100);
            for(int j6 = 0; j6 < 33; j6++)
            {
                int k6 = 999;
                int i7 = 0;
                for(int k7 = 0; k7 < 34; k7++)
                {
                    if(mapback.buffer[k7 + j6 * mapback.indexWidth] == 0)
                    {
                        if(k6 == 999)
                            k6 = k7;
                        continue;
                    }
                    if(k6 == 999)
                        continue;
                    i7 = k7;
                    break;
                }

                anIntArray968[j6] = k6;
                anIntArray1057[j6] = i7 - k6;
            }

            for(int l6 = 5; l6 < 156; l6++)
            {
                int j7 = 999;
                int l7 = 0;
                for(int j8 = 25; j8 < 172; j8++)
                {
                    if(mapback.buffer[j8 + l6 * mapback.indexWidth] == 0 && (j8 > 34 || l6 > 34))
                    {
                        if(j7 == 999)
                            j7 = j8;
                        continue;
                    }
                    if(j7 == 999)
                        continue;
                    l7 = j8;
                    break;
                }

                anIntArray1052[l6 - 5] = j7 - 25;
                anIntArray1229[l6 - 5] = l7 - j7;
            }

            TriangleRasterizer.setDimensions(-950, 479, 96);
            anIntArray1180 = TriangleRasterizer.heightoffsets;
            TriangleRasterizer.setDimensions(-950, 190, 261);
            anIntArray1181 = TriangleRasterizer.heightoffsets;
            TriangleRasterizer.setDimensions(-950, 512, 334);
            anIntArray1182 = TriangleRasterizer.heightoffsets;
            int ai[] = new int[9];
            for(int i8 = 0; i8 < 9; i8++)
            {
                int k8 = 128 + i8 * 32 + 15;
                int l8 = 600 + k8 * 3;
                int i9 = TriangleRasterizer.SINE_TABLE[k8];
                ai[i8] = l8 * i9 >> 16;
            }

            Palette.method310(500, 800, 512, 334, ai, aBoolean1231);
            Censor.unpackCensor(class44_4);
            monitor = new Monitor(this, anInt1096);
            createThread(monitor, 10);
            GameObject.main = this;
            ObjectDefinition.main = this;
            NpcDefinition.main = this;
            return;
        } catch(Exception exception) {
			exception.printStackTrace();
            Signlink.reportError("loaderror " + aString1049 + " " + anInt1079);
        }
        aBoolean926 = true;
    }

    public void populatePlayerList(ByteBuffer buffer0, int i, byte byte0)
    {
        while(buffer0.bitOffset + 10 < i * 8) 
        {
            int j = buffer0.getBits(11);
            if(j == 2047)
                break;
            System.out.println("New player populated into system! " + j);
            if(playerArray[j] == null)
            {
                playerArray[j] = new Player();
                if(appearanceBuffers[j] != null)
                    playerArray[j].parseAppearance(appearanceBuffers[j]);
            }
            addedPlayers[playerOffset++] = j;
            Player class30_sub2_sub4_sub1_sub2 = playerArray[j];
            class30_sub2_sub4_sub1_sub2.anInt1537 = loopCycle;
            int k = buffer0.getBits(1);
            if(k == 1)
                pFlagUpdateList[amtplayerupdatestack++] = j;
            int l = buffer0.getBits(1);
            int dy = buffer0.getBits(5);
            if(dy > 15)
                dy -= 32;
            int dx = buffer0.getBits(5);
            if(dx > 15)
                dx -= 32;
            class30_sub2_sub4_sub1_sub2.updateMobPosition(((Mob) (localPlayer)).xList[0] + dx, ((Mob) (localPlayer)).yList[0] + dy, l == 1);
        }
        buffer0.endBitAccess();
    }

    public void handleMinimap(boolean flag)
    {
        isOnlineGame &= flag;
        if(anInt1021 != 0)
            return;
        if(super.anInt26 == 1)
        {
            int i = super.pressedX - 25 - 550;
            int j = super.pressedY - 5 - 4;
            if(i >= 0 && j >= 0 && i < 146 && j < 151)
            {
				/* Divide by 2 */
                i -= 73;
                j -= 75;
                int k = cameraYaw + anInt1209 & 0x7ff;
                int i1 = TriangleRasterizer.SINE_TABLE[k];
                int j1 = TriangleRasterizer.COSINE_TABLE[k];
                i1 = i1 * (anInt1170 + 256) >> 8;
                j1 = j1 * (anInt1170 + 256) >> 8;
                int k1 = j * i1 + i * j1 >> 11;
                int l1 = j * j1 - i * i1 >> 11;
                int i2 = ((Mob) (localPlayer)).fineX + k1 >> 7;
                int j2 = ((Mob) (localPlayer)).fineY - l1 >> 7;
                boolean flag1 = sendWalkPacket(1, 0, 0, -11308, 0, ((Mob) (localPlayer)).yList[0], 0, 0, j2, ((Mob) (localPlayer)).xList[0], true, i2);
                if(flag1)
                {
                    gameBuffer.put(i);
                    gameBuffer.put(j);
                    gameBuffer.putWord(cameraYaw);
                    gameBuffer.put(57);
                    gameBuffer.put(anInt1209);
                    gameBuffer.put(anInt1170);
                    gameBuffer.put(89);
                    gameBuffer.putWord(((Mob) (localPlayer)).fineX);
                    gameBuffer.putWord(((Mob) (localPlayer)).fineY);
                    gameBuffer.put(anInt1264);
                    gameBuffer.put(63);
                }
            }
            anInt1117++;
            if(anInt1117 > 1151)
            {
                anInt1117 = 0;
                gameBuffer.putPacket(246);
                gameBuffer.put(0);
                int l = gameBuffer.offset;
                if((int)(Math.random() * 2D) == 0)
                    gameBuffer.put(101);
                gameBuffer.put(197);
                gameBuffer.putWord((int)(Math.random() * 65536D));
                gameBuffer.put((int)(Math.random() * 256D));
                gameBuffer.put(67);
                gameBuffer.putWord(14214);
                if((int)(Math.random() * 2D) == 0)
                    gameBuffer.putWord(29487);
                gameBuffer.putWord((int)(Math.random() * 65536D));
                if((int)(Math.random() * 2D) == 0)
                    gameBuffer.put(220);
                gameBuffer.put(180);
                gameBuffer.endVarByte(gameBuffer.offset - l, (byte)0);
            }
        }
    }

    public String method93(int i, int j)
    {
        if(i <= 0)
            packetId = inbuffer.getUbyte();
        if(j < 0x3b9ac9ff)
            return String.valueOf(j);
        else
            return "*";
    }

    public void method94(int i)
    {
        if(i != -13873)
        {
            for(int j = 1; j > 0; j++);
        }
        Graphics g = getDrawableComponent().getGraphics();
        g.setColor(Color.black);
        g.fillRect(0, 0, 765, 503);
        setDeltime(false, 1);
        if(aBoolean926)
        {
            runflamecycle = false;
            g.setFont(new Font("Helvetica", 1, 16));
            g.setColor(Color.yellow);
            int k = 35;
            g.drawString("Sorry, an error has occured whilst loading RuneScape", 30, k);
            k += 50;
            g.setColor(Color.white);
            g.drawString("To fix this try the following (in order):", 30, k);
            k += 50;
            g.setColor(Color.white);
            g.setFont(new Font("Helvetica", 1, 12));
            g.drawString("1: Try closing ALL open web-browser windows, and reloading", 30, k);
            k += 30;
            g.drawString("2: Try clearing your web-browsers cache from tools->internet options", 30, k);
            k += 30;
            g.drawString("3: Try using a different game-world", 30, k);
            k += 30;
            g.drawString("4: Try rebooting your computer", 30, k);
            k += 30;
            g.drawString("5: Try selecting a different version of Java from the play-game menu", 30, k);
        }
        if(aBoolean1176)
        {
            runflamecycle = false;
            g.setFont(new Font("Helvetica", 1, 20));
            g.setColor(Color.white);
            g.drawString("Error - unable to load game!", 50, 50);
            g.drawString("To play RuneScape make sure you play from", 50, 100);
            g.drawString("http://www.runescape.com", 50, 150);
        }
        if(aBoolean1252)
        {
            runflamecycle = false;
            g.setColor(Color.yellow);
            int l = 35;
            g.drawString("Error a copy of RuneScape already appears to be loaded", 30, l);
            l += 50;
            g.setColor(Color.white);
            g.drawString("To fix this try the following (in order):", 30, l);
            l += 50;
            g.setColor(Color.white);
            g.setFont(new Font("Helvetica", 1, 12));
            g.drawString("1: Try closing ALL open web-browser windows, and reloading", 30, l);
            l += 30;
            g.drawString("2: Try rebooting your computer, and reloading", 30, l);
            l += 30;
        }
    }

    @Override
    public URL getCodeBase()
    {
        if(Signlink.applet != null)
            return Signlink.applet.getCodeBase();
        try
        {
            if(super.appletFrame != null)
                return new URL("http://127.0.0.1:" + (80 + portOffset));
        }
        catch(Exception ex) { 
        }
        return super.getCodeBase();
    }

    public void updateNpcs(int i)
    {
        for(int j = 0; j < anInt836; j++)
        {
            int k = localNpcIds[j];
            Npc class30_sub2_sub4_sub1_sub1 = npcs[k];
            if(class30_sub2_sub4_sub1_sub1 != null)
                updateMobOrentation(46988, class30_sub2_sub4_sub1_sub1.definition.npc_halftileoffsets, class30_sub2_sub4_sub1_sub1);
        }

        if(i != -8066)
            anInt1218 = 148;
    }

    public void updateMobOrentation(int i, int j, Mob class30_sub2_sub4_sub1)
    {
        if(i != 46988)
            packetId = -1;
        if(class30_sub2_sub4_sub1.fineX < 128 || class30_sub2_sub4_sub1.fineY < 128 || class30_sub2_sub4_sub1.fineX >= 13184 || class30_sub2_sub4_sub1.fineY >= 13184)
        {
            class30_sub2_sub4_sub1.animid_request = -1;
            class30_sub2_sub4_sub1.anInt1520 = -1;
            class30_sub2_sub4_sub1.forcewlk_sp1 = 0;
            class30_sub2_sub4_sub1.forcewlk_sp2 = 0;
            class30_sub2_sub4_sub1.fineX = class30_sub2_sub4_sub1.xList[0] * 128 + class30_sub2_sub4_sub1.halfOffsets * 64;
            class30_sub2_sub4_sub1.fineY = class30_sub2_sub4_sub1.yList[0] * 128 + class30_sub2_sub4_sub1.halfOffsets * 64;
            class30_sub2_sub4_sub1.method446(true);
        }
        if(class30_sub2_sub4_sub1 == localPlayer && (class30_sub2_sub4_sub1.fineX < 1536 || class30_sub2_sub4_sub1.fineY < 1536 || class30_sub2_sub4_sub1.fineX >= 11776 || class30_sub2_sub4_sub1.fineY >= 11776))
        {
            class30_sub2_sub4_sub1.animid_request = -1;
            class30_sub2_sub4_sub1.anInt1520 = -1;
            class30_sub2_sub4_sub1.forcewlk_sp1 = 0;
            class30_sub2_sub4_sub1.forcewlk_sp2 = 0;
            class30_sub2_sub4_sub1.fineX = class30_sub2_sub4_sub1.xList[0] * 128 + class30_sub2_sub4_sub1.halfOffsets * 64;
            class30_sub2_sub4_sub1.fineY = class30_sub2_sub4_sub1.yList[0] * 128 + class30_sub2_sub4_sub1.halfOffsets * 64;
            class30_sub2_sub4_sub1.method446(true);
        }
        if(class30_sub2_sub4_sub1.forcewlk_sp1 > loopCycle)
            method97(class30_sub2_sub4_sub1, true);
        else
        if(class30_sub2_sub4_sub1.forcewlk_sp2 >= loopCycle)
            method98(class30_sub2_sub4_sub1, aByte1012);
        else
            method99((byte)34, class30_sub2_sub4_sub1);
        method100(class30_sub2_sub4_sub1, -843);
        method101(class30_sub2_sub4_sub1, -805);
    }

    public void method97(Mob class30_sub2_sub4_sub1, boolean flag)
    {
        int i = class30_sub2_sub4_sub1.forcewlk_sp1 - loopCycle;
        int j = class30_sub2_sub4_sub1.forcewlk_startx * 128 + class30_sub2_sub4_sub1.halfOffsets * 64;
        int k = class30_sub2_sub4_sub1.forcewlk_starty * 128 + class30_sub2_sub4_sub1.halfOffsets * 64;
        class30_sub2_sub4_sub1.fineX += (j - class30_sub2_sub4_sub1.fineX) / i;
        if(!flag)
            return;
        class30_sub2_sub4_sub1.fineY += (k - class30_sub2_sub4_sub1.fineY) / i;
        class30_sub2_sub4_sub1.anInt1503 = 0;
        if(class30_sub2_sub4_sub1.forcewlk_dir == 0)
            class30_sub2_sub4_sub1.anInt1510 = 1024;
        if(class30_sub2_sub4_sub1.forcewlk_dir == 1)
            class30_sub2_sub4_sub1.anInt1510 = 1536;
        if(class30_sub2_sub4_sub1.forcewlk_dir == 2)
            class30_sub2_sub4_sub1.anInt1510 = 0;
        if(class30_sub2_sub4_sub1.forcewlk_dir == 3)
            class30_sub2_sub4_sub1.anInt1510 = 512;
    }

    public void method98(Mob class30_sub2_sub4_sub1, byte byte0)
    {
        if(class30_sub2_sub4_sub1.forcewlk_sp2 == loopCycle || class30_sub2_sub4_sub1.animid_request == -1 || class30_sub2_sub4_sub1.animdelay_request != 0 || class30_sub2_sub4_sub1.anInt1528 + 1 > AnimSequence.animationsequences[class30_sub2_sub4_sub1.animid_request].method258(class30_sub2_sub4_sub1.anInt1527, (byte)-39))
        {
            int i = class30_sub2_sub4_sub1.forcewlk_sp2 - class30_sub2_sub4_sub1.forcewlk_sp1;
            int j = loopCycle - class30_sub2_sub4_sub1.forcewlk_sp1;
            int k = class30_sub2_sub4_sub1.forcewlk_startx * 128 + class30_sub2_sub4_sub1.halfOffsets * 64;
            int l = class30_sub2_sub4_sub1.forcewlk_starty * 128 + class30_sub2_sub4_sub1.halfOffsets * 64;
            int i1 = class30_sub2_sub4_sub1.forcewlk_endx * 128 + class30_sub2_sub4_sub1.halfOffsets * 64;
            int j1 = class30_sub2_sub4_sub1.forcewlk_endy * 128 + class30_sub2_sub4_sub1.halfOffsets * 64;
            class30_sub2_sub4_sub1.fineX = (k * (i - j) + i1 * j) / i;
            class30_sub2_sub4_sub1.fineY = (l * (i - j) + j1 * j) / i;
        }
        class30_sub2_sub4_sub1.anInt1503 = 0;
        if(class30_sub2_sub4_sub1.forcewlk_dir == 0)
            class30_sub2_sub4_sub1.anInt1510 = 1024;
        if(class30_sub2_sub4_sub1.forcewlk_dir == 1)
            class30_sub2_sub4_sub1.anInt1510 = 1536;
        if(class30_sub2_sub4_sub1.forcewlk_dir == 2)
            class30_sub2_sub4_sub1.anInt1510 = 0;
        if(class30_sub2_sub4_sub1.forcewlk_dir == 3)
            class30_sub2_sub4_sub1.anInt1510 = 512;
        class30_sub2_sub4_sub1.anInt1552 = class30_sub2_sub4_sub1.anInt1510;
        if(byte0 != aByte1012)
            anInt1096 = -383;
    }

    public void method99(byte byte0, Mob class30_sub2_sub4_sub1)
    {
        class30_sub2_sub4_sub1.anInt1517 = class30_sub2_sub4_sub1.standAnimation;
        if(class30_sub2_sub4_sub1.stack_position_mob == 0)
        {
            class30_sub2_sub4_sub1.anInt1503 = 0;
            return;
        }
        if(class30_sub2_sub4_sub1.animid_request != -1 && class30_sub2_sub4_sub1.animdelay_request == 0)
        {
            AnimSequence class20 = AnimSequence.animationsequences[class30_sub2_sub4_sub1.animid_request];
            if(class30_sub2_sub4_sub1.anInt1542 > 0 && class20.anInt363 == 0)
            {
                class30_sub2_sub4_sub1.anInt1503++;
                return;
            }
            if(class30_sub2_sub4_sub1.anInt1542 <= 0 && class20.anInt364 == 0)
            {
                class30_sub2_sub4_sub1.anInt1503++;
                return;
            }
        }
        int i = class30_sub2_sub4_sub1.fineX;
        int j = class30_sub2_sub4_sub1.fineY;
        int k = class30_sub2_sub4_sub1.xList[class30_sub2_sub4_sub1.stack_position_mob - 1] * 128 + class30_sub2_sub4_sub1.halfOffsets * 64;
        int l = class30_sub2_sub4_sub1.yList[class30_sub2_sub4_sub1.stack_position_mob - 1] * 128 + class30_sub2_sub4_sub1.halfOffsets * 64;
        if(k - i > 256 || k - i < -256 || l - j > 256 || l - j < -256)
        {
            class30_sub2_sub4_sub1.fineX = k;
            class30_sub2_sub4_sub1.fineY = l;
            return;
        }
        if(i < k)
        {
            if(j < l)
                class30_sub2_sub4_sub1.anInt1510 = 1280;
            else
            if(j > l)
                class30_sub2_sub4_sub1.anInt1510 = 1792;
            else
                class30_sub2_sub4_sub1.anInt1510 = 1536;
        } else
        if(i > k)
        {
            if(j < l)
                class30_sub2_sub4_sub1.anInt1510 = 768;
            else
            if(j > l)
                class30_sub2_sub4_sub1.anInt1510 = 256;
            else
                class30_sub2_sub4_sub1.anInt1510 = 512;
        } else
        if(j < l)
            class30_sub2_sub4_sub1.anInt1510 = 1024;
        else
            class30_sub2_sub4_sub1.anInt1510 = 0;
        int i1 = class30_sub2_sub4_sub1.anInt1510 - class30_sub2_sub4_sub1.anInt1552 & 0x7ff;
        if(i1 > 1024)
            i1 -= 2048;
        int j1 = class30_sub2_sub4_sub1.turnAnimation180;
        if(i1 >= -256 && i1 <= 256)
            j1 = class30_sub2_sub4_sub1.walkAnimation;
        else
        if(i1 >= 256 && i1 < 768)
            j1 = class30_sub2_sub4_sub1.turnCcwAnimation90;
        else
        if(i1 >= -768 && i1 <= -256)
            j1 = class30_sub2_sub4_sub1.turnCwAnimation90;
        if(j1 == -1)
            j1 = class30_sub2_sub4_sub1.walkAnimation;
        class30_sub2_sub4_sub1.anInt1517 = j1;
        int k1 = 4;
        if(class30_sub2_sub4_sub1.anInt1552 != class30_sub2_sub4_sub1.anInt1510 && class30_sub2_sub4_sub1.anInt1502 == -1 && class30_sub2_sub4_sub1.rotation != 0)
            k1 = 2;
        if(class30_sub2_sub4_sub1.stack_position_mob > 2)
            k1 = 6;
        if(class30_sub2_sub4_sub1.stack_position_mob > 3)
            k1 = 8;
        if(class30_sub2_sub4_sub1.anInt1503 > 0 && class30_sub2_sub4_sub1.stack_position_mob > 1)
        {
            k1 = 8;
            class30_sub2_sub4_sub1.anInt1503--;
        }
        if(class30_sub2_sub4_sub1.running_stack[class30_sub2_sub4_sub1.stack_position_mob - 1])
            k1 <<= 1;
        if(k1 >= 8 && class30_sub2_sub4_sub1.anInt1517 == class30_sub2_sub4_sub1.walkAnimation && class30_sub2_sub4_sub1.runAnimation != -1)
            class30_sub2_sub4_sub1.anInt1517 = class30_sub2_sub4_sub1.runAnimation;
        if(i < k)
        {
            class30_sub2_sub4_sub1.fineX += k1;
            if(class30_sub2_sub4_sub1.fineX > k)
                class30_sub2_sub4_sub1.fineX = k;
        } else
        if(i > k)
        {
            class30_sub2_sub4_sub1.fineX -= k1;
            if(class30_sub2_sub4_sub1.fineX < k)
                class30_sub2_sub4_sub1.fineX = k;
        }
        if(j < l)
        {
            class30_sub2_sub4_sub1.fineY += k1;
            if(class30_sub2_sub4_sub1.fineY > l)
                class30_sub2_sub4_sub1.fineY = l;
        } else
        if(j > l)
        {
            class30_sub2_sub4_sub1.fineY -= k1;
            if(class30_sub2_sub4_sub1.fineY < l)
                class30_sub2_sub4_sub1.fineY = l;
        }
        if(class30_sub2_sub4_sub1.fineX == k && class30_sub2_sub4_sub1.fineY == l)
        {
            class30_sub2_sub4_sub1.stack_position_mob--;
            if(class30_sub2_sub4_sub1.anInt1542 > 0)
                class30_sub2_sub4_sub1.anInt1542--;
        }
    }

    public void method100(Mob class30_sub2_sub4_sub1, int i)
    {
        if(i >= 0)
            return;
        if(class30_sub2_sub4_sub1.rotation == 0)
            return;
        if(class30_sub2_sub4_sub1.anInt1502 != -1 && class30_sub2_sub4_sub1.anInt1502 < 32768)
        {
            Npc class30_sub2_sub4_sub1_sub1 = npcs[class30_sub2_sub4_sub1.anInt1502];
            if(class30_sub2_sub4_sub1_sub1 != null)
            {
                int i1 = class30_sub2_sub4_sub1.fineX - ((Mob) (class30_sub2_sub4_sub1_sub1)).fineX;
                int k1 = class30_sub2_sub4_sub1.fineY - ((Mob) (class30_sub2_sub4_sub1_sub1)).fineY;
                if(i1 != 0 || k1 != 0)
                    class30_sub2_sub4_sub1.anInt1510 = (int)(Math.atan2(i1, k1) * 325.94900000000001D) & 0x7ff;
            }
        }
        if(class30_sub2_sub4_sub1.anInt1502 >= 32768)
        {
            int j = class30_sub2_sub4_sub1.anInt1502 - 32768;
            if(j == localPlayerId)
                j = localPlayerIndex;
            Player class30_sub2_sub4_sub1_sub2 = playerArray[j];
            if(class30_sub2_sub4_sub1_sub2 != null)
            {
                int l1 = class30_sub2_sub4_sub1.fineX - ((Mob) (class30_sub2_sub4_sub1_sub2)).fineX;
                int i2 = class30_sub2_sub4_sub1.fineY - ((Mob) (class30_sub2_sub4_sub1_sub2)).fineY;
                if(l1 != 0 || i2 != 0)
                    class30_sub2_sub4_sub1.anInt1510 = (int)(Math.atan2(l1, i2) * 325.94900000000001D) & 0x7ff;
            }
        }
        if((class30_sub2_sub4_sub1.anInt1538 != 0 || class30_sub2_sub4_sub1.anInt1539 != 0) && (class30_sub2_sub4_sub1.stack_position_mob == 0 || class30_sub2_sub4_sub1.anInt1503 > 0))
        {
            int k = class30_sub2_sub4_sub1.fineX - (class30_sub2_sub4_sub1.anInt1538 - paletteX - paletteX) * 64;
            int j1 = class30_sub2_sub4_sub1.fineY - (class30_sub2_sub4_sub1.anInt1539 - paletteY - paletteY) * 64;
            if(k != 0 || j1 != 0)
                class30_sub2_sub4_sub1.anInt1510 = (int)(Math.atan2(k, j1) * 325.94900000000001D) & 0x7ff;
            class30_sub2_sub4_sub1.anInt1538 = 0;
            class30_sub2_sub4_sub1.anInt1539 = 0;
        }
        int l = class30_sub2_sub4_sub1.anInt1510 - class30_sub2_sub4_sub1.anInt1552 & 0x7ff;
        if(l != 0)
        {
            if(l < class30_sub2_sub4_sub1.rotation || l > 2048 - class30_sub2_sub4_sub1.rotation)
                class30_sub2_sub4_sub1.anInt1552 = class30_sub2_sub4_sub1.anInt1510;
            else
            if(l > 1024)
                class30_sub2_sub4_sub1.anInt1552 -= class30_sub2_sub4_sub1.rotation;
            else
                class30_sub2_sub4_sub1.anInt1552 += class30_sub2_sub4_sub1.rotation;
            class30_sub2_sub4_sub1.anInt1552 &= 0x7ff;
            if(class30_sub2_sub4_sub1.anInt1517 == class30_sub2_sub4_sub1.standAnimation && class30_sub2_sub4_sub1.anInt1552 != class30_sub2_sub4_sub1.anInt1510)
            {
                if(class30_sub2_sub4_sub1.standTurnAnimation != -1)
                {
                    class30_sub2_sub4_sub1.anInt1517 = class30_sub2_sub4_sub1.standTurnAnimation;
                    return;
                }
                class30_sub2_sub4_sub1.anInt1517 = class30_sub2_sub4_sub1.walkAnimation;
            }
        }
    }

    public void method101(Mob class30_sub2_sub4_sub1, int i)
    {
        if(i >= 0)
            aBoolean919 = !aBoolean919;
        class30_sub2_sub4_sub1.aBoolean1541 = false;
        if(class30_sub2_sub4_sub1.anInt1517 != -1)
        {
            AnimSequence class20 = AnimSequence.animationsequences[class30_sub2_sub4_sub1.anInt1517];
            class30_sub2_sub4_sub1.anInt1519++;
            if(class30_sub2_sub4_sub1.anInt1518 < class20.anInt352 && class30_sub2_sub4_sub1.anInt1519 > class20.method258(class30_sub2_sub4_sub1.anInt1518, (byte)-39))
            {
                class30_sub2_sub4_sub1.anInt1519 = 0;
                class30_sub2_sub4_sub1.anInt1518++;
            }
            if(class30_sub2_sub4_sub1.anInt1518 >= class20.anInt352)
            {
                class30_sub2_sub4_sub1.anInt1519 = 0;
                class30_sub2_sub4_sub1.anInt1518 = 0;
            }
        }
        if(class30_sub2_sub4_sub1.anInt1520 != -1 && loopCycle >= class30_sub2_sub4_sub1.anInt1523)
        {
            if(class30_sub2_sub4_sub1.anInt1521 < 0)
                class30_sub2_sub4_sub1.anInt1521 = 0;
            AnimSequence class20_1 = SpotAnim.aClass23Array403[class30_sub2_sub4_sub1.anInt1520].aClass20_407;
            for(class30_sub2_sub4_sub1.anInt1522++; class30_sub2_sub4_sub1.anInt1521 < class20_1.anInt352 && class30_sub2_sub4_sub1.anInt1522 > class20_1.method258(class30_sub2_sub4_sub1.anInt1521, (byte)-39); class30_sub2_sub4_sub1.anInt1521++)
                class30_sub2_sub4_sub1.anInt1522 -= class20_1.method258(class30_sub2_sub4_sub1.anInt1521, (byte)-39);

            if(class30_sub2_sub4_sub1.anInt1521 >= class20_1.anInt352 && (class30_sub2_sub4_sub1.anInt1521 < 0 || class30_sub2_sub4_sub1.anInt1521 >= class20_1.anInt352))
                class30_sub2_sub4_sub1.anInt1520 = -1;
        }
        if(class30_sub2_sub4_sub1.animid_request != -1 && class30_sub2_sub4_sub1.animdelay_request <= 1)
        {
            AnimSequence class20_2 = AnimSequence.animationsequences[class30_sub2_sub4_sub1.animid_request];
            if(class20_2.anInt363 == 1 && class30_sub2_sub4_sub1.anInt1542 > 0 && class30_sub2_sub4_sub1.forcewlk_sp1 <= loopCycle && class30_sub2_sub4_sub1.forcewlk_sp2 < loopCycle)
            {
                class30_sub2_sub4_sub1.animdelay_request = 1;
                return;
            }
        }
        if(class30_sub2_sub4_sub1.animid_request != -1 && class30_sub2_sub4_sub1.animdelay_request == 0)
        {
            AnimSequence class20_3 = AnimSequence.animationsequences[class30_sub2_sub4_sub1.animid_request];
            for(class30_sub2_sub4_sub1.anInt1528++; class30_sub2_sub4_sub1.anInt1527 < class20_3.anInt352 && class30_sub2_sub4_sub1.anInt1528 > class20_3.method258(class30_sub2_sub4_sub1.anInt1527, (byte)-39); class30_sub2_sub4_sub1.anInt1527++)
                class30_sub2_sub4_sub1.anInt1528 -= class20_3.method258(class30_sub2_sub4_sub1.anInt1527, (byte)-39);

            if(class30_sub2_sub4_sub1.anInt1527 >= class20_3.anInt352)
            {
                class30_sub2_sub4_sub1.anInt1527 -= class20_3.anInt356;
                class30_sub2_sub4_sub1.anInt1530++;
                if(class30_sub2_sub4_sub1.anInt1530 >= class20_3.anInt362)
                    class30_sub2_sub4_sub1.animid_request = -1;
                if(class30_sub2_sub4_sub1.anInt1527 < 0 || class30_sub2_sub4_sub1.anInt1527 >= class20_3.anInt352)
                    class30_sub2_sub4_sub1.animid_request = -1;
            }
            class30_sub2_sub4_sub1.aBoolean1541 = class20_3.aBoolean358;
        }
        if(class30_sub2_sub4_sub1.animdelay_request > 0)
            class30_sub2_sub4_sub1.animdelay_request--;
    }

    public void drawGameScreen(boolean flag)
    {
        if(!flag)
            anInt939 = packetencryption.poll();
        if(paintRequested)
        {
            paintRequested = false;
            backleft1_imagefetcher.updateGraphics(4, 23680, super.appletGraphics, 0);
            backleft2_imagefetcher.updateGraphics(357, 23680, super.appletGraphics, 0);
            backright1_imagefetcher.updateGraphics(4, 23680, super.appletGraphics, 722);
            backright2_imagefetcher.updateGraphics(205, 23680, super.appletGraphics, 743);
            backtop1_imagefetcher.updateGraphics(0, 23680, super.appletGraphics, 0);
            backvmid1_imagefetcher.updateGraphics(4, 23680, super.appletGraphics, 516);
            backvmid2_imagefetcher.updateGraphics(205, 23680, super.appletGraphics, 516);
            backvmid3_imagefetcher.updateGraphics(357, 23680, super.appletGraphics, 496);
            backhmid2_imagefetcher.updateGraphics(338, 23680, super.appletGraphics, 0);
            aBoolean1153 = true;
            aBoolean1223 = true;
            update_tabs = true;
            updatetoolbar = true;
            if(landscape_stage != 2)
            {
                toplefttext_imagefetcher.updateGraphics(4, 23680, super.appletGraphics, 4);
                aClass15_1164.updateGraphics(4, 23680, super.appletGraphics, 550);
            }
        }
        if(landscape_stage == 2)
            method146((byte)1);
        if(aBoolean885 && clickarea == 1)
            aBoolean1153 = true;
        if(anInt1189 != -1)
        {
            boolean flag1 = method119(anInt945, false, anInt1189);
            if(flag1)
                aBoolean1153 = true;
        }
        if(anInt1246 == 2)
            aBoolean1153 = true;
        if(anInt1086 == 2)
            aBoolean1153 = true;
        if(aBoolean1153)
        {
            method36((byte)-81);
            aBoolean1153 = false;
        }
        if(anInt1276 == -1)
        {
            aClass9_1059.anInt224 = anInt1211 - anInt1089 - 77;
            if(super.newMouseX > 448 && super.newMouseX < 560 && super.newMouseY > 332)
                method65(463, 77, super.newMouseX - 17, super.newMouseY - 357, aClass9_1059, 0, false, anInt1211, 0);
            int i = anInt1211 - 77 - aClass9_1059.anInt224;
            if(i < 0)
                i = 0;
            if(i > anInt1211 - 77)
                i = anInt1211 - 77;
            if(anInt1089 != i)
            {
                anInt1089 = i;
                aBoolean1223 = true;
            }
        }
        if(anInt1276 != -1)
        {
            boolean flag2 = method119(anInt945, false, anInt1276);
            if(flag2)
                aBoolean1223 = true;
        }
        if(anInt1246 == 3)
            aBoolean1223 = true;
        if(anInt1086 == 3)
            aBoolean1223 = true;
        if(aString844 != null)
            aBoolean1223 = true;
        if(aBoolean885 && clickarea == 2)
            aBoolean1223 = true;
        if(aBoolean1223)
        {
            renderChat(6);
            aBoolean1223 = false;
        }
        if(landscape_stage == 2)
        {
            drawMinimap(false);
            aClass15_1164.updateGraphics(4, 23680, super.appletGraphics, 550);
        }
        if(anInt1054 != -1)
            update_tabs = true;
        if(update_tabs)
        {
            if(anInt1054 != -1 && anInt1054 == current_tab)
            {
                anInt1054 = -1;
                gameBuffer.putPacket(120);
                gameBuffer.put(current_tab);
            }
            update_tabs = false;
            aClass15_1125.initialize(0);
            backmid1.renderImage(0, 16083, 0);
            if(anInt1189 == -1)
            {
                if(tab_interfaces[current_tab] != -1)
                {
                    if(current_tab == 0)
                        redstone1.renderImage(22, 16083, 10);
                    if(current_tab == 1)
                        redstone2.renderImage(54, 16083, 8);
                    if(current_tab == 2)
                        redstone2.renderImage(82, 16083, 8);
                    if(current_tab == 3)
                        redstone3.renderImage(110, 16083, 8);
                    if(current_tab == 4)
                        redstone2_2.renderImage(153, 16083, 8);
                    if(current_tab == 5)
                        redstone2_2.renderImage(181, 16083, 8);
                    if(current_tab == 6)
                        redstone1_2.renderImage(209, 16083, 9);
                }
                if(tab_interfaces[0] != -1 && (anInt1054 != 0 || loopCycle % 20 < 10))
                    sideicons[0].renderImage(29, 16083, 13);
                if(tab_interfaces[1] != -1 && (anInt1054 != 1 || loopCycle % 20 < 10))
                    sideicons[1].renderImage(53, 16083, 11);
                if(tab_interfaces[2] != -1 && (anInt1054 != 2 || loopCycle % 20 < 10))
                    sideicons[2].renderImage(82, 16083, 11);
                if(tab_interfaces[3] != -1 && (anInt1054 != 3 || loopCycle % 20 < 10))
                    sideicons[3].renderImage(115, 16083, 12);
                if(tab_interfaces[4] != -1 && (anInt1054 != 4 || loopCycle % 20 < 10))
                    sideicons[4].renderImage(153, 16083, 13);
                if(tab_interfaces[5] != -1 && (anInt1054 != 5 || loopCycle % 20 < 10))
                    sideicons[5].renderImage(180, 16083, 11);
                if(tab_interfaces[6] != -1 && (anInt1054 != 6 || loopCycle % 20 < 10))
                    sideicons[6].renderImage(208, 16083, 13);
            }
            aClass15_1125.updateGraphics(160, 23680, super.appletGraphics, 516);
            aClass15_1124.initialize(0);
            backbase2.renderImage(0, 16083, 0);
            if(anInt1189 == -1)
            {
                if(tab_interfaces[current_tab] != -1)
                {
                    if(current_tab == 7)
                        redstone1_3.renderImage(42, 16083, 0);
                    if(current_tab == 8)
                        redstone2_3.renderImage(74, 16083, 0);
                    if(current_tab == 9)
                        redstone2_3.renderImage(102, 16083, 0);
                    if(current_tab == 10)
                        redstone3_2.renderImage(130, 16083, 1);
                    if(current_tab == 11)
                        redstone2_4.renderImage(173, 16083, 0);
                    if(current_tab == 12)
                        redstone2_4.renderImage(201, 16083, 0);
                    if(current_tab == 13)
                        redstone1_4.renderImage(229, 16083, 0);
                }
                if(tab_interfaces[8] != -1 && (anInt1054 != 8 || loopCycle % 20 < 10))
                    sideicons[7].renderImage(74, 16083, 2);
                if(tab_interfaces[9] != -1 && (anInt1054 != 9 || loopCycle % 20 < 10))
                    sideicons[8].renderImage(102, 16083, 3);
                if(tab_interfaces[10] != -1 && (anInt1054 != 10 || loopCycle % 20 < 10))
                    sideicons[9].renderImage(137, 16083, 4);
                if(tab_interfaces[11] != -1 && (anInt1054 != 11 || loopCycle % 20 < 10))
                    sideicons[10].renderImage(174, 16083, 2);
                if(tab_interfaces[12] != -1 && (anInt1054 != 12 || loopCycle % 20 < 10))
                    sideicons[11].renderImage(201, 16083, 2);
                if(tab_interfaces[13] != -1 && (anInt1054 != 13 || loopCycle % 20 < 10))
                    sideicons[12].renderImage(226, 16083, 2);
            }
            aClass15_1124.updateGraphics(466, 23680, super.appletGraphics, 496);
            toplefttext_imagefetcher.initialize(0);
        }
        if(updatetoolbar)
        {
            updatetoolbar = false;
            toolbartext_imagefetcher.initialize(0);
            backbase1.renderImage(0, 16083, 0);
            p12Font.drawCenteredXText(0xffffff, 55, anInt939, "Public chat", 28, true);
            if(anInt1287 == 0)
                p12Font.drawCenteredXText(65280, 55, anInt939, "On", 41, true);
            if(anInt1287 == 1)
                p12Font.drawCenteredXText(0xffff00, 55, anInt939, "Friends", 41, true);
            if(anInt1287 == 2)
                p12Font.drawCenteredXText(0xff0000, 55, anInt939, "Off", 41, true);
            if(anInt1287 == 3)
                p12Font.drawCenteredXText(65535, 55, anInt939, "Hide", 41, true);
            p12Font.drawCenteredXText(0xffffff, 184, anInt939, "Private chat", 28, true);
            if(anInt845 == 0)
                p12Font.drawCenteredXText(65280, 184, anInt939, "On", 41, true);
            if(anInt845 == 1)
                p12Font.drawCenteredXText(0xffff00, 184, anInt939, "Friends", 41, true);
            if(anInt845 == 2)
                p12Font.drawCenteredXText(0xff0000, 184, anInt939, "Off", 41, true);
            p12Font.drawCenteredXText(0xffffff, 324, anInt939, "Trade/compete", 28, true);
            if(anInt1248 == 0)
                p12Font.drawCenteredXText(65280, 324, anInt939, "On", 41, true);
            if(anInt1248 == 1)
                p12Font.drawCenteredXText(0xffff00, 324, anInt939, "Friends", 41, true);
            if(anInt1248 == 2)
                p12Font.drawCenteredXText(0xff0000, 324, anInt939, "Off", 41, true);
            p12Font.drawCenteredXText(0xffffff, 458, anInt939, "Report abuse", 33, true);
            toolbartext_imagefetcher.updateGraphics(453, 23680, super.appletGraphics, 0);
            toplefttext_imagefetcher.initialize(0);
        }
        anInt945 = 0;
    }

    public boolean method103(Widget class9, boolean flag)
    {
        int i = class9.actionCode;
        if(flag)
            loadClient();
        if(i >= 1 && i <= 200 || i >= 701 && i <= 900)
        {
            if(i >= 801)
                i -= 701;
            else
            if(i >= 701)
                i -= 601;
            else
            if(i >= 101)
                i -= 101;
            else
                i--;
            interfacestringstack[anInt1133] = "Remove @whi@" + friendusernames[i];
            interfaceopcodestack[anInt1133] = 792;
            anInt1133++;
            interfacestringstack[anInt1133] = "Message @whi@" + friendusernames[i];
            interfaceopcodestack[anInt1133] = 639;
            anInt1133++;
            return true;
        }
        if(i >= 401 && i <= 500)
        {
            interfacestringstack[anInt1133] = "Remove @whi@" + class9.inactiveText;
            interfaceopcodestack[anInt1133] = 322;
            anInt1133++;
            return true;
        } else
        {
            return false;
        }
    }

    public void processGFXs(boolean flag)
    {
        StillGraphic class30_sub2_sub4_sub3 = (StillGraphic)gfxs_storage.getFirst();
        isOnlineGame &= flag;
        for(; class30_sub2_sub4_sub3 != null; class30_sub2_sub4_sub3 = (StillGraphic)gfxs_storage.getNextFront())
            if(class30_sub2_sub4_sub3.anInt1560 != currentZ || class30_sub2_sub4_sub3.destroy_gfx)
                class30_sub2_sub4_sub3.removeDeque();
            else
            if(loopCycle >= class30_sub2_sub4_sub3.runcycle)
            {
                class30_sub2_sub4_sub3.method454(anInt945, true);
                if(class30_sub2_sub4_sub3.destroy_gfx)
                    class30_sub2_sub4_sub3.removeDeque();
                else
                    pallet.method285(class30_sub2_sub4_sub3.anInt1560, 0, (byte)6, class30_sub2_sub4_sub3.anInt1563, -1, class30_sub2_sub4_sub3.anInt1562, 60, class30_sub2_sub4_sub3.anInt1561, class30_sub2_sub4_sub3, false);
            }

    }

    public void drawWidget(Widget widget, int offsetx, int offsety, int offsety$)
    {
        if(widget.type != 0 || widget.childrenIds == null)
            return;
        if(widget.isActive && anInt1026 != widget.widgetId && anInt1048 != widget.widgetId && anInt1039 != widget.widgetId)
            return;
        int rasterWidthOffset = BasicRasterizer.widthOffset;
        int rasterHeightOffset = BasicRasterizer.heightOffset;
        int rasterWidth = BasicRasterizer.width;
        int rasterHeight = BasicRasterizer.height;
        BasicRasterizer.setDimensions(offsetx + widget.width, offsetx, offsety + widget.height, offsety);
        int amountchildren = widget.childrenIds.length;
        for(int child = 0; child < amountchildren; child++)
        {
            int x = widget.childrenOffX[child] + offsetx;
            int y = (widget.childrenOffY[child] + offsety) - offsety$;
            Widget childWidget = Widget.widgets[widget.childrenIds[child]];
            x += childWidget.offsetX;
            y += childWidget.offsetY;
            if(childWidget.actionCode > 0)
                handleWidgetActionCode(childWidget);
            if(childWidget.type == 0)
            {
                if(childWidget.anInt224 > childWidget.currentHeight - childWidget.height)
                    childWidget.anInt224 = childWidget.currentHeight - childWidget.height;
                if(childWidget.anInt224 < 0)
                    childWidget.anInt224 = 0;
                drawWidget(childWidget, x, y, childWidget.anInt224);
                if(childWidget.currentHeight > childWidget.height)
                    drawScrollBar(519, childWidget.height, childWidget.anInt224, y, x + childWidget.width, childWidget.currentHeight);
            } else
            if(childWidget.type != 1)
                if(childWidget.type == 2)
                {
                    int spriteOffset = 0;
                    for(int y$ = 0; y$ < childWidget.height; y$++)
                    {
                        for(int x$ = 0; x$ < childWidget.width; x$++)
                        {
                            int drawX = x + x$ * (32 + childWidget.xOff);
                            int drawY = y + y$ * (32 + childWidget.yOff);
                            if(spriteOffset < 20)
                            {
                                drawX += childWidget.anIntArray215[spriteOffset];
                                drawY += childWidget.anIntArray247[spriteOffset];
                            }
                            if(childWidget.itemIds[spriteOffset] > 0)
                            {
                                int k6 = 0;
                                int j7 = 0;
                                int itemid = childWidget.itemIds[spriteOffset] - 1;
                                if(drawX > BasicRasterizer.widthOffset - 32 && drawX < BasicRasterizer.width && drawY > BasicRasterizer.heightOffset - 32 && drawY < BasicRasterizer.height || anInt1086 != 0 && moveitem_startslot == spriteOffset)
                                {
                                    int l9 = 0;
                                    if(anInt1282 == 1 && anInt1283 == spriteOffset && anInt1284 == childWidget.widgetId)
                                        l9 = 0xffffff;
                                    DirectColorSprite sprite = ItemDefinition.asSprite(itemid, childWidget.itemAmounts[spriteOffset], l9, 9);
                                    if(sprite != null)
                                    {
                                        if(anInt1086 != 0 && moveitem_startslot == spriteOffset && moveitem_frameid == childWidget.widgetId)
                                        {
                                            k6 = super.newMouseX - anInt1087;
                                            j7 = super.newMouseY - anInt1088;
                                            if(k6 < 5 && k6 > -5)
                                                k6 = 0;
                                            if(j7 < 5 && j7 > -5)
                                                j7 = 0;
                                            if(anInt989 < 5)
                                            {
                                                k6 = 0;
                                                j7 = 0;
                                            }
                                            sprite.drawBlended(drawX + k6, drawY + j7, 128, aBoolean1043);
                                            if(drawY + j7 < BasicRasterizer.heightOffset && childWidget.anInt224 > 0)
                                            {
                                                int i10 = (anInt945 * (BasicRasterizer.heightOffset - drawY - j7)) / 3;
                                                if(i10 > anInt945 * 10)
                                                    i10 = anInt945 * 10;
                                                if(i10 > childWidget.anInt224)
                                                    i10 = childWidget.anInt224;
                                                childWidget.anInt224 -= i10;
                                                anInt1088 += i10;
                                            }
                                            if(drawY + j7 + 32 > BasicRasterizer.height && childWidget.anInt224 < childWidget.currentHeight - childWidget.height)
                                            {
                                                int j10 = (anInt945 * ((drawY + j7 + 32) - BasicRasterizer.height)) / 3;
                                                if(j10 > anInt945 * 10)
                                                    j10 = anInt945 * 10;
                                                if(j10 > childWidget.currentHeight - childWidget.height - childWidget.anInt224)
                                                    j10 = childWidget.currentHeight - childWidget.height - childWidget.anInt224;
                                                childWidget.anInt224 += j10;
                                                anInt1088 -= j10;
                                            }
                                        } else
                                        if(anInt1246 != 0 && anInt1245 == spriteOffset && anInt1244 == childWidget.widgetId)
                                            sprite.drawBlended(drawX, drawY, 128, aBoolean1043);
                                        else
                                            sprite.draw(drawX, 16083, drawY);
                                        if(sprite.spriteWidth == 33 || childWidget.itemAmounts[spriteOffset] != 1)
                                        {
                                            int k10 = childWidget.itemAmounts[spriteOffset];
                                            p11Font.drawText(0, amountToAmountStr(-33245, k10), drawY + 10 + j7, 822, drawX + 1 + k6);
                                            p11Font.drawText(0xffff00, amountToAmountStr(-33245, k10), drawY + 9 + j7, 822, drawX + k6);
                                        }
                                    }
                                }
                            } else
                            if(childWidget.sprites != null && spriteOffset < 20)
                            {
                                DirectColorSprite sprite = childWidget.sprites[spriteOffset];
                                if(sprite != null)
                                    sprite.draw(drawX, 16083, drawY);
                            }
                            spriteOffset++;
                        }

                    }

                } else
                if(childWidget.type == 3)
                {
                    boolean flag = false;
                    if(anInt1039 == childWidget.widgetId || anInt1048 == childWidget.widgetId || anInt1026 == childWidget.widgetId)
                        flag = true;
                    int j3;
                    if(executeWidgetScripts(childWidget))
                    {
                        j3 = childWidget.activeTextColor;
                        if(flag && childWidget.anInt239 != 0)
                            j3 = childWidget.anInt239;
                    } else
                    {
                        j3 = childWidget.inactiveTextColor;
                        if(flag && childWidget.anInt216 != 0)
                            j3 = childWidget.anInt216;
                    }
                    if(childWidget.alpha == 0)
                    {
                        if(childWidget.isSolidQuad)
                            BasicRasterizer.drawQuad( x, y, childWidget.width, childWidget.height, j3);
                        else
                            BasicRasterizer.drawQuadrilateralOutline(x, y, childWidget.width, childWidget.height, j3);
                    } else
                    if(childWidget.isSolidQuad)
                        BasicRasterizer.drawQuad(x, y, childWidget.width, childWidget.height, 256 - (childWidget.alpha & 0xff), j3);
                    else
                        BasicRasterizer.drawQuadOutline(x, y, childWidget.width, childWidget.height, 256 - (childWidget.alpha & 0xff), j3);
                } else
                if(childWidget.type == 4)
                {
                    BitmapFont font = childWidget.textFont;
                    String s = childWidget.inactiveText;
                    boolean flag1 = false;
                    if(anInt1039 == childWidget.widgetId || anInt1048 == childWidget.widgetId || anInt1026 == childWidget.widgetId)
                        flag1 = true;
                    int color;
                    if(executeWidgetScripts(childWidget))
                    {
                        color = childWidget.activeTextColor;
                        if(flag1 && childWidget.anInt239 != 0)
                            color = childWidget.anInt239;
                        if(childWidget.activeText.length() > 0)
                            s = childWidget.activeText;
                    } else
                    {
                        color = childWidget.inactiveTextColor;
                        if(flag1 && childWidget.anInt216 != 0)
                            color = childWidget.anInt216;
                    }
                    if(childWidget.fieldType == 6 && aBoolean1149)
                    {
                        s = "Please wait...";
                        color = childWidget.inactiveTextColor;
                    }
                    if(BasicRasterizer.bufferWidth == 479)
                    {
                        if(color == 0xffff00)
                            color = 255;
                        if(color == 49152)
                            color = 0xffffff;
                    }
                    for(int l6 = y + font.maxh; s.length() > 0; l6 += font.maxh)
                    {
                        if(s.indexOf("%") != -1)
                        {
                            do
                            {
                                int k7 = s.indexOf("%1");
                                if(k7 == -1)
                                    break;
                                s = s.substring(0, k7) + method93(369, handleWidgetScript(childWidget, 0)) + s.substring(k7 + 2);
                            } while(true);
                            do
                            {
                                int l7 = s.indexOf("%2");
                                if(l7 == -1)
                                    break;
                                s = s.substring(0, l7) + method93(369, handleWidgetScript(childWidget, 1)) + s.substring(l7 + 2);
                            } while(true);
                            do
                            {
                                int i8 = s.indexOf("%3");
                                if(i8 == -1)
                                    break;
                                s = s.substring(0, i8) + method93(369, handleWidgetScript(childWidget, 2)) + s.substring(i8 + 2);
                            } while(true);
                            do
                            {
                                int j8 = s.indexOf("%4");
                                if(j8 == -1)
                                    break;
                                s = s.substring(0, j8) + method93(369, handleWidgetScript(childWidget, 3)) + s.substring(j8 + 2);
                            } while(true);
                            do
                            {
                                int k8 = s.indexOf("%5");
                                if(k8 == -1)
                                    break;
                                s = s.substring(0, k8) + method93(369, handleWidgetScript(childWidget, 4)) + s.substring(k8 + 2);
                            } while(true);
                        }
                        int l8 = s.indexOf("\\n");
                        String text;
                        if(l8 != -1)
                        {
                            text = s.substring(0, l8);
                            s = s.substring(l8 + 2);
                        } else
                        {
                            text = s;
                            s = "";
                        }
                        if(childWidget.isTextCentered)
                            font.drawCenteredXText(color, x + childWidget.width / 2, anInt939, text, l6, childWidget.drawTextShadow);
                        else
                            font.drawText2(false, childWidget.drawTextShadow, x, color, text, l6);
                    }

                } else
                if(childWidget.type == 5)
                {
                    DirectColorSprite sprite;
                    if(executeWidgetScripts(childWidget))
                        sprite = childWidget.activeSprite;
                    else
                        sprite = childWidget.inactiveSprite;
                    if(sprite != null)
                        sprite.draw(x, 16083, y);
                } else
                if(childWidget.type == 6)
                {
                    int cW = TriangleRasterizer.centerWidth;
                    int cH = TriangleRasterizer.centerHeight;
                    TriangleRasterizer.centerWidth = x + childWidget.width / 2;
                    TriangleRasterizer.centerHeight = y + childWidget.height / 2;
                    int i5 = TriangleRasterizer.SINE_TABLE[childWidget.rotationAngleX] * childWidget.rotationOrigin >> 16;
                    int l5 = TriangleRasterizer.COSINE_TABLE[childWidget.rotationAngleX] * childWidget.rotationOrigin >> 16;
                    boolean isActive = executeWidgetScripts(childWidget);
                    int animId;
                    if(isActive)
                        animId = childWidget.activeAnimId;
                    else
                        animId = childWidget.inactiveAnimId;
                    Model model;
                    if(animId == -1)
                    {
                        model = childWidget.getAnimatedModel(-1, -1, isActive);
                    } else
                    {
                        AnimSequence sequence = AnimSequence.animationsequences[animId];
                        model = childWidget.getAnimatedModel(sequence.anIntArray354[childWidget.anInt246], sequence.anIntArray353[childWidget.anInt246], isActive);
                    }
                    if(model != null)
                        model.drawModel(0, childWidget.rotationAngleY, 0, childWidget.rotationAngleX, 0, i5, l5);
                    TriangleRasterizer.centerWidth = cW;
                    TriangleRasterizer.centerHeight = cH;
                } else
                if(childWidget.type == 7)
                {
                    BitmapFont class30_sub2_sub1_sub4_1 = childWidget.textFont;
                    int k4 = 0;
                    for(int j5 = 0; j5 < childWidget.height; j5++)
                    {
                        for(int i6 = 0; i6 < childWidget.width; i6++)
                        {
                            if(childWidget.itemIds[k4] > 0)
                            {
                                ItemDefinition class8 = ItemDefinition.getItemDefinition(childWidget.itemIds[k4] - 1);
                                String s2 = class8.withItemName;
                                if(class8.aBoolean176 || childWidget.itemAmounts[k4] != 1)
                                    s2 = s2 + " x" + method14(childWidget.itemAmounts[k4], 0);
                                int i9 = x + i6 * (115 + childWidget.xOff);
                                int k9 = y + j5 * (12 + childWidget.yOff);
                                if(childWidget.isTextCentered)
                                    class30_sub2_sub1_sub4_1.drawCenteredXText(childWidget.inactiveTextColor, i9 + childWidget.width / 2, anInt939, s2, k9, childWidget.drawTextShadow);
                                else
                                    class30_sub2_sub1_sub4_1.drawText2(false, childWidget.drawTextShadow, i9, childWidget.inactiveTextColor, s2, k9);
                            }
                            k4++;
                        }

                    }

                }
        }
        BasicRasterizer.setDimensions(rasterWidth, rasterWidthOffset, rasterHeight, rasterHeightOffset);
    }

    public void method106(IndexedColorSprite class30_sub2_sub1_sub2, int i)
    {
        int j = 256;
        if(i >= 0)
            gameBuffer.put(126);
        for(int k = 0; k < anIntArray1190.length; k++)
            anIntArray1190[k] = 0;

        for(int l = 0; l < 5000; l++)
        {
            int i1 = (int)(Math.random() * 128D * (double)j);
            anIntArray1190[i1] = (int)(Math.random() * 256D);
        }

        for(int j1 = 0; j1 < 20; j1++)
        {
            for(int k1 = 1; k1 < j - 1; k1++)
            {
                for(int i2 = 1; i2 < 127; i2++)
                {
                    int k2 = i2 + (k1 << 7);
                    anIntArray1191[k2] = (anIntArray1190[k2 - 1] + anIntArray1190[k2 + 1] + anIntArray1190[k2 - 128] + anIntArray1190[k2 + 128]) / 4;
                }

            }

            int ai[] = anIntArray1190;
            anIntArray1190 = anIntArray1191;
            anIntArray1191 = ai;
        }

        if(class30_sub2_sub1_sub2 != null)
        {
            int l1 = 0;
            for(int j2 = 0; j2 < class30_sub2_sub1_sub2.indexHeight; j2++)
            {
                for(int l2 = 0; l2 < class30_sub2_sub1_sub2.indexWidth; l2++)
                    if(class30_sub2_sub1_sub2.buffer[l1++] != 0)
                    {
                        int i3 = l2 + 16 + class30_sub2_sub1_sub2.offsetX;
                        int j3 = j2 + 16 + class30_sub2_sub1_sub2.offsetY;
                        int k3 = i3 + (j3 << 7);
                        anIntArray1190[k3] = 0;
                    }

            }

        }
    }

    public void parsePlayerUpdateFlags(int flags, int id, ByteBuffer buffer, Player player)
    {
        if((flags & 0x400) != 0)
        {
            player.forcewlk_startx = buffer.getUbyteB();
            player.forcewlk_starty = buffer.getUbyteB();
            player.forcewlk_endx = buffer.getUbyteB();
            player.forcewlk_endy = buffer.getUbyteB();
            player.forcewlk_sp1 = buffer.getUwordLE128() + loopCycle;
            player.forcewlk_sp2 = buffer.getUword128() + loopCycle;
            player.forcewlk_dir = buffer.getUbyteB();
            player.method446(true);
        }
        if((flags & 0x100) != 0)
        {
            player.anInt1520 = buffer.getUwordLE();
            int k = buffer.getDword();
            player.anInt1524 = k >> 16;
            player.anInt1523 = loopCycle + (k & 0xffff);
            player.anInt1521 = 0;
            player.anInt1522 = 0;
            if(((Mob) (player)).anInt1523 > loopCycle)
                player.anInt1521 = -1;
            if(((Mob) (player)).anInt1520 == 65535)
                player.anInt1520 = -1;
        }
		/* Animation */
        if((flags & 8) != 0)
        {
            int l = buffer.getUwordLE();
            if(l == 65535)
                l = -1;
            int i2 = buffer.getUbyteA();
            if(l == ((Mob) (player)).animid_request && l != -1)
            {
                int i3 = AnimSequence.animationsequences[l].anInt365;
                if(i3 == 1) {
                    player.anInt1527 = 0;
                    player.anInt1528 = 0;
                    player.animdelay_request = i2;
                    player.anInt1530 = 0;
                }
                if(i3 == 2)
                    player.anInt1530 = 0;
            } else
            if(l == -1 || ((Mob) (player)).animid_request == -1 || AnimSequence.animationsequences[l].anInt359 >= AnimSequence.animationsequences[((Mob) (player)).animid_request].anInt359)
            {
                player.animid_request = l;
                player.anInt1527 = 0;
                player.anInt1528 = 0;
                player.animdelay_request = i2;
                player.anInt1530 = 0;
                player.anInt1542 = ((Mob) (player)).stack_position_mob;
            }
        }
        if((flags & 4) != 0)
        {
            player.chat_txt = buffer.getString();
            if(((Mob) (player)).chat_txt.charAt(0) == '~')
            {
                player.chat_txt = ((Mob) (player)).chat_txt.substring(1);
                pushMessage(((Mob) (player)).chat_txt, 2, player.name, aBoolean991);
            } else
            if(player == localPlayer)
                pushMessage(((Mob) (player)).chat_txt, 2, player.name, aBoolean991);
            player.anInt1513 = 0;
            player.anInt1531 = 0;
            player.anInt1535 = 150;
        }
        if((flags & 0x80) != 0)
        {
            int i1 = buffer.getUwordLE();
            int j2 = buffer.getUbyte();
            int j3 = buffer.getUbyteA();
            int k3 = buffer.offset;
            if(player.name != null && player.updated)
            {
                long l3 = TextTools.stringToLong(player.name);
                boolean flag = false;
                if(j2 <= 1)
                {
                    for(int i4 = 0; i4 < amt_ignorehashes; i4++)
                    {
                        if(ignore_hashes[i4] != l3)
                            continue;
                        flag = true;
                        break;
                    }

                }
                if(!flag && ontutorial_island == 0)
                    try
                    {
                        aClass30_Sub2_Sub2_834.offset = 0;
                        buffer.getReverse(aClass30_Sub2_Sub2_834.payload, 0, j3);
                        aClass30_Sub2_Sub2_834.offset = 0;
                        String s = ChatUtils.method525(j3, true, aClass30_Sub2_Sub2_834);
                        s = Censor.censor(s, 0);
                        player.chat_txt = s;
                        player.anInt1513 = i1 >> 8;
                        player.anInt1531 = i1 & 0xff;
                        player.anInt1535 = 150;
                        if(j2 == 2 || j2 == 3)
                            pushMessage(s, 1, "@cr2@" + player.name, aBoolean991);
                        else
                        if(j2 == 1)
                            pushMessage(s, 1, "@cr1@" + player.name, aBoolean991);
                        else
                            pushMessage(s, 2, player.name, aBoolean991);
                    }
                    catch(Exception exception)
                    {
                        Signlink.reportError("cde2");
                    }
            }
            buffer.offset = k3 + j3;
        }
        if((flags & 1) != 0)
        {
            player.anInt1502 = buffer.getUwordLE();
            if(((Mob) (player)).anInt1502 == 65535)
                player.anInt1502 = -1;
        }
        if((flags & 0x10) != 0)
        {
            int size = buffer.getUbyteA();
            byte src[] = new byte[size];
            ByteBuffer appearanceBuffer = new ByteBuffer(src);
            buffer.get(src, 0, size);
            appearanceBuffers[id] = appearanceBuffer;
            player.parseAppearance(appearanceBuffer);
        }
        if((flags & 2) != 0)
        {
            player.anInt1538 = buffer.getUwordLE128();
            player.anInt1539 = buffer.getUwordLE();
        }
        if((flags & 0x20) != 0)
        {
            int k1 = buffer.getUbyte();
            int k2 = buffer.getByte128();
            player.pushHit(-35698, k2, k1, loopCycle);
            player.anInt1532 = loopCycle + 300;
            player.anInt1533 = buffer.getUbyteA();
            player.anInt1534 = buffer.getUbyte();
        }
        if((flags & 0x200) != 0)
        {
            int l1 = buffer.getUbyte();
            int l2 = buffer.getUbyteB();
            player.pushHit(-35698, l2, l1, loopCycle);
            player.anInt1532 = loopCycle + 300;
            player.anInt1533 = buffer.getUbyte();
            player.anInt1534 = buffer.getUbyteA();
        }
    }

    public void handleKeyboardArrows(int i)
    {
        if(i != 3)
            packetId = -1;
        try
        {
            int finex = ((Mob) (localPlayer)).fineX + anInt1278;
            int finey = ((Mob) (localPlayer)).fineY + anInt1131;
            if(anInt1014 - finex < -500 || anInt1014 - finex > 500 || anInt1015 - finey < -500 || anInt1015 - finey > 500)
            {
                anInt1014 = finex;
                anInt1015 = finey;
            }
            if(anInt1014 != finex)
                anInt1014 += (finex - anInt1014) / 16;
            if(anInt1015 != finey)
                anInt1015 += (finey - anInt1015) / 16;
            /* Left */
            if(super.activeKeycodes[1] == 1)
                camerayawrate += (-24 - camerayawrate) / 2;
            /* Right */
            else
            if(super.activeKeycodes[2] == 1)
                camerayawrate += (24 - camerayawrate) / 2;
            /* Up */
            else
                camerayawrate /= 2;
            if(super.activeKeycodes[3] == 1)
                camerayrate += (12 - camerayrate) / 2;
            /* Down */
            else
            if(super.activeKeycodes[4] == 1)
                camerayrate += (-12 - camerayrate) / 2;
            else
                camerayrate /= 2;
            cameraYaw = cameraYaw + camerayawrate / 2 & 0x7ff;
            cameraPitch += camerayrate / 2;
            if(cameraPitch < 128)
                cameraPitch = 128;
            if(cameraPitch > 383)
                cameraPitch = 383;
            int l = anInt1014 >> 7;
            int i1 = anInt1015 >> 7;
            int j1 = calculateTileHeight(anInt1014, anInt1015, currentZ);
            int k1 = 0;
            if(l > 3 && i1 > 3 && l < 100 && i1 < 100)
            {
                for(int l1 = l - 4; l1 <= l + 4; l1++)
                {
                    for(int k2 = i1 - 4; k2 <= i1 + 4; k2++)
                    {
                        int l2 = currentZ;
                        if(l2 < 3 && (tileFlags[1][l1][k2] & 2) == 2)
                            l2++;
                        int i3 = j1 - tileHeightmap[l2][l1][k2];
                        if(i3 > k1)
                            k1 = i3;
                    }

                }

            }
            anInt1005++;
            if(anInt1005 > 1512)
            {
                anInt1005 = 0;
                gameBuffer.putPacket(77);
                gameBuffer.put(0);
                int i2 = gameBuffer.offset;
                gameBuffer.put((int)(Math.random() * 256D));
                gameBuffer.put(101);
                gameBuffer.put(233);
                gameBuffer.putWord(45092);
                if((int)(Math.random() * 2D) == 0)
                    gameBuffer.putWord(35784);
                gameBuffer.put((int)(Math.random() * 256D));
                gameBuffer.put(64);
                gameBuffer.put(38);
                gameBuffer.putWord((int)(Math.random() * 65536D));
                gameBuffer.putWord((int)(Math.random() * 65536D));
                gameBuffer.endVarByte(gameBuffer.offset - i2, (byte)0);
            }
            int j2 = k1 * 192;
            if(j2 > 0x17f00)
                j2 = 0x17f00;
            if(j2 < 32768)
                j2 = 32768;
            if(j2 > anInt984)
            {
                anInt984 += (j2 - anInt984) / 24;
                return;
            }
            if(j2 < anInt984)
            {
                anInt984 += (j2 - anInt984) / 80;
                return;
            }
        }
        catch(Exception _ex)
        {
            Signlink.reportError("glfc_ex " + ((Mob) (localPlayer)).fineX + "," + ((Mob) (localPlayer)).fineY + "," + anInt1014 + "," + anInt1015 + "," + chunkx_ + "," + chunky_ + "," + paletteX + "," + paletteY);
            throw new RuntimeException("eek");
        }
    }

    public void handleDrawCycle(int junk)
    {
        if(aBoolean1252 || aBoolean926 || aBoolean1176)
        {
            method94(-13873);
            return;
        }
        drawCycle++;
        if(!isOnlineGame)
            drawTitleScreen(false, false);
        else
            drawGameScreen(true);
        anInt1213 = 0;
    }

    public boolean onFriendsList(boolean flag, String s)
    {
        if(s == null)
            return false;
        for(int i = 0; i < amt_friendhashes; i++)
            if(s.equalsIgnoreCase(friendusernames[i]))
                return true;

        if(flag)
            gameBuffer.put(138);
        return s.equalsIgnoreCase(localPlayer.name);
    }

    public static String getLevelColor(int i, int j, boolean junk)
    {
        int k = i - j;
        if(k < -9)
            return "@red@";
        if(k < -6)
            return "@or3@";
        if(k < -3)
            return "@or2@";
        if(k < 0)
            return "@or1@";
        if(k > 9)
            return "@gre@";
        if(k > 6)
            return "@gr3@";
        if(k > 3)
            return "@gr2@";
        if(k > 0)
            return "@gr1@";
        else
            return "@yel@";
    }

    public void method111(byte byte0, int i)
    {
        if(byte0 == 2)
            byte0 = 0;
        else
            loadClient();
        Signlink.waveVolume = i;
    }

    public void method112(int i)
    {
        if(i != 8)
            anInt939 = 130;
        method76((byte)-13);
        if(anInt917 == 1)
        {
            cross_sprites[anInt916 / 100].draw(anInt914 - 8 - 4, 16083, anInt915 - 8 - 4);
            anInt1142++;
            if(anInt1142 > 67)
            {
                anInt1142 = 0;
                gameBuffer.putPacket(78);
            }
        }
        if(anInt917 == 2)
            cross_sprites[4 + anInt916 / 100].draw(anInt914 - 8 - 4, 16083, anInt915 - 8 - 4);
        if(anInt1018 != -1)
        {
            method119(anInt945, false, anInt1018);
            drawWidget(Widget.widgets[anInt1018],0, 0, 0);
        }
        if(anInt857 != -1)
        {
            method119(anInt945, false, anInt857);
            drawWidget(Widget.widgets[anInt857],0, 0, 0);
        }
        calculateOntutorialIsland(184);
        if(!aBoolean885)
        {
            method82(0);
            method125(45706);
        } else
        if(clickarea == 0)
            method40((byte)9);
        if(anInt1055 == 1)
            headicons[1].draw(472, 16083, 296);
        if(drawfps)
        {
            char c = '\u01FB';
            int k = 20;
            int i1 = 0xffff00;
            if(super.fps < 15)
                i1 = 0xff0000;
            p12Font.drawHeightAlignedText("Fps:" + super.fps, c, i1, (byte)-80, k);
            k += 15;
            Runtime runtime = Runtime.getRuntime();
            int j1 = (int)((runtime.totalMemory() - runtime.freeMemory()) / 1024L);
            i1 = 0xffff00;
            if(j1 > 0x2000000 && lowMemory)
                i1 = 0xff0000;
            p12Font.drawHeightAlignedText("Mem:" + j1 + "k", c, 0xffff00, (byte)-80, k);
            k += 15;
        }
        if(anInt1104 != 0)
        {
            int j = anInt1104 / 50;
            int l = j / 60;
            j %= 60;
            if(j < 10)
                p12Font.drawText(0xffff00, "System update in: " + l + ":0" + j, 329, 822, 4);
            else
                p12Font.drawText(0xffff00, "System update in: " + l + ":" + j, 329, 822, 4);
            anInt849++;
            if(anInt849 > 75)
            {
                anInt849 = 0;
                gameBuffer.putPacket(148);
            }
        }
    }

    public void method113(long l, int i)
    {
        try
        {
            if(l == 0L)
                return;
            if(amt_ignorehashes >= 100)
            {
                pushMessage("Your ignore list is full. Max of 100 hit", 0, "", aBoolean991);
                return;
            }
            String s = TextTools.formatUsername(-45804, TextTools.longToString(l, (byte)-99));
            for(int j = 0; j < amt_ignorehashes; j++)
                if(ignore_hashes[j] == l)
                {
                    pushMessage(s + " is already on your ignore list", 0, "", aBoolean991);
                    return;
                }

            if(i < 4 || i > 4)
                return;
            for(int k = 0; k < amt_friendhashes; k++)
                if(friend_hashes[k] == l)
                {
                    pushMessage("Please remove " + s + " from your friend list first", 0, "", aBoolean991);
                    return;
                }

            ignore_hashes[amt_ignorehashes++] = l;
            aBoolean1153 = true;
            gameBuffer.putPacket(133);
            gameBuffer.putQword(l);
            return;
        }
        catch(RuntimeException runtimeexception)
        {
            Signlink.reportError("45688, " + l + ", " + i + ", " + runtimeexception.toString());
        }
        throw new RuntimeException();
    }

    public void updatePlayers(byte byte0)
    {
        if(byte0 != aByte973)
            return;
        for(int i = -1; i < playerOffset; i++)
        {
            int j;
            if(i == -1)
                j = localPlayerIndex;
            else
                j = addedPlayers[i];
            Player class30_sub2_sub4_sub1_sub2 = playerArray[j];
            if(class30_sub2_sub4_sub1_sub2 != null)
                updateMobOrentation(46988, 1, class30_sub2_sub4_sub1_sub2);
        }

    }

    public void updateObjects(byte byte0)
    {
        if(byte0 == 8)
            byte0 = 0;
        else
            gameBuffer.put(101);
        if(landscape_stage == 2)
        {
            for(SpawnedObject class30_sub1 = (SpawnedObject)aClass19_1179.getFirst(); class30_sub1 != null; class30_sub1 = (SpawnedObject)aClass19_1179.getNextFront())
            {
                if(class30_sub1.anInt1294 > 0)
                    class30_sub1.anInt1294--;
                if(class30_sub1.anInt1294 == 0)
                {
                    if(class30_sub1.anInt1299 < 0 || LandscapeLoader.method178(class30_sub1.anInt1299, class30_sub1.anInt1301, 8))
                    {
                        method142(class30_sub1.pallete_y, class30_sub1.cheight, class30_sub1.anInt1300, class30_sub1.anInt1301, class30_sub1.pallete_x, class30_sub1.type_num, class30_sub1.anInt1299, 4);
                        class30_sub1.removeDeque();
                    }
                } else
                {
                    if(class30_sub1.anInt1302 > 0)
                        class30_sub1.anInt1302--;
                    if(class30_sub1.anInt1302 == 0 && class30_sub1.pallete_x >= 1 && class30_sub1.pallete_y >= 1 && class30_sub1.pallete_x <= 102 && class30_sub1.pallete_y <= 102 && (class30_sub1.objid < 0 || LandscapeLoader.method178(class30_sub1.objid, class30_sub1.type, 8)))
                    {
                        method142(class30_sub1.pallete_y, class30_sub1.cheight, class30_sub1.rotation, class30_sub1.type, class30_sub1.pallete_x, class30_sub1.type_num, class30_sub1.objid, 4);
                        class30_sub1.anInt1302 = -1;
                        if(class30_sub1.objid == class30_sub1.anInt1299 && class30_sub1.anInt1299 == -1)
                            class30_sub1.removeDeque();
                        else
                        if(class30_sub1.objid == class30_sub1.anInt1299 && class30_sub1.rotation == class30_sub1.anInt1300 && class30_sub1.type == class30_sub1.anInt1301)
                            class30_sub1.removeDeque();
                    }
                }
            }

        }
    }

    public void method116(boolean flag)
    {
        int i = b12Font.widthFontMetrics(anInt1116, "Choose Option");
        isOnlineGame &= flag;
        for(int j = 0; j < anInt1133; j++)
        {
            int k = b12Font.widthFontMetrics(anInt1116, interfacestringstack[j]);
            if(k > i)
                i = k;
        }

        i += 8;
        int l = 15 * anInt1133 + 21;
        if(super.pressedX > 4 && super.pressedY > 4 && super.pressedX < 516 && super.pressedY < 338)
        {
            int i1 = super.pressedX - 4 - i / 2;
            if(i1 + i > 512)
                i1 = 512 - i;
            if(i1 < 0)
                i1 = 0;
            int l1 = super.pressedY - 4;
            if(l1 + l > 334)
                l1 = 334 - l;
            if(l1 < 0)
                l1 = 0;
            aBoolean885 = true;
            clickarea = 0;
            anInt949 = i1;
            anInt950 = l1;
            anInt951 = i;
            anInt952 = 15 * anInt1133 + 22;
        }
        if(super.pressedX > 553 && super.pressedY > 205 && super.pressedX < 743 && super.pressedY < 466)
        {
            int j1 = super.pressedX - 553 - i / 2;
            if(j1 < 0)
                j1 = 0;
            else
            if(j1 + i > 190)
                j1 = 190 - i;
            int i2 = super.pressedY - 205;
            if(i2 < 0)
                i2 = 0;
            else
            if(i2 + l > 261)
                i2 = 261 - l;
            aBoolean885 = true;
            clickarea = 1;
            anInt949 = j1;
            anInt950 = i2;
            anInt951 = i;
            anInt952 = 15 * anInt1133 + 22;
        }
        if(super.pressedX > 17 && super.pressedY > 357 && super.pressedX < 496 && super.pressedY < 453)
        {
            int k1 = super.pressedX - 17 - i / 2;
            if(k1 < 0)
                k1 = 0;
            else
            if(k1 + i > 479)
                k1 = 479 - i;
            int j2 = super.pressedY - 357;
            if(j2 < 0)
                j2 = 0;
            else
            if(j2 + l > 96)
                j2 = 96 - l;
            aBoolean885 = true;
            clickarea = 2;
            anInt949 = k1;
            anInt950 = j2;
            anInt951 = i;
            anInt952 = 15 * anInt1133 + 22;
        }
    }

    public void parseLocalPlayerMovement(ByteBuffer buffer, int i, byte byte0)
    {
        buffer.initBitAccess();
        int doUpdate = buffer.getBits(1);
        if(doUpdate == 0)
            return;
        int updateType = buffer.getBits(2);
        System.out.println("Local update type: " + updateType);
        if(updateType == 0)
        {
            pFlagUpdateList[amtplayerupdatestack++] = localPlayerIndex;
            return;
        }
        if(updateType == 1)
        {
            int l = buffer.getBits(3);
            localPlayer.handleMobMovment(false, l);
            int doMaskUpdate = buffer.getBits(1);
            if(doMaskUpdate == 1)
                pFlagUpdateList[amtplayerupdatestack++] = localPlayerIndex;
            return;
        }
        if(updateType == 2)
        {
            int runDelta0 = buffer.getBits(3);
            localPlayer.handleMobMovment(true, runDelta0);
            int runDelta1 = buffer.getBits(3);
            localPlayer.handleMobMovment(true, runDelta1);
            int doMaskUpdate = buffer.getBits(1);
            if(doMaskUpdate == 1)
                pFlagUpdateList[amtplayerupdatestack++] = localPlayerIndex;
            return;
        }
        if(updateType == 3)
        {
            currentZ = buffer.getBits(2);
            int teleported = buffer.getBits(1);
            int doMaskUpdate = buffer.getBits(1);
            if(doMaskUpdate == 1)
                pFlagUpdateList[amtplayerupdatestack++] = localPlayerIndex;
            int yOff = buffer.getBits(7); 
            int xOff = buffer.getBits(7);
            localPlayer.updateMobPosition(xOff, yOff, teleported == 1);
        }
    }

    public void method118(int i)
    {
        runflamecycle = false;
        while(aBoolean962) 
        {
            runflamecycle = false;
            try
            {
                Thread.sleep(50L);
            }
            catch(Exception _ex) { }
        }
        aClass30_Sub2_Sub1_Sub2_966 = null;
        aClass30_Sub2_Sub1_Sub2_967 = null;
        titlescreen_sprites = null;
        anIntArray850 = null;
        anIntArray851 = null;
        anIntArray852 = null;
        anIntArray853 = null;
        anIntArray1190 = null;
        anIntArray1191 = null;
        anIntArray828 = null;
        anIntArray829 = null;
        aClass30_Sub2_Sub1_Sub1_1201 = null;
        aClass30_Sub2_Sub1_Sub1_1202 = null;
        if(i < 3 || i > 3)
            grounditems = null;
    }

    public boolean method119(int i, boolean flag, int j)
    {
        boolean flag1 = false;
        if(flag)
            throw new NullPointerException();
        Widget class9 = Widget.widgets[j];
        for(int k = 0; k < class9.childrenIds.length; k++)
        {
            if(class9.childrenIds[k] == -1)
                break;
            Widget class9_1 = Widget.widgets[class9.childrenIds[k]];
            if(class9_1.type == 1)
                flag1 |= method119(i, false, class9_1.widgetId);
            if(class9_1.type == 6 && (class9_1.inactiveAnimId != -1 || class9_1.activeAnimId != -1))
            {
                boolean flag2 = executeWidgetScripts(class9_1);
                int l;
                if(flag2)
                    l = class9_1.activeAnimId;
                else
                    l = class9_1.inactiveAnimId;
                if(l != -1)
                {
                    AnimSequence class20 = AnimSequence.animationsequences[l];
                    for(class9_1.anInt208 += i; class9_1.anInt208 > class20.method258(class9_1.anInt246, (byte)-39);)
                    {
                        class9_1.anInt208 -= class20.method258(class9_1.anInt246, (byte)-39) + 1;
                        class9_1.anInt246++;
                        if(class9_1.anInt246 >= class20.anInt352)
                        {
                            class9_1.anInt246 -= class20.anInt356;
                            if(class9_1.anInt246 < 0 || class9_1.anInt246 >= class20.anInt352)
                                class9_1.anInt246 = 0;
                        }
                        flag1 = true;
                    }

                }
            }
        }

        return flag1;
    }

    public int calculateCameraHeight1()
    {
        int j = 3;
        if(camerapitch$ < 310)
        {
            int camx = camerax >> 7;
            int camy = cameray >> 7;
            int plrx = ((Mob) (localPlayer)).fineX >> 7;
            int plry = ((Mob) (localPlayer)).fineY >> 7;
            if((tileFlags[currentZ][camx][camy] & 4) != 0)
                j = currentZ;
            int dx;
            if(plrx > camx)
                dx = plrx - camx;
            else
                dx = camx - plrx;
            int dy;
            if(plry > camy)
                dy = plry - camy;
            else
                dy = camy - plry;
            if(dx > dy)
            {
                int i2 = (dy * 0x10000) / dx;
                int k2 = 32768;
                while(camx != plrx)
                {
                    if(camx < plrx)
                        camx++;
                    else
                    if(camx > plrx)
                        camx--;
                    if((tileFlags[currentZ][camx][camy] & 4) != 0)
                        j = currentZ;
                    k2 += i2;
                    if(k2 >= 0x10000)
                    {
                        k2 -= 0x10000;
                        if(camy < plry)
                            camy++;
                        else
                        if(camy > plry)
                            camy--;
                        if((tileFlags[currentZ][camx][camy] & 4) != 0)
                            j = currentZ;
                    }
                }
            } else
            {
                int j2 = (dx * 0x10000) / dy;
                int l2 = 32768;
                while(camy != plry)
                {
                    if(camy < plry)
                        camy++;
                    else
                    if(camy > plry)
                        camy--;
                    if((tileFlags[currentZ][camx][camy] & 4) != 0)
                        j = currentZ;
                    l2 += j2;
                    if(l2 >= 0x10000)
                    {
                        l2 -= 0x10000;
                        if(camx < plrx)
                            camx++;
                        else
                        if(camx > plrx)
                            camx--;
                        if((tileFlags[currentZ][camx][camy] & 4) != 0)
                            j = currentZ;
                    }
                }
            }
        }
        if((tileFlags[currentZ][((Mob) (localPlayer)).fineX >> 7][((Mob) (localPlayer)).fineY >> 7] & 4) != 0)
            j = currentZ;
        return j;
    }

    public int calculateCameraHeight2()
    {
        int j = calculateTileHeight(camerax, cameray, currentZ);
        if(j - cameraz < 800 && (tileFlags[currentZ][camerax >> 7][cameray >> 7] & 4) != 0)
            return currentZ;
        else
            return 3;
    }

    public void method122(int i, long l)
    {
        try
        {
            if(i != 3)
                loadClient();
            if(l == 0L)
                return;
            for(int j = 0; j < amt_ignorehashes; j++)
                if(ignore_hashes[j] == l)
                {
                    amt_ignorehashes--;
                    aBoolean1153 = true;
                    for(int k = j; k < amt_ignorehashes; k++)
                        ignore_hashes[k] = ignore_hashes[k + 1];

                    gameBuffer.putPacket(74);
                    gameBuffer.putQword(l);
                    return;
                }

            return;
        }
        catch(RuntimeException runtimeexception)
        {
            Signlink.reportError("47229, " + i + ", " + l + ", " + runtimeexception.toString());
        }
        throw new RuntimeException();
    }

    public String getParameter(String s)
    {
        if(Signlink.applet != null)
            return Signlink.applet.getParameter(s);
        else
            return super.getParameter(s);
    }

    public void volumeAdjustMidi(byte byte0, boolean flag, int i)
    {
        if(byte0 == 0)
            byte0 = 0;
        else
            grounditems = null;
        Signlink.midiVolume = i;
        if(flag)
            Signlink.midiFileName = "voladjust";
    }

    public int handleWidgetScript(Widget widget, int script)
    {
        if(widget.scriptOpcodes == null || script >= widget.scriptOpcodes.length)
            return -2;
        try
        {
            int ops[] = widget.scriptOpcodes[script];
            int k = 0;
            int opOffset = 0;
            int i1 = 0;
            do
            {
                int opcode = ops[opOffset++];
                int value = 0;
                byte byte0 = 0;
                if(opcode == 0)
                    return k;
                if(opcode == 1)
                    value = skillLevels[ops[opOffset++]];
                if(opcode == 2)
                    value = anIntArray1044[ops[opOffset++]];
                if(opcode == 3)
                    value = skillExperience[ops[opOffset++]];
                if(opcode == 4)
                {
                    Widget widget_ = Widget.widgets[ops[opOffset++]];
                    int itemid = ops[opOffset++];
                    if(itemid >= 0 && itemid < ItemDefinition.maximumId && (!ItemDefinition.getItemDefinition(itemid).aBoolean161 || members))
                    {
                        for(int j3 = 0; j3 < widget_.itemIds.length; j3++)
                            if(widget_.itemIds[j3] == itemid + 1)
                                value += widget_.itemAmounts[j3];
                    }
                }
                if(opcode == 5)
                    value = configstates[ops[opOffset++]];
                if(opcode == 6)
                    value = XP_TABLE[anIntArray1044[ops[opOffset++]] - 1];
                if(opcode == 7)
                    value = (configstates[ops[opOffset++]] * 100) / 46875;
                if(opcode == 8)
                    value = localPlayer.combatLevel;
                if(opcode == 9)
                {
                    for(int l1 = 0; l1 < SkillConstants.amt_skills; l1++)
                        if(SkillConstants.skill_active[l1])
                            value += anIntArray1044[l1];
                }
                if(opcode == 10)
                {
                    Widget class9_2 = Widget.widgets[ops[opOffset++]];
                    int l2 = ops[opOffset++] + 1;
                    if(l2 >= 0 && l2 < ItemDefinition.maximumId && (!ItemDefinition.getItemDefinition(l2).aBoolean161 || members))
                    {
                        for(int k3 = 0; k3 < class9_2.itemIds.length; k3++)
                        {
                            if(class9_2.itemIds[k3] != l2)
                                continue;
                            value = 0x3b9ac9ff;
                            break;
                        }

                    }
                }
                if(opcode == 11)
                    value = anInt1148;
                if(opcode == 12)
                    value = anInt878;
                if(opcode == 13)
                {
                    int i2 = configstates[ops[opOffset++]];
                    int i3 = ops[opOffset++];
                    value = (i2 & 1 << i3) == 0 ? 0 : 1;
                }
                if(opcode == 14)
                {
                    int j2 = ops[opOffset++];
                    VarbitFile class37 = VarbitFile.varbitArray[j2];
                    int l3 = class37.config_num;
                    int i4 = class37.anInt649;
                    int j4 = class37.anInt650;
                    int k4 = BIT_MASKS[j4 - i4];
                    value = configstates[l3] >> i4 & k4;
                }
                if(opcode == 15)
                    byte0 = 1;
                if(opcode == 16)
                    byte0 = 2;
                if(opcode == 17)
                    byte0 = 3;
                if(opcode == 18)
                    value = (((Mob) (localPlayer)).fineX >> 7) + paletteX;
                if(opcode == 19)
                    value = (((Mob) (localPlayer)).fineY >> 7) + paletteY;
                if(opcode == 20)
                    value = ops[opOffset++];
                if(byte0 == 0)
                {
                    if(i1 == 0)
                        k += value;
                    if(i1 == 1)
                        k -= value;
                    if(i1 == 2 && value != 0)
                        k /= value;
                    if(i1 == 3)
                        k *= value;
                    i1 = 0;
                } else
                {
                    i1 = byte0;
                }
            } while(true);
        }
        catch(Exception _ex)
        {
            return -1;
        }
    }

    public void method125(int i)
    {
        if(anInt1133 < 2 && anInt1282 == 0 && anInt1136 == 0)
            return;
        String s;
        if(anInt1282 == 1 && anInt1133 < 2)
            s = "Use " + usedItemName + " with...";
        else
        if(anInt1136 == 1 && anInt1133 < 2)
            s = aString1139 + "...";
        else
            s = interfacestringstack[anInt1133 - 1];
        if(anInt1133 > 2)
            s = s + "@whi@ / " + (anInt1133 - 2) + " more options";
        b12Font.method390(true, 4, 0xffffff, s, loopCycle / 1000, 973, 15);
        if(i != 45706)
        {
            for(int j = 1; j > 0; j++);
        }
    }

    public void drawMinimap(boolean flag)
    {
        if(flag)
            return;
        aClass15_1164.initialize(0);
        if(anInt1021 == 2)
        {
            byte abyte0[] = mapback.buffer;
            int ai[] = BasicRasterizer.pixelBuffer;
            int k2 = abyte0.length;
            for(int i5 = 0; i5 < k2; i5++)
                if(abyte0[i5] == 0)
                    ai[i5] = 0;

            compass.draw(33, cameraYaw, anIntArray1057, 256, anIntArray968, -236, 25, 0, 0, 33, 25);
            toplefttext_imagefetcher.initialize(0);
            return;
        }
        int i = cameraYaw + anInt1209 & 0x7ff;
        int j = 48 + ((Mob) (localPlayer)).fineX / 32;
        int l2 = 464 - ((Mob) (localPlayer)).fineY / 32;
        aClass30_Sub2_Sub1_Sub1_1263.draw(151, i, anIntArray1229, 256 + anInt1170, anIntArray1052, -236, l2, 5, 25, 146, j);
        compass.draw(33, cameraYaw, anIntArray1057, 256, anIntArray968, -236, 25, 0, 0, 33, 25);
        for(int j5 = 0; j5 < mapfunctionstackpos; j5++)
        {
            int k = (anIntArray1072[j5] * 4 + 2) - ((Mob) (localPlayer)).fineX / 32;
            int i3 = (anIntArray1073[j5] * 4 + 2) - ((Mob) (localPlayer)).fineY / 32;
            drawOnMinimap(mapfunctionstack[j5], k, i3, false);
        }

        for(int k5 = 0; k5 < 104; k5++)
        {
            for(int l5 = 0; l5 < 104; l5++)
            {
                Deque class19 = grounditems[currentZ][k5][l5];
                if(class19 != null)
                {
                    int l = (k5 * 4 + 2) - ((Mob) (localPlayer)).fineX / 32;
                    int j3 = (l5 * 4 + 2) - ((Mob) (localPlayer)).fineY / 32;
                    drawOnMinimap(grounditem_mapdotsprite, l, j3, false);
                }
            }

        }

        for(int i6 = 0; i6 < anInt836; i6++)
        {
            Npc class30_sub2_sub4_sub1_sub1 = npcs[localNpcIds[i6]];
            if(class30_sub2_sub4_sub1_sub1 != null && class30_sub2_sub4_sub1_sub1.hasDefinition())
            {
                NpcDefinition class5 = class30_sub2_sub4_sub1_sub1.definition;
                if(class5.confignpcs != null)
                    class5 = class5.method161(anInt877);
                if(class5 != null && class5.displaymapdot && class5.isvisible)
                {
                    int i1 = ((Mob) (class30_sub2_sub4_sub1_sub1)).fineX / 32 - ((Mob) (localPlayer)).fineX / 32;
                    int k3 = ((Mob) (class30_sub2_sub4_sub1_sub1)).fineY / 32 - ((Mob) (localPlayer)).fineY / 32;
                    drawOnMinimap(mapdots1, i1, k3, false);
                }
            }
        }

        for(int j6 = 0; j6 < playerOffset; j6++)
        {
            Player class30_sub2_sub4_sub1_sub2 = playerArray[addedPlayers[j6]];
            if(class30_sub2_sub4_sub1_sub2 != null && class30_sub2_sub4_sub1_sub2.hasDefinition())
            {
                int j1 = ((Mob) (class30_sub2_sub4_sub1_sub2)).fineX / 32 - ((Mob) (localPlayer)).fineX / 32;
                int l3 = ((Mob) (class30_sub2_sub4_sub1_sub2)).fineY / 32 - ((Mob) (localPlayer)).fineY / 32;
                boolean flag1 = false;
                long l6 = TextTools.stringToLong(class30_sub2_sub4_sub1_sub2.name);
                for(int k6 = 0; k6 < amt_friendhashes; k6++)
                {
                    if(l6 != friend_hashes[k6] || anIntArray826[k6] == 0)
                        continue;
                    flag1 = true;
                    break;
                }

                boolean flag2 = false;
                if(localPlayer.itemModel != 0 && class30_sub2_sub4_sub1_sub2.itemModel != 0 && localPlayer.itemModel == class30_sub2_sub4_sub1_sub2.itemModel)
                    flag2 = true;
                if(flag1)
                    drawOnMinimap(mapdots3, j1, l3, false);
                else
                if(flag2)
                    drawOnMinimap(mapdots4, j1, l3, false);
                else
                    drawOnMinimap(mapdots2, j1, l3, false);
            }
        }

        if(markertype != 0 && loopCycle % 20 < 10)
        {
            if(markertype == 1 && nmarker_id >= 0 && nmarker_id < npcs.length)
            {
                Npc class30_sub2_sub4_sub1_sub1_1 = npcs[nmarker_id];
                if(class30_sub2_sub4_sub1_sub1_1 != null)
                {
                    int k1 = ((Mob) (class30_sub2_sub4_sub1_sub1_1)).fineX / 32 - ((Mob) (localPlayer)).fineX / 32;
                    int i4 = ((Mob) (class30_sub2_sub4_sub1_sub1_1)).fineY / 32 - ((Mob) (localPlayer)).fineY / 32;
                    method81(mapmarker1, -760, i4, k1);
                }
            }
            if(markertype == 2)
            {
                int l1 = ((markerloc_x - paletteX) * 4 + 2) - ((Mob) (localPlayer)).fineX / 32;
                int j4 = ((markerloc_y - paletteY) * 4 + 2) - ((Mob) (localPlayer)).fineY / 32;
                method81(mapmarker1, -760, j4, l1);
            }
            if(markertype == 10 && pmarker_id >= 0 && pmarker_id < playerArray.length)
            {
                Player class30_sub2_sub4_sub1_sub2_1 = playerArray[pmarker_id];
                if(class30_sub2_sub4_sub1_sub2_1 != null)
                {
                    int i2 = ((Mob) (class30_sub2_sub4_sub1_sub2_1)).fineX / 32 - ((Mob) (localPlayer)).fineX / 32;
                    int k4 = ((Mob) (class30_sub2_sub4_sub1_sub2_1)).fineY / 32 - ((Mob) (localPlayer)).fineY / 32;
                    method81(mapmarker1, -760, k4, i2);
                }
            }
        }
        if(anInt1261 != 0)
        {
            int j2 = (anInt1261 * 4 + 2) - ((Mob) (localPlayer)).fineX / 32;
            int l4 = (anInt1262 * 4 + 2) - ((Mob) (localPlayer)).fineY / 32;
            drawOnMinimap(mapmarker0, j2, l4, false);
        }
        BasicRasterizer.drawQuad(97, 78, 3, 3, 0xffffff);
        toplefttext_imagefetcher.initialize(0);
    }

    public void calculateSpriteMobXY(boolean junk, Mob mob, int height)
    {
        calculateSpriteXY(mob.fineX, height, anInt875, mob.fineY);
    }

    public void calculateSpriteXY(int x, int h, int junk, int y)
    {
        if(x < 128 || y < 128 || x > 13056 || y > 13056)
        {
            spriteX = -1;
            spriteY = -1;
            return;
        }
        int z = calculateTileHeight(x, y, currentZ) - h;
        x -= camerax;
        z -= cameraz;
        y -= cameray;
        int pitchsine = Model.sinetable[camerapitch$];
        int pitchcosine = Model.cosinetable[camerapitch$];
        int yawsine = Model.sinetable[camerayaw$];
        int yawcosine = Model.cosinetable[camerayaw$];
        /* Z Axis rotation */
        int j2 = (y * yawsine + x * yawcosine) >> 16;
        y = (y * yawcosine - x * yawsine) >> 16;
        x = j2;
        /* X Axis rotation */
        j2 = (z * pitchcosine - y * pitchsine) >> 16;
        y = (z * pitchsine + y * pitchcosine) >> 16;
        z = j2;
        if(y >= 50)
        {
            spriteX = TriangleRasterizer.centerWidth + (x << 9) / y;
            spriteY = TriangleRasterizer.centerHeight + (z << 9) / y;
            return;
        } else
        {
            spriteX = -1;
            spriteY = -1;
            return;
        }
    }

    public void method129(boolean flag)
    {
        if(anInt1195 == 0)
            return;
        int i = 0;
        if(flag)
            packetId = -1;
        if(anInt1104 != 0)
            i = 1;
        for(int j = 0; j < 100; j++)
            if(msgbody_stack[j] != null)
            {
                int k = msgtype_stack[j];
                String s = msgprefix_stack[j];
                boolean flag1 = false;
                if(s != null && s.startsWith("@cr1@"))
                {
                    s = s.substring(5);
                    boolean flag2 = true;
                }
                if(s != null && s.startsWith("@cr2@"))
                {
                    s = s.substring(5);
                    byte byte0 = 2;
                }
                if((k == 3 || k == 7) && (k == 7 || anInt845 == 0 || anInt845 == 1 && onFriendsList(false, s)))
                {
                    int l = 329 - i * 13;
                    if(super.newMouseX > 4 && super.newMouseY - 4 > l - 10 && super.newMouseY - 4 <= l + 3)
                    {
                        int i1 = p12Font.widthFontMetrics(anInt1116, "From:  " + s + msgbody_stack[j]) + 25;
                        if(i1 > 450)
                            i1 = 450;
                        if(super.newMouseX < 4 + i1)
                        {
                            if(rights >= 1)
                            {
                                interfacestringstack[anInt1133] = "Report abuse @whi@" + s;
                                interfaceopcodestack[anInt1133] = 2606;
                                anInt1133++;
                            }
                            interfacestringstack[anInt1133] = "Add ignore @whi@" + s;
                            interfaceopcodestack[anInt1133] = 2042;
                            anInt1133++;
                            interfacestringstack[anInt1133] = "Add friend @whi@" + s;
                            interfaceopcodestack[anInt1133] = 2337;
                            anInt1133++;
                        }
                    }
                    if(++i >= 5)
                        return;
                }
                if((k == 5 || k == 6) && anInt845 < 2 && ++i >= 5)
                    return;
            }

    }

    public void addSpawnObject(int junk, int j, int k, int r, int tn, int py, int t, int h, int px, int j2) {
        SpawnedObject class30_sub1 = null;
        for(SpawnedObject class30_sub1_1 = (SpawnedObject) aClass19_1179.getFirst(); class30_sub1_1 != null; class30_sub1_1 = (SpawnedObject)aClass19_1179.getNextFront())
        {
            if(class30_sub1_1.cheight != h || class30_sub1_1.pallete_x != px || class30_sub1_1.pallete_y != py || class30_sub1_1.type_num != tn)
                continue;
            class30_sub1 = class30_sub1_1;
            break;
        }
        if(class30_sub1 == null) {
            class30_sub1 = new SpawnedObject();
            class30_sub1.cheight = h;
            class30_sub1.type_num = tn;
            class30_sub1.pallete_x = px;
            class30_sub1.pallete_y = py;
            method89(false, class30_sub1);
            aClass19_1179.addLast(class30_sub1);
        }
        class30_sub1.objid = k;
        class30_sub1.type = t;
        class30_sub1.rotation = r;
        class30_sub1.anInt1302 = j2;
        class30_sub1.anInt1294 = j;
    }

    public boolean executeWidgetScripts(Widget widget)
    {
        if(widget.scriptInstructions == null)
            return false;
        for(int i = 0; i < widget.scriptInstructions.length; i++)
        {
            int rValue = handleWidgetScript(widget, i);
            int op = widget.scriptConditions[i];
            if(widget.scriptInstructions[i] == 2)
            {
                if(rValue >= op)
                    return false;
            } else
            if(widget.scriptInstructions[i] == 3)
            {
                if(rValue <= op)
                    return false;
            } else
            if(widget.scriptInstructions[i] == 4)
            {
                if(rValue == op)
                    return false;
            } else if(rValue != op)
                return false;
        }
        return true;
    }

    public DataInputStream writeJaggrabRequest(String requestAddress) throws IOException {
        if(!useWebJaggrab)
            if(Signlink.applet != null)
                return Signlink.createInputStream(requestAddress);
            else
                return new DataInputStream((new URL(getCodeBase(), requestAddress)).openStream());
        if(jaggrabSocket != null) {
            try {
                jaggrabSocket.close();
            } catch(Exception ex) { 
            }
            jaggrabSocket = null;
        }
        jaggrabSocket = createSocket(43594);
        jaggrabSocket.setSoTimeout(10000);
        java.io.InputStream inputstream = jaggrabSocket.getInputStream();
        OutputStream outputstream = jaggrabSocket.getOutputStream();
        outputstream.write(("JAGGRAB /" + requestAddress + "\n\n").getBytes());
        return new DataInputStream(inputstream);
    }

    public void method133(byte byte0)
    {
        char c = '\u0100';
        if(anInt1040 > 0)
        {
            for(int i = 0; i < 256; i++)
                if(anInt1040 > 768)
                    anIntArray850[i] = method83(true, anIntArray851[i], anIntArray852[i], 1024 - anInt1040);
                else
                if(anInt1040 > 256)
                    anIntArray850[i] = anIntArray852[i];
                else
                    anIntArray850[i] = method83(true, anIntArray852[i], anIntArray851[i], 256 - anInt1040);

        } else
        if(anInt1041 > 0)
        {
            for(int j = 0; j < 256; j++)
                if(anInt1041 > 768)
                    anIntArray850[j] = method83(true, anIntArray851[j], anIntArray853[j], 1024 - anInt1041);
                else
                if(anInt1041 > 256)
                    anIntArray850[j] = anIntArray853[j];
                else
                    anIntArray850[j] = method83(true, anIntArray853[j], anIntArray851[j], 256 - anInt1041);

        } else
        {
            for(int k = 0; k < 256; k++)
                anIntArray850[k] = anIntArray851[k];

        }
        for(int l = 0; l < 33920; l++)
            titletopleft_imagefetcher.pixelBuffer[l] = aClass30_Sub2_Sub1_Sub1_1201.buffer[l];

        int i1 = 0;
        int j1 = 1152;
        for(int k1 = 1; k1 < c - 1; k1++)
        {
            int l1 = (anIntArray969[k1] * (c - k1)) / c;
            int j2 = 22 + l1;
            if(j2 < 0)
                j2 = 0;
            i1 += j2;
            for(int l2 = j2; l2 < 128; l2++)
            {
                int j3 = anIntArray828[i1++];
                if(j3 != 0)
                {
                    int l3 = j3;
                    int j4 = 256 - j3;
                    j3 = anIntArray850[j3];
                    int l4 = titletopleft_imagefetcher.pixelBuffer[j1];
                    titletopleft_imagefetcher.pixelBuffer[j1++] = ((j3 & 0xff00ff) * l3 + (l4 & 0xff00ff) * j4 & 0xff00ff00) + ((j3 & 0xff00) * l3 + (l4 & 0xff00) * j4 & 0xff0000) >> 8;
                } else
                {
                    j1++;
                }
            }

            j1 += j2;
        }

        titletopleft_imagefetcher.updateGraphics(0, 23680, super.appletGraphics, 0);
        for(int i2 = 0; i2 < 33920; i2++)
            titletopright_imagefetcher.pixelBuffer[i2] = aClass30_Sub2_Sub1_Sub1_1202.buffer[i2];

        i1 = 0;
        j1 = 1176;
        for(int k2 = 1; k2 < c - 1; k2++)
        {
            int i3 = (anIntArray969[k2] * (c - k2)) / c;
            int k3 = 103 - i3;
            j1 += i3;
            for(int i4 = 0; i4 < k3; i4++)
            {
                int k4 = anIntArray828[i1++];
                if(k4 != 0)
                {
                    int i5 = k4;
                    int j5 = 256 - k4;
                    k4 = anIntArray850[k4];
                    int k5 = titletopright_imagefetcher.pixelBuffer[j1];
                    titletopright_imagefetcher.pixelBuffer[j1++] = ((k4 & 0xff00ff) * i5 + (k5 & 0xff00ff) * j5 & 0xff00ff00) + ((k4 & 0xff00) * i5 + (k5 & 0xff00) * j5 & 0xff0000) >> 8;
                } else
                {
                    j1++;
                }
            }

            i1 += 128 - k3;
            j1 += 128 - k3 - i3;
        }

        titletopright_imagefetcher.updateGraphics(0, 23680, super.appletGraphics, 637);
        if(byte0 != 9)
            packetId = inbuffer.getUbyte();
    }

    public void parsePlayerUpdates(byte byte0, int i, ByteBuffer buffer0)
    {
        int amountPlayers = buffer0.getBits(8);
        System.out.println("Amount players: "+ amountPlayers);
        if(amountPlayers < playerOffset)
        {
            for(int k = amountPlayers; k < playerOffset; k++)
                eRmQueue[eRmQueuePosition++] = addedPlayers[k];

        }
        if(amountPlayers > playerOffset)
        {
            Signlink.reportError(username + " Too many players");
            throw new RuntimeException("eek");
        }
        playerOffset = 0;
        for(int l = 0; l < amountPlayers; l++)
        {
            int i1 = addedPlayers[l];
            Player class30_sub2_sub4_sub1_sub2 = playerArray[i1];
            int doUpdate = buffer0.getBits(1);
            System.out.println("APID: " + i1 + ", Do Update?: " + doUpdate);
            if(doUpdate == 0)
            {
                addedPlayers[playerOffset++] = i1;
                class30_sub2_sub4_sub1_sub2.anInt1537 = loopCycle;
            } else
            {
                int updateType = buffer0.getBits(2);
                System.out.println("Local update type: " + updateType);
                if(updateType == 0)
                {
                    addedPlayers[playerOffset++] = i1;
                    class30_sub2_sub4_sub1_sub2.anInt1537 = loopCycle;
                    pFlagUpdateList[amtplayerupdatestack++] = i1;
                } else
                if(updateType == 1)
                {
                    addedPlayers[playerOffset++] = i1;
                    class30_sub2_sub4_sub1_sub2.anInt1537 = loopCycle;
                    int l1 = buffer0.getBits(3);
                    class30_sub2_sub4_sub1_sub2.handleMobMovment(false, l1);
                    int j2 = buffer0.getBits(1);
                    if(j2 == 1)
                        pFlagUpdateList[amtplayerupdatestack++] = i1;
                } else
                if(updateType == 2)
                {
                    addedPlayers[playerOffset++] = i1;
                    class30_sub2_sub4_sub1_sub2.anInt1537 = loopCycle;
                    int i2 = buffer0.getBits(3);
                    class30_sub2_sub4_sub1_sub2.handleMobMovment(true, i2);
                    int k2 = buffer0.getBits(3);
                    class30_sub2_sub4_sub1_sub2.handleMobMovment(true, k2);
                    int l2 = buffer0.getBits(1);
                    if(l2 == 1)
                        pFlagUpdateList[amtplayerupdatestack++] = i1;
                } else
                if(updateType == 3)
                    eRmQueue[eRmQueuePosition++] = i1;
            }
        }
    }

    public void drawTitleScreen(boolean flag, boolean flag1)
    {
        method64(0);
        aClass15_1109.initialize(0);
        aClass30_Sub2_Sub1_Sub2_966.renderImage(0, 16083, 0);
        char c = '\u0168';
        char c1 = '\310';
        if(flag1)
            return;
        if(titlescreen_tab == 0)
        {
            int i = c1 / 2 + 80;
            p11Font.drawCenteredXText(0x75a9a9, c / 2, anInt939, ondemandhandler.extraFilesMsgStr, i, true);
            i = c1 / 2 - 20;
            b12Font.drawCenteredXText(0xffff00, c / 2, anInt939, "Welcome to RuneScape", i, true);
            i += 30;
            int l = c / 2 - 80;
            int k1 = c1 / 2 + 20;
            aClass30_Sub2_Sub1_Sub2_967.renderImage(l - 73, 16083, k1 - 20);
            b12Font.drawCenteredXText(0xffffff, l, anInt939, "New User", k1 + 5, true);
            l = c / 2 + 80;
            aClass30_Sub2_Sub1_Sub2_967.renderImage(l - 73, 16083, k1 - 20);
            b12Font.drawCenteredXText(0xffffff, l, anInt939, "Existing User", k1 + 5, true);
        }
        if(titlescreen_tab == 2)
        {
            int j = c1 / 2 - 40;
            if(loginMessage0.length() > 0)
            {
                b12Font.drawCenteredXText(0xffff00, c / 2, anInt939, loginMessage0, j - 15, true);
                b12Font.drawCenteredXText(0xffff00, c / 2, anInt939, loginMessage1, j, true);
                j += 30;
            } else
            {
                b12Font.drawCenteredXText(0xffff00, c / 2, anInt939, loginMessage1, j - 7, true);
                j += 30;
            }
            b12Font.drawText2(false, true, c / 2 - 90, 0xffffff, "Username: " + username + ((userpass_swtch == 0) & (loopCycle % 40 < 20) ? "@yel@|" : ""), j);
            j += 15;
            b12Font.drawText2(false, true, c / 2 - 88, 0xffffff, "Password: " + TextTools.asteriskicide(password, 0) + ((userpass_swtch == 1) & (loopCycle % 40 < 20) ? "@yel@|" : ""), j);
            j += 15;
            if(!flag)
            {
                int i1 = c / 2 - 80;
                int l1 = c1 / 2 + 50;
                aClass30_Sub2_Sub1_Sub2_967.renderImage(i1 - 73, 16083, l1 - 20);
                b12Font.drawCenteredXText(0xffffff, i1, anInt939, "Login", l1 + 5, true);
                i1 = c / 2 + 80;
                aClass30_Sub2_Sub1_Sub2_967.renderImage(i1 - 73, 16083, l1 - 20);
                b12Font.drawCenteredXText(0xffffff, i1, anInt939, "Cancel", l1 + 5, true);
            }
        }
        if(titlescreen_tab == 3)
        {
            b12Font.drawCenteredXText(0xffff00, c / 2, anInt939, "Create a free account", c1 / 2 - 60, true);
            int k = c1 / 2 - 35;
            b12Font.drawCenteredXText(0xffffff, c / 2, anInt939, "To create a new account you need to", k, true);
            k += 15;
            b12Font.drawCenteredXText(0xffffff, c / 2, anInt939, "go back to the main RuneScape webpage", k, true);
            k += 15;
            b12Font.drawCenteredXText(0xffffff, c / 2, anInt939, "and choose the red 'create account'", k, true);
            k += 15;
            b12Font.drawCenteredXText(0xffffff, c / 2, anInt939, "button at the top right of that page.", k, true);
            k += 15;
            int j1 = c / 2;
            int i2 = c1 / 2 + 50;
            aClass30_Sub2_Sub1_Sub2_967.renderImage(j1 - 73, 16083, i2 - 20);
            b12Font.drawCenteredXText(0xffffff, j1, anInt939, "Cancel", i2 + 5, true);
        }
        aClass15_1109.updateGraphics(171, 23680, super.appletGraphics, 202);
        if(paintRequested)
        {
            paintRequested = false;
            logo_imagefetcher.updateGraphics(0, 23680, super.appletGraphics, 128);
            bottomleftmid_imagefetcher.updateGraphics(371, 23680, super.appletGraphics, 202);
            aClass15_1112.updateGraphics(265, 23680, super.appletGraphics, 0);
            aClass15_1113.updateGraphics(265, 23680, super.appletGraphics, 562);
            aClass15_1114.updateGraphics(171, 23680, super.appletGraphics, 128);
            aClass15_1115.updateGraphics(171, 23680, super.appletGraphics, 562);
        }
    }

    public void clientProcess(byte byte0)
    {
        aBoolean962 = true;
        if(byte0 != 59)
            anInt1058 = -186;
        try
        {
            long l = System.currentTimeMillis();
            int i = 0;
            int j = 20;
            while(runflamecycle) 
            {
                flameCycle++;
                method58(25106);
                method58(25106);
                method133((byte)9);
                if(++i > 10)
                {
                    long l1 = System.currentTimeMillis();
                    int k = (int)(l1 - l) / 10 - j;
                    j = 40 - k;
                    if(j < 5)
                        j = 5;
                    i = 0;
                    l = l1;
                }
                try
                {
                    Thread.sleep(j);
                }
                catch(Exception _ex) { }
            }
        }
        catch(Exception _ex) { }
        aBoolean962 = false;
    }

    public void updateGraphics() {
        paintRequested = true;
    }

    public void parseExtraFiles(int i, ByteBuffer buffer0, int j)
    {
        while(i >= 0) 
            j = -1;
        if(j == 84)
        {
            int k = buffer0.getUbyte();
            int j3 = anInt1268 + (k >> 4 & 7);
            int i6 = anInt1269 + (k & 7);
            int l8 = buffer0.getUword();
            int k11 = buffer0.getUword();
            int l13 = buffer0.getUword();
            if(j3 >= 0 && i6 >= 0 && j3 < 104 && i6 < 104)
            {
                Deque class19_1 = grounditems[currentZ][j3][i6];
                if(class19_1 != null)
                {
                    for(GroundItem class30_sub2_sub4_sub2_3 = (GroundItem)class19_1.getFirst(); class30_sub2_sub4_sub2_3 != null; class30_sub2_sub4_sub2_3 = (GroundItem)class19_1.getNextFront())
                    {
                        if(class30_sub2_sub4_sub2_3.itemid != (l8 & 0x7fff) || class30_sub2_sub4_sub2_3.amount != k11)
                            continue;
                        class30_sub2_sub4_sub2_3.amount = l13;
                        break;
                    }
                    method25(j3, i6);
                }
            }
            return;
        }
		/* Something Sound */
        if(j == 105)
        {
            int l = buffer0.getUbyte();
            int k3 = anInt1268 + (l >> 4 & 7);
            int j6 = anInt1269 + (l & 7);
            int i9 = buffer0.getUword();
            int l11 = buffer0.getUbyte();
            int i14 = l11 >> 4 & 0xf;
            int i16 = l11 & 7;
            if(((Mob) (localPlayer)).xList[0] >= k3 - i14 && ((Mob) (localPlayer)).xList[0] <= k3 + i14 && ((Mob) (localPlayer)).yList[0] >= j6 - i14 && ((Mob) (localPlayer)).yList[0] <= j6 + i14 && aBoolean848 && !lowMemory && anInt1062 < 50)
            {
                anIntArray1207[anInt1062] = i9;
                anIntArray1241[anInt1062] = i16;
                anIntArray1250[anInt1062] = Sound.anIntArray326[i9];
                anInt1062++;
            }
        }
		/* Spawn ground item */
        if(j == 215)
        {
            int i1 = buffer0.getUword128();
            int l3 = buffer0.getUbyteB();
            int k6 = anInt1268 + (l3 >> 4 & 7);
            int j9 = anInt1269 + (l3 & 7);
            int i12 = buffer0.getUword128();
            int j14 = buffer0.getUword();
            if(k6 >= 0 && j9 >= 0 && k6 < 104 && j9 < 104 && i12 != localPlayerId)
            {
                GroundItem class30_sub2_sub4_sub2_2 = new GroundItem();
                class30_sub2_sub4_sub2_2.itemid = i1;
                class30_sub2_sub4_sub2_2.amount = j14;
                if(grounditems[currentZ][k6][j9] == null)
                    grounditems[currentZ][k6][j9] = new Deque();
                grounditems[currentZ][k6][j9].addLast(class30_sub2_sub4_sub2_2);
                method25(k6, j9);
            }
            return;
        }
        if(j == 156)
        {
            int j1 = buffer0.getByte128();
            int i4 = anInt1268 + (j1 >> 4 & 7);
            int l6 = anInt1269 + (j1 & 7);
            int k9 = buffer0.getUword();
            if(i4 >= 0 && l6 >= 0 && i4 < 104 && l6 < 104)
            {
                Deque class19 = grounditems[currentZ][i4][l6];
                if(class19 != null)
                {
                    for(GroundItem class30_sub2_sub4_sub2 = (GroundItem) class19.getFirst(); class30_sub2_sub4_sub2 != null; class30_sub2_sub4_sub2 = (GroundItem)class19.getNextFront()) {
                        if(class30_sub2_sub4_sub2.itemid != (k9 & 0x7fff))
                            continue;
                        class30_sub2_sub4_sub2.removeDeque();
                        break;
                    }
                    if(class19.getFirst() == null)
                        grounditems[currentZ][i4][l6] = null;
                    method25(i4, l6);
                }
            }
            return;
        }
        if(j == 160)
        {
            int k1 = buffer0.getUbyteB();
            int j4 = anInt1268 + (k1 >> 4 & 7);
            int i7 = anInt1269 + (k1 & 7);
            int l9 = buffer0.getUbyteB();
            int j12 = l9 >> 2;
            int k14 = l9 & 3;
            int j16 = OBJECT_TYPES[j12];
            int j17 = buffer0.getUword128();
            if(j4 >= 0 && i7 >= 0 && j4 < 103 && i7 < 103)
            {
                int j18 = tileHeightmap[currentZ][j4][i7];
                int i19 = tileHeightmap[currentZ][j4 + 1][i7];
                int l19 = tileHeightmap[currentZ][j4 + 1][i7 + 1];
                int k20 = tileHeightmap[currentZ][j4][i7 + 1];
                if(j16 == 0)
                {
                    Wall class10 = pallet.getWall(currentZ, j4, i7, false);
                    if(class10 != null)
                    {
                        int k21 = class10.anInt280 >> 14 & 0x7fff;
						/* Object type 2: diagnol wall */
                        if(j12 == 2)
                        {
                            class10.aActor_278 = new GameObject(k21, 4 + k14, 2, i19, (byte)7, l19, j18, k20, j17, false);
                            class10.aActor_279 = new GameObject(k21, k14 + 1 & 3, 2, i19, (byte)7, l19, j18, k20, j17, false);
                        } else
                        {
                            class10.aActor_278 = new GameObject(k21, k14, j12, i19, (byte)7, l19, j18, k20, j17, false);
                        }
                    }
                }
                if(j16 == 1)
                {
                    WallDecoration class26 = pallet.getWallDecoration(j4, 866, i7, currentZ);
                    if(class26 != null)
                        class26.wd_entity = new GameObject(class26.wd_objhash >> 14 & 0x7fff, 0, 4, i19, (byte)7, l19, j18, k20, j17, false);
                }
                if(j16 == 2)
                {
                    GeneralEntity class28 = pallet.method298(j4, i7, (byte)4, currentZ);
                    if(j12 == 11)
                        j12 = 10;
                    if(class28 != null)
                        class28.aActor_521 = new GameObject(class28.anInt529 >> 14 & 0x7fff, k14, j12, i19, (byte)7, l19, j18, k20, j17, false);
                }
                if(j16 == 3)
                {
                    FloorDecoration class49 = pallet.getFloorDecoration(i7, j4, currentZ, 0);
                    if(class49 != null)
                        class49.aActor_814 = new GameObject(class49.anInt815 >> 14 & 0x7fff, k14, 22, i19, (byte)7, l19, j18, k20, j17, false);
                }
            }
            return;
        }
        if(j == 147) {
            int l1 = buffer0.getUbyteB();
            int x = anInt1268 + (l1 >> 4 & 7);
            int y = anInt1269 + (l1 & 7);
            int playerId = buffer0.getUword();
            byte byte0 = buffer0.getByteB();
            int l14 = buffer0.getUwordLE();
            byte byte1 = buffer0.getByteA();
            int k17 = buffer0.getUword();
            int k18 = buffer0.getUbyteB();
            int j19 = k18 >> 2;
            int i20 = k18 & 3;
            int l20 = OBJECT_TYPES[j19];
            byte byte2 = buffer0.getByte();
            int objectId = buffer0.getUword();
            byte byte3 = buffer0.getByteA();
            Player player;
            if(playerId == localPlayerId)
                player = localPlayer;
            else
                player = playerArray[playerId];
            if(player != null) {
                ObjectDefinition model = ObjectDefinition.getObjectDefinition(objectId);
                int height0 = tileHeightmap[currentZ][x][y];
                int height1 = tileHeightmap[currentZ][x + 1][y];
                int height2 = tileHeightmap[currentZ][x + 1][y + 1];
                int height3 = tileHeightmap[currentZ][x][y + 1];
                Model translatedModel = model.method578(j19, i20, height0, height1, height2, height3, -1);
                if(translatedModel != null) {
                    addSpawnObject(404, k17 + 1, -1, 0, l20, y, 0, currentZ, x, l14 + 1);
                    player.anInt1707 = l14 + loopCycle;
                    player.anInt1708 = k17 + loopCycle;
                    player.aActor_Sub6_1714 = translatedModel;
                    int i23 = model.anInt744;
                    int j23 = model.size;
                    if(i20 == 1 || i20 == 3)
                    {
                        i23 = model.size;
                        j23 = model.anInt744;
                    }
                    player.anInt1711 = x * 128 + i23 * 64;
                    player.anInt1713 = y * 128 + j23 * 64;
                    player.anInt1712 = calculateTileHeight( player.anInt1711, player.anInt1713, currentZ);
                    if(byte2 > byte0)
                    {
                        byte byte4 = byte2;
                        byte2 = byte0;
                        byte0 = byte4;
                    }
                    if(byte3 > byte1)
                    {
                        byte byte5 = byte3;
                        byte3 = byte1;
                        byte1 = byte5;
                    }
                    player.anInt1719 = x + byte2;
                    player.anInt1721 = x + byte0;
                    player.anInt1720 = y + byte3;
                    player.anInt1722 = y + byte1;
                }
            }
        }
		/* Spawn Object Packet */
        if(j == 151)
        {
            int cxy = buffer0.getByte128();
            int px = anInt1268 + (cxy >> 4 & 7);
            int py = anInt1269 + (cxy & 7);
            int oid = buffer0.getUwordLE();
            int rt = buffer0.getUbyteB();
            int typeId = rt >> 2;
            int rota = rt & 3;
            int l17 = OBJECT_TYPES[typeId];
            if(px >= 0 && py >= 0 && px < 104 && py < 104)
                addSpawnObject(404, -1, oid, rota, l17, py, typeId, currentZ, px, 0);
            return;
        }
        if(j == 4)
        {
            int j2 = buffer0.getUbyte();
            int i5 = anInt1268 + (j2 >> 4 & 7);
            int l7 = anInt1269 + (j2 & 7);
            int k10 = buffer0.getUword();
            int l12 = buffer0.getUbyte();
            int j15 = buffer0.getUword();
            if(i5 >= 0 && l7 >= 0 && i5 < 104 && l7 < 104)
            {
                i5 = i5 * 128 + 64;
                l7 = l7 * 128 + 64;
                StillGraphic class30_sub2_sub4_sub3 = new StillGraphic(currentZ, loopCycle, 6, j15, k10, calculateTileHeight(i5, l7, currentZ) - l12, l7, i5);
                gfxs_storage.addLast(class30_sub2_sub4_sub3);
            }
            return;
        }
        if(j == 44)
        {
            int k2 = buffer0.getUwordLE128();
            int j5 = buffer0.getUword();
            int i8 = buffer0.getUbyte();
            int l10 = anInt1268 + (i8 >> 4 & 7);
            int i13 = anInt1269 + (i8 & 7);
            if(l10 >= 0 && i13 >= 0 && l10 < 104 && i13 < 104)
            {
                GroundItem class30_sub2_sub4_sub2_1 = new GroundItem();
                class30_sub2_sub4_sub2_1.itemid = k2;
                class30_sub2_sub4_sub2_1.amount = j5;
                if(grounditems[currentZ][l10][i13] == null)
                    grounditems[currentZ][l10][i13] = new Deque();
                grounditems[currentZ][l10][i13].addLast(class30_sub2_sub4_sub2_1);
                method25(l10, i13);
            }
            return;
        }
        if(j == 101)
        {
            int l2 = buffer0.getUbyteA();
            int k5 = l2 >> 2;
            int j8 = l2 & 3;
            int i11 = OBJECT_TYPES[k5];
            int j13 = buffer0.getUbyte();
            int k15 = anInt1268 + (j13 >> 4 & 7);
            int l16 = anInt1269 + (j13 & 7);
            if(k15 >= 0 && l16 >= 0 && k15 < 104 && l16 < 104)
                addSpawnObject(404, -1, -1, j8, i11, l16, k5, currentZ, k15, 0);
            return;
        }
        if(j == 117)
        {
            int i3 = buffer0.getUbyte();
            int l5 = anInt1268 + (i3 >> 4 & 7);
            int k8 = anInt1269 + (i3 & 7);
            int j11 = l5 + buffer0.getByte();
            int k13 = k8 + buffer0.getByte();
            int l15 = buffer0.getWord();
            int i17 = buffer0.getUword();
            int i18 = buffer0.getUbyte() * 4;
            int l18 = buffer0.getUbyte() * 4;
            int k19 = buffer0.getUword();
            int j20 = buffer0.getUword();
            int i21 = buffer0.getUbyte();
            int j21 = buffer0.getUbyte();
            if(l5 >= 0 && k8 >= 0 && l5 < 104 && k8 < 104 && j11 >= 0 && k13 >= 0 && j11 < 104 && k13 < 104 && i17 != 65535)
            {
                l5 = l5 * 128 + 64;
                k8 = k8 * 128 + 64;
                j11 = j11 * 128 + 64;
                k13 = k13 * 128 + 64;
                Projectile class30_sub2_sub4_sub4 = new Projectile(i21, l18, 46883, k19 + loopCycle, j20 + loopCycle, j21, currentZ, calculateTileHeight(l5, k8, currentZ) - i18, k8, l5, l15, i17);
                class30_sub2_sub4_sub4.method455(k19 + loopCycle, k13, calculateTileHeight(j11, k13, currentZ) - l18, j11, (byte)-83);
                aClass19_1013.addLast(class30_sub2_sub4_sub4);
            }
        }
    }

    public static void initializeLowMemory() {
        Palette.lowmemory = true;
        TriangleRasterizer.lowmemory = true;
        lowMemory = true;
        LandscapeLoader.lowmemory = true;
        ObjectDefinition.lowmemory = true;
    }

    public void method139(ByteBuffer buffer0, int i, int j)
    {
        if(i >= 0)
            anInt1118 = -7;
        buffer0.initBitAccess();
        int k = buffer0.getBits(8);
        if(k < anInt836) {
            for(int l = k; l < anInt836; l++)
                eRmQueue[eRmQueuePosition++] = localNpcIds[l];
        }
        if(k > anInt836)
        {
            Signlink.reportError(username + " Too many npcs");
            throw new RuntimeException("eek");
        }
        anInt836 = 0;
        for(int i1 = 0; i1 < k; i1++)
        {
            int j1 = localNpcIds[i1];
            Npc class30_sub2_sub4_sub1_sub1 = npcs[j1];
            int k1 = buffer0.getBits(1);
            if(k1 == 0)
            {
                localNpcIds[anInt836++] = j1;
                class30_sub2_sub4_sub1_sub1.anInt1537 = loopCycle;
            } else
            {
                int l1 = buffer0.getBits(2);
                if(l1 == 0)
                {
                    localNpcIds[anInt836++] = j1;
                    class30_sub2_sub4_sub1_sub1.anInt1537 = loopCycle;
                    pFlagUpdateList[amtplayerupdatestack++] = j1;
                } else
                if(l1 == 1)
                {
                    localNpcIds[anInt836++] = j1;
                    class30_sub2_sub4_sub1_sub1.anInt1537 = loopCycle;
                    int i2 = buffer0.getBits(3);
                    class30_sub2_sub4_sub1_sub1.handleMobMovment(false, i2);
                    int k2 = buffer0.getBits(1);
                    if(k2 == 1)
                        pFlagUpdateList[amtplayerupdatestack++] = j1;
                } else
                if(l1 == 2)
                {
                    localNpcIds[anInt836++] = j1;
                    class30_sub2_sub4_sub1_sub1.anInt1537 = loopCycle;
                    int j2 = buffer0.getBits(3);
                    class30_sub2_sub4_sub1_sub1.handleMobMovment(true, j2);
                    int l2 = buffer0.getBits(3);
                    class30_sub2_sub4_sub1_sub1.handleMobMovment(true, l2);
                    int i3 = buffer0.getBits(1);
                    if(i3 == 1)
                        pFlagUpdateList[amtplayerupdatestack++] = j1;
                } else
                if(l1 == 3)
                    eRmQueue[eRmQueuePosition++] = j1;
            }
        }

    }

    public void loopCycleTitle(boolean flag)
    {
        if(!flag)
            grounditems = null;
        if(titlescreen_tab == 0)
        {
            int i = super.appletWidth / 2 - 80;
            int l = super.appletHeight / 2 + 20;
            l += 20;
            if(super.anInt26 == 1 && super.pressedX >= i - 75 && super.pressedX <= i + 75 && super.pressedY >= l - 20 && super.pressedY <= l + 20)
            {
                titlescreen_tab = 3;
                userpass_swtch = 0;
            }
            i = super.appletWidth / 2 + 80;
            if(super.anInt26 == 1 && super.pressedX >= i - 75 && super.pressedX <= i + 75 && super.pressedY >= l - 20 && super.pressedY <= l + 20)
            {
                loginMessage0 = "";
                loginMessage1 = "Enter your username & password.";
                titlescreen_tab = 2;
                userpass_swtch = 0;
                return;
            }
        } else
        {
            if(titlescreen_tab == 2)
            {
                int j = super.appletHeight / 2 - 40;
                j += 30;
                j += 25;
                if(super.anInt26 == 1 && super.pressedY >= j - 15 && super.pressedY < j)
                    userpass_swtch = 0;
                j += 15;
                if(super.anInt26 == 1 && super.pressedY >= j - 15 && super.pressedY < j)
                    userpass_swtch = 1;
                j += 15;
                int i1 = super.appletWidth / 2 - 80;
                int k1 = super.appletHeight / 2 + 50;
                k1 += 20;
				/* Login button */
                if(super.anInt26 == 1 && super.pressedX >= i1 - 75 && super.pressedX <= i1 + 75 && super.pressedY >= k1 - 20 && super.pressedY <= k1 + 20)
                {
                    loginAttempts = 0;
                    handleLogin(username, password, false);
                    if(isOnlineGame)
                        return;
                }
                i1 = super.appletWidth / 2 + 80;
                if(super.anInt26 == 1 && super.pressedX >= i1 - 75 && super.pressedX <= i1 + 75 && super.pressedY >= k1 - 20 && super.pressedY <= k1 + 20)
                {
                    titlescreen_tab = 0;
                    username = "";
                    password = "";
                }
                do
                {
                    int l1 = removeKeyId();
                    if(l1 == -1)
                        break;
                    boolean flag1 = false;
                    for(int i2 = 0; i2 < passchars.length(); i2++)
                    {
                        if(l1 != passchars.charAt(i2))
                            continue;
                        flag1 = true;
                        break;
                    }

                    if(userpass_swtch == 0)
                    {
						/* Backspace */
                        if(l1 == 8 && username.length() > 0)
                            username = username.substring(0, username.length() - 1);
						/* Tab, Line Feed and Carrage Return */
                        if(l1 == 9 || l1 == 10 || l1 == 13)
                            userpass_swtch = 1;
                        if(flag1)
                            username += (char)l1;
                        if(username.length() > 12)
                            username = username.substring(0, 12);
                    } else
                    if(userpass_swtch == 1)
                    {
                        if(l1 == 8 && password.length() > 0)
                            password = password.substring(0, password.length() - 1);
                        if(l1 == 9 || l1 == 10 || l1 == 13)
                            userpass_swtch = 0;
                        if(flag1)
                            password += (char)l1;
                        if(password.length() > 20)
                            password = password.substring(0, 20);
                    }
                } while(true);
                return;
            }
            if(titlescreen_tab == 3)
            {
                int k = super.appletWidth / 2;
                int j1 = super.appletHeight / 2 + 50;
                j1 += 20;
                if(super.anInt26 == 1 && super.pressedX >= k - 75 && super.pressedX <= k + 75 && super.pressedY >= j1 - 20 && super.pressedY <= j1 + 20)
                    titlescreen_tab = 0;
            }
        }
    }

    public void drawOnMinimap(DirectColorSprite sprite, int x, int y, boolean flag)
    {
        int yaw = cameraYaw + anInt1209 & 0x7ff;
        int l = x * x + y * y;
        if(l > 6400)
            return;
        int sine = Model.sinetable[yaw];
        int cosine = Model.cosinetable[yaw];
        sine = (sine * 256) / (anInt1170 + 256);
        cosine = (cosine * 256) / (anInt1170 + 256);
        int posx = y * sine + x * cosine >> 16;
        int posy = y * cosine - x * sine >> 16;
        if(l > 2500)
        {
            sprite.draw(mapback, false, 83 - posy - sprite.spriteHeight / 2 - 4, ((94 + posx) - sprite.spriteWidth / 2) + 4);
            return;
        } else
        {
            sprite.draw(((94 + posx) - sprite.spriteWidth / 2) + 4, 16083, 83 - posy - sprite.spriteHeight / 2 - 4);
            return;
        }
    }

    public void method142(int i, int j, int k, int l, int i1, int j1, int k1, 
            int l1)
    {
        if(i1 >= 1 && i >= 1 && i1 <= 102 && i <= 102)
        {
            if(lowMemory && j != currentZ)
                return;
            int i2 = 0;
            if(j1 == 0)
                i2 = pallet.method300(j, i1, i);
            if(j1 == 1)
                i2 = pallet.method301(j, i1, 0, i);
            if(j1 == 2)
                i2 = pallet.method302(j, i1, i);
            if(j1 == 3)
                i2 = pallet.method303(j, i1, i);
            if(i2 != 0)
            {
                int i3 = pallet.method304(j, i1, i, i2);
                int j2 = i2 >> 14 & 0x7fff;
                int k2 = i3 & 0x1f;
                int l2 = i3 >> 6;
                if(j1 == 0)
                {
                    pallet.method291(i1, j, i, (byte)-119);
                    ObjectDefinition class46 = ObjectDefinition.getObjectDefinition(j2);
                    if(class46.aBoolean767)
                        planeFlags[j].method215(l2, k2, class46.aBoolean757, true, i1, i);
                }
                if(j1 == 1)
                    pallet.method292(i1, i, j);
                if(j1 == 2)
                {
                    pallet.method293(i1, i, j);
                    ObjectDefinition class46_1 = ObjectDefinition.getObjectDefinition(j2);
                    if(i1 + class46_1.anInt744 > 103 || i + class46_1.anInt744 > 103 || i1 + class46_1.size > 103 || i + class46_1.size > 103)
                        return;
                    if(class46_1.aBoolean767)
                        planeFlags[j].method216(l2, class46_1.anInt744, i1, i, (byte)6, class46_1.size, class46_1.aBoolean757);
                }
                if(j1 == 3)
                {
                    pallet.method294((byte)9, j, i, i1);
                    ObjectDefinition class46_2 = ObjectDefinition.getObjectDefinition(j2);
                    if(class46_2.aBoolean767 && class46_2.aBoolean778)
                        planeFlags[j].method218(360, i, i1);
                }
            }
            if(k1 >= 0)
            {
                int j3 = j;
                if(j3 < 3 && (tileFlags[1][i1][i] & 2) == 2)
                    j3++;
                LandscapeLoader.method188(pallet, k, i, l, j3, planeFlags[j], tileHeightmap, i1, k1, j, (byte)93);
            }
        }
    }

    public void parsePlayerUpdate(int i, ByteBuffer buffer, int junk)
    {
        eRmQueuePosition = 0;
        amtplayerupdatestack = 0;
        parseLocalPlayerMovement(buffer, i, (byte)5);
        parsePlayerUpdates((byte)2, i, buffer);
        populatePlayerList(buffer, i, (byte)8);
        handlePlayerUpdateFlags(buffer);
        for(int k = 0; k < eRmQueuePosition; k++)
        {
            int l = eRmQueue[k];
            if(((Mob) (playerArray[l])).anInt1537 != loopCycle)
                playerArray[l] = null;
        }

        if(buffer.offset != i)
        {
            Signlink.reportError("Error packet size mismatch in getplayer pos:" + buffer.offset + " psize:" + i);
            throw new RuntimeException("eek");
        }
        for(int i1 = 0; i1 < playerOffset; i1++)
            if(playerArray[addedPlayers[i1]] == null)
            {
                Signlink.reportError(username + " null entry in pl list - pos:" + i1 + " size:" + playerOffset);
                throw new RuntimeException("eek");
            }

    }

    public void setCurrentCameraVars(int pitch, int yaw, int y, int x0, int z0, int y0)
    {
        int pitchexemp = 2048 - pitch & 0x7ff;
        int yawexemp = 2048 - yaw & 0x7ff;
        int x1 = 0;
        int z1 = 0;
        int y1 = y;
        /* X axis rotation */
        if(pitchexemp != 0)
        {
            int sine = Model.sinetable[pitchexemp];
            int cosine = Model.cosinetable[pitchexemp];
            int zcalc = z1 * cosine - y1 * sine >> 16;
            y1 = z1 * sine + y1 * cosine >> 16;
            z1 = zcalc;
        }
        /* Z axis rotation *Counter* */
        if(yawexemp != 0)
        {
            int sine = Model.sinetable[yawexemp];
            int cosine = Model.cosinetable[yawexemp];
            int xcalc = y1 * sine + x1 * cosine >> 16;
            y1 = y1 * cosine - x1 * sine >> 16;
            x1 = xcalc;
        }
        camerax = x0 - x1;
        cameraz = z0 - z1;
        cameray = y0 - y1;
        camerapitch$ = pitch;
        camerayaw$ = yaw;
    }

    public boolean parsePackets(boolean flag) {
        if(!flag)
            grounditems = null;
        if(bufferedConnection == null)
            return false;
        try {
            int i = bufferedConnection.getAvailable();
            if(i == 0)
                return false;
            if(packetId == -1) {
                bufferedConnection.read(inbuffer.payload, 0, 1);
                packetId = inbuffer.payload[0] & 0xff;
                if(packetencryption != null)
                    packetId = packetId - packetencryption.poll() & 0xff;
                packetSize = PacketConstants.incoming_sizes[packetId];
                i--;
            }
			/* Var byte */
            if(packetSize == -1)
                if(i > 0) {
                    bufferedConnection.read(inbuffer.payload, 0, 1);
                    packetSize = inbuffer.payload[0] & 0xff;
                    i--;
                } else {
                    return false;
                }
			/* Var short */
            if(packetSize == -2)
                if(i > 1) {
                    bufferedConnection.read(inbuffer.payload, 0, 2);
                    inbuffer.offset = 0;
                    packetSize = inbuffer.getUword();
                    i -= 2;
                } else {
                    return false;
                }
            if(i < packetSize)
                return false;
            inbuffer.offset = 0;
            bufferedConnection.read(inbuffer.payload, 0, packetSize);
            anInt1009 = 0;
            anInt843 = anInt842;
            anInt842 = anInt841;
            anInt841 = packetId;
            if(packetId == 81) {
                parsePlayerUpdate(packetSize, inbuffer, 9759);
                aBoolean1080 = false;
                packetId = -1;
                return true;
            }
            if(packetId == 176) {
                anInt1167 = inbuffer.getUbyteA();
                anInt1154 = inbuffer.getUword128();
                anInt1120 = inbuffer.getUbyte();
                anInt1193 = inbuffer.getDwordB();
                pastlaginamountdays = inbuffer.getUword();
                if(anInt1193 != 0 && anInt857 == -1) {
                    Signlink.lookupHost(TextTools.getHostAddress(anInt1193, true));
                    method147(537);
                    char c = '\u028A';
                    if(anInt1167 != 201 || anInt1120 == 1)
                        c = '\u028F';
                    aString881 = "";
                    aBoolean1158 = false;
                    for(int k9 = 0; k9 < Widget.widgets.length; k9++) {
                        if(Widget.widgets[k9] == null || Widget.widgets[k9].actionCode != c)
                            continue;
                        anInt857 = Widget.widgets[k9].parentId;
                        break;
                    }
                }
                packetId = -1;
                return true;
            }
            if(packetId == 64) {
                anInt1268 = inbuffer.getUbyteA();
                anInt1269 = inbuffer.getUbyteB();
                for(int j = anInt1268; j < anInt1268 + 8; j++) {
                    for(int l9 = anInt1269; l9 < anInt1269 + 8; l9++)
                        if(grounditems[currentZ][j][l9] != null) {
                            grounditems[currentZ][j][l9] = null;
                            method25(j, l9);
                        }
                }
                for(SpawnedObject class30_sub1 = (SpawnedObject)aClass19_1179.getFirst(); class30_sub1 != null; class30_sub1 = (SpawnedObject)aClass19_1179.getNextFront())
                    if(class30_sub1.pallete_x >= anInt1268 && class30_sub1.pallete_x < anInt1268 + 8 && class30_sub1.pallete_y >= anInt1269 && class30_sub1.pallete_y < anInt1269 + 8 && class30_sub1.cheight == currentZ)
                        class30_sub1.anInt1294 = 0;
                packetId = -1;
                return true;
            }
			/* Mob on interface */
            if(packetId == 185) {
                int k = inbuffer.getUwordLE128();
                Widget.widgets[k].unactiveAnimFetchType = 3;
                if(localPlayer.npc == null)
                    Widget.widgets[k].unactiveAnimModelId = (localPlayer.colorIds[0] << 25) + (localPlayer.colorIds[4] << 20) + (localPlayer.appearanceStates[0] << 15) + (localPlayer.appearanceStates[8] << 10) + (localPlayer.appearanceStates[11] << 5) + localPlayer.appearanceStates[1];
                else
                    Widget.widgets[k].unactiveAnimModelId = (int)(0x12345678L + localPlayer.npc.id);
                packetId = -1;
                return true;
            }
			/* Stops the camera */
            if(packetId == 107)
            {
                aBoolean1160 = false;
                for(int l = 0; l < 5; l++)
                    cameramovements[l] = false;
                packetId = -1;
                return true;
            }
            if(packetId == 72)
            {
                int i1 = inbuffer.getUwordLE();
                Widget class9 = Widget.widgets[i1];
                for(int k15 = 0; k15 < class9.itemIds.length; k15++)
                {
                    class9.itemIds[k15] = -1;
                    class9.itemIds[k15] = 0;
                }

                packetId = -1;
                return true;
            }
			/* Update ignores */
            if(packetId == 214) {
                amt_ignorehashes = packetSize / 8;
                for(int j1 = 0; j1 < amt_ignorehashes; j1++)
                    ignore_hashes[j1] = inbuffer.getQword();

                packetId = -1;
                return true;
            }
            if(packetId == 166)
            {
                aBoolean1160 = true;
                spincam_x = inbuffer.getUbyte();
                spincam_y = inbuffer.getUbyte();
                spincam_z = inbuffer.getUword();
                spincam_speed = inbuffer.getUbyte();
                spincam_angle = inbuffer.getUbyte();
                if(spincam_angle >= 100) {
                    camerax = spincam_x * 128 + 64;
                    cameray = spincam_y * 128 + 64;
                    cameraz = calculateTileHeight(camerax, cameray, currentZ) - spincam_z;
                }
                packetId = -1;
                return true;
            }
            if(packetId == 134)
            {
                aBoolean1153 = true;
                int k1 = inbuffer.getUbyte();
                int i10 = inbuffer.getDwordA();
                int l15 = inbuffer.getUbyte();
                skillExperience[k1] = i10;
                skillLevels[k1] = l15;
                anIntArray1044[k1] = 1;
                for(int k20 = 0; k20 < 98; k20++)
                    if(i10 >= XP_TABLE[k20])
                        anIntArray1044[k1] = k20 + 2;
                packetId = -1;
                return true;
            }
            if(packetId == 71)
            {
                int inter = inbuffer.getUword();
                int tab = inbuffer.getByte128();
                if(inter == 65535)
                    inter = -1;
                tab_interfaces[tab] = inter;
                aBoolean1153 = true;
                update_tabs = true;
                packetId = -1;
                return true;
            }
            if(packetId == 74)
            {
                int i2 = inbuffer.getUwordLE();
                if(i2 == 65535)
                    i2 = -1;
                if(i2 != anInt956 && aBoolean1151 && !lowMemory && anInt1259 == 0)
                {
                    anInt1227 = i2;
                    aBoolean1228 = true;
                    ondemandhandler.requestPriority(2, anInt1227);
                }
                anInt956 = i2;
                packetId = -1;
                return true;
            }
            if(packetId == 121)
            {
                int j2 = inbuffer.getUwordLE128();
                int k10 = inbuffer.getUword128();
                if(aBoolean1151 && !lowMemory)
                {
                    anInt1227 = j2;
                    aBoolean1228 = false;
                    ondemandhandler.requestPriority(2, anInt1227);
                    anInt1259 = k10;
                }
                packetId = -1;
                return true;
            }
            if(packetId == 109)
            {
                killToMainscreen(true);
                packetId = -1;
                return false;
            }
            if(packetId == 70)
            {
                int offsetX = inbuffer.getWord();
                int offsetY = inbuffer.getWordLE();
                int widgetid = inbuffer.getUwordLE();
                Widget class9_5 = Widget.widgets[widgetid];
                class9_5.offsetX = offsetX;
                class9_5.offsetY = offsetY;
                packetId = -1;
                return true;
            }
			/* Update pallete packets */
            if(packetId == 73 || packetId == 241) {
                int l2 = chunkx_;
                int i11 = chunky_;
                if(packetId == 73) {
                    l2 = inbuffer.getUword128();
                    i11 = inbuffer.getUword();
                    aBoolean1159 = false;
                }
                if(packetId == 241) {
                    i11 = inbuffer.getUword128();
                    inbuffer.initBitAccess();
                    for(int j16 = 0; j16 < 4; j16++) {
                        for(int l20 = 0; l20 < 13; l20++) {
                            for(int j23 = 0; j23 < 13; j23++) {
                                int i26 = inbuffer.getBits(1);
                                if(i26 == 1)
                                    custompalette[j16][l20][j23] = inbuffer.getBits(26);
                                else
                                    custompalette[j16][l20][j23] = -1;
                            }
                        }
                    }
                    inbuffer.endBitAccess();
                    l2 = inbuffer.getUword();
                    aBoolean1159 = true;
                }
                if(chunkx_ == l2 && chunky_ == i11 && landscape_stage == 2)
                {
                    packetId = -1;
                    return true;
                }
                chunkx_ = l2;
                chunky_ = i11;
                paletteX = (chunkx_ - 6) * 8;
                paletteY = (chunky_ - 6) * 8;
                isLoadedLandscapes = false;
				/* Already loaded */
                if((chunkx_ / 8 == 48 || chunkx_ / 8 == 49) && chunky_ / 8 == 48)
                    isLoadedLandscapes = true;
				/* Already laoded */
                if(chunkx_ / 8 == 48 && chunky_ / 8 == 148)
                    isLoadedLandscapes = true;
                landscape_stage = 1;
                timestamp = System.currentTimeMillis();
                toplefttext_imagefetcher.initialize(0);
                p12Font.drawCenteredYText(0, "Loading - please wait.", 23693, 151, 257);
                p12Font.drawCenteredYText(0xffffff, "Loading - please wait.", 23693, 150, 256);
                toplefttext_imagefetcher.updateGraphics(4, 23680, super.appletGraphics, 4);
                if(packetId == 73)
                {
                    int k16 = 0;
                    for(int i21 = (chunkx_ - 6) / 8; i21 <= (chunkx_ + 6) / 8; i21++) {
                        for(int k23 = (chunky_ - 6) / 8; k23 <= (chunky_ + 6) / 8; k23++)
                            k16++;
                    }
                    tileSrcs = new byte[k16][];
                    regionSrcs = new byte[k16][];
                    regionHashes = new int[k16];
                    anIntArray1235 = new int[k16];
                    anIntArray1236 = new int[k16];
                    k16 = 0;
                    for(int l23 = (chunkx_ - 6) / 8; l23 <= (chunkx_ + 6) / 8; l23++)
                    {
                        for(int j26 = (chunky_ - 6) / 8; j26 <= (chunky_ + 6) / 8; j26++)
                        {
                            regionHashes[k16] = (l23 << 8) + j26;
                            if(isLoadedLandscapes && (j26 == 49 || j26 == 149 || j26 == 147 || l23 == 50 || l23 == 49 && j26 == 47))
                            {
                                anIntArray1235[k16] = -1;
                                anIntArray1236[k16] = -1;
                                k16++;
                            } else
                            {
                                int k28 = anIntArray1235[k16] = ondemandhandler.getMapArchive(l23, j26, 0);
                                if(k28 != -1)
                                    ondemandhandler.requestPriority(3, k28);
                                int j30 = anIntArray1236[k16] = ondemandhandler.getMapArchive(l23, j26, 1);
                                if(j30 != -1)
                                    ondemandhandler.requestPriority(3, j30);
                                k16++;
                            }
                        }

                    }

                }
                if(packetId == 241)
                {
                    int l16 = 0;
                    int ai[] = new int[676];
                    for(int i24 = 0; i24 < 4; i24++)
                    {
                        for(int k26 = 0; k26 < 13; k26++)
                        {
                            for(int l28 = 0; l28 < 13; l28++)
                            {
                                int k30 = custompalette[i24][k26][l28];
                                if(k30 != -1)
                                {
                                    int k31 = k30 >> 14 & 0x3ff;
                                    int i32 = k30 >> 3 & 0x7ff;
                                    int k32 = (k31 / 8 << 8) + i32 / 8;
                                    for(int j33 = 0; j33 < l16; j33++)
                                    {
                                        if(ai[j33] != k32)
                                            continue;
                                        k32 = -1;
                                        break;
                                    }

                                    if(k32 != -1)
                                        ai[l16++] = k32;
                                }
                            }

                        }

                    }

                    tileSrcs = new byte[l16][];
                    regionSrcs = new byte[l16][];
                    regionHashes = new int[l16];
                    anIntArray1235 = new int[l16];
                    anIntArray1236 = new int[l16];
                    for(int l26 = 0; l26 < l16; l26++)
                    {
                        int i29 = regionHashes[l26] = ai[l26];
                        int l30 = i29 >> 8 & 0xff;
                        int l31 = i29 & 0xff;
                        int j32 = anIntArray1235[l26] = ondemandhandler.getMapArchive(l30, l31, 0);
                        if(j32 != -1)
                            ondemandhandler.requestPriority(3, j32);
                        int i33 = anIntArray1236[l26] = ondemandhandler.getMapArchive(l30, l31, 1);
                        if(i33 != -1)
                            ondemandhandler.requestPriority(3, i33);
                    }

                }
                int i17 = paletteX - anInt1036;
                int j21 = paletteY - anInt1037;
                anInt1036 = paletteX;
                anInt1037 = paletteY;
                for(int i10 = 0; i10 < 16384; i10++)
                {
                    Npc class30_sub2_sub4_sub1_sub1 = npcs[i10];
                    if(class30_sub2_sub4_sub1_sub1 != null) {
                        for(int j29 = 0; j29 < 10; j29++)  {
                            ((Mob) (class30_sub2_sub4_sub1_sub1)).xList[j29] -= i17;
                            ((Mob) (class30_sub2_sub4_sub1_sub1)).yList[j29] -= j21;
                        }
                        class30_sub2_sub4_sub1_sub1.fineX -= i17 * 128;
                        class30_sub2_sub4_sub1_sub1.fineY -= j21 * 128;
                    }
                }

                for(int i27 = 0; i27 < maxplayers; i27++)
                {
                    Player class30_sub2_sub4_sub1_sub2 = playerArray[i27];
                    if(class30_sub2_sub4_sub1_sub2 != null)
                    {
                        for(int i31 = 0; i31 < 10; i31++)
                        {
                            ((Mob) (class30_sub2_sub4_sub1_sub2)).xList[i31] -= i17;
                            ((Mob) (class30_sub2_sub4_sub1_sub2)).yList[i31] -= j21;
                        }

                        class30_sub2_sub4_sub1_sub2.fineX -= i17 * 128;
                        class30_sub2_sub4_sub1_sub2.fineY -= j21 * 128;
                    }
                }

                aBoolean1080 = true;
                byte byte1 = 0;
                byte byte2 = 104;
                byte byte3 = 1;
                if(i17 < 0)
                {
                    byte1 = 103;
                    byte2 = -1;
                    byte3 = -1;
                }
                byte byte4 = 0;
                byte byte5 = 104;
                byte byte6 = 1;
                if(j21 < 0)
                {
                    byte4 = 103;
                    byte5 = -1;
                    byte6 = -1;
                }
                for(int k33 = byte1; k33 != byte2; k33 += byte3)
                {
                    for(int l33 = byte4; l33 != byte5; l33 += byte6)
                    {
                        int i34 = k33 + i17;
                        int j34 = l33 + j21;
                        for(int k34 = 0; k34 < 4; k34++)
                            if(i34 >= 0 && j34 >= 0 && i34 < 104 && j34 < 104)
                                grounditems[k34][k33][l33] = grounditems[k34][i34][j34];
                            else
                                grounditems[k34][k33][l33] = null;

                    }

                }

                for(SpawnedObject class30_sub1_1 = (SpawnedObject)aClass19_1179.getFirst(); class30_sub1_1 != null; class30_sub1_1 = (SpawnedObject)aClass19_1179.getNextFront())
                {
                    class30_sub1_1.pallete_x -= i17;
                    class30_sub1_1.pallete_y -= j21;
                    if(class30_sub1_1.pallete_x < 0 || class30_sub1_1.pallete_y < 0 || class30_sub1_1.pallete_x >= 104 || class30_sub1_1.pallete_y >= 104)
                        class30_sub1_1.removeDeque();
                }

                if(anInt1261 != 0)
                {
                    anInt1261 -= i17;
                    anInt1262 -= j21;
                }
                aBoolean1160 = false;
                packetId = -1;
                return true;
            }
            if(packetId == 208)
            {
                int i3 = inbuffer.getWordLE();
                if(i3 >= 0)
                    method60(i3, (byte)6);
                anInt1018 = i3;
                packetId = -1;
                return true;
            }
			/* ? Something with rendering */
            if(packetId == 99)
            {
                anInt1021 = inbuffer.getUbyte();
                packetId = -1;
                return true;
            }
            if(packetId == 75)
            {
                int j3 = inbuffer.getUwordLE128();
                int j11 = inbuffer.getUwordLE128();
                Widget.widgets[j11].unactiveAnimFetchType = 2;
                Widget.widgets[j11].unactiveAnimModelId = j3;
                packetId = -1;
                return true;
            }
            if(packetId == 114)
            {
                anInt1104 = inbuffer.getUwordLE() * 30;
                packetId = -1;
                return true;
            }
			/* Special packet? */
            if(packetId == 60)
            {
                anInt1269 = inbuffer.getUbyte();
                anInt1268 = inbuffer.getUbyteA();
                while(inbuffer.offset < packetSize) 
                {
                    int k3 = inbuffer.getUbyte();
                    parseExtraFiles(anInt1119, inbuffer, k3);
                }
                packetId = -1;
                return true;
            }
			/* Random Camera Movement */
            if(packetId == 35)
            {
                int l3 = inbuffer.getUbyte();
                int k11 = inbuffer.getUbyte();
                int j17 = inbuffer.getUbyte();
                int k21 = inbuffer.getUbyte();
                cameramovements[l3] = true;
                cameratransvars[l3] = k11;
                cameratransvars4[l3] = j17;
                cameratransvars3[l3] = k21;
                cameratransvars2[l3] = 0;
                packetId = -1;
                return true;
            }
			/* Sound packet */
            if(packetId == 174)
            {
                int i4 = inbuffer.getUword();
                int l11 = inbuffer.getUbyte();
                int k17 = inbuffer.getUword();
                if(aBoolean848 && !lowMemory && anInt1062 < 50)
                {
                    anIntArray1207[anInt1062] = i4;
                    anIntArray1241[anInt1062] = l11;
                    anIntArray1250[anInt1062] = k17 + Sound.anIntArray326[i4];
                    anInt1062++;
                }
                packetId = -1;
                return true;
            }
			/* Player option */
            if(packetId == 104)
            {
                int j4 = inbuffer.getUbyteA();
                int i12 = inbuffer.getByte128();
                String s6 = inbuffer.getString();
                if(j4 >= 1 && j4 <= 5)
                {
                    if(s6.equalsIgnoreCase("null"))
                        s6 = null;
                    aStringArray1127[j4 - 1] = s6;
                    aBooleanArray1128[j4 - 1] = i12 == 0;
                }
                packetId = -1;
                return true;
            }
            if(packetId == 78)
            {
                anInt1261 = 0;
                packetId = -1;
                return true;
            }
            if(packetId == 253)
            {
                String s = inbuffer.getString();
                if(s.endsWith(":tradereq:"))
                {
                    String s3 = s.substring(0, s.indexOf(":"));
                    long l17 = TextTools.stringToLong(s3);
                    boolean flag2 = false;
                    for(int j27 = 0; j27 < amt_ignorehashes; j27++)
                    {
                        if(ignore_hashes[j27] != l17)
                            continue;
                        flag2 = true;
                        break;
                    }

                    if(!flag2 && ontutorial_island == 0)
                        pushMessage("wishes to trade with you.", 4, s3, aBoolean991);
                } else
                if(s.endsWith(":duelreq:"))
                {
                    String s4 = s.substring(0, s.indexOf(":"));
                    long l18 = TextTools.stringToLong(s4);
                    boolean flag3 = false;
                    for(int k27 = 0; k27 < amt_ignorehashes; k27++)
                    {
                        if(ignore_hashes[k27] != l18)
                            continue;
                        flag3 = true;
                        break;
                    }

                    if(!flag3 && ontutorial_island == 0)
                        pushMessage("wishes to duel with you.", 8, s4, aBoolean991);
                } else
                if(s.endsWith(":chalreq:"))
                {
                    String s5 = s.substring(0, s.indexOf(":"));
                    long l19 = TextTools.stringToLong(s5);
                    boolean flag4 = false;
                    for(int l27 = 0; l27 < amt_ignorehashes; l27++)
                    {
                        if(ignore_hashes[l27] != l19)
                            continue;
                        flag4 = true;
                        break;
                    }

                    if(!flag4 && ontutorial_island == 0)
                    {
                        String s8 = s.substring(s.indexOf(":") + 1, s.length() - 9);
                        pushMessage(s8, 8, s5, aBoolean991);
                    }
                } else
                {
                    pushMessage(s, 0, "", aBoolean991);
                }
                packetId = -1;
                return true;
            }
            if(packetId == 1)
            {
                for(int k4 = 0; k4 < playerArray.length; k4++)
                    if(playerArray[k4] != null)
                        playerArray[k4].animid_request = -1;

                for(int j12 = 0; j12 < npcs.length; j12++)
                    if(npcs[j12] != null)
                        npcs[j12].animid_request = -1;

                packetId = -1;
                return true;
            }
			/* Friend packet */
            if(packetId == 50)
            {
                long l4 = inbuffer.getQword();
                int i18 = inbuffer.getUbyte();
                String s7 = TextTools.formatUsername(-45804, TextTools.longToString(l4, (byte)-99));
                for(int k24 = 0; k24 < amt_friendhashes; k24++)
                {
                    if(l4 != friend_hashes[k24])
                        continue;
                    if(anIntArray826[k24] != i18)
                    {
                        anIntArray826[k24] = i18;
                        aBoolean1153 = true;
                        if(i18 > 0)
                            pushMessage(s7 + " has logged in.", 5, "", aBoolean991);
                        if(i18 == 0)
                            pushMessage(s7 + " has logged out.", 5, "", aBoolean991);
                    }
                    s7 = null;
                    break;
                }

                if(s7 != null && amt_friendhashes < 200)
                {
                    friend_hashes[amt_friendhashes] = l4;
                    friendusernames[amt_friendhashes] = s7;
                    anIntArray826[amt_friendhashes] = i18;
                    amt_friendhashes++;
                    aBoolean1153 = true;
                }
                for(boolean flag6 = false; !flag6;)
                {
                    flag6 = true;
                    for(int k29 = 0; k29 < amt_friendhashes - 1; k29++)
                        if(anIntArray826[k29] != nodeid && anIntArray826[k29 + 1] == nodeid || anIntArray826[k29] == 0 && anIntArray826[k29 + 1] != 0)
                        {
                            int j31 = anIntArray826[k29];
                            anIntArray826[k29] = anIntArray826[k29 + 1];
                            anIntArray826[k29 + 1] = j31;
                            String s10 = friendusernames[k29];
                            friendusernames[k29] = friendusernames[k29 + 1];
                            friendusernames[k29 + 1] = s10;
                            long l32 = friend_hashes[k29];
                            friend_hashes[k29] = friend_hashes[k29 + 1];
                            friend_hashes[k29 + 1] = l32;
                            aBoolean1153 = true;
                            flag6 = false;
                        }

                }

                packetId = -1;
                return true;
            }
            if(packetId == 110)
            {
                if(current_tab == 12)
                    aBoolean1153 = true;
                anInt1148 = inbuffer.getUbyte();
                packetId = -1;
                return true;
            }
            if(packetId == 254)
            {
                markertype = inbuffer.getUbyte();
                if(markertype == 1)
                    nmarker_id = inbuffer.getUword();
                if(markertype >= 2 && markertype <= 6)
                {
                    if(markertype == 2)
                    {
                        markeroffset_x = 64;
                        markeroffset_y = 64;
                    }
                    if(markertype == 3)
                    {
                        markeroffset_x = 0;
                        markeroffset_y = 64;
                    }
                    if(markertype == 4)
                    {
                        markeroffset_x = 128;
                        markeroffset_y = 64;
                    }
                    if(markertype == 5)
                    {
                        markeroffset_x = 64;
                        markeroffset_y = 0;
                    }
                    if(markertype == 6)
                    {
                        markeroffset_x = 64;
                        markeroffset_y = 128;
                    }
                    markertype = 2;
                    markerloc_x = inbuffer.getUword();
                    markerloc_y = inbuffer.getUword();
                    markerheight = inbuffer.getUbyte();
                }
                if(markertype == 10)
                    pmarker_id = inbuffer.getUword();
                packetId = -1;
                return true;
            }
            if(packetId == 248)
            {
                int i5 = inbuffer.getUword128();
                int k12 = inbuffer.getUword();
                if(anInt1276 != -1)
                {
                    anInt1276 = -1;
                    aBoolean1223 = true;
                }
                if(anInt1225 != 0)
                {
                    anInt1225 = 0;
                    aBoolean1223 = true;
                }
                anInt857 = i5;
                anInt1189 = k12;
                aBoolean1153 = true;
                update_tabs = true;
                aBoolean1149 = false;
                packetId = -1;
                return true;
            }
            if(packetId == 79)
            {
                int j5 = inbuffer.getUwordLE();
                int l12 = inbuffer.getUword128();
                Widget class9_3 = Widget.widgets[j5];
                if(class9_3 != null && class9_3.type == 0)
                {
                    if(l12 < 0)
                        l12 = 0;
                    if(l12 > class9_3.currentHeight - class9_3.height)
                        l12 = class9_3.currentHeight - class9_3.height;
                    class9_3.anInt224 = l12;
                }
                packetId = -1;
                return true;
            }
            if(packetId == 68)
            {
                for(int k5 = 0; k5 < configstates.length; k5++)
                    if(configstates[k5] != configqueue[k5])
                    {
                        configstates[k5] = configqueue[k5];
                        parseClientVarps(false, k5);
                        aBoolean1153 = true;
                    }

                packetId = -1;
                return true;
            }
            if(packetId == 196)
            {
                long l5 = inbuffer.getQword();
                int j18 = inbuffer.getDword();
                int l21 = inbuffer.getUbyte();
                boolean flag5 = false;
                for(int i28 = 0; i28 < 100; i28++)
                {
                    if(anIntArray1240[i28] != j18)
                        continue;
                    flag5 = true;
                    break;
                }

                if(l21 <= 1)
                {
                    for(int l29 = 0; l29 < amt_ignorehashes; l29++)
                    {
                        if(ignore_hashes[l29] != l5)
                            continue;
                        flag5 = true;
                        break;
                    }

                }
                if(!flag5 && ontutorial_island == 0)
                    try
                    {
                        anIntArray1240[anInt1169] = j18;
                        anInt1169 = (anInt1169 + 1) % 100;
                        String s9 = ChatUtils.method525(packetSize - 13, true, inbuffer);
                        if(l21 != 3)
                            s9 = Censor.censor(s9, 0);
                        if(l21 == 2 || l21 == 3)
                            pushMessage(s9, 7, "@cr2@" + TextTools.formatUsername(-45804, TextTools.longToString(l5, (byte)-99)), aBoolean991);
                        else
                        if(l21 == 1)
                            pushMessage(s9, 7, "@cr1@" + TextTools.formatUsername(-45804, TextTools.longToString(l5, (byte)-99)), aBoolean991);
                        else
                            pushMessage(s9, 3, TextTools.formatUsername(-45804, TextTools.longToString(l5, (byte)-99)), aBoolean991);
                    }
                    catch(Exception exception1)
                    {
                        Signlink.reportError("cde1");
                    }
                packetId = -1;
                return true;
            }
            if(packetId == 85)
            {
                anInt1269 = inbuffer.getUbyteA();
                anInt1268 = inbuffer.getUbyteA();
                packetId = -1;
                return true;
            }
            if(packetId == 24)
            {
                anInt1054 = inbuffer.getUbyteB();
                if(anInt1054 == current_tab)
                {
                    if(anInt1054 == 3)
                        current_tab = 1;
                    else
                        current_tab = 3;
                    aBoolean1153 = true;
                }
                packetId = -1;
                return true;
            }
            if(packetId == 246)
            {
                int i6 = inbuffer.getUwordLE();
                int i13 = inbuffer.getUword();
                int k18 = inbuffer.getUword();
                if(k18 == 65535)
                {
                    Widget.widgets[i6].unactiveAnimFetchType = 0;
                    packetId = -1;
                    return true;
                } else
                {
                    ItemDefinition class8 = ItemDefinition.getItemDefinition(k18);
                    Widget.widgets[i6].unactiveAnimFetchType = 4;
                    Widget.widgets[i6].unactiveAnimModelId = k18;
                    Widget.widgets[i6].rotationAngleX = class8.rotation;
                    Widget.widgets[i6].rotationAngleY = class8.anInt198;
                    Widget.widgets[i6].rotationOrigin = (class8.zoom * 100) / i13;
                    packetId = -1;
                    return true;
                }
            }
            if(packetId == 171)
            {
                boolean flag1 = inbuffer.getUbyte() == 1;
                int j13 = inbuffer.getUword();
                Widget.widgets[j13].isActive = flag1;
                packetId = -1;
                return true;
            }
            if(packetId == 142)
            {
                int j6 = inbuffer.getUwordLE();
                method60(j6, (byte)6);
                if(anInt1276 != -1)
                {
                    anInt1276 = -1;
                    aBoolean1223 = true;
                }
                if(anInt1225 != 0)
                {
                    anInt1225 = 0;
                    aBoolean1223 = true;
                }
                anInt1189 = j6;
                aBoolean1153 = true;
                update_tabs = true;
                anInt857 = -1;
                aBoolean1149 = false;
                packetId = -1;
                return true;
            }
            if(packetId == 126)
            {
                String s1 = inbuffer.getString();
                int k13 = inbuffer.getUword128();
                Widget.widgets[k13].inactiveText = s1;
                if(Widget.widgets[k13].parentId == tab_interfaces[current_tab])
                    aBoolean1153 = true;
                packetId = -1;
                return true;
            }
            if(packetId == 206)
            {
                anInt1287 = inbuffer.getUbyte();
                anInt845 = inbuffer.getUbyte();
                anInt1248 = inbuffer.getUbyte();
                updatetoolbar = true;
                aBoolean1223 = true;
                packetId = -1;
                return true;
            }
            if(packetId == 240)
            {
                if(current_tab == 12)
                    aBoolean1153 = true;
                anInt878 = inbuffer.getWord();
                packetId = -1;
                return true;
            }
            if(packetId == 8)
            {
                int widgetId = inbuffer.getUwordLE128();
                int modelId = inbuffer.getUword();
                Widget.widgets[widgetId].unactiveAnimFetchType = 1;
                Widget.widgets[widgetId].unactiveAnimModelId = modelId;
                packetId = -1;
                return true;
            }
            if(packetId == 122)
            {
                int widgetId = inbuffer.getUwordLE128();
                int color = inbuffer.getUwordLE128();
                int rChannel = color >> 10 & 0x1f;
                int gChannel = color >> 5 & 0x1f;
                int bChannel = color & 0x1f;
                Widget.widgets[widgetId].inactiveTextColor = (rChannel << 19) + (gChannel << 11) + (bChannel << 3);
                packetId = -1;
                return true;
            }
            if(packetId == 53)
            {
                aBoolean1153 = true;
                int widgetId = inbuffer.getUword();
                Widget widget = Widget.widgets[widgetId];
                int len = inbuffer.getUword();
                for(int j22 = 0; j22 < len; j22++)
                {
                    int amount = inbuffer.getUbyte();
                    if(amount == 255)
                        amount = inbuffer.getDwordB();
                    widget.itemIds[j22] = inbuffer.getUwordLE128();
                    widget.itemAmounts[j22] = amount;
                }

                for(int j25 = len; j25 < widget.itemIds.length; j25++)
                {
                    widget.itemIds[j25] = 0;
                    widget.itemAmounts[j25] = 0;
                }

                packetId = -1;
                return true;
            }
            if(packetId == 230)
            {
                int origin = inbuffer.getUword128();
                int widgetId = inbuffer.getUword();
                int rotX = inbuffer.getUword();
                int rotY = inbuffer.getUwordLE128();
                Widget.widgets[widgetId].rotationAngleX = rotX;
                Widget.widgets[widgetId].rotationAngleY = rotY;
                Widget.widgets[widgetId].rotationOrigin = origin;
                packetId = -1;
                return true;
            }
            if(packetId == 221)
            {
                anInt900 = inbuffer.getUbyte();
                aBoolean1153 = true;
                packetId = -1;
                return true;
            }
            if(packetId == 177)
            {
                aBoolean1160 = true;
                normalcam_x = inbuffer.getUbyte();
                normalcam_y = inbuffer.getUbyte();
                normalcam_z = inbuffer.getUword();
                normalcam_speed = inbuffer.getUbyte();
                normalcam_angle = inbuffer.getUbyte();
                if(normalcam_angle >= 100)
                {
                    int k7 = normalcam_x * 128 + 64;
                    int k14 = normalcam_y * 128 + 64;
                    int i20 = calculateTileHeight(k7, k14, currentZ) - normalcam_z;
                    int dy = k7 - camerax;
                    int dy2 = i20 - cameraz;
                    int dx = k14 - cameray;
					/* Distance formula */
                    int d = (int)Math.sqrt(dy * dy + dx * dx);
                    camerapitch$ = (int)(Math.atan2(dy2, d) * 325.94900000000001D) & 0x7ff;
                    camerayaw$ = (int)(Math.atan2(dy, dx) * -325.94900000000001D) & 0x7ff;
                    if(camerapitch$ < 128)
                        camerapitch$ = 128;
                    if(camerapitch$ > 383)
                        camerapitch$ = 383;
                }
                packetId = -1;
                return true;
            }
            if(packetId == 249)
            {
                isMembers = inbuffer.getByte128();
                localPlayerId = inbuffer.getUwordLE128();
                packetId = -1;
                return true;
            }
            if(packetId == 65)
            {
                parseNpcUpdate(inbuffer, packetSize, 973);
                packetId = -1;
                return true;
            }
            if(packetId == 27)
            {
                aBoolean1256 = false;
                anInt1225 = 1;
                aString1004 = "";
                aBoolean1223 = true;
                packetId = -1;
                return true;
            }
            if(packetId == 187)
            {
                aBoolean1256 = false;
                anInt1225 = 2;
                aString1004 = "";
                aBoolean1223 = true;
                packetId = -1;
                return true;
            }
            if(packetId == 97)
            {
                int l7 = inbuffer.getUword();
                method60(l7, (byte)6);
                if(anInt1189 != -1)
                {
                    anInt1189 = -1;
                    aBoolean1153 = true;
                    update_tabs = true;
                }
                if(anInt1276 != -1)
                {
                    anInt1276 = -1;
                    aBoolean1223 = true;
                }
                if(anInt1225 != 0)
                {
                    anInt1225 = 0;
                    aBoolean1223 = true;
                }
                anInt857 = l7;
                aBoolean1149 = false;
                packetId = -1;
                return true;
            }
            if(packetId == 218)
            {
                int i8 = inbuffer.getWordLE128();
                anInt1042 = i8;
                aBoolean1223 = true;
                packetId = -1;
                return true;
            }
            if(packetId == 87)
            {
                int j8 = inbuffer.getUwordLE();
                int l14 = inbuffer.getDwordA();
                configqueue[j8] = l14;
                if(configstates[j8] != l14)
                {
                    configstates[j8] = l14;
                    parseClientVarps(false, j8);
                    aBoolean1153 = true;
                    if(anInt1042 != -1)
                        aBoolean1223 = true;
                }
                packetId = -1;
                return true;
            }
            if(packetId == 36)
            {
                int k8 = inbuffer.getUwordLE();
                byte byte0 = inbuffer.getByte();
                configqueue[k8] = byte0;
                if(configstates[k8] != byte0)
                {
                    configstates[k8] = byte0;
                    parseClientVarps(false, k8);
                    aBoolean1153 = true;
                    if(anInt1042 != -1)
                        aBoolean1223 = true;
                }
                packetId = -1;
                return true;
            }
            if(packetId == 61)
            {
                anInt1055 = inbuffer.getUbyte();
                packetId = -1;
                return true;
            }
            if(packetId == 200)
            {
                int l8 = inbuffer.getUword();
                int i15 = inbuffer.getWord();
                Widget class9_4 = Widget.widgets[l8];
                class9_4.inactiveAnimId = i15;
                if(i15 == -1)
                {
                    class9_4.anInt246 = 0;
                    class9_4.anInt208 = 0;
                }
                packetId = -1;
                return true;
            }
            if(packetId == 219)
            {
                if(anInt1189 != -1)
                {
                    anInt1189 = -1;
                    aBoolean1153 = true;
                    update_tabs = true;
                }
                if(anInt1276 != -1)
                {
                    anInt1276 = -1;
                    aBoolean1223 = true;
                }
                if(anInt1225 != 0)
                {
                    anInt1225 = 0;
                    aBoolean1223 = true;
                }
                anInt857 = -1;
                aBoolean1149 = false;
                packetId = -1;
                return true;
            }
            if(packetId == 34)
            {
                aBoolean1153 = true;
                int i9 = inbuffer.getUword();
                Widget class9_2 = Widget.widgets[i9];
                while(inbuffer.offset < packetSize) 
                {
                    int j20 = inbuffer.getSmartB();
                    int i23 = inbuffer.getUword();
                    int l25 = inbuffer.getUbyte();
                    if(l25 == 255)
                        l25 = inbuffer.getDword();
                    if(j20 >= 0 && j20 < class9_2.itemIds.length)
                    {
                        class9_2.itemIds[j20] = i23;
                        class9_2.itemAmounts[j20] = l25;
                    }
                }
                packetId = -1;
                return true;
            }
            if(packetId == 105 || packetId == 84 || packetId == 147 || packetId == 215 || packetId == 4 || packetId == 117 || packetId == 156 || packetId == 44 || packetId == 160 || packetId == 101 || packetId == 151)
            {
                parseExtraFiles(anInt1119, inbuffer, packetId);
                packetId = -1;
                return true;
            }
            if(packetId == 106)
            {
                current_tab = inbuffer.getUbyteA();
                aBoolean1153 = true;
                update_tabs = true;
                packetId = -1;
                return true;
            }
            if(packetId == 164)
            {
                int j9 = inbuffer.getUwordLE();
                method60(j9, (byte)6);
                if(anInt1189 != -1)
                {
                    anInt1189 = -1;
                    aBoolean1153 = true;
                    update_tabs = true;
                }
                anInt1276 = j9;
                aBoolean1223 = true;
                anInt857 = -1;
                aBoolean1149 = false;
                packetId = -1;
                return true;
            }
			packetId = -1;
            return true;
            //signlink.reportError("T1 - " + packetId + "," + packetSize + " - " + anInt842 + "," + anInt843);
            //killToMainscreen(true);
        }
        catch(IOException _ex)
        {
            connectionLost(-670);
        }
        catch(Exception exception)
        {
            String s2 = "T2 - " + packetId + "," + anInt842 + "," + anInt843 + " - " + packetSize + "," + (paletteX + ((Mob) (localPlayer)).xList[0]) + "," + (paletteY + ((Mob) (localPlayer)).yList[0]) + " - ";
            for(int j15 = 0; j15 < packetSize && j15 < 50; j15++)
                s2 = s2 + inbuffer.payload[j15] + ",";
            exception.printStackTrace();
            Signlink.reportError(s2);
            killToMainscreen(true);
        }
        return true;
    }

    public void method146(byte byte0)
    {
        anInt1265++;
        processPlayers(0, true);
        processNPCs(true, anInt882);
        processPlayers(0, false);
        processNPCs(false, anInt882);
        processProjectiles(-948);
        processGFXs(true);
        if(!aBoolean1160)
        {
            int pitch = cameraPitch;
            if(anInt984 / 256 > pitch)
                pitch = anInt984 / 256;
            if(cameramovements[4] && cameratransvars4[4] + 128 > pitch)
                pitch = cameratransvars4[4] + 128;
            int yaw = cameraYaw + anInt896 & 0x7ff;
            setCurrentCameraVars(pitch, yaw, 600 + pitch * 3, anInt1014, calculateTileHeight( ((Mob) (localPlayer)).fineX, ((Mob) (localPlayer)).fineY, currentZ) - 50, anInt1015);
        }
        int j;
        if(!aBoolean1160)
            j = calculateCameraHeight1();
        else
            j = calculateCameraHeight2();
        int l = camerax;
        int i1 = cameraz;
        int j1 = cameray;
        int k1 = camerapitch$;
        int l1 = camerayaw$;
        for(int i2 = 0; i2 < 5; i2++)
            if(cameramovements[i2])
            {
                int j2 = (int)((Math.random() * (double)(cameratransvars[i2] * 2 + 1) - (double)cameratransvars[i2]) + Math.sin((double)cameratransvars2[i2] * ((double)cameratransvars3[i2] / 100D)) * (double)cameratransvars4[i2]);
                if(i2 == 0)
                    camerax += j2;
                if(i2 == 1)
                    cameraz += j2;
                if(i2 == 2)
                    cameray += j2;
                if(i2 == 3)
                    camerayaw$ = camerayaw$ + j2 & 0x7ff;
                if(i2 == 4)
                {
                    camerapitch$ += j2;
                    if(camerapitch$ < 128)
                        camerapitch$ = 128;
                    if(camerapitch$ > 383)
                        camerapitch$ = 383;
                }
            }

        int k2 = TriangleRasterizer.unpackcounter;
        Model.aBoolean1684 = true;
        if(byte0 != 1)
        {
            return;
        } else
        {
            Model.anInt1687 = 0;
            Model.anInt1685 = super.newMouseX - 4;
            Model.anInt1686 = super.newMouseY - 4;
            BasicRasterizer.resetPixelBuffer();
            pallet.setDimensions(camerax, cameray, camerayaw$, cameraz, j, camerapitch$, false);
            pallet.method288((byte)104);
            updateMobGraphics(anInt898);
            drawMarkerOnLocation(-252);
            method37(854, k2);
            method112(8);
            toplefttext_imagefetcher.updateGraphics(4, 23680, super.appletGraphics, 4);
            camerax = l;
            cameraz = i1;
            cameray = j1;
            camerapitch$ = k1;
            camerayaw$ = l1;
            return;
        }
    }

    public void method147(int i)
    {
        gameBuffer.putPacket(130);
        if(anInt1189 != -1)
        {
            anInt1189 = -1;
            aBoolean1153 = true;
            aBoolean1149 = false;
            update_tabs = true;
        }
        if(anInt1276 != -1)
        {
            anInt1276 = -1;
            aBoolean1223 = true;
            aBoolean1149 = false;
        }
        anInt857 = -1;
        if(i <= 0)
            gameBuffer.put(13);
    }

    public Main()
    {
        distancestrength = new int[104][104];
        anIntArray826 = new int[200];
        grounditems = new Deque[4][104][104];
        aBoolean830 = true;
        runflamecycle = false;
        aClass30_Sub2_Sub2_834 = new ByteBuffer(new byte[5000]);
        npcs = new Npc[16384];
        localNpcIds = new int[16384];
        anInt838 = 9;
        eRmQueue = new int[1000];
        loginBuffer = ByteBuffer.createBuffer(1);
        aBoolean848 = true;
        anInt857 = -1;
        skillExperience = new int[SkillConstants.amt_skills];
        useWebJaggrab = true;
        cameratransvars = new int[5];
        anInt874 = -1;
        anInt875 = -680;
        cameramovements = new boolean[5];
        anInt877 = 1834;
        runclient = false;
        aString881 = "";
        anInt882 = -30815;
        anInt883 = 533;
        localPlayerId = -1;
        aBoolean885 = false;
        aString887 = "";
        maxplayers = 2048;
        localPlayerIndex = 2047;
        playerArray = new Player[maxplayers];
        addedPlayers = new int[maxplayers];
        pFlagUpdateList = new int[maxplayers];
        appearanceBuffers = new ByteBuffer[maxplayers];
        anInt897 = 1;
        anIntArrayArray901 = new int[104][104];
        anInt902 = 0x766654;
        aByteArray912 = new byte[16384];
        aByte920 = 14;
        anInt921 = 732;
        skillLevels = new int[SkillConstants.amt_skills];
        aByte923 = 25;
        ignore_hashes = new long[100];
        aBoolean926 = false;
        anInt927 = 0x332d25;
        cameratransvars3 = new int[5];
        anIntArrayArray929 = new int[104][104];
        CRC = new CRC32();
        anInt939 = 748;
        msgtype_stack = new int[100];
        msgprefix_stack = new String[100];
        msgbody_stack = new String[100];
        sideicons = new IndexedColorSprite[13];
        focusPacketToggle = true;
        friend_hashes = new long[200];
        anInt956 = -1;
        aBoolean962 = false;
        spriteX = -1;
        spriteY = -1;
        anIntArray968 = new int[33];
        anIntArray969 = new int[256];
        fileIndexes = new FileIndex[5];
        configstates = new int[2000];
        aBoolean972 = false;
        aByte973 = -74;
        anInt975 = 50;
        anIntArray976 = new int[anInt975];
        anIntArray977 = new int[anInt975];
        anIntArray978 = new int[anInt975];
        anIntArray979 = new int[anInt975];
        anIntArray980 = new int[anInt975];
        anIntArray981 = new int[anInt975];
        anIntArray982 = new int[anInt975];
        aStringArray983 = new String[anInt975];
        anInt985 = -1;
        hitmarks = new DirectColorSprite[20];
        characterColorIds = new int[5];
        aBoolean991 = false;
        aBoolean994 = false;
        anInt1002 = 0x23201b;
        aString1004 = "";
        aByte1012 = 24;
        aClass19_1013 = new Deque();
        camerapacket_write = false;
        anInt1018 = -1;
        cameratransvars2 = new int[5];
        aBoolean1031 = false;
        mapfunction = new DirectColorSprite[100];
        anInt1042 = -1;
        aBoolean1043 = false;
        anIntArray1044 = new int[SkillConstants.amt_skills];
        configqueue = new int[2000];
        aBoolean1047 = true;
        anInt1050 = 111;
        anIntArray1052 = new int[151];
        anInt1054 = -1;
        gfxs_storage = new Deque();
        anIntArray1057 = new int[33];
        anInt1058 = 24869;
        aClass9_1059 = new Widget();
        mapscene = new IndexedColorSprite[100];
        anInt1063 = 0x4d4233;
        characterModelIds = new int[7];
        anIntArray1072 = new int[1000];
        anIntArray1073 = new int[1000];
        aBoolean1080 = false;
        anInt1081 = -733;
        friendusernames = new String[200];
        inbuffer = ByteBuffer.createBuffer(1);
        jaggrabArchiveCrcs = new int[9];
        interfacestack_a = new int[500];
        interfacestack_b = new int[500];
        interfaceopcodestack = new int[500];
        interfacestack_c = new int[500];
        headicons = new DirectColorSprite[20];
        update_tabs = false;
        anInt1105 = 519;
        aBoolean1106 = false;
        anInt1116 = 445;
        anInt1118 = -29508;
        anInt1119 = -77;
        aString1121 = "";
        aStringArray1127 = new String[5];
        aBooleanArray1128 = new boolean[5];
        custompalette = new int[4][13][13];
        anInt1132 = 2;
        anInt1135 = -12499;
        mapfunctionstack = new DirectColorSprite[1000];
        isLoadedLandscapes = false;
        aBoolean1149 = false;
        cross_sprites = new DirectColorSprite[8];
        aBoolean1151 = true;
        aBoolean1153 = false;
        isOnlineGame = false;
        aBoolean1158 = false;
        aBoolean1159 = false;
        aBoolean1160 = false;
        anInt1171 = 1;
        username = "";
        password = "";
        aBoolean1176 = false;
        anInt1178 = -1;
        aClass19_1179 = new Deque();
        cameraPitch = 128;
        anInt1189 = -1;
        gameBuffer = ByteBuffer.createBuffer(1);
        aByte1194 = 5;
        interfacestringstack = new String[500];
        cameratransvars4 = new int[5];
        aBoolean1206 = true;
        anIntArray1207 = new int[50];
        anInt1210 = 2;
        anInt1211 = 78;
        aString1212 = "";
        aByte1217 = 6;
        anInt1218 = -589;
        mod_icons = new IndexedColorSprite[2];
        current_tab = 3;
        aBoolean1223 = false;
        aBoolean1228 = true;
        anIntArray1229 = new int[151];
        planeFlags = new PlaneFlags[4];
        updatetoolbar = false;
        anIntArray1240 = new int[100];
        anIntArray1241 = new int[50];
        aBoolean1242 = false;
        anIntArray1250 = new int[50];
        aBoolean1252 = false;
        paintRequested = false;
        aBoolean1256 = false;
        loginMessage0 = "";
        loginMessage1 = "";
        aByte1274 = -13;
        anInt1276 = -1;
        aBoolean1277 = true;
        anInt1279 = 2;
        walkingstepsx = new int[4000];
        walkingstepsy = new int[4000];
        anInt1289 = -1;
    }

    public static boolean JAGGRAB_DISABLED;
    public int amt_ignorehashes;
    public static byte aByte823 = 77;
    public long timestamp;
    public int distancestrength[][];
    public int anIntArray826[];
    public Deque grounditems[][][];
    public int anIntArray828[];
    public int anIntArray829[];
    public boolean aBoolean830;
    public volatile boolean runflamecycle;
    public Socket jaggrabSocket;
    public int titlescreen_tab;
    public ByteBuffer aClass30_Sub2_Sub2_834;
    public Npc npcs[];
    public int anInt836;
    public int localNpcIds[];
    public int anInt838;
    public int eRmQueuePosition;
    public int eRmQueue[];
    public int anInt841;
    public int anInt842;
    public int anInt843;
    public String aString844;
    public int anInt845;
    public static int anInt846;
    public ByteBuffer loginBuffer;
    public boolean aBoolean848;
    public static int anInt849;
    public int anIntArray850[];
    public int anIntArray851[];
    public int anIntArray852[];
    public int anIntArray853[];
    public static int anInt854;
    public int markertype;
    public static BigInteger modulus = new BigInteger("94110576314610994718998081678112721707302768097953573382594472711586892647324048387895220267278548668404446455854984542894191923190062621913004110871090621766911511417077647755530435334378014024591274020906279816717181801750245525684733633557077844371661010447566347245316848850605330431469711797488557035631");
    public int anInt857;
    public int camerax;
    public int cameraz;
    public int cameray;
    public int camerapitch$;
    public int camerayaw$;
    public int rights;
    public int skillExperience[];
    public IndexedColorSprite redstone1_3;
    public IndexedColorSprite redstone2_3;
    public IndexedColorSprite redstone3_2;
    public IndexedColorSprite redstone1_4;
    public IndexedColorSprite redstone2_4;
    public DirectColorSprite mapmarker0;
    public DirectColorSprite mapmarker1;
    public boolean useWebJaggrab;
    public int cameratransvars[];
    public int anInt874;
    public int anInt875;
    public boolean cameramovements[];
    public int anInt877;
    public int anInt878;
    public Monitor monitor;
    public volatile boolean runclient;
    public String aString881;
    public int anInt882;
    public int anInt883;
    public int localPlayerId;
    public boolean aBoolean885;
    public int currentWidgetId;
    public String aString887;
    public int maxplayers;
    public int localPlayerIndex;
    public Player playerArray[];
    public int playerOffset;
    public int addedPlayers[];
    public int amtplayerupdatestack;
    public int pFlagUpdateList[];
    public ByteBuffer appearanceBuffers[];
    public int anInt896;
    public int anInt897;
    public int anInt898;
    public int amt_friendhashes;
    public int anInt900;
    public int anIntArrayArray901[][];
    public int anInt902;
    public ImageFetcher backleft1_imagefetcher;
    public ImageFetcher backleft2_imagefetcher;
    public ImageFetcher backright1_imagefetcher;
    public ImageFetcher backright2_imagefetcher;
    public ImageFetcher backtop1_imagefetcher;
    public ImageFetcher backvmid1_imagefetcher;
    public ImageFetcher backvmid2_imagefetcher;
    public ImageFetcher backvmid3_imagefetcher;
    public ImageFetcher backhmid2_imagefetcher;
    public byte aByteArray912[];
    public int anInt913;
    public int anInt914;
    public int anInt915;
    public int anInt916;
    public int anInt917;
    public int currentZ;
    public static boolean aBoolean919 = true;
    public byte aByte920;
    public int anInt921;
    public int skillLevels[];
    public byte aByte923;
    public static int anInt924;
    public long ignore_hashes[];
    public boolean aBoolean926;
    public int anInt927;
    public int cameratransvars3[];
    public int anIntArrayArray929[][];
    public CRC32 CRC;
    public DirectColorSprite aClass30_Sub2_Sub1_Sub1_931;
    public DirectColorSprite aClass30_Sub2_Sub1_Sub1_932;
    public int pmarker_id;
    public int markerloc_x;
    public int markerloc_y;
    public int markerheight;
    public int markeroffset_x;
    public int markeroffset_y;
    public int anInt939;
    public static int anInt940;
    public static int anInt941;
    public int msgtype_stack[];
    public String msgprefix_stack[];
    public String msgbody_stack[];
    public int anInt945;
    public Palette pallet;
    public IndexedColorSprite sideicons[];
    public int clickarea;
    public int anInt949;
    public int anInt950;
    public int anInt951;
    public int anInt952;
    public long aLong953;
    public boolean focusPacketToggle;
    public long friend_hashes[];
    public int anInt956;
    public static int nodeid = 10;
    public static int portOffset;
    public static boolean members = true;
    public static boolean lowMemory;
    public int anInt961;
    public volatile boolean aBoolean962;
    public int spriteX;
    public int spriteY;
    public int anIntArray965[] = {
        0xffff00, 0xff0000, 65280, 65535, 0xff00ff, 0xffffff
    };
    public IndexedColorSprite aClass30_Sub2_Sub1_Sub2_966;
    public IndexedColorSprite aClass30_Sub2_Sub1_Sub2_967;
    public int anIntArray968[];
    public int anIntArray969[];
    public FileIndex fileIndexes[];
    public int configstates[];
    public boolean aBoolean972;
    public byte aByte973;
    public int anInt974;
    public int anInt975;
    public int anIntArray976[];
    public int anIntArray977[];
    public int anIntArray978[];
    public int anIntArray979[];
    public int anIntArray980[];
    public int anIntArray981[];
    public int anIntArray982[];
    public String aStringArray983[];
    public int anInt984;
    public int anInt985;
    public static int anInt986;
    public DirectColorSprite hitmarks[];
    public int anInt988;
    public int anInt989;
    public int characterColorIds[];
    public boolean aBoolean991;
    public int anInt992;
    public static boolean aBoolean993;
    public boolean aBoolean994;
    public int normalcam_x;
    public int normalcam_y;
    public int normalcam_z;
    public int normalcam_speed;
    public int normalcam_angle;
    public IsaacCipher packetencryption;
    public DirectColorSprite mapedge;
    public int anInt1002;
    public static final int anIntArrayArray1003[][] = {
        {
            6798, 107, 10283, 16, 4797, 7744, 5799, 4634, 33697, 22433, 
            2983, 54193
        }, {
            8741, 12, 64030, 43162, 7735, 8404, 1701, 38430, 24094, 10153, 
            56621, 4783, 1341, 16578, 35003, 25239
        }, {
            25238, 8742, 12, 64030, 43162, 7735, 8404, 1701, 38430, 24094, 
            10153, 56621, 4783, 1341, 16578, 35003
        }, {
            4626, 11146, 6439, 12, 4758, 10270
        }, {
            4550, 4537, 5681, 5673, 5790, 6806, 8076, 4574
        }
    };
    public String aString1004;
    public static int anInt1005;
    public int pastlaginamountdays;
    public int packetSize;
    public int packetId;
    public int anInt1009;
    public int anInt1010;
    public int anInt1011;
    public byte aByte1012;
    public Deque aClass19_1013;
    public int anInt1014;
    public int anInt1015;
    public int camerapacket_delay;
    public boolean camerapacket_write;
    public int anInt1018;
    public static int XP_TABLE[];
    public int anInt1020;
    public int anInt1021;
    public int anInt1022;
    public int landscape_stage;
    public IndexedColorSprite scrollbar0;
    public IndexedColorSprite scrollbar1;
    public int anInt1026;
    public IndexedColorSprite backbase1;
    public IndexedColorSprite backbase2;
    public IndexedColorSprite backmid1;
    public int cameratransvars2[];
    public boolean aBoolean1031;
    public static BigInteger publicKey = new BigInteger("65537");
    public DirectColorSprite mapfunction[];
    public int paletteX;
    public int paletteY;
    public int anInt1036;
    public int anInt1037;
    public int loginAttempts;
    public int anInt1039;
    public int anInt1040;
    public int anInt1041;
    public int anInt1042;
    public boolean aBoolean1043;
    public int anIntArray1044[];
    public int configqueue[];
    public int isMembers;
    public boolean aBoolean1047;
    public int anInt1048;
    public String aString1049;
    public int anInt1050;
    public static int anInt1051;
    public int anIntArray1052[];
    public ArchivePackage titlescreen_archive;
    public int anInt1054;
    public int anInt1055;
    public Deque gfxs_storage;
    public int anIntArray1057[];
    public int anInt1058;
    public Widget aClass9_1059;
    public IndexedColorSprite mapscene[];
    public static int drawCycle;
    public int anInt1062;
    public int anInt1063;
    public int anInt1064;
    public int characterModelIds[];
    public int moveitem_endslot;
    public int anInt1067;
    public OndemandHandler ondemandhandler;
    public int chunkx_;
    public int chunky_;
    public int mapfunctionstackpos;
    public int anIntArray1072[];
    public int anIntArray1073[];
    public DirectColorSprite grounditem_mapdotsprite;
    public DirectColorSprite mapdots1;
    public DirectColorSprite mapdots2;
    public DirectColorSprite mapdots3;
    public DirectColorSprite mapdots4;
    public int anInt1079;
    public boolean aBoolean1080;
    public int anInt1081;
    public String friendusernames[];
    public ByteBuffer inbuffer;
    public int moveitem_frameid;
    public int moveitem_startslot;
    public int anInt1086;
    public int anInt1087;
    public int anInt1088;
    public int anInt1089;
    public int jaggrabArchiveCrcs[];
    public int interfacestack_a[];
    public int interfacestack_b[];
    public int interfaceopcodestack[];
    public int interfacestack_c[];
    public DirectColorSprite headicons[];
    public static int anInt1096 = -192;
    public static int anInt1097;
    public int spincam_x;
    public int spincam_y;
    public int spincam_z;
    public int spincam_speed;
    public int spincam_angle;
    public boolean update_tabs;
    public int anInt1104;
    public int anInt1105;
    public boolean aBoolean1106;
    public ImageFetcher logo_imagefetcher;
    public ImageFetcher bottomleftmid_imagefetcher;
    public ImageFetcher aClass15_1109;
    public ImageFetcher titletopleft_imagefetcher;
    public ImageFetcher titletopright_imagefetcher;
    public ImageFetcher aClass15_1112;
    public ImageFetcher aClass15_1113;
    public ImageFetcher aClass15_1114;
    public ImageFetcher aClass15_1115;
    public int anInt1116;
    public static int anInt1117;
    public int anInt1118;
    public int anInt1119;
    public int anInt1120;
    public String aString1121;
    public DirectColorSprite compass;
    public ImageFetcher toolbartext_imagefetcher;
    public ImageFetcher aClass15_1124;
    public ImageFetcher aClass15_1125;
    public static Player localPlayer;
    public String aStringArray1127[];
    public boolean aBooleanArray1128[];
    public int custompalette[][][];
    public int tab_interfaces[] = {
        -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 
        -1, -1, -1, -1, -1
    };
    public int anInt1131;
    public int anInt1132;
    public int anInt1133;
    public static int anInt1134;
    public int anInt1135;
    public int anInt1136;
    public int anInt1137;
    public int anInt1138;
    public String aString1139;
    public DirectColorSprite mapfunctionstack[];
    public boolean isLoadedLandscapes;
    public static int anInt1142;
    public IndexedColorSprite redstone1;
    public IndexedColorSprite redstone2;
    public IndexedColorSprite redstone3;
    public IndexedColorSprite redstone1_2;
    public IndexedColorSprite redstone2_2;
    public int anInt1148;
    public boolean aBoolean1149;
    public DirectColorSprite cross_sprites[];
    public boolean aBoolean1151;
    public IndexedColorSprite titlescreen_sprites[];
    public boolean aBoolean1153;
    public int anInt1154;
    public static int anInt1155;
    public static boolean drawfps;
    public boolean isOnlineGame;
    public boolean aBoolean1158;
    public boolean aBoolean1159;
    public boolean aBoolean1160;
    public static int loopCycle;
    public static String passchars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!\"\243$%^&*()-_=+[{]};:'@#~,<.>/?\\| ";
    public ImageFetcher aClass15_1163;
    public ImageFetcher aClass15_1164;
    public ImageFetcher toplefttext_imagefetcher;
    public ImageFetcher chat_imagefetcher;
    public int anInt1167;
    public BufferedConnection bufferedConnection;
    public int anInt1169;
    public int anInt1170;
    public int anInt1171;
    public long aLong1172;
    public String username;
    public String password;
    public static int anInt1175;
    public boolean aBoolean1176;
    public final int OBJECT_TYPES[] = {
        0, 0, 0, 0, 1, 1, 1, 1, 1, 2, 
        2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 
        2, 2, 3
    };
    public int anInt1178;
    public Deque aClass19_1179;
    public int anIntArray1180[];
    public int anIntArray1181[];
    public int anIntArray1182[];
    public byte tileSrcs[][];
    public int cameraPitch;
    public int cameraYaw;
    public int camerayawrate;
    public int camerayrate;
    public static int anInt1188;
    public int anInt1189;
    public int anIntArray1190[];
    public int anIntArray1191[];
    public ByteBuffer gameBuffer;
    public int anInt1193;
    public byte aByte1194;
    public int anInt1195;
    public IndexedColorSprite invback;
    public IndexedColorSprite mapback;
    public IndexedColorSprite chatback;
    public String interfacestringstack[];
    public static byte aByte1200 = 9;
    public DirectColorSprite aClass30_Sub2_Sub1_Sub1_1201;
    public DirectColorSprite aClass30_Sub2_Sub1_Sub1_1202;
    public int cameratransvars4[];
    public static final int anIntArray1204[] = {
        9104, 10275, 7595, 3610, 7975, 8526, 918, 38802, 24466, 10145, 
        58654, 5027, 1457, 16565, 34991, 25486
    };
    public static boolean flagged;
    public boolean aBoolean1206;
    public int anIntArray1207[];
    public int flameCycle;
    public int anInt1209;
    public int anInt1210;
    public int anInt1211;
    public String aString1212;
    public int anInt1213;
    public int tileHeightmap[][][];
    public long ssk;
    public int userpass_swtch;
    public byte aByte1217;
    public int anInt1218;
    public IndexedColorSprite mod_icons[];
    public long lastPressedTimestamp;
    public int current_tab;
    public int nmarker_id;
    public boolean aBoolean1223;
    public static boolean aBoolean1224 = true;
    public int anInt1225;
    public static int anInt1226;
    public int anInt1227;
    public boolean aBoolean1228;
    public int anIntArray1229[];
    public PlaneFlags planeFlags[];
    public static boolean aBoolean1231;
    public static int BIT_MASKS[];
    public boolean updatetoolbar;
    public int regionHashes[];
    public int anIntArray1235[];
    public int anIntArray1236[];
    public int anInt1237;
    public int anInt1238;
    public final int anInt1239 = 100;
    public int anIntArray1240[];
    public int anIntArray1241[];
    public boolean aBoolean1242;
    public int anInt1243;
    public int anInt1244;
    public int anInt1245;
    public int anInt1246;
    public byte regionSrcs[][];
    public int anInt1248;
    public int anInt1249;
    public int anIntArray1250[];
    public int ontutorial_island;
    public boolean aBoolean1252;
    public int anInt1253;
    public int anInt1254;
    public boolean paintRequested;
    public boolean aBoolean1256;
    public int anInt1257;
    public byte tileFlags[][][];
    public int anInt1259;
    public static int anInt1260;
    public int anInt1261;
    public int anInt1262;
    public DirectColorSprite aClass30_Sub2_Sub1_Sub1_1263;
    public int anInt1264;
    public int anInt1265;
    public String loginMessage0;
    public String loginMessage1;
    public int anInt1268;
    public int anInt1269;
    public BitmapFont p11Font;
    public BitmapFont p12Font;
    public BitmapFont b12Font;
    public BitmapFont q8Font;
    public byte aByte1274;
    public int anInt1275;
    public int anInt1276;
    public boolean aBoolean1277;
    public int anInt1278;
    public int anInt1279;
    public int walkingstepsx[];
    public int walkingstepsy[];
    public int anInt1282;
    public int anInt1283;
    public int anInt1284;
    public int anInt1285;
    public String usedItemName;
    public int anInt1287;
    public static int stepCounter;
    public int anInt1289;
    public static int anInt1290;

    static {
        XP_TABLE = new int[99];
        int i = 0;
        for(int j = 0; j < 99; j++) {
            int l = j + 1;
            int i1 = (int)((double) l + 300D * Math.pow(2D, (double) l / 7D));
            i += i1;
            XP_TABLE[j] = i / 4;
        }
        BIT_MASKS = new int[32];
        i = 2;
        for(int k = 0; k < 32; k++)
        {
            BIT_MASKS[k] = i - 1;
            i += i;
        }
    }
}
