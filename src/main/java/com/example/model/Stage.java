package main.java.com.example.model;

public class Stage {
    private String descriptif;
    private String entreprise;

    public Stage() {}

    public String getDescriptif() { return descriptif; }
    public void setDescriptif(String descriptif) { this.descriptif = descriptif; }
    public String getEntreprise() { return entreprise; }
    public void setEntreprise(String entreprise) { this.entreprise = entreprise; }

    @Override
    public String toString() { return "Stage{" + entreprise + "}"; }
}