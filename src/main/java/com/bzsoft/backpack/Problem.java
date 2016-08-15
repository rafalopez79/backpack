package com.bzsoft.backpack;


public class Problem {

    protected final double capacity;
    protected final Item[] items;

    public Problem(final double capacity, final Item[] items) {
        this.capacity = capacity;
        this.items = items;
    }

    public double getCapacity() {
        return capacity;
    }

    public Item[] getItems() {
        return items;
    }

    public int getSize(){
        return items.length;
    }


}
