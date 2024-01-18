import java.util.HashMap;
import java.util.Map;

class DynamicConnectivity {
    private Map<Integer, Integer> parent;
    private Map<Integer, Integer> rank;

    public DynamicConnectivity() {
        parent = new HashMap<>();
        rank = new HashMap<>();
    }

    private int find(int x) {
        if (!parent.containsKey(x)) {
            parent.put(x, x);
            rank.put(x, 0);
            return x;
        }

        if (parent.get(x) != x) {
            parent.put(x, find(parent.get(x)));
        }
        return parent.get(x);
    }

    public boolean isConnected(int x, int y) {
        return find(x) == find(y);
    }

    public void union(int x, int y) {
        int rootX = find(x);
        int rootY = find(y);

        if (rootX != rootY) {
            if (rank.get(rootX) < rank.get(rootY)) {
                parent.put(rootX, rootY);
            } else if (rank.get(rootX) > rank.get(rootY)) {
                parent.put(rootY, rootX);
            } else {
                parent.put(rootY, rootX);
                rank.put(rootX, rank.get(rootX) + 1);
            }
        }
    }

    public void removeEdge(int x, int y) {
        int rootX = find(x);
        int rootY = find(y);

        if (rootX == rootY) {
            parent.put(x, x);
            parent.put(y, y);
        }
    }
}

public class Main {
    public static void main(String[] args) {
        DynamicConnectivity dynamicConnectivity = new DynamicConnectivity();

       
        dynamicConnectivity.union(1, 2);
        dynamicConnectivity.union(2, 3);
        dynamicConnectivity.union(4, 5);

        
        System.out.println(dynamicConnectivity.isConnected(1, 3)); 
        System.out.println(dynamicConnectivity.isConnected(1, 5)); 

        
        dynamicConnectivity.removeEdge(2, 3);

       
        System.out.println(dynamicConnectivity.isConnected(1, 3)); // Output: false
    }
}
