import java.util.ArrayList;
import java.util.List;

class ProductCreatedEvent {
    private final String productId;
    private final String productName;

    public ProductCreatedEvent(String productId, String productName) {
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

class ProductAggregate {
    private String productId;
    private String productName;
    private final List<ProductCreatedEvent> events = new ArrayList<>();

    public void apply(ProductCreatedEvent event) {
        this.productId = event.getProductId();
        this.productName = event.getProductName();
        events.add(event);
    }

    public void createProduct(String productId, String productName) {
        ProductCreatedEvent event = new ProductCreatedEvent(productId, productName);
        apply(event);
    }

    public String getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public List<ProductCreatedEvent> getEvents() {
        return events;
    }
}

class EventStore {
    private final List<Object> eventLog = new ArrayList<>();

    public void storeEvent(Object event) {
        eventLog.add(event);
    }

    public ProductAggregate replayEvents(String productId) {
        ProductAggregate productAggregate = new ProductAggregate();

        for (Object event : eventLog) {
            if (event instanceof ProductCreatedEvent) {
                ProductCreatedEvent productCreatedEvent = (ProductCreatedEvent) event;
                if (productId.equals(productCreatedEvent.getProductId())) {
                    productAggregate.apply(productCreatedEvent);
                }
            }
        }

        return productAggregate;
    }
}

public class Main {
    public static void main(String[] args) {
        EventStore eventStore = new EventStore();

        ProductAggregate productAggregate = new ProductAggregate();
        productAggregate.createProduct("P001", "Laptop");
        eventStore.storeEvent(productAggregate.getEvents().get(0));

        ProductAggregate reconstructedProduct = eventStore.replayEvents("P001");

        System.out.println("Reconstructed Product ID: " + reconstructedProduct.getProductId());
        System.out.println("Reconstructed Product Name: " + reconstructedProduct.getProductName());
    }
}
