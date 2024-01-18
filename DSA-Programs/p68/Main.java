import java.util.ArrayDeque;
import java.util.Deque;

class LinearFunction {
    long slope;
    long intercept;

    public LinearFunction(long slope, long intercept) {
        this.slope = slope;
        this.intercept = intercept;
    }

    public long evaluate(long x) {
        return slope * x + intercept;
    }
}

class ConvexHullTrick {
    Deque<LinearFunction> hull;

    public ConvexHullTrick() {
        hull = new ArrayDeque<>();
    }

    private double intersection(LinearFunction f1, LinearFunction f2) {
        return (double) (f2.intercept - f1.intercept) / (f1.slope - f2.slope);
    }

    public void addFunction(long slope, long intercept) {
        LinearFunction newFunction = new LinearFunction(slope, intercept);

        while (hull.size() >= 2) {
            LinearFunction f2 = hull.pollLast();
            LinearFunction f1 = hull.peekLast();

            if (intersection(f1, newFunction) > intersection(f1, f2)) {
                hull.addLast(f2);
                break;
            }
        }

        hull.addLast(newFunction);
    }

    public long queryMaxValue(long x) {
        while (hull.size() >= 2 && hull.peekFirst().evaluate(x) < hull.peek().evaluate(x)) {
            hull.pollFirst();
        }

        return hull.peekFirst().evaluate(x);
    }
}

public class Main {
    public static void main(String[] args) {
        ConvexHullTrick convexHullTrick = new ConvexHullTrick();

        convexHullTrick.addFunction(2, 3);
        convexHullTrick.addFunction(-1, 5);
        convexHullTrick.addFunction(3, 1);

        long queryPoint = 4;
        long maxValue = convexHullTrick.queryMaxValue(queryPoint);
        System.out.println("Maximum value at x=" + queryPoint + ": " + maxValue);
    }
}