package main.java.com.example.model;

import java.util.List;
import java.util.Objects;

public class Master {
    private Integer id;
    private String nom;
    private Enseignant responsable;
    private Departement departement;
    private List<Etudiant> etudiants;
    private List<Enseignement> enseignements;
    private List<Candidature> candidatures;
    private List<Seminaire> seminaires;
    private List<Salle> salles;





    public Master() {}
    public Master(int id) {
        this.id=id;
    }
    public Master(Integer id, String nom) { this.id = id; this.nom = nom; }

    public Master(Integer id, String nom, Enseignant responsable, Departement departement){
        this.id = id;
        this.nom = nom;
        
    }




    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }
    public Enseignant getResponsable() { return responsable; }
    public void setResponsable(Enseignant responsable) { this.responsable = responsable; }
    public Departement getDepartement() { return departement; }
    public void setDepartement(Departement departement) { this.departement = departement; }
    public List<Etudiant> getEtudiants() { return etudiants; }
    public void setEtudiants(List<Etudiant> etudiants) { this.etudiants = etudiants; }
    public List<Enseignement> getEnseignements() { return enseignements; }
    public void setEnseignements(List<Enseignement> enseignements) { this.enseignements = enseignements; }

    @Override
    public String toString() {
        return "Master{" + id + ", " + nom + "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Master)) return false;
        Master m = (Master) o;
        return Objects.equals(id, m.id);
    }

    @Override
    public int hashCode() { return Objects.hash(id); }
}

