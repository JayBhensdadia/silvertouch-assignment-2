import java.util.BitSet;

class Main {
    private static final int DEFAULT_SIZE = 1000;
    private static final int[] PRIME_NUMBERS = { 2, 3, 5, 7, 11, 13, 17, 19, 23, 29 };

    private BitSet bitSet;
    private int numHashFunctions;
    private int size;

    public Main() {
        this(DEFAULT_SIZE);
    }

    public Main(int size) {
        this.size = size;
        this.bitSet = new BitSet(size);
        this.numHashFunctions = 3; 
    }

    private int[] getHashValues(String element) {
        int[] hashValues = new int[numHashFunctions];

        for (int i = 0; i < numHashFunctions; i++) {
            hashValues[i] = Math.abs((element.hashCode() + PRIME_NUMBERS[i]) % size);
        }

        return hashValues;
    }

    public void add(String element) {
        int[] hashValues = getHashValues(element);

        for (int hashValue : hashValues) {
            bitSet.set(hashValue);
        }

        checkAndResize();
    }

    public boolean contains(String element) {
        int[] hashValues = getHashValues(element);

        for (int hashValue : hashValues) {
            if (!bitSet.get(hashValue)) {
                return false;
            }
        }

        return true;
    }

    private void checkAndResize() {
        if (bitSet.cardinality() > (size * 0.75)) {
            size *= 2;
            BitSet newBitSet = new BitSet(size);
            newBitSet.or(bitSet);
            bitSet = newBitSet;
        }
    }

    public static void main(String[] args) {
        Main bloomFilter = new Main();

        bloomFilter.add("jamrukh");

        System.out.println(bloomFilter.contains("jamrukh"));   
        System.out.println(bloomFilter.contains("grape"));   
    }
}
