import java.util.Arrays;

public class Main {

    private final int depth;
    private final int width;
    private final int[][] sketch;

    public Main(int depth, int width) {
        this.depth = depth;
        this.width = width;
        this.sketch = new int[depth][width];
    }

    public void update(String element, int count) {
        for (int i = 0; i < depth; i++) {
            int hashValue = hash(element, i);
            sketch[i][hashValue] += count;
        }
    }

    public int estimate(String element) {
        int minCount = Integer.MAX_VALUE;

        for (int i = 0; i < depth; i++) {
            int hashValue = hash(element, i);
            minCount = Math.min(minCount, sketch[i][hashValue]);
        }

        return minCount;
    }

    private int hash(String element, int index) {
        int hash = 7;
        for (int i = 0; i < element.length(); i++) {
            hash = hash * 31 + element.charAt(i);
        }
        return (hash + index) % width;
    }

    public static void main(String[] args) {
        Main countMinSketch = new Main(4, 10);

        countMinSketch.update("apple", 3);
        countMinSketch.update("banana", 5);
        countMinSketch.update("apple", 2);

        int appleCount = countMinSketch.estimate("apple");
        int bananaCount = countMinSketch.estimate("banana");

        System.out.println("count for 'apple': " + appleCount);
        System.out.println("count for 'banana': " + bananaCount);
    }
}
