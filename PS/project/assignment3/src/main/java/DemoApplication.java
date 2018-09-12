import model.Activity;
import model.Category;
import model.Role;
import model.User;
import repository.ActivityRepositoryImpl;
import repository.Factory;
import repository.UserRepositoryImpl;
import service.ActivityService;
import service.UserService;

public class DemoApplication {

        public static void main(String[] args) {
            User user = new User("Palcu Daniela", "ldpalcu","dani2713", Role.ADMIN);
            /*UserService userService = new UserService();
            userService.addUser(user);*/

            UserRepositoryImpl userRepository = new UserRepositoryImpl(new Factory());
            userRepository.persist(user);




            /*User user1 = new User("Popa Ada","popada","ada2713",Role.USER);
            userService.addUser(user1);

            Activity activity = new Activity("Horse Riding","we", Category.CATEGORYL,100);
            ActivityService activityService = new ActivityService();
            activityService.addActivity(activity);*/
            //ActivityService activityService = new ActivityService();
            //Activity activity = activityService.findActivityById(1l);
            //System.out.println(activity.getUsers().toString());
//
//            userService.deleteUser(user1);
            //user.setName("Palcu Dana");
           //userService.updateUser(user);

            /*System.out.println(userService.findUserById(1));

            User user1 = userService.findUserById(8);
            System.out.println(user1);
            user1.setPassword("dana");
            userService.updateUser(user1);
            System.out.println(userService.findUserById(8));*/
            //System.out.println(activityService.findActivityById(1l));



        }
}
