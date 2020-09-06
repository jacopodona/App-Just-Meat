package com.example.justmeat.homepage;

public class IndirizzoPreferito {

    private String nome, indirizzo;
    private double latitude,longitude;

    public IndirizzoPreferito(String nome, String indirizzo) {
        this.nome = nome;
        this.indirizzo = indirizzo;
    }

    public IndirizzoPreferito(){

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

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
