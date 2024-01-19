import java.util.ArrayList;
import java.util.List;

class GarbageCollector {
    private List<Object> objects = new ArrayList<>();

    public void addObject(Object obj) {
        objects.add(obj);
    }

    public void removeObject(Object obj) {
        objects.remove(obj);
        System.out.println("Object removed: " + obj);
        collectGarbage();
    }

    private void collectGarbage() {
        for (Object obj : new ArrayList<>(objects)) {
            if (getReferences(obj) == 0) {
                objects.remove(obj);
                System.out.println("Garbage collected: " + obj);
            }
        }
    }

    private int getReferences(Object obj) {
        int count = 0;
        for (Object o : objects) {
            if (o != null && o != obj) {
                
                if (o instanceof ObjectWithReference) {
                    ObjectWithReference objWithRef = (ObjectWithReference) o;
                    if (objWithRef.getReference() == obj) {
                        count++;
                    }
                }
            }
        }
        return count;
    }
}

class ObjectWithReference {
    private Object reference;

    public ObjectWithReference(Object reference) {
        this.reference = reference;
    }

    public Object getReference() {
        return reference;
    }
}

public class Main {
    public static void main(String[] args) {
        GarbageCollector garbageCollector = new GarbageCollector();

        
        ObjectWithReference obj1 = new ObjectWithReference(null);
        ObjectWithReference obj2 = new ObjectWithReference(obj1);
        ObjectWithReference obj3 = new ObjectWithReference(obj2);

        
        garbageCollector.addObject(obj1);
        garbageCollector.addObject(obj2);
        garbageCollector.addObject(obj3);

        
        garbageCollector.removeObject(obj1);
        garbageCollector.removeObject(obj2);
        garbageCollector.removeObject(obj3);
    }
}
