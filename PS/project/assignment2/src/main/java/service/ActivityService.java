package service;

import model.Activity;
import model.Category;
import repository.ActivityRepository;
import repository.ActivityRepositoryImpl;

import javax.inject.Inject;
import java.util.List;

public class ActivityService {

    //TODO CHANGE THE TYPE OF VARIABLE TO AN INTERFACE
    //TODO Add methods in interface

    private ActivityRepository activityRepository;

    /*public ActivityService(ActivityRepository activityRepository) {
        this.activityRepository = activityRepository;
    }*/

    public void addActivity(Activity activity){
        activityRepository.persist(activity);
    }

    public void updateActivity(Activity activity){
        activityRepository.update(activity);
    }

    public void deleteActivity(Activity activity){
        activityRepository.delete(activity);
    }

    public Activity findActivityById(Long id){
        Activity activity = (Activity) activityRepository.findById(id);
        return  activity;
    }

    public List<Activity> findAllActivities(){
        return activityRepository.findAll();
    }

    public List<Activity> findActivitiesByName(String name){
        return activityRepository.findByName(name);
    }

    public List<Activity> findActivitiesByLocation(String location){
        return activityRepository.findByLocation(location);
    }

    public List<Activity> findActivitiesByCategory(Category category){
        return activityRepository.findByCategory(category);
    }

    public List<Activity> findActivitiesByAvailability(boolean availability){
        return activityRepository.findByAvailability(availability);
    }

    public List<Activity> findActivitiesByLocationAndCategory(String location, Category category){
        return activityRepository.findByLocationAndCategory(location,category);
    }

    public List<Activity> findActivitiesByNameAndCategory(String name, Category category){
        return activityRepository.findByNameAndCategory(name,category);
    }

    public List<Activity> findActivitiesByNameAndLocation(String name, String location){
        return activityRepository.findByNameAndLocation(name, location);
    }

    public ActivityRepository getActivityRepository() {
        return activityRepository;
    }

    @Inject
    public void setActivityRepository(ActivityRepository activityRepository) {
        this.activityRepository = activityRepository;
    }
}
