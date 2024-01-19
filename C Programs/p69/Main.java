import java.util.Random;

public class Main {

    private static double evaluateFunction(double x) {
        return x * x;
    }

    private static double hillClimbing(double initialX, double stepSize, int maxIterations) {
        double currentX = initialX;

        for (int i = 0; i < maxIterations; i++) {
            double currentValue = evaluateFunction(currentX);
            double nextX = currentX + stepSize;
            double nextValue = evaluateFunction(nextX);

            if (nextValue < currentValue) {
                currentX = nextX;
            } else {
                break;
            }
        }

        return currentX;
    }

    public static void main(String[] args) {
        Random random = new Random();
        
        double initialX = random.nextDouble() * 10 - 5;

        double stepSize = 0.1;

        int maxIterations = 100;

        double optimalX = hillClimbing(initialX, stepSize, maxIterations);

        System.out.println("Optimal X: " + optimalX);
        System.out.println("Optimal Value: " + evaluateFunction(optimalX));
    }
}
