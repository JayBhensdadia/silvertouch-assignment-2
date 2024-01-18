import java.util.HashMap;
import java.util.Map;

class RadixNode {
    private Map<Character, RadixNode> children;
    private boolean isEnd;

    public RadixNode() {
        this.children = new HashMap<>();
        this.isEnd = false;
    }

    public Map<Character, RadixNode> getChildren() {
        return children;
    }

    public boolean isEnd() {
        return isEnd;
    }

    public void setEnd(boolean end) {
        isEnd = end;
    }
}

public class RadixTree {
    private RadixNode root;

    public RadixTree() {
        this.root = new RadixNode();
    }

    public void insert(String word) {
        insertHelper(root, word);
    }

    private void insertHelper(RadixNode node, String word) {
        if (word.isEmpty()) {
            node.setEnd(true);
            return;
        }

        char firstChar = word.charAt(0);
        RadixNode child = node.getChildren().get(firstChar);

        if (child == null) {
            child = new RadixNode();
            node.getChildren().put(firstChar, child);
        }

        insertHelper(child, word.substring(1));
    }

    public boolean search(String word) {
        return searchHelper(root, word);
    }

    private boolean searchHelper(RadixNode node, String word) {
        if (word.isEmpty()) {
            return node.isEnd();
        }

        char firstChar = word.charAt(0);
        RadixNode child = node.getChildren().get(firstChar);

        return child != null && searchHelper(child, word.substring(1));
    }

    public static void main(String[] args) {
        RadixTree radixTree = new RadixTree();

        radixTree.insert("apple");
        radixTree.insert("app");
        radixTree.insert("banana");

        System.out.println("Search 'apple': " + radixTree.search("apple")); // true
        System.out.println("Search 'app': " + radixTree.search("app")); // true
        System.out.println("Search 'orange': " + radixTree.search("orange")); // false
    }
}
