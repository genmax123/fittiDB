package de.genrichgolzsch.fittidb.dao;                       // DAO-Paket

import de.genrichgolzsch.fittidb.db.DBConnection;            // Zentrale DB-Verbindung
import de.genrichgolzsch.fittidb.model.Role;                 // Model Role

import java.sql.Connection;                                  // JDBC Connection
import java.sql.PreparedStatement;                           // PreparedStatement
import java.sql.ResultSet;                                   // ResultSet
import java.sql.SQLException;                                // SQL Exception
import java.util.ArrayList;                                  // ArrayList
import java.util.List;                                       // List Interface

/**
 * DAO für die Tabelle "roles".
 */
public class RoleDao {                                       // Klassenbeginn

    public List<Role> getAllRoles() throws SQLException {    // Alle Rollen laden

        String sql = "SELECT role_id, role_name, description FROM roles";
        // SQL-SELECT für Rollen

        Connection connection = DBConnection.getConnection(); // DB öffnen
        PreparedStatement stmt = connection.prepareStatement(sql); // Statement bauen
        ResultSet rs = stmt.executeQuery();                   // Abfrage ausführen

        List<Role> roles = new ArrayList<>();                 // Ergebnisliste

        while (rs.next()) {                                   // Alle Datensätze durchlaufen

            int roleId = rs.getInt("role_id");                // ID lesen
            String roleName = rs.getString("role_name");      // Name lesen
            String description = rs.getString("description");// Beschreibung lesen

            Role role = new Role(roleId, roleName, description);
            // Role-Objekt erstellen

            roles.add(role);                                  // Zur Liste hinzufügen
        }

        rs.close();                                           // ResultSet schließen
        stmt.close();                                         // Statement schließen
        connection.close();                                   // Connection schließen

        return roles;                                         // Rollenliste zurückgeben
    }

    public Role getRoleById(int roleId) throws SQLException { // Rolle nach ID laden

        String sql = "SELECT role_id, role_name, description FROM roles WHERE role_id = ?";
        // SQL-SELECT mit Platzhalter

        Connection connection = DBConnection.getConnection(); // DB öffnen
        PreparedStatement stmt = connection.prepareStatement(sql); // Statement bauen

        stmt.setInt(1, roleId);                               // Platzhalter setzen

        ResultSet rs = stmt.executeQuery();                   // Abfrage ausführen

        if (rs.next()) {                                      // Treffer vorhanden?

            Role role = new Role(
                rs.getInt("role_id"),                         // ID
                rs.getString("role_name"),                    // Name
                rs.getString("description")                   // Beschreibung
            );

            rs.close();                                       // ResultSet schließen
            stmt.close();                                     // Statement schließen
            connection.close();                               // Connection schließen

            return role;                                      // Rolle zurückgeben
        }

        rs.close();                                           // ResultSet schließen
        stmt.close();                                         // Statement schließen
        connection.close();                                   // Connection schließen

        return null;                                          // Keine Rolle gefunden
    }
}
