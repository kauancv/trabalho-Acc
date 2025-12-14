package TabelaHash;

public class TabelaHashEncadeamento {

    private final ListaEncadeada[] table;
    private final int size;
    public int collisions = 0;

    public TabelaHashEncadeamento(int size) {
        this.size = size;
        this.table = new ListaEncadeada[size];

        for (int i = 0; i < size; i++) {
            table[i] = new ListaEncadeada();
        }
    }

    private int hash(int key) {
        return key % size;
    }

    public void insert(int key) {
        int index = hash(key);

        // colisão ocorre quando a lista não está vazia
        if (!table[index].isEmpty()) {
            collisions++;
        }

        table[index].insert(key);
    }

    public void search(int key) {
        int index = hash(key);
        table[index].search(key);
    }

    public void remove(int key) {
        int index = hash(key);
        table[index].remove(key);
    }
}
