import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        String input = "banana";
        String bwtResult = burrowsWheelerTransform(input);
        System.out.println("Burrows-Wheeler Transform Result: " + bwtResult);
    }

    public static String burrowsWheelerTransform(String input) {
        int length = input.length();
        String[] rotations = new String[length];

        for (int i = 0; i < length; i++) {
            rotations[i] = input.substring(i) + input.substring(0, i);
        }

        Arrays.sort(rotations);

        StringBuilder result = new StringBuilder();
        for (String rotation : rotations) {
            result.append(rotation.charAt(length - 1));
        }

        return result.toString();
    }
}
