public class Main {

    public static int maxSumIncreasingSubsequence(int[] nums) {
        int n = nums.length;
        int[] dp = new int[n];
        int maxSum = Integer.MIN_VALUE;

        for (int i = 0; i < n; i++) {
            dp[i] = nums[i];
            for (int j = 0; j < i; j++) {
                if (nums[i] > nums[j]) {
                    dp[i] = Math.max(dp[i], dp[j] + nums[i]);
                }
            }
            maxSum = Math.max(maxSum, dp[i]);
        }

        return maxSum;
    }

    public static void main(String[] args) {
        int[] nums = {1, 101, 2, 3, 100, 4, 5};
        System.out.println("Maximum Sum Increasing Subsequence: " + maxSumIncreasingSubsequence(nums));

        int[] nums2 = {3, 4, 5, 10};
        System.out.println("Maximum Sum Increasing Subsequence: " + maxSumIncreasingSubsequence(nums2));

        int[] nums3 = {10, 5, 4, 3};
        System.out.println("Maximum Sum Increasing Subsequence: " + maxSumIncreasingSubsequence(nums3));
    }
}
