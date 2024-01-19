import java.util.Scanner;

class BankAccount {
    protected double balance;

    public BankAccount(double initialBalance) {
        this.balance = initialBalance;
    }

    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            System.out.println("Deposited: $" + amount);
        } else {
            System.out.println("Invalid deposit amount.");
        }
    }

    public void withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            System.out.println("Withdrawn: $" + amount);
        } else {
            System.out.println("Invalid withdrawal amount or insufficient funds.");
        }
    }

    public double checkBalance() {
        System.out.println("Current Balance: $" + balance);
        return balance;
    }
}

class SavingsAccount extends BankAccount {
    private double interestRate;

    public SavingsAccount(double initialBalance, double interestRate) {
        super(initialBalance);
        this.interestRate = interestRate;
    }

    public void applyInterest() {
        double interest = balance * interestRate;
        balance += interest;
        System.out.println("Interest applied: $" + interest);
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        SavingsAccount savingsAccount = new SavingsAccount(1000, 0.05);

        savingsAccount.deposit(500);
        savingsAccount.checkBalance();
        savingsAccount.applyInterest();
        savingsAccount.checkBalance();
        savingsAccount.withdraw(200);
        savingsAccount.checkBalance();

        scanner.close();
    }
}
