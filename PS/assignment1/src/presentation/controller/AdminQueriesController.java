package presentation.controller;

import business.EmployeeOperations;
import business.ServiceFactory;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import model.User;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import model.validation.UserValidator;

import java.io.IOException;


public class AdminQueriesController {

    @FXML
    private Button back;
    @FXML
    private ComboBox menuUser;
    @FXML
    private TextField idUser;
    @FXML
    private TextField name;
    @FXML
    private TextField username;
    @FXML
    private TextField password;
    @FXML
    private TextField role;
    @FXML
    private TextArea textArea;

    private ServiceFactory serviceFactory;

    public AdminQueriesController(){
        serviceFactory = new ServiceFactory();
    }

    @FXML
    public void executeUser(){
        int idEmployee;
        if (idUser.getText().equals("")){
            idEmployee = 0;
        }else{
            idEmployee = Integer.parseInt(idUser.getText());
        }
        String nameEmployee = name.getText();
        String usernameEmployee = username.getText();
        String passwordEmployee = password.getText();
        String roleEmployee = role.getText();

        String option = menuUser.getSelectionModel().getSelectedItem().toString();

        User employee = new User(idEmployee,nameEmployee,usernameEmployee,passwordEmployee,roleEmployee);
        //validate employee
        UserValidator userValidator = new UserValidator(employee);

        serviceFactory.getEmployeeOperations().setEmployee(employee);
        serviceFactory.getEmployeeOperations().setUserValidator(userValidator);
        serviceFactory.getEmployeeOperations().execute(option);
        employee = serviceFactory.getEmployeeOperations().getEmployee();

        textArea.clear();

        if (employee!= null) {
            textArea.appendText("Succes!\n");
            textArea.appendText(employee.toString());
        }else{
            textArea.appendText("Failure!\n");
        }

    }

    @FXML
    public void back() throws IOException {
        AnchorPane employeePane = FXMLLoader.load(getClass().getResource("/presentation/view/admin_window.fxml"));
        back.getScene().setRoot(employeePane);
    }
}
