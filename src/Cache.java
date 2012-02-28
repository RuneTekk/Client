// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

import sign.Signlink;

public class Cache {

    public Cache(int size) {
        root = new SubNode();
        queue = new Queue();
        cacheSize = size;
        remaining = size;
        hashTable = new HashTable(1024);
    }

    public SubNode get(long key) {
        SubNode node = (SubNode) hashTable.getNode(key);
        if(node != null) {
            queue.add(node);
            foundAttempts++;
        } else {
            unknownAttempts++;
        }
        return node;
    }

    public void put(SubNode node, long key) {
        try {
            if(remaining == 0) {
                SubNode queueNode = queue.removeFirst();
                queueNode.removeDeque();
                queueNode.removeQueue();
                if(queueNode == root) {
                    queueNode = queue.removeFirst();
                    queueNode.removeDeque();
                    queueNode.removeQueue();
                }
            } else {
                remaining--;
            }
            hashTable.put(node, key);
            queue.add(node);
            return;
        } catch (RuntimeException ex) {
            ex.printStackTrace();
            Signlink.reportError("47547, " + node + ", " + key + ", " + ex);
        }
        throw new RuntimeException();
    }

    public void clear() {
        do {
            SubNode node = queue.removeFirst();
            if(node != null) {
                node.removeDeque();
                node.removeQueue();
            } else {
                remaining = cacheSize;
                return;
            }
        } while(true);
    }

    public int unknownAttempts;
    public int foundAttempts;
    public SubNode root;
    public int cacheSize;
    public int remaining;
    public HashTable hashTable;
    public Queue queue;
}
