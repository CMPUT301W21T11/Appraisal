package com.example.appraisal.backend.specific_experiment;

/**
 * Object to assist Trial display
 */
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
     * @return String -- ID of the trial
     */
    public String getID() {
        return ID;
    }

    /**
     * Gets the outcome fo the Trial
     * @return String -- outcome of the trial
     */
    public String getOutcome() {
        return outcome;
    }

    /**
     * Gets the date of the trial
     * @return  String -- date of the trial as string
     */
    public String getDate() {return date;}
}
