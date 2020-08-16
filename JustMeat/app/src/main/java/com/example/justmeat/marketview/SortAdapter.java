package com.example.justmeat.marketview;

import android.content.res.ColorStateList;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.justmeat.R;

import java.util.ArrayList;

class SortAdapter extends RecyclerView.Adapter<SortAdapter.SortViewHolder> {
    ArrayList<String> sortingMethods;
    SortModal sortModal;
    SortViewHolder currentActive;

    public SortAdapter(SortModal sortModal) {
        this.sortModal = sortModal;
        this.sortingMethods = sortModal.sortingMethods;
    }

    @NonNull
    @Override
    public SortAdapter.SortViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        SortViewHolder sVH;
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_marketview_sort_element, parent, false);
        sVH = new SortViewHolder(v);
        return sVH;
    }

    @Override
    public void onBindViewHolder(@NonNull SortAdapter.SortViewHolder holder, int position) {
        String currentItem = sortingMethods.get(position);
        if(currentItem.equals(sortModal.activeSortingMethod)){
            holder.sort_txt.setTextColor(ContextCompat.getColor(sortModal.getContext(), R.color.cerise));
            holder.sort_img.setSelected(true);
            currentActive = holder;
        }
        holder.sort_txt.setText(currentItem);
    }

    @Override
    public int getItemCount() {
        return sortingMethods.size();
    }

    public class SortViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView sort_txt;
        ImageView sort_img;
        ColorStateList defualtColor;

        public SortViewHolder(@NonNull View itemView) {
            super(itemView);
            sort_img = itemView.findViewById(R.id.marketview_btn_cardsort);
            sort_txt = itemView.findViewById(R.id.marketview_txt_cardsort);
            itemView.setOnClickListener(this);
            defualtColor = sort_txt.getTextColors();
        }

        @Override
        public void onClick(View v) {
            if(currentActive.sort_txt != this.sort_txt){
                //disattivo l'altro filtro
                currentActive.sort_img.setSelected(false);
                currentActive.sort_txt.setTextColor(defualtColor);
                //attivo il nuovo filtro
                activeSort();
            }
        }
        public void activeSort(){
            this.sort_txt.setTextColor(ContextCompat.getColor(sortModal.getContext(), R.color.cerise));
            this.sort_img.setSelected(true);
            currentActive = this;
            sortModal.sortBy(sort_txt.getText().toString());
        }
    }
}
