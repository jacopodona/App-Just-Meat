package com.example.justmeat.homepage.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.justmeat.R;
import com.example.justmeat.homepage.DettagliMieiOrdiniFragment;
import com.example.justmeat.homepage.DettagliOrdinePreferitoFragment;
import com.example.justmeat.homepage.HomepageActivity;
import com.example.justmeat.homepage.MieiOrdini;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class MieiOrdiniAdapter extends RecyclerView.Adapter<MieiOrdiniHolder> {

    private List<MieiOrdini> listaOrdini;
    private Activity activity;

    public MieiOrdiniAdapter(List<MieiOrdini> listaOrdini, Activity activity) {
        this.listaOrdini = listaOrdini;
        this.activity=activity;
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

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((HomepageActivity)activity).navigateTo(new DettagliMieiOrdiniFragment(),true);
                }
            });





        }
    }

    @Override
    public int getItemCount() {
        return listaOrdini.size();
    }
}
