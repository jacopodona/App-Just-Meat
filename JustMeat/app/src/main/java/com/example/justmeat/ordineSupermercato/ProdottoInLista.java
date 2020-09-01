package com.example.justmeat.ordineSupermercato;

public class ProdottoInLista {
    private String nome;
    private Double prezzo;

    public ProdottoInLista(String nome, Double prezzo) {
        this.nome = nome;
        this.prezzo = prezzo;
    }

    public String getNome() {
        return nome;
    }

    public Double getPrezzo() {
        return prezzo;
    }
}
