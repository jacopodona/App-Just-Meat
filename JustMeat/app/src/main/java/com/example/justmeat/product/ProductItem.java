package com.example.justmeat.product;

public class ProductItem {
    //esempio d'uso: new ProductItem(R.drawable.acqua.svg, 0.30, 1 L, acqua)
    private int imgProd;
    private double prezzo;
    private String nome;
    private int idCategoria;

    public ProductItem(int xImg, double xPrice,  String xName, int cat){
        this.imgProd= xImg;
        this.nome= xName;
        this.prezzo= xPrice;
        this.idCategoria= cat;
    }

    public double getPrezzo() {
        return prezzo;
    }

    public int getImgProd() {
        return imgProd;
    }

    public String getNome() {
        return nome;
    }

    public int getIdCategoria() {
        return idCategoria;
    }
}
