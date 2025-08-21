package dao;

import model.Passager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PassagerDAO {
    public Passager create(Passager p) throws SQLException {
        String sql = "INSERT INTO passagers(nom,email,passport) VALUES(?,?,?)";
        try (Connection c = DBConnection.getConnection(); PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, p.getNom());
            ps.setString(2, p.getEmail());
            ps.setString(3, p.getPassport());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) { if (rs.next()) p.setId(rs.getLong(1)); }
        }
        return p;
    }

    public Passager findById(long id) throws SQLException {
        String sql = "SELECT * FROM passagers WHERE id = ?";
        try (Connection c = DBConnection.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) { if (rs.next()) return map(rs); }
        }
        return null;
    }

    public List<Passager> findAll() throws SQLException {
        String sql = "SELECT * FROM passagers";
        List<Passager> list = new ArrayList<>();
        try (Connection c = DBConnection.getConnection(); PreparedStatement ps = c.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) list.add(map(rs));
        }
        return list;
    }

    public boolean delete(long id) throws SQLException {
        String sql = "DELETE FROM passagers WHERE id = ?";
        try (Connection c = DBConnection.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setLong(1, id);
            return ps.executeUpdate() > 0;
        }
    }

    private Passager map(ResultSet rs) throws SQLException {
        Passager p = new Passager();
        p.setId(rs.getLong("id"));
        p.setNom(rs.getString("nom"));
        p.setEmail(rs.getString("email"));
        p.setPassport(rs.getString("passport"));
        return p;
    }
}
