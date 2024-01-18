 class SparseTable {

    private int[][] sparseTable;
    private int[] logTable;
    private int[] inputArray;

    public SparseTable(int[] array) {
        int n = array.length;
        int logN = (int) (Math.log(n) / Math.log(2)) + 1;

        sparseTable = new int[n][logN];
        logTable = new int[n + 1];
        inputArray = array;

        buildSparseTable();
    }

    private void buildSparseTable() {
        int n = inputArray.length;

        
        for (int i = 0; i < n; i++) {
            sparseTable[i][0] = inputArray[i];
        }

        
        for (int i = 2; i <= n; i++) {
            logTable[i] = logTable[i >> 1] + 1;
        }

        
        for (int j = 1; (1 << j) <= n; j++) {
            for (int i = 0; i + (1 << j) <= n; i++) {
                int leftInterval = sparseTable[i][j - 1];
                int rightInterval = sparseTable[i + (1 << (j - 1))][j - 1];
                sparseTable[i][j] = Math.min(leftInterval, rightInterval);
            }
        }
    }

    public int query(int left, int right) {
        int k = logTable[right - left + 1];
        int leftInterval = sparseTable[left][k];
        int rightInterval = sparseTable[right - (1 << k) + 1][k];
        return Math.min(leftInterval, rightInterval);
    }


}

public class Main {

    public static void main(String[] args) {
        int[] array = {2, 5, 1, 8, 7, 3};
        SparseTable sparseTable = new SparseTable(array);

        
        System.out.println("Minimum in range [1, 4]: " + sparseTable.query(1, 4)); 
        System.out.println("Minimum in range [2, 5]: " + sparseTable.query(2, 5)); 
        System.out.println("Minimum in range [0, 3]: " + sparseTable.query(0, 3)); 
    }
    
}