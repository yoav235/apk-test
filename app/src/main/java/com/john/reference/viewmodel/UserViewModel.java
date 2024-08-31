package com.john.reference.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.john.reference.model.UserModel;
import com.john.reference.model.UserRepository;

import java.util.List;

public class UserViewModel extends AndroidViewModel {
    private UserRepository repository;
    private LiveData<List<UserModel>> allUsers;

    public UserViewModel(Application application) {
        super(application);
        repository = new UserRepository(application);
        allUsers = repository.getAllUsers();
    }

    public void insert(String firstName, String lastName, String email, String avatar) {
        repository.insert(firstName, lastName, email, avatar);
    }

    public void update(String firstName, String lastName, String email, String avatar, UserModel selectedUser) {
        repository.update(firstName, lastName, email, avatar, selectedUser);
    }

    public void delete(UserModel user) {
        repository.delete(user);
    }

    public LiveData<List<UserModel>> getAllUsers() {
        return allUsers;
    }
}

