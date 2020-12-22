package com.example.justmeat.homepage;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.justmeat.R;
import com.example.justmeat.homepage.adapter.OrdiniPreferitiAdapter;
import com.example.justmeat.marketview.ProductItem;
import com.example.justmeat.utilities.HttpJsonRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.LinkedList;

public class OrdiniPreferitiFragment extends Fragment {

    private ArrayList listaOrdiniPreferiti;
    private String httpToken;
    private RecyclerView recyclerView;
    private OrdiniPreferitiAdapter adapter;
    private ProgressBar progressBar;



    public OrdiniPreferitiFragment(String httpToken) {
        this.httpToken= httpToken;
        this.adapter= null;
        this.recyclerView= null;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_ordini_preferiti,container,false);

        progressBar=view.findViewById(R.id.ordinipreferiti_loading);

        getOrdiniPreferiti(getContext());

        recyclerView = view.findViewById(R.id.homepage_recyclerview_ordinipreferiti);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL,false);

        recyclerView.setLayoutManager(layoutManager);

        return view;
    }

    public void getOrdiniPreferiti(Context context){

        final LinkedList ordini= new <OrdinePreferito>LinkedList();

        new HttpJsonRequest(context, "/api/v1/get_favourite_orders", Request.Method.GET, httpToken,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("Ordini Preferiti", response.toString());

                        LinkedList<OrdinePreferito> listaOrdiniPreferiti= new LinkedList();

                            try {
                                JSONArray results = response.getJSONArray("results");
                                for(int pos = 0; pos < results.length(); pos++){
                                    ArrayList<ProductItem> productItems = new ArrayList<>();
                                    JSONObject ordineJSONObject = results.getJSONObject(pos);
                                    int order_id = ordineJSONObject.getInt("order_id");
                                    int supermarket = ordineJSONObject.getInt("supermarket_id");
                                    String nomeSpkmt = ordineJSONObject.getString("supermarket_name");
                                    String favourite = ordineJSONObject.getString("favourite");


                                    JSONArray products = ordineJSONObject.getJSONArray("products");
                                    for (int i = 0; i < products.length(); i++){
                                        JSONObject currentJSONObject = products.getJSONObject(i);

                                        int id = currentJSONObject.getInt("id");
                                        String name = currentJSONObject.getString("name");
                                        int peso = currentJSONObject.getInt("weight");
                                        Double prezzo = currentJSONObject.getDouble("price");
                                        int qt = currentJSONObject.getInt("quantity");

                                        productItems.add(new ProductItem(id, name, prezzo, qt, peso));
                                    }
                                    OrdinePreferito ordinePreferito = new OrdinePreferito(order_id, supermarket, nomeSpkmt, favourite, productItems);
                                    listaOrdiniPreferiti.add(ordinePreferito);
                                }



                            } catch (Exception e){
                                Log.e("Err Ordini prefe", e.toString());
                            }

                        adapter =  new OrdiniPreferitiAdapter(listaOrdiniPreferiti, getActivity());
                        recyclerView.setAdapter(adapter);
                        recyclerView.setNestedScrollingEnabled(false);
                        progressBar.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.VISIBLE);



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
