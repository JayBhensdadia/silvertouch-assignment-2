import javax.swing.*;
import java.awt.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;


@interface UIElement {
    String value();
}


class DynamicUIFrame extends JFrame {
    public DynamicUIFrame() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLayout(new FlowLayout());
    }

    
    public void createUI(Class<?> clazz) {
        UIElement annotation = clazz.getAnnotation(UIElement.class);
        if (annotation != null) {
            String elementName = annotation.value();
            try {
                Constructor<?> constructor = clazz.getDeclaredConstructor();
                Object instance = constructor.newInstance();

                if (instance instanceof Component) {
                    Component component = (Component) instance;
                    add(component);
                    setTitle(elementName);
                } else {
                    System.out.println("Error: The annotated class is not a UI component.");
                }
            } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Error: UIElement annotation not found.");
        }
    }
}


@UIElement("Button")
class DynamicButton extends JButton {
    public DynamicButton() {
        setText("Click me!");
    }
}

@UIElement("Label")
class DynamicLabel extends JLabel {
    public DynamicLabel() {
        setText("Dynamic Label");
    }
}

public class Main{
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            DynamicUIFrame frame = new DynamicUIFrame();

            
            frame.createUI(DynamicButton.class);
            frame.createUI(DynamicLabel.class);

            frame.setVisible(true);
        });
    }
}
