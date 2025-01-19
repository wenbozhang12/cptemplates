public class Combinatorics {
    int mod = (int) 1e9 + 7;
    Long[] fac;
    long fac(int i){
        if(i == 0)
            return 1;
        if(fac[i] != null)
            return fac[i];
        return fac[i] = i * fac(i - 1) % mod;
    }

    Long[] powDp;
    long powDp(long b, long e){
        if(powDp[0] != null)
            return powDp[(int)b];
        return powDp[(int)b] = pow(b, mod - 2);
    }
    long pow(long b, long e){
        if(e == 0)
            return 1;
        if(e % 2 == 0){
            return pow(b*b%mod, e/2);
        }
        return b*pow(b*b%mod, e/2)%mod;
    }

    long nCk(int n, int k){
        return fac(n) * pow(fac(k)*fac(n - k)%mod, mod - 2) % mod;
    }


    long[] fact, invFact;
    long[][] prefixC;

    long pow(long base, int exp) {
        long res = 1;
        while(exp > 0) {
            if((exp & 1) == 1) res = (res * base) % mod;
            base = (base * base) % mod;
            exp >>= 1;
        }
        return res;
    }

    void build(int n, int k) {
        fact = new long[n + 1];
        invFact = new long[n + 1];
        fact[0] = 1;
        for(int i = 1; i <= n; i++) {
            fact[i] = fact[i - 1] * i % mod;
        }
        invFact[n] = pow(fact[n], mod - 2);
        for(int i = n - 1; i >= 0; i--) {
            invFact[i] = invFact[i + 1] * (i + 1) % mod;
        }
    }

    long nCr(int n, int r) {
        if(r > n) return 0;
        return fact[n] * invFact[r] % mod * invFact[n - r] % mod;
    }
}
