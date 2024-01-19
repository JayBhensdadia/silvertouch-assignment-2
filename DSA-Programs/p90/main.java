package p90;

import java.util.HashMap;
import java.util.Map;

class Node {
    int start;
    int end = Integer.MAX_VALUE; // Initially set to infinity for edges going to the end of the string
    Map<Character, Node> children = new HashMap<>();
    Node suffixLink;

    public Node(int start) {
        this.start = start;
    }

    public int edgeLength(int currentIdx) {
        return Math.min(end, currentIdx + 1) - start;
    }
}

class OnlineSuffixTree {
    private Node root;
    private Node activeNode;
    private int activeEdge;
    private int activeLength;
    private int remainingSuffixCount;
    private int leafEnd;
    private Node lastNewNode;
    private int activeIndex;

    public OnlineSuffixTree() {
        root = new Node(-1); // Dummy root node
        activeNode = root;
        activeEdge = -1;
        activeLength = 0;
        remainingSuffixCount = 0;
        leafEnd = -1;
        lastNewNode = null;
        activeIndex = 0;
    }

    private void extendSuffixTree(int index) {
        leafEnd = index;

        remainingSuffixCount++;
        lastNewNode = null;

        while (remainingSuffixCount > 0) {
            if (activeLength == 0) {
                activeEdge = index;
            }

            if (!activeNode.children.containsKey(getActiveChar())) {
                activeNode.children.put(getActiveChar(), new Node(index));
                addSuffixLink(activeNode);
            } else {
                Node nextNode = activeNode.children.get(getActiveChar());
                if (walkDown(nextNode)) {
                    continue;
                }

                if (getChar(nextNode.start + activeLength) == getActiveChar()) {
                    if (lastNewNode != null && activeNode != root) {
                        addSuffixLink(activeNode);
                        lastNewNode = null;
                    }
                    activeLength++;
                    break;
                }

                splitEdge(nextNode);

                if (lastNewNode != null) {
                    addSuffixLink(lastNewNode);
                    lastNewNode = null;
                }
            }

            remainingSuffixCount--;
            if (activeNode == root && activeLength > 0) {
                activeLength--;
                activeEdge = index - remainingSuffixCount + 1;
            } else if (activeNode != root) {
                activeNode = activeNode.suffixLink != null ? activeNode.suffixLink : root;
            }
        }
    }

    private void splitEdge(Node existingNode) {
        Node newNode = new Node(existingNode.start);
        existingNode.start += activeLength;
        newNode.children.put(getChar(existingNode.start), existingNode);
        activeNode.children.put(getActiveChar(), newNode);
        newNode.children.put(getActiveChar(), new Node(leafEnd));
        lastNewNode = newNode;
    }

    private char getChar(int index) {
        // Replace with the actual function to get the character at the given index in the input string
        return 'a';
    }

    private char getActiveChar() {
        // Replace with the actual function to get the character at the active edge in the input string
        return getChar(activeEdge);
    }

    private boolean walkDown(Node nextNode) {
        int edgeLength = nextNode.edgeLength(leafEnd);
        if (activeLength >= edgeLength) {
            activeEdge += edgeLength;
            activeLength -= edgeLength;
            activeNode = nextNode;
            return true;
        }
        return false;
    }

    private void addSuffixLink(Node node) {
        if (lastNewNode != null) {
            lastNewNode.suffixLink = node;
        }
        lastNewNode = node;
    }

    public static void main(String[] args) {
        OnlineSuffixTree suffixTree = new OnlineSuffixTree();

        // Simulating online updates to the suffix tree
        String input = "ababc";
        for (int i = 0; i < input.length(); i++) {
            suffixTree.extendSuffixTree(i);
        }
    }
}

