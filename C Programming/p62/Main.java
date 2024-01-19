public class Main {

    public static int minDistance(String word1, String word2) {
        int m = word1.length();
        int n = word2.length();

        int[][] dp = new int[m + 1][n + 1];

        for (int i = 0; i <= m; i++) {
            for (int j = 0; j <= n; j++) {
                if (i == 0) {
                    dp[i][j] = j;
                } else if (j == 0) {
                    dp[i][j] = i;
                } else if (word1.charAt(i - 1) == word2.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1];
                } else {
                    dp[i][j] = 1 + min(dp[i - 1][j], dp[i][j - 1], dp[i - 1][j - 1]);
                }
            }
        }

        printEditOperations(dp, word1, word2);

        return dp[m][n];
    }

    private static int min(int a, int b, int c) {
        return Math.min(Math.min(a, b), c);
    }

    private static void printEditOperations(int[][] dp, String word1, String word2) {
        int i = dp.length - 1;
        int j = dp[0].length - 1;

        while (i > 0 || j > 0) {
            if (i > 0 && dp[i][j] == 1 + dp[i - 1][j]) {
                System.out.println("Delete '" + word1.charAt(i - 1) + "' from word1");
                i--;
            } else if (j > 0 && dp[i][j] == 1 + dp[i][j - 1]) {
                System.out.println("Insert '" + word2.charAt(j - 1) + "' into word1");
                j--;
            } else {
                if (i > 0 && j > 0 && word1.charAt(i - 1) != word2.charAt(j - 1)) {
                    System.out.println("Replace '" + word1.charAt(i - 1) + "' with '" + word2.charAt(j - 1) + "'");
                }
                i--;
                j--;
            }
        }
    }

    public static void main(String[] args) {
        String word1 = "horse";
        String word2 = "ros";

        System.out.println("Minimum Edit Distance: " + minDistance(word1, word2));
    }
}
