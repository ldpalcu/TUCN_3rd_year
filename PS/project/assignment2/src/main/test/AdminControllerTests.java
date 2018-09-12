import controller.AdminController;
import controller.UserController;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import view.AdminView;
import view.UserView;


import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class AdminControllerTests {

    @MockitoAnnotations.Mock
    AdminView adminView;

    @InjectMocks
    AdminController adminController;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void chooseView_showActivitiesView(){

        adminController.handleActivities();

        verify(adminView).showActivitiesView();
    }

    @Test
    public void chooseView_showUsersView(){

        adminController.handleUsers();

        verify(adminView).showUsersView();
    }

    @Test
    public void chooseView_showReportsView(){

        adminController.handleReports();

        verify(adminView).showReportsView();

    }
}
