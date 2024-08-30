package com.john.reference;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class UserViewModel extends AndroidViewModel {
    private UserRepository repository;
    private LiveData<List<UserModel>> allUsers;

    public UserViewModel(Application application) {
        super(application);
        repository = new UserRepository(application);
        allUsers = repository.getAllUsers();
    }

    public void insert(UserModel user) {
        repository.insert(user);
    }

    public void update(UserModel user) {
        repository.update(user);
    }

    public void delete(UserModel user) {
        repository.delete(user);
    }

    public LiveData<List<UserModel>> getAllUsers() {
        return allUsers;
    }
}

