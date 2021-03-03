package com.example.appraisal.backend;

import java.util.ArrayList;

public class Experiment {
    private String title;
    private String description;
    private ArrayList<Trial> list_o_trail;
    private final User owner;
    private final String pid;

    public Experiment(String title, String description, User owner, String pid) {
        this.title = title;
        this.description = description;
        this.owner = owner;
        this.pid = pid;

        list_o_trail = new ArrayList<>();
    }

    public void addTrail(Trial t) throws Exception {
        if (!t.getParentExperimentID().equals(pid)) {
            throw new Exception("Trail does not belong to this experiment!");
        }
        list_o_trail.add(t);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getOwner() {
        return owner;
    }

    public String getPid() {
        return pid;
    }
}
