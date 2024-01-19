import java.util.HashMap;
import java.util.Map;

public class Main {

    public static String compress(String input) {
        Map<String, Integer> dictionary = new HashMap<>();
        int dictSize = 256; 
        StringBuilder compressed = new StringBuilder();
        StringBuilder currentSubstring = new StringBuilder();

        for (char c : input.toCharArray()) {
            currentSubstring.append(c);
            if (!dictionary.containsKey(currentSubstring.toString())) {
                dictionary.put(currentSubstring.toString(), dictSize++);
                currentSubstring.setLength(currentSubstring.length() - 1);
                compressed.append(dictionary.get(currentSubstring.toString())).append(" ");
                currentSubstring.setLength(0);
                currentSubstring.append(c);
            }
        }

        if (currentSubstring.length() > 0) {
            compressed.append(dictionary.get(currentSubstring.toString())).append(" ");
        }

        return compressed.toString();
    }

    public static void main(String[] args) {
        String input = "ABABABAABABA";
        String compressedOutput = compress(input);
        System.out.println("Compressed Output: " + compressedOutput);
    }
}
