package TabelaHash;

public class LinkedList {

    private Node head;

    public LinkedList() {
        head = null;
    }

    // ===== método que o HashChaining precisa =====
    public boolean isEmpty() {
        return head == null;
    }

    public void insert(int key) {
        Node newNode = new Node(key);
        newNode.next = head;
        head = newNode;
    }

    public boolean search(int key) {
        Node current = head;

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

        Node current = head;

        while (current.next != null) {
            if (current.next.key == key) {
                current.next = current.next.next;
                return;
            }
            current = current.next;
        }
    }
}
