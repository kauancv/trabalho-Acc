import java.util.Random;

public class UtilidadesMatriz {

    public static int[][] gerarMatriz(int n, long seed) {
        Random rand = new Random(seed);
        int[][] matriz = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                
                matriz[i][j] = rand.nextInt(10); 
            }
        }
        return matriz;
    }

    // Operações Matemáticas Básicas
    public static int[][] somar(int[][] A, int[][] B) {
        int n = A.length;
        int[][] C = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                C[i][j] = A[i][j] + B[i][j];
        return C;
    }

    public static int[][] subtrair(int[][] A, int[][] B) {
        int n = A.length;
        int[][] C = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                C[i][j] = A[i][j] - B[i][j];
        return C;
    }

    
    public static int[][] dividir(int[][] P, int i, int j, int novoTamanho) {
        int[][] C = new int[novoTamanho][novoTamanho];
        for (int i2 = 0; i2 < novoTamanho; i2++)
            for (int j2 = 0; j2 < novoTamanho; j2++)
                C[i2][j2] = P[i + i2][j + j2];
        return C;
    }

    public static void juntar(int[][] C, int[][] P, int i, int j) {
        int n = P.length;
        for (int i2 = 0; i2 < n; i2++)
            for (int j2 = 0; j2 < n; j2++)
                C[i + i2][j + j2] = P[i2][j2];
    }

    
    public static int proximaPotencia2(int n) {
        int pot = 1;
        while (pot < n) pot *= 2;
        return pot;
    }

    public static int[][] adicionarPadding(int[][] A, int novoTamanho) {
        int[][] novo = new int[novoTamanho][novoTamanho];
        for (int i = 0; i < A.length; i++)
            for (int j = 0; j < A.length; j++)
                novo[i][j] = A[i][j];
        // O Java já inicia posições vazias com 0
        return novo;
    }

    public static int[][] removerPadding(int[][] A, int tamanhoOriginal) {
        int[][] resultado = new int[tamanhoOriginal][tamanhoOriginal];
        for (int i = 0; i < tamanhoOriginal; i++)
            for (int j = 0; j < tamanhoOriginal; j++)
                resultado[i][j] = A[i][j];
        return resultado;
    }
}