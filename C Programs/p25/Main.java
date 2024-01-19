public class Main {
    public static void main(String[] args) {
        int[] nums = {3, 34, 4, 12, 5, 2};
        int targetSum = 19;

        boolean result = subsetSum(nums, targetSum);
        System.out.println("Subset with the sum " + targetSum + " exists: " + result);
    }

    public static boolean subsetSum(int[] nums, int targetSum) {
        int n = nums.length;
        boolean[][] dp = new boolean[n + 1][targetSum + 1];

        for (int i = 0; i <= n; i++) {
            dp[i][0] = true;
        }

        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= targetSum; j++) {
                if (j < nums[i - 1]) {
                    dp[i][j] = dp[i - 1][j];
                } else {
                    dp[i][j] = dp[i - 1][j] || dp[i - 1][j - nums[i - 1]];
                }
            }
        }

        return dp[n][targetSum];
    }
}
