package com.example.model;

public class Salle {
    private String batiment;
    private String numeroSalle;
    private int capacite;

    public Salle() {}

    // Getters and Setters
    public String getBatiment() {
        return batiment;
    }

    public void setBatiment(String batiment) {
        this.batiment = batiment;
    }

    public String getNumeroSalle() {
        return numeroSalle;
    }

    public void setNumeroSalle(String numeroSalle) {
        this.numeroSalle = numeroSalle;
    }

    public int getCapacite() {
        return capacite;
    }

    public void setCapacite(int capacite) {
        this.capacite = capacite;
    }

    @Override
    public String toString() {
        return "Salle{" +
                "batiment='" + batiment + '\'' +
                ", numeroSalle='" + numeroSalle + '\'' +
                ", capacite=" + capacite +
                '}';
    }
}
