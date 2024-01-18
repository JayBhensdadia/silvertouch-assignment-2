
import java.util.Random;

class ArbitraryRandomGenerator {

    private double[] probabilities;
    private int[] values;
    private Random random;

    public ArbitraryRandomGenerator(double[] probabilities, int[] values) {
        if (probabilities.length != values.length) {
            throw new IllegalArgumentException("Probabilities and values arrays must have the same length.");
        }

        this.probabilities = probabilities;
        this.values = values;
        this.random = new Random();
    }

    public int generateRandomNumber() {
        
        double sum = 0;
        for (double probability : probabilities) {
            sum += probability;
        }

        if (Math.abs(sum - 1.0) > 1e-6) {
            throw new IllegalArgumentException("Probabilities should sum to 1.");
        }

        
        double randomValue = random.nextDouble();

        
        double cumulativeProbability = 0;
        for (int i = 0; i < probabilities.length; i++) {
            cumulativeProbability += probabilities[i];
            if (randomValue <= cumulativeProbability) {
                return values[i];
            }
        }

        
        throw new IllegalStateException("Error in random number generation.");
    }

    
}

public class Main{
    public static void main(String[] args) {
        double[] probabilities = {0.2, 0.3, 0.5};
        int[] values = {1, 2, 3};
        int numSamples = 1000;

        ArbitraryRandomGenerator generator = new ArbitraryRandomGenerator(probabilities, values);

        
        for (int i = 0; i < numSamples; i++) {
            int randomNumber = generator.generateRandomNumber();
            System.out.print(randomNumber + " ");
        }
    }
}