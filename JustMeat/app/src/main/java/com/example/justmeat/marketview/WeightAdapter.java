package com.example.justmeat.marketview;

import android.content.res.ColorStateList;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.justmeat.R;

import java.util.ArrayList;

class WeightAdapter extends RecyclerView.Adapter<WeightAdapter.WeightViewHolder> {
    ArrayList<Integer> weights;
    String um;
    ProductFragment productFragment;
    WeightViewHolder currentActive;
    public WeightAdapter(ArrayList<Integer> weights, String um, ProductFragment productFragment) {
        this.weights = weights;
        this.um = um;
        this.productFragment = productFragment;
    }

    @NonNull
    @Override
    public WeightViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_marketview_weight, parent, false);
        WeightViewHolder weightViewHolder = new WeightViewHolder(view);
        return weightViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final WeightViewHolder holder, int position) {
        final Integer valWeight = weights.get(position);
        holder.value.setText(valWeight+" "+ um );
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currentActive != holder){
                    if(currentActive != null){
                        currentActive.WightUnactive(v);
                    }
                    currentActive = holder;
                    holder.WeightActive(v);
                    productFragment.updatePrezzo(valWeight);
                }
            }
        });

        if(position == 0){
            holder.WeightActive(holder.itemView);
            currentActive = holder;
        }
    }

    @Override
    public int getItemCount() {
        return weights.size();
    }

    public class WeightViewHolder extends RecyclerView.ViewHolder {
        TextView value;
        CardView cardView;
        ColorStateList defaultColor;
        public WeightViewHolder(@NonNull View itemView) {
            super(itemView);
            value = itemView.findViewById(R.id.marketview_txt_value_weight);
            cardView = itemView.findViewById(R.id.marketview_card_weight);
            defaultColor = value.getTextColors();
        }
        public void WeightActive(View v){
            cardView.setCardBackgroundColor(ContextCompat.getColor(v.getContext(), R.color.cerise));
            value.setTextColor(ContextCompat.getColor(v.getContext(), R.color.white));
        }
        public void WightUnactive(View v){
            cardView.setCardBackgroundColor(ContextCompat.getColor(v.getContext(), R.color.white));
            value.setTextColor(defaultColor);
        }
    }
}
