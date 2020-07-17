package com.example.justmeat.marketview;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.justmeat.R;

import java.util.ArrayList;

public class MarketViewProductGridAdapter extends RecyclerView.Adapter<MarketViewProductGridAdapter.ProductViewHolder> {
    private ArrayList<ProductItem> pList;
    private MarketViewFragment marketViewFragment;

    public static class ProductViewHolder extends RecyclerView.ViewHolder{
        public ImageView imgProduct;
        public TextView nome, price;
        CardView cardView, add_btn;

        public ProductViewHolder(View itemView) {
            super(itemView);
            imgProduct = itemView.findViewById(R.id.marketview_img_prodcard_grid);
            nome = itemView.findViewById(R.id.marketview_txt_prodcard_nome_grid);
            price = itemView.findViewById(R.id.marketview_txt_prodcard_prezzo_grid);
            cardView = (CardView) itemView.findViewById(R.id.product_card_grid);
            add_btn = itemView.findViewById(R.id.marketview_btn_prodcard_add_grid);
        }
    }

    public MarketViewProductGridAdapter(MarketViewFragment marketViewFragment){
        this.marketViewFragment = marketViewFragment;
        pList = marketViewFragment.pList;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ProductViewHolder pVH;
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_marketview_grid_prodotto, parent, false);
        pVH = new ProductViewHolder(v);
        return pVH;
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, final int position) {
        final ProductItem currentItem= pList.get(position);
        holder.price.setText(String.format("%.2f",currentItem.getPrezzo())+" €");
       // holder.imgP.setImageResource(currentItem.getImgProd());
        holder.nome.setText(currentItem.getNome());

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                marketViewFragment.getActivity().getSupportFragmentManager().
                        beginTransaction().replace(R.id.marketview_frame_container, new PruductFragment(currentItem)).addToBackStack("product").commit();
            }
        });

        holder.add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MarketViewActivity marketViewActivity = (MarketViewActivity) marketViewFragment.getActivity();
                if (currentItem.qt>0){
                    currentItem.qt +=1;
                } else{
                    currentItem.qt =1;
                    marketViewActivity.carrello.add(currentItem);
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return pList.size();
    }
}
