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

public class MarketViewProductAdapter extends RecyclerView.Adapter<MarketViewProductAdapter.ProductViewHolder> {
    private ArrayList<ProductItem> pList;
    private MarketViewFragment marketViewFragment;

    public static class ProductViewHolder extends RecyclerView.ViewHolder{
        public ImageView imgP;
        public TextView nome;
        public TextView price;
        CardView cardView;

        public ProductViewHolder(View itemView) {
            super(itemView);
            imgP = itemView.findViewById(R.id.marketview_img_prodcard);
            nome = itemView.findViewById(R.id.marketview_txt_prodcard_nome);
            price = itemView.findViewById(R.id.marketview_txt_prodcard_prezzo);
            cardView = (CardView) itemView.findViewById(R.id.product_card_id);
        }
    }

    public MarketViewProductAdapter(MarketViewFragment marketViewFragment, ArrayList<ProductItem> xPL){
        this.marketViewFragment = marketViewFragment;
        this.pList = xPL;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ProductViewHolder pVH;
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_marketview_prodotto, parent, false);
        pVH = new ProductViewHolder(v);
        return pVH;
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, final int position) {
        final ProductItem currentItem= pList.get(position);
        holder.price.setText(currentItem.getPrezzo()+" €");
       // holder.imgP.setImageResource(currentItem.getImgProd());
        holder.nome.setText(currentItem.getNome());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                marketViewFragment.getActivity().getSupportFragmentManager().
                        beginTransaction().replace(R.id.marketview_frame_container, new PruductFragment(currentItem)).addToBackStack("product").commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return pList.size();
    }
}
