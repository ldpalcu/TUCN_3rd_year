package model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "ACTIVITY")
public class Activity implements Serializable{

    private Long id;

    private String name;

    private String location;

    @Enumerated(EnumType.STRING)
    private Category category;

    private int maxUsers;

    private int currentNumberUsers;

    private boolean availability;

    private State state;

    private List<User> queuedUsers;

    private List<User> users;

    public Activity(long id,String name, String location, Category category, int maxUsers) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.category = category;
        this.maxUsers = maxUsers;
        this.availability = true;
        this.currentNumberUsers = 0;
        this.state = State.SCHEDULED;
        users = new ArrayList<>();
        queuedUsers = new ArrayList<>();
    }

    public Activity(){

    }

    public Activity(String name, String location, Category category, int maxUsers) {
        this.name = name;
        this.location = location;
        this.category = category;
        this.maxUsers = maxUsers;
        this.availability = true;
        this.currentNumberUsers = 0;
        this.state = State.SCHEDULED;
        users = new ArrayList<>();
        queuedUsers = new ArrayList<>();
    }

    @Id
    @GeneratedValue(generator="increment")
    @GenericGenerator(name="increment", strategy = "increment")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public int getMaxUsers() {
        return maxUsers;
    }

    public void setMaxUsers(int maxUsers) {
        this.maxUsers = maxUsers;
    }

    public boolean isAvailability() {
        return availability;
    }

    public void setAvailability(boolean availability) {
        this.availability = availability;
    }

    public int getCurrentNumberUsers() {
        return currentNumberUsers;
    }

    public void setCurrentNumberUsers(int currentNumberUsers) {
        this.currentNumberUsers = currentNumberUsers;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "activities", cascade = CascadeType.ALL)
    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "activitiesQueued", cascade = CascadeType.ALL)
    public List<User> getQueuedUsers() {
        return queuedUsers;
    }

    public void setQueuedUsers(List<User> queuedUsers) {
        this.queuedUsers = queuedUsers;
    }

    public String showActivity() {
        return "Activity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", location='" + location + '\'' +
                ", category=" + category +
                ", maxUsers=" + maxUsers +
                ", currentNumberUsers=" + currentNumberUsers +
                ", availability=" + availability +
                ", state=" + state +
                ", users=" + users +
                ", queuedUsers=" + queuedUsers +
                '}' + "\n";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Activity activity = (Activity) o;

        if (maxUsers != activity.maxUsers) return false;
        if (currentNumberUsers != activity.currentNumberUsers) return false;
        if (availability != activity.availability) return false;
        if (!id.equals(activity.id)) return false;
        if (!name.equals(activity.name)) return false;
        if (!location.equals(activity.location)) return false;
        if (category != activity.category) return false;
        return state == activity.state;
    }

    @Override
    public int hashCode() {

        int result = (id == null) ? 0 : id.hashCode();
        result = 31 * result + name.hashCode();
        result = 31 * result + location.hashCode();
        result = 31 * result + category.hashCode();
        result = 31 * result + maxUsers;
        result = 31 * result + currentNumberUsers;
        result = 31 * result + (availability ? 1 : 0);
        result = 31 * result + state.hashCode();
        return result;
    }
}
