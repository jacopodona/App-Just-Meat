package com.example.justmeat.homepage;

public class InfoCardMappe {

    private int id;
    private String image;
    private String nomeSupermercato;
    private String indirizzo;

    public InfoCardMappe(String image, String nomeSupermercato) {
        this.image = image;
        this.nomeSupermercato = nomeSupermercato;
    }

    public InfoCardMappe(int id,String image, String nomeSupermercato, String indirizzo) {
        this.id=id;
        this.image = image;
        this.nomeSupermercato = nomeSupermercato;
        this.indirizzo = indirizzo;
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

    public String getIndirizzo() {
        return indirizzo;
    }

    public int getId() {
        return id;
    }

    public void setIndirizzo(String indirizzo) {
        this.indirizzo = indirizzo;
    }
}
