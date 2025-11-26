package main.java.com.example.model;

import java.time.LocalDateTime;

public class Reservation {
    private Integer id;
    private Salle salle;
    private Enseignement enseignement;
    private Master master;
    private LocalDateTime debut;
    private LocalDateTime fin;
    private int nbHeures;

    public Reservation() {}

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public Salle getSalle() { return salle; }
    public void setSalle(Salle salle) { this.salle = salle; }
    public Enseignement getEnseignement() { return enseignement; }
    public void setEnseignement(Enseignement enseignement) { this.enseignement = enseignement; }
    public Master getMaster() { return master; }
    public void setMaster(Master master) { this.master = master; }
    public LocalDateTime getDebut() { return debut; }
    public void setDebut(LocalDateTime debut) { this.debut = debut; }
    public LocalDateTime getFin() { return fin; }
    public void setFin(LocalDateTime fin) { this.fin = fin; }
    public int getNbHeures() { return nbHeures; }
    public void setNbHeures(int nbHeures) { this.nbHeures = nbHeures; }

    @Override
    public String toString() { return "Reservation{" + id + ", " + salle + "}"; }
}