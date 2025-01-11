import java.util.*;

public class Graph {

    HashMap<Integer, List<Integer>> buildGraph(int[][] edges, int n){
        HashMap<Integer, List<Integer>> g = new HashMap<Integer, List<Integer>>();
        for(var e : edges){
            g.computeIfAbsent(e[0], l -> new ArrayList<Integer>()).add(e[1]);
            g.computeIfAbsent(e[1], l -> new ArrayList<Integer>()).add(e[0]);
        }
        return g;
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
        dist[s] = 0;
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
