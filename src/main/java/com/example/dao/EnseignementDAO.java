package main.java.com.example.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import main.java.com.example.jdbc.ConfigConnection;
import main.java.com.example.model.Enseignant;
import main.java.com.example.model.Enseignement;
import main.java.com.example.model.Etudiant;
import main.java.com.example.model.Inscription;
import main.java.com.example.model.Master;




public class EnseignementDAO {

    private Connection cn;
    public EnseignementDAO() throws Exception{
        this.cn = ConfigConnection.getConnection();
        this.cn.setAutoCommit(false);
    }

    public EnseignementDAO(Connection cn){
        this.cn = cn;
    }

   public Enseignement findById(int id) throws SQLException {
    String sqlEnseignement = ""
        + "SELECT "
        + "    e.Enseignement_ID, e.intitule, e.description, e.Obligatoire, "
        + "    en.Enseignant_ID, en.nom AS enseignant_nom, en.prenom AS enseignant_prenom, "
        + "    en.grade, en.telephone, en.fax, en.email, "
        + "    m.Master_ID, m.nom_master AS master_nom "
        + "FROM Enseignement e "
        + "JOIN Enseignant en ON e.Enseignant_ID = en.Enseignant_ID "
        + "JOIN Master m ON e.Master_id = m.Master_ID "
        + "JOIN Departement d ON m.departement_id = d.departement_id "
        + "WHERE e.Enseignement_ID = ?";

    String sqlInscriptions = ""
        + "SELECT "
        + "    i.Inscription_ID, ed.etudiant_id, ed.nom AS etudiant_nom, ed.prenom AS etudiant_prenom "
        + "FROM Inscription i "
        + "JOIN Etudiant ed ON i.etudiant_id = ed.etudiant_id "
        + "WHERE i.Enseignement_id = ?";

    PreparedStatement stmtEnseignement = null;
    PreparedStatement stmtInscriptions = null;
    ResultSet rsEnseignement = null;
    ResultSet rsInscriptions = null;
    Enseignement e = null;

    try {
        // Recherche de l'enseignement par ID
        stmtEnseignement = cn.prepareStatement(sqlEnseignement);
        stmtEnseignement.setInt(1, id);
        rsEnseignement = stmtEnseignement.executeQuery();

        if (rsEnseignement.next()) {
            e = new Enseignement();
            e.setId(rsEnseignement.getInt("Enseignement_ID"));
            e.setIntitule(rsEnseignement.getString("intitule"));
            e.setDescription(rsEnseignement.getString("description"));
            e.setObligatoire(rsEnseignement.getString("Obligatoire"));

            // Création de l'objet Enseignant
            Enseignant enseignant = new Enseignant(
                rsEnseignement.getInt("Enseignant_ID"),
                rsEnseignement.getString("enseignant_nom"),
                rsEnseignement.getString("enseignant_prenom"),
                rsEnseignement.getString("grade"),
                rsEnseignement.getString("telephone"),
                rsEnseignement.getString("fax"),
                rsEnseignement.getString("email")
            );
            e.setEnseignant(enseignant);

            // Création de l'objet Master
            Master master = new Master(
                rsEnseignement.getInt("Master_ID"),
                rsEnseignement.getString("master_nom")
            );
            e.setMaster(master);

            // Récupération des inscriptions pour cet enseignement
            stmtInscriptions = cn.prepareStatement(sqlInscriptions);
            stmtInscriptions.setInt(1, id);
            rsInscriptions = stmtInscriptions.executeQuery();

            ArrayList<Inscription> lstInscriptions = new ArrayList<>();
            while (rsInscriptions.next()) {
                Etudiant etudiant = new Etudiant(
                    rsInscriptions.getInt("etudiant_id"),
                    rsInscriptions.getString("etudiant_nom"),
                    rsInscriptions.getString("etudiant_prenom")
                );
                Inscription i = new Inscription(
                    rsInscriptions.getInt("Inscription_ID"),
                    etudiant
                );
                lstInscriptions.add(i);
            }
            e.setInscriptions(lstInscriptions);
        }
    } catch (SQLException ex) {
        System.out.println("Erreur SQL : " + ex.getMessage());
    } finally {
        // Fermeture des ressources
        try { if (rsInscriptions != null) rsInscriptions.close(); } catch (SQLException ex) { /* Ignorer */ }
        try { if (stmtInscriptions != null) stmtInscriptions.close(); } catch (SQLException ex) { /* Ignorer */ }
        try { if (rsEnseignement != null) rsEnseignement.close(); } catch (SQLException ex) { /* Ignorer */ }
        try { if (stmtEnseignement != null) stmtEnseignement.close(); } catch (SQLException ex) { /* Ignorer */ }
    }

    return e;
}

