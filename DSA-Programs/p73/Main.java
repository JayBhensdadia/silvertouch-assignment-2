import java.util.Arrays;

class FractionalCascading {

    static int[][] constructDataStructures(int[][] sets) {
        int n = sets.length;
        int[][] dataStructures = new int[n][];

        
        for (int i = 0; i < n; i++) {
            dataStructures[i] = Arrays.copyOf(sets[i], sets[i].length);
        }

        
        for (int i = 0; i < n; i++) {
            Arrays.sort(dataStructures[i]);
        }

        
        for (int i = n - 2; i >= 0; i--) {
            for (int j = 0; j < dataStructures[i].length; j++) {
                int key = dataStructures[i][j];
                int position = Arrays.binarySearch(dataStructures[i + 1], key);
                if (position < 0) {
                    position = -(position + 1);
                }
                dataStructures[i][j] = position;
            }
        }

        return dataStructures;
    }

    static int fractionalCascadingSearch(int[][] dataStructures, int target) {
        int result = 0;
        for (int i = 0; i < dataStructures.length; i++) {
            int position = Arrays.binarySearch(dataStructures[i], target);
            if (position < 0) {
                position = -(position + 1);
            }
            result += position;
            if (i < dataStructures.length - 1) {
                result = 2 * result - i;
            }
        }
        return result;
    }
}

public class Main {

    public static void main(String[] args) {
        
        int[][] sets = {
            {1, 3, 5, 7},
            {2, 4, 6, 8},
            {1, 2, 3, 4, 5, 6, 7, 8}
        };

        int[][] dataStructures = FractionalCascading.constructDataStructures(sets);

        
        int target = 5;
        int result = FractionalCascading.fractionalCascadingSearch(dataStructures, target);

        System.out.println("Element " + target + " is at position " + result);
    }
}
