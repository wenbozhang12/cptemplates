import java.util.ArrayList;
import java.util.List;

public class Primes {

    List<int[]> getPrimeFactors(int n) {
        List<int[]> factors = new ArrayList<>();
        for(int j = 2; j*j <= n; j++) {
            if(n % j == 0) {
                int[] cur = new int[]{j, 0};
                while (n % j == 0) {
                    cur[1]++;
                    n /= j;
                }
                factors.add(cur);
            }
        }
        if(n > 1) {
            factors.add(new int[]{n, 1});
        }
        return factors;
    }

    boolean[] sieve(int n) {
        boolean[] sieve = new boolean[n + 1];
        sieve[0] = true;
        sieve[1] = true;
        for (int i = 2; i <= n; i++) {
            if (!sieve[i]) {
                for (int j = i * i; j <= n && j > 0; j += i) {
                    sieve[j] = true;
                }
            }
        }
        return sieve;
    }


    List<Integer> getPrimes(int n){
        List<Integer> primes = new ArrayList<>();
        boolean[] sieve = sieve(n);
        for (int i = 2; i <= n; i++) {
            if (!sieve[i]) {
                primes.add(i);
            }
        }
        return primes;
    }
}
