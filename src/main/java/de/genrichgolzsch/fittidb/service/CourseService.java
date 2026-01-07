package de.genrichgolzsch.fittidb.service;                          // Service-Paket

import de.genrichgolzsch.fittidb.dao.CourseBookingDao;              // DAO für Buchungen
import de.genrichgolzsch.fittidb.dao.CourseDao;                     // DAO für Kurse
import de.genrichgolzsch.fittidb.model.Course;                      // Model Course
import de.genrichgolzsch.fittidb.model.CourseBooking;               // Model CourseBooking

import java.sql.SQLException;                                       // SQL-Fehler
import java.util.List;                                              // List

/**
 * Service-Klasse für Kurse und Kursbuchungen.
 * Enthält fachliche Regeln (z. B. max. Teilnehmer).
 */
public class CourseService {                                        // Klassenbeginn

    private final CourseDao courseDao = new CourseDao();            // Kurs-DAO
    private final CourseBookingDao bookingDao = new CourseBookingDao(); // Buchungs-DAO

    public List<Course> getAllCourses() throws SQLException {       // Alle Kurse laden
        return courseDao.getAllCourses();                           // Delegation an DAO
    }

    public List<CourseBooking> getBookingsForCourse(int courseId) throws SQLException {
        // Buchungen zu einem Kurs laden
        return bookingDao.getBookingsByCourse(courseId);            // Delegation an DAO
    }

    public List<CourseBooking> getBookingsForPerson(int personsId) throws SQLException {
        // Buchungen einer Person laden
        return bookingDao.getBookingsByPerson(personsId);           // Delegation an DAO
    }

    public void createCourse(Course course) throws SQLException {   // Kurs anlegen
        courseDao.insertCourse(course);                             // INSERT über DAO
    }

    public void bookCourse(int personsId, int courseId, String bookingDate) throws SQLException {
        // Person in Kurs einbuchen

        Course course = courseDao.getCourseById(courseId);          // Kurs laden

        if (course == null) {                                       // Kurs existiert nicht?
            throw new IllegalArgumentException("Kurs existiert nicht");
        }

        List<CourseBooking> currentBookings = bookingDao.getBookingsByCourse(courseId);
        // Aktuelle Buchungen laden

        if (currentBookings.size() >= course.getMaxParticipants()) { // Kurs voll?
            throw new IllegalStateException("Kurs ist voll");
        }

        List<CourseBooking> personBookings = bookingDao.getBookingsByPerson(personsId);
        // Buchungen dieser Person laden

        for (CourseBooking b : personBookings) {                    // Alle durchgehen
            if (b.getCourseId() == courseId) {                      // Schon gebucht?
                throw new IllegalStateException("Person ist bereits in diesem Kurs gebucht");
            }
        }

        CourseBooking booking = new CourseBooking(0, personsId, courseId, bookingDate);
        // Booking-Objekt bauen (bookingId wird von DB vergeben)

        bookingDao.insertBooking(booking);                          // Buchung speichern
    }

    public void cancelBooking(int bookingId) throws SQLException {  // Buchung löschen
        bookingDao.deleteBooking(bookingId);                        // DELETE über DAO
    }
    public List<String> getCourseOverviewLines() throws SQLException {                 // Überblickszeilen erzeugen

    List<Course> courses = courseDao.getAllCourses();                              // Alle Kurse laden
    List<String> lines = new java.util.ArrayList<>();                              // Ergebnisliste

    for (Course c : courses) {                                                     // Jeden Kurs durchgehen
        int current = bookingDao.getBookingsByCourse(c.getCourseId()).size();      // Aktuelle Teilnehmer zählen

        String line = c.getCourseName() + " | "                                    // Kursname
                    + current + "/" + c.getMaxParticipants() + " | "               // Belegung
                    + (c.getDescription() == null ? "" : c.getDescription());      // Beschreibung

        lines.add(line);                                                          // Zeile hinzufügen
    }

    return lines;                                                                  // Zeilen zurückgeben
}

}
