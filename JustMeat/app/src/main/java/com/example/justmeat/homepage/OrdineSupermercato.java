package com.example.justmeat.homepage;

public class OrdineSupermercato {
    private String id,numProdotti;

    public OrdineSupermercato(String id, String numProdotti) {
        this.id = id;
        this.numProdotti = numProdotti;
    }

    public String getId() {
        return id;
    }

    public String getNumProdotti() {
        return numProdotti;
    }
}
