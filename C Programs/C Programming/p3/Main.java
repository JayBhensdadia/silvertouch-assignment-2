import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter n: ");
        int n = scanner.nextInt();
        int[][] matrix = new int[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                System.out.print("Element at ("+i+","+j+") : ");
                matrix[i][j] = scanner.nextInt();
                System.out.println();
            }
        }

        int trace = 0;
        int normal = 0;

        for (int i = 0; i < n; i++) {
            trace += matrix[i][i];
            for (int j = 0; j < n; j++) {
                normal += matrix[i][j] * matrix[i][j];
            }
        }

        normal = (int) Math.sqrt(normal);

        System.out.println("Trace: " + trace);
        System.out.println("Normal: " + normal);

        scanner.close();
    }
}
