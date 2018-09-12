package view;

import client.SecondConnection;
import com.google.inject.Guice;
import com.google.inject.Injector;
import controller.UserController;
import injector.ClientDisneyInjector;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import model.Activity;
import model.Category;
import service.ActivityService;
import service.RegistrationService;
import service.UserService;

import java.io.IOException;
import java.net.URL;
import java.util.List;


public class UserViewImpl implements UserView {

    @FXML
    private TextArea textAreaActivities;

    @FXML
    private TextArea textAreaRegister;

    @FXML
    private Button listActivities;

    @FXML
    private ComboBox menuRegister;

    @FXML
    private ComboBox menuFilter;

    @FXML
    private Button backButton;

    @FXML
    private AnchorPane userPane;

    @FXML
    private TextField idUser;

    @FXML
    private TextField loc;

    @FXML
    private TextField name;

    @FXML
    private TextField maxUsers;

    @FXML
    private TextField category;

    @FXML
    private TextField currentNumberUsers;

    @FXML
    private TextField idActivity;


    public void initialize(){

        Injector injector = ClientDisneyInjector.create();
        RegistrationService registrationService = injector.getInstance(RegistrationService.class);
        ActivityService activityService = injector.getInstance(ActivityService.class);
        UserService userService = injector.getInstance(UserService.class);
        SecondConnection secondConnection = injector.getInstance(SecondConnection.class);

        UserController userController = new UserController(this, registrationService, userService, activityService);

        secondConnection.addObservers(userController);

        listActivities.setOnAction(e->{
            userController.viewListActivities();
        });

        menuRegister.setOnAction(e -> {
            userController.chooseAction();
        });
        menuFilter.setOnAction(e ->{
            userController.chooseFilter();
        });

    }

    public void setTextAreaActivities(List<Activity> text){
        textAreaActivities.clear();
        textAreaActivities.appendText("Id" + " " + "Name" + " " + "CurrentNumberUsers" + " "+ "MaxUsers" + " " +"Location"+" "+"Category"+" "+"Users"+"\n");
        for (Activity activity: text
             ) {
            textAreaActivities.appendText(activity.showActivity());
            //textAreaActivities.appendText(activity.getId() + " " + activity.getName() + " " + activity.getCurrentNumberUsers() + " "+ activity.getMaxUsers() + " " +activity.getLocation()+" "+activity.getCategory()+" "+activity.getUsers()+"\n");
        }

    }

    public void setTextAreaRegister(String text){
        textAreaRegister.clear();
        if (text!=null){
            textAreaRegister.appendText(text);
        }

    }

    public Long getIdUser(){
        if (idUser.getText().equals("")){
            return 0l;
        }
        if (!idUser.getText().matches("[0-9]+")){
            return 0l;
        }
        return Long.parseLong(idUser.getText());
    }

    public String getLocation(){
        return loc.getText();
    }

    public String getName(){
        return name.getText();
    }

    public Category getCategory(){
        String categoryIn = category.getText();

        switch(categoryIn){
            case "CATEGORYL":
                return Category.CATEGORYL;
            case "CATEGORYM":
                return Category.CATEGORYM;
            case "CATEGORYH":
                return Category.CATEGORYH;
        }

        return null;
    }

    public int getMaxUsers(){
        if (maxUsers.getText().equals("")){
            return 0;
        }
        if (!maxUsers.getText().matches("[0-9]+")){
            return 0;
        }
        return Integer.parseInt(maxUsers.getText());
    }

    public int getCurrentNumberUsers(){
        if (currentNumberUsers.getText().equals("")){
            return 0;
        }
        if (!currentNumberUsers.getText().matches("[0-9]+")){
            return 0;
        }
        return Integer.parseInt(maxUsers.getText());
    }

    public Long getIdActivity(){
        if (idActivity.getText().equals("")){
            return 0l;
        }
        if (!idActivity.getText().matches("[0-9]+")){
            return 0l;
        }
        return Long.parseLong(idActivity.getText());
    }

    public String getOption(){
        return menuFilter.getSelectionModel().getSelectedItem().toString();
    }

    public String getAction(){
        return menuRegister.getSelectionModel().getSelectedItem().toString();
    }


    public void back(){

        AnchorPane mainPane = null;
        URL resource = ClassLoader.getSystemResource("view/main_window.fxml");
        try {
            mainPane = FXMLLoader.load(resource);
        } catch (IOException e) {
            e.printStackTrace();
        }
        backButton.getScene().setRoot(mainPane);

    }



}
