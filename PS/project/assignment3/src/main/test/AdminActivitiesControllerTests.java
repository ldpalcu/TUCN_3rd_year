import controller.AdminActivitiesController;
import model.*;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import service.ActivityService;
import view.AdminActivitiesView;
import view.AdminUsersView;
import view.UserView;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class AdminActivitiesControllerTests {

    @Mock
    private ActivityService activityService;

    @Mock
    private AdminActivitiesView userView;

    @InjectMocks
    private AdminActivitiesController adminActivitiesController;

    private Activity activityBeforeUpdate;

    private Activity activityAfterUpdate;

    @Before
    public void setUp(){

        MockitoAnnotations.initMocks(this);
        activityBeforeUpdate = new Activity("Horse Riding","Anim Street",Category.CATEGORYL,100);
        activityAfterUpdate = new Activity(1L,"Horse Riding","Meme Street",Category.CATEGORYL,100);
    }

    @Test
    public void givenAllFields_insert_addUser(){

        when(userView.getName()).thenReturn("Horse Riding");
        when(userView.getCategory()).thenReturn(Category.CATEGORYL);
        when(userView.getLocation()).thenReturn("Anim Street");
        when(userView.getMaxUsers()).thenReturn(100);
        //when(userView.getIdActivity()).thenReturn(1L);

        adminActivitiesController.addActivity();

        verify(activityService).addActivity(activityBeforeUpdate);

    }

    @Test
    public void givenAllFields_update_updateUser(){


        when(userView.getName()).thenReturn("Horse Riding");
        when(userView.getCategory()).thenReturn(Category.CATEGORYL);
        when(userView.getLocation()).thenReturn("Meme Street");
        when(userView.getMaxUsers()).thenReturn(100);
        when(userView.getIdActivity()).thenReturn(1L);
        when(userView.getMenuStateOption()).thenReturn(State.SCHEDULED.toString());

        when(activityService.findActivityById(1L)).thenReturn(activityAfterUpdate);


        adminActivitiesController.updateActivity();

        verify(activityService).updateActivity(activityAfterUpdate);

    }

    @Test
    public void givenId_delete_deleteUser(){
        when(userView.getIdActivity()).thenReturn(1L);
        when(activityService.findActivityById(1L)).thenReturn(activityAfterUpdate);

        adminActivitiesController.deleteActivity();

        verify(activityService).deleteActivity(activityAfterUpdate);

    }

    @Test
    public void givenId_select_selectActivity(){
        when(userView.getIdActivity()).thenReturn(1L);
        when(activityService.findActivityById(1L)).thenReturn(activityAfterUpdate);

        adminActivitiesController.selectActivity();

        verify(userView).setTextAreaActivity(activityAfterUpdate.showActivity());
    }
}
