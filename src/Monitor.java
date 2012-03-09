// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

import sign.Signlink;

public final class Monitor implements Runnable {

    public void run() {
        while(running) {
            synchronized(sync) {
                if(position < 500) {
                    x_queue[position] = ((ApplicationApplet) (local_main)).newMouseX;
                    y_queue[position] = ((ApplicationApplet) (local_main)).newMouseY;
                    position++;
                }
            }
            try {
                Thread.sleep(50L);
            } catch(Exception _ex) {
			}
        }
    }

    public Monitor(Main main, int i) {
        sync = new Object();
        y_queue = new int[500];
        running = true;
        x_queue = new int[500];
        local_main = main;
    }

    public Main local_main;
    public Object sync;
    public int y_queue[];
    public boolean running;
    public int x_queue[];
    public int position;
}
