import controller.UserController;
import model.Activity;
import model.Category;
import model.Role;
import model.User;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import service.ActivityService;
import service.RegistrationService;
import service.UserService;
import view.UserView;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class UserControllerTests {

    @MockitoAnnotations.Mock
    private UserView userView;

    @MockitoAnnotations.Mock
    private ActivityService activityService;

    @MockitoAnnotations.Mock
    private UserService userService;

    @MockitoAnnotations.Mock
    private RegistrationService registrationService;

    @InjectMocks
    private UserController userController;

    private Activity activity;

    private User user;

    private List<Activity> activities;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        activity = new Activity(1L,"Dinosaur Park","Happy Street", Category.CATEGORYL,100);
        user = new User(1L,"Popa Ada","popada","ada2713", Role.USER);
        activities = new ArrayList<Activity>();
    }


    @Test
    public void selectAllActivities_viewListActivities(){
        activities.add(activity);
        when(activityService.findAllActivities()).thenReturn(activities);

        userController.viewListActivities();

        verify(userView).setTextAreaActivities(activities);

    }

    @Test
    public void registerAsUserForAnActivity(){
        when(userView.getIdActivity()).thenReturn(1L);
        when(userView.getIdUser()).thenReturn(1L);
        when(activityService.findActivityById(1L)).thenReturn(activity);
        when(userService.findUserById(1L)).thenReturn(user);

        userController.register();

        verify(registrationService).register(activity, user);

    }
}
