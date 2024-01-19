package p87;

import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.List;

class LZWCompression {

    public static List<Integer> compress(String input) {
        Map<String, Integer> dictionary = new HashMap<>();
        List<Integer> result = new ArrayList<>();
        int currentCode = 256;

        StringBuilder currentPhrase = new StringBuilder();
        for (char c : input.toCharArray()) {
            currentPhrase.append(c);
            if (!dictionary.containsKey(currentPhrase.toString())) {
                dictionary.put(currentPhrase.toString(), currentCode++);
                currentPhrase.deleteCharAt(currentPhrase.length() - 1);
                result.add(dictionary.get(currentPhrase.toString()));
                dictionary.put(currentPhrase.append(c).toString(), currentCode++);
            }
        }
        if (currentPhrase.length() > 0) {
            result.add(dictionary.get(currentPhrase.toString()));
        }
        return result;
    }

    public static String decompress(List<Integer> compressed) {
        Map<Integer, String> dictionary = new HashMap<>();
        int currentCode = 256;

        for (int i = 0; i < 256; i++) {
            dictionary.put(i, String.valueOf((char) i));
        }

        StringBuilder result = new StringBuilder();
        String currentPhrase = String.valueOf((char) (int) compressed.get(0));
        result.append(currentPhrase);

        for (int i = 1; i < compressed.size(); i++) {
            int code = compressed.get(i);
            String entry;
            if (dictionary.containsKey(code)) {
                entry = dictionary.get(code);
            } else if (code == currentCode) {
                entry = currentPhrase + currentPhrase.charAt(0);
            } else {
                throw new IllegalArgumentException("Invalid compressed data");
            }

            result.append(entry);
            dictionary.put(currentCode++, currentPhrase + entry.charAt(0));
            currentPhrase = entry;
        }
        return result.toString();
    }

    public static void main(String[] args) {
        String input = "abababababababab";
        List<Integer> compressed = compress(input);
        System.out.println("Compressed: " + compressed);

        String decompressed = decompress(compressed);
        System.out.println("Decompressed: " + decompressed);
    }
}

