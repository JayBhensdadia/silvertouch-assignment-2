class FenwickTree {
    private int[] fenwickTree;

    public FenwickTree(int size) {
        fenwickTree = new int[size + 1];
    }

    public void update(int index, int delta) {
        index++; 
        while (index < fenwickTree.length) {
            fenwickTree[index] += delta;
            index += index & -index; 
        }
    }

    public int query(int index) {
        index++; 
        int sum = 0;
        while (index > 0) {
            sum += fenwickTree[index];
            index -= index & -index; 
        }
        return sum;
    }

    public int rangeQuery(int start, int end) {
        return query(end) - query(start - 1);
    }

    
}

public class Main {

    public static void main(String[] args) {
        int[] array = {1, 2, 3, 4, 5, 6, 7, 8, 9};
        FenwickTree fenwickTree = new FenwickTree(array.length);

        
        for (int i = 0; i < array.length; i++) {
            fenwickTree.update(i, array[i]);
        }

        
        System.out.println("Prefix sum from 0 to 4: " + fenwickTree.rangeQuery(0, 4)); 

        
        fenwickTree.update(2, 10);

        
        System.out.println("Updated prefix sum from 0 to 4: " + fenwickTree.rangeQuery(0, 4)); 
    }
}