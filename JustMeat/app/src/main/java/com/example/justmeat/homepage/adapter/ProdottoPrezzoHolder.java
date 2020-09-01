package com.example.justmeat.homepage.adapter;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.justmeat.R;

public class ProdottoPrezzoHolder extends RecyclerView.ViewHolder {

public TextView nomeProdotto;
public TextView prezzoProdotto;



public ProdottoPrezzoHolder(@NonNull View itemView) {
        super(itemView);
        nomeProdotto = itemView.findViewById(R.id.homepage_stato_ordine_card_prodotto_nome);
        prezzoProdotto = itemView.findViewById(R.id.homepage_stato_ordine_card_prodotto_prezzo);


        }
}