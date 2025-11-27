package com.example.model;

public class Master {
    private int masterId;
    private String nomMaster;
    private int responsableId;
    private int departementId;

    public Master() {}

    public Master(int masterId, String nomMaster, int responsableId, int departementId) {
        this.masterId = masterId;
        this.nomMaster = nomMaster;
        this.responsableId = responsableId;
        this.departementId = departementId;
    }

    // Getters and Setters
    public int getMasterId() {
        return masterId;
    }

    public void setMasterId(int masterId) {
        this.masterId = masterId;
    }

    public String getNomMaster() {
        return nomMaster;
    }

    public void setNomMaster(String nomMaster) {
        this.nomMaster = nomMaster;
    }

    public int getResponsableId() {
        return responsableId;
    }

    public void setResponsableId(int responsableId) {
        this.responsableId = responsableId;
    }

    public int getDepartementId() {
        return departementId;
    }

    public void setDepartementId(int departementId) {
        this.departementId = departementId;
    }

    @Override
    public String toString() {
        return "Master{" +
                "masterId=" + masterId +
                ", nomMaster='" + nomMaster + '\'' +
                ", responsableId=" + responsableId +
                ", departementId=" + departementId +
                '}';
    }
}
