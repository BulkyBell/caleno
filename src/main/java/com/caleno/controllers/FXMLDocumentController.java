package com.caleno.controllers;

import com.caleno.model.database;
import com.caleno.model.getData;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * @Author BulkyBell / Sergio Colomer Rodríguez
 */
public class FXMLDocumentController implements Initializable {
    @FXML
    private AnchorPane main_form;
    @FXML
    private TextField username;
    @FXML
    private PasswordField password;
    @FXML
    private Button loginBtn;
    @FXML
    private Button close;

    private double x = 0;
    private double y = 0;
    private Connection connect;
    private PreparedStatement prepare;
    private ResultSet result;

    public void loginAdmin(){
        String sql = "SELECT * FROM admin WHERE username = ? and password = ?";
        connect = database.connectDb();
        try{
            prepare = connect.prepareStatement(sql);
            prepare.setString(1, username.getText());
            prepare.setString(2, password.getText());
            result = prepare.executeQuery();

            Alert alert;
            if(username.getText().isEmpty() || password.getText().isEmpty()){
                // Displays an error message if any fields is empty
                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please, fill all the blank fields.");
                alert.showAndWait();
            }else{
                if(result.next()){
                    // If query returns a result, login is successful
                    getData.username = username.getText();
                    alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Logged Successfully.");
                    alert.showAndWait();

                    // Scene hides and moves onto the next screen
                    loginBtn.getScene().getWindow().hide();
                    Parent root = FXMLLoader.load(getClass().getResource("/com/caleno/dashboard.fxml"));
                    Stage stage = new Stage();
                    Scene scene = new Scene(root);

                    root.setOnMousePressed((MouseEvent event) ->{
                        x = event.getSceneX();
                        y = event.getSceneY();
                    });

                    root.setOnMouseDragged((MouseEvent event) ->{
                        stage.setX(event.getScreenX() - x);
                        stage.setY(event.getScreenY() - y);
                    });

                    stage.initStyle(StageStyle.TRANSPARENT);
                    stage.setScene(scene);
                    stage.show();
                }else{
                    // Displays an error message if username or password is incorrect
                    alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Wrong Username/Password, please, try again.");
                    alert.showAndWait();
                }
            }
        }catch(Exception e){e.printStackTrace();}
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        close.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                close();
            }
        });
    }
    public void close(){
        Stage stage = (Stage) close.getScene().getWindow();
        stage.close();
    }
}
