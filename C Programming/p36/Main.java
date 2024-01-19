import java.util.Random;

public class Main {

    public static int select(int[] array, int k) {
        return select(array, 0, array.length - 1, k);
    }

    private static int select(int[] array, int left, int right, int k) {
        if (left == right) {
            return array[left];
        }

        int pivotIndex = partition(array, left, right);
        int length = pivotIndex - left + 1;

        if (k == length) {
            return array[pivotIndex];
        } else if (k < length) {
            return select(array, left, pivotIndex - 1, k);
        } else {
            return select(array, pivotIndex + 1, right, k - length);
        }
    }

    private static int partition(int[] array, int left, int right) {
        
        int pivotIndex = randomizedPivot(left, right);
        int pivot = array[pivotIndex];

        swap(array, pivotIndex, right);

        int i = left - 1;

        for (int j = left; j < right; j++) {
            if (array[j] <= pivot) {
                i++;
                swap(array, i, j);
            }
        }

        swap(array, i + 1, right);

        return i + 1;
    }

    private static int randomizedPivot(int left, int right) {
        Random random = new Random();
        return random.nextInt(right - left + 1) + left;
    }

    private static void swap(int[] array, int i, int j) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

    public static void main(String[] args) {
        int[] array = { 4, 3, 9, 1, 7, 6, 8, 2, 4 };
        int k = 5;

        int result = select(array, k);

        System.out.println("The " + k + "-th smallest element is: " + result);
    }
}
