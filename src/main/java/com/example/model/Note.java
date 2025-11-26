package main.java.com.example.model;

import java.util.Objects;

public class Note {
    private Integer id;
    private double note;
    private Enseignement enseignement;
    private Etudiant etudiant;
    private Master master;

    public Note() {}

    public Note(Integer id,Etudiant etudiant,Enseignement enseignement,int note){
        this.id = id;
        this.etudiant = etudiant;
        this.enseignement = enseignement;
        this.note = note;
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public double getNote() { return note; }
    public void setNote(double note) { this.note = note; }
    public Enseignement getEnseignement() { return enseignement; }
    public void setEnseignement(Enseignement enseignement) { this.enseignement = enseignement; }
    public Etudiant getEtudiant() { return etudiant; }
    public void setEtudiant(Etudiant etudiant) { this.etudiant = etudiant; }
    public Master getMaster() { return this.master; }
    public void setMaster(Master master) { this.master = master; }


    @Override
    public String toString() {
        return "Note{" + id + ", " + note + "}";
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Note)) return false;
        Note n = (Note) o;
        return Objects.equals(id, n.id);
    }
    @Override
    public int hashCode() { return Objects.hash(id); }
}
