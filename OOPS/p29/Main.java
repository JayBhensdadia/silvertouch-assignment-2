interface Expression {
    double evaluate();
}

class Constant implements Expression {
    private double value;

    Constant(double value) {
        this.value = value;
    }

    @Override
    public double evaluate() {
        return value;
    }
}

class Variable implements Expression {
    private String name;

    Variable(String name) {
        this.name = name;
    }

    @Override
    public double evaluate() {

        return 0.0;
    }
}

class AddExpression implements Expression {
    private Expression left;
    private Expression right;

    AddExpression(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public double evaluate() {
        return left.evaluate() + right.evaluate();
    }
}

class MultiplyExpression implements Expression {
    private Expression left;
    private Expression right;

    MultiplyExpression(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public double evaluate() {
        return left.evaluate() * right.evaluate();
    }
}

public class Main {
    public static void main(String[] args) {

        Expression expression = new MultiplyExpression(
                new AddExpression(new Constant(2), new Constant(3)),
                new Constant(4));

        double result = expression.evaluate();
        System.out.println("Result: " + result);
    }
}
