package com.example.justmeat.marketview;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.justmeat.R;
import com.example.justmeat.carrello.CarrelloActivity;
import com.example.justmeat.utilities.MyApplication;

import java.util.ArrayList;

public class MarketViewActivity extends AppCompatActivity{
    ArrayList<ProductItem> carrello;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marketview);
        carrello = ((MyApplication) this.getApplication()).getCarrelloListProduct();
        setBackButton();
        setCarrelloButton();
        this.getSupportFragmentManager().beginTransaction().replace(R.id.marketview_frame_container, new MarketViewFragment()).commit();
    }

    private void setBackButton() {
        ImageView back = findViewById(R.id.marketview_btn_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
                    getSupportFragmentManager().popBackStack();
                } else {
                    onBackPressed();
                }
            }
        });
    }
    private void setCarrelloButton() {
        final AppCompatActivity context = this;
        ImageView cart = findViewById(R.id.marketview_btn_cart);
        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), CarrelloActivity.class);
                ((MyApplication) context.getApplication()).setCarrelloListProduct(carrello);
                startActivity(intent);
            }
        });
    }

}
