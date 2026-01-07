public class Main {
    public static void main(String[] args) {
       
        int[] tamanhos = {64, 128, 256, 512, 1024}; 
        long seed = 42; 
        System.out.println("=== BENCHMARK: CLÁSSICO vs STRASSEN ===");
        System.out.printf("%-10s | %-15s | %-15s | %-12s%n", 
            "N", "Clássico (s)", "Strassen (s)", "Recursões");
        System.out.println("-------------------------------------------------------------");

        for (int n : tamanhos) {
            // Gera matrizes
            int[][] A = UtilidadesMatriz.gerarMatriz(n, seed);
            int[][] B = UtilidadesMatriz.gerarMatriz(n, seed + 1);

            // --- Teste Clássico ---
            long inicioClassico = System.nanoTime();
            MultiplicadorClassico.multiplicar(A, B);
            long fimClassico = System.nanoTime();
            double tempoClassico = (fimClassico - inicioClassico) / 1_000_000_000.0; // Segundos

            // --- Teste Strassen ---
            long inicioStrassen = System.nanoTime();
            MultiplicadorStrassen.multiplicar(A, B);
            long fimStrassen = System.nanoTime();
            double tempoStrassen = (fimStrassen - inicioStrassen) / 1_000_000_000.0; // Segundos
            
            // Pega o número de recursões
            long recursoes = MultiplicadorStrassen.contadorRecursao;

            // Imprime linha da tabela
            System.out.printf("%-10d | %-15.4f | %-15.4f | %-12d%n", 
                n, tempoClassico, tempoStrassen, recursoes);
            
            // Limpa memória para não afetar o próximo teste
            System.gc();
        }
        System.out.println("-------------------------------------------------------------");
        System.out.println("Benchmark finalizado.");
    }
}