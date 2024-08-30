package com.john.reference;
import retrofit2.Call;
import retrofit2.http.GET;




public interface ApiService {
    @GET("users")
    Call<UserResponse> getUsers();
}
