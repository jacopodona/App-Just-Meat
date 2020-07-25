package com.example.justmeat.marketview;

import java.io.Serializable;
import java.util.Comparator;

public class ProductItem implements Serializable {
    private int img, categoria;
    private double prezzo, discount;
    private String nome;
    boolean pref = false;
    public int qt = 0;

    public static Comparator<ProductItem> increasePComparator = new Comparator<ProductItem>() {
        @Override
        public int compare(ProductItem o1, ProductItem o2) {
            if(o1.getPrezzo() >= o2.getPrezzo())
                return 1;
            else
                return -1;
        }
    };
    public static Comparator<ProductItem> decreasePComparator = new Comparator<ProductItem>() {
        @Override
        public int compare(ProductItem o1, ProductItem o2) {
            if(o1.getPrezzo() <= o2.getPrezzo())
                return 1;
            else
                return -1;
        }
    };
    public static Comparator<ProductItem> increaseNameComparator = new Comparator<ProductItem>() {
        @Override
        public int compare(ProductItem o1, ProductItem o2) {
            return o1.getNome().compareTo(o2.getNome());
        }
    };
    public static Comparator<ProductItem> discountComparator = new Comparator<ProductItem>() {
        @Override
        public int compare(ProductItem o1, ProductItem o2) {
            if(o1.getDiscount() <= o2.getDiscount())
                return 1;
            else
                return -1;
        }
    };

    public ProductItem( double prezzo,  String nome, int categoria, double discount){
        this.nome = nome;
        this.prezzo = prezzo;
        this.categoria = categoria;
        this.discount = discount;
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

    public double getDiscount(){
        return discount;
    }

}
