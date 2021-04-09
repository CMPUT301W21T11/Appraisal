package com.example.appraisal.model.core;

import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * This class responsible for authenticating users
 * PACKAGE PROTECTED
 */
class FirebaseAuthentication {
    // Authentication

    private static FirebaseAuth mAuth;

    /**
     * Constructor for Firebase Authentication
     */
    public FirebaseAuthentication(){
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
    }

    /**
     * Instantiate a unique ID if the user login for the first time
     * @return user id
     */
    public String getUserID()  {
        if (mAuth.getCurrentUser() != null) {
            return mAuth.getCurrentUser().getUid();
        }
        else{
            return "None";
        }
    }

    /**
     * Get the current user
     * @return the current firebase user
     */
    public FirebaseUser getCurrentUser(){
        return mAuth.getCurrentUser();
    }

    /**
     * Check if the user is logged in, otherwise new user
     * @return if user is logged in
     */
    public boolean isLoggedIn(){
        if (mAuth.getCurrentUser() == null){
            Log.d("isLoggedIn", "returns false");
            return false;
        } else {
            Log.d("isLoggedIn", "returns true");
            return true;
        }
    }

}
