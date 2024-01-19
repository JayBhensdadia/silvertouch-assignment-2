import java.util.UUID;
import java.util.HashMap;
import java.util.Map;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

class Customer {
    private UUID customerId;
    private String name;

    public Customer(String name) {
        this.customerId = UUID.randomUUID();
        this.name = name;
    }

    public UUID getCustomerId() {
        return customerId;
    }

    public String getName() {
        return name;
    }
}

class Product {
    private UUID productId;
    private String name;
    private BigDecimal price;

    public Product(String name, BigDecimal price) {
        this.productId = UUID.randomUUID();
        this.name = name;
        this.price = price;
    }

    public UUID getProductId() {
        return productId;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }
}



class Order {
    private UUID orderId;
    private Customer customer;
    private List<OrderItem> orderItems;

    public Order(Customer customer) {
        this.orderId = UUID.randomUUID();
        this.customer = customer;
        this.orderItems = new ArrayList<>();
    }

    public UUID getOrderId() {
        return orderId;
    }

    public Customer getCustomer() {
        return customer;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void addOrderItem(OrderItem orderItem) {
        orderItems.add(orderItem);
    }
}

class OrderItem {
    private Product product;
    private int quantity;

    public OrderItem(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }
}


class CustomerRepository {
    private Map<UUID, Customer> customers;

    public CustomerRepository() {
        this.customers = new HashMap<>();
    }

    public void save(Customer customer) {
        customers.put(customer.getCustomerId(), customer);
    }

    public Customer findById(UUID customerId) {
        return customers.get(customerId);
    }
}


class ProductRepository {
    private Map<UUID, Product> products;

    public ProductRepository() {
        this.products = new HashMap<>();
    }

    public void save(Product product) {
        products.put(product.getProductId(), product);
    }

    public Product findById(UUID productId) {
        return products.get(productId);
    }
}

class OrderRepository {
    private Map<UUID, Order> orders;

    public OrderRepository() {
        this.orders = new HashMap<>();
    }

    public void save(Order order) {
        orders.put(order.getOrderId(), order);
    }

    public Order findById(UUID orderId) {
        return orders.get(orderId);
    }
}



class ProductService {
    private ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product createProduct(String name, BigDecimal price) {
        Product product = new Product(name, price);
        productRepository.save(product);
        return product;
    }

    public Product getProductById(UUID productId) {
       return productRepository.findById(productId);
    }
}



class CustomerService {
    private CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Customer createCustomer(String name) {
        Customer customer = new Customer(name);
        customerRepository.save(customer);
        return customer;
    }

    public Customer findCustomerById(UUID customerId) {
        return customerRepository.findById(customerId);
    }
}

class OrderService {
    private OrderRepository orderRepository;
    private ProductService productService;
    private CustomerService customerService;

    public OrderService(OrderRepository orderRepository, ProductService productService, CustomerService customerService) {
        this.orderRepository = orderRepository;
        this.productService = productService;
        this.customerService = customerService;
    }

    public Order createOrder(UUID customerId, UUID productId, int quantity) {
        Customer customer = customerService.findCustomerById(customerId);
        if (customer == null) {
            throw new IllegalArgumentException("Customer not found");
        }

        Product product = productService.getProductById(productId);
        if (product == null) {
            throw new IllegalArgumentException("Product not found");
        }

        Order order = new Order(customer);
        OrderItem orderItem = new OrderItem(product, quantity);
        order.addOrderItem(orderItem);
        orderRepository.save(order);

        return order;
    }
}




public class Main {
    public static void main(String[] args) {
        CustomerRepository customerRepository = new CustomerRepository();
        ProductRepository productRepository = new ProductRepository();
        OrderRepository orderRepository = new OrderRepository();

        CustomerService customerService = new CustomerService(customerRepository);
        ProductService productService = new ProductService(productRepository);
        OrderService orderService = new OrderService(orderRepository, productService, customerService);

        // Create customers and products
        Customer customer1 = customerService.createCustomer("Alice");
        Customer customer2 = customerService.createCustomer("Bob");

        Product product1 = productService.createProduct("Laptop", new BigDecimal("999.99"));
        Product product2 = productService.createProduct("Phone", new BigDecimal("299.99"));

        // Create orders
        Order order1 = orderService.createOrder(customer1.getCustomerId(), product1.getProductId(), 2);
        Order order2 = orderService.createOrder(customer2.getCustomerId(), product2.getProductId(), 1);

        // Display orders
        displayOrderDetails(order1);
        displayOrderDetails(order2);
    }

    private static void displayOrderDetails(Order order) {
        System.out.println("Order ID: " + order.getOrderId());
        System.out.println("Customer: " + order.getCustomer().getName());

        for (OrderItem orderItem : order.getOrderItems()) {
            System.out.println("Product: " + orderItem.getProduct().getName());
            System.out.println("Quantity: " + orderItem.getQuantity());
            System.out.println("Total Price: " + orderItem.getProduct().getPrice().multiply(BigDecimal.valueOf(orderItem.getQuantity())));
            System.out.println("---------------------");
        }
        System.out.println();
    }
}



