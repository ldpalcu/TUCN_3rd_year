package model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "ACTIVITY")
public class Activity {

    private Long id;

    private String name;

    private String location;

    @Enumerated(EnumType.STRING)
    private Category category;

    private int maxUsers;

    private int currentNumberUsers;

    private boolean availability;

    private List<User> users=new ArrayList<>();

    public Activity(long id,String name, String location, Category category, int maxUsers) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.category = category;
        this.maxUsers = maxUsers;
        this.availability = true;
        this.currentNumberUsers = 0;
        //users = new ArrayList<>();
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
        //users = new ArrayList<>();
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

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "activities")
    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
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
                ", users=" + users +
                '}' + "\n";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Activity activity = (Activity) o;
        return maxUsers == activity.maxUsers &&
                currentNumberUsers == activity.currentNumberUsers &&
                availability == activity.availability &&
                Objects.equals(id, activity.id) &&
                Objects.equals(name, activity.name) &&
                Objects.equals(location, activity.location) &&
                category == activity.category &&
                Objects.equals(users, activity.users);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, name, location, category, maxUsers, currentNumberUsers, availability, users);
    }
}
