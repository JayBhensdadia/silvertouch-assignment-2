import java.util.Arrays;

class WaveletTree {
    private int[] arr;
    private int[][] bitmaps;
    private int[] mid;

    public WaveletTree(int[] array, int alphabetSize) {
        this.arr = Arrays.copyOf(array, array.length);
        this.bitmaps = new int[alphabetSize][];
        this.mid = new int[arr.length];

        constructWaveletTree(0, arr.length - 1, 1, 0, alphabetSize - 1);
    }

    private void constructWaveletTree(int left, int right, int node, int rangeStart, int rangeEnd) {
        if (rangeStart == rangeEnd) {
            return;
        }

        int midValue = (rangeStart + rangeEnd) / 2;
        bitmaps[node] = new int[right - left + 1];
        mid[node] = midValue;

        int countLeft = 0;
        for (int i = left; i <= right; i++) {
            if (arr[i] <= midValue) {
                countLeft++;
            }
        }

        int leftPtr = left;
        int rightPtr = mid[node] + 1;

        for (int i = left; i <= right; i++) {
            if (arr[i] <= midValue) {
                bitmaps[node][i - left] = ++countLeft;
            } else {
                bitmaps[node][i - left] = countLeft;
            }
        }

        int midIndex = left + countLeft - 1;

        constructWaveletTree(left, midIndex, 2 * node, rangeStart, midValue);
        constructWaveletTree(midIndex + 1, right, 2 * node + 1, midValue + 1, rangeEnd);
    }

    public int rangeQuery(int left, int right, int k) {
        return rangeQuery(left, right, k, 0, arr.length - 1, 1);
    }

    private int rangeQuery(int left, int right, int k, int queryStart, int queryEnd, int node) {
        if (queryStart == queryEnd) {
            return queryStart;
        }

        int midValue = mid[node];
        int countLeftInQuery = (left > 0 ? bitmaps[node][left - 1 - queryStart] : 0) + bitmaps[node][right - queryStart];

        if (k <= countLeftInQuery) {
            return rangeQuery((left > 0 ? bitmaps[node][left - 1 - queryStart] : 0) + queryStart,
                    bitmaps[node][right - queryStart] + queryStart - 1, k, queryStart, midValue, 2 * node);
        } else {
            return rangeQuery(left - (left > 0 ? bitmaps[node][left - 1 - queryStart] : 0) + queryStart,
                    right - bitmaps[node][right - queryStart] + queryStart, k - countLeftInQuery, midValue + 1, queryEnd, 2 * node + 1);
        }
    }
}

public class Main {
    public static void main(String[] args) {
        int[] array = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        int alphabetSize = 10;

        WaveletTree waveletTree = new WaveletTree(array, alphabetSize);

        int left = 2, right = 8, k = 5;
        int result = waveletTree.rangeQuery(left, right, k);
        System.out.println("k-th smallest element in the range [" + left + ", " + right + "] is: " + result);
    }
}
