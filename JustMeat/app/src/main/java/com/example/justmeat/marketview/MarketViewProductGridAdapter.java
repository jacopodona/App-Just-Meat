package com.example.justmeat.marketview;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.justmeat.R;

import java.util.ArrayList;
import java.util.List;

public class MarketViewProductGridAdapter extends RecyclerView.Adapter<MarketViewProductGridAdapter.ProductViewHolder> implements Filterable {
    private ArrayList<ProductItem> pListFull;
    private ArrayList<ProductItem> pList;
    private MarketViewFragment marketViewFragment;

    @Override
    public Filter getFilter() {
        return productFilter;
    }
    private Filter productFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<ProductItem> filteredList = new ArrayList<>();
            if (marketViewFragment.activeFilter == -1){
                if(constraint == null || constraint.length() == 0 ){
                        filteredList.addAll(pListFull);
                    }  else {
                        String filterPattern = constraint.toString().toLowerCase().trim();
                        for (ProductItem productItem : pListFull) {
                            if (productItem.getNome().toLowerCase().contains(filterPattern)){
                                filteredList.add(productItem);
                            }
                        }
                }
            } else {
                if(constraint == null || constraint.length() == 0 ){
                    for (ProductItem productItem : pListFull) {
                        if (productItem.getIdCategoria() == marketViewFragment.activeFilter){
                            filteredList.add(productItem);
                        }
                    }
                }  else {
                    String filterPattern = constraint.toString().toLowerCase().trim();
                    for (ProductItem productItem : pListFull) {
                        if (productItem.getNome().toLowerCase().contains(filterPattern) && productItem.getIdCategoria() == marketViewFragment.activeFilter){
                            filteredList.add(productItem);
                        }
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;

            return results;
        }
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            pList.clear();
            pList.addAll((List)results.values);
            notifyDataSetChanged();
        }
    };

    public static class ProductViewHolder extends RecyclerView.ViewHolder{
        public ImageView imgProduct;
        public TextView nome, price, discount;
        CardView cardView, add_btn;

        public ProductViewHolder(View itemView) {
            super(itemView);
            imgProduct = itemView.findViewById(R.id.marketview_img_prodcard_grid);
            nome = itemView.findViewById(R.id.marketview_txt_prodcard_nome_grid);
            price = itemView.findViewById(R.id.marketview_txt_prodcard_prezzo_grid);
            cardView = (CardView) itemView.findViewById(R.id.product_card_grid);
            add_btn = itemView.findViewById(R.id.marketview_btn_prodcard_add_grid);
            discount = itemView.findViewById(R.id.marketview_txt_prodcard_discount_grid);
        }
    }

    public MarketViewProductGridAdapter(MarketViewFragment marketViewFragment){
        this.marketViewFragment = marketViewFragment;
        pList = marketViewFragment.pList;
        this.pListFull = marketViewFragment.pListFull;
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
                    currentItem.qt += 1;
                } else{
                    currentItem.qt = 1;
                    marketViewActivity.carrello.add(currentItem);
                }

            }
        });

        if(currentItem.getDiscount()>0){
            holder.discount.setText((currentItem.getDiscount()*100) +"%");
        } else {
            holder.discount.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return pList.size();
    }
}
