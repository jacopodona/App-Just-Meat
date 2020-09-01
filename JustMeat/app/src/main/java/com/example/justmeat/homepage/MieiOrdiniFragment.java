package com.example.justmeat.homepage;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.justmeat.R;
import com.example.justmeat.homepage.adapter.MieiOrdiniAdapter;
import com.example.justmeat.utilities.HttpJsonRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class MieiOrdiniFragment extends Fragment {

    private String httpToken;
    private MieiOrdiniAdapter adapter;
    private RecyclerView recyclerView;

    public MieiOrdiniFragment(String httpToken) {
        this.httpToken= httpToken;
        this.adapter= null;
        this.recyclerView= null;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_miei_ordini,container,false);
        this.getDataOrdini(getContext());

        recyclerView = view.findViewById(R.id.homepage_recyclerview_mieiordini);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL,false);

        recyclerView.setLayoutManager(layoutManager);






        return view;
    }

    public void getDataOrdini(Context context){

        final LinkedList ordini= new <MieiOrdini>LinkedList();

        new HttpJsonRequest(context, "/api/v1/get_user_orders", Request.Method.GET, httpToken,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("Orders", response.toString());

                        try {
                            int numElementi=(response.getJSONObject("metadata").getInt("returned"));
                            for(int i=0; i<numElementi;i++){
                                try {



                                    MieiOrdini ordine = new MieiOrdini();
                                    ordine.setNomeSupermercato(response.getJSONArray("results").getJSONObject(i).get("supermarket").toString());
                                    ordine.setIndirizzo(response.getJSONArray("results").getJSONObject(i).get("supermarket_address").toString());
                                    ordine.setStato(response.getJSONArray("results").getJSONObject(i).get("status").toString());
                                    ordine.setNumOrdine(Integer.parseInt(response.getJSONArray("results").getJSONObject(i).get("order_id").toString()));

                                    //Date
                                    SimpleDateFormat format= new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
                                    String date= response.getJSONArray("results").getJSONObject(i).get("pickup_time").toString();
                                    Date dateFormat = format.parse(date);
                                    ordine.setDataOrdine(dateFormat);
                                    //---------------------

                                    ordini.add(ordine);


                                    adapter = new MieiOrdiniAdapter(ordini, getActivity());
                                    recyclerView.setAdapter(adapter);
                                    recyclerView.setNestedScrollingEnabled(false);

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                            }

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
