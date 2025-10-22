package SegmentTree;

public class SegmentTreeFFZLazyProp {
    long[] min;
    long[] max;
    long[] lazy;
    int N;

    public SegmentTreeFFZLazyProp(int n){
        N = 1;
        while(N <= n){
            N <<= 1;
        }
        min = new long[2*N];
        max = new long[2*N];
        lazy = new long[2*N];
    }

    void propagate(int p){
        if(lazy[p] != 0){
            min[p] += lazy[p];
            max[p] += lazy[p];
            if(2*p < 2*N){
                lazy[2*p] += lazy[p];
                lazy[2*p + 1] += lazy[p];
            }
            lazy[p] = 0;
        }
    }

    int ffz(int ql, int qh){
        return ffz(0, N - 1, 1, ql, qh);
    }

    int ffz(int l, int h, int p, int ql, int qh){
        propagate(p);
        if(h < ql || l > qh)
            return -1;
        if(max[p] < 0 || min[p] > 0)
            return -1;
        if(l == h)
            return l;
        int m = l + (h - l)/2;
        int ret = ffz(l, m, 2*p, ql, qh);
        if(ret != -1)
            return ret;
        return ffz(m + 1, h, 2*p + 1, ql, qh);
    }

    void add(int ql, int qh, long v){
        add(0, N - 1, 1, ql, qh, v);
    }

    void add(int l, int h, int p, int ql, int qh, long v){
        propagate(p);
        if(h < ql || l > qh)
            return;
        if(l >= ql && h <= qh){
            min[p] += v;
            max[p] += v;
            if(2*p < 2*N){
                lazy[2*p] += v;
                lazy[2*p + 1] += v;
            }
            return;
        }
        int m = l + (h - l)/2;
        add(l, m, 2*p, ql, qh, v);
        add(m + 1, h, 2*p + 1, ql, qh, v);
        min[p] = Math.min(min[2*p], min[2*p+1]);
        max[p] = Math.max(max[2*p], max[2*p+1]);
    }
}
