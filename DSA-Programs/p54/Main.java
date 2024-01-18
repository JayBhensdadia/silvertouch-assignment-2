class UnionFindNode {
    int parent;
    int rank;

    public UnionFindNode(int parent, int rank) {
        this.parent = parent;
        this.rank = rank;
    }
}

class PersistentUnionFind {
    public int[] version; 
    public UnionFindNode[] nodes;

    public PersistentUnionFind(int size) {
        version = new int[size];
        nodes = new UnionFindNode[size];

        for (int i = 0; i < size; i++) {
            nodes[i] = new UnionFindNode(i, 0);
            version[i] = 0; 
        }
    }

    public int find(int x, int targetVersion) {
        if (x < 0 || x >= nodes.length || targetVersion < 0) {
            throw new IllegalArgumentException("Invalid element or version");
        }

        while (version[x] <= targetVersion && x != nodes[x].parent) {
            x = nodes[x].parent;
        }

        return x;
    }

    public boolean union(int x, int y) {
        int rootX = find(x, Integer.MAX_VALUE);
        int rootY = find(y, Integer.MAX_VALUE);

        if (rootX == rootY) {
            return false; 
        }

        if (nodes[rootX].rank < nodes[rootY].rank) {
            nodes[rootX].parent = rootY;
        } else if (nodes[rootX].rank > nodes[rootY].rank) {
            nodes[rootY].parent = rootX;
        } else {
            nodes[rootY].parent = rootX;
            nodes[rootX].rank++;
        }

        return true; 
    }

    public void updateVersion(int element, int newVersion) {
        if (element < 0 || element >= version.length || newVersion < version[element]) {
            throw new IllegalArgumentException("Invalid element or version");
        }

        version[element] = newVersion;
    }
}

public class Main {
    public static void main(String[] args) {
        int size = 5;
        PersistentUnionFind puf = new PersistentUnionFind(size);

        System.out.println("Initial state:");
        printState(puf, size);

        puf.union(0, 1);
        puf.updateVersion(0, 1);
        puf.updateVersion(1, 1);

        puf.union(2, 3);
        puf.updateVersion(2, 2);
        puf.updateVersion(3, 2);

        puf.union(0, 2);
        puf.updateVersion(2, 3);

        System.out.println("\nState after unions and updates:");
        printState(puf, size);
    }

    private static void printState(PersistentUnionFind puf, int size) {
        for (int i = 0; i < size; i++) {
            System.out.println("Element " + i + ": Root=" + puf.find(i, Integer.MAX_VALUE) +
                    ", Rank=" + puf.nodes[i].rank + ", Version=" + puf.version[i]);
        }
    }
}
