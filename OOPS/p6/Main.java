


interface Prototype {
    Prototype clone();
    void displayInfo();
}


class ConcretePrototype implements Prototype {
    private String data;

    public ConcretePrototype(String data) {
        this.data = data;
    }

    
    public Prototype clone() {
        return new ConcretePrototype(this.data);
    }

    
    public void displayInfo() {
        System.out.println("ConcretePrototype with data: " + data);
    }
}


public class Main {
    public static void main(String[] args) {
        
        ConcretePrototype original = new ConcretePrototype("Original Data");

        
        ConcretePrototype clone = (ConcretePrototype) original.clone();

        
        System.out.println("Original Object:");
        original.displayInfo();

        System.out.println("\nCloned Object:");
        clone.displayInfo();
    }
}
