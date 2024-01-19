import java.util.ArrayList;
import java.util.List;

class Product {
    private String productId;
    private String productName;
    private double price;
    private int quantity;

    public Product(String productId, String productName, double price, int quantity) {
        this.productId = productId;
        this.productName = productName;
        this.price = price;
        this.quantity = quantity;
    }

    public String getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void updateQuantity(int newQuantity) {
        this.quantity = newQuantity;
    }
}

class Category {
    private String categoryId;
    private String categoryName;
    private List<Product> products;

    public Category(String categoryId, String categoryName) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.products = new ArrayList<>();
    }

    public String getCategoryId() {
        return categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void addProduct(Product product) {
        products.add(product);
    }

    public void removeProduct(Product product) {
        products.remove(product);
    }

    public List<Product> getProducts() {
        return products;
    }
}

class Warehouse {
    private List<Product> products;
    private List<Category> categories;

    public Warehouse() {
        this.products = new ArrayList<>();
        this.categories = new ArrayList<>();
    }

    public void addProduct(Product product) {
        products.add(product);
    }

    public void removeProduct(Product product) {
        products.remove(product);
    }

    public void addCategory(Category category) {
        categories.add(category);
    }

    public void removeCategory(Category category) {
        categories.remove(category);
    }

    public void updateStock(String productId, int newQuantity) {
        Product product = getProductById(productId);
        if (product != null) {
            product.updateQuantity(newQuantity);
        }
    }

    public List<Product> getProducts() {
        return products;
    }

    public List<Category> getCategories() {
        return categories;
    }

    private Product getProductById(String productId) {
        for (Product product : products) {
            if (product.getProductId().equals(productId)) {
                return product;
            }
        }
        return null;
    }
}

public class Main {
    public static void main(String[] args) {
        Warehouse warehouse = new Warehouse();

        Category electronics = new Category("C001", "Electronics");
        Category clothing = new Category("C002", "Clothing");

        Product laptop = new Product("P001", "Laptop", 12000, 30);
        Product shirt = new Product("P002", "Men's Shirt", 1500, 50);

        electronics.addProduct(laptop);
        clothing.addProduct(shirt);

        warehouse.addCategory(electronics);
        warehouse.addCategory(clothing);

        warehouse.addProduct(laptop);
        warehouse.addProduct(shirt);

        System.out.println("Inventory Report:");
        System.out.println("Categories:");
        for (Category category : warehouse.getCategories()) {
            System.out.println(category.getCategoryId() + " " + category.getCategoryName());
        }

        System.out.println("\nProducts:");
        for (Product product : warehouse.getProducts()) {
            System.out.println(product.getProductId() + " " + product.getProductName() +
                    " Price: " + product.getPrice() + " Quantity: " + product.getQuantity());
        }
    }
}
