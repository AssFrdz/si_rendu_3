package com.example.model;

public class Enseignement {
    private int enseignementId;
    private int masterId;
    private String intitule;
    private String description;
    private String obligatoire; // ENUM('OUI','NON')
    private int enseignantId;

    public Enseignement() {}

    // Getters and Setters
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

    public String getIntitule() {
        return intitule;
    }

    public void setIntitule(String intitule) {
        this.intitule = intitule;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getObligatoire() {
        return obligatoire;
    }

    public void setObligatoire(String obligatoire) {
        this.obligatoire = obligatoire;
    }

    public int getEnseignantId() {
        return enseignantId;
    }

    public void setEnseignantId(int enseignantId) {
        this.enseignantId = enseignantId;
    }

    @Override
    public String toString() {
        return "Enseignement{" +
                "enseignementId=" + enseignementId +
                ", masterId=" + masterId +
                ", intitule='" + intitule + '\'' +
                ", description='" + description + '\'' +
                ", obligatoire='" + obligatoire + '\'' +
                ", enseignantId=" + enseignantId +
                '}';
    }
}
