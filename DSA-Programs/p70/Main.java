import java.util.TreeSet;

class YFastTrie<T extends Comparable<T>> {
    private TreeSet<T> universe; 
    private Node root;
    private int universeSize;

    private class Node {
        private Node left, right;
        private int level;
        private TreeSet<T> bucket;

        Node(int level) {
            this.level = level;
            this.bucket = new TreeSet<>();
        }
    }

    public YFastTrie() {
        this.universe = new TreeSet<>();
        this.root = new Node(0);
        this.universeSize = 0;
    }

    public void insert(T element) {
        if (universe.contains(element)) {
            return; 
        }

        universe.add(element);
        universeSize++;

        Node currentNode = root;
        for (int i = NodeSize(); i >= 0; i--) {
            if ((Hash(element) & (1 << i)) == 0) {
                if (currentNode.left == null) {
                    currentNode.left = new Node(i);
                }
                currentNode = currentNode.left;
            } else {
                if (currentNode.right == null) {
                    currentNode.right = new Node(i);
                }
                currentNode = currentNode.right;
            }
            currentNode.bucket.add(element);
        }
    }

    public boolean delete(T element) {
        if (!universe.contains(element)) {
            return false; 
        }

        universe.remove(element);
        universeSize--;

        Node currentNode = root;
        for (int i = NodeSize(); i >= 0; i--) {
            currentNode.bucket.remove(element);

            if ((Hash(element) & (1 << i)) == 0) {
                currentNode = currentNode.left;
            } else {
                currentNode = currentNode.right;
            }
        }

        return true;
    }

    public T findKthSmallest(int k) {
        if (k <= 0 || k > universeSize) {
            throw new IllegalArgumentException("Invalid value of k");
        }

        Node currentNode = root;
        T result = null;

        for (int i = NodeSize(); i >= 0; i--) {
            int leftSize = (currentNode.left != null) ? currentNode.left.bucket.size() : 0;

            if (k <= leftSize) {
                currentNode = currentNode.left;
            } else {
                k -= leftSize;
                currentNode = currentNode.right;
                result = currentNode.bucket.first();
            }
        }

        return result;
    }

    public T findKthLargest(int k) {
        if (k <= 0 || k > universeSize) {
            throw new IllegalArgumentException("Invalid value of k");
        }
    
        Node currentNode = root;
        T result = null;
    
        for (int i = NodeSize(); i >= 0; i--) {
            if (currentNode == null) {
                break; 
            }
    
            int rightSize = (currentNode.right != null) ? currentNode.right.bucket.size() : 0;
    
            if (k <= rightSize) {
                currentNode = currentNode.right;
            } else {
                k -= rightSize;
                currentNode = currentNode.left;
                if (currentNode != null) {
                    result = currentNode.bucket.last();
                }
            }
        }
    
        return result;
    }
    

    private int Hash(T element) {
        return element.hashCode();
    }

    private int NodeSize() {
        return (int) Math.ceil(Math.log(universeSize + 1) / Math.log(2));
    }
}

public class Main {
    public static void main(String[] args) {
        YFastTrie<Integer> yFastTrie = new YFastTrie<>();

        yFastTrie.insert(3);
        yFastTrie.insert(1);
        yFastTrie.insert(4);
        yFastTrie.insert(2);
        yFastTrie.insert(5);

        System.out.println("3rd smallest element: " + yFastTrie.findKthSmallest(3)); 
        System.out.println("2nd largest element: " + yFastTrie.findKthLargest(2));    

        yFastTrie.delete(2);

        System.out.println("After deleting 2, 2nd smallest element: " + yFastTrie.findKthSmallest(2)); 
    }
}
