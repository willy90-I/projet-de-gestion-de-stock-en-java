package dao;

import model.Vol;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class VolDAO {
    private static final DateTimeFormatter fmt = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    public Vol create(Vol v) throws SQLException {
        String sql = "INSERT INTO vols(numero,destination,depart,arrivee,capacite,placesReservees) VALUES(?,?,?,?,?,?)";
        try (Connection c = DBConnection.getConnection(); PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, v.getNumero());
            ps.setString(2, v.getDestination());
            ps.setString(3, v.getDepart().format(fmt));
            ps.setString(4, v.getArrivee().format(fmt));
            ps.setInt(5, v.getCapacite());
            ps.setInt(6, v.getPlacesReservees());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) v.setId(rs.getLong(1));
            }
        }
        return v;
    }

    public Vol findById(long id) throws SQLException {
        String sql = "SELECT * FROM vols WHERE id = ?";
        try (Connection c = DBConnection.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return map(rs);
            }
        }
        return null;
    }

    public List<Vol> findAll() throws SQLException {
        String sql = "SELECT * FROM vols";
        List<Vol> list = new ArrayList<>();
        try (Connection c = DBConnection.getConnection(); PreparedStatement ps = c.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) list.add(map(rs));
        }
        return list;
    }

    public List<Vol> searchByDestination(String destination) throws SQLException {
        String sql = "SELECT * FROM vols WHERE destination LIKE ?";
        List<Vol> list = new ArrayList<>();
        try (Connection c = DBConnection.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, "%" + destination + "%");
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) list.add(map(rs));
            }
        }
        return list;
    }

    public boolean delete(long id) throws SQLException {
        String sql = "DELETE FROM vols WHERE id = ?";
        try (Connection c = DBConnection.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setLong(1, id);
            return ps.executeUpdate() > 0;
        }
    }

    public boolean updatePlacesReservees(long id, int newReserved) throws SQLException {
        String sql = "UPDATE vols SET placesReservees = ? WHERE id = ?";
        try (Connection c = DBConnection.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, newReserved);
            ps.setLong(2, id);
            return ps.executeUpdate() > 0;
        }
    }

    private Vol map(ResultSet rs) throws SQLException {
        Vol v = new Vol();
        v.setId(rs.getLong("id"));
        v.setNumero(rs.getString("numero"));
        v.setDestination(rs.getString("destination"));
        v.setDepart(LocalDateTime.parse(rs.getString("depart"), fmt));
        v.setArrivee(LocalDateTime.parse(rs.getString("arrivee"), fmt));
        v.setCapacite(rs.getInt("capacite"));
        v.setPlacesReservees(rs.getInt("placesReservees"));
        return v;
    }
}

