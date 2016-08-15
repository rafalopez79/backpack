package com.bzsoft.backpack.impl;

import java.util.BitSet;


final class Node implements Comparable<Node>{

    private final double           value;
    private final double           w;
    private final double           estimate;
    private final BitSet           path;
    private final int len;
    private final int size;

    public Node(final double value, final double w, final double estimate, final BitSet path, final int len, final int size) {
        this.value = value;
        this.w = w;
        this.estimate = estimate;
        this.path = path;
        this.len = len;
        this.size = size;
    }

    public double getValue() {
        return value;
    }

    public double getWeight() {
        return w;
    }

    public double getEstimate() {
        return estimate;
    }

    public BitSet getPath() {
        return path;
    }

    public int getLen(){
        return len;
    }

    @Override
    public int compareTo(final Node n) {
        //expected incremented units per item in bag

                final double nest =  n.len == 0 ? 0 : (n.estimate - n.value)/ n.len;
                final double est = this.len == 0 ? 0 : (this.estimate - this.value)/ this.len;
                return est < nest ? -1 : est > nest ? 1 : 0;

        //        final double nest =  size - n.len == 0 ? 0 : (n.estimate - n.value)/ (size - n.len);
        //        final double est = size - this.len == 0 ? 0 : (this.estimate - this.value)/ (size - this.len);
        //        return est < nest ? 1 : est > nest ? -1 : 0;

        //classical
        //final double est = estimate;
        //final double nest = n.estimate;
        //return est < nest ? 1 : est > nest ? -1 : 0;

    }

    @Override
    public String toString() {
        return "[" + value + "," + estimate + ","+(estimate - value)+"]";
    }

}
