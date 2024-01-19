
interface Target {
    void request();
}


class Adaptee {
    void specificRequest() {
        System.out.println("Specific request from Adaptee.");
    }
}


class Adapter implements Target {
    private Adaptee adaptee;

    public Adapter(Adaptee adaptee) {
        this.adaptee = adaptee;
    }

    @Override
    public void request() {
        
        adaptee.specificRequest();
    }
}


public class Main {
    public static void main(String[] args) {
        
        Adaptee adaptee = new Adaptee();

        
        Target adapter = new Adapter(adaptee);

        
        adapter.request();
    }
}
