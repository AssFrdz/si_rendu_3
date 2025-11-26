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
import main.java.com.example.model.Master;
import main.java.com.example.model.Note;

public class NoteDAO {

    private Connection cn;
    public NoteDAO() throws Exception{
        this.cn = ConfigConnection.getConnection();
        this.cn.setAutoCommit(false);
    }

    public NoteDAO(Connection cn){
        this.cn = cn;
    }

    
    // Trouver une note par son ID
    public Note findById(int id) throws SQLException {
        String sql = ""
            + "SELECT "
            + "    n.Note_ID, n.Valeur_Note, "
            + "    e.Etudiant_ID, ed.Nom AS etudiant_nom, ed.Prenom AS etudiant_prenom, "
            + "    en.Enseignement_ID, en.Master_ID, en.Intitule AS enseignement_intitule, "
            + "    en.Description AS enseignement_description, en.Obligatoire AS enseignement_obligatoire, "
            + "    es.Enseignant_ID, es.Nom AS enseignant_nom, es.Prenom AS enseignant_prenom, "
            + "    es.Grade, es.Telephone, es.Fax, es.Email, "
            + "    m.Master_ID AS master_id, m.Nom_Master AS master_nom "
            + "FROM Note n "
            + "JOIN Etudiant e ON n.Etudiant_ID = e.Etudiant_ID "
            + "JOIN Etudiant ed ON e.Etudiant_ID = ed.Etudiant_ID "
            + "JOIN Enseignement en ON n.Enseignement_ID = en.Enseignement_ID AND en.Master_ID = en.Master_ID "
            + "JOIN Enseignant es ON en.Enseignant_ID = es.Enseignant_ID "
            + "JOIN Master m ON en.Master_ID = m.Master_ID "
            + "WHERE n.Note_ID = ?";

        PreparedStatement ps = null;
        ResultSet rs = null;
        Note note = null;

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

                note = new Note();
                note.setId(rs.getInt("Note_ID"));
                note.setNote(rs.getDouble("Valeur_Note"));
                note.setEtudiant(etudiant);
                note.setEnseignement(enseignement);
            }
        } finally {
            if (rs != null) rs.close();
            if (ps != null) ps.close();
        }

        return note;
    }

    // Trouver toutes les notes
    public List<Note> findAll() throws SQLException {
        String sql = ""
            + "SELECT "
            + "    n.Note_ID, n.Valeur_Note, "
            + "    e.Etudiant_ID, ed.Nom AS etudiant_nom, ed.Prenom AS etudiant_prenom, "
            + "    en.Enseignement_ID, en.Master_ID, en.Intitule AS enseignement_intitule, "
            + "    en.Descripti1on AS enseignement_description, en.Obligatoire AS enseignement_obligatoire, "
            + "    es.Enseignant_ID, es.Nom AS enseignant_nom, es.Prenom AS enseignant_prenom, "
            + "    es.Grade, es.Telephone, es.Fax, es.Email, "
            + "    m.Master_ID AS master_id, m.Nom_Master AS master_nom "
            + "FROM Note n "
            + "JOIN Etudiant e ON n.Etudiant_ID = e.Etudiant_ID "
            + "JOIN Etudiant ed ON e.Etudiant_ID = ed.Etudiant_ID "
            + "JOIN Enseignement en ON n.Enseignement_ID = en.Enseignement_ID AND en.Master_ID = en.Master_ID "
            + "JOIN Enseignant es ON en.Enseignant_ID = es.Enseignant_ID "
            + "JOIN Master m ON en.Master_ID = m.Master_ID";

        List<Note> notes = new ArrayList<>();
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = cn.prepareStatement(sql);
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

                Note note = new Note();
                note.setId(rs.getInt("Note_ID"));
                note.setNote(rs.getDouble("Valeur_Note"));
                note.setEtudiant(etudiant);
                note.setEnseignement(enseignement);

                notes.add(note);
            }
        } finally {
            if (rs != null) rs.close();
            if (ps != null) ps.close();
        }

        return notes;
    }

    // Calculer la moyenne d'un étudiant pour un enseignement donné
    public double moyenneEnseignement(int idEtudiant, int idEnseignement) throws SQLException {
        String sql = ""
            + "SELECT AVG(Valeur_Note) AS moyenne "
            + "FROM Note "
            + "WHERE Etudiant_ID = ? AND Enseignement_ID = ?";

        PreparedStatement ps = null;
        ResultSet rs = null;
        double moyenne = 0.0;

        try {
            ps = cn.prepareStatement(sql);
            ps.setInt(1, idEtudiant);
            ps.setInt(2, idEnseignement);
            rs = ps.executeQuery();

            if (rs.next()) {
                moyenne = rs.getDouble("moyenne");
            }
        } finally {
            if (rs != null) rs.close();
            if (ps != null) ps.close();
        }

        return moyenne;
    }

    // Sauvegarder une note
    public void save(Note note) throws SQLException {
        String sql = ""
            + "INSERT INTO Note (note_id,Etudiant_ID, Enseignement_ID, Valeur_Note) "
            + "VALUES (?,?, ?, ?)";

        PreparedStatement ps = null;
        try {
            ps = cn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setInt(1, note.getId());
            ps.setInt(2, note.getEtudiant().getId());
            ps.setInt(3, note.getEnseignement().getId());
            ps.setDouble(4, note.getNote());

            int affectedRows = ps.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("La création de la note a échoué, aucune ligne affectée.");
            }

            // Récupérer l'ID généré automatiquement
            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    note.setId(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("Impossible de récupérer l'ID généré pour la note.");
                }
            }
        } finally {
            if (ps != null) ps.close();
        }
    }

    // Mettre à jour une note
    public void update(Note note) throws SQLException {
        String sql = ""
            + "UPDATE Note "
            + "SET Valeur_Note = ?, etudiant_id = ? "
            + "WHERE Note_ID = ?";

        PreparedStatement ps = null;
        try {
            ps = cn.prepareStatement(sql);
            ps.setDouble(1, note.getNote());
            ps.setDouble(2, note.getEtudiant().getId());
            ps.setInt(3, note.getId());

            int affectedRows = ps.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Aucune note trouvée avec l'ID : " + note.getId());
            }
        } finally {
            if (ps != null) ps.close();
        }
    }

    // Supprimer une note
    public void delete(int noteId) throws SQLException {
        String sql = "DELETE FROM Note WHERE Note_ID = ?";

        PreparedStatement ps = null;
        try {
            ps = cn.prepareStatement(sql);
            ps.setInt(1, noteId);

            int affectedRows = ps.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Aucune note trouvée avec l'ID : " + noteId);
            }
        } finally {
            if (ps != null) ps.close();
        }
    }
}

