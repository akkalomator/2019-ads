package ru.mail.polis.ads.part10;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.StringTokenizer;

/**
 * submission - https://www.e-olymp.com/ru/submissions/6401229
 */
public class Problem2 {

    private static class Edge {

        private int first;
        private int second;
        private int weight;

        public Edge(int first, int second, int weight) {
            this.first = first;
            this.second = second;
            this.weight = weight;
        }
    }

    private static class FastScanner {
        private final BufferedReader reader;
        private StringTokenizer tokenizer;

        FastScanner(final InputStream in) {
            reader = new BufferedReader(new InputStreamReader(in));
        }

        String next() {
            while (tokenizer == null || !tokenizer.hasMoreTokens()) {
                try {
                    tokenizer = new StringTokenizer(reader.readLine());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return tokenizer.nextToken();
        }

        int nextInt() {
            return Integer.parseInt(next());
        }
    }

    private static int[] parent;

    private static void solve() {
        FastScanner fastScanner = new FastScanner(System.in);
        int n = fastScanner.nextInt();
        int m = fastScanner.nextInt();

        parent = new int[n + 1];
        for (int i = 1; i <= n; i++) {
            parent[i] = i;
        }

        Queue<Edge> queue = new PriorityQueue<>(n, Comparator.comparing(edge -> edge.weight));

        for (int i = 0; i < m; i++) {
            int firstId = fastScanner.nextInt();
            int secondId = fastScanner.nextInt();
            int weight = fastScanner.nextInt();

            queue.add(new Edge(firstId, secondId, weight));
        }

        long minSum = 0;

        int countSet = n;
        while (countSet != 1) {
            Edge edge = queue.poll();
            if (findSet(edge.first) != findSet(edge.second)) {
                unionSet(edge.first, edge.second);
                minSum += edge.weight;
                countSet--;
            }
        }

        System.out.println(minSum);
    }

    private static void unionSet(int firstNode, int secondNode) {
        firstNode = findSet(firstNode);
        secondNode = findSet(secondNode);
        if (firstNode != secondNode) {
            parent[secondNode] = firstNode;
        }
    }

    private static int findSet(int idNode) {
        if (idNode == parent[idNode]) {
            return idNode;
        }
        return parent[idNode] = findSet(parent[idNode]);
    }

    public static void main(String[] args) {
        solve();
    }
}
