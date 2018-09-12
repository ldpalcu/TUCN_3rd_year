package service;

import model.Action;
import model.Activity;
import model.State;
import model.User;
import repository.*;
import server.ServerNotification;

import javax.inject.Inject;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RegistrationService {

    private UserRepository userRepository;
    private ActivityRepository activityRepository;
    private ServerNotification serverNotification;

    public UserRepository getUserRepository() {
        return userRepository;
    }

    public RegistrationService() {
    }

    @Inject
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public ActivityRepository getActivityRepository() {
        return activityRepository;
    }

    @Inject
    public void setActivityRepository(ActivityRepository activityRepository) {
        this.activityRepository = activityRepository;
    }

    @Inject
    public void setServerNotification(ServerNotification serverNotification) {
        this.serverNotification = serverNotification;
    }

    public RegistrationService(UserRepository userRepository, ActivityRepository activityRepository) {
        this.activityRepository = activityRepository;
        this.userRepository = userRepository;
    }

    public void register(Activity activity, User user){
        if (activity.isAvailability()) {
            activity.getUsers().add(user);
            user.getActivities().add(activity);

            activity.setCurrentNumberUsers(activity.getCurrentNumberUsers() + 1);

            if (activity.getCurrentNumberUsers() >= activity.getMaxUsers()) {
                activity.setAvailability(false);
            }

            userRepository.update(user);
            activityRepository.update(activity);

        }
        else{
            activity.getQueuedUsers().add(user);
            user.getActivitiesQueued().add(activity);
            activityRepository.update(activity);
            userRepository.update(user);
        }

    }

    public void unregister(Activity activity, User user){
        System.out.println(user.toString());
        List<User> users = new ArrayList<>();

        System.out.println(activity.getUsers().remove(user));

        user.getActivities().remove(activity);

        activity.setCurrentNumberUsers(activity.getCurrentNumberUsers() - 1);

        activity.setAvailability(true);

        userRepository.update(user);
        activityRepository.update(activity);

        if (activity.getQueuedUsers().size() > 0){
            serverNotification.notifyUsers(Action.UNREGISTER, activity);

            User userInQueue = activity.getQueuedUsers().get(0);
            activity.getQueuedUsers().remove(0);
            userInQueue.getActivitiesQueued().remove(activity);

            register(activity, userInQueue);
        }


    }
}
