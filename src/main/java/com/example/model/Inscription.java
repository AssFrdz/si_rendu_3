package main.java.com.example.model;
import java.util.Objects;

public class Inscription {
    private Integer id;
    private Etudiant etudiant;
    private Enseignement enseignement;
    private Master master;

    public Inscription() {}

     public Inscription(int id, Etudiant e, Enseignement en) {
        this.id=id;
        this.etudiant=e;
        this.enseignement=en;
     }

      public Inscription(int id, Etudiant e, Enseignement en, Master m) {
        this.id=id;
        this.etudiant=e;
        this.enseignement=en;
        this.master = m;
     }

     public Inscription(int id, Etudiant e) {
        this.id=id;
        this.etudiant=e;
     }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public Etudiant getEtudiant() { return etudiant; }
    public void setEtudiant(Etudiant etudiant) { this.etudiant = etudiant; }
    public Enseignement getEnseignement() { return enseignement; }
    public void setEnseignement(Enseignement enseignement) { this.enseignement = enseignement; }
    public Master getMaster(){ return this.master; }
    public void setMaster(Master value){ this.master = value ; }

    @Override
    public String toString() {
        return "Inscription{" + id + ", etu=" + etudiant.getNom() + ", ens=" + enseignement.getIntitule() + "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Inscription)) return false;
        Inscription i = (Inscription) o;
        return Objects.equals(id, i.id);
    }

    @Override
    public int hashCode() { return Objects.hash(id); }
}