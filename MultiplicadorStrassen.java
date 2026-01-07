public class MultiplicadorStrassen {

    
    public static long contadorRecursao = 0;

    // Método público que prepara a matriz (Padding) antes da recursão
    public static int[][] multiplicar(int[][] A, int[][] B) {
        contadorRecursao = 0; // Reseta contador
        
        int n = A.length;
        int m = UtilidadesMatriz.proximaPotencia2(n);

        // Adiciona zeros se não for potência de 2
        int[][] A_pad = UtilidadesMatriz.adicionarPadding(A, m);
        int[][] B_pad = UtilidadesMatriz.adicionarPadding(B, m);

        int[][] C_pad = strassenRecursivo(A_pad, B_pad);

        // Remove o padding para entregar o resultado no tamanho correto
        return UtilidadesMatriz.removerPadding(C_pad, n);
    }

    private static int[][] strassenRecursivo(int[][] A, int[][] B) {
        contadorRecursao++; 
        int n = A.length;

        // Caso base: matriz 1x1 
        if (n == 1) {
            int[][] C = new int[1][1];
            C[0][0] = A[0][0] * B[0][0];
            return C;
        }

       
        int metade = n / 2;
        int[][] A11 = UtilidadesMatriz.dividir(A, 0, 0, metade);
        int[][] A12 = UtilidadesMatriz.dividir(A, 0, metade, metade);
        int[][] A21 = UtilidadesMatriz.dividir(A, metade, 0, metade);
        int[][] A22 = UtilidadesMatriz.dividir(A, metade, metade, metade);

        int[][] B11 = UtilidadesMatriz.dividir(B, 0, 0, metade);
        int[][] B12 = UtilidadesMatriz.dividir(B, 0, metade, metade);
        int[][] B21 = UtilidadesMatriz.dividir(B, metade, 0, metade);
        int[][] B22 = UtilidadesMatriz.dividir(B, metade, metade, metade);

        
        int[][] S1 = UtilidadesMatriz.subtrair(B12, B22);
        int[][] P1 = strassenRecursivo(A11, S1);

        int[][] S2 = UtilidadesMatriz.somar(A11, A12);
        int[][] P2 = strassenRecursivo(S2, B22);

        int[][] S3 = UtilidadesMatriz.somar(A21, A22);
        int[][] P3 = strassenRecursivo(S3, B11);

        int[][] S4 = UtilidadesMatriz.subtrair(B21, B11);
        int[][] P4 = strassenRecursivo(A22, S4);

        int[][] S5 = UtilidadesMatriz.somar(A11, A22);
        int[][] S6 = UtilidadesMatriz.somar(B11, B22);
        int[][] P5 = strassenRecursivo(S5, S6);

        int[][] S7 = UtilidadesMatriz.subtrair(A12, A22);
        int[][] S8 = UtilidadesMatriz.somar(B21, B22);
        int[][] P6 = strassenRecursivo(S7, S8);

        int[][] S9 = UtilidadesMatriz.subtrair(A11, A21);
        int[][] S10 = UtilidadesMatriz.somar(B11, B12);
        int[][] P7 = strassenRecursivo(S9, S10);

       
        int[][] C11 = UtilidadesMatriz.somar(UtilidadesMatriz.subtrair(UtilidadesMatriz.somar(P5, P4), P2), P6);
        int[][] C12 = UtilidadesMatriz.somar(P1, P2);
        int[][] C21 = UtilidadesMatriz.somar(P3, P4);
        int[][] C22 = UtilidadesMatriz.somar(UtilidadesMatriz.subtrair(UtilidadesMatriz.somar(P5, P1), P3), P7);

        // Monta a matriz final
        int[][] C = new int[n][n];
        UtilidadesMatriz.juntar(C, C11, 0, 0);
        UtilidadesMatriz.juntar(C, C12, 0, metade);
        UtilidadesMatriz.juntar(C, C21, metade, 0);
        UtilidadesMatriz.juntar(C, C22, metade, metade);

        return C;
    }
}