package model;

import java.util.Date;

public class EventEmployee {

    private int id;
    private int idUser;
    private String userName;
    private Date dateFrom;
    private String information;

    public EventEmployee(){

    }

    public EventEmployee(int id, int idUser, String userName, Date dateFrom, String information) {
        this.id = id;
        this.idUser = idUser;
        this.userName = userName;
        this.dateFrom = dateFrom;
        this.information = information;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Date getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(Date dateFrom) {
        this.dateFrom = dateFrom;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    @Override
    public String toString() {
        return "EventEmployee{" +
                "id=" + id +
                ", idUser=" + idUser +
                ", userName='" + userName + '\'' +
                ", dateFrom=" + dateFrom +
                ", information='" + information + '\'' +
                '}';
    }
}
