package com.example.justmeat.shop;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.justmeat.R;
import com.example.justmeat.homepage.HomepageActivity;
import com.example.justmeat.ordineSupermercato.StatoOrdineSupermercatoActivity;

import java.util.ArrayList;

public class OrdineSupermercatoAdapter extends RecyclerView.Adapter<OrdineSupermercatoAdapter.OrdineSupermercatoHolder> {

    private ArrayList<OrdineSupermercato> list;
    private Activity activity;
    private onItemClickListener mListener;

    public interface onItemClickListener{
        void onItemClick(int position);
    }

    public void setOnItemClickListener(onItemClickListener listener){
        mListener=listener;
    }


    public static class OrdineSupermercatoHolder extends RecyclerView.ViewHolder {

        public TextView idOrdine,statoOrdine;

        public OrdineSupermercatoHolder(@NonNull View itemView, final onItemClickListener listener) {
            super(itemView);
            idOrdine=itemView.findViewById(R.id.shop_card_ordinesupermercato_idOrdine);
            statoOrdine=itemView.findViewById(R.id.shop_card_ordinesupermercato_statoOrdine);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener!=null){
                        int position=getAdapterPosition();
                        if(position!=RecyclerView.NO_POSITION){
                            listener.onItemClick(position);
                        }
                    }
                }
            });
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
        OrdineSupermercatoHolder ordineSupermercatoHolder= new OrdineSupermercatoHolder(view,mListener);
        return ordineSupermercatoHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull OrdineSupermercatoHolder holder, final int position) {
        OrdineSupermercato currentItem=list.get(position);

        String valueId= String.valueOf(currentItem.getId());
        holder.idOrdine.setText(valueId);
        holder.statoOrdine.setText(currentItem.getStato());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
