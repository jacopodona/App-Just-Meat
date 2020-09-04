package com.example.justmeat.homepage;

import java.io.Serializable;

public class Supermercato implements Serializable {

    int id;
    String nome,indirizzo;
    Double latitude, longitude;

    public Supermercato(int id, String nome, String indirizzo, Double latitude, Double longitude) {
        this.id = id;
        this.nome = nome;
        this.indirizzo = indirizzo;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public int getId() {
        return id;
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

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setIndirizzo(String indirizzo) {
        this.indirizzo = indirizzo;
    }
}
