// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

import java.io.*;
import java.net.Socket;
import java.util.zip.CRC32;
import java.util.zip.GZIPInputStream;
import sign.Signlink;

public class OndemandHandler extends NumericLoader implements Runnable {

    public boolean isValid(byte src[], int expectedVersion, int expectedChecksum) {
        if(src == null || src.length < 2)
            return false;
        int offset = src.length - 2;
        int version = ((src[offset] & 0xff) << 8) + (src[offset + 1] & 0xff);
        crc.reset();
        crc.update(src, 0, offset);
        int checksum = (int) crc.getValue();
        if(version != expectedVersion)
            return false;
        return checksum == expectedChecksum;
    }

    public void parseResponse() {
        try {
            int avail = inputStream.available();
            if(blockSize == 0 && avail >= 6) {
                hasActiveRequests = true;
                for(int off = 0; off < 6; off += inputStream.read(payload, off, 6 - off));
                int index = payload[0] & 0xff;
                int id = ((payload[1] & 0xff) << 8) + (payload[2] & 0xff);
                int size = ((payload[3] & 0xff) << 8) + (payload[4] & 0xff);
                int chunk = payload[5] & 0xff;
                currentRequest = null;
                for(OndemandRequest request = (OndemandRequest)activeRequests.getFirst(); request != null; request = (OndemandRequest)activeRequests.getNextFront()) {
                    if(request.indexId == index && request.archiveId == id)
                        currentRequest = request;
                    if(currentRequest != null)
                        request.cycle = 0;
                }
                if(currentRequest != null) {
                    requestCounter = 0;
                    if(size == 0) {
                        Signlink.reportError("Rej: " + index + "," + id);
                        currentRequest.archiveBuffer = null;
                        if(currentRequest.isOnloadPriority) {
                            synchronized(finishedRequests) {
                                finishedRequests.addLast(currentRequest);
                            }
                        }
                        else
                            currentRequest.removeDeque();
                        currentRequest = null;
                    } else {
                        if(currentRequest.archiveBuffer == null && chunk == 0)
                            currentRequest.archiveBuffer = new byte[size];
                        if(currentRequest.archiveBuffer == null && chunk != 0)
                            throw new IOException("missing start of file");
                    }
                }
                archiveOffset = chunk * 500;
                blockSize = 500;
                if(blockSize > size - chunk * 500)
                    blockSize = size - chunk * 500;
            }
            if(blockSize > 0 && avail >= blockSize) {
                hasActiveRequests = true;
                byte src[] = payload;
                int offset = 0;
                if(currentRequest != null) {
                    src = currentRequest.archiveBuffer;
                    offset = archiveOffset;
                }
                for(int off = 0; off < blockSize; off += inputStream.read(src, off + offset, blockSize - off));
                if(blockSize + archiveOffset >= src.length && currentRequest != null) {
                    if(main.fileIndexes[0] != null)
                        main.fileIndexes[currentRequest.indexId + 1].put(src, currentRequest.archiveId, src.length);
                    if(!currentRequest.isOnloadPriority && currentRequest.indexId == 3) {
                        currentRequest.isOnloadPriority = true;
                        currentRequest.indexId = 93;
                    }
                    if(currentRequest.isOnloadPriority)
                        synchronized(finishedRequests) {
                            finishedRequests.addLast(currentRequest);
                        }
                    else
                        currentRequest.removeDeque();
                }
                blockSize = 0;
                return;
            }
        } catch(IOException ioex) {
            try {
                socket.close();
            } catch(Exception ex) { 
            }
            socket = null;
            inputStream = null;
            outputStream = null;
            blockSize = 0;
        }
    }

