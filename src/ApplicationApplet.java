// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

import java.applet.Applet;
import java.awt.*;
import java.awt.event.*;
import java.io.PrintStream;
import sign.Signlink;

public class ApplicationApplet extends Applet implements Runnable, MouseListener, MouseMotionListener, KeyListener, FocusListener, WindowListener {

    public void frameInitialize(int h, boolean junk, int w) {
        applet_width = w;
        applet_height = h;
        frame = new ApplicationFrame(this, applet_width, applet_height);
        graphics = getDrawableComponent().getGraphics();
        aClass15_13 = new ImageFetcher(applet_width, applet_height, getDrawableComponent(), 0);
        createThread(this, 1);
    }

    public void webInitialize(int h, boolean junk, int w) {
        applet_width = w;
        applet_height = h;
        graphics = getDrawableComponent().getGraphics();
        aClass15_13 = new ImageFetcher(applet_width, applet_height, getDrawableComponent(), 0);
        createThread(this, 1);
    }

    public void run() {
        getDrawableComponent().addMouseListener(this);
        getDrawableComponent().addMouseMotionListener(this);
        getDrawableComponent().addKeyListener(this);
        getDrawableComponent().addFocusListener(this);
        if(frame != null)
            frame.addWindowListener(this);
        drawLoadingBar(0, (byte) 4, "Loading...");
        loadClient();
        int opos = 0;
        int ratio = 256;
        int del = 1;
        int count = 0;
        int intex = 0;
        for(int k1 = 0; k1 < 10; k1++)
            times[k1] = System.currentTimeMillis();
        long l = System.currentTimeMillis();
        while(anInt4 >= 0) {
            if(anInt4 > 0) {
                anInt4--;
                if(anInt4 == 0) {
                    method3(4747);
                    return;
                }
            }
            int r = ratio;
            int d = del;
            ratio = 300;
            del = 1;
            long l1 = System.currentTimeMillis();
            if(times[opos] == 0L) {
                ratio = r;
                del = d;
            } else
            if(l1 > times[opos])
                ratio = (int)((long) (2560 * deltime) / (l1 - times[opos]));
            if(ratio < 25)
                ratio = 25;
            if(ratio > 256)
            {
                ratio = 256;
                del = (int)((long) deltime - (l1 - times[opos]) / 10L);
            }
            if(del > deltime)
                del = deltime;
            times[opos] = l1;
            opos = (opos + 1) % 10;
            if(del > 1)
            {
                for(int k2 = 0; k2 < 10; k2++)
                    if(times[k2] != 0L)
                        times[k2] += del;

            }
            if(del < mindel)
                del = mindel;
            try
            {
                Thread.sleep(del);
            }
            catch(InterruptedException _ex)
            {
                intex++;
            }
            for(; count < 256; count += ratio)
            {
                anInt26 = anInt22;
                curpressed_x = pressed_x;
                curpressed_y = pressed_y;
                curpressed_t = pressed_t;
                anInt22 = 0;
                handleLoopCycle();
                kread_offset = kwrite_offset;
            }
            count &= 0xff;
            if(deltime > 0)
                fps = (1000 * ratio) / (deltime * 256);
            handleDrawCycle(0);
            if(aBoolean9)
            {
                System.out.println("ntime:" + l1);
                for(int l2 = 0; l2 < 10; l2++)
                {
                    int i3 = ((opos - l2 - 1) + 20) % 10;
                    System.out.println("otim" + i3 + ":" + times[i3]);
                }

                System.out.println("fps:" + fps + " ratio:" + ratio + " count:" + count);
                System.out.println("del:" + del + " deltime:" + deltime + " mindel:" + mindel);
                System.out.println("intex:" + intex + " opos:" + opos);
                aBoolean9 = false;
                intex = 0;
            }
        }
        if(anInt4 == -1)
            method3(4747);
    }

    public void method3(int i) {
        anInt4 = -2;
        destroy(493);
        if(i != 4747)
            return;
        if(frame != null) {
            try
            {
                Thread.sleep(1000L);
            }
            catch(Exception _ex) { }
            try
            {
                System.exit(0);
                return;
            }
            catch(Throwable _ex) { }
        }
    }

