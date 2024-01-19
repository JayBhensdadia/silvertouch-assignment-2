class BankAccount {
    private double balance;

    public BankAccount(double initialBalance) {
        this.balance = initialBalance;
    }

    public synchronized void deposit(double amount) {
        balance += amount;
    }

    public synchronized void withdraw(double amount) {
        if (amount <= balance) {
            balance -= amount;
        } else {
            System.out.println("Insufficient funds!");
        }
    }

    public synchronized double getBalance() {
        return balance;
    }
}

public class Main {

    public static void main(String[] args) {
        BankAccount account = new BankAccount(1000);

        
        performTransaction(account, 100);
        performTransaction(account, 200);
        performTransaction(account, 500);

        
        System.out.println("Final Balance: " + account.getBalance());
    }

    private static void performTransaction(BankAccount account, double amount) {
        
        synchronized (account) {
            try {
                
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            
            if (amount > 0) {
                account.deposit(amount);
                System.out.println("Deposited: " + amount);
            } else {
                account.withdraw(Math.abs(amount));
                System.out.println("Withdrawn: " + Math.abs(amount));
            }
        }
    }
}
