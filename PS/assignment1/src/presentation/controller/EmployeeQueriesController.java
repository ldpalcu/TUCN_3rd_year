package presentation.controller;

import business.AccountOperations;
import business.ClientOperations;
import business.ServiceFactory;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import model.Account;
import model.Client;
import model.SavingAccount;
import model.SpendingAccount;
import model.validation.AccountValidator;
import model.validation.ClientValidator;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class EmployeeQueriesController {

    @FXML
    private Button backButton;
    @FXML
    private ComboBox menuClient;
    @FXML
    private ComboBox menuAccount;
    @FXML
    private TextField idClient;
    @FXML
    private TextField name;
    @FXML
    private TextField cardNumber;
    @FXML
    private TextField cnp;
    @FXML
    private TextField address;
    @FXML
    private TextField idAccount;
    @FXML
    private TextField amount;
    @FXML
    private TextField date;
    @FXML
    private TextField typeAccount;
    @FXML
    private TextField idClientAccount;
    @FXML
    private TextField interestRate;
    @FXML
    private TextField period;
    @FXML
    private TextArea textAreaClient;
    @FXML
    private TextArea textAreaAccount;

    private ServiceFactory serviceFactory;

    public EmployeeQueriesController() {
        this.serviceFactory = new ServiceFactory();
    }

    public void executeClient(){

        int newIdClient;
        if (idClient.getText().equals("")){
            newIdClient = 0;
        }else{
            newIdClient = Integer.parseInt(idClient.getText());
        }
        String newName = name.getText();
        String newCardNumber = cardNumber.getText();
        long newCNP;
        if (cnp.getText().equals("")){
            newCNP = 0;
        }
        else{
            newCNP = Long.parseLong(cnp.getText());
        }
        String newAddress = address.getText();

        String option = menuClient.getSelectionModel().getSelectedItem().toString();

        Client client = new Client(newIdClient,newName,newCardNumber,newCNP,newAddress);

        ClientValidator clientValidator = new ClientValidator(client);

        serviceFactory.getClientOperations().setClient(client);
        serviceFactory.getClientOperations().setClientValidator(clientValidator);
        serviceFactory.getClientOperations().execute(option);
        client = serviceFactory.getClientOperations().getClient();

        textAreaClient.clear();
        if (client!=null) {
            textAreaClient.appendText("Succes\n");
            textAreaClient.appendText(client.toString());
        }else{
            textAreaClient.appendText("Failure\n");
        }

    }

    public void executeAccount() throws ParseException {

        long newIdAccount;

        if (idAccount.getText().equals("")){
            newIdAccount = 0;
        }else{
            newIdAccount = Long.parseLong(idAccount.getText());
        }

        double newAmount;
        if (amount.getText().equals("")){
            newAmount = 0;
        }
        else{
            newAmount = Double.parseDouble(amount.getText());
        }

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date newDate = null;
        if (date.getText().equals("")){
            newDate = df.parse("1970-01-01");
        }
        else{
            newDate = df.parse(date.getText());
        }

        String newTypeAccount = typeAccount.getText();
        long newIdClientAccount;
        if (idClientAccount.getText().equals("")){
            newIdClientAccount = 0;
        }
        else{
            newIdClientAccount = Long.parseLong(idClientAccount.getText());
        }

        double newInterestRate;
        if (interestRate.getText().equals("")){
            newInterestRate = 0;
        }
        else{
            newInterestRate = Double.parseDouble(interestRate.getText());
        }

        int newPeriod;
        if(period.getText().equals("")){
            newPeriod = 0;
        }
        else{
            newPeriod = Integer.parseInt(period.getText());
        }

        Account account;
        if (newTypeAccount.equals("spending")){
            account = new SpendingAccount(newIdAccount, newAmount, newDate, newTypeAccount, newIdClientAccount);
        }else{
            account = new SavingAccount(newIdAccount, newAmount, newDate, newTypeAccount,newInterestRate,newPeriod, newIdClientAccount);
        }

        AccountValidator accountValidator = new AccountValidator(account);
        String option = menuAccount.getSelectionModel().getSelectedItem().toString();

        serviceFactory.getAccountOperations().setAccount(account);
        serviceFactory.getAccountOperations().setAccountValidator(accountValidator);
        serviceFactory.getAccountOperations().execute(option);
        account = serviceFactory.getAccountOperations().getAccount();

        textAreaAccount.clear();
        textAreaAccount.appendText("Succes\n");
        if (account != null) {
            textAreaAccount.appendText("Succes\n");
            textAreaAccount.appendText(account.toString());
        }else{
            textAreaAccount.appendText("Failure\n");
        }



    }

    public void back() throws IOException {
        AnchorPane employeePane = FXMLLoader.load(getClass().getResource("/presentation/view/employee_window.fxml"));
        backButton.getScene().setRoot(employeePane);
    }




}
