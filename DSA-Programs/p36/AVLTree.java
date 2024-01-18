

class AVLNode {
    int key;
    int height;
    AVLNode left, right;

    public AVLNode(int key) {
        this.key = key;
        this.height = 1;
        this.left = null;
        this.right = null;
    }
}

public class AVLTree {
    private AVLNode root;

    public AVLTree() {
        this.root = null;
    }

    
    private int height(AVLNode node) {
        return (node == null) ? 0 : node.height;
    }

    
    private int balanceFactor(AVLNode node) {
        return (node == null) ? 0 : height(node.left) - height(node.right);
    }

    
    private void updateHeight(AVLNode node) {
        node.height = 1 + Math.max(height(node.left), height(node.right));
    }

    
    private AVLNode rotateRight(AVLNode y) {
        AVLNode x = y.left;
        AVLNode T2 = x.right;

        
        x.right = y;
        y.left = T2;

        
        updateHeight(y);
        updateHeight(x);

        return x;
    }

    
    private AVLNode rotateLeft(AVLNode x) {
        AVLNode y = x.right;
        AVLNode T2 = y.left;

        
        y.left = x;
        x.right = T2;

        
        updateHeight(x);
        updateHeight(y);

        return y;
    }

    
    public void insert(int key) {
        root = insertRecursive(root, key);
    }

    private AVLNode insertRecursive(AVLNode node, int key) {
        
        if (node == null) {
            return new AVLNode(key);
        }

        if (key < node.key) {
            node.left = insertRecursive(node.left, key);
        } else if (key > node.key) {
            node.right = insertRecursive(node.right, key);
        } else {
            
            return node;
        }

        
        updateHeight(node);

        
        int balance = balanceFactor(node);

       
        if (balance > 1 && key < node.left.key) {
            return rotateRight(node);
        }
        
        if (balance < -1 && key > node.right.key) {
            return rotateLeft(node);
        }
        
        if (balance > 1 && key > node.left.key) {
            node.left = rotateLeft(node.left);
            return rotateRight(node);
        }
        
        if (balance < -1 && key < node.right.key) {
            node.right = rotateRight(node.right);
            return rotateLeft(node);
        }

        return node;
    }

    
    public void delete(int key) {
        root = deleteRecursive(root, key);
    }

    private AVLNode deleteRecursive(AVLNode node, int key) {
        if (node == null) {
            return null;
        }

        if (key < node.key) {
            node.left = deleteRecursive(node.left, key);
        } else if (key > node.key) {
            node.right = deleteRecursive(node.right, key);
        } else {
            
            if ((node.left == null) || (node.right == null)) {
                AVLNode temp = (node.left != null) ? node.left : node.right;

                
                if (temp == null) {
                    temp = node;
                    node = null;
                } else {
                    
                    node = temp; 
                }
            } else {
                
                AVLNode temp = findMin(node.right);
                node.key = temp.key;
                node.right = deleteRecursive(node.right, temp.key);
            }
        }

        if (node == null) {
            return null; 
        }

        
        updateHeight(node);

        
        int balance = balanceFactor(node);

        
        if (balance > 1 && balanceFactor(node.left) >= 0) {
            return rotateRight(node);
        }
        
        if (balance > 1 && balanceFactor(node.left) < 0) {
            node.left = rotateLeft(node.left);
            return rotateRight(node);
        }
        
        if (balance < -1 && balanceFactor(node.right) <= 0) {
            return rotateLeft(node);
        }
        
        if (balance < -1 && balanceFactor(node.right) > 0) {
            node.right = rotateRight(node.right);
            return rotateLeft(node);
        }

        return node;
    }

    
    public boolean search(int key) {
        return searchRecursive(root, key);
    }

    private boolean searchRecursive(AVLNode node, int key) {
        if (node == null) {
            return false;
        }

        if (key == node.key) {
            return true;
        } else if (key < node.key) {
            return searchRecursive(node.left, key);
        } else {
            return searchRecursive(node.right, key);
        }
    }

    
    private AVLNode findMin(AVLNode node) {
        while (node.left != null) {
            node = node.left;
        }
        return node;
    }

    
    public void inorder() {
        inorderRecursive(root);
        System.out.println();
    }

    private void inorderRecursive(AVLNode node) {
        if (node != null) {
            inorderRecursive(node.left);
            System.out.print(node.key + " ");
            inorderRecursive(node.right);
        }
    }

    public static void main(String[] args) {
        AVLTree avlTree = new AVLTree();

       
        avlTree.insert(10);
        avlTree.insert(20);
        avlTree.insert(30);
        avlTree.insert(40);
        avlTree.insert(50);
        avlTree.insert(25);

        System.out.println("Inorder traversal of the AVL tree:");
        avlTree.inorder();

        
        int keyToSearch = 30;
        if (avlTree.search(keyToSearch)) {
            System.out.println(keyToSearch + " found in the AVL tree.");
        } else {
            System.out.println(keyToSearch + " not found in the AVL tree.");
        }

        
        int keyToDelete = 30;
        avlTree.delete(keyToDelete);

        System.out.println("Inorder traversal after deleting " + keyToDelete + ":");
        avlTree.inorder();
    }
}
