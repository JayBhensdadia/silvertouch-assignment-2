
interface Expression {
    int interpret(Context context);
}

class Number implements Expression {
    private int value;

    public Number(int value) {
        this.value = value;
    }

    @Override
    public int interpret(Context context) {
        return value;
    }
}

class Add implements Expression {
    private Expression left;
    private Expression right;

    public Add(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public int interpret(Context context) {
        return left.interpret(context) + right.interpret(context);
    }
}

class Subtract implements Expression {
    private Expression left;
    private Expression right;

    public Subtract(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public int interpret(Context context) {
        return left.interpret(context) - right.interpret(context);
    }
}

class Context {

}

public class Main {
    public static void main(String[] args) {

        Expression expression = new Add(new Number(5), new Subtract(new Number(3), new Number(2)));

        Context context = new Context();

        int result = expression.interpret(context);

        System.out.println("Result: " + result);
    }
}
