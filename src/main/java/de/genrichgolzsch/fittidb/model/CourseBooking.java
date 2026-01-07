package de.genrichgolzsch.fittidb.model;                 // Model-Paket

/**
 * Repräsentiert eine Kursbuchung (Junktionstabelle).
 */
public class CourseBooking {                             // Klassenbeginn

    private int bookingId;                               // Primary Key
    private int personsId;                               // FK zu persons
    private int courseId;                                // FK zu courses
    private String bookingDate;                          // Buchungsdatum (YYYY-MM-DD)

    public CourseBooking(int bookingId,
                         int personsId,
                         int courseId,
                         String bookingDate) {           // Konstruktor
        this.bookingId = bookingId;                      // ID setzen
        this.personsId = personsId;                      // Personen-ID setzen
        this.courseId = courseId;                        // Kurs-ID setzen
        this.bookingDate = bookingDate;                  // Datum setzen
    }

    public int getBookingId() {                          // Getter bookingId
        return bookingId;                                // Rückgabe
    }

    public int getPersonsId() {                          // Getter personsId
        return personsId;                                // Rückgabe
    }

    public int getCourseId() {                           // Getter courseId
        return courseId;                                 // Rückgabe
    }

    public String getBookingDate() {                     // Getter bookingDate
        return bookingDate;                              // Rückgabe
    }
}
