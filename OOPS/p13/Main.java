
interface PaymentStrategy {
    void pay(int amount);
}

class CreditCardPayment implements PaymentStrategy {
    private String cardNumber;

    public CreditCardPayment(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    @Override
    public void pay(int amount) {
        System.out.println("Paid " + amount + " using credit card " + cardNumber);
    }
}

class PayPalPayment implements PaymentStrategy {
    private String email;

    public PayPalPayment(String email) {
        this.email = email;
    }

    @Override
    public void pay(int amount) {
        System.out.println("Paid " + amount + " using PayPal account " + email);
    }
}

class ShoppingCart {
    private PaymentStrategy paymentStrategy;

    public void setPaymentStrategy(PaymentStrategy paymentStrategy) {
        this.paymentStrategy = paymentStrategy;
    }

    public void checkout(int amount) {
        if (paymentStrategy != null) {
            paymentStrategy.pay(amount);
        } else {
            System.out.println("No payment strategy set");
        }
    }
}

public class Main {
    public static void main(String[] args) {

        PaymentStrategy creditCardStrategy = new CreditCardPayment("1234-5678-9876-5432");
        PaymentStrategy payPalStrategy = new PayPalPayment("user@example.com");

        ShoppingCart shoppingCart = new ShoppingCart();

        shoppingCart.setPaymentStrategy(creditCardStrategy);
        shoppingCart.checkout(100);

        shoppingCart.setPaymentStrategy(payPalStrategy);
        shoppingCart.checkout(50);
    }
}
