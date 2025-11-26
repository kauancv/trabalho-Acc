package TabelaHash;

import java.util.Random;

public class Main {

    public static void main(String[] args) {

        int N = 100000; // Número de elementos a serem inseridos
        int size = 200003; // Tamanho da tabela hash (de preferência número primo)
        double fatorCarga = (double) N / size; // Cálculo do Fator de Carga

        System.out.println("=== CONFIGURAÇÃO DO BENCHMARK ===");
        System.out.println("N (Elementos): " + N);
        System.out.println("Tamanho da Tabela (M): " + size);
        System.out.printf("Fator de Carga (lambda = N/M): %.4f\n", fatorCarga);

        System.out.println("\n=== GERANDO DADOS ===");
        int[] aleatorio = gerarAleatorio(N);
        int[] ordenado = gerarOrdenado(N);
        int[] quase = gerarQuaseOrdenado(N);

        // ===== TESTE ENCADEAMENTO EXTERNO =====
        System.out.println("\n=========== HASH CHAINING (Encadeamento Externo) ===========");
        testarHashChaining("Aleatório", aleatorio, size, N);
        testarHashChaining("Ordenado", ordenado, size, N);
        testarHashChaining("Quase Ordenado", quase, size, N);

        // ===== TESTE ENDEREÇAMENTO ABERTO (Sondagem Linear) =====
        System.out.println("\n=========== HASH OPEN ADDRESSING (Sondagem Linear) ===========");
        testarHashOpen("Aleatório", aleatorio, size, N);
        testarHashOpen("Ordenado", ordenado, size, N);
        testarHashOpen("Quase Ordenado", quase, size, N);

        System.out.println("\n=== BENCHMARK CONCLUÍDO ===");
    }

    // =============================
    //  GERADORES DE DADOS (Mantidos)
    // =============================

    // ... (Os métodos gerarAleatorio, gerarOrdenado, gerarQuaseOrdenado permanecem os mesmos) ...

    public static int[] gerarAleatorio(int n) {
        Random rand = new Random();
        int[] v = new int[n];
        for (int i = 0; i < n; i++)
            v[i] = rand.nextInt(1_000_000_000) + 1;
        return v;
    }

    public static int[] gerarOrdenado(int n) {
        int[] v = new int[n];
        for (int i = 0; i < n; i++)
            v[i] = i + 1;
        return v;
    }

    public static int[] gerarQuaseOrdenado(int n) {
        int[] v = gerarOrdenado(n);  // começa ordenado
        Random rand = new Random();
        int qtdBaguncar = (int)(n * 0.10); // 10%

        for (int i = 0; i < qtdBaguncar; i++) {
            int pos = rand.nextInt(n);
            v[pos] = rand.nextInt(1_000_000_000) + 1;
        }
        return v;
    }

    // =============================
    //  TESTES HASH CHAINING (Atualizado)
    // =============================

    public static void testarHashChaining(String nome, int[] dados, int size, int N) {

        HashChaining h = new HashChaining(size);
        long inicio, fim;

        // INSERÇÃO: N chaves
        inicio = System.nanoTime();
        for (int x : dados) h.insert(x);
        fim = System.nanoTime();
        double tempoTotalInsercao = (fim - inicio) / 1e6; // ms
        double tempoMedioInsercao = tempoTotalInsercao / N; // ms por operação

        // BUSCA: M chaves (M=N)
        Random rand = new Random();
        int M = dados.length;
        int[] busca = new int[M];

        // M/2 presentes, M/2 aleatórias (ausentes)
        for (int i = 0; i < M/2; i++) busca[i] = dados[i];
        for (int i = M/2; i < M; i++) busca[i] = rand.nextInt(1_000_000_000) + 1;

        inicio = System.nanoTime();
        for (int x : busca) h.search(x);
        fim = System.nanoTime();
        double tempoTotalBusca = (fim - inicio) / 1e6;
        double tempoMedioBusca = tempoTotalBusca / M; // ms por operação

        // REMOÇÃO: K chaves (10% de N)
        int K = dados.length / 10;
        inicio = System.nanoTime();
        for (int i = 0; i < K; i++) h.remove(dados[i]);
        fim = System.nanoTime();
        double tempoTotalRemocao = (fim - inicio) / 1e6;
        double tempoMedioRemocao = tempoTotalRemocao / K; // ms por operação

        System.out.println("\n--- " + nome + " ---");
        System.out.println("Colisões (Total): " + h.collisions);
        System.out.printf("Tempo Total Inserção: %.2f ms\n", tempoTotalInsercao);
        System.out.printf("Tempo Médio Inserção: %.6f ms\n", tempoMedioInsercao);
        System.out.printf("Tempo Total Busca: %.2f ms\n", tempoTotalBusca);
        System.out.printf("Tempo Médio Busca: %.6f ms\n", tempoMedioBusca);
        System.out.printf("Tempo Total Remoção: %.2f ms\n", tempoTotalRemocao);
        System.out.printf("Tempo Médio Remoção: %.6f ms\n", tempoMedioRemocao);
    }

    // =============================
    //  TESTES HASH OPEN ADDRESSING (Atualizado)
    // =============================

    public static void testarHashOpen(String nome, int[] dados, int size, int N) {

        HashOpenAddressing h = new HashOpenAddressing(size);
        long inicio, fim;

        // INSERÇÃO: N chaves
        inicio = System.nanoTime();
        for (int x : dados) h.insert(x);
        fim = System.nanoTime();
        double tempoTotalInsercao = (fim - inicio) / 1e6;
        double tempoMedioInsercao = tempoTotalInsercao / N;

        // BUSCA: M chaves (M=N)
        Random rand = new Random();
        int M = dados.length;
        int[] busca = new int[M];

        // M/2 presentes, M/2 aleatórias (ausentes)
        for (int i = 0; i < M/2; i++) busca[i] = dados[i];
        for (int i = M/2; i < M; i++) busca[i] = rand.nextInt(1_000_000_000) + 1;

        inicio = System.nanoTime();
        for (int x : busca) h.search(x);
        fim = System.nanoTime();
        double tempoTotalBusca = (fim - inicio) / 1e6;
        double tempoMedioBusca = tempoTotalBusca / M;

        // REMOÇÃO: K chaves (10% de N)
        int K = dados.length / 10;
        inicio = System.nanoTime();
        for (int i = 0; i < K; i++) h.remove(dados[i]);
        fim = System.nanoTime();
        double tempoTotalRemocao = (fim - inicio) / 1e6;
        double tempoMedioRemocao = tempoTotalRemocao / K;

        System.out.println("\n--- " + nome + " ---");
        System.out.println("Colisões (Sondagens Adicionais): " + h.collisions);
        System.out.printf("Tempo Total Inserção: %.2f ms\n", tempoTotalInsercao);
        System.out.printf("Tempo Médio Inserção: %.6f ms\n", tempoMedioInsercao);
        System.out.printf("Tempo Total Busca: %.2f ms\n", tempoTotalBusca);
        System.out.printf("Tempo Médio Busca: %.6f ms\n", tempoMedioBusca);
        System.out.printf("Tempo Total Remoção: %.2f ms\n", tempoTotalRemocao);
        System.out.printf("Tempo Médio Remoção: %.6f ms\n", tempoMedioRemocao);
    }
}