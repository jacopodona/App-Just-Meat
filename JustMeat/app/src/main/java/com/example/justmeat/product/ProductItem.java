package com.example.justmeat.product;

public class ProductItem {
    //esempio d'uso: new ProductItem(R.drawable.acqua.svg, 0.30, 1 L, acqua)
    private int imgProd;
    private double prezzo;
    private String size;
    private String nome;

    public ProductItem(int xImg, double xPrice, String xSize, String xName){
        this.imgProd= xImg;
        this.nome= xName;
        this.prezzo= xPrice;
        this.size = xSize;
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

    public String getSize() {
        return size;
    }
}
