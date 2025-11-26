package main.java.com.example.model;

import java.util.List;

public class Salle {
    private String numSalle;
    private String batiment;
    private int capacite;
    private List<Reservation> reservations;

    public Salle() {}
    public Salle(String numSalle,String batiment, int capacite) {

        this.numSalle = numSalle;
        this.batiment=batiment;
        this.capacite = capacite;

    }



    public String getNumSalle() { return numSalle; }
    public void setNumSalle(String numSalle) { this.numSalle = numSalle; }
    public String getBatiment() { return batiment; }
    public void setBatiment(String batiment) { this.batiment = batiment; }
    public int getCapacite() { return capacite; }
    public void setCapacite(int capacite) { this.capacite = capacite; }
    public List<Reservation> getReservations() { return reservations; }
    public void setReservations(List<Reservation> reservations) { this.reservations = reservations; }

    @Override
    public String toString() { return "Salle{" + numSalle + ", " + batiment + "}"; }
}