    public void parseVersionList(ArchivePackage pack, Main m) {
        String versionArchives[] = {
            "model_version", "anim_version", "midi_version", "map_version"
        };
        for(int i = 0; i < 4; i++) {
            byte src[] = pack.getArchive(versionArchives[i], null);
            int amountEntries = src.length / 2;
            ByteBuffer buffer0 = new ByteBuffer(src);
            archiveVersions[i] = new int[amountEntries];
            archivePriorities[i] = new byte[amountEntries];
            for(int j = 0; j < amountEntries; j++)
                archiveVersions[i][j] = buffer0.getUword();
        }
        String crcArchives[] = {
            "model_crc", "anim_crc", "midi_crc", "map_crc"
        };
        for(int i = 0; i < 4; i++) {
            byte src[] = pack.getArchive(crcArchives[i], null);
            int amountEntries = src.length / 4;
            ByteBuffer class30_sub2_sub2_1 = new ByteBuffer(src);
            archiveCrcs[i] = new int[amountEntries];
            for(int j = 0; j < amountEntries; j++)
                archiveCrcs[i][j] = class30_sub2_sub2_1.getDword();
        }
        byte src[] = pack.getArchive("model_index", null);
        int amountEntries = archiveVersions[0].length;
        modelFlags = new byte[amountEntries];
        for(int k1 = 0; k1 < amountEntries; k1++)
            if(k1 < src.length)
                modelFlags[k1] = src[k1];
            else
                modelFlags[k1] = 0;
        src = pack.getArchive("map_index", null);
        ByteBuffer buffer = new ByteBuffer(src);
        amountEntries = src.length / 7;
        landscapeHashes = new int[amountEntries];
        mapTileArchives = new int[amountEntries];
        mapObjArchives = new int[amountEntries];
        extra_maps = new int[amountEntries];
        for(int i = 0; i < amountEntries; i++) {
            landscapeHashes[i] = buffer.getUword();
            mapTileArchives[i] = buffer.getUword();           
            mapObjArchives[i] = buffer.getUword();
            extra_maps[i] = buffer.getUbyte();
        }
        src = pack.getArchive("anim_index", null);
        buffer = new ByteBuffer(src);
        amountEntries = src.length / 2;
        animFlags = new int[amountEntries];
        src = pack.getArchive("midi_index", null);
        buffer = new ByteBuffer(src);
        amountEntries = src.length;
        midiFlags = new int[amountEntries];
        for(int i = 0; i < amountEntries; i++)
            midiFlags[i] = buffer.getUbyte();
        main = m;
        isActive = true;
        m.createThread(this, 2);
    }

    public int amount() {
        synchronized(currentRequests) {
            int i = currentRequests.size();
            return i;
        }
    }

    public void pause() {
        isActive = false;
    }

    public void method554(boolean members, int i)
    {
        int j = landscapeHashes.length;
        for(int k = 0; k < j; k++)
            if(members || extra_maps[k] != 0)
            {
                setArchivePriority(3, mapObjArchives[k], (byte)2);
                setArchivePriority(3, mapTileArchives[k], (byte)2);
            }

    }

    public int getAmountArchives(int indexId) {
        return archiveVersions[indexId].length;
    }

    public void writeRequest(OndemandRequest request) {
        try {
            if(socket == null) {
                long currentTime = System.currentTimeMillis();
                if(currentTime - connectedTime < 4000L)
                    return;
                connectedTime = currentTime;
                System.out.println(43594 + Main.portOffset);
                socket = main.createSocket(43596);
                inputStream = socket.getInputStream();
                outputStream = socket.getOutputStream();
                outputStream.write(15);
                for(int j = 0; j < 8; j++)
                    inputStream.read();
                requestCounter = 0;
            }
            payload[0] = (byte)request.indexId;
            payload[1] = (byte)(request.archiveId >> 8);
            payload[2] = (byte)request.archiveId;
            if(request.isOnloadPriority)
                payload[3] = 2;
            else
            if(!main.isOnlineGame)
                payload[3] = 1;
            else
                payload[3] = 0;
            outputStream.write(payload, 0, 4);
            connectionAttempts = 0;
            anInt1349 = -10000;
            return;
        }
        catch(IOException ioex) { }
        try
        {
            socket.close();
        }
        catch(Exception ex) { }
        socket = null;
        inputStream = null;
        outputStream = null;
        blockSize = 0;
        anInt1349++;
    }

    public int getAmountAnims() {
        return animFlags.length;
    }

