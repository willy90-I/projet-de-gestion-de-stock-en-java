package dao;

import model.Vol;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VolDAO {

    public boolean insert(Vol v) {
        String sql = "INSERT INTO vols(numero_vol, destination, date_depart, heure_depart, heure_arrivee, capacite) VALUES(?,?,?,?,?,?)";
        try {
            Connection c = DBConnection.getConnection();
            try (PreparedStatement ps = c.prepareStatement(sql)) {
                ps.setString(1, v.getNumeroVol());
                ps.setString(2, v.getDestination());
                ps.setString(3, v.getDateDepart());
                ps.setString(4, v.getHeureDepart());
                ps.setString(5, v.getHeureArrivee());
                ps.setInt(6, v.getCapacite());
                ps.executeUpdate();
                return true;
            }
        } catch (SQLException e) {
            System.err.println("Erreur insert vol: " + e.getMessage());
            return false;
        }
    }

    public List<Vol> listAll() {
        List<Vol> list = new ArrayList<>();
        String sql = "SELECT * FROM vols";
        try {
            Connection c = DBConnection.getConnection();
            try (Statement st = c.createStatement(); ResultSet rs = st.executeQuery(sql)) {
                while (rs.next()) {
                    Vol v = new Vol(
                            rs.getString("numero_vol"),
                            rs.getString("destination"),
                            rs.getString("date_depart"),
                            rs.getString("heure_depart"),
                            rs.getString("heure_arrivee"),
                            rs.getInt("capacite")
                    );
                    list.add(v);
                }
            }
        } catch (SQLException e) {
            System.err.println("Erreur list vols: " + e.getMessage());
        }
        return list;
    }

    public Vol findByNumero(String numero) {
        String sql = "SELECT * FROM vols WHERE numero_vol = ?";
        try {
            Connection c = DBConnection.getConnection();
            try (PreparedStatement ps = c.prepareStatement(sql)) {
                ps.setString(1, numero);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        return new Vol(
                                rs.getString("numero_vol"),
                                rs.getString("destination"),
                                rs.getString("date_depart"),
                                rs.getString("heure_depart"),
                                rs.getString("heure_arrivee"),
                                rs.getInt("capacite")
                        );
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Erreur find vol: " + e.getMessage());
        }
        return null;
    }

    public boolean delete(String numero) {
        String sql = "DELETE FROM vols WHERE numero_vol = ?";
        try {
            Connection c = DBConnection.getConnection();
            try (PreparedStatement ps = c.prepareStatement(sql)) {
                ps.setString(1, numero);
                int r = ps.executeUpdate();
                return r > 0;
            }
        } catch (SQLException e) {
            System.err.println("Erreur delete vol: " + e.getMessage());
            return false;
        }
    }

    public int countReservations(String numeroVol) {
        String sql = "SELECT COUNT(*) as cnt FROM reservations WHERE numero_vol = ? AND statut = 'CONFIRME'";
        try {
            Connection c = DBConnection.getConnection();
            try (PreparedStatement ps = c.prepareStatement(sql)) {
                ps.setString(1, numeroVol);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) return rs.getInt("cnt");
                }
            }
        } catch (SQLException e) {
            System.err.println("Erreur count reservations: " + e.getMessage());
        }
        return 0;
    }
}
