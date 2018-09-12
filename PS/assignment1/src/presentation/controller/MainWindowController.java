package presentation.controller;

import business.LoginUser;
import model.Role;
import model.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import javafx.scene.control.TextField;

import java.io.IOException;

public class MainWindowController  {

    @FXML
    private TextField username;
    @FXML
    private TextField password;
    @FXML
    private AnchorPane rootPane;


    @FXML
    public void handleLogin() throws IOException {
        AnchorPane adminPane, employeePane;

        String user = username.getText();
        String pass = password.getText();

        LoginUser loginUser = new LoginUser(user, pass);
        User existingUser = loginUser.login();

        if (existingUser == null) return;

        if (existingUser.getRole().equals(Role.ADMIN.toString())){
            adminPane = FXMLLoader.load(getClass().getResource("/presentation/view/admin_window.fxml"));
            rootPane.getChildren().setAll(adminPane);
        }
        else{
            employeePane = FXMLLoader.load(getClass().getResource("/presentation/view/employee_window.fxml"));
            rootPane.getChildren().setAll(employeePane);
        }

    }


}
