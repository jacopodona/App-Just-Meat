package com.example.justmeat.homepage.adapter;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.justmeat.R;

public class BuoniHolder extends RecyclerView.ViewHolder {

    public TextView buono;
    public TextView valoreBuono;



    public BuoniHolder(@NonNull View itemView) {
        super(itemView);
        buono = itemView.findViewById(R.id.row_buoni_buono);
        valoreBuono = itemView.findViewById(R.id.stato_ordine_buono_value);


    }
}