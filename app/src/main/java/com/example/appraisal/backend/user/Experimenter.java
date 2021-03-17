package com.example.appraisal.backend.user;

import com.example.appraisal.backend.trial.Trial;

import java.util.ArrayList;

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

    public ArrayList<String> getTrial_list() {
        return trial_id_list;
    }

    public void setTrial_list(ArrayList<String> trial_list) {
        this.trial_id_list= trial_list;
    }
}
