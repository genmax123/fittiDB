package de.genrichgolzsch.fittidb.dao; // Paket für Datenbankzugriffe (DAO-Schicht)

import de.genrichgolzsch.fittidb.db.DBConnection; // Zugriff auf zentrale DB-Verbindung

import de.genrichgolzsch.fittidb.model.User; // Model-Klasse für Benutzer

import java.sql.Connection; // JDBC-Verbindung

import java.sql.PreparedStatement; // Vorbereitete SQL-Anweisung (sicher)

import java.sql.ResultSet; // Ergebnis einer SQL-Abfrage

import java.sql.SQLException; // SQL-Fehlerbehandlung

/**
 * DAO-Klasse für Benutzer.
 * Enthält alle SQL-Zugriffe auf die Tabelle "users".
 */
public class UserDao { // Klassendefinition

    /**
     * Prüft Login-Daten und liefert einen User zurück,
     * oder null, falls kein Treffer existiert.
     */
    public User login(String username, String password) throws SQLException { // Öffentliche Login-Methode

        String sql = "SELECT user_id, username, role_id, aktive "
                   + "FROM users "
                   + "WHERE username = ? AND password = ?"; // SQL-Abfrage mit Platzhaltern (?)

        Connection connection = DBConnection.getConnection();  // Holt eine offene DB-Verbindung

        PreparedStatement stmt = connection.prepareStatement(sql); // Erstellt ein vorbereitetes SQL-Statement

        stmt.setString(1, username); // Setzt ersten Platzhalter (username)

        stmt.setString(2, password); // Setzt zweiten Platzhalter (password)

        ResultSet rs = stmt.executeQuery();   // Führt SELECT-Abfrage aus

        if (rs.next()) {   // Prüft: existiert ein Datensatz?

            int userId = rs.getInt("user_id");  // Liest Benutzer-ID aus

            String name = rs.getString("username");   // Liest Benutzername aus

            int roleId = rs.getInt("role_id");  // Liest Rollen-ID aus

            boolean active = rs.getBoolean("aktive");  // Liest Aktiv-Status aus

            rs.close();  // Schließt ResultSet

            stmt.close(); // Schließt PreparedStatement

            connection.close(); // Schließt DB-Verbindung

            return new User(userId, name, roleId, active); // Gibt User-Objekt zurück
        }

        rs.close(); // Schließt ResultSet (kein Treffer)

        stmt.close(); // Schließt PreparedStatement

        connection.close(); // Schließt DB-Verbindung

        return null;  // Login fehlgeschlagen
    }
}
