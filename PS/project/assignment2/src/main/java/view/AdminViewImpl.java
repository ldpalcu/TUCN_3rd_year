package view;

import controller.AdminController;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;

public class AdminViewImpl implements AdminView{

    @FXML
    private Button buttonUsers;

    @FXML
    private Button buttonActivities;

    @FXML
    private Button buttonReports;

    @FXML
    private Button logout;

    @FXML
    private AnchorPane adminPane;




    public void initialize(){

        AdminController adminController = new AdminController(this);
        buttonUsers.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                adminController.handleUsers();
            }
        });
        buttonActivities.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                adminController.handleActivities();
            }
        });

        buttonReports.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                adminController.handleReports();
            }
        });
    }

    @Override
    public void showActivitiesView() {
        AnchorPane activitiesPane = null;
        try {
                URL resource = ClassLoader.getSystemResource("view/admin_window_activities.fxml");
                activitiesPane = FXMLLoader.load(resource);

        } catch (IOException e) {
            e.printStackTrace();
        }
        ObservableList<Node> children = adminPane.getChildren();
        children.setAll(activitiesPane);

    }

    @Override
    public void showUsersView() {
        AnchorPane usersPane = null;
        try {
            URL resource = ClassLoader.getSystemResource("view/admin_window_reg_user.fxml");
            usersPane = FXMLLoader.load(resource);

        } catch (IOException e) {
            e.printStackTrace();
        }
        adminPane.getChildren().setAll(usersPane);
    }

    @Override
    public void showReportsView() {
        AnchorPane reportsPane = null;
        try {
            URL resource = ClassLoader.getSystemResource("view/admin_window_reports.fxml");
            reportsPane = FXMLLoader.load(resource);

        } catch (IOException e) {
            e.printStackTrace();
        }
        adminPane.getChildren().setAll(reportsPane);
    }

    public void back(){
        AnchorPane mainPane = null;
        URL resource = ClassLoader.getSystemResource("view/main_window.fxml");
        try {
            mainPane = FXMLLoader.load(resource);
        } catch (IOException e) {
            e.printStackTrace();
        }
        logout.getScene().setRoot(mainPane);

    }
}
