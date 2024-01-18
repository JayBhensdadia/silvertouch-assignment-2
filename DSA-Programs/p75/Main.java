class AVLTree {
    static class Node {
        int key;
        int height;
        Node left, right;

        Node(int key) {
            this.key = key;
            this.height = 1;
        }
    }

    private Node root;

    private int height(Node node) {
        return (node != null) ? node.height : 0;
    }

    private int balanceFactor(Node node) {
        return (node != null) ? height(node.left) - height(node.right) : 0;
    }

    private Node rightRotate(Node y) {
        Node x = y.left;
        Node T2 = x.right;

        
        x.right = y;
        y.left = T2;

        
        y.height = Math.max(height(y.left), height(y.right)) + 1;
        x.height = Math.max(height(x.left), height(x.right)) + 1;

        return x;
    }

    private Node leftRotate(Node x) {
        Node y = x.right;
        Node T2 = y.left;

        
        y.left = x;
        x.right = T2;

        
        x.height = Math.max(height(x.left), height(x.right)) + 1;
        y.height = Math.max(height(y.left), height(y.right)) + 1;

        return y;
    }

    private Node insert(Node node, int key) {
        
        if (node == null) {
            return new Node(key);
        }

        if (key < node.key) {
            node.left = insert(node.left, key);
        } else if (key > node.key) {
            node.right = insert(node.right, key);
        } else {
            
            return node;
        }

        
        node.height = Math.max(height(node.left), height(node.right)) + 1;

        
        int balance = balanceFactor(node);

        
        if (balance > 1 && key < node.left.key) {
            return rightRotate(node);
        }

        
        if (balance < -1 && key > node.right.key) {
            return leftRotate(node);
        }

        
        if (balance > 1 && key > node.left.key) {
            node.left = leftRotate(node.left);
            return rightRotate(node);
        }

        
        if (balance < -1 && key < node.right.key) {
            node.right = rightRotate(node.right);
            return leftRotate(node);
        }

        return node;
    }

    private Node minValueNode(Node node) {
        Node current = node;
        while (current.left != null) {
            current = current.left;
        }
        return current;
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
            if ((root.left == null) || (root.right == null)) {
                Node temp = (root.left != null) ? root.left : root.right;

                if (temp == null) {
                    temp = root;
                    root = null;
                } else {
                    root = temp;
                }
            } else {
                Node temp = minValueNode(root.right);
                root.key = temp.key;
                root.right = delete(root.right, temp.key);
            }
        }

        if (root == null) {
            return root;
        }

        
        root.height = Math.max(height(root.left), height(root.right)) + 1;

        
        int balance = balanceFactor(root);

        
        if (balance > 1 && balanceFactor(root.left) >= 0) {
            return rightRotate(root);
        }

        
        if (balance < -1 && balanceFactor(root.right) <= 0) {
            return leftRotate(root);
        }

        
        if (balance > 1 && balanceFactor(root.left) < 0) {
            root.left = leftRotate(root.left);
            return rightRotate(root);
        }

        
        if (balance < -1 && balanceFactor(root.right) > 0) {
            root.right = rightRotate(root.right);
            return leftRotate(root);
        }

        return root;
    }

    void insert(int key) {
        root = insert(root, key);
    }

    void delete(int key) {
        root = delete(root, key);
    }

    void inorder() {
        inorder(root);
        System.out.println();
    }

    private void inorder(Node node) {
        if (node != null) {
            inorder(node.left);
            System.out.print(node.key + " ");
            inorder(node.right);
        }
    }
}

public class Main {
    public static void main(String[] args) {
        AVLTree tree = new AVLTree();

        
        tree.insert(10);
        tree.insert(20);
        tree.insert(30);
        tree.insert(40);
        tree.insert(50);
        tree.insert(25);

        System.out.println("Inorder traversal:");
        tree.inorder();

        
        tree.delete(30);
        tree.delete(40);

        System.out.println("Inorder traversal after deletion:");
        tree.inorder();
    }
}
