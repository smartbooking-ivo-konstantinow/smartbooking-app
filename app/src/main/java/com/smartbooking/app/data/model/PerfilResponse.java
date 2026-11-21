package com.smartbooking.app.data.model;

import com.google.gson.annotations.SerializedName;

public class PerfilResponse {

    private int id;
    private String email;

    private String rol;

    @SerializedName("first_name")
    private String firstName;

    @SerializedName("last_name")
    private String lastName;

    public int getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getRol() {
        return rol;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }
}
