package com.example.justmeat.homepage;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.justmeat.R;
import com.example.justmeat.homepage.adapter.ListaSupermercatiAdapter;
import com.example.justmeat.utilities.HttpJsonRequest;
import com.google.android.material.button.MaterialButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ListaSupermercatiFragment extends Fragment {

    private String httpToken;
    private RecyclerView recyclerView;
    private ListaSupermercatiAdapter adapter;
    private ArrayList<Supermercato> listaSupermercati;
    private Double latitudine,longitudine;
    private ImageButton searchOnMapsButton;
    private ProgressBar loading;
    private int range;
    private final double latitudinetrento = 46.0793;
    private final double longitudinetrento = 11.1302;

    private static final int REQUEST_CODE_LOCATION_PERMISSION = 1;

    public ListaSupermercatiFragment(String httpToken,Double latitudine, Double longitudine,int range) {
        this.httpToken = httpToken;
        this.adapter = null;
        this.recyclerView = null;
        this.latitudine=latitudine;
        this.longitudine=longitudine;
        this.range=range;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lista_supermercati, container, false);


        listaSupermercati = new ArrayList<>();

        recyclerView = view.findViewById(R.id.homepage_recyclerview_listasupermercati);
        searchOnMapsButton = view.findViewById(R.id.homepage_imagebutton_search_on_maps);
        loading=view.findViewById(R.id.listasupermercati_loading);

        cercaSuperMercati(latitudine,longitudine,range);


        searchOnMapsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //adapter.clear();
                //((HomepageActivity)getActivity()).navigateTo(new MapFragment(),true);
                if(ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED){

                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE_LOCATION_PERMISSION);

                }else{
                    getCurrentLocation();
                }

                /*Intent i = new Intent(getActivity(), MapFragment.class);
                getActivity().startActivity(i);*/

            }


        });


        return view;
    }


    private void setupRecyclerView() {
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new ListaSupermercatiAdapter(listaSupermercati, getActivity());
        recyclerView.setAdapter(adapter);
        loading.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode== REQUEST_CODE_LOCATION_PERMISSION && grantResults.length>0){
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                getCurrentLocation();
            }else{
                //Toast.makeText(getContext(), "permissio denied", Toast.LENGTH_SHORT).show();
                AlertDialog.Builder builder=new AlertDialog.Builder(getContext());
                builder.setTitle("Necessari permessi");
                builder.setMessage("Per il corretto funzionamento di questa funzione sono necessari i permessi al GPS");
                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                AlertDialog dialog=builder.create();
                dialog.show();
            }
        }
    }

    private void getCurrentLocation(){
        Intent i = new Intent(getActivity(), MapFragment.class);
        i.putExtra("lista_supermercati",listaSupermercati);
        i.putExtra("range",range);
        getActivity().startActivity(i);
    }

    public void cercaSuperMercati(Double latitudine,Double longitudine,int range){
        listaSupermercati=new ArrayList<>();
        JSONObject body=new JSONObject();
        Log.e("Details--->", "Richiesta con lat:"+latitudine+" long:"+longitudine+" range:"+range);

        try {
            body.put("latitude", latitudine);
            body.put("longitude", longitudine);
            body.put("range", range);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        new HttpJsonRequest(getContext(), "/api/v1/get_supermarkets_in_range", Request.Method.POST,body, httpToken,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("OK", response.toString());
                        try {
                            JSONArray results=response.getJSONArray("results");
                            for(int i=0;i<results.length();i++){
                                JSONObject supermercato=results.getJSONObject(i);
                                int id=supermercato.getInt("id");
                                String nome=supermercato.getString("name");
                                String indirizzo=supermercato.getString("address");
                                double latitude=supermercato.getDouble("latitude");
                                double longitude=supermercato.getDouble("longitude");
                                listaSupermercati.add(new Supermercato(id,nome,indirizzo,latitude,longitude));
                            }
                            Log.d("Details-->","Supermercati Aggiunti: "+listaSupermercati.size());
                            setupRecyclerView();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Err connessione", error.toString());

                    }
                }).run();
    }
}
