package com.example.appraisal.model;

public class UserModel {
  String userID;
  String email;
  String phone;

  public UserModel(String userID, String email, String phone) {
    this.userID = userID;
    this.email = email;
    this.phone = phone;
  }

  public String getUserID() { return userID; }

  public void setUserID(String userID) { this.userID = userID; }

  public String getEmail() { return email; }

  public void setEmail(String email) { this.email = email; }

  public String getPhone() { return phone; }

  public void setPhone(String phone) { this.phone = phone; }
}
