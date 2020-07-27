package com.example.justmeat.utilities;

import android.app.Application;

import com.example.justmeat.marketview.ProductItem;

import java.util.ArrayList;

public class MyApplication extends Application {
    private ArrayList<ProductItem> carrelloListProduct = new ArrayList<>();
    String httpToken;

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
}
