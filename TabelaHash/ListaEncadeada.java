package TabelaHash;

public class ListaEncadeada {

    private No head;

    public ListaEncadeada() {
        head = null;
    }

    //método que o TabelaHashEncadeamento precisa =====
    public boolean isEmpty() {
        return head == null;
    }

    public void insert(int key) {
        No newNode = new No(key);
        newNode.next = head;
        head = newNode;
    }

    public boolean search(int key) {
        No current = head;

        while (current != null) {
            if (current.key == key) return true;
            current = current.next;
        }

        return false;
    }

    public void remove(int key) {
        if (head == null) return;

        if (head.key == key) {
            head = head.next;
            return;
        }

        No current = head;

        while (current.next != null) {
            if (current.next.key == key) {
                current.next = current.next.next;
                return;
            }
            current = current.next;
        }
    }
}
