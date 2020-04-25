package com.example.justmeat.product;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.justmeat.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {
    private ArrayList<ProductItem> pList;
    public static class ProductViewHolder extends RecyclerView.ViewHolder{
        public ImageView imgP;
        public TextView nome;
        public TextView price;
        public ProductViewHolder(View itemView) {
            super(itemView);
            imgP = itemView.findViewById(R.id.productImg);
            nome = itemView.findViewById(R.id.productName);
            price = itemView.findViewById(R.id.poductPrice);
        }
    }
    public ProductAdapter(ArrayList<ProductItem> xPL){
        this.pList= xPL;
    }
    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.product_item, parent, false);
        ProductViewHolder pVH = new ProductViewHolder(v);
        return pVH;
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        ProductItem currentItem= pList.get(position);
        holder.price.setText(currentItem.getPrezzo()+"/"+currentItem.getSize() );
        holder.imgP.setImageResource(currentItem.getImgProd());
        holder.nome.setText(currentItem.getNome());
    }

    @Override
    public int getItemCount() {
        return pList.size();
    }
}
