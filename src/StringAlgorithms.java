import java.util.Arrays;

public class StringAlgorithms {

    int[] lps(String s) {
        int n = s.length();
        int[] lps = new int[n];
        for(int i = 1, j = 0; i < n; i++){
            while(j > 0 && s.charAt(i) != s.charAt(j)){
                j = lps[j - 1];
            }
            if(s.charAt(i) == s.charAt(j)){
                lps[i] = ++j;
            }
        }
        return lps;
    }

    int[] zfunc(String s){
        int n = s.length();
        int l = 0, r = 0;
        int[] z = new int[n];
        for(int i = 1; i < n; i++){
            if(i < r){
                z[i] = Math.min(z[i - l], r - i);
            }
            while(i + z[i] < n && s.charAt(i + z[i]) == s.charAt(z[i])){
                z[i]++;
            }
            if(i + z[i] > r){
                r = i + z[i];
                l = i;
            }
        }
        return z;
    }

    int[] manacher_odd(String s){
        int n = s.length();
        s = "$" + s + "^";
        int[] p = new int[n + 2];
        int l = 0, r = 1;
        for(int i = 1; i <= n; i++) {
            if(i <= r) {
                p[i] = Math.min(r - i, p[l + (r - i)]);
            }
            while(s.charAt(i - p[i]) == s.charAt(i + p[i])) {
                p[i]++;
            }
            if(i + p[i] > r) {
                l = i - p[i];
                r = i + p[i];
            }
        }
        return p;
    }

    int[] sortCyclicShift(String s){
        int n = s.length();
        int alphabet = 26;
        int[] p = new int[n];
        int[] c = new int[n];
        int[] cnt = new int[Math.max(alphabet,n)];
        for (int i = 0; i < n; i++)
            cnt[s.charAt(i) - 'a']++;
        for (int i = 1; i < alphabet; i++)
            cnt[i] += cnt[i-1];
        for (int i = 0; i < n; i++)
            p[--cnt[s.charAt(i) - 'a']] = i;
        c[p[0]] = 0;
        int classes = 1;
        for (int i = 1; i < n; i++) {
            if (s.charAt(p[i]) - 'a' != s.charAt(p[i-1]) - 'a')
                classes++;
            c[p[i]] = classes - 1;
        }
        int[] pn = new int[n];
        for (int h = 0; (1 << h) < n; ++h) {
            int[] cn = new int[n];
            cnt = new int[Math.max(alphabet,n)];
            for (int i = 0; i < n; i++) {
                pn[i] = p[i] - (1 << h);
                if (pn[i] < 0)
                    pn[i] += n;
            }
            for (int i = 0; i < n; i++)
                cnt[c[pn[i]]]++;
            for (int i = 1; i < classes; i++)
                cnt[i] += cnt[i-1];
            for (int i = n-1; i >= 0; i--)
                p[--cnt[c[pn[i]]]] = pn[i];
            cn[p[0]] = 0;
            classes = 1;
            for (int i = 1; i < n; i++) {
                int cur0 = c[p[i]];
                int cur1 = c[(p[i] + (1 << h)) % n];
                int prev0 = c[p[i-1]];
                int prev1 = c[(p[i-1] + (1 << h)) % n];
                if (cur0 != prev0 || cur1 != prev1)
                    ++classes;
                cn[p[i]] = classes - 1;
            }
            c = cn;
        }
        return p;
    }

    public static void main(String[] args) {
        StringAlgorithms s = new StringAlgorithms();
        System.out.println(Arrays.toString(s.sortCyclicShift("baaa")));
    }
}
