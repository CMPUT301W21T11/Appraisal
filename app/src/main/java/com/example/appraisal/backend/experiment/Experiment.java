package com.example.appraisal.backend.experiment;

import com.example.appraisal.backend.trial.Trial;

import java.lang.reflect.Array;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * This is Experiment object class.
 * It has getters for all values except exp_ID.
 * It has setters for checking status and adding trials.
 */
public class Experiment implements Serializable {
    private String exp_id;
    private String owner;
    private String description;
//     private User owner;
//     // Need a way to store list of contributors
//     private List<User> contributors;
    // Also need a way to tell what is the type of this experiment (can use getClass() method)
    private String type;
    private String rules;
    private String region;
    private Integer minimum_trials;
    private Boolean is_geolocation_required;
    private Boolean is_published;
    private Boolean is_ended;
//    private ArrayList<User> experimenters_list;

    public Experiment(String exp_id, String owner, String description, String type, Boolean is_geolocation_required, Integer minimum_trials, String rules, String region){
        this.exp_id = exp_id;
        this.owner = owner;
//         trial_list = new ArrayList<>();
//         contributors = new ArrayList<>();

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

    public ArrayList<Trial> getTrials() {
        return new ArrayList<>(trial_list);
    }

    public User getOwner() {
        return new User(owner.getID(), owner.getUsername(), owner.getEmail(), owner.getPhoneNumber());
    }

    public void addContributor(User user) {
        contributors.add(user);
    }

    public List<User> getContributors() {
        return new ArrayList<>(contributors);
    }
}

