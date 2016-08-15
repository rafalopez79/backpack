package com.bzsoft.backpack;


public class BaseItem implements Item {

    protected final double w;
    protected final double v;

    public BaseItem(final double w, final double v) {
        this.w = w;
        this.v = v;
    }

    @Override
    public double getWeight() {
        return w;
    }

    @Override
    public double getValue() {
        return v;
    }

    @Override
    public String toString() {
        return "[w=" + w + ", v=" + v + ", "+ v/w+"]";
    }

}

