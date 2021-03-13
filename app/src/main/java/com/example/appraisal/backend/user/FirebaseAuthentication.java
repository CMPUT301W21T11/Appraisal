package com.example.appraisal.backend.user;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.appraisal.model.MainModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import static android.content.ContentValues.TAG;

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

        mAuth.signInAnonymously();
        String id = get_userID();

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

    public String get_userID(){

        if (mAuth.getCurrentUser() != null) {
            return mAuth.getCurrentUser().getUid();
        }
        else {

            return "None";
        }

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
