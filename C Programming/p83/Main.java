package C.p83;
import java.util.ArrayList;
import java.util.List;

class ExpressionAddOperators {

    public List<String> addOperators(String num, int target) {
        List<String> result = new ArrayList<>();
        if (num == null || num.length() == 0) {
            return result;
        }

        dfs(num, target, 0, 0, 0, "", result);
        return result;
    }

    private void dfs(String num, int target, int index, long prevOperand, long currentOperand, String expression, List<String> result) {
        if (index == num.length()) {
            if (currentOperand == target) {
                result.add(expression);
            }
            return;
        }

        for (int i = index; i < num.length(); i++) {
            if (i > index && num.charAt(index) == '0') {
                // Skip numbers with leading zeros
                break;
            }

            long operand = Long.parseLong(num.substring(index, i + 1));

            if (index == 0) {
                dfs(num, target, i + 1, operand, operand, expression + operand, result);
            } else {
                dfs(num, target, i + 1, operand, currentOperand + operand, expression + "+" + operand, result);
                dfs(num, target, i + 1, -operand, currentOperand - operand, expression + "-" + operand, result);
                dfs(num, target, i + 1, prevOperand * operand, currentOperand - prevOperand + prevOperand * operand,
                        expression + "*" + operand, result);
            }
        }
    }

    public static void main(String[] args) {
        ExpressionAddOperators solution = new ExpressionAddOperators();
        List<String> result = solution.addOperators("123", 6);

        System.out.println("Result: " + result);
    }
}

public class Main {
}
