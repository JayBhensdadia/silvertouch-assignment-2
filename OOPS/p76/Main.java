import java.util.ArrayList;
import java.util.List;

class Stock {
    private String symbol;
    private String companyName;
    private double currentPrice;

    public Stock(String symbol, String companyName, double currentPrice) {
        this.symbol = symbol;
        this.companyName = companyName;
        this.currentPrice = currentPrice;
    }

    public String getSymbol() {
        return symbol;
    }

    public String getCompanyName() {
        return companyName;
    }

    public double getCurrentPrice() {
        return currentPrice;
    }
}

class Transaction {
    private Stock stock;
    private int quantity;
    private double price;
    private TransactionType type;

    public Transaction(Stock stock, int quantity, double price, TransactionType type) {
        this.stock = stock;
        this.quantity = quantity;
        this.price = price;
        this.type = type;
    }

    public Stock getStock() {
        return stock;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getPrice() {
        return price;
    }

    public TransactionType getType() {
        return type;
    }
}

enum TransactionType {
    BUY, SELL
}

class Portfolio {
    private List<Transaction> transactions;

    public Portfolio() {
        this.transactions = new ArrayList<>();
    }

    public void buyStock(Stock stock, int quantity) {
        double totalPrice = stock.getCurrentPrice() * quantity;
        Transaction buyTransaction = new Transaction(stock, quantity, totalPrice, TransactionType.BUY);
        transactions.add(buyTransaction);
        System.out.println("Bought " + quantity + " shares of " + stock.getCompanyName() +
                " (Symbol: " + stock.getSymbol() + ") for " + totalPrice);
    }

    public void sellStock(Stock stock, int quantity) {
        double totalPrice = stock.getCurrentPrice() * quantity;
        Transaction sellTransaction = new Transaction(stock, quantity, totalPrice, TransactionType.SELL);
        transactions.add(sellTransaction);
        System.out.println("Sold " + quantity + " shares of " + stock.getCompanyName() +
                " (Symbol: " + stock.getSymbol() + ") for " + totalPrice);
    }

    public double calculatePortfolioValue() {
        double portfolioValue = 0;
        for (Transaction transaction : transactions) {
            if (transaction.getType() == TransactionType.BUY) {
                portfolioValue -= transaction.getPrice();
            } else {
                portfolioValue += transaction.getPrice();
            }
        }
        return portfolioValue;
    }

    public void displayTransactionHistory() {
        System.out.println("\nTransaction History:");
        for (Transaction transaction : transactions) {
            String action = (transaction.getType() == TransactionType.BUY) ? "Bought" : "Sold";
            System.out.println(action + " " + transaction.getQuantity() + " shares of " +
                    transaction.getStock().getCompanyName() + " (Symbol: " + transaction.getStock().getSymbol() +
                    ") for " + transaction.getPrice());
        }
    }
}

public class Main {
    public static void main(String[] args) {
        Stock appleStock = new Stock("AAPL", "Apple Inc.", 150.0);
        Stock googleStock = new Stock("GOOGL", "Alphabet Inc.", 2700.0);

        Portfolio portfolio = new Portfolio();

        portfolio.buyStock(appleStock, 10);
        portfolio.sellStock(googleStock, 5);
        portfolio.buyStock(googleStock, 8);

        System.out.println("\nPortfolio Value: " + portfolio.calculatePortfolioValue());
        portfolio.displayTransactionHistory();
    }
}
