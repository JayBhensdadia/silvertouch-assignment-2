package p91;

import java.math.BigInteger;
import java.security.SecureRandom;

class EllipticCurveFactorization {

    static class Point {
        BigInteger x, y;

        public Point(BigInteger x, BigInteger y) {
            this.x = x;
            this.y = y;
        }
    }

    private static final BigInteger TWO = BigInteger.valueOf(2);

    public static BigInteger ellipticCurveFactorization(BigInteger n, int B, int attempts) {
        for (int attempt = 0; attempt < attempts; attempt++) {
            BigInteger x = BigInteger.valueOf(new SecureRandom().nextInt(n.intValue()));
            BigInteger a = new BigInteger("2");
            BigInteger b = x.pow(2).subtract(BigInteger.ONE).mod(n);

            Point P = new Point(x, b.modPow(TWO, n));

            for (int j = 2; j <= B; j++) {
                P = doublePoint(P, a, n);

                if (!P.x.gcd(n).equals(BigInteger.ONE)) {
                    BigInteger factor = P.x.gcd(n);
                    if (!factor.equals(n)) {
                        return factor;
                    }
                    break;
                }
            }
        }

        return null;
    }

    private static Point doublePoint(Point P, BigInteger a, BigInteger n) {
        BigInteger lambda = P.x.multiply(P.y).multiply(TWO).mod(n);
        BigInteger mu = P.x.pow(2).subtract(BigInteger.ONE).multiply(P.y.pow(2).subtract(BigInteger.ONE).mod(n)).mod(n);

        BigInteger x = lambda.multiply(mu.pow(2)).subtract(P.x).subtract(P.x).mod(n);
        BigInteger y = lambda.multiply(mu.multiply(mu.subtract(BigInteger.ONE)).subtract(TWO.multiply(P.x))).mod(n);

        return new Point(x, y);
    }

    public static void main(String[] args) {
        BigInteger numberToFactorize = new BigInteger("5959"); // Replace with the number you want to factorize
        int B = 20; // Bound for smoothness
        int attempts = 10; // Number of random curves to try

        BigInteger factor = ellipticCurveFactorization(numberToFactorize, B, attempts);

        if (factor != null) {
            System.out.println("Factor found: " + factor);
        } else {
            System.out.println("Factorization failed. Try increasing the number of attempts or using a larger B.");
        }
    }
}

