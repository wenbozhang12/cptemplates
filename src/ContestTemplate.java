import java.lang.*;
import java.util.*;

public class ContestTemplate {

    static List<int[]>[] adj;
    static HashSet<Integer> bridges;
    public static void main(String[] args) {
        var in = new ContestScanner();
        var out = new ContestPrinter();
        int t = in.nextInt();
        for(int i = 0; i < t; i++){
            int n = in.nextInt();
            int m = in.nextInt();
            int[][] edges = new int[n][2];
            for(int j = 0; j < m; j++){
                edges[j][0] = in.nextInt() - 1;
                edges[j][1] = in.nextInt() - 1;
            }
            int q = in.nextInt();
            int[] qs = new int[q];
            for(int j = 0; j < q; j++){
                qs[j] = in.nextInt();
            }
            adj = buildAdjacencyList(edges, n);
            var tf = new TarjanBridgeFinder(adj, n);
            bridges = tf.getBridges();
            var path = new HashSet<Integer>();
            dfs(0, path, new HashSet<>(), n - 1);
            if(path.isEmpty()){
                out.println(-1);
                continue;
            }
            var dq = new ArrayDeque<Integer>();
            int[] min = new int[n];
            Arrays.fill(min, Integer.MAX_VALUE);
            boolean[] vis = new boolean[n];
            for(int j: path){
                vis[edges[j][0]] = true;
                vis[edges[j][1]] = true;
                min[edges[j][0]] = j;
                min[edges[j][1]] = j;
                dq.add(edges[j][0]);
                dq.add(edges[j][1]);
            }
            while(!dq.isEmpty()){
                int s = dq.size();
                HashSet<Integer> vislevel = new HashSet<>();
                for(int j = 0; j < s; j++) {
                    var cur = dq.poll();
                    for (var to : adj[cur]) {
                        if (!vis[to[0]]) {
                            min[to[0]] = Math.min(min[to[0]], min[cur]);
                            if(!vislevel.contains(to[0])) {
                                dq.add(to[0]);
                                vislevel.add(to[0]);
                            }
                        }
                    }
                }
                for(int j : vislevel)
                    vis[j] = true;
            }
            for(int j: qs){
                out.print(min[j - 1] + 1);
                out.print(" ");
            }
            out.println();
        }
        out.flush();
        out.close();
    }

    static boolean dfs(int cur, HashSet<Integer> path, HashSet<Integer> vis, int t){
        if(cur == t)
            return true;
        for(var to : adj[cur]){
            if(!vis.contains(to[0])){
                vis.add(to[0]);
                if(bridges.contains(to[1])){
                    path.add(to[1]);
                }
                if(dfs(to[0], path, vis, t))
                    return true;
                if(bridges.contains(to[1])){
                    path.remove(to[1]);
                }
            }
        }
        return false;
    }

    static List<int[]>[] buildAdjacencyList(int[][] edges, int n){
        List<int[]>[] adj = new List[n];
        for (int i = 0; i < n; i++) adj[i] = new ArrayList<>();
        for (int i = 0; i < edges.length; i++) {
            int[] e = edges[i];
            int a = e[0], b = e[1];
            adj[a].add(new int[]{b, i});
            adj[b].add(new int[]{a, i});
        }
        return adj;
    }


    public static class TarjanBridgeFinder {
        boolean[] vis;
        int[] tin;
        int[] tout;
        int t;
        int[] low;
        List<int[]>[] adj;
        HashSet<Integer> bridges;
        public TarjanBridgeFinder(List<int[]>[] adj, int n) {
            this.adj = adj;
            vis = new boolean[n];
            tin = new int[n];
            tout = new int[n];
            low = new int[n];
            t = 1;
            bridges = new HashSet<>();
            dfs(0, -1);
        }
        HashSet<Integer> getBridges() {
            return bridges;
        }

        void dfs(int v, int p) {
            vis[v] = true;
            tin[v] = low[v] = t++;
            boolean parent_skipped = false;
            for (var to : adj[v]) {
                if (to[0] == p && !parent_skipped) {
                    parent_skipped = true;
                    continue;
                }
                if (vis[to[0]]) {
                    low[v] = Math.min(low[v], tin[to[0]]);
                } else {
                    dfs(to[0], v);
                    low[v] = Math.min(low[v], low[to[0]]);
                    if (low[to[0]] > tin[v])
                        bridges.add(to[1]);
                }
            }
        }
    }


    static class ContestPrinter extends java.io.PrintWriter{
        public ContestPrinter(java.io.PrintStream stream){
            super(stream);
        }
        public ContestPrinter(java.io.File file) throws java.io.FileNotFoundException{
            super(new java.io.PrintStream(file));
        }
        public ContestPrinter(){
            super(System.out);
        }

