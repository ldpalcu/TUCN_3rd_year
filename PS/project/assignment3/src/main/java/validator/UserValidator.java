package validator;

import model.Role;
import model.User;

import java.util.ArrayList;
import java.util.List;

public class UserValidator {

    private final User user;

    private static final String PATTERN_USERNAME = "^.{6,}$";
    private static final String PATTERN_PASSWORD = "^(?=.*\\d)(?=.*[a-zA-Z]).{1,}$";

    public List<String> errors;

    public UserValidator(User user) {
        this.user = user;
        errors = new ArrayList<>();
    }

    public boolean validateUser(){
        validateUsername();
        validatePassword();
        validateRole();
        return errors.isEmpty();
    }

    public void validateUsername(){
        if (!user.getUsername().matches(PATTERN_USERNAME)){
            errors.add("Username has not at least 6 characters!");
        }
    }

    public void validatePassword(){
        if (!user.getPassword().matches(PATTERN_PASSWORD)){
            errors.add("Password has not at least one character and at least one digit!");
        }
    }

    public void validateRole(){
        if (user.getRole().equals(Role.USER) || user.getRole().equals(Role.ADMIN)){
            return;
        }else{
            errors.add("You introduce the wrong role!");
        }
    }


}
