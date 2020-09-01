package com.example.justmeat.homepage.Java;

import java.util.Date;

public class MieiOrdini {
     private String nomeSupermercato, indirizzo, stato;
     private Date dataOrdine;
     private int numOrdine;

    public MieiOrdini(String nomeSupermercato, String indirizzo, String stato, Date dataOrdine, int numOrdine) {
        this.nomeSupermercato = nomeSupermercato;
        this.indirizzo = indirizzo;
        this.stato = stato;
        this.dataOrdine = dataOrdine;
        this.numOrdine=numOrdine;
    }

    public MieiOrdini() {
        this.nomeSupermercato = "";
        this.indirizzo="";
        this.stato="";
        this.dataOrdine=new Date();
    }

    public int getNumOrdine() {
        return numOrdine;
    }

    public void setNumOrdine(int numOrdine) {
        this.numOrdine = numOrdine;
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
