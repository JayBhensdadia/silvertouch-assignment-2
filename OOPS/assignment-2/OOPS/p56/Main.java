import java.util.Random;
import java.util.random.RandomGenerator;



public class Main {

    public static void main(String[] args) {
        int numberToFactorize = 21; 

        System.out.println("Number to factorize: " + numberToFactorize);

        
        int a = chooseRandomA(numberToFactorize);
        System.out.println("Chosen random number a: " + a);

        
        int gcd = gcd(a, numberToFactorize);
        if (gcd > 1) {
            System.out.println("Non-trivial factor found: " + gcd);
        } else {
            
            System.out.println("No non-trivial factor found. Proceeding with quantum part...");

            
            int period = simulateQuantumPart(a, numberToFactorize);
            System.out.println("Estimated period r: " + period);

            
            int factor1 = computeFactor(a, numberToFactorize, period);
            int factor2 = computeFactor(a, numberToFactorize, period, true);

            System.out.println("Factors found: " + factor1 + ", " + factor2);
        }
    }

    private static int chooseRandomA(int n) {
        RandomGenerator randomDataGenerator = new Random();
        return (int) randomDataGenerator.nextInt(2, n);
    }

    private static int gcd(int a, int b) {
        while (b != 0) {
            int temp = b;
            b = a % b;
            a = temp;
        }
        return a;
    }

    private static int simulateQuantumPart(int a, int n) {
        
        
        

        RandomGenerator randomDataGenerator = new Random();
        return (int) randomDataGenerator.nextInt(1, n);
    }

    private static int computeFactor(int a, int n, int period) {
        return computeFactor(a, n, period, false);
    }

    private static int computeFactor(int a, int n, int period, boolean negative) {
        int factor = (int) Math.pow(a, period / 2);

        if (negative) {
            factor = -factor;
        }

        return gcd(factor + 1, n);
    }
}
