package com.example.appraisal.backend.specific_experiment;

/**
 * Object to assist Trial display
 */
// TODO ABOUT TO BE REMOVED
public class ViewTrial {
    private String ID;
    private String outcome;
    private String date;

    public ViewTrial(String ID, String outcome, String date) {
        this.ID = ID;
        this.outcome = outcome;
        this.date = date;
    }

    /**
     * Gets the Id of the Trial
     * @return
     */
    public String getID() {
        return ID;
    }

    /**
     * Gets the outcome fo the Trial
     * @return
     */
    public String getOutcome() {
        return outcome;
    }

    public String getDate() {return date;}
}
