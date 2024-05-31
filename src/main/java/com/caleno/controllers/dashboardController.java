package com.caleno.controllers;

import java.io.File;
import java.net.URL;
import java.sql.*;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import com.caleno.model.database;
import com.caleno.Employee;
import com.caleno.model.getData;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


public class dashboardController implements Initializable {

    @FXML
    private AnchorPane main_form;

    @FXML
    private Button close;

    @FXML
    private Button minimize;

    @FXML
    private Label username;

    @FXML
    private Button home_btn;

    @FXML
    private Button addEmployee_btn;

    @FXML
    private Button salary_btn;

    @FXML
    private Button logout;

    @FXML
    private AnchorPane home_form;

    @FXML
    private Label home_totalEmployees;

    @FXML
    private Label home_totalPresents;

    @FXML
    private Label home_totalInactive;

    @FXML
    private BarChart<?, ?> home_chart;

    @FXML
    private AnchorPane addEmployee_form;

    @FXML
    private TableView<Employee> addEmployee_tableView;

    @FXML
    private TableColumn<Employee, String> addEmployee_col_employee_ID;

    @FXML
    private TableColumn<Employee, String> addEmployee_col_firstName;

    @FXML
    private TableColumn<Employee, String> addEmployee_col_lastName;

    @FXML
    private TableColumn<Employee, String> addEmployee_col_gender;

    @FXML
    private TableColumn<Employee, String> addEmployee_col_phoneNum;

    @FXML
    private TableColumn<Employee, String> addEmployee_col_position;

    @FXML
    private TableColumn<Employee, String> addEmployee_col_date;

    @FXML
    private TextField addEmployee_search;

    @FXML
    private TextField addEmployee_employee_ID;

    @FXML
    private TextField addEmployee_firstName;

    @FXML
    private TextField addEmployee_lastName;

    @FXML
    private ComboBox<String> addEmployee_gender;

    @FXML
    private TextField addEmployee_phoneNum;

    @FXML
    private ComboBox<String> addEmployee_position;

    @FXML
    private ImageView addEmployee_image;

    @FXML
    private Button addEmployee_importBtn;

    @FXML
    private Button addEmployee_addBtn;

    @FXML
    private Button addEmployee_updateBtn;

    @FXML
    private Button addEmployee_deleteBtn;

    @FXML
    private Button addEmployee_clearBtn;

    @FXML
    private AnchorPane salary_form;

    @FXML
    private Label salary_employee_ID;
    @FXML
    private Label salary_firstName;
    @FXML
    private Label salary_lastName;
    @FXML
    private Label salary_position;
    @FXML
    private TextField salary_salary;
    @FXML
    private Button salary_updateBtn;
    @FXML
    private Button salary_clearBtn;
    @FXML
    private CheckBox salary_active;

    @FXML
    private TableView<Employee> salary_tableView;
    @FXML
    private TableColumn<Employee, String> salary_col_employee_ID;
    @FXML
    private TableColumn<Employee, String> salary_col_firstName;
    @FXML
    private TableColumn<Employee, String> salary_col_lastName;
    @FXML
    private TableColumn<Employee, String> salary_col_position;
    @FXML
    private TableColumn<Employee, String> salary_col_salary;
    @FXML
    private TableColumn<Employee, String> salary_col_active;
    @FXML
    private AnchorPane button_total;
    @FXML
    private AnchorPane button_active;
    @FXML
    private AnchorPane button_inactive;

    private ObservableList<String> positionList = FXCollections.observableArrayList("Position 1", "Position 2", "Position 3", "Position 4");
    private ObservableList<String> genderOptions = FXCollections.observableArrayList("Male", "Female", "Other");

    private Connection connect;
    private Statement statement;
    private PreparedStatement prepare;
    private ResultSet result;

    private Image image;

