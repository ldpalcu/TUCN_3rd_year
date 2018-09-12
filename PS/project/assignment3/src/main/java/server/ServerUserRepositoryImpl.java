package server;

import model.Action;
import model.User;
import repository.UserRepository;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

public class ServerUserRepositoryImpl implements UserRepository {

    ServerProvider serverProvider;

    @Inject
    public void setServerProvider(ServerProvider serverProvider) {
        this.serverProvider = serverProvider;
    }

    @Override
    public void persist(User entity) {
        serverProvider.sendMessage(Action.ADD_USER, entity);
    }

    @Override
    public void update(User entity) {
        serverProvider.sendMessage(Action.UPDATE_USER, entity);
    }

    @Override
    public void delete(User entity) {
        serverProvider.sendMessage(Action.DELETE_USER, entity);
    }

    @Override
    public User findById(long id) {
        return (User) serverProvider.sendMessage(Action.GET_USER_BY_ID, id);
    }

    @Override
    public User findByUsernameAndPassword(String username, String password) {
        List<String> fields = new ArrayList<>();
        fields.add(username);
        fields.add(password);
        return (User) serverProvider.sendMessage(Action.GET_USER_BY_USERNAME_AND_PASSWORD, fields);
    }

    @Override
    public List<User> findAll() {
        return (List<User>) serverProvider.sendMessage(Action.GET_USERS, " ");
    }
}
