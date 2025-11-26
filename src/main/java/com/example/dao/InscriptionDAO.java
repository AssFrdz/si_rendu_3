package main.java.com.example.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import main.java.com.example.jdbc.ConfigConnection;
import main.java.com.example.model.Enseignant;
import main.java.com.example.model.Enseignement;
import main.java.com.example.model.Etudiant;
import main.java.com.example.model.Inscription;
import main.java.com.example.model.Master;

public class InscriptionDAO {

    private Connection cn;

    public InscriptionDAO() throws Exception{
        this.cn = ConfigConnection.getConnection();
        this.cn.setAutoCommit(false);
    }

    public void setAutoCommit(boolean v) throws SQLException{
        this.cn.setAutoCommit(v);
    }

    public void rollback() throws SQLException{
        this.cn.rollback();
    }

    public InscriptionDAO(Connection cn){
        this.cn = cn;
    }

   public Inscription findById(int id) throws SQLException{
    String sql = ""
        + "SELECT "
        + "    i.Inscription_ID, "
        + "    ed.Etudiant_ID, ed.Nom AS etudiant_nom, ed.Prenom AS etudiant_prenom, "
        + "    en.Enseignement_ID, en.Master_ID, en.Intitule AS enseignement_intitule, "
        + "    en.Description AS enseignement_description, en.Obligatoire AS enseignement_obligatoire, "
        + "    es.Enseignant_ID, es.Nom AS enseignant_nom, es.Prenom AS enseignant_prenom, "
        + "    es.Grade, es.Telephone, es.Fax, es.Email, "
        + "    m.Master_ID AS master_id, m.Nom_Master AS master_nom "
        + "FROM Inscription i "
        + "JOIN Etudiant ed ON i.Etudiant_ID = ed.Etudiant_ID "
        + "JOIN Enseignement en ON i.Enseignement_ID = en.Enseignement_ID AND i.Master_ID = en.Master_ID "
        + "JOIN Enseignant es ON en.Enseignant_ID = es.Enseignant_ID "
        + "JOIN Master m ON en.Master_ID = m.Master_ID "
        + "WHERE i.Inscription_ID = ?";

    PreparedStatement ps = null;
    ResultSet rs = null;
    Inscription inscription = null;

    try {
        ps = cn.prepareStatement(sql);
        ps.setInt(1, id);
        rs = ps.executeQuery();

        if (rs.next()) {
            Etudiant etudiant = new Etudiant(
                rs.getInt("Etudiant_ID"),
                rs.getString("etudiant_nom"),
                rs.getString("etudiant_prenom")
            );

            Enseignant enseignant = new Enseignant(
                rs.getInt("Enseignant_ID"),
                rs.getString("enseignant_nom"),
                rs.getString("enseignant_prenom"),
                rs.getString("Grade"),
                rs.getString("Telephone"),
                rs.getString("Fax"),
                rs.getString("Email")
            );

            Master master = new Master(
                rs.getInt("master_id"),
                rs.getString("master_nom")
            );

            Enseignement enseignement = new Enseignement(
                rs.getInt("Enseignement_ID"),
                rs.getString("enseignement_intitule"),
                rs.getString("enseignement_description"),
                rs.getString("enseignement_obligatoire"),
                enseignant,
                master
            );

            inscription = new Inscription(
                rs.getInt("Inscription_ID"),
                etudiant,
                enseignement
            );
        }
    } finally {
        if (rs != null) rs.close();
        if (ps != null) ps.close();
    }

    return inscription;
}

