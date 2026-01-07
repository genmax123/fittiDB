package de.genrichgolzsch.fittidb.service;                 // Service-Schicht

import de.genrichgolzsch.fittidb.dao.UserDao;              // Zugriff auf User-DAO
import de.genrichgolzsch.fittidb.model.User;               // User-Datenobjekt

import java.sql.SQLException;                              // SQL-Fehler

/**
 * Service-Klasse für Authentifizierung (Login).
 */
public class AuthService {                                 // Klassenbeginn

    private final UserDao userDao = new UserDao();         // DAO-Instanz

    /**
     * Prüft Login-Daten und gibt den User zurück,
     * falls der Login erfolgreich ist.
     */
    public User login(String username, String password) {  // Login-Methode
        try {                                               // Fehler abfangen

            User user = userDao.login(username, password); // DB-Abfrage

            if (user == null) {                             // Kein Treffer?
                return null;                                // Login fehlgeschlagen
            }

            if (!user.isActive()) {                         // Benutzer inaktiv?
                return null;                                // Login verweigert
            }

            return user;                                    // Login erfolgreich

        } catch (SQLException e) {                          // SQL-Fehler
            e.printStackTrace();                            // Fehler ausgeben
            return null;                                    // Login fehlgeschlagen
        }
    }
}
