package com.example.bankmanagementsystem3;

import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import java.io.IOException;

public class LoginController {
    @FXML private TextField accountNumberField;
    @FXML private PasswordField passwordField;
    @FXML private Button loginButton;
    @FXML private Button registerButton;
    @FXML private Label statusLabel;
    @FXML private ProgressIndicator progressIndicator;
    @FXML private VBox formContainer;

    private BankManagementSystem app;
    private CredentialsStore credentialsStore;

    public void setApp(BankManagementSystem app) {
        this.app = app;
    }

    public void setCredentialsStore(CredentialsStore store) {
        this.credentialsStore = store;
    }

    @FXML
    private void initialize() {
        if (credentialsStore == null) {
            credentialsStore = new CredentialsStore(); // Fallback
        }
        // Move styles to CSS
    }

    @FXML
    private void login() {
        String accountNumber = accountNumberField.getText().trim();
        String password = passwordField.getText().trim();

        if (accountNumber.isEmpty() || password.isEmpty()) {
            statusLabel.setText("All fields are required.");
            return;
        }
        if (!accountNumber.matches("\\d{5,10}")) {
            statusLabel.setText("Account number must be 5-10 digits.");
            return;
        }

        progressIndicator.setVisible(true);
        loginButton.setDisable(true);
        registerButton.setDisable(true);

        Task<Void> loginTask = new Task<>() {
            @Override
            protected Void call() throws Exception {
                Thread.sleep(1000); // Simulate network delay
                return null;
            }
        };

        loginTask.setOnSucceeded(event -> {
            try {
                if (credentialsStore != null && credentialsStore.validateCredentials(accountNumber, password)) {
                    System.out.println("Attempting to show bank view for account: " + accountNumber);
                    app.showBankView(accountNumber);
                    System.out.println("Bank view loaded successfully");
                } else {
                    statusLabel.setText(credentialsStore == null ? "System error: Credentials store not initialized" : "Invalid credentials.");
                }
            } catch (Exception e) {
                System.err.println("Error navigating to bank view: " + e.getMessage());
                e.printStackTrace();
                statusLabel.setText("Error navigating to bank view: " + e.getMessage());
            } finally {
                progressIndicator.setVisible(false);
                loginButton.setDisable(false);
                registerButton.setDisable(false);
            }
        });

        loginTask.setOnFailed(event -> {
            System.err.println("Login failed: " + loginTask.getException().getMessage());
            statusLabel.setText("Login failed: " + loginTask.getException().getMessage());
            progressIndicator.setVisible(false);
            loginButton.setDisable(false);
            registerButton.setDisable(false);
        });

        new Thread(loginTask).start();
    }

    @FXML
    private void showRegisterView() {
        try {
            app.showRegisterView();
        } catch (IOException e) {
            System.err.println("Error navigating to register view: " + e.getMessage());
            e.printStackTrace();
            statusLabel.setText("Error navigating to register view: " + e.getMessage());
        }
    }
}