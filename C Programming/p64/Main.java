public class Main {

    public static int minJumps(int[] arr) {
        int n = arr.length;

        if (n <= 1) {
            return 0;
        }

        if (arr[0] == 0) {
            return -1;
        }

        int maxReach = arr[0];
        int steps = arr[0];
        int jumps = 1;

        for (int i = 1; i < n; i++) {
            if (i == n - 1) {
                return jumps;
            }

            maxReach = Math.max(maxReach, i + arr[i]);
            steps--;

            if (steps == 0) {
                jumps++;

                if (i >= maxReach) {
                    return -1;
                }

                steps = maxReach - i;
            }
        }

        return -1; 
    }

    public static void main(String[] args) {
        int[] arr1 = {2, 3, 1, 1, 2, 4, 2, 0, 1, 1};
        System.out.println("Minimum Jumps for arr1: " + minJumps(arr1));

        int[] arr2 = {1, 3, 5, 8, 9, 2, 6, 7, 6, 8, 9};
        System.out.println("Minimum Jumps for arr2: " + minJumps(arr2));

        int[] arr3 = {2, 1, 1, 1, 4};
        System.out.println("Minimum Jumps for arr3: " + minJumps(arr3));
    }
}