    public void requestPriority(int indexId, int archiveId) {
        if(indexId < 0 || indexId > archiveVersions.length || archiveId < 0 || archiveId > archiveVersions[indexId].length)
            return;
        if(archiveVersions[indexId][archiveId] == 0)
            return;
        synchronized(currentRequests)  {
            for(OndemandRequest class30_sub2_sub3 = (OndemandRequest) currentRequests.getFirst(); class30_sub2_sub3 != null; class30_sub2_sub3 = (OndemandRequest)currentRequests.getNext())
                if(class30_sub2_sub3.indexId == indexId && class30_sub2_sub3.archiveId == archiveId)
                    return;
            OndemandRequest request = new OndemandRequest();
            request.indexId = indexId;
            request.archiveId = archiveId;
            request.isOnloadPriority = true;
            synchronized(onloadArchiveRequests) {
                onloadArchiveRequests.addLast(request);
            }
            currentRequests.add(request);
        }
    }

    public int getModelSettingFlag(int i, int j)
    {
        return modelFlags[i] & 0xff;
    }

    @Override
    public void run() {
        try {
            while(isActive) {
                cycle++;
                int sleepTime = 20;
                if(heighestPriority == 0 && main.fileIndexes[0] != null)
                    sleepTime = 50;
                try {
                    Thread.sleep(sleepTime);
                }
                catch(Exception ex) { 
                }
                hasActiveRequests = true;
                for(int j = 0; j < 100; j++) {
                    if(!hasActiveRequests)
                        break;
                    hasActiveRequests = false;
                    handleOnloadArchiveRequests();
                    handleOnloadRequests();
                    if(amountOnloadArchives == 0 && j >= 5)
                        break;
                    handleExtraFiles();
                    if(inputStream != null)
                        parseResponse();
                }
                boolean hasRequest = false;
                for(OndemandRequest request = (OndemandRequest) activeRequests.getFirst(); request != null; request = (OndemandRequest)activeRequests.getNextFront())
                    if(request.isOnloadPriority) {
                        hasRequest = true;
                        request.cycle++;
                        if(request.cycle > 50) {
                            request.cycle = 0;
                            writeRequest(request);
                        }
                    }
                if(!hasRequest) {
                    for(OndemandRequest request = (OndemandRequest)activeRequests.getFirst(); request != null; request = (OndemandRequest)activeRequests.getNextFront()) {
                        hasRequest = true;
                        request.cycle++;
                        if(request.cycle > 50) {
                            request.cycle = 0;
                            writeRequest(request);
                        }
                    }

                }
                if(hasRequest) {
                    requestCounter++;
                    if(requestCounter > 750) {
                        try {
                            socket.close();
                        }
                        catch(Exception ex) { 
                        }
                        socket = null;
                        inputStream = null;
                        outputStream = null;
                        blockSize = 0;
                    }
                } else {
                    requestCounter = 0;
                    extraFilesMsgStr = "";
                }
                if(main.isOnlineGame && socket != null && outputStream != null && (heighestPriority > 0 || main.fileIndexes[0] == null)) {
                    connectionAttempts++;
                    if(connectionAttempts > 500) {
                        connectionAttempts = 0;
                        payload[0] = 0;
                        payload[1] = 0;
                        payload[2] = 0;
                        payload[3] = 10;
                        try {
                            outputStream.write(payload, 0, 4);
                        } catch(IOException ex) {
                            requestCounter = 5000;
                        }
                    }
                }
            }
            return;
        } catch(Exception ex) {
            Signlink.reportError("od_ex " + ex.getMessage());
        }
    }

    public void request(int indexId, int archiveId) {
        if(main.fileIndexes[0] == null)
            return;
        if(archiveVersions[indexId][archiveId] == 0)
            return;
        if(archivePriorities[indexId][archiveId] == 0)
            return;
        if(heighestPriority == 0)
            return;
        OndemandRequest request = new OndemandRequest();
        request.indexId = indexId;
        request.archiveId = archiveId;
        request.isOnloadPriority = false;
        synchronized(afterLoadArchiveRequests) {
            afterLoadArchiveRequests.addLast(request);
        }
    }

