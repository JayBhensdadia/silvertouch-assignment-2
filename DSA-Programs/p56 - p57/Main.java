import java.util.Comparator;
import java.util.PriorityQueue;

class DoubleEndedPriorityQueue {
    private PriorityQueue<Integer> minHeap;
    private PriorityQueue<Integer> maxHeap;

    public DoubleEndedPriorityQueue() {
        minHeap = new PriorityQueue<>();
        maxHeap = new PriorityQueue<>(Comparator.reverseOrder());
    }

    public void insertMin(int element) {
        minHeap.offer(element);
        balanceHeaps();
    }

    public void insertMax(int element) {
        maxHeap.offer(element);
        balanceHeaps();
    }

    public int deleteMin() {
        if (isEmpty()) {
            throw new IllegalStateException("Priority queue is empty");
        }

        int element = minHeap.poll();
        balanceHeaps();
        return element;
    }

    public int deleteMax() {
        if (isEmpty()) {
            throw new IllegalStateException("Priority queue is empty");
        }

        int element = maxHeap.poll();
        balanceHeaps();
        return element;
    }

    public boolean isEmpty() {
        return minHeap.isEmpty() && maxHeap.isEmpty();
    }

    private void balanceHeaps() {
        while (minHeap.size() > maxHeap.size() + 1) {
            maxHeap.offer(minHeap.poll());
        }

        while (maxHeap.size() > minHeap.size()) {
            minHeap.offer(maxHeap.poll());
        }
    }
}

public class Main {
    public static void main(String[] args) {
        DoubleEndedPriorityQueue priorityQueue = new DoubleEndedPriorityQueue();

        priorityQueue.insertMin(3);
        priorityQueue.insertMax(7);
        priorityQueue.insertMin(1);

        System.out.println("Min Element: " + priorityQueue.deleteMin());
        System.out.println("Max Element: " + priorityQueue.deleteMax());
    }
}
