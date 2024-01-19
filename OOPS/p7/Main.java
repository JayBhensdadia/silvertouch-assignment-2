
class Context {
    private State currentState;

    public Context() {

        currentState = new StateA();
    }

    public void setState(State newState) {
        currentState = newState;
    }

    public void request() {
        currentState.handleRequest(this);
    }
}

interface State {
    void handleRequest(Context context);
}

class StateA implements State {
    @Override
    public void handleRequest(Context context) {
        System.out.println("Handling request in State A");

        context.setState(new StateB());
    }
}

class StateB implements State {
    @Override
    public void handleRequest(Context context) {
        System.out.println("Handling request in State B");

        context.setState(new StateA());
    }
}

public class Main {
    public static void main(String[] args) {
        Context context = new Context();

        context.request();

        context.request();

        context.request();
    }
}
