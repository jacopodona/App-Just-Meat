package com.example.justmeat.homepage;

import java.util.Date;

public class OrdinePreferito extends MieiOrdini {

    public String nomeOrdine;


    public OrdinePreferito(String nomeSupermercato, String indirizzo, String stato, Date dataOrdine,String nomeOrdine, int numeroOrdine) {
        super(nomeSupermercato, indirizzo, stato, dataOrdine,numeroOrdine);
        this.nomeOrdine=nomeOrdine;
    }

    public String getNomeOrdine() {
        return nomeOrdine;
    }

    public void setNomeOrdine(String nomeOrdine) {
        this.nomeOrdine = nomeOrdine;
    }
}
