package Arvore_AVL;

public class ArvoreAVL {

    class Node {
        int key, height;
        Node left, right;

        Node(int d) {
            key = d;
            height = 1; // altura 
        }
    }

    private Node root;
    public long rotations = 0; 

    // Altura auxiliar 
    private int height(Node N) {
        if (N == null) return 0;
        return N.height;
    }

    // Calcula Fator de Balanceamento: FB = alt(esq) - alt(dir)
   // Conforme Aula 10 
    private int getBalance(Node N) {
        if (N == null) return 0;
        return height(N.left) - height(N.right);
    }

    // Rotação à Direita 
    private Node rightRotate(Node y) {
        Node x = y.left;
        Node T2 = x.right;

        // Realiza rotação
        x.right = y;
        y.left = T2;

        // Atualiza alturas
        y.height = Math.max(height(y.left), height(y.right)) + 1;
        x.height = Math.max(height(x.left), height(x.right)) + 1;

        rotations++; // Conta a rotação
        return x;
    }

    // Rotação à Esquerda 
    private Node leftRotate(Node x) {
        Node y = x.right;
        Node T2 = y.left;

        // Realiza rotação
        y.left = x;
        x.right = T2;

        // Atualiza alturas
        x.height = Math.max(height(x.left), height(x.right)) + 1;
        y.height = Math.max(height(y.left), height(y.right)) + 1;

        rotations++; // Conta a rotação
        return y;
    }

    // INSERÇÃO
    public void insert(int key) {
        root = insert(root, key);
    }

    private Node insert(Node node, int key) {
     
        if (node == null) return (new Node(key));

        if (key < node.key)
            node.left = insert(node.left, key);
        else if (key > node.key)
            node.right = insert(node.right, key);
        else
            return node; 

        //  Atualiza altura do ancestral
        node.height = 1 + Math.max(height(node.left), height(node.right));

        //  Obtém o fator de balanceamento para verificar se desbalanceou
        int balance = getBalance(node);

        // Se desbalanceou, tem 4 casos 

        // Caso Esquerda-Esquerda (Rotação Simples Direita)
        if (balance > 1 && key < node.left.key)
            return rightRotate(node);

        // Caso Direita-Direita (Rotação Simples Esquerda)
        if (balance < -1 && key > node.right.key)
            return leftRotate(node);

        // Caso Esquerda-Direita (Rotação Dupla: Esq -> Dir)
        if (balance > 1 && key > node.left.key) {
            node.left = leftRotate(node.left);
            return rightRotate(node);
        }

        // Caso Direita-Esquerda (Rotação Dupla: Dir -> Esq)
        if (balance < -1 && key < node.right.key) {
            node.right = rightRotate(node.right);
            return leftRotate(node);
        }

        return node;
    }
    
    // BUSCA
    public boolean search(int key) {
        Node current = root;
        while (current != null) {
            if (key == current.key) return true;
            current = (key < current.key) ? current.left : current.right;
        }
        return false;
    }
    
     //  REMOÇÃO 
    public void remove(int key) {
        root = deleteNode(root, key);
    }

    private Node deleteNode(Node root, int key) {
        if (root == null) return root;

        if (key < root.key) root.left = deleteNode(root.left, key);
        else if (key > root.key) root.right = deleteNode(root.right, key);
        else {
            if ((root.left == null) || (root.right == null)) {
                Node temp = null;
                if (temp == root.left) temp = root.right;
                else temp = root.left;

                if (temp == null) {
                    temp = root;
                    root = null;
                } else root = temp;
            } else {
                Node temp = minValueNode(root.right);
                root.key = temp.key;
                root.right = deleteNode(root.right, temp.key);
            }
        }

        if (root == null) return root;

        //  Atualiza altura
        root.height = Math.max(height(root.left), height(root.right)) + 1;

        // Balanceamento 
        int balance = getBalance(root);

        if (balance > 1 && getBalance(root.left) >= 0)
            return rightRotate(root);

        if (balance > 1 && getBalance(root.left) < 0) {
            root.left = leftRotate(root.left);
            return rightRotate(root);
        }

        if (balance < -1 && getBalance(root.right) <= 0)
            return leftRotate(root);

        if (balance < -1 && getBalance(root.right) > 0) {
            root.right = rightRotate(root.right);
            return leftRotate(root);
        }

        return root;
    }

    private Node minValueNode(Node node) {
        Node current = node;
        while (current.left != null) current = current.left;
        return current;
    }

    public int getHeight() {
        return height(root);
    }
}