module com.example.bankmanagementsystem3 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.bankmanagementsystem3 to javafx.fxml;
    exports com.example.bankmanagementsystem3;
    exports model;
    opens model to javafx.fxml;
}