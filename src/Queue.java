// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

public final class Queue {

    public Queue() {
        root = new SubNode();
        root.childSubnode = root;
        root.parentSubnode = root;
    }

    public void add(SubNode node) {
        if(node.parentSubnode != null)
            node.removeQueue();
        node.parentSubnode = root.parentSubnode;
        node.childSubnode = root;
        node.parentSubnode.childSubnode = node;
        node.childSubnode.parentSubnode = node;
    }

    public SubNode removeFirst()  {
        SubNode node = root.childSubnode;
        if(node == root) {
            return null;
        } else {
            node.removeQueue();
            return node;
        }
    }

    public SubNode getFirst() {
        SubNode class30_sub2 = root.childSubnode;
        if(class30_sub2 == root) {
            iterator = null;
            return null;
        } else {
            iterator = class30_sub2.childSubnode;
            return class30_sub2;
        }
    }

    public SubNode getNext() {
        SubNode class30_sub2 = iterator;
        if(class30_sub2 == root) {
            iterator = null;
            return null;
        } else {
            iterator = class30_sub2.childSubnode;
            return class30_sub2;
        }
    }

    public int size() {
        int size = 0;
        for(SubNode class30_sub2 = root.childSubnode; class30_sub2 != root; class30_sub2 = class30_sub2.childSubnode)
            size++;
        return size;
    }

    public SubNode root;
    public SubNode iterator;
}
