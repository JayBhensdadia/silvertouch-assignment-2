public class Main {

    public static int maxCoins(int[] nums) {
        int n = nums.length;

        int[] extendedNums = new int[n + 2];
        extendedNums[0] = extendedNums[n + 1] = 1;
        System.arraycopy(nums, 0, extendedNums, 1, n);

        int[][] dp = new int[n + 2][n + 2];

        for (int len = 1; len <= n; len++) {
            for (int left = 1; left <= n - len + 1; left++) {
                int right = left + len - 1;

                for (int k = left; k <= right; k++) {
                    dp[left][right] = Math.max(dp[left][right],
                            extendedNums[left - 1] * extendedNums[k] * extendedNums[right + 1] +
                                    dp[left][k - 1] + dp[k + 1][right]);
                }
            }
        }

        return dp[1][n];
    }

    public static void main(String[] args) {
        int[] balloons = { 3, 1, 5, 8 };
        int maxCoins = maxCoins(balloons);

        System.out.println("Maximum Coins Obtained: " + maxCoins);
    }
}
