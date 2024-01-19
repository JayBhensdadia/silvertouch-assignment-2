import java.util.Arrays;
import java.util.concurrent.RecursiveTask;
import java.util.concurrent.ForkJoinPool;

public class Main {

    public static void parallelMergeSort(int[] array) {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        forkJoinPool.invoke(new MergeSortTask(array, 0, array.length - 1));
        forkJoinPool.shutdown();
    }

    private static class MergeSortTask extends RecursiveTask<Void> {
        private final int[] array;
        private final int left;
        private final int right;

        public MergeSortTask(int[] array, int left, int right) {
            this.array = array;
            this.left = left;
            this.right = right;
        }

        @Override
        protected Void compute() {
            if (left < right) {
                int mid = (left + right) / 2;

                MergeSortTask leftTask = new MergeSortTask(array, left, mid);
                MergeSortTask rightTask = new MergeSortTask(array, mid + 1, right);

                invokeAll(leftTask, rightTask);

                merge(array, left, mid, right);
            }
            return null;
        }

        private void merge(int[] array, int left, int mid, int right) {
            int[] temp = Arrays.copyOfRange(array, left, right + 1);

            int i = 0, j = mid - left + 1, k = left;

            while (i <= mid - left && j <= right - left) {
                if (temp[i] <= temp[j]) {
                    array[k++] = temp[i++];
                } else {
                    array[k++] = temp[j++];
                }
            }

            while (i <= mid - left) {
                array[k++] = temp[i++];
            }
            while (j <= right - left) {
                array[k++] = temp[j++];
            }
        }
    }

    public static void main(String[] args) {
        int[] data = {5, 3, 9, 1, 7, 4, 8, 2, 6};
        System.out.println("Original array: " + Arrays.toString(data));

        parallelMergeSort(data);

        System.out.println("Sorted array: " + Arrays.toString(data));
    }
}
