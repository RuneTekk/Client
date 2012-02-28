// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

import sign.Signlink;

public class HashTable {

    public HashTable(int size) {
        tableSize = size;
        table = new Node[size];
        for(int i = 0; i < size; i++) {
            Node class30 = table[i] = new Node();
            class30.parentNode = class30;
            class30.childNode = class30;
        }
    }

    public Node getNode(long l) {
        Node tableNode = table[(int)(l & (long)(tableSize - 1))];
        for(Node node = tableNode.parentNode; node != tableNode; node = node.parentNode)
            if(node.key == l)
                return node;
        return null;
    }

    public void put(Node node, long key) {
        try {
            if(node.childNode != null)
                node.removeDeque();
            Node tableNode = table[(int)(key & (long)(tableSize - 1))];
            node.childNode = tableNode.childNode;
            node.parentNode = tableNode;
            node.childNode.parentNode = node;
            node.parentNode.childNode = node;
            node.key = key;
            return;
        } catch(RuntimeException ex) {
            Signlink.reportError("91499, " + node + ", " + key + ", " + ex.toString());
        }
        throw new RuntimeException();
    }

    public int tableSize;
    public Node table[];
}
