package com.example.appraisal.backend.user;

import com.example.appraisal.backend.trial.Trial;

import java.util.ArrayList;

/**
 * Make an experimenter object
 */
public class Experimenter {
    private String id;
    private ArrayList<String> trial_id_list;

    public Experimenter(String id){
        this.id = id;
        trial_id_list = new ArrayList<String>();
    }

    /**
     * Get the Id of the Experimenter
     * @return
     */
    public String getId() {
        return id;
    }

    /**
     * Get a list of trial ID's that the Experimenter has done
     * @return
     */
    public ArrayList<String> getTrialList() {
        return trial_id_list;
    }

    /**
     * Update the list of trial ID's
     * @param trial_list
     */
    public void setTrialList(ArrayList<String> trial_list) {
        this.trial_id_list= trial_list;
    }
}
