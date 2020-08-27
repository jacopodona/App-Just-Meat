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

class MarketViewProductListAdapter extends RecyclerView.Adapter<MarketViewProductListAdapter.ProductViewHolder> implements Filterable {
    private ArrayList<ProductItem> pList;
    private ArrayList<ProductItem> pListFull;
    private MarketViewFragment marketViewFragment;

    public MarketViewProductListAdapter(MarketViewFragment marketViewFragment) {
        this.marketViewFragment = marketViewFragment;
        this.pList = marketViewFragment.pList;
        this.pListFull = marketViewFragment.pListFull;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ProductViewHolder pVH;
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_marketview_list_prodotto, parent, false);
        pVH = new ProductViewHolder(v);
        return pVH;
    }

    @Override
    public void onBindViewHolder(@NonNull final ProductViewHolder holder, int position) {
        final ProductItem currentItem= pList.get(position);
        holder.nome.setText(currentItem.getNome());
        holder.counter = 1;

        holder.prezzo.setText(String.format("%.2f",currentItem.getPrezzo())+" €");
        double price = currentItem.getPrezzo()*holder.counter;
        holder.totale.setText(String.format("%.2f",price)+" €");

        holder.txt_qt.setText(""+holder.counter);

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                marketViewFragment.getActivity().getSupportFragmentManager().
                        beginTransaction().replace(R.id.marketview_frame_container, new ProductFragment(currentItem)).addToBackStack("product").commit();
            }
        });

        holder.more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.counter++;
                holder.txt_qt.setText(""+holder.counter);
                double price = currentItem.getPrezzo()*holder.counter;
                holder.totale.setText(String.format("%.2f",price)+" €");
            }
        });

        holder.less.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(holder.counter>1){
                    holder.counter--;
                    holder.txt_qt.setText(""+holder.counter);
                    double price = currentItem.getPrezzo()*holder.counter;
                    holder.totale.setText(String.format("%.2f",price)+" €");
                }
            }
        });
        holder.addbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //reset the card counter to 1
                MarketViewActivity marketViewActivity = (MarketViewActivity) marketViewFragment.getActivity();
                boolean check = false;
                for(ProductItem carrelloItem : marketViewActivity.carrello){
                    if(currentItem.getId() == carrelloItem.getId() && carrelloItem.getWeight() == currentItem.getWeight()){
                        carrelloItem.qt += holder.counter;
                        check = true;
                    }
                } if(!check){
                    currentItem.qt = 1;
                    marketViewActivity.carrello.add(currentItem);
                }
                holder.counter = 1;
                holder.txt_qt.setText(""+holder.counter);
                holder.totale.setText(String.format("%.2f",currentItem.getPrezzo())+" €");
            }
        });
    }

    @Override
    public int getItemCount() {
        return pList.size();
    }
    @Override
    public Filter getFilter() {
        return productFilter;
    }
    private Filter productFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<ProductItem> filteredList = new ArrayList<>();
            if (marketViewFragment.activeFilter == -1) {
                if (constraint == null || constraint.length() == 0) {
                    filteredList.addAll(pListFull);
                } else {
                    String filterPattern = constraint.toString().toLowerCase().trim();
                    for (ProductItem productItem : pListFull) {
                        if (productItem.getNome().toLowerCase().contains(filterPattern)) {
                            filteredList.add(productItem);
                        }
                    }
                }
            } else {
                if (constraint == null || constraint.length() == 0) {
                    for (ProductItem productItem : pListFull) {
                        if (productItem.getCategoria() == marketViewFragment.activeFilter) {
                            filteredList.add(productItem);
                        }
                    }
                } else {
                    String filterPattern = constraint.toString().toLowerCase().trim();
                    for (ProductItem productItem : pListFull) {
                        if (productItem.getNome().toLowerCase().contains(filterPattern) && productItem.getCategoria() == marketViewFragment.activeFilter) {
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

    public class ProductViewHolder extends RecyclerView.ViewHolder {
        ImageView imgProduct, more, less;
        TextView nome, produttore, prezzo, totale, txt_qt;
        CardView cardView, addbtn;
        int counter;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.marketview_card_prodcard_list);
            nome = itemView.findViewById(R.id.marketview_txt_prodcard_nome_list);
            produttore = itemView.findViewById(R.id.marketview_txt_prodcard_produttore_list);
            prezzo = itemView.findViewById(R.id.marketview_txt_prodcard_prezzo_list);
            totale = itemView.findViewById(R.id.marketview_txt_prodcard_totproduct_list);
            imgProduct = itemView.findViewById(R.id.marketview_img_prodcard_prodotto_list);
            txt_qt = itemView.findViewById(R.id.marketview_txt_prodcard_qt_list);
            more = itemView.findViewById(R.id.marketview_btn_prodcard_more_list);
            less = itemView.findViewById(R.id.marketview_btn_prodcard_less_list);
            addbtn = itemView.findViewById(R.id.marketview_btn_prodcard_add_list);
        }
    }

}
