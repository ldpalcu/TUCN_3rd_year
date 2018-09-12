package business;

import model.validation.AccountValidator;
import data_access.dao.AccountDAO;
import model.Account;

public class AccountOperations extends Operations {

    private Account account;
    private AccountDAO accountDAO;
    private AccountValidator accountValidator;

    public AccountOperations(){
        this.accountDAO = new AccountDAO();
    }

    public AccountOperations(Account account) {
        this.account = account;
        this.accountDAO = new AccountDAO();
        this.accountValidator = new AccountValidator(account);
    }

    public void update(){
        accountDAO.updateQuery(account, account.getIdAccount());
    }

    public void create(){
        if (accountValidator.validateAccount()){
            accountDAO.insertQuery(account);
        }
        else{
            System.out.println(accountValidator.errors.toString());
            account = null;
        }

    }

    public void delete(){
        accountDAO.deleteQuery(account.getIdAccount());
    }

    public void viewObject(){
        account = accountDAO.findById(account.getIdAccount());
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public AccountValidator getAccountValidator() {
        return accountValidator;
    }

    public void setAccountValidator(AccountValidator accountValidator) {
        this.accountValidator = accountValidator;
    }

}
