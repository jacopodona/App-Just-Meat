package com.example.justmeat.homepage.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.justmeat.R;
import com.example.justmeat.homepage.DettagliMieiOrdiniFragment;

import java.util.List;

public class ProdottoPrezzoAdapter extends RecyclerView.Adapter<ProdottoPrezzoHolder> {

    private List prodotti;

    public ProdottoPrezzoAdapter(List prodotti) {
        this.prodotti = prodotti;
    }

    @NonNull
    @Override
    public ProdottoPrezzoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_prodotto_prezzo, parent, false);//era shr_product_card
        return new ProdottoPrezzoHolder(layoutView);
    }

    @Override
    public void onBindViewHolder(@NonNull ProdottoPrezzoHolder holder, int position) {
        if (prodotti != null && position < prodotti.size()) {
            DettagliMieiOrdiniFragment.Prodotto prodotto = (DettagliMieiOrdiniFragment.Prodotto)prodotti.get(position);

            holder.nomeProdotto.setText(prodotto.getNomeProdotto());
            holder.prezzoProdotto.setText(prodotto.getPrezzo()+" €");



        }
    }

    @Override
    public int getItemCount() {
        return prodotti.size();
    }
}