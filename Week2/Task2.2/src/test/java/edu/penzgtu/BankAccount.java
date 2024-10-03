package edu.penzgtu;

public class BankAccount {
    private String accountNumber;
    private double balance;
    public BankAccount(String accountNumber, double balance) {
        this.accountNumber = accountNumber;
        this.balance = balance;
    }

    public void deposit(double amount) {
        balance += amount;
        System.out.println(amount + " deposited successfully");
    }

    public void withdraw(double amount) {
        if (amount <= balance) {
            balance -= amount;
            System.out.println(amount + " withdrawn successfully");
        } else {
            System.out.println("Insufficient funds");
        }
    }

    public double getBalance() {
        return balance;
    }

    public static void main(String[] args) {
        BankAccount account = new BankAccount("123456", 1000.0);
        System.out.println("Current balance: " + account.getBalance());

        account.deposit(500.0);
        System.out.println("Current balance: " + account.getBalance());

        account.withdraw(200.0);
        System.out.println("Current balance: " + account.getBalance());

        account.withdraw(1500.0);
        System.out.println("Current balance: " + account.getBalance());
    }
}
