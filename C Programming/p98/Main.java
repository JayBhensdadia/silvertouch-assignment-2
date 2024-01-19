package p98;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

class Graph {
    private int[][] adjacencyMatrix;

    public Graph(int[][] adjacencyMatrix) {
        this.adjacencyMatrix = adjacencyMatrix;
    }

    public int getVertexCount() {
        return adjacencyMatrix.length;
    }

    public int getEdgeWeight(int src, int dest) {
        return adjacencyMatrix[src][dest];
    }
}

class Individual {
    private List<Integer> path;
    private int fitness;

    public Individual(List<Integer> path, Graph graph) {
        this.path = path;
        calculateFitness(graph);
    }

    public List<Integer> getPath() {
        return path;
    }

    public int getFitness() {
        return fitness;
    }

    void calculateFitness(Graph graph) {
        int totalWeight = 0;
        int n = graph.getVertexCount();

        for (int i = 0; i < n - 1; i++) {
            int src = path.get(i);
            int dest = path.get(i + 1);
            totalWeight += graph.getEdgeWeight(src, dest);
        }

        
        totalWeight += graph.getEdgeWeight(path.get(n - 1), path.get(0));

        this.fitness = totalWeight;
    }
}

class GraphGeneticAlgorithm {

    public static List<Individual> initializePopulation(int populationSize, Graph graph) {
        List<Individual> population = new ArrayList<>();

        for (int i = 0; i < populationSize; i++) {
            List<Integer> path = generateRandomPath(graph.getVertexCount());
            population.add(new Individual(path, graph));
        }

        return population;
    }

    private static List<Integer> generateRandomPath(int size) {
        List<Integer> path = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            path.add(i);
        }
        Collections.shuffle(path);
        return path;
    }

    public static List<Individual> crossover(Individual parent1, Individual parent2) {
        
        int size = parent1.getPath().size();
        int startPos = new Random().nextInt(size);
        int endPos = new Random().nextInt(size - startPos) + startPos;

        List<Integer> childPath = new ArrayList<>(parent1.getPath().subList(startPos, endPos));

        for (int gene : parent2.getPath()) {
            if (!childPath.contains(gene)) {
                childPath.add(gene);
            }
        }

        Individual child = new Individual(childPath, new Graph(new int[0][0]));
        return Collections.singletonList(child);
    }

    public static void mutate(Individual individual) {
        
        int size = individual.getPath().size();
        int pos1 = new Random().nextInt(size);
        int pos2 = new Random().nextInt(size);

        Collections.swap(individual.getPath(), pos1, pos2);
        individual.calculateFitness(new Graph(new int[0][0]));  
    }

    public static List<Individual> geneticAlgorithm(Graph graph, int populationSize, int generations) {
        List<Individual> population = initializePopulation(populationSize, graph);

        for (int generation = 0; generation < generations; generation++) {
            
            List<Individual> parents = tournamentSelection(population);

            
            List<Individual> offspring = crossover(parents.get(0), parents.get(1));

            
            for (Individual child : offspring) {
                mutate(child);
            }

            
            for (Individual child : offspring) {
                population.add(child);
            }

            
            population = selectNextGeneration(population, populationSize);
        }

        
        return population;
    }

    private static List<Individual> tournamentSelection(List<Individual> population) {
        Collections.shuffle(population);
        int tournamentSize = 5;
        List<Individual> tournament = population.subList(0, tournamentSize);
        return (List<Individual>) tournament.stream().min((ind1, ind2) -> Integer.compare(ind1.getFitness(), ind2.getFitness())).orElse(null);
    }

    private static List<Individual> selectNextGeneration(List<Individual> population, int populationSize) {
        Collections.sort(population, (ind1, ind2) -> Integer.compare(ind1.getFitness(), ind2.getFitness()));
        return new ArrayList<>(population.subList(0, populationSize));
    }

    public static void main(String[] args) {
        int[][] adjacencyMatrix = {
                {0, 29, 20, 21},
                {29, 0, 15, 30},
                {20, 15, 0, 17},
                {21, 30, 17, 0}
        };

        Graph graph = new Graph(adjacencyMatrix);

        int populationSize = 50;
        int generations = 100;

        List<Individual> finalPopulation = geneticAlgorithm(graph, populationSize, generations);

        
        
    }
}
