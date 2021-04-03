package com.example.appraisal.backend.experiment;

import com.example.appraisal.backend.trial.Trial;
import com.example.appraisal.backend.trial.TrialType;

public class ExperimentModifier {
    private Trial added_trial;
    private TrialType type;
    private Experiment target_qr;

    public void setTrialType(TrialType type) {
        this.type = type;
    }

    public void setExperiment(Experiment experiment) {
        this.target_qr = experiment;
    }

    /**
     * A binomial binary trial should be encoded as a pair of number "successful-fail"
     * @param value a pair of number split by a "-"
     */
    public void parseBin(String value) {
        String[] bin_val = value.split("-");
    }
}
