package SegmentTree;

public class SegmentTreeMaxLazyProp {
    long[] seg;
    long[] lazy;
    int N;

    public SegmentTreeMaxLazyProp(int n){
        N = 1;
        while(N <= n){
            N <<= 1;
        }
        seg = new long[2*N];
        lazy = new long[2*N];
    }

    long query(int ql, int qh){
        return query(0, N - 1, 1, ql, qh);
    }

    long query(int l, int h, int p, int ql, int qh){
        propagate(p);
        if(h < ql || l > qh)
            return 0;
        if(l >= ql && h <= qh)
            return seg[p];
        int m = l + (h - l)/2;
        return Math.max(query(l, m, 2*p, ql, qh), query(m + 1, h, 2*p + 1, ql, qh));
    }

    void add(int ql, int qh, int v){
        add(0, N - 1, 1, ql, qh, v);
    }

    void add(int l, int h, int p, int ql, int qh, int v){
        propagate(p);
        if(h < ql || l > qh)
            return;
        if(l >= ql && h <= qh){
            seg[p] += v;
            if(2*p + 1 < 2*N){
                lazy[2*p] += v;
                lazy[2*p+1] += v;
            }
            return;
        }
        int m = l + (h - l)/2;
        add(l, m, 2*p, ql, qh, v);
        add(m + 1, h, 2*p + 1, ql, qh, v);
        seg[p] = Math.max(seg[2*p], seg[2*p + 1]);
    }

    private void propagate(int p) {
        if(lazy[p] != 0){
            seg[p] += lazy[p];
            if(2*p + 1 < 2*N){
                lazy[2*p] += lazy[p];
                lazy[2*p+1] += lazy[p];
            }
            lazy[p] = 0;
        }
    }
}
