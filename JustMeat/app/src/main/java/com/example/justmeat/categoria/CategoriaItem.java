package com.example.justmeat.categoria;

public class CategoriaItem {
    private int iconaImg;
    private String nomeCat;
    private int idCategoria;

    public CategoriaItem(int img, String nCat, int idCategoria){
        this.iconaImg=img;
        this.nomeCat = nCat;
        this.idCategoria = idCategoria;
    }


    public int getIconaImg() {
        return iconaImg;
    }

    public String getNomeCat() {
        return nomeCat;
    }

    public int getIdCategoria(){return idCategoria;};

}
