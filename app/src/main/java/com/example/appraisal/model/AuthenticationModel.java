package com.example.appraisal.model;

import com.google.firebase.auth.FirebaseAuth;

public class AuthenticationModel {
  // Authentication
  protected FirebaseAuth mAuth;

  public AuthenticationModel() {
    // Initialize Firebase Auth
    mAuth = FirebaseAuth.getInstance();
  }

  /**
   * Sign in the user anonymously
   */
  public void sign_in() { mAuth.signInAnonymously(); }

  /**
   * Used to sign out a user but will not be available to the user.
   */
  public void sign_out() { mAuth.signOut(); }

  /**
   * Get the user id
   * @return
   */

  public String get_userID() { return mAuth.getCurrentUser().getUid(); }
}
