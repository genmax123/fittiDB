package de.genrichgolzsch.fittidb.dao;                                      // DAO-Paket

import de.genrichgolzsch.fittidb.db.DBConnection;                           // DB-Verbindung
import de.genrichgolzsch.fittidb.model.Person;                              // Model Person

import java.sql.Connection;                                                 // JDBC Connection
import java.sql.PreparedStatement;                                          // PreparedStatement
import java.sql.ResultSet;                                                  // ResultSet
import java.sql.SQLException;                                               // SQL Exception
import java.util.ArrayList;                                                 // Liste
import java.util.List;                                                      // List Interface

/**
 * DAO für die Tabelle "persons".
 */
public class PersonDao {                                                    // Klassenbeginn

    public List<Person> getAllPersons() throws SQLException {               // Alle Personen holen

        String sql = "SELECT persons_id, first_name, last_name, birthdate, email, phone_nr, " +
                     "street, house_number, postal_code, city " +
                     "FROM persons " +
                     "ORDER BY last_name, first_name";                     // SQL-Statement

        Connection connection = DBConnection.getConnection();               // DB öffnen
        PreparedStatement stmt = connection.prepareStatement(sql);          // Statement bauen
        ResultSet rs = stmt.executeQuery();                                 // Abfrage ausführen

        List<Person> persons = new ArrayList<>();                           // Ergebnisliste

        while (rs.next()) {                                                 // Solange Datensätze da sind

            int id = rs.getInt("persons_id");                               // ID lesen
            String firstName = rs.getString("first_name");                  // Vorname lesen
            String lastName = rs.getString("last_name");                    // Nachname lesen
            String birthdate = rs.getString("birthdate");                   // Datum als String
            String email = rs.getString("email");                           // E-Mail lesen
            String phoneNr = rs.getString("phone_nr");                      // Telefon lesen
            String street = rs.getString("street");                         // Straße lesen
            String houseNumber = rs.getString("house_number");              // Hausnummer lesen
            String postalCode = rs.getString("postal_code");                // PLZ lesen
            String city = rs.getString("city");                             // Stadt lesen

            Person person = new Person(id, firstName, lastName, birthdate,
                                       email, phoneNr, street, houseNumber,
                                       postalCode, city);                   // Person-Objekt bauen

            persons.add(person);                                            // Zur Liste hinzufügen
        }

        rs.close();                                                         // ResultSet schließen
        stmt.close();                                                       // Statement schließen
        connection.close();                                                  // Connection schließen

        return persons;                                                      // Liste zurückgeben
    }

    public void insertPerson(Person person) throws SQLException {           // Neue Person einfügen

        String sql = "INSERT INTO persons " +
                     "(first_name, last_name, birthdate, email, phone_nr, street, house_number, postal_code, city) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";                  // INSERT Statement

        Connection connection = DBConnection.getConnection();               // DB öffnen
        PreparedStatement stmt = connection.prepareStatement(sql);          // Statement bauen

        stmt.setString(1, person.getFirstName());                           // 1: Vorname
        stmt.setString(2, person.getLastName());                            // 2: Nachname
        stmt.setString(3, person.getBirthdate());                           // 3: Geburtsdatum
        stmt.setString(4, person.getEmail());                               // 4: E-Mail
        stmt.setString(5, person.getPhoneNr());                             // 5: Telefon
        stmt.setString(6, person.getStreet());                              // 6: Straße
        stmt.setString(7, person.getHouseNumber());                         // 7: Hausnummer
        stmt.setString(8, person.getPostalCode());                          // 8: PLZ
        stmt.setString(9, person.getCity());                                // 9: Stadt

        stmt.executeUpdate();                                               // INSERT ausführen

        stmt.close();                                                       // Statement schließen
        connection.close();                                                  // Connection schließen
    }
}
