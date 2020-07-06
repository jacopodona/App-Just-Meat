package com.example.justmeat.homepage.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.justmeat.R;
import com.example.justmeat.homepage.MieiOrdini;
import com.example.justmeat.homepage.OrdinePreferito;

import java.util.List;

public class OrdiniPreferitiAdapter extends RecyclerView.Adapter<OrdiniPreferitiHolder> {

    private List<OrdinePreferito> listaOrdiniPreferiti;

    public OrdiniPreferitiAdapter(List<OrdinePreferito> listaOrdiniPreferiti) {
        this.listaOrdiniPreferiti = listaOrdiniPreferiti;
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
            OrdinePreferito ordine = listaOrdiniPreferiti.get(position);

            holder.indirizzo.setText(ordine.getIndirizzo());
            holder.nomeSupermercato.setText(ordine.getNomeSupermercato());
            holder.nomeOrdine.setText(ordine.getNomeOrdine());

        }
    }

    @Override
    public int getItemCount() {
        return listaOrdiniPreferiti.size();
    }
}
