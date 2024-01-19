import java.util.LinkedList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter n: ");
        int n = scanner.nextInt(); 
        System.out.print("Enter k: ");
        int k = scanner.nextInt(); 
        solveJosephusProblem(n, k);
    }

    public static void solveJosephusProblem(int n, int k) {
        LinkedList<Integer> circle = new LinkedList<>();
        for (int i = 1; i <= n; i++) {
            circle.add(i);
        }

        int index = 0;
        while (circle.size() > 1) {
            index = (index + k - 1) % circle.size();
            System.out.println("Person " + circle.remove(index) + " is eliminated.");
        }

        System.out.println("The survivor is Person " + circle.get(0));
    }
}