    public void setDeltime(boolean junk, int i) {
        deltime = 1000 / i;
    }

    public void start()
    {
        if(anInt4 >= 0)
            anInt4 = 0;
    }

    public void stop()
    {
        if(anInt4 >= 0)
            anInt4 = 4000 / deltime;
    }

    public void destroy() {
        anInt4 = -1;
        try {
            Thread.sleep(5000L);
        } catch(Exception _ex) { 
		}
        if(anInt4 == -1)
            method3(4747);
    }

    public void update(Graphics g)
    {
        if(graphics == null)
            graphics = g;
        aBoolean16 = true;
        method10((byte) 1);
    }

    public void paint(Graphics g)
    {
        if(graphics == null)
            graphics = g;
        aBoolean16 = true;
        method10((byte)1);
    }

    public void mousePressed(MouseEvent mouseevent)
    {
        int i = mouseevent.getX();
        int j = mouseevent.getY();
        if(frame != null) {
            i -= 4;
            j -= 22;
        }
        idle_counter = 0;
        pressed_x = i;
        pressed_y = j;
        pressed_t = System.currentTimeMillis();
        if(mouseevent.isMetaDown()) {
            anInt22 = 2;
            anInt19 = 2;
            return;
        } else {
            anInt22 = 1;
            anInt19 = 1;
            return;
        }
    }

    public void mouseReleased(MouseEvent mouseevent)
    {
        idle_counter = 0;
        anInt19 = 0;
    }

    public void mouseClicked(MouseEvent mouseevent)
    {
    }

    public void mouseEntered(MouseEvent mouseevent)
    {
    }

    public void mouseExited(MouseEvent mouseevent)
    {
        idle_counter = 0;
        mouse_x = -1;
        mouse_y = -1;
    }

    public void mouseDragged(MouseEvent mouseevent) {
        int i = mouseevent.getX();
        int j = mouseevent.getY();
        if(frame != null)
        {
            i -= 4;
            j -= 22;
        }
        idle_counter = 0;
        mouse_x = i;
        mouse_y = j;
    }

    public void mouseMoved(MouseEvent mouseevent) {
        int i = mouseevent.getX();
        int j = mouseevent.getY();
        if(frame != null)
        {
            i -= 4;
            j -= 22;
        }
        idle_counter = 0;
        mouse_x = i;
        mouse_y = j;
    }

    public void keyPressed(KeyEvent keyevent) {
        idle_counter = 0;
        int i = keyevent.getKeyCode();
        int j = keyevent.getKeyChar();
        if(j < 30)
            j = 0;
        /* Left */
        if(i == 37)
            j = 1;
        if(i == 39)
            j = 2;
        if(i == 38)
            j = 3;
        if(i == 40)
            j = 4;
        if(i == 17)
            j = 5;
        if(i == 8)
            j = 8;
        if(i == 127)
            j = 8;
        if(i == 9)
            j = 9;
        if(i == 10)
            j = 10;
        if(i >= 112 && i <= 123)
            j = (1008 + i) - 112;
        if(i == 36)
            j = 1000;
        if(i == 35)
            j = 1001;
        if(i == 33)
            j = 1002;
        if(i == 34)
            j = 1003;
        if(j > 0 && j < 128)
            active_keycodes[j] = 1;
        if(j > 4) {
            keyqueue[kwrite_offset] = j;
            kwrite_offset = kwrite_offset + 1 & 0x7f;
        }
    }

    public void keyReleased(KeyEvent keyevent) {
        idle_counter = 0;
        int i = keyevent.getKeyCode();
        char c = keyevent.getKeyChar();
        if(c < '\036')
            c = '\0';
        if(i == 37)
            c = '\001';
        if(i == 39)
            c = '\002';
        if(i == 38)
            c = '\003';
        if(i == 40)
            c = '\004';
        if(i == 17)
            c = '\005';
        if(i == 8)
            c = '\b';
        if(i == 127)
            c = '\b';
        if(i == 9)
            c = '\t';
        if(i == 10)
            c = '\n';
        if(c > 0 && c < '\200')
            active_keycodes[c] = 0;
    }

