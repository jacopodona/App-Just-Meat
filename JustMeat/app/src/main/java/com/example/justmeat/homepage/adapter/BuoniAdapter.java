package com.example.justmeat.homepage.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.justmeat.R;
import com.example.justmeat.homepage.DettagliMieiOrdiniFragment;

import java.util.List;

public class BuoniAdapter extends RecyclerView.Adapter<BuoniHolder> {

    private List buoni;

    public BuoniAdapter(List buoni) {
        this.buoni = buoni;
    }

    @NonNull
    @Override
    public BuoniHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_buoni, parent, false);//era shr_product_card
        return new BuoniHolder(layoutView);
    }

    @Override
    public void onBindViewHolder(@NonNull BuoniHolder holder, int position) {
        if (buoni != null && position < buoni.size()) {
            DettagliMieiOrdiniFragment.Buono buono = (DettagliMieiOrdiniFragment.Buono)buoni.get(position);

            holder.buono.setText("Buono -"+buono.getPercentuale()+"%");
            holder.valoreBuono.setText("-"+buono.getValoreBuono()+" €");



        }
    }

    @Override
    public int getItemCount() {
        return buoni.size();
    }
}