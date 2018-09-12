package controller;


import model.Action;
import model.Activity;
import model.Category;
import model.State;
import service.ActivityService;
import validator.ActivityValidator;
import view.AdminActivitiesView;

import java.util.List;

public class AdminActivitiesController {

    private final AdminActivitiesView adminActivitiesView;
    private final ActivityService activityService;

    public AdminActivitiesController(AdminActivitiesView adminActivitiesView, ActivityService activityService) {
        this.adminActivitiesView = adminActivitiesView;
        this.activityService = activityService;
    }

    public void addActivity(){

        String name = adminActivitiesView.getName();
        String location = adminActivitiesView.getLocation();
        int maxUsers = adminActivitiesView.getMaxUsers();
        Category category = adminActivitiesView.getCategory();

        Activity activity = new Activity(name, location, category,maxUsers);
        ActivityValidator activityValidator = new ActivityValidator(activity);
        if (activityValidator.validateActivity()){
            activityService.addActivity(activity);
        }
        else{
            adminActivitiesView.setTextAreaActivity(activityValidator.errors.toString());
        }


    }

    public void updateActivity(){
        Long idActivity = adminActivitiesView.getIdActivity();
        Activity activity = activityService.findActivityById(idActivity);
        if (!adminActivitiesView.getName().equals("")){
            activity.setName(adminActivitiesView.getName());
        }
        if (!adminActivitiesView.getLocation().equals("")){
            activity.setLocation(adminActivitiesView.getLocation());
        }
        if (adminActivitiesView.getMaxUsers() != 0){
            activity.setMaxUsers(adminActivitiesView.getMaxUsers());
        }
        if (adminActivitiesView.getCategory() != null){
            activity.setCategory(adminActivitiesView.getCategory());
        }
        activity.setState(State.valueOf(adminActivitiesView.getMenuStateOption()));

        ActivityValidator activityValidator = new ActivityValidator(activity);
        if (activityValidator.validateActivity()) {
            activityService.updateActivity(activity);
        }
        else{
            adminActivitiesView.setTextAreaActivity(activityValidator.errors.toString());
        }

    }

    public void deleteActivity(){
        Long idActivity = adminActivitiesView.getIdActivity();
        Activity activity = activityService.findActivityById(idActivity);

        if (idActivity == 0){
            adminActivitiesView.setTextAreaActivity("Error!");
        }else{
            activityService.deleteActivity(activity);
        }



    }

    public void selectActivity(){
        Long idActivity = adminActivitiesView.getIdActivity();
        Activity activity = activityService.findActivityById(idActivity);

        if (idActivity == 0 || activity == null){
            adminActivitiesView.setTextAreaActivity("Error!");
        }
        else{
            adminActivitiesView.setTextAreaActivity(activity.showActivity());
        }

    }

    public void selectAllActivities(){
        List<Activity> activities = activityService.findAllActivities();
        adminActivitiesView.setTextAreaActivities(activities);
    }



    public void chooseAction(){
        String option = adminActivitiesView.getOption();
        switch (option){
            case "Add":
                addActivity();
                break;
            case "Select":
                selectActivity();
                break;
            case "Delete":
                deleteActivity();
                break;
            case "Update":
                updateActivity();
                break;
            case "Select All":
                selectAllActivities();
                break;
        }
    }
}
