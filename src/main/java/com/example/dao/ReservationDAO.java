package main.java.com.example.dao;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import main.java.com.example.jdbc.ConfigConnection;
import main.java.com.example.model.Enseignant;
import main.java.com.example.model.Enseignement;
import main.java.com.example.model.Master;
import main.java.com.example.model.Reservation;
import main.java.com.example.model.Salle;

public class ReservationDAO {
    private Connection cn;

    public ReservationDAO() throws Exception{
        this.cn = ConfigConnection.getConnection();
        this.cn.setAutoCommit(false);
    }
    public ReservationDAO(Connection cn) {
        this.cn = cn;
    }

    // Trouver une réservation par son ID
    public Reservation findById(int id) throws SQLException {
        String sql = ""
            + "SELECT "
            + "    r.Reservation_ID, "
            + "    r.Batiment, r.Numero_Salle, "
            + "    r.Enseignement_ID, r.Master_ID, "
            + "    r.Date_Resa, r.Heure_Debut, r.Heure_Fin, r.Nombre_Heures, "
            + "    en.Intitule AS enseignement_intitule, en.Description AS enseignement_description, en.Obligatoire AS enseignement_obligatoire, "
            + "    es.Enseignant_ID, es.Nom AS enseignant_nom, es.Prenom AS enseignant_prenom, es.Grade, es.Telephone, es.Fax, es.Email, "
            + "    m.Master_ID AS master_id, m.Nom_Master AS master_nom "
            + "FROM Reservation r "
            + "JOIN Enseignement en ON r.Enseignement_ID = en.Enseignement_ID AND r.Master_ID = en.Master_ID "
            + "JOIN Enseignant es ON en.Enseignant_ID = es.Enseignant_ID "
            + "JOIN Master m ON r.Master_ID = m.Master_ID "
            + "WHERE r.Reservation_ID = ?";

        PreparedStatement ps = null;
        ResultSet rs = null;
        Reservation reservation = null;

        try {
            ps = cn.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();

            if (rs.next()) {
                Salle salle = new Salle();
                salle.setBatiment(rs.getString("Batiment"));
                salle.setNumSalle(rs.getString("Numero_Salle"));

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

                LocalDateTime dateDebut = LocalDateTime.of(
                    rs.getDate("Date_Resa").toLocalDate(),
                    rs.getTime("Heure_Debut").toLocalTime()
                );

                LocalDateTime dateFin = LocalDateTime.of(
                    rs.getDate("Date_Resa").toLocalDate(),
                    rs.getTime("Heure_Fin").toLocalTime()
                );

                reservation = new Reservation();
                reservation.setId(rs.getInt("Reservation_ID"));
                reservation.setSalle(salle);
                reservation.setEnseignement(enseignement);
                reservation.setMaster(master);
                reservation.setDebut(dateDebut);
                reservation.setFin(dateFin);
                reservation.setNbHeures(rs.getInt("Nombre_Heures"));
            }
        } finally {
            if (rs != null) rs.close();
            if (ps != null) ps.close();
        }

        return reservation;
    }

