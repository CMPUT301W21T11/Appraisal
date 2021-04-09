package com.example.appraisal.backend.user;

import android.os.Parcel;
import android.os.Parcelable;

import org.jetbrains.annotations.NotNull;

/**
 * This class represents a user of the app
 */
public class User implements Parcelable {

    private String Id;
    private String username;
    private String email;
    private String phone_number;
    private Integer num_of_exp;

    public User(String Id, String username, String email, String phone_number) {
        this.Id = Id;
        this.username = username;
        this.email = email;
        this.phone_number = phone_number;
        this.num_of_exp = 0;
    }

    /**
     * Constructor for parcelable
     * @param in -- the parcel
     */
    protected User(Parcel in) {
        Id = in.readString();
        username = in.readString();
        email = in.readString();
        phone_number = in.readString();
    }

    /**
     * Creator method for Parcelable interface
     */
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

    /**
     * This method sets the user id
     *
     * @param id -- the user id as String
     */
    public void setId(String id) {
        this.Id = id;
    }

    /**
     * This method returns the user id
     *
     * @return String -- user id
     */
    public String getId() {
        return Id;
    }

    /**
     * This method returns the user name
     *
     * @return String -- user name
     */
    public String getUsername() {
        return username;
    }

    /**
     * This method returns the email of the user
     *
     * @return String -- email
     */
    public String getEmail() {
        return email;
    }

    /**
     * This method returns the phone user of the user
     *
     * @return String -- phone number as string
     */
    public String getPhoneNumber() {
        return phone_number;
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
    public void writeToParcel(@NotNull Parcel dest, int flags) {
        dest.writeString(this.Id);
        dest.writeString(this.username);
        dest.writeString(this.email);
        dest.writeString(this.phone_number);
    }

    /**
     * This method returns the number of experiment the user participated
     *
     * @return Integer -- number of experiments
     */
    public Integer getNumOfExp() {
        return num_of_exp;
    }

    /**
     * This method sets the number of experiment the user participates
     *
     * @param num_of_exp -- number of experiments
     */
    public void setNumOfExp(Integer num_of_exp) {
        this.num_of_exp = num_of_exp;
    }
}
