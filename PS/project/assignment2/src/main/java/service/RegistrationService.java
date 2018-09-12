package service;

import model.Activity;
import model.User;
import repository.*;

import javax.inject.Inject;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class RegistrationService {

    private UserRepository userRepository;
    private ActivityRepository activityRepository;

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

    public RegistrationService(UserRepository userRepository, ActivityRepository activityRepository) {
        this.activityRepository = activityRepository;
        this.userRepository = userRepository;
    }

    public void register(Activity activity, User user){
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();

        if (activity.isAvailability()) {
            activity.getUsers().add(user);
            user.getActivities().add(activity);

            activity.setCurrentNumberUsers(activity.getCurrentNumberUsers() + 1);

            if (activity.getCurrentNumberUsers() >= activity.getMaxUsers()) {
                activity.setAvailability(false);
            }

            userRepository.persist(user);
            activityRepository.persist(activity);
        }

    }
}
