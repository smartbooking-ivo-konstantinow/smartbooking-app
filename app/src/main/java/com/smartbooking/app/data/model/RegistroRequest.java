package com.smartbooking.app.data.model;

import com.google.gson.annotations.SerializedName;

public class RegistroRequest {
    private String email;
    private String password;

    @SerializedName("first_name")
    private String firstName;

    @SerializedName("last_name")
    private String lastName;

    public RegistroRequest(String email, String password, String firstName, String lastName) {
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
    }
}