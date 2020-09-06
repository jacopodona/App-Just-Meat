package com.example.justmeat.homepage;

public class IndirizzoPreferito {

    private String nome, indirizzo;
    private double latitude,longitude;

    public IndirizzoPreferito(String nome, String indirizzo) {
        this.nome = nome;
        this.indirizzo = indirizzo;
    }

    public IndirizzoPreferito(String nome, String indirizzo, double latitude, double longitude) {
        this.nome = nome;
        this.indirizzo = indirizzo;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getIndirizzo() {
        return indirizzo;
    }

    public void setIndirizzo(String indirizzo) {
        this.indirizzo = indirizzo;
    }

    @Override
    public String toString() {
        return nome;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }
}