    public List<Enseignement> findByIntitule(String intitule) throws SQLException {
    String sqlEnseignement = ""
        + "SELECT "
        + "    e.Enseignement_ID, e.intitule, e.description, e.Obligatoire, "
        + "    en.Enseignant_ID, en.nom AS enseignant_nom, en.prenom AS enseignant_prenom, "
        + "    en.grade, en.telephone, en.fax, en.email, "
        + "    m.Master_ID, m.nom_master AS master_nom "
        + "FROM Enseignement e "
        + "JOIN Enseignant en ON e.Enseignant_ID = en.Enseignant_ID "
        + "JOIN Master m ON e.Master_id = m.Master_ID "
        + "JOIN Departement d ON m.departement_id = d.departement_id "
        + "WHERE e.intitule LIKE ?";

    String sqlInscriptions = ""
        + "SELECT "
        + "    i.Inscription_ID, ed.etudiant_id, ed.nom AS etudiant_nom, ed.prenom AS etudiant_prenom "
        + "FROM Inscription i "
        + "JOIN Etudiant ed ON i.etudiant_id = ed.etudiant_id "
        + "WHERE i.Enseignement_id = ?";

    PreparedStatement stmtEnseignement = null;
    PreparedStatement stmtInscriptions = null;
    ResultSet rsEnseignement = null;
    ResultSet rsInscriptions = null;
    Enseignement e = null;

    List<Enseignement> enseignements = new ArrayList<Enseignement>();

    try {
        // Recherche de l'enseignement par intitule
        stmtEnseignement = cn.prepareStatement(sqlEnseignement);
        stmtEnseignement.setString(1, "%" + intitule + "%");
        rsEnseignement = stmtEnseignement.executeQuery();

        while (rsEnseignement.next()) {
            e = new Enseignement();
            e.setId(rsEnseignement.getInt("Enseignement_ID"));
            e.setIntitule(rsEnseignement.getString("intitule"));
            e.setDescription(rsEnseignement.getString("description"));
            e.setObligatoire(rsEnseignement.getString("Obligatoire"));

            // Création de l'objet Enseignant
            Enseignant enseignant = new Enseignant(
                rsEnseignement.getInt("Enseignant_ID"),
                rsEnseignement.getString("enseignant_nom"),
                rsEnseignement.getString("enseignant_prenom"),
                rsEnseignement.getString("grade"),
                rsEnseignement.getString("telephone"),
                rsEnseignement.getString("fax"),
                rsEnseignement.getString("email")
            );
            e.setEnseignant(enseignant);

            // Création de l'objet Master
            Master master = new Master(
                rsEnseignement.getInt("Master_ID"),
                rsEnseignement.getString("master_nom")
            );
            e.setMaster(master);

            // Récupération des inscriptions pour cet enseignement
            stmtInscriptions = cn.prepareStatement(sqlInscriptions);
            stmtInscriptions.setInt(1, e.getId());
            rsInscriptions = stmtInscriptions.executeQuery();

            ArrayList<Inscription> lstInscriptions = new ArrayList<>();
            while (rsInscriptions.next()) {
                Etudiant etudiant = new Etudiant(
                    rsInscriptions.getInt("etudiant_id"),
                    rsInscriptions.getString("etudiant_nom"),
                    rsInscriptions.getString("etudiant_prenom")
                );
                Inscription i = new Inscription(
                    rsInscriptions.getInt("Inscription_ID"),
                    etudiant
                );
                lstInscriptions.add(i);
            }
            e.setInscriptions(lstInscriptions);

            enseignements.add(e);
        }
    } catch (SQLException ex) {
        System.out.println("Erreur SQL : " + ex.getMessage());
    } finally {
        // Fermeture des ressources
        try { if (rsInscriptions != null) rsInscriptions.close(); } catch (SQLException ex) { /* Ignorer */ }
        try { if (stmtInscriptions != null) stmtInscriptions.close(); } catch (SQLException ex) { /* Ignorer */ }
        try { if (rsEnseignement != null) rsEnseignement.close(); } catch (SQLException ex) { /* Ignorer */ }
        try { if (stmtEnseignement != null) stmtEnseignement.close(); } catch (SQLException ex) { /* Ignorer */ }
    }

    return enseignements;
}


