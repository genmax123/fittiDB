package de.genrichgolzsch.fittidb.model;              // Model-Paket

/**
 * Repräsentiert eine Rolle aus der Tabelle "roles".
 */
public class Role {                                   // Klassenbeginn

    private int roleId;                                // Primary Key
    private String roleName;                           // Rollenname
    private String description;                        // Beschreibung

    public Role(int roleId, String roleName, String description) { // Konstruktor
        this.roleId = roleId;                          // ID setzen
        this.roleName = roleName;                      // Name setzen
        this.description = description;                // Beschreibung setzen
    }

    public int getRoleId() {                           // Getter roleId
        return roleId;                                 // Rückgabe
    }

    public String getRoleName() {                      // Getter roleName
        return roleName;                               // Rückgabe
    }

    public String getDescription() {                   // Getter description
        return description;                            // Rückgabe
    }
}
