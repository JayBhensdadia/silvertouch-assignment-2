import java.util.ArrayList;
import java.util.List;
import java.util.Random;

class Node {
    int data;
    List<Node> forwards;

    public Node(int data, int level) {
        this.data = data;
        this.forwards = new ArrayList<>(level + 1);
        for (int i = 0; i <= level; i++) {
            forwards.add(null);
        }
    }
}

class SkipGraph {
    private static final int MAX_LEVEL = 16; 
    private int level; 
    private Node head; 
    private Random random;

    public SkipGraph() {
        this.level = 0;
        this.head = new Node(Integer.MIN_VALUE, MAX_LEVEL);
        this.random = new Random();
    }

    private int randomLevel() {
        int level = 0;
        while (random.nextDouble() < 0.5 && level < MAX_LEVEL) {
            level++;
        }
        return level;
    }

    public void insert(int data) {
        int newLevel = randomLevel();
        if (newLevel > level) {
            for (int i = level + 1; i <= newLevel; i++) {
                head.forwards.add(null);
            }
            level = newLevel;
        }

        Node newNode = new Node(data, newLevel);
        Node current = head;

        for (int i = level; i >= 0; i--) {
            while (current.forwards.get(i) != null && current.forwards.get(i).data < data) {
                current = current.forwards.get(i);
            }
            if (i <= newLevel) {
                newNode.forwards.set(i, current.forwards.get(i));
                current.forwards.set(i, newNode);
            }
        }
    }

    public boolean search(int data) {
        Node current = head;

        for (int i = level; i >= 0; i--) {
            while (current.forwards.get(i) != null && current.forwards.get(i).data < data) {
                current = current.forwards.get(i);
            }
        }

        Node next = current.forwards.get(0);
        return next != null && next.data == data;
    }

    public void printSkipGraph() {
        for (int i = level; i >= 0; i--) {
            Node current = head.forwards.get(i);
            System.out.print("Level " + i + ": ");
            while (current != null) {
                System.out.print(current.data + " ");
                current = current.forwards.get(i);
            }
            System.out.println();
        }
    }

   
}


public class Main {

    public static void main(String[] args) {
        SkipGraph skipGraph = new SkipGraph();
        skipGraph.insert(3);
        skipGraph.insert(6);
        skipGraph.insert(7);
        skipGraph.insert(9);
        skipGraph.insert(12);
        skipGraph.insert(19);
        skipGraph.insert(17);
        skipGraph.insert(26);
        skipGraph.insert(21);
        skipGraph.insert(25);

        skipGraph.printSkipGraph();

        int searchData = 17;
        boolean found = skipGraph.search(searchData);
        System.out.println("Search for " + searchData + ": " + (found ? "Found" : "Not found"));
    }
    
}