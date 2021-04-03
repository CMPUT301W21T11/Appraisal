package com.example.appraisal.backend.user;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

/**
 * This class represents a user of the app
 */
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

    protected User(@NonNull Parcel in) {
        ID = in.readString();
        username = in.readString();
        email = in.readString();
        phone_number = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        /**
         * {@inheritDoc}
         */
        @Override
        @NonNull
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        @NonNull
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    /**
     * This method set the user id of the object
     * @param ID -- the user id
     */
    public void setID(String ID) {
        this.ID = ID;
    }

    /**
     * This method returns the id of the user
     * @return String -- id of the user
     */
    public String getID() {
        return ID;
    }

    /**
     * This method returns the user name of the user
     * @return String -- user name of the user
     */
    public String getUsername() {
        return username;
    }

    /**
     * This method returns the email of the user
     * @return String -- email of the user
     */
    public String getEmail() {
        return email;
    }

    /**
     * This method returns the phone number of the user
     * @return String -- phone number as String of the user
     */
    public String getPhoneNumber() {
        return phone_number;
    }

    /**
     * This method get the number of experiments the user have
     * @return Integer -- the number of experiments the user have
     */
    public Integer getNumOfExp() {
        return num_of_exp;
    }

    /**
     * This method sets the number of experiments the user have
     * @param num_of_exp -- the number of experiments the user sets to
     */
    public void setNumOfExp(Integer num_of_exp) {
        this.num_of_exp = num_of_exp;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(this.ID);
        dest.writeString(this.username);
        dest.writeString(this.email);
        dest.writeString(this.phone_number);
    }
}
