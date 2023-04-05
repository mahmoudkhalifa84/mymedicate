package com.medicate_int.mymedicate.ui;

import androidx.fragment.app.FragmentActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.medicate_int.mymedicate.R;
import com.medicate_int.mymedicate.ui.Login;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    String cord, title;
    float lat, log;
    String def = "32.853370, 13.084861";
    Intent intent;
    private static final String TAG = "MapsActivityTAG";

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.maps);
        intent = getIntent();
        title = intent.getStringExtra("title");
        lat = intent.getFloatExtra("lat", 0);
        log = intent.getFloatExtra("log", 0);
        if (lat == 0 || log == 0) Login.Message(getString(R.string.uknown_error), this);

        Log.d(TAG, "onCreate: LAT > " + lat + "  LOG > " + log);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(GoogleMap googleMap) {
        if (log > 0 && lat > 0) {
            try {
                mMap = googleMap;
                // Add a marker in Sydney and move the camera
                LatLng sydney = new LatLng(lat, log);
                mMap.setContentDescription(title);
                mMap.addMarker(new MarkerOptions().position(sydney).title(title));
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 15f));

            } catch (Exception e) {
                Login.Message(getString(R.string.uknown_error), this);
        /*mMap = googleMap;
        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(32.853370, 13.084861);
        mMap.setContentDescription("موقع شركة ميديكيت ليبيا");
        mMap.setMyLocationEnabled(true);
        mMap.addMarker(new MarkerOptions().position(sydney).title("موقع شركة ميديكيت ليبيا"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 15f));*/
            }
        }
    }
}