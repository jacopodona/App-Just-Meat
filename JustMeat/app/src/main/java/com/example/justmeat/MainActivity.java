package com.example.justmeat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;
import com.example.justmeat.categoria.*;
import com.example.justmeat.product.*;

public class MainActivity extends AppCompatActivity {


    ArrayList<ProductItem> listProd ;
    public int activeFilter = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ArrayList<CategoriaItem> catList = new ArrayList<CategoriaItem>();
        listProd = new ArrayList<ProductItem>();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //inizializzazione barra categoria
        for (int i= 0;i<10; i++){
            catList.add(new CategoriaItem(R.drawable.ic_whatshot, "cat"+i, i));
        }
        //alcuni esempi di inizializzazione
        {
            listProd.add(new ProductItem(R.drawable.carot, 2.30, "Carote",1));
            listProd.add(new ProductItem(R.drawable.pescespada, 5.77, "Filetto di pesce spada",2));
            listProd.add(new ProductItem(R.drawable.cola, 0.90, "Coca Cola",3));
            listProd.add(new ProductItem(R.drawable.salad, 1.35, "Insalata ice berg",5));
            listProd.add(new ProductItem(R.drawable.soap, 1.58, "Sapone scrub",5));
            listProd.add(new ProductItem(R.drawable.tagliata, 14.32, "Tagliata di Manzo",5));
            listProd.add(new ProductItem(R.drawable.tonno, 0.87, "Tonno in scatola",1));
            listProd.add(new ProductItem(R.drawable.carot, 2.30, "Carote",2));
            listProd.add(new ProductItem(R.drawable.pescespada, 5.77, "Filetto di pesce spada",3));
            listProd.add(new ProductItem(R.drawable.cola, 0.90, "Coca Cola",6));
            listProd.add(new ProductItem(R.drawable.salad, 1.35, "Insalata ice berg",8));
            listProd.add(new ProductItem(R.drawable.soap, 1.58, "Sapone scrub",9));
            listProd.add(new ProductItem(R.drawable.tagliata, 14.32, "Tagliata di Manzo",0));
            listProd.add(new ProductItem(R.drawable.tonno, 0.87, "Tonno in scatola al naturale",2));
        }

        setCategoryLayout(catList);
        setProductLayout(listProd);

    }
    //metodo che inizializza la visuale delle categorie e la aggiunge alla visuale principale
    private void setCategoryLayout(ArrayList<CategoriaItem> catList){
        //rv che andremo a visualizzare relativo alle categorie
        RecyclerView cRV;
        //"carica" solo le card che verranno visualizzate categorie
        RecyclerView.Adapter cRVA;
        //allign the item categorie
        RecyclerView.LayoutManager cRVLM;
        cRV = findViewById(R.id.categoryRecycleView);
        cRV.setHasFixedSize(true ); //vengono mostrate le card che ci stanno nello spazio senza sovropporle
        cRVLM= new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        cRVA = new CategoriaAdapter(catList, this);
        cRV.setLayoutManager(cRVLM);
        cRV.setAdapter(cRVA);
    }

    private void setProductLayout(ArrayList<ProductItem> pList){
        RecyclerView pRV;
        RecyclerView.Adapter pRVA;
        RecyclerView.LayoutManager pRVLM;
        pRV = findViewById(R.id.productRecycleView);
        pRV.setHasFixedSize(true);
        pRV.setNestedScrollingEnabled(false);
        pRVLM = new GridLayoutManager(this, 3);
        pRVA = new ProductAdapter(pList);
        pRV.setLayoutManager(pRVLM);
        pRV.setAdapter(pRVA);
    }

    public void filter(int idCat){
        ArrayList<ProductItem> filterList = new ArrayList<ProductItem>();
        for(int i=0; i<listProd.size();i++){
            if (listProd.get(i).getIdCategoria()==idCat){
                filterList.add(listProd.get(i));
            }
        }
        setProductLayout(filterList);
        //colorare pulsante attivo

    }

    public void removeFilter(){
        setProductLayout(listProd);
    }
}


