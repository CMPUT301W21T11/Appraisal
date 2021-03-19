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

    public String getId() {
        return id;
    }

    public ArrayList<String> getTrialList() {
        return trial_id_list;
    }

    public void setTrialList(ArrayList<String> trial_list) {
        this.trial_id_list= trial_list;
    }
}
