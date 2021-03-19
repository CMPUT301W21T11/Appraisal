package com.example.appraisal.backend.user;

import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * This class responsible for authenticating users
 */
public class FirebaseAuthentication {
    // Authentication

    private static FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener authStateListener;

    /**
     * Constructor for Firebase Authentication
     */
    public FirebaseAuthentication(){
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
    }

    @Deprecated
    public void signOut(){
        mAuth.signOut();
    }

    /**
     * Sign in the user anonymously
     */
    public void signIn() {
        mAuth.signInAnonymously();
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
