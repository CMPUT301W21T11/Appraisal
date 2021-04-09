package com.example.appraisal.backend.trial;

import androidx.annotation.NonNull;

import org.jetbrains.annotations.NotNull;

/**
 * This enum represents all the possible trial types that can be created
 */
public enum TrialType {
    // The idea of using enum to hold constant variables (instead of public static class) is taken from
    // this stackoverflow thread:
    // URL: https://stackoverflow.com/questions/66066/what-is-the-best-way-to-implement-constants-in-java

    BINOMIAL_TRIAL("Binomial Trials"),
    COUNT_TRIAL("Count-based trials"),
    MEASUREMENT_TRIAL("Measurement Trials"),
    NON_NEG_INT_TRIAL("Non-negative Integer Trials");

    private final String label;

    TrialType(String label) {
        this.label = label;
    }

    /**
     * This method returns the string label of each trial type
     * @return String -- label of the enum trial type
     */
    public String getLabel() {
        return label;
    }

    /**
     * This method returns a TrialType object given a String key
     * It is case insensitive
     *
     * @return TrialType -- the TrialType object representation of the given key
     */
    public static @NotNull TrialType getInstance(@NonNull String key) {
        for (TrialType t: TrialType.values()) {
            if (t.getLabel().equalsIgnoreCase(key.trim())) {
                return t;
            }
        }
        throw new IllegalArgumentException("Error: enum type for "+ key +" Does not exist");
    }
}
