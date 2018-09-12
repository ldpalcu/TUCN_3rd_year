package server;

import model.Action;

import java.util.List;
import java.util.Observer;

public interface ServerProvider {

    Object sendMessage(Action message, Object object);

    //Object receiveObject();

    void addObserver(Observer observer);

}
