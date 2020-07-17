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
    CarrelloActivity carrelloActivity;
    ArrayList<ProductItem> carrello;

    public CarrelloProductAdapter(CarrelloActivity carrelloActivity){
        this.carrello = carrelloActivity.carrello;
        this.carrelloActivity = carrelloActivity;
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
    public void onBindViewHolder(@NonNull final CarrelloProductAdapter.ProductViewHolder holder, final int position) {
        final ProductItem currentItem = carrello.get(position);
        //holder.imgProduct.setImageResource(currentItem.getImgProd());
        holder.nome.setText(currentItem.getNome());
        //holder.produttore.setText(currentItem.getProduttore());
        if(currentItem.qt > 1){
            holder.counter = currentItem.qt;
        } else {
            holder.counter = 1;
        }

        holder.prezzo.setText(String.format("%.2f",currentItem.getPrezzo())+" €");
        double price = currentItem.getPrezzo()*holder.counter;
        holder.totale.setText(String.format("%.2f",price)+" €");

        holder.txt_qt.setText(""+holder.counter);

        holder.more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.counter++;
                currentItem.qt = holder.counter;
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
                    currentItem.qt = holder.counter;
                    holder.txt_qt.setText(""+holder.counter);
                    double price = currentItem.getPrezzo()*holder.counter;
                    holder.totale.setText(String.format("%.2f",price)+" €");
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
        holder.cardView.setOnTouchListener(new OnSwipeTouchListener(carrelloActivity){
            @Override
            public void onSwipeLeft() {
                System.out.println("rem item");
                currentItem.qt = 0;
                carrelloActivity.carrello.remove(currentItem);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, carrello.size());
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
            totale = itemView.findViewById(R.id.carrello_txt_totproduct);
            imgProduct = itemView.findViewById(R.id.carrello_img_prodotto);
            txt_qt = itemView.findViewById(R.id.carrello_txt_qt);
            more = itemView.findViewById(R.id.carrello_btn_more);
            less = itemView.findViewById(R.id.carrello_btn_less);
            pref = itemView.findViewById(R.id.carrello_btn_pref);
        }
    }
}
