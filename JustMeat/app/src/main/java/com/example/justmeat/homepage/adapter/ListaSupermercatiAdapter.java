package com.example.justmeat.homepage.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;
import com.example.justmeat.R;
import com.example.justmeat.addCard.AddCardDialog;
import com.example.justmeat.homepage.Supermercato;
import com.example.justmeat.marketview.MarketViewActivity;
import com.example.justmeat.utilities.HttpJsonRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class ListaSupermercatiAdapter extends RecyclerView.Adapter<ListaSupermercatiHolder>{

    private List<Supermercato> listaSupermercati;
    private Activity activity;
    String httpToken;

    public ListaSupermercatiAdapter(List<Supermercato> listaOrdini, Activity activity, String httpToken) {
        this.listaSupermercati = listaOrdini;
        this.activity=activity;
        this.httpToken = httpToken;
    }

    @NonNull
    @Override
    public ListaSupermercatiHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_supermercato, parent, false);//era shr_product_card
        return new ListaSupermercatiHolder(layoutView);
    }

    @Override
    public void onBindViewHolder(@NonNull ListaSupermercatiHolder holder, final int position) {
        if (listaSupermercati != null && position < listaSupermercati.size()) {
            final Supermercato supermercato = listaSupermercati.get(position);

            Glide.with(activity)
                    .load("http://just-feet.herokuapp.com/images/sl_"+supermercato.getId()+".jpg" )
                    .override(360, 240)
                    .into(holder.logoSupermercato);
            holder.logoSupermercato.setAdjustViewBounds(true);

            holder.nomeSupermercato.setText(supermercato.getNome());
            holder.indirizzoSupermercato.setText(supermercato.getIndirizzo());
            holder.shoppingButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getUserCard(supermercato, httpToken, activity);
                }
            });

            
            holder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //check card
                    getUserCard(supermercato, httpToken, activity);
                }
            });


        }
    }

    public void getUserCard(final Supermercato supermercato, String httpToken, Activity activity) {
        new HttpJsonRequest(activity, "/api/v1/get_user_fidcards", Request.Method.GET, httpToken, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    parseCard(response, supermercato);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println(error.toString());
            }
        }).run();
        return;
    }

    private void parseCard(JSONObject response, Supermercato supermercato) throws JSONException {
        JSONArray results = response.getJSONArray("results");
        for (int i = 0; i < results.length(); i++){
            JSONObject currentItem = results.getJSONObject(i);
            int id, chain;
            id = currentItem.getInt("id");
            chain = currentItem.getInt("chain");
            if(supermercato.getChain() == chain){
                System.out.println("json "+id);
                operationOnResponse(id, supermercato);
                return;
            }
        }
        operationOnResponse(0, supermercato);
        return;
    }

    private void operationOnResponse(int tessera_id, Supermercato supermercato) {
        if (tessera_id == 0){
            AddCardDialog addCardDialog = new AddCardDialog(activity, httpToken, supermercato, activity);
            addCardDialog.show();
        } else {
            Intent i = new Intent(activity, MarketViewActivity.class);
            i.putExtra("idSupermercato",supermercato.getId());
            i.putExtra("nomeSupermercato", supermercato.getNome());
            i.putExtra("loyalty", tessera_id);
            activity.startActivity(i);
        }
    }

    @Override
    public int getItemCount() {
        return listaSupermercati.size();
    }

}
