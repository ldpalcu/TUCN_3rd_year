package server;

import model.Action;
import model.Activity;

public interface ServerNotification {

     void notifyUsers(Action action, Object object);
}