    public void keyTyped(KeyEvent keyevent) {
    }

    public int getKey(int junk) {
        int k = -1;
        if(kwrite_offset != kread_offset) {
            k = keyqueue[kread_offset];
            kread_offset = kread_offset + 1 & 0x7f;
        }
        return k;
    }

    public void focusGained(FocusEvent focusevent) {
        aBoolean17 = true;
        aBoolean16 = true;
        method10((byte) 1);
    }

    public void focusLost(FocusEvent focusevent) {
        aBoolean17 = false;
        for(int i = 0; i < 128; i++)
            active_keycodes[i] = 0;
    }

    public void windowActivated(WindowEvent windowevent) {
    }

    public void windowClosed(WindowEvent windowevent) {
    }

    public void windowClosing(WindowEvent windowevent) {
        destroy();
    }

    public void windowDeactivated(WindowEvent windowevent) {
    }

    public void windowDeiconified(WindowEvent windowevent) {
    }

    public void windowIconified(WindowEvent windowevent) {
    }

    public void windowOpened(WindowEvent windowevent) {
    }

    public void loadClient() {
    }

    public void handleLoopCycle() {
    }

    public void destroy(int junk) {
    }

    public void handleDrawCycle(int junk) {
    }

    public void method10(byte junk) {
    }

    public Component getDrawableComponent() {
        if(frame != null)
            return frame;
        else
            return this;
    }

    public void createThread(Runnable runnable, int i) {
        Thread thread = new Thread(runnable);
        thread.start();
        thread.setPriority(i);
    }

    public void drawLoadingBar(int i, byte junk, String s) {
        while(graphics == null) {
            graphics = getDrawableComponent().getGraphics();
            try {
                getDrawableComponent().repaint();
            } catch(Exception _ex) { 
			}
            try {
                Thread.sleep(1000L);
            } catch(Exception _ex) { 
			}
        }
        Font font = new Font("Helvetica", 1, 13);
        FontMetrics fontmetrics = getDrawableComponent().getFontMetrics(font);
        Font font1 = new Font("Helvetica", 0, 13);
        getDrawableComponent().getFontMetrics(font1);
        if(aBoolean16) {
            graphics.setColor(Color.black);
            graphics.fillRect(0, 0, applet_width, applet_height);
            aBoolean16 = false;
        }
        Color color = new Color(140, 17, 17);
        int j = applet_height / 2 - 18;
        graphics.setColor(color);
        graphics.drawRect(applet_width / 2 - 152, j, 304, 34);
        graphics.fillRect(applet_width / 2 - 150, j + 2, i * 3, 30);
        graphics.setColor(Color.black);
        graphics.fillRect((applet_width / 2 - 150) + i * 3, j + 2, 300 - i * 3, 30);
        graphics.setFont(font);
        graphics.setColor(Color.white);
        graphics.drawString(s, (applet_width - fontmetrics.stringWidth(s)) / 2, j + 22);
    }

    public ApplicationApplet() {
        deltime = 20;
        mindel = 1;
        times = new long[10];
        aBoolean9 = false;
        aBoolean16 = true;
        aBoolean17 = true;
        active_keycodes = new int[128];
        keyqueue = new int[128];
    }

    public int anInt4;
    public int deltime;
    public int mindel;
    public long times[];
    public int fps;
    public boolean aBoolean9;
    public int applet_width;
    public int applet_height;
    public Graphics graphics;
    public ImageFetcher aClass15_13;
    public ApplicationFrame frame;
    public boolean aBoolean16;
    public boolean aBoolean17;
    public int idle_counter;
    public int anInt19;
    public int mouse_x;
    public int mouse_y;
    public int anInt22;
    public int pressed_x;
    public int pressed_y;
    public long pressed_t;
    public int anInt26;
    public int curpressed_x;
    public int curpressed_y;
    public long curpressed_t;
    public int active_keycodes[];
    public int keyqueue[];
    public int kread_offset;
    public int kwrite_offset;
    public static int anInt34;
}
