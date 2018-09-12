package validator;

import model.Activity;
import model.Category;

import java.util.ArrayList;
import java.util.List;

public class ActivityValidator {

    private final Activity activity;

    public List<String> errors;

    public ActivityValidator(Activity activity) {
        this.activity = activity;
        errors = new ArrayList<>();
    }

    public boolean validateActivity(){

        validateCategory();
        return errors.isEmpty();
    }

    public void validateCategory(){
        if (activity.getCategory().equals(Category.CATEGORYH) || activity.getCategory().equals(Category.CATEGORYL) || activity.getCategory().equals(Category.CATEGORYM)){
            return;
        }else{
            errors.add("You introduce the wrong category!");
        }
    }
}
