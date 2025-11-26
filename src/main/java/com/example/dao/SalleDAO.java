package main.java.com.example.dao;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import main.java.com.example.jdbc.ConfigConnection;
import main.java.com.example.model.Enseignement;
import main.java.com.example.model.Master;
import main.java.com.example.model.Reservation;
import main.java.com.example.model.Salle;

public class SalleDAO {
    private Connection cn;
    public SalleDAO() throws Exception{
        this.cn = ConfigConnection.getConnection();
        this.cn.setAutoCommit(false);
    }

    public SalleDAO(Connection cn){
        this.cn = cn;
    }
    // Trouver une salle par son numéro et son bâtiment
    public Salle findById(String batiment, String numSalle) throws SQLException {
        String sql = ""
            + "SELECT Batiment, Numero_Salle, Capacite "
            + "FROM Salle "
            + "WHERE Batiment = ? AND Numero_Salle = ?";

        PreparedStatement ps = null;
        ResultSet rs = null;
        Salle salle = null;

        try {
            ps = cn.prepareStatement(sql);
            ps.setString(1, batiment);
            ps.setString(2, numSalle);
            rs = ps.executeQuery();

            if (rs.next()) {
                salle = new Salle();
                salle.setBatiment(rs.getString("Batiment"));
                salle.setNumSalle(rs.getString("Numero_Salle"));
                salle.setCapacite(rs.getInt("Capacite"));
                salle.setReservations(findReservationsBySalle(batiment, numSalle));
            }
        } finally {
            if (rs != null) rs.close();
            if (ps != null) ps.close();
        }

        return salle;
    }

    // Trouver toutes les salles
    public List<Salle> findAll() throws SQLException {
        String sql = "SELECT Batiment, Numero_Salle, Capacite FROM Salle";
        List<Salle> salles = new ArrayList<>();
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = cn.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                Salle salle = new Salle();
                salle.setBatiment(rs.getString("Batiment"));
                salle.setNumSalle(rs.getString("Numero_Salle"));
                salle.setCapacite(rs.getInt("Capacite"));
                salle.setReservations(findReservationsBySalle(rs.getString("Batiment"), rs.getString("Numero_Salle")));
                salles.add(salle);
            }
        } finally {
            if (rs != null) rs.close();
            if (ps != null) ps.close();
        }

        return salles;
    }

    // Trouver les réservations pour une salle donnée
    private List<Reservation> findReservationsBySalle(String batiment, String numSalle) throws SQLException {
        String sql = ""
            + "SELECT "
            + "    r.Reservation_ID, "
            + "    r.Batiment, r.Numero_Salle, "
            + "    r.Enseignement_ID, r.Master_ID, "
            + "    r.Date_Resa, r.Heure_Debut, r.Heure_Fin, r.Nombre_Heures, "
            + "    en.Intitule AS enseignement_intitule, "
            + "    m.Nom_Master AS master_nom "
            + "FROM Reservation r "
            + "JOIN Enseignement en ON r.Enseignement_ID = en.Enseignement_ID AND r.Master_ID = en.Master_ID "
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
                Enseignement enseignement = new Enseignement();
                enseignement.setId(rs.getInt("Enseignement_ID"));
                enseignement.setMaster(new Master(rs.getInt("Master_ID"), rs.getString("master_nom")));
                enseignement.setIntitule(rs.getString("enseignement_intitule"));

                Salle salle = new Salle();
                salle.setBatiment(rs.getString("Batiment"));
                salle.setNumSalle(rs.getString("Numero_Salle"));

                Reservation reservation = new Reservation();
                reservation.setId(rs.getInt("Reservation_ID"));
                reservation.setSalle(salle);
                reservation.setEnseignement(enseignement);
                reservation.setMaster(new Master(rs.getInt("Master_ID"), rs.getString("master_nom")));
                reservation.setDebut(LocalDateTime.of(rs.getDate("Date_Resa").toLocalDate(), rs.getTime("Heure_Debut").toLocalTime()));
                reservation.setFin(LocalDateTime.of(rs.getDate("Date_Resa").toLocalDate(), rs.getTime("Heure_Fin").toLocalTime()));
                reservation.setNbHeures(rs.getInt("Nombre_Heures"));

                reservations.add(reservation);
            }
        } finally {
            if (rs != null) rs.close();
            if (ps != null) ps.close();
        }

        return reservations;
    }

    // Sauvegarder une salle
    public void save(Salle salle) throws SQLException {
        String sql = ""
            + "INSERT INTO Salle (Batiment, Numero_Salle, Capacite) "
            + "VALUES (?, ?, ?)";

        PreparedStatement ps = null;
        try {
            ps = cn.prepareStatement(sql);
            ps.setString(1, salle.getBatiment());
            ps.setString(2, salle.getNumSalle());
            ps.setInt(3, salle.getCapacite());

            int affectedRows = ps.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("La création de la salle a échoué, aucune ligne affectée.");
            }
        } finally {
            if (ps != null) ps.close();
        }
    }

    // Mettre à jour une salle
    public void update(Salle salle) throws SQLException {
        String sql = ""
            + "UPDATE Salle "
            + "SET Capacite = ? "
            + "WHERE Batiment = ? AND Numero_Salle = ?";

        PreparedStatement ps = null;
        try {
            ps = cn.prepareStatement(sql);
            ps.setInt(1, salle.getCapacite());
            ps.setString(2, salle.getBatiment());
            ps.setString(3, salle.getNumSalle());

            int affectedRows = ps.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Aucune salle trouvée avec le bâtiment : " + salle.getBatiment() + " et le numéro : " + salle.getNumSalle());
            }
        } finally {
            if (ps != null) ps.close();
        }
    }

    // Supprimer une salle
    public void delete(String batiment, String numSalle) throws SQLException {
        String sql = "DELETE FROM Salle WHERE Batiment = ? AND Numero_Salle = ?";

        PreparedStatement ps = null;
        try {
            ps = cn.prepareStatement(sql);
            ps.setString(1, batiment);
            ps.setString(2, numSalle);

            int affectedRows = ps.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Aucune salle trouvée avec le bâtiment : " + batiment + " et le numéro : " + numSalle);
            }
        } finally {
            if (ps != null) ps.close();
        }
    }
}
