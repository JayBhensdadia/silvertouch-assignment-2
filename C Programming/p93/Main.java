package p93;

// Implement the Carter-Wegman Universal Hash Function with main method.

class CarterWegmanHash {
    private int p = 101; // a large prime number
    private int[] a; // an array of random integers

    public CarterWegmanHash(int size) {
        a = new int[size];
        for (int i = 0; i < size; i++) {
            a[i] = (int) (Math.random() * p);
        }
    }

    public int hashFunction(String key, int i) {
        int hash = 0;
        for (int j = 0; j < key.length(); j++) {
            hash += a[i] * key.charAt(j);
            hash %= p;
        }
        return hash;
    }

    public static void main(String[] args) {
        CarterWegmanHash cwHash = new CarterWegmanHash(10);
        String key = "hello";
        int hash1 = cwHash.hashFunction(key, 0);
        int hash2 = cwHash.hashFunction(key, 1);
        System.out.println("Hash values: " + hash1 + ", " + hash2);
    }
}