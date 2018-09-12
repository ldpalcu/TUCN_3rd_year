package model.validation;

import model.Account;
import model.TypeAccount;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AccountValidator {

    private final Account account;

    public List<String> errors;


    public AccountValidator(Account account) {
        this.account = account;
        errors = new ArrayList<>();
    }

    public boolean validateAccount(){
        validateTypeAccount();
        validateSum();
        return errors.isEmpty();
    }


    public void validateTypeAccount(){
        if (account.getTypeAccount().equals(TypeAccount.SAVING.toString()) ||
                account.getTypeAccount().equals(TypeAccount.SPENDING.toString())){
            return;
        }else{
            errors.add("You introduce the wrong type of account!");
        }
    }

    public void validateSum(){
        if (account.getAmount() < 0){
            errors.add("The amount is less than 0!");
        }
    }
}
