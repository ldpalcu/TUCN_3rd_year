package model;

import java.util.Date;

/**
 * @author Daniela Palcu, created on 18.03.2018
 *
 * This class represents the model of an account.
 */
public class Account {

    private long idAccount;
    private double amount;
    private Date date;
    private String typeAccount;
    private long idClient;

    //constructor

    public Account(){

    }

    public Account(long idAccount, double amount, Date date, String typeAccount, long idClient) {
        this.idAccount = idAccount;
        this.amount = amount;
        this.date = date;
        this.typeAccount = typeAccount;
        this.idClient = idClient;
    }


    //getter and setter


    public long getIdAccount() {
        return idAccount;
    }

    public void setIdAccount(long idAccount) {
        this.idAccount = idAccount;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getTypeAccount() {
        return typeAccount;
    }

    public void setTypeAccount(String typeAccount) {
        this.typeAccount = typeAccount;
    }

    public long getIdClient() {
        return idClient;
    }

    public void setIdClient(long idClient) {
        this.idClient = idClient;
    }

    @Override
    public String toString() {
        return "Account{" +
                "idAccount=" + idAccount +
                ", amount=" + amount +
                ", date=" + date +
                ", typeAccount='" + typeAccount + '\'' +
                ", idClient=" + idClient +
                '}';
    }
}
