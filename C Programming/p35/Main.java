import java.math.BigInteger;
import java.security.SecureRandom;

public class Main {
    private static final int BIT_LENGTH = 1024;

    private BigInteger n;
    private BigInteger e;
    private BigInteger d;

    public Main() {
        generateKeyPair();
    }

    private void generateKeyPair() {
        SecureRandom random = new SecureRandom();

        BigInteger p = BigInteger.probablePrime(BIT_LENGTH, random);
        BigInteger q = BigInteger.probablePrime(BIT_LENGTH, random);

        n = p.multiply(q);

        BigInteger phi = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));

        do {
            e = new BigInteger(BIT_LENGTH, random);
        } while (e.compareTo(BigInteger.ONE) <= 0 || e.compareTo(phi) >= 0 || !e.gcd(phi).equals(BigInteger.ONE));

        d = e.modInverse(phi);
    }

    public BigInteger encrypt(BigInteger plaintext) {
        return plaintext.modPow(e, n);
    }

    public BigInteger decrypt(BigInteger ciphertext) {
        return ciphertext.modPow(d, n);
    }

    public static void main(String[] args) {
        Main rsa = new Main();

        BigInteger plaintext = new BigInteger("123");
        System.out.println("Original plaintext: " + plaintext);

        BigInteger ciphertext = rsa.encrypt(plaintext);
        System.out.println("Encrypted ciphertext: " + ciphertext);

        BigInteger decryptedText = rsa.decrypt(ciphertext);
        System.out.println("Decrypted plaintext: " + decryptedText);
    }
}
