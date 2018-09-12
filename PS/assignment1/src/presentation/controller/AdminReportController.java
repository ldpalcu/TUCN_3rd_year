package presentation.controller;

import business.Bank;
import business.ServiceFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import model.EventEmployee;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class AdminReportController {

    @FXML
    private Button back;
    @FXML
    private TextField nameEmployee;
    @FXML
    private TextField dateFrom;
    @FXML
    private TextField dateTo;
    @FXML
    private Button executeButton;
    @FXML
    private TableView<EventEmployee> table;

    private ObservableList<EventEmployee> data;

    private ServiceFactory serviceFactory;

    public AdminReportController() {
        serviceFactory = new ServiceFactory();
    }

    @FXML
    public void executeReport() throws ParseException {

        String name = nameEmployee.getText();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date newDateFrom = null;
        if (dateFrom.getText().equals("")){
            newDateFrom = df.parse("1970-01-01");
        }
        else{
            newDateFrom = df.parse(dateFrom.getText());
        }

        Date newDateTo = null;
        if (dateTo.getText().equals("")){
            newDateTo = df.parse("1970-01-01");
        }
        else{
            newDateTo = df.parse(dateTo.getText());
        }

        showReports(serviceFactory.getBank().generateReports(name, newDateFrom, newDateTo));
    }

    public void showReports(List<EventEmployee> list){
        table.getItems().setAll(list);
    }

    public void back() throws IOException {
        AnchorPane employeePane = FXMLLoader.load(getClass().getResource("/presentation/view/admin_window.fxml"));
        back.getScene().setRoot(employeePane);
    }

}
