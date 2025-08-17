package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DBConnection {
    private static final String URL = "jdbc:sqlite:gestion_vols.db";
    private static Connection conn = null;

    public static Connection getConnection() throws SQLException {
        if (conn == null || conn.isClosed()) { // réouvre si fermé
            try {
                Class.forName("org.sqlite.JDBC"); // charge le driver
            } catch (ClassNotFoundException e) {
                System.err.println("Driver SQLite introuvable !");
                throw new SQLException(e);
            }

            conn = DriverManager.getConnection(URL);
            createTables(conn);
        }
        return conn;
    }

    private static void createTables(Connection c) throws SQLException {
        String sqlVols = "CREATE TABLE IF NOT EXISTS vols (" +
                "numero_vol TEXT PRIMARY KEY," +
                "destination TEXT NOT NULL," +
                "date_depart TEXT NOT NULL," +
                "heure_depart TEXT NOT NULL," +
                "heure_arrivee TEXT NOT NULL," +
                "capacite INTEGER DEFAULT 150);";

        String sqlPassagers = "CREATE TABLE IF NOT EXISTS passagers (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nom TEXT NOT NULL," +
                "email TEXT," +
                "numero_passeport TEXT);";

        String sqlReservations = "CREATE TABLE IF NOT EXISTS reservations (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "numero_vol TEXT NOT NULL," +
                "passager_id INTEGER NOT NULL," +
                "statut TEXT NOT NULL," +
                "date_reservation TEXT NOT NULL," +
                "FOREIGN KEY(numero_vol) REFERENCES vols(numero_vol)," +
                "FOREIGN KEY(passager_id) REFERENCES passagers(id));";

        try (Statement st = c.createStatement()) {
            st.execute(sqlVols);
            st.execute(sqlPassagers);
            st.execute(sqlReservations);
        }
    }
}
