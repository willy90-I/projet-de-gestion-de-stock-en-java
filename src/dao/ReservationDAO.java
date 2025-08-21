package dao;

import model.Reservation;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReservationDAO {
    public Reservation create(Reservation r) throws SQLException {
        String sql = "INSERT INTO reservations(volId,passagerId,statut) VALUES(?,?,?)";
        try (Connection c = DBConnection.getConnection(); PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setLong(1, r.getVolId());
            ps.setLong(2, r.getPassagerId());
            ps.setString(3, r.getStatut().name());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) { if (rs.next()) r.setId(rs.getLong(1)); }
        }
        return r;
    }

    public Reservation findById(long id) throws SQLException {
        String sql = "SELECT * FROM reservations WHERE id = ?";
        try (Connection c = DBConnection.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) { if (rs.next()) return map(rs); }
        }
        return null;
    }

    public List<Reservation> findAll() throws SQLException {
        String sql = "SELECT * FROM reservations";
        List<Reservation> list = new ArrayList<>();
        try (Connection c = DBConnection.getConnection(); PreparedStatement ps = c.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) list.add(map(rs));
        }
        return list;
    }

    public boolean updateStatus(long id, Reservation.Statut statut) throws SQLException {
        String sql = "UPDATE reservations SET statut = ? WHERE id = ?";
        try (Connection c = DBConnection.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, statut.name());
            ps.setLong(2, id);
            return ps.executeUpdate() > 0;
        }
    }

    public boolean delete(long id) throws SQLException {
        String sql = "DELETE FROM reservations WHERE id = ?";
        try (Connection c = DBConnection.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setLong(1, id);
            return ps.executeUpdate() > 0;
        }
    }

    private Reservation map(ResultSet rs) throws SQLException {
        Reservation r = new Reservation();
        r.setId(rs.getLong("id"));
        r.setVolId(rs.getLong("volId"));
        r.setPassagerId(rs.getLong("passagerId"));
        r.setStatut(Reservation.Statut.valueOf(rs.getString("statut")));
        return r;
    }
}
