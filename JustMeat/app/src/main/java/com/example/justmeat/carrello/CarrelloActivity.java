package com.example.justmeat.carrello;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.justmeat.R;
import com.example.justmeat.marketview.ProductItem;

import java.util.ArrayList;

public class CarrelloActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carrello);
        setLayoutCarrello();
    }

    private void setLayoutCarrello() {
        ImageView back_btn = findViewById(R.id.carrello_btn_back);
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
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

        //lista che viene creata nella marketview activity
        ArrayList<ProductItem> carrello = new ArrayList<>();
        carrello.add(new ProductItem(14.32, "Tagliata di Manzo",0));
        carrello.add(new ProductItem( 14.32, "Tagliata di Manzo",5));
        carrello.add(new ProductItem(14.32, "Tagliata di Manzo",0));

        RecyclerView rv = findViewById(R.id.carrello_rv);
        RecyclerView.LayoutManager rvLM = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false );
        RecyclerView.Adapter rvAdapter = new CarrelloProductAdapter(carrello);

        rv.setLayoutManager(rvLM);
        rv.setAdapter(rvAdapter);
    }
}
