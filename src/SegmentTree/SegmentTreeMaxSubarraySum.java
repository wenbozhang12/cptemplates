package SegmentTree;

public class SegmentTreeMaxSubarraySum {

    class Node {
        long maxSum;
        long leftMax;
        long rightMax;
        long segSum;
    }
    Node[] seg;
    int N;

    public SegmentTreeMaxSubarraySum(int[] arr){
        int n = arr.length;
        N = 1;
        while(N <= n){
            N <<= 1;
        }
        seg = new Node[2*N];
        for(int i = 0; i < 2*N; i++)
            seg[i] = new Node();
        for(int i = 0; i < n; i++)
            update(i, arr[i]);
    }

    Node query(int ql, int qh){
        return query(0, N - 1, 1, ql, qh);
    }

    Node query(int l, int h, int p, int ql, int qh){
        if(h < ql || l > qh)
            return new Node();
        if(l >= ql && h <= qh)
            return seg[p];
        int m = l + (h - l)/2;
        return merge(query(l, m, 2*p, ql, qh), query(m + 1, h, 2*p + 1, ql, qh));
    }

    void update(int p, int v){
        p += N;
        seg[p] = new Node();
        seg[p].leftMax = v;
        seg[p].rightMax = v;
        seg[p].segSum = v;
        seg[p].maxSum = v;
        while(p > 1){
            p >>= 1;
            seg[p] = merge(seg[2*p], seg[2*p+1]);
        }
    }

    Node merge(Node l, Node r){
        var res = new Node();
        res.leftMax = Math.max(l.leftMax, l.segSum + r.leftMax);
        res.rightMax = Math.max(r.rightMax, r.segSum + l.rightMax);
        res.segSum += l.segSum + r.segSum;
        res.maxSum = Math.max(l.rightMax + r.leftMax, Math.max(l.maxSum, r.maxSum));
        return res;
    }
}
