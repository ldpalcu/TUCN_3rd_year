package server;


import client.SecondConnection;
import com.google.inject.Guice;
import com.google.inject.Injector;
import injector.ServerDisneyInjector;
import model.Action;
import model.Activity;
import model.User;
import repository.ActivityRepository;
import repository.UserRepository;

import javax.inject.Inject;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Server
{
    static class Connection extends Thread {

        private long lastSentMessageMillis;
        private ActivityRepository activityRepository;
        private UserRepository userRepository;
        private ObjectOutputStream output;
        private ObjectInputStream input;
        private ObjectOutputStream outputNotify;
        private Socket clientSocket;
        private List<ServerData> connectedUsers;


        @Inject
        public void setActivityRepository(ActivityRepository activityRepository) {
            this.activityRepository = activityRepository;
        }

        @Inject
        public void setUserRepository(UserRepository userRepository) {
            this.userRepository = userRepository;
        }


        public Connection(Socket clientSocket, ObjectInputStream input, ObjectOutputStream output, ObjectOutputStream outputNotify, ActivityRepository activityRepository, UserRepository userRepository, List<ServerData> connectedUsers) {
            System.out.println("Thread start server!");
            this.activityRepository = activityRepository;
            this.userRepository = userRepository;
            this.output = output;
            this.input = input;
            this.outputNotify = outputNotify;
            this.clientSocket = clientSocket;
            this.connectedUsers = connectedUsers;
        }

        @Override
        public void run()
        {
            System.out.println("Thread Server is running!");

            try{

                while (clientSocket.isConnected())
                {
                    if (System.currentTimeMillis() - lastSentMessageMillis > 10000)
                    {
                        System.out.println(Instant.now() + " Sending the notification to client");
                        lastSentMessageMillis = System.currentTimeMillis();
                    }

                    // Seems that input.available() is not reliable?
                    boolean clientHasData = clientSocket.getInputStream().available() > 0;
                    if (clientHasData){
                        Action msg = Action.valueOf(input.readObject().toString());
                        Object object = input.readObject();
                        System.out.println(Instant.now() + " Got from client: " + msg + " "+ object.toString());
                        Object receivedObject = handleMessageFromClient(msg, object);
                        output.writeObject(msg);
                        output.writeObject(receivedObject);
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


                System.out.println(Instant.now() + " Client disconnected?");


            }catch (IOException | ClassNotFoundException e){

            }

            // cleanup

        }

        public Object handleMessageFromClient(Action message, Object object){
            if (message.equals(Action.GET_ACTIVITY_BY_ID)){
                long id = (long) object;
                return activityRepository.findById(id);
            }else if (message.equals(Action.ADD_ACTIVITY)){
                Activity activity = (Activity) object;
                activityRepository.persist(activity);
            }else if (message.equals(Action.UPDATE_ACTIVITY)){
                Activity activity = (Activity) object;
                activityRepository.update(activity);

            }else if (message.equals(Action.DELETE_ACTIVITY)){
                Activity activity = (Activity) object;
                activityRepository.delete(activity);

            }else if (message.equals(Action.GET_ACTIVITIES)){
                return activityRepository.findAll();

            }else if (message.equals(Action.ADD_USER)){
                User user = (User) object;
                userRepository.persist(user);

            }else if (message.equals(Action.UPDATE_USER)){
                User user = (User) object;
                userRepository.update(user);

            }else if (message.equals(Action.DELETE_USER)){
                User user = (User) object;
                userRepository.delete(user);

            }else if (message.equals(Action.GET_USER_BY_ID)){
                long id = (long) object;
                return userRepository.findById(id);

            }else if (message.equals(Action.GET_USERS)){
                return userRepository.findAll();

            }else if (message.equals(Action.GET_USER_BY_USERNAME_AND_PASSWORD)){
                List<String> fields = (List<String>) object;
                User user = userRepository.findByUsernameAndPassword(fields.get(0), fields.get(1));
                connectedUsers.add(new ServerData(user, outputNotify));
                return user;
            }else if (message.equals(Action.UNREGISTER)){
                Activity activity = (Activity) object;
                notifyUsers(activity);
            }

            return null;

        }

        public void notifyUsers(Activity activity){
            try {

                for (ServerData serverData : connectedUsers){
                    if (activity.getQueuedUsers().contains(serverData.getUser())){
                        serverData.getOutputStream().writeObject(String.valueOf("Notify"));
                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }


    public static void main(String[] args) throws IOException, ClassNotFoundException
    {
        Injector injector = Guice.createInjector(new ServerDisneyInjector());
        ActivityRepository activityRepository = injector.getInstance(ActivityRepository.class);
        UserRepository userRepository = injector.getInstance(UserRepository.class);

        List<ServerData> connectedUsers = new ArrayList<>();

        try (ServerSocket firstSocket = new ServerSocket(3000);
           ServerSocket secondSocket = new ServerSocket(4000))
        {
            while (true)
            {
                System.out.println(Instant.now() + " Waiting for client...");
                Socket clientSocket = firstSocket.accept();
                ObjectOutputStream outputStream = new ObjectOutputStream(clientSocket.getOutputStream());
                ObjectInputStream inputStream = new ObjectInputStream(clientSocket.getInputStream());

                Socket notificationSocket = secondSocket.accept();
                ObjectOutputStream notifyOutput = new ObjectOutputStream(notificationSocket.getOutputStream());

                new Connection(clientSocket, inputStream, outputStream, notifyOutput, activityRepository, userRepository,connectedUsers).start();

            }


        }
    }
}

