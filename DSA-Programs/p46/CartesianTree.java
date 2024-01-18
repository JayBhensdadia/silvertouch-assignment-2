import java.util.Stack;

class Node {
    int data;
    Node left, right;

    public Node(int data) {
        this.data = data;
        this.left = this.right = null;
    }
}

public class CartesianTree {

    public Node constructCartesianTree(int[] arr) {
        if (arr == null || arr.length == 0) {
            return null;
        }

        Stack<Node> stack = new Stack<>();

        for (int value : arr) {
            Node node = new Node(value);

            while (!stack.isEmpty() && stack.peek().data > value) {
                node.left = stack.pop();
            }

            if (!stack.isEmpty()) {
                stack.peek().right = node;
            }

            stack.push(node);
        }

        Node root = null;
        while (!stack.isEmpty()) {
            root = stack.pop();
        }

        return root;
    }

    private void inOrderTraversal(Node root) {
        if (root != null) {
            inOrderTraversal(root.left);
            System.out.print(root.data + " ");
            inOrderTraversal(root.right);
        }
    }

    public static void main(String[] args) {
        CartesianTree cartesianTree = new CartesianTree();
        int[] arr = {3, 2, 6, 1, 9};

        Node root = cartesianTree.constructCartesianTree(arr);

        System.out.println("In-order traversal of Cartesian Tree:");
        cartesianTree.inOrderTraversal(root);
    }
}
