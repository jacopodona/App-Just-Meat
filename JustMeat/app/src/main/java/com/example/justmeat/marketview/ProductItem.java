package com.example.justmeat.marketview;

import java.io.Serializable;
import java.util.Comparator;

public class ProductItem implements Serializable {
    private int categoria, id, weight, fk_weight;
    private double prezzo, discount;
    private String nome, manufacturer, description, um, image;
    boolean pref;
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

    public ProductItem(int id, String nome, double prezzo, double discount, String image, String description, int categoria, String manufacturer, String um, int weight, int fk_weight, boolean favourite){
        this.id = id;
        this.nome = nome;
        this.prezzo = prezzo;
        this.discount = discount;
        this.image = image;
        this.description = description;
        this.categoria = categoria;
        this.manufacturer = manufacturer;
        this.um = um;
        this.weight = weight;
        this.pref = favourite;
        this.fk_weight = fk_weight;
    }

    public ProductItem(int id, String name, double prezzo, int qt, int weight){
        this.id = id;
        this.nome = name;
        this.prezzo = prezzo;
        this.qt = qt;
        this.weight = weight;
    }

    public ProductItem(String name, double prezzo, int qt, double discount){
        this.prezzo = prezzo;
        this.qt = qt;
        this.discount = discount;
        this.nome = name;
    }

    public int getWeight() {
        return weight;
    }

    public int getCategoria() {
        return categoria;
    }

    public int getId() {
        return id;
    }

    public String getUm() {
        return um;
    }

    public String getDescription() {
        return description;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public int getQt() {
        return qt;
    }

    public double getPrezzo() {
        return prezzo;
    }

    public String getNome() {
        return nome;
    }

    public boolean isPref() {
        return pref;
    }

    public double getDiscount(){
        return discount;
    }

    public int getFk_weight() {
        return fk_weight;
    }

    public String getImage() {
        return image;
    }
}
