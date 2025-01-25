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
}
