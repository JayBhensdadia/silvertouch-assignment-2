import java.util.Stack;

class TreeNode {
    int value;
    TreeNode left;
    TreeNode right;

    public TreeNode(int value) {
        this.value = value;
    }
}

public class Main {
    public static void performGarbageCollection(TreeNode root) {
        if (root == null) {
            return;
        }

        Stack<TreeNode> stack = new Stack<>();
        TreeNode current = root;

        while (current != null || !stack.isEmpty()) {
            if (current != null) {
                stack.push(current);
                TreeNode temp = current;
                current = current.left;
                temp.left = null; // Mark left child as visited
            } else {
                TreeNode node = stack.pop();
                current = node.right;
                // Process or collect the node (e.g., print its value)
                System.out.print(node.value + " ");
            }
        }
    }

    public static void main(String[] args) {
        // Create a sample binary tree
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.right = new TreeNode(3);
        root.left.left = new TreeNode(4);
        root.left.right = new TreeNode(5);
        root.right.left = new TreeNode(6);
        root.right.right = new TreeNode(7);

        System.out.println("Original Tree:");
        printInOrder(root);

        System.out.println("\n\nPerforming Garbage Collection:");
        performGarbageCollection(root);
    }

    private static void printInOrder(TreeNode node) {
        if (node != null) {
            printInOrder(node.left);
            System.out.print(node.value + " ");
            printInOrder(node.right);
        }
    }
}
