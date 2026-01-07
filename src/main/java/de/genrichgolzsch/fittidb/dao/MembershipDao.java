package de.genrichgolzsch.fittidb.dao;                                 // DAO-Paket

import de.genrichgolzsch.fittidb.db.DBConnection;                      // DB-Verbindung
import de.genrichgolzsch.fittidb.model.Membership;                     // Model Membership

import java.sql.Connection;                                            // JDBC Connection
import java.sql.PreparedStatement;                                     // PreparedStatement
import java.sql.ResultSet;                                             // ResultSet
import java.sql.SQLException;                                          // SQL Exception

/**
 * DAO für die Tabelle "memberships".
 */
public class MembershipDao {                                           // Klassenbeginn

    public Membership getByPersonId(int personsId) throws SQLException { // Membership zu Person laden

        String sql = "SELECT membership_id, persons_id, plan_id, start_date, active " +
                     "FROM memberships " +
                     "WHERE persons_id = ?";                           // SQL-SELECT

        Connection connection = DBConnection.getConnection();           // DB öffnen
        PreparedStatement stmt = connection.prepareStatement(sql);      // Statement bauen

        stmt.setInt(1, personsId);                                      // Platzhalter setzen

        ResultSet rs = stmt.executeQuery();                             // Abfrage ausführen

        if (rs.next()) {                                                // Treffer vorhanden?

            Membership membership = new Membership(
                rs.getInt("membership_id"),                             // membership_id
                rs.getInt("persons_id"),                                // persons_id
                rs.getInt("plan_id"),                                   // plan_id
                rs.getString("start_date"),                             // start_date
                rs.getBoolean("active")                                 // active
            );                                                          // Objekt bauen

            rs.close();                                                 // ResultSet schließen
            stmt.close();                                               // Statement schließen
            connection.close();                                         // Connection schließen

            return membership;                                          // Membership zurückgeben
        }

        rs.close();                                                     // ResultSet schließen
        stmt.close();                                                   // Statement schließen
        connection.close();                                             // Connection schließen

        return null;                                                    // Kein Datensatz gefunden
    }

    public void insertMembership(Membership membership) throws SQLException { // Membership einfügen

        String sql = "INSERT INTO memberships (persons_id, plan_id, start_date, active) " +
                     "VALUES (?, ?, ?, ?)";                             // SQL-INSERT

        Connection connection = DBConnection.getConnection();           // DB öffnen
        PreparedStatement stmt = connection.prepareStatement(sql);      // Statement bauen

        stmt.setInt(1, membership.getPersonsId());                      // persons_id setzen
        stmt.setInt(2, membership.getPlanId());                         // plan_id setzen
        stmt.setString(3, membership.getStartDate());                   // start_date setzen
        stmt.setBoolean(4, membership.isActive());                      // active setzen

        stmt.executeUpdate();                                           // INSERT ausführen

        stmt.close();                                                   // Statement schließen
        connection.close();                                             // Connection schließen
    }

    public void updateMembershipPlan(int personsId, int newPlanId) throws SQLException {
        // Plan einer Person ändern

        String sql = "UPDATE memberships SET plan_id = ? WHERE persons_id = ?"; // SQL-UPDATE

        Connection connection = DBConnection.getConnection();           // DB öffnen
        PreparedStatement stmt = connection.prepareStatement(sql);      // Statement bauen

        stmt.setInt(1, newPlanId);                                      // neuer plan_id
        stmt.setInt(2, personsId);                                      // Ziel person_id

        stmt.executeUpdate();                                           // UPDATE ausführen

        stmt.close();                                                   // Statement schließen
        connection.close();                                             // Connection schließen
    }

    public void setMembershipActive(int personsId, boolean active) throws SQLException {
        // Aktivstatus ändern

        String sql = "UPDATE memberships SET active = ? WHERE persons_id = ?";  // SQL-UPDATE

        Connection connection = DBConnection.getConnection();           // DB öffnen
        PreparedStatement stmt = connection.prepareStatement(sql);      // Statement bauen

        stmt.setBoolean(1, active);                                     // active setzen
        stmt.setInt(2, personsId);                                      // Ziel person_id

        stmt.executeUpdate();                                           // UPDATE ausführen

        stmt.close();                                                   // Statement schließen
        connection.close();                                             // Connection schließen
    }
}
