import java.util.concurrent.atomic.AtomicReference;

class Node<T> {
    final T value;
    Node<T> next;

    Node(T value) {
        this.value = value;
    }
}

public class Main<T> {
    private final AtomicReference<Node<T>> top = new AtomicReference<>();

    public void push(T value) {
        Node<T> newNode = new Node<>(value);
        Node<T> oldTop;

        do {
            oldTop = top.get();
            newNode.next = oldTop;
        } while (!top.compareAndSet(oldTop, newNode));
    }

    public T pop() {
        Node<T> oldTop;
        Node<T> newTop;

        do {
            oldTop = top.get();

            if (oldTop == null) {
                return null; 
            }

            newTop = oldTop.next;
        } while (!top.compareAndSet(oldTop, newTop));

        return oldTop.value;
    }

    public static void main(String[] args) {
        Main<Integer> stack = new Main<>();

        
        Runnable pushTask = () -> {
            for (int i = 0; i < 5; i++) {
                stack.push(i);
            }
        };

        Thread thread1 = new Thread(pushTask);
        Thread thread2 = new Thread(pushTask);

        thread1.start();
        thread2.start();

        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        
        Integer value;
        while ((value = stack.pop()) != null) {
            System.out.println("Popped: " + value);
        }
    }
}

