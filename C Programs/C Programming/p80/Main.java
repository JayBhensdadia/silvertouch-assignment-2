import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

class Main extends RecursiveTask<int[]> {
    private final int[] array;
    private final int[] result;
    private final int low;
    private final int high;

    private static final int SEQUENTIAL_THRESHOLD = 1000;

    public Main(int[] array) {
        this(array, 0, array.length);
    }

    private Main(int[] array, int low, int high) {
        this.array = array;
        this.low = low;
        this.high = high;
        this.result = new int[array.length];
    }

    @Override
    protected int[] compute() {
        if (high - low <= SEQUENTIAL_THRESHOLD) {
            sequentialPrefixSum();
            return result;
        } else {
            int mid = (low + high) >>> 1;
            Main leftTask = new Main(array, low, mid);
            Main rightTask = new Main(array, mid, high);
            leftTask.fork();
            int[] rightResult = rightTask.compute();
            int[] leftResult = leftTask.join();
            parallelPrefixCombine(leftResult, rightResult);
            return result;
        }
    }

    private void sequentialPrefixSum() {
        result[low] = array[low];
        for (int i = low + 1; i < high; i++) {
            result[i] = result[i - 1] + array[i];
        }
    }

    private void parallelPrefixCombine(int[] leftResult, int[] rightResult) {
        for (int i = low; i < high; i++) {
            result[i] = leftResult[i - low] + rightResult[i - low];
        }
    }

    public static int[] parallelPrefixSum(int[] array) {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        return forkJoinPool.invoke(new Main(array));
    }

    public static void main(String[] args) {
        int[] inputArray = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        int[] resultArray = parallelPrefixSum(inputArray);

        System.out.println("Input Array: " + java.util.Arrays.toString(inputArray));
        System.out.println("Parallel Prefix Sum: " + java.util.Arrays.toString(resultArray));
    }
}
