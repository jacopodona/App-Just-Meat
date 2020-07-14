package com.example.justmeat.homepage.adapter;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.justmeat.R;
import com.example.justmeat.homepage.MieiOrdini;
import com.example.justmeat.homepage.Supermercato;
import com.example.justmeat.marketview.MarketViewActivity;

import java.util.List;

public class ListaSupermercatiAdapter extends RecyclerView.Adapter<ListaSupermercatiHolder>{

    private List<Supermercato> listaSupermercati;
    private Activity activity;

    public ListaSupermercatiAdapter(List<Supermercato> listaOrdini, Activity activity) {
        this.listaSupermercati = listaOrdini;
        this.activity=activity;
    }

    @NonNull
    @Override
    public ListaSupermercatiHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_supermercato, parent, false);//era shr_product_card
        return new ListaSupermercatiHolder(layoutView);
    }

    @Override
    public void onBindViewHolder(@NonNull ListaSupermercatiHolder holder, int position) {
        if (listaSupermercati != null && position < listaSupermercati.size()) {
            Supermercato supermercato = listaSupermercati.get(position);

            if(position==0){
                //holder.itemView.setTranslationY(150);
            }
            //holder.itemView.setY(150);

            holder.logoSupermercato.setImageResource(R.drawable.aldi);
            holder.nomeSupermercato.setText(supermercato.getNome());
            holder.indirizzoSupermercato.setText(supermercato.getIndirizzo());

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(v.getContext(), MarketViewActivity.class);
                    activity.startActivity(i);
                }
            });


        }
    }

    @Override
    public int getItemCount() {
        return listaSupermercati.size();
    }
}
