package com.example.justmeat.homepage.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.justmeat.R;
import com.example.justmeat.homepage.IndirizzoPreferito;
import com.example.justmeat.homepage.MieiOrdini;

import java.util.List;

public class IndirizziPreferitiAdapter extends RecyclerView.Adapter<IndirizziPreferitiHolder> {

    private List<IndirizzoPreferito> listaIndirizziPreferiti;

    public IndirizziPreferitiAdapter(List<IndirizzoPreferito> listaIndirizziPreferiti) {
        this.listaIndirizziPreferiti = listaIndirizziPreferiti;
    }

    @NonNull
    @Override
    public IndirizziPreferitiHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_indirizzirpeferiti, parent, false);//era shr_product_card
        return new IndirizziPreferitiHolder(layoutView);
    }

    @Override
    public void onBindViewHolder(@NonNull IndirizziPreferitiHolder holder, final int position) {
        if (listaIndirizziPreferiti != null && position < listaIndirizziPreferiti.size()) {
            IndirizzoPreferito indirizzoPreferito = listaIndirizziPreferiti.get(position);

            holder.indirizzo.setText(indirizzoPreferito.getIndirizzo());
            holder.nomeIndirizzo.setText(indirizzoPreferito.getNome());

            holder.deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listaIndirizziPreferiti.remove(position);
                    notifyItemRemoved(position);
                    notifyItemRangeChanged(position, listaIndirizziPreferiti.size());
                }
            });
         }
    }

    @Override
    public int getItemCount() {
        return listaIndirizziPreferiti.size();
    }
}
