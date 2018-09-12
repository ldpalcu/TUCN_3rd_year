package server;

import model.Action;
import model.Activity;
import model.Category;
import repository.ActivityRepository;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

public class ServerActivityRepositoryImpl implements ActivityRepository {

    ServerProvider serverProvider;

    @Inject
    public ServerActivityRepositoryImpl(ServerProvider serverProvider){
        this.serverProvider = serverProvider;
    }

    @Override
    public void persist(Activity entity) {
        serverProvider.sendMessage(Action.ADD_ACTIVITY, entity);
    }

    @Override
    public void update(Activity entity) {
        serverProvider.sendMessage(Action.UPDATE_ACTIVITY, entity);
    }

    @Override
    public void delete(Activity entity) {
        serverProvider.sendMessage(Action.DELETE_ACTIVITY, entity);
    }

    @Override
    public Activity findById(long id) {
        Activity activity = (Activity) serverProvider.sendMessage(Action.GET_ACTIVITY_BY_ID, id);
        return activity;
    }

    @Override
    public List<Activity> findAll() {
        return (List<Activity>) serverProvider.sendMessage(Action.GET_ACTIVITIES, " ");
    }

    @Override
    public List<Activity> findByName(String name) {
        return (List<Activity>) serverProvider.sendMessage(Action.GET_ACTIVITY_BY_NAME, name);
    }

    @Override
    public List<Activity> findByLocation(String location) {
        return null;
    }

    @Override
    public List<Activity> findByCategory(Category category) {
        return null;
    }

    @Override
    public List<Activity> findByAvailability(boolean availability) {
        return null;
    }

    @Override
    public List<Activity> findByNameAndCategory(String name, Category category) {
        return null;
    }

    @Override
    public List<Activity> findByNameAndLocation(String name, String location) {
        return null;
    }

    @Override
    public List<Activity> findByLocationAndCategory(String location, Category category) {
        return null;
    }

}
