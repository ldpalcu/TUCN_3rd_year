package view;

import com.google.inject.Injector;
import controller.AdminActivitiesController;
import injector.ClientDisneyInjector;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import model.Activity;
import model.Category;
import service.ActivityService;

import java.io.IOException;
import java.net.URL;
import java.util.List;

public class AdminActivitiesViewImpl implements AdminActivitiesView  {

    @FXML
    private ComboBox menuActivity;

    @FXML
    private TextField idActivity;

    @FXML
    private TextField name;

    @FXML
    private TextField loc;

    @FXML
    private TextField maxUsers;

    @FXML
    private TextField category;

    @FXML
    private TextArea textAreaActivity;

    @FXML
    private Button backButton;

    @FXML
    private AnchorPane activitiesPane;

    @FXML
    private ComboBox menuState;


    public void initialize(){

        Injector injector = ClientDisneyInjector.create();
        ActivityService activityService = injector.getInstance(ActivityService.class);

        AdminActivitiesController adminActivitiesController = new AdminActivitiesController(this,activityService);

        menuActivity.setOnAction(e -> {
            adminActivitiesController.chooseAction();
        });

    }


    @Override
    public Long getIdActivity() {
        String idActivityNew = idActivity.getText();
        if (!idActivityNew.matches("[0-9]+")){
            setTextAreaActivity("The id contains letters!");
            return 0l;
        }
        return Long.parseLong(idActivity.getText());
    }

    @Override
    public String getName() {
        return name.getText();
    }

    @Override
    public String getLocation() {
        return loc.getText();
    }

    @Override
    public int getMaxUsers() {
        if (maxUsers.getText().equals("")){
            return 0;
        }
        if (!maxUsers.getText().matches("[0-9]+")){
            return 0;
        }
        return Integer.parseInt(maxUsers.getText());
    }

    @Override
    public Category getCategory() {
        String categoryInput = category.getText();
        switch (categoryInput){
            case "CATEGORYL":
                return Category.CATEGORYL;
            case "CATEGORYM" :
                return Category.CATEGORYM;
            case "CATEGORYH" :
                return Category.CATEGORYH;
        }

        return null;
    }

    @Override
    public String getOption() {
        return menuActivity.getSelectionModel().getSelectedItem().toString();
    }

    @Override
    public void setTextAreaActivity(String text) {
        textAreaActivity.clear();
        textAreaActivity.appendText(text);
    }

    public void setTextAreaActivities(List<Activity> activities){
        textAreaActivity.clear();
        textAreaActivity.appendText("Id" + " " + "Name" + " " + "CurrentNumberUsers" + " "+ "MaxUsers" + " " +"Location"+" "+"Category"+" "+"Users"+"\n");
        for (Activity activity: activities
                ) {
            textAreaActivity.appendText(activity.showActivity());
            //textAreaActivities.appendText(activity.getId() + " " + activity.getName() + " " + activity.getCurrentNumberUsers() + " "+ activity.getMaxUsers() + " " +activity.getLocation()+" "+activity.getCategory()+" "+activity.getUsers()+"\n");
        }
    }

    public String getMenuStateOption() {
        return menuState.getSelectionModel().getSelectedItem().toString();
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
