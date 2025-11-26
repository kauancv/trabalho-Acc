package TabelaHash;

public class HashChaining {

    private final LinkedList[] table;
    private final int size;
    public int collisions = 0;

    public HashChaining(int size) {
        this.size = size;
        this.table = new LinkedList[size];

        for (int i = 0; i < size; i++) {
            table[i] = new LinkedList();
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
