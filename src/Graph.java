import java.util.*;

public class Graph {

    HashMap<Integer, List<Integer>> buildGraph(int[][] edges){
        var g = new HashMap<Integer, List<Integer>>();
        for(var e : edges){
            g.computeIfAbsent(e[0], l -> new ArrayList<>()).add(e[1]);
            g.computeIfAbsent(e[1], l -> new ArrayList<>()).add(e[0]);
        }
        return g;
    }

    int[] findFurthest(int i, HashMap<Integer, List<Integer>> g, int p){
        int[] res = new int[]{i, 0};
        for(var to : g.getOrDefault(i, new ArrayList<>())){
            var ret = findFurthest(to, g, i);
            if(ret[1] >= res[1]){
                res = ret;
                ret[1]++;
            }
        }
        return res;
    }

    boolean findPath(int i, int t, HashMap<Integer, List<Integer>> g, List<Integer> path, int p){
        if(i == t)
            return true;
        for(var to : g.getOrDefault(i, new ArrayList<>())){
            if(to == p)
                continue;
            path.add(to);
            if(findPath(to, t, g, path, i)){
                return true;
            }
            path.removeLast();
        }
        return false;
    }

    HashMap<Integer, List<int[]>> buildGraphWeighted(int[][] edges){
        var g = new HashMap<Integer, List<int[]>>();
        for(var e : edges){
            g.computeIfAbsent(e[0], l -> new ArrayList<>()).add(new int[]{e[1], e[2]});
            g.computeIfAbsent(e[1], l -> new ArrayList<>()).add(new int[]{e[0], e[2]});
        }
        return g;
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

    int[][] dirs = {{0, 1},{1,0},{-1,0},{0,-1}};

    int[] bfs(int s, HashMap<Integer, List<Integer>> g, int n){
        int[] dist = new int[n];
        Arrays.fill(dist, -1);
        var dq = new ArrayDeque<Integer>();
        dq.add(s);
        int depth = 1;
        dist[s] = 0;
        while(!dq.isEmpty()){
            int l = dq.size();
            for(int i = 0; i < l; i++){
                int cur = dq.poll();
                for(var to : g.getOrDefault(cur, new ArrayList<>())){
                    if(dist[to] == -1){
                        dist[to] = depth;
                        dq.add(to);
                    }
                }
            }
            depth++;
        }
        return dist;
    }

    long[] djikstra(int s, HashMap<Integer, List<int[]>> g, int n){
        long[] dist = new long[n];
        Arrays.fill(dist, Long.MAX_VALUE);
        var pq = new PriorityQueue<long[]>((a,b) -> Long.compare(a[0], b[0]));
        pq.add(new long[]{0, s});
        while(!pq.isEmpty()){
            long[] cur = pq.poll();
            if(cur[0] >= dist[(int)cur[1]])
                continue;
            dist[(int)cur[1]] = cur[0];
            for(var to : g.getOrDefault((int)cur[1], new ArrayList<>())){
                pq.add(new long[]{to[1] + cur[0], to[0]});
            }
        }
        return dist;
    }
}
