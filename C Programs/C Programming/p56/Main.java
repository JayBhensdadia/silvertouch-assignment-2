import java.util.*;

class SkipNode {
    int key;
    List<SkipNode> forward;

    public SkipNode(int key, int level) {
        this.key = key;
        this.forward = new ArrayList<>(level + 1);
        for (int i = 0; i <= level; i++) {
            forward.add(null);
        }
    }
}

public class Main {
    private static final int MAX_LEVEL = 16;
    private int level;
    private SkipNode header;

    public Main() {
        this.level = 0;
        this.header = new SkipNode(Integer.MIN_VALUE, MAX_LEVEL);
    }

    public int randomLevel() {
        int level = 0;
        while (Math.random() < 0.5 && level < MAX_LEVEL) {
            level++;
        }
        return level;
    }

    public void insert(int key) {
        SkipNode[] update = new SkipNode[MAX_LEVEL + 1];
        SkipNode current = header;

        for (int i = level; i >= 0; i--) {
            while (current.forward.get(i) != null && current.forward.get(i).key < key) {
                current = current.forward.get(i);
            }
            update[i] = current;
        }

        int newLevel = randomLevel();

        if (newLevel > level) {
            for (int i = level + 1; i <= newLevel; i++) {
                update[i] = header;
            }
            level = newLevel;
        }

        SkipNode newNode = new SkipNode(key, newLevel);

        for (int i = 0; i <= newLevel; i++) {
            newNode.forward.set(i, update[i].forward.get(i));
            update[i].forward.set(i, newNode);
        }
    }

    public boolean search(int key) {
        SkipNode current = header;

        for (int i = level; i >= 0; i--) {
            while (current.forward.get(i) != null && current.forward.get(i).key < key) {
                current = current.forward.get(i);
            }
        }

        current = current.forward.get(0);

        return current != null && current.key == key;
    }

    public void delete(int key) {
        SkipNode[] update = new SkipNode[MAX_LEVEL + 1];
        SkipNode current = header;

        for (int i = level; i >= 0; i--) {
            while (current.forward.get(i) != null && current.forward.get(i).key < key) {
                current = current.forward.get(i);
            }
            update[i] = current;
        }

        current = current.forward.get(0);

        if (current != null && current.key == key) {
            for (int i = 0; i <= level; i++) {
                if (update[i].forward.get(i) != current) {
                    break;
                }
                update[i].forward.set(i, current.forward.get(i));
            }

            while (level > 0 && header.forward.get(level) == null) {
                level--;
            }
        }
    }

    public void display() {
        for (int i = level; i >= 0; i--) {
            SkipNode node = header.forward.get(i);
            System.out.print("Level " + i + ": ");
            while (node != null) {
                System.out.print(node.key + " ");
                node = node.forward.get(i);
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
        skipGraph.insert(19);
        skipGraph.insert(17);
        skipGraph.insert(26);
        skipGraph.insert(21);
        skipGraph.insert(25);
        skipGraph.display();

        System.out.println("Search for 19: " + skipGraph.search(19));
        System.out.println("Search for 20: " + skipGraph.search(20));

        skipGraph.delete(19);
        skipGraph.delete(25);
        skipGraph.display();
    }
}
