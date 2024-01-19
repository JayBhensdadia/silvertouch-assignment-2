import java.util.concurrent.CompletableFuture;

class StateMachine {
    private State currentState;

    public StateMachine() {
        this.currentState = new StateA();
    }

    public void run() {
        while (currentState != null) {
            currentState = currentState.execute();
        }
    }

    private interface State {
        State execute();
    }

    private class StateA implements State {
        @Override
        public State execute() {
            System.out.println("State A");

            CompletableFuture<Void> future = new CompletableFuture<>();
            new Thread(() -> {
                try {
                    Thread.sleep(1000);
                    future.complete(null);
                } catch (InterruptedException e) {
                    future.completeExceptionally(e);
                }
            }).start();
            future.join();
            return new StateB();
        }
    }

    private class StateB implements State {
        @Override
        public State execute() {
            System.out.println("State B");

            return new StateC();
        }
    }

    private class StateC implements State {
        @Override
        public State execute() {
            System.out.println("State C");

            CompletableFuture<Void> future = new CompletableFuture<>();
            new Thread(() -> {
                try {
                    Thread.sleep(1500);
                    future.complete(null);
                } catch (InterruptedException e) {
                    future.completeExceptionally(e);
                }
            }).start();
            future.join();
            return null;
        }
    }
}

public class Main {
    public static void main(String[] args) {
        StateMachine stateMachine = new StateMachine();
        stateMachine.run();
    }
}
