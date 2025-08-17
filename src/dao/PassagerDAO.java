package dao;

import model.Passager;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PassagerDAO {

    public int insert(Passager p) {
        String sql = "INSERT INTO passagers(nom,email,numero_passeport) VALUES(?,?,?)";
        try {
            Connection c = DBConnection.getConnection();
            try (PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                ps.setString(1, p.getNom());
                ps.setString(2, p.getEmail());
                ps.setString(3, p.getNumeroPasseport());
                ps.executeUpdate();
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) return rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            System.err.println("Erreur insert passager: " + e.getMessage());
        }
        return -1;
    }

    public Passager findById(int id) {
        String sql = "SELECT * FROM passagers WHERE id=?";
        try {
            Connection c = DBConnection.getConnection();
            try (PreparedStatement ps = c.prepareStatement(sql)) {
                ps.setInt(1, id);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        return new Passager(
                                rs.getInt("id"),
                                rs.getString("nom"),
                                rs.getString("email"),
                                rs.getString("numero_passeport")
                        );
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Erreur find passager: " + e.getMessage());
        }
        return null;
    }

    public List<Passager> listAll() {
        List<Passager> list = new ArrayList<>();
        String sql = "SELECT * FROM passagers";
        try {
            Connection c = DBConnection.getConnection();
            try (Statement st = c.createStatement(); ResultSet rs = st.executeQuery(sql)) {
                while (rs.next()) {
                    Passager p = new Passager(
                            rs.getInt("id"),
                            rs.getString("nom"),
                            rs.getString("email"),
                            rs.getString("numero_passeport")
                    );
                    list.add(p);
                }
            }
        } catch (SQLException e) {
            System.err.println("Erreur list passagers: " + e.getMessage());
        }
        return list;
    }
}
