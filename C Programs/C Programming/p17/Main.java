import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {
    private static final String ALPHABET = "abcdefghijklmnopqrstuvwxyz";
    private static final Map<Character, Character> encryptionKey = generateEncryptionKey();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the message to encrypt: ");
        String message = scanner.nextLine();

        String encryptedMessage = encrypt(message);
        System.out.println("Encrypted message: " + encryptedMessage);

        String decryptedMessage = decrypt(encryptedMessage);
        System.out.println("Decrypted message: " + decryptedMessage);

        scanner.close();
    }

    private static Map<Character, Character> generateEncryptionKey() {
        Map<Character, Character> key = new HashMap<>();
        for (int i = 0; i < ALPHABET.length(); i++) {
            char originalChar = ALPHABET.charAt(i);
            char encryptedChar = ALPHABET.charAt((i + 3) % ALPHABET.length()); 
            key.put(originalChar, encryptedChar);
        }
        return key;
    }

    private static String encrypt(String message) {
        StringBuilder encryptedMessage = new StringBuilder();
        for (char c : message.toCharArray()) {
            if (Character.isLetter(c)) {
                char encryptedChar = encryptionKey.get(Character.toLowerCase(c));
                encryptedMessage.append(Character.isUpperCase(c) ? Character.toUpperCase(encryptedChar) : encryptedChar);
            } else {
                encryptedMessage.append(c);
            }
        }
        return encryptedMessage.toString();
    }

    private static String decrypt(String encryptedMessage) {
        StringBuilder decryptedMessage = new StringBuilder();
        for (char c : encryptedMessage.toCharArray()) {
            if (Character.isLetter(c)) {
                char decryptedChar = getKeyByValue(encryptionKey, Character.toLowerCase(c));
                decryptedMessage.append(Character.isUpperCase(c) ? Character.toUpperCase(decryptedChar) : decryptedChar);
            } else {
                decryptedMessage.append(c);
            }
        }
        return decryptedMessage.toString();
    }

    private static <K, V> K getKeyByValue(Map<K, V> map, V value) {
        for (Map.Entry<K, V> entry : map.entrySet()) {
            if (value.equals(entry.getValue())) {
                return entry.getKey();
            }
        }
        return null;
    }
}

