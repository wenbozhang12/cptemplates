import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TarjanOfflineLCA {
    HashMap<Integer, List<Integer>> g;
    HashMap<Integer, List<int[]>> qm;
    int[] res;
    int[] anc;
    boolean[] vis;
    DSU ds;
    public TarjanOfflineLCA(int[][] edges, int[][] queries) {
        g = buildGraph(edges);
        int r = 0;
        for(var q : queries) {
            qm.computeIfAbsent(q[0], l -> new ArrayList<>()).add(new int[]{q[1], r});
            qm.computeIfAbsent(q[1], l -> new ArrayList<>()).add(new int[]{q[0], r});
            r++;
        }
        res = new int[r];
        vis = new boolean[g.size()];
        anc = new int[g.size()];
        ds = new DSU(g.size());
        dfs(0);
    }

    void dfs(int i){
        vis[i] = true;
        anc[i] = i;
        for(int to : g.getOrDefault(i, new ArrayList<>())) {
            if(!vis[to]) {
                dfs(to);
                ds.union(i, to);
                anc[ds.find(i)] = i;
            }
        }
        for(int[] other : qm.getOrDefault(i, new ArrayList<>())) {
            if(vis[other[0]]) {
                res[other[1]] = anc[ds.find(other[0])];
            }
        }
    }
    HashMap<Integer, List<Integer>> buildGraph(int[][] edges){
        HashMap<Integer, List<Integer>> g = new HashMap<Integer, List<Integer>>();
        for(var e : edges){
            g.computeIfAbsent(e[0], l -> new ArrayList<Integer>()).add(e[1]);
            g.computeIfAbsent(e[1], l -> new ArrayList<Integer>()).add(e[0]);
        }
        return g;
    }

    public class DSU {
        int[] par;
        int[] size;
        public DSU(int n){
            par = new int[n];
            size = new int[n];
            for(int i = 0; i < n; i++){
                par[i] = i;
                size[i] = 1;
            }
        }

        int find(int x){
            return x == par[x] ? x : (par[x] = find(par[x]));
        }

        void union(int x, int y){
            int px = find(x);
            int py = find(y);
            if(px != py){
                if(size[px] < size[py]){
                    par[py] = px;
                    size[px] += size[py];
                }
                else{
                    par[px] = py;
                    size[py] += size[px];
                }
            }
        }
    }
}
