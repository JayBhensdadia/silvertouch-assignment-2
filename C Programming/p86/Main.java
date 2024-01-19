package C.p86;

 class LinearCongruentialGenerator {
    private long seed;
    private final long a;
    private final long c;
    private final long m;

    public LinearCongruentialGenerator(long seed, long a, long c, long m) {
        this.seed = seed;
        this.a = a;
        this.c = c;
        this.m = m;
    }

    public long next() {
        seed = (a * seed + c) % m;
        return seed;
    }

    public static void main(String[] args) {
        long seed = 123;
        long a = 1664525;
        long c = 1013904223;
        long m = (long) Math.pow(2, 32);

        LinearCongruentialGenerator lcg = new LinearCongruentialGenerator(seed, a, c, m);

        for (int i = 0; i < 10; i++) {
            System.out.println(lcg.next());
        }
    }
}

public class Main {
}
