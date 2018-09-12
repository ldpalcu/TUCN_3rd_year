package view;

import com.google.inject.AbstractModule;
import model.User;

import repository.ActivityRepository;
import repository.ActivityRepositoryImpl;
import repository.UserRepository;
import repository.UserRepositoryImpl;

public class DisneyInjector extends AbstractModule {

    @Override
    protected void configure() {
        bind(UserRepository.class).to(UserRepositoryImpl.class);
        bind(ActivityRepository.class).to(ActivityRepositoryImpl.class);
    }
}
