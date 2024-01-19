package C.p78;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

interface FitnessFunction {
    double evaluate(List<Double> inputs);
}

class Individual {
    String expression;

    public Individual(String expression) {
        this.expression = expression;
    }

}

class GeneticProgramming {
    private static final int POPULATION_SIZE = 100;
    private static final int MAX_GENERATIONS = 50;
    private static final Random random = new Random();

    private FitnessFunction fitnessFunction;

    public GeneticProgramming(FitnessFunction fitnessFunction) {
        this.fitnessFunction = fitnessFunction;
    }

    public Individual evolve() {
        List<Individual> population = initializePopulation();

        for (int generation = 0; generation < MAX_GENERATIONS; generation++) {
            List<Individual> nextGeneration = new ArrayList<>();

            for (int i = 0; i < POPULATION_SIZE; i++) {
                Individual parent1 = selectParent(population);
                Individual parent2 = selectParent(population);

                Individual child = crossover(parent1, parent2);
                mutate(child);

                nextGeneration.add(child);
            }

            population = nextGeneration;
        }

        return getBestIndividual(population);
    }

    private List<Individual> initializePopulation() {
        List<Individual> population = new ArrayList<>();
        for (int i = 0; i < POPULATION_SIZE; i++) {
            String randomExpression = generateRandomExpression();
            population.add(new Individual(randomExpression));
        }
        return population;
    }

    private String generateRandomExpression() {

        return "x + 2 * x";
    }

    private Individual selectParent(List<Individual> population) {
        return population.get(random.nextInt(POPULATION_SIZE));
    }

    private Individual crossover(Individual parent1, Individual parent2) {
        return random.nextBoolean() ? parent1 : parent2;
    }

    private void mutate(Individual individual) {
    }

    private Individual getBestIndividual(List<Individual> population) {
        Individual bestIndividual = population.get(0);
        double bestFitness = evaluateFitness(bestIndividual);

        for (Individual individual : population) {
            double fitness = evaluateFitness(individual);
            if (fitness < bestFitness) {
                bestFitness = fitness;
                bestIndividual = individual;
            }
        }

        return bestIndividual;
    }

    private double evaluateFitness(Individual individual) {
        List<Double> inputs = new ArrayList<>();
        for (double x = -5.0; x <= 5.0; x += 0.5) {
            inputs.add(x);
        }

        double fitness = 0.0;
        for (Double input : inputs) {
            fitness += Math.abs(fitnessFunction.evaluate(List.of(input)) - evaluateExpression(individual.expression, input));
        }

        return fitness;
    }

    private double evaluateExpression(String expression, double x) {
        return 0.0;
    }

    public static void main(String[] args) {
        GeneticProgramming gp = new GeneticProgramming(inputs -> Math.sin(inputs.get(0)) + Math.cos(2 * inputs.get(0)));
        Individual bestIndividual = gp.evolve();
        System.out.println("Best individual: " + bestIndividual.expression);
    }
}
