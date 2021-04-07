package com.example.appraisal.UI.geolocation;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * This class represents a current marker on the map
 */
public class CurrentMarker implements Parcelable {
    private double latitude;
    private double longitude;

    public CurrentMarker(double latitude, double longitude){
        this.latitude = latitude;
        this.longitude = longitude;
    }

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

    protected CurrentMarker(Parcel in) {
        this.latitude = in.readDouble();
        this.longitude = in.readDouble();
    }
    
    public static final Creator<CurrentMarker> CREATOR = new Creator<CurrentMarker>() {
        @Override
        public CurrentMarker createFromParcel(Parcel in) {
            return new CurrentMarker(in);
        }

        @Override
        public CurrentMarker[] newArray(int size) {
            return new CurrentMarker[size];
        }
    };

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
