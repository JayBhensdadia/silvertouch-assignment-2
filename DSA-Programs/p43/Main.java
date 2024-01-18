class Node {
    int key;
    Node left, right, parent;

    public Node(int key) {
        this.key = key;
        this.left = this.right = this.parent = null;
    }
}

class SplayTree {
    public Node root;

    public SplayTree() {
        this.root = null;
    }

   
    private void splay(Node x) {
        while (x.parent != null) {
            Node parent = x.parent;
            Node grandParent = parent.parent;

            if (grandParent == null) {
                if (x == parent.left) {
                    rotateRight(parent);
                } else {
                    rotateLeft(parent);
                }
            } else {
                if (x == parent.left && parent == grandParent.left) {
                    rotateRight(grandParent);
                    rotateRight(parent);
                } else if (x == parent.right && parent == grandParent.right) {
                    rotateLeft(grandParent);
                    rotateLeft(parent);
                } else if (x == parent.right && parent == grandParent.left) {
                    rotateLeft(parent);
                    rotateRight(grandParent);
                } else {
                    rotateRight(parent);
                    rotateLeft(grandParent);
                }
            }
        }
        root = x;
    }

    
    private void rotateLeft(Node x) {
        Node y = x.right;
        x.right = y.left;

        if (y.left != null) {
            y.left.parent = x;
        }

        y.parent = x.parent;

        if (x.parent == null) {
            root = y;
        } else if (x == x.parent.left) {
            x.parent.left = y;
        } else {
            x.parent.right = y;
        }

        y.left = x;
        x.parent = y;
    }

    
    private void rotateRight(Node x) {
        Node y = x.left;
        x.left = y.right;

        if (y.right != null) {
            y.right.parent = x;
        }

        y.parent = x.parent;

        if (x.parent == null) {
            root = y;
        } else if (x == x.parent.right) {
            x.parent.right = y;
        } else {
            x.parent.left = y;
        }

        y.right = x;
        x.parent = y;
    }

    
    public void search(int key) {
        Node node = search(root, key);
        if (node != null) {
            splay(node);
        }
    }

    private Node search(Node root, int key) {
        if (root == null || root.key == key) {
            return root;
        }

        if (key < root.key) {
            return search(root.left, key);
        }

        return search(root.right, key);
    }

    
    public void insert(int key) {
        root = insert(root, key);
        splay(search(root, key));
    }

    private Node insert(Node root, int key) {
        if (root == null) {
            return new Node(key);
        }

        if (key < root.key) {
            root.left = insert(root.left, key);
            root.left.parent = root;
        } else if (key > root.key) {
            root.right = insert(root.right, key);
            root.right.parent = root;
        }

        return root;
    }

    
    public void inOrderTraversal(Node root) {
        if (root != null) {
            inOrderTraversal(root.left);
            System.out.print(root.key + " ");
            inOrderTraversal(root.right);
        }
    }


}


public class Main {

    public static void main(String[] args) {
        SplayTree splayTree = new SplayTree();

        splayTree.insert(50);
        splayTree.insert(30);
        splayTree.insert(70);
        splayTree.insert(20);
        splayTree.insert(40);

        System.out.println("In-order traversal before search:");
        splayTree.inOrderTraversal(splayTree.root);

        splayTree.search(40);

        System.out.println("\nIn-order traversal after searching for 40:");
        splayTree.inOrderTraversal(splayTree.root);
    }
    
}