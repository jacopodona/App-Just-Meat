package com.example.justmeat.carrello;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.justmeat.R;
import com.example.justmeat.marketview.ProductItem;

import java.util.ArrayList;

class CarrelloProductAdapter extends RecyclerView.Adapter<CarrelloProductAdapter.ProductViewHolder>{

    ArrayList<ProductItem> carrello;

    public CarrelloProductAdapter(ArrayList<ProductItem> carrello){
        this.carrello = carrello;
    }

    @NonNull
    @Override
    public CarrelloProductAdapter.ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ProductViewHolder productViewHolder;
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_carrello_prodotto, parent, false);
        productViewHolder = new ProductViewHolder(view);
        return productViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final CarrelloProductAdapter.ProductViewHolder holder, int position) {
        final ProductItem currentItem = carrello.get(position);
        //holder.imgProduct.setImageResource(currentItem.getImgProd());
        holder.nome.setText(currentItem.getNome());
        //holder.produttore.setText(currentItem.getProduttore());
        holder.prezzo.setText(currentItem.getPrezzo()+" €");
        holder.totale.setText((currentItem.getPrezzo()*holder.counter)+" €");

        holder.txt_qt.setText(""+holder.counter);

        holder.more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.counter++;
                holder.txt_qt.setText(""+holder.counter);
                holder.totale.setText((currentItem.getPrezzo()*holder.counter)+" €");
            }
        });

        holder.less.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(holder.counter>1){
                    holder.counter--;
                    holder.txt_qt.setText(""+holder.counter);
                    holder.totale.setText((currentItem.getPrezzo()*holder.counter)+" €");
                }
            }
        });

        holder.pref.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(holder.pref.isSelected()){
                    holder.pref.setSelected(false);
                } else {
                    holder.pref.setSelected(true);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return carrello.size();
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder {
        ImageView imgProduct, more, less, pref;
        TextView nome, produttore, prezzo, totale, txt_qt;
        CardView cardView;
        int counter;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.carrello_card_prodotto);
            nome = itemView.findViewById(R.id.carrello_txt_prodotto);
            produttore = itemView.findViewById(R.id.carrello_txt_produttore);
            prezzo = itemView.findViewById(R.id.carrello_txt_prezzo);
            totale = itemView.findViewById(R.id.carrello_txt_tot);
            imgProduct = itemView.findViewById(R.id.carrello_img_prodotto);
            txt_qt = itemView.findViewById(R.id.carrello_txt_qt);
            more = itemView.findViewById(R.id.carrello_btn_more);
            less = itemView.findViewById(R.id.carrello_btn_less);
            pref = itemView.findViewById(R.id.carrello_btn_pref);
            counter = 1;

            cardView.setOnTouchListener(new OnSwipeTouchListener(itemView.getContext()){
                @Override
                public void onSwipeLeft() {

                }
            });
        }
    }
}
