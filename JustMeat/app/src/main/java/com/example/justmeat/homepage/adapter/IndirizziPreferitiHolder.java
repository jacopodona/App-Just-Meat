package com.example.justmeat.homepage.adapter;

import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.justmeat.R;

public class IndirizziPreferitiHolder extends RecyclerView.ViewHolder {

    public TextView nomeIndirizzo;
    public TextView indirizzo;
    public ImageButton deleteButton;


    public IndirizziPreferitiHolder(@NonNull View itemView) {
        super(itemView);
        nomeIndirizzo = itemView.findViewById(R.id.homepage_textview_nomeindirizzo);
        indirizzo = itemView.findViewById(R.id.homepage_textview_indirizzopreferito);
        deleteButton = itemView.findViewById(R.id.homepage_imagebutton_delete_indirizzo_preferito);

    }
}