
interface Measurable<T> {
    double getMeasurement(T item);
}


class Box<T extends Measurable<T>> {
    private T content;

    public Box(T content) {
        this.content = content;
    }

    public double measureContent() {
        return content.getMeasurement(content);
    }
}


class Fruit implements Measurable<Fruit> {
    private String name;
    private double weight;

    public Fruit(String name, double weight) {
        this.name = name;
        this.weight = weight;
    }

    @Override
    public double getMeasurement(Fruit item) {
        return item.weight;
    }
}


public class Main {
    public static void main(String[] args) {
        Fruit apple = new Fruit("Apple", 0.2);
        Box<Fruit> fruitBox = new Box<>(apple);

        double boxMeasurement = fruitBox.measureContent();
        System.out.println("Box content measurement: " + boxMeasurement);
    }
}
