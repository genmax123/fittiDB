package de.genrichgolzsch.fittidb.dao;                               // DAO-Paket

import de.genrichgolzsch.fittidb.db.DBConnection;                    // Zentrale DB-Verbindung
import de.genrichgolzsch.fittidb.model.MembershipPlan;               // Model MembershipPlan

import java.sql.Connection;                                          // JDBC Connection
import java.sql.PreparedStatement;                                   // PreparedStatement
import java.sql.ResultSet;                                           // ResultSet
import java.sql.SQLException;                                        // SQL Exception
import java.util.ArrayList;                                          // ArrayList
import java.util.List;                                               // List Interface

/**
 * DAO für die Tabelle "membership_plans".
 */
public class MembershipPlanDao {                                     // Klassenbeginn

    public List<MembershipPlan> getAllPlans() throws SQLException {  // Alle Pläne laden

        String sql = "SELECT plan_id, plan_name, monthly_price, description " +
                     "FROM membership_plans " +
                     "ORDER BY monthly_price";                       // SQL-SELECT

        Connection connection = DBConnection.getConnection();         // DB öffnen
        PreparedStatement stmt = connection.prepareStatement(sql);   // Statement bauen
        ResultSet rs = stmt.executeQuery();                           // Abfrage ausführen

        List<MembershipPlan> plans = new ArrayList<>();               // Ergebnisliste

        while (rs.next()) {                                           // Datensätze durchlaufen

            int planId = rs.getInt("plan_id");                        // ID lesen
            String planName = rs.getString("plan_name");              // Name lesen
            double monthlyPrice = rs.getDouble("monthly_price");     // Preis lesen
            String description = rs.getString("description");         // Beschreibung lesen

            MembershipPlan plan = new MembershipPlan(
                planId, planName, monthlyPrice, description);         // Objekt bauen

            plans.add(plan);                                          // Zur Liste hinzufügen
        }

        rs.close();                                                   // ResultSet schließen
        stmt.close();                                                 // Statement schließen
        connection.close();                                           // Connection schließen

        return plans;                                                 // Liste zurückgeben
    }

    public MembershipPlan getPlanById(int planId) throws SQLException { // Plan nach ID laden

        String sql = "SELECT plan_id, plan_name, monthly_price, description " +
                     "FROM membership_plans " +
                     "WHERE plan_id = ?";                             // SQL mit Platzhalter

        Connection connection = DBConnection.getConnection();         // DB öffnen
        PreparedStatement stmt = connection.prepareStatement(sql);   // Statement bauen

        stmt.setInt(1, planId);                                       // Platzhalter setzen

        ResultSet rs = stmt.executeQuery();                           // Abfrage ausführen

        if (rs.next()) {                                              // Treffer vorhanden?

            MembershipPlan plan = new MembershipPlan(
                rs.getInt("plan_id"),                                 // ID
                rs.getString("plan_name"),                            // Name
                rs.getDouble("monthly_price"),                        // Preis
                rs.getString("description")                           // Beschreibung
            );

            rs.close();                                               // ResultSet schließen
            stmt.close();                                             // Statement schließen
            connection.close();                                       // Connection schließen

            return plan;                                              // Plan zurückgeben
        }

        rs.close();                                                   // ResultSet schließen
        stmt.close();                                                 // Statement schließen
        connection.close();                                           // Connection schließen

        return null;                                                  // Kein Plan gefunden
    }
}
