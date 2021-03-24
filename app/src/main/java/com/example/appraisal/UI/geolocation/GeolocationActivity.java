package com.example.appraisal.UI.geolocation;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.appraisal.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.maps.CameraUpdateFactory;
import com.google.android.libraries.maps.GoogleMap;
import com.google.android.libraries.maps.GoogleMap.OnMyLocationButtonClickListener;
import com.google.android.libraries.maps.GoogleMap.OnMyLocationClickListener;
import com.google.android.libraries.maps.OnMapReadyCallback;
import com.google.android.libraries.maps.SupportMapFragment;
import com.google.android.libraries.maps.model.CameraPosition;
import com.google.android.libraries.maps.model.LatLng;
import com.google.android.libraries.maps.model.Marker;
import com.google.android.libraries.maps.model.MarkerOptions;

import org.jetbrains.annotations.NotNull;

// Location Purposes
// Google Maps Beta (Not the production version)
// Tasks

public class GeolocationActivity extends AppCompatActivity implements
        OnMyLocationButtonClickListener,
        OnMyLocationClickListener,
        OnMapReadyCallback,
        ActivityCompat.OnRequestPermissionsResultCallback,
        GoogleMap.OnMarkerClickListener, GoogleMap.OnMarkerDragListener {
    private GoogleMap mMap;

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;

    private boolean permissionDenied = false;
    private boolean locationPermissionGranted;

    private FusedLocationProviderClient fusedLocationProviderClient;

    // Set up key values for storing activity state
    private static final String KEY_CAMERA_POSITION = "camera_position";
    private static final String KEY_LOCATION = "location";

    private Location lastKnownLocation;

    private LatLng currentLocation;

    private CameraPosition cameraPosition;

    // Default location is near Edmonton City Centre
    private final LatLng defaultLocation = new LatLng(53.546123, -113.493822);
    private static final int DEFAULT_ZOOM = 15;

    private double markerLat;
    private double markerLong;

    // TODO:
    // 1. Add geolocation -> current location -> but marker can be draggable
    // 2. Once they upload -> they can store it on Firestore (Geocode Lat/Long)
    // 3. Extract all the firestore geocoded for all the trials on the experiment and plot them on the map
    // 3b. Specific ID of the experimenter (owner, timestamp, other details)
    // when the user clicks on the marker, it opens a text box

    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Retrieve location and camera position from saved instance state.
//        if (savedInstanceState != null) {
//            lastKnownLocation = savedInstanceState.getParcelable(KEY_LOCATION);
//            cameraPosition = savedInstanceState.getParcelable(KEY_CAMERA_POSITION);
//        }

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        setContentView(R.layout.activity_geolocation);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

//        googleMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(this, R.raw.map_style));

        mMap = googleMap;
        mMap.setOnMyLocationButtonClickListener(this);
        mMap.setOnMyLocationClickListener(this);
        enableMyLocation();

        mMap.setOnMarkerClickListener(this);
//        mMap.setOnInfoWindowClickListener(this);
        mMap.setOnMarkerDragListener(this);
//        mMap.setOnInfoWindowCloseListener(this);
//        mMap.setOnInfoWindowLongClickListener(this);

    }

    /**
     * Enables the My Location layer if the fine location permission has been granted.
     */
    private void enableMyLocation() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            if (mMap != null) {
                // Turns on the blue dot (current location)
                mMap.setMyLocationEnabled(true);
                locationPermissionGranted = true;
                getDeviceLocation();
            }
        } else {
            // Permission to access the location is missing. Show rationale and request permission
            PermissionUtils.requestPermission(this, LOCATION_PERMISSION_REQUEST_CODE,
                    Manifest.permission.ACCESS_FINE_LOCATION, true);
        }
    }


    /**
     * Gets called when the user clicks the crosshair icon
     * @return
     */
    @Override
    public boolean onMyLocationButtonClick() {
//        Toast.makeText(this, "MyLocation button clicked", Toast.LENGTH_SHORT).show();
        return false;
    }

    /**
     * Gets called when user clicks the blue dot on the map
     * @param location
     */
    @Override
    public void onMyLocationClick(@NonNull @NotNull Location location) {
        Toast.makeText(this, "Current location:\n" + location, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode != LOCATION_PERMISSION_REQUEST_CODE) {
            return;
        }
        if (PermissionUtils.isPermissionGranted(permissions, grantResults, Manifest.permission.ACCESS_FINE_LOCATION)) {
            // Enable the my location layer if the permission has been granted.
            enableMyLocation();
        } else {
            // Permission was denied. Display an error message
            // Display the missing permission error dialog when the fragments resume.
            permissionDenied = true;
        }
    }


    @Override
    protected void onResumeFragments() {
        super.onResumeFragments();
        if (permissionDenied) {
            // Permission was not granted, display error dialog.
            showMissingPermissionError();
            permissionDenied = false;
        }
    }

    /**
     * Displays a dialog with error message explaining that the location permission is missing.
     */
    private void showMissingPermissionError() {
        PermissionUtils.PermissionDeniedDialog
                .newInstance(true).show(getSupportFragmentManager(), "dialog");
    }

    /**
     * Get the device's current location
     */
    private void getDeviceLocation() {
        /*
         * Get the best and most recent location of the device, which may be null in rare
         * cases when a location is not available.
         */
        try {
            if (locationPermissionGranted) {
                Task<Location> locationResult = fusedLocationProviderClient.getLastLocation();
                locationResult.addOnCompleteListener(this, new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        if (task.isSuccessful()) {
                            // Set the map's camera position to the current location of the device.
                            lastKnownLocation = task.getResult();
                            Log.d("Latitude", String.valueOf(lastKnownLocation.getLatitude()));
                            Log.d("Longitude", String.valueOf(lastKnownLocation.getLongitude()));
                            if (lastKnownLocation != null) {

                                LatLng currentLocation = new LatLng(lastKnownLocation.getLatitude(),
                                        lastKnownLocation.getLongitude());

                                Marker currentLocationMarker = mMap.addMarker(new MarkerOptions().position(currentLocation).title("You're here").draggable(true));
                                currentLocationMarker.showInfoWindow();

                                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                                        currentLocation, 15));


                            }
                        } else {
                            mMap.moveCamera(CameraUpdateFactory
                                    .newLatLngZoom(defaultLocation, DEFAULT_ZOOM));
                            mMap.getUiSettings().setMyLocationButtonEnabled(false);
                        }
                    }
                });
            }
        } catch (SecurityException e) {
            Log.e("Exception: %s", e.getMessage(), e);
        }
    }

    public void saveMarkerLocation(View v) {
        finish();
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        return false;
    }

    /**
     * Gets activated as soon as the user holds the marker
     *
     * @param marker
     */
    @Override
    public void onMarkerDragStart(Marker marker) {
//        Toast.makeText(this, "User starts to drag me.", Toast.LENGTH_SHORT).show();
    }

    /**
     * Gets activated while user is dragging the marker across the map
     *
     * @param marker
     */
    @Override
    public void onMarkerDrag(Marker marker) {
//        Toast.makeText(this, "I'm being dragged.", Toast.LENGTH_SHORT).show();
    }

    /**
     * Gets activated once the user dropped the marker somewhere else
     *
     * @param marker
     */
    @Override
    public void onMarkerDragEnd(Marker marker) {
        marker.showInfoWindow();
        markerLat = marker.getPosition().latitude;
        markerLong = marker.getPosition().longitude;
        Toast.makeText(this, "Lat: " + String.valueOf(markerLat) + "\n" + "Long: " + String.valueOf(markerLong), Toast.LENGTH_SHORT).show();
    }
}
