package com.example.appraisal.UI.geolocation;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.appraisal.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class GeolocationActivity extends AppCompatActivity implements OnMapReadyCallback {
    private GoogleMap mMap;

    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_geolocation);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng sydney = new LatLng(-34, 151);
        Marker sydneyMarker = mMap.addMarker(new MarkerOptions().position(sydney).title("Trial #1").snippet("Success"));
        sydneyMarker.showInfoWindow();
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

        LatLng edmontonLatLng = new LatLng(54,  113.4938);
        Marker edmontonMarker = mMap.addMarker(new MarkerOptions().position(edmontonLatLng).title("Trial #2").snippet("Failure"));
        edmontonMarker.showInfoWindow();
        mMap.moveCamera(CameraUpdateFactory.newLatLng(edmontonLatLng));
    }
}
