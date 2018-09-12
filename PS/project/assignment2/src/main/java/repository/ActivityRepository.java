package repository;

import model.Activity;
import model.Category;

import java.io.Serializable;
import java.util.List;

public interface ActivityRepository extends GenericRepository<Activity,Long> {

    void persist(Activity entity);

    void update(Activity entity);

    void delete(Activity entity);

    Activity findById(long id);

    List<Activity> findAll();

    List<Activity> findByName(String name);

    List<Activity> findByLocation(String location);

    List<Activity> findByCategory(Category category);

    List<Activity> findByAvailability(boolean availability);

    List<Activity> findByNameAndCategory(String name, Category category);

    List<Activity> findByNameAndLocation(String name, String location);

    List<Activity> findByLocationAndCategory(String location, Category category);



}
