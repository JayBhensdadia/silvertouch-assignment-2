import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the base: ");
        double base = scanner.nextDouble();

        System.out.print("Enter the height: ");
        double height = scanner.nextDouble();

        double area = base * height;

        System.out.println("Area of the parallelogram: " + area);

        scanner.close();
    }
}
