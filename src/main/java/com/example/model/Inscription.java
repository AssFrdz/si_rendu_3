package com.example.model;

public class Inscription {
    private int inscriptionId;
    private int etudiantId;
    private int enseignementId;
    private int masterId;

    public Inscription() {}

    // Getters and Setters
    public int getInscriptionId() {
        return inscriptionId;
    }

    public void setInscriptionId(int inscriptionId) {
        this.inscriptionId = inscriptionId;
    }

    public int getEtudiantId() {
        return etudiantId;
    }

    public void setEtudiantId(int etudiantId) {
        this.etudiantId = etudiantId;
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

    @Override
    public String toString() {
        return "Inscription{" +
                "inscriptionId=" + inscriptionId +
                ", etudiantId=" + etudiantId +
                ", enseignementId=" + enseignementId +
                ", masterId=" + masterId +
                '}';
    }
}
