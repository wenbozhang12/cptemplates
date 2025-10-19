public class Matrix {
    long[][] mul(long[][] A, long[][] B) {
        int aRows = A.length;
        int aColumns = A[0].length;
        int bRows = B.length;
        int bColumns = B[0].length;

        long[][] C = new long[aRows][bColumns];
        for (int i = 0; i < aRows; i++) {
            for (int j = 0; j < bColumns; j++) {
                for (int k = 0; k < aColumns; k++) {
                    C[i][j] += A[i][k] * B[k][j];
                }
            }
        }
        return C;
    }

    long[][] exp(long[][] A, long e){
        if(e == 1)
            return A;
        if(e % 2 == 0){
            return exp(mul(A, A), e/2);
        }
        return mul(exp(mul(A, A), e/2),A);
    }

    int[][] transpose(int[][] A){
        int[][] C = new int[A[0].length][A.length];
        for (int i = 0; i < A.length; i++) {
            for (int j = 0; j < A[0].length; j++) {
                C[j][i] = A[i][j];
            }
        }
        return C;
    }
}
