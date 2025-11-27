package com.example.dao;

import com.example.jdbc.ConfigConnection;
import com.example.model.Inscription;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class InscriptionDAO {
    private Connection connection;

    public InscriptionDAO() throws Exception {
        this.connection = ConfigConnection.getConnection();
        this.connection.setAutoCommit(false);
    }

    public InscriptionDAO(Connection connection) {
        this.connection = connection;
    }

    public Inscription findById(int id) throws SQLException {
        String sql = "SELECT * FROM Inscription WHERE Inscription_ID = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return extractInscriptionFromResultSet(rs);
                }
            }
        }
        return null;
    }

    public List<Inscription> findAll() throws SQLException {
        List<Inscription> inscriptions = new ArrayList<>();
        String sql = "SELECT * FROM Inscription";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                inscriptions.add(extractInscriptionFromResultSet(rs));
            }
        }
        return inscriptions;
    }

    public void save(Inscription inscription) throws SQLException {
        String sql = "INSERT INTO Inscription (Etudiant_ID, Enseignement_ID, Master_ID) VALUES (?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, inscription.getEtudiantId());
            ps.setInt(2, inscription.getEnseignementId());
            ps.setInt(3, inscription.getMasterId());

            int affectedRows = ps.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        inscription.setInscriptionId(generatedKeys.getInt(1));
                    }
                }
            }
            connection.commit();
        } catch (SQLException e) {
            connection.rollback();
            throw e;
        }
    }

    public void update(Inscription inscription) throws SQLException {
        String sql = "UPDATE Inscription SET Etudiant_ID = ?, Enseignement_ID = ?, Master_ID = ? WHERE Inscription_ID = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, inscription.getEtudiantId());
            ps.setInt(2, inscription.getEnseignementId());
            ps.setInt(3, inscription.getMasterId());
            ps.setInt(4, inscription.getInscriptionId());
            ps.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            connection.rollback();
            throw e;
        }
    }

    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM Inscription WHERE Inscription_ID = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            connection.rollback();
            throw e;
        }
    }

    private Inscription extractInscriptionFromResultSet(ResultSet rs) throws SQLException {
        Inscription inscription = new Inscription();
        inscription.setInscriptionId(rs.getInt("Inscription_ID"));
        inscription.setEtudiantId(rs.getInt("Etudiant_ID"));
        inscription.setEnseignementId(rs.getInt("Enseignement_ID"));
        inscription.setMasterId(rs.getInt("Master_ID"));
        return inscription;
    }

    public void close() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }
}
