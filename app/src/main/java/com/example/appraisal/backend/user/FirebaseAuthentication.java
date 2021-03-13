package com.example.appraisal.backend.user;

import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class FirebaseAuthentication {
    // Authentication
    protected FirebaseAuth mAuth;

    public FirebaseAuthentication(){
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
    }

    /**
     * Sign in the user anonymously
     */
    public void sign_in() {
        mAuth.signInAnonymously();
    }

    /**
     * Get the user id
     * @return
     */

    public String get_userID() throws Exception{
        if (mAuth.getCurrentUser() == null) {
            throw new Exception("Current user is null");
        }
        return mAuth.getCurrentUser().getUid();
    }

    /**
     * Get the current user
     * @return
     */

    public FirebaseUser getCurrentUser(){
        return mAuth.getCurrentUser();
    }

    /**
     * Check if the user is logged in, otherwise new user
     * @return
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
