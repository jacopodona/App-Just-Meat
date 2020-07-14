package com.example.justmeat.homepage.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.justmeat.R;

public class MieiOrdiniHolder extends RecyclerView.ViewHolder {

    public TextView nomeSupermercato;
    public TextView data;
    public TextView statoOrdine;
    public ImageView logoSupermercato;

    public MieiOrdiniHolder(@NonNull View itemView) {
        super(itemView);
        nomeSupermercato = itemView.findViewById(R.id.homepage_textview_nomesupermercato);
        data = itemView.findViewById(R.id.homepage_textview_data);
        statoOrdine = itemView.findViewById(R.id.homepage_textview_statoordine);
        logoSupermercato = itemView.findViewById(R.id.homepage_textview_logosupermercato);
    }
}