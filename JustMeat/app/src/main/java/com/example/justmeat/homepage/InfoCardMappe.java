package com.example.justmeat.homepage;

public class InfoCardMappe {

    private String image;
    private String nomeSupermercato;

    public InfoCardMappe(String image, String nomeSupermercato) {
        this.image = image;
        this.nomeSupermercato = nomeSupermercato;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getNomeSupermercato() {
        return nomeSupermercato;
    }

    public void setNomeSupermercato(String nomeSupermercato) {
        this.nomeSupermercato = nomeSupermercato;
    }
}
