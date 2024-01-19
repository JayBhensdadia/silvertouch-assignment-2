import java.util.Arrays;
import java.util.stream.IntStream;

interface QuantumGate {
    void apply(Qubit qubit);
}

class HadamardGate implements QuantumGate {
    @Override
    public void apply(Qubit qubit) {
        int numStates = qubit.getAmplitudes().length;
        double[][] hadamardMatrix = new double[numStates][numStates];

        double factor = 1 / Math.sqrt(numStates);
        for (int i = 0; i < numStates; i++) {
            for (int j = 0; j < numStates; j++) {
                hadamardMatrix[i][j] = factor * (Math.pow(-1, Integer.bitCount(i & j)));
            }
        }

        qubit.applyMatrix(hadamardMatrix);
    }
}


class XGate implements QuantumGate {
    @Override
    public void apply(Qubit qubit) {
        int numStates = qubit.getAmplitudes().length;
        double[][] xMatrix = new double[numStates][numStates];

        for (int i = 0; i < numStates; i++) {
            for (int j = 0; j < numStates; j++) {
                if (i == numStates - 1 - j) {
                    xMatrix[i][j] = 1; // Flip the amplitude for |0> and |1> states
                }
            }
        }

        qubit.applyMatrix(xMatrix);
    }
}


class Qubit {
    private double[] amplitudes;

    public Qubit(int numStates) {
        this.amplitudes = new double[numStates];

        Arrays.fill(amplitudes, 1 / Math.sqrt(numStates));
    }

    public void applyGate(QuantumGate gate) {
        gate.apply(this);
    }

    public void applyMatrix(double[][] matrix) {
        if (matrix.length != amplitudes.length || matrix[0].length != amplitudes.length) {
            throw new IllegalArgumentException("Matrix dimensions do not match qubit state size.");
        }
    
        double[] newAmplitudes = new double[amplitudes.length];
        for (int i = 0; i < amplitudes.length; i++) {
            final int row = i;  // Create effectively final variable for 'i'
            newAmplitudes[i] = IntStream.range(0, amplitudes.length)
                    .mapToDouble(j -> matrix[row][j] * amplitudes[j])
                    .sum();
        }
    
        amplitudes = newAmplitudes;
    }

    public double[] getAmplitudes() {
        return amplitudes;
    }

    @Override
    public String toString() {
        return Arrays.toString(amplitudes);
    }
}

public class Main {
    public static void main(String[] args) {
        int numQubits = 2;

        Qubit qubit = new Qubit((int) Math.pow(2, numQubits));

        QuantumGate hadamardGate = new HadamardGate();
        qubit.applyGate(hadamardGate);
        System.out.println("After Hadamard Gate: " + qubit);

        QuantumGate xGate = new XGate();
        qubit.applyGate(xGate);
        System.out.println("After X Gate: " + qubit);
    }
}
