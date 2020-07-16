package com.example.justmeat.homepage;

import android.os.Bundle;
import android.util.Log;

import androidx.fragment.app.FragmentActivity;

import com.example.justmeat.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapFragment extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.fragment_map);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.homepage_map);
        mapFragment.getMapAsync(this);
        Log.e("fuuuuuuuck", "double fuuuuuck");
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        try {
            mMap = googleMap;

            LatLng firenze = new LatLng(43.776366, 11.247822);
            mMap.addMarker(new MarkerOptions().position(firenze).title("Siamo a Firenze!"));
            CameraPosition cameraPosition = new CameraPosition.Builder().target(firenze).zoom(15).build();

            mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

            Log.e("fuuuuuuuck", "double fuuuuuck");

        }catch (Exception e){
            Log.e("Exception",e.toString());
        }

    }
}

