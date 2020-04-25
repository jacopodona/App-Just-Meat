package com.example.justmeat.categoria;

public class CategoriaItem {
    private int iconaImg;
    private String nomeCat;

    public CategoriaItem(int img, String nCat){
        this.iconaImg=img;
        this.nomeCat = nCat;
    }


    public int getIconaImg() {
        return iconaImg;
    }

    public String getNomeCat() {
        return nomeCat;
    }
}
