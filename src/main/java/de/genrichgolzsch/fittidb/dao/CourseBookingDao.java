package de.genrichgolzsch.fittidb.dao;                                   // DAO-Paket

import de.genrichgolzsch.fittidb.db.DBConnection;                        // DB-Verbindung
import de.genrichgolzsch.fittidb.model.CourseBooking;                   // Model CourseBooking

import java.sql.Connection;                                             // JDBC Connection
import java.sql.PreparedStatement;                                      // PreparedStatement
import java.sql.ResultSet;                                              // ResultSet
import java.sql.SQLException;                                           // SQL Exception
import java.util.ArrayList;                                             // ArrayList
import java.util.List;                                                  // List

/**
 * DAO für die Tabelle "course_bookings".
 * Verwaltet n:m-Beziehungen zwischen Personen und Kursen.
 */
public class CourseBookingDao {                                         // Klassenbeginn

    public List<CourseBooking> getBookingsByCourse(int courseId) throws SQLException {
        // Alle Buchungen zu einem Kurs laden

        String sql = "SELECT booking_id, persons_id, course_id, booking_date " +
                     "FROM course_bookings " +
                     "WHERE course_id = ?";                             // SQL-SELECT

        Connection connection = DBConnection.getConnection();           // DB öffnen
        PreparedStatement stmt = connection.prepareStatement(sql);      // Statement bauen

        stmt.setInt(1, courseId);                                       // course_id setzen

        ResultSet rs = stmt.executeQuery();                             // Abfrage ausführen

        List<CourseBooking> bookings = new ArrayList<>();               // Ergebnisliste

        while (rs.next()) {                                             // Datensätze durchlaufen

            CourseBooking booking = new CourseBooking(
                rs.getInt("booking_id"),                                // booking_id
                rs.getInt("persons_id"),                                // persons_id
                rs.getInt("course_id"),                                 // course_id
                rs.getString("booking_date")                            // booking_date
            );

            bookings.add(booking);                                      // Zur Liste hinzufügen
        }

        rs.close();                                                     // ResultSet schließen
        stmt.close();                                                   // Statement schließen
        connection.close();                                             // Connection schließen

        return bookings;                                                // Buchungsliste zurückgeben
    }

    public List<CourseBooking> getBookingsByPerson(int personsId) throws SQLException {
        // Alle Buchungen einer Person laden

        String sql = "SELECT booking_id, persons_id, course_id, booking_date " +
                     "FROM course_bookings " +
                     "WHERE persons_id = ?";                            // SQL-SELECT

        Connection connection = DBConnection.getConnection();           // DB öffnen
        PreparedStatement stmt = connection.prepareStatement(sql);      // Statement bauen

        stmt.setInt(1, personsId);                                      // persons_id setzen

        ResultSet rs = stmt.executeQuery();                             // Abfrage ausführen

        List<CourseBooking> bookings = new ArrayList<>();               // Ergebnisliste

        while (rs.next()) {                                             // Datensätze durchlaufen

            CourseBooking booking = new CourseBooking(
                rs.getInt("booking_id"),                                // booking_id
                rs.getInt("persons_id"),                                // persons_id
                rs.getInt("course_id"),                                 // course_id
                rs.getString("booking_date")                            // booking_date
            );

            bookings.add(booking);                                      // Zur Liste hinzufügen
        }

        rs.close();                                                     // ResultSet schließen
        stmt.close();                                                   // Statement schließen
        connection.close();                                             // Connection schließen

        return bookings;                                                // Buchungsliste zurückgeben
    }

    public void insertBooking(CourseBooking booking) throws SQLException {
        // Neue Kursbuchung anlegen

        String sql = "INSERT INTO course_bookings (persons_id, course_id, booking_date) " +
                     "VALUES (?, ?, ?)";                                // SQL-INSERT

        Connection connection = DBConnection.getConnection();           // DB öffnen
        PreparedStatement stmt = connection.prepareStatement(sql);      // Statement bauen

        stmt.setInt(1, booking.getPersonsId());                         // persons_id setzen
        stmt.setInt(2, booking.getCourseId());                          // course_id setzen
        stmt.setString(3, booking.getBookingDate());                    // booking_date setzen

        stmt.executeUpdate();                                           // INSERT ausführen

        stmt.close();                                                   // Statement schließen
        connection.close();                                             // Connection schließen
    }

    public void deleteBooking(int bookingId) throws SQLException {
        // Kursbuchung löschen

        String sql = "DELETE FROM course_bookings WHERE booking_id = ?"; // SQL-DELETE

        Connection connection = DBConnection.getConnection();           // DB öffnen
        PreparedStatement stmt = connection.prepareStatement(sql);      // Statement bauen

        stmt.setInt(1, bookingId);                                      // booking_id setzen

        stmt.executeUpdate();                                           // DELETE ausführen

        stmt.close();                                                   // Statement schließen
        connection.close();                                             // Connection schließen
    }
}
