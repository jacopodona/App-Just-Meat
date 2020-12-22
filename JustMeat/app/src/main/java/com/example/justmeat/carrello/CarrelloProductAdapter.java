package com.example.justmeat.carrello;


import android.content.DialogInterface;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.StrikethroughSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
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
        Glide.with(carrelloActivity)
                .load("http://just-feet.herokuapp.com"+currentItem.getImage())
                .override(360, 240)
                .into(holder.imgProduct);
        holder.imgProduct.setAdjustViewBounds(true);
        holder.nome.setText(currentItem.getNome());
        holder.produttore.setText(currentItem.getManufacturer());
        if(currentItem.qt > 1){
            holder.counter = currentItem.qt;
        } else {
            holder.counter = 1;
        }
        holder.weight.setText(currentItem.getWeight()+" "+currentItem.getUm());

        double price = currentItem.getPrezzo()*holder.counter *(1-currentItem.getDiscount());

        if(currentItem.getDiscount()>0){
            double discountPrezzo = currentItem.getPrezzo()*(1-currentItem.getDiscount());
            String defaultPrezzo = String.format("%.2f",currentItem.getPrezzo())+"€";
            SpannableString stringPrezzo = new SpannableString(defaultPrezzo +" " +String.format("%.2f",discountPrezzo)+"€" );
            stringPrezzo.setSpan(new StrikethroughSpan(), 0, defaultPrezzo.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            holder.prezzo.setText(stringPrezzo);
        } else {
            holder.prezzo.setText(String.format("%.2f",currentItem.getPrezzo())+"€");
        }

        holder.totale.setText(String.format("%.2f",price)+" €");

        holder.txt_qt.setText(""+holder.counter);

        holder.more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.counter++;
                currentItem.qt = holder.counter;
                holder.txt_qt.setText(""+holder.counter);
                double price = currentItem.getPrezzo()*holder.counter * (1-currentItem.getDiscount());
                holder.totale.setText(String.format("%.2f",price)+" €");
                carrelloActivity.tot += currentItem.getPrezzo()*(1-currentItem.getDiscount());
                carrelloActivity.totale_txt.setText(String.format("%.2f",carrelloActivity.tot)+" €");
            }
        });

        holder.less.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(holder.counter>1){
                    holder.counter--;
                    currentItem.qt = holder.counter;
                    holder.txt_qt.setText(""+holder.counter);
                    double price = currentItem.getPrezzo()*holder.counter * (1-currentItem.getDiscount());
                    holder.totale.setText(String.format("%.2f",price)+" €");
                    carrelloActivity.tot -= currentItem.getPrezzo()*(1-currentItem.getDiscount());
                    carrelloActivity.totale_txt.setText(String.format("%.2f",carrelloActivity.tot)+" €");
                } else if(holder.counter == 1){
                    new AlertDialog.Builder(carrelloActivity)
                            .setTitle("Rimuovere Articolo")
                            .setMessage("Sei sicuro di voler rimuovere l'articolo dal carrello?")
                            .setPositiveButton("Rimuovi", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    removeItem(position);
                                    carrelloActivity.checkEmptyCart();
                                }
                            })
                            .setNegativeButton("Annulla", null)
                            .show();

                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return carrello.size();
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder {
        ImageView imgProduct, more, less;
        TextView nome, produttore, prezzo, totale, txt_qt, weight;
        CardView cardView;
        int counter;
        View viewBackground, viewForeground;

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
            weight = itemView.findViewById(R.id.carrello_txt_weight);
            viewBackground = itemView.findViewById(R.id.carrello_rl_background);
            viewForeground = itemView.findViewById(R.id.carrello_cl_foreground);
        }
    }

    public void removeItem(int position){
        ProductItem currentItem = carrello.get(position);
        carrelloActivity.tot -= currentItem.getPrezzo()*(1-currentItem.getDiscount())*currentItem.getQt();
        carrelloActivity.totale_txt.setText(String.format("%.2f",carrelloActivity.tot)+" €");
        carrelloActivity.carrello.remove(position);
        notifyItemRemoved(position);
    }
}