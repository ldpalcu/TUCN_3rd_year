package server;

import model.Action;
import model.User;

import java.io.ObjectOutputStream;

public class ServerData {

    private long idServerData;

    private User user;

    private ObjectOutputStream outputStream;

    public ServerData() {
    }

    public ServerData(User user, ObjectOutputStream outputStream) {
        this.user = user;
        this.outputStream = outputStream;
    }

    public User getUser() {
        return user;
    }

    public ObjectOutputStream getOutputStream() {
        return outputStream;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setOutputStream(ObjectOutputStream outputStream) {
        this.outputStream = outputStream;
    }
}
