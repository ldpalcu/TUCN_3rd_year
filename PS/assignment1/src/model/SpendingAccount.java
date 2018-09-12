package model;

import java.util.Date;

public class SpendingAccount extends Account {

    private boolean overdraft;

    public SpendingAccount(long idAccount, double amount, Date date, String typeAccount, long idClient) {
        super(idAccount, amount, date, typeAccount, idClient);
        this.overdraft = false;
    }

    public boolean isOverdraft() {
        return overdraft;
    }

    public void setOverdraft(boolean overdraft) {
        this.overdraft = overdraft;
    }

}
