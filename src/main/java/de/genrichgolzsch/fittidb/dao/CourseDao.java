package de.genrichgolzsch.fittidb.dao;                                   // DAO-Paket

import de.genrichgolzsch.fittidb.db.DBConnection;                        // DB-Verbindung
import de.genrichgolzsch.fittidb.model.Course;                            // Model Course

import java.sql.Connection;                                              // JDBC Connection
import java.sql.PreparedStatement;                                       // PreparedStatement
import java.sql.ResultSet;                                               // ResultSet
import java.sql.SQLException;                                            // SQL Exception
import java.util.ArrayList;                                              // ArrayList
import java.util.List;                                                   // List

/**
 * DAO für die Tabelle "courses".
 */
public class CourseDao {                                                 // Klassenbeginn

    public List<Course> getAllCourses() throws SQLException {            // Alle Kurse laden

        String sql = "SELECT course_id, course_name, max_participants, description " +
                     "FROM courses " +
                     "ORDER BY course_name";                             // SQL-SELECT

        Connection connection = DBConnection.getConnection();            // DB öffnen
        PreparedStatement stmt = connection.prepareStatement(sql);       // Statement bauen
        ResultSet rs = stmt.executeQuery();                               // Abfrage ausführen

        List<Course> courses = new ArrayList<>();                        // Ergebnisliste

        while (rs.next()) {                                              // Datensätze durchlaufen

            int courseId = rs.getInt("course_id");                       // ID lesen
            String courseName = rs.getString("course_name");             // Name lesen
            int maxParticipants = rs.getInt("max_participants");         // Max. Teilnehmer lesen
            String description = rs.getString("description");            // Beschreibung lesen

            Course course = new Course(courseId, courseName, maxParticipants, description);
            // Course-Objekt bauen

            courses.add(course);                                         // Zur Liste hinzufügen
        }

        rs.close();                                                      // ResultSet schließen
        stmt.close();                                                    // Statement schließen
        connection.close();                                              // Connection schließen

        return courses;                                                  // Liste zurückgeben
    }

    public Course getCourseById(int courseId) throws SQLException {      // Kurs nach ID laden

        String sql = "SELECT course_id, course_name, max_participants, description " +
                     "FROM courses " +
                     "WHERE course_id = ?";                              // SQL-SELECT mit Platzhalter

        Connection connection = DBConnection.getConnection();            // DB öffnen
        PreparedStatement stmt = connection.prepareStatement(sql);       // Statement bauen

        stmt.setInt(1, courseId);                                        // Platzhalter setzen

        ResultSet rs = stmt.executeQuery();                              // Abfrage ausführen

        if (rs.next()) {                                                 // Treffer vorhanden?

            Course course = new Course(
                rs.getInt("course_id"),                                  // ID
                rs.getString("course_name"),                             // Name
                rs.getInt("max_participants"),                           // Max. Teilnehmer
                rs.getString("description")                              // Beschreibung
            );

            rs.close();                                                  // ResultSet schließen
            stmt.close();                                                // Statement schließen
            connection.close();                                          // Connection schließen

            return course;                                               // Kurs zurückgeben
        }

        rs.close();                                                      // ResultSet schließen
        stmt.close();                                                    // Statement schließen
        connection.close();                                              // Connection schließen

        return null;                                                     // Kein Kurs gefunden
    }

    public void insertCourse(Course course) throws SQLException {        // Neuen Kurs einfügen

        String sql = "INSERT INTO courses (course_name, max_participants, description) " +
                     "VALUES (?, ?, ?)";                                 // SQL-INSERT

        Connection connection = DBConnection.getConnection();            // DB öffnen
        PreparedStatement stmt = connection.prepareStatement(sql);       // Statement bauen

        stmt.setString(1, course.getCourseName());                       // course_name setzen
        stmt.setInt(2, course.getMaxParticipants());                     // max_participants setzen
        stmt.setString(3, course.getDescription());                      // description setzen

        stmt.executeUpdate();                                            // INSERT ausführen

        stmt.close();                                                    // Statement schließen
        connection.close();                                              // Connection schließen
    }
}
