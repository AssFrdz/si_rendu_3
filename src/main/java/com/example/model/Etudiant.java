package main.java.com.example.model;


import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public class Etudiant {
    private Integer id;
    private String nom;
    private String prenom;
    private LocalDate dateNaiss;
    private String adresse;
    private String ville;
    private String codePostal;
    private String telephone;
    private String email;
    private Master master;
    private List<Note> notes;
    private List<Preference> preferences;
    private List<Inscription> inscriptions;
    private Stage stage;

    public Etudiant() {}
    public Etudiant(Integer id, String nom, String prenom) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }
    public String getPrenom() { return prenom; }
    public void setPrenom(String prenom) { this.prenom = prenom; }
    public LocalDate getDateNaiss() { return dateNaiss; }
    public void setDateNaiss(LocalDate dateNaiss) { this.dateNaiss = dateNaiss; }
    public String getAdresse() { return adresse; }
    public void setAdresse(String adresse) { this.adresse = adresse; }
    public String getVille() { return ville; }
    public void setVille(String ville) { this.ville = ville; }
    public String getCodePostal() { return codePostal; }
    public void setCodePostal(String codePostal) { this.codePostal = codePostal; }
    public String getTelephone() { return telephone; }
    public void setTelephone(String telephone) { this.telephone = telephone; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public Master getMaster() { return master; }
    public void setMaster(Master master) { this.master = master; }
    public List<Note> getNotes() { return notes; }
    public void setNotes(List<Note> notes) { this.notes = notes; }
    public List<Inscription> getInscriptions() { return inscriptions; }
    public void setInscriptions(List<Inscription> inscriptions) { this.inscriptions = inscriptions; }
    public Stage getStage() { return stage; }
    public void setStage(Stage stage) { this.stage = stage; }

    @Override
    public String toString() {
        return "Etudiant{" + id + ", " + nom + " " + prenom + "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Etudiant)) return false;
        Etudiant e = (Etudiant) o;
        return Objects.equals(id, e.id);
    }
    @Override
    public int hashCode() { return Objects.hash(id); }
}
