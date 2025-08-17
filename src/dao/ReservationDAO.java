package dao;

import model.Reservation;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReservationDAO {

    public int insert(Reservation r) {
        String sql = "INSERT INTO reservations(numero_vol, passager_id, statut, date_reservation) VALUES(?,?,?,?)";
        try {
            Connection c = DBConnection.getConnection();
            try (PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                ps.setString(1, r.getNumeroVol());
                ps.setInt(2, r.getPassagerId());
                ps.setString(3, r.getStatut());
                ps.setString(4, r.getDateReservation());
                ps.executeUpdate();
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) return rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            System.err.println("Erreur insert reservation: " + e.getMessage());
        }
        return -1;
    }

    public boolean updateStatut(int id, String statut) {
        String sql = "UPDATE reservations SET statut=? WHERE id=?";
        try {
            Connection c = DBConnection.getConnection();
            try (PreparedStatement ps = c.prepareStatement(sql)) {
                ps.setString(1, statut);
                ps.setInt(2, id);
                int r = ps.executeUpdate();
                return r > 0;
            }
        } catch (SQLException e) {
            System.err.println("Erreur update reservation: " + e.getMessage());
        }
        return false;
    }

    public List<Reservation> listAll() {
        List<Reservation> list = new ArrayList<>();
        String sql = "SELECT * FROM reservations";
        try {
            Connection c = DBConnection.getConnection();
            try (Statement st = c.createStatement(); ResultSet rs = st.executeQuery(sql)) {
                while (rs.next()) {
                    Reservation r = new Reservation(
                            rs.getInt("id"),
                            rs.getString("numero_vol"),
                            rs.getInt("passager_id"),
                            rs.getString("statut"),
                            rs.getString("date_reservation")
                    );
                    list.add(r);
                }
            }
        } catch (SQLException e) {
            System.err.println("Erreur list reservations: " + e.getMessage());
        }
        return list;
    }
}
