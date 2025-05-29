package com.example.bankmanagementsystem3;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.TransactionHistoryController;

import java.io.IOException;

public class BankManagementSystem extends Application {

    private Stage primaryStage = null;
    private final CredentialsStore credentialsStore = new CredentialsStore();

    @Override
    public void start(Stage primaryStage) throws IOException {
        this.primaryStage = primaryStage;
        showLoginView();
    }

    public void showLoginView() throws IOException {
        try {
            System.out.println("[" + java.time.LocalDateTime.now() + "] Loading login.fxml from: " + getClass().getResource("/com/example/bankmanagementsystem3/login.fxml"));
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/bankmanagementsystem3/login.fxml"));
            Parent root = loader.load();
            LoginController controller = loader.getController();
            controller.setApp(this);
            controller.setCredentialsStore(credentialsStore);
            Scene scene = new Scene(root);
            primaryStage.setTitle("Bank Management System");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            System.err.println("[" + java.time.LocalDateTime.now() + "] Error loading login view: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

    public void showRegisterView() throws IOException {
        try {
            System.out.println("[" + java.time.LocalDateTime.now() + "] Loading register.fxml from: " + getClass().getResource("/com/example/bankmanagementsystem3/register.fxml"));
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/bankmanagementsystem3/register.fxml"));
            Parent root = loader.load();
            RegisterController controller = loader.getController();
            controller.setApp(this);
            controller.setCredentialsStore(credentialsStore);
            Scene scene = new Scene(root);
            primaryStage.setTitle("Register for Bank System");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            System.err.println("[" + java.time.LocalDateTime.now() + "] Error loading register view: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

    public void showBankView(String accountNumber) throws IOException {
        try {
            System.out.println("[" + java.time.LocalDateTime.now() + "] Loading bankview.fxml for account: " + accountNumber);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/bankmanagementsystem3/bankview.fxml"));
            Parent root = loader.load();
            BankController controller = loader.getController();
            controller.setApp(this);
            controller.setCredentialsStore(credentialsStore);
            controller.setAccountNumber(accountNumber);
            Scene scene = new Scene(root);
            primaryStage.setTitle("Bank Account View");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            System.err.println("[" + java.time.LocalDateTime.now() + "] Error loading bank view: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

    public void showTransactionHistory(String accountNumber) throws IOException {
        try {
            System.out.println("[" + java.time.LocalDateTime.now() + "] Loading transactionhistory.fxml for account: " + accountNumber);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/bankmanagementsystem3/transactionhistory.fxml"));
            Parent root = loader.load();
            TransactionHistoryController controller = loader.getController();
            if (controller == null) {
                System.err.println("[" + java.time.LocalDateTime.now() + "] TransactionHistoryController is null");
                throw new IOException("TransactionHistoryController not found in transactionhistory.fxml");
            }
            controller.setApp(this);
            controller.setCredentialsStore(credentialsStore);
            controller.setAccountNumber(accountNumber);
            Scene scene = new Scene(root);
            primaryStage.setTitle("Transaction History");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            System.err.println("[" + java.time.LocalDateTime.now() + "] Error loading transaction history view: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

    public void showPaymentView(String accountNumber) throws IOException {
        try {
            System.out.println("[" + java.time.LocalDateTime.now() + "] Loading payment.fxml from: " + getClass().getResource("/com/example/bankmanagementsystem3/payment.fxml"));
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/bankmanagementsystem3/payment.fxml"));
            if (loader.getLocation() == null) {
                throw new IOException("payment.fxml not found at /com/example/bankmanagementsystem3/payment.fxml");
            }
            Parent root = loader.load();
            PaymentController controller = loader.getController();
            if (controller == null) {
                throw new IOException("PaymentController not found in payment.fxml");
            }
            controller.setApp(this);
            controller.setCredentialsStore(credentialsStore);
            controller.setAccountNumber(accountNumber);
            Scene scene = new Scene(root);
            primaryStage.setTitle("Make a Payment");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            System.err.println("[" + java.time.LocalDateTime.now() + "] Error loading payment view: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

    public void showAccountView(String accountNumber) throws IOException {
        try {
            System.out.println("[" + java.time.LocalDateTime.now() + "] Loading accountview.fxml from: " + getClass().getResource("/com/example/bankmanagementsystem3/accountview.fxml"));
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/bankmanagementsystem3/accountview.fxml"));
            if (loader.getLocation() == null) {
                throw new IOException("accountview.fxml not found at /com/example/bankmanagementsystem3/accountview.fxml");
            }
            Parent root = loader.load();
            AccountController controller = loader.getController();
            if (controller == null) {
                throw new IOException("AccountController not found in accountview.fxml");
            }
            controller.setApp(this);
            controller.setCredentialsStore(credentialsStore);
            controller.setAccountNumber(accountNumber);
            Scene scene = new Scene(root);
            primaryStage.setTitle("Account Details");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            System.err.println("[" + java.time.LocalDateTime.now() + "] Error loading account view: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}