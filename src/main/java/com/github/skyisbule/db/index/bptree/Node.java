package com.github.skyisbule.db.index.bptree;


import java.util.ArrayList;

public class Node<E> {

    private ArrayList<E> keys;
    private ArrayList<Object> pointers;
    private Node<E> next;
    private Node<E> prev;
    boolean isLeaf;
    int nodeSize;

    public Node(int nodeSize, boolean isLeaf) {
        super();
        keys = new ArrayList<E>();
        pointers = new ArrayList<Object>();
        this.nodeSize = nodeSize;
        this.isLeaf = isLeaf;
    }

    public ArrayList<E> getKeys() {
        return keys;
    }

    public void setKeys(ArrayList<E> keys) {
        this.keys = keys;
    }

    public ArrayList<Object> getPointers() {
        return pointers;
    }

    public void setPointers(ArrayList<Object> pointers) {
        this.pointers = pointers;
    }

    public Node<E> getNext() {
        return next;
    }

    public void setNext(Node<E> next) {
        this.next = next;
    }

    public Node<E> getPrev() {
        return prev;
    }

    public void setPrev(Node<E> prev) {
        this.prev = prev;
    }

    public boolean isLeaf() {
        return isLeaf;
    }

    public void setLeaf(boolean isLeaf) {
        this.isLeaf = isLeaf;
    }

    public int getNodeSize() {
        return nodeSize;
    }

    public void setNodeSize(int nodeSize) {
        this.nodeSize = nodeSize;
    }
}
