package com.example.justmeat.homepage;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.justmeat.R;
import com.example.justmeat.homepage.adapter.ListaSupermercatiAdapter;
import com.example.justmeat.utilities.HttpJsonRequest;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ListaSupermercatiFragment extends Fragment {

    private String httpToken;
    private RecyclerView recyclerView;
    private ListaSupermercatiAdapter adapter;
    private ArrayList<Supermercato> listaSupermercati;
    private LatLng currentLatLng;
    private ImageView searchOnMapsButton;
    ImageView placeholder;
    private ProgressBar loading;
    final  static int range = 10000; // range fisso a 10km
    String selectedLocation = "";
    Toolbar toolbar;

    private static final int REQUEST_CODE_LOCATION_PERMISSION = 1;

    public ListaSupermercatiFragment(String httpToken,Double latitudine, Double longitudine) {
        this.httpToken = httpToken;
        this.adapter = null;
        this.recyclerView = null;
        this.currentLatLng = new LatLng(latitudine, longitudine);
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lista_supermercati, container, false);
        listaSupermercati = new ArrayList<>();

        try{
            Geocoder geocoder = new Geocoder(this.getActivity(), Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(currentLatLng.latitude, currentLatLng.longitude, 1);

            if(addresses.get(0).getThoroughfare() != null){
                selectedLocation += addresses.get(0).getThoroughfare();
            }
            if (addresses.get(0).getSubThoroughfare() != null)
                selectedLocation +=" " + addresses.get(0).getSubThoroughfare() +", ";
            selectedLocation += addresses.get(0).getLocality();
            System.out.println("location: " + selectedLocation);
        } catch (Exception e){
            e.printStackTrace();
        }

        toolbar = ((HomepageActivity)this.getActivity()).toolbar;
        toolbar.setTitle(selectedLocation);

        recyclerView = view.findViewById(R.id.homepage_recyclerview_listasupermercati);
        searchOnMapsButton = view.findViewById(R.id.homepage_imagebutton_search_on_maps);
        searchOnMapsButton.setSelected(false);
        loading=view.findViewById(R.id.listasupermercati_loading);
        searchOnMapsButton.setVisibility(View.INVISIBLE);
        placeholder = view.findViewById(R.id.listasupermercati_img_placeholder);
        placeholder.setVisibility(View.INVISIBLE);

        cercaSuperMercati(range);

        searchOnMapsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((HomepageActivity) getActivity()).navigateTo(new MapFragment(currentLatLng, listaSupermercati), true);
            }


        });
        return view;
    }

    private void setupRecyclerView() {
        loading.setVisibility(View.GONE);
        if(listaSupermercati.size()>0){
            recyclerView.setHasFixedSize(true);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL,false);
            recyclerView.setLayoutManager(layoutManager);
            adapter = new ListaSupermercatiAdapter(listaSupermercati, getActivity(), httpToken);
            recyclerView.setAdapter(adapter);
            recyclerView.setVisibility(View.VISIBLE);
            searchOnMapsButton.setVisibility(View.VISIBLE);
        } else {
            placeholder.setVisibility(View.VISIBLE);
        }

    }

    public void cercaSuperMercati(int range){
        listaSupermercati=new ArrayList<>();
        JSONObject body=new JSONObject();
        Log.e("Details--->", "Richiesta con lat:"+currentLatLng.latitude+" long:"+currentLatLng.longitude+" range:"+range);

        try {
            body.put("latitude", currentLatLng.latitude);
            body.put("longitude", currentLatLng.longitude);
            body.put("range", range);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        new HttpJsonRequest(getContext(), "/api/v1/get_supermarkets_in_range", Request.Method.POST,body, httpToken,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray results=response.getJSONArray("results");
                            for(int i=0;i<results.length();i++){
                                JSONObject supermercato=results.getJSONObject(i);
                                int id=supermercato.getInt("id");
                                String nome=supermercato.getString("name");
                                String indirizzo=supermercato.getString("address");
                                double latitude=supermercato.getDouble("latitude");
                                double longitude=supermercato.getDouble("longitude");
                                int chain = supermercato.getInt("chain");
                                listaSupermercati.add(new Supermercato(id,nome,indirizzo,latitude,longitude,chain));
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
