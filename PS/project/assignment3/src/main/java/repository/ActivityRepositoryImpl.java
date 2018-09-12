package repository;

import model.Activity;
import model.Category;
import model.User;
import org.hibernate.Session;

import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;

public class ActivityRepositoryImpl implements ActivityRepository {

    private Factory factory;

    public Factory getFactory() {
        return factory;
    }

    @Inject
    public void setFactory(Factory factory) {
        this.factory = factory;
    }

    public ActivityRepositoryImpl() {
    }

    public ActivityRepositoryImpl(Factory factory) {
        this.factory = factory;
    }

    public void persist(Activity entity) {
        Session session = factory.openCurentSession();
        session.save(entity);
        factory.closeCurrentSession();
    }

    public void update(Activity entity) {
        Session session = factory.openCurentSession();
        session.update(entity);
        factory.closeCurrentSession();
    }

    public void delete(Activity entity) {
        factory.openCurentSession();
        factory.getCurrentSession().delete(entity);
        factory.closeCurrentSession();

    }

    public Activity findById(long id) {
        Session session = factory.openCurentSession();
        List<Activity> activities = session.createQuery("from Activity where id="+id, Activity.class).list();
        factory.closeCurrentSession();
        if (activities.size()==0){
            return null;
        }
        return activities.get(0);
    }

    public List<Activity> findAll(){
        Session session = factory.openCurentSession();
        List<Activity> activities = session.createQuery("from Activity ", Activity.class).list();
        factory.closeCurrentSession();
        return activities;
    }

    @Override
    public List<Activity> findByName(String name) {
        Session session = factory.openCurentSession();
        List<Activity> activities = session.createQuery("from Activity where name="+"'"+name+"'", Activity.class).list();
        factory.closeCurrentSession();
        return activities;
    }

    @Override
    public List<Activity> findByLocation(String location) {
        Session session = factory.openCurentSession();
        List<Activity> activities = session.createQuery("from Activity where location="+"'"+location+"'", Activity.class).list();
        factory.closeCurrentSession();
        return activities;
    }

    @Override
    public List<Activity> findByCategory(Category category) {
        Session session = factory.openCurentSession();
        List<Activity> activities = session.createQuery("from Activity where category="+category.ordinal(), Activity.class).list();
        factory.closeCurrentSession();
        return activities;
    }

    @Override
    public List<Activity> findByAvailability(boolean availability) {
        Session session = factory.openCurentSession();
        List<Activity> activities = session.createQuery("from Activity where availability="+availability, Activity.class).list();
        factory.closeCurrentSession();
        return activities;
    }

    @Override
    public List<Activity> findByNameAndCategory(String name, Category category) {
        Session session = factory.openCurentSession();
        List<Activity> activities = session.createQuery("from Activity where name="+"'"+name+"'"+ " and category="+category, Activity.class).list();
        factory.closeCurrentSession();
        return activities;
    }

    @Override
    public List<Activity> findByNameAndLocation(String name, String location) {
        Session session = factory.openCurentSession();
        List<Activity> activities = session.createQuery("from Activity where name="+"'"+name+"'"+ " and location="+"'"+location+"'", Activity.class).list();
        factory.closeCurrentSession();
        return activities;
    }

    @Override
    public List<Activity> findByLocationAndCategory(String location, Category category) {
        Session session = factory.openCurentSession();
        List<Activity> activities = session.createQuery("from Activity where location="+"'"+location+"'"+ " and category="+category, Activity.class).list();
        factory.closeCurrentSession();
        return activities;
    }



}
