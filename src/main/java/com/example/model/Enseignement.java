package main.java.com.example.model;


import java.util.List;
import java.util.Objects;

public class Enseignement {
    private int id;
    private String intitule;
    private String description;
    private String obligatoire;
    private Enseignant enseignant;
    private Master master;
    private List<Inscription> inscriptions;

    public Enseignement() {}

    public Enseignement(int id, String intitule, String description, String obligatoire, Enseignant enseignant, Master master){
        this.id = id;
        this.intitule = intitule;
        this.description = description;
        this.obligatoire = obligatoire;
        this.enseignant = enseignant;
        this.master = master;
    }

    public Enseignement(int id){
        this.id = id;
    }

    public Integer getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getIntitule() { return intitule; }
    public void setIntitule(String intitule) { this.intitule = intitule; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String isObligatoire() { return obligatoire; }
    public void setObligatoire(String obligatoire) { this.obligatoire = obligatoire; }
    public Enseignant getEnseignant() { return enseignant; }
    public void setEnseignant(Enseignant enseignant) { this.enseignant = enseignant; }
    public Master getMaster() { return master; }
    public void setMaster(Master master) { this.master = master; }
    public List<Inscription> getInscriptions() { return inscriptions; }
    public void setInscriptions(List<Inscription> inscriptions) { this.inscriptions = inscriptions; }

    @Override
    public String toString() {
        return "Enseignement{" + id + ", " + intitule + "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Enseignement)) return false;
        Enseignement e = (Enseignement) o;
        return Objects.equals(id, e.id);
    }

    @Override
    public int hashCode() { return Objects.hash(id); }
}