public class UnionFind {

    int[] par;
    int[] size;
    public UnionFind(int n) {
        par = new int[n];
        size = new int[n];
        for (int i = 0; i < n; i++) {
            par[i] = i;
            size[i] = 1;
        }
    }
    
    int find(int x) {
        while (x != par[x]) {
            par[x] = par[par[x]];
            x = par[x];
        }
        return x;
    }
    void union(int x, int y) {
        int find = find(x);
        int find2 = find(y);
        if (find == find2) {
            return;
        }
        if (size[find] < size[find2]) {
            par[find] = find2;
            size[find2] += size[find];
        }
        else {
            par[find2] = find;
            size[find] += size[find2];
        }
    }
}
