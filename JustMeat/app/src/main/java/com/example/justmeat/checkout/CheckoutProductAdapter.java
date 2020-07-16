package com.example.justmeat.checkout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.justmeat.R;
import com.example.justmeat.marketview.ProductItem;

import java.util.ArrayList;

public class CheckoutProductAdapter extends RecyclerView.Adapter <CheckoutProductAdapter.ProductViewHolder> {
    ArrayList<ProductItem> productList;
    CheckoutActivity checkoutActivity;

    public CheckoutProductAdapter(ArrayList<ProductItem> productList, CheckoutActivity checkoutActivity) {
        this.productList = productList;
        this.checkoutActivity = checkoutActivity;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ProductViewHolder pVH;
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_checkout_prodotto, parent, false);
        pVH = new ProductViewHolder(view);
        return pVH;
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        ProductItem currentItem = productList.get(position);
        double price = currentItem.getPrezzo()*currentItem.getQt();
        holder.prodqt.setText(""+currentItem.getQt());
        holder.prodprice.setText(String.format("%.2f",price)+" €");
        holder.prodname.setText(currentItem.getNome());
        System.out.println(""+checkoutActivity.subtotal);
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder {
        TextView prodqt, prodname, prodprice;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            prodqt = itemView.findViewById(R.id.checkout_txt_prod_qt);
            prodname = itemView.findViewById(R.id.checkout_txt_prod_name);
            prodprice = itemView.findViewById(R.id.checkout_txt_prod_price);
        }
    }
}
