import java.util.List;
import java.util.function.Predicate;

interface Constraint<T> {
    boolean satisfy(T value);
}

class PositiveNumberConstraint implements Constraint<Number> {
    @Override
    public boolean satisfy(Number value) {
        return value.doubleValue() > 0;
    }
}

class ConstrainedGeneric<T> {
    private final Constraint<T> constraint;

    public ConstrainedGeneric(Constraint<T> constraint) {
        this.constraint = constraint;
    }

    public void process(T value) {
        if (constraint.satisfy(value)) {
            System.out.println("Processing: " + value);

        } else {
            System.out.println("Constraint not satisfied for: " + value);
        }
    }
}

public class Main {
    public static void main(String[] args) {

        ConstrainedGeneric<Number> constrainedNumber = new ConstrainedGeneric<>(value -> value.doubleValue() > 0);

        constrainedNumber.process(5);
        constrainedNumber.process(-2);

        ConstrainedGeneric<Number> constrainedPositiveNumber = new ConstrainedGeneric<>(new PositiveNumberConstraint());
        constrainedPositiveNumber.process(3.14);
        constrainedPositiveNumber.process(-1);
    }
}
