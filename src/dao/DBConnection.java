package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DBConnection {
    private static final String URL = "jdbc:sqlite:voyages.db";

    static {
        try {
            Class.forName("org.sqlite.JDBC");
            initializeDatabase();
        } catch (Exception e) {
            System.err.println("Erreur initialisation JDBC: " + e.getMessage());
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL);
    }

    private static void initializeDatabase() {
        try (Connection c = getConnection(); Statement st = c.createStatement()) {
            // tables
            st.executeUpdate("CREATE TABLE IF NOT EXISTS vols (id INTEGER PRIMARY KEY AUTOINCREMENT, numero TEXT, destination TEXT, depart TEXT, arrivee TEXT, capacite INTEGER, placesReservees INTEGER DEFAULT 0)");
            st.executeUpdate("CREATE TABLE IF NOT EXISTS passagers (id INTEGER PRIMARY KEY AUTOINCREMENT, nom TEXT, email TEXT, passport TEXT)");
            st.executeUpdate("CREATE TABLE IF NOT EXISTS reservations (id INTEGER PRIMARY KEY AUTOINCREMENT, volId INTEGER, passagerId INTEGER, statut TEXT, FOREIGN KEY(volId) REFERENCES vols(id), FOREIGN KEY(passagerId) REFERENCES passagers(id))");
            st.executeUpdate("CREATE TABLE IF NOT EXISTS agences (id INTEGER PRIMARY KEY AUTOINCREMENT, nom TEXT, contact TEXT, offres TEXT)");
        } catch (SQLException e) {
            System.err.println("Erreur création tables: " + e.getMessage());
        }
    }
}
