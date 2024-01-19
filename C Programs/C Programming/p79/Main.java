import java.util.PriorityQueue;

public class Main {

    public static int connectSticks(int[] sticks) {
        PriorityQueue<Integer> minHeap = new PriorityQueue<>();

        for (int stick : sticks) {
            minHeap.offer(stick);
        }

        int totalCost = 0;

        while (minHeap.size() > 1) {
            int firstStick = minHeap.poll();
            int secondStick = minHeap.poll();

            int currentCost = firstStick + secondStick;
            totalCost += currentCost;

            minHeap.offer(currentCost); 
        }

        return totalCost;
    }

    public static void main(String[] args) {
        int[] sticks = {2, 4, 3};
        int result = connectSticks(sticks);

        System.out.println("Minimum Cost to Connect Sticks: " + result);
    }
}
