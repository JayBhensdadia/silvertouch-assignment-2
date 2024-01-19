import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        int[] nums1 = {1, 3, 4, 5};
        int[] nums2 = {2, 4, 6, 8};

        double median = findMedianSortedArrays(nums1, nums2);
        System.out.println("Median of the two sorted arrays: " + median);
    }

    public static double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int[] mergedArray = mergeArrays(nums1, nums2);

        int length = mergedArray.length;
        if (length % 2 == 0) {
            int mid1 = mergedArray[length / 2 - 1];
            int mid2 = mergedArray[length / 2];
            return (double) (mid1 + mid2) / 2;
        } else {
            return mergedArray[length / 2];
        }
    }

    private static int[] mergeArrays(int[] nums1, int[] nums2) {
        int[] mergedArray = new int[nums1.length + nums2.length];
        int i = 0, j = 0, k = 0;

        while (i < nums1.length && j < nums2.length) {
            if (nums1[i] < nums2[j]) {
                mergedArray[k++] = nums1[i++];
            } else {
                mergedArray[k++] = nums2[j++];
            }
        }

        while (i < nums1.length) {
            mergedArray[k++] = nums1[i++];
        }

        while (j < nums2.length) {
            mergedArray[k++] = nums2[j++];
        }

        return mergedArray;
    }
}
