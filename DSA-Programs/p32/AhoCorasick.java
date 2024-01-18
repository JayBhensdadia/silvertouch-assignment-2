import java.util.LinkedList;
import java.util.Queue;

public class AhoCorasick {

    static class TrieNode {
        TrieNode[] children = new TrieNode[26];
        TrieNode fail;
        boolean isEnd;
        String word;

        TrieNode() {
            isEnd = false;
            fail = null;
            word = null;
            for (int i = 0; i < 26; i++) {
                children[i] = null;
            }
        }
    }

    static class Trie {
        TrieNode root;

        Trie() {
            root = new TrieNode();
        }

        void insert(String pattern) {
            TrieNode node = root;
            for (char c : pattern.toCharArray()) {
                int index = c - 'a';
                if (node.children[index] == null) {
                    node.children[index] = new TrieNode();
                }
                node = node.children[index];
            }
            node.isEnd = true;
            node.word = pattern;
        }
    }

    static void buildFailureLinks(Trie trie) {
        Queue<TrieNode> queue = new LinkedList<>();
        for (TrieNode child : trie.root.children) {
            if (child != null) {
                queue.add(child);
                child.fail = trie.root;
            }
        }

        while (!queue.isEmpty()) {
            TrieNode currentNode = queue.poll();

            for (int i = 0; i < 26; i++) {
                TrieNode child = currentNode.children[i];
                if (child != null) {
                    queue.add(child);

                    TrieNode failNode = currentNode.fail;
                    while (failNode != null && failNode.children[i] == null) {
                        failNode = failNode.fail;
                    }

                    if (failNode == null) {
                        child.fail = trie.root;
                    } else {
                        child.fail = failNode.children[i];
                    }
                }
            }
        }
    }

    static void search(Trie trie, String text) {
        TrieNode currentNode = trie.root;
        for (char c : text.toCharArray()) {
            int index = c - 'a';
            while (currentNode != null && currentNode.children[index] == null) {
                currentNode = currentNode.fail;
            }

            if (currentNode == null) {
                currentNode = trie.root;
            } else {
                currentNode = currentNode.children[index];
                TrieNode temp = currentNode;

                while (temp != null) {
                    if (temp.isEnd) {
                        System.out.println("Pattern found: " + temp.word);
                    }
                    temp = temp.fail;
                }
            }
        }
    }

    public static void main(String[] args) {
        Trie trie = new Trie();
        trie.insert("he");
        trie.insert("she");
        trie.insert("his");
        trie.insert("hers");

        buildFailureLinks(trie);

        String text = "ahishers";
        search(trie, text);
    }
}
