import java.util.ArrayList;
import java.util.List;

class Node {
    Node left, right, up, down;
    int rowId;

    public Node(int rowId) {
        this.rowId = rowId;
    }
}

class ColumnNode extends Node {
    int columnId;
    int nodeCount;

    public ColumnNode(int columnId) {
        super(-1); // -1 is used to indicate that it's a column header node
        this.columnId = columnId;
        this.nodeCount = 0;
    }
}

class DancingLinks {
    ColumnNode header;
    List<Node> solutions;

    public DancingLinks(int[][] matrix) {
        initialize(matrix);
    }

    private void initialize(int[][] matrix) {
        int rowCount = matrix.length;
        int colCount = matrix[0].length;

        // Create header nodes for each column
        header = new ColumnNode(-1);
        ColumnNode[] columnHeaders = new ColumnNode[colCount];
        for (int j = 0; j < colCount; j++) {
            columnHeaders[j] = new ColumnNode(j);
            columnHeaders[j].left = header.left;
            columnHeaders[j].right = header;
            header.left = columnHeaders[j];
            columnHeaders[j].right.left = columnHeaders[j];
        }

        // Create nodes for each '1' in the matrix
        Node[][] nodes = new Node[rowCount][colCount];
        for (int i = 0; i < rowCount; i++) {
            for (int j = 0; j < colCount; j++) {
                if (matrix[i][j] == 1) {
                    Node newNode = new Node(i);
                    newNode.column = columnHeaders[j];
                    newNode.up = columnHeaders[j].up;
                    newNode.down = columnHeaders[j];
                    columnHeaders[j].up.down = newNode;
                    columnHeaders[j].up = newNode;
                    columnHeaders[j].nodeCount++;
                    nodes[i][j] = newNode;
                }
            }
        }

        solutions = new ArrayList<>();
    }

    private ColumnNode getMinColumn() {
        ColumnNode minColumn = (ColumnNode) header.right;
        for (ColumnNode col = (ColumnNode) header.right; col != header; col = (ColumnNode) col.right) {
            if (col.nodeCount < minColumn.nodeCount) {
                minColumn = col;
            }
        }
        return minColumn;
    }

    private void cover(ColumnNode column) {
        column.right.left = column.left;
        column.left.right = column.right;

        for (Node row = column.down; row != column; row = row.down) {
            for (Node rightNode = row.right; rightNode != row; rightNode = rightNode.right) {
                rightNode.up.down = rightNode.down;
                rightNode.down.up = rightNode.up;
                ((ColumnNode) rightNode.column).nodeCount--; // Corrected here
            }
        }
    }

    private void uncover(ColumnNode column) {
        for (Node row = column.up; row != column; row = row.up) {
            for (Node leftNode = row.left; leftNode != row; leftNode = leftNode.left) {
                leftNode.up.down = leftNode;
                leftNode.down.up = leftNode;
                ((ColumnNode) leftNode.column).nodeCount++; // Corrected here
            }
        }
        column.right.left = column;
        column.left.right = column;
    }
    public void search(int k) {
        if (header.right == header) {
            printSolutions();
            return;
        } else {
            ColumnNode column = getMinColumn();
            cover(column);

            for (Node row = column.down; row != column; row = row.down) {
                solutions.add(row);

                for (Node rightNode = row.right; rightNode != row; rightNode = rightNode.right) {
                    cover((ColumnNode) rightNode.column);
                }

                search(k + 1);
                solutions.remove(row);
                column = (ColumnNode) row.column;

                for (Node leftNode = row.left; leftNode != row; leftNode = leftNode.left) {
                    uncover((ColumnNode) leftNode.column);
                }
            }

            uncover(column);
        }
    }

    private void printSolutions() {
        for (Node node : solutions) {
            System.out.print(node.rowId + " ");
        }
        System.out.println();
    }
}

public class Main {
    public static void main(String[] args) {
        int[][] matrix = {
            {1, 0, 0, 1, 0, 0, 1},
            {1, 0, 0, 1, 1, 0, 0},
            {0, 1, 1, 0, 0, 1, 1},
            {0, 1, 1, 0, 1, 1, 0},
            {0, 0, 1, 1, 0, 1, 1},
            {0, 0, 1, 1, 1, 0, 0}
        };

        DancingLinks dlx = new DancingLinks(matrix);
        dlx.search(0);
    }
}
