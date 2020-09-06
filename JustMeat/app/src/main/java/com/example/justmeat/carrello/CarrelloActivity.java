package com.example.justmeat.carrello;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.justmeat.R;
import com.example.justmeat.marketview.ProductItem;
import com.example.justmeat.utilities.MyApplication;
import com.example.justmeat.whithdrawal.WithdrawalActivity;

import java.util.ArrayList;

public class CarrelloActivity extends AppCompatActivity implements RecyclerItemTouchHelper.RecyclerItemTouchHelperListener {
    ArrayList<ProductItem> carrello;
    public TextView totale_txt;
    double tot;
    CarrelloProductAdapter carrelloProductAdapter;
    int idSupermercato;
    String nomeSpkmt;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        tot = 0.00;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carrello);
        carrello = ((MyApplication) this.getApplication()).getCarrelloListProduct();

        idSupermercato = getIntent().getIntExtra("idSupermercato",4);
        nomeSpkmt = getIntent().getStringExtra("nomeSupermercato");

        TextView titolo = findViewById(R.id.carrello_txt_nomenegozio);
        titolo.setText(nomeSpkmt);

        this.totale_txt = findViewById(R.id.carrello_txt_totale);
        for(ProductItem currentItem : carrello){
            tot += (currentItem.getPrezzo()*currentItem.qt)*(1-currentItem.getDiscount());
        }
        totale_txt.setText(String.format("%.2f",tot)+" €");

        setLayoutCarrello();
    }

    @Override
    protected void onPause() {
        ((MyApplication) this.getApplication()).setCarrelloListProduct(carrello);
        super.onPause();

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

                    intent.putExtra("idSupermercato", idSupermercato);
                    intent.putExtra("nomeSupermercato", nomeSpkmt);
                    startActivity(intent);
                }
            });

            RecyclerView.LayoutManager rvLM = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false );
            carrelloProductAdapter = new CarrelloProductAdapter(this);

            rv.setLayoutManager(rvLM);
            rv.setAdapter(carrelloProductAdapter);

            ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new RecyclerItemTouchHelper(0, ItemTouchHelper.LEFT, this);
            new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(rv);

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


    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {
        if(viewHolder instanceof CarrelloProductAdapter.ProductViewHolder){
            carrelloProductAdapter.removeItem(viewHolder.getAdapterPosition());
        }
    }
}