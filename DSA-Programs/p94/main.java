package p94;

class RangeMinimumQuery {
    private int[][] sparseTable;
    private int[] logTable;

    public RangeMinimumQuery(int[] array) {
        int n = array.length;
        int logN = (int) (Math.log(n) / Math.log(2)) + 1;

        sparseTable = new int[n][logN];
        logTable = new int[n + 1];

        for (int i = 2; i <= n; i++) {
            logTable[i] = logTable[i / 2] + 1;
        }

        for (int i = 0; i < n; i++) {
            sparseTable[i][0] = array[i];
        }

        for (int j = 1; (1 << j) <= n; j++) {
            for (int i = 0; i + (1 << j) - 1 < n; i++) {
                sparseTable[i][j] = Math.min(sparseTable[i][j - 1], sparseTable[i + (1 << (j - 1))][j - 1]);
            }
        }
    }

    public void update(int index, int value) {
        sparseTable[index][0] = value;

        for (int j = 1; (1 << j) <= sparseTable.length; j++) {
            int prevIndex = Math.max(0, index - (1 << (j - 1)));
            sparseTable[index][j] = Math.min(sparseTable[index][j - 1], sparseTable[prevIndex][j - 1]);
        }
    }

    public int query(int left, int right) {
        int k = logTable[right - left + 1];
        return Math.min(sparseTable[left][k], sparseTable[right - (1 << k) + 1][k]);
    }

    public static void main(String[] args) {
        int[] array = {2, 5, 1, 8, 4, 3, 6};
        RangeMinimumQuery rmq = new RangeMinimumQuery(array);

        System.out.println("Query(1, 4): " + rmq.query(1, 4)); // Output: 1

        rmq.update(2, 0);

        System.out.println("Query(1, 4) after update: " + rmq.query(1, 4)); // Output: 0
    }
}
