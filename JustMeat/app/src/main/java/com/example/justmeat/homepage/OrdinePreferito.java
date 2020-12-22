package com.example.justmeat.homepage;

import com.example.justmeat.marketview.ProductItem;

import java.util.ArrayList;

public class OrdinePreferito {

    private int idOrdinePreferito;
    private int idSupermercato;
    private String nomeSupermercato;
    private String nomeOrdinePreferito;
    ArrayList<ProductItem> productItems;

    public OrdinePreferito(int order_id, int supermarket, String nomeSpkmt, String favourite, ArrayList<ProductItem> productItems) {
        this.idOrdinePreferito = order_id;
        this.idSupermercato = supermarket;
        this.nomeSupermercato = nomeSpkmt;
        this.nomeOrdinePreferito = favourite;
        this.productItems = productItems;
    }

    public int getIdOrdinePreferito() {
        return idOrdinePreferito;
    }

    public int getIdSupermercato() {
        return idSupermercato;
    }

    public String getNomeSupermercato() {
        return nomeSupermercato;
    }

    public String getNomeOrdinePreferito() {
        return nomeOrdinePreferito;
    }

    public ArrayList<ProductItem> getProductItems() {
        return productItems;
    }
}
