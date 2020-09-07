package com.example.justmeat.utilities;

import android.app.Application;

import com.example.justmeat.homepage.User;
import com.example.justmeat.marketview.ProductItem;

import java.util.ArrayList;

public class MyApplication extends Application {
    private ArrayList<ProductItem> carrelloListProduct = new ArrayList<>();
    String httpToken;
    private User utente;


    public void setHttpToken(String httpToken){
        this.httpToken = httpToken;
    }

    public String getHttpToken(){ 
        return this.httpToken;
    }

    public void setCarrelloListProduct(ArrayList<ProductItem> carrelloListProduct){
        this.carrelloListProduct = carrelloListProduct;
    }
    public ArrayList<ProductItem> getCarrelloListProduct(){
        return carrelloListProduct;
    }

    public ArrayList<ProductItem> newCarrelloListProduct(){
        carrelloListProduct = new ArrayList<>();
        return carrelloListProduct;
    }

    public User getUtente() {
        return utente;
    }

    public void setUtente(User utente) {
        this.utente = utente;
    }
}
