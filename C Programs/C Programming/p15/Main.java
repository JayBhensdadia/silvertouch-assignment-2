import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter radius: ");
        double radius = scanner.nextDouble();

        double surfaceArea = 4 * Math.PI * Math.pow(radius, 2);

        System.out.println("Surface area of the sphere: " + surfaceArea);

        scanner.close();
    }
}
