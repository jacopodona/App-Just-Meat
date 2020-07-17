package com.example.justmeat.marketview;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.example.justmeat.R;

public class PruductFragment extends Fragment {
    ProductItem prodotto;
    int counter = 1;

    PruductFragment(ProductItem prodotto){
        this.prodotto = prodotto;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_prodotto, container, false);
        setLayout(view);
        return view;
    }

    private void setLayout(View view){
        //ImageView img = view.findViewById(R.id.marketview_img_prodotto);
        //img.setImageDrawable();

        TextView nome = view.findViewById(R.id.marketview_txt_prodotto);
        nome.setText(prodotto.getNome());

        final TextView prezzo = view.findViewById(R.id.marketview_txt_prezzo);
        prezzo.setText(String.format("%.2f",prodotto.getPrezzo())+" €");

        final TextView txt_qt = view.findViewById(R.id.marketview_txt_qt);
        txt_qt.setText(""+counter);

        ImageView more = view.findViewById(R.id.marketview_btn_more);
        more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                counter++;
                txt_qt.setText(""+counter);
            }
        });

        ImageView less = view.findViewById(R.id.marketview_btn_less);
        less.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(counter>1){
                    counter--;
                    txt_qt.setText(""+counter);
                }
            }
        });

        final ImageView love = view.findViewById(R.id.marketview_btn_pref);

        if(prodotto.pref){
            love.setSelected(true);
        } else{
            love.setSelected(false);
        }

        love.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(prodotto.pref){
                    love.setSelected(false);
                    prodotto.pref = false;
                    //informazione da salvare
                } else {
                    love.setSelected(true);
                    prodotto.pref = true;
                }
            }
        });

        CardView aggiungi = view.findViewById(R.id.marketview_btn_add);
        aggiungi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prodotto.qt = counter;
                MarketViewActivity marketViewActivity = (MarketViewActivity) PruductFragment.super.getActivity();
                marketViewActivity.carrello.add(prodotto);
            }
        });
    }
}
