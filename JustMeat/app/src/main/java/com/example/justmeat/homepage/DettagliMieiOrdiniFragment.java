package com.example.justmeat.homepage;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;
import com.example.justmeat.R;
import com.example.justmeat.homepage.adapter.BuoniAdapter;
import com.example.justmeat.homepage.adapter.ProdottoPrezzoAdapter;
import com.example.justmeat.marketview.ProductItem;
import com.example.justmeat.utilities.HttpJsonRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class DettagliMieiOrdiniFragment  extends Fragment {

    private MieiOrdini ordine;
    private RecyclerView recyclerView;
    private RecyclerView recyclerViewBuoni;
    private ProdottoPrezzoAdapter adapter;
    private BuoniAdapter adapterBuoni;
    private View view;
    private ImageView maps;


    public DettagliMieiOrdiniFragment(MieiOrdini ordine){
        this.ordine= ordine;
        recyclerView=null;
        recyclerViewBuoni=null;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view= inflater.inflate(R.layout.fragment_stato_ordine,container,false);
        this.getOrdine(getContext());
        CustomProgressBar pb;
        pb=view.findViewById(R.id.homepage_customProgressBar);
        //pb.setProgress(36);//test

        TextView statoOrdine= view.findViewById(R.id.homepage_statoordine_textview_stato);
        TextView dataOrdine=view.findViewById(R.id.homepage_statoordine_textview_data);
        ImageView imageSupermercato= view.findViewById(R.id.homepage_statoordine_textview_imgview);
        TextView indirizzoSupermercato = view.findViewById(R.id.homepage_statoordine_textview_indirizzo);
        maps = view.findViewById(R.id.homepage_button_aprisupermercatoinmaps);


        //statoOrdine.setText(ordine.getStato());

        DateFormat dateFormat = new SimpleDateFormat("d MMMM yyyy");
        dataOrdine.setText(dateFormat.format(ordine.getDataOrdine()));


        ImageView logo= view.findViewById(R.id.homepage_statoordine_textview_imgview);
        Glide.with(this.getActivity())
                .load("http://just-feet.herokuapp.com"+"/images/sl_"+ordine.getIdSupermercato()+".jpg")
                .into(logo);


        indirizzoSupermercato.setText(ordine.getIndirizzo());

        switch (ordine.getStato()){
            case "Ricevuto":
                statoOrdine.setText("In preparazione");
                pb.setProgress(5);
                break;
            case "Pronto":
                statoOrdine.setText("Pronto per il Ritiro");
                pb.setProgress(50);
                break;
            case "Ritirato":
                statoOrdine.setText("Ritirato");
                pb.setProgress(100);
                break;
        }

        maps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri gmmIntentUri = Uri.parse("geo:37.7749,-122.4192?q=" + Uri.encode(ordine.getIndirizzo()));
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent);
            }
        });

        recyclerView = view.findViewById(R.id.homepage_statoordine_rv_prod);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL,false);

        recyclerView.setLayoutManager(layoutManager);

        //rec view Buoni
        recyclerViewBuoni = view.findViewById(R.id.homepage_statoordine_rv_coupon);
        recyclerViewBuoni.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManagerBuoni = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL,false);

        recyclerViewBuoni.setLayoutManager(layoutManagerBuoni);

        return view;
    }


    public void getOrdine(Context context){
        final ArrayList<ProductItem> products = new ArrayList<>();
        final ArrayList<Double> coupons = new ArrayList<>();
        System.out.println("/api/v1/get_order/"+ordine.getNumOrdine());

        new HttpJsonRequest(context, "/api/v1/get_order/"+ordine.getNumOrdine(), Request.Method.GET, ((HomepageActivity)getActivity()).getHttpToken(),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("Order", response.toString());

                        try {
                            JSONObject results = response.getJSONObject("results");
                            //Prendo i buoni

                            JSONArray arrayCoupons = results.getJSONArray("coupons");
                            for (int pos = 0; pos < arrayCoupons.length(); pos++) {
                                JSONObject currentCoupon = arrayCoupons.getJSONObject(pos);
                                coupons.add(currentCoupon.getDouble("coupon_discount"));
                            }

                            double subtotale=0;

                            JSONArray arrayProducts = results.getJSONArray("products");
                            for(int pos = 0; pos < arrayProducts.length(); pos++){
                                JSONObject product = arrayProducts.getJSONObject(pos);
                                Double sconto;
                                String name = product.getString("product_name");
                                Double prezzo = product.getDouble("price");
                                int qt = product.getInt("quantity");

                                if(product.optInt("loyalty", 0) == 1){
                                    //TODO  manca un controllo sulla carta
                                     sconto = 0.0;
                                } else {
                                    sconto = product.getDouble("discount");
                                }
                                products.add(new ProductItem(name, prezzo, qt, sconto));

                                subtotale = subtotale+(prezzo * qt * (1 - sconto));

                            }
                            TextView subtotaleTextView= view.findViewById(R.id.homepage_statoordine_txt_value_subtot);
                            subtotaleTextView.setText(String.format("%.2f",subtotale)+" €");

                            adapter = new ProdottoPrezzoAdapter(products);
                            recyclerView.setAdapter(adapter);
                            recyclerView.setNestedScrollingEnabled(false);


                            double totale = subtotale;
                            for (Double b:coupons) {
                                totale = totale * (1 - b);
                            }

                            TextView totaleTextView= view.findViewById(R.id.homepage_statoordine_txt_tot);
                            totaleTextView.setText(String.format("%.2f",totale)+" €");

                            if(coupons.size() > 0){
                                adapterBuoni = new BuoniAdapter(coupons, subtotale);
                                recyclerViewBuoni.setAdapter(adapterBuoni);
                                recyclerViewBuoni.setNestedScrollingEnabled(false);
                            } else {
                                view.findViewById(R.id.homepage_statoordine_txt_title_coupon).setVisibility(View.INVISIBLE);
                                subtotaleTextView.setVisibility(View.INVISIBLE);
                                view.findViewById(R.id.homepage_statoordine_txt_title_subtot).setVisibility(View.INVISIBLE);
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

    public class Prodotto {
        private String nomeProdotto;
        private double prezzo;

        public String getNomeProdotto() {
            return nomeProdotto;
        }

        public void setNomeProdotto(String nomeProdotto) {
            this.nomeProdotto = nomeProdotto;
        }

        public double getPrezzo() {
            return prezzo;
        }

        public void setPrezzo(double prezzo) {
            this.prezzo = prezzo;
        }
    }



}