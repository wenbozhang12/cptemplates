import java.util.*;

public class BinaryLifting {

    int[] depth;
    int[][] par;
    List<Integer>[] g;
    int N;

    public BinaryLifting(int[][] edges){
        int n = edges.length + 1;
        g = buildAdjacencyList(edges, n);
        depth = new int[n];
        N = 0;
        while((1 << N) <= n)
            N++;
        par = new int[n][N];
        for(int[] i : par)
            Arrays.fill(i, -1);
        dfs(0, -1, 0);
    }

    void dfs(int i, int p, int d){
        depth[i] = d;
        par[i][0] = p;
        for(int j = 1; j < N; j++){
            if(par[i][j - 1] == -1)
                break;
            par[i][j] = par[par[i][j - 1]][j - 1];
        }
        for(int to : g[i]){
            if(to != p)
                dfs(to, i, d + 1);
        }
    }

    int lca(int a, int b){
        if(depth[a] > depth[b]){
            int temp = a;
            a = b;
            b = temp;
        }
        int diff = depth[b] - depth[a];
        for(int i = N - 1; i >= 0; i--){
            if((diff & (1 << i)) > 0){
                b = par[b][i];
            }
        }
        if(b == a)
            return a;
        for(int i = N - 1; i >= 0; i--){
            if(par[a][i] != par[b][i]){
                a = par[a][i];
                b = par[b][i];
            }
        }
        return par[a][0];
    }

    List<Integer>[] buildAdjacencyList(int[][] edges, int n){
        List<Integer>[] adj = new List[n];
        for (int i = 0; i < n; i++) adj[i] = new ArrayList<>();
        for (int[] e : edges) {
            int a = e[0], b = e[1];
            adj[a].add(b);
            adj[b].add(a);
        }
        return adj;
    }
}
