package com.example.justmeat.addCard;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.justmeat.R;
import com.example.justmeat.homepage.CarteFedeltaFragment;
import com.example.justmeat.homepage.FidCard;
import com.example.justmeat.homepage.Supermercato;
import com.example.justmeat.marketview.MarketViewActivity;
import com.example.justmeat.utilities.HttpJsonRequest;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class AddCardDialog extends Dialog {
    public int chain_id = 0;
    public int card_id;
    public ChainAdapter.ChainViewHolder actualChainVH;
    String httpToken;
    ArrayList <Integer> chain_ids = new ArrayList<>();
    CarteFedeltaFragment carteFedeltaFragment;
    Activity activity;
    Supermercato supermercato;


    public AddCardDialog(@NonNull Context context, String httpToken) {
        super(context);
        this.setContentView(R.layout.dialog_addfidcard);

        this.httpToken = httpToken;


        final TextInputLayout cardIdInput = findViewById(R.id.addfidcard_intxt_code);

        Button confirm = findViewById(R.id.addfidcard_btn_confirm);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(cardIdInput.getEditText().getText().toString().isEmpty()) {
                    card_id = 0;
                } else {
                    card_id = (int)Integer.parseInt(cardIdInput.getEditText().getText().toString());
                }

                if(chain_id != 0 && card_id != 0){
                    try {
                        AddCardRequest(card_id, chain_id);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                if (chain_id == 0){
                    System.out.println("chain id vuoto");
                }
                if (card_id == 0){
                    System.out.println("card id vuoto");
                }
            }
        });

        Button dismiss = findViewById(R.id.addfidcard_btn_neutral);
        dismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("dismiss");
                if(activity != null){
                    Intent i = new Intent(activity, MarketViewActivity.class);
                    i.putExtra("idSupermercato",supermercato.getId());
                    i.putExtra("nomeSupermercato", supermercato.getNome());
                    i.putExtra("loyalty", 0);
                    activity.startActivity(i);
                }
                AddCardDialog.super.dismiss();
            }
        });

        this.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        this.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
    }

    public AddCardDialog(Context context, String httpToken, CarteFedeltaFragment carteFedeltaFragment){
       this(context, httpToken);
       this.carteFedeltaFragment = carteFedeltaFragment;
       setChainView();
    }

    public AddCardDialog(Context context, String httpToken, Supermercato supermercato, Activity activity){
        this(context, httpToken);
        chain_ids.add(new Integer(supermercato.getChain()));
        this.supermercato = supermercato;
        this.activity = activity;
        //different title "Accedi a offerte aggiuntive con la tessera fedeltà"
        setRV();
    }

    private void setChainView() {
        new HttpJsonRequest(getContext(), "/api/v1/get_supermarkets_chain", Request.Method.GET, httpToken, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    parseChain(response);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                //se chain_ids.isEmpty show placeholder
                setRV();
            }
        }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println(error.toString());
            }
        }).run();
    }

    private void parseChain(JSONObject response) throws JSONException {
        JSONArray jsonArray = response.getJSONArray("results");
        for(int i = 0; i < jsonArray.length(); i++){
            JSONObject currentObject = jsonArray.getJSONObject(i);
            int currentInt = currentObject.getInt("chain");
            boolean addOk = true;
            for (int j = 0; j < carteFedeltaFragment.fidCards.size(); j++){
                if(carteFedeltaFragment.fidCards.get(j).getChain() == currentInt){
                    addOk = false;
                }
            }
            if(addOk){
                chain_ids.add(new Integer(currentInt));
            }
        }
    }

    private void setRV() {
        RecyclerView rv = findViewById(R.id.addfidcard_rv);
        RecyclerView.LayoutManager rvLM = new LinearLayoutManager(getContext());
        RecyclerView.Adapter rvA = new ChainAdapter(this);

        rv.setAdapter(rvA);
        rv.setLayoutManager(rvLM);
    }

    private void AddCardRequest(final int card_id, final int chain_id) throws JSONException {
        JSONObject body = new JSONObject();
        body.put("fidcard_id", card_id);
        body.put("supermarket_name", chain_id);
        new HttpJsonRequest(getContext(), "/api/v1/add_fidcard", Request.Method.POST, body, httpToken, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                if(carteFedeltaFragment != null){
                    carteFedeltaFragment.fidCards.add(new FidCard(card_id, chain_id));
                    for (int i = 0; i < carteFedeltaFragment.fidCards.size(); i++){
                        System.out.println(""+carteFedeltaFragment.fidCards.get(i).getId());
                    }
                    carteFedeltaFragment.fRVA.notifyDataSetChanged();
                } else if(activity != null){
                    Intent i = new Intent(activity, MarketViewActivity.class);
                    i.putExtra("idSupermercato",supermercato.getId());
                    i.putExtra("nomeSupermercato", supermercato.getNome());
                    i.putExtra("loyalty", card_id);
                    activity.startActivity(i);
                }
                AddCardDialog.this.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        }).run();
        System.out.println(body.toString());
    }
}
