package model;

/**
 * @author Daniela Palcu, created on 18.03.2018
 *
 * This class represents the model of a client.
 */
public class Client {

    private int idClient;
    private String name;
    private String idCardNumber;
    private long CNP;
    private String address;

    //constructor

    public Client(){

    }

    public Client(int idClient, String name, String idCardNumber, long CNP, String address) {
        this.idClient = idClient;
        this.name = name;
        this.idCardNumber = idCardNumber;
        this.CNP = CNP;
        this.address = address;
    }




    //getter and setter

    public int getIdClient() {
        return idClient;
    }

    public void setIdClient(int idClient) {
        this.idClient = idClient;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdCardNumber() {
        return idCardNumber;
    }

    public void setIdCardNumber(String idCardNumber) {
        this.idCardNumber = idCardNumber;
    }

    public long getCNP() {
        return CNP;
    }

    public void setCNP(long CNP) {
        this.CNP = CNP;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "Client{" +
                "idClient=" + idClient +
                ", name='" + name + '\'' +
                ", idCardNumber='" + idCardNumber + '\'' +
                ", CNP=" + CNP +
                ", address='" + address + '\'' +
                '}';
    }
}
