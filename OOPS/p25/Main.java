public class Main<T> {
    private T value;

    public Main(T value) {
        this.value = value;
    }

    public T getValue() {
        return value;
    }

    public double square() {
        return ((Integer) value).doubleValue() * ((Integer) value).doubleValue();
    }

    public static void main(String[] args) {
        Main<Integer> integerTemplate = new Main<Integer>(5);
        System.out.println("Square of integer: " + integerTemplate.square());
    }
}
