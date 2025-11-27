package com.example.dao;

import com.example.jdbc.ConfigConnection;
import com.example.model.Reservation;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReservationDAO {
    private Connection connection;

    public ReservationDAO() throws Exception {
        this.connection = ConfigConnection.getConnection();
        this.connection.setAutoCommit(false);
    }

    public ReservationDAO(Connection connection) {
        this.connection = connection;
    }

    public Reservation findById(int id) throws SQLException {
        String sql = "SELECT * FROM Reservation WHERE Reservation_ID = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return extractReservationFromResultSet(rs);
                }
            }
        }
        return null;
    }

    public List<Reservation> findAll() throws SQLException {
        List<Reservation> reservations = new ArrayList<>();
        String sql = "SELECT * FROM Reservation";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                reservations.add(extractReservationFromResultSet(rs));
            }
        }
        return reservations;
    }

    public void save(Reservation reservation) throws SQLException {
        String sql = "INSERT INTO Reservation (Batiment, Numero_Salle, Enseignement_ID, Master_ID, Enseignant_ID, Date_Resa, Heure_Debut, Heure_Fin, Nombre_Heures) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, reservation.getBatiment());
            ps.setString(2, reservation.getNumeroSalle());
            ps.setInt(3, reservation.getEnseignementId());
            ps.setInt(4, reservation.getMasterId());
            ps.setInt(5, reservation.getEnseignantId());
            ps.setDate(6, reservation.getDateResa());
            ps.setTime(7, reservation.getHeureDebut());
            ps.setTime(8, reservation.getHeureFin());
            ps.setInt(9, reservation.getNombreHeures());

            int affectedRows = ps.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        reservation.setReservationId(generatedKeys.getInt(1));
                    }
                }
            }
            connection.commit();
        } catch (SQLException e) {
            connection.rollback();
            throw e;
        }
    }

    public void update(Reservation reservation) throws SQLException {
        String sql = "UPDATE Reservation SET Batiment = ?, Numero_Salle = ?, Enseignement_ID = ?, Master_ID = ?, Enseignant_ID = ?, Date_Resa = ?, Heure_Debut = ?, Heure_Fin = ?, Nombre_Heures = ? WHERE Reservation_ID = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, reservation.getBatiment());
            ps.setString(2, reservation.getNumeroSalle());
            ps.setInt(3, reservation.getEnseignementId());
            ps.setInt(4, reservation.getMasterId());
            ps.setInt(5, reservation.getEnseignantId());
            ps.setDate(6, reservation.getDateResa());
            ps.setTime(7, reservation.getHeureDebut());
            ps.setTime(8, reservation.getHeureFin());
            ps.setInt(9, reservation.getNombreHeures());
            ps.setInt(10, reservation.getReservationId());
            ps.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            connection.rollback();
            throw e;
        }
    }

    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM Reservation WHERE Reservation_ID = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            connection.rollback();
            throw e;
        }
    }

    private Reservation extractReservationFromResultSet(ResultSet rs) throws SQLException {
        Reservation reservation = new Reservation();
        reservation.setReservationId(rs.getInt("Reservation_ID"));
        reservation.setBatiment(rs.getString("Batiment"));
        reservation.setNumeroSalle(rs.getString("Numero_Salle"));
        reservation.setEnseignementId(rs.getInt("Enseignement_ID"));
        reservation.setMasterId(rs.getInt("Master_ID"));
        reservation.setEnseignantId(rs.getInt("Enseignant_ID"));
        reservation.setDateResa(rs.getDate("Date_Resa"));
        reservation.setHeureDebut(rs.getTime("Heure_Debut"));
        reservation.setHeureFin(rs.getTime("Heure_Fin"));
        reservation.setNombreHeures(rs.getInt("Nombre_Heures"));
        return reservation;
    }

    public void close() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }
}
