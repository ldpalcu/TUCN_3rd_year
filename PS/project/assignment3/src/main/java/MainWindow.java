
import controller.UserController;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.User;
import service.UserService;
import view.LoginView;
import view.LoginViewImpl;

import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;
import java.util.List;
import java.util.ResourceBundle;

public class MainWindow extends Application {


    @Override
    public void start(Stage primaryStage) throws IOException {
        URL resource = ClassLoader.getSystemResource("view/main_window.fxml");
        Parent root = FXMLLoader.load(resource);

        primaryStage.setTitle("Disneyland App");
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }



}
