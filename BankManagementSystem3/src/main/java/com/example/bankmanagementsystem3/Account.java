package com.example.bankmanagementsystem3;

public class Account {
    private String accountNumber;
    private String name;
    private String password;
    private double balance;

    public Account(String accountNumber, String name, String password) {
        this.accountNumber = accountNumber;
        this.name = name;
        this.password = password;
        this.balance = 0.0;
    }

    public String getAccountNumber() { return accountNumber; }
    public String getName() { return name; }
    public String getPassword() { return password; }
    public double getBalance() { return balance; }
    public void setBalance(double balance) { this.balance = balance; }
}