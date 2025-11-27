package com.example.model;

import java.sql.Date;
import java.sql.Time;

public class Reservation {
    private int reservationId;
    private String batiment;
    private String numeroSalle;
    private int enseignementId;
    private int masterId;
    private int enseignantId;
    private Date dateResa;
    private Time heureDebut;
    private Time heureFin;
    private int nombreHeures;

    public Reservation() {}

    // Getters and Setters
    public int getReservationId() {
        return reservationId;
    }

    public void setReservationId(int reservationId) {
        this.reservationId = reservationId;
    }

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

    public int getEnseignementId() {
        return enseignementId;
    }

    public void setEnseignementId(int enseignementId) {
        this.enseignementId = enseignementId;
    }

    public int getMasterId() {
        return masterId;
    }

    public void setMasterId(int masterId) {
        this.masterId = masterId;
    }

    public int getEnseignantId() {
        return enseignantId;
    }

    public void setEnseignantId(int enseignantId) {
        this.enseignantId = enseignantId;
    }

    public Date getDateResa() {
        return dateResa;
    }

    public void setDateResa(Date dateResa) {
        this.dateResa = dateResa;
    }

    public Time getHeureDebut() {
        return heureDebut;
    }

    public void setHeureDebut(Time heureDebut) {
        this.heureDebut = heureDebut;
    }

    public Time getHeureFin() {
        return heureFin;
    }

    public void setHeureFin(Time heureFin) {
        this.heureFin = heureFin;
    }

    public int getNombreHeures() {
        return nombreHeures;
    }

    public void setNombreHeures(int nombreHeures) {
        this.nombreHeures = nombreHeures;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "reservationId=" + reservationId +
                ", batiment='" + batiment + '\'' +
                ", numeroSalle='" + numeroSalle + '\'' +
                ", enseignementId=" + enseignementId +
                ", masterId=" + masterId +
                ", enseignantId=" + enseignantId +
                ", dateResa=" + dateResa +
                ", heureDebut=" + heureDebut +
                ", heureFin=" + heureFin +
                ", nombreHeures=" + nombreHeures +
                '}';
    }
}
