package presentation.controller;

import business.ClientOperations;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class EmployeeController {

    @FXML
    private AnchorPane rootPane;
    @FXML
    private Button logout;

    public void handleButton1() throws IOException {
        AnchorPane employeePane = FXMLLoader.load(getClass().getResource("/presentation/view/employee_window_bank.fxml"));
        rootPane.getChildren().setAll(employeePane);
    }

    public void handleButton2() throws IOException {
        AnchorPane employeePane = FXMLLoader.load(getClass().getResource("/presentation/view/employee_window_crud.fxml"));
        rootPane.getChildren().setAll(employeePane);
    }

    public void logout() throws IOException {
        AnchorPane employeePane = FXMLLoader.load(getClass().getResource("/presentation/view/main_window.fxml"));
        logout.getScene().setRoot(employeePane);
    }

}
