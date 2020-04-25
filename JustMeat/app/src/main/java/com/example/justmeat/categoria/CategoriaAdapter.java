package com.example.justmeat.categoria;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.justmeat.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CategoriaAdapter extends RecyclerView.Adapter<CategoriaAdapter.CategoriaViewHolder> {
    private CategoriaItem[] cIL;

    public static class CategoriaViewHolder extends RecyclerView.ViewHolder{
        public ImageView img;
        public TextView nome;
        public CategoriaViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.catIcona);
            nome = itemView.findViewById(R.id.catNome);
        }
    }

    public CategoriaAdapter(CategoriaItem [] cIL){
        this.cIL= cIL;
    }

    @Override
    public CategoriaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.categoria_item, parent, false);
        CategoriaViewHolder cVH = new CategoriaViewHolder(v);
        return cVH;
    }

    @Override
    public void onBindViewHolder(@NonNull CategoriaViewHolder holder, int position) {
        CategoriaItem currentItem= this.cIL[position];
        holder.img.setImageResource(currentItem.getIconaImg());
        holder.nome.setText(currentItem.getNomeCat());
    }

    @Override
    public int getItemCount() {
        return cIL.length;
    }
}
