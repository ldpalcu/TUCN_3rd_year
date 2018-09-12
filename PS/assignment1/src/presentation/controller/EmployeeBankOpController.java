package presentation.controller;

import business.Bank;
import business.ServiceFactory;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class EmployeeBankOpController {

    @FXML
    private Button back;
    @FXML
    private TextField idAccountFrom;
    @FXML
    private TextField idAccountTo;
    @FXML
    private TextField amountTransfer;
    @FXML
    private TextField idClient;
    @FXML
    private TextField idAccount;
    @FXML
    private TextField amountProcess;
    @FXML
    private TextField date;
    @FXML
    private TextField information;
    @FXML
    private Button execute;
    @FXML
    private Button process;
    @FXML
    private TextArea textArea;

    private ServiceFactory serviceFactory;


    public EmployeeBankOpController() {
        this.serviceFactory = new ServiceFactory();
    }

    @FXML
    public void transferMoney(){

        long idFrom = Long.parseLong(idAccountFrom.getText());
        long idTo = Long.parseLong(idAccountTo.getText());
        double amount = Double.parseDouble(amountTransfer.getText());

        serviceFactory.getBank().transferMoney(idFrom, idTo, amount);

    }

    @FXML
    public void processUtilities() throws ParseException {

        int newIdAccount = Integer.parseInt(idAccount.getText());
        int newIdClient = Integer.parseInt(idClient.getText());
        double amount = Double.parseDouble(amountProcess.getText());
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date newDate = null;
        if (date.getText().equals("")){
            newDate = df.parse("1970-01-01");
        }
        else{
            newDate = df.parse(date.getText());
        }
        String info = information.getText();

        serviceFactory.getBank().processUtilities(0, newIdAccount, newIdClient, amount,newDate,info);


    }

    public void back() throws IOException {
        AnchorPane employeePane = FXMLLoader.load(getClass().getResource("/presentation/view/employee_window.fxml"));
        back.getScene().setRoot(employeePane);
    }
}
