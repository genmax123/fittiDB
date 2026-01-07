package de.genrichgolzsch.fittidb.dao;                                     // DAO-Paket

import de.genrichgolzsch.fittidb.db.DBConnection;                          // DB-Verbindung
import de.genrichgolzsch.fittidb.model.HealthData;                         // Model HealthData

import java.sql.Connection;                                                // JDBC Connection
import java.sql.PreparedStatement;                                         // PreparedStatement
import java.sql.ResultSet;                                                 // ResultSet
import java.sql.SQLException;                                              // SQL Exception

/**
 * DAO für die Tabelle "health_data".
 * 1:1-Beziehung zu persons über persons_id.
 */
public class HealthDataDao {                                               // Klassenbeginn

    public HealthData getByPersonId(int personsId) throws SQLException {   // HealthData zu Person laden

        String sql = "SELECT health_id, persons_id, height_cm, weight_kg, bmi_zone " +
                     "FROM health_data " +
                     "WHERE persons_id = ?";                               // SQL-SELECT mit Platzhalter

        Connection connection = DBConnection.getConnection();              // DB öffnen
        PreparedStatement stmt = connection.prepareStatement(sql);         // Statement bauen

        stmt.setInt(1, personsId);                                         // persons_id setzen

        ResultSet rs = stmt.executeQuery();                                // Abfrage ausführen

        if (rs.next()) {                                                   // Datensatz gefunden?

            int healthId = rs.getInt("health_id");                         // Health-ID lesen
            int pid = rs.getInt("persons_id");                             // Personen-ID lesen
            int heightCm = rs.getInt("height_cm");                         // Größe lesen
            int weightKg = rs.getInt("weight_kg");                         // Gewicht lesen
            String bmiZone = rs.getString("bmi_zone");                    // BMI-Zone lesen

            HealthData data = new HealthData(healthId, pid,
                                             heightCm, weightKg, bmiZone); // Objekt bauen

            rs.close();                                                    // ResultSet schließen
            stmt.close();                                                  // Statement schließen
            connection.close();                                             // Connection schließen

            return data;                                                   // HealthData zurückgeben
        }

        rs.close();                                                        // ResultSet schließen (kein Treffer)
        stmt.close();                                                      // Statement schließen
        connection.close();                                                 // Connection schließen

        return null;                                                       // Kein Datensatz vorhanden
    }

    public void insertHealthData(HealthData data) throws SQLException {    // Neue HealthData einfügen

        String sql = "INSERT INTO health_data " +
                     "(persons_id, height_cm, weight_kg, bmi_zone) " +
                     "VALUES (?, ?, ?, ?)";                                // INSERT Statement

        Connection connection = DBConnection.getConnection();              // DB öffnen
        PreparedStatement stmt = connection.prepareStatement(sql);         // Statement bauen

        stmt.setInt(1, data.getPersonsId());                               // 1: persons_id
        stmt.setInt(2, data.getHeightCm());                                // 2: Größe
        stmt.setInt(3, data.getWeightKg());                                // 3: Gewicht
        stmt.setString(4, data.getBmiZone());                              // 4: BMI-Zone

        stmt.executeUpdate();                                              // INSERT ausführen

        stmt.close();                                                      // Statement schließen
        connection.close();                                                 // Connection schließen
    }

    public void updateHealthData(HealthData data) throws SQLException {    // HealthData aktualisieren

        String sql = "UPDATE health_data " +
                     "SET height_cm = ?, weight_kg = ?, bmi_zone = ? " +
                     "WHERE persons_id = ?";                               // UPDATE per persons_id

        Connection connection = DBConnection.getConnection();              // DB öffnen
        PreparedStatement stmt = connection.prepareStatement(sql);         // Statement bauen

        stmt.setInt(1, data.getHeightCm());                                // Neue Größe
        stmt.setInt(2, data.getWeightKg());                                // Neues Gewicht
        stmt.setString(3, data.getBmiZone());                              // Neue BMI-Zone
        stmt.setInt(4, data.getPersonsId());                               // Ziel-Person

        stmt.executeUpdate();                                              // UPDATE ausführen

        stmt.close();                                                      // Statement schließen
        connection.close();                                                 // Connection schließen
    }
}
