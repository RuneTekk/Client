// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

import java.awt.*;

public class ApplicationFrame extends Frame {

    public ApplicationFrame(ApplicationApplet applet, int width, int height) {
        applicationApplet = applet;
        setTitle("RuneTekk");
        setResizable(false);
        show();
        toFront();
        resize(width + 8, height + 28);
    }

    public Graphics getGraphics() {
        Graphics g = super.getGraphics();
        g.translate(4, 24);
        return g;
    }

    public void update(Graphics g) {
        applicationApplet.update(g);
    }

    public void paint(Graphics g) {
        applicationApplet.paint(g);
    }

    public ApplicationApplet applicationApplet;
}
