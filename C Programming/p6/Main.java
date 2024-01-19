import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter String: ");
        String input = scanner.nextLine().toLowerCase().replaceAll("[^a-z]", "");

        boolean isPalindrome = true;
        int left = 0;
        int right = input.length() - 1;

        while (left < right) {
            if (input.charAt(left) != input.charAt(right)) {
                isPalindrome = false;
                break;
            }
            left++;
            right--;
        }

        System.out.println(isPalindrome);

        scanner.close();
    }
}