    public OndemandRequest getFinishedRequest() {
        OndemandRequest request;
        synchronized(finishedRequests) {
            request = (OndemandRequest)finishedRequests.removeFirst();
        }
        if(request == null)
            return null;
        synchronized(currentRequests) {
            request.removeQueue();
        }
        if(request.archiveBuffer == null)
            return request;
        int requestSize = 0;
        try {
            GZIPInputStream gzipIs = new GZIPInputStream(new ByteArrayInputStream(request.archiveBuffer));
            do {
                if(requestSize == gzipBuffer.length)
                    throw new RuntimeException("buffer overflow!");
                int read = gzipIs.read(gzipBuffer, requestSize, gzipBuffer.length - requestSize);
                if(read == -1)
                    break;
                requestSize += read;
            } while(true);
        } catch(IOException ex) {
            throw new RuntimeException("error unzipping");
        }
        request.archiveBuffer = new byte[requestSize];
        System.arraycopy(gzipBuffer, 0, request.archiveBuffer, 0, requestSize);
        return request;
    }

    public int getMapArchive(int regionX, int regionY, int archiveType) {
        int hash = (regionX << 8) + regionY;
        for(int j1 = 0; j1 < landscapeHashes.length; j1++)
            if(landscapeHashes[j1] == hash)
                if(archiveType == 0)
                    return mapTileArchives[j1];
                else
                    return mapObjArchives[j1];
        return -1;
    }

    @Override
    public void load(int request) {
        requestPriority(0, request);
    }

    public void setArchivePriority(int indexId, int archiveId, byte priority) {
        if(main.fileIndexes[0] == null)
            return;
        if(archiveVersions[indexId][archiveId] == 0)
            return;
        byte src[] = main.fileIndexes[indexId + 1].get(archiveId);
        if(isValid(src, archiveVersions[indexId][archiveId], archiveCrcs[indexId][archiveId]))
            return;
        archivePriorities[indexId][archiveId] = priority;
        if(priority > heighestPriority)
            heighestPriority = priority;
        amountExtraArchives++;
    }

    public boolean method564(int i)
    {
        for(int k = 0; k < landscapeHashes.length; k++)
            if(mapObjArchives[k] == i)
                return true;
        return false;
    }

    public void handleOnloadRequests()
    {
        amountOnloadArchives = 0;
        amountLoadedRequests = 0;
        for(OndemandRequest request = (OndemandRequest)activeRequests.getFirst(); request != null; request = (OndemandRequest)activeRequests.getNextFront())
            if(request.isOnloadPriority)
                amountOnloadArchives++;
            else
                amountLoadedRequests++;
        while(amountOnloadArchives < 10) {
            OndemandRequest request = (OndemandRequest)onloadRequests.removeFirst();
            if(request == null)
                break;
            if(archivePriorities[request.indexId][request.archiveId] != 0)
                queuedExtraArchives++;
            archivePriorities[request.indexId][request.archiveId] = 0;
            activeRequests.addLast(request);
            amountOnloadArchives++;
            writeRequest(request);
            hasActiveRequests = true;
        }
    }

    public void method566(int i)
    {
        synchronized(afterLoadArchiveRequests)
        {
            afterLoadArchiveRequests.clear();
        }
    }

    public void handleOnloadArchiveRequests() {
        OndemandRequest request;
        synchronized(onloadArchiveRequests) {
            request = (OndemandRequest) onloadArchiveRequests.removeFirst();
        }
        while(request != null) {
            hasActiveRequests = true;
            byte src[] = null;
            if(main.fileIndexes[0] != null)
                src = main.fileIndexes[request.indexId + 1].get(request.archiveId);
            if(!isValid(src, archiveVersions[request.indexId][request.archiveId], archiveCrcs[request.indexId][request.archiveId]))
                src = null;
            synchronized(onloadArchiveRequests) {
                if(src == null) {
                    onloadRequests.addLast(request);
                } else {
                    request.archiveBuffer = src;
                    synchronized(finishedRequests) {
                        finishedRequests.addLast(request);
                    }
                }
                request = (OndemandRequest) onloadArchiveRequests.removeFirst();
            }
        }
    }

