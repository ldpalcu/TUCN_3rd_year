import controller.LoginController;
import model.Role;
import model.User;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import service.UserService;
import view.LoginView;

import static org.mockito.Mockito.*;

public class LoginControllerTests {

    @MockitoAnnotations.Mock
    LoginView loginView;

    @MockitoAnnotations.Mock
    UserService userService;

    @InjectMocks
    LoginController loginController;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void givenAdminUsernameAndPassword_login_showAdminView(){
        when(loginView.getUsername()).thenReturn("ldpalcu");
        when(loginView.getPassword()).thenReturn("dani2713");

        when(userService.findUserByUsernameAndPassword("ldpalcu", "dani2713"))
                .thenReturn(new User("Palcu Daniela", "ldpalcu", "dani2713", Role.ADMIN));


        loginController.handleLogin();

        verify(loginView).showAdminView();
    }

    @Test
    public void givenUserUsernameAndPassword_login_showUserView(){
        when(loginView.getUsername()).thenReturn("popada");
        when(loginView.getPassword()).thenReturn("ada2713");

        when(userService.findUserByUsernameAndPassword("popada", "ada2713"))
                .thenReturn(new User("Popa Ada", "popada", "ada2713", Role.USER));

        loginController.handleLogin();

        verify(loginView).showUserView();
    }

    @Test
    public void givenInvalidUsernameAndPassword_login_showErrorMessage(){
        when(loginView.getUsername()).thenReturn("admin");
        when(loginView.getPassword()).thenReturn("admin");

        loginController.handleLogin();

        verify(loginView).showErrorMessage("Invalid username/password\n");
    }

}
