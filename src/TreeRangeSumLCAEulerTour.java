import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class TreeRangeSumLCAEulerTour {
    List<Integer>[] adj;
    int[] first;
    int[] depth;
    List<Integer> path;
    SegmentTreeMin lcaTree;
    List<Integer>[] edgeIds;
    List<Integer> edgesPath;
    int[][] edges;
    HashMap<Integer, Integer>[] idMap;
    public TreeRangeSumLCAEulerTour(int[][] edges) {
        int n = edges.length + 1;
        this.edges = edges;
        edgeIds = new List[n];
        idMap = new HashMap[n];
        for (int i = 0; i < n; i++) {
            edgeIds[i] = new ArrayList<>();
            idMap[i] = new HashMap<>();
        }
        adj = buildAdjacencyList(edges, n);
        first = new int[n];
        depth = new int[n];
        path = new ArrayList<>();
        edgesPath = new ArrayList<>();
        dfs(0, 1);
        lcaTree = new SegmentTreeMin(path);
        queryPrepare(n);
    }

    void dfs(int i, int d){
        first[i] = path.size();
        path.add(i);
        depth[i] = d;
        for(int j = 0; j < adj[i].size(); j++){
            int to = adj[i].get(j);
            if(depth[to] == 0){
                edgesPath.add(edgeIds[i].get(j));
                dfs(to, d+1);
                edgesPath.add(edgeIds[i].get(j));
                path.add(i);
            }
        }
    }

    List<Integer>[] buildAdjacencyList(int[][] edges, int n){
        List<Integer>[] adj = new List[n];
        for (int i = 0; i < n; i++) adj[i] = new ArrayList<>();
        for (int i = 0; i < edges.length; i++) {
            int a = edges[i][0], b = edges[i][1];
            adj[a].add(b);
            adj[b].add(a);
            edgeIds[a].add(i);
            edgeIds[b].add(i);
            idMap[a].put(b, i);
            idMap[b].put(a, i);
        }
        return adj;
    }

    int[] firstEdge;
    int[] lastEdge;
    SegmentTreeRangeSum firstTree;
    SegmentTreeRangeSum lastTree;
    void queryPrepare(int n){
        firstEdge = new int[n - 1];
        lastEdge = new int[n - 1];
        Arrays.fill(firstEdge, -1);
        Arrays.fill(lastEdge, -1);
        for(int i = 0; i < edgesPath.size(); i++){
            if(firstEdge[edgesPath.get(i)] == -1){
                firstEdge[edgesPath.get(i)] = i;
            }
            else if(lastEdge[edgesPath.get(i)] == -1){
                lastEdge[edgesPath.get(i)] = i;
            }
        }
        firstTree = new SegmentTreeRangeSum(edgesPath.size());
        lastTree = new SegmentTreeRangeSum(edgesPath.size());
        for(int i = 0; i < n - 1; i++){
            firstTree.update(firstEdge[i], edges[edgesPath.get(i)][2]);
            lastTree.update(lastEdge[i], edges[edgesPath.get(i)][2]);
        }
    }

    long query(int a, int b){
        int lca = lcaTree.query(a, b);
        return firstTree.query(first[lca], first[a] - 1) - lastTree.query(first[lca], first[a] - 1)
                + firstTree.query(first[lca], first[b] - 1) - lastTree.query(first[lca], first[b] - 1);
    }

    void update(int a, int b, int v){
        int i = idMap[a].get(b);
        firstTree.update(firstEdge[i], v);
        lastTree.update(lastEdge[i], v);
    }


    public class SegmentTreeMin {
        int[] seg;
        int N;

        public SegmentTreeMin(List<Integer> path){
            int n = path.size();
            N = 1;
            while(N <= n){
                N <<= 1;
            }
            seg = new int[2*N];
            Arrays.fill(seg, -1);
            for(int i = 0; i < n; i++){
                seg[i + N] = path.get(i);
            }
            for(int i = N - 1; i > 0; i--){
                if(seg[2*i] == -1)
                    seg[i] = seg[2*i+1];
                else if(seg[2*i+1] == -1)
                    seg[i] = seg[2*i];
                else
                    seg[i] = depth[seg[2*i]] < depth[seg[2*i + 1]] ? seg[2*i] : seg[2*i + 1];
            }
        }

        int query(int ql, int qh){
            if(first[ql] > first[qh]){
                int temp = ql;
                ql = qh;
                qh = temp;
            }
            return query(0, N - 1, 1, first[ql], first[qh]);
        }

        int query(int l, int h, int p, int ql, int qh){
            if(h < ql || l > qh)
                return -1;
            if(l >= ql && h <= qh)
                return seg[p];
            int m = l + (h - l)/2;
            int le = query(l, m, 2*p, ql, qh), ri = query(m + 1, h, 2*p + 1, ql, qh);
            if(le == -1)
                return ri;
            if(ri == -1)
                return le;
            return depth[le] < depth[ri] ? le : ri;
        }

    }

    public class SegmentTreeRangeSum {
        long[] seg;
        int N;

        public SegmentTreeRangeSum(int n){
            N = 1;
            while(N <= n){
                N <<= 1;
            }
            seg = new long[2*N];
        }

        long query(int ql, int qh){
            return query(0, N - 1, 1, ql, qh);
        }

        long query(int l, int h, int p, int ql, int qh){
            if(h < ql || l > qh)
                return 0;
            if(l >= ql && h <= qh)
                return seg[p];
            int m = l + (h - l)/2;
            return query(l, m, 2*p, ql, qh) + query(m + 1, h, 2*p + 1, ql, qh);
        }


        void update(int p, long v){
            p += N;
            seg[p] = v;
            while(p > 1){
                p /= 2;
                seg[p] = seg[2*p] + seg[2*p + 1];
            }
        }

    }

    public static void main(String[] args) {
        var test = new TreeRangeSumLCAEulerTour(new int[][]{{0, 1, 1}, {1, 2, 1}, {0, 3, 1}});
        System.out.println(test.query(2, 3));
        test.update(0, 3, 0);
        System.out.println(test.query(2, 3));
    }

}