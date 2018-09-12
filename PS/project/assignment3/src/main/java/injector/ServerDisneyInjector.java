package injector;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import repository.ActivityRepository;
import repository.ActivityRepositoryImpl;
import repository.UserRepository;
import repository.UserRepositoryImpl;
import server.ServerProvider;
import server.ServerProviderImpl;

public class ServerDisneyInjector extends AbstractModule {
    @Override
    protected void configure() {

        bind(ActivityRepository.class).to(ActivityRepositoryImpl.class);
        bind(UserRepository.class).to(UserRepositoryImpl.class);

    }
}
