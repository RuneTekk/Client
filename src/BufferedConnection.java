// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

import java.io.*;
import java.net.Socket;

public class BufferedConnection implements Runnable {

    public BufferedConnection(ApplicationApplet applet, int i, Socket s) throws IOException {
        applicationApplet = applet;
        socket = s;
        socket.setSoTimeout(30000);
        socket.setTcpNoDelay(true);
        inputStream = socket.getInputStream();
        outputStream = socket.getOutputStream();
    }

    public void close() {
        isClosed = true;
        try {
            if(inputStream != null)
                inputStream.close();
            if(outputStream != null)
                outputStream.close();
            if(socket != null)
                socket.close();
        } catch(IOException ex) {
            System.out.println("Error closing stream");
        }
        running = false;
        synchronized(this) {
            notify();
        }
        socketBuffer = null;
    }

    public int read() throws IOException {
        if(isClosed)
            return 0;
        else
            return inputStream.read();
    }

    public int getAvailable() throws IOException {
        if(isClosed)
            return 0;
        else
            return inputStream.available();
    }

    public void read(byte dest[], int off, int len) throws IOException {
        if(isClosed)
            return;
        int read;
        for(; len > 0; len -= read) {
            read = inputStream.read(dest, off, len);
            if(read <= 0)
                throw new IOException("EOF");
            off += read;
        }
    }

    public void write(byte src[], int off, int len) throws IOException {
        if(isClosed)
            return;
        if(exceptionThrown) {
            exceptionThrown = false;
            throw new IOException("Error in writer thread");
        }
        if(socketBuffer == null)
            socketBuffer = new byte[5000];
        synchronized(this) {
            for(int pos = 0; pos < len; pos++) {
                socketBuffer[bufferOffset] = src[pos + off];
                bufferOffset = (bufferOffset + 1) % 5000;
                if(bufferOffset == (writeOffset + 4900) % 5000)
                    throw new IOException("buffer overflow");
            }
            if(!running) {
                running = true;
                applicationApplet.createThread(this, 3);
            }
            notify();
        }
    }

    @Override
    public void run() {
        while(running) {
            int len;
            int off;
            synchronized(this) {
                if(bufferOffset == writeOffset) {
                    try {
                        wait();
                    }
                    catch(InterruptedException ex) { 
                    }              
                }
                if(!running)
                    return;
                off = writeOffset;
                if(bufferOffset >= writeOffset)
                    len = bufferOffset - writeOffset;
                else
                    len = 5000 - writeOffset;
            }
            if(len > 0)
            {
                try {
                    outputStream.write(socketBuffer, off, len);
                } catch(IOException ex) {
                    exceptionThrown = true;
                }
                writeOffset = (writeOffset + len) % 5000;
                try
                {
                    if(bufferOffset == writeOffset)
                        outputStream.flush();
                } catch(IOException ex) {
                    exceptionThrown = true;
                }
            }
        }
    }

    public void printInfo() {
        System.out.println("dummy:" + isClosed);
        System.out.println("tcycl:" + writeOffset);
        System.out.println("tnum:" + bufferOffset);
        System.out.println("writer:" + running);
        System.out.println("ioerror:" + exceptionThrown);
        try {
            System.out.println("available:" + getAvailable());
            return;
        } catch(IOException ex) {
            return;
        }
    }

    public InputStream inputStream;
    public OutputStream outputStream;
    public Socket socket;
    public boolean isClosed;
    public ApplicationApplet applicationApplet;
    public byte socketBuffer[];
    public int writeOffset;
    public int bufferOffset;
    public boolean running;
    public boolean exceptionThrown;
}
