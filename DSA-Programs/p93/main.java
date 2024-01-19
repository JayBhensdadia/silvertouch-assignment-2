package p93;

import java.util.Arrays;

class WaveletMatrix {
    private int[][] matrix;
    private int[] starts;
    private int[] ends;

    public WaveletMatrix(int[] array, int maxValue) {
        int n = array.length;
        matrix = new int[log2(maxValue) + 1][n];
        starts = new int[log2(maxValue) + 1];
        ends = new int[log2(maxValue) + 1];

        for (int i = 0; i <= log2(maxValue); i++) {
            int low = 0, high = n - 1;
            for (int j = 0; j < n; j++) {
                if ((array[j] & (1 << i)) != 0) {
                    matrix[i][high--] = array[j];
                } else {
                    matrix[i][low++] = array[j];
                }
            }
            starts[i] = low;
            ends[i] = high + 1;
        }
    }

    public int kthSmallest(int k, int left, int right, int bit) {
        if (left == right) {
            return matrix[bit][left];
        }

        int mid = (left + right) / 2;
        int count = mid - left + 1 - (starts[bit] > left ? starts[bit] - left : 0);

        if (count >= k) {
            return kthSmallest(k, left, mid, bit);
        } else {
            return kthSmallest(k - count, mid + 1, right, bit);
        }
    }

    public int rank(int value, int left, int right, int bit) {
        if (left == right) {
            return (value & (1 << bit)) != 0 ? 1 : 0;
        }

        int mid = (left + right) / 2;
        int count = mid - left + 1 - (starts[bit] > left ? starts[bit] - left : 0);

        if ((value & (1 << bit)) != 0) {
            return count + rank(value, mid + 1, right, bit);
        } else {
            return rank(value, left, mid, bit);
        }
    }

    private static int log2(int x) {
        return (int) (Math.log(x) / Math.log(2));
    }

    public static void main(String[] args) {
        int[] array = {3, 5, 2, 8, 7, 1, 4, 6};
        int maxValue = Arrays.stream(array).max().orElse(0);

        WaveletMatrix waveletMatrix = new WaveletMatrix(array, maxValue);

        // Example range queries
        int kthSmallest = waveletMatrix.kthSmallest(5, 0, array.length - 1, 2);
        int rank = waveletMatrix.rank(4, 0, array.length - 1, 2);

        System.out.println("5th smallest element in the range is: " + kthSmallest);
        System.out.println("Rank of 4 in the range is: " + rank);
    }
}

