import java.util.ArrayList;
import java.util.List;
import java.util.Random;

class Node {
    int value;
    List<Node> next;

    public Node(int value, int level) {
        this.value = value;
        this.next = new ArrayList<>(level + 1);
    }
}

public class Main {
    private static final int MAX_LEVEL = 16;
    private int currentLevel;
    private Node head;
    private Random random;

    public Main() {
        this.currentLevel = 0;
        this.head = new Node(Integer.MIN_VALUE, MAX_LEVEL);
        this.random = new Random();
    }

    private int randomLevel() {
        int level = 0;
        while (level < MAX_LEVEL && random.nextDouble() < 0.5) {
            level++;
        }
        return level;
    }

    public void insert(int value) {
        int level = randomLevel();

        if (level > currentLevel) {
            currentLevel = level;
            head.next.add(null);
        }

        Node newNode = new Node(value, level);
        Node current = head;

        for (int i = currentLevel; i >= 0; i--) {
            while (current.next.get(i) != null && current.next.get(i).value < value) {
                current = current.next.get(i);
            }
            if (i <= level) {
                newNode.next.add(i, current.next.get(i));
                current.next.set(i, newNode);
            }
        }
    }

    public boolean search(int value) {
        Node current = head;

        for (int i = currentLevel; i >= 0; i--) {
            while (current.next.get(i) != null && current.next.get(i).value < value) {
                current = current.next.get(i);
            }
        }

        return current.next.get(0) != null && current.next.get(0).value == value;
    }

    public void delete(int value) {
        Node current = head;
        Node[] update = new Node[MAX_LEVEL + 1];

        for (int i = currentLevel; i >= 0; i--) {
            while (current.next.get(i) != null && current.next.get(i).value < value) {
                current = current.next.get(i);
            }
            update[i] = current;
        }

        Node toDelete = current.next.get(0);

        if (toDelete != null && toDelete.value == value) {
            for (int i = 0; i <= currentLevel; i++) {
                if (update[i].next.get(i) != toDelete) {
                    break;
                }
                update[i].next.set(i, toDelete.next.get(i));
            }

            while (currentLevel > 0 && head.next.get(currentLevel) == null) {
                currentLevel--;
            }
        }
    }

    public void printSkipGraph() {
        for (int i = currentLevel; i >= 0; i--) {
            Node current = head;
            System.out.print("Level " + i + ": ");
            while (current.next.get(i) != null) {
                System.out.print(current.next.get(i).value + " ");
                current = current.next.get(i);
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        Main skipGraph = new Main();
        skipGraph.insert(3);
        skipGraph.insert(6);
        skipGraph.insert(7);
        skipGraph.insert(9);
        skipGraph.insert(12);
        
        skipGraph.printSkipGraph();

        System.out.println("Search for 19: " + skipGraph.search(19));
        System.out.println("Search for 8: " + skipGraph.search(8));

        skipGraph.delete(19);
        skipGraph.delete(9);
        skipGraph.printSkipGraph();
    }
}
