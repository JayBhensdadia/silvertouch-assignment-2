package C.p85;
class CountRangeSum {

    public int countRangeSum(int[] nums, int lower, int upper) {
        if (nums == null || nums.length == 0) {
            return 0;
        }

        int n = nums.length;
        long[] prefixSum = new long[n + 1];
        for (int i = 0; i < n; i++) {
            prefixSum[i + 1] = prefixSum[i] + nums[i];
        }

        return countRangeSumRecursive(prefixSum, lower, upper, 0, n);
    }

    private int countRangeSumRecursive(long[] prefixSum, int lower, int upper, int left, int right) {
        if (left == right) {
            return 0;
        }

        int mid = (left + right) / 2;
        int count = countRangeSumRecursive(prefixSum, lower, upper, left, mid)
                + countRangeSumRecursive(prefixSum, lower, upper, mid + 1, right);

        int i = left;
        int j = mid + 1;
        int k = mid + 1;

        long[] merged = new long[right - left + 1];

        for (int idx = 0; idx <= mid - left; idx++) {
            while (i <= mid && prefixSum[j] - prefixSum[i] >= lower) {
                i++;
            }
            while (j <= right && prefixSum[j] - prefixSum[i] <= upper) {
                j++;
            }
            count += j - i;

            while (k <= right && prefixSum[k] < prefixSum[idx + left]) {
                merged[idx] = prefixSum[k];
                k++;
                idx++;
            }

            merged[idx] = prefixSum[idx + left];
        }

        System.arraycopy(merged, 0, prefixSum, left, merged.length);

        return count;
    }

    public static void main(String[] args) {
        CountRangeSum solution = new CountRangeSum();
        int[] nums = {-2, 5, -1};
        int lower = -2;
        int upper = 2;
        int result = solution.countRangeSum(nums, lower, upper);

        System.out.println("Count of Range Sum: " + result);
    }
}

public class Main {
}
