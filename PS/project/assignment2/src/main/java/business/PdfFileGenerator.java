package business;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfDocument;
import com.itextpdf.text.pdf.PdfException;
import com.itextpdf.text.pdf.PdfWriter;
import model.Activity;
import model.User;
import repository.ActivityRepositoryImpl;
import repository.Factory;
import service.ActivityService;

import javax.inject.Inject;
import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class PdfFileGenerator implements ReportGenerator {


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

        Document doc = new Document();

        try {
            PdfWriter.getInstance(doc, new FileOutputStream(userSelectedLoc+".pdf"));
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        doc.open();
        Font font = FontFactory.getFont(FontFactory.COURIER, 16, BaseColor.BLACK);
        Chunk chunk = new Chunk("Hello World", font);

        ActivityService activityService = new ActivityService();
        activityService.setActivityRepository(new ActivityRepositoryImpl(new Factory()));

        List<Activity> activities = activityService.findAllActivities();
        for (Activity activity : activities){
            try {
                doc.add(new Paragraph("ACTIVITY"));
                doc.add(new Paragraph(activity.showActivity(), font));
                doc.add(Chunk.NEWLINE);
                /*List<User> users = activity.getUsers();
                for (User user : users){
                    doc.add(new Paragraph(user.toString(), font));
                    doc.add(Chunk.NEWLINE);
                }*/
            } catch (DocumentException e) {
                e.printStackTrace();
            }
        }

        try {
            doc.add(chunk);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        doc.close();

    }
}
