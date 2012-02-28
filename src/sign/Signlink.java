// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Signlink.java

package sign;

import java.applet.Applet;
import java.io.*;
import java.net.*;

public class Signlink implements Runnable {

    public static void initialize(InetAddress inetaddress) {
        sessionId = (int)(Math.random() * 99999999D);
        if(isActive) {
            try
            {
                Thread.sleep(500L);
            }
            catch(Exception _ex) { }
            isActive = false;
        }
        addressPort = 0;
        threadRunnable = null;
        hostAddress = null;
        fileName = null;
        inputStreamUrl = null;
        hostInetAddress = inetaddress;
        Thread thread = new Thread(new Signlink());
        thread.setDaemon(true);
        thread.start();
        while(!isActive) {
            try {
                Thread.sleep(50L);
            } catch(Exception ex) { 
            }
        }
    }

    @Override
    public void run() {
        isActive = true;
        String cacheDir = getCacheDirectory();
        uid = getUid(cacheDir);
        try {
            File file = new File(cacheDir + "main_file_cache.dat");
            if(file.exists() && file.length() > 0x3200000L)
                file.delete();
            mainCacheRandomAccessFile = new RandomAccessFile(cacheDir + "main_file_cache.dat", "rw");
            for(int j = 0; j < 5; j++)
                cacheRandomAccessFiles[j] = new RandomAccessFile(cacheDir + "main_file_cache.idx" + j, "rw");
        } catch(Exception ex) {
            ex.printStackTrace();
        }
        for(int session = sessionId; sessionId == session;) {
            if(addressPort != 0) {
                try {
                    createdSocket = new Socket(hostInetAddress, addressPort);
                } catch(Exception ex) {
                    createdSocket = null;
                }
                addressPort = 0;
            } else if(threadRunnable != null) {
                Thread thread = new Thread(threadRunnable);
                thread.setDaemon(true);
                thread.start();
                thread.setPriority(threadPriority);
                threadRunnable = null;
            } else
            if(hostAddress != null) {
                try{
                    lookupAddress = InetAddress.getByName(hostAddress).getHostName();
                } catch(Exception ex) {
                    lookupAddress = "unknown";
                }
                hostAddress = null;
            } else if(fileName != null) {
                if(fileBuffer != null) {
                    try
                    {
                        FileOutputStream fileoutputstream = new FileOutputStream(cacheDir + fileName);
                        fileoutputstream.write(fileBuffer, 0, fileSize);
                        fileoutputstream.close();
                    }
                    catch(Exception ex) { 
                    }
                }
                if(writeWaveFile){
                    waveFileName = cacheDir + fileName;
                    writeWaveFile = false;
                }
                if(writeMidiFile) {
                    midiFileName = cacheDir + fileName;
                    writeMidiFile = false;
                }
                fileName = null;
            } else if(inputStreamUrl != null) {
                try {
                    createdInputStream = new DataInputStream((new URL(applet.getCodeBase(), inputStreamUrl)).openStream());
                } catch(Exception ex) {
                    createdInputStream = null;
                }
                inputStreamUrl = null;
            }
            try {
                Thread.sleep(50L);
            } catch(Exception ex) { 
            }
        }

    }

    public static String getCacheDirectory() {
        String as[] = {
            "./cache/"
        };
        if(storeId < 32 || storeId > 34)
            storeId = 32;
        String s = "";//".file_store_" + storeId;
        for(int i = 0; i < as.length; i++) {
            try {
                String dir = as[i];
                if(dir.length() > 0) {
                    File file = new File(dir);
                    if(!file.exists())
                        continue;
                }
                File file1 = new File(dir + s);
                if(file1.exists() || file1.mkdir())
                    return dir + s + "/";
            } catch(Exception ex) { 
            }
        }
        return null;
    }

