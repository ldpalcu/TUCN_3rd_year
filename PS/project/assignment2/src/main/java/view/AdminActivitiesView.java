package view;

import model.Activity;
import model.Category;

import java.util.List;

public interface AdminActivitiesView {

    Long getIdActivity();

    String getName();

    String getLocation();

    int getMaxUsers();

    Category getCategory();

    String getOption();

    void setTextAreaActivity(String text);

    void setTextAreaActivities(List<Activity> activities);


}
