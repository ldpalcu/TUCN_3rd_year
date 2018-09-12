import controller.AdminUsersController;
import model.Role;
import model.User;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import repository.UserRepositoryImpl;
import service.UserService;
import view.AdminUsersView;

import javax.inject.Inject;

import static org.mockito.Mockito.*;

public class AdminUsersControllerTests {

    @Mock
    private UserService userService;

    @Mock
    private AdminUsersView adminUsersView;

    @InjectMocks
    private AdminUsersController adminUsersController;

    private User userBeforeUpdate;

    private User userAfterUpdate;


    @Before
    public void setUp(){

        MockitoAnnotations.initMocks(this);
        userBeforeUpdate = new User("User User","username","dani2713", Role.USER);
        userAfterUpdate = new User(1L,"user user","username","user2713", Role.USER);
    }

    @Test
    public void givenUsernamePasswordAndName_insert_addUser(){

        when(adminUsersView.getUsername()).thenReturn("username");
        when(adminUsersView.getPassword()).thenReturn("dani2713");
        when(adminUsersView.getName()).thenReturn("User User");
        when(adminUsersView.getOptionMenuRole()).thenReturn(Role.USER);

        adminUsersController.addUser();

        verify(userService).addUser(userBeforeUpdate);

    }

    @Test
    public void givenAllFieldsUser_update_updateUser(){

        when(adminUsersView.getUsername()).thenReturn("username");
        when(adminUsersView.getPassword()).thenReturn("user2713");
        when(adminUsersView.getName()).thenReturn("user user");
        when(adminUsersView.getIdUser()).thenReturn(1L);
        when(adminUsersView.getOptionMenuRole()).thenReturn(Role.USER);

        when(userService.findUserById(1L)).thenReturn(userAfterUpdate);

        adminUsersController.updateUser();

        verify(userService).updateUser(userAfterUpdate);

    }

    @Test
    public void givenIdUser_delete_deleteUser(){

        when(adminUsersView.getIdUser()).thenReturn(1L);
        when(userService.findUserById(1L)).thenReturn(userAfterUpdate);

        adminUsersController.deleteUser();

        verify(userService).deleteUser(userAfterUpdate);

    }

    @Test
    public void givenIdUser_select_selectUser(){
        when(adminUsersView.getIdUser()).thenReturn(1L);
        when(userService.findUserById(1L)).thenReturn(userAfterUpdate);

        adminUsersController.selectUser();

        verify(adminUsersView).setTextAreaUser(userAfterUpdate.toString());
    }



}
