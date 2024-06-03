module com.caleno {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.caleno to javafx.fxml;
    exports com.caleno.controllers;
    opens com.caleno.controllers to javafx.fxml;
    exports com.caleno.model;
    opens com.caleno.model to javafx.fxml;
    exports com.caleno.Application;
    opens com.caleno.Application to javafx.fxml;
}