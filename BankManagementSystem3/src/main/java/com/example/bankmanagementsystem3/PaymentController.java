package com.example.bankmanagementsystem3;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class PaymentController {
    @FXML private TextField recipientAccountField;
    @FXML private TextField amountField;
    @FXML private Button makePaymentButton;
    @FXML private Button backButton;
    @FXML private Label statusLabel;

    private CredentialsStore credentialsStore;
    private BankManagementSystem app;
    private String accountNumber;

    public void setApp(BankManagementSystem app) {
        this.app = app;
    }

    public void setCredentialsStore(CredentialsStore store) {
        this.credentialsStore = store;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    @FXML
    private void initialize() {
        if (amountField != null) {
            amountField.textProperty().addListener((obs, oldValue, newValue) -> {
                if (!newValue.matches("\\d*(\\.\\d*)?")) {
                    amountField.setText(oldValue);
                }
            });
        }
    }

    @FXML
    private void makePayment() {
        if (credentialsStore == null || accountNumber == null || statusLabel == null) {
            statusLabel.setText("Error: Account not initialized.");
            return;
        }
        try {
            String recipientAccount = recipientAccountField.getText().trim();
            double amount = Double.parseDouble(amountField.getText().trim());

            if (recipientAccount.isEmpty()) {
                statusLabel.setText("Please enter a recipient account number.");
                return;
            }
            if (amount <= 0) {
                statusLabel.setText("Please enter a positive amount.");
                return;
            }
            if (!credentialsStore.accountExists(recipientAccount)) {
                statusLabel.setText("Recipient account does not exist.");
                return;
            }
            if (recipientAccount.equals(accountNumber)) {
                statusLabel.setText("Cannot send payment to your own account.");
                return;
            }

            if (credentialsStore.makePayment(accountNumber, recipientAccount, amount)) {
                credentialsStore.addTransaction(accountNumber, "Payment sent: " + amount + " to " + recipientAccount);
                credentialsStore.addTransaction(recipientAccount, "Payment received: " + amount + " from " + accountNumber);
                statusLabel.setText("Payment of " + amount + " sent successfully to " + recipientAccount);
                amountField.clear();
                recipientAccountField.clear();
            } else {
                statusLabel.setText("Payment failed: Insufficient funds.");
            }
        } catch (NumberFormatException e) {
            statusLabel.setText("Invalid amount.");
        }
    }

    @FXML
    private void backToBankView() {
        try {
            app.showBankView(accountNumber);
        } catch (Exception e) {
            statusLabel.setText("Error returning to bank view: " + e.getMessage());
            e.printStackTrace();
        }
    }
}