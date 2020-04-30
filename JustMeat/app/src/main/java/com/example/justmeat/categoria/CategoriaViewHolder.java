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
    CategoriaAdapter adapter;

    public CategoriaViewHolder(@NonNull View itemView, CategoriaAdapter adapter) {
        super(itemView);
        itemView.setOnClickListener(this);
        img = itemView.findViewById(R.id.catIcona);
        img.setColorFilter(Color.parseColor("#7f7f7f"));
        nome = itemView.findViewById(R.id.catNome);
        this.adapter = adapter;
    }

    @Override
    public void onClick(View v) {
        if(adapter.currentActive != null){
            adapter.currentActive.decolor();
        }
        adapter.currentActive = this;
        if(adapter.main.activeFilter != id){
            adapter.main.filter(id);
            adapter.main.activeFilter= id;
            color();
        }
        else{
            decolor();
            adapter.main.removeFilter();
            adapter.main.activeFilter =-1;
        }
    }
    public void color(){
        this.img.setColorFilter(Color.parseColor("#ffffff"));
        this.img.setSelected(true);
    }
    public void decolor(){
        this.img.setColorFilter(Color.parseColor("#7f7f7f"));
        this.img.setSelected(false);
    }
}
