package com.example.bankmanagementsystem3;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import java.io.IOException;

public class RegisterController {
    @FXML private TextField accountNumberField;
    @FXML private PasswordField passwordField;

    private BankManagementSystem app;
    private CredentialsStore credentialsStore;

    public void setApp(BankManagementSystem app) {
        this.app = app;
    }

    public void setCredentialsStore(CredentialsStore store) {
        this.credentialsStore = store;
    }

    @FXML
    private void register() {
        String accountNumber = accountNumberField.getText().trim();
        String password = passwordField.getText().trim();

        if (accountNumber.isEmpty() || password.isEmpty()) {
            System.err.println("Registration failed: All fields are required.");
            return;
        }

        if (credentialsStore.registerAccount(accountNumber, password)) {
            try {
                app.showLoginView();
            } catch (IOException e) {
                System.err.println("Error returning to login view: " + e.getMessage());
                e.printStackTrace();
            }
        } else {
            System.err.println("Registration failed: Account may already exist.");
        }
    }

    @FXML
    private void backToLogin() {
        try {
            app.showLoginView();
        } catch (IOException e) {
            System.err.println("Error returning to login view: " + e.getMessage());
            e.printStackTrace();
        }
    }
}