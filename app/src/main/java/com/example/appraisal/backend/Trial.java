package com.example.appraisal.backend;

abstract class Trial {
    protected final User conductor;
    protected final GeoLocation location;
    protected final String parentExperimentID;

    /** Constructor for non-geolocation trials */
    public Trial(User conductor, String parentExperimentID) {
        this.conductor = conductor;
        this.parentExperimentID = parentExperimentID;
        location = null;
    }

    /** Constructor for geolocation trials */
    public Trial(User conductor, String parentExperimentID, GeoLocation location) {
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
