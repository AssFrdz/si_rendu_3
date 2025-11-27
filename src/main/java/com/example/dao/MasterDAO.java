package com.example.dao;

import com.example.jdbc.ConfigConnection;
import com.example.model.Master;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MasterDAO {
    private Connection connection;

    public MasterDAO() throws Exception {
        this.connection = ConfigConnection.getConnection();
        this.connection.setAutoCommit(false);
    }

    public MasterDAO(Connection connection) {
        this.connection = connection;
    }

    public Master findById(int id) throws SQLException {
        String sql = "SELECT * FROM Master WHERE Master_ID = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return extractMasterFromResultSet(rs);
                }
            }
        }
        return null;
    }

    public List<Master> findAll() throws SQLException {
        List<Master> masters = new ArrayList<>();
        String sql = "SELECT * FROM Master";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                masters.add(extractMasterFromResultSet(rs));
            }
        }
        return masters;
    }

    public void save(Master master) throws SQLException {
        String sql = "INSERT INTO Master (Nom_Master, Responsable_ID, Departement_ID) VALUES (?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, master.getNomMaster());
            ps.setInt(2, master.getResponsableId());
            ps.setInt(3, master.getDepartementId());

            int affectedRows = ps.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        master.setMasterId(generatedKeys.getInt(1));
                    }
                }
            }
            connection.commit();
        } catch (SQLException e) {
            connection.rollback();
            throw e;
        }
    }

    public void update(Master master) throws SQLException {
        String sql = "UPDATE Master SET Nom_Master = ?, Responsable_ID = ?, Departement_ID = ? WHERE Master_ID = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, master.getNomMaster());
            ps.setInt(2, master.getResponsableId());
            ps.setInt(3, master.getDepartementId());
            ps.setInt(4, master.getMasterId());
            ps.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            connection.rollback();
            throw e;
        }
    }

    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM Master WHERE Master_ID = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            connection.rollback();
            throw e;
        }
    }

    private Master extractMasterFromResultSet(ResultSet rs) throws SQLException {
        Master master = new Master();
        master.setMasterId(rs.getInt("Master_ID"));
        master.setNomMaster(rs.getString("Nom_Master"));
        master.setResponsableId(rs.getInt("Responsable_ID"));
        master.setDepartementId(rs.getInt("Departement_ID"));
        return master;
    }

    public void close() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }
}
