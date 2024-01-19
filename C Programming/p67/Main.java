import java.util.Arrays;
import java.util.Comparator;

class Item {
    int value;
    int weight;

    public Item(int value, int weight) {
        this.value = value;
        this.weight = weight;
    }
}

public class Main {

    public static double fractionalKnapsack(int[] values, int[] weights, int capacity) {
        int n = values.length;

        Item[] items = new Item[n];
        for (int i = 0; i < n; i++) {
            items[i] = new Item(values[i], weights[i]);
        }

        Arrays.sort(items, Comparator.comparingDouble(item -> -((double) item.value / item.weight)));

        double maxTotalValue = 0.0;
        int remainingCapacity = capacity;

        for (Item item : items) {
            if (remainingCapacity == 0) {
                break;
            }

            int weightToAdd = Math.min(remainingCapacity, item.weight);
            maxTotalValue += (double) weightToAdd * item.value / item.weight;
            remainingCapacity -= weightToAdd;
        }

        return maxTotalValue;
    }

    public static void main(String[] args) {
        int[] values = { 60, 100, 120 };
        int[] weights = { 10, 20, 30 };
        int capacity = 50;

        double maxTotalValue = fractionalKnapsack(values, weights, capacity);
        System.out.println("Maximum Total Value in Knapsack: " + maxTotalValue);
    }
}