 public List<Inscription> findByName(String nom) throws SQLException{
    String sql = ""
        + "SELECT "
        + "    i.Inscription_ID, "
        + "    ed.Etudiant_ID, ed.Nom AS etudiant_nom, ed.Prenom AS etudiant_prenom, "
        + "    en.Enseignement_ID, en.Master_ID, en.Intitule AS enseignement_intitule, "
        + "    en.Description AS enseignement_description, en.Obligatoire AS enseignement_obligatoire, "
        + "    es.Enseignant_ID, es.Nom AS enseignant_nom, es.Prenom AS enseignant_prenom, "
        + "    es.Grade, es.Telephone, es.Fax, es.Email, "
        + "    m.Master_ID AS master_id, m.Nom_Master AS master_nom "
        + "FROM Inscription i "
        + "JOIN Etudiant ed ON i.Etudiant_ID = ed.Etudiant_ID "
        + "JOIN Enseignement en ON i.Enseignement_ID = en.Enseignement_ID AND i.Master_ID = en.Master_ID "
        + "JOIN Enseignant es ON en.Enseignant_ID = es.Enseignant_ID "
        + "JOIN Master m ON en.Master_ID = m.Master_ID "
        + "WHERE ed.nom LIKE ?";

    PreparedStatement ps = null;
    ResultSet rs = null;
    Inscription inscription = null;
    List<Inscription> lst = new ArrayList<Inscription>();

    try {
        ps = cn.prepareStatement(sql);
        ps.setString(1, nom);
        rs = ps.executeQuery();

        while (rs.next()) {
            Etudiant etudiant = new Etudiant(
                rs.getInt("Etudiant_ID"),
                rs.getString("etudiant_nom"),
                rs.getString("etudiant_prenom")
            );

            Enseignant enseignant = new Enseignant(
                rs.getInt("Enseignant_ID"),
                rs.getString("enseignant_nom"),
                rs.getString("enseignant_prenom"),
                rs.getString("Grade"),
                rs.getString("Telephone"),
                rs.getString("Fax"),
                rs.getString("Email")
            );

            Master master = new Master(
                rs.getInt("master_id"),
                rs.getString("master_nom")
            );

            Enseignement enseignement = new Enseignement(
                rs.getInt("Enseignement_ID"),
                rs.getString("enseignement_intitule"),
                rs.getString("enseignement_description"),
                rs.getString("enseignement_obligatoire"),
                enseignant,
                master
            );

            inscription = new Inscription(
                rs.getInt("Inscription_ID"),
                etudiant,
                enseignement
            );

            lst.add(inscription);
        }
    } finally {
        if (rs != null) rs.close();
        if (ps != null) ps.close();
    }

    return lst;
}


public List<Inscription> findAll() throws SQLException {
    String sql = ""
        + "SELECT "
        + "    i.inscription_id, i.etudiant_id, ed.nom AS etudiant_nom, ed.prenom AS etudiant_prenom, "
        + "    i.enseignement_id, en.intitule AS enseignement_intitule, en.description AS enseignement_description, en.obligatoire AS enseignement_obligatoire, "
        + "    es.enseignant_id, es.nom AS enseignant_nom, es.prenom AS enseignant_prenom, es.grade, es.telephone, es.fax, es.email, "
        + "    m.master_id, m.nom_master AS master_nom "
        + "FROM Inscription i "
        + "JOIN Etudiant ed ON i.etudiant_id = ed.etudiant_id "
        + "JOIN Enseignement en ON i.enseignement_id = en.enseignement_id "
        + "JOIN Enseignant es ON en.enseignant_id = es.enseignant_id "
        + "JOIN Master m ON en.master_id = m.master_id";

    PreparedStatement ps = null;
    ResultSet rs = null;
    List<Inscription> inscriptions = new ArrayList<>();

    try {
        ps = cn.prepareStatement(sql);
        rs = ps.executeQuery();

        while (rs.next()) {
            Etudiant etudiant = new Etudiant(
                rs.getInt("etudiant_id"),
                rs.getString("etudiant_nom"),
                rs.getString("etudiant_prenom")
            );

            Enseignant enseignant = new Enseignant(
                rs.getInt("enseignant_id"),
                rs.getString("enseignant_nom"),
                rs.getString("enseignant_prenom"),
                rs.getString("grade"),
                rs.getString("telephone"),
                rs.getString("fax"),
                rs.getString("email")
            );

            Master master = new Master(
                rs.getInt("master_id"),
                rs.getString("master_nom")
            );

            Enseignement enseignement = new Enseignement(
                rs.getInt("enseignement_id"),
                rs.getString("enseignement_intitule"),
                rs.getString("enseignement_description"),
                rs.getString("enseignement_obligatoire"),
                enseignant,
                master
            );

            Inscription inscription = new Inscription(
                rs.getInt("inscription_id"),
                etudiant,
                enseignement
            );

            inscriptions.add(inscription);
        }
    } finally {
        if (rs != null) rs.close();
        if (ps != null) ps.close();
    }

    return inscriptions;
}

public void save(Inscription inscription) throws SQLException {
    String sql = ""
        + "INSERT INTO Inscription (inscription_id,etudiant_id, enseignement_id,master_id) "
        + "VALUES (?,?,?,?)";

    PreparedStatement ps = null;
    try {
        ps = cn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
        ps.setInt(1, inscription.getId());
        ps.setInt(2, inscription.getEtudiant().getId());
        ps.setInt(3, inscription.getEnseignement().getId());
        ps.setInt(4, inscription.getMaster().getId());


        int affectedRows = ps.executeUpdate();
        if (affectedRows == 0) {
            throw new SQLException("La création de l'inscription a échoué, aucune ligne affectée.");
        }

        // Récupérer l'ID généré automatiquement
        try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
            if (generatedKeys.next()) {
                inscription.setId(generatedKeys.getInt(1));
            } else {
                throw new SQLException("Impossible de récupérer l'ID généré pour l'inscription.");
            }
        }

    } finally {
        if (ps != null) ps.close();
    }
}

public void update(Inscription inscription) throws SQLException {
    String sql = ""
        + "UPDATE Inscription "
        + "SET etudiant_id = ?, enseignement_id = ?, master_id = ? "
        + "WHERE inscription_id = ?";

    PreparedStatement ps = null;
    try {
        ps = cn.prepareStatement(sql);
        ps.setInt(1, inscription.getEtudiant().getId());
        ps.setInt(2, inscription.getEnseignement().getId());
        ps.setInt(3, inscription.getEnseignement().getMaster().getId());
        ps.setInt(4, inscription.getId());

        int affectedRows = ps.executeUpdate();
        if (affectedRows == 0) {
            throw new SQLException("Aucune inscription trouvée avec l'ID : " + inscription.getId());
        }
    } finally {
        if (ps != null) ps.close();
    }
}

public void delete(int inscriptionId) throws SQLException {
    String sql = "DELETE FROM Inscription WHERE inscription_id = ?";

    PreparedStatement ps = null;
    try {
        ps = cn.prepareStatement(sql);
        ps.setInt(1, inscriptionId);

        int affectedRows = ps.executeUpdate();
        if (affectedRows == 0) {
            throw new SQLException("Aucune inscription trouvée avec l'ID : " + inscriptionId);
        }
    } finally {
        if (ps != null) ps.close();
    }
}


}


    




    

