
interface RealSubject {
    void request();
}

class RealSubjectImpl implements RealSubject {
    @Override
    public void request() {
        System.out.println("RealSubject is processing the request.");
    }
}

class Proxy implements RealSubject {
    private RealSubject realSubject;

    @Override
    public void request() {

        if (realSubject == null) {
            realSubject = new RealSubjectImpl();
        }

        System.out.println("Proxy is processing the request before delegating to RealSubject.");

        realSubject.request();

        System.out.println("Proxy is processing the request after delegating to RealSubject.");
    }
}

public class Main {
    public static void main(String[] args) {

        Proxy proxy = new Proxy();
        proxy.request();
    }
}
