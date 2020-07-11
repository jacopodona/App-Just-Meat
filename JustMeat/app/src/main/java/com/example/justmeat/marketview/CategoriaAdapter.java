package com.example.justmeat.marketview;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.justmeat.R;

import java.util.ArrayList;


public class CategoriaAdapter extends RecyclerView.Adapter<CategoriaAdapter.CategoriaViewHolder> {
    private ArrayList<CategoriaItem> cIL;
    public MarketViewFragment marketViewFragment;
    CategoriaViewHolder currentActive;

    public CategoriaAdapter(ArrayList<CategoriaItem> cIL, MarketViewFragment marketViewFragment) {
        this.cIL= cIL;
        this.marketViewFragment = marketViewFragment;
    }

    @Override
    public CategoriaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.card_categoria, parent, false);
        CategoriaViewHolder cVH = new CategoriaViewHolder(v);
        return cVH;
    }

    @Override
    public void onBindViewHolder(@NonNull CategoriaViewHolder holder, int position) {
        CategoriaItem currentItem= this.cIL.get(position);
        holder.img.setImageResource(currentItem.getIcona());
        holder.id = currentItem.getId();
        holder.nome = currentItem.getNome();
    }

    @Override
    public int getItemCount() {
        return cIL.size();
    }

    public class CategoriaViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public ImageView img;
        public int id;
        public String nome;

        public CategoriaViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            img = itemView.findViewById(R.id.marketview_img_categoria);
        }

        public void onClick(View view){
            TextView txt = marketViewFragment.getActivity().findViewById(R.id.marketview_txt_categoria);
            if(currentActive != null) {
                currentActive.img.setSelected(false);
            }
            currentActive = this;
            if(marketViewFragment.activeFilter != id) {
                //marketViewFragment.filter(id);
                txt.setText(nome);
                marketViewFragment.activeFilter = id;
                this.img.setSelected(true);
            }
            else {
                txt.setText("");
                this.img.setSelected(false);
                //marketViewFragment.removeFilter();
                marketViewFragment.activeFilter = -1;
            }
        }
    }
}
