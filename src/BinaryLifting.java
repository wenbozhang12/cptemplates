import java.util.*;

public class BinaryLifting {

    int[] depth;
    int[][] par;
    HashMap<Integer, List<Integer>> g;
    int N;

    public BinaryLifting(int[][] edges){
        g = buildGraph(edges);
        int n = edges.length + 1;
        depth = new int[n];
        N = 0;
        while((1 << N) <= n)
            N++;
        par = new int[n][N];
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
        for(int to : g.getOrDefault(i, new ArrayList<>())){
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

    HashMap<Integer, List<Integer>> buildGraph(int[][] edges){
        HashMap<Integer, List<Integer>> g = new HashMap<Integer, List<Integer>>();
        for(var e : edges){
            g.computeIfAbsent(e[0] - 1, l -> new ArrayList<Integer>()).add(e[1] - 1);
            g.computeIfAbsent(e[1] - 1, l -> new ArrayList<Integer>()).add(e[0] - 1);
        }
        return g;
    }
}
