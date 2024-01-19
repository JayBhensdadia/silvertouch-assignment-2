import java.util.ArrayList;
import java.util.List;

class Product {
    private String productId;
    private String name;
    private double price;

    public Product(String productId, String name, double price) {
        this.productId = productId;
        this.name = name;
        this.price = price;
    }

    public String getProductId() {
        return productId;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }
}

class ShoppingCart {
    private List<Product> cartItems;

    public ShoppingCart() {
        this.cartItems = new ArrayList<>();
    }

    public void addProduct(Product product) {
        cartItems.add(product);
        System.out.println("Product added to the shopping cart: " + product.getName());
    }

    public void removeProduct(Product product) {
        cartItems.remove(product);
        System.out.println("Product removed from the shopping cart: " + product.getName());
    }

    public List<Product> getCartItems() {
        return cartItems;
    }

    public void clearCart() {
        cartItems.clear();
        System.out.println("Shopping cart cleared.");
    }
}

class Order {
    private int orderId;
    private List<Product> orderItems;
    private double totalAmount;

    public Order(int orderId, List<Product> orderItems) {
        this.orderId = orderId;
        this.orderItems = orderItems;
        calculateTotalAmount();
    }

    public int getOrderId() {
        return orderId;
    }

    public List<Product> getOrderItems() {
        return orderItems;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    private void calculateTotalAmount() {
        totalAmount = orderItems.stream().mapToDouble(Product::getPrice).sum();
    }
}

public class Main {
    public static void main(String[] args) {
        Product laptop = new Product("P001", "Laptop", 800.0);
        Product smartphone = new Product("P002", "Smartphone", 400.0);
        Product headphones = new Product("P003", "Headphones", 50.0);

        ShoppingCart shoppingCart = new ShoppingCart();

        shoppingCart.addProduct(laptop);
        shoppingCart.addProduct(smartphone);
        shoppingCart.addProduct(headphones);

        System.out.println("Shopping Cart Items:");
        for (Product product : shoppingCart.getCartItems()) {
            System.out.println(product.getName() + " - " + product.getPrice());
        }

        Order order = new Order(1, shoppingCart.getCartItems());

        System.out.println("\nOrder Details:");
        System.out.println("Order ID: " + order.getOrderId());
        System.out.println("Order Items:");
        for (Product product : order.getOrderItems()) {
            System.out.println(product.getName() + " - " + product.getPrice());
        }
        System.out.println("Total Amount: " + order.getTotalAmount());

        shoppingCart.clearCart();
    }
}
