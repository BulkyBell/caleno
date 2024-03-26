module com.caleno {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.caleno to javafx.fxml;
    exports com.caleno;
}