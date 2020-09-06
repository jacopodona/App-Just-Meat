package com.example.justmeat.marketview;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.justmeat.R;
import com.example.justmeat.carrello.CarrelloActivity;
import com.example.justmeat.utilities.HttpJsonRequest;
import com.example.justmeat.utilities.MyApplication;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MarketViewActivity extends AppCompatActivity{
    MarketViewActivity.CustomArray carrello;
    MarketViewFragment marketViewFragment;
    boolean barcodeActive = false;
    ArrayList<ProductItem> editFavoriteProd = new ArrayList<>();
    ImageView marketImage;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        System.out.println(((MyApplication)getApplication()).getHttpToken());
        super.onCreate(savedInstanceState);
        int id_negozio=getIntent().getIntExtra("idSupermercato",4);


        String nomeSupermercato= getIntent().getStringExtra("nomeSupermercato");



        setContentView(R.layout.activity_marketview);
        marketImage = findViewById(R.id.marketview_img_appbar);
        carrello = new CustomArray(((MyApplication) this.getApplication()).getCarrelloListProduct());
        setBackButton();
        setCarrelloButton();
        quantityOnCart();
        marketViewFragment = new MarketViewFragment(id_negozio);
        this.getSupportFragmentManager().beginTransaction().replace(R.id.marketview_frame_container, marketViewFragment).commit();
    }

    @Override
    protected void onStop() {
        if(!editFavoriteProd.isEmpty()) {
            for (ProductItem productItem : editFavoriteProd){
                JSONObject body = new JSONObject();
                if(productItem.pref){
                    try {
                        body.put("product_id", productItem.getId());
                        new HttpJsonRequest(getBaseContext(), "/api/v1/add_favourite", Request.Method.POST, body, ((MyApplication) getApplication()).getHttpToken(), new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                System.out.println(error.toString());
                            }
                        }).run();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else{
                    try {
                        body.put("product_id", productItem.getId());
                        new HttpJsonRequest(getBaseContext(), "/api/v1/del_favourite", Request.Method.POST, body, ((MyApplication) getApplication()).getHttpToken(), new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                System.out.println(error.toString());
                            }
                        }).run();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
            editFavoriteProd = new ArrayList<>();
        }
        super.onStop();
    }

    private void setBackButton() {
        ImageView back = findViewById(R.id.marketview_btn_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStack();
        } else if (barcodeActive){
            marketViewFragment.searchView.setQuery("", true);
            barcodeActive = false;
        }  else {
            super.onBackPressed();
        }
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
