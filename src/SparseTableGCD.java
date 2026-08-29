public class SparseTableGCD {
    int[][] st;
    int[] lg;

    int gcd(int a, int b) {
        while (b != 0) {
            int temp = b;
            b = a % b;
            a = temp;
        }
        return Math.abs(a);
    }

    public SparseTableGCD(int[] arr) {
        int n = arr.length;
        if (n == 0) return;

        int maxLog = (int) (Math.log(n) / Math.log(2)) + 1;

        st = new int[n][maxLog];
        lg = new int[n + 1];

        for (int i = 2; i <= n; i++) {
            lg[i] = lg[i / 2] + 1;
        }

        for (int i = 0; i < n; i++) {
            st[i][0] = arr[i];
        }

        for (int j = 1; j < maxLog; j++) {
            for (int i = 0; i + (1 << j) <= n; i++) {
                st[i][j] = gcd(st[i][j - 1], st[i + (1 << (j - 1))][j - 1]);
            }
        }
    }

    int query(int L, int R) {
        if (L > R) {
            return 0;
        }
        int j = lg[R - L + 1];
        return gcd(st[L][j], st[R - (1 << j) + 1][j]);
    }
}