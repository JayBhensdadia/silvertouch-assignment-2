class Singleton {
    private static volatile Singleton instance;

    private Singleton() {
        
    }

    public static Singleton getInstance() {
        if (instance == null) {
            synchronized (Singleton.class) {
                if (instance == null) {
                    instance = new Singleton();
                }
            }
        }
        return instance;
    }

    public void printMsg(){
        System.out.println("Print Singleton!!!");
    }
}

public class Main{
    public static void main(String[] args) {
        Singleton.getInstance().printMsg();
    }
}

