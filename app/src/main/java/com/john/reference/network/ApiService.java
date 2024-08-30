package com.john.reference.network;
import com.john.reference.model.UserResponse;

import retrofit2.Call;
import retrofit2.http.GET;




public interface ApiService {
    @GET("users")
    Call<UserResponse> getUsers();
}
