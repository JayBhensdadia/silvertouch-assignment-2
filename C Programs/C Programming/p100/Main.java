import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class Main {

    public static int minimumTotal(List<List<Integer>> triangle) {
        int[] dp = new int[triangle.size() + 1];

        for (int row = triangle.size() - 1; row >= 0; row--) {
            List<Integer> currentRow = triangle.get(row);
            for (int i = 0; i < currentRow.size(); i++) {
                dp[i] = currentRow.get(i) + Math.min(dp[i], dp[i + 1]);
            }
        }
        return dp[0];
    }

    public static void main(String[] args) {
        List<List<Integer>> triangle = new ArrayList<>();
        triangle.add(Arrays.asList(2));
        triangle.add(Arrays.asList(3, 4));
        triangle.add(Arrays.asList(6, 5, 7));
        triangle.add(Arrays.asList(4, 1, 8, 3));

        int result = minimumTotal(triangle);
        System.out.println("Minimum Path Sum: " + result);
    }
}
