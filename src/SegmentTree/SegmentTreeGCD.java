package SegmentTree;

import java.util.*;

public class SegmentTreeGCD {
    long[] seg;
    int N;

    public SegmentTreeGCD(int n){
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
        return gcd(query(l, m, 2*p, ql, qh), query(m + 1, h, 2*p + 1, ql, qh));
    }


    void update(int p, long v){
        p += N;
        seg[p] = v;
        p /= 2;
        while(p > 1){
            seg[p] = gcd(seg[2*p], seg[2*p + 1]);
            p /= 2;
        }
    }

    long gcd(long a, long b){
        return b == 0 ? a : gcd(b, a % b);
    }

}
