package com.example.justmeat.homepage.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.justmeat.R;

public class OrdiniPreferitiHolder extends RecyclerView.ViewHolder {

    public TextView nomeSupermercato;
    public TextView nomeOrdine;
    public ImageView logo;


    public OrdiniPreferitiHolder(@NonNull View itemView) {
        super(itemView);
        nomeSupermercato = itemView.findViewById(R.id.homepage_card_nomesupermercato_ordinipreferiti);
        nomeOrdine = itemView.findViewById(R.id.homepage_card_nomeordine_ordinipreferiti);
        logo = itemView.findViewById(R.id.homepage_card_imglogo_ordinipreferiti);
    }
}