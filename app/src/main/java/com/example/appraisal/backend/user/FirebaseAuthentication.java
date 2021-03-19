package com.example.appraisal.backend.user;

import android.util.Log;


import androidx.annotation.NonNull;

import com.example.appraisal.model.MainModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.concurrent.Executor;

public class FirebaseAuthentication {
    // Authentication

    protected static FirebaseAuth mAuth;
    protected FirebaseAuth.AuthStateListener authStateListener;

    public FirebaseAuthentication(){
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
    }

    public void sign_out(){
        mAuth.signOut();

    }


    /**
     * Sign in the user anonymously
     */
    public void sign_in() {
        mAuth.signInAnonymously();
    }

    /**
     * Get the user id
     * @return user id
     */
    public String get_userID()  {
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
