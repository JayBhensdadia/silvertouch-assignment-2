class TSTNode {
    char data;
    boolean isEndOfWord;
    TSTNode left, middle, right;

    public TSTNode(char data) {
        this.data = data;
    }
}

class TernarySearchTree {
    private TSTNode root;

    public void insert(String word) {
        root = insert(root, word.toCharArray(), 0);
    }

    private TSTNode insert(TSTNode node, char[] word, int index) {
        char currentChar = word[index];

        if (node == null) {
            node = new TSTNode(currentChar);
        }

        if (currentChar < node.data) {
            node.left = insert(node.left, word, index);
        } else if (currentChar > node.data) {
            node.right = insert(node.right, word, index);
        } else {
            if (index < word.length - 1) {
                node.middle = insert(node.middle, word, index + 1);
            } else {
                node.isEndOfWord = true;
            }
        }

        return node;
    }

    public boolean search(String word) {
        return search(root, word.toCharArray(), 0);
    }

    private boolean search(TSTNode node, char[] word, int index) {
        if (node == null) {
            return false;
        }

        char currentChar = word[index];

        if (currentChar < node.data) {
            return search(node.left, word, index);
        } else if (currentChar > node.data) {
            return search(node.right, word, index);
        } else {
            if (index == word.length - 1) {
                return node.isEndOfWord;
            } else {
                return search(node.middle, word, index + 1);
            }
        }
    }


}


public class Main {

    public static void main(String[] args) {
        TernarySearchTree tst = new TernarySearchTree();

        
        tst.insert("apple");
        tst.insert("banana");
        tst.insert("orange");

        
        System.out.println(tst.search("apple"));   
        System.out.println(tst.search("orange"));  
        System.out.println(tst.search("grape"));   
    }
}