package com.example.appraisal.backend.user;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class FirebaseAuthentication {
  // Authentication
  protected FirebaseAuth mAuth;

  public FirebaseAuthentication() {
    // Initialize Firebase Auth
    mAuth = FirebaseAuth.getInstance();
    mAuth.signInAnonymously();
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

  /**
   * Get the current user
   * @return
   */

  public FirebaseUser getCurrentUser() { return mAuth.getCurrentUser(); }

  /**
   * Check if the user is logged in, otherwise new user
   * @return
   */

  public boolean isLoggedIn() {
    if (mAuth.getCurrentUser() == null) {
      return false;
    } else {
      return true;
    }
  }
}
