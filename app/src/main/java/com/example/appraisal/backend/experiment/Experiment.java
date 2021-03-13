package com.example.appraisal.backend.experiment;

import android.os.Parcelable;

import com.example.appraisal.backend.trial.Trial;

import java.io.Serializable;
import java.util.ArrayList;

public class Experiment implements Serializable {
    //private Integer exp_id;       // is this needed?
    private String owner;
    private String description;
    private String type;
    private String rules;
    private String region;
    private Integer minimum_trials;
    private Boolean is_geolocation_required;
    private Boolean is_published;
    private Boolean is_ended;
    private ArrayList<Trial> trial_list;

    public Experiment(String owner, String description, String type, Boolean is_geolocation_required, Integer minimum_trials, String rules, String region){
        this.owner = owner;
        this.description = description;
        this.type = type;
        this.is_geolocation_required = is_geolocation_required;
        this.minimum_trials = minimum_trials;
        this.rules = rules;
        this.region = region;
        this.is_published = true;
        this.is_ended = false;
        trial_list = new ArrayList<>();
    }

    // assumption experiment details cannot be edited after creating it
    // experiment can only be unpublished or ended (only these have setters)
    // all fields have getters

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

    public void addTrial(Trial trial) {
        trial_list.add(trial);
    }

 
    public ArrayList<Trial> getTrials() {
        return trial_list;
    }


}

