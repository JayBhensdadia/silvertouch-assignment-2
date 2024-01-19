
class Main {

    private double result;

    public static Main start() {
        return new Main();
    }

    public Main add(double value) {
        result += value;
        return this;
    }

    public Main subtract(double value) {
        result -= value;
        return this;
    }

    public Main multiply(double value) {
        result *= value;
        return this;
    }

    public Main divide(double value) {
        if (value != 0) {
            result /= value;
        } else {
            throw new IllegalArgumentException("Division by zero is not allowed.");
        }
        return this;
    }

    public Main power(int exponent) {
        result = Math.pow(result, exponent);
        return this;
    }

    public double getResult() {
        return result;
    }

    public static void main(String[] args) {
        double result = Main.start()
                .add(5)
                .multiply(2)
                .subtract(3)
                .divide(2)
                .power(2)
                .getResult();

        System.out.println("Result: " + result);
    }
}
