package view;

import com.google.inject.Guice;
import com.google.inject.Injector;
import controller.AdminUsersController;
import injector.ClientDisneyInjector;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import model.Role;
import service.UserService;

import java.io.IOException;
import java.net.URL;


public class AdminUsersViewImpl implements AdminUsersView {

    @FXML
    private TextField idUser;

    @FXML
    private TextField name;

    @FXML
    private TextField username;

    @FXML
    private TextField password;

    @FXML
    private ComboBox menuUser;

    @FXML
    private TextArea textAreaUser;

    @FXML
    private Button backButton;

    @FXML
    private AnchorPane usersPane;

    @FXML
    private ComboBox menuRole;


    
    public void initialize(){
        Injector injector = ClientDisneyInjector.create();
        UserService userService = injector.getInstance(UserService.class);

        AdminUsersController adminUsersController = new AdminUsersController(this, userService);
        menuUser.setOnAction(e->{
            adminUsersController.chooseAction();
        });
    }

    public static AdminUsersViewImpl create(){
        return new AdminUsersViewImpl();
    }

    @Override
    public Long getIdUser() {
        return Long.parseLong(idUser.getText());
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
    public String getName() {
        return name.getText();
    }

    public String getOption(){
        return menuUser.getSelectionModel().getSelectedItem().toString();
    }

    public void setTextAreaUser(String text){
        textAreaUser.clear();
        textAreaUser.appendText(text);
    }

    public Role getOptionMenuRole(){
        String role = menuRole.getSelectionModel().getSelectedItem().toString();
        if (role.equalsIgnoreCase("user")){
            return Role.USER;
        }else if (role.equalsIgnoreCase("admin")){
            return Role.ADMIN;
        }
        return null;

    }

    public void back(){

        AnchorPane mainPane = null;
        URL resource = ClassLoader.getSystemResource("view/admin_window.fxml");
        try {
            mainPane = FXMLLoader.load(resource);
        } catch (IOException e) {
            e.printStackTrace();
        }
        backButton.getScene().setRoot(mainPane);

    }
}
