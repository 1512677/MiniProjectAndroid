package com.trungvinh.miniprojectandroid;


import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.List;

import FindDirection.DirectionFinderListener;
import FindDirection.Route;

/**
 * Created by TrungVinh on 5/14/2018.
 */

public class GoogleMapFragment extends FragmentActivity implements OnMapReadyCallback, DirectionFinderListener {
    double lat, lng;
    FloatingActionButton currentLocation, placeLocation, goDirection;
    private GoogleMap mMap;
    private String name;
    private List<Marker> originMarkers = new ArrayList<>();
    private List<Marker> destinationMarkers = new ArrayList<>();
    private List<Polyline> polylinePaths = new ArrayList<>();
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_googlemapfragment);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Intent i = this.getIntent();
        name = i.getStringExtra("name");
        lat = i.getDoubleExtra("lat", 0);
        lng = i.getDoubleExtra("lng", 0);
        Log.e("lat", Double.toString(lat));
        Log.e("lng", Double.toString(lng));

        currentLocation = (FloatingActionButton) findViewById(R.id.myLocationButton);
        currentLocation.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                GoCurrentLocation();
            }
        });

        placeLocation = (FloatingActionButton) findViewById(R.id.yourLocationButton);
        placeLocation.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                GoSearchPlace();
            }
        });
    }

    void GoSearchPlace() {
        // create an animation for map while moving to this location
        mMap.animateCamera(CameraUpdateFactory.zoomTo(18), 2000, null);
        // set some feature of map
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(new LatLng(lat, lng))           // Sets the center of the map to HCMUS
                .zoom(18)                               // Sets the zoom (1<= zoom <= 20)
                .build();
        // do animation to move to this location
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }

    void GoCurrentLocation() {
        // create an animation for map while moving to this location
        mMap.animateCamera(CameraUpdateFactory.zoomTo(18), 2000, null);
        // set some feature of map
        CameraPosition cameraPosition = new CameraPosition.Builder()
                // Sets the center of the map to HCMUS
                .target(new LatLng(LocationService.lastLocation.getLatitude(), LocationService.lastLocation.getLongitude()))
                .zoom(18)                               // Sets the zoom (1<= zoom <= 20)
                .build();
        // do animation to move to this location
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.addMarker(new MarkerOptions()
                .position(new LatLng(lat, lng))
                .title(name)
        );
        mMap.addMarker(new MarkerOptions()
                .position(new LatLng(LocationService.lastLocation.getLatitude(), LocationService.lastLocation.getLongitude()))
                .title("Your current location")
        );
        GoSearchPlace();
    }

    @Override
    public void onDirectionFinderStart() {
        progressDialog = ProgressDialog.show(this, "Please wait.",
                "Finding direction..!", true);

        if (originMarkers != null) {
            for (Marker marker : originMarkers) {
                marker.remove();
            }
        }

        if (destinationMarkers != null) {
            for (Marker marker : destinationMarkers) {
                marker.remove();
            }
        }

        if (polylinePaths != null) {
            for (Polyline polyline : polylinePaths) {
                polyline.remove();
            }
        }
    }

    @Override
    public void onDirectionFinderSuccess(List<Route> routes) {
        progressDialog.dismiss();
        polylinePaths = new ArrayList<>();
        originMarkers = new ArrayList<>();
        destinationMarkers = new ArrayList<>();

        for (Route route : routes) {
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(route.startLocation, 16));
            ((TextView) findViewById(R.id.tvDuration)).setText(route.duration.text);
            ((TextView) findViewById(R.id.tvDistance)).setText(route.distance.text);

            originMarkers.add(mMap.addMarker(new MarkerOptions()
                    .title(route.startAddress)
                    .position(route.startLocation)));
            destinationMarkers.add(mMap.addMarker(new MarkerOptions()
                    .title(route.endAddress)
                    .position(route.endLocation)));

            PolylineOptions polylineOptions = new PolylineOptions().
                    geodesic(true).
                    color(Color.BLUE).
                    width(10);

            for (int i = 0; i < route.points.size(); i++)
                polylineOptions.add(route.points.get(i));

            polylinePaths.add(mMap.addPolyline(polylineOptions));
        }
    }
}
