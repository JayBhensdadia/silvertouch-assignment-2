import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter a number: ");
        int number = scanner.nextInt();

        boolean isBuzz = (number % 7 == 0 || number % 10 == 7);

        System.out.println("Is Buzz number? " + isBuzz);

        scanner.close();
    }
}
