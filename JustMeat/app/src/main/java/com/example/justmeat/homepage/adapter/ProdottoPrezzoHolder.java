package com.example.justmeat.homepage.adapter;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.justmeat.R;

public class ProdottoPrezzoHolder extends RecyclerView.ViewHolder {

public TextView nomeProdotto;
public TextView prezzoProdotto;
public TextView qtProdotto;
public TextView discountProdotto;
public TextView titleDiscount;



public ProdottoPrezzoHolder(@NonNull View itemView) {
        super(itemView);
        nomeProdotto = itemView.findViewById(R.id.checkout_txt_prod_name);
        prezzoProdotto = itemView.findViewById(R.id.checkout_txt_prod_price);
        qtProdotto = itemView.findViewById(R.id.checkout_txt_prod_qt);
        discountProdotto = itemView.findViewById(R.id.checkout_txt_prod_discount);
        titleDiscount = itemView.findViewById(R.id.checkout_txt_title_prod_discount);
        }
}