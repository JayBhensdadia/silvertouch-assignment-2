import java.util.HashMap;
import java.util.Map;

public class Main {

    public static int findShortestSubarray(int[] nums) {
        Map<Integer, Integer> leftIndex = new HashMap<>();
        Map<Integer, Integer> frequency = new HashMap<>();
        int maxDegree = 0;
        int minLength = Integer.MAX_VALUE;

        for (int i = 0; i < nums.length; i++) {
            frequency.put(nums[i], frequency.getOrDefault(nums[i], 0) + 1);

            if (!leftIndex.containsKey(nums[i])) {
                leftIndex.put(nums[i], i);
            }

            int degree = frequency.get(nums[i]);

            if (degree == maxDegree) {
                minLength = Math.min(minLength, i - leftIndex.get(nums[i]) + 1);
            } else if (degree > maxDegree) {
                maxDegree = degree;
                minLength = i - leftIndex.get(nums[i]) + 1;
            }
        }

        return minLength;
    }

    public static void main(String[] args) {
        int[] nums = {1, 2, 2, 3, 1, 4, 2};

        int shortestSubarrayLength = findShortestSubarray(nums);
        System.out.println("Length of the Shortest Subarray with Maximum Degree: " + shortestSubarrayLength);
    }
}
