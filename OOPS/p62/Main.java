import java.util.HashMap;
import java.util.Map;

class Product {
    private String name;
    private double price;

    public Product(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public String getName(){
        return name;
    }

    public double getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return name + " ($" + price + ")";
    }
}

class ShoppingCart {
    private Map<Product, Integer> items;

    public ShoppingCart() {
        this.items = new HashMap<>();
    }

    public void addItem(Product product, int quantity) {
        items.put(product, items.getOrDefault(product, 0) + quantity);
        System.out.println("Added " + quantity + "x " + product.getName() + " to the cart.");
    }

    public void removeItem(Product product, int quantity) {
        if (items.containsKey(product)) {
            int currentQuantity = items.get(product);
            if (quantity >= currentQuantity) {
                items.remove(product);
            } else {
                items.put(product, currentQuantity - quantity);
            }
            System.out.println("Removed " + quantity + "x " + product.getName() + " from the cart.");
        } else {
            System.out.println("Product not found in the cart.");
        }
    }

    public double calculateTotal() {
        double total = 0.0;
        for (Map.Entry<Product, Integer> entry : items.entrySet()) {
            total += entry.getKey().getPrice() * entry.getValue();
        }
        return total;
    }

    public void checkout() {
        System.out.println("Checking out...");
        double total = calculateTotal();
        System.out.println("Total amount: $" + total);
        System.out.println("Thank you for shopping!");
        items.clear();
    }

    @Override
    public String toString() {
        StringBuilder cartContent = new StringBuilder("Shopping Cart:\n");
        for (Map.Entry<Product, Integer> entry : items.entrySet()) {
            cartContent.append(entry.getKey().toString())
                    .append(" x")
                    .append(entry.getValue())
                    .append("\n");
        }
        return cartContent.toString();
    }
}

class Customer {
    private String name;
    private ShoppingCart shoppingCart;

    public Customer(String name) {
        this.name = name;
        this.shoppingCart = new ShoppingCart();
    }

    public void addItemToCart(Product product, int quantity) {
        shoppingCart.addItem(product, quantity);
    }

    public void removeItemFromCart(Product product, int quantity) {
        shoppingCart.removeItem(product, quantity);
    }

    public void viewCart() {
        System.out.println(name + "'s " + shoppingCart.toString());
    }

    public void checkout() {
        shoppingCart.checkout();
    }
}

public class Main {
    public static void main(String[] args) {
        
        Product laptop = new Product("Laptop", 999.99);
        Product phone = new Product("Smartphone", 499.99);
        Product headphones = new Product("Headphones", 79.99);

        
        Customer customer1 = new Customer("Alice");
        Customer customer2 = new Customer("Bob");

        
        customer1.addItemToCart(laptop, 1);
        customer1.addItemToCart(phone, 2);

        
        customer2.addItemToCart(phone, 1);
        customer2.addItemToCart(headphones, 1);

        
        customer1.viewCart();
        customer1.checkout();

        
        customer2.removeItemFromCart(phone, 1);
        customer2.viewCart();
        customer2.checkout();
    }
}

