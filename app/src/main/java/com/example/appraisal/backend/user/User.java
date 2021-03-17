package com.example.appraisal.backend.user;

import android.os.Parcel;
import android.os.Parcelable;

public class User implements Parcelable {

    private String ID;

    private String username;
    private String email;
    private String phone_number;
    private Integer num_of_exp;

    public User(String ID, String username, String email, String phone_number) {
        this.ID = ID;
        this.username = username;
        this.email = email;
        this.phone_number = phone_number;
        this.num_of_exp = 0;
    }

    protected User(Parcel in) {
        ID = in.readString();
        username = in.readString();
        email = in.readString();
        phone_number = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getID() {
        return ID;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phone_number;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.ID);
        dest.writeString(this.username);
        dest.writeString(this.email);
        dest.writeString(this.phone_number);
    }

    public Integer getNum_of_exp() {
        return num_of_exp;
    }

    public void setNum_of_exp(Integer num_of_exp) {
        this.num_of_exp = num_of_exp;
    }
}
