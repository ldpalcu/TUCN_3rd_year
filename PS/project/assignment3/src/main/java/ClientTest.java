import com.google.inject.Guice;
import com.google.inject.Injector;
import injector.ClientDisneyInjector;
import model.Activity;
import model.Category;
import service.ActivityService;

public class ClientTest {

    public static void main(String[] args) {
        Activity activity = new Activity("Horse Riding","loc", Category.CATEGORYM,100);

        Injector injector = ClientDisneyInjector.create();
        ActivityService activityService = injector.getInstance(ActivityService.class);

        //activityService.addActivity(activity);
        System.out.println(activityService.findActivityById(1L));
    }
}
