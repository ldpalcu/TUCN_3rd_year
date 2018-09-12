package server;

import model.Action;
import model.Activity;

import javax.inject.Inject;

public class ServerNotificationImpl implements ServerNotification {

    ServerProvider serverProvider;

    @Inject
    public void setServerProvider(ServerProvider serverProvider) {
        this.serverProvider = serverProvider;
    }

    @Override
    public void notifyUsers(Action action, Object object) {
        serverProvider.sendMessage(action, object);
    }
}
