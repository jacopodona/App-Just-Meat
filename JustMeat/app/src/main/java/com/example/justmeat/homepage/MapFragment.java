package com.example.justmeat.homepage;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import com.example.justmeat.R;
import com.example.justmeat.homepage.Java.InfoCardMappe;
import com.example.justmeat.homepage.adapter.CardMappaAdaprter;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapFragment extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private String latitudine, longitudine, nomeSupermercato;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.fragment_map);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.homepage_map);
        mapFragment.getMapAsync(this);

        latitudine = getIntent().getStringExtra("Longitudine");
        longitudine = getIntent().getStringExtra("Latitudine");
        nomeSupermercato = getIntent().getStringExtra("NomeSupermercato");




    }

    @Override
    public void onMapReady(GoogleMap googleMap) {



        LocationRequest locationRequest = new LocationRequest();
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(3000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        LocationServices.getFusedLocationProviderClient(MapFragment.this)
                .requestLocationUpdates(locationRequest, new LocationCallback() {

                    @Override
                    public void onLocationResult(LocationResult locationResult) {
                        super.onLocationResult(locationResult);
                        LocationServices.getFusedLocationProviderClient(MapFragment.this)
                                .removeLocationUpdates(this);
                        if(locationResult != null && locationResult.getLocations().size()>0){
                            int latestLocationIndex = locationResult.getLocations().size()-1;
                            double latitude = locationResult.getLocations().get(latestLocationIndex).getLatitude();
                            double longitude = locationResult.getLocations().get(latestLocationIndex).getLongitude();
                            LatLng currentPosition = new LatLng(latitude,longitude);
                            mMap.addMarker(new MarkerOptions()
                                    .position(currentPosition)
                                    .title(nomeSupermercato)
                            );
                            mMap.addCircle(new CircleOptions()
                                    .center(currentPosition)
                                    .radius(10000)
                                    .strokeWidth(10)
                                    .strokeColor(getResources().getColor(R.color.primaryColor))


                            );

                            CameraPosition cameraPosition = new CameraPosition.Builder().target(currentPosition ).zoom(10).build();
                            mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                        }
                    }
                }, Looper.getMainLooper());
        Log.e("LongLat", locationRequest.toString()+"");


        mMap = googleMap;

        CardMappaAdaprter cardMappaAdaprter= new CardMappaAdaprter(this, mMap);
        mMap.setInfoWindowAdapter(cardMappaAdaprter);

        InfoCardMappe infoCardMappe = new InfoCardMappe("aldi","Aldi");

        Marker m = mMap.addMarker(new MarkerOptions().position(new LatLng(Double.parseDouble(longitudine), Double.parseDouble(latitudine))).title(nomeSupermercato)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA)));
        m.setTag(infoCardMappe);

        /*LatLng firenze = new LatLng(Double.parseDouble(longitudine), Double.parseDouble(latitudine));
        mMap.addMarker(new MarkerOptions().position(firenze).title(nomeSupermercato)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA))
        );*/



        /*LatLng randomPosition = new LatLng(45.4323422, 10.9426668);
        mMap.addMarker(new MarkerOptions().position(randomPosition).title("nomeSupermercato"));*/
        /*CameraPosition cameraPosition = new CameraPosition.Builder().target(firenze ).zoom(12).build();
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));*/







    }
}