    public void handleExtraFiles() {
        while(amountOnloadArchives == 0 && amountLoadedRequests < 10) {
            if(heighestPriority == 0)
                break;
            OndemandRequest archiveRequest;
            synchronized(afterLoadArchiveRequests) {
                archiveRequest = (OndemandRequest) afterLoadArchiveRequests.removeFirst();
            }
            while(archiveRequest != null) {
                if(archivePriorities[archiveRequest.indexId][archiveRequest.archiveId] != 0) {
                    archivePriorities[archiveRequest.indexId][archiveRequest.archiveId] = 0;
                    activeRequests.addLast(archiveRequest);
                    writeRequest(archiveRequest);
                    hasActiveRequests = true;
                    if(queuedExtraArchives < amountExtraArchives)
                        queuedExtraArchives++;
                    extraFilesMsgStr = "Loading extra files - " + (queuedExtraArchives * 100) / amountExtraArchives + "%";
                    amountLoadedRequests++;
                    if(amountLoadedRequests == 10)
                        return;
                }
                synchronized(afterLoadArchiveRequests) {
                    archiveRequest = (OndemandRequest) afterLoadArchiveRequests.removeFirst();
                }
            }
            for(int indexId = 0; indexId < 4; indexId++) {
                byte priorities[] = archivePriorities[indexId];
                int k = priorities.length;
                for(int l = 0; l < k; l++)
                    if(priorities[l] == heighestPriority) {
                        priorities[l] = 0;
                        OndemandRequest request = new OndemandRequest();
                        request.indexId = indexId;
                        request.archiveId = l;
                        request.isOnloadPriority = false;
                        activeRequests.addLast(request);
                        writeRequest(request);
                        hasActiveRequests = true;
                        if(queuedExtraArchives < amountExtraArchives)
                            queuedExtraArchives++;
                        extraFilesMsgStr = "Loading extra files - " + (queuedExtraArchives * 100) / amountExtraArchives + "%";
                        amountLoadedRequests++;
                        if(amountLoadedRequests == 10)
                            return;
                    }
            }
            heighestPriority--;
        }
    }

    public boolean isExtraMidiFile(int id) {
        return midiFlags[id] == 1;
    }

    public OndemandHandler()
    {
        activeRequests = new Deque();
        extraFilesMsgStr = "";
        crc = new CRC32();
        payload = new byte[500];
        archivePriorities = new byte[4][];
        afterLoadArchiveRequests = new Deque();
        isActive = true;
        hasActiveRequests = false;
        finishedRequests = new Deque();
        gzipBuffer = new byte[65000];
        currentRequests = new Queue();
        archiveVersions = new int[4][];
        archiveCrcs = new int[4][];
        onloadRequests = new Deque();
        onloadArchiveRequests = new Deque();
    }

    public int amountExtraArchives;
    public Deque activeRequests;
    public int heighestPriority;
    public String extraFilesMsgStr;
    public int connectionAttempts;
    public long connectedTime;
    public int mapObjArchives[];
    public CRC32 crc;
    public byte payload[];
    public int anInt1340;
    public int cycle;
    public byte archivePriorities[][];
    public Main main;
    public Deque afterLoadArchiveRequests;
    public int archiveOffset;
    public int blockSize;
    public int midiFlags[];
    public int anInt1349;
    public int mapTileArchives[];
    public int queuedExtraArchives;
    public boolean isActive;
    public OutputStream outputStream;
    public int extra_maps[];
    public boolean hasActiveRequests;
    public Deque finishedRequests;
    public byte gzipBuffer[];
    public int animFlags[];
    public Queue currentRequests;
    public InputStream inputStream;
    public Socket socket;
    public int archiveVersions[][];
    public int archiveCrcs[][];
    public int amountOnloadArchives;
    public int amountLoadedRequests;
    public Deque onloadRequests;
    public OndemandRequest currentRequest;
    public Deque onloadArchiveRequests;
    public int landscapeHashes[];
    public byte modelFlags[];
    public int requestCounter;
}
