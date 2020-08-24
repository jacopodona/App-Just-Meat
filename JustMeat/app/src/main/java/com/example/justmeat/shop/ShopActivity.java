package com.example.justmeat.shop;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.justmeat.R;
import com.example.justmeat.ordineSupermercato.StatoOrdineSupermercatoActivity;

import java.util.ArrayList;
import java.util.Random;

public class ShopActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    OrdineSupermercatoAdapter adapter;
    RecyclerView.LayoutManager layoutManager;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);

        context=this;

        Random rand=new Random();
        ArrayList<OrdineSupermercato> arrayList=new ArrayList<OrdineSupermercato>();
        arrayList.add(new OrdineSupermercato("101","5","Ritirato"));
        arrayList.add(new OrdineSupermercato("102","6","Ritirato"));
        arrayList.add(new OrdineSupermercato("103","18","Ritirato"));
        arrayList.add(new OrdineSupermercato("104","7", "Pronto per il ritiro"));
        arrayList.add(new OrdineSupermercato("105","10", "Pronto per il ritiro"));
        arrayList.add(new OrdineSupermercato("106","15", "In attesa"));
        arrayList.add(new OrdineSupermercato("107","9","In attesa"));

        recyclerView=findViewById(R.id.shop_recyclerview_listaordini);
        layoutManager=new LinearLayoutManager(this);
        adapter=new OrdineSupermercatoAdapter(arrayList,this);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new OrdineSupermercatoAdapter.onItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent=new Intent(context, StatoOrdineSupermercatoActivity.class);
                Log.e("ehehe","Dovrei aprire activity ma non lo faccio ehehe");
                startActivity(intent);
            }
        });

    }
}