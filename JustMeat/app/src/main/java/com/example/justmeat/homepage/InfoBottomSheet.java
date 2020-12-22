package com.example.justmeat.homepage;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;
import com.example.justmeat.R;
import com.example.justmeat.addCard.AddCardDialog;
import com.example.justmeat.marketview.MarketViewActivity;
import com.example.justmeat.utilities.HttpJsonRequest;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class InfoBottomSheet extends BottomSheetDialogFragment {
    Supermercato supermercato;
    String httpToken;
    Activity activity;

    public InfoBottomSheet(Supermercato supermercato, String httpToken, Activity activity) {
        this.supermercato = supermercato;
        this.httpToken = httpToken;
        this.activity = activity;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bottom_sheet_spkmt, container, false);

        ImageView logo = view.findViewById(R.id.infobox_img_logo);
        Glide.with(view)
                .load("http://just-feet.herokuapp.com/images/sl_"+supermercato.getId()+".jpg")
                .override(360,240)
                .into(logo);

        ImageView placeholder = view.findViewById(R.id.infobox_img_placeholder);
        Glide.with(view)
                .load("http://just-feet.herokuapp.com/images/si_"+supermercato.getId()+".jpg")
                .into(placeholder);

        TextView nome = view.findViewById(R.id.infobox_txt_name);
        nome.setText(supermercato.getNome());

        TextView address = view.findViewById(R.id.infobox_txt_address);
        address.setText(supermercato.getIndirizzo());

        Button gotoBtn = view.findViewById(R.id.infobox_btn_goto);
        gotoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getUserCard(supermercato, httpToken, activity);
            }
        });
        return view;
    }

    public void getUserCard(final Supermercato supermercato, String httpToken, Activity activity) {
        final int[] u_id = new int[1];
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
}
