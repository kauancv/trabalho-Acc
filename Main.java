import java.util.Random;
import TabelaHash.TabelaHashEncadeamento;
import TabelaHash.TabelaHashEnderecamentoAberto;
import Arvore_AVL.ArvoreAVL;
import Arvore_binaria.ArvoreBinariaBusca;

public class Main {
    public static void main(String[] args) {

       
       
        int N = 100000; // Carga de trabalho padrão (Hash e AVL aguentam bem)
        int size = 200003; // Tamanho da tabela hash 
        double fatorCarga = (double) N / size;

        System.out.println("CONFIGURAÇÃO DO BENCHMARK");
        System.out.println("N (Padrão): " + N);
        System.out.println("Tamanho da Tabela Hash (M): " + size);
        System.out.printf("Fator de Carga (lambda): %.4f\n", fatorCarga);

        System.out.println("\n=== GERANDO DADOS PRINCIPAIS ===");
        // Dados para Hash e AVL (que suportam 100 mil)
        int[] aleatorio = gerarAleatorio(N);
        int[] ordenado = gerarOrdenado(N);
        int[] quase = gerarQuaseOrdenado(N);

   

        //TESTES TABELA HASH
        System.out.println("\n INICIANDO TESTES: TABELAS HASH");

        System.out.println("\n TabelaHashEncadeamento (Encadeamento Externo) ");
        testarHashChaining("Aleatório", aleatorio, size, N);
        testarHashChaining("Ordenado", ordenado, size, N);
        testarHashChaining("Quase Ordenado", quase, size, N);

        // HASH OPEN ADDRESSING
        System.out.println("\n TabelaHashEnderecamentoAberto (Sondagem Linear)");
        testarHashOpen("Aleatório", aleatorio, size, N);
        testarHashOpen("Ordenado", ordenado, size, N);
        testarHashOpen("Quase Ordenado", quase, size, N);

      
        //TESTES ÁRVORES
        System.out.println("\n\n INICIANDO TESTES: ÁRVORES");


        System.out.println("\n ÁRVORE AVL (Balanceada) ");
        // AVL robusta, usa o N total (100.000)
        testarAVL("Aleatório", aleatorio, N);
        testarAVL("Ordenado", ordenado, N);
        testarAVL("Quase Ordenado", quase, N);

        // ARVORE BINÁRIA DE BUSCA (ABB)
        System.out.println("\n ÁRVORE BINÁRIA DE BUSCA (Sem Balanceamento)");
        
        //(Geralmente funciona com 100k pois a árvore fica equilibrada)
        testarABB("Aleatório", aleatorio, N);
        
        //Onde a ABB falha
        //  redução estratégica para conseguir medir o desempenho sem travar 
        int nPequeno = 20000; // 
        
        System.out.println("\n[AVISO] foi reduzido N de " + N + " para " + nPequeno + " nos testes ordenados da ABB.");
        System.out.println("Motivo: A ABB degenera em lista encadeada, causando estouro de pilha (StackOverflow) com o N grande.");

        // Geramos vetores menores apenas para este teste
        int[] ordenadoPeq = gerarOrdenado(nPequeno);
        int[] quasePeq = gerarQuaseOrdenado(nPequeno);

        testarABB("Ordenado (N Reduzido)", ordenadoPeq, nPequeno);
        testarABB("Quase Ordenado (N Reduzido)", quasePeq, nPequeno);

        System.out.println("\n FIM - BENCHMARK CONCLUÍDO COM SUCESSO");
    }

 
    //  GERADOR DE DADOS
    public static int[] gerarAleatorio(int n) {
        Random rand = new Random();
        int[] v = new int[n];
        for (int i = 0; i < n; i++) v[i] = rand.nextInt(1_000_000_000) + 1;
        return v;
    }

    public static int[] gerarOrdenado(int n) {
        int[] v = new int[n];
        for (int i = 0; i < n; i++) v[i] = i + 1;
        return v;
    }

    public static int[] gerarQuaseOrdenado(int n) {
        int[] v = gerarOrdenado(n);
        Random rand = new Random();
        int qtdBaguncar = (int)(n * 0.10); // 10%
        for (int i = 0; i < qtdBaguncar; i++) {
            int pos = rand.nextInt(n);
            v[pos] = rand.nextInt(1_000_000_000) + 1;
        }
        return v;
    }


    //  MÉTODOS DE TESTE (HASH)
    public static void testarHashChaining(String nome, int[] dados, int size, int N) {
        TabelaHashEncadeamento h = new TabelaHashEncadeamento(size);
        long inicio, fim;

        // Inserção
        inicio = System.nanoTime();
        for (int x : dados) h.insert(x);
        fim = System.nanoTime();
        double tempoInsercao = (fim - inicio) / 1e6;

        // Busca
        Random rand = new Random();
        int M = dados.length;
        int[] busca = new int[M];
        for (int i = 0; i < M/2; i++) busca[i] = dados[i];
        for (int i = M/2; i < M; i++) busca[i] = rand.nextInt(1_000_000_000) + 1;

        inicio = System.nanoTime();
        for (int x : busca) h.search(x);
        fim = System.nanoTime();
        double tempoBusca = (fim - inicio) / 1e6;

        // Remoção
        int K = dados.length / 10;
        inicio = System.nanoTime();
        for (int i = 0; i < K; i++) h.remove(dados[i]);
        fim = System.nanoTime();
        double tempoRemocao = (fim - inicio) / 1e6;

        System.out.println("--- " + nome + " ---");
        System.out.println("Colisões: " + h.collisions);
        System.out.printf("Tempo Inserção: %.2f ms\n", tempoInsercao);
        System.out.printf("Tempo Busca: %.2f ms\n", tempoBusca);
        System.out.printf("Tempo Remoção: %.2f ms\n", tempoRemocao);
    }

