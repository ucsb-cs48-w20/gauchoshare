package com.ucsb.integration.MainPage.Profile;

import androidx.fragment.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import com.ucsb.integration.R;

public class userMap extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_map);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        //mMap = googleMap;
        //LatLng TutorialsPoint = new LatLng(21, 57);
        //mMap.addMarker(new
        //        MarkerOptions().position(TutorialsPoint).title("Tutorialspoint.com"));
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(TutorialsPoint));
        mMap = googleMap;

    }
}