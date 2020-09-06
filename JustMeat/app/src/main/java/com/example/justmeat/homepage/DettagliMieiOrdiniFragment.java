package com.example.justmeat.homepage;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;
import com.example.justmeat.R;
import com.example.justmeat.homepage.adapter.BuoniAdapter;
import com.example.justmeat.homepage.adapter.MieiOrdiniAdapter;
import com.example.justmeat.homepage.adapter.ProdottoPrezzoAdapter;
import com.example.justmeat.utilities.HttpJsonRequest;
import com.google.android.material.card.MaterialCardView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;

public class DettagliMieiOrdiniFragment  extends Fragment {

    private MieiOrdini ordine;
    private RecyclerView recyclerView;
    private RecyclerView recyclerViewBuoni;
    private ProdottoPrezzoAdapter adapter;
    private BuoniAdapter adapterBuoni;
    private View view;
    private MaterialCardView maps;


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
        pb.setProgress(36);//test

        TextView statoOrdine= view.findViewById(R.id.homepage_statoordine_textview_stato);
        TextView dataOrdine=view.findViewById(R.id.homepage_statoordine_textview_data);
        ImageView imageSupermercato= view.findViewById(R.id.homepage_statoordine_textview_imgview);
        TextView indirizzoSupermercato = view.findViewById(R.id.homepage_statoordine_textview_indirizzo);
        maps=view.findViewById(R.id.homepage_button_aprisupermercatoinmaps);


        statoOrdine.setText(ordine.getStato());

        DateFormat dateFormat = new SimpleDateFormat("d MMMM yyyy");
        dataOrdine.setText(dateFormat.format(ordine.getDataOrdine()));


        ImageView logo= view.findViewById(R.id.homepage_statoordine_textview_imgview);
        Glide.with(this.getActivity()).load("http://just-feet.herokuapp.com"+"/images/sl_"+ordine.getIdSupermercato()+".jpg")

                .into(logo);

        


        indirizzoSupermercato.setText(ordine.getIndirizzo());

        maps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri gmmIntentUri = Uri.parse("geo:37.7749,-122.4192?q=" + Uri.encode(ordine.getIndirizzo()));
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent);
            }
        });

        recyclerView = view.findViewById(R.id.homepage_stato_ordine_rec_view);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL,false);

        recyclerView.setLayoutManager(layoutManager);

        //rec view Buoni
        recyclerViewBuoni = view.findViewById(R.id.stato_ordine_rec_view_buoni);
        recyclerViewBuoni.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManagerBuoni = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL,false);

        recyclerViewBuoni.setLayoutManager(layoutManagerBuoni);

        return view;
    }


    public void getOrdine(Context context){

        final LinkedList prodotti= new <Prodotto>LinkedList();

        new HttpJsonRequest(context, "/api/v1/get_order/"+ordine.getNumOrdine(), Request.Method.GET, ((HomepageActivity)getActivity()).getHttpToken(),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("Order", response.toString());

                        try {
                            //Prendo i buoni
                            LinkedList<Buono> listaBuoni= new LinkedList();
                            for (int c = 0; c<((JSONArray)response.getJSONObject("results").getJSONArray("coupons_discounts")).length(); c++) {
                                try {
                                    Buono buono= new Buono();
                                    buono.setPercentuale((int)(Double.parseDouble(response.getJSONObject("results").getJSONArray("coupons_discounts").get(c).toString())*100));
                                    listaBuoni.add(buono);

                                    buono= new Buono();
                                    buono.setPercentuale(10);
                                    listaBuoni.add(buono);




                                } catch (Exception e){
                                    Log.e("Err buoni", e.toString());
                                }

                            }

                            int numElementi=(response.getJSONObject("metadata").getInt("returned"));
                            double subtotale=0;

                            for(int i=0; i<numElementi;i++){

                                Prodotto prodotto = new Prodotto();
                                prodotto.setNomeProdotto(response.getJSONObject("results").getJSONArray("products").getJSONObject(i).get("product_name").toString());
                                prodotto.setPrezzo(Double.parseDouble((response.getJSONObject("results").getJSONArray("products").getJSONObject(i).get("price").toString())));
                                prodotti.add(prodotto);
                                subtotale= subtotale+prodotto.getPrezzo();
                                adapter = new ProdottoPrezzoAdapter(prodotti);
                                recyclerView.setAdapter(adapter);
                                recyclerView.setNestedScrollingEnabled(false);
                            }
                            TextView subtotaleTextView= view.findViewById(R.id.stato_ordine_subtotale_value);
                            subtotaleTextView.setText(subtotale+" €");



                            //Calcolo valore buono
                            double totale=subtotale;
                            for (Buono b:listaBuoni) {
                                b.setValoreBuono((totale*b.getPercentuale())/100);
                                totale= totale-b.getValoreBuono();

                            }

                            TextView totaleTextView= view.findViewById(R.id.stato_ordine_totale_value);
                            totaleTextView.setText(totale+" €");

                            adapterBuoni = new BuoniAdapter(listaBuoni);
                            recyclerViewBuoni.setAdapter(adapterBuoni);
                            recyclerViewBuoni.setNestedScrollingEnabled(false);


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

    public class Buono{
        private int percentuale;
        private double valoreBuono;

        public int getPercentuale() {
            return percentuale;
        }

        public void setPercentuale(int percentuale) {
            this.percentuale = percentuale;
        }

        public double getValoreBuono() {
            return valoreBuono;
        }

        public void setValoreBuono(double valoreBuono) {
            this.valoreBuono = valoreBuono;
        }
    }


}