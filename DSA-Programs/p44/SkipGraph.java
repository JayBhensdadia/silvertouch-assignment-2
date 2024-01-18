import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

class Node {
    int value;
    List<Node> forward;

    public Node(int value, int level) {
        this.value = value;
        this.forward = new ArrayList<>(Collections.nCopies(level + 1, null));
    }
}

public class SkipGraph {
    private static final int MAX_LEVEL = 16; // Maximum level for the skip graph
    private Node head;
    private int level;
    private Random random;

    public SkipGraph() {
        this.head = new Node(Integer.MIN_VALUE, MAX_LEVEL);
        this.level = 0;
        this.random = new Random();
    }

    private int randomLevel() {
        int level = 0;
        while (random.nextDouble() < 0.5 && level < MAX_LEVEL) {
            level++;
        }
        return level;
    }

    public boolean search(int target) {
        Node current = head;

        for (int i = level; i >= 0; i--) {
            while (current.forward.get(i) != null && current.forward.get(i).value < target) {
                current = current.forward.get(i);
            }
        }

        return current.value == target;
    }

    public void insert(int value) {
        Node[] update = new Node[MAX_LEVEL + 1];
        Node current = head;

        for (int i = level; i >= 0; i--) {
            while (current.forward.get(i) != null && current.forward.get(i).value < value) {
                current = current.forward.get(i);
            }
            update[i] = current;
        }

        int newLevel = randomLevel();
        if (newLevel > level) {
            for (int i = level + 1; i <= newLevel; i++) {
                update[i] = head;
            }
            level = newLevel;
        }

        Node newNode = new Node(value, newLevel);
        for (int i = 0; i <= newLevel; i++) {
            newNode.forward.add(i, update[i].forward.get(i));
            update[i].forward.set(i, newNode);
        }
    }

    public void delete(int value) {
        Node[] update = new Node[MAX_LEVEL + 1];
        Node current = head;

        for (int i = level; i >= 0; i--) {
            while (current.forward.get(i) != null && current.forward.get(i).value < value) {
                current = current.forward.get(i);
            }
            update[i] = current;
        }

        Node targetNode = current.forward.get(0);
        if (targetNode != null && targetNode.value == value) {
            for (int i = 0; i <= level; i++) {
                if (update[i].forward.get(i) != targetNode) {
                    break;
                }
                update[i].forward.set(i, targetNode.forward.get(i));
            }

            while (level > 0 && head.forward.get(level) == null) {
                level--;
            }
        }
    }

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
        skipGraph.insert(29);

        System.out.println("Search 19: " + skipGraph.search(19));
        System.out.println("Search 8: " + skipGraph.search(8));

        skipGraph.delete(19);
        skipGraph.delete(8);

        System.out.println("Search 19 after deletion: " + skipGraph.search(19));
    }
}
