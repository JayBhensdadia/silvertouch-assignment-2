import java.util.Arrays;

import java.util.Random;

class ReservoirSampling {

    public static int[] reservoirSampling(int[] stream, int k) {
        int[] reservoir = new int[k];
        Random random = new Random();

        
        for (int i = 0; i < k; i++) {
            reservoir[i] = stream[i];
        }

        
        for (int i = k; i < stream.length; i++) {
            
            int j = random.nextInt(i + 1);

            
            if (j < k) {
                reservoir[j] = stream[i];
            }
        }

        return reservoir;
    }

   
}

public class Main {
    public static void main(String[] args) {
        int[] stream = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        int k = 3;

        int[] sampledElements = ReservoirSampling.reservoirSampling(stream, k);

        System.out.println("Sampled Elements: " + Arrays.toString(sampledElements));
    }
}
