package com.example.justmeat.homepage.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.justmeat.R;
import com.example.justmeat.homepage.MieiOrdini;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class MieiOrdiniAdapter extends RecyclerView.Adapter<MieiOrdiniHolder> {

    private List<MieiOrdini> listaOrdini;

    public MieiOrdiniAdapter(List<MieiOrdini> listaOrdini) {
        this.listaOrdini = listaOrdini;
    }

    @NonNull
    @Override
    public MieiOrdiniHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_imieiordini, parent, false);//era shr_product_card
        return new MieiOrdiniHolder(layoutView);
    }

    @Override
    public void onBindViewHolder(@NonNull MieiOrdiniHolder holder, int position) {
        if (listaOrdini != null && position < listaOrdini.size()) {
            MieiOrdini ordine = listaOrdini.get(position);

            holder.data.setText(ordine.getDataOrdine().toString());
            holder.logoSupermercato.setImageResource(R.drawable.aldi);
            holder.nomeSupermercato.setText(ordine.getNomeSupermercato());
            holder.statoOrdine.setText(ordine.getStato());







        }
    }

    @Override
    public int getItemCount() {
        return listaOrdini.size();
    }
}
