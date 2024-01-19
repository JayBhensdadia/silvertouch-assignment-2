class Main {
    private int p = 101; 
    private int[] a;

    public Main(int size) {
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
        Main cwHash = new Main(10);
        String key = "hello";
        int hash1 = cwHash.hashFunction(key, 0);
        int hash2 = cwHash.hashFunction(key, 1);
        System.out.println("Hash values: " + hash1 + ", " + hash2);
    }
}