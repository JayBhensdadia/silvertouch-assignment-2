import java.util.Random;

class Node {
    int key, priority;
    Node left, right;

    public Node(int key) {
        this.key = key;
        this.priority = new Random().nextInt(Integer.MAX_VALUE);
    }
}

class Treap {
    private Node root;

    private Node rotateRight(Node y) {
        Node x = y.left;
        y.left = x.right;
        x.right = y;
        return x;
    }

    private Node rotateLeft(Node x) {
        Node y = x.right;
        x.right = y.left;
        y.left = x;
        return y;
    }

    private Node insert(Node root, int key) {
        if (root == null) {
            return new Node(key);
        }

        if (key <= root.key) {
            root.left = insert(root.left, key);
            if (root.left.priority > root.priority) {
                root = rotateRight(root);
            }
        } else {
            root.right = insert(root.right, key);
            if (root.right.priority > root.priority) {
                root = rotateLeft(root);
            }
        }

        return root;
    }

    public void insert(int key) {
        root = insert(root, key);
    }

    private Node delete(Node root, int key) {
        if (root == null) {
            return root;
        }

        if (key < root.key) {
            root.left = delete(root.left, key);
        } else if (key > root.key) {
            root.right = delete(root.right, key);
        } else {
            if (root.left == null) {
                return root.right;
            } else if (root.right == null) {
                return root.left;
            }

            if (root.left.priority > root.right.priority) {
                root = rotateRight(root);
                root.right = delete(root.right, key);
            } else {
                root = rotateLeft(root);
                root.left = delete(root.left, key);
            }
        }

        return root;
    }

    public void delete(int key) {
        root = delete(root, key);
    }

    public boolean search(int key) {
        return search(root, key);
    }

    private boolean search(Node root, int key) {
        if (root == null) {
            return false;
        }

        if (key == root.key) {
            return true;
        } else if (key < root.key) {
            return search(root.left, key);
        } else {
            return search(root.right, key);
        }
    }

 
}

public class Main {

    public static void main(String[] args) {
        Treap treap = new Treap();
        treap.insert(3);
        treap.insert(1);
        treap.insert(4);
        treap.insert(2);

        System.out.println("Search for key 2: " + treap.search(2));
        System.out.println("Search for key 5: " + treap.search(5)); 

        treap.delete(2);
        System.out.println("Search for key 2 after deletion: " + treap.search(2)); 
    }
}