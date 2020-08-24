package com.example.justmeat.ordineSupermercato;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.justmeat.R;
import com.example.justmeat.shop.OrdineSupermercato;

public class StatoOrdineSupermercatoActivity extends AppCompatActivity {

    private TextView id,stato,numeroProdotti;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stato_ordine_supermercato);

        id=findViewById(R.id.statoordinesupermercato_id_ordine);
        stato=findViewById(R.id.statoordinesupermercato_stato_ordine);
        numeroProdotti=findViewById(R.id.statoordinesupermercato_num_prodotti);

        Intent intent=getIntent();
        Bundle bundle=intent.getExtras();
        OrdineSupermercato ordine= (OrdineSupermercato) bundle.getSerializable("Ordine");

        id.setText(ordine.getId());
        stato.setText(ordine.getStato());
        numeroProdotti.setText(ordine.getNumProdotti());

    }
}