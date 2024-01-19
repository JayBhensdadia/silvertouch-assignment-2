import java.util.BitSet;
import java.util.function.Function;

public class Main<T> {
    private final int size;
    private final int numHashFunctions;
    private final BitSet bitSet;
    private final Function<T, Integer>[] hashFunctions;

    public Main(int size, int numHashFunctions) {
        this.size = size;
        this.numHashFunctions = numHashFunctions;
        this.bitSet = new BitSet(size);
        this.hashFunctions = createHashFunctions();
    }

    public void add(T element) {
        for (Function<T, Integer> hashFunction : hashFunctions) {
            int hash = hashFunction.apply(element);
            int index = hash % size;
            bitSet.set(index);
        }
    }

    public boolean contains(T element) {
        for (Function<T, Integer> hashFunction : hashFunctions) {
            int hash = hashFunction.apply(element);
            int index = hash % size;
            if (!bitSet.get(index)) {
                return false;
            }
        }
        return true;
    }

    @SafeVarargs
    private final Function<T, Integer>[] createHashFunctions(Function<T, Integer>... hashFunctions) {
        Function<T, Integer>[] newHashFunctions = new Function[numHashFunctions];
        for (int i = 0; i < numHashFunctions; i++) {
            if (i < hashFunctions.length) {
                newHashFunctions[i] = hashFunctions[i];
            } else {
                newHashFunctions[i] = createDefaultHashFunction(i);
            }
        }
        return newHashFunctions;
    }

    private Function<T, Integer> createDefaultHashFunction(int seed) {
        return element -> {
            int hash = 17;
            hash = 31 * hash + seed;
            hash = 31 * hash + (element == null ? 0 : element.hashCode());
            return Math.abs(hash);
        };
    }

    public static void main(String[] args) {
        Main<String> bloomFilter = new Main<>(1000, 3);

        bloomFilter.add("apple");
        bloomFilter.add("banana");
        bloomFilter.add("orange");

        System.out.println("Contains 'apple': " + bloomFilter.contains("apple"));
        System.out.println("Contains 'grape': " + bloomFilter.contains("grape"));
    }
}
