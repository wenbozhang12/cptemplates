import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SumOverSubsets {

    int[] submaskCounts(int[] nums){
        int max = 0;
        for(int i : nums){
            max = Math.max(max, i);
        }
        int N = 0;
        while((1 << N) <= max){
            N++;
        }

        int[] dp = new int[1 << N];
        for(int i : nums){
            dp[i]++;
        }

        for(int i = 0; i < N; i++){
            for(int mask = 0; mask < (1 << N); mask++){
                if((mask & (1 << i)) != 0){
                    dp[mask] += dp[mask ^ (1 << i)];
                }
            }
        }
        return  dp;
    }
}
