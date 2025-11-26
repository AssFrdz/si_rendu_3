package main.java.com.example.model;

public class Preference {
    private Integer id;
    private Enseignement enseignement;
    private Master master;
    private boolean preferenceBool;

    public Preference() {}

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public Enseignement getEnseignement() { return enseignement; }
    public void setEnseignement(Enseignement enseignement) { this.enseignement = enseignement; }
    public Master getMaster() { return master; }
    public void setMaster(Master master) { this.master = master; }
    public boolean isPreferenceBool() { return preferenceBool; }
    public void setPreferenceBool(boolean preferenceBool) { this.preferenceBool = preferenceBool; }

    @Override
    public String toString() { return "Preference{" + id + ", " + preferenceBool + "}"; }
}