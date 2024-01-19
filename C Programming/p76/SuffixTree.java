package C.p76;

import java.util.HashMap;
import java.util.Map;

class SuffixTreeNode {
    Map<Character, SuffixTreeNode> children = new HashMap<>();
    int start;
    int end;

    public SuffixTreeNode(int start, int end) {
        this.start = start;
        this.end = end;
    }
}

public class SuffixTree {
    private SuffixTreeNode root;
    private String text;

    public SuffixTree(String text) {
        this.root = new SuffixTreeNode(-1, -1); // Root has no edge
        this.text = text;
        buildTree();
    }

    private void buildTree() {
        for (int i = 0; i < text.length(); i++) {
            addSuffix(i);
        }
    }

    private void addSuffix(int suffixStart) {
        SuffixTreeNode node = root;
        int i = suffixStart;

        while (i < text.length()) {
            char currentChar = text.charAt(i);
            if (!node.children.containsKey(currentChar)) {
                node.children.put(currentChar, new SuffixTreeNode(i, text.length() - 1));
                return;
            }

            node = node.children.get(currentChar);
            int j = node.start;

            while (i < text.length() && j <= node.end && text.charAt(i) == text.charAt(j)) {
                i++;
                j++;
            }

            if (j <= node.end) {
                // Split the edge
                SuffixTreeNode newNode = new SuffixTreeNode(node.start, j - 1);
                node.start = j;
                newNode.children.put(text.charAt(j), node);
                node = newNode;
            }
        }
    }

    public void displayTree() {
        displayTree(root, 0);
    }

    private void displayTree(SuffixTreeNode node, int level) {
        for (Map.Entry<Character, SuffixTreeNode> entry : node.children.entrySet()) {
            System.out.println("  ".repeat(level) + entry.getKey() + ": " + entry.getValue().start + ", " + entry.getValue().end);
            displayTree(entry.getValue(), level + 1);
        }
    }

    public static void main(String[] args) {
        String text = "banana";
        SuffixTree suffixTree = new SuffixTree(text);
        suffixTree.displayTree();
    }
}
