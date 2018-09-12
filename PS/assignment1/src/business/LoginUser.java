package business;

import data_access.dao.UserDAO;
import model.User;

public class LoginUser {

    private static String username;
    private static String password;
    private static UserDAO userDAO;

    public LoginUser(String username, String password) {
        this.username = username;
        this.password = password;
        userDAO = new UserDAO();
    }

    public static User login(){
        User existingUser = (User) userDAO.findByUsernameAndPassword(username, password);
        if (existingUser == null) return null;
        return existingUser;
    }
}
