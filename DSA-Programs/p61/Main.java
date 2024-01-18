class Node {
    Node parent, left, right;
    boolean isRoot, isReversed;

    Node() {
        parent = left = right = null;
        isRoot = true;
        isReversed = false;
    }
}

class LinkCutTree {
    private static void connect(Node ch, Node p, boolean isLeftChild) {
        if (ch != null) {
            if (isLeftChild)
                p.left = ch;
            else
                p.right = ch;
        }
        if (ch != null)
            ch.parent = p;
    }

    private static void rotate(Node x) {
        Node p = x.parent;
        Node g = p.parent;
        boolean isRootP = p.isRoot;

        if (g != null) {
            if (p == g.left)
                connect(x.right, p, true);
            else
                connect(x.left, p, false);
        } else {
            x.parent = null;
        }

        if (p.left == x) {
            connect(x.right, p, true);
            connect(p, x, false);
        } else {
            connect(x.left, p, false);
            connect(p, x, true);
        }

        if (isRootP)
            x.isRoot = true;
        p.isRoot = false;
    }

    private static void splay(Node x) {
        while (!x.isRoot) {
            Node p = x.parent;
            Node g = p.parent;

            if (!p.isRoot) {
                if ((p.left == x) == (g.left == p))
                    rotate(p);
                else
                    rotate(x);
            }
            rotate(x);
        }
    }

    private static Node access(Node x) {
        Node last = null;
        for (Node y = x; y != null; y = y.parent) {
            splay(y);
            y.right = last;
            last = y;
        }
        splay(x);
        return last;
    }

    private static void makeRoot(Node x) {
        access(x);
        splay(x);
        x.isReversed = !x.isReversed;
    }

    public static void link(Node x, Node y) {
        makeRoot(x);
        x.parent = y;
    }

    public static void cut(Node x, Node y) {
        makeRoot(x);
        access(y);
        splay(y);
        if (y.left == x) {
            y.left.parent = null;
            y.left = null;
        }
    }

    public static boolean isConnected(Node x, Node y) {
        makeRoot(x);
        access(y);
        splay(y);
        return x.parent != null || x == y;
    }
}

public class Main {
    public static void main(String[] args) {
        // Example usage
        Node[] nodes = new Node[6];
        for (int i = 0; i < 6; i++) {
            nodes[i] = new Node();
        }

        LinkCutTree.link(nodes[0], nodes[1]);
        LinkCutTree.link(nodes[1], nodes[2]);
        LinkCutTree.link(nodes[2], nodes[3]);

        System.out.println(LinkCutTree.isConnected(nodes[0], nodes[3])); 

        LinkCutTree.cut(nodes[1], nodes[2]);

        System.out.println(LinkCutTree.isConnected(nodes[0], nodes[3])); 
    }
}
