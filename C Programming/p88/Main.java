package C.p88;

import java.util.Stack;

class TreeNode {
    String val;
    TreeNode left, right;

    public TreeNode(String val) {
        this.val = val;
        this.left = this.right = null;
    }
}

 class ExpressionTreeBuilder {

    public TreeNode buildExpressionTree(String[] postfix) {
        Stack<TreeNode> stack = new Stack<>();

        for (String token : postfix) {
            TreeNode node = new TreeNode(token);

            if (isOperator(token)) {
                node.right = stack.pop();
                node.left = stack.pop();
            }

            stack.push(node);
        }

        return stack.pop();
    }

    private boolean isOperator(String token) {
        return token.equals("+") || token.equals("-") || token.equals("*") || token.equals("/");
    }

    public void inorderTraversal(TreeNode root) {
        if (root != null) {
            inorderTraversal(root.left);
            System.out.print(root.val + " ");
            inorderTraversal(root.right);
        }
    }

    public static void main(String[] args) {
        ExpressionTreeBuilder treeBuilder = new ExpressionTreeBuilder();
        String[] postfixExpression = {"3", "4", "+", "2", "*", "7", "/"};

        TreeNode root = treeBuilder.buildExpressionTree(postfixExpression);

        System.out.println("Inorder Traversal of the Expression Tree:");
        treeBuilder.inorderTraversal(root);
    }
}

public class Main {
}