    public List<Enseignement> findAll() throws SQLException {
    String sqlEnseignement = ""
        + "SELECT "
        + "    e.Enseignement_ID, e.intitule, e.description, e.Obligatoire, "
        + "    en.Enseignant_ID, en.nom AS enseignant_nom, en.prenom AS enseignant_prenom, "
        + "    en.grade, en.telephone, en.fax, en.email, "
        + "    m.Master_ID, m.nom_master AS master_nom "
        + "FROM Enseignement e "
        + "JOIN Enseignant en ON e.Enseignant_ID = en.Enseignant_ID "
        + "JOIN Master m ON e.Master_id = m.Master_ID "
        + "JOIN Departement d ON m.departement_id = d.departement_id";

    String sqlInscriptions = ""
        + "SELECT "
        + "    i.Inscription_ID, ed.etudiant_id, ed.nom AS etudiant_nom, ed.prenom AS etudiant_prenom "
        + "FROM Inscription i "
        + "JOIN Etudiant ed ON i.etudiant_id = ed.etudiant_id "
        + "WHERE i.Enseignement_id = ?";

    List<Enseignement> lstEns = new ArrayList<>();
    PreparedStatement stmtEnseignement = null;
    PreparedStatement stmtInscriptions = null;
    ResultSet rsEnseignement = null;
    ResultSet rsInscriptions = null;

    try {
        
        stmtEnseignement = cn.prepareStatement(sqlEnseignement);
        rsEnseignement = stmtEnseignement.executeQuery();

        while (rsEnseignement.next()) {
            Enseignement e = new Enseignement();
            e.setId(rsEnseignement.getInt("Enseignement_ID"));
            e.setIntitule(rsEnseignement.getString("intitule"));
            e.setDescription(rsEnseignement.getString("description"));
            e.setObligatoire(rsEnseignement.getString("Obligatoire"));

            Enseignant enseignant = new Enseignant(
                rsEnseignement.getInt("Enseignant_ID"),
                rsEnseignement.getString("enseignant_nom"),
                rsEnseignement.getString("enseignant_prenom"),
                rsEnseignement.getString("grade"),
                rsEnseignement.getString("telephone"),
                rsEnseignement.getString("fax"),
                rsEnseignement.getString("email")
            );
            e.setEnseignant(enseignant);

           
            Master master = new Master(
                rsEnseignement.getInt("Master_ID"),
                rsEnseignement.getString("master_nom")
            );
            e.setMaster(master);

            
            stmtInscriptions = cn.prepareStatement(sqlInscriptions);
            stmtInscriptions.setInt(1, e.getId());
            rsInscriptions = stmtInscriptions.executeQuery();

            ArrayList<Inscription> lstInscriptions = new ArrayList<>();
            while (rsInscriptions.next()) {
                Etudiant etudiant = new Etudiant(
                    rsInscriptions.getInt("etudiant_id"),
                    rsInscriptions.getString("etudiant_nom"),
                    rsInscriptions.getString("etudiant_prenom")
                );
                Inscription i = new Inscription(
                    rsInscriptions.getInt("Inscription_ID"),
                    etudiant
                );
                lstInscriptions.add(i);
            }
            e.setInscriptions(lstInscriptions);

            lstEns.add(e);
        }
    } catch (SQLException ex) {
        System.out.println("Erreur SQL : " + ex.getMessage());
    } finally {
        try { if (rsInscriptions != null) rsInscriptions.close(); } catch (SQLException ex) { /* Ignorer */ }
        try { if (stmtInscriptions != null) stmtInscriptions.close(); } catch (SQLException ex) { /* Ignorer */ }
        try { if (rsEnseignement != null) rsEnseignement.close(); } catch (SQLException ex) { /* Ignorer */ }
        try { if (stmtEnseignement != null) stmtEnseignement.close(); } catch (SQLException ex) { /* Ignorer */ }
    }

    return lstEns;
}


public void save(Enseignement enseignement) throws SQLException {
    String sql = ""
        + "INSERT INTO Enseignement (Enseignement_ID,Master_ID, Intitule, Description, Obligatoire, Enseignant_ID) "
        + "VALUES (?, ?, ?, ?, ?, ?)";

    PreparedStatement stmt = null;
    ResultSet generatedKeys = null;

    try {
        stmt = cn.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
        stmt.setInt(1, enseignement.getId());
        stmt.setInt(2, enseignement.getMaster().getId());
        stmt.setString(3, enseignement.getIntitule());
        stmt.setString(4, enseignement.getDescription());
        stmt.setString(5, enseignement.isObligatoire());
        stmt.setInt(6, enseignement.getEnseignant().getId());

        int affectedRows = stmt.executeUpdate();

        if (affectedRows > 0) {
                generatedKeys = stmt.getGeneratedKeys();
                if (generatedKeys.next()) {
                    enseignement.setId(generatedKeys.getInt(1));
                }
        }
        
        cn.commit();
        System.out.println("Enseignement saved successfully with ID: " + enseignement.getId());

    } catch (SQLException e) {
            cn.rollback();
            throw e;
    } finally {
        if (generatedKeys != null) generatedKeys.close();
        if (stmt != null) stmt.close();
    }
}

