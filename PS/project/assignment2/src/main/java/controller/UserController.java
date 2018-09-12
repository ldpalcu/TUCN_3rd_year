package controller;

import model.Activity;
import model.Category;
import model.User;
import service.ActivityService;
import service.RegistrationService;
import service.UserService;
import view.AdminViewImpl;
import view.UserView;

import java.util.List;

public class UserController {

    private final UserView userView;
    private final ActivityService activityService;
    private final UserService userService;
    private final RegistrationService registrationService;

    public UserController(UserView userView, RegistrationService registrationService, UserService userService, ActivityService activityService) {
        this.userView = userView;
        this.registrationService = registrationService;
        this.userService = userService;
        this.activityService = activityService;
    }

    public void viewListActivities(){
        List<Activity> activities = activityService.findAllActivities();
        userView.setTextAreaActivities(activities);
    }

    public void register(){
        User user = userService.findUserById(userView.getIdUser());
        Activity activity = activityService.findActivityById(userView.getIdActivity());

        if (user == null || activity == null){
            userView.setTextAreaRegister("Error!");
        }else{
            registrationService.register(activity, user);
        }



    }

    public void filterActivitiesByName(){
        String name = userView.getName();
        List<Activity> activities = activityService.findActivitiesByName(name);
        userView.setTextAreaActivities(activities);
    }

    public void filterActivitiesByLocation(){
        String location = userView.getLocation();
        List<Activity> activities = activityService.findActivitiesByLocation(location);
        userView.setTextAreaActivities(activities);
    }

    public void filterActivitiesByCategory(){
        Category category = userView.getCategory();
        if (category == null){
            userView.setTextAreaRegister("You introduced the wrong category! You can introduce:CATEGORYL/M/H!");
        }else{
            List<Activity> activities = activityService.findActivitiesByCategory(category);
            userView.setTextAreaActivities(activities);
        }

    }

    public void filterActivitiesByAvailability(){

    }

    public void filterActivitiesByLocationAndCategory(){
        String location = userView.getLocation();
        Category category = userView.getCategory();
        if (category == null){
            userView.setTextAreaRegister("You introduced the wrong category! You can introduce:CATEGORYL/M/H!");
        }else{
            List<Activity> activities = activityService.findActivitiesByLocationAndCategory(location,category);
            userView.setTextAreaActivities(activities);
        }

    }

    public void filterActivitiesByNameAndCategory(){
        String name = userView.getName();
        Category category = userView.getCategory();
        List<Activity> activities = activityService.findActivitiesByNameAndCategory(name,category);
        userView.setTextAreaActivities(activities);
    }

    public void filterActivitiesByNameAndLocation(){
        String name = userView.getName();
        String location = userView.getLocation();
        List<Activity> activities = activityService.findActivitiesByNameAndLocation(name, location);
        userView.setTextAreaActivities(activities);
    }
    public void chooseFilter(){
        String option = userView.getOption();
        switch (option){
            case "Name":
                filterActivitiesByName();
                break;
            case "Location":
                filterActivitiesByLocation();
                break;
            case "Category":
                filterActivitiesByCategory();
                break;
            case "Availability":
                filterActivitiesByAvailability();
                break;
            case "Location and Category":
                filterActivitiesByLocationAndCategory();
                break;
            case "Name and Category":
                filterActivitiesByNameAndCategory();
                break;
            case "Name and Location":
                filterActivitiesByNameAndLocation();
                break;
        }

    }







}
