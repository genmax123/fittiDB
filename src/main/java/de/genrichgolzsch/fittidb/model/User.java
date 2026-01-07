package de.genrichgolzsch.fittidb.model; // Paket für Model- / Datenklassen

/**
 * Repräsentiert einen Benutzer aus der Tabelle "users".
 * Diese Klasse enthält NUR Daten, keine Logik.
 */
public class User { // Klassendefinition

    private int userId; // Eindeutige Benutzer-ID (Primary Key)

    private String username;  // Benutzername für den Login

    private int roleId; // Verweis auf die Rolle des Benutzers (Foreign Key)

    private boolean active; // Gibt an, ob der Benutzer aktiv ist

    /**
     * Konstruktor zum Erzeugen eines User-Objekts.
     */
    public User(int userId, String username, int roleId, boolean active) { // Konstruktor-Definition

        this.userId = userId; // Übergibt die Benutzer-ID

        this.username = username; // Übergibt den Benutzernamen

        this.roleId = roleId; // Übergibt die Rollen-ID

        this.active = active; // Übergibt den Aktiv-Status
    }

    public int getUserId() {  // Getter für userId
        return userId;
    }

    public String getUsername() { // Getter für username
        return username;
    }

    public int getRoleId() { // Getter für roleId
        return roleId;
    }

    public boolean isActive() {   // Getter für active
        return active;
    }
}
