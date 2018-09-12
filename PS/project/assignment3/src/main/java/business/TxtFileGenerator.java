package business;

import model.Activity;
import model.User;
import repository.ActivityRepositoryImpl;
import repository.Factory;
import service.ActivityService;

import javax.inject.Inject;
import javax.swing.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class TxtFileGenerator implements ReportGenerator {

    @Override
    public void generateReport() {

        String userSelectedLoc = null;

        JFrame parentFrame = new JFrame();

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Specify a file to save");

        int userSelection = fileChooser.showSaveDialog(parentFrame);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            userSelectedLoc = fileToSave.getAbsolutePath();
        }

        BufferedWriter out = null;
        try {
            if (userSelectedLoc != null){
                out = new BufferedWriter(new java.io.FileWriter(userSelectedLoc+".txt"));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        ActivityService activityService = new ActivityService();
        activityService.setActivityRepository(new ActivityRepositoryImpl(new Factory()));

        List<Activity> activities = activityService.findAllActivities();
        for (Activity activity:activities){
            try {
                out.write("ACTIVITY");
                out.newLine();
                out.write(activity.showActivity());
                out.newLine();
                /*List<User> users = activity.getUsers();
                System.out.println(users);
                for (User user:users){
                    out.write(user.toString());
                    out.newLine();
                }*/
                out.newLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
