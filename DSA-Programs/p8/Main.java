import java.util.ArrayList;
import java.util.List;

class Main {

    public static List<Integer> substringSegmentQuery(String s, int[][] queries) {
        int n = s.length();
        int[] count = new int[n + 1];
        List<Integer> results = new ArrayList<>();

        for (int i = 1; i <= n; i++) {
            count[i] = count[i - 1] + (s.charAt(i - 1) - 'A' + 1);
        }

        for (int[] query : queries) {
            int type = query[0];
            int index = query[1];

            if (type == 1) {
                results.add(count[index + 1]);
            } else {
                results.add(count[n] - count[index]);
            }
        }

        return results;
    }

    public static void main(String[] args) {
        String s1 = "AABBBCCCC";
        int[][] queries1 = {{1, 0}, {2, 1}, {1, 0}, {2, 2}, {1, 3}};

        String s2 = "XXYYY";
        int[][] queries2 = {{1, 3}, {2, 3}, {1, 2}};

        List<Integer> results1 = substringSegmentQuery(s1, queries1);
        List<Integer> results2 = substringSegmentQuery(s2, queries2);

        System.out.println("Results for s1:");
        System.out.println(results1);

        System.out.println("Results for s2:");
        System.out.println(results2);
    }
}
