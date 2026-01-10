public class Main {
    public static void main(String[] args) {
        
        
        int[] tamanhos = {64, 128, 256, 512}; 
        long seed = 42;

        System.out.println("== Trabalho2 de ACC ==");
        System.out.println("=== BENCHMARK: CLÁSSICO vs STRASSEN ===");
        System.out.printf("%-10s | %-15s | %-15s | %-12s%n", 
            "N", "Clássico (s)", "Strassen (s)", "Recursões");
        System.out.println("______________");

        for (int n : tamanhos) {
       
            int[][] A = UtilidadesMatriz.gerarMatriz(n, seed);
            int[][] B = UtilidadesMatriz.gerarMatriz(n, seed + 1);

    
            long inicioClassico = System.nanoTime();
            MultiplicadorClassico.multiplicar(A, B);
            long fimClassico = System.nanoTime();
            double tempoClassico = (fimClassico - inicioClassico) / 1_000_000_000.0; 

         
            long inicioStrassen = System.nanoTime();
            MultiplicadorStrassen.multiplicar(A, B);
            long fimStrassen = System.nanoTime();
            double tempoStrassen = (fimStrassen - inicioStrassen) / 1_000_000_000.0; 
            
         
            long recursoes = MultiplicadorStrassen.contadorRecursao;

  
            System.out.printf("%-10d | %-15.4f | %-15.4f | %-12d%n", 
                n, tempoClassico, tempoStrassen, recursoes);
            
            
            System.gc();
        }
        System.out.println("");
        System.out.println(" Fim do Benchmark .");
    }
}