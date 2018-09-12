package business;

import model.validation.UserValidator;
import data_access.dao.UserDAO;
import model.User;

public class EmployeeOperations extends Operations {

    private UserDAO userDAO;
    private User employee;
    private UserValidator userValidator;

    public EmployeeOperations(){
        this.userDAO = new UserDAO();
    }

    public EmployeeOperations(User employee) {
        this.employee = employee;
        this.userDAO = new UserDAO();
        this.userValidator = new UserValidator(employee);
    }

    public UserDAO getUserDAO() {
        return userDAO;
    }

    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public User getEmployee() {
        return employee;
    }

    public void setEmployee(User employee) {
        this.employee = employee;
    }

    public UserValidator getUserValidator() {
        return userValidator;
    }

    public void setUserValidator(UserValidator userValidator) {
        this.userValidator = userValidator;
    }

    public void update(){
        userDAO.updateQuery(employee, employee.getId());
    }

    public void create(){
        if (userValidator.validateUser()) {
            userDAO.insertQuery(employee);
        }
        else{
            System.out.println(userValidator.errors.toString());
            employee = null;
        }

    }

    public void delete(){

        userDAO.deleteQuery(employee.getId());
    }

    public void viewObject(){
        employee = userDAO.findByName(employee.getName());
    }

}
