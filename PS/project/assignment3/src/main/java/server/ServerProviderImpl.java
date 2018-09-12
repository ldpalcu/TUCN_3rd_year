package server;

import client.Connection;
import client.SecondConnection;
import model.Action;

import javax.inject.Inject;
import java.io.IOException;
import java.util.Observer;

public class ServerProviderImpl implements ServerProvider {

    private Connection connection;
    private SecondConnection secondConnection;

    @Inject
    public ServerProviderImpl(Connection connection, SecondConnection secondConnection) throws IOException {
        this.connection = connection;
        this.secondConnection = secondConnection;

    }

    public Object sendMessage(Action message, Object object) {
        try {
            return connection.handleMessageFromService(message, object);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }


    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void addObserver(Observer observer) {
        secondConnection.addObservers(observer);
    }

}
