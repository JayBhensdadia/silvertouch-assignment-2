package C.p79;

import java.util.PriorityQueue;

public class Main {

    public static int connectSticks(int[] sticks) {
        // Use a priority queue to always connect the smallest two sticks
        PriorityQueue<Integer> minHeap = new PriorityQueue<>();

        // Add all sticks to the min heap
        for (int stick : sticks) {
            minHeap.offer(stick);
        }

        int totalCost = 0;

        // Connect sticks until only one stick remains in the heap
        while (minHeap.size() > 1) {
            int firstStick = minHeap.poll();
            int secondStick = minHeap.poll();

            int currentCost = firstStick + secondStick;
            totalCost += currentCost;

            minHeap.offer(currentCost); // Add the connected stick back to the heap
        }

        return totalCost;
    }

    public static void main(String[] args) {
        int[] sticks = {2, 4, 3};
        int result = connectSticks(sticks);

        System.out.println("Minimum Cost to Connect Sticks: " + result);
    }
}
