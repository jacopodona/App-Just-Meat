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

import java.text.DateFormat;
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
            final MieiOrdini ordine = listaOrdini.get(position);

            DateFormat dateFormat = new SimpleDateFormat("d MMMM yyyy");

            holder.data.setText(dateFormat.format(ordine.getDataOrdine()));

            switch (ordine.getNomeSupermercato()){
                case "MiniPoli":
                    holder.logoSupermercato.setImageResource(R.drawable.minipoli);
                    break;

                case "Aldi":
                    holder.logoSupermercato.setImageResource(R.drawable.aldi);
                    break;
                default:
                    holder.logoSupermercato.setImageResource(R.drawable.minipoli);
                    break;
            }


            holder.nomeSupermercato.setText(ordine.getNomeSupermercato());
            holder.statoOrdine.setText(ordine.getStato());

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((HomepageActivity)activity).navigateTo(new DettagliMieiOrdiniFragment(ordine),true);
                }
            });





        }
    }

    @Override
    public int getItemCount() {
        return listaOrdini.size();
    }
}
