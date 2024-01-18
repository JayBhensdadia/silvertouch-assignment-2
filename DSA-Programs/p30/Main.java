import java.util.BitSet;
import java.util.function.Function;

class BloomFilter<T> {
    private BitSet bitSet;
    private int size;
    private Function<T, Integer>[] hashFunctions;

    public BloomFilter(int size, Function<T, Integer>[] hashFunctions) {
        this.size = size;
        this.bitSet = new BitSet(size);
        this.hashFunctions = hashFunctions;
    }

    public void add(T element) {
        for (Function<T, Integer> hashFunction : hashFunctions) {
            int index = Math.abs(hashFunction.apply(element) % size);
            bitSet.set(index, true);
        }
    }

    public boolean contains(T element) {
        for (Function<T, Integer> hashFunction : hashFunctions) {
            int index = Math.abs(hashFunction.apply(element) % size);
            if (!bitSet.get(index)) {
                return false;
            }
        }
        return true;
    }

 
}




public class Main {

    public static void main(String[] args) {
        Function<String, Integer> hashFunction1 = String::hashCode;
        Function<String, Integer> hashFunction2 = s -> s.hashCode() * 31;

        BloomFilter<String> bloomFilter = new BloomFilter<>(100, new Function[]{hashFunction1, hashFunction2});

        bloomFilter.add("apple");
        bloomFilter.add("banana");
        bloomFilter.add("orange");

        System.out.println(bloomFilter.contains("apple"));    
        System.out.println(bloomFilter.contains("grapefruit")); 
    }
    
}