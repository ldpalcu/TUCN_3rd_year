package injector;

import client.Connection;
import client.SecondConnection;
import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Singleton;
import model.User;

import repository.ActivityRepository;
import repository.ActivityRepositoryImpl;
import repository.UserRepository;
import repository.UserRepositoryImpl;
import server.*;

import java.io.IOException;

public class ClientDisneyInjector extends AbstractModule {

    @Override
    protected void configure() {
        bind(UserRepository.class).to(ServerUserRepositoryImpl.class);
        bind(ActivityRepository.class).to(ServerActivityRepositoryImpl.class);
        bind(ServerProvider.class).to(ServerProviderImpl.class);
        bind(ServerNotification.class).to(ServerNotificationImpl.class);
        try {
            bind(Connection.class).toInstance(new Connection());
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            bind(SecondConnection.class).toInstance(new SecondConnection());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    private static Injector injector = null;

    //create only an instance of Injector
    public static Injector create(){
        if (injector == null){
            injector = Guice.createInjector(new ClientDisneyInjector());
        }
        return injector;
    }
}
