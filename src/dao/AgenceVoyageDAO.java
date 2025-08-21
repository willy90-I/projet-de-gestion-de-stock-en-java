package dao;

import model.AgenceVoyage;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AgenceVoyageDAO {
    public AgenceVoyage create(AgenceVoyage a) throws SQLException {
        String sql = "INSERT INTO agences(nom,contact,offres) VALUES(?,?,?)";
        try (Connection c = DBConnection.getConnection(); PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, a.getNom()); ps.setString(2, a.getContact()); ps.setString(3, a.getOffres());
            ps.executeUpdate(); try (ResultSet rs = ps.getGeneratedKeys()) { if (rs.next()) a.setId(rs.getLong(1)); }
        }
        return a;
    }

    public AgenceVoyage findById(long id) throws SQLException {
        String sql = "SELECT * FROM agences WHERE id = ?";
        try (Connection c = DBConnection.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setLong(1, id); try (ResultSet rs = ps.executeQuery()) { if (rs.next()) return map(rs); }
        }
        return null;
    }

    public List<AgenceVoyage> findAll() throws SQLException {
        String sql = "SELECT * FROM agences";
        List<AgenceVoyage> list = new ArrayList<>();
        try (Connection c = DBConnection.getConnection(); PreparedStatement ps = c.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) list.add(map(rs));
        }
        return list;
    }

    public boolean delete(long id) throws SQLException {
        String sql = "DELETE FROM agences WHERE id = ?";
        try (Connection c = DBConnection.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setLong(1, id); return ps.executeUpdate() > 0;
        }
    }

    private AgenceVoyage map(ResultSet rs) throws SQLException {
        AgenceVoyage a = new AgenceVoyage(); a.setId(rs.getLong("id")); a.setNom(rs.getString("nom")); a.setContact(rs.getString("contact")); a.setOffres(rs.getString("offres")); return a;
    }
}
