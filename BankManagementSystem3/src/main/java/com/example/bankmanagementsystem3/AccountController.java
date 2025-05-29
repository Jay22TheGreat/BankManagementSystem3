package com.example.bankmanagementsystem3;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class AccountController {

    @FXML private TextField accountNumberField;
    @FXML private Label balanceLabel;
    @FXML private PasswordField newPasswordField;
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
        if (accountNumberField != null) {
            accountNumberField.setText(accountNumber);
        }
        updateBalanceDisplay();
    }

    private void updateBalanceDisplay() {
        if (balanceLabel == null || credentialsStore == null || accountNumber == null) {
            if (statusLabel != null) {
                statusLabel.setText("Error: Account not initialized.");
            }
            return;
        }
        double balance = credentialsStore.getBalance(accountNumber);
        balanceLabel.setText(String.format("Balance: %.2f", balance));
    }

    @FXML
    private void updatePassword() {
        if (credentialsStore == null || accountNumber == null || statusLabel == null) {
            statusLabel.setText("Error: Account not initialized.");
            return;
        }
        String newPassword = newPasswordField.getText().trim();
        if (newPassword.isEmpty()) {
            statusLabel.setText("Please enter a new password.");
            return;
        }
        if (credentialsStore.updatePassword(accountNumber, newPassword)) {
            statusLabel.setText("Password updated successfully.");
            newPasswordField.clear();
        } else {
            statusLabel.setText("Failed to update password.");
        }
    }

    @FXML
    private void backToBankView() {
        try {
            app.showBankView(accountNumber);
        } catch (Exception e) {
            if (statusLabel != null) {
                statusLabel.setText("Error returning to bank view: " + e.getMessage());
            }
            e.printStackTrace();
        }
    }
}