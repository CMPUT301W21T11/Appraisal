package com.example.appraisal.backend.user;

import com.example.appraisal.backend.trial.Trial;

import java.util.ArrayList;

public class Experimenter {
    private String id;
    private ArrayList<Trial> trial_list;

    public Experimenter(String id){
        this.id = id;
        trial_list = new ArrayList<Trial>();
    }

    public String getId() {
        return id;
    }

    public ArrayList<Trial> getTrial_list() {
        return trial_list;
    }

    public void setTrial_list(ArrayList<Trial> trial_list) {
        this.trial_list = trial_list;
    }
}
