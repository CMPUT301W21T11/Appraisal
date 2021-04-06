package com.example.appraisal.backend.specific_experiment;

import android.content.Context;

import com.example.appraisal.R;
import com.example.appraisal.backend.trial.TrialType;

/**
 * This class is for holding the contends of our custom QR codes
 */
public class QRValues {
    private final String signature;
    private final TrialType type;
    private final double value;
    private final String exp_id;
    private final Context parent;

    public QRValues(Context parent, String signature, TrialType type, double value, String exp_id) {
        this.signature = signature;
        this.type = type;
        this.value = value;
        this.exp_id = exp_id;
        this.parent = parent;
    }

    /**
     * Get the signature of the QR
     * @return String -- signature of the QR
     */
    public String getSignature() {
        return signature;
    }

    /**
     * Get the type of the trial stored in this QR code
     * @return {@link TrialType} -- the Trial type of the QR Code
     */
    public TrialType getType() {
        return type;
    }

    /**
     * Get the value of the trial
     * For Binomial trials, 0 represents failure, and 1 represents success
     * @return double -- value of the trial
     */
    public double getValue() {
        return value;
    }

    /**
     * Return the ID of the experiment
     * @return String -- ID of the experiment
     */
    public String getExpId() {
        return exp_id;
    }

    /**
     * Check if the signature of the QR code is compatible with the app
     * @return boolean -- true if compatible, false otherwise
     */
    public boolean checkSignature() {
        String target_signature = parent.getResources().getString(R.string.app_name);
        return getSignature().equalsIgnoreCase(target_signature);
    }
}
