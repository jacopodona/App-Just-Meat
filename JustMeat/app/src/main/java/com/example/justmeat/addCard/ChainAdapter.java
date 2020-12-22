package com.example.justmeat.addCard;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.justmeat.R;

import java.util.ArrayList;

class ChainAdapter extends RecyclerView.Adapter<ChainAdapter.ChainViewHolder>{
    ArrayList<Integer> chain_ids;
    AddCardDialog addCardDialog;
    public ChainAdapter(AddCardDialog addCardDialog) {
        this.addCardDialog = addCardDialog;
        this.chain_ids = addCardDialog.chain_ids;
    }

    @NonNull
    @Override
    public ChainViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ChainViewHolder cVH;
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_chain_addfidcard, parent, false);
        cVH = new ChainViewHolder(v);
        return cVH;
    }

    @Override
    public void onBindViewHolder(@NonNull final ChainViewHolder holder, int position) {
        final int currentItem = chain_ids.get(position);
        Glide.with(addCardDialog.getContext())
                .load("http://just-feet.herokuapp.com/images/sl_" + currentItem + ".jpg" )
                .override(80,40)
                .into(holder.chain_logo);
        switch (currentItem){
            case 1:{
                holder.chain_name.setText("naturasì");
                break;
            }
            case 2:{
                holder.chain_name.setText("ALDI");
                break;
            }
            case 3:{
                holder.chain_name.setText("COOP");
                break;
            }
            case 4:{
                holder.chain_name.setText("Duplicard");
                break;
            }
        }
        holder.chain_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(addCardDialog.chain_id == currentItem){
                    //tutto disabilitato
                    holder.chain_card.setBackgroundColor(holder.chain_card.getContext().getResources().getColor(R.color.white));
                    addCardDialog.chain_id = 0;

                } else {
                    //disabilito il precedente
                    if(addCardDialog.actualChainVH != null){
                        addCardDialog.actualChainVH.chain_card.setBackgroundColor(holder.chain_card.getContext().getResources().getColor(R.color.white));
                    }


                    addCardDialog.chain_id = currentItem;

                    addCardDialog.actualChainVH = holder;
                    holder.chain_card.setBackgroundColor(holder.chain_card.getContext().getResources().getColor(R.color.maximumyellowred));
                    //abilito il nuovo
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return chain_ids.size();
    }

    public class ChainViewHolder extends RecyclerView.ViewHolder {
        ImageView chain_logo;
        TextView chain_name;
        ConstraintLayout chain_card;
        public ChainViewHolder(@NonNull View itemView) {
            super(itemView);
            chain_logo = itemView.findViewById(R.id.addfidcard_img_chaincard);
            chain_name = itemView.findViewById(R.id.addfidcard_txt_chaincard);
            chain_card = itemView.findViewById(R.id.addfidcard_card_chaincard);
        }
    }
}
