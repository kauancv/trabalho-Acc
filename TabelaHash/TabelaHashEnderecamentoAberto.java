package TabelaHash;

public class TabelaHashEnderecamentoAberto {

    private final Integer[] table;
    private final int size;
    public int collisions = 0;
    private final Integer DELETED = Integer.MIN_VALUE;

    public TabelaHashEnderecamentoAberto(int size) {
        this.size = size;
        this.table = new Integer[size];
    }

    private int hash(int key) {
        return key % size;
    }

    public void insert(int key) {
        int index = hash(key);

        while (table[index] != null && table[index] != DELETED) {
            collisions++;
            index = (index + 1) % size;
        }

        table[index] = key;
    }

    public void search(int key) {
        int index = hash(key);
        int start = index;

        while (table[index] != null) {
            if (table[index].equals(key)) return;

            index = (index + 1) % size;

            // voltou ao começo, chave não existe
            if (index == start) break;
        }

    }

    public void remove(int key) {
        int index = hash(key);
        int start = index;

        while (table[index] != null) {
            if (table[index].equals(key)) {
                table[index] = DELETED; // marcação de removido
                return;
            }

            index = (index + 1) % size;

            if (index == start) break;
        }
    }
}