        private static String dtos(double x, int n) {
            StringBuilder sb = new StringBuilder();
            if(x < 0){
                sb.append('-');
                x = -x;
            }
            x += Math.pow(10, -n)/2;
            sb.append((long)x);
            sb.append(".");
            x -= (long)x;
            for(int i = 0;i < n;i++){
                x *= 10;
                sb.append((int)x);
                x -= (int)x;
            }
            return sb.toString();
        }

        @Override
        public void print(float f){
            super.print(dtos(f, 20));
        }
        @Override
        public void println(float f){
            super.println(dtos(f, 20));
        }
        @Override
        public void print(double d){
            super.print(dtos(d, 20));
        }
        @Override
        public void println(double d){
            super.println(dtos(d, 20));
        }



        public void printArray(int[] array, String separator){
            int n = array.length;
            if(n==0){
                super.println();
                return;
            }
            for(int i=0; i<n-1; i++){
                super.print(array[i]);
                super.print(separator);
            }
            super.println(array[n-1]);
        }
        public void printArray(int[] array){
            this.printArray(array, " ");
        }
        public void printArray(int[] array, String separator, java.util.function.IntUnaryOperator map){
            int n = array.length;
            if(n==0){
                super.println();
                return;
            }
            for(int i=0; i<n-1; i++){
                super.print(map.applyAsInt(array[i]));
                super.print(separator);
            }
            super.println(map.applyAsInt(array[n-1]));
        }
        public void printArray(int[] array, java.util.function.IntUnaryOperator map){
            this.printArray(array, " ", map);
        }

        public void printArray(long[] array, String separator){
            int n = array.length;
            if(n==0){
                super.println();
                return;
            }
            for(int i=0; i<n-1; i++){
                super.print(array[i]);
                super.print(separator);
            }
            super.println(array[n-1]);
        }
        public void printArray(long[] array){
            this.printArray(array, " ");
        }
        public void printArray(long[] array, String separator, java.util.function.LongUnaryOperator map){
            int n = array.length;
            if(n==0){
                super.println();
                return;
            }
            for(int i=0; i<n-1; i++){
                super.print(map.applyAsLong(array[i]));
                super.print(separator);
            }
            super.println(map.applyAsLong(array[n-1]));
        }
        public void printArray(long[] array, java.util.function.LongUnaryOperator map){
            this.printArray(array, " ", map);
        }
        public <T> void printArray(T[] array, String separator){
            int n = array.length;
            if(n==0){
                super.println();
                return;
            }
            for(int i=0; i<n-1; i++){
                super.print(array[i]);
                super.print(separator);
            }
            super.println(array[n-1]);
        }
        public <T> void printArray(T[] array){
            this.printArray(array, " ");
        }
        public <T> void printArray(T[] array, String separator, java.util.function.UnaryOperator<T> map){
            int n = array.length;
            if(n==0){
                super.println();
                return;
            }
            for(int i=0; i<n-1; i++){
                super.print(map.apply(array[i]));
                super.print(separator);
            }
            super.println(map.apply(array[n-1]));
        }
        public <T> void printArray(T[] array, java.util.function.UnaryOperator<T> map){
            this.printArray(array, " ", map);
        }
    }

    static class ContestScanner {
        private final java.io.InputStream in;
        private final byte[] buffer = new byte[1024];
        private int ptr = 0;
        private int buflen = 0;

        private static final long LONG_MAX_TENTHS = 922337203685477580L;
        private static final int LONG_MAX_LAST_DIGIT = 7;
        private static final int LONG_MIN_LAST_DIGIT = 8;

        public ContestScanner(java.io.InputStream in){
            this.in = in;
        }
        public ContestScanner(java.io.File file) throws java.io.FileNotFoundException {
            this(new java.io.BufferedInputStream(new java.io.FileInputStream(file)));
        }
        public ContestScanner(){
            this(System.in);
        }

        private boolean hasNextByte() {
            if (ptr < buflen) {
                return true;
            }else{
                ptr = 0;
                try {
                    buflen = in.read(buffer);
                } catch (java.io.IOException e) {
                    e.printStackTrace();
                }
                if (buflen <= 0) {
                    return false;
                }
            }
            return true;
        }
        private int readByte() {
            if (hasNextByte()) return buffer[ptr++]; else return -1;
        }
        private static boolean isPrintableChar(int c) {
            return 33 <= c && c <= 126;
        }
        public boolean hasNext() {
            while(hasNextByte() && !isPrintableChar(buffer[ptr])) ptr++;
            return hasNextByte();
        }
        public String next() {
            if (!hasNext()) throw new java.util.NoSuchElementException();
            StringBuilder sb = new StringBuilder();
            int b = readByte();
            while(isPrintableChar(b)) {
                sb.appendCodePoint(b);
                b = readByte();
            }
            return sb.toString();
        }

