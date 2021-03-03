package com.example.appraisal.model;

import com.example.appraisal.backend.User;

public class MainModel implements SingletonResetable {
    private User current_user;
    private static final MainModel instance = new MainModel();
    private MainModel() {}

    public static MainModel getInstance() {
        return instance;
    }

    public User getCurrent_user() {
        return current_user;
    }

    @Override
    public void hardReset() {

    }

    @Override
    public void setAccess(Boolean b) {

    }
}
