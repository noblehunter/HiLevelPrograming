package edu.penzgtu;
// Обьявление класса BankAccount
public class BankAccount {
    private String accountNumber;
    private double balance;
    public BankAccount(String accountNumber, double balance) {
        this.accountNumber = accountNumber;
        this.balance = balance;
    }
    // Метод для пополнения счета
    public void deposit(double amount) {
        balance += amount;
        System.out.println(amount + " депозит успешен");
    }
    // Метод для снятия средств со счета
    public void withdraw(double amount) {
        if (amount <= balance) {
            balance -= amount;
            System.out.println(amount + " вывод успешен");
        } else {
            System.out.println("Недостаточно средств");
        }
    }
    // Метод для получения текущего баланса
    public double getBalance() {
        return balance;
    }
    // Основной метод программы
    public static void main(String[] args) {
        // Вывод всех получившихся данных
        BankAccount account = new BankAccount("123456", 1000.0);
        System.out.println("Текущий баланс: " + account.getBalance());

        account.deposit(500.0);
        System.out.println("Текущий баланс: " + account.getBalance());

        account.withdraw(200.0);
        System.out.println("Текущий баланс: " + account.getBalance());

        account.withdraw(1500.0);
        System.out.println("Текущий баланс: " + account.getBalance());
    }
}
