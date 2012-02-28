// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

public class Deque {

    public Deque() {
        root = new Node();
        root.parentNode = root;
        root.childNode = root;
    }

    public void addLast(Node node) {
        if(node.childNode != null)
            node.removeDeque();
        node.childNode = root.childNode;
        node.parentNode = root;
        node.childNode.parentNode = node;
        node.parentNode.childNode = node;
    }

    public void addFirst(Node node) {
        if(node.childNode != null)
            node.removeDeque();
        node.childNode = root;
        node.parentNode = root.parentNode;
        node.childNode.parentNode = node;
        node.parentNode.childNode = node;
    }

    public Node removeFirst() {
        Node class30 = root.parentNode;
        if(class30 == root) {
            return null;
        } else {
            class30.removeDeque();
            return class30;
        }
    }

    public Node getFirst() {
        Node node = root.parentNode;
        if(node == root) {
            iterator = null;
            return null;
        } else {
            iterator = node.parentNode;
            return node;
        }
    }

    public Node getLast() {
        Node node = root.childNode;
        if(node == root)
        {
            iterator = null;
            return null;
        } else
        {
            iterator = node.childNode;
            return node;
        }
    }

    public Node getNextFront() {
        Node class30 = iterator;
        if(class30 == root)
        {
            iterator = null;
            return null;
        } else
        {
            iterator = class30.parentNode;
            return class30;
        }
    }

    public Node getNextBack() {
        Node node = iterator;
        if(node == root)
        {
            iterator = null;
            return null;
        }
        iterator = node.childNode;
        return node;
    }

    public void clear() {
        if(root.parentNode == root)
            return;
        do {
            Node class30 = root.parentNode;
            if(class30 == root)
                return;
            class30.removeDeque();
        } while(true);
    }

    public Node root;
    public Node iterator;
}
