

import model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.util.List;

public class Main{
    public static void main(String[] args) {
        System.out.println("Hello world");

        SessionFactory sessionFactory;
        // A SessionFactory is set up once for an application!
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
            .configure() // configures settings from hibernate.cfg.xml
            .build();
        try {
            sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        }
        catch (Exception e) {
            // The registry would be destroyed by the SessionFactory, but we had trouble building the SessionFactory
            // so destroy it manually.
            StandardServiceRegistryBuilder.destroy( registry );
            System.err.println("Error in hibernate: ");
            e.printStackTrace();
            return;
        }

        try(Session session = sessionFactory.openSession()) {
            //TODO: Don't forget beginTransaction/commit when doing *changes* on the data
            session.beginTransaction();
            session.save(new User("Daniel"));
            session.save(new User("John"));
            session.getTransaction().commit();
        }

        // Search using HQL
        try(Session session = sessionFactory.openSession()) {
            List<User> usersHql = session.createQuery("from User", User.class).list();
            for (User user : usersHql) {
                System.out.println(">> User: " + user.getName());
            }
        }

        // Search using Criteria
        try(Session session = sessionFactory.openSession()) {
            List<User> usersCriteria = session.createCriteria(User.class).list();
            for (User user : usersCriteria) {
                System.out.println(">> User: " + user.getName());
            }
        }

        // deleting John
        try(Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            List<User> johns = session.createQuery("from User where name='John'", User.class).list();
            session.delete(johns.get(0));
            session.getTransaction().commit();
        }

        // updating Daniel
        try(Session session = sessionFactory.openSession()){
            session.beginTransaction();
            List<User> users = session.createQuery("from User", User.class).list();
            User John = users.get(0);
            John.setName("Another Daniel");
            session.update(John);
            session.getTransaction().commit();
        }

        try(Session session = sessionFactory.openSession()) {
            List<User> usersAfterDeleteAndUpdate = session.createCriteria(User.class).list();
            for (User user : usersAfterDeleteAndUpdate) {
                System.out.println(">> User: " + user.getName());
            }
        }
    }
}
