package com.example.justmeat.homepage;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.justmeat.R;
import com.example.justmeat.utilities.HttpJsonRequest;
import com.example.justmeat.utilities.MyApplication;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MapFragment extends Fragment implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private GoogleMap mMap;
    private String nomeSupermercato;
    private int range;
    LatLng currentLatLng;
    private ArrayList<Supermercato> listaSupermercati;
    public static final int DEFAULT_ZOOM = 12;

    public MapFragment(LatLng currentLatLng) {
        this.currentLatLng = currentLatLng;
    }
    public MapFragment(LatLng currentLatLng, ArrayList<Supermercato> listaSupermercati){
        this.listaSupermercati = listaSupermercati;
        this.currentLatLng = currentLatLng;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map, container, false);

        ImageView buttonMap = view.findViewById(R.id.map_btn);
        buttonMap.setSelected(true);
        buttonMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MapFragment.this.getActivity().onBackPressed();
            }
        });

        if(listaSupermercati.size() > 3)
            getAllSpmkt();


        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.homepage_map);
        mapFragment.getMapAsync(this);

        return view;
    }

    private void getAllSpmkt() {
        String httpToken = ((HomepageActivity)getActivity()).getHttpToken();
        new HttpJsonRequest(this.getActivity(), "/api/v1/get_supermarkets", Request.Method.GET, httpToken, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                System.out.println(response.toString());
                try {
                    JSONArray results = response.getJSONArray("results");
                    for (int i = 0; i < results.length(); i++){
                        JSONObject supermercato = results.getJSONObject(i);
                        int id = supermercato.getInt("id");
                        String nome = supermercato.getString("name");
                        String indirizzo = supermercato.getString("address");
                        double latitude = supermercato.getDouble("latitude");
                        double longitude = supermercato.getDouble("longitude");
                        int chain = supermercato.getInt("chain");
                        listaSupermercati.add(new Supermercato(id,nome,indirizzo,latitude,longitude,chain));
                    }
                    SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                            .findFragmentById(R.id.homepage_map);
                    mapFragment.getMapAsync(MapFragment.this);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Err connessione", error.toString());
            }
        }).run();
    }

    @Override
    public void onMapReady(GoogleMap map) {
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, DEFAULT_ZOOM));
        map.addMarker( new MarkerOptions()
                .position(currentLatLng)
                .icon(bitmapDescriptFromVector(this.getContext(), R.drawable.usermarker))
        );
        for (int i = 0; i < listaSupermercati.size(); i++ ){
            Supermercato supermercato = listaSupermercati.get(i);
            map.addMarker( new MarkerOptions()
                    .position(new LatLng(supermercato.latitude, supermercato.longitude))
                    .snippet(""+i)
                    .icon(bitmapDescriptFromVector(this.getContext(), R.drawable.ic_marketmarker))
            );
        }
        map.setOnMarkerClickListener(this);
    }

    private BitmapDescriptor bitmapDescriptFromVector(Context context, int vectorResId){
        Drawable vectorDrawable = ContextCompat.getDrawable(context, vectorResId);
        vectorDrawable.setBounds(0, 0, vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight());
        Bitmap bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        vectorDrawable.draw(canvas);
        return  BitmapDescriptorFactory.fromBitmap(bitmap);
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        String httpToken = ((MyApplication)this.getActivity().getApplication()).getHttpToken();
        if(marker.getSnippet() != null) {
            int pos = Integer.parseInt(marker.getSnippet());
            Supermercato supermercato = listaSupermercati.get(pos);
            InfoBottomSheet infoBottomSheet = new InfoBottomSheet(supermercato, httpToken, this.getActivity());
            infoBottomSheet.show(getChildFragmentManager(), "ModalBottomSheet");
        }
        return false;
    }
}
