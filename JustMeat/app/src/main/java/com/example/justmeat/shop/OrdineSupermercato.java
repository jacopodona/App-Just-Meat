package com.example.justmeat.shop;

public class OrdineSupermercato {
    private String id,numProdotti, stato;

    public OrdineSupermercato(String id, String numProdotti, String stato) {
        this.id = id;
        this.numProdotti = numProdotti;
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
