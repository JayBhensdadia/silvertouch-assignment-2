public class Main {

    public static boolean subsetSum(int[] nums, int target) {
        return subsetSumHelper(nums, target, 0, 0);
    }

    private static boolean subsetSumHelper(int[] nums, int target, int currentSum, int index) {
        if (currentSum == target) {
            return true;
        }

        if (index == nums.length) {
            return false;
        }

        if (currentSum + nums[index] <= target) {
            if (subsetSumHelper(nums, target, currentSum + nums[index], index + 1)) {
                return true;
            }
        }

        return subsetSumHelper(nums, target, currentSum, index + 1);
    }

    public static void main(String[] args) {
        int[] nums = {3, 34, 4, 12, 5, 2};
        int target = 9;

        boolean result = subsetSum(nums, target);

        if (result) {
            System.out.println("Subset with the given sum exists.");
        } else {
            System.out.println("No subset with the given sum exists.");
        }
    }
}
