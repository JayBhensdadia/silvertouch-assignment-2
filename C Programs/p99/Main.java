import java.util.HashMap;
import java.util.Map;

class Main {
    private int numStates;
    private int numObservations;
    private double[][] transitionProbabilities;
    private double[][] observationProbabilities;
    private double[] startProbabilities;

    public Main(int numStates, int numObservations) {
        this.numStates = numStates;
        this.numObservations = numObservations;
        this.transitionProbabilities = new double[numStates][numStates];
        this.observationProbabilities = new double[numStates][numObservations];
        this.startProbabilities = new double[numStates];
    }

    

    public int[] viterbi(int[] observations) {
        int[][] v = new int[observations.length][numStates];
        double[][] delta = new double[observations.length][numStates];
        int[][] psi = new int[observations.length][numStates];

        
        for (int state = 0; state < numStates; state++) {
            delta[0][state] = startProbabilities[state] * observationProbabilities[state][observations[0]];
            psi[0][state] = -1;
        }

        
        for (int t = 1; t < observations.length; t++) {
            for (int state = 0; state < numStates; state++) {
                double maxDelta = -1;
                int prevState = -1;
                for (int prevStateCandidate = 0; prevStateCandidate < numStates; prevStateCandidate++) {
                    double tempDelta = delta[t - 1][prevStateCandidate] * transitionProbabilities[prevStateCandidate][state];
                    if (tempDelta > maxDelta) {
                        maxDelta = tempDelta;
                        prevState = prevStateCandidate;
                    }
                }
                delta[t][state] = maxDelta * observationProbabilities[state][observations[t]];
                psi[t][state] = prevState;
            }
        }

        
        int[] path = new int[observations.length];
        double maxDelta = -1;
        int finalState = -1;
        for (int state = 0; state < numStates; state++) {
            if (delta[observations.length - 1][state] > maxDelta) {
                maxDelta = delta[observations.length - 1][state];
                finalState = state;
            }
        }
        path[observations.length - 1] = finalState;
        for (int t = observations.length - 1; t > 0; t--) {
            path[t - 1] = psi[t][path[t]];
        }

        return path;
    }

    public static void main(String[] args) {
        
        int numStates = 3;
        int numObservations = 2;
        Main hmm = new Main(numStates, numObservations);
        int[] observations = {0, 1, 0, 1, 1};
        int[] path = hmm.viterbi(observations);
        for (int state : path) {
            System.out.print(state + " ");
        }
    }
}
