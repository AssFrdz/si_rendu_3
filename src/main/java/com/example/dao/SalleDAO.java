package com.example.dao;

import com.example.jdbc.ConfigConnection;
import com.example.model.Salle;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SalleDAO {
    private Connection connection;

    public SalleDAO() throws Exception {
        this.connection = ConfigConnection.getConnection();
        this.connection.setAutoCommit(false);
    }

    public SalleDAO(Connection connection) {
        this.connection = connection;
    }

    public Salle findById(String batiment, String numeroSalle) throws SQLException {
        String sql = "SELECT * FROM Salle WHERE Batiment = ? AND Numero_Salle = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, batiment);
            ps.setString(2, numeroSalle);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return extractSalleFromResultSet(rs);
                }
            }
        }
        return null;
    }

    public List<Salle> findAll() throws SQLException {
        List<Salle> salles = new ArrayList<>();
        String sql = "SELECT * FROM Salle";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                salles.add(extractSalleFromResultSet(rs));
            }
        }
        return salles;
    }

    public void save(Salle salle) throws SQLException {
        String sql = "INSERT INTO Salle (Batiment, Numero_Salle, Capacite) VALUES (?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, salle.getBatiment());
            ps.setString(2, salle.getNumeroSalle());
            ps.setInt(3, salle.getCapacite());
            ps.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            connection.rollback();
            throw e;
        }
    }

    public void update(Salle salle) throws SQLException {
        String sql = "UPDATE Salle SET Capacite = ? WHERE Batiment = ? AND Numero_Salle = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, salle.getCapacite());
            ps.setString(2, salle.getBatiment());
            ps.setString(3, salle.getNumeroSalle());
            ps.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            connection.rollback();
            throw e;
        }
    }

    public void delete(String batiment, String numeroSalle) throws SQLException {
        String sql = "DELETE FROM Salle WHERE Batiment = ? AND Numero_Salle = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, batiment);
            ps.setString(2, numeroSalle);
            ps.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            connection.rollback();
            throw e;
        }
    }

    private Salle extractSalleFromResultSet(ResultSet rs) throws SQLException {
        Salle salle = new Salle();
        salle.setBatiment(rs.getString("Batiment"));
        salle.setNumeroSalle(rs.getString("Numero_Salle"));
        salle.setCapacite(rs.getInt("Capacite"));
        return salle;
    }

    public void close() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }
}
