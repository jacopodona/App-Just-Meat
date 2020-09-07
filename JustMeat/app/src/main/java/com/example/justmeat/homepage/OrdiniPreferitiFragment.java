package com.example.justmeat.homepage;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
import com.example.justmeat.homepage.adapter.MieiOrdiniAdapter;
import com.example.justmeat.homepage.adapter.OrdiniPreferitiAdapter;
import com.example.justmeat.utilities.HttpJsonRequest;

import org.json.JSONArray;
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
                                for (int c = 0; c<(response.getJSONArray("results").length()); c++) {
                                    OrdinePreferito ordinePreferito= new OrdinePreferito();
                                    ordinePreferito.setIdOrdinePreferito(Integer.parseInt(response.getJSONArray("results").getJSONObject(c).getString("order_id")));
                                    ordinePreferito.setIdSupermercato(Integer.parseInt(response.getJSONArray("results").getJSONObject(c).getString("supermarket_id")));
                                    ordinePreferito.setNomeSupermercato(response.getJSONArray("results").getJSONObject(c).getString("supermarket_name"));
                                    ordinePreferito.setNomeOrdinePreferito(response.getJSONArray("results").getJSONObject(c).getString("favourite"));

                                    for(int i=0; i<response.getJSONArray("results").getJSONObject(c).getJSONArray("products").length(); i++){
                                        ProdottoOrdinePreferito prodottoOrdinePreferito = new ProdottoOrdinePreferito();
                                        prodottoOrdinePreferito.setId(Integer.parseInt(response.getJSONArray("results").getJSONObject(c).getJSONArray("products").getJSONObject(i).getString("id")));
                                        prodottoOrdinePreferito.setNome(response.getJSONArray("results").getJSONObject(c).getJSONArray("products").getJSONObject(i).getString("name"));
                                        prodottoOrdinePreferito.setPeso(Double.parseDouble(response.getJSONArray("results").getJSONObject(c).getJSONArray("products").getJSONObject(i).getString("weight")));
                                        prodottoOrdinePreferito.setPrezzo(Double.parseDouble(response.getJSONArray("results").getJSONObject(c).getJSONArray("products").getJSONObject(i).getString("price")));
                                        prodottoOrdinePreferito.setQuantità(Integer.parseInt(response.getJSONArray("results").getJSONObject(c).getJSONArray("products").getJSONObject(i).getString("quantity")));
                                        ordinePreferito.getListaProdotti().add(prodottoOrdinePreferito);
                                    }
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
