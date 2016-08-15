package com.bzsoft.backpack;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import com.bzsoft.backpack.impl.ProblemSolverImpl;

/**
 * The class <code>SolverTest</code> is an implementation of a greedy algorithm
 * to solve the knapsack problem.
 *
 */
public class SolverTest {

    /**
     * The main class
     */
    public static void main(final String[] args) {
        try {
            final String[] list = { "ks_100_0", "ks_100_1", "ks_100_2", "ks_1000_0", "ks_10000_0", "ks_19_0",
                    "ks_19_1", "ks_200_0", "ks_200_1",
                    "ks_30_0", "ks_300_0", "ks_4_0", "ks_40_0", "ks_400_0", "ks_45_0", "ks_50_0", "ks_50_1",
                    "ks_500_0", "ks_60_0" };
            for (final String s : list) {
                solve("data/" + s);
            }
        } catch (final IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Read the instance, solve it, and print the solution in the standard
     * output
     */
    public static void solve(final String url) throws IOException {
        // read the lines out of the file
        System.out.println("Solving " + url);
        final List<String> lines = new ArrayList<String>();

        BufferedReader input = null;
        try {
            input = new BufferedReader(new InputStreamReader(SolverTest.class.getClassLoader().getResourceAsStream(url)));
            String line = null;
            while ((line = input.readLine()) != null) {
                lines.add(line);
            }
        } finally {
            input.close();
        }

        // parse the data in the file
        final String[] firstLine = lines.get(0).split("\\s+");
        final int items = Integer.parseInt(firstLine[0]);
        final int capacity = Integer.parseInt(firstLine[1]);

        final int[] values = new int[items];
        final int[] weights = new int[items];

        for (int i = 1; i < items + 1; i++) {
            final String line = lines.get(i);
            final String[] parts = line.split("\\s+");

            values[i - 1] = Integer.parseInt(parts[0]);
            weights[i - 1] = Integer.parseInt(parts[1]);
        }

        final ProblemSolver ps = new ProblemSolverImpl();
        final BaseItem[] its = new BaseItem[items];
        for (int i = 0; i < items; i++) {
            its[i] = new BaseItem(weights[i], values[i]);
        }
        final Problem p = new Problem(capacity, its);
        final Solution s = ps.solve(p);
        System.out.println();

        System.out.println(s.getValue() + " 0");
        System.out.println(s.getTime() / 1000 + "s " + s.getExploredNodes() + " nodes");
        System.out.println();

    }
}