import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the side length of the cube: ");
        double sideLength = scanner.nextDouble();

        double volume = Math.pow(sideLength, 3);

        System.out.println("Volume of the cube: " + volume);

        scanner.close();
    }
}
