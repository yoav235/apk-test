package com.john.reference.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UserResponse {

    @SerializedName("page")
    private int page;

    @SerializedName("per_page")
    private int perPage;

    @SerializedName("total")
    private int total;

    @SerializedName("total_pages")
    private int totalPages;

    @SerializedName("data")
    private List<UserModel> users;

    // Getters and Setters for all fields...
    int getPage() {
        return page;
    }

    void setPage(int page) {
        this.page = page;
    }

    int getPerPage() {
        return perPage;
    }

    void setPerPage(int perPage) {
        this.perPage = perPage;
    }

    int getTotal() {
        return total;
    }

    void setTotal(int total) {
        this.total = total;
    }

    int getTotalPages() {
        return totalPages;
    }

    void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    List<UserModel> getUsers() {
        return users;
    }

    void addUser(UserModel user) {
        users.add(user);
    }
}
