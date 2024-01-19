import java.util.ArrayList;
import java.util.List;

class Transaction {
    private String transactionId;
    private String type;
    private double amount;

    public Transaction(String transactionId, String type, double amount) {
        this.transactionId = transactionId;
        this.type = type;
        this.amount = amount;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public String getType() {
        return type;
    }

    public double getAmount() {
        return amount;
    }
}

class Account {
    private String accountId;
    private double balance;
    private List<Transaction> transactionHistory;

    public Account(String accountId) {
        this.accountId = accountId;
        this.balance = 0.0;
        this.transactionHistory = new ArrayList<>();
    }

    public String getAccountId() {
        return accountId;
    }

    public double getBalance() {
        return balance;
    }

    public List<Transaction> getTransactionHistory() {
        return transactionHistory;
    }

    public void deposit(double amount) {
        balance += amount;
        Transaction transaction = new Transaction(
            "T" + System.currentTimeMillis(),
            "deposite",
            amount
        );
        transactionHistory.add(transaction);
        System.out.println("Deposit of " + amount + " successful. New balance: " + balance);
    }

    public void withdraw(double amount) {
        if (balance >= amount) {
            balance -= amount;
            Transaction transaction = new Transaction(
                "T" + System.currentTimeMillis(),
                "withdraw",
                amount
            );
            transactionHistory.add(transaction);
            System.out.println("Withdrawal of " + amount + " successful. New balance: " + balance);
        } else {
            System.out.println("Insufficient funds. Withdrawal failed.");
        }
    }

    public void printTransactionHistory() {
        System.out.println("Transaction History for Account " + accountId + ":");
        for (Transaction transaction : transactionHistory) {
            System.out.println(
                "Transaction ID: " + transaction.getTransactionId() +
                ", Type: " + transaction.getType() +
                ", Amount: " + transaction.getAmount()
            );
        }
    }
}

class Customer {
    private String customerId;
    private String name;
    private List<Account> accounts;

    public Customer(String customerId, String name) {
        this.customerId = customerId;
        this.name = name;
        this.accounts = new ArrayList<>();
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getName() {
        return name;
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public void addAccount(Account account) {
        accounts.add(account);
    }
}

class Bank {
    private List<Customer> customers;

    public Bank() {
        this.customers = new ArrayList<>();
    }

    public List<Customer> getCustomers() {
        return customers;
    }

    public void addCustomer(Customer customer) {
        customers.add(customer);
    }
}

public class Main {
    public static void main(String[] args) {
        Bank bank = new Bank();

        Customer customer1 = new Customer("a1", "aarav");
        Customer customer2 = new Customer("a2", "narem");

        Account account1 = new Account("1");
        Account account2 = new Account("2");

        customer1.addAccount(account1);
        customer2.addAccount(account2);

        bank.addCustomer(customer1);
        bank.addCustomer(customer2);

        // Perform transactions
        account1.deposit(1000.0);
        account1.withdraw(500.0);

        account2.deposit(1500.0);
        account2.withdraw(200.0);

        // Print transaction history
        account1.printTransactionHistory();
        account2.printTransactionHistory();
    }
}
