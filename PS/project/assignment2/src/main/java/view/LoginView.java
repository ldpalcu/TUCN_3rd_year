package view;

interface LoginDataProvider{

    String getUsername();

    String getPassword();

}

interface LoginViewProvider{
    void showAdminView();

    void showUserView();

    void showErrorMessage(String message);
}

public interface LoginView extends LoginDataProvider, LoginViewProvider{


}
