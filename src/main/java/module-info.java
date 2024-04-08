module com.caleno {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.caleno to javafx.fxml;
    exports com.caleno;
}