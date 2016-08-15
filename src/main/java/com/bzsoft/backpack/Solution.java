package com.bzsoft.backpack;

public class Solution {

    private final Item[] items;
    private final double value;
    private final long exploredNodes;
    private final long time;

    public Solution(final Item[] items, final double value, final long exploredNodes, final long time) {
        this.items = items;
        this.value = value;
        this.exploredNodes = exploredNodes;
        this.time = time;
    }

    public Item[] getItems() {
        return items;
    }

    public double getValue() {
        return value;
    }

    public long getExploredNodes() {
        return exploredNodes;
    }

    public long getTime() {
        return time;
    }


}
