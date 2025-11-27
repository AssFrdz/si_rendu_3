package com.example.dao;

import com.example.jdbc.ConfigConnection;
import com.example.model.Enseignement;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EnseignementDAO {
    private Connection connection;

    public EnseignementDAO() throws Exception {
        this.connection = ConfigConnection.getConnection();
        this.connection.setAutoCommit(false);
    }

    public EnseignementDAO(Connection connection) {
        this.connection = connection;
    }

    public Enseignement findById(int enseignementId, int masterId) throws SQLException {
        String sql = "SELECT * FROM Enseignement WHERE Enseignement_ID = ? AND Master_ID = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, enseignementId);
            ps.setInt(2, masterId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return extractEnseignementFromResultSet(rs);
                }
            }
        }
        return null;
    }

    public List<Enseignement> findAll() throws SQLException {
        List<Enseignement> enseignements = new ArrayList<>();
        String sql = "SELECT * FROM Enseignement";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                enseignements.add(extractEnseignementFromResultSet(rs));
            }
        }
        return enseignements;
    }

    public void save(Enseignement enseignement) throws SQLException {
        String sql = "INSERT INTO Enseignement (Enseignement_ID, Master_ID, Intitule, Description, Obligatoire, Enseignant_ID) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, enseignement.getEnseignementId());
            ps.setInt(2, enseignement.getMasterId());
            ps.setString(3, enseignement.getIntitule());
            ps.setString(4, enseignement.getDescription());
            ps.setString(5, enseignement.getObligatoire());
            ps.setInt(6, enseignement.getEnseignantId());
            ps.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            connection.rollback();
            throw e;
        }
    }

    public void update(Enseignement enseignement) throws SQLException {
        String sql = "UPDATE Enseignement SET Intitule = ?, Description = ?, Obligatoire = ?, Enseignant_ID = ? WHERE Enseignement_ID = ? AND Master_ID = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, enseignement.getIntitule());
            ps.setString(2, enseignement.getDescription());
            ps.setString(3, enseignement.getObligatoire());
            ps.setInt(4, enseignement.getEnseignantId());
            ps.setInt(5, enseignement.getEnseignementId());
            ps.setInt(6, enseignement.getMasterId());
            ps.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            connection.rollback();
            throw e;
        }
    }

    public void delete(int enseignementId, int masterId) throws SQLException {
        String sql = "DELETE FROM Enseignement WHERE Enseignement_ID = ? AND Master_ID = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, enseignementId);
            ps.setInt(2, masterId);
            ps.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            connection.rollback();
            throw e;
        }
    }

    private Enseignement extractEnseignementFromResultSet(ResultSet rs) throws SQLException {
        Enseignement enseignement = new Enseignement();
        enseignement.setEnseignementId(rs.getInt("Enseignement_ID"));
        enseignement.setMasterId(rs.getInt("Master_ID"));
        enseignement.setIntitule(rs.getString("Intitule"));
        enseignement.setDescription(rs.getString("Description"));
        enseignement.setObligatoire(rs.getString("Obligatoire"));
        enseignement.setEnseignantId(rs.getInt("Enseignant_ID"));
        return enseignement;
    }

    public void close() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }
}
