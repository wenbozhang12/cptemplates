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
