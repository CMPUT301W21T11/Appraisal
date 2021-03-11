package com.example.appraisal.backend.experiment;

import android.os.Parcel;
import android.os.Parcelable;
import com.example.appraisal.backend.trial.Trial;
import com.example.appraisal.backend.user.User;

import java.util.ArrayList;
import java.util.List;

public class Experiment implements Parcelable {
    private List<Trial> trial_list;

    private String title;
    private String description;
    private User owner;

    public Experiment(String title, String description, User owner) {
        this.title = title;
        this.description = description;
        this.owner = owner;
        trial_list = new ArrayList<>();
    }

    public Experiment(Parcel in) {
        title = in.readString();
        description = in.readString();
        owner = in.readParcelable(User.class.getClassLoader());
    }

    public static final Creator<Experiment> CREATOR = new Creator<Experiment>() {
        @Override
        public Experiment createFromParcel(Parcel in) {
            return new Experiment(in);
        }

        @Override
        public Experiment[] newArray(int size) {
            return new Experiment[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(description);
        dest.writeParcelable(owner, flags);
    }

    public void addTrial(Trial trial) {
        trial_list.add(trial);
    }

}
