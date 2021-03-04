package com.example.appraisal.backend;

import java.io.Serializable;
import java.util.ArrayList;

public class Experiment implements Serializable {
    private String title;
    private String description;
    private ArrayList<Trial> list_o_trial;

    private final String type;
    private final User owner;
    private final String pid;

    public Experiment(String title, String description, String type, User owner, String pid) {
        this.title = title;
        this.description = description;
        this.owner = owner;
        this.pid = pid;
        this.type = type;

        list_o_trial = new ArrayList<>();
    }

    public void addTrial(Trial t) {
        list_o_trial.add(t);
    }

    public ArrayList<Trial> getTrials() {
        return new ArrayList<>(list_o_trial);
    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) throws Exception{
        if (title.trim().isEmpty()) {
            throw new Exception("Error: Empty title");
        } else {
            this.title = title;
        }
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) throws Exception {
        if (description.trim().isEmpty()) {
            throw new Exception("Error: Empty description");
        } else {
            this.description = description;
        }
    }

    public User getOwner() {
        return owner;
    }

    public String getPid() {
        return pid;
    }

    public String getType() {
        return type;
    }
}
