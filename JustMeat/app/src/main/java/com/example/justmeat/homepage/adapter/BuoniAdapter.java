package com.example.justmeat.homepage.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.justmeat.R;

import java.util.ArrayList;

public class BuoniAdapter extends RecyclerView.Adapter<BuoniHolder> {

    private ArrayList<Double> buoni;
    Double subtotale;

    public BuoniAdapter(ArrayList<Double> buoni, Double subtotale) {
        this.buoni = buoni;
        this.subtotale = subtotale;
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
            Double coupon = buoni.get(position);

            holder.buono.setText("Buono -"+coupon*100+"%");

            Double valoreBuono = subtotale*coupon;
            holder.valoreBuono.setText("-"+valoreBuono+" €");
        }
    }

    @Override
    public int getItemCount() {
        return buoni.size();
    }
}