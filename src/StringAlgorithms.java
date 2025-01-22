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
}
