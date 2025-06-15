import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LCAEulerTour {
    List<Integer>[] adj;
    int[] first;
    int[] depth;
    List<Integer> path;
    SegmentTreeMin lcaTree;
    public LCAEulerTour(int[][] edges) {
        int n = edges.length + 1;
        adj = buildAdjacencyList(edges, n);
        first = new int[n];
        depth = new int[n];
        path = new ArrayList<>();
        dfs(0, - 1, 1);
        lcaTree = new SegmentTreeMin(path);
    }

    void dfs(int i, int p, int d){
        first[i] = path.size();
        path.add(i);
        depth[i] = d;
        for(int to : adj[i]){
            if(to != p){
                dfs(to, i, d+1);
                path.add(i);
            }
        }
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

    public static void main(String[] args) {
        var test = new LCAEulerTour(new int[][]{{0,2},{0, 1}, {0,3}, {1,4}, {1,5}, {4,6}});
        System.out.println(test.lcaTree.query(0, 2));
        System.out.println(test.lcaTree.query(1, 1));
        System.out.println(test.lcaTree.query(3, 4));
        System.out.println(test.lcaTree.query(6, 5));
    }

}
