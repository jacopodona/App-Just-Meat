package com.example.justmeat.categoria;

import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.justmeat.MainActivity;
import com.example.justmeat.R;

public class CategoriaViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    public ImageView img;
    public TextView nome;
    public int id;
    MainActivity main;
    public CategoriaViewHolder(@NonNull View itemView, MainActivity main) {
        super(itemView);
        itemView.setOnClickListener(this);
        img = itemView.findViewById(R.id.catIcona);
        nome = itemView.findViewById(R.id.catNome);
        this.main = main;
    }
    @Override
    public void onClick(View v) {
        if(main.activeFilter != id){
            main.filter(id);
            main.activeFilter= id;
            color();
        }
        else{
            decolor();
            main.removeFilter();
            main.activeFilter =-1;
        }
    }
    public void color(){
        this.img.setColorFilter(Color.parseColor("#aa2233"));
    }
    public void decolor(){
        this.img.setColorFilter(Color.parseColor("#000000"));
    }
}
