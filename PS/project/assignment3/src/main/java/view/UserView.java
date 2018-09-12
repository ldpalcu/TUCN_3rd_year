package view;

import model.Activity;
import model.Category;

import java.util.List;

public interface UserView {

    void setTextAreaActivities(List<Activity> text);

    void setTextAreaRegister(String text);

    Long getIdUser();

    Long getIdActivity();

    String getLocation();

    String getName();

    int getMaxUsers();

    int getCurrentNumberUsers();

    Category getCategory();

    String getOption();

    String getAction();


}
