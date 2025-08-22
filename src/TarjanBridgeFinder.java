import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class TarjanBridgeFinder {
    boolean[] vis;
    int[] tin;
    int[] tout;
    int t;
    int[] low;
    List<int[]>[] adj;
    HashSet<Integer> bridges;
    public TarjanBridgeFinder(List<int[]>[] adj, int n) {
        this.adj = adj;
        vis = new boolean[n];
        tin = new int[n];
        tout = new int[n];
        low = new int[n];
        t = 1;
        bridges = new HashSet<>();
        dfs(0, -1);
    }
    HashSet<Integer> getBridges() {
        return bridges;
    }

    void dfs(int v, int p) {
        vis[v] = true;
        tin[v] = low[v] = t++;
        boolean parent_skipped = false;
        for (var to : adj[v]) {
            if (to[0] == p && !parent_skipped) {
                parent_skipped = true;
                continue;
            }
            if (vis[to[0]]) {
                low[v] = Math.min(low[v], tin[to[0]]);
            } else {
                dfs(to[0], v);
                low[v] = Math.min(low[v], low[to[0]]);
                if (low[to[0]] > tin[v])
                    bridges.add(to[1]);
            }
        }
    }
}
