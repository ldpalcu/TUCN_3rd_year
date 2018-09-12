package controller;


import model.Role;
import model.User;
import service.UserService;
import validator.UserValidator;
import view.AdminUsersView;

import java.util.List;

public class AdminUsersController {

    private final AdminUsersView adminUsersView;
    private final UserService userService;

    public AdminUsersController(AdminUsersView adminUsersView, UserService userService) {
        this.adminUsersView = adminUsersView;
        this.userService = userService;
    }

    public void addUser(){
        String username = adminUsersView.getUsername();
        String password = adminUsersView.getPassword();
        String name = adminUsersView.getName();
        Role role = adminUsersView.getOptionMenuRole();

        User user = new User(name,username,password, role);
        UserValidator userValidator = new UserValidator(user);
        if (userValidator.validateUser()){
            userService.addUser(user);
        }
        else{
            adminUsersView.setTextAreaUser(userValidator.errors.toString());
        }

    }

    public void updateUser(){
        Long idUser = adminUsersView.getIdUser();
        User user = userService.findUserById(idUser);
        if (!adminUsersView.getPassword().equals("")){
            user.setPassword(adminUsersView.getPassword());
        }
        if (!adminUsersView.getName().equals("")){
            user.setName(adminUsersView.getName());
        }
        if (!adminUsersView.getUsername().equals("")){
            user.setUsername(adminUsersView.getUsername());
        }
        user.setRole(adminUsersView.getOptionMenuRole());

        UserValidator userValidator = new UserValidator(user);
        if (userValidator.validateUser()){
            userService.updateUser(user);
        }
        else{
            adminUsersView.setTextAreaUser(userValidator.errors.toString());
        }
    }

    public void deleteUser(){
        Long idUser = adminUsersView.getIdUser();
        User user = userService.findUserById(idUser);

        if (user == null){
            adminUsersView.setTextAreaUser("The user doesn't exist!");
        }else{
            userService.deleteUser(user);
        }

    }

    public void selectUser(){
        Long idUser = adminUsersView.getIdUser();
        User user = userService.findUserById(idUser);
        if (user == null){
            adminUsersView.setTextAreaUser("The user doesn't exist!");
        }
        else{
            adminUsersView.setTextAreaUser(user.toString());
        }


    }

    public void selectAllUsers(){
        List<User> users = userService.findAllUsers();
        adminUsersView.setTextAreaUser(users.toString());
    }

    public void chooseAction(){
        String option = adminUsersView.getOption();
        switch (option){
            case "Add":
                addUser();
                break;
            case "Select":
                selectUser();
                break;
            case "Delete":
                deleteUser();
                break;
            case "Update":
                updateUser();
                break;
            case "Select All":
                selectAllUsers();
                break;
        }
    }
}
