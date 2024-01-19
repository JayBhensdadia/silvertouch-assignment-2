import java.util.ArrayList;
import java.util.List;

interface Observable {
    void addObserver(Observer observer);

    void removeObserver(Observer observer);

    void notifyObservers(String message);
}

interface Observer {
    void update(String message);
}

class ConcreteObserver implements Observer {
    private final String name;

    ConcreteObserver(String name) {
        this.name = name;
    }

    @Override
    public void update(String message) {
        System.out.println(name + " received message: " + message);
    }
}

class ThreadSafeObservable implements Observable {
    private final List<Observer> observers = new ArrayList<>();

    @Override
    public synchronized void addObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public synchronized void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public synchronized void notifyObservers(String message) {
        for (Observer observer : observers) {
            observer.update(message);
        }
    }
}

public class Main {
    public static void main(String[] args) {

        ThreadSafeObservable observable = new ThreadSafeObservable();

        Observer observer1 = new ConcreteObserver("Observer 1");
        Observer observer2 = new ConcreteObserver("Observer 2");

        observable.addObserver(observer1);
        observable.addObserver(observer2);

        new Thread(() -> observable.notifyObservers("Message from Thread 1")).start();
        new Thread(() -> observable.notifyObservers("Message from Thread 2")).start();
    }
}
