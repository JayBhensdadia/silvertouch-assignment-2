import java.util.HashMap;
import java.util.Map;

interface Shape {
    void draw(int x, int y);
}

class Circle implements Shape {
    private String color;

    public Circle(String color) {
        this.color = color;
    }

    @Override
    public void draw(int x, int y) {
        System.out.println("Drawing a " + color + " circle at coordinates (" + x + ", " + y + ")");
    }
}

class ShapeFactory {
    private static final Map<String, Shape> circleMap = new HashMap<>();

    public static Shape getCircle(String color) {

        if (!circleMap.containsKey(color)) {

            circleMap.put(color, new Circle(color));
        }

        return circleMap.get(color);
    }
}

public class Main {
    public static void main(String[] args) {

        Shape redCircle = ShapeFactory.getCircle("Red");
        redCircle.draw(100, 100);

        Shape blueCircle = ShapeFactory.getCircle("Blue");
        blueCircle.draw(150, 150);

        Shape greenCircle = ShapeFactory.getCircle("Green");
        greenCircle.draw(200, 200);

        Shape redCircle2 = ShapeFactory.getCircle("Red");
        redCircle2.draw(250, 250);

        Shape blueCircle2 = ShapeFactory.getCircle("Blue");
        blueCircle2.draw(300, 300);
    }
}
