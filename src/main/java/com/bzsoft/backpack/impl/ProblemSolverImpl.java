package com.bzsoft.backpack.impl;

import java.util.Arrays;
import java.util.BitSet;
import java.util.Comparator;
import java.util.Iterator;
import java.util.PriorityQueue;

import com.bzsoft.backpack.Item;
import com.bzsoft.backpack.Problem;
import com.bzsoft.backpack.ProblemSolver;
import com.bzsoft.backpack.Solution;



public class ProblemSolverImpl implements ProblemSolver {

    private static final class ItemComparator implements Comparator<Item> {

        @Override
        public int compare(final Item a, final Item b) {
            final double aRatio = a.getValue() / a.getWeight();
            final double bRatio = b.getValue() / b.getWeight();
            return aRatio < bRatio ? 1 : aRatio > bRatio ? -1 : 0;
        }
    }

    private final Comparator<Item> comparator;

    public ProblemSolverImpl() {
        this.comparator = new ItemComparator();
    }

    @Override
    public Solution solve(final Problem p) {
        final long startTime = System.currentTimeMillis();
        final int len = p.getSize();
        final PriorityQueue<Node> queue = new PriorityQueue<Node>(2*len);
        final Item[] pitems = p.getItems();
        final Item[] items = new Item[len];
        System.arraycopy(pitems, 0, items, 0, len);
        final double capacity = p.getCapacity();
        Arrays.sort(items, comparator);
        Node sol = createGreedySolution(capacity, items);
        System.out.println(" - Greedy: "+sol);
        queue.add(new Node(0, 0, 0, new BitSet(len), 0, len));
        long iter = 0;
        while (!queue.isEmpty()) {
            iter ++;
            final Node n =  queue.poll();
            final int l = n.getLen();
            final Item item = items[l];
            final Node n1 = createChildNode(n, item, items, true, capacity, sol);
            if (n1 != null) {
                if (n1.getLen() == len) {
                    if (n1.getValue() > sol.getValue()) {
                        sol = n1;
                        System.out.println(" - "+sol);
                    }
                } else {
                    queue.add(n1);
                }
            }

            final Node n0 = createChildNode(n, item, items, false, capacity, sol);
            if (n0 != null) {
                if (n0.getLen() == len) {
                    if (n0.getValue() > sol.getValue()) {
                        sol = n0;
                        System.out.println(" - "+sol);
                    }
                } else {
                    queue.add(n0);
                }
            }
            bound(queue, sol.getValue());
        }
        final long endTime = System.currentTimeMillis();
        return new Solution(getItems(items, sol.getPath()), sol.getValue(), iter, endTime - startTime);
    }

    private static final Item[] getItems(final Item[] items, final BitSet path) {
        int count = 0;
        final int l =  path.length();
        for (int i = 0; i < l; i++ ) {
            count += path.get(i) ? 1 : 0;
        }
        final Item[] result = new Item[count];
        for (int i = 0, j = 0; i < l; i++) {
            if (path.get(i)) {
                result[j] = items[i];
                j ++;
            }
        }
        return result;
    }

    private static final Node createGreedySolution(final double capacity, final Item[] items){
        final int size = items.length;
        Node sol = null;
        for(int i = 0; i < size; i++){
            final Node s = createGreedySolution(capacity, items, i);
            if (sol == null || s.getValue() > sol.getValue()){
                sol = s;
            }
        }
        return sol;
    }

    private static final Node createGreedySolution(final double capacity, final Item[] items, final int start){
        final int size = items.length;
        double totalValue = 0;
        double totalWeight = 0;
        final BitSet path = new BitSet(size);
        for (int i = start, j = 0; j < size; j++, i = (i + 1)%size) {
            final Item item = items[i];
            final double w = item.getWeight();
            final double v = item.getValue();
            if (totalWeight + w <= capacity) {
                totalValue += v;
                totalWeight += w;
                path.set(i);
            }
        }
        return new Node(totalValue, totalWeight, totalValue, path, size, items.length);
    }


    private static final double estimate(final double capacity, final Item[] items, final int len, final double value, final double weight) {
        final int size = items.length;
        double totalValue = value;
        double totalWeight = weight;
        for (int i = len + 1; i < size; i++) {
            final Item item = items[i];
            final double w = item.getWeight();
            final double v = item.getValue();
            if (totalWeight + w <= capacity) {
                totalValue += v;
                totalWeight += w;
            } else if (totalWeight < capacity) {
                totalValue += v * (capacity - totalWeight) / w;
                break;
            } else {
                break;
            }
        }
        return totalValue;
    }


    private static final Node createChildNode(final Node parent, final Item item, final Item[] items, final boolean choose, final double capacity,
            final Node solution) {
        final double w = parent.getWeight() + (choose ? item.getWeight() : 0d);
        if (w > capacity) {
            return null;
        }
        final BitSet path = parent.getPath();
        final int l = parent.getLen();
        final double value = parent.getValue() + (choose ? item.getValue() : 0d);
        final double estimate = estimate(capacity, items, l, value, w);
        if (estimate <= solution.getValue()) {
            return null;
        }
        final BitSet nPath = new BitSet(items.length);
        nPath.or(path);
        if (choose){
            nPath.set(l);
        }
        return new Node(value, w, estimate, nPath, l+1, items.length);
    }

    private static final void bound(final Iterable<Node> queue, final double value) {
        final Iterator<Node> it = queue.iterator();
        while(it.hasNext()){
            final Node n = it.next();
            final double estimate = n.getEstimate();
            if (estimate <= value) {
                it.remove();
            }
        }
    }

}
