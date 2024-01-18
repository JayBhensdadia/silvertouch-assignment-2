import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicMarkableReference;

class Node<T> {
    final T value;
    final int level;
    final AtomicMarkableReference<Node<T>>[] next;

    Node(T value, int level) {
        this.value = value;
        this.level = level;
        this.next = new AtomicMarkableReference[level + 1];
        for (int i = 0; i <= level; i++) {
            this.next[i] = new AtomicMarkableReference<>(null, false);
        }
    }
}

class SkipList<T extends Comparable<T>> {
    private static final int MAX_LEVEL = 31; 
    public final Node<T> head = new Node<>(null, MAX_LEVEL);
    private final ThreadLocalRandom random = ThreadLocalRandom.current();

    public boolean contains(T value) {
        int topLevel = head.level;
        Node<T> current = head;

        for (int i = topLevel; i >= 0; i--) {
            while (current.next[i].getReference() != null &&
                    current.next[i].getReference().value.compareTo(value) < 0) {
                current = current.next[i].getReference();
            }
        }

        Node<T> next = current.next[0].getReference();
        return next != null && next.value.equals(value);
    }

    public boolean add(T value) {
        int topLevel = randomLevel();
        Node<T>[] update = new Node[MAX_LEVEL + 1];
        Node<T> current = head;

        for (int i = topLevel; i >= 0; i--) {
            while (current.next[i].getReference() != null &&
                    current.next[i].getReference().value.compareTo(value) < 0) {
                current = current.next[i].getReference();
            }
            update[i] = current;
        }

        Node<T> newNode = new Node<>(value, topLevel);

        for (int i = 0; i <= topLevel; i++) {
            newNode.next[i].set(update[i].next[i].getReference(), false);
            update[i].next[i].set(newNode, false);
        }

        return true; 
    }

    public boolean remove(T value) {
        Node<T>[] update = new Node[MAX_LEVEL + 1];
        Node<T> current = head;

        for (int i = head.level; i >= 0; i--) {
            while (current.next[i].getReference() != null &&
                    current.next[i].getReference().value.compareTo(value) < 0) {
                current = current.next[i].getReference();
            }
            update[i] = current;
        }

        Node<T> toRemove = current.next[0].getReference();

        while (toRemove != null && toRemove.value.equals(value)) {
            for (int i = toRemove.level; i >= 0; i--) {
                Node<T> next = toRemove.next[i].getReference();
                update[i].next[i].set(next, false);
            }

            toRemove = current.next[0].getReference();
        }

        return true; 
    }

    private int randomLevel() {
        int level = 0;
        while (random.nextDouble() < 0.5 && level < MAX_LEVEL) {
            level++;
        }
        return level;
    }

    
}

public class Main {
    public static void main(String[] args) {
        SkipList<Integer> skipList = new SkipList<>();

        
        for (int i = 1; i <= 10; i++) {
            skipList.add(i * 2);
        }

        
        System.out.println("Contains 6: " + skipList.contains(6)); 
        System.out.println("Contains 15: " + skipList.contains(15)); 

        
        skipList.remove(6);
        System.out.println("Contains 6 after removal: " + skipList.contains(6)); 

        
        skipList.add(25);
        skipList.add(10);

        
        System.out.println("Contains 25: " + skipList.contains(25)); 

        
        visualizeSkipList(skipList);
    }

    private static void visualizeSkipList(SkipList<Integer> skipList) {
        System.out.println("Visualizing Skip List:");
        Node<Integer> current = skipList.head;

        while (current.next[0].getReference() != null) {
            System.out.print(current.value + " -> ");
            current = current.next[0].getReference();
        }
        System.out.println("null");
    }
}
