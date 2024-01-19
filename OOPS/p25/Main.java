public class Main<T extends Number> {
    private T value;

    public Main(T value) {
        this.value = value;
    }

    public T getValue() {
        return value;
    }

    public double square() {
        return value.doubleValue() * value.doubleValue();
    }

    public static void main(String[] args) {
        Main<Integer> integerTemplate = new Main<>(5);
        System.out.println("Square of integer: " + integerTemplate.square());
    }
}
