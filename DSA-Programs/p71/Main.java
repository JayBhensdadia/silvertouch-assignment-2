import java.util.ArrayList;
import java.util.List;

class CacheObliviousBTree<K extends Comparable<K>, V> {
    private static final int DEFAULT_ORDER = 4;

    private class Node {
        int count; 
        List<K> keys;
        List<V> values; 
        List<Node> children;

        Node() {
            this.count = 0;
            this.keys = new ArrayList<>();
            this.values = new ArrayList<>();
            this.children = new ArrayList<>();
        }
    }

    private Node root;
    private int order;

    public CacheObliviousBTree() {
        this(DEFAULT_ORDER);
    }

    public CacheObliviousBTree(int order) {
        if (order < 2) {
            throw new IllegalArgumentException("Order must be at least 2.");
        }
        this.order = order;
        this.root = new Node();
    }

    public V search(K key) {
        return search(root, key);
    }

    private V search(Node node, K key) {
        int i = 0;
        while (i < node.count && key.compareTo(node.keys.get(i)) > 0) {
            i++;
        }

        if (i < node.count && key.equals(node.keys.get(i))) {
            
            return node.values.get(i);
        } else if (node.children.isEmpty()) {
            
            return null;
        } else {
            
            return search(node.children.get(i), key);
        }
    }

    public void insert(K key, V value) {
        insert(root, key, value);
    }

    private void insert(Node node, K key, V value) {
        int i = 0;
        while (i < node.count && key.compareTo(node.keys.get(i)) > 0) {
            i++;
        }

        if (i < node.count && key.equals(node.keys.get(i))) {
            
            node.values.set(i, value);
        } else if (node.children.isEmpty()) {
            
            node.keys.add(i, key);
            node.values.add(i, value);
            node.count++;
        } else {
            
            insert(node.children.get(i), key, value);

            
            if (node.children.get(i).count > order - 1) {
                splitChild(node, i);
            }
        }
    }

    private void splitChild(Node parentNode, int childIndex) {
        Node child = parentNode.children.get(childIndex);
        Node newChild = new Node();

        newChild.keys.addAll(child.keys.subList(order / 2, child.keys.size()));
        child.keys.subList(order / 2, child.keys.size()).clear();

        newChild.values.addAll(child.values.subList(order / 2, child.values.size()));
        child.values.subList(order / 2, child.values.size()).clear();

        parentNode.keys.add(childIndex, child.keys.get(order / 2 - 1));
        parentNode.values.add(childIndex, child.values.get(order / 2 - 1));
        parentNode.count++;

        newChild.children.addAll(child.children.subList(order / 2, child.children.size()));
        child.children.subList(order / 2, child.children.size()).clear();

        parentNode.children.add(childIndex + 1, newChild);
    }
}

public class Main {
    public static void main(String[] args) {
        CacheObliviousBTree<Integer, String> btree = new CacheObliviousBTree<>(3);

        btree.insert(1, "One");
        btree.insert(2, "Two");
        btree.insert(3, "Three");
        btree.insert(4, "Four");

        System.out.println("Search 2: " + btree.search(2)); 
        System.out.println("Search 5: " + btree.search(5)); 

        btree.insert(5, "Five");
        System.out.println("Search 5 after insertion: " + btree.search(5)); 
    }
}
