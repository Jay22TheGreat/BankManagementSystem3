package com.example.bankmanagementsystem3;

import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.List;

public class CredentialsStore {
    private Map<String, String> credentials = new HashMap<>();
    private Map<String, List<Transaction>> transactionHistory = new HashMap<>();
    private Map<String, Double> balances = new HashMap<>();

    public CredentialsStore() {
        // Initialize with a default account for testing
        credentials.put("12345", "password");
        transactionHistory.put("12345", new ArrayList<>());
        balances.put("12345", 1000.00); // Initial balance for default account
    }

    public boolean validateCredentials(String accountNumber, String password) {
        String storedPassword = credentials.get(accountNumber);
        return storedPassword != null && storedPassword.equals(password);
    }

    public double getBalance(String accountNumber) {
        return balances.getOrDefault(accountNumber, 0.00);
    }

    public boolean updateBalance(String accountNumber, double amount) {
        if (credentials.containsKey(accountNumber)) {
            double currentBalance = balances.getOrDefault(accountNumber, 0.00);
            double newBalance = currentBalance + amount;
            if (newBalance < 0) {
                System.out.println("[" + java.time.LocalDateTime.now() + "] Failed to update balance for " + accountNumber + ": Insufficient funds (current: " + currentBalance + ", attempted change: " + amount + ")");
                return false;
            }
            balances.put(accountNumber, newBalance);
            System.out.println("[" + java.time.LocalDateTime.now() + "] Balance updated for " + accountNumber + ": " + currentBalance + " -> " + newBalance);
            return true;
        }
        System.out.println("[" + java.time.LocalDateTime.now() + "] Failed to update balance for " + accountNumber + ": Account does not exist");
        return false;
    }

    public void addTransaction(String accountNumber, String transaction) {
        if (credentials.containsKey(accountNumber)) {
            Transaction newTransaction = new Transaction(transaction);
            transactionHistory.computeIfAbsent(accountNumber, k -> new ArrayList<>()).add(newTransaction);
            System.out.println("[" + java.time.LocalDateTime.now() + "] Transaction added for " + accountNumber + ": " + newTransaction);
        } else {
            System.out.println("[" + java.time.LocalDateTime.now() + "] Failed to add transaction for " + accountNumber + ": Account does not exist");
        }
    }

    public boolean updatePassword(String accountNumber, String newPassword) {
        if (credentials.containsKey(accountNumber) && newPassword != null && !newPassword.isEmpty()) {
            credentials.put(accountNumber, newPassword);
            System.out.println("[" + java.time.LocalDateTime.now() + "] Password updated for account " + accountNumber + " to " + newPassword);
            return true;
        }
        return false;
    }

    public boolean accountExists(String accountNumber) {
        return credentials.containsKey(accountNumber);
    }

    public boolean makePayment(String senderAccount, String recipientAccount, double amount) {
        if (senderAccount == null || recipientAccount == null || amount <= 0) {
            System.out.println("[" + java.time.LocalDateTime.now() + "] Payment failed: Invalid input (sender: " + senderAccount + ", recipient: " + recipientAccount + ", amount: " + amount + ")");
            return false;
        }
        double senderBalance = getBalance(senderAccount);
        if (senderBalance < amount) {
            System.out.println("[" + java.time.LocalDateTime.now() + "] Payment failed: Insufficient funds for " + senderAccount + " (balance: " + senderBalance + ", attempted: " + amount + ")");
            return false;
        }
        boolean senderUpdated = updateBalance(senderAccount, -amount);
        boolean recipientUpdated = updateBalance(recipientAccount, amount);
        if (senderUpdated && recipientUpdated) {
            addTransaction(senderAccount, "Payment of " + amount + " to " + recipientAccount);
            addTransaction(recipientAccount, "Received payment of " + amount + " from " + senderAccount);
            System.out.println("[" + java.time.LocalDateTime.now() + "] Payment successful: " + amount + " from " + senderAccount + " to " + recipientAccount);
            return true;
        }
        System.out.println("[" + java.time.LocalDateTime.now() + "] Payment failed: Balance update failed (senderUpdated: " + senderUpdated + ", recipientUpdated: " + recipientUpdated + ")");
        return false;
    }

    public boolean registerAccount(String accountNumber, String password) {
        if (accountNumber == null || password == null || accountNumber.isEmpty() || password.isEmpty()) {
            return false;
        }
        if (credentials.containsKey(accountNumber)) {
            return false;
        }
        credentials.put(accountNumber, password);
        transactionHistory.put(accountNumber, new ArrayList<>());
        balances.put(accountNumber, 0.00); // Initialize new account with 0 balance
        System.out.println("[" + java.time.LocalDateTime.now() + "] Registered new account: " + accountNumber + " with password: " + password + " and initial balance: 0.00");
        return true;
    }

    public List<Transaction> getTransactionHistory(String accountNumber) {
        List<Transaction> transactions = transactionHistory.getOrDefault(accountNumber, new ArrayList<>());
        System.out.println("[" + java.time.LocalDateTime.now() + "] Retrieved transactions for " + accountNumber + ": " + transactions);
        return transactions;
    }

    // Debug method to dump entire transaction history
    public String getFullTransactionHistoryDebug() {
        StringBuilder debugOutput = new StringBuilder("Full Transaction History:\n");
        for (Map.Entry<String, List<Transaction>> entry : transactionHistory.entrySet()) {
            debugOutput.append("Account: ").append(entry.getKey()).append("\n");
            for (Transaction t : entry.getValue()) {
                debugOutput.append("  - ").append(t.toString()).append("\n");
            }
        }
        return debugOutput.toString();
    }
}