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
}
