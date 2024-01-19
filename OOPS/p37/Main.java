import java.util.concurrent.atomic.AtomicMarkableReference;

class Node<T> {
    final T value;
    AtomicMarkableReference<Node<T>> next;

    Node(T value) {
        this.value = value;
        this.next = new AtomicMarkableReference<>(null, false);
    }
}

public class Main<T> {
    private ThreadLocal<Node<T>> localHazardPointer = ThreadLocal.withInitial(() -> null);

    private AtomicMarkableReference<Node<T>> top = new AtomicMarkableReference<>(null, false);

    public void push(T value) {
        Node<T> newNode = new Node<>(value);

        while (true) {
            Node<T> currentTop = top.getReference();
            newNode.next.set(currentTop, false);

            if (top.compareAndSet(currentTop, newNode, false, false)) {
                return;
            }
        }
    }

    public T pop() {
        while (true) {
            Node<T> currentTop = top.getReference();

            if (currentTop == null) {
                return null; 
            }

            Node<T> newTop = currentTop.next.getReference();
            if (top.compareAndSet(currentTop, newTop, false, false)) {
                
                currentTop.next.set(null, true);
                return currentTop.value;
            }
        }
    }

    private void reclaimHazardPointers() {
        Node<T> local = localHazardPointer.get();
        if (local != null) {
            localHazardPointer.remove();
        }
    }

    public void useHazardPointer(Runnable action) {
        try {
            localHazardPointer.set(top.getReference());
            action.run();
        } finally {
            reclaimHazardPointers();
        }
    }

    public static void main(String[] args) {
        Main<Integer> stack = new Main<>();

        
        Runnable pushTask = () -> {
            for (int i = 0; i < 5; i++) {
                stack.push(i);
            }
        };

        Runnable popTask = () -> {
            for (int i = 0; i < 5; i++) {
                Integer value = stack.pop();
                System.out.println("Popped: " + value);
            }
        };

        Thread thread1 = new Thread(pushTask);
        Thread thread2 = new Thread(popTask);

        thread1.start();
        thread2.start();

        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
