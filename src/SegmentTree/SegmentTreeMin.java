package SegmentTree;

import java.util.Arrays;

public class SegmentTreeMin {
    long[] seg;
    int N;

    public SegmentTreeMin(int n){
        N = 1;
        while(N <= n){
            N <<= 1;
        }
        seg = new long[2*N];
    }

    public SegmentTreeMin(int[] nums){
        int n = nums.length;
        N = 1;
        while(N <= n){
            N <<= 1;
        }
        seg = new long[2*N];
        Arrays.fill(seg, Long.MAX_VALUE);
        for(int i = 0; i < n; i++){
            seg[i + N] = nums[i];
        }
        for(int i = N - 1; i > 0; i--){
            seg[i] = Math.min(seg[2*i], seg[2*i+1]);
        }
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
        return Math.min(query(l, m, 2*p, ql, qh), query(m + 1, h, 2*p + 1, ql, qh));
    }


    void update(int p, long v){
        p += N;
        seg[p] = v;
        while(p > 1){
            p /= 2;
            seg[p] = Math.min(seg[2*p], seg[2*p + 1]);
        }
    }

}
