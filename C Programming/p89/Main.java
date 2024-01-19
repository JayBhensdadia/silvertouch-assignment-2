
import java.util.Arrays;
import java.util.Scanner;

class QuantumWalk {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the number of steps: ");
        int steps = scanner.nextInt();
        scanner.close();

        
        double[] psi = new double[2 * steps + 1];
        Arrays.fill(psi, 1.0 / Math.sqrt(2 * steps + 1));

        
        for (int i = 0; i < steps; i++) {
            double[] temp = new double[psi.length];
            temp[i + 1] = psi[i] / Math.sqrt(2);
            temp[i] = psi[i] / Math.sqrt(2);
            temp[i + 1 + steps] = psi[i + steps] / Math.sqrt(2);
            temp[i + steps] = -psi[i + steps] / Math.sqrt(2);
            psi = temp;
        }

        
        double norm = 0;
        for (double d : psi) {
            norm += d * d;
        }
        norm = Math.sqrt(norm);
        for (int i = 0; i < psi.length; i++) {
            psi[i] /= norm;
        }

        
        for (int i = 0; i < psi.length; i++) {
            System.out.printf("Probability at position %d: %.4f%n", i - steps, psi[i] * psi[i]);
        }
    }
}