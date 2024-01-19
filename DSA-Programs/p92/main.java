package p92;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

class Node {
    int value;
    Node[] next;

    public Node(int level, int value) {
        this.value = value;
        this.next = new Node[level + 1];
    }
}

class PersistentSkipList {
    private Node[] head;
    private int maxLevel;
    private Random random;

    public PersistentSkipList(int maxLevel) {
        this.maxLevel = maxLevel;
        this.head = new Node[maxLevel + 1];
        this.random = new Random();

        for (int i = 0; i <= maxLevel; i++) {
            head[i] = new Node(maxLevel, Integer.MIN_VALUE);
        }
    }

    private int randomLevel() {
        int level = 0;
        while (ThreadLocalRandom.current().nextBoolean() && level < maxLevel) {
            level++;
        }
        return level;
    }

    public void insert(int value) {
        int level = randomLevel();
        Node[] update = new Node[maxLevel + 1];
        Node current = head[maxLevel];

        for (int i = maxLevel; i >= 0; i--) {
            while (current.next[i] != null && current.next[i].value < value) {
                current = current.next[i];
            }
            update[i] = current;
        }

        Node newNode = new Node(level, value);
        for (int i = 0; i <= level; i++) {
            newNode.next[i] = update[i].next[i];
            update[i].next[i] = newNode;
        }
    }

    public boolean search(int value, int version) {
        Node current = head[version];
        for (int i = version; i >= 0; i--) {
            while (current.next[i] != null && current.next[i].value < value) {
                current = current.next[i];
            }
        }
        return current.next[0] != null && current.next[0].value == value;
    }

    public void printList(int version) {
        Node current = head[version].next[0];
        while (current != null) {
            System.out.print(current.value + " ");
            current = current.next[0];
        }
        System.out.println();
    }

    public static void main(String[] args) {
        PersistentSkipList skipList = new PersistentSkipList(4);

        // Version 1
        skipList.insert(3);
        skipList.insert(6);
        skipList.insert(7);
        System.out.print("Version 1: ");
        skipList.printList(1);

        // Version 2
        skipList.insert(4);
        skipList.insert(5);
        System.out.print("Version 2: ");
        skipList.printList(2);

        // Query in Version 1
        System.out.println("Search 5 in Version 1: " + skipList.search(5, 1));

        // Query in Version 2
        System.out.println("Search 5 in Version 2: " + skipList.search(5, 2));
    }
}