    public static void testarHashOpen(String nome, int[] dados, int size, int N) {
        TabelaHashEnderecamentoAberto h = new TabelaHashEnderecamentoAberto(size);
        long inicio, fim;

        // Inserção
        inicio = System.nanoTime();
        for (int x : dados) h.insert(x);
        fim = System.nanoTime();
        double tempoInsercao = (fim - inicio) / 1e6;

        // Busca
        Random rand = new Random();
        int M = dados.length;
        int[] busca = new int[M];
        for (int i = 0; i < M/2; i++) busca[i] = dados[i];
        for (int i = M/2; i < M; i++) busca[i] = rand.nextInt(1_000_000_000) + 1;

        inicio = System.nanoTime();
        for (int x : busca) h.search(x);
        fim = System.nanoTime();
        double tempoBusca = (fim - inicio) / 1e6;

        // Remoção
        int K = dados.length / 10;
        inicio = System.nanoTime();
        for (int i = 0; i < K; i++) h.remove(dados[i]);
        fim = System.nanoTime();
        double tempoRemocao = (fim - inicio) / 1e6;

        System.out.println("--- " + nome + " ---");
        System.out.println("Colisões (Sondagens): " + h.collisions);
        System.out.printf("Tempo Inserção: %.2f ms\n", tempoInsercao);
        System.out.printf("Tempo Busca: %.2f ms\n", tempoBusca);
        System.out.printf("Tempo Remoção: %.2f ms\n", tempoRemocao);
    }

  
    //  ÁRVORES
    public static void testarAVL(String nome, int[] dados, int N) {
        ArvoreAVL avl = new ArvoreAVL();
        long inicio, fim;

        // Inserção
        inicio = System.nanoTime();
        for (int x : dados) avl.insert(x);
        fim = System.nanoTime();
        double tempoInsercao = (fim - inicio) / 1e6;

        // Busca
        Random rand = new Random();
        int M = dados.length;
        int[] busca = new int[M];
        for (int i = 0; i < M/2; i++) busca[i] = dados[i];
        for (int i = M/2; i < M; i++) busca[i] = rand.nextInt(1_000_000_000) + 1;

        inicio = System.nanoTime();
        for (int x : busca) avl.search(x);
        fim = System.nanoTime();
        double tempoBusca = (fim - inicio) / 1e6;

        // Remoção
        int K = dados.length / 10;
        inicio = System.nanoTime();
        for (int i = 0; i < K; i++) avl.remove(dados[i]);
        fim = System.nanoTime();
        double tempoRemocao = (fim - inicio) / 1e6;

        System.out.println("--- AVL: " + nome + " ---");
        System.out.printf("Tempo Inserção: %.2f ms\n", tempoInsercao);
        System.out.printf("Tempo Busca: %.2f ms\n", tempoBusca);
        System.out.printf("Tempo Remoção: %.2f ms\n", tempoRemocao);
        System.out.println("Rotações: " + avl.rotations);
        System.out.println("Altura Final: " + avl.getHeight());
    }

    public static void testarABB(String nome, int[] dados, int N) {
        ArvoreBinariaBusca abb = new ArvoreBinariaBusca();
        long inicio, fim;

        // Inserção
        inicio = System.nanoTime();
        for (int x : dados) abb.insert(x);
        fim = System.nanoTime();
        double tempoInsercao = (fim - inicio) / 1e6;

        // Busca
        Random rand = new Random();
        int M = dados.length;
        int[] busca = new int[M];
        for (int i = 0; i < M/2; i++) busca[i] = dados[i];
        for (int i = M/2; i < M; i++) busca[i] = rand.nextInt(1_000_000_000) + 1;

        inicio = System.nanoTime();
        for (int x : busca) abb.search(x);
        fim = System.nanoTime();
        double tempoBusca = (fim - inicio) / 1e6;

        // Remoção
        int K = dados.length / 10;
        inicio = System.nanoTime();
        for (int i = 0; i < K; i++) abb.remove(dados[i]);
        fim = System.nanoTime();
        double tempoRemocao = (fim - inicio) / 1e6;

        System.out.println("--- ABB: " + nome + " ---");
        System.out.printf("Tempo Inserção: %.2f ms\n", tempoInsercao);
        System.out.printf("Tempo Busca: %.2f ms\n", tempoBusca);
        System.out.printf("Tempo Remoção: %.2f ms\n", tempoRemocao);
        System.out.println("Altura Final: " + abb.getHeight());
    }
}