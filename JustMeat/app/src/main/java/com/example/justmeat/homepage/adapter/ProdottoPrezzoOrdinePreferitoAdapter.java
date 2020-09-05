package com.example.justmeat.homepage.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.justmeat.R;
import com.example.justmeat.homepage.DettagliMieiOrdiniFragment;
import com.example.justmeat.homepage.ProdottoOrdinePreferito;

import java.util.List;

public class ProdottoPrezzoOrdinePreferitoAdapter extends RecyclerView.Adapter<ProdottoPrezzoHolder> {

    private List prodotti;

    public ProdottoPrezzoOrdinePreferitoAdapter(List prodotti) {
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
            ProdottoOrdinePreferito prodotto = (ProdottoOrdinePreferito)prodotti.get(position);

            holder.nomeProdotto.setText(prodotto.getNome());
            holder.prezzoProdotto.setText(prodotto.getPrezzo()+" €");



        }
    }

    @Override
    public int getItemCount() {
        return prodotti.size();
    }
}