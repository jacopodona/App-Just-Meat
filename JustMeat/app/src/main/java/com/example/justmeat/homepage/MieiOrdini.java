package com.example.justmeat.homepage;

import java.util.Date;

public class MieiOrdini {
     private String nomeSupermercato, indirizzo, stato;
     private Date dataOrdine;

    public MieiOrdini(String nomeSupermercato, String indirizzo, String stato, Date dataOrdine) {
        this.nomeSupermercato = nomeSupermercato;
        this.indirizzo = indirizzo;
        this.stato = stato;
        this.dataOrdine = dataOrdine;
    }

    public String getNomeSupermercato() {
        return nomeSupermercato;
    }

    public void setNomeSupermercato(String nomeSupermercato) {
        this.nomeSupermercato = nomeSupermercato;
    }

    public String getIndirizzo() {
        return indirizzo;
    }

    public void setIndirizzo(String indirizzo) {
        this.indirizzo = indirizzo;
    }

    public String getStato() {
        return stato;
    }

    public void setStato(String stato) {
        this.stato = stato;
    }

    public Date getDataOrdine() {
        return dataOrdine;
    }

    public void setDataOrdine(Date dataOrdine) {
        this.dataOrdine = dataOrdine;
    }
}
