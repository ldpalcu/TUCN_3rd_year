package client;

import com.google.inject.Singleton;
import model.Action;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.time.Instant;

@Singleton
public class Connection extends Thread
{
    private final Socket socket;
    private final ObjectOutputStream output;
    private final ObjectInputStream input;

    public Connection() throws IOException
    {
        this.socket = new Socket("localhost", 3000);
        output = new ObjectOutputStream(socket.getOutputStream());
        input = new ObjectInputStream(socket.getInputStream());
        this.start();
    }

    @Override
    public void run()
    {
        try
        {
            while (socket.isConnected())
            {
                // Seems that input.available() is not reliable?
                boolean serverHasData = socket.getInputStream().available() > 0;

                if (serverHasData) {

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
        }
    }


    public Object handleMessageFromService(Action message, Object object) throws IOException, ClassNotFoundException {

        output.writeObject(message);
        output.writeObject(object);

        input.readObject();
        return input.readObject();

    }

}
