import java.util.Random;

public class Main {

    public static boolean isPrime(int n, int k) {
        if (n <= 1 || n == 4) {
            return false;
        }
        if (n <= 3) {
            return true;
        }

        int d = n - 1;
        while (d % 2 == 0) {
            d /= 2;
        }

        for (int i = 0; i < k; i++) {
            if (!millerRabinTest(n, d)) {
                return false;
            }
        }

        return true;
    }

    private static boolean millerRabinTest(int n, int d) {
        Random rand = new Random();
        int a = 2 + rand.nextInt(n - 4);

        int x = power(a, d, n);

        if (x == 1 || x == n - 1) {
            return true;
        }

        while (d != n - 1) {
            x = (x * x) % n;
            d *= 2;

            if (x == 1) {
                return false;
            }
            if (x == n - 1) {
                return true;
            }
        }

        return false;
    }

    private static int power(int x, int y, int p) {
        int result = 1;
        x = x % p;

        while (y > 0) {
            if (y % 2 == 1) {
                result = (result * x) % p;
            }
            y = y >> 1;
            x = (x * x) % p;
        }

        return result;
    }

    public static void main(String[] args) {
        int number = 29;
        int iterations = 5;

        boolean result = isPrime(number, iterations);

        if (result) {
            System.out.println(number + " is prime.");
        } else {
            System.out.println(number + " is composite.");
        }
    }
}