    public void homeTotalEmployees() {

        String sql = "SELECT COUNT(id) FROM employeedata";

        connect = database.connectDb();
        int countData = 0;
        try {

            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            while (result.next()) {
                countData = result.getInt("COUNT(id)");
            }

            home_totalEmployees.setText(String.valueOf(countData));

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void homeEmployeeTotalPresent() {

        String sql = "SELECT COUNT(id) FROM employee_info WHERE active = 1 ";

        connect = database.connectDb();
        int countData = 0;
        try {
            statement = connect.createStatement();
            result = statement.executeQuery(sql);

            while (result.next()) {
                countData = result.getInt("COUNT(id)");
            }
            home_totalPresents.setText(String.valueOf(countData));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void homeTotalInactive() {

        String sql = "SELECT COUNT(employee_id) FROM employee_info WHERE active = 0";

        connect = database.connectDb();
        int countData = 0;
        try {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            while (result.next()) {
                countData = result.getInt("COUNT(employee_id)");
            }
            home_totalInactive.setText(String.valueOf(countData));

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void homeChart() {
        home_chart.getData().clear();
        String sql = "SELECT date, COUNT(id) FROM employeedata GROUP BY date ORDER BY TIMESTAMP(date) ASC LIMIT 7";
        connect = database.connectDb();
        try {
            XYChart.Series chart = new XYChart.Series();

            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            while (result.next()) {
                chart.getData().add(new XYChart.Data(result.getString(1), result.getInt(2)));
            }

            home_chart.getData().add(chart);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateHomeChart(String query) {
        home_chart.getData().clear();
        connect = database.connectDb();
        try {
            XYChart.Series chart = new XYChart.Series();

            prepare = connect.prepareStatement(query);
            result = prepare.executeQuery();

            while (result.next()) {
                chart.getData().add(new XYChart.Data<>(result.getString(1), result.getInt(2)));
            }
            home_chart.getData().add(chart);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (result != null) result.close();
                if (prepare != null) prepare.close();
                if (connect != null) connect.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void handleTotalEmployeesClick(MouseEvent event) {
        updateHomeChart("SELECT date, COUNT(id) FROM employeedata GROUP BY date ORDER BY TIMESTAMP(date) ASC LIMIT 7");
    }

    @FXML
    private void handleActiveEmployeesClick(MouseEvent event) {
        updateHomeChart("SELECT ed.date, COUNT(ed.id) " +
                "FROM employeedata ed " +
                "JOIN employee_info ei ON ed.employee_id = ei.employee_id " +
                "WHERE ei.active = 1 " +
                "GROUP BY ed.date " +
                "ORDER BY TIMESTAMP(ed.date) ASC " +
                "LIMIT 7");
    }

    @FXML
    private void handleInactiveEmployeesClick(MouseEvent event) {
        updateHomeChart("SELECT ed.date, COUNT(ed.id) " +
                "FROM employeedata ed " +
                "JOIN employee_info ei ON ed.employee_id = ei.employee_id " +
                "WHERE ei.active = 0 " +
                "GROUP BY ed.date " +
                "ORDER BY TIMESTAMP(ed.date) ASC " +
                "LIMIT 7");
    }




    public void addEmployeeAdd() {
        String sql = "INSERT INTO employeedata (employee_id, firstName, lastName, gender, phoneNum, position, image, date) "
                + "VALUES(?,?,?,?,?,?,?,?)";

        connect = database.connectDb();

        try {
            Alert alert;
            if (addEmployee_firstName.getText().isEmpty()
                    || addEmployee_lastName.getText().isEmpty()
                    || addEmployee_gender.getSelectionModel().getSelectedItem() == null
                    || addEmployee_phoneNum.getText().isEmpty()
                    || addEmployee_position.getSelectionModel().getSelectedItem() == null
                    || addEmployee_image.getImage() == null || getData.path.isEmpty()) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all blank fields");
                alert.showAndWait();
            } else {
                // Check if employee ID already exists
                String checkId = "SELECT employee_id FROM employeedata WHERE employee_id = ?";
                prepare = connect.prepareStatement(checkId);
                prepare.setString(1, addEmployee_employee_ID.getText());
                result = prepare.executeQuery();

                if (result.next()) {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Employee ID already exists!");
                    alert.showAndWait();
                } else {
                    // Insert into employeedata
                    prepare = connect.prepareStatement(sql);
                    prepare.setString(1, addEmployee_employee_ID.getText());
                    prepare.setString(2, addEmployee_firstName.getText());
                    prepare.setString(3, addEmployee_lastName.getText());
                    prepare.setString(4, (String) addEmployee_gender.getSelectionModel().getSelectedItem());
                    prepare.setString(5, addEmployee_phoneNum.getText());
                    prepare.setString(6, (String) addEmployee_position.getSelectionModel().getSelectedItem());

                    String uri = getData.path;
                    uri = uri.replace("\\", "\\\\");

                    prepare.setString(7, uri);

                    Date date = new Date();
                    java.sql.Date sqlDate = new java.sql.Date(date.getTime());
                    prepare.setDate(8, sqlDate);

                    prepare.executeUpdate();

                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Added!");
                    alert.showAndWait();

                    // Update the table view
                    addEmployeeShowListData();
                    addEmployeeReset();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void addEmployeeUpdate() {
        String uri = getData.path;
        if (uri != null) {
            uri = uri.replace("\\", "\\\\");
        }

        Date date = new Date();
        java.sql.Date sqlDate = new java.sql.Date(date.getTime());

        String employeeID = addEmployee_employee_ID.getText();

        if (employeeID.isEmpty()
                || addEmployee_firstName.getText().isEmpty()
                || addEmployee_lastName.getText().isEmpty()
                || addEmployee_gender.getSelectionModel().getSelectedItem() == null
                || addEmployee_phoneNum.getText().isEmpty()
                || addEmployee_position.getSelectionModel().getSelectedItem() == null
                || addEmployee_image.getImage() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please fill all blank fields");
            alert.showAndWait();
            return;
        }

        connect = database.connectDb();
        try {
            String checkEmployeeSQL = "SELECT COUNT(*) FROM employeedata WHERE employee_id = ?";
            prepare = connect.prepareStatement(checkEmployeeSQL);
            prepare.setString(1, employeeID);
            result = prepare.executeQuery();

            if (result.next() && result.getInt(1) == 0) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Employee ID: " + employeeID + " does not exist");
                alert.showAndWait();
                return;
            }

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Message");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure you want to UPDATE Employee ID: " + employeeID + "?");
            Optional<ButtonType> option = alert.showAndWait();

            if (option.isPresent() && option.get().equals(ButtonType.OK)) {
                String sql = "UPDATE employeedata SET firstName = ?, lastName = ?, gender = ?, phoneNum = ?, position = ?, image = ?, date = ? WHERE employee_id = ?";
                prepare = connect.prepareStatement(sql);
                prepare.setString(1, addEmployee_firstName.getText());
                prepare.setString(2, addEmployee_lastName.getText());
                prepare.setString(3, addEmployee_gender.getSelectionModel().getSelectedItem());
                prepare.setString(4, addEmployee_phoneNum.getText());
                prepare.setString(5, addEmployee_position.getSelectionModel().getSelectedItem());
                prepare.setString(6, uri != null ? uri : "");
                prepare.setDate(7, sqlDate);
                prepare.setString(8, employeeID);
                prepare.executeUpdate();

                double salary = 0;
                String checkData = "SELECT * FROM employee_info WHERE employee_id = ?";
                prepare = connect.prepareStatement(checkData);
                prepare.setString(1, employeeID);
                result = prepare.executeQuery();

                if (result.next()) {
                    salary = result.getDouble("salary");
                }

                String updateInfo = "UPDATE employee_info SET firstName = ?, lastName = ?, position = ? WHERE employee_id = ?";
                prepare = connect.prepareStatement(updateInfo);
                prepare.setString(1, addEmployee_firstName.getText());
                prepare.setString(2, addEmployee_lastName.getText());
                prepare.setString(3, addEmployee_position.getSelectionModel().getSelectedItem());
                prepare.setString(4, employeeID);
                prepare.executeUpdate();

                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Message");
                alert.setHeaderText(null);
                alert.setContentText("Successfully Updated!");
                alert.showAndWait();

                addEmployeeShowListData();
                addEmployeeReset();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addEmployeeDelete() {
        String sql = "DELETE FROM employeedata WHERE employee_id = '"
                + addEmployee_employee_ID.getText() + "'";
        connect = database.connectDb();
        try {
            Alert alert;
            if (addEmployee_employee_ID.getText().isEmpty()
                    || addEmployee_firstName.getText().isEmpty()
                    || addEmployee_lastName.getText().isEmpty()
                    || addEmployee_gender.getSelectionModel().getSelectedItem() == null
                    || addEmployee_phoneNum.getText().isEmpty()
                    || addEmployee_position.getSelectionModel().getSelectedItem() == null
                    || addEmployee_image.getImage() == null) {
                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all blank fields");
                System.out.println(getData.path);
                alert.showAndWait();
            } else {
                alert = new Alert(AlertType.CONFIRMATION);
                alert.setTitle("Cofirmation Message");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure you want to DELETE Employee ID: " + addEmployee_employee_ID.getText() + "?");
                Optional<ButtonType> option = alert.showAndWait();

                if (option.get().equals(ButtonType.OK)) {
                    statement = connect.createStatement();
                    statement.executeUpdate(sql);

                    String deleteInfo = "DELETE FROM employee_info WHERE employee_id = '"
                            + addEmployee_employee_ID.getText() + "'";

                    prepare = connect.prepareStatement(deleteInfo);
                    prepare.executeUpdate();

                    alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Deleted!");
                    alert.showAndWait();

                    addEmployeeShowListData();
                    addEmployeeReset();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void addEmployeeReset() {
        addEmployee_employee_ID.setText("");
        addEmployee_firstName.setText("");
        addEmployee_lastName.setText("");
        addEmployee_gender.getSelectionModel().clearSelection();
        addEmployee_position.getSelectionModel().clearSelection();
        addEmployee_phoneNum.setText("");
        addEmployee_image.setImage(null);
        getData.path = "";
    }

    public void addEmployeeInsertImage() {
        FileChooser open = new FileChooser();
        File file = open.showOpenDialog(main_form.getScene().getWindow());
        Alert alert;
        if (file != null) {
            getData.path = file.getAbsolutePath();
            image = new Image(file.toURI().toString(), 123, 150, false, true);
            addEmployee_image.setImage(image);
        } else {
            alert = new Alert(AlertType.WARNING);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please, select a valid image");
        }
    }


    public void addEmployeePositionList() {

        if (positionList != null && !positionList.isEmpty()) {
            Platform.runLater(() -> {
                addEmployee_position.setItems(positionList);
            });
        }
    }


    public void addEmployeeGenderList() {

        if (genderOptions != null && !genderOptions.isEmpty()) {
            Platform.runLater(() -> {
                addEmployee_gender.setItems(genderOptions);
            });
        }
    }

    public void addEmployeeSearch() {
        FilteredList<Employee> filter = new FilteredList<>(addEmployeeList, e -> true);
        addEmployee_search.textProperty().addListener((Observable, oldValue, newValue) -> {

            filter.setPredicate(predicateEmployeeData -> {

                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String searchKey = newValue.toLowerCase();
                if (predicateEmployeeData.getEmployee_Id().toString().contains(searchKey)) {
                    return true;
                } else if (predicateEmployeeData.getFirstName().toLowerCase().contains(searchKey)) {
                    return true;
                } else if (predicateEmployeeData.getLastName().toLowerCase().contains(searchKey)) {
                    return true;
                } else if (predicateEmployeeData.getGender().toLowerCase().contains(searchKey)) {
                    return true;
                } else if (predicateEmployeeData.getPhoneNum().toLowerCase().contains(searchKey)) {
                    return true;
                } else if (predicateEmployeeData.getPosition().toLowerCase().contains(searchKey)) {
                    return true;
                } else if (predicateEmployeeData.getDate().toString().contains(searchKey)) {
                    return true;
                } else {
                    return false;
                }
            });
        });
        SortedList<Employee> sortList = new SortedList<>(filter);

        sortList.comparatorProperty().bind(addEmployee_tableView.comparatorProperty());
        addEmployee_tableView.setItems(sortList);
    }

    public ObservableList<Employee> addEmployeeListData() {
        ObservableList<Employee> listData = FXCollections.observableArrayList();
        String sql = "SELECT * FROM employeedata";

        connect = database.connectDb();

        try {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();
            Employee employeeD;

            while (result.next()) {
                employeeD = new Employee(result.getInt("employee_id"),
                        result.getString("firstName"),
                        result.getString("lastName"),
                        result.getString("gender"),
                        result.getString("phoneNum"),
                        result.getString("position"),
                        result.getString("image"),
                        result.getDate("date"));
                listData.add(employeeD);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return listData;
    }

    private ObservableList<Employee> addEmployeeList;

    public void addEmployeeShowListData() {
        addEmployeeList = addEmployeeListData();

        addEmployee_col_employee_ID.setCellValueFactory(new PropertyValueFactory<>("employee_Id"));
        addEmployee_col_firstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        addEmployee_col_lastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        addEmployee_col_gender.setCellValueFactory(new PropertyValueFactory<>("gender"));
        addEmployee_col_phoneNum.setCellValueFactory(new PropertyValueFactory<>("phoneNum"));
        addEmployee_col_position.setCellValueFactory(new PropertyValueFactory<>("position"));
        addEmployee_col_date.setCellValueFactory(new PropertyValueFactory<>("date"));

        addEmployee_tableView.setItems(addEmployeeList);
    }

    public void salaryUpdate() {
        String sql = "UPDATE employee_info SET salary = '" + salary_salary.getText()
                + "', active = '" + (salary_active.isSelected() ? 1 : 0)
                + "' WHERE employee_id = '" + salary_employee_ID.getText() + "'";

        connect = database.connectDb();

        try {
            Alert alert;

            if (salary_employee_ID.getText().isEmpty()
                    || salary_firstName.getText().isEmpty()
                    || salary_lastName.getText().isEmpty()
                    || salary_position.getText().isEmpty()) {
                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please select an item first");
                alert.showAndWait();
            } else {
                statement = connect.createStatement();
                statement.executeUpdate(sql);

                alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Information Message");
                alert.setHeaderText(null);
                alert.setContentText("Successfully Updated!");
                alert.showAndWait();

                salaryShowListData();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void salaryReset() {
        salary_employee_ID.setText("");
        salary_firstName.setText("");
        salary_lastName.setText("");
        salary_position.setText("");
        salary_salary.setText("");
    }

    public ObservableList<Employee> salaryListData() {
        ObservableList<Employee> listData = FXCollections.observableArrayList();
        String sql = "SELECT * FROM employee_info";
        connect = database.connectDb();
        try {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();
            Employee employeeD;

            while (result.next()) {
                employeeD = new Employee(result.getInt("employee_id"),
                        result.getString("firstName"),
                        result.getString("lastName"),
                        result.getString("position"),
                        result.getDouble("salary"),
                        result.getBoolean("active"));
                listData.add(employeeD);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return listData;
    }

    private ObservableList<Employee> salaryList;

    public void salaryShowListData() {
        salaryList = salaryListData();

        salary_col_employee_ID.setCellValueFactory(new PropertyValueFactory<>("employee_Id"));
        salary_col_firstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        salary_col_lastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        salary_col_position.setCellValueFactory(new PropertyValueFactory<>("position"));
        salary_col_salary.setCellValueFactory(new PropertyValueFactory<>("salary"));
        salary_col_active.setCellValueFactory(new PropertyValueFactory<>("active"));

        salary_tableView.setItems(salaryList);

    }

    public void salarySelect() {

        Employee employeeD = salary_tableView.getSelectionModel().getSelectedItem();
        int num = salary_tableView.getSelectionModel().getSelectedIndex();

        if ((num - 1) < -1) {
            return;
        }

        salary_employee_ID.setText(String.valueOf(employeeD.getEmployee_Id()));
        salary_firstName.setText(employeeD.getFirstName());
        salary_lastName.setText(employeeD.getLastName());
        salary_position.setText(employeeD.getPosition());
        salary_salary.setText(String.valueOf(employeeD.getSalary()));
        salary_active.setSelected(employeeD.getActive());
    }


    public void employeeSelect() {
        Employee employeeD = addEmployee_tableView.getSelectionModel().getSelectedItem();
        int num = addEmployee_tableView.getSelectionModel().getSelectedIndex();

        if (employeeD == null || num < 0) {
            return;
        }

        addEmployee_employee_ID.setText(String.valueOf(employeeD.getEmployee_Id()));
        addEmployee_firstName.setText(employeeD.getFirstName());
        addEmployee_lastName.setText(employeeD.getLastName());
        addEmployee_phoneNum.setText(employeeD.getPhoneNum());

        String gender = employeeD.getGender();
        if (addEmployee_gender.getItems().contains(gender)) {
            addEmployee_gender.setValue(gender);
        } else {
            System.err.println("Género no encontrado en la lista de opciones.");
        }

        String position = employeeD.getPosition();
        if (addEmployee_position.getItems().contains(position)) {
            addEmployee_position.setValue(position);
        } else {
            System.err.println("Posición no encontrada en la lista de opciones.");
        }

        // Configurar imagen
        File imageFile = new File(employeeD.getProfileImage());
        if (imageFile.exists()) {
            Image image = new Image(imageFile.toURI().toString(), 123, 150, false, true);
            addEmployee_image.setImage(image);
        } else {
            System.err.println("Imagen no encontrada: " + employeeD.getProfileImage());
        }
    }

    private int getIndex(List<String> list, String value) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).equals(value)) {
                return i;
            }
        }
        return -1;

    }

    public void defaultNav() {
        home_btn.setStyle("-fx-background-color:linear-gradient(to bottom right, #f28482, #eb5e28);");
    }

    public void displayUsername() {
        username.setText(getData.username);
    }

    public void switchForm(ActionEvent event) {

        if (event.getSource() == home_btn) {
            home_form.setVisible(true);
            addEmployee_form.setVisible(false);
            salary_form.setVisible(false);

            home_btn.setStyle("-fx-background-color:linear-gradient(to bottom right, #f28482, #eb5e28);");
            addEmployee_btn.setStyle("-fx-background-color:transparent");
            salary_btn.setStyle("-fx-background-color:transparent");

            homeTotalEmployees();
            homeEmployeeTotalPresent();
            homeTotalInactive();
            homeChart();

        } else if (event.getSource() == addEmployee_btn) {
            home_form.setVisible(false);
            addEmployee_form.setVisible(true);
            salary_form.setVisible(false);

            addEmployee_btn.setStyle("-fx-background-color:linear-gradient(to bottom right, #f28482, #eb5e28);");
            home_btn.setStyle("-fx-background-color:transparent");
            salary_btn.setStyle("-fx-background-color:transparent");

            addEmployeeGenderList();
            addEmployeePositionList();
            addEmployeeSearch();

        } else if (event.getSource() == salary_btn) {
            home_form.setVisible(false);
            addEmployee_form.setVisible(false);
            salary_form.setVisible(true);

            salary_btn.setStyle("-fx-background-color:linear-gradient(to bottom right, #f28482, #eb5e28);");
            addEmployee_btn.setStyle("-fx-background-color:transparent");
            home_btn.setStyle("-fx-background-color:transparent");

            salaryShowListData();
        }
    }

    private double x = 0;
    private double y = 0;

    public void logout() {

        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Message");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to logout?");
        Optional<ButtonType> option = alert.showAndWait();
        try {
            if (option.get().equals(ButtonType.OK)) {

                logout.getScene().getWindow().hide();
                Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
                Stage stage = new Stage();
                Scene scene = new Scene(root);

                root.setOnMousePressed((MouseEvent event) -> {
                    x = event.getSceneX();
                    y = event.getSceneY();
                });

                root.setOnMouseDragged((MouseEvent event) -> {
                    stage.setX(event.getScreenX() - x);
                    stage.setY(event.getScreenY() - y);

                    stage.setOpacity(.8);
                });

                root.setOnMouseReleased((MouseEvent event) -> {
                    stage.setOpacity(1);
                });

                stage.initStyle(StageStyle.TRANSPARENT);

                stage.setScene(scene);
                stage.show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void close() {
        System.exit(0);
    }

    public void minimize() {
        Stage stage = (Stage) main_form.getScene().getWindow();
        stage.setIconified(true);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        addEmployeeGenderList();
        addEmployeePositionList();
        addEmployeeShowListData();
        close.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                close();
            }
        });
        minimize.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                minimize();
            }
        });

        displayUsername();
        defaultNav();

        homeTotalEmployees();
        homeEmployeeTotalPresent();
        homeTotalInactive();
        homeChart();
        salaryShowListData();
    }
}
