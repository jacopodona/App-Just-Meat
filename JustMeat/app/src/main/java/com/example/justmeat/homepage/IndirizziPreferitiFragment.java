package com.example.justmeat.homepage;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.justmeat.R;
import com.example.justmeat.homepage.adapter.IndirizziPreferitiAdapter;
import com.example.justmeat.homepage.adapter.MieiOrdiniAdapter;
import com.example.justmeat.homepage.adapter.OrdiniPreferitiAdapter;
import com.example.justmeat.utilities.HttpJsonRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class IndirizziPreferitiFragment extends Fragment {

    private String httpToken;
    private IndirizziPreferitiAdapter adapter;
    private RecyclerView recyclerView;

    public IndirizziPreferitiFragment(String httpToken) {
        this.httpToken = httpToken;
        adapter = null;
        recyclerView = null;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_indirizzi_preferiti, container, false);
        this.getIndirizziPreferiti();


        recyclerView = view.findViewById(R.id.homepage_recyclerview_indirizzipreferiti);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);

        recyclerView.setLayoutManager(layoutManager);


        ImageView imageView = view.findViewById(R.id.homepage_imageview_plusbutton_indirizzi);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((HomepageActivity) getActivity()).navigateTo(new AggiungiIndirizzoPreferitoFragment(httpToken), true);
            }
        });

        return view;
    }

    public void getIndirizziPreferiti() {
        final ArrayList ordiniPreferitiList = new ArrayList<JSONObject>();
        new HttpJsonRequest(getContext(), "/api/v1/get_favourite_addresses", Request.Method.GET, httpToken,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("OK", response.toString());
                        LinkedList<IndirizzoPreferito> listaIndirizziPreferiti = new LinkedList();

                        try {
                            for (int c = 0; c < (response.getJSONArray("results").length()); c++) {
                                IndirizzoPreferito indirizzoPreferito = new IndirizzoPreferito();
                                indirizzoPreferito.setIndirizzo(response.getJSONArray("results").getJSONObject(c).getString("address"));
                                indirizzoPreferito.setNome(response.getJSONArray("results").getJSONObject(c).getString("name"));
                                indirizzoPreferito.setLatitude(Double.parseDouble(response.getJSONArray("results").getJSONObject(c).getString("latitude")));
                                indirizzoPreferito.setLongitude(Double.parseDouble(response.getJSONArray("results").getJSONObject(c).getString("longitude")));
                                indirizzoPreferito.setId(Integer.parseInt(response.getJSONArray("results").getJSONObject(c).getString("id")));


                                listaIndirizziPreferiti.add(indirizzoPreferito);
                            }

                            adapter = new IndirizziPreferitiAdapter(listaIndirizziPreferiti, getActivity());
                            recyclerView.setAdapter(adapter);
                            recyclerView.setNestedScrollingEnabled(false);

                        } catch (Exception e) {
                            Log.e("Err Ordini prefe", e.toString());
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