 public void update(Enseignement enseignement) throws SQLException {
        String sql = "UPDATE Enseignement SET Master_ID = ?, Intitule = ?, Description = ?,Obligatoire = ?, Enseignant_ID = ?  WHERE Enseignement_id = ?";
        PreparedStatement ps = null;

        try {
            ps = cn.prepareStatement(sql);
            ps.setInt(1, enseignement.getMaster().getId());
            ps.setString(2, enseignement.getIntitule());
            ps.setString(3, enseignement.getDescription());
            ps.setString(4, enseignement.isObligatoire());
            ps.setInt(5, enseignement.getEnseignant().getId());
            ps.setInt(6, enseignement.getId());

            int rowsUpdated = ps.executeUpdate();
            cn.commit();

            System.out.println(rowsUpdated + " Enseignements updated successfully");
        } catch (SQLException e) {
            cn.rollback();
            throw e;
        } finally {
            if (ps != null) ps.close();
        }
    }

public void delete(int enseignementId) throws SQLException {
    String sql = "DELETE FROM Enseignement WHERE Enseignement_ID = ?";

    PreparedStatement stmt = null;
    try {
        stmt = cn.prepareStatement(sql);
        stmt.setInt(1, enseignementId);

        int affectedRows = stmt.executeUpdate();
        if (affectedRows == 0) {
            throw new SQLException("Aucun enseignement trouvé avec l'ID : " + enseignementId);
        }
    } finally {
        if (stmt != null) {
            stmt.close();
        }
    }
}


}

