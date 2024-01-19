package C.p82;
import java.util.BitSet;
import java.util.Random;

class BloomFilter {
    private final BitSet bitSet;
    private final int size;
    private final int hashFunctions;

    public BloomFilter(int size, int hashFunctions) {
        this.bitSet = new BitSet(size);
        this.size = size;
        this.hashFunctions = hashFunctions;
    }

    public void add(String element) {
        for (int i = 0; i < hashFunctions; i++) {
            int hash = hash(element, i);
            bitSet.set(hash, true);
        }
    }

    public boolean contains(String element) {
        for (int i = 0; i < hashFunctions; i++) {
            int hash = hash(element, i);
            if (!bitSet.get(hash)) {
                return false;
            }
        }
        return true;
    }

    private int hash(String element, int seed) {
        Random random = new Random(seed);
        return Math.abs(random.nextInt()) % size;
    }

    public double calculateFalsePositiveProbability(int elementsAdded, int totalElements) {
        double probability = Math.pow(1 - Math.exp(-hashFunctions * (double) elementsAdded / size), hashFunctions);
        return Math.pow(probability, totalElements);
    }

    public static void main(String[] args) {
        int size = 1000;
        int hashFunctions = 5;
        BloomFilter bloomFilter = new BloomFilter(size, hashFunctions);

        bloomFilter.add("example1");
        bloomFilter.add("example2");
        bloomFilter.add("example3");

        System.out.println("Bloom Filter contains 'example1': " + bloomFilter.contains("example1"));
        System.out.println("Bloom Filter contains 'example4': " + bloomFilter.contains("example4"));

        int elementsAdded = 3;
        int totalElements = 10;
        double falsePositiveProbability = bloomFilter.calculateFalsePositiveProbability(elementsAdded, totalElements);
        System.out.println("False Positive Probability: " + falsePositiveProbability);
    }
}
public class Main {
}

