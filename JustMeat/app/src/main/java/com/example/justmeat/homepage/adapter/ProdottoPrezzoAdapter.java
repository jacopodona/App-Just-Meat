package com.example.justmeat.homepage.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.justmeat.R;
import com.example.justmeat.marketview.ProductItem;

import java.util.ArrayList;

public class ProdottoPrezzoAdapter extends RecyclerView.Adapter<ProdottoPrezzoHolder> {

    ArrayList<ProductItem> pList;

    public ProdottoPrezzoAdapter(ArrayList<ProductItem> pList) {
        this.pList = pList;
    }

    @NonNull
    @Override
    public ProdottoPrezzoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_checkout_prodotto, parent, false);//era shr_product_card
        return new ProdottoPrezzoHolder(layoutView);
    }

    @Override
    public void onBindViewHolder(@NonNull ProdottoPrezzoHolder holder, int position) {
        ProductItem currentItem = pList.get(position);
        double price = currentItem.getPrezzo() * currentItem.getQt();
        holder.qtProdotto.setText(""+currentItem.getQt());
        if(currentItem.getDiscount() > 0){
            holder.titleDiscount.setVisibility(View.VISIBLE);
            double discountPrezzo = price * currentItem.getDiscount();
            holder.discountProdotto.setText("-"+String.format("%.2f",discountPrezzo)+" €");
        } else {
            holder.titleDiscount.setVisibility(View.INVISIBLE);
        }
        holder.prezzoProdotto.setText(String.format("%.2f",price)+" €");
        holder.nomeProdotto.setText(currentItem.getNome());
    }

    @Override
    public int getItemCount() {
        return pList.size();
    }
}