public class Main {

    private int[] segmentTree;
    private int[] originalArray;

    public Main(int[] array) {
        int n = array.length;
        int height = (int) Math.ceil(Math.log(n) / Math.log(2));
        int maxSize = 2 * (int) Math.pow(2, height) - 1;

        segmentTree = new int[maxSize];
        originalArray = array.clone();

        buildSegmentTree(0, 0, n - 1);
    }

    private int buildSegmentTree(int index, int start, int end) {
        if (start == end) {
            segmentTree[index] = originalArray[start];
            return originalArray[start];
        }

        int mid = (start + end) / 2;
        segmentTree[index] = Math.min(
                buildSegmentTree(2 * index + 1, start, mid),
                buildSegmentTree(2 * index + 2, mid + 1, end)
        );

        return segmentTree[index];
    }

    public int getMinimumInRange(int queryStart, int queryEnd) {
        int n = originalArray.length;
        if (queryStart < 0 || queryEnd >= n || queryStart > queryEnd) {
            throw new IllegalArgumentException("Invalid query range");
        }

        return getMinimumInRangeUtil(0, 0, n - 1, queryStart, queryEnd);
    }

    private int getMinimumInRangeUtil(int index, int segmentStart, int segmentEnd,
                                      int queryStart, int queryEnd) {
        if (queryStart <= segmentStart && queryEnd >= segmentEnd) {
            return segmentTree[index];
        }

        if (queryStart > segmentEnd || queryEnd < segmentStart) {
            return Integer.MAX_VALUE;
        }

        int mid = (segmentStart + segmentEnd) / 2;
        return Math.min(
                getMinimumInRangeUtil(2 * index + 1, segmentStart, mid, queryStart, queryEnd),
                getMinimumInRangeUtil(2 * index + 2, mid + 1, segmentEnd, queryStart, queryEnd)
        );
    }

    public static void main(String[] args) {
        int[] array = {3, 1, 4, 6, 2, 8, 5};
        Main rmq = new Main(array);

        int queryStart = 1;
        int queryEnd = 4;
        int minimumInRange = rmq.getMinimumInRange(queryStart, queryEnd);

        System.out.println("Minimum value in range [" + queryStart + ", " + queryEnd + "]: " + minimumInRange);
    }
}
