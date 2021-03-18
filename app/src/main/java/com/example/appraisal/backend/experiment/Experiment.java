package com.example.appraisal.backend.experiment;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.appraisal.backend.trial.BinomialTrial;
import com.example.appraisal.backend.trial.CountTrial;
import com.example.appraisal.backend.trial.Trial;
import com.example.appraisal.backend.user.Experimenter;

import java.util.ArrayList;

/**
 * This is Experiment object class.
 * It has getters for all values except exp_ID.
 * It has setters for checking status and adding trials.
 */
public class Experiment implements Parcelable {
    private String exp_id;
    private String owner;
    private String description;
    // Need a way to store list of contributors
    private ArrayList<Experimenter> experimenters;
    private ArrayList<String> trial_id_list;
    private ArrayList<Trial> trial_list;

    // Also need a way to tell what is the type of this experiment (can use getClass() method)
    private String type;
    private String rules;
    private String region;
    private Integer minimum_trials;
    private Boolean is_geolocation_required;
    private Boolean is_published;
    private Boolean is_ended;

    public Experiment(String exp_id, String owner, String description, String type, Boolean is_geolocation_required, Integer minimum_trials, String rules, String region){
        this.exp_id = exp_id;
        this.owner = owner;
        this.trial_id_list = new ArrayList<>();
        this.trial_list = new ArrayList<>();
        this.experimenters = new ArrayList<>();

        this.description = description;
        this.type = type;
        this.is_geolocation_required = is_geolocation_required;
        this.minimum_trials = minimum_trials;
        this.rules = rules;
        this.region = region;
        this.is_published = true;
        this.is_ended = false;
//        experimenters_list = new ArrayList<>();
    }

    protected Experiment(Parcel in) {
        exp_id = in.readString();
        owner = in.readString();
        description = in.readString();
        type = in.readString();
        rules = in.readString();
        region = in.readString();
        if (in.readByte() == 0) {
            minimum_trials = null;
        } else {
            minimum_trials = in.readInt();
        }
        byte tmpIs_geolocation_required = in.readByte();
        is_geolocation_required = tmpIs_geolocation_required == 0 ? null : tmpIs_geolocation_required == 1;
        byte tmpIs_published = in.readByte();
        is_published = tmpIs_published == 0 ? null : tmpIs_published == 1;
        byte tmpIs_ended = in.readByte();
        is_ended = tmpIs_ended == 0 ? null : tmpIs_ended == 1;
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

    public String getExp_id() {
        return exp_id;
    }

    public String getOwner() {
        return owner;
    }

    public String getDescription() {
        return description;
    }

    public String getType() {
        return type;
    }

    public String getRegion() {
        return region;
    }

    public String getRules(){
        return rules;
    }

    public Integer getMinimum_trials() {
        return minimum_trials;
    }

    public Boolean getIs_geolocation_required() {
        return is_geolocation_required;
    }

    public Boolean getIs_published() {
        return is_published;
    }

    public void setIs_published(Boolean is_published) {
        this.is_published = is_published;
    }

    public Boolean getIs_ended() {
        return is_ended;
    }

    public void setIs_ended(Boolean is_ended) {
        this.is_ended = is_ended;
 
    }

    public ArrayList<String> getTrials() {

        for(Experimenter experimenter: experimenters){
            ArrayList<String> trials = experimenter.getTrial_list();
            for (String trial: trials){
                trial_id_list.add(trial);
            }
        }
        return trial_id_list;
    }


    public ArrayList<Trial> getTrialList() {
        trial_list.add(new CountTrial());
        trial_list.add(new CountTrial());
        return trial_list;
    }

    public void addTrial(Trial trial) {
        trial_list.add(trial);
    }

    public ArrayList<Experimenter> getExperimenters() {
        return experimenters;
    }

    /**
     * Describe the kinds of special objects contained in this Parcelable
     * instance's marshaled representation. For example, if the object will
     * include a file descriptor in the output of {@link #writeToParcel(Parcel, int)},
     * the return value of this method must include the
     * {@link #CONTENTS_FILE_DESCRIPTOR} bit.
     *
     * @return a bitmask indicating the set of special object types marshaled
     * by this Parcelable object instance.
     */
    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * Flatten this object in to a Parcel.
     *
     * @param dest  The Parcel in which the object should be written.
     * @param flags Additional flags about how the object should be written.
     *              May be 0 or {@link #PARCELABLE_WRITE_RETURN_VALUE}.
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(exp_id);
        dest.writeString(owner);
        dest.writeString(description);
        dest.writeString(type);
        dest.writeString(rules);
        dest.writeString(region);
        if (minimum_trials == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(minimum_trials);
        }
        dest.writeByte((byte) (is_geolocation_required == null ? 0 : is_geolocation_required ? 1 : 2));
        dest.writeByte((byte) (is_published == null ? 0 : is_published ? 1 : 2));
        dest.writeByte((byte) (is_ended == null ? 0 : is_ended ? 1 : 2));
    }
}

