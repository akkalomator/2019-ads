package ru.mail.polis.ads.part10;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

/**
 * submission - https://www.e-olymp.com/ru/submissions/6401216
 */
public class Problem1 {

    private static class Edge {

        private int to;
        private int id;

        public Edge(int to, int id) {
            this.to = to;
            this.id = id;
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

    private static int n;
    private static int timer;
    private static int[] tin;
    private static int[] fup;
    private static List<List<Edge>> graph;
    private static int countBridges;
    private static boolean[] isBridge;

    private static void solve() {
        try (PrintWriter printWriter = new PrintWriter(System.out)) {

            FastScanner fastScanner = new FastScanner(System.in);
            n = fastScanner.nextInt();
            int m = fastScanner.nextInt();

            fup = new int[n + 1];
            tin = new int[n + 1];
            graph = new ArrayList<>();
            for (int i = 0; i <= n; i++) {
                graph.add(new ArrayList<>());
                tin[i] = 0;
                fup[i] = 0;
            }

            for (int i = 1; i <= m; i++) {
                final int from = fastScanner.nextInt();
                final int to = fastScanner.nextInt();

                graph.get(from).add(new Edge(to, i));
                graph.get(to).add(new Edge(from, i));
            }

            countBridges = 0;

            isBridge = new boolean[m + 1];
            Arrays.fill(isBridge, false);

            findBridges();

            printWriter.println(countBridges);
            for (int i = 1; i <= m && countBridges > 0; i++) {

                if (isBridge[i]) {
                    printWriter.print(i + " ");
                    countBridges--;
                }
            }
        }
    }

    private static void findBridges() {
        timer = 0;
        for (int i = 1; i <= n; i++) {
            if (tin[i] == 0) {
                dfs(i, -1);
            }
        }
    }

    private static void dfs(int nodeId, int prevLinkId) {
        timer++;
        tin[nodeId] = timer;
        fup[nodeId] = timer;

        for (Edge edge : graph.get(nodeId)) {
            if (edge.id == prevLinkId) {
                continue;
            }
            if (tin[edge.to] == 0) {
                dfs(edge.to, edge.id);
                fup[nodeId] = Math.min(fup[nodeId], fup[edge.to]);
            } else {
                fup[nodeId] = Math.min(fup[nodeId], tin[edge.to]);
            }
        }

        if (prevLinkId != -1 && fup[nodeId] == tin[nodeId]) {
            countBridges++;
            isBridge[prevLinkId] = true;
        }
    }

    public static void main(String[] args) {
        solve();
    }
}
