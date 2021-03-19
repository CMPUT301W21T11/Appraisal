package com.example.appraisal.backend.user;

import java.util.ArrayList;

/**
 * Make an experimenter object
 */
public class Experimenter {
    private String id;
    private ArrayList<String> trial_id_list;

    /**
     * Constructor for an experimenter
     * @param id
     */
    public Experimenter(String id){
        this.id = id;
        trial_id_list = new ArrayList<String>();
    }

    /**
     * Get the experimenter's id
     * @return id
     */
    public String getId() {
        return id;
    }

    /**
     * Get the trial list
     * @return an array list of string
     */
    public ArrayList<String> getTrialList() {
        return trial_id_list;
    }

    /**
     * Set the trial list
     * @param trial_list
     */
    public void setTrialList(ArrayList<String> trial_list) {
        this.trial_id_list= trial_list;
    }
}
