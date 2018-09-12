package business;

import model.Account;
import model.Bill;
import model.EventEmployee;
import model.User;
import data_access.dao.AccountDAO;
import data_access.dao.BillDAO;
import data_access.dao.EventEmployeeDAO;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Bank {

    AccountDAO accountDAO;
    EventEmployeeDAO eventEmployeeDAO;
    BillDAO billDAO;
    User user;

    public Bank(){
        accountDAO = new AccountDAO();
        eventEmployeeDAO = new EventEmployeeDAO();
        billDAO = new BillDAO();
        user = LoginUser.login();
    }

    public void transferMoney(long idAccountFrom, long idAccountTo, double amount){
        Account accountFrom = accountDAO.findById(idAccountFrom);
        Account accountTo = accountDAO.findById(idAccountTo);

        //check if I can withdraw money from the first account
        double newAmount = accountFrom.getAmount() - amount;
        if (newAmount < 0 ){
            System.out.println("You can't withdraw money from this account!");
            return;
        }
        else{
             accountFrom.setAmount(newAmount);
        }

        //update with the new amount for the first count
        accountDAO.updateQuery(accountFrom,accountFrom.getIdAccount());

        accountTo.setAmount(accountTo.getAmount() + amount);

        //update with the new amount for the second count
        accountDAO.updateQuery(accountTo, accountTo.getIdAccount());

        //DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();

        eventEmployeeDAO.insertQuery(new EventEmployee(0, user.getId(),user.getName(),date,"transfer money"));
    }

    public void processUtilities(int idBill, int idAccount, int idClient, double amount, Date date, String information){

        Bill bill;
        Account account = accountDAO.findByIdClientAndAccount(idAccount, idClient);
        if (account.getAmount() - amount < 0 && account.getTypeAccount().equals("SPENDING")){
            System.out.println("Error! The client doesn't have enough money in the account or you access a SPENDING account!");
        }
        else{

            account.setAmount(account.getAmount() - amount);
            accountDAO.updateQuery(account, account.getIdAccount());
            bill = new Bill(idBill, idClient, idAccount, amount, date, information);

            billDAO.insertQuery(bill);

            eventEmployeeDAO.insertQuery(new EventEmployee(0, user.getId(),user.getName(),date,"process utilities: " + information + " for client " + idClient));

        }

    }

    public List<EventEmployee> generateReports(String nameEmployee, Date dateFrom, Date dateTo){
        ArrayList<EventEmployee> filteredList = new ArrayList<>();

        ArrayList<EventEmployee> list = eventEmployeeDAO.findByUserName(nameEmployee);
        for (EventEmployee eventEmployee : list ){
            if (eventEmployee.getDateFrom().compareTo(dateFrom) >= 0 &&
                    eventEmployee.getDateFrom().compareTo(dateTo) <= 0){
                filteredList.add(eventEmployee);
            }
        }

        FileWriter.writeReport(filteredList);

        return filteredList;
    }




}
