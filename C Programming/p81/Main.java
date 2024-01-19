package C.p81;
import java.util.HashMap;
public class Main {
}


class CountSubarraysWithGivenSum {

    public static int countSubarraysWithGivenSum(int[] nums, int targetSum) {
        int count = 0;
        int currentSum = 0;

        HashMap<Integer, Integer> prefixSumCount = new HashMap<>();
        prefixSumCount.put(0, 1);

        for (int num : nums) {
            currentSum += num;
            if (prefixSumCount.containsKey(currentSum - targetSum)) {
                count += prefixSumCount.get(currentSum - targetSum);
            }
            prefixSumCount.put(currentSum, prefixSumCount.getOrDefault(currentSum, 0) + 1);
        }

        return count;
    }

    public static void main(String[] args) {
        int[] nums = {10, 2, -2, -20, 10};
        int targetSum = -10;
        int result = countSubarraysWithGivenSum(nums, targetSum);
        System.out.println("Count of Subarrays with Sum " + targetSum + ": " + result);
    }
}

