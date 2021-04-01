package com.example.appraisal.UI.geolocation;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.appraisal.R;
import com.example.appraisal.backend.experiment.Experiment;
import com.example.appraisal.backend.trial.Trial;
import com.example.appraisal.model.MainModel;
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
import com.google.android.libraries.maps.UiSettings;
import com.google.android.libraries.maps.model.CameraPosition;
import com.google.android.libraries.maps.model.LatLng;
import com.google.android.libraries.maps.model.Marker;
import com.google.android.libraries.maps.model.MarkerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.stream.DoubleStream;

//import com.google.android.gms.maps.SupportMapFragment;

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

    private CollectionReference exp_ref;

    private Experiment current_experiment;

    private UiSettings mUiSettings;

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
//    private static final int MAP_REQUEST_CODE = 0;
//    private static final int PLOT_TRIALS_REQUEST_CODE = 1;

    private String flag;

    private Button save_geolocation_btn;

    // TODO:
    // 1. Add geolocation -> current location -> but marker can be draggable
    // 2. Once they upload -> they can store it on Firestore (Geocode Lat/Long)
    // 3. Extract all the firestore geocoded for all the trials on the experiment and plot them on the map
    // 3b. Specific ID of the experimenter (owner, timestamp, other details)
    // when the user clicks on the marker, it opens a text box

    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        flag = intent.getStringExtra("Map Request Code");


        try {
            current_experiment = MainModel.getCurrentExperiment();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            exp_ref = MainModel.getExperimentReference();
        } catch (Exception e) {
            e.printStackTrace();
        }



        // Retrieve location and camera position from saved instance state.
        if (savedInstanceState != null) {
            lastKnownLocation = savedInstanceState.getParcelable(KEY_LOCATION);
            cameraPosition = savedInstanceState.getParcelable(KEY_CAMERA_POSITION);
        }


        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        setContentView(R.layout.activity_geolocation);

        save_geolocation_btn = findViewById(R.id.save_geo_btn);

        if (flag.equals("User Location")) {
//            Log.d("Loc", "User Location");
        } else if (flag.equals("Plot Trials Map")) {
//            Log.d("Loc", "Plot Trials");
            save_geolocation_btn.setVisibility(View.GONE);
        }


        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onMapReady(GoogleMap googleMap) {

//        googleMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(this, R.raw.map_style));

        mMap = googleMap;

        mUiSettings = mMap.getUiSettings();
        mUiSettings.setCompassEnabled(true);
        mUiSettings.setZoomControlsEnabled(true);
        mUiSettings.setZoomGesturesEnabled(true);
        mMap.setOnMyLocationButtonClickListener(this);
        mMap.setOnMyLocationClickListener(this);

        if (flag.equals("User Location")) {
            enableMyLocation();
        } else if (flag.equals("Plot Trials Map")) {
            // Fetch firebase
            // Plot the markers
//            mockPlotTrialMarkers();
            getAllGeoLocations();
        }


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
     *
     * @return
     */
    @Override
    public boolean onMyLocationButtonClick() {
//        Toast.makeText(this, "MyLocation button clicked", Toast.LENGTH_SHORT).show();
        return false;
    }

    /**
     * Gets called when user clicks the blue dot on the map
     *
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

                                markerLat = lastKnownLocation.getLatitude();
                                markerLong = lastKnownLocation.getLongitude();

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
        Intent intent = new Intent();
        CurrentMarker marker = new CurrentMarker(markerLat, markerLong);
        intent.putExtra("currentMarker", marker);
        setResult(RESULT_OK, intent);
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
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(marker.getPosition().latitude, marker.getPosition().longitude)));
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
//        Toast.makeText(this, "Lat: " + String.valueOf(markerLat) + "\n" + "Long: " + String.valueOf(markerLong), Toast.LENGTH_SHORT).show();
    }

    /**
     * Draw multiple markers on the map
     *
     * @param trials
     */

    private void drawMultipleMarkers(ArrayList<Trial> trials) {
        for (Trial trial : trials) {
            mMap.addMarker(new MarkerOptions().position(defaultLocation).title("Trial").snippet("I am a trial"));
        }
    }

    private void drawMarker(double latitude, double longitude, String title, String snippet) {
        mMap.addMarker(new MarkerOptions().position(new LatLng(latitude, longitude)).title(title).snippet(snippet));
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    private void mockPlotTrialMarkers() {
        // Edmonton Landmarks Latitudes/Longitudes
        double[] latitudes = {53.546123, 53.5627, 53.5225, 53.5232, 53.5442};
        double[] longitudes = {-113.493822, -113.5055, -113.6242, -113.5263, -113.4925};
        String[] names = {"City Centre", "Kingsway Mall", "West Edmonton Mall", "University of Alberta", "Government of Alberta"};
        String[] snippets = {"Mall", "Mall", "Mall", "University", "Government"};
        for (int i = 0; i < 5; i++) {
            drawMarker(latitudes[i], longitudes[i], names[i], snippets[i]);
        }
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(average(latitudes), average(longitudes)), 10));

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private double average(double[] nums){
        double avg = 1.0d* DoubleStream.of(nums).sum() / nums.length;
        return avg;
    }

    /**
     * Saved the instance of a map when an activity is paused or the user rotates their devices.
     *
     * @param outState
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if (mMap != null) {
            outState.putParcelable(KEY_CAMERA_POSITION, mMap.getCameraPosition());
            outState.putParcelable(KEY_LOCATION, lastKnownLocation);
        }
        super.onSaveInstanceState(outState);
    }


    private void getAllGeoLocations() {

        exp_ref.document(current_experiment.getExpId()).collection("Trials").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                // clear old list
//                geolocation_list.clear();

                // check each experiment document
                double sum_latitudes = 0;
                double sum_longitudes = 0;

                double count = 0.0;
                double avg_latitudes;
                double avg_longitudes;

                for (QueryDocumentSnapshot doc : value) {

                    // get all the fields of the experiment
                    String trial_ID = doc.getId();
                    GeoPoint trial_geolocation = (GeoPoint) doc.getData().get("geolocation");

                    if (trial_geolocation != null)
                    {
                        drawMarker(trial_geolocation.getLatitude(), trial_geolocation.getLongitude(), "Sample Title", "ABC");
                        sum_latitudes += trial_geolocation.getLatitude();
                        sum_longitudes += trial_geolocation.getLongitude();
                        count = count + 1.0;
                    }
                    // add experiment to the list to display
//                    geolocation_list.add(trial_geolocation);
                }

                avg_latitudes = sum_latitudes / count;
                avg_longitudes = sum_longitudes / count;
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(avg_latitudes, avg_longitudes), 5));
            }
        });
    }

}
