package controller;

import javafx.fxml.FXML;
import model.Role;
import model.User;
import service.UserService;
import view.LoginView;

import javax.inject.Inject;

public class LoginController {

    private  LoginView loginView;
    private UserService userService;

    @Inject
    public void setUserService(UserService userService){
        this.userService = userService;
    }

    public LoginController (LoginView loginView, UserService userService){
        this.loginView = loginView;
        this.userService = userService;
    }

    public void handleLogin(){

        String username = loginView.getUsername();
        String password = loginView.getPassword();
        System.out.println(username);
        System.out.println(password);

        User user = userService.findUserByUsernameAndPassword(username, password);

        //TODO validators
        if (user == null){
            loginView.showErrorMessage("Invalid username/password\n");
        }
        else {
            if (user.getRole().equals(Role.ADMIN)) {
                loginView.showAdminView();
            } else if (user.getRole().equals(Role.USER)) {
                loginView.showUserView();
            } else {
                loginView.showErrorMessage("Invalid username/password\n");
            }
        }
    }



}
