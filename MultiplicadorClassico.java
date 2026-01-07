public class MultiplicadorClassico {
    //método tradicional com três laços (O(n^3)).

    public static int[][] multiplicar(int[][] A, int[][] B) {
        int n = A.length;
        int[][] C = new int[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                int soma = 0;
                for (int k = 0; k < n; k++) {
                    soma += A[i][k] * B[k][j];
                }
                C[i][j] = soma;
            }
        }
        return C;
    }
}