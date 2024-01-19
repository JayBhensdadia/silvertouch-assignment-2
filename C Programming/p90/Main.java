package p90;

class MinTaps {
    public int minTaps(int n, int[] ranges) {
        int[] ends = new int[n + 1];
        for (int i = 0; i <= n; i++) {
            ends[i] = i + ranges[i];
        }
        int numTaps = 0, furthestEnd = 0, nextFurthestEnd = 0;
        for (int i = 0; i <= n; i++) {
            if (i > furthestEnd) {
                if (nextFurthestEnd <= i) {
                    return -1; // cannot water the entire garden
                }
                numTaps++;
                furthestEnd = nextFurthestEnd;
            }
            nextFurthestEnd = Math.max(nextFurthestEnd, ends[i]);
        }
        return numTaps;
    }

    public static void main(String[] args) {
        MinTaps solver = new MinTaps();
        int n = 5;
        int[] ranges = {3, 4, 1, 1, 0, 0};
        System.out.println("Minimum number of taps needed: " + solver.minTaps(n, ranges));
    }
}