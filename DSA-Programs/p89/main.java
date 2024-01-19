package p89;

class Node {
    int value, sum, size;
    Node parent, left, right;

    public Node(int value) {
        this.value = value;
        this.sum = value;
        this.size = 1;
        this.parent = this.left = this.right = null;
    }
}

class LinkCutTree {
    private Node[] nodes;

    public LinkCutTree(int n) {
        nodes = new Node[n];
        for (int i = 0; i < n; i++) {
            nodes[i] = new Node(0); // Initialize with values as needed
        }
    }

    private void update(Node node) {
        node.size = 1 + getSize(node.left) + getSize(node.right);
        node.sum = node.value + getSum(node.left) + getSum(node.right);
    }

    private int getSize(Node node) {
        return (node == null) ? 0 : node.size;
    }

    private int getSum(Node node) {
        return (node == null) ? 0 : node.sum;
    }

    private void connect(Node parent, Node child, boolean isLeftChild) {
        if (child != null) {
            if (isLeftChild) {
                parent.left = child;
            } else {
                parent.right = child;
            }
        }
        if (child != null) {
            child.parent = parent;
        }
    }

    private void rotate(Node child, boolean isLeftChild) {
        Node parent = child.parent;
        Node grandparent = parent.parent;

        connect(grandparent, child, parent == grandparent.left);
        connect(parent, isLeftChild ? child.right : child.left, isLeftChild);
        connect(child, parent, !isLeftChild);

        update(parent);
        update(child);
    }

    private void splay(Node node) {
        while (node.parent != null) {
            Node parent = node.parent;
            Node grandparent = parent.parent;

            if (grandparent == null) {
                rotate(node, node == parent.left);
            } else {
                boolean zigzag = (node == parent.left) != (parent == grandparent.left);
                if (zigzag) {
                    rotate(node, node == parent.left);
                    rotate(node, node == parent.left);
                } else {
                    rotate(parent, parent == grandparent.left);
                    rotate(node, node == parent.left);
                }
            }
        }
    }

    private void access(Node node) {
        Node last = null;
        while (node != null) {
            splay(node);
            node.right = last;
            update(node);
            last = node;
            node = node.parent;
        }
    }

    public void link(int parent, int child) {
        Node parentNode = nodes[parent];
        Node childNode = nodes[child];

        access(childNode);
        splay(childNode);
        splay(parentNode);

        childNode.parent = parentNode;
        parentNode.right = childNode;

        update(parentNode);
    }

    public void cut(int child) {
        Node childNode = nodes[child];

        access(childNode);
        splay(childNode);

        childNode.left.parent = null;
        childNode.left = null;

        update(childNode);
    }

    public int queryPathSum(int from, int to) {
        Node fromNode = nodes[from];
        Node toNode = nodes[to];

        access(fromNode);
        splay(fromNode);

        access(toNode);
        splay(toNode);

        return toNode.sum;
    }

    public static void main(String[] args) {
        LinkCutTree tree = new LinkCutTree(5);

        // Example usage
        tree.link(0, 1);
        tree.link(1, 2);
        tree.link(2, 3);
        tree.link(3, 4);

        System.out.println(tree.queryPathSum(0, 4)); // Output: Sum of values along the path from node 0 to node 4
    }
}

