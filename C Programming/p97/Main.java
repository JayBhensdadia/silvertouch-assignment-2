package p97;

import java.util.PriorityQueue;

class KthSmallestInMatrix {

    public static int kthSmallest(int[][] matrix, int k) {
        int rows = matrix.length;
        int cols = matrix[0].length;

        
        PriorityQueue<Element> minHeap = new PriorityQueue<>((a, b) -> a.val - b.val);

        
        for (int i = 0; i < rows; i++) {
            minHeap.offer(new Element(i, 0, matrix[i][0]));
        }

        
        for (int i = 0; i < k - 1; i++) {
            Element element = minHeap.poll();

            
            if (element.col < cols - 1) {
                minHeap.offer(new Element(element.row, element.col + 1, matrix[element.row][element.col + 1]));
            }
        }

        
        return minHeap.peek().val;
    }

    static class Element {
        int row;
        int col;
        int val;

        public Element(int row, int col, int val) {
            this.row = row;
            this.col = col;
            this.val = val;
        }
    }

    public static void main(String[] args) {
        
        int[][] matrix = {
            {1,  5,  9},
            {10, 11, 13},
            {12, 13, 15}
        };

        int k = 8;

        int result = kthSmallest(matrix, k);
        System.out.println("Kth Smallest Element: " + result);
    }
}
