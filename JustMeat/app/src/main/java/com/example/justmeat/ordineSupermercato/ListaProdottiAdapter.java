package com.example.justmeat.ordineSupermercato;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.justmeat.R;
import com.example.justmeat.shop.OrdineSupermercato;
import com.example.justmeat.shop.OrdineSupermercatoAdapter;

import java.util.ArrayList;

public class ListaProdottiAdapter extends RecyclerView.Adapter<ListaProdottiAdapter.ListaProdottiViewHolder>{

    private ArrayList<ProdottoInLista> listaProdotti;

    public static class ListaProdottiViewHolder extends RecyclerView.ViewHolder{
        public TextView nomeProdotto,prezzoProdotto;

        public ListaProdottiViewHolder(@NonNull View itemView) {
            super(itemView);
            nomeProdotto=itemView.findViewById(R.id.card_listaordini_nomeprodotto);
            prezzoProdotto=itemView.findViewById(R.id.card_listaordini_prezzo);
        }
    }

    public ListaProdottiAdapter(ArrayList<ProdottoInLista> lista){
        listaProdotti=lista;
    }

    @NonNull
    @Override
    public ListaProdottiViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v=LayoutInflater.from(parent.getContext()).inflate(R.layout.card_listaprodotti,parent,false);
        ListaProdottiViewHolder listaProdottiViewHolder=new ListaProdottiViewHolder(v);
        return  listaProdottiViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ListaProdottiViewHolder holder, int position) {
        holder.nomeProdotto.setText(listaProdotti.get(position).getNome());
        holder.prezzoProdotto.setText(String.valueOf(listaProdotti.get(position).getPrezzo())+" €");
    }

    @Override
    public int getItemCount() {
        return listaProdotti.size();
    }
}
