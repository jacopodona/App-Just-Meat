package com.example.justmeat.homepage.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.justmeat.R;
import com.example.justmeat.homepage.DettagliMieiOrdiniFragment;
import com.example.justmeat.homepage.HomepageActivity;
import com.example.justmeat.homepage.OrdineSupermercato;
import com.example.justmeat.homepage.StatoOrdineSupermercato;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class OrdineSupermercatoAdapter extends RecyclerView.Adapter<OrdineSupermercatoAdapter.OrdineSupermercatoHolder> {

    private ArrayList<OrdineSupermercato> list;
    private Activity activity;


    public static class OrdineSupermercatoHolder extends RecyclerView.ViewHolder {

        public TextView idOrdine,numProdotti;

        public OrdineSupermercatoHolder(@NonNull View itemView) {
            super(itemView);
            idOrdine=itemView.findViewById(R.id.homepage_card_ordinesupermercato_idOrdine);
            numProdotti=itemView.findViewById(R.id.homepage_card_ordinesupermercato_numprodotti);
        }
    }

    public OrdineSupermercatoAdapter(ArrayList<OrdineSupermercato> listOrdini, Activity activity){
        list=listOrdini;
        this.activity=activity;
    }

    @NonNull
    @Override
    public OrdineSupermercatoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.card_ordinesupermercato,parent,false);
        OrdineSupermercatoHolder ordineSupermercatoHolder= new OrdineSupermercatoHolder(view);
        return ordineSupermercatoHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull OrdineSupermercatoHolder holder, final int position) {
        OrdineSupermercato currentItem=list.get(position);

        holder.idOrdine.setText(currentItem.getId());
        holder.numProdotti.setText(currentItem.getNumProdotti());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((HomepageActivity)activity).navigateTo(new StatoOrdineSupermercato(),true);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
