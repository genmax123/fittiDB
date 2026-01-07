package de.genrichgolzsch.fittidb.db; // Paket: ordnet die Klasse der DB-Schicht zu

import java.sql.Connection;// JDBC-Schnittstelle für eine Datenbankverbindung

import java.sql.DriverManager;// Klasse zum Erzeugen von DB-Verbindungen

import java.sql.SQLException;// Exception für SQL-Fehler

import java.sql.Statement;// SQL-Befehl-Objekt (z. B. für PRAGMA)

/**
 * Stellt eine Verbindung zur SQLite-Datenbank her.
 * Keine GUI-Logik, keine Fachlogik.
 */
public class DBConnection { // Klassendefinition

    private static final String DB_URL = "jdbc:sqlite:data/fittidb.db"; // Verbindungs-URL zur SQLite-Datenbankdatei

    private static boolean connectedOnce = false; // Merker: wurde schon einmal verbunden?

    public static Connection getConnection() throws SQLException { // Öffentliche Methode, liefert Connection zurück

        Connection connection = DriverManager.getConnection(DB_URL); // Baut die Verbindung zur Datenbank auf

        try (Statement stmt = connection.createStatement()) { // Erstellt ein SQL-Statement (auto-close)

            stmt.execute("PRAGMA foreign_keys = ON"); // Aktiviert Fremdschlüsselprüfung in SQLite
        } // Statement wird hier automatisch geschlossen

        if (!connectedOnce) { // Prüft: Verbindung noch nicht gemeldet?

            System.out.println("✔ Verbindung zur SQLite-Datenbank erfolgreich hergestellt."); // Gibt Statusmeldung in der Konsole aus

            connectedOnce = true; // Merkt sich: Meldung wurde ausgegeben
        }

        return connection; // Gibt die offene Verbindung zurück
    }
}
