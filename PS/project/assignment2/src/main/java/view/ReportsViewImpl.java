package view;

import controller.ReportsController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;

public class ReportsViewImpl implements ReportsView {

    @FXML
    private Button backButton;

    @FXML
    private ComboBox menuReport;

    @FXML
    private Button generateReport;

    @FXML
    private AnchorPane reportsPane;

    public void initialize(){

        ReportsController reportsController = new ReportsController(this);
        generateReport.setOnAction(e -> {
            reportsController.generateReport();
        });

    }

    @Override
    public String getOption() {
        return menuReport.getSelectionModel().getSelectedItem().toString();
    }


    //TODO it doesn't work
    @Override
    public void back() {
        AnchorPane mainPane = null;
        URL resource = ClassLoader.getSystemResource("view/admin_window.fxml");
        try {
            mainPane = FXMLLoader.load(resource);
        } catch (IOException e) {
            e.printStackTrace();
        }
        backButton.getScene().setRoot(mainPane);

    }
}
