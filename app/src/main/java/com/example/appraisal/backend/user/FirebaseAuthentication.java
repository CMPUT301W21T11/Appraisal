package com.example.appraisal.backend.user;

import android.content.Intent;
import android.util.Log;
import android.widget.Button;

import androidx.annotation.NonNull;

import com.example.appraisal.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import static android.content.ContentValues.TAG;

/**
 * This class responsible for authenticating users
 */
public class FirebaseAuthentication {
    // Authentication

    private static FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private final String BROADCAST_FILTER = "connection_established";

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
