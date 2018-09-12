package repository;

import model.User;

import java.util.List;

public interface UserRepository extends GenericRepository<User,Long> {

    void persist(User entity);

    void update(User entity);

    void delete(User entity);

    User findById(long id);

    User findByUsernameAndPassword(String username, String password);

    List<User> findAll();
}
