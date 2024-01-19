
interface Handler {
    void setNextHandler(Handler nextHandler);

    void handleRequest(Request request);
}

class Request {
    private String type;

    public Request(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}

class ConcreteHandler implements Handler {
    private Handler nextHandler;

    @Override
    public void setNextHandler(Handler nextHandler) {
        this.nextHandler = nextHandler;
    }

    @Override
    public void handleRequest(Request request) {
        if (canHandle(request)) {
            System.out.println("ConcreteHandler is handling the request of type: " + request.getType());
        } else if (nextHandler != null) {
            System.out.println("ConcreteHandler passes the request to the next handler.");
            nextHandler.handleRequest(request);
        } else {
            System.out.println("No handler can process the request.");
        }
    }

    private boolean canHandle(Request request) {

        return "TypeA".equals(request.getType());
    }
}

public class Main{
    public static void main(String[] args) {

        Handler handler1 = new ConcreteHandler();
        Handler handler2 = new ConcreteHandler();
        Handler handler3 = new ConcreteHandler();

        handler1.setNextHandler(handler2);
        handler2.setNextHandler(handler3);

        Request requestA = new Request("TypeA");
        Request requestB = new Request("TypeB");

        handler1.handleRequest(requestA);
        System.out.println("--------");
        handler1.handleRequest(requestB);
    }
}
