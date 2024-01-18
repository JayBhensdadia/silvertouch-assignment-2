class Node {
    int key;
    Node parent, left, right;
    int color;

    

    public Node(int key) {
        this.key = key;
        this.parent = null;
        this.left = null;
        this.right = null;
        this.color = 1;
    }
}

class RBTree {
    private Node root;
    private Node nil;

    public RBTree() {
        nil = new Node(0);
        nil.color = 0; 
        root = nil;
    }

   
    private void leftRotate(Node x) {
        Node y = x.right;
        x.right = y.left;

        if (y.left != nil) {
            y.left.parent = x;
        }

        y.parent = x.parent;

        if (x.parent == nil) {
            root = y;
        } else if (x == x.parent.left) {
            x.parent.left = y;
        } else {
            x.parent.right = y;
        }

        y.left = x;
        x.parent = y;
    }

    
    private void rightRotate(Node y) {
        Node x = y.left;
        y.left = x.right;

        if (x.right != nil) {
            x.right.parent = y;
        }

        x.parent = y.parent;

        if (y.parent == nil) {
            root = x;
        } else if (y == y.parent.left) {
            y.parent.left = x;
        } else {
            y.parent.right = x;
        }

        x.right = y;
        y.parent = x;
    }

    
    public void insert(int key) {
        Node z = new Node(key);
        Node y = nil;
        Node x = root;

        while (x != nil) {
            y = x;
            if (z.key < x.key) {
                x = x.left;
            } else {
                x = x.right;
            }
        }

        z.parent = y;

        if (y == nil) {
            root = z;
        } else if (z.key < y.key) {
            y.left = z;
        } else {
            y.right = z;
        }

        z.left = nil;
        z.right = nil;
        z.color = 1; 

        insertFixup(z);
    }

    
    private void insertFixup(Node z) {
        while (z.parent.color == 1) {
            if (z.parent == z.parent.parent.left) {
                Node y = z.parent.parent.right;

                if (y.color == 1) {
                    z.parent.color = 0;
                    y.color = 0;
                    z.parent.parent.color = 1;
                    z = z.parent.parent;
                } else {
                    if (z == z.parent.right) {
                        z = z.parent;
                        leftRotate(z);
                    }

                    z.parent.color = 0;
                    z.parent.parent.color = 1;
                    rightRotate(z.parent.parent);
                }
            } else {
                Node y = z.parent.parent.left;

                if (y.color == 1) {
                    z.parent.color = 0;
                    y.color = 0;
                    z.parent.parent.color = 1;
                    z = z.parent.parent;
                } else {
                    if (z == z.parent.left) {
                        z = z.parent;
                        rightRotate(z);
                    }

                    z.parent.color = 0;
                    z.parent.parent.color = 1;
                    leftRotate(z.parent.parent);
                }
            }
        }

        root.color = 0; 
    }

    
    public void delete(int key) {
        Node z = search(key);

        if (z != null) {
            deleteNode(z);
        }
    }

    
    private void deleteFixup(Node x) {
        while (x != root && x.color == 0) {
            if (x == x.parent.left) {
                Node w = x.parent.right;

                if (w.color == 1) {
                    w.color = 0;
                    x.parent.color = 1;
                    leftRotate(x.parent);
                    w = x.parent.right;
                }

                if (w.left.color == 0 && w.right.color == 0) {
                    w.color = 1;
                    x = x.parent;
                } else {
                    if (w.right.color == 0) {
                        w.left.color = 0;
                        w.color = 1;
                        rightRotate(w);
                        w = x.parent.right;
                    }

                    w.color = x.parent.color;
                    x.parent.color = 0;
                    w.right.color = 0;
                    leftRotate(x.parent);
                    x = root;
                }
            } else {
                Node w = x.parent.left;

                if (w.color == 1) {
                    w.color = 0;
                    x.parent.color = 1;
                    rightRotate(x.parent);
                    w = x.parent.left;
                }

                if (w.right.color == 0 && w.left.color == 0) {
                    w.color = 1;
                    x = x.parent;
                } else {
                    if (w.left.color == 0) {
                        w.right.color = 0;
                        w.color = 1;
                        leftRotate(w);
                        w = x.parent.left;
                    }

                    w.color = x.parent.color;
                    x.parent.color = 0;
                    w.left.color = 0;
                    rightRotate(x.parent);
                    x = root;
                }
            }
        }

        x.color = 0; 
    }

    
    private void transplant(Node u, Node v) {
        if (u.parent == nil) {
            root = v;
        } else if (u == u.parent.left) {
            u.parent.left = v;
        } else {
            u.parent.right = v;
        }

        v.parent = u.parent;
    }

    
    private Node minimum(Node x) {
        while (x.left != nil) {
            x = x.left;
        }
        return x;
    }

    
    private void deleteNode(Node z) {
        Node y = z;
        Node x;
        int yOriginalColor = y.color;

        if (z.left == nil) {
            x = z.right;
            transplant(z, z.right);
        } else if (z.right == nil) {
            x = z.left;
            transplant(z, z.left);
        } else {
            y = minimum(z.right);
            yOriginalColor = y.color;
            x = y.right;

            if (y.parent == z) {
                x.parent = y;
            } else {
                transplant(y, y.right);
                y.right = z.right;
                y.right.parent = y;
            }

            transplant(z, y);
            y.left = z.left;
            y.left.parent = y;
            y.color = z.color;
        }

        if (yOriginalColor == 0) {
            deleteFixup(x);
        }
    }

    
    private Node search(int key) {
        return search(root, key);
    }

    private Node search(Node x, int key) {
        if (x == nil || key == x.key) {
            return x;
        }

        if (key < x.key) {
            return search(x.left, key);
        } else {
            return search(x.right, key);
        }
    }

    
    private void inorderTraversal(Node x) {
        if (x != nil) {
            inorderTraversal(x.left);
            System.out.print(x.key + " ");
            inorderTraversal(x.right);
        }
    }

    
    public void printInorder() {
        System.out.print("Inorder Traversal: ");
        inorderTraversal(root);
        System.out.println();
    }

  
}


public class Main {

    public static void main(String[] args) {
        RBTree rbTree = new RBTree();

        rbTree.insert(10);
        rbTree.insert(20);
        rbTree.insert(30);
        rbTree.insert(15);
        rbTree.insert(25);

        rbTree.printInorder(); 

        rbTree.delete(20);

        rbTree.printInorder(); 
    }
}