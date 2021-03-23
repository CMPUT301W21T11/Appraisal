package com.example.appraisal.backend.specific_experiment;

/**
 * Object to assist Trial display
 */
// TODO ABOUT TO BE REMOVED
public class ViewTrial {
    private String ID;
    private String outcome;

    public ViewTrial(String ID, String outcome) {
        this.ID = ID;
        this.outcome = outcome;
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
}
