import java.util.concurrent.atomic.AtomicReference;

class Node<T> {
    final T value;
    AtomicReference<Node<T>> next;

    Node(T value) {
        this.value = value;
        this.next = new AtomicReference<>(null);
    }
}

public class Main<T> {
    private final AtomicReference<Node<T>> head;
    private final AtomicReference<Node<T>> tail;

    public Main() {
        Node<T> dummy = new Node<>(null);
        this.head = new AtomicReference<>(dummy);
        this.tail = new AtomicReference<>(dummy);
    }

    public void enqueue(T value) {
        Node<T> newNode = new Node<>(value);
        while (true) {
            Node<T> observedTail = tail.get();
            Node<T> observedNext = observedTail.next.get();

            if (observedTail == tail.get()) {
                if (observedNext != null) {

                    tail.compareAndSet(observedTail, observedNext);
                } else {

                    if (observedTail.next.compareAndSet(null, newNode)) {

                        tail.compareAndSet(observedTail, newNode);
                        return;
                    }
                }
            }
        }
    }

    public T dequeue() {
        while (true) {
            Node<T> observedHead = head.get();
            Node<T> observedTail = tail.get();
            Node<T> observedFirst = observedHead.next.get();

            if (observedHead == head.get()) {
                if (observedHead == observedTail) {
                    if (observedFirst == null) {

                        return null;
                    }

                    tail.compareAndSet(observedTail, observedFirst);
                } else {

                    if (head.compareAndSet(observedHead, observedFirst)) {

                        return observedFirst.value;
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        Main<Integer> queue = new Main<>();

        Thread producer1 = new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                queue.enqueue(i);
                System.out.println("Produced: " + i);
            }
        });

        Thread producer2 = new Thread(() -> {
            for (int i = 5; i < 10; i++) {
                queue.enqueue(i);
                System.out.println("Produced: " + i);
            }
        });

        Thread consumer1 = new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                Integer value = queue.dequeue();
                System.out.println("Consumed by Consumer 1: " + value);
            }
        });

        Thread consumer2 = new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                Integer value = queue.dequeue();
                System.out.println("Consumed by Consumer 2: " + value);
            }
        });

        producer1.start();
        producer2.start();
        consumer1.start();
        consumer2.start();

        try {
            producer1.join();
            producer2.join();
            consumer1.join();
            consumer2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
