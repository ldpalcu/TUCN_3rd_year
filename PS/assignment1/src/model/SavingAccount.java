package model;

import java.util.Date;

public class SavingAccount extends Account {

    private double interestRate;
    private int period;

    public SavingAccount(long idAccount, double amount, Date date, String typeAccount, double interestRate, int period, long idClient) {
        super(idAccount, amount, date, typeAccount, idClient);
        this.interestRate = interestRate;
        this.period = period;
    }

    public double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(double interestRate) {
        this.interestRate = interestRate;
    }

    public int getPeriod() {
        return period;
    }

    public void setPeriod(int period) {
        this.period = period;
    }

    public void addInterest(){

        setAmount(getAmount()*Math.pow(1+interestRate,period));

    }

}
