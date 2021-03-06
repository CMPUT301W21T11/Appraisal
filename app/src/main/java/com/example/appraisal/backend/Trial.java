package com.example.appraisal.backend;

import androidx.annotation.Nullable;

abstract class Trial {
    protected final User conductor;
    protected final GeoLocation location;

    public Trial(User conductor, @Nullable GeoLocation location) {
        // if location is null indicates it is a non-geolocation trial
        this.conductor = conductor;
        this.location = location;
    }

    public GeoLocation getGeoLocation() {
        return location;
    }

}
