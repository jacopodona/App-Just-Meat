package com.example.justmeat.homepage.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.justmeat.R;
import com.example.justmeat.homepage.DettagliOrdinePreferitoFragment;
import com.example.justmeat.homepage.HomepageActivity;
import com.example.justmeat.homepage.OrdinePreferito;

import java.util.List;

public class OrdiniPreferitiAdapter extends RecyclerView.Adapter<OrdiniPreferitiHolder> {

    private List<OrdinePreferito> listaOrdiniPreferiti;
    private Activity activity;

    public OrdiniPreferitiAdapter(List<OrdinePreferito> listaOrdiniPreferiti, Activity activity) {
        this.listaOrdiniPreferiti = listaOrdiniPreferiti;
        this.activity = activity;
    }

    @NonNull
    @Override
    public OrdiniPreferitiHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_ordinipreferiti, parent, false);//era shr_product_card
        return new OrdiniPreferitiHolder(layoutView);
    }

    @Override
    public void onBindViewHolder(@NonNull OrdiniPreferitiHolder holder, int position) {
        if (listaOrdiniPreferiti != null && position < listaOrdiniPreferiti.size()) {
            final OrdinePreferito ordine = listaOrdiniPreferiti.get(position);


            holder.nomeSupermercato.setText(ordine.getNomeSupermercato());
            holder.nomeOrdine.setText(ordine.getNomeOrdinePreferito());

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((HomepageActivity)activity).navigateTo(new DettagliOrdinePreferitoFragment(ordine),true);
                }
            });

            Glide.with(activity).load("http://just-feet.herokuapp.com"+"/images/sl_"+ordine.getIdSupermercato()+".jpg")
                    .into(holder.logo);

        }
    }

    @Override
    public int getItemCount() {
        return listaOrdiniPreferiti.size();
    }}
