package p95;

import java.util.Arrays;

class SqrtDecomposition {
    private int[] array;
    private int[] blockSum;
    private int[] lazyUpdates;
    private int blockSize;

    public SqrtDecomposition(int[] inputArray) {
        int n = inputArray.length;
        blockSize = (int) Math.ceil(Math.sqrt(n));
        int numBlocks = (int) Math.ceil((double) n / blockSize);

        array = new int[n];
        blockSum = new int[numBlocks];
        lazyUpdates = new int[numBlocks];

        for (int i = 0; i < n; i++) {
            array[i] = inputArray[i];
            blockSum[i / blockSize] += inputArray[i];
        }
    }

    public void rangeUpdate(int left, int right, int value) {
        int startBlock = left / blockSize;
        int endBlock = right / blockSize;

        if (startBlock == endBlock) {
            for (int i = left; i <= right; i++) {
                array[i] += value;
                blockSum[startBlock] += value;
            }
        } else {
            for (int i = left; i < (startBlock + 1) * blockSize; i++) {
                array[i] += value;
                blockSum[startBlock] += value;
            }

            for (int block = startBlock + 1; block < endBlock; block++) {
                lazyUpdates[block] += value;
            }

            for (int i = endBlock * blockSize; i <= right; i++) {
                array[i] += value;
                blockSum[endBlock] += value;
            }
        }
    }

    public int rangeQuery(int left, int right) {
        int startBlock = left / blockSize;
        int endBlock = right / blockSize;

        int sum = 0;
        if (startBlock == endBlock) {
            for (int i = left; i <= right; i++) {
                sum += array[i] + lazyUpdates[startBlock];
            }
        } else {
            for (int i = left; i < (startBlock + 1) * blockSize; i++) {
                sum += array[i] + lazyUpdates[startBlock];
            }

            for (int block = startBlock + 1; block < endBlock; block++) {
                sum += blockSum[block] + lazyUpdates[block] * blockSize;
            }

            for (int i = endBlock * blockSize; i <= right; i++) {
                sum += array[i] + lazyUpdates[endBlock];
            }
        }

        return sum;
    }

    public static void main(String[] args) {
        int[] inputArray = {1, 2, 3, 4, 5, 6, 7, 8};
        SqrtDecomposition sqrtDecomposition = new SqrtDecomposition(inputArray);

        System.out.println("Original Array: " + Arrays.toString(inputArray));

        sqrtDecomposition.rangeUpdate(1, 4, 2);
        System.out.println("Array after range update(1, 4, 2): " + Arrays.toString(sqrtDecomposition.array));

        int queryResult = sqrtDecomposition.rangeQuery(2, 6);
        System.out.println("Range query(2, 6): " + queryResult);
    }
}

