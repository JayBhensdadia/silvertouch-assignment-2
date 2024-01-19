import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter n: ");
        int n = scanner.nextInt();
        System.out.print("Enter r: ");
        int r = scanner.nextInt();

        int nPr = factorial(n) / factorial(n - r);
        int nCr = factorial(n) / (factorial(r) * factorial(n - r));

        System.out.println("Permutation (nPr): " + nPr);
        System.out.println("Combination (nCr): " + nCr);

        scanner.close();
    }

    private static int factorial(int num) {
        if (num == 0 || num == 1) {
            return 1;
        } else {
            return num * factorial(num - 1);
        }
    }
}
