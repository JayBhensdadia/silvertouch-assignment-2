import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.TYPE)
@interface GenerateToString {
}

@GenerateToString
class Example {
    private String name;
    private int age;

}

public class Main {
    public static void main(String[] args) {

        processAnnotations(Example.class);
    }

    private static void processAnnotations(Class<?> clazz) {
        if (clazz.isAnnotationPresent(GenerateToString.class)) {

            String generatedCode = generateToStringMethod(clazz);
            System.out.println("Generated Code:\n" + generatedCode);
        }
    }

    private static String generateToStringMethod(Class<?> clazz) {
        StringBuilder toStringMethod = new StringBuilder("public String toString() {\n");
        toStringMethod.append("    return \"" + clazz.getSimpleName() + "{");

        for (java.lang.reflect.Field field : clazz.getDeclaredFields()) {
            toStringMethod.append(field.getName() + "=\" + " + field.getName() + " + \", ");
        }

        toStringMethod.delete(toStringMethod.length() - 2, toStringMethod.length());
        toStringMethod.append("}\";\n}");

        return toStringMethod.toString();
    }
}
