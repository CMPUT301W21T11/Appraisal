package com.example.appraisal.UI.geolocation;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.firestore.GeoPoint;

import java.util.ArrayList;

/**
 * This class represents a Geo point on the map
 */
public class Geopoints implements Parcelable {
    private double latitude;
    private double longitude;
    private ArrayList<GeoPoint> geolocations;

    public Geopoints(double latitude, double longitude) {
        this.geolocations = new ArrayList<GeoPoint>();
    }

    public static final Creator<Geopoints> CREATOR = new Creator<Geopoints>() {
        @Override
        public Geopoints createFromParcel(Parcel in) {
            return new Geopoints(in);
        }

        @Override
        public Geopoints[] newArray(int size) {
            return new Geopoints[size];
        }
    };

    /**
     * get latitude of the marker
     * @return double -- latitude
     */
    public double getLatitude() {
        return latitude;
    }

    /**
     * set latitiude of the marker
     * @param latitude -- new latitude to set
     */
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    /**
     * get longitude of the marker
     * @return double -- longitude
     */
    public double getLongitude() {
        return longitude;
    }

    /**
     * Set the longitude of the marker
     * @param longitude -- new longitude to set
     */
    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    protected Geopoints(Parcel in) {
        this.latitude = in.readDouble();
        this.longitude = in.readDouble();
    }

    /**
     * Describe the kinds of special objects contained in this Parcelable
     * instance's marshaled representation. For example, if the object will
     * include a file descriptor in the output of {@link #writeToParcel(Parcel, int)},
     * the return value of this method must include the
     * {@link #CONTENTS_FILE_DESCRIPTOR} bit.
     *
     * @return a bitmask indicating the set of special object types marshaled
     * by this Parcelable object instance.
     */
    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * Flatten this object in to a Parcel.
     *
     * @param dest  The Parcel in which the object should be written.
     * @param flags Additional flags about how the object should be written.
     *              May be 0 or {@link #PARCELABLE_WRITE_RETURN_VALUE}.
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(this.latitude);
        dest.writeDouble(this.longitude);
    }



}
