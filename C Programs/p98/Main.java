import java.util.Arrays;
import java.util.Random;

class Graph {
    private int[][] adjacencyMatrix;

    public Graph(int[][] adjacencyMatrix) {
        this.adjacencyMatrix = adjacencyMatrix;
    }

    public int[][] getAdjacencyMatrix() {
        return adjacencyMatrix;
    }

    public int calculateFitness() {

        int count = 0;
        for (int i = 0; i < adjacencyMatrix.length; i++) {
            for (int j = i + 1; j < adjacencyMatrix.length; j++) {
                if (adjacencyMatrix[i][j] == 1) {
                    count++;
                }
            }
        }
        return count;
    }
}

class Main {
    private static final int POPULATION_SIZE = 10;
    private static final double MUTATION_RATE = 0.1;

    public static Graph[] initializePopulation(int graphSize) {
        Graph[] population = new Graph[POPULATION_SIZE];
        for (int i = 0; i < POPULATION_SIZE; i++) {
            int[][] adjacencyMatrix = generateRandomGraph(graphSize);
            population[i] = new Graph(adjacencyMatrix);
        }
        return population;
    }

    private static int[][] generateRandomGraph(int graphSize) {
        int[][] adjacencyMatrix = new int[graphSize][graphSize];
        Random random = new Random();
        for (int i = 0; i < graphSize; i++) {
            for (int j = i + 1; j < graphSize; j++) {
                adjacencyMatrix[i][j] = adjacencyMatrix[j][i] = random.nextInt(2);
            }
        }
        return adjacencyMatrix;
    }

    private static Graph[] selectParents(Graph[] population) {

        Random random = new Random();
        Graph[] parents = new Graph[2];
        parents[0] = population[random.nextInt(POPULATION_SIZE)];
        parents[1] = population[random.nextInt(POPULATION_SIZE)];
        return parents;
    }

    private static Graph crossover(Graph parent1, Graph parent2) {

        int[][] childMatrix = new int[parent1.getAdjacencyMatrix().length][parent1.getAdjacencyMatrix().length];
        for (int i = 0; i < parent1.getAdjacencyMatrix().length; i++) {
            for (int j = 0; j < parent1.getAdjacencyMatrix().length; j++) {
                childMatrix[i][j] = (i % 2 == 0) ? parent1.getAdjacencyMatrix()[i][j]
                        : parent2.getAdjacencyMatrix()[i][j];
            }
        }
        return new Graph(childMatrix);
    }

    private static void mutate(Graph individual) {

        Random random = new Random();
        int[][] adjacencyMatrix = individual.getAdjacencyMatrix();
        int i = random.nextInt(adjacencyMatrix.length);
        int j = random.nextInt(adjacencyMatrix.length);
        adjacencyMatrix[i][j] = 1 - adjacencyMatrix[i][j];
    }

    public static void main(String[] args) {
        int graphSize = 5;
        Graph[] population = initializePopulation(graphSize);

        for (int generation = 1; generation <= 100; generation++) {

            Arrays.sort(population, (g1, g2) -> Integer.compare(g2.calculateFitness(), g1.calculateFitness()));

            Graph[] newPopulation = new Graph[POPULATION_SIZE];
            for (int i = 0; i < POPULATION_SIZE; i += 2) {
                Graph[] parents = selectParents(population);
                Graph child1 = crossover(parents[0], parents[1]);
                Graph child2 = crossover(parents[1], parents[0]);
                if (Math.random() < MUTATION_RATE) {
                    mutate(child1);
                }
                if (Math.random() < MUTATION_RATE) {
                    mutate(child2);
                }
                newPopulation[i] = child1;
                newPopulation[i + 1] = child2;
            }

            population = newPopulation;

            System.out.println("Generation " + generation + ": Best Fitness = " + population[0].calculateFitness());
        }
    }
}
