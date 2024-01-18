import java.util.Random;

class Node {
    int value;
    Node[] next;

    public Node(int value, int level) {
        this.value = value;
        this.next = new Node[level + 1];
    }
}

public class SkipList {
    private static final int MAX_LEVEL = 16; 
    private int level; 
    private Node head; 
    private Random random; 

    public SkipList() {
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

   
    public void insert(int value) {
        Node[] update = new Node[MAX_LEVEL + 1];
        Node current = head;

        for (int i = level; i >= 0; i--) {
            while (current.next[i] != null && current.next[i].value < value) {
                current = current.next[i];
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
            newNode.next[i] = update[i].next[i];
            update[i].next[i] = newNode;
        }
    }

    
    public boolean search(int value) {
        Node current = head;
        for (int i = level; i >= 0; i--) {
            while (current.next[i] != null && current.next[i].value < value) {
                current = current.next[i];
            }
        }
        current = current.next[0];
        return current != null && current.value == value;
    }

    
    public void delete(int value) {
        Node[] update = new Node[MAX_LEVEL + 1];
        Node current = head;

        for (int i = level; i >= 0; i--) {
            while (current.next[i] != null && current.next[i].value < value) {
                current = current.next[i];
            }
            update[i] = current;
        }

        current = current.next[0];

        if (current != null && current.value == value) {
            for (int i = 0; i <= level; i++) {
                if (update[i].next[i] != current) {
                    break;
                }
                update[i].next[i] = current.next[i];
            }

            
            while (level > 0 && head.next[level] == null) {
                level--;
            }
        }
    }

    
    public void display() {
        for (int i = level; i >= 0; i--) {
            Node current = head.next[i];
            System.out.print("Level " + i + ": ");
            while (current != null) {
                System.out.print(current.value + " ");
                current = current.next[i];
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        SkipList skipList = new SkipList();
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
        skipList.display();

        System.out.println("Search for 19: " + skipList.search(19));
        System.out.println("Search for 20: " + skipList.search(20));

        skipList.delete(19);
        skipList.display();
    }
}
