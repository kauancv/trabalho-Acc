package Arvore_binaria;

public class BinarySearchTree {

    // Classe interna para o Nó da árvore
    class Node {
        int key;
        Node left, right;

        public Node(int item) {
            key = item;
            left = right = null;
        }
    }

    // Raiz da árvore
    protected Node root;

    public BinarySearchTree() {
        root = null;
    }

  
    //  OPERAÇÃO 1: INSERÇÃO
  
    public void insert(int key) {
        root = insertRec(root, key);
    }

    private Node insertRec(Node root, int key) {
        // Se a árvore estiver vazia ou chegar numa folha, cria o nó
        if (root == null) {
            root = new Node(key);
            return root;
        }

        // Caminha pela árvore (menores à esquerda, maiores à direita)
        if (key < root.key)
            root.left = insertRec(root.left, key);
        else if (key > root.key)
            root.right = insertRec(root.right, key);

        // Retorna o ponteiro do nó (inalterado)
        return root;
    }

 
    //  OPERAÇÃO 2: BUSCA
 
    public boolean search(int key) {
        return searchRec(root, key);
    }

    private boolean searchRec(Node root, int key) {
        // Caso base: raiz é nula ou a chave está na raiz
        if (root == null) return false;
        if (root.key == key) return true;

        // Se a chave é menor que a raiz, busca na esquerda
        if (key < root.key)
            return searchRec(root.left, key);
        
        // Se a chave é maior, busca na direita
        return searchRec(root.right, key);
    }


    //  OPERAÇÃO 3: REMOÇÃO
 
    public void remove(int key) {
        root = deleteRec(root, key);
    }

    private Node deleteRec(Node root, int key) {
        if (root == null) return root;

        // Navega até encontrar o nó
        if (key < root.key)
            root.left = deleteRec(root.left, key);
        else if (key > root.key)
            root.right = deleteRec(root.right, key);
        else {
            // Nó encontrado! removendo...
            
            // Caso 1: Nó com apenas um filho ou nenhum
            if (root.left == null) return root.right;
            else if (root.right == null) return root.left;

            // Caso 2: Nó com dois filhos
            // Pega o sucessor in-order (o menor da subárvore direita)
            root.key = minValue(root.right);

            // Deleta o sucessor
            root.right = deleteRec(root.right, root.key);
        }
        return root;
    }

    private int minValue(Node root) {
        int minv = root.key;
        while (root.left != null) {
            minv = root.left.key;
            root = root.left;
        }
        return minv;
    }

    
    //  MÉTRICA: ALTURA DA ÁRVORE 

    public int getHeight() {
        return height(root);
    }

    // Calcula a altura recursivamente
    protected int height(Node node) {
        if (node == null) return -1; // Altura de árvore vazia é -1 (convenção comum)
        
        int leftHeight = height(node.left);
        int rightHeight = height(node.right);

        return 1 + Math.max(leftHeight, rightHeight);
    }
}
