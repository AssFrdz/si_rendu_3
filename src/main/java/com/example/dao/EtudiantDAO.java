package main.java.com.example.dao;

import main.java.com.example.jdbc.ConfigConnection;
import main.java.com.example.model.Etudiant;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object for Etudiant entity
 * Handles all database operations for the Etudiant table
 */
public class EtudiantDAO {
    private Connection connection;

    /**
     * Constructor - establishes database connection
     */
    public EtudiantDAO() throws Exception {
        this.connection = ConfigConnection.getConnection();
        this.connection.setAutoCommit(false);
    }

    /**
     * Constructor with existing connection (for transaction management)
     */
    public EtudiantDAO(Connection connection) {
        this.connection = connection;
    }

    /**
     * Find an Etudiant by ID
     */
    public Etudiant findById(int id) throws SQLException {
        String sql = "SELECT * FROM Etudiant WHERE Etudiant_ID = ?";
        PreparedStatement ps = null;
        ResultSet rs = null;
        Etudiant etudiant = null;

        try {
            ps = connection.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();

            if (rs.next()) {
                etudiant = new Etudiant();
                etudiant.setId(rs.getInt("Etudiant_ID"));
                etudiant.setNom(rs.getString("Nom"));
                etudiant.setPrenom(rs.getString("Prenom"));
                etudiant.setEmail(rs.getString("Email"));
                // Add other fields as needed
            }
        } finally {
            if (rs != null) rs.close();
            if (ps != null) ps.close();
        }

        return etudiant;
    }

    /**
     * Find an Etudiant by name
     */
    public List<Etudiant> findByNom(String nom) throws SQLException {
        String sql = "SELECT * FROM Etudiant WHERE Nom = ?";
        PreparedStatement ps = null;
        ResultSet rs = null;
        Etudiant etudiant = null;
        List<Etudiant> lstEtudiants = new ArrayList<Etudiant>();

        try {
            ps = connection.prepareStatement(sql);
            ps.setString(1, nom);
            rs = ps.executeQuery();

            while (rs.next()) {
                etudiant = new Etudiant();
                etudiant.setId(rs.getInt("Etudiant_ID"));
                etudiant.setNom(rs.getString("Nom"));
                etudiant.setPrenom(rs.getString("Prenom"));
                etudiant.setEmail(rs.getString("Email"));
                lstEtudiants.add(etudiant);
            }
        } finally {
            if (rs != null) rs.close();
            if (ps != null) ps.close();
        }

        return lstEtudiants;
    }

    /**
     * Get all Etudiants
     */
    public List<Etudiant> findAll() throws SQLException {
        String sql = "SELECT * FROM Etudiant";
        Statement stmt = null;
        ResultSet rs = null;
        List<Etudiant> etudiants = new ArrayList<>();

        try {
            stmt = connection.createStatement();
            rs = stmt.executeQuery(sql);

            while (rs.next()) {
                Etudiant etudiant = new Etudiant();
                etudiant.setId(rs.getInt("Etudiant_ID"));
                etudiant.setNom(rs.getString("Nom"));
                etudiant.setPrenom(rs.getString("Prenom"));
                etudiant.setEmail(rs.getString("Email"));
                etudiants.add(etudiant);
            }
        } finally {
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
        }

        return etudiants;
    }

    /**
     * Insert a new Etudiant
     */
    public void save(Etudiant etudiant) throws SQLException {
        String sql = "INSERT INTO Etudiant (Nom, Prenom, Email,Date_Naissance) VALUES (?, ?, ?,?)";
        PreparedStatement ps = null;
        ResultSet generatedKeys = null;

        try {
            ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, etudiant.getNom());
            ps.setString(2, etudiant.getPrenom());
            ps.setString(3, etudiant.getEmail());
            ps.setDate(4, java.sql.Date.valueOf(etudiant.getDateNaiss()));

            int affectedRows = ps.executeUpdate();

            if (affectedRows > 0) {
                generatedKeys = ps.getGeneratedKeys();
                if (generatedKeys.next()) {
                    etudiant.setId(generatedKeys.getInt(1));
                }
            }

            connection.commit();
            System.out.println("Etudiant saved successfully with ID: " + etudiant.getId());
        } catch (SQLException e) {
            connection.rollback();
            throw e;
        } finally {
            if (generatedKeys != null) generatedKeys.close();
            if (ps != null) ps.close();
        }
    }

    /**
     * Update an existing Etudiant
     */
    public void update(Etudiant etudiant) throws SQLException {
        String sql = "UPDATE Etudiant SET Nom = ?, Prenom = ?, Email = ? WHERE Etudiant_ID = ?";
        PreparedStatement ps = null;

        try {
            ps = connection.prepareStatement(sql);
            ps.setString(1, etudiant.getNom());
            ps.setString(2, etudiant.getPrenom());
            ps.setString(3, etudiant.getEmail());
            ps.setInt(4, etudiant.getId());

            int rowsUpdated = ps.executeUpdate();
            connection.commit();

            System.out.println(rowsUpdated + " Etudiant(s) updated successfully");
        } catch (SQLException e) {
            connection.rollback();
            throw e;
        } finally {
            if (ps != null) ps.close();
        }
    }

    /**
     * Delete an Etudiant by ID
     */
    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM Etudiant WHERE Etudiant_ID = ?";
        PreparedStatement ps = null;

        try {
            ps = connection.prepareStatement(sql);
            ps.setInt(1, id);

            int rowsDeleted = ps.executeUpdate();
            connection.commit();

            System.out.println(rowsDeleted + " Etudiant(s) deleted successfully");
        } catch (SQLException e) {
            connection.rollback();
            throw e;
        } finally {
            if (ps != null) ps.close();
        }
    }

    /**
     * Close the connection
     */
    public void close() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }
}