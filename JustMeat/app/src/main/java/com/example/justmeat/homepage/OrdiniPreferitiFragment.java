package com.example.justmeat.homepage;

import android.content.Context;
import android.os.Bundle;
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
import com.example.justmeat.homepage.adapter.MieiOrdiniAdapter;
import com.example.justmeat.homepage.adapter.OrdiniPreferitiAdapter;
import com.example.justmeat.utilities.HttpJsonRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class OrdiniPreferitiFragment extends Fragment {

    private ArrayList listaOrdiniPreferiti;
    private String httpToken;
    private RecyclerView recyclerView;
    private OrdiniPreferitiAdapter adapter;

    /*public OrdiniPreferitiFragment(ArrayList<JSONObject> listaOrdiniPreferiti) {
        this.listaOrdiniPreferiti= listaOrdiniPreferiti;
    }*/

    public OrdiniPreferitiFragment(String httpToken) {
        this.httpToken= httpToken;
        this.adapter= null;
        this.recyclerView= null;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_ordini_preferiti,container,false);

        List lista= new LinkedList();
        OrdinePreferito m= new OrdinePreferito();
        lista.add(m);
        m= new OrdinePreferito();
        lista.add(m);

        getOrdiniPreferiti(getContext());



        recyclerView = view.findViewById(R.id.homepage_recyclerview_ordinipreferiti);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL,false);

        recyclerView.setLayoutManager(layoutManager);


        adapter =  new OrdiniPreferitiAdapter(lista, getActivity());
        recyclerView.setAdapter(adapter);
        recyclerView.setNestedScrollingEnabled(false);


        return view;
    }

    public void getOrdiniPreferiti(Context context){

        final LinkedList ordini= new <OrdinePreferito>LinkedList();

        new HttpJsonRequest(context, "/api/v1/get_favourite_orders", Request.Method.GET, httpToken,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("Ordini Preferiti", response.toString());
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
