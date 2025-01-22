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

    int[][] dirs = {{0,1},{1,0},{0,-1},{-1,0}};
    for(int[] d : dirs){
        int x = d[0] + cur[0];
        int y = d[1] + cur[1];
        if(x >= 0 && x < n && y >= 0 && y < m && grid[x][y] != -1){
            // do stuff
        }
    }
}
