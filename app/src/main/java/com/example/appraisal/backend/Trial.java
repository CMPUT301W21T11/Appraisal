package com.example.appraisal.backend;

import androidx.annotation.Nullable;

abstract class Trial {
    protected final User conductor;
    protected final GeoLocation location;
    protected final String parentExperimentID;

    public Trial(User conductor, String parentExperimentID, @Nullable GeoLocation location) {
        // if location is null indicates it is a non-geolocation trial
        this.conductor = conductor;
        this.parentExperimentID = parentExperimentID;
        this.location = location;
    }

    public String getParentExperimentID() {
        return parentExperimentID;
    }

    public GeoLocation getGeoLocation() {
        return location;
    }

}
