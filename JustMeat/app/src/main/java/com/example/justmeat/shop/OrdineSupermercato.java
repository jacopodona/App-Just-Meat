package com.example.justmeat.shop;

import java.io.Serializable;

public class OrdineSupermercato implements Serializable {
    private String id,numProdotti, stato;

    public OrdineSupermercato(String id, String numProdotti, String stato) {
        this.id = id;
        this.numProdotti = numProdotti;
        this.stato=stato;
    }

    public String getId() {
        return id;
    }

    public String getNumProdotti() {
        return numProdotti;
    }

    public String getStato() {
        return stato;
    }
}
