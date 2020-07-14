package com.example.justmeat.marketview;

public class CategoriaItem {
    private int icona;
    private String nome;
    private int id;

    public CategoriaItem(int icona, String nome, int id) {
        this.icona = icona;
        this.nome = nome;
        this.id = id;
    }


    public int getIcona() {
        return icona;
    }

    public String getNome() {
        return nome;
    }

    public int getId() { return id; };
}
