package com.john.reference.model;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.room.Room;

import com.john.reference.network.ApiClient;
import com.john.reference.network.ApiService;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserRepository {
    private UserDao userDao;
    private LiveData<List<UserModel>> allUsers;
    private final ExecutorService executorService;
    private ApiService apiService;

    public UserRepository(Application application) {
        AppDatabase database = Room.databaseBuilder(application, AppDatabase.class, "user_database")
                .build();
        userDao = database.userDao();
        allUsers = userDao.getAllUsers();
        executorService = Executors.newFixedThreadPool(2);
        apiService = ApiClient.getClient().create(ApiService.class);
    }

    public void insert(UserModel user) {
        executorService.execute(() -> userDao.insert(user));
    }

    public void update(UserModel user) {
        executorService.execute(() -> userDao.update(user));
    }

    public void delete(UserModel user) {
        executorService.execute(() -> userDao.delete(user));
    }

    public LiveData<List<UserModel>> getAllUsers() {
        // Fetch from API and save to local database
        apiService.getUsers().enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    for (UserModel user : response.body().getUsers()) {
                        executorService.execute(() -> {
                            userDao.insert(user);
                        });

                    }
                }
            }


            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                // Handle API failure
            }
        });
        return allUsers;
    }
}

