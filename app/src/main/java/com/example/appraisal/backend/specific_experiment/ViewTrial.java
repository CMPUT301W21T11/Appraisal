package com.example.appraisal.backend.specific_experiment;

public class ViewTrial {
    private String ID;
    private String outcome;

    public ViewTrial(String ID, String outcome) {
        this.ID = ID;
        this.outcome = outcome;
    }

    public String getID() {
        return ID;
    }

    public String getOutcome() {
        return outcome;
    }
}
