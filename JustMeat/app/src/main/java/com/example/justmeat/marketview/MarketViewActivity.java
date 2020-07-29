package com.example.justmeat.marketview;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.justmeat.R;
import com.example.justmeat.carrello.CarrelloActivity;
import com.example.justmeat.utilities.MyApplication;

import java.util.ArrayList;

public class MarketViewActivity extends AppCompatActivity{
    MarketViewActivity.CustomArray carrello ;
    ImageView marketImage;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marketview);
        marketImage = findViewById(R.id.marketview_img_appbar);
        carrello = new CustomArray(((MyApplication) this.getApplication()).getCarrelloListProduct());
        setBackButton();
        setCarrelloButton();
        quantityOnCart();
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
    private void quantityOnCart() {
        TextView cartQt = findViewById(R.id.marketview_txt_cartqt);
        int qt = carrello.size();
        System.out.println(""+qt);
        if(qt == 0){
            cartQt.setVisibility(View.INVISIBLE);
        } else {
            cartQt.setVisibility(View.VISIBLE);
            cartQt.setText(""+qt);
        }
    }
    public class CustomArray extends ArrayList<ProductItem>{
        CustomArray(ArrayList<ProductItem> pList){
            super(pList);
        }

        @Override
        public boolean add(ProductItem productItem) {
            Boolean b = super.add(productItem);
            quantityOnCart();
            return b;
        }

        @Override
        public boolean remove(@Nullable Object o) {
            boolean b = super.remove(o);
            quantityOnCart();
            return b;
        }
    }
}
