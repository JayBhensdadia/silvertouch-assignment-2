public class Main {

    public static int maxSubarraySumCircular(int[] nums) {
        int totalSum = 0;
        int maxSum = Integer.MIN_VALUE;
        int minSum = Integer.MAX_VALUE;
        int currentMax = 0;
        int currentMin = 0;

        for (int num : nums) {
            totalSum += num;

            currentMax = Math.max(currentMax + num, num);
            maxSum = Math.max(maxSum, currentMax);

            currentMin = Math.min(currentMin + num, num);
            minSum = Math.min(minSum, currentMin);
        }

        if (maxSum > 0) {
            return Math.max(maxSum, totalSum - minSum);
        } else {
            return maxSum;
        }
    }

    public static void main(String[] args) {
        int[] nums1 = {1, -2, 3, -2};
        System.out.println("Maximum Subarray Sum Circular: " + maxSubarraySumCircular(nums1));

        int[] nums2 = {5, -3, 5};
        System.out.println("Maximum Subarray Sum Circular: " + maxSubarraySumCircular(nums2));

        int[] nums3 = {3, -1, 2, -1};
        System.out.println("Maximum Subarray Sum Circular: " + maxSubarraySumCircular(nums3));

        int[] nums4 = {3, -2, 2, -3};
        System.out.println("Maximum Subarray Sum Circular: " + maxSubarraySumCircular(nums4));
    }
}
