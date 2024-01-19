import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class Main{

    public static void main(String[] args) {
        
        MyClass myObject = new MyClass("Hello, Reflection!");

        
        Class<?> myClass = myObject.getClass();
        System.out.println("Class Name: " + myClass.getName());

        
        System.out.println("Fields:");
        Field[] fields = myClass.getDeclaredFields();
        for (Field field : fields) {
            System.out.println("  - " + field.getName() + " (" + field.getType() + ")");
        }

        
        System.out.println("Methods:");
        Method[] methods = myClass.getDeclaredMethods();
        for (Method method : methods) {
            System.out.println("  - " + method.getName() + " (" + method.getReturnType() + ")");
        }

        
        try {
            Field messageField = myClass.getDeclaredField("message");
            messageField.setAccessible(true); 
            String messageValue = (String) messageField.get(myObject);
            System.out.println("Original Message: " + messageValue);

            
            messageField.set(myObject, "Modified by Reflection!");
            System.out.println("Modified Message: " + myObject.getMessage());
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}

class MyClass {
    private String message;

    public MyClass(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    private void privateMethod() {
        System.out.println("Executing private method.");
    }
}
