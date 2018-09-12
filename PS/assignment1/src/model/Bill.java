package model;

import java.util.Date;

public class Bill {

    private int idBill;
    private int client;
    private int account;
    private double amount;
    private Date date;
    private String information;

    //constructor

    public Bill(int idBill, int client, int account, double amount, Date date, String information) {
        this.idBill = idBill;
        this.client = client;
        this.account = account;
        this.amount = amount;
        this.date = date;
        this.information = information;
    }


    //getter and setter

    public int getIdBill() {
        return idBill;
    }

    public void setIdBill(int idBill) {
        this.idBill = idBill;
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

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    public int getClient() {
        return client;
    }

    public void setClient(int client) {
        this.client = client;
    }

    public int getAccount() {
        return account;
    }

    public void setAccount(int account) {
        this.account = account;
    }

    @Override
    public String toString() {
        return "Bill{" +
                "idBill=" + idBill +
                ", client=" + client +
                ", account=" + account +
                ", amount=" + amount +
                ", date=" + date +
                ", information='" + information + '\'' +
                '}';
    }
}