    // Trouver toutes les réservations
    public List<Reservation> findAll() throws SQLException {
        String sql = ""
            + "SELECT "
            + "    r.Reservation_ID, "
            + "    r.Batiment, r.Numero_Salle, "
            + "    r.Enseignement_ID, r.Master_ID, "
            + "    r.Date_Resa, r.Heure_Debut, r.Heure_Fin, r.Nombre_Heures, "
            + "    en.Intitule AS enseignement_intitule, en.Description AS enseignement_description, en.Obligatoire AS enseignement_obligatoire, "
            + "    es.Enseignant_ID, es.Nom AS enseignant_nom, es.Prenom AS enseignant_prenom, es.Grade, es.Telephone, es.Fax, es.Email, "
            + "    m.Master_ID AS master_id, m.Nom_Master AS master_nom "
            + "FROM Reservation r "
            + "JOIN Enseignement en ON r.Enseignement_ID = en.Enseignement_ID AND r.Master_ID = en.Master_ID "
            + "JOIN Enseignant es ON en.Enseignant_ID = es.Enseignant_ID "
            + "JOIN Master m ON r.Master_ID = m.Master_ID";

        List<Reservation> reservations = new ArrayList<>();
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = cn.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                Salle salle = new Salle();
                salle.setBatiment(rs.getString("Batiment"));
                salle.setNumSalle(rs.getString("Numero_Salle"));

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

                LocalDateTime dateDebut = LocalDateTime.of(
                    rs.getDate("Date_Resa").toLocalDate(),
                    rs.getTime("Heure_Debut").toLocalTime()
                );

                LocalDateTime dateFin = LocalDateTime.of(
                    rs.getDate("Date_Resa").toLocalDate(),
                    rs.getTime("Heure_Fin").toLocalTime()
                );

                Reservation reservation = new Reservation();
                reservation.setId(rs.getInt("Reservation_ID"));
                reservation.setSalle(salle);
                reservation.setEnseignement(enseignement);
                reservation.setMaster(master);
                reservation.setDebut(dateDebut);
                reservation.setFin(dateFin);
                reservation.setNbHeures(rs.getInt("Nombre_Heures"));

                reservations.add(reservation);
            }
        } finally {
            if (rs != null) rs.close();
            if (ps != null) ps.close();
        }

        return reservations;
    }

    // Trouver les réservations pour une salle donnée
    public List<Reservation> findBySalle(String batiment, String numSalle) throws SQLException {
        String sql = ""
            + "SELECT "
            + "    r.Reservation_ID, "
            + "    r.Batiment, r.Numero_Salle, "
            + "    r.Enseignement_ID, r.Master_ID, "
            + "    r.Date_Resa, r.Heure_Debut, r.Heure_Fin, r.Nombre_Heures, "
            + "    en.Intitule AS enseignement_intitule, en.Description AS enseignement_description, en.Obligatoire AS enseignement_obligatoire, "
            + "    es.Enseignant_ID, es.Nom AS enseignant_nom, es.Prenom AS enseignant_prenom, es.Grade, es.Telephone, es.Fax, es.Email, "
            + "    m.Master_ID AS master_id, m.Nom_Master AS master_nom "
            + "FROM Reservation r "
            + "JOIN Enseignement en ON r.Enseignement_ID = en.Enseignement_ID AND r.Master_ID = en.Master_ID "
            + "JOIN Enseignant es ON en.Enseignant_ID = es.Enseignant_ID "
            + "JOIN Master m ON r.Master_ID = m.Master_ID "
            + "WHERE r.Batiment = ? AND r.Numero_Salle = ?";

        List<Reservation> reservations = new ArrayList<>();
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = cn.prepareStatement(sql);
            ps.setString(1, batiment);
            ps.setString(2, numSalle);
            rs = ps.executeQuery();

            while (rs.next()) {
                Salle salle = new Salle();
                salle.setBatiment(rs.getString("Batiment"));
                salle.setNumSalle(rs.getString("Numero_Salle"));

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

                LocalDateTime dateDebut = LocalDateTime.of(
                    rs.getDate("Date_Resa").toLocalDate(),
                    rs.getTime("Heure_Debut").toLocalTime()
                );

                LocalDateTime dateFin = LocalDateTime.of(
                    rs.getDate("Date_Resa").toLocalDate(),
                    rs.getTime("Heure_Fin").toLocalTime()
                );

                Reservation reservation = new Reservation();
                reservation.setId(rs.getInt("Reservation_ID"));
                reservation.setSalle(salle);
                reservation.setEnseignement(enseignement);
                reservation.setMaster(master);
                reservation.setDebut(dateDebut);
                reservation.setFin(dateFin);
                reservation.setNbHeures(rs.getInt("Nombre_Heures"));

                reservations.add(reservation);
            }
        } finally {
            if (rs != null) rs.close();
            if (ps != null) ps.close();
        }

        return reservations;
    }

    // Sauvegarder une réservation
   public void save(Reservation reservation) throws SQLException {
    String sql = ""
        + "INSERT INTO Reservation (Batiment, Numero_Salle, Enseignement_ID, Enseignant_ID, Master_ID, Date_Resa, Heure_Debut, Heure_Fin, Nombre_Heures) "
        + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
    PreparedStatement ps = null;
    try {
        ps = cn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
        ps.setString(1, reservation.getSalle().getBatiment());
        ps.setString(2, reservation.getSalle().getNumSalle());
        ps.setInt(3, reservation.getEnseignement().getId());
        ps.setInt(4, reservation.getEnseignement().getEnseignant().getId()); // Ajout de l'enseignant_id
        ps.setInt(5, reservation.getEnseignement().getMaster().getId());
        ps.setDate(6, Date.valueOf(reservation.getDebut().toLocalDate()));
        ps.setTime(7, Time.valueOf(reservation.getDebut().toLocalTime()));
        ps.setTime(8, Time.valueOf(reservation.getFin().toLocalTime()));
        ps.setInt(9, reservation.getNbHeures());
        int affectedRows = ps.executeUpdate();
        if (affectedRows == 0) {
            throw new SQLException("La création de la réservation a échoué, aucune ligne affectée.");
        }
        // Récupérer l'ID généré automatiquement
        try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
            if (generatedKeys.next()) {
                reservation.setId(generatedKeys.getInt(1));
            } else {
                throw new SQLException("Impossible de récupérer l'ID généré pour la réservation.");
            }
        }
    } finally {
        if (ps != null) ps.close();
    }
}

 public void update(Reservation reservation) throws SQLException {
    String sql = ""
        + "UPDATE Reservation "
        + "SET Batiment = ?, Numero_Salle = ?, Enseignement_ID = ?, Enseignant_ID = ?, Master_ID = ?, Date_Resa = ?, Heure_Debut = ?, Heure_Fin = ?, Nombre_Heures = ? "
        + "WHERE Reservation_ID = ?";
    PreparedStatement ps = null;
    try {
        ps = cn.prepareStatement(sql);
        ps.setString(1, reservation.getSalle().getBatiment());
        ps.setString(2, reservation.getSalle().getNumSalle());
        ps.setInt(3, reservation.getEnseignement().getId());
        ps.setInt(4, reservation.getEnseignement().getEnseignant().getId()); // Ajout de l'enseignant_id
        ps.setInt(5, reservation.getEnseignement().getMaster().getId());
        ps.setDate(6, Date.valueOf(reservation.getDebut().toLocalDate()));
        ps.setTime(7, Time.valueOf(reservation.getDebut().toLocalTime()));
        ps.setTime(8, Time.valueOf(reservation.getFin().toLocalTime()));
        ps.setInt(9, reservation.getNbHeures());
        ps.setInt(10, reservation.getId());
        int affectedRows = ps.executeUpdate();
        if (affectedRows == 0) {
            throw new SQLException("Aucune réservation trouvée avec l'ID : " + reservation.getId());
        }
    } finally {
        if (ps != null) ps.close();
    }
}
  // Supprimer une réservation
    public void delete(int reservationId) throws SQLException {
        String sql = "DELETE FROM Reservation WHERE Reservation_ID = ?";

        PreparedStatement ps = null;
        try {
            ps = cn.prepareStatement(sql);
            ps.setInt(1, reservationId);

            int affectedRows = ps.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Aucune réservation trouvée avec l'ID : " + reservationId);
            }
        } finally {
            if (ps != null) ps.close();
        }
    }
}
