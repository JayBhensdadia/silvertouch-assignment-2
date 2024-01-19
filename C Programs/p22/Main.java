public class Main {
    public static void main(String[] args) {
        String str1 = "ABCBDAB";
        String str2 = "BDCAB";
        String str3 = "BADACB";

        int result = longestCommonSubsequence(str1, str2, str3);
        System.out.println("Length of Longest Common Subsequence: " + result);
    }

    public static int longestCommonSubsequence(String str1, String str2, String str3) {
        int[][][] dp = new int[str1.length() + 1][str2.length() + 1][str3.length() + 1];

        for (int i = 1; i <= str1.length(); i++) {
            for (int j = 1; j <= str2.length(); j++) {
                for (int k = 1; k <= str3.length(); k++) {
                    if (str1.charAt(i - 1) == str2.charAt(j - 1) && str1.charAt(i - 1) == str3.charAt(k - 1)) {
                        dp[i][j][k] = dp[i - 1][j - 1][k - 1] + 1;
                    } else {
                        dp[i][j][k] = Math.max(Math.max(dp[i - 1][j][k], dp[i][j - 1][k]), dp[i][j][k - 1]);
                    }
                }
            }
        }

        return dp[str1.length()][str2.length()][str3.length()];
    }
}
