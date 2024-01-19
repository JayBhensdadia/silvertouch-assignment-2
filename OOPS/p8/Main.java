
interface Element {
    void accept(Visitor visitor);
}

class ConcreteElementA implements Element {
    @Override
    public void accept(Visitor visitor) {
        visitor.visitElementA(this);
    }

    String operationA() {
        return "Operation A in ConcreteElementA";
    }
}

class ConcreteElementB implements Element {
    @Override
    public void accept(Visitor visitor) {
        visitor.visitElementB(this);
    }

    String operationB() {
        return "Operation B in ConcreteElementB";
    }
}

interface Visitor {
    void visitElementA(ConcreteElementA elementA);

    void visitElementB(ConcreteElementB elementB);
}

class ConcreteVisitor implements Visitor {
    @Override
    public void visitElementA(ConcreteElementA elementA) {
        System.out.println("Visitor is performing operation on " + elementA.operationA());
    }

    @Override
    public void visitElementB(ConcreteElementB elementB) {
        System.out.println("Visitor is performing operation on " + elementB.operationB());
    }
}

class ObjectStructure {
    private Element[] elements;

    public ObjectStructure(Element... elements) {
        this.elements = elements;
    }

    public void acceptVisitor(Visitor visitor) {
        for (Element element : elements) {
            element.accept(visitor);
        }
    }
}

public class Main {
    public static void main(String[] args) {
        ConcreteElementA elementA = new ConcreteElementA();
        ConcreteElementB elementB = new ConcreteElementB();

        ObjectStructure objectStructure = new ObjectStructure(elementA, elementB);

        ConcreteVisitor visitor = new ConcreteVisitor();

        objectStructure.acceptVisitor(visitor);
    }
}
