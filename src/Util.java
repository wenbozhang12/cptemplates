public class Util {

    int max(int... a){
        int res = Integer.MIN_VALUE;
        for(int i : a){
            res = Math.max(res,i);
        }
        return res;
    }

    int min(int... a){
        int res = Integer.MAX_VALUE;
        for(int i : a){
            res = Math.min(res,i);
        }
        return res;
    }

    long gcd(long a, long b){
        if(b == 0) return a;
        return gcd(b, a % b);
    }

    int digitSum(int i){
        int res = 0;
        while(i > 0){
            res += i % 10;
            i /= 10;
        }
        return res;
    }

    long lcm(long a, long b){
        return a /gcd(a, b) *b;
    }

    int[][] dirs = {{0,1},{1,0},{0,-1},{-1,0}};
    void dirs(int[][] grid) {
        int n = grid.length;
        int m = grid[0].length;
        int[] cur = {0,1};
        for(int[] d : dirs){
            int x = d[0] + cur[0];
            int y = d[1] + cur[1];
            if(x >= 0 && x < n && y >= 0 && y < m && grid[x][y] != -1){
                // do stuff
            }
        }
    }
}
