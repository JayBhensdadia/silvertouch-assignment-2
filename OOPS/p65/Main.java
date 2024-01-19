import java.util.ArrayList;
import java.util.List;

abstract class Shape {
    public abstract double calculateArea();

    public abstract void draw();
}

class Circle extends Shape {
    private double radius;

    public Circle(double radius) {
        this.radius = radius;
    }

    @Override
    public double calculateArea() {
        return Math.PI * radius * radius;
    }

    @Override
    public void draw() {
        System.out.println("Drawing Circle with radius " + radius);
    }
}

class Rectangle extends Shape {
    private double width;
    private double height;

    public Rectangle(double width, double height) {
        this.width = width;
        this.height = height;
    }

    @Override
    public double calculateArea() {
        return width * height;
    }

    @Override
    public void draw() {
        System.out.println("Drawing Rectangle with width " + width + " and height " + height);
    }
}

class Triangle extends Shape {
    private double base;
    private double height;

    public Triangle(double base, double height) {
        this.base = base;
        this.height = height;
    }

    @Override
    public double calculateArea() {
        return 0.5 * base * height;
    }

    @Override
    public void draw() {
        System.out.println("Drawing Triangle with base " + base + " and height " + height);
    }
}

class Drawing {
    private List<Shape> shapes;

    public Drawing() {
        this.shapes = new ArrayList<>();
    }

    public void addShape(Shape shape) {
        shapes.add(shape);
    }

    public void drawShapes() {
        System.out.println("Drawing all shapes:");
        for (Shape shape : shapes) {
            shape.draw();
        }
    }

    public double calculateTotalArea() {
        double totalArea = 0.0;
        for (Shape shape : shapes) {
            totalArea += shape.calculateArea();
        }
        return totalArea;
    }
}

public class Main {
    public static void main(String[] args) {

        Circle circle = new Circle(5);
        Rectangle rectangle = new Rectangle(4, 6);
        Triangle triangle = new Triangle(3, 8);

        Drawing drawing = new Drawing();
        drawing.addShape(circle);
        drawing.addShape(rectangle);
        drawing.addShape(triangle);

        drawing.drawShapes();
        double totalArea = drawing.calculateTotalArea();
        System.out.println("Total Area of all shapes: " + totalArea);
    }
}
