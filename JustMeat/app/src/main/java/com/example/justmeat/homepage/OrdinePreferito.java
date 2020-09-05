package com.example.justmeat.homepage;

import com.example.justmeat.homepage.adapter.OrdiniPreferitiAdapter;

import java.util.Date;
import java.util.LinkedList;

public class OrdinePreferito {

    private int idOrdinePreferito;
    private int idSupermercato;
    private String nomeSupermercato;
    private String nomeOrdinePreferito;
    private LinkedList<ProdottoOrdinePreferito> listaProdotti;

    public OrdinePreferito(){
        this.listaProdotti= new LinkedList<>();

    }

    public int getIdOrdinePreferito() {
        return idOrdinePreferito;
    }

    public void setIdOrdinePreferito(int idOrdinePreferito) {
        this.idOrdinePreferito = idOrdinePreferito;
    }

    public int getIdSupermercato() {
        return idSupermercato;
    }

    public void setIdSupermercato(int idSupermercato) {
        this.idSupermercato = idSupermercato;
    }

    public String getNomeSupermercato() {
        return nomeSupermercato;
    }

    public void setNomeSupermercato(String nomeSupermercato) {
        this.nomeSupermercato = nomeSupermercato;
    }

    public String getNomeOrdinePreferito() {
        return nomeOrdinePreferito;
    }

    public void setNomeOrdinePreferito(String nomeOrdinePreferito) {
        this.nomeOrdinePreferito = nomeOrdinePreferito;
    }

    public LinkedList<ProdottoOrdinePreferito> getListaProdotti() {
        return listaProdotti;
    }

    public void setListaProdotti(LinkedList<ProdottoOrdinePreferito> listaProdotti) {
        this.listaProdotti = listaProdotti;
    }



}
