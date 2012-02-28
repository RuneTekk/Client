// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 


public class Node {

    public void removeDeque() {
        if(childNode == null) {
            return;
        } else {
            childNode.parentNode = parentNode;
            parentNode.childNode = childNode;
            parentNode = null;
            childNode = null;
            return;
        }
    }

    public Node() {
        aBoolean547 = true;
    }

    public boolean aBoolean547;
    public long key;
    public Node parentNode;
    public Node childNode;
    public static boolean aBoolean551;
}
