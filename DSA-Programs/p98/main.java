package p98;

import java.util.concurrent.*;

class ParallelBinarySearch {

    static class BinarySearchTask implements Callable<Integer> {
        private final int[] array;
        private final int target;
        private final int start;
        private final int end;

        public BinarySearchTask(int[] array, int target, int start, int end) {
            this.array = array;
            this.target = target;
            this.start = start;
            this.end = end;
        }

        @Override
        public Integer call() {
            return binarySearch(array, target, start, end);
        }
    }

    public static int parallelBinarySearch(int[] array, int target, int numProcessors) throws InterruptedException, ExecutionException {
        int arrayLength = array.length;
        ExecutorService executor = Executors.newFixedThreadPool(numProcessors);
        int segmentSize = arrayLength / numProcessors;

        // Create tasks for each processor
        Future<Integer>[] futures = new Future[numProcessors];
        for (int i = 0; i < numProcessors; i++) {
            int start = i * segmentSize;
            int end = (i == numProcessors - 1) ? arrayLength : (i + 1) * segmentSize;
            futures[i] = executor.submit(new BinarySearchTask(array, target, start, end));
        }

        // Wait for tasks to complete
        for (int i = 0; i < numProcessors; i++) {
            int result = futures[i].get();
            if (result != -1) {
                executor.shutdown();
                return result; // Found in one of the segments
            }
        }

        executor.shutdown();
        return -1; // Not found
    }

    public static int binarySearch(int[] array, int target, int start, int end) {
        while (start <= end) {
            int mid = start + (end - start) / 2;
            if (array[mid] == target) {
                return mid; // Element found
            } else if (array[mid] < target) {
                start = mid + 1;
            } else {
                end = mid - 1;
            }
        }
        return -1; // Element not found in the segment
    }

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        int[] sortedArray = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        int target = 7;
        int numProcessors = 2;

        int result = parallelBinarySearch(sortedArray, target, numProcessors);

        if (result != -1) {
            System.out.println("Element found at index: " + result);
        } else {
            System.out.println("Element not found in the array.");
        }
    }
}