        public long nextLong() {
            if (!hasNext()) throw new java.util.NoSuchElementException();
            long n = 0;
            boolean minus = false;
            int b = readByte();
            if (b == '-') {
                minus = true;
                b = readByte();
            }
            if (b < '0' || '9' < b) {
                throw new NumberFormatException();
            }
            while (true) {
                if ('0' <= b && b <= '9') {
                    int digit = b - '0';
                    if (n >= LONG_MAX_TENTHS) {
                        if (n == LONG_MAX_TENTHS) {
                            if (minus) {
                                if (digit <= LONG_MIN_LAST_DIGIT) {
                                    n = -n * 10 - digit;
                                    b = readByte();
                                    if (!isPrintableChar(b)) {
                                        return n;
                                    } else if (b < '0' || '9' < b) {
                                        throw new NumberFormatException(
                                                String.format("%d%s... is not number", n, Character.toString(b))
                                        );
                                    }
                                }
                            } else {
                                if (digit <= LONG_MAX_LAST_DIGIT) {
                                    n = n * 10 + digit;
                                    b = readByte();
                                    if (!isPrintableChar(b)) {
                                        return n;
                                    } else if (b < '0' || '9' < b) {
                                        throw new NumberFormatException(
                                                String.format("%d%s... is not number", n, Character.toString(b))
                                        );
                                    }
                                }
                            }
                        }
                        throw new ArithmeticException(
                                String.format("%s%d%d... overflows long.", minus ? "-" : "", n, digit)
                        );
                    }
                    n = n * 10 + digit;
                }else if(b == -1 || !isPrintableChar(b)){
                    return minus ? -n : n;
                }else{
                    throw new NumberFormatException();
                }
                b = readByte();
            }
        }
        public int nextInt() {
            long nl = nextLong();
            if (nl < Integer.MIN_VALUE || nl > Integer.MAX_VALUE) throw new NumberFormatException();
            return (int) nl;
        }
        public double nextDouble() {
            return Double.parseDouble(next());
        }

        public long[] nextLongArray(int length){
            long[] array = new long[length];
            for(int i=0; i<length; i++) array[i] = this.nextLong();
            return array;
        }
        public long[] nextLongArray(int length, java.util.function.LongUnaryOperator map){
            long[] array = new long[length];
            for(int i=0; i<length; i++) array[i] = map.applyAsLong(this.nextLong());
            return array;
        }
        public int[] nextIntArray(int length){
            int[] array = new int[length];
            for(int i=0; i<length; i++) array[i] = this.nextInt();
            return array;
        }
        public int[] nextIntArray(int length, java.util.function.IntUnaryOperator map){
            int[] array = new int[length];
            for(int i=0; i<length; i++) array[i] = map.applyAsInt(this.nextInt());
            return array;
        }
        public double[] nextDoubleArray(int length){
            double[] array = new double[length];
            for(int i=0; i<length; i++) array[i] = this.nextDouble();
            return array;
        }
        public double[] nextDoubleArray(int length, java.util.function.DoubleUnaryOperator map){
            double[] array = new double[length];
            for(int i=0; i<length; i++) array[i] = map.applyAsDouble(this.nextDouble());
            return array;
        }

        public long[][] nextLongMatrix(int height, int width){
            long[][] mat = new long[height][width];
            for(int h=0; h<height; h++) for(int w=0; w<width; w++){
                mat[h][w] = this.nextLong();
            }
            return mat;
        }
        public int[][] nextIntMatrix(int height, int width){
            int[][] mat = new int[height][width];
            for(int h=0; h<height; h++) for(int w=0; w<width; w++){
                mat[h][w] = this.nextInt();
            }
            return mat;
        }
        public double[][] nextDoubleMatrix(int height, int width){
            double[][] mat = new double[height][width];
            for(int h=0; h<height; h++) for(int w=0; w<width; w++){
                mat[h][w] = this.nextDouble();
            }
            return mat;
        }

        public char[][] nextCharMatrix(int height, int width){
            char[][] mat = new char[height][width];
            for(int h=0; h<height; h++){
                String s = this.next();
                for(int w=0; w<width; w++){
                    mat[h][w] = s.charAt(w);
                }
            }
            return mat;
        }
    }
}

