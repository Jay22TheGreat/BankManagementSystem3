package com.example.bankmanagementsystem3;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class BankController {
    @FXML private TextField accountNumberField;
    @FXML private Label balanceLabel;
    @FXML private TextField amountField;
    @FXML private Label statusLabel;
    @FXML private ComboBox<String> transactionTypeCombo;
    @FXML private Label transactionSummaryLabel;

    private BankManagementSystem app;
    private CredentialsStore credentialsStore;
    private String accountNumber;

    public void setApp(BankManagementSystem app) {
        this.app = app;
    }

    public void setCredentialsStore(CredentialsStore credentialsStore) {
        this.credentialsStore = credentialsStore;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
        if (accountNumberField != null) {
            accountNumberField.setText(accountNumber);
        }
        updateAccountDetails();
    }

    private void updateAccountDetails() {
        if (credentialsStore == null || accountNumber == null) {
            if (statusLabel != null) {
                statusLabel.setText("Error: Unable to load account details.");
            }
            return;
        }

        // Fetch balance
        double balance = credentialsStore.getBalance(accountNumber);
        if (balanceLabel != null) {
            balanceLabel.setText(String.format("Balance: $%.2f", balance));
        }

        // Update transaction summary (placeholder)
        if (transactionSummaryLabel != null) {
            transactionSummaryLabel.setText("Last transaction: Deposit $100 on " + java.time.LocalDate.now());
        }
    }

    @FXML
    private void showAccountView() throws Exception {
        if (app != null) app.showAccountView(accountNumber);
    }

    @FXML
    private void showTransactionHistory() throws Exception {
        if (app != null) app.showTransactionHistory(accountNumber);
    }

    @FXML
    private void showPaymentView() throws Exception {
        if (app != null) app.showPaymentView(accountNumber);
    }

    @FXML
    public void deposit(ActionEvent actionEvent) {
        handleTransaction();
    }

    @FXML
    public void withdraw(ActionEvent actionEvent) {
        handleTransaction();
    }

    private void handleTransaction() {
        if (credentialsStore == null || accountNumber == null) {
            statusLabel.setText("Error: Account not initialized.");
            System.err.println("Account not initialized: credentialsStore or accountNumber is null");
            return;
        }

        // Check if a transaction type is selected
        String selectedType = transactionTypeCombo.getValue();
        if (selectedType == null) {
            statusLabel.setText("Error: Please select a transaction type.");
            System.err.println("No transaction type selected in ComboBox");
            return;
        }

        // Validate amount input
        String amountText = amountField.getText().trim();
        if (amountText.isEmpty()) {
            statusLabel.setText("Error: Please enter an amount.");
            System.err.println("Amount field is empty");
            return;
        }

        double amount;
        try {
            amount = Double.parseDouble(amountText);
            if (amount <= 0) {
                statusLabel.setText("Error: Amount must be greater than zero.");
                System.err.println("Invalid amount: " + amount + " (must be greater than zero)");
                return;
            }
        } catch (NumberFormatException e) {
            statusLabel.setText("Error: Invalid amount format.");
            System.err.println("Invalid amount format: " + amountText);
            return;
        }

        // Perform the transaction
        if (selectedType.equals("Deposit")) {
            boolean success = credentialsStore.updateBalance(accountNumber, amount);
            if (success) {
                credentialsStore.addTransaction(accountNumber, String.format("Deposit of $%.2f", amount));
                statusLabel.setText(String.format("Success: Deposited $%.2f", amount));
                System.out.println("Deposit successful: " + amount);
            } else {
                statusLabel.setText("Error: Deposit failed.");
                System.err.println("Deposit failed for account: " + accountNumber);
                return;
            }
        } else if (selectedType.equals("Withdraw")) {
            boolean success = credentialsStore.updateBalance(accountNumber, -amount);
            if (success) {
                credentialsStore.addTransaction(accountNumber, String.format("Withdrawal of $%.2f", amount));
                statusLabel.setText(String.format("Success: Withdrew $%.2f", amount));
                System.out.println("Withdrawal successful: " + amount);
            } else {
                statusLabel.setText("Error: Insufficient funds.");
                System.err.println("Withdrawal failed: Insufficient funds for account: " + accountNumber);
                return;
            }
        } else {
            statusLabel.setText("Error: Invalid transaction type.");
            System.err.println("Invalid transaction type: " + selectedType);
            return;
        }

        // Update balance display
        updateAccountDetails();

        // Clear the amount field
        amountField.clear();

        // Update transaction summary
        if (transactionSummaryLabel != null) {
            transactionSummaryLabel.setText(String.format("Last transaction: %s $%.2f on %s",
                    selectedType, amount, java.time.LocalDate.now()));
        }
    }

    @FXML
    public void checkBalance(ActionEvent actionEvent) {
        updateAccountDetails();
    }

    @FXML
    public void logout(ActionEvent actionEvent) throws Exception {
        if (app != null) app.showLoginView();
    }
}