import java.util.ArrayList;
import java.util.List;

public class Main {
    private List<Object> elements = new ArrayList<>();

    public void addElement(Object element) {
        elements.add(element);
    }

    public Object getElement(int index) {
        return elements.get(index);
    }

    public static void main(String[] args) {
        Main container = new Main();

        container.addElement("Hello");
        container.addElement(42);
        container.addElement(3.14);

        // Retrieve elements
        Object firstElement = container.getElement(0);
        Object secondElement = container.getElement(1);
        Object thirdElement = container.getElement(2);

        System.out.println("First Element: " + firstElement);
        System.out.println("Second Element: " + secondElement);
        System.out.println("Third Element: " + thirdElement);
    }
}

