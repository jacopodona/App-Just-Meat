package com.example.justmeat.utilities;

import android.app.Application;

import com.example.justmeat.marketview.ProductItem;

import java.util.ArrayList;

public class MyApplication extends Application {
    private ArrayList<ProductItem> carrelloListProduct = new ArrayList<>();

    public void setCarrelloListProduct(ArrayList<ProductItem> carrelloListProduct){
        this.carrelloListProduct = carrelloListProduct;
    }
    public ArrayList<ProductItem> getCarrelloListProduct(){
        return carrelloListProduct;
    }
}
