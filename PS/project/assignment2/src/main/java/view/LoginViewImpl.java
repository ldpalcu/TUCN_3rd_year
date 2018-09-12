package view;

import com.google.inject.Guice;
import com.google.inject.Injector;
import controller.LoginController;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import repository.Factory;
import repository.UserRepositoryImpl;
import service.UserService;

import java.io.IOException;
import java.net.URL;



public class LoginViewImpl implements LoginView {

    @FXML
    private TextField username;

    @FXML
    private TextField password;

    @FXML
    private Button login;

    @FXML
    private AnchorPane rootPane;



    public void initialize(){

        Injector injector = Guice.createInjector(new DisneyInjector());
        //UserService userService = new UserService(new UserRepositoryImpl(new Factory()));
        UserService userService = injector.getInstance(UserService.class);

        LoginController loginViewController = new LoginController(this, userService);
        login.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                loginViewController.handleLogin();
            }
        });
    }

    @Override
    public String getUsername() {
        return username.getText();
    }

    @Override
    public String getPassword() {
        return password.getText();
    }


    @Override
    public void showAdminView() {
        AnchorPane adminPane = null;
        try {
            URL resource = ClassLoader.getSystemResource("view/admin_window.fxml");
            adminPane = FXMLLoader.load(resource);

        } catch (IOException e) {
            e.printStackTrace();
        }
        rootPane.getChildren().setAll(adminPane);

    }

    @Override
    public void showUserView() {
        AnchorPane userPane = null;
        try {
            URL resource = ClassLoader.getSystemResource("view/user_activities.fxml");
            userPane= FXMLLoader.load(resource);

        } catch (IOException e) {
            e.printStackTrace();
        }
        rootPane.getChildren().setAll(userPane);
    }

    @Override
    public void showErrorMessage(String message) {
        //TODO put it in a dialog message
        System.out.println(message);
    }
}
