package com.example.justmeat.homepage;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.justmeat.R;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;

import java.util.Arrays;
import java.util.List;

import static android.app.Activity.RESULT_OK;

public class TrovaSupermercatiFragment extends Fragment {
    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 17;
    int REQUEST_CHECK_SETTINGS = 33;
    private static int AUTOCOMPLETE_REQUEST_CODE = 13;
    LocationRequest request;
    FusedLocationProviderClient fusedLocationProviderClient;
    private String httpToken;
    private int range;

    public TrovaSupermercatiFragment(String httpToken) {
        this.httpToken = httpToken;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_trova_supermercati, container, false);

        Button currentLocation = view.findViewById(R.id.homepage_btn_currentLocation);
        CardView enterLocation = view.findViewById(R.id.homepage_btn_writeLocation);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this.getActivity());

        enterLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                placeAutocomplete();
            }
        });
        currentLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(
                        TrovaSupermercatiFragment.this.getActivity().getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(
                            TrovaSupermercatiFragment.this.getActivity(),
                            new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION
                    );
                } else {
                    getCurrentLocation();
                }
            }
        });

        return view;
    }

    private void getCurrentLocation() {
        request = new LocationRequest()
                .setInterval(10000)
                .setFastestInterval(5000)
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(request);

        SettingsClient client = LocationServices.getSettingsClient(TrovaSupermercatiFragment.this.getActivity());
        Task<LocationSettingsResponse> task = client.checkLocationSettings(builder.build());
        task.addOnSuccessListener(TrovaSupermercatiFragment.this.getActivity(), new OnSuccessListener<LocationSettingsResponse>() {
            @SuppressLint("MissingPermission")
            @Override
            public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
                fusedLocationProviderClient.requestLocationUpdates(request, locationCallback, null);
            }
        });
        task.addOnFailureListener(TrovaSupermercatiFragment.this.getActivity(), new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                if (e instanceof ResolvableApiException){
                    try{
                        ResolvableApiException resolvable = (ResolvableApiException) e;
                        resolvable.startResolutionForResult(TrovaSupermercatiFragment.this.getActivity(), REQUEST_CHECK_SETTINGS);
                    } catch (IntentSender.SendIntentException sendEx){

                    }
                }
            }
        });
    }

    private LocationCallback locationCallback = new LocationCallback(){
        @Override
        public void onLocationResult(LocationResult locationResult) {
            if (locationResult == null){
                return;
            }
            for (Location location : locationResult.getLocations()) {
                ((HomepageActivity) getActivity()).navigateTo(new ListaSupermercatiFragment(httpToken, location.getLatitude(),
                    location.getLongitude()), true);

            }
            fusedLocationProviderClient.removeLocationUpdates(locationCallback);
        }
    };

    private void placeAutocomplete() {
        String apiKey = getString(R.string.maps_api_key);
        if(!Places.isInitialized()){
            Places.initialize(getActivity().getApplicationContext(), apiKey);
        }
        PlacesClient placesClient = Places.createClient(this.getContext());

        List<Place.Field> fields = Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG);

        Intent intent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.FULLSCREEN, fields)
                .setCountry("IT")
                .build(this.getActivity());
        startActivityForResult(intent, AUTOCOMPLETE_REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == AUTOCOMPLETE_REQUEST_CODE) {
            if(resultCode == RESULT_OK) {
                Place place = Autocomplete.getPlaceFromIntent(data);
                ((HomepageActivity) getActivity()).navigateTo(new ListaSupermercatiFragment(httpToken, place.getLatLng().latitude,
                        place.getLatLng().longitude), true);
                System.out.println("selezionato: "+place.getLatLng().toString());
            } else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
                Status status = Autocomplete.getStatusFromIntent(data);
                System.out.println(status.toString());
            }
            return;
        }
        if(requestCode == REQUEST_CHECK_SETTINGS){
            if(resultCode == Activity.RESULT_OK){
                getCurrentLocation();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
