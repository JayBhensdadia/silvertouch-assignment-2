import java.util.Arrays;

public class SegmentTreeLazyPropagation {
    int[] segTree;
    int[] lazy;
    int[] arr;

    public SegmentTreeLazyPropagation(int[] input) {
        int n = input.length;
        int height = (int) Math.ceil(Math.log(n) / Math.log(2));
        int maxSize = 2 * (int) Math.pow(2, height) - 1;

        segTree = new int[maxSize];
        lazy = new int[maxSize];
        arr = input;

        buildSegmentTree(0, n - 1, 0);
    }

    private void buildSegmentTree(int start, int end, int index) {
        if (start == end) {
            segTree[index] = arr[start];
            return;
        }

        int mid = (start + end) / 2;
        buildSegmentTree(start, mid, 2 * index + 1);
        buildSegmentTree(mid + 1, end, 2 * index + 2);
        segTree[index] = segTree[2 * index + 1] + segTree[2 * index + 2];
    }

    public void updateRange(int start, int end, int delta) {
        updateRangeUtil(0, arr.length - 1, start, end, 0, delta);
    }

    private void updateRangeUtil(int segStart, int segEnd, int start, int end, int index, int delta) {
        
        if (lazy[index] != 0) {
            segTree[index] += (segEnd - segStart + 1) * lazy[index];

            
            if (segStart != segEnd) {
                lazy[2 * index + 1] += lazy[index];
                lazy[2 * index + 2] += lazy[index];
            }

            
            lazy[index] = 0;
        }

        
        if (segEnd < start || segStart > end) {
            return;
        }

        
        if (start <= segStart && end >= segEnd) {
            segTree[index] += (segEnd - segStart + 1) * delta;

            
            if (segStart != segEnd) {
                lazy[2 * index + 1] += delta;
                lazy[2 * index + 2] += delta;
            }

            return;
        }

       
        int mid = (segStart + segEnd) / 2;
        updateRangeUtil(segStart, mid, start, end, 2 * index + 1, delta);
        updateRangeUtil(mid + 1, segEnd, start, end, 2 * index + 2, delta);

        
        segTree[index] = segTree[2 * index + 1] + segTree[2 * index + 2];
    }

    public int rangeQuery(int start, int end) {
        return rangeQueryUtil(0, arr.length - 1, start, end, 0);
    }

    private int rangeQueryUtil(int segStart, int segEnd, int start, int end, int index) {
       
        if (lazy[index] != 0) {
            segTree[index] += (segEnd - segStart + 1) * lazy[index];

            
            if (segStart != segEnd) {
                lazy[2 * index + 1] += lazy[index];
                lazy[2 * index + 2] += lazy[index];
            }

            
            lazy[index] = 0;
        }

        
        if (segEnd < start || segStart > end) {
            return 0;
        }

        
        if (start <= segStart && end >= segEnd) {
            return segTree[index];
        }

        
        int mid = (segStart + segEnd) / 2;
        int leftQuery = rangeQueryUtil(segStart, mid, start, end, 2 * index + 1);
        int rightQuery = rangeQueryUtil(mid + 1, segEnd, start, end, 2 * index + 2);

        return leftQuery + rightQuery;
    }

    public static void main(String[] args) {
        int[] arr = {1, 3, 5, 7, 9, 11};
        SegmentTreeLazyPropagation segmentTree = new SegmentTreeLazyPropagation(arr);

        System.out.println("Original Array: " + Arrays.toString(arr));
        System.out.println("Sum in range(1, 3): " + segmentTree.rangeQuery(1, 3));

        segmentTree.updateRange(1, 3, 2);

        System.out.println("Array after update: " + Arrays.toString(arr));
        System.out.println("Sum in range(1, 3): " + segmentTree.rangeQuery(1, 3));
    }
}
