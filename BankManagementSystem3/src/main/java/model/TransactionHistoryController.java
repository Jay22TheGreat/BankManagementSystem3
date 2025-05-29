package model;

import com.example.bankmanagementsystem3.BankManagementSystem;
import com.example.bankmanagementsystem3.CredentialsStore;
import com.example.bankmanagementsystem3.Transaction;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import java.io.IOException;
import java.util.List;

public class TransactionHistoryController {
    @FXML private Label transactionListLabel;

    private BankManagementSystem app;
    private CredentialsStore credentialsStore;
    private String accountNumber;

    public void setApp(BankManagementSystem app) {
        this.app = app;
        System.out.println("[" + java.time.LocalDateTime.now() + "] App set in TransactionHistoryController: " + (app != null ? "Initialized" : "Null"));
    }

    public void setCredentialsStore(CredentialsStore credentialsStore) {
        this.credentialsStore = credentialsStore;
        System.out.println("[" + java.time.LocalDateTime.now() + "] CredentialsStore set in TransactionHistoryController: " + (credentialsStore != null ? "Initialized" : "Null"));
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
        System.out.println("[" + java.time.LocalDateTime.now() + "] Account number set in TransactionHistoryController: " + accountNumber);
        updateTransactionDisplay();
    }

    private void updateTransactionDisplay() {
        if (transactionListLabel == null) {
            System.err.println("[" + java.time.LocalDateTime.now() + "] transactionListLabel is null in TransactionHistoryController");
            return;
        }
        if (credentialsStore == null) {
            System.err.println("[" + java.time.LocalDateTime.now() + "] credentialsStore is null in TransactionHistoryController");
            transactionListLabel.setText("Error: Credentials store not initialized.");
            return;
        }
        if (accountNumber == null) {
            System.err.println("[" + java.time.LocalDateTime.now() + "] accountNumber is null in TransactionHistoryController");
            transactionListLabel.setText("Error: Account not set.");
            return;
        }
        List<Transaction> transactions = credentialsStore.getTransactionHistory(accountNumber);
        if (transactions.isEmpty()) {
            transactionListLabel.setText("No transactions found.");
            System.out.println("[" + java.time.LocalDateTime.now() + "] No transactions found for account: " + accountNumber);
            // Debug: Dump entire transaction history
            System.out.println("[" + java.time.LocalDateTime.now() + "] Full transactionHistory: " + credentialsStore.getFullTransactionHistoryDebug());
        } else {
            StringBuilder transactionText = new StringBuilder();
            for (int i = 0; i < transactions.size(); i++) {
                transactionText.append("Transaction ").append(i + 1).append(": ").append(transactions.get(i).toString()).append("\n");
            }
            transactionListLabel.setText(transactionText.toString());
            System.out.println("[" + java.time.LocalDateTime.now() + "] Transactions displayed for " + accountNumber + ": " + transactionText.toString());
        }
    }

    @FXML
    private void backToBankView(ActionEvent event) {
        try {
            app.showBankView(accountNumber);
        } catch (IOException e) {
            System.err.println("[" + java.time.LocalDateTime.now() + "] Error navigating to bank view: " + e.getMessage());
            e.printStackTrace();
        }
    }
}