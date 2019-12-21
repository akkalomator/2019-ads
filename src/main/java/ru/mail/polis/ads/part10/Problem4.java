package ru.mail.polis.ads.part10;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

/**
 * submission - https://www.e-olymp.com/ru/submissions/6401246
  */
public class Problem4 {

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

    private static int[] colors;
    private static List<Integer>[] graphT;
    private static List<Integer>[] graph;
    private static Set<Integer>[] colorsComb;
    private static boolean[] visited;

    private static void solve(final FastScanner in, final PrintWriter out) {
        int n = in.nextInt(), m = in.nextInt();

        graph = new List[n + 1];
        graphT = new List[n + 1];
        colorsComb = new Set[n + 1];

        for (int i = 1; i <= n; i++) {
            graph[i] = new ArrayList<>();
            graphT[i] = new ArrayList<>();
            colorsComb[i] = new HashSet<>();
        }

        for (int i = 1; i <= m; i++) {
            int a = in.nextInt(), b = in.nextInt();
            graph[a].add(b);
            graphT[b].add(a);
        }

        visited = new boolean[n + 1];
        final ArrayList<Integer> sorted = new ArrayList<>(n + 1);
        for (int i = 1; i <= n; ++i) {
            if (!visited[i]) {
                dfs1(i, sorted);
            }
        }

        int counter = 0;
        colors = new int[n + 1];
        for (int i = n - 1; i >= 0; --i) {
            if (visited[sorted.get(i)]) {
                ++counter;
                dfs2(sorted.get(i), counter);
            }
        }

        int result = 0;
        for (int i = 1; i <= n; ++i) {
            result += colorsComb[i].size();
        }
        out.println(result);
    }

    static void dfs1(int current, ArrayList<Integer> sorted) {
        visited[current] = true;
        for (Integer integer : graph[current]) {
            if (!visited[integer]) {
                dfs1(integer, sorted);
            }
        }
        sorted.add(current);
    }


    static void dfs2(int current, int currentColor) {
        visited[current] = false;
        colors[current] = currentColor;
        for (Integer integer : graphT[current]) {
            if (visited[integer]) {
                dfs2(integer, currentColor);
            } else if (colors[integer] != currentColor) {
                colorsComb[currentColor].add(colors[integer]);
            }
        }
    }

    public static void main(final String[] arg) {
        final FastScanner in = new FastScanner(System.in);
        try (PrintWriter out = new PrintWriter(System.out)) {
            solve(in, out);
        }
    }

}
