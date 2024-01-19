import java.util.Random;

class SkipListNode {
    int value;
    SkipListNode[] forward;

    public SkipListNode(int value, int level) {
        this.value = value;
        this.forward = new SkipListNode[level + 1];
    }
}

public class Main {
    private static final int MAX_LEVEL = 16; 
    private int level;
    private SkipListNode head; 

    public Main() {
        this.level = 0;
        this.head = new SkipListNode(Integer.MIN_VALUE, MAX_LEVEL);
    }

    private int randomLevel() {
        Random rand = new Random();
        int lvl = 0;
        while (lvl < MAX_LEVEL && rand.nextDouble() < 0.5) {
            lvl++;
        }
        return lvl;
    }

    public void insert(int value) {
        int updateLevel = level;
        SkipListNode[] update = new SkipListNode[MAX_LEVEL + 1];

        SkipListNode current = head;
        for (int i = level; i >= 0; i--) {
            while (current.forward[i] != null && current.forward[i].value < value) {
                current = current.forward[i];
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

        SkipListNode newNode = new SkipListNode(value, newLevel);
        for (int i = 0; i <= newLevel; i++) {
            newNode.forward[i] = update[i].forward[i];
            update[i].forward[i] = newNode;
        }
    }

    public void delete(int value) {
        SkipListNode[] update = new SkipListNode[MAX_LEVEL + 1];
        SkipListNode current = head;

        for (int i = level; i >= 0; i--) {
            while (current.forward[i] != null && current.forward[i].value < value) {
                current = current.forward[i];
            }
            update[i] = current;
        }

        if (current.forward[0] != null && current.forward[0].value == value) {
            for (int i = 0; i <= level; i++) {
                if (update[i].forward[i] != current.forward[i]) {
                    break;
                }
                update[i].forward[i] = current.forward[i];
            }

            while (level > 0 && head.forward[level] == null) {
                level--;
            }
        }
    }

    public boolean search(int value) {
        SkipListNode current = head;
        for (int i = level; i >= 0; i--) {
            while (current.forward[i] != null && current.forward[i].value < value) {
                current = current.forward[i];
            }
        }
        return current.forward[0] != null && current.forward[0].value == value;
    }

    public void printSkipList() {
        System.out.println("Skip List (Level " + level + "):");
        for (int i = level; i >= 0; i--) {
            SkipListNode current = head.forward[i];
            System.out.print("Level " + i + ": ");
            while (current != null) {
                System.out.print(current.value + " ");
                current = current.forward[i];
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        Main skipList = new Main();

        skipList.insert(3);
        skipList.insert(6);
        skipList.insert(7);
        skipList.insert(9);
        skipList.insert(12);
        skipList.insert(19);
        skipList.insert(17);
        skipList.insert(26);
        skipList.insert(21);
        skipList.insert(25);

        skipList.printSkipList();

        System.out.println("Search 19: " + skipList.search(19));
        System.out.println("Search 8: " + skipList.search(8));

        skipList.delete(19);
        skipList.delete(21);

        skipList.printSkipList();
    }
}
