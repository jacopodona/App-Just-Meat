package com.example.justmeat.categoria;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.justmeat.MainActivity;
import com.example.justmeat.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CategoriaAdapter extends RecyclerView.Adapter<CategoriaViewHolder> {
    private ArrayList<CategoriaItem> cIL;
    public MainActivity main;
    CategoriaViewHolder currentActive;

    //inizilizza categoriaAdapter, copia l'arraylist
    public CategoriaAdapter(ArrayList<CategoriaItem> cIL, MainActivity mainActivity){
        this.cIL= cIL;
        this.main=mainActivity;
    }

    @Override
    public CategoriaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.categoria_item, parent, false);
        CategoriaViewHolder cVH = new CategoriaViewHolder(v, this);
        return cVH;
    }

    //crea un viewHolder per un certo elemento della lista
    @Override
    public void onBindViewHolder(@NonNull CategoriaViewHolder holder, int position) {
        CategoriaItem currentItem= this.cIL.get(position);
        holder.img.setImageResource(currentItem.getIconaImg());
        holder.nome.setText(currentItem.getNomeCat());
        holder.id = currentItem.getIdCategoria();
    }

    @Override
    public int getItemCount() {
        return cIL.size();
    }
}
