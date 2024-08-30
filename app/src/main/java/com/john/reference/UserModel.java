package com.john.reference;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity(tableName = "users")
public class UserModel {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @SerializedName("email")
    private String email;

    @SerializedName("first_name")
    private String first_name;

    @SerializedName("last_name")
    private String last_name;

    @SerializedName("avatar")
    private String avatar;

    // Getters and Setters for all fields...
    int getId() {
        return id;
    }

    void setId(int id) {
        this.id = id;
    }

    String getEmail() {
        return email;
    }

    void setEmail(String email) {
        this.email = email;
    }

    String getFirst_name() {
        return first_name;
    }

    void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    String getLast_name() {
        return last_name;
    }

    void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    String getAvatar() {
        return avatar;
    }

    void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
