package com.example.justmeat.carrello;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.justmeat.R;
import com.example.justmeat.marketview.ProductItem;
import com.example.justmeat.utilities.MyApplication;
import com.example.justmeat.whithdrawal.WithdrawalActivity;

import java.util.ArrayList;

public class CarrelloActivity extends AppCompatActivity {
    ArrayList<ProductItem> carrello;
    public TextView totale_txt;
    double tot;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        tot = 0.00;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carrello);
        carrello = ((MyApplication) this.getApplication()).getCarrelloListProduct();

        this.totale_txt = findViewById(R.id.carrello_txt_totale);
        for(ProductItem currentItem : carrello){
            tot += currentItem.getPrezzo()*currentItem.qt;
        }
        totale_txt.setText(String.format("%.2f",tot)+" €");

        setLayoutCarrello();
    }

    @Override
    protected void onStart() {
        super.onStart();
        setLayoutCarrello();
    }

    private void setLayoutCarrello() {
        Button gotoCheckout = findViewById(R.id.carrello_btn_checkout);
        ImageView emptyCart = findViewById(R.id.carrello_img_emptycart);
        TextView totTitle = findViewById(R.id.carrello_txt_totaletitle);
        RecyclerView rv = findViewById(R.id.carrello_rv);

        if(carrello.isEmpty()){
            emptyCart.setVisibility(View.VISIBLE);
            rv.setVisibility(View.GONE);
            totale_txt.setVisibility(View.GONE);
            totTitle.setVisibility(View.GONE);

            gotoCheckout.setText("Continua lo shopping");
            gotoCheckout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });
        } else {
            gotoCheckout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), WithdrawalActivity.class);
                    startActivity(intent);
                }
            });

            final ImageView pref_btn = findViewById(R.id.carrello_btn_bookmark);
            pref_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final Dialog pref_dialog = new Dialog(v.getContext());
                    pref_dialog.setContentView(R.layout.dialog_preferiti);
                    pref_dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    Button neutral_btn = pref_dialog.findViewById(R.id.carrello_btn_neutral_dialogpref);
                    neutral_btn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            pref_dialog.dismiss();
                        }
                    });
                    pref_dialog.show();
                }
            });
            RecyclerView.LayoutManager rvLM = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false );
            RecyclerView.Adapter rvAdapter = new CarrelloProductAdapter(this);

            rv.setLayoutManager(rvLM);
            rv.setAdapter(rvAdapter);

            emptyCart.setVisibility(View.GONE);
            rv.setVisibility(View.VISIBLE);
            totale_txt.setVisibility(View.VISIBLE);
            totTitle.setVisibility(View.VISIBLE);
        }

        ImageView back_btn = findViewById(R.id.carrello_btn_back);
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }


    @Override
    public void onBackPressed(){
        ((MyApplication) this.getApplication()).setCarrelloListProduct(carrello);
        super.onBackPressed();
    }
}
