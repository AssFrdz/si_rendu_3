package main.java.com.example.model;


import java.util.List;
import java.util.Objects;

public class Enseignant {
    private Integer id;
    private String nom;
    private String prenom;
    private String grade;
    private String telephone;
    private String fax;
    private String email;
    private Departement departement;
    private List<Master> masters;

    public Enseignant() {}

    public Enseignant(Integer id, String nom, String prenom) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
    }

    public Enseignant(Integer id, String nom, String prenom, String grade, String telephone,String fax,String email,Departement departement) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.grade = grade;
        this.telephone = telephone;
        this.fax = fax;
        this.email = email;
        this.departement = departement;
    }

    public Enseignant(Integer id, String nom, String prenom, String grade, String telephone,String fax,String email) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.grade = grade;
        this.telephone = telephone;
        this.fax = fax;
        this.email = email;
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }
    public String getPrenom() { return prenom; }
    public void setPrenom(String prenom) { this.prenom = prenom; }
    public String getGrade() { return grade; }
    public void setGrade(String grade) { this.grade = grade; }
    public String getTelephone() { return telephone; }
    public void setTelephone(String telephone) { this.telephone = telephone; }
    public String getFax() { return fax; }
    public void setFax(String fax) { this.fax = fax; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public Departement getDepartement() { return departement; }
    public void setDepartement(Departement departement) { this.departement = departement; }
    public List<Master> getMasters() { return masters; }
    public void setMasters(List<Master> masters) { this.masters = masters; }

    @Override
    public String toString() {
        return "Enseignant{" + id + ", " + nom + " " + prenom + "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Enseignant)) return false;
        Enseignant e = (Enseignant) o;
        return Objects.equals(id, e.id);
    }

    @Override
    public int hashCode() { return Objects.hash(id); }
}