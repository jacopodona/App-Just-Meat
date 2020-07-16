package com.example.justmeat.marketview;

public class ProductItem {
    private int img;
    private double prezzo;
    private String nome;
    private int categoria;
    boolean pref = false;
    public int qt;

    public ProductItem(int img, double prezzo,  String nome, int categoria){
        this.img= img;
        this.nome= nome;
        this.prezzo= prezzo;
        this.categoria= categoria;
    }
    public ProductItem( double prezzo,  String nome, int categoria){
        this.nome= nome;
        this.prezzo= prezzo;
        this.categoria= categoria;
    }

    public int getQt() {
        return qt;
    }

    public double getPrezzo() {
        return prezzo;
    }

    public int getImgProd() {
        return img;
    }

    public String getNome() {
        return nome;
    }

    public int getIdCategoria() {
        return categoria;
    }

    public boolean getPref() {
        return pref;
    }

}
