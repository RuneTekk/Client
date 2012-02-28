// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 


public class SubNode extends Node {

    public void removeQueue() {
        if(parentSubnode == null) {
            return;
        } else {
            parentSubnode.childSubnode = childSubnode;
            childSubnode.parentSubnode = parentSubnode;
            childSubnode = null;
            parentSubnode = null;
            return;
        }
    }

    public SubNode() {
    }

    public SubNode childSubnode;
    public SubNode parentSubnode;
    public static int anInt1305;
}
