package client;

import javafx.collections.ObservableList;
import model.Action;
import server.Server;
import server.ServerData;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.time.Instant;
import java.util.Observable;
import java.util.Observer;

public class SecondConnection extends Thread {

    private final Socket socket;
    private final ObjectInputStream input;
    private ObservableClient observableObject;

    public SecondConnection() throws IOException
    {
        System.out.println("created_Socket_2");
        this.socket = new Socket("localhost", 4000);
        input = new ObjectInputStream(socket.getInputStream());
        observableObject = new ObservableClient();
        this.start();
    }

    @Override
    public void run()
    {
        //System.out.println("Thread start!");
        try
        {
            while (socket.isConnected())
            {
                // Seems that input.available() is not reliable?
                boolean serverHasData = socket.getInputStream().available() > 0;

                if (serverHasData) {
                    Object object = input.readObject();
                    observableObject.setChanged();
                    observableObject.notifyObservers();
                    observableObject.clearChanged();
                    System.out.println(object.toString());
                }
                try
                {
                    Thread.sleep(500);
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
            }
            System.out.println(Instant.now() + " Server disconnected");
        }
        catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void addObservers(Observer observer){
        observableObject.addObserver(observer);
    }

}
