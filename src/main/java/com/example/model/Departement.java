package main.java.com.example.model;


import java.util.List;
import java.util.Objects;

public class Departement {
    private Integer id;
    private String nom;
    private List<Enseignant> enseignants;
    private List<Master> masters;

    public Departement() {}
    public Departement(Integer id, String nom) {
        this.id = id;
        this.nom = nom;
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }
    public List<Enseignant> getEnseignants() { return enseignants; }
    public void setEnseignants(List<Enseignant> enseignants) { this.enseignants = enseignants; }
    public List<Master> getMasters() { return masters; }
    public void setMasters(List<Master> masters) { this.masters = masters; }

    @Override
    public String toString() { return "Departement{" + id + ", " + nom + '}'; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Departement)) return false;
        Departement d = (Departement) o;
        return Objects.equals(id, d.id);
    }
    @Override
    public int hashCode() { return Objects.hash(id); }
}
