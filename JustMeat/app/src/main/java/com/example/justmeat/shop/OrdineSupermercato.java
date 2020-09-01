package com.example.justmeat.shop;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class OrdineSupermercato implements Serializable,Comparable<OrdineSupermercato>{
    private Integer id;
    private String numProdotti, stato,supermarket;
    private Date pickupTime;
    private ArrayList<String> prodotti;

    public OrdineSupermercato(int id, String stato) {
        this.id = id;
        this.stato=stato;
        numProdotti="0";
        pickupTime=new Date();
        prodotti=new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public String getNumProdotti() {
        return numProdotti;
    }

    public String getStato() {
        return stato;
    }

    public Date getPickupTime() {
        return pickupTime;
    }

    public ArrayList<String> getProdotti() {
        return prodotti;
    }

    public void setPickupTime(Date pickupTime) {
        this.pickupTime = pickupTime;
    }

    public void setStato(String stato) {
        this.stato = stato;
    }

    public String getSupermarket() {
        return supermarket;
    }

    public void setSupermarket(String supermarket) {
        this.supermarket = supermarket;
    }

    public void setProdotti(ArrayList<String> prodotti) {
        this.prodotti = prodotti;
        numProdotti=String.valueOf(prodotti.size());
    }

    @Override
    public int compareTo(OrdineSupermercato o) {
        int lastCmp = id.compareTo(o.getId());
        return lastCmp;
    }
}
