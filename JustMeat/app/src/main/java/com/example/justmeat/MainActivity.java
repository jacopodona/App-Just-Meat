package com.example.justmeat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
import com.example.justmeat.categoria.*;
import com.example.justmeat.product.*;

public class MainActivity extends AppCompatActivity {




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        CategoriaItem[] catList = new CategoriaItem[10];
        ArrayList<ProductItem> listProd = new ArrayList<ProductItem>();

        for (int i=0; i<10; i++){
            catList[i] = new CategoriaItem(R.drawable.ic_whatshot, "cat"+i);
        }

        //alcuni esempi di inizializzazione
        listProd.add(new ProductItem(R.drawable.ic_cloud_black, 2.30 ,"1 L", "Acqua" ));
        listProd.add(new ProductItem(R.drawable.ic_cloud_black, 5.77,"kg", "Polpette di Manzo" ));
        listProd.add(new ProductItem(R.drawable.ic_cloud_black, 2.30 ,"1 L", "Acqua" ));
        listProd.add(new ProductItem(R.drawable.ic_cloud_black, 5.77,"kg", "Polpette di Manzo" ));
        listProd.add(new ProductItem(R.drawable.ic_cloud_black, 2.30 ,"1 L", "Acqua" ));
        listProd.add(new ProductItem(R.drawable.ic_cloud_black, 5.77,"kg", "Polpette di Manzo" ));
        listProd.add(new ProductItem(R.drawable.ic_cloud_black, 2.30 ,"1 L", "Acqua" ));
        listProd.add(new ProductItem(R.drawable.ic_cloud_black, 5.77,"kg", "Polpette di Manzo" ));
        listProd.add(new ProductItem(R.drawable.ic_cloud_black, 2.30 ,"1 L", "Acqua" ));
        listProd.add(new ProductItem(R.drawable.ic_cloud_black, 5.77,"kg", "Polpette di Manzo" ));
        listProd.add(new ProductItem(R.drawable.ic_cloud_black, 2.30 ,"1 L", "Acqua" ));
        listProd.add(new ProductItem(R.drawable.ic_cloud_black, 5.77,"kg", "Polpette di Manzo" ));
        listProd.add(new ProductItem(R.drawable.ic_cloud_black, 2.30 ,"1 L", "Acqua" ));
        listProd.add(new ProductItem(R.drawable.ic_cloud_black, 5.77,"kg", "Polpette di Manzo" ));
        listProd.add(new ProductItem(R.drawable.ic_cloud_black, 2.30 ,"1 L", "Acqua" ));
        listProd.add(new ProductItem(R.drawable.ic_cloud_black, 5.77,"kg", "Polpette di Manzo" ));



        setCategoryLayout(catList);
        setProductLayout(listProd);

    }
    //metodo che inizializza la visuale delle categorie e la aggiunge alla visuale principale
    private void setCategoryLayout(CategoriaItem[] catList){
        //rv che andremo a visualizzare relativo alle categorie
        RecyclerView cRV;
        //"carica" solo le card che verranno visualizzate categorie
        RecyclerView.Adapter cRVA;
        //allign the item categorie
        RecyclerView.LayoutManager cRVLM;
        cRV = findViewById(R.id.categoryRecycleView);
        cRV.setHasFixedSize(true ); //vengono mostrate le card che ci stanno nello spazio senza sovropporle
        cRVLM= new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        cRVA = new CategoriaAdapter(catList);

        cRV.setLayoutManager(cRVLM);
        cRV.setAdapter(cRVA);
    }
    private void setProductLayout(ArrayList<ProductItem> pList){
        RecyclerView pRV;
        RecyclerView.Adapter pRVA;
        RecyclerView.LayoutManager pRVLM;
        pRV = findViewById(R.id.productRecycleView);
        pRV.setHasFixedSize(true);
        pRVLM = new GridLayoutManager(this, 3);
        pRVA = new ProductAdapter(pList);
        pRV.setLayoutManager(pRVLM);
        pRV.setAdapter(pRVA);
    }
}


