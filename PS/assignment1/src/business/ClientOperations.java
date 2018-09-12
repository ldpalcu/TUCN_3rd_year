package business;

import model.validation.ClientValidator;
import data_access.dao.ClientDAO;
import model.Client;

public class ClientOperations extends Operations{

    private Client client;
    private ClientDAO clientDAO;
    private ClientValidator clientValidator;

    public ClientOperations(){
        this.clientDAO = new ClientDAO();
    }
    public ClientOperations(Client client) {
        this.clientDAO = new ClientDAO();
        this.client = client;
        this.clientValidator = new ClientValidator(client);

    }


    public void update(){
        clientDAO.updateQuery(client, client.getIdClient());
    }

    public void create(){
        if (clientValidator.validateClient()){
            clientDAO.insertQuery(client);
        }else{
            System.out.println(clientValidator.errors.toString());
            client = null;
        }
    }

    public void delete(){
        clientDAO.deleteQuery(client.getIdClient());
    }

    public void viewObject(){
        client = clientDAO.findByName(client.getName());
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public ClientDAO getClientDAO() {
        return clientDAO;
    }

    public void setClientDAO(ClientDAO clientDAO) {
        this.clientDAO = clientDAO;
    }

    public ClientValidator getClientValidator() {
        return clientValidator;
    }

    public void setClientValidator(ClientValidator clientValidator) {
        this.clientValidator = clientValidator;
    }
}
