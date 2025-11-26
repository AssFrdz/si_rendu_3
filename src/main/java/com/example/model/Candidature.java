package main.java.com.example.model;



import java.util.Objects;

public class Candidature {
    private Etudiant etudiant;
    private Preference preference;
    private boolean confirme;

    public Candidature() {}

    public Etudiant getEtudiant() { return etudiant; }
    public void setEtudiant(Etudiant etudiant) { this.etudiant = etudiant; }
    public Preference getPreference() { return preference; }
    public void setPreference(Preference preference) { this.preference = preference; }
    public boolean isConfirme() { return confirme; }
    public void setConfirme(boolean confirme) { this.confirme = confirme; }

    @Override
    public String toString() {
        return "Candidature{etu=" + etudiant + ", pref=" + preference + ", confirme=" + confirme + "}";
    }
}