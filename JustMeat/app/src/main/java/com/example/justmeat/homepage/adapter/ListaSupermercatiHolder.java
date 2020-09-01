package com.example.justmeat.homepage.adapter;

import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.justmeat.R;

public class ListaSupermercatiHolder extends RecyclerView.ViewHolder {
    public CardView cardSupermercato;

    public TextView nomeSupermercato;
    public TextView indirizzoSupermercato;
    public ImageView logoSupermercato;
    public ImageButton posizioneButton;

    public ListaSupermercatiHolder(@NonNull View itemView) {
        super(itemView);
        nomeSupermercato = itemView.findViewById(R.id.homepage_imageview_nomesupermercato_listasupermercati);
        indirizzoSupermercato = itemView.findViewById(R.id.homepage_imageview_indirizzosupermercato_listasupermercati);
        logoSupermercato = itemView.findViewById(R.id.homepage_imageview_logosupermercato_listasupermercati);
        posizioneButton = itemView.findViewById(R.id.card_supermercato_posizione_button);
        cardSupermercato = itemView.findViewById(R.id.homepage_card_cardsupermercato);
    }
}