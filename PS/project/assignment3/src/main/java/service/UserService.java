package service;

import model.User;

import repository.UserRepository;

import javax.inject.Inject;
import java.util.List;

public class UserService {

    private UserRepository userRepository;

    public void addUser(User user){
        userRepository.persist(user);
    }

    public void deleteUser(User user){
        userRepository.delete(user);
    }

    public void updateUser(User user){
        userRepository.update(user);
    }

    public User findUserById(long id){
        return userRepository.findById(id);
    }

    public User findUserByUsernameAndPassword(String username, String password){
        return userRepository.findByUsernameAndPassword(username, password);
    }

    public List<User> findAllUsers(){
        return userRepository.findAll();
    }

    public UserRepository getUserRepository() {
        return userRepository;
    }

    @Inject
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

}
