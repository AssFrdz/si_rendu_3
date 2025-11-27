package com.example.model;

public class Etudiant {
    private int etudiantId;
    private String nom;
    private String prenom;
    private String email;
    private String dateNaissance;

    // Default constructor
    public Etudiant() {}

    // Constructor with all fields
    public Etudiant(int etudiantId, String nom, String prenom, String email, String dateNaissance) {
        this.etudiantId = etudiantId;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.dateNaissance = dateNaissance;
    }

    // Getters and Setters
    public int getEtudiantId() {
        return etudiantId;
    }

    public void setEtudiantId(int etudiantId) {
        this.etudiantId = etudiantId;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDateNaissance() {
        return dateNaissance;
    }

    public void setDateNaissance(String dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    @Override
    public String toString() {
        return "Etudiant{" +
                "etudiantId=" + etudiantId +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", email='" + email + '\'' +
                ", dateNaissance='" + dateNaissance + '\'' +
                '}';
    }
}
