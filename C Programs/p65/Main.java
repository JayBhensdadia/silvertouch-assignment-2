import java.util.Random;

public class Main {
    private static final int POPULATION_SIZE = 50;
    private static final int DIMENSION = 10;
    private static final int MAX_GENERATIONS = 1000;
    private static final double CROSSOVER_RATE = 0.8;
    private static final double MUTATION_FACTOR = 0.5;

    private static final double LOWER_BOUND = -5.12;
    private static final double UPPER_BOUND = 5.12;

    private static double[][] population;

    public static void main(String[] args) {
        initializePopulation();
        optimize();

        System.out.println("Optimal solution found:");
        printVector(population[0]);
    }

    private static void initializePopulation() {
        population = new double[POPULATION_SIZE][DIMENSION];
        Random random = new Random();

        for (int i = 0; i < POPULATION_SIZE; i++) {
            for (int j = 0; j < DIMENSION; j++) {
                population[i][j] = LOWER_BOUND + random.nextDouble() * (UPPER_BOUND - LOWER_BOUND);
            }
        }
    }

    private static double fitnessFunction(double[] vector) {
        // Example: Sphere Function
        double sum = 0;
        for (double value : vector) {
            sum += value * value;
        }
        return sum;
    }

    private static void optimize() {
        for (int generation = 0; generation < MAX_GENERATIONS; generation++) {
            for (int i = 0; i < POPULATION_SIZE; i++) {
                double[] targetVector = population[i];

                int r1, r2, r3;
                do {
                    r1 = getRandomIndex(POPULATION_SIZE, i);
                } while (r1 == i);

                do {
                    r2 = getRandomIndex(POPULATION_SIZE, i, r1);
                } while (r2 == i || r2 == r1);

                do {
                    r3 = getRandomIndex(POPULATION_SIZE, i, r1, r2);
                } while (r3 == i || r3 == r1 || r3 == r2);

                double[] mutantVector = createMutantVector(population[r1], population[r2], population[r3]);
                double[] trialVector = crossover(targetVector, mutantVector);

                double targetFitness = fitnessFunction(targetVector);
                double trialFitness = fitnessFunction(trialVector);

                if (trialFitness < targetFitness) {
                    population[i] = trialVector;
                }
            }
        }
    }

    private static int getRandomIndex(int max, int... exclude) {
        Random random = new Random();
        int index;
        do {
            index = random.nextInt(max);
        } while (contains(exclude, index));
        return index;
    }

    private static boolean contains(int[] array, int value) {
        for (int element : array) {
            if (element == value) {
                return true;
            }
        }
        return false;
    }

    private static double[] createMutantVector(double[] target, double[] donor1, double[] donor2) {
        double[] mutantVector = new double[DIMENSION];

        for (int i = 0; i < DIMENSION; i++) {
            mutantVector[i] = donor1[i] + MUTATION_FACTOR * (donor2[i] - target[i]);
        }

        return mutantVector;
    }

    private static double[] crossover(double[] target, double[] mutant) {
        double[] trialVector = new double[DIMENSION];
        Random random = new Random();

        for (int i = 0; i < DIMENSION; i++) {
            trialVector[i] = random.nextDouble() < CROSSOVER_RATE ? mutant[i] : target[i];
        }

        return trialVector;
    }

    private static void printVector(double[] vector) {
        for (double value : vector) {
            System.out.print(value + " ");
        }
        System.out.println();
    }
}
