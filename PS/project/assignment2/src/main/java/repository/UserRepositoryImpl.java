package repository;


import model.User;
import org.hibernate.Session;

import javax.inject.Inject;
import java.util.List;

public class UserRepositoryImpl  implements UserRepository {

    public UserRepositoryImpl() {
    }

    //TODO implement interface
    private Factory factory;

    @Inject
    public void setFactory(Factory factory) {
        this.factory = factory;
    }

    public UserRepositoryImpl(Factory factory) {
        this.factory = factory;

    }

    public void persist(User entity) {
        //TODO se insereaza de mai multe ori un user din cauza lui Role
        Session session = factory.openCurentSession();
        session.save(entity);
        factory.closeCurrentSession();
    }

    public void update(User entity) {
        Session session = factory.openCurentSession();
        session.update(entity);
        factory.closeCurrentSession();
    }

    public void delete(User entity) {
        Session session = factory.openCurentSession();
        session.delete(entity);
        factory.closeCurrentSession();

    }

    public User findById(long id) {
        Session session = factory.openCurentSession();
        List<User> users = session.createQuery("from User where id="+id, User.class).list();
        factory.closeCurrentSession();
        if (users.size()==0){
            return null;
        }
        return users.get(0);
    }

    public User findByUsernameAndPassword(String username, String password){
        Session session = factory.openCurentSession();
        List<User> users
                = session.createQuery("from " +
                " User where username='" + username +"' and password='"+password+"'", User.class).list();
        factory.closeCurrentSession();
        if (users.size() == 0){
            return null;
        }
        return users.get(0);
    }

    public List<User> findAll(){
        Session session = factory.openCurentSession();
        List<User> users = session.createQuery("from User ", User.class).list();
        factory.closeCurrentSession();
        return users;
    }


}
