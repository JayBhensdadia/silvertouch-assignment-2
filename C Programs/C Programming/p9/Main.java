import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter a number: ");
        int number = scanner.nextInt();

        int originalNumber = number;
        int sumOfDigits = 0;

        while (number > 0) {
            sumOfDigits += number % 10;
            number /= 10;
        }

        boolean isHarshad = (originalNumber % sumOfDigits == 0);

        if (isHarshad) {
            System.out.println("Is Harshad number? Yes");
        } else {
            System.out.println("Is Harshad number? No");
        }

        scanner.close();
    }
}
