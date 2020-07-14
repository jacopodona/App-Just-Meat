package com.example.justmeat.homepage.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.justmeat.R;

public class OrdiniPreferitiHolder extends RecyclerView.ViewHolder {

    public TextView nomeSupermercato;
    public TextView indirizzo;
    public TextView nomeOrdine;


    public OrdiniPreferitiHolder(@NonNull View itemView) {
        super(itemView);
        nomeSupermercato = itemView.findViewById(R.id.homepage_card_nomesupermercato_ordinipreferiti);
        indirizzo = itemView.findViewById(R.id.homepage_card_indirizzosupermercato_ordinipreferiti);
        nomeOrdine = itemView.findViewById(R.id.homepage_card_nomeordine_ordinipreferiti);

    }
}