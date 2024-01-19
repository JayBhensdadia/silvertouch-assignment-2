import java.util.ArrayList;
import java.util.List;

public class Main {

    public static String shortestSuperstring(List<String> strings) {
        List<String> result = new ArrayList<>(strings);

        while (result.size() > 1) {
            int minOverlap = Integer.MAX_VALUE;
            int concatIndex1 = -1;
            int concatIndex2 = -1;

            for (int i = 0; i < result.size(); i++) {
                for (int j = i + 1; j < result.size(); j++) {
                    String str1 = result.get(i);
                    String str2 = result.get(j);

                    int overlap = getOverlapLength(str1, str2);

                    if (overlap < minOverlap) {
                        minOverlap = overlap;
                        concatIndex1 = i;
                        concatIndex2 = j;
                    }
                }
            }

            if (minOverlap == 0) {
                break;
            }

            String mergedString = mergeStrings(result.get(concatIndex1), result.get(concatIndex2), minOverlap);
            result.remove(concatIndex2);
            result.set(concatIndex1, mergedString);
        }

        return result.get(0);
    }

    private static int getOverlapLength(String str1, String str2) {
        int maxOverlap = Math.min(str1.length(), str2.length());

        for (int i = 1; i < maxOverlap; i++) {
            if (str1.endsWith(str2.substring(0, i))) {
                return i;
            }
        }

        return 0;
    }

    private static String mergeStrings(String str1, String str2, int overlap) {
        return str1 + str2.substring(overlap);
    }

    public static void main(String[] args) {
        List<String> strings = List.of("cat", "atg", "gat", "gatc", "tcg");

        String result = shortestSuperstring(strings);

        System.out.println("Shortest Superstring: " + result);
    }
}
