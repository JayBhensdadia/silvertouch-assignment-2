import java.util.ArrayList;
import java.util.List;
import java.util.Random;

class Bidder {
    private String bidderName;
    private double balance;

    public Bidder(String bidderName, double balance) {
        this.bidderName = bidderName;
        this.balance = balance;
    }

    public String getBidderName() {
        return bidderName;
    }

    public double getBalance() {
        return balance;
    }

    public void deductBalance(double amount) {
        if (amount <= balance) {
            balance -= amount;
        } else {
            System.out.println("Insufficient funds to deduct.");
        }
    }

    public void addBalance(double amount) {
        balance += amount;
    }
}

class Item {
    private String itemName;
    private double initialPrice;
    private Bidder highestBidder;
    private double highestBid;

    public Item(String itemName, double initialPrice) {
        this.itemName = itemName;
        this.initialPrice = initialPrice;
        this.highestBidder = null;
        this.highestBid = initialPrice;
    }

    public String getItemName() {
        return itemName;
    }

    public double getInitialPrice() {
        return initialPrice;
    }

    public Bidder getHighestBidder() {
        return highestBidder;
    }

    public double getHighestBid() {
        return highestBid;
    }

    public void placeBid(Bidder bidder, double bidAmount) {
        if (bidAmount > highestBid) {
            if (bidder.getBalance() >= bidAmount) {
                highestBidder = bidder;
                highestBid = bidAmount;
                System.out.println("Bid placed successfully by " + bidder.getBidderName() +
                        " for item " + itemName + " with a bid of " + bidAmount);
            } else {
                System.out.println("Bidder " + bidder.getBidderName() + " has insufficient funds.");
            }
        } else {
            System.out.println("Bid amount should be higher than the current highest bid.");
        }
    }
}

class Auction {
    private List<Item> items;

    public Auction() {
        this.items = new ArrayList<>();
    }

    public void addItem(Item item) {
        items.add(item);
    }

    public void startAuction() {
        Random random = new Random();

        for (Item item : items) {
            System.out.println("Auction for item: " + item.getItemName() +
                    " starting with initial price " + item.getInitialPrice());

            // Simulate bidding process with random bidders and bid amounts
            for (int i = 0; i < 3; i++) {
                Bidder bidder = new Bidder("Bidder" + (i + 1), random.nextDouble() * 1000 + 100);
                double bidAmount = random.nextDouble() * 200 + 50;
                item.placeBid(bidder, bidAmount);
            }

            System.out.println("Auction ended for item: " + item.getItemName() +
                    ". Highest bid: " + item.getHighestBid() +
                    " by " + (item.getHighestBidder() != null ? item.getHighestBidder().getBidderName() : "No bidder"));
            System.out.println("--------------------------------------------------");
        }
    }
}

public class Main {
    public static void main(String[] args) {
        // Create an auction
        Auction auction = new Auction();

        // Add items to the auction
        Item item1 = new Item("Painting", 200);
        Item item2 = new Item("Antique Watch", 150);
        Item item3 = new Item("Vintage Camera", 180);

        auction.addItem(item1);
        auction.addItem(item2);
        auction.addItem(item3);

        // Start the auction
        auction.startAuction();
    }
}
