package com.example.appraisal.backend.user;

public class User {

    private String ID;
    private String username;
    private String email;
    private String phone_number;

    public User(String ID, String username, String email, String phone_number) {
        this.ID = ID;
        this.username = username;
        this.email = email;
        this.phone_number = phone_number;
    }

    public String getID() {
        return ID;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone_number() {
        return phone_number;
    }
}