    public static int getUid(String dir) {
        try {
            File file = new File(dir + "uid.dat");
            if(!file.exists() || file.length() < 4L) {
                DataOutputStream dataoutputstream = new DataOutputStream(new FileOutputStream(dir + "uid.dat"));
                dataoutputstream.writeInt((int)(Math.random() * 99999999D));
                dataoutputstream.close();
            }
        } catch(Exception ex) { 
        }
        try {
            DataInputStream datainputstream = new DataInputStream(new FileInputStream(dir + "uid.dat"));
            int i = datainputstream.readInt();
            datainputstream.close();
            return i + 1;
        } catch(Exception _ex) {
            return 0;
        }
    }

    public static synchronized Socket createSocket(int i) throws IOException {
        for(addressPort = i; addressPort != 0;) {
            try {
                Thread.sleep(50L);
            }
            catch(Exception _ex) { 
            }
        }
        if(createdSocket == null)
            throw new IOException("could not open socket");
        else
            return createdSocket;
    }

    public static synchronized DataInputStream createInputStream(String s) throws IOException {
        for(inputStreamUrl = s; inputStreamUrl != null;) {
            try{
                Thread.sleep(50L);
            }
            catch(Exception _ex) { }        
        }
        if(createdInputStream == null)
            throw new IOException("could not open: " + s);
        else
            return createdInputStream;
    }

    public static synchronized void lookupHost(String s) {
        lookupAddress = s;
        hostAddress = s;
    }

    public static synchronized void createThread(Runnable runnable, int priority) {
        threadPriority = priority;
        threadRunnable = runnable;
    }

    public static synchronized boolean writeWaveFile(byte src[], int size) {
        if(size > 0x1e8480)
            return false;
        if(fileName != null) {
            return false;
        } else {
            waveFileOffset = (waveFileOffset + 1) % 5;
            fileSize = size;
            fileBuffer = src;
            writeWaveFile = true;
            fileName = "sound" + waveFileOffset + ".wav";
            return true;
        }
    }

    public static synchronized boolean reloadWaveFile() {
        if(fileName != null) {
            return false;
        } else {
            fileBuffer = null;
            writeWaveFile = true;
            fileName = "sound" + waveFileOffset + ".wav";
            return true;
        }
    }

    public static synchronized void writeMidiFile(byte src[], int size) {
        if(size > 0x1e8480)
            return;
        if(fileName != null) {
            return;
        } else {
            midipos = (midipos + 1) % 5;
            fileSize = size;
            fileBuffer = src;
            writeMidiFile = true;
            fileName = "jingle" + midipos + ".mid";
            return;
        }
    }

    public static void reportError(String s) {
        if(!allowErrorReporting)
            return;
        if(!isActive)
            return;
        System.out.println("Error: " + s);
        try {
            s = s.replace(':', '_');
            s = s.replace('@', '_');
            s = s.replace('&', '_');
            s = s.replace('#', '_');
            DataInputStream datainputstream = createInputStream("reporterror" + 317 + ".cgi?error=" + errorMessage + " " + s);
            datainputstream.readLine();
            datainputstream.close();
            return;
        } catch(IOException ex) {
            return;
        }
    }

    public Signlink() {
    }

    public static int uid;
    public static int storeId = 32;
    public static RandomAccessFile mainCacheRandomAccessFile = null;
    public static RandomAccessFile cacheRandomAccessFiles[] = new RandomAccessFile[5];
    public static boolean sunjava;
    public static Applet applet = null;
    public static boolean isActive;
    public static int sessionId;
    public static InetAddress hostInetAddress;
    public static int addressPort;
    public static Socket createdSocket = null;
    public static int threadPriority = 1;
    public static Runnable threadRunnable = null;
    public static String hostAddress = null;
    public static String lookupAddress = null;
    public static String inputStreamUrl = null;
    public static DataInputStream createdInputStream = null;
    public static int fileSize;
    public static String fileName = null;
    public static byte fileBuffer[] = null;
    public static boolean writeMidiFile;
    public static int midipos;
    public static String midiFileName = null;
    public static int midiVolume;
    public static int midiFade;
    public static boolean writeWaveFile;
    public static int waveFileOffset;
    public static String waveFileName = null;
    public static int waveVolume;
    public static boolean allowErrorReporting = true;
    public static String errorMessage = "";

}
