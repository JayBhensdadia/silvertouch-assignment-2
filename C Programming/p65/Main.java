import java.util.Arrays;
import java.util.Random;
import java.util.function.Function;

public class Main {

    public static double[] optimize(Function<double[], Double> function, int populationSize,
                                    int maxGenerations, double crossoverRate, double scalingFactor) {
        Random random = new Random();
        // int dimension = function.apply(new double[1]).length;
        int xx = double[1].length;
        int dimension = function.apply(xx);

        double[][] population = new double[populationSize][dimension];
        for (int i = 0; i < populationSize; i++) {
            for (int j = 0; j < dimension; j++) {
                population[i][j] = random.nextDouble();
            }
        }

        for (int generation = 0; generation < maxGenerations; generation++) {
            for (int i = 0; i < populationSize; i++) {
                int r1, r2, r3;
                do {
                    r1 = random.nextInt(populationSize);
                } while (r1 == i);
                do {
                    r2 = random.nextInt(populationSize);
                } while (r2 == i || r2 == r1);
                do {
                    r3 = random.nextInt(populationSize);
                } while (r3 == i || r3 == r1 || r3 == r2);

                double[] trialVector = new double[dimension];
                for (int j = 0; j < dimension; j++) {
                    double rand = random.nextDouble();
                    if (rand < crossoverRate || j == random.nextInt(dimension)) {
                        trialVector[j] = population[r1][j] + scalingFactor * (population[r2][j] - population[r3][j]);
                    } else {
                        trialVector[j] = population[i][j];
                    }
                }

                double fitnessTrial = function.apply(trialVector);
                double fitnessCurrent = function.apply(population[i]);

                if (fitnessTrial < fitnessCurrent) {
                    for (int j = 0; j < dimension; j++) {
                        population[i][j] = trialVector[j];
                    }
                }
            }
        }

        double[] bestIndividual = population[0];
        double bestFitness = function.apply(bestIndividual);

        for (int i = 1; i < populationSize; i++) {
            double fitness = function.apply(population[i]);
            if (fitness < bestFitness) {
                bestFitness = fitness;
                bestIndividual = Arrays.copyOf(population[i], dimension);
            }
        }

        return bestIndividual;
    }

    public static void main(String[] args) {
        Function<double[], Double> sphereFunction = vector -> Arrays.stream(vector)
                .map(x -> x * x)
                .sum();

        int populationSize = 50;
        int maxGenerations = 100;
        double crossoverRate = 0.8;
        double scalingFactor = 0.8;

        double[] result = optimize(sphereFunction, populationSize, maxGenerations, crossoverRate, scalingFactor);

        System.out.println("Optimal Solution: " + Arrays.toString(result));
        System.out.println("Optimal Value: " + sphereFunction.apply(result));
    }
}
