import java.util.HashMap;
import java.util.Map;


class CreateProductCommand {
    private String productId;
    private String productName;

    public CreateProductCommand(String productId, String productName) {
        this.productId = productId;
        this.productName = productName;
    }

    public String getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }
}


class ProductCommandHandler {
    private static final Map<String, String> productDatabase = new HashMap<>();

    public void handle(CreateProductCommand command) {
        String productId = command.getProductId();
        String productName = command.getProductName();

        
        productDatabase.put(productId, productName);

        System.out.println("Product created - ID: " + productId + ", Name: " + productName);
    }
}


class GetProductQuery {
    private String productId;

    public GetProductQuery(String productId) {
        this.productId = productId;
    }

    public String getProductId() {
        return productId;
    }
}


class ProductQueryHandler {
    private static final Map<String, String> productDatabase = new HashMap<>();

    public String handle(GetProductQuery query) {
        String productId = query.getProductId();

        
        String productName = productDatabase.getOrDefault(productId, "Product not found");

        return "Product ID: " + productId + ", Name: " + productName;
    }
}


public class Main {
    public static void main(String[] args) {
        
        CreateProductCommand createCommand = new CreateProductCommand("P001", "Laptop");
        ProductCommandHandler commandHandler = new ProductCommandHandler();
        commandHandler.handle(createCommand);

        
        GetProductQuery getQuery = new GetProductQuery("P001");
        ProductQueryHandler queryHandler = new ProductQueryHandler();
        String productInfo = queryHandler.handle(getQuery);

        System.out.println(productInfo);
    }
}
