package main.java.com.example.model;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public class Seminaire {
    private Integer id;
    private Master master;
    private String entreprise;
    private boolean confirme;
    private List<Etudiant> lstPresences;

    public Seminaire() {}

    public Integer getId() { return this.id; }
    public void setId(Integer id) { this.id = id; }
    public Master getMaster() { return this.master; }
    public void setMaster(Master value){ this.master = value;}
    public String getEntreprise(){return this.entreprise;}
    public void setEntreprise(String value){this.entreprise=value;}
    public boolean getConfirme(){return this.confirme;}
    public void setConfirme(boolean value){this.confirme=value;}
    public List<Etudiant> getLstPresence(){return this.lstPresences;}
    public void setLstPresence(List<Etudiant> value){this.lstPresences=value;}

    
